package edu.columbia.rdf.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.core.path.Path;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

/**
 * Concerning primitive types in the database.
 * 
 * @author Antony Holmes
 *
 */
public class Types {
  public static Type createType(Connection connection, String type, Path path)
      throws SQLException {
    return createType(connection, type, path.toString());
  }

  /**
   * Creates a type in one of the type tables which all have the same structure
   * so the can be addressed with the same code apart from changing the table
   * name.
   * 
   * @param connection
   * @param type
   * @param name
   * @return
   * @throws SQLException
   * @throws SQLException
   * @throws ParseException
   */
  public static Type createType(Connection connection, String type, String name)
      throws SQLException {

    /*
     * DatabaseResultsTable query = new TableQuery(connection) .select("id",
     * "name") .from(type) .where("name") .values(name) .fetch();
     */

    String sql = new StringBuilder("SELECT ").append(type).append(".id, ")
        .append(type).append(".name FROM ").append(type).append(" WHERE ")
        .append(type).append(".name = ?").toString();

    
    
    PreparedStatement statement = connection.prepareStatement(sql);

    DatabaseResultsTable table;

    try {
      statement.setString(1, name);

      System.err.println(statement);
      
      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return new Type(table.getInt(0, 0), table.getString(0, 1));
    }

    sql = new StringBuilder().append("INSERT INTO ").append(type)
        .append(" (name) VALUES (?)").toString();

    statement = connection.prepareStatement(sql);

    try {
      statement.setString(1, name);
      
      System.err.println(statement);

      statement.execute();
    } finally {
      statement.close();
    }

    return createType(connection, type, name);
  }
}
