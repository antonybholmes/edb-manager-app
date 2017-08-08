package org.edb.manager.app.tools;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.abh.common.cryptography.CryptographyException;


public class Version {
	public static void main(String[] args) throws SQLException, CryptographyException {
		Connection connection = DatabaseService.getConnection();
		
		try {
			createVersion(connection, 29);
	    } finally {
	    	connection.close();
	    }
	}
	
	/**
	 * Creates an id for the experiment that is slightly more descriptive
	 * than a simple number.
	 * 
	 * @param personId
	 * @return
	 * @throws SQLException 
	 */
	public static void createVersion(Connection connection, int version) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM version");
		
		try {
			statement.execute();
		} finally {
			statement.close();
		}
		
		statement = 
				connection.prepareStatement("INSERT INTO version (version) VALUES (?)");
		
		try {
			statement.setInt(1, version);
		
			statement.execute();
		} finally {
			statement.close();
		}
	}
}
