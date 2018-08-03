package edu.columbia.rdf.edb.manager.app.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.path.Path;
import org.jebtk.core.path.RootPath;
import org.jebtk.core.text.Parser;
import org.jebtk.core.text.TextUtils;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.Person;
import edu.columbia.rdf.edb.Species;

public class RnaSeq {
  public static void main(String[] args)
      throws SQLException, IOException, ParseException {
    Connection connection = DatabaseService.getConnection();

    try {
      createExperiment(connection,
          PathUtils.getPath(
              "/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20160729/"));
    } finally {
      connection.close();
    }
  }

  public static void createExperiment(Connection connection,
      java.nio.file.Path dir) throws SQLException, IOException, ParseException {
    java.nio.file.Path idfFile = dir.resolve("data.idf");

    int[] ids = Experiments.createExperimentFromIdf(connection, idfFile);

    int experimentId = ids[0];
    int exVfsId = ids[1];

    Experiment experiment = Experiments.getExperiment(connection, experimentId);

    Person person = Persons.createPersonFromIdf(connection, idfFile);
    Persons.createRoleFromIdf(connection, idfFile);

    java.nio.file.Path sdrfFile = dir.resolve("data.sdrf");

    createSamplesFromSDRF(connection, sdrfFile, experiment, person, exVfsId);
  }

  public static void createSamplesFromSDRF(Connection connection,
      java.nio.file.Path sdrfFile,
      Experiment experiment,
      Person person,
      int exVfsId)
          throws SQLException, IOException, SQLException, ParseException {
    BufferedReader reader = FileUtils.newBufferedReader(sdrfFile);

    String line;

    int samplesVfsRootId = VFS.createSamplesDir(connection, exVfsId);

    try {
      List<String> header = TextUtils.fastSplit(reader.readLine(),
          TextUtils.TAB_DELIMITER);

      while ((line = reader.readLine()) != null) {
        System.err.println(line);

        List<String> tokens = TextUtils.fastSplit(line,
            TextUtils.TAB_DELIMITER);

        String name = tokens.get(TextUtils.findFirst(header, "Name"));

        String seqId = tokens.get(TextUtils.findFirst(header, "Seq Id"));

        Species organism = Experiments.getOrganism(connection,
            tokens.get(TextUtils.findFirst(header, "Organism")));

        Type genome = Genomic.createGenome(connection,
            tokens.get(TextUtils.findFirst(header, "Genome")));

        String date = tokens.get(TextUtils.findFirst(header, "Release Date"));

        int readLength = Parser
            .toInt(tokens.get(TextUtils.findFirst(header, "Read Length")));



        int sampleId = createRnaSeqSample(connection,
            experiment,
            name,
            seqId,
            organism,
            genome,
            readLength,
            person,
            date);

        String fileId = tokens.get(TextUtils.findFirst(header, "File Id"));
        
        List<String> filetypes = 
            TextUtils.commaSplit(tokens.get(TextUtils.findFirst(header, "Filetypes")));

        for (String type : filetypes) {
          String file = fileId + "_" + type + ".txt";

          int vfsId = VFS.createVfsFile(connection, samplesVfsRootId, file);

          VFS.createSampleFileLink(connection, sampleId, vfsId);

          VFS.updatePath(connection,
              vfsId,
              "/rna_seq/rdf/" + 
              experiment.getPublicId() +
              "/" + genome.getName() + 
              "/" + file);
          
          // Link files via genome
          VFS.createSampleGenomeFile(connection, 
              sampleId,
              genome.getId(),
              vfsId);
        }

        createGEO(connection, sampleId, header, tokens);
      }
    } finally {
      reader.close();
    }

    // ExperimentPermissions.main(null);
    // SamplePermissions.main(null);
  }

  public static int createRnaSeqSample(Connection connection,
      Experiment experiment,
      String name,
      String seqId,
      Species organism,
      Type genome,
      int readLength,
      Person person,
      String releaseDate) throws SQLException, IOException, ParseException {

    Type expressionType = Samples.createDataType(connection, "RNA-Seq");

    Type experimentField = Tags.createTag(connection,
        new RootPath("Experiment", "Name"));

    Type role = Persons.createRole(connection, "Investigator");

    int sampleId = Samples.createSample(connection,
        experiment.getId(),
        expressionType.getId(),
        name,
        organism.getId(),
        releaseDate);

    Type field = Tags.createTag(connection, new RootPath("Sample", "Name"));

    // Index the sample name
    addSampleSearchTerms(connection, sampleId, field, TextUtils.keywords(name));

    Samples.createAlias(connection, sampleId, name);

    Samples.createSamplePerson(connection, sampleId, person.getId(), role);

    field = Tags.createTag(connection, new RootPath("Sample", "Organism"));

    // Index the sample name
    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(organism.getScientificName()));

