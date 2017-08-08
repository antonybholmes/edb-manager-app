package org.edb.manager.app.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.abh.common.bioinformatics.annotation.Type;
import org.abh.common.io.FileUtils;
import org.abh.common.io.PathUtils;
import org.abh.common.path.Path;
import org.abh.common.text.Parser;
import org.abh.common.text.TextUtils;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.Person;
import edu.columbia.rdf.edb.Species;

public class RnaSeq {
	public static void main(String[] args) throws SQLException, IOException, ParseException {
		Connection connection = DatabaseService.getConnection();

		try {
			createExperiment(connection, 
					PathUtils.getPath("/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20160729/"));
		} finally {
			connection.close();
		}
	}


	public static void createExperiment(Connection connection, 
			java.nio.file.Path dir) throws SQLException, IOException, ParseException {
		java.nio.file.Path idfFile = dir.resolve("data.idf");

		int[] ids = Experiments.createExperimentFromIdf(connection, 
				idfFile);

		int experimentId = ids[0];
		int exVfsId = ids[1];

		Experiment experiment = 
				Experiments.getExperiment(connection, experimentId);

		Person person = Persons.createPersonFromIdf(connection, idfFile);
		Persons.createRoleFromIdf(connection, idfFile);

		java.nio.file.Path sdrfFile = dir.resolve("data.sdrf");

		createSamplesFromSDRF(connection,
				sdrfFile,
				experiment,
				person,
				exVfsId);
	}

	public static void createSamplesFromSDRF(Connection connection, 
			java.nio.file.Path sdrfFile,
			Experiment experiment,
			Person person,
			int exVfsId) throws SQLException, IOException, SQLException, ParseException {
		BufferedReader reader = FileUtils.newBufferedReader(sdrfFile);

		String line;

		int samplesVfsRootId = VFS.createSamplesDir(connection, exVfsId);

		try {
			List<String> header = 
					TextUtils.fastSplit(reader.readLine(), TextUtils.TAB_DELIMITER);

			while ((line = reader.readLine()) != null) {
				System.err.println(line);

				List<String> tokens = 
						TextUtils.fastSplit(line, TextUtils.TAB_DELIMITER);

				String name = 
						tokens.get(TextUtils.findFirst(header, "Name"));

				String seqId = 
						tokens.get(TextUtils.findFirst(header, "Seq Id"));

				Species organism = Experiments.getOrganism(connection,
						tokens.get(TextUtils.findFirst(header, "Organism")));

				Type genome = Genomic.createGenome(connection, 
						tokens.get(TextUtils.findFirst(header, "Genome")));

				String date = 
						tokens.get(TextUtils.findFirst(header, "Release Date"));
				
				int readLength = 
						Parser.toInt(tokens.get(TextUtils.findFirst(header, "Read Length")));

				java.nio.file.Path dataDir = 
						PathUtils.getPath(tokens.get(TextUtils.findFirst(header, "Raw Data Directory")), name);

				int sampleId = createRnaSeqSample(connection, 
						experiment,
						name,
						seqId,
						organism,
						genome,
						readLength,
						person,
						date);

				String file = name + ".fpkm.txt";

				int sampleVfsId = VFS.createVfsFile(connection, samplesVfsRootId, file);

				VFS.createSampleFileLink(connection, sampleId, sampleVfsId);

				VFS.updatePath(connection, 
						sampleVfsId, 
						"/rna_seq/" + genome.getName() + "/rdf/" + experiment.getPublicId() + "/" + file);

				createGEO(connection, sampleId, header, tokens);
			}
		} finally {
			reader.close();
		}

		//ExperimentPermissions.main(null);
		//SamplePermissions.main(null);
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

		Type expressionType = 
				Samples.createExpressionType(connection, "RNA-seq");

		Type experimentField = 
				Tags.createTag(connection, new Path("Experiment", "Name"));

		Type role = Persons.createRole(connection, "Investigator");

		int sampleId = Samples.createSample(connection,
				experiment.getId(), 
				expressionType.getId(), 
				name,
				organism.getId(),
				releaseDate);

		Type field = 
				Tags.createTag(connection, new Path("Sample", "Name"));

		// Index the sample name
		addSampleSearchTerms(connection,
				sampleId, 
				field, 
				TextUtils.keywords(name));

		Samples.createAlias(connection, sampleId, name);

		Samples.createSamplePerson(connection,
				sampleId, 
				person.getId(), 
				role);

		field = Tags.createTag(connection, new Path("Sample", "Organism"));

		// Index the sample name
		addSampleSearchTerms(connection,
				sampleId, 
				field, 
				TextUtils.keywords(organism.getScientificName()));

		field = Tags.createTag(connection, new Path("Sample", "Expression_Type"));

		// Index the sample name
		addSampleSearchTerm(connection,
				sampleId, 
				field, 
				"RNA-seq");

		field = Tags.createTag(connection, new Path("Sample", "Person"));

		addSampleSearchTerms(connection,
				sampleId, 
				field, 
				TextUtils.keywords(person.getName()));

		// Index samples on the experiment title

		addSampleSearchTerms(connection,
				sampleId, 
				experimentField, 
				TextUtils.keywords(experiment.getName()));

		//Files.createDirectory(connection, sampleId, dataDir);

		// VFS
		//int vfsId = VFS.createVfsDir(connection, PathUtils.toString(dataDir));
		//VFS.createSampleFileLink(connection, sampleId, vfsId);

		// seq id type
		Path path = new Path("RNA-seq", "Sample", "Seq_Id");

		field = Tags.createTag(connection, path);
		Samples.createSampleTag(connection, sampleId, field, seqId);
		addSampleSearchTerms(connection,
				sampleId, 
				field, 
				TextUtils.keywords(seqId));

		
		path = new Path("RNA-seq", "Sample", "Genome");
		field = Tags.createTag(connection, path);
		Samples.createSampleTag(connection, sampleId, field, genome.getName());
		addSampleSearchTerms(connection,
				sampleId, 
				field, 
				TextUtils.keywords(genome.getName()));
		
		//path = new Path("RNA-seq", "Sample", "Organism");
		//field = Fields.createField(connection, path);
		//Samples.createSampleField(connection, sampleId, field, organism.getName());
		//addSampleSearchTerms(connection,
		//		sampleId, 
		//		field, 
		//		TextUtils.keywords(genome.getName()));
		
		path = new Path("Sample", "Organism");
		field = Tags.createTag(connection, path);
		Samples.createSampleTag(connection, sampleId, field, organism.getName());
		addSampleSearchTerms(connection,
				sampleId, 
				field, 
				TextUtils.keywords(genome.getName()));
		
		path = new Path("RNA-seq", "Sample", "Read_Length");
		field = Tags.createTag(connection, path);
		Samples.createSampleTag(connection, 
				sampleId, 
				field, 
				readLength);

		
		return sampleId;
	}

