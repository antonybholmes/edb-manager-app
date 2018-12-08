package edu.columbia.rdf.edb.manager.app.tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;

import org.jebtk.core.Resources;
import org.jebtk.core.cli.Arg;
import org.jebtk.core.cli.ArgParser;
import org.jebtk.core.cli.Args;

public class MainCreateExperimentDB {

  /**
   * @param args
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws SQLException
   */
  public static void main(String[] args)
      throws IOException, SQLException, ClassNotFoundException {
    Args options = new Args();

    options
        .add(new Arg('d', "database", true, "Database."));
    options.add(
        new Arg('i', "ip", true, "Database ip address."));
    options.add(
        new Arg('p', "password", true, "Database password."));
    options.add(
        new Arg('u', "user", true, "Database username."));
    options
        .add(new Arg('s', "store", true, "Store folder."));

    String database = null;
    String ip = null;
    String user = null;
    String password = null;
    String store = null;

    ArgParser clos = new ArgParser(options).parse(args);

    for (Entry<String, List<String>> clo : clos) {
      switch (clo.getKey()) {
      case "database":
        database = clo.getValue().get(0);
        break;
      case "ip":
        ip = clo.getValue().get(0);
        break;
      case "user":
        user = clo.getValue().get(0);
        break;
      case "p":
        password = clo.getValue().get(0);
        break;
      case "store":
        store = clo.getValue().get(0);
        break;
      }
    }

    String sql = Resources.getInstance().loadText("experimentdb.sql");

    Connection connection = DatabaseService
        .getConnection(database, ip, user, password);

    try {
      connection.createStatement().execute(sql);

    } finally {
      connection.close();
    }
  }

}
