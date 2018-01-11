package edu.columbia.rdf.edb.manager.app.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.core.cryptography.Cryptography;
import org.jebtk.core.cryptography.CryptographyException;
import org.jebtk.core.io.FileUtils;
import org.jebtk.core.text.TextUtils;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

import edu.columbia.rdf.edb.Person;

public class Persons {
  public static final String LOGIN_SQL = "SELECT login_persons.id, login_persons.user_name, login_persons.password_hash_salted, login_persons.salt";

  public static void main(String[] args)
      throws SQLException, CryptographyException, SQLException, ParseException {
    Connection connection = DatabaseService.getConnection();

    try {
      Person person = createPerson(connection,
          "Lab_RDF",
          "Lab_RDF",
          "Columbia University",
          "212-851-5270",
          "1130 St. Nicholas Ave., NY NY 10032",
          "abh2138@columbia.edu");

      // Promote me to a login user

      String user = "rdf_lab";
      String password = "ItAly4u";

      createLoginUser(connection, person.getId(), user, password);

      // createSessionKey(connection, personId, ipAddress);

      // anywhere
      String ipAddress = "*"; // 127.0.0.1";

      String key = "86I4qZh3pI2pttB66Ffa8TyOE976sI0CtFFChxngIbaW2YOdpdCBigyrralMoiyk";

      createApiKey(connection, person.getId(), ipAddress, key);

      createIpLogin(connection, person.getId(), ipAddress);

      // String key = LoginService.createSessionKey(connection, personId,
      // ipAddress);

      System.err.println(user + " " + person.getId());

      /*
       * personId = createPerson(connection, "Guest", "Guest",
       * "Columbia University", "123-456-7890",
       * "1130 St. Nicholas Ave., NY NY 10032", "guest@columbia.edu");
       */
    } finally {
      connection.close();
    }
  }

  public static Person createPersonFromIdf(Connection connection,
      java.nio.file.Path idfFile)
      throws SQLException, IOException, SQLException, ParseException {
    BufferedReader reader = FileUtils.newBufferedReader(idfFile);

    String line;

    String name = null;
    String description = null;
    String address = null;
    String affiliation = null;
    String firstName = null;
    String lastName = null;
    String phone = null;
    String email = null;

    try {
      while ((line = reader.readLine()) != null) {
        List<String> tokens = TextUtils.fastSplit(line,
            TextUtils.TAB_DELIMITER);

        if (tokens.get(0).equalsIgnoreCase("experiment description")) {
          description = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("investigation title")) {
          name = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person address")) {
          address = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person affiliation")) {
          affiliation = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person first name")) {
          firstName = tokens.get(1);
        } else if (tokens.get(0).equalsIgnoreCase("person last name")) {
          lastName = tokens.get(1);
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

    System.err.println(name + " " + description);

    // Create the experiment

    Person personId = Persons.createPerson(connection,
        firstName,
        lastName,
        affiliation,
        phone,
        address,
        email);

    return personId;
  }

  public static Type createRoleFromIdf(Connection connection,
      java.nio.file.Path idfFile)
      throws SQLException, IOException, SQLException, ParseException {
    BufferedReader reader = FileUtils.newBufferedReader(idfFile);

    String line;

    String roleName = null;

    try {
      while ((line = reader.readLine()) != null) {
        List<String> tokens = TextUtils.fastSplit(line,
            TextUtils.TAB_DELIMITER);

        if (tokens.get(0).equalsIgnoreCase("person roles")) {
          roleName = tokens.get(1);
        }
      }
    } finally {
      reader.close();
    }

    // Create the experiment

    Type role = Persons.createRole(connection, roleName);

    return role;
  }

  private static String createApiKey(Connection connection,
      int personId,
      String ipAddress,
      String key) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT api_key_persons.key FROM api_key_persons WHERE api_key_persons.person_id = ?");

    DatabaseResultsTable table;

    String oldKey = null;

    try {
      statement.setInt(1, personId);

      statement.execute();

      table = JDBCConnection.getTable(statement);

      if (table.getRowCount() > 0) {
        oldKey = table.getString(0, 0);
      }
    } finally {
      statement.close();
    }

    if (oldKey != null) {
      statement = connection.prepareStatement(
          "DELETE FROM api_keys WHERE key = ? AND ip_address = ?");

      try {
        statement.setString(1, oldKey);
        statement.setString(2, ipAddress);

        statement.execute();
      } finally {
        statement.close();
      }
    }

    statement = connection
        .prepareStatement("DELETE FROM api_key_persons WHERE person_id = ?");

    try {
      statement.setInt(1, personId);

      statement.execute();
    } finally {
      statement.close();
    }

    // UUID.randomUUID().toString(); //Cryptography.getSalt512();

    System.err.println(key);

    statement = connection.prepareStatement(
        "INSERT INTO api_key_persons (person_id, key) VALUES (?, ?)");

    try {
      statement.setInt(1, personId);
      statement.setString(2, key);

      System.err.println(statement.toString());

      statement.execute();
    } finally {
      statement.close();
    }

    statement = connection.prepareStatement(
        "INSERT INTO api_keys (key, ip_address) VALUES (?, ?)");

    try {
      statement.setString(1, key);
      statement.setString(2, ipAddress);

      System.err.println(statement.toString());

      statement.execute();
    } finally {
      statement.close();
    }

    return key;
  }

  /**
   * Map an ip address (or range) to a login account. This can be used to detect
   * client logins for users without them having to login in.
   * 
   * @param connection
   * @param personId
   * @param ipAddress
   * @throws SQLException
   */
  private static void createIpLogin(Connection connection,
      int personId,
      String ipAddress) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT login_ip_address.id FROM login_ip_address WHERE login_ip_address.person_id = ? AND login_ip_address.ip_address = ?");

    DatabaseResultsTable table;

    try {
      statement.setInt(1, personId);
      statement.setString(2, ipAddress);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      return;
    }

    statement = connection.prepareStatement(
        "INSERT INTO login_ip_address (person_id, ip_address) VALUES (?, ?)");

    try {
      statement.setInt(1, personId);
      statement.setString(2, ipAddress);

      statement.execute();
    } finally {
      statement.close();
    }
  }

  static Type createRole(Connection connection, String name)
      throws SQLException {

    return Types.createType(connection, "roles", name);
  }

  public static Person createPerson(Connection connection,
      String firstName,
      String lastName,
      String affiliation,
      String phone,
      String address,
      String email) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT persons.id, persons.first_name, persons.last_name, persons.email FROM persons WHERE persons.email = ?");

    DatabaseResultsTable table;

    try {
      statement.setString(1, email);

      table = JDBCConnection.getTable(statement);
    } finally {
      statement.close();
    }

    if (table.getRowCount() == 1) {
      Person person = new Person(table.getInt(0, 0), table.getString(0, 1),
          table.getString(0, 2), table.getString(0, 3));

      return person;
    }

    statement = connection.prepareStatement(
        "INSERT INTO persons (public_uuid, first_name, last_name, affiliation, phone, address, email) VALUES (?, ?, ?, ?, ?, ?, ?)");

    statement.setString(1, generatePersonUUID());
    statement.setString(2, firstName);
    statement.setString(3, lastName);
    statement.setString(4, affiliation);
    statement.setString(5, phone);
    statement.setString(6, address);
    statement.setString(7, email);

    statement.execute();

    return createPerson(connection,
        firstName,
        lastName,
        affiliation,
        phone,
        address,
        email);
  }

