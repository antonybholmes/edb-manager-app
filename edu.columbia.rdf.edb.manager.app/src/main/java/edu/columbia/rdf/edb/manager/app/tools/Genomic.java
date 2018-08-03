package edu.columbia.rdf.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

public class Genomic {
  public static void main(String[] args) throws SQLException {
    try {
      Connection connection = DatabaseService.getConnection();

      try {
        createGenome(connection, "hg19");
        createChromosome(connection, "chr1");
        createGene(connection, "MEF2B");
      } finally {
        connection.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Type createGenome(Connection connection, String name)
      throws SQLException {
    return Types.createType(connection, "genomes", name);
    
    /*
    String sql = "SELECT genome.id, genome.name FROM genomes WHERE genome.name = ?";

    PreparedStatement statement = connection.prepareStatement(sql);

    DatabaseResultsTable table;

    try {
      statement.setString(1, name);
      statement.setString(2, db);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return new Type(table.getInt(0, 0), table.getString(0, 1));
    }

    sql = "INSERT INTO genomes (name, db) VALUES (?, ?)";

    // System.err.println(sql);

    statement = connection.prepareStatement(sql);

    try {
      statement.setString(1, name);
      statement.setString(2, db);
      statement.execute();
    } finally {
      statement.close();
    }

    return createGenome(connection, name, db);
    */
  }

  public static Type createChromosome(Connection connection, String name)
      throws SQLException {
    return Types.createType(connection, "chromosomes", name.toLowerCase());
  }

  public static Type createGene(Connection connection, String name)
      throws SQLException {

    return Types.createType(connection, "genes", name.toUpperCase());
  }

  public static int createRNASeq(Connection connection,
      int sampleId,
      int geneId,
      int chromosomeId,
      int start,
      int end,
      String locus,
      int genomeId,
      double fpkm) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT rna_seq.id FROM rna_seq WHERE rna_seq.sample_id = ? AND rna_seq.gene_id = ? AND rna_seq.locus = ?");

    DatabaseResultsTable table;

    try {
      statement.setInt(1, sampleId);
      statement.setInt(2, geneId);
      statement.setString(3, locus);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return table.getInt(0, 0);
    }

    statement = connection.prepareStatement(
        "INSERT INTO rna_seq (sample_id, gene_id, chromosome_id, start_pos, end_pos, locus, genome_id, fpkm) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

    try {
      statement.setInt(1, sampleId);
      statement.setInt(2, geneId);
      statement.setInt(3, chromosomeId);
      statement.setInt(4, start);
      statement.setInt(5, end);
      statement.setString(6, locus);
      statement.setInt(7, genomeId);
      statement.setDouble(8, fpkm);
      statement.execute();
    } finally {
      statement.close();
    }

    return createRNASeq(connection,
        sampleId,
        geneId,
        chromosomeId,
        start,
        end,
        locus,
        genomeId,
        fpkm);
  }
}
