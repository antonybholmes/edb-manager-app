package edu.columbia.rdf.edb.manager.app.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.io.PathUtils;
import org.jebtk.core.path.Path;
import org.jebtk.core.text.TextUtils;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.Person;
import edu.columbia.rdf.edb.Species;

public class Microarray {
  public static void main(String[] args)
      throws SQLException, IOException, ParseException {

    Connection connection = DatabaseService.getConnection();

    try {
      Type assayId = Microarray.createAssay(connection, "Gene Expression");

      Type providerId = Microarray.createProvider(connection, "Affymetrix");

      Microarray
          .createArrayDesign(connection, "HG-U133_Plus_2", assayId, providerId);
      Microarray.createArrayDesign(connection, "HG_U95A", assayId, providerId);
      Microarray
          .createArrayDesign(connection, "HG_U95Av2", assayId, providerId);
      Microarray
          .createArrayDesign(connection, "Mouse_430_2", assayId, providerId);

      java.nio.file.Path sdrfFile;
      java.nio.file.Path idfFile;

      Species organismId;
      Type expressionType;

      Person personId;
      Type role;

      expressionType = Samples.createDataType(connection, "Microarray");

      //
      // Human experiments
      //

      organismId = Experiments
          .createOrganism(connection, "Human", "Homo sapiens");

      // "christof_2"
      idfFile = PathUtils.getPath(
          "/mnt/hddb/experimentdb/data/experiments/christof_2/data.idf");

      int[] ids = Experiments.createExperimentFromIdf(connection, idfFile);

      int experimentId = ids[0];
      int exVfsId = ids[1];

      Experiment experiment = Experiments.getExperiment(connection,
          experimentId);

      personId = Persons.createPersonFromIdf(connection, idfFile);
      role = Persons.createRoleFromIdf(connection, idfFile);

      sdrfFile = PathUtils.getPath(
          "/mnt/hddb/experimentdb/data/experiments/christof_2/data.sdrf");
      Microarray.createSamplesFromSDRF(connection,
          sdrfFile,
          experiment,
          personId.getId(),
          role,
          expressionType,
          organismId.getId(),
          exVfsId);

      /*
       * // roy idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/roy_1/data.idf");
       * 
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "roy_1"); personId = Persons.createPersonFromIdf(connection, idfFile);
       * role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/roy_1/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * 
       * 
       * // // laura //
       * 
       * 
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/laura_1/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "laura_1"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/laura_1/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * 
       * // ulf idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/ulf_1/data.idf");
       * 
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "ulf_1"); personId = Persons.createPersonFromIdf(connection, idfFile);
       * role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/ulf_1/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * 
       * 
       * 
       * 
       * 
       * 
       * // // katia // idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/katia_1/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "katia_1"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/katia_1/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * 
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/katia_2/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "katia_2"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/katia_2/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * 
       * 
       * 
       * // // jiyuan // idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_1/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "jiyuan_1"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_1/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_2/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "jiyuan_2"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_2/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_3/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "jiyuan_3"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_3/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * // // david //
       * 
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/david_1/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "david_1"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/david_1/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       */

      //
      // Mice experiments
      //

      organismId = Experiments
          .createOrganism(connection, "Mouse", "Mus musculus");

      // jiyuan

      /*
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_5/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "jiyuan_5"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_5/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * 
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_7/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "jiyuan_7"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_7/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       */

      /*
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_4/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "jiyuan_4"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_4/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       * 
       * 
       * 
       * 
       * idfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_6/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "jiyuan_6"); personId = Persons.createPersonFromIdf(connection,
       * idfFile); role = Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfFile = new
       * File("/mnt/hddb/experimentdb/data/experiments/jiyuan_6/data.sdrf");
       * GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
       * personId.getId(), role, expressionType, organismId.getId());
       */

      // david
      // idfFile = new
      // File("/mnt/hddb/experimentdb/data/experiments/david_2/data.idf");
      // experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
      // "david_2");
      // personId = Persons.createPersonFromIdf(connection, idfFile);
      // role = Persons.createRoleFromIdf(connection, idfFile);
      //
      // sdrfFile = new
      // File("/mnt/hddb/experimentdb/data/experiments/david_2/data.sdrf");
      // GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
      // personId.getId(), role, expressionType, organismId.getId());

      // idfFile = new
      // File("/mnt/hddb/experimentdb/data/experiments/david_3/data.idf");
      // experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
      // "david_3");
      // personId = Persons.createPersonFromIdf(connection, idfFile);
      // role = Persons.createRoleFromIdf(connection, idfFile);
      //
      // sdrfFile = new
      // File("/mnt/hddb/experimentdb/data/experiments/david_3/data.sdrf");
      // GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
      // personId.getId(), role, expressionType, organismId.getId());

      // idfFile =
      // PathUtils.getPath("/mnt/hddb/experimentdb/data/experiments/david_4/data.idf");
      // experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
      // "david_4");
      // personId = Persons.createPersonFromIdf(connection, idfFile);
      // role = Persons.createRoleFromIdf(connection, idfFile);
      //
      // sdrfFile =
      // PathUtils.getPath("/mnt/hddb/experimentdb/data/experiments/david_4/data.sdrf");
      // GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, dataDirectory2,
      // experimentId, personId.getId(), role, expressionType,
      // organismId.getId());

      // idfFile = new
      // File("/mnt/hddb/experimentdb/data/experiments/david_5/data.idf");
      // experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
      // "david_5");
      // personId = Persons.createPersonFromIdf(connection, idfFile);
      // role = Persons.createRoleFromIdf(connection, idfFile);
      //
      // sdrfFile = new
      // File("/mnt/hddb/experimentdb/data/experiments/david_5/data.sdrf");
      // GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
      // personId.getId(), role, expressionType, organismId.getId());

      // christof

      // idfFile = new
      // File("/mnt/hddb/experimentdb/data/experiments/christof_1/data.idf");
      // experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
      // "christof_1");
      // personId = Persons.createPersonFromIdf(connection, idfFile);
      // role = Persons.createRoleFromIdf(connection, idfFile);
      //
      // sdrfFile = new
      // File("/mnt/hddb/experimentdb/data/experiments/christof_1/data.sdrf");
      // GEP.createSamplesFromSDRF(sdrfFile, dataDirectory, experimentId,
      // personId.getId(), role, expressionType, organismId.getId());
    } finally {
      connection.close();
    }

    ExperimentPermissions.main(null);
    SamplePermissions.main(null);
  }

