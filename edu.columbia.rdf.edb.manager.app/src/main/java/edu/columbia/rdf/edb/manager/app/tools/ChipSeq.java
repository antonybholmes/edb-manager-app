package edu.columbia.rdf.edb.manager.app.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
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

public class ChipSeq {
  public static void main(String[] args)
      throws SQLException, IOException, ParseException {
    Connection connection = DatabaseService.getConnection();

    try {

      // "basso_11",
      createExperiment(connection,
          PathUtils.getPath(
              "/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20160729/"));

      // createExperiment(connection,
      // "fabbri_2",
      // PathUtils.getPath("/mnt/hddb/experimentdb/data/experiments/chip_seq/giulia_20160629/"));

      // createExperiment(connection,
      // "basso_10",
      // PathUtils.getPath("/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20160502/"));

      //
      // Katia 2016-01-27 Pooled P3H
      //
      // createExperiment(connection,
      // "basso_9",
      // PathUtils.getPath("/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20160127/"));

      /*
       * // // Katia 2016-01-26 //
       * 
       * idfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20140310/data.idf"
       * );
       * 
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "basso_8");
       * 
       * experiment = Experiments.getExperiment(connection, experimentId);
       * 
       * person = Persons.createPersonFromIdf(connection, idfFile);
       * Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20140310/data.sdrf"
       * ); createSamplesFromSDRF(connection, sdrfFile, experiment, person);
       */

      /*
       * // // Jiyian 2016-01-08 //
       * 
       * idfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/melnick_oci_20160108/data.idf"
       * );
       * 
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "melnick_2");
       * 
       * experiment = Experiments.getExperiment(connection, experimentId);
       * 
       * person = Persons.createPersonFromIdf(connection, idfFile);
       * Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/melnick_oci_20160108/data.sdrf"
       * ); createSamplesFromSDRF(connection, sdrfFile, experiment, person);
       */

      //
      // Katia 2015-10-01
      //

      /*
       * idfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20151001/data.idf"
       * );
       * 
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "basso_7");
       * 
       * experiment = Experiments.getExperiment(connection, experimentId);
       * 
       * person = Persons.createPersonFromIdf(connection, idfFile);
       * Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20151001/data.sdrf"
       * ); createSamplesFromSDRF(connection, sdrfFile, experiment, person);
       * 
       */

      //
      // Jiyuan 2015-09016
      //

      /*
       * idfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/jiyuan_20150916/data.idf"
       * );
       * 
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "jiyuan-chipseq-20150916");
       * 
       * experiment = Experiments.getExperiment(connection, experimentId);
       * 
       * person = Persons.createPersonFromIdf(connection, idfFile);
       * Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/jiyuan_20150916/data.sdrf"
       * ); createSamplesFromSDRF(connection, sdrfFile, experiment, person);
       * 
       */

      //
      // Katia 2015-07-09
      //

      /*
       * idfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20150709/data.idf"
       * );
       * 
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "basso-chipseq-20150709");
       * 
       * experiment = Experiments.getExperiment(connection, experimentId);
       * 
       * person = Persons.createPersonFromIdf(connection, idfFile);
       * Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/katia_20150709/data.sdrf"
       * ); createSamplesFromSDRF(connection, sdrfFile, experiment, person);
       */

      //
      // Melnick 1
      //

      /*
       * idfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/melnick_1/data.idf");
       * experimentId = Experiments.createExperimentFromIdf(connection, idfFile,
       * "melnick-chipseq-1"); experiment =
       * Experiments.getExperiment(connection, experimentId);
       * 
       * person = Persons.createPersonFromIdf(connection, idfFile);
       * Persons.createRoleFromIdf(connection, idfFile);
       * 
       * sdrfjava.nio.file.Path = new File(
       * "/mnt/hddb/experimentdb/data/experiments/chip_seq/melnick_1/data.sdrf")
       * ; createSamplesFromSDRF(connection, sdrfFile, experiment, person);
       */

      /*
       * 
       * // // Aster for Guilia //
       * 
       * experiment = Experiments.getExperiment(connection,
       * Experiments.createExperiment(connection, "Aster_001", "Aster Notch1",
       * "Aster Notch1"));
       * 
       * Organism organism = Experiments.createOrganism(connection, "Human",
       * "Homo sapiens");
       * 
       * Type genome = Genomic.createGenome(connection, "hg19");
       * 
       * String date = "2015-05-22";
       * 
       * Person person = Persons.createPerson(connection, "James", "Zou",
       * "Harvard University", "617-301-0832",
       * "185 Cambridge St., Boston MA 02114", "jzou@fas.harvard.edu");
       * 
       * Type cellType = createCellType(connection, "CUTLL"); Type chipType =
       * createChipType(connection, "Transcription Factor");
       * 
       * Type treatment = createTreatment(connection, "NOTCH1_1");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL_Notch1_1_AS003", "AS003", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "NOTCH1_2");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL_Notch1_2_AS004", "AS004", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "H3K4me1");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL_H3K4me1_1_AS005", "AS005", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "H3K4me3");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL_H3K4me3_1_AS006", "AS006", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "H3K27me3");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL_H3K27me3_1_AS007", "AS007", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "H3K27Ac_DMSO");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL1_H3K27Ac_DMSO_AS014", "AS014", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "H3K27Ac_w4h");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL1_H3K27Ac_w4h_AS012", "AS012", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "NOTCH1_w4h");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL1_NOTCH1_w4h_AS011", "AS011", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * chipType = createChipType(connection, "Input");
       * 
       * treatment = createTreatment(connection, "Input_1");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL_Input_1_AS001", "AS001", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "Input_2");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL_Input_2_AS002", "AS002", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "Input_w4h");
       * 
       * createChipSeqSample(connection, experiment,
       * "Aster_CUTLL1_Input_w4h_AS010", "AS010", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/hg19/");
       * 
       * 
       * 
       * // // Katia test //
       * 
       * experiment = Experiments.getExperiment(connection,
       * Experiments.createExperiment(connection, "Basso_20150-05-03",
       * "Katia CB Test", "Katia CB Test"));
       * 
       * organism = Experiments.createOrganism(connection, "Human",
       * "Homo sapiens");
       * 
       * genome = Genomic.createGenome(connection, "hg19");
       * 
       * date = "2015-05-03";
       * 
       * person = Persons.createPerson(connection, "Katia", "Basso",
       * "Columbia University", "212-851-5270",
       * "1130 St. Nicholas Ave., NY NY 10032", "kb451@columbia.edu");
       * 
       * cellType = createCellType(connection, "CB4"); chipType =
       * createChipType(connection, "Transcription Factor");
       * 
       * treatment = createTreatment(connection, "FOXO1_04");
       * 
       * createChipSeqSample(connection, experiment, "RDF_CB4_FOXO1_04_RK066",
       * "RK066", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "FOXO1_08");
       * 
       * createChipSeqSample(connection, experiment, "RDF_CB4_FOXO1_08_RK067",
       * "RK067", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "MEF2B_04");
       * 
       * createChipSeqSample(connection, experiment, "RDF_CB4_MEF2B_04_RK068",
       * "RK068", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "MEF2B_08");
       * 
       * createChipSeqSample(connection, experiment, "RDF_CB4_MEF2B_08_RK069",
       * "RK069", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/hg19/");
       * 
       * cellType = createCellType(connection, "CB5");
       * 
       * treatment = createTreatment(connection, "BCL6_04");
       * 
       * createChipSeqSample(connection, experiment, "RDF_CB5_BCL6_04_RK064",
       * "RK064", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/hg19/");
       * 
       * treatment = createTreatment(connection, "BCL6_08");
       * 
       * createChipSeqSample(connection, experiment, "RDF_CB5_BCL6_08_RK065",
       * "RK065", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/hg19/");
       * 
       * chipType = createChipType(connection, "Input");
       * 
       * treatment = createTreatment(connection, "Input_05");
       * 
       * createChipSeqSample(connection, experiment, "RDF_CB5_Input_05_RK070",
       * "RK070", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/hg19/");
       * 
       * // // Ge //
       * 
       * date = "2015-04-14";
       * 
       * experiment = Experiments.getExperiment(connection,
       * Experiments.createExperiment(connection, "GE1", "MLL4 Mice",
       * "MLL4 Mice"));
       * 
       * 
       * organism = Experiments.createOrganism(connection, "Mouse",
       * "Mus musculus");
       * 
       * genome = Genomic.createGenome(connection, "mm10");
       * 
       * person = Persons.createPerson(connection, "Kai", "Ge", "NIH",
       * "301-451-1998",
       * "Building 10, Room 8N307, 10 Center Drive Bethesda, MD 20814",
       * "kai.ge@nih.gov");
       * 
       * cellType = createCellType(connection, "GFP_preadipocytes"); chipType =
       * createChipType(connection, "Transcription Factor");
       * 
       * treatment = createTreatment(connection, "MLL4");
       * 
       * cellType = createCellType(connection, "GFP_preadipocytes");
       * createChipSeqSample(connection, experiment,
       * "Ge.GFP_preadipocytes.MLL4.GE002", "GE002", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/mm10/");
       * 
       * //System.exit(0);
       * 
       * cellType = createCellType(connection, "GFP_D5_myocytes");
       * createChipSeqSample(connection, experiment,
       * "Ge.GFP_D5_myocytes.MLL4.GE004", "GE004", chipType, cellType,
       * treatment, organism, genome, person, date,
       * 
       * "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "Cre_preadipocytes");
       * createChipSeqSample(connection, experiment,
       * "Ge.Cre_preadipocytes.MLL4.GE006", "GE006", chipType, cellType,
       * treatment, organism, genome, person, date,
       * 
       * "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "Cre_D5_myocytes");
       * createChipSeqSample(connection, experiment,
       * "Ge.Cre_D5_myocytes.MLL4.GE008", "GE008", chipType, cellType,
       * treatment, organism, genome, person, date,
       * 
       * "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "D2_WT");
       * createChipSeqSample(connection, experiment,
       * "Ge.MLL4_ChIPSeq_D2_WT.MLL4.GE010", "GE010", chipType, cellType,
       * treatment, organism, genome, person, date,
       * 
       * "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "D2_KO");
       * createChipSeqSample(connection, experiment,
       * "Ge.MLL4_ChIPSeq_D2_KO.MLL4.GE012", "GE012", chipType, cellType,
       * treatment, organism, genome, person, date,
       * 
       * "/chip_seq/mm10/");
       * 
       * chipType = createChipType(connection, "Input"); treatment =
       * createTreatment(connection, "Input"); cellType =
       * createCellType(connection, "GFP_preadipocytes");
       * createChipSeqSample(connection, experiment,
       * "Ge.GFP_preadipocytes.Input.GE001", "GE001", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "GFP_D5_myocytes");
       * createChipSeqSample(connection, experiment,
       * "Ge.GFP_D5_myocytes.Input.GE003", "GE003", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "Cre_preadipocytes");
       * createChipSeqSample(connection, experiment,
       * "Ge.Cre_preadipocytes.Input.GE005", "GE005", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "Cre_D5_myocytes");
       * createChipSeqSample(connection, experiment,
       * "Ge.Cre_D5_myocytes.Input.GE007", "GE007", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "D2_WT");
       * createChipSeqSample(connection, experiment, "Ge.DNA_D2_WT.Input.GE009",
       * "GE009", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/mm10/");
       * 
       * cellType = createCellType(connection, "D2_KO");
       * createChipSeqSample(connection, experiment, "Ge.DNA_D2_KO.Input.GE011",
       * "GE011", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/mm10/");
       * 
       * date = "2015-02-20";
       * 
       * // // Human experiments //
       * 
       * experiment = Experiments.getExperiment(connection,
       * Experiments.createExperiment(connection, "Basso_2015-03-12", "CB",
       * "CB"));
       * 
       * 
       * organism = Experiments.createOrganism(connection, "Human",
       * "Homo sapiens");
       * 
       * 
       * genome = Genomic.createGenome(connection, "hg19");
       * 
       * // // Duplicates removed //
       * 
       * date = "2015-03-12";
       * 
       * person = Persons.createPerson(connection, "Katia", "Basso",
       * "Columbia University", "212-851-5270",
       * "1130 St. Nicholas Ave., NY NY 10032", "kb451@columbia.edu");
       * 
       * cellType = createCellType(connection, "CB4"); chipType =
       * createChipType(connection, "Transcription Factor");
       * 
       * treatment = createTreatment(connection, "BCL6");
       * createChipSeqSample(connection, experiment, "CB4_BCL6_RK040", "RK040",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "CREBBP");
       * createChipSeqSample(connection, experiment, "CB4_CREBBP_RK041",
       * "RK041", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "EP300");
       * createChipSeqSample(connection, experiment, "CB4_EP300_RK042", "RK042",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "EZH2");
       * createChipSeqSample(connection, experiment, "CB4_EZH2_RK058", "RK058",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * createChipSeqSample(connection, experiment, "CB4_FOXO1_RK059", "RK059",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "MEF2B");
       * createChipSeqSample(connection, experiment, "CB4_MEF2B_RK060", "RK060",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "MLL2");
       * createChipSeqSample(connection, experiment, "CB4_MLL2_RK046", "RK046",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * cellType = createCellType(connection, "CB5");
       * 
       * treatment = createTreatment(connection, "BCL6");
       * createChipSeqSample(connection, experiment, "CB5_BCL6_RK050", "RK050",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "CREBBP");
       * createChipSeqSample(connection, experiment, "CB5_CREBBP_RK053",
       * "RK053", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "EP300");
       * createChipSeqSample(connection, experiment, "CB5_EP300_RK054", "RK054",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "EP300");
       * createChipSeqSample(connection, experiment, "CB5_EP300_RK061", "RK061",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "EZH2");
       * createChipSeqSample(connection, experiment, "CB5_EZH2_RK062", "RK062",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "FOXO1");
       * createChipSeqSample(connection, experiment, "CB5_FOXO1_RK051", "RK051",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "MEF2B");
       * createChipSeqSample(connection, experiment, "CB5_MEF2B_RK044", "RK044",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "MLL2");
       * createChipSeqSample(connection, experiment, "CB5_MLL2_RK052", "RK052",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * chipType = createChipType(connection, "Input");
       * 
       * cellType = createCellType(connection, "CB4");
       * 
       * treatment = createTreatment(connection, "Input");
       * createChipSeqSample(connection, experiment, "CB4_Input_RK063", "RK063",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * cellType = createCellType(connection, "CB5");
       * 
       * createChipSeqSample(connection, experiment, "CB5_Input_RK045", "RK045",
       * chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * chipType = createChipType(connection, "Histone");
       * 
       * cellType = createCellType(connection, "CB4");
       * 
       * treatment = createTreatment(connection, "H3K4me1");
       * createChipSeqSample(connection, experiment, "CB4_H3K4me1_RK049",
       * "RK049", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K4me3");
       * createChipSeqSample(connection, experiment, "CB4_H3K4me3_RK048",
       * "RK048", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K27Ac");
       * createChipSeqSample(connection, experiment, "CB4_H3K27Ac_RK043",
       * "RK043", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K27me3");
       * createChipSeqSample(connection, experiment, "CB4_H3K27me3_RK047",
       * "RK047", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * cellType = createCellType(connection, "CB5");
       * 
       * treatment = createTreatment(connection, "H3K4me1");
       * createChipSeqSample(connection, experiment, "CB5_H3K4me1_RK057",
       * "RK057", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K4me3");
       * createChipSeqSample(connection, experiment, "CB5_H3K4me3_RK056",
       * "RK056", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K27Ac");
       * createChipSeqSample(connection, experiment, "CB5_H3K27Ac_RK055",
       * "RK055", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * // Giulia
       * 
       * experiment = Experiments.getExperiment(connection,
       * Experiments.createExperiment(connection, "Fabbri_2015-03-12", "MO1043",
       * "MO1043"));
       * 
       * 
       * person = Persons.createPerson(connection, "Giulia", "Fabbri",
       * "Columbia University", "212-851-5270",
       * "1130 St. Nicholas Ave., NY NY 10032", "fabbri@icg.cpmc.columbia.edu");
       * 
       * chipType = createChipType(connection, "Transcription Factor"); cellType
       * = createCellType(connection, "MO1043");
       * 
       * treatment = createTreatment(connection, "HA");
       * createChipSeqSample(connection, experiment, "MO1043_ICN1HA_HA_RG010",
       * "RG010", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * createChipSeqSample(connection, experiment, "MO1043_ICN1HA_HA_RG016",
       * "RG016", chipType, cellType, treatment, organism, genome, person, date,
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "Tc");
       * createChipSeqSample(connection, experiment, "MO1043_ICN1HA_Tc_RG009",
       * "RG009", chipType, cellType, treatment, organism, genome, person, date,
       * 
       * "/chip_seq/");
       * 
       * createChipSeqSample(connection, experiment, "MO1043_ICN1HA_Tc_RG015",
       * "RG015", chipType, cellType, treatment, organism, genome, person, date,
       * 
       * "/chip_seq/");
       * 
       * chipType = createChipType(connection, "Input"); treatment =
       * createTreatment(connection, "Input");
       * 
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_Input_RG014", "RG014", chipType, cellType, treatment,
       * organism, genome, person, date,
       * 
       * "/chip_seq/");
       * 
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_Input_RG022", "RG022", chipType, cellType, treatment,
       * organism, genome, person, date,
       * 
       * "/chip_seq/");
       * 
       * chipType = createChipType(connection, "Histone");
       * 
       * treatment = createTreatment(connection, "H3K4me1");
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_H3K4me1_RG012", "RG012", chipType, cellType, treatment,
       * organism, genome, person, date,
       * 
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K4me1");
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_H3K4me1_RG018", "RG018", chipType, cellType, treatment,
       * organism, genome, person, date,
       * 
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K4me3");
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_H3K4me3_RG011", "RG011", chipType, cellType, treatment,
       * organism, genome, person, date,
       * 
       * "/chip_seq/");
       * 
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_H3K4me3_RG017", "RG017", chipType, cellType, treatment,
       * organism, genome, person, date,
       * 
       * "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K27Ac");
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_H3K27Ac_RG013", "RG013", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/");
       * 
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_H3K27Ac_RG019", "RG019", chipType, cellType, treatment,
       * organism, genome, person, date, "/chip_seq/");
       * 
       * treatment = createTreatment(connection, "H3K27me3");
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_H3K27me3-a_RG020", "RG020", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/");
       * 
       * createChipSeqSample(connection, experiment,
       * "MO1043_ICN1HA_H3K27me3-b_RG021", "RG021", chipType, cellType,
       * treatment, organism, genome, person, date, "/chip_seq/");
       * 
       * // // Original //
       * 
       * 
       * date = "2014-10-09";
       * 
       * experiment = Experiments.getExperiment(connection,
       * Experiments.createExperiment(connection, "Basso_2014-10-09", "CB",
       * "CB"));
       * 
       * 
       * person = Persons.createPerson(connection, "Katia", "Basso",
       * "Columbia University", "212-851-5270",
       * "1130 St. Nicholas Ave., NY NY 10032", "kb451@columbia.edu");
       * 
       * cellType = createCellType(connection, "CB4"); chipType =
       * createChipType(connection, "Transcription Factor");
       * 
       */

      // treatment = createTreatment(connection, "BCL6");
      // createChipSeqSample(connection, experiment,
      // "CB4_BCL6_RK040",
      // "RK040",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "CREBBP");
      // createChipSeqSample(connection,
      // experiment,
      // "CB4_CREBBP_RK041",
      // "RK041",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "EP300");
      // createChipSeqSample(connection, experiment,
      // "CB4_EP300_RK042",
      // "RK042",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "EZH2");
      // createChipSeqSample(connection, experiment,
      // "CB4_EZH2_RK058",
      // "RK058",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "FOXO1");
      // createChipSeqSample(connection, experiment,
      // "CB4_FOXO1_RK059",
      // "RK059",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "MEF2B");
      // createChipSeqSample(connection, experiment,
      // "CB4_MEF2B_RK060",
      // "RK060",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "MLL2");
      // createChipSeqSample(connection, experiment,
      // "CB4_MLL2_RK046",
      // "RK046",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // cellType = createCellType(connection, "CB5");
      // treatment = createTreatment(connection, "BCL6");
      // createChipSeqSample(connection, experiment,
      // "CB5_BCL6_RK050",
      // "RK050",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "CREBBP");
      // createChipSeqSample(connection, experiment,
      // "CB5_CREBBP_RK053",
      // "RK053",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "EP300");
      // createChipSeqSample(connection, experiment,
      // "CB5_EP300_RK054",
      // "RK054",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // createChipSeqSample(connection, experiment,
      // "CB5_EP300_RK061",
      // "RK061",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "EZH2");
      // createChipSeqSample(connection, experiment,
      // "CB5_EZH2_RK062",
      // "RK062",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "FOXO1");
      // createChipSeqSample(connection, experiment,
      // "CB5_FOXO1_RK051",
      // "RK051",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");
      //
      // treatment = createTreatment(connection, "MEF2B");
      // createChipSeqSample(connection, experiment,
      // "CB5_MEF2B_RK044",
      // "RK044",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "MLL2");
      // createChipSeqSample(connection, experiment,
      // "CB5_MLL2_RK052",
      // "RK052",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Contains duplicate reads.");

      // chipType = createChipType(connection, "Input");
      //
      // cellType = createCellType(connection, "CB4");
      //
      // treatment = createTreatment(connection, "Input");
      // createChipSeqSample(connection, experiment,
      // "CB4_Input_RK063",
      // "RK063",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Inputs/",
      // "Contains duplicate reads.");

      // cellType = createCellType(connection, "CB5");
      //
      // createChipSeqSample(connection, experiment,
      // "CB5_Input_RK045",
      // "RK045",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Inputs/",
      // "Contains duplicate reads.");
      //
      // chipType = createChipType(connection, "Histone");
      //
      // cellType = createCellType(connection, "CB4");
      //
      // treatment = createTreatment(connection, "H3K4me1");
      // createChipSeqSample(connection, experiment,
      // "CB4_H3K4me1_RK049",
      // "RK049",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "H3K4me3");
      // createChipSeqSample(connection, experiment,
      // "CB4_H3K4me3_RK048",
      // "RK048",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Contains duplicate reads.");
      //
      // treatment = createTreatment(connection, "H3K27Ac");
      // createChipSeqSample(connection, experiment,
      // "CB4_H3K27Ac_RK043",
      // "RK043",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "H3K27me3");
      // createChipSeqSample(connection, experiment,
      // "CB4_H3K27me3_RK047",
      // "RK047",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Contains duplicate reads.");
      //
      // cellType = createCellType(connection, "CB5");
      //
      // treatment = createTreatment(connection, "H3K4me1");
      // createChipSeqSample(connection, experiment,
      // "CB5_H3K4me1_RK057",
      // "RK057",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Contains duplicate reads.");

      // treatment = createTreatment(connection, "H3K4me3");
      // createChipSeqSample(connection, experiment,
      // "CB5_H3K4me3_RK056",
      // "RK056",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Contains duplicate reads.");
      //
      // treatment = createTreatment(connection, "H3K27Ac");
      // createChipSeqSample(connection, experiment,
      // "CB5_H3K27Ac_RK055",
      // "RK055",
      // chipType,
      // cellType,
      // treatment,
      // organism,
      // genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Contains duplicate reads.");

      // Giulia

      // date = "2015-02-12";
      //
      // person = Persons.createPerson(connection,
      // "Giulia",
      // "Fabbri",
      // "Columbia University",
      // "212-851-5270",
      // "1130 St. Nicholas Ave., NY NY 10032",
      // "fabbri@icg.cpmc.columbia.edu");
      //
      // chipType = createChipType(connection, "Transcription Factor");
      // cellType = createCellType(connection, "MO1043");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_HA_RG010",
      // "RG010",
      // chipType,
      // cellType,
      // organism,
      // genome,
      // person,
      // date,
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_HA_RG016",
      // "RG016",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_Tc_RG009",
      // "RG009",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_Tc_RG015",
      // "RG015",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Transcription_Factors/",
      // "Reads have not been pooled.");
      //
      // chipType = createChipType(connection, "Input");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_Input_RG014",
      // "RG014",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Inputs/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_Input_RG022",
      // "RG022",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Inputs/",
      // "Reads have not been pooled.");
      //
      // chipType = createChipType(connection, "Histone");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_H3K4me1_RG012",
      // "RG012",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_H3K4me1_RG018",
      // "RG018",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_H3K4me3_RG011",
      // "RG011",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_H3K4me3_RG017",
      // "RG017",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_H3K27Ac_RG013",
      // "RG013",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_H3K27Ac_RG019",
      // "RG019",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_H3K27me3-a_RG020",
      // "RG020",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Reads have not been pooled.");
      //
      // createChipSeqSample(connection, experiment,
      // "MO1043_ICN1HA_H3K27me3-b_RG021",
      // "RG021",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // date,
      //
      // "/chip_seq/ChIP_seq/Histones/",
      // "Reads have not been pooled.");

      //
      // Staudt
      //

      // person = Persons.createPerson(connection, "Louis", "Staudt",
      // "Metabolism Branch Center for Cancer Research", "National Cancer
      // Institute, NIH, Bethesda, Maryland 20892, USA", TextUtils.NA);
      //
      // chipType = createChipType(connection, "Transcription Factor");
      //
      // cellType = createCellType(connection, "BL41");
      //
      // createChipSeqSample(connection, experiment,
      // "Staudt_BL41_anti_TCF3_ST001",
      // "ST001",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // "2015-02-20",
      //
      // "/chip_seq/ChIP_seq/Controls/");
      //
      // cellType = createCellType(connection, "Namalwa");
      //
      // createChipSeqSample(connection, experiment,
      // "Staudt_Namalwa_anti_TCF3_ST002",
      // "ST002",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // "2015-02-20",
      //
      // "/chip_seq/ChIP_seq/Controls/");
      //
      // chipType = createChipType(connection, "Control");
      //
      // cellType = createCellType(connection, "BL41");
      //
      // createChipSeqSample(connection, experiment,
      // "Staudt_BL41_Biotag_Control_ST003",
      // "ST003",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // "2015-02-20",
      //
      // "/chip_seq/ChIP_seq/Controls/");
      //
      // cellType = createCellType(connection, "Namalwa");
      //
      // createChipSeqSample(connection, experiment,
      // "Staudt_Namalwa_Biotag_Control_ST004",
      // "ST004",
      // chipType,
      // cellType,
      // organism,genome,
      // person,
      // "2015-02-20",
      //
      // "/chip_seq/ChIP_seq/Controls/");
    } finally {
      connection.close();
    }
  }

