package edu.columbia.rdf.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.jebtk.database.JDBCConnection;

import edu.columbia.rdf.edb.Person;

public class SamplePermissions {
  private static final String SAMPLES_SQL = "SELECT samples.id FROM samples";

  private static final String EXPERIMENT_SAMPLES_SQL = "SELECT samples.id FROM samples WHERE experiment_id = ?";

  public static void main(String[] args) throws SQLException, ParseException {
    Connection connection = DatabaseService.getConnection();

    try {
      /*
       * Person personId = Persons.createPerson(connection, "Lab_RDF",
       * "Lab_RDF", "Columbia University", "212-851-5270",
       * "1130 St. Nicholas Ave., NY NY 10032", "abh2138@columbia.edu");
       */

      List<Person> persons = Persons.getGlobalPersons(connection);

      for (Person person : persons) {
        createViews(connection, person.getId());
      }
    } finally {
      connection.close();
    }
  }

  public static void createViews(Connection connection, int personId)
      throws SQLException {
    List<Integer> ids = JDBCConnection.getIntList(connection, SAMPLES_SQL);

    PreparedStatement statement = connection.prepareStatement(
        "DELETE FROM sample_permissions WHERE sample_permissions.person_id = ?");

    try {
      statement.setInt(1, personId);

      statement.execute();
    } finally {
      statement.close();
    }

    statement = connection.prepareStatement(
        "INSERT INTO sample_permissions (sample_id, person_id) VALUES (?, ?)");

    try {
      for (int id : ids) {
        statement.setInt(1, id);
        statement.setInt(2, personId);

        statement.execute();
      }
    } finally {
      statement.close();
    }
  }

  public static void createViews(Connection connection,
      int experimentId,
      int personId) throws SQLException {
    PreparedStatement statement = connection
        .prepareStatement(EXPERIMENT_SAMPLES_SQL);

    List<Integer> ids;

    try {
      statement.setInt(1, experimentId);

      ids = JDBCConnection.getIntList(statement);
    } finally {
      statement.close();
    }

    statement = connection.prepareStatement(
        "INSERT INTO sample_permissions (sample_id, person_id) VALUES (?, ?)");

    try {
      for (int id : ids) {
        statement.setInt(1, id);
        statement.setInt(2, personId);

        statement.execute();
      }
    } finally {
      statement.close();
    }
  }
}
