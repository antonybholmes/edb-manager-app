package org.edb.manager.app.tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.abh.common.Resources;
import org.abh.common.cli.CommandLineArg;
import org.abh.common.cli.CommandLineArgs;
import org.abh.common.cli.CommandLineOption;
import org.abh.common.cli.Options;


public class MainCreateExperimentDB {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		Options options = new Options();
		
		options.addOption(new CommandLineOption('d', "database", true, "Database."));
		options.addOption(new CommandLineOption('i', "ip", true, "Database ip address."));
		options.addOption(new CommandLineOption('p', "password", true, "Database password."));
		options.addOption(new CommandLineOption('u', "user", true, "Database username."));
		options.addOption(new CommandLineOption('s', "store", true, "Store folder."));
		
		String database = null;
		String ip = null;
		String user = null;
		String password = null;
		String store = null;
		
		CommandLineArgs clos = CommandLineArgs.parse(options, args);
		
		for (CommandLineArg clo : clos) {
			switch(clo.getShortName()) {
			case 'd':
				database = clo.getValue();
				break;
			case 'i':
				ip = clo.getValue();
				break;
			case 'u':
				user = clo.getValue();
				break;
			case 'p':
				password = clo.getValue();
				break;
			case 's':
				store = clo.getValue();
				break;
			}
		}
		
		String sql = Resources.getInstance().loadText("experimentdb.sql");
		
		Connection connection = DatabaseService.getConnection(database, ip, user, password);
		
		try {
			connection.createStatement().execute(sql);
			
		} finally {
			connection.close();
		}
	}

}