  /*
   * private static void addPeaksFromJson(Connection connection, String name,
   * String s1, String s2, String genome, int readLength, String peakCaller,
   * String peakCallerParameters, java.nio.file.Path file) throws SQLException,
   * IOException { PreparedStatement statement;
   * 
   * int id = -1;
   * 
   * statement = connection.
   * prepareStatement("SELECT chip_seq_peaks.id FROM chip_seq_peaks WHERE chip_seq_peaks.name = ? AND chip_seq_peaks.genome = ? AND chip_seq_peaks.peak_caller = ? AND chip_seq_peaks.peak_caller_parameters = ?"
   * );
   * 
   * try { statement.setString(1, name); statement.setString(2, genome);
   * statement.setString(3, peakCaller); statement.setString(4,
   * peakCallerParameters);
   * 
   * id = JDBCConnection.getInt(statement); } finally { statement.close(); }
   * 
   * if (id != -1) {
   * 
   * statement = connection.
   * prepareStatement("DELETE FROM chip_seq_samples_peaks WHERE chip_seq_samples_peaks.peak_id = ?"
   * );
   * 
   * try { statement.setInt(1, id);
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * statement = connection.
   * prepareStatement("DELETE FROM chip_seq_peaks WHERE chip_seq_peaks.id = ?");
   * 
   * try { statement.setInt(1, id);
   * 
   * statement.execute(); } finally { statement.close(); } }
   * 
   * statement = connection.
   * prepareStatement("INSERT INTO chip_seq_peaks (name, genome, read_length, peak_caller, peak_caller_parameters, json_file) VALUES (?, ?, ?, ?, ?, ?)"
   * );
   * 
   * try { statement.setString(1, name); statement.setString(2, genome);
   * statement.setInt(3, readLength); statement.setString(4, peakCaller);
   * statement.setString(5, peakCallerParameters); statement.setString(6,
   * "peaks/" + PathUtils.getName(file));
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * id = -1;
   * 
   * statement = connection.
   * prepareStatement("SELECT chip_seq_peaks.id FROM chip_seq_peaks WHERE chip_seq_peaks.name = ? AND chip_seq_peaks.genome = ? AND chip_seq_peaks.peak_caller = ? AND chip_seq_peaks.peak_caller_parameters = ?"
   * );
   * 
   * try { statement.setString(1, name); statement.setString(2, genome);
   * statement.setString(3, peakCaller); statement.setString(4,
   * peakCallerParameters);
   * 
   * id = JDBCConnection.getInt(statement); } finally { statement.close(); }
   * 
   * // // Associate the peaks with samples //
   * 
   * int sid1 = -1;
   * 
   * statement = connection.
   * prepareStatement("SELECT samples.id FROM samples WHERE samples.name LIKE ? LIMIT 1"
   * );
   * 
   * 
   * 
   * try { statement.setString(1, "%" + s1 + "%");
   * 
   * System.err.println(statement);
   * 
   * sid1 = JDBCConnection.getInt(statement); } finally { statement.close(); }
   * 
   * int sid2 = -1;
   * 
   * statement = connection.
   * prepareStatement("SELECT samples.id FROM samples WHERE samples.name LIKE ? LIMIT 1"
   * );
   * 
   * try { statement.setString(1, "%" + s2 + "%");
   * 
   * sid2 = JDBCConnection.getInt(statement); } finally { statement.close(); }
   * 
   * statement = connection.
   * prepareStatement("INSERT INTO chip_seq_samples_peaks (sample_id, peak_id) VALUES (?, ?)"
   * );
   * 
   * try { statement.setInt(1, sid1); statement.setInt(2, id);
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * statement = connection.
   * prepareStatement("INSERT INTO chip_seq_samples_peaks (sample_id, peak_id) VALUES (?, ?)"
   * );
   * 
   * try { statement.setInt(1, sid2); statement.setInt(2, id);
   * 
   * statement.execute(); } finally { statement.close(); } }
   */