  public static void createExperiment(Connection connection,
      Species organism,
      java.nio.file.Path dir) throws SQLException, IOException, ParseException {
    java.nio.file.Path idfFile = dir.resolve("data.idf");

    int[] ids = Experiments.createExperimentFromIdf(connection, idfFile);

    int experimentId = ids[0];
    int exVfsId = ids[1];

    Experiment experiment = Experiments.getExperiment(connection, experimentId);

    Person person = Persons.createPersonFromIdf(connection, idfFile);
    Type role = Persons.createRoleFromIdf(connection, idfFile);

    java.nio.file.Path sdrfFile = dir.resolve("data.sdrf");

    Type expressionType = Samples.createDataType(connection,
        "Microarray");

    createSamplesFromSDRF(connection,
        sdrfFile,
        experiment,
        person.getId(),
        role,
        expressionType,
        organism.getId(),
        exVfsId);
  }

  /**
   * Creates samples for GEP based on the SDRF template.
   * 
   * @param sdrfFile
   * @param dataDirectory2
   * @param experimentId
   * @param personId
   * @param role
   * @param typeId
   * @param organismId
   * @throws SQLException
   * @throws IOException
   * @throws SQLException
   * @throws ParseException
   */
  public static void createSamplesFromSDRF(Connection connection,
      java.nio.file.Path sdrfFile,
      Experiment experiment,
      int personId,
      Type role,
      Type expressionType,
      int organismId,
      int exVfsId)
      throws SQLException, IOException, SQLException, ParseException {

    String line;

    Person person = Persons.getPerson(connection, personId);

    Map<Integer, Species> organisms = Species.getSpecies(connection);

    Type allGepField = Tags.createTag(connection,
        Path.createRootPath(expressionType.getName(), "All"));

    Type experimentField = Tags.createTag(connection,
        Path.createRootPath("Experiment", "Name"));

    int samplesVfsRootId = VFS.createSamplesDir(connection, exVfsId);

    BufferedReader reader = FileUtils.newBufferedReader(sdrfFile);

    try {
      List<String> header = TextUtils.fastSplit(reader.readLine(),
          TextUtils.TAB_DELIMITER);

      Map<String, Integer> nameIndexMap = TextUtils.valueIndexMap(header);

      while ((line = reader.readLine()) != null) {
        List<String> tokens = TextUtils.fastSplit(line,
            TextUtils.TAB_DELIMITER);

        String name = tokens.get(0);

        int sampleId = Samples.createSample(connection,
            experiment.getId(),
            expressionType.getId(),
            name,
            organismId);

        Type field = Tags.createTag(connection,
            Path.createRootPath("Sample", "Name"));

        addSampleSearchTerms(connection,
            sampleId,
            field,
            TextUtils.keywords(name));

        // Index the sample name
        addSampleSearchTerms(connection,
            sampleId,
            allGepField,
            TextUtils.keywords(name));

        Samples.createAlias(connection, sampleId, name);

        //Samples.createSamplePerson(connection, sampleId, personId, role);

        addSampleSearchTerms(connection,
            sampleId,
            allGepField,
            TextUtils.keywords(person.getName()));

        field = Tags.createTag(connection,
            Path.createRootPath("Sample", "Organism"));

        // Index the sample name

        System.err.println(
            sampleId + " " + field + " " + organismId + " " + organisms);

        addSampleSearchTerms(connection,
            sampleId,
            field,
            TextUtils.keywords(organisms.get(organismId).getScientificName()));

        field = Tags.createTag(connection,
            Path.createRootPath("Sample", "Expression_Type"));

        // Index the sample name
        addSampleSearchTerms(connection,
            sampleId,
            field,
            TextUtils.keywords("Microarray"));

        // Index samples on the experiment title

        addSampleSearchTerms(connection,
            sampleId,
            allGepField,
            TextUtils.keywords(experiment.getName()));

        addSampleSearchTerms(connection,
            sampleId,
            experimentField,
            TextUtils.keywords(experiment.getName()));

        String arrayDesign = tokens
            .get(nameIndexMap.get("Characteristics[Array Platform]"));

        getArrayDesign(connection, arrayDesign);

        Files.createDirectory(connection, sampleId, experiment.getPublicId());

        // We store all the samples in the same directory since there
        // are a lot of them
        int sampleDirVfsId = VFS
            .createVfsDir(connection, samplesVfsRootId, name);

        VFS.createExperimentFileLink(connection,
            experiment.getId(),
            sampleDirVfsId);

        // Link sample to its directory
        VFS.createSampleFileLink(connection, sampleId, sampleDirVfsId);

        String celFileName = tokens.get(nameIndexMap.get("Array Data File"));

        createVFSFile(connection,
            experiment,
            sampleId,
            sampleDirVfsId,
            celFileName);

        String chpFileName = tokens
            .get(nameIndexMap.get("Derived Array Data File"));

        createVFSFile(connection,
            experiment,
            sampleId,
            sampleDirVfsId,
            chpFileName);

        // Every experiment has rma and mas5 data on a per sample basis
        String mas5File = name + ".mas5-annotated";

        createVFSFile(connection,
            experiment,
            sampleId,
            sampleDirVfsId,
            mas5File);

        mas5File = name + ".mas5.CHP";

        createVFSFile(connection,
            experiment,
            sampleId,
            sampleDirVfsId,
            mas5File);

        String rmaFile = name + ".rma-annotated";

        createVFSFile(connection,
            experiment,
            sampleId,
            sampleDirVfsId,
            rmaFile);

        rmaFile = name + ".rma.chp";

        createVFSFile(connection,
            experiment,
            sampleId,
            sampleDirVfsId,
            rmaFile);

        // keyword
        // int fieldId = Keywords.createField(connection, "name");

        // Keywords.keywordIndexSample(connection, experimentId, sampleId,
        // fieldId, name);

        String section = "Source";

        for (int i = 0; i < header.size(); ++i) {
          if (header.get(i).startsWith("Sample Name")) {
            section = "Sample";
          } else if (header.get(i).startsWith("Extract Name")) {
            section = "Extract";
          } else if (header.get(i).startsWith("Labeled Extract Name")) {
            section = "Labeled_Extract";
          } else if (header.get(i).startsWith("Hybridization")) {
            section = "Hybridization";
          } else {

          }

          String fieldName = header.get(i);

          boolean isCharacteristic = fieldName.contains("Characteristics");

          fieldName = fieldName.replaceAll("\\%", "Percent");
          fieldName = fieldName.replaceAll("Characteristics\\[", "")
              .replaceAll("\\]", "");
          fieldName = fieldName.replaceAll(" +", "_");

          String value = tokens.get(i);

          // Index by name
          field = Tags.createTag(connection,
              Path.createRootPath("Sample", "Person"));

          System.err.println(
              person.getName() + " " + TextUtils.keywords(person.getName()));

          addSampleSearchTerms(connection,
              sampleId,
              field,
              TextUtils.keywords(person.getName()));

          // Index by field

          Path path;

          if (isCharacteristic) {
            path = Path.createRootPath(expressionType
                .getName(), "Sample", section, "Characteristic", fieldName);
          } else {
            path = Path.createRootPath(expressionType.getName(),
                "Sample",
                section,
                fieldName);
          }

          field = Tags.createTag(connection, path);

          Samples.createSampleTag(connection, sampleId, field, value);

          addSampleSearchTerms(connection,
              sampleId,
              field,
              TextUtils.keywords(value));

          addSampleSearchTerms(connection,
              sampleId,
              allGepField,
              TextUtils.keywords(value));

        }

        System.err.println("eh " + sampleId);

        // Test just insert one
        // break;
      }
    } finally {
      reader.close();
    }

    // GEO stuff

    reader = FileUtils.newBufferedReader(sdrfFile);

    try {
      List<String> header = TextUtils.fastSplit(reader.readLine(),
          TextUtils.TAB_DELIMITER);

      String seriesAccession = null;
      String sampleAccession = null;
      String platform = null;

      while ((line = reader.readLine()) != null) {
        List<String> tokens = TextUtils.fastSplit(line,
            TextUtils.TAB_DELIMITER);

        String name = tokens.get(0);

        int sampleId = Samples.createSample(connection,
            experiment.getId(),
            expressionType.getId(),
            name,
            organismId);

        for (int i = 0; i < header.size(); ++i) {
          if (header.get(i)
              .startsWith("Characteristics[GEO Series Accession]")) {
            seriesAccession = tokens.get(i);
          } else if (header.get(i)
              .startsWith("Characteristics[GEO Accession]")) {
            sampleAccession = tokens.get(i);
          } else if (header.get(i)
              .startsWith("Characteristics[GEO Platform]")) {
            platform = tokens.get(i);
          } else {

          }
        }

        if (sampleAccession == null
            || sampleAccession.toLowerCase().equals("n/a")) {
          continue;
        }

        int geoPlatformId = GEO.createPlatform(connection, platform);

        int geoSeriesId = GEO.createSeries(connection, seriesAccession);

        Samples.createGEO(connection,
            sampleId,
            seriesAccession,
            sampleAccession,
            platform);

        GEO.createSample(connection,
            geoSeriesId,
            geoPlatformId,
            sampleId,
            sampleAccession);
      }
    } finally {
      reader.close();
    }
  }