  public static Person getPerson(Connection connection, int personId)
      throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT persons.id, persons.first_name, persons.last_name FROM persons WHERE persons.id = ?");

    DatabaseResultsTable table;

    Person person = null;

    try {
      statement.setInt(1, personId);

      table = JDBCConnection.getTable(statement);

      if (table.getRowCount() == 1) {
        person = new Person(table.getInt(0, 0), table.getString(0, 1),
            table.getString(0, 2));
      }
    } finally {
      statement.close();
    }

    return person;

  }

  public static void createLoginUser(Connection connection,
      int personId,
      String user,
      String password) throws SQLException, CryptographyException {
    createLoginUser(connection,
        personId,
        user,
        password,
        Cryptography.getSalt512());
  }

  /**
   * Create a login user.
   * 
   * @param connection
   * @param personId
   * @param user
   * @param password
   * @param key
   * @throws SQLException
   * @throws CryptographyException
   */
  public static void createLoginUser(Connection connection,
      int userId,
      String user,
      String password,
      String key) throws SQLException, CryptographyException {

    String salt = Cryptography.getSalt512();

    String hashedPasswordSalted = Cryptography.getSHA512Hash(password, salt);

    // System.err.println(hashedUser);
    // System.err.println(hashedPassword);

    PreparedStatement statement = connection.prepareStatement(
        "DELETE FROM login_persons WHERE login_persons.person_id = ?");
    try {
      statement.setInt(1, userId);

      statement.execute();
    } finally {
      statement.close();
    }

    statement = connection.prepareStatement(
        "INSERT INTO login_persons (person_id, user_name, password_hash_salted, salt, key) VALUES (?, ?, ?, ?, ?)");

    try {
      statement.setInt(1, userId);
      statement.setString(2, user);
      statement.setString(3, hashedPasswordSalted);
      statement.setString(4, salt);
      statement.setString(5, key);

      statement.execute();
    } finally {
      statement.close();
    }
  }

  public static List<Person> getGlobalPersons(Connection connection)
      throws SQLException {
    PreparedStatement statement = connection.prepareStatement(
        "SELECT persons.id, persons.first_name, persons.last_name FROM persons WHERE persons.user_type_id = 3 OR persons.user_type_id = 4");

    DatabaseResultsTable table;

    List<Person> persons = new ArrayList<Person>();

    try {
      table = JDBCConnection.getTable(statement);

      for (int i = 0; i < table.getRowCount(); ++i) {
        Person person = new Person(table.getInt(i, 0), table.getString(i, 1),
            table.getString(i, 2));

        persons.add(person);
      }
    } finally {
      statement.close();
    }

    return persons;
  }

  public static String generatePersonUUID() {
    return Cryptography.generateRandomId();
  }
}
