package edu.columbia.rdf.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

public class Samples {
  //private static final String JSON_SAMPLE_FIELDS_SQL = "INSERT INTO json_sample_fields (sample_id, json) VALUES ('a1', 'a2')";

  //private static final String JSON_SAMPLE_GEO_SQL = "INSERT INTO json_sample_geo (sample_id, json) VALUES ('a1', 'a2')";

  //private static final String JSON_SAMPLE_PERSONS_SQL = "INSERT INTO json_sample_persons (sample_id, json) VALUES ('a1', 'a2')";

  public static Type createDataType(Connection connection, String name)
      throws SQLException {
    return Types.createType(connection, "data_types", name);
  }

  public static void keywordIndexAttribute(Connection connection,
      int attributeId,
      String value) throws SQLException {
    /*
     * List<String> keywords = Text.splitWords(value);
     * 
     * for (String keyword : keywords) { int keywordId =
     * CreateExperiment.createKeyword(connection, keyword);
     * 
     * PreparedStatement statement = connection.
     * prepareStatement("SELECT keyword_attributes.id FROM keyword_attributes WHERE keyword_attributes.keyword_id = ? AND keyword_attributes.attribute_id = ?"
     * );
     * 
     * ResultTable table;
     * 
     * try { statement.setInt(1, keywordId); statement.setInt(2, attributeId);
     * 
     * table = MySQLJDBCDatabase.create(statement); } finally {
     * statement.close(); }
     * 
     * if (table.getRowCount() == 1) { continue; }
     * 
     * statement = connection.
     * prepareStatement("INSERT INTO keyword_attributes VALUES (DEFAULT, ?, ?)"
     * );
     * 
     * try { statement.setInt(1, keywordId); statement.setInt(2, attributeId);
     * 
     * statement.execute(); } finally { statement.close(); } }
     */
  }

  public static int createSamplePerson(Connection connection, 
      int sampleId,
      int personId, 
      Type role) throws SQLException {
    PreparedStatement statement = connection.
        prepareStatement("SELECT sample_persons.id FROM sample_persons WHERE sample_persons.sample_id = ? AND sample_persons.person_id = ? AND sample_persons.role_id = ?");

    DatabaseResultsTable table;

    try { 
      statement.setInt(1, sampleId); statement.setInt(2, personId);
      statement.setInt(3, role.getId());

      table = JDBCConnection.getTable(statement); 
    } finally { 
      statement.close();
    }

    if (table.getRowCount() == 1) { 
      return table.getInt(0, 0);
    }

    statement = connection.
        prepareStatement("INSERT INTO sample_persons (sample_id, person_id, role_id) VALUES (?, ?, ?)");

    try { 
      statement.setInt(1, sampleId); statement.setInt(2, personId);
      statement.setInt(3, role.getId());

      statement.execute(); 
    } finally { 
      statement.close(); 
    }

    return createSamplePerson(connection, sampleId, personId, role);
  }

  public static int createSampleTag(Connection connection,
      int sampleId,
      Type field,
      String value) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT sample_tags.id FROM sample_tags WHERE sample_tags.sample_id = ? AND sample_tags.tag_id = ?");

    DatabaseResultsTable table;

    try {
      statement.setInt(1, sampleId);
      statement.setInt(2, field.getId());

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return table.getInt(0, 0);
    }

    statement = connection.prepareStatement(
        "INSERT INTO sample_tags (sample_id, tag_id, tag_type_id, str_value) VALUES (?, ?, 1, ?)");

    try {
      statement.setInt(1, sampleId);
      statement.setInt(2, field.getId());
      statement.setString(3, value);

      statement.execute();
    } finally {
      statement.close();
    }