  public static void addSampleSearchTerms(Connection connection,
      int sampleId,
      Type field,
      Set<String> keywords) throws SQLException {
    for (String keyword : keywords) {
      addSampleSearchTerm(connection, sampleId, field, keyword);
    }
  }

  public static void addSampleSearchTerm(Connection connection,
      int sampleId,
      Type tag,
      String keyword) throws SQLException {
    // int categoryId = Search.createSearchCategory(connection, path);

    // Type field = Samples.createField(connection, path);

    Search.createSampleSearchKeyword(connection, sampleId, keyword, tag);

    // automatically add it to all
    addSampleSearchTermAll(connection, sampleId, keyword);

  }

  public static void addSampleSearchTermAll(Connection connection,
      int sampleId,
      String keyword) throws SQLException {
    // int categoryId = Search.createSearchCategory(connection, path);

    Type field = Tags.createTag(connection, "All");

    Search.createSampleSearchKeyword(connection, sampleId, keyword, field);
  }

  public static void createArrayDesign(Connection connection,
      String arrayDesign,
      String assay,
      String provider)
      throws SQLException, IOException, SQLException {

    Type providerId = createProvider(connection, provider);

    Type assayId = createAssay(connection, assay);

    createArrayDesign(connection, arrayDesign, assayId, providerId);
  }

