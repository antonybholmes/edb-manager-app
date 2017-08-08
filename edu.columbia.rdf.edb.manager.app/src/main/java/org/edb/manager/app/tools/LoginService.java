package org.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.UUID;

import org.abh.common.cryptography.Cryptography;
import org.abh.common.cryptography.CryptographyException;
import org.abh.common.database.DatabaseConnection;
import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.database.JDBCConnection;


public class LoginService {
	private static final String SQL_USER = "SELECT login_persons.person_id, login_persons.password_hash_salted, login_persons.salt FROM login_persons WHERE login_persons.user_name = ?";
	private static final String SQL_DELETE = "DELETE FROM login_sessions WHERE login_sessions.person_id = ? AND login_sessions.ip_address = ?";
	private static final String SQL_SESSION = "INSERT INTO login_sessions VALUES (DEFAULT, ?, ?, ?, DEFAULT)";

	private static final String SQL_DELETE_KEY = "DELETE FROM login_keys WHERE login_keys.person_id = ?";
	private static final String SQL_KEY = "INSERT INTO login_keys VALUES (DEFAULT, ?, ?)";
	
	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection("jdbc:postgresql://localhost/experiment_store?user=antony&password=w0tnofear");
		
		//return DriverManager.getConnection("jdbc:mysql://localhost:3306/array_server_login?user=arrayserverlogin&password=2xKncC3D9MNjvefx");
	}
	
	public static DatabaseConnection getDatabase() throws SQLException {
		return new JDBCConnection(getConnection());
	}
	
	public static String generateKey(Connection connection, String user, String password) throws SQLException, CryptographyException, SQLException, ParseException {
		
		String key = "-1";
		
		PreparedStatement statement = connection.prepareStatement(SQL_USER);

		DatabaseResultsTable userTable = null;
		
		try {
			statement.setString(1, user);
			
			userTable = JDBCConnection.getTable(statement);
		}
		finally {
			if (statement != null) {
				statement.close();
			}
		}
		
		int personId = userTable.getInt(0, 0);
		String passwordHashSalted = userTable.getString(0, 1);
		String salt = userTable.getString(0, 2);
		
		String comparePasswordHashSalted = Cryptography.getSHA512Hash(Cryptography.getSHA512Hash(password), salt);
		
		boolean success = comparePasswordHashSalted.equals(passwordHashSalted);
		
		if (success) {
			key = createKey(connection, personId);
		}
		
		//logAttempt(connection, personId, ipAddress, success);
		
		return key;
	}
	
	private static String createKey(Connection connection,
			int userId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement(SQL_DELETE_KEY);
		
		try {
			statement.setInt(1, userId);
			
			statement.execute();
		}
		finally {
			statement.close();
		}
		
		String key = UUID.randomUUID().toString(); //Cryptography.getSalt512();
		
		statement = connection.prepareStatement(SQL_KEY);
		
		try {
			statement.setInt(1, userId);
			statement.setString(2, key);
		
			System.err.println(statement.toString());
			
			statement.execute();
			
		} finally {
			statement.close();
		}
	
		return key;
	}

	public static String createSessionKey(Connection connection,
			int personId,
			String ipAddress) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement(SQL_DELETE);
		
		try {
			statement.setInt(1, personId);
			statement.setString(2, ipAddress);
			
			System.err.println(statement);
			
			statement.execute();
		}
		finally {
			statement.close();
		}
		
		String key = Cryptography.generateRandAlphaNumId(64);
		
		statement = connection.prepareStatement(SQL_SESSION);
		
		try {
			statement.setInt(1, personId);
			statement.setString(2, ipAddress);
			statement.setString(3, key);
		
			System.err.println(statement.toString());
			
			statement.execute();
		} finally {
			statement.close();
		}
	
		return key;
	}
}
