package org.edb.manager.app.tools;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RemoveSample {
	public static void main(String[] args) {
		try {
			Connection connection = DatabaseService.getConnection();
			
			try {
				//removeExperiments(connection); //(connection, experimentId);
			
				//removeSampleTypes(connection);
				
				removeSample(connection, 3976);
			} finally {
		    	connection.close();
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeSample(Connection connection, int sampleId) throws SQLException, IOException {
		System.err.println("Sample " + sampleId);
		
		removeSamplePersons(connection, sampleId);
		
		removeSampleArrayDesign(connection, sampleId);

		removeFiles(connection, sampleId);
		
		removeSampleFiles(connection, sampleId);
		
		removeSampleKeywords(connection, sampleId);
		
		removeSections(connection, sampleId);
		
		removeSampleEntry(connection, sampleId);
    }

	private static void removeSamplePersons(Connection connection,
			int sampleId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sample_persons WHERE sample_persons.sample_id = ?");
		
		try {
			statement.setInt(1, sampleId);
		
			
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment persons removed.");
	}
	
	private static void removeFiles(Connection connection,
			int sampleId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM files WHERE files.id IN (SELECT sample_files.file_id FROM sample_files WHERE sample_files.sample_id = ?)");
		
		try {
			statement.setInt(1, sampleId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment files removed for experiment " + sampleId + ".");
	}
	
	private static void removeSampleFiles(Connection connection,
			int sampleId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sample_files WHERE sample_files.sample_id = ?");
		
		try {
			statement.setInt(1, sampleId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Files removed for sample " + sampleId + ".");
	}
	
	private static void removeSampleArrayDesign(Connection connection,
			int sampleId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sample_array_design WHERE sample_array_design.sample_id = ?");
		
		try {
			statement.setInt(1, sampleId);
			
			System.err.println(statement);

			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment array designs removed for sample " + sampleId + ".");
	}
	
	private static void removeSections(Connection connection,
			int sampleId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sections WHERE sections.sample_id = ?");
		
		try {
			statement.setInt(1, sampleId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Sections removed for sample " + sampleId + ".");
	}
	
	private static void removeSampleKeywords(Connection connection,
			int sampleId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM keywords_samples WHERE keywords_samples.sample_id = ?");
		
		try {
			statement.setInt(1, sampleId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Sections removed for sample " + sampleId + ".");
	}
	
	private static void removeSampleEntry(Connection connection,
			int sampleId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM samples WHERE samples.id = ?");
		
		try {
			statement.setInt(1, sampleId);

			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Sample " + sampleId + " removed.");
	}
}