    field = Tags.createTag(connection, new RootPath("Sample", "Expression_Type"));

    // Index the sample name
    addSampleSearchTerm(connection, sampleId, field, "RNA-Seq");

    field = Tags.createTag(connection, new RootPath("Sample", "Person"));

    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(person.getName()));

    // Index samples on the experiment title

    addSampleSearchTerms(connection,
        sampleId,
        experimentField,
        TextUtils.keywords(experiment.getName()));

    // Files.createDirectory(connection, sampleId, dataDir);

    // VFS
    // int vfsId = VFS.createVfsDir(connection, PathUtils.toString(dataDir));
    // VFS.createSampleFileLink(connection, sampleId, vfsId);

    // seq id type
    Path path = new RootPath("RNA-Seq", "Sample", "Seq_Id");

    field = Tags.createTag(connection, path);
    Samples.createSampleTag(connection, sampleId, field, seqId);
    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(seqId));

    path = new RootPath("RNA-Seq", "Sample", "Genome");
    field = Tags.createTag(connection, path);
    Samples.createSampleTag(connection, sampleId, field, genome.getName());
    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(genome.getName()));

    // path = new RootPath("RNA-seq", "Sample", "Organism");
    // field = Fields.createField(connection, path);
    // Samples.createSampleField(connection, sampleId, field,
    // organism.getName());
    // addSampleSearchTerms(connection,
    // sampleId,
    // field,
    // TextUtils.keywords(genome.getName()));

    path = new RootPath("Sample", "Organism");
    field = Tags.createTag(connection, path);
    Samples.createSampleTag(connection, sampleId, field, organism.getName());
    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(genome.getName()));

    path = new RootPath("RNA-Seq", "Sample", "Read_Length");
    field = Tags.createTag(connection, path);
    Samples.createSampleTag(connection, sampleId, field, readLength);
    
    ///path = new RootPath("RNA-Seq", "Sample", "File_Id");
    //field = Tags.createTag(connection, path);
    //Samples.createSampleTag(connection, sampleId, field, fileId);

    return sampleId;
  }

  public static void addSampleSearchTerms(Connection connection,
      int sampleId,
      Type field,
      Set<String> keywords) throws SQLException, ParseException {
    Microarray.addSampleSearchTerms(connection, sampleId, field, keywords);

    // Default add it to all

    Type allField = Tags.createTag(connection, new RootPath("RNA-Seq", "All"));

    Microarray.addSampleSearchTerms(connection, sampleId, allField, keywords);
  }

  public static void addSampleSearchTerm(Connection connection,
      int sampleId,
      Type field,
      String keyword) throws SQLException, ParseException {
    Microarray.addSampleSearchTerm(connection, sampleId, field, keyword);

    // Default add it to all

    Type allField = Tags.createTag(connection, new RootPath("RNA-Seq", "All"));

    Microarray.addSampleSearchTerm(connection, sampleId, allField, keyword);
  }

  public static void createGEO(Connection connection,
      int sampleId,
      List<String> header,
      List<String> tokens) throws SQLException, ParseException {
    String sa = tokens.get(TextUtils.findFirst(header, "GEO Series Accession"));

    String a = tokens.get(TextUtils.findFirst(header, "GEO Accession"));

    String p = tokens.get(TextUtils.findFirst(header, "GEO Platform"));

    Type field = Tags.createTag(connection,
        new RootPath("Sample", "GEO", "Series", "Accession"));

    addSampleSearchTerm(connection, sampleId, field, sa);

    field = Tags.createTag(connection, new RootPath("Sample", "GEO", "Accession"));

    addSampleSearchTerm(connection, sampleId, field, a);

    field = Tags.createTag(connection, new RootPath("Sample", "GEO", "Platform"));

    addSampleSearchTerm(connection, sampleId, field, p);

    if (sa == null || sa.toLowerCase().equals("n/a")) {
      return;
    }

    Samples.createGEO(connection, sampleId, sa, a, p);

    int geoPlatformId = GEO.createPlatform(connection, p);

    int geoSeriesId = GEO.createSeries(connection, sa);

    GEO.createSample(connection, geoSeriesId, geoPlatformId, sampleId, sa);
  }
}
