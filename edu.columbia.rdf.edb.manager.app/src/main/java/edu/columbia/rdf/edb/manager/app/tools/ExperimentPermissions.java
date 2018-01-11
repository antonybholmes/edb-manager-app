package edu.columbia.rdf.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

import edu.columbia.rdf.edb.Person;

public class ExperimentPermissions {
  public static void main(String[] args) throws SQLException, ParseException {
    Connection connection = DatabaseService.getConnection();

    try {
      List<Person> persons = Persons.getGlobalPersons(connection);

      /*
       * Person personId = Persons.createPerson(connection, "Lab_RDF",
       * "Lab_RDF", "Columbia University", "212-851-5270",
       * "1130 St. Nicholas Ave., NY NY 10032", "abh2138@columbia.edu");
       */

      for (Person person : persons) {
        System.err.println("person " + person.getName());

        createViews(connection, person.getId());
      }

    } finally {
      connection.close();
    }
  }

  public static void createViews(Connection connection, int personId)
      throws SQLException, ParseException {
    PreparedStatement statement = connection
        .prepareStatement("SELECT experiments.id FROM experiments");

    DatabaseResultsTable table;

    try {
      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    statement = connection.prepareStatement(
        "DELETE FROM experiment_permissions WHERE experiment_permissions.person_id = ?");

    try {
      statement.setInt(1, personId);

      statement.execute();
    } finally {
      statement.close();
    }

    statement = connection.prepareStatement(
        "INSERT INTO experiment_permissions (experiment_id, person_id) VALUES (?, ?)");

    try {
      for (int i = 0; i < table.getRowCount(); ++i) {
        statement.setInt(1, table.getInt(i, 0));
        statement.setInt(2, personId);

        statement.execute();
      }
    } finally {
      statement.close();
    }
  }
}