    return createSampleTag(connection, sampleId, field, value);
  }

  /**
   * Create a numerical field.
   * 
   * @param connection
   * @param sampleId
   * @param tag
   * @param value
   * @return
   * @throws SQLException
   * @throws ParseException
   */
  public static int createSampleTag(Connection connection,
      int sampleId,
      Type tag,
      int value) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT sample_tags.id FROM sample_tags WHERE sample_tags.sample_id = ? AND sample_tags.tag_id = ?");

    int table = -1;

    try {
      statement.setInt(1, sampleId);
      statement.setInt(2, tag.getId());

      System.err.println(statement);

      table = JDBCConnection.getInt(statement);
    } finally {
      statement.close();
    }

    if (table != -1) {
      return table;
    }

    statement = connection.prepareStatement(
        "INSERT INTO sample_tags (sample_id, tag_id, tag_type_id, value) VALUES (?, ?, 2, ?)");

    try {
      statement.setInt(1, sampleId);
      statement.setInt(2, tag.getId());
      statement.setInt(3, value);

      System.err.println(statement);

      statement.execute();
    } finally {
      statement.close();
    }

    return createSampleTag(connection, sampleId, tag, value);
  }

  /**
   * Create a numerical field.
   * 
   * @param connection
   * @param sampleId
   * @param field
   * @param value
   * @return
   * @throws SQLException
   * @throws ParseException
   */
  public static int createSampleTag(Connection connection,
      int sampleId,
      Type field,
      double value) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT sample_tags.id FROM sample_tags WHERE sample_tags.sample_id = ? AND sample_tags.tag_id = ?");

    DatabaseResultsTable table;

    try {
      statement.setInt(1, sampleId);
      statement.setInt(2, field.getId());

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return table.getInt(0, 0);
    }

    statement = connection.prepareStatement(
        "INSERT INTO sample_tags (sample_id, tag_id, tag_type_id, value) VALUES (?, ?, 3, ?)");

    try {
      statement.setInt(1, sampleId);
      statement.setInt(2, field.getId());
      statement.setDouble(3, value);

      statement.execute();
    } finally {
      statement.close();
    }

    return createSampleTag(connection, sampleId, field, value);
  }

  public static int createSample(Connection connection,
      int experimentId,
      int expressionTypeId,
      String name,
      int organismId) throws SQLException {
    return createSample(connection,
        experimentId,
        expressionTypeId,
        name,
        organismId,
        new SimpleDateFormat("yyyy-MM-dd")
        .format(Calendar.getInstance().getTime()));
  }

  public static int createSample(Connection connection,
      int experimentId,
      int expressionTypeId,
      String name,
      int organismId,
      String releaseDate) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT samples.id FROM samples WHERE samples.name = ? AND samples.experiment_id = ?");

    List<Integer> ids = new ArrayList<Integer>();

    try {
      statement.setString(1, name);
      statement.setInt(2, experimentId);

      System.err.println(statement);

      ids.addAll(JDBCConnection.getIntList(statement));
    } finally {
      statement.close();
    }

    System.err.println("ids " + ids);

    if (ids.size() > 0) {
      int id = ids.get(0);

      // create alias
      createAlias(connection, id, name);

      return id;
    }

    statement = connection.prepareStatement(
        "INSERT INTO samples (experiment_id, expression_type_id, name, organism_id) VALUES (?, ?, ?, ?)");

    try {
      statement.setInt(1, experimentId);
      statement.setInt(2, expressionTypeId);
      statement.setString(3, name);
      statement.setInt(4, organismId);

      System.err.println(statement);

      statement.execute();
    } finally {
      statement.close();
    }

    return createSample(connection,
        experimentId,
        expressionTypeId,
        name,
        organismId,
        releaseDate);
  }

  /**
   * Each sample needs an alias which we default to its name, Other aliases may
   * be added.
   * 
   * @param connection
   * @param sampleId
   * @param name
   * @return
   * @throws SQLException
   * @throws SQLException
   */
  public static int createAlias(Connection connection,
      int sampleId,
      String name) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT sample_aliases.id FROM sample_aliases WHERE sample_aliases.sample_id = ?");

    List<Integer> ids = new ArrayList<Integer>();

    try {
      statement.setInt(1, sampleId);

      ids.addAll(JDBCConnection.getIntList(statement));
    } finally {
      statement.close();
    }

    if (ids.size() > 0) {
      return ids.get(0);
    }

    // alias the name and the id itself
    statement = connection.prepareStatement(
        "INSERT INTO sample_aliases (sample_id, name) VALUES (?, ?)");

    try {
      statement.setInt(1, sampleId);
      statement.setString(2, name);

      statement.execute();
    } finally {
      statement.close();
    }

    statement = connection.prepareStatement(
        "INSERT INTO sample_aliases (sample_id, name) VALUES (?, ?)");

    try {
      statement.setInt(1, sampleId);
      statement.setString(2, Integer.toString(sampleId));

      statement.execute();
    } finally {
      statement.close();
    }

    return createAlias(connection, sampleId, name);
  }

  public static int createGEO(Connection connection,
      int sampleId,
      String seriesAccession,
      String sampleAccession,
      String platform) throws SQLException, ParseException {
    if (seriesAccession == null || seriesAccession.length() < 4
        || sampleAccession == null || sampleAccession.length() < 4
        || platform == null || platform.length() < 4) {
      return -1;
    }

    PreparedStatement statement = connection.prepareStatement(
        "SELECT samples_geo.id FROM samples_geo WHERE samples_geo.sample_id = ?");

    DatabaseResultsTable table;

    try {
      statement.setInt(1, sampleId);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return table.getInt(0, 0);
    }

    statement = connection.prepareStatement(
        "INSERT INTO samples_geo (sample_id, geo_series_accession, geo_accession, geo_platform) VALUES (?, ?, ?, ?)");

    try {
      statement.setInt(1, sampleId);
      statement.setString(2, seriesAccession);
      statement.setString(3, sampleAccession);
      statement.setString(4, platform);

      statement.execute();
    } finally {
      statement.close();
    }

    return createGEO(connection,
        sampleId,
        seriesAccession,
        sampleAccession,
        platform);
  }

  /*
  public static void sampleJson(Connection connection) throws SQLException {

    PreparedStatement statement = connection
        .prepareStatement("delete from json_sample_fields");

    try {
      statement.execute();
    } finally {
      statement.close();
    }

    List<Integer> sampleIds = edu.columbia.rdf.edb.http.Samples
        .getAllSampleIds(connection);

    for (int sampleId : sampleIds) {

      JsonBuilder tagsJson = JsonBuilder.create().startArray();

      DatabaseServlet.constructTagsJson(connection, sampleId, tagsJson);

      tagsJson.endArray();

      String sql = JSON_SAMPLE_FIELDS_SQL
          .replace("a1", Integer.toString(sampleId))
          .replace("a2", tagsJson.toString());

      SysUtils.err().println("sample", sampleId, sql);

      statement = connection.prepareStatement(sql);

      try {
        // statement.setInt(1, sampleId);
        // statement.setString(2, tagsJson.toString());

        statement.execute();
      } finally {
        statement.close();
      }

      // break;
    }
  }
   */

  /*
  public static void sampleGeoJson(Connection connection) throws SQLException {
    PreparedStatement statement = connection
        .prepareStatement("delete from json_sample_geo");

    try {
      statement.execute();
    } finally {
      statement.close();
    }

    List<Integer> sampleIds = edu.columbia.rdf.edb.http.Samples
        .getAllSampleIds(connection);

    for (int sampleId : sampleIds) {

      JsonObject json = DatabaseServlet.constructGeoJson(connection, sampleId);

      if (json != null) {

        String sql = JSON_SAMPLE_GEO_SQL
            .replace("a1", Integer.toString(sampleId))
            .replace("a2", json.toString());

        SysUtils.err().println("sample", sampleId, sql);

        statement = connection.prepareStatement(sql);

        try {
          // statement.setInt(1, sampleId);
          // statement.setString(2, tagsJson.toString());

          statement.execute();
        } finally {
          statement.close();
        }
      }

      // break;
    }
  }
   */

  /*
  public static void samplePersonsJson(Connection connection)
      throws SQLException {
    PreparedStatement statement = connection
        .prepareStatement("delete from json_sample_persons");

    try {
      statement.execute();
    } finally {
      statement.close();
    }

    List<Integer> sampleIds = edu.columbia.rdf.edb.http.Samples
        .getAllSampleIds(connection);

    for (int sampleId : sampleIds) {

      String json = DatabaseServlet.constructPersonsJson(connection, sampleId);

      if (json != null) {

        String sql = JSON_SAMPLE_PERSONS_SQL
            .replace("a1", Integer.toString(sampleId))
            .replace("a2", json.toString());

        SysUtils.err().println("sample", sampleId, sql);

        statement = connection.prepareStatement(sql);

        try {
          // statement.setInt(1, sampleId);
          // statement.setString(2, tagsJson.toString());

          statement.execute();
        } finally {
          statement.close();
        }
      }

      // break;
    }
  }
   */
}
