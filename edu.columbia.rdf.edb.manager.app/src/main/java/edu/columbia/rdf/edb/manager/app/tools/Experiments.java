package edu.columbia.rdf.edb.manager.app.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.sys.SysUtils;
import org.jebtk.core.text.TextUtils;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

import edu.columbia.rdf.edb.Experiment;
import edu.columbia.rdf.edb.Person;
import edu.columbia.rdf.edb.Species;

public class Experiments {
  public static int[] createExperimentFromIdf(Connection connection,
      java.nio.file.Path idfFile) throws SQLException, IOException {
    BufferedReader reader = FileUtils.newBufferedReader(idfFile);

    String line;

    String name = null;
    String description = null;
    String address = null;
    String affiliation = null;
    String firstName = null;
    String lastName = null;
    String roleName = null;
    String phone = null;
    String email = null;
    String publicName = null;

    try {
      while ((line = reader.readLine()) != null) {
        List<String> tokens = TextUtils.tabSplit(line);

        if (tokens.get(0).equalsIgnoreCase("experiment description")) {
          description = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("investigation title")) {
          name = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("public id")) {
          publicName = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person address")) {
          address = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person affiliation")) {
          affiliation = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person first name")) {
          firstName = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person last name")) {
          lastName = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person roles")) {
          roleName = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person phone")) {
          if (tokens.size() > 1) {
            phone = tokens.get(1);
          } else {
            phone = TextUtils.NA;
          }
        } else if (tokens.get(0).equalsIgnoreCase("person email")) {
          email = tokens.get(1);
        } else {

        }
      }
    } finally {
      reader.close();
    }

    System.err.println("p " + publicName);

    SysUtils.err().println(publicName, name, description);

    // Create the experiment

    Person personId = Persons.createPerson(connection,
        firstName,
        lastName,
        affiliation,
        phone,
        address,
        email);

    int experimentId = Experiments
        .createExperiment(connection, publicName, name, description);

    // addExperimentSearchTerms(connection, experimentId, new
    // Path("all_categories"), TextUtils.keywords(name));

    Type role = Persons.createRole(connection, roleName);

    // int fileId = Files.createExperimentFile(connection, dataDirectory,
    // experimentId, "expression.rma");

    // fileId = Files.createExperimentFile(connection, dataDirectory,
    // experimentId, "expression.mas5");

    System.err.println(experimentId + " " + personId + " " + role + " ");

    // Index some keywords

    // int fieldId = Keywords.createField(connection, "name");

    // Keywords.keywordIndexExperiment(connection, experimentId, fieldId, name);

    // Create a file entry
    int exsVfsRootId = VFS.createVfsExDir(connection);
    int exVfsId = VFS.createVfsDir(connection, exsVfsRootId, publicName);
    VFS.createExperimentFileLink(connection, experimentId, exVfsId);

    int[] ret = { experimentId, exVfsId };

    return ret;
  }

  public static Species createOrganism(Connection connection,
      String name,
      String scientificName) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT organisms.id, organisms.name, organisms.scientific_name FROM organisms WHERE organisms.name = ?");

    DatabaseResultsTable table;

    try {
      statement.setString(1, name);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return new Species(table.getInt(0, 0), table.getString(0, 1),
          table.getString(0, 2));
    }

    statement = connection.prepareStatement(
        "INSERT INTO organisms (name, scientific_name) VALUES (?, ?)");

    try {
      statement.setString(1, name);
      statement.setString(2, scientificName);

      statement.execute();
    } finally {
      statement.close();
    }

    return createOrganism(connection, name, scientificName);
  }

  public static Species getOrganism(Connection connection,
      String scientificName) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT organisms.id, organisms.name, organisms.scientific_name FROM organisms WHERE organisms.scientific_name = ?");

    DatabaseResultsTable table;

    try {
      statement.setString(1, scientificName);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return new Species(table.getInt(0, 0), table.getString(0, 1),
          table.getString(0, 2));
    } else {
      return null;
    }
  }

  public static int createExperiment(Connection connection,
      String publicId,
      String name,
      String description) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT experiments.id FROM experiments WHERE experiments.name = ? OR experiments.public_id = ?");

    DatabaseResultsTable table;

    try {
      statement.setString(1, name);
      statement.setString(2, publicId);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() > 0) {
      return table.getInt(0, 0);
    }

    statement = connection.prepareStatement(
        "INSERT INTO experiments (public_id, name, description) VALUES (?, ?, ?)");

    try {
      statement.setString(1, publicId);
      statement.setString(2, name);
      statement.setString(3, description);

      System.err.println("experiment " + statement);

      statement.execute();
    } finally {
      statement.close();
    }

    return createExperiment(connection, publicId, name, description);
  }

  /**
   * Creates an id for the experiment that is slightly more descriptive than a
   * simple number.
   * 
   * @param personId
   * @return
   * @throws SQLException
   * @throws SQLException
   * @throws ParseException
   */
  public static String createExperimentPublicId(Connection connection,
      int personId) throws SQLException, ParseException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT LOWER(persons.first_name) FROM persons WHERE persons.id = ?");

    DatabaseResultsTable table;

    try {
      statement.setInt(1, personId);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    String name = table.getString(0, 0);

    statement = connection
        .prepareStatement("SELECT MAX(experiments.id) FROM experiments");

    table = null;

    try {
      // statement.setString(1, name + "%");

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    System.err.println("rows " + table.getString(0, 0));

    int count = 1 + (table.getString(0, 0) != null ? table.getInt(0, 0) : 0);

    String publicId = name + "_" + Integer.toString(count);

    System.err.println("public id: " + publicId);

    return publicId;
  }

  public static Experiment getExperiment(Connection connection,
      int experimentId) throws SQLException, ParseException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT experiments.id, experiments.public_id, experiments.name, experiments.description FROM experiments WHERE experiments.id = ?");

    DatabaseResultsTable table;

    Experiment experiment = null;

    try {
      statement.setInt(1, experimentId);

      table = JDBCConnection.getTable(statement);

      if (table.getRowCount() == 1) {
        experiment = new Experiment(table.getInt(0, 0), table.getString(0, 1),
            table.getString(0, 2), table.getString(0, 3), null);
      }
    } finally {
      statement.close();
    }

    return experiment;

  }
}