  /*
   * private static void addPeaksFromBedGraph(Connection connection, String
   * name, String s1, String s2, String genome, String peakCaller, String
   * peakCallerParameters, java.nio.file.Path file) throws SQLException,
   * IOException, ParseException { PreparedStatement statement;
   * 
   * int id = -1;
   * 
   * statement = connection.
   * prepareStatement("SELECT peaks.id FROM peaks WHERE peaks.name = ? AND peaks.genome = ? AND peaks.peak_caller = ? AND peaks.peak_caller_parameters = ?"
   * );
   * 
   * try { statement.setString(1, name); statement.setString(2, genome);
   * statement.setString(3, peakCaller); statement.setString(4,
   * peakCallerParameters);
   * 
   * id = JDBCConnection.getInt(statement); } finally { statement.close(); }
   * 
   * if (id != -1) {
   * 
   * 
   * statement = connection.
   * prepareStatement("DELETE FROM peak_locations WHERE peak_locations.peak_id = ?"
   * );
   * 
   * try { statement.setInt(1, id);
   * 
   * System.err.println(statement);
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * statement = connection.
   * prepareStatement("DELETE FROM samples_peaks WHERE samples_peaks.peak_id = ?"
   * );
   * 
   * try { statement.setInt(1, id);
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * statement =
   * connection.prepareStatement("DELETE FROM peaks WHERE peaks.id = ?");
   * 
   * try { statement.setInt(1, id);
   * 
   * statement.execute(); } finally { statement.close(); } }
   * 
   * statement = connection.
   * prepareStatement("INSERT INTO peaks (name, genome, peak_caller, peak_caller_parameters) VALUES (?, ?, ?, ?)"
   * );
   * 
   * try { statement.setString(1, name); statement.setString(2, genome);
   * statement.setString(3, peakCaller); statement.setString(4,
   * peakCallerParameters);
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * id = -1;
   * 
   * statement = connection.
   * prepareStatement("SELECT peaks.id FROM peaks WHERE peaks.name = ? AND peaks.genome = ? AND peaks.peak_caller = ? AND peaks.peak_caller_parameters = ?"
   * );
   * 
   * try { statement.setString(1, name); statement.setString(2, genome);
   * statement.setString(3, peakCaller); statement.setString(4,
   * peakCallerParameters);
   * 
   * id = JDBCConnection.getInt(statement); } finally { statement.close(); }
   * 
   * // // Associate the peaks with samples //
   * 
   * int sid1 = -1;
   * 
   * statement = connection.
   * prepareStatement("SELECT samples.id FROM samples WHERE samples.name LIKE ? LIMIT 1"
   * );
   * 
   * 
   * 
   * try { statement.setString(1, "%" + s1 + "%");
   * 
   * System.err.println(statement);
   * 
   * sid1 = JDBCConnection.getInt(statement); } finally { statement.close(); }
   * 
   * int sid2 = -1;
   * 
   * statement = connection.
   * prepareStatement("SELECT samples.id FROM samples WHERE samples.name LIKE ? LIMIT 1"
   * );
   * 
   * try { statement.setString(1, "%" + s2 + "%");
   * 
   * sid2 = JDBCConnection.getInt(statement); } finally { statement.close(); }
   * 
   * statement = connection.
   * prepareStatement("INSERT INTO samples_peaks (sample_id, peak_id) VALUES (?, ?)"
   * );
   * 
   * try { statement.setInt(1, sid1); statement.setInt(2, id);
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * statement = connection.
   * prepareStatement("INSERT INTO samples_peaks (sample_id, peak_id) VALUES (?, ?)"
   * );
   * 
   * try { statement.setInt(1, sid2); statement.setInt(2, id);
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * // // Create the peak list //
   * 
   * BufferedReader reader = FileUtils.newBufferedReader(file);
   * 
   * String line; List<String> tokens;
   * 
   * try { reader.readLine();
   * 
   * while ((line = reader.readLine()) != null) { tokens =
   * TextUtils.tabSplit(line);
   * 
   * String chr = tokens.get(0); int start = TextUtils.parseInt(tokens.get(1)) +
   * 1; int end = TextUtils.parseInt(tokens.get(2)); double value =
   * TextUtils.parseDouble(tokens.get(3));
   * 
   * statement = connection.
   * prepareStatement("INSERT INTO peak_locations (peak_id, chr, peak_start, peak_end, value) VALUES (?, ?, ?, ?, ?)"
   * );
   * 
   * try { statement.setInt(1, id); statement.setString(2, chr);
   * statement.setInt(3, start); statement.setInt(4, end);
   * statement.setDouble(5, value);
   * 
   * statement.execute(); } finally { statement.close(); } } } finally {
   * reader.close(); } }
   */

