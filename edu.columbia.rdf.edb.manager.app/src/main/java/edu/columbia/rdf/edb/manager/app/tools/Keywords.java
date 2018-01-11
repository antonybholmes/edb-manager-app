package edu.columbia.rdf.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import org.jebtk.core.text.TextUtils;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

public class Keywords {
  public static void keywordIndexExperiment(Connection connection,
      int experimentId,
      int fieldId,
      String text) throws SQLException {
    Set<String> keywords = TextUtils.keywords(text);

    System.err.println(text);

    for (String keyword : keywords) {
      int keywordId = createKeyword(connection, keyword);

      System.err.println(keyword + " " + keywordId);

      PreparedStatement statement = connection.prepareStatement(
          "SELECT keywords_experiments.id FROM keywords_experiments WHERE keywords_experiments.keyword_id = ? AND keywords_experiments.experiment_id = ? AND keywords_experiments.field_id = ?");

      DatabaseResultsTable table;

      try {
        statement.setInt(1, keywordId);
        statement.setInt(2, experimentId);
        statement.setInt(3, fieldId);

        table = JDBCConnection.getTable(statement);
      } finally {
        statement.close();
      }

      if (table.getRowCount() == 1) {
        continue;
      }

      statement = connection.prepareStatement(
          "INSERT INTO keywords_experiments (keyword_id, experiment_id, field_id) VALUES (?, ?, ?)");

      try {
        statement.setInt(1, keywordId);
        statement.setInt(2, experimentId);
        statement.setInt(3, fieldId);

        statement.execute();
      } finally {
        statement.close();
      }
    }
  }

  public static void keywordIndexSample(Connection connection,
      int experimentId,
      int sampleId,
      int fieldId,
      String text) throws SQLException {
    Set<String> keywords = TextUtils.keywords(text);

    System.err.println(text);

    for (String keyword : keywords) {
      int keywordId = createKeyword(connection, keyword);

      System.err.println(keyword + " " + keywordId);

      PreparedStatement statement = connection.prepareStatement(
          "SELECT keywords_samples.id FROM keywords_samples WHERE keywords_samples.keyword_id = ? AND keywords_samples.sample_id = ? AND keywords_samples.field_id = ?");

      DatabaseResultsTable table;

      try {
        statement.setInt(1, keywordId);
        statement.setInt(2, sampleId);
        statement.setInt(3, fieldId);

        table = JDBCConnection.getTable(statement);
      } finally {
        statement.close();
      }

      if (table.getRowCount() == 1) {
        continue;
      }

      statement = connection.prepareStatement(
          "INSERT INTO keywords_samples (keyword_id, experiment_id, sample_id, field_id) VALUES (?, ?, ?, ?)");

      try {
        statement.setInt(1, keywordId);
        statement.setInt(2, experimentId);
        statement.setInt(3, sampleId);
        statement.setInt(4, fieldId);

        statement.execute();
      } finally {
        statement.close();
      }
    }
  }

  public static int createKeyword(Connection connection, String keyword)
      throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT keywords.id FROM keywords WHERE keywords.name = ?");

    DatabaseResultsTable table;

    try {
      statement.setString(1, keyword.toLowerCase());

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return table.getInt(0, 0);
    }

    statement = connection
        .prepareStatement("INSERT INTO keywords (name) VALUES (?)");

    try {
      statement.setString(1, keyword.toLowerCase());

      statement.execute();
    } finally {
      statement.close();
    }

    return createKeyword(connection, keyword);
  }

  /*
   * public static int createField(Connection connection, String field) throws
   * SQLException { PreparedStatement statement = connection.
   * prepareStatement("SELECT fields.id FROM fields WHERE fields.name = ?");
   * 
   * DatabaseResultsTable table;
   * 
   * try { statement.setString(1, field.toLowerCase());
   * 
   * table = JDBCConnection.getTable(statement); } finally { statement.close();
   * }
   * 
   * if (table.getRowCount() == 1) { return table.getInt(0, 0); }
   * 
   * statement =
   * connection.prepareStatement("INSERT INTO fields (name) VALUES (?)");
   * 
   * try { statement.setString(1, field.toLowerCase());
   * 
   * statement.execute(); } finally { statement.close(); }
   * 
   * return createField(connection, field); }
   */
}