	public static void addSampleSearchTerms(Connection connection,
			int sampleId, 
			Type field, 
			Set<String> keywords) throws SQLException, ParseException {
		Microarray.addSampleSearchTerms(connection,
				sampleId, 
				field, 
				keywords);

		// Default add it to all

		Type allField = Tags.createTag(connection, new Path("RNA-seq", "All"));

		Microarray.addSampleSearchTerms(connection,
				sampleId, 
				allField, 
				keywords);
	}

	public static void addSampleSearchTerm(Connection connection,
			int sampleId, 
			Type field, 
			String keyword) throws SQLException, ParseException {
		Microarray.addSampleSearchTerm(connection,
				sampleId, 
				field, 
				keyword);

		// Default add it to all

		Type allField = 
				Tags.createTag(connection, new Path("RNA-seq", "All"));

		Microarray.addSampleSearchTerm(connection,
				sampleId, 
				allField, 
				keyword);
	}

	public static void createGEO(Connection connection, 
			int sampleId, 
			List<String> header,
			List<String> tokens) throws SQLException, ParseException {
		String sa = tokens.get(TextUtils.findFirst(header, "GEO Series Accession"));

		String a = tokens.get(TextUtils.findFirst(header, "GEO Accession"));

		String p = tokens.get(TextUtils.findFirst(header, "GEO Platform"));

		Type field = Tags.createTag(connection, new Path("Sample", "GEO", "Series", "Accession"));

		addSampleSearchTerm(connection, sampleId, field, sa);

		field = Tags.createTag(connection, new Path("Sample", "GEO", "Accession"));

		addSampleSearchTerm(connection, sampleId, field, a);

		field = Tags.createTag(connection, new Path("Sample", "GEO", "Platform"));

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