  public static Type createChipType(Connection connection, String name)
      throws SQLException {
    return new Type(-1, name); // Types.createType(connection, "chip_seq_types",
                               // name);
  }

  public static Type createCellType(Connection connection, String name)
      throws SQLException {
    return new Type(-1, name); // Types.createType(connection, "cell_types",
                               // name);
  }

  public static Type createTreatment(Connection connection, String name)
      throws SQLException {
    return new Type(-1, name); // Types.createType(connection, "treatments",
                               // name);
  }

  public static int createChipSeqSample(Connection connection,
      Experiment experiment,
      String name,
      String seqId,
      Type chipSeqType,
      Type cellType,
      Type treatment,
      Species organism,
      Type genome,
      Person person,
      String releaseDate) throws SQLException, IOException, ParseException {

    Type expressionType = Samples.createExpressionType(connection, "ChIP-Seq");

    Type experimentField = Tags.createTag(connection,
        Path.createRootPath("Experiment", "Name"));

    Type role = Persons.createRole(connection, "Investigator");

    int sampleId = Samples.createSample(connection,
        experiment.getId(),
        expressionType.getId(),
        name,
        organism.getId(),
        releaseDate);

    Type field = Tags.createTag(connection,
        Path.createRootPath("Sample", "Name"));

    // Index the sample name
    addSampleSearchTerms(connection, sampleId, field, TextUtils.keywords(name));

    Samples.createAlias(connection, sampleId, name);

    Samples.createSamplePerson(connection, sampleId, person.getId(), role);

    
    field = Tags.createTag(connection,
        Path.createRootPath("Sample", "Organism"));

    // Index the sample name
    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(organism.getScientificName()));