  public static int createArrayDesign(Connection connection,
      String name,
      Type assayId,
      Type providerId) throws SQLException {
    System.err.println(name);

    PreparedStatement statement = connection.prepareStatement(
        "SELECT microarray_platforms.id FROM microarray_platforms WHERE microarray_platforms.name = ?");

    DatabaseResultsTable table;

    try {
      statement.setString(1, name);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return table.getInt(0, 0);
    }

    statement = connection.prepareStatement(
        "INSERT INTO microarray_platforms (name, assay_id, provider_id) VALUES (?, ?, ?)");

    try {
      statement.setString(1, name);
      statement.setInt(2, assayId.getId());
      statement.setInt(3, providerId.getId());

      statement.execute();
    } finally {
      statement.close();
    }

    return createArrayDesign(connection, name, assayId, providerId);
  }

  static Type createProvider(Connection connection, String name)
      throws SQLException {

    return Types.createType(connection, "providers", name);
  }

  public static Type createAssay(Connection connection, String name)
      throws SQLException {

    return Types.createType(connection, "microarray_assays", name);
  }

  public static int getArrayDesign(Connection connection, String name)
      throws SQLException {

    PreparedStatement statement = connection.prepareStatement(
        "SELECT microarray_platforms.id FROM microarray_platforms WHERE microarray_platforms.name = ?");

    DatabaseResultsTable table;

    try {
      statement.setString(1, name);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 0) {
      return -1;
    }

    return table.getInt(0, 0);
  }

  public static int createVFSFile(Connection connection,
      Experiment experiment,
      int sampleId,
      int sampleDirVfsId,
      String file) throws SQLException {
    int vfsId = VFS.createVfsFile(connection, sampleDirVfsId, file);

    VFS.updatePath(connection,
        vfsId,
        "/" + experiment.getPublicId() + "/" + file);

    // Meta data

    // Type field = Fields.createField(connection, "VFS/Name");
    // createVfsField(connection, vfsId, field, file);

    // VFS.addVfsSearchTerm(connection, vfsId, field, file);

    // field = Fields.createField(connection, "VFS/Version");
    // createVfsField(connection, vfsId, field, 1);

    // link samples to files

    VFS.createSampleFileLink(connection, sampleId, vfsId);

    // link experiment to files

    VFS.createExperimentFileLink(connection, experiment.getId(), vfsId);

    return vfsId;
  }
}