    field = Tags.createTag(connection,
        Path.createRootPath("Sample", "Expression_Type"));

    // Index the sample name
    addSampleSearchTerm(connection, sampleId, field, "ChIP-Seq");

    field = Tags.createTag(connection, Path.createRootPath("Sample", "Person"));

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
    Path path = Path.createRootPath("ChIP-Seq", "Sample", "Seq_Id");

    field = Tags.createTag(connection, path);

    Samples.createSampleTag(connection, sampleId, field, seqId);

    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(seqId));

    // cell type

    path = Path.createRootPath("ChIP-Seq", "Sample", "Classification");

    field = Tags.createTag(connection, path);

    Samples.createSampleTag(connection, sampleId, field, chipSeqType.getName());

    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(chipSeqType.getName()));

    // cell type

    path = Path.createRootPath("ChIP-Seq", "Sample", "Cell_Type");

    field = Tags.createTag(connection, path);

    Samples.createSampleTag(connection, sampleId, field, cellType.getName());

    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(cellType.getName()));

    // treatment type

    path = Path.createRootPath("ChIP-Seq", "Sample", "Treatment");

    field = Tags.createTag(connection, path);

    Samples.createSampleTag(connection, sampleId, field, treatment.getName());

    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(treatment.getName()));

    path = Path.createRootPath("ChIP-Seq", "Sample", "Genome");

    field = Tags.createTag(connection, path);

    Samples.createSampleTag(connection, sampleId, field, genome.getName());

    addSampleSearchTerms(connection,
        sampleId,
        field,
        TextUtils.keywords(genome.getName()));

    return sampleId;
  }

  public static void addSampleSearchTerms(Connection connection,
      int sampleId,
      Type field,
      Set<String> keywords) throws SQLException, ParseException {
    Microarray.addSampleSearchTerms(connection, sampleId, field, keywords);

    // Default add it to all

    Type allField = Tags.createTag(connection,
        Path.createRootPath("ChIP-Seq", "All"));

    Microarray.addSampleSearchTerms(connection, sampleId, allField, keywords);
  }

  public static void addSampleSearchTerm(Connection connection,
      int sampleId,
      Type field,
      String keyword) throws SQLException, ParseException {
    Microarray.addSampleSearchTerm(connection, sampleId, field, keyword);

    // Default add it to all

    Type allField = Tags.createTag(connection,
        Path.createRootPath("ChIP-Seq", "All"));

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
        Path.createRootPath("Sample", "GEO", "Series", "Accession"));

    addSampleSearchTerm(connection, sampleId, field, sa);

    field = Tags.createTag(connection,
        Path.createRootPath("Sample", "GEO", "Accession"));

    addSampleSearchTerm(connection, sampleId, field, a);

    field = Tags.createTag(connection,
        Path.createRootPath("Sample", "GEO", "Platform"));

    addSampleSearchTerm(connection, sampleId, field, p);

    if (sa == null || sa.toLowerCase().equals("n/a")) {
      return;
    }

    Samples.createGEO(connection, sampleId, sa, a, p);

    int geoPlatformId = GEO.createPlatform(connection, p);

    int geoSeriesId = GEO.createSeries(connection, sa);

    GEO.createSample(connection, geoSeriesId, geoPlatformId, sampleId, sa);
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

        String dataDir = TextUtils.cat(
            tokens.get(TextUtils.findFirst(header, "Raw Data Directory")),
            name);

        Type cellType = createCellType(connection,
            tokens.get(TextUtils.findFirst(header, "Cell Type")));

        Type chipType = createChipType(connection,
            tokens.get(TextUtils.findFirst(header, "ChIP Type")));

        Type treatment = createTreatment(connection,
            tokens.get(TextUtils.findFirst(header, "Treatment")));

        int sampleId = createChipSeqSample(connection,
            experiment,
            name,
            seqId,
            chipType,
            cellType,
            treatment,
            organism,
            genome,
            person,
            date);

        int sampleDirVfsId = VFS
            .createVfsDir(connection, samplesVfsRootId, name);

        VFS.createSampleFileLink(connection, sampleId, sampleDirVfsId);

        // VFS.updatePath(connection,
        // sampleDirVfsId,
        // TextUtils.cat("/chip_seq/", genome.getName(), "/", lab, "/", name));

        VFS.updatePath(connection, sampleDirVfsId, dataDir);

        createPeakInfo(connection, sampleId, genome, header, tokens);

        createGEO(connection, sampleId, header, tokens);

        // Add additional files

        // for (int i : TextUtils.findByRegex(header, "File")) {
        // Files.createSampleFile2(connection, sampleId, tokens.get(i),
        // Io.getFileExtension(tokens.get(i)));
        // }
      }
    } finally {
      reader.close();
    }

    //ExperimentPermissions.main(null);
    //SamplePermissions.main(null);
  }

  private static void createPeakInfo(Connection connection,
      int sampleId,
      Type genome,
      List<String> header,
      List<String> tokens) throws SQLException, ParseException {
    // TODO Auto-generated method stub

    // Genome
    // Read Length
    // Reads Mapped Reads
    // Duplicate Reads
    // % Duplicate Reads
    // Unique Reads
    // % Unique Reads

    createIntPeakInfo(connection,
        sampleId,
        header,
        tokens,
        "Read Length",
        "Read_Length");

    createIntPeakInfo(connection, sampleId, header, tokens, "Reads", "Reads");

    createIntPeakInfo(connection,
        sampleId,
        header,
        tokens,
        "Mapped Reads",
        "Mapped_Reads");

    createIntPeakInfo(connection,
        sampleId,
        header,
        tokens,
        "Duplicate Reads",
        "Duplicate_Reads");

    createFloatPeakInfo(connection,
        sampleId,
        header,
        tokens,
        "% Duplicate Reads",
        "Percent_Duplicate_Reads");

    createIntPeakInfo(connection,
        sampleId,
        header,
        tokens,
        "Unique Reads",
        "Unique_Reads");

    createFloatPeakInfo(connection,
        sampleId,
        header,
        tokens,
        "% Unique Reads",
        "Percent_Unique_Reads");

    createPeakInfo(connection,
        sampleId,
        header,
        tokens,
        "Peak Caller",
        "Peak_Caller");

    createPeakInfo(connection,
        sampleId,
        header,
        tokens,
        "Peak Caller Parameters",
        "Peak_Caller_Parameters");
  }

  private static void createPeakInfo(Connection connection,
      int sampleId,
      List<String> header,
      List<String> tokens,
      String name,
      String pathName) throws SQLException, ParseException {
    System.err.println("peak path " + name + " " + pathName + " " + header);

    Path path = Path.createRootPath("ChIP-Seq", "Sample", pathName);

    Type field = Tags.createTag(connection, path);

    Samples.createSampleTag(connection,
        sampleId,
        field,
        tokens.get(TextUtils.findFirst(header, name)));
  }

  private static void createIntPeakInfo(Connection connection,
      int sampleId,
      List<String> header,
      List<String> tokens,
      String name,
      String pathName) throws SQLException {
    Path path = Path.createRootPath("ChIP-Seq", "Sample", pathName);

    Type field = Tags.createTag(connection, path);

    Samples.createSampleTag(connection,
        sampleId,
        field,
        Integer.parseInt(tokens.get(TextUtils.findFirst(header, name))));
  }

  private static void createFloatPeakInfo(Connection connection,
      int sampleId,
      List<String> header,
      List<String> tokens,
      String name,
      String pathName) throws SQLException {
    Path path = Path.createRootPath("ChIP-Seq", "Sample", pathName);

    Type field = Tags.createTag(connection, path);

    Samples.createSampleTag(connection,
        sampleId,
        field,
        Double.parseDouble(tokens.get(TextUtils.findFirst(header, name))));
  }

  public static int createSampleFile(Connection connection,
      java.nio.file.Path dataDirectory,
      int experimentId,
      int sampleId,
      String fileName) throws SQLException, IOException, ParseException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT sample_files.id FROM sample_files WHERE sample_files.sample_id = ? AND sample_files.name = ?");

    DatabaseResultsTable table;

    try {
      statement.setInt(1, sampleId);
      statement.setString(2, fileName);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return table.getInt(0, 0);
    }

    // java.nio.file.Path does not exist so must create it

    statement = connection.prepareStatement(
        "SELECT experiments.public_id FROM experiments WHERE experiments.id = ?");

    try {
      statement.setInt(1, experimentId);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    String publicId = table.getString(0, 0);

    // local files
    int locationId = 1;

    // java.nio.file.Path path = Io.createFile(dataDirectory,
    // Io.concatenatePath(publicId, fileName));

    int type = Files.createFileType(connection, fileName);

    statement = connection.prepareStatement(
        "INSERT INTO sample_files (sample_id, name, path, file_type_id, file_location_id) VALUES (?, ?, ?, ?, ?)");

    try {
      statement.setInt(1, sampleId);
      statement.setString(2, fileName);
      statement.setString(3, PathUtils.toString(dataDirectory));
      statement.setInt(4, type);
      statement.setInt(5, locationId);

      statement.execute();
    } finally {
      statement.close();
    }

    return createSampleFile(connection,
        dataDirectory,
        experimentId,
        sampleId,
        fileName);
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
}
