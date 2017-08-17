package edu.columbia.rdf.edb.manager.app.tools;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;


public class RemoveExperiments {
	public static void main(String[] args) throws SQLException, IOException, SQLException, ParseException {
		Connection connection = DatabaseService.getConnection();
		
		try {
			//removeExperiments(connection); //(connection, experimentId);
		
			//removeSampleTypes(connection);
			
			removeExperiment(connection, 39);
		} finally {
	    	connection.close();
	    }
	}
	
	private static void removeExperiments(Connection connection) throws SQLException, IOException, SQLException, ParseException {
		PreparedStatement statement = 
				connection.prepareStatement("SELECT experiments.id FROM experiments");
		
		try {
			DatabaseResultsTable table = JDBCConnection.getTable(statement);
			
			for (int i = 0; i < table.getRowCount(); ++i) {
				int experimentId = table.getInt(i, 0);
				
				removeExperiment(connection, experimentId);
			}
			
		} finally {
			statement.close();
		}
	}
	
	public static void removeExperiment(Connection connection, int experimentId) throws SQLException, IOException, ParseException {
		System.err.println("Experiment " + experimentId);
		
		removeFileViewPermissions(connection, experimentId);
		
		removeExperimentViewPermissions(connection, experimentId);
		
		removeExperimentFiles(connection, experimentId);
		
		removeSamplePersons(connection, experimentId);
		
		removeSampleArrayDesigns(connection, experimentId);

		removeSampleFiles(connection, experimentId);
		
		removeFileViewPermissions(connection, experimentId);
		

		removeSections(connection, experimentId);
		
		removeAliases(connection, experimentId);
		
		
			
		removeSampleViewPermissions(connection, experimentId);
		
		
		removeSamples(connection, experimentId);
		
		
		//removeExperimentsSamples(connection, experimentId);
		
		//removeExperimentKeywords(connection, experimentId);
		
		removeExperimentEntry(connection, experimentId);
    }
	
	private static void removeExperimentFiles(Connection connection,
			int experimentId) throws SQLException, ParseException {
		PreparedStatement statement = 
				connection.prepareStatement("SELECT experiment_files.file_id FROM experiment_files WHERE experiment_files.experiment_id = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setInt(1, experimentId);

			 table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		List<Integer> ids = new ArrayList<Integer>();
		
		for (int i = 0; i < table.getRowCount(); ++i) {
			ids.add(table.getInt(i, 0));
		}
		
		
		statement = 
				connection.prepareStatement("DELETE FROM experiment_files WHERE experiment_files.experiment_id = ?");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		statement = connection.prepareStatement("DELETE FROM files WHERE files.id = ?");
		
		try {
			for(int id : ids) {
				statement.setInt(1, id);

				statement.execute();
			}
		} finally {
			statement.close();
		}
	}
	
	/*
	private static void removeExperimentsSamples(Connection connection,
			int experimentId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM experiments_samples WHERE experiments_samples.experiment_id = ?");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment view permissions removed.");
	}
	*/
	
	private static void removeSampleViewPermissions(Connection connection,
			int experimentId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sample_view_permissions WHERE sample_view_permissions.sample_id IN (SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?)");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment view permissions removed.");
	}
	
	private static void removeFileViewPermissions(Connection connection,
			int experimentId) throws SQLException {
		
		// Delete files at a sample level
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM file_view_permissions WHERE file_view_permissions.file_id IN (SELECT sample_files.file_id FROM sample_files WHERE sample_files.sample_id in (SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?))");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		// Delete files at an experiment level
		statement = 
				connection.prepareStatement("DELETE FROM file_view_permissions WHERE file_view_permissions.file_id IN (SELECT experiment_files.file_id FROM experiment_files WHERE experiment_files.experiment_id = ?)");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("File view permissions removed.");
	}
	
	private static void removeExperimentViewPermissions(Connection connection,
			int experimentId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM experiment_view_permissions WHERE experiment_view_permissions.experiment_id = ?");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment view permissions removed.");
	}

	private static void removeSamplePersons(Connection connection,
			int experimentId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sample_persons WHERE sample_persons.sample_id IN (SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?)");
		
		try {
			statement.setInt(1, experimentId);
		
			
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment persons removed.");
	}
	
	private static void removeSampleFiles(Connection connection,
			int experimentId) throws SQLException, ParseException {
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT sample_files.file_id FROM sample_files WHERE sample_files.sample_id IN (SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?)");
		
		DatabaseResultsTable table;
		
		try {
			statement.setInt(1, experimentId);

			 table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		List<Integer> ids = new ArrayList<Integer>();
		
		for (int i = 0; i < table.getRowCount(); ++i) {
			ids.add(table.getInt(i, 0));
		}
		
		
		statement = 
				connection.prepareStatement("DELETE FROM sample_files WHERE sample_files.sample_id IN (SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?)");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		statement = connection.prepareStatement("DELETE FROM files WHERE files.id = ?");
		
		try {
			for(int id : ids) {
				statement.setInt(1, id);

				statement.execute();
			}
		} finally {
			statement.close();
		}
		
		System.err.println("Sample files removed for experiment " + experimentId + ".");
	}
	
	private static void removeSampleTypes(Connection connection) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sample_types");
		
		try {
			//statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Sample types removed.");
	}
	
	private static void removeSampleArrayDesigns(Connection connection,
			int experimentId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sample_array_design WHERE sample_array_design.sample_id IN (SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?)");
		
		try {
			statement.setInt(1, experimentId);
			
			System.err.println(statement);

			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment array designs removed for experiment " + experimentId + ".");
	}
	
	private static void removeSections(Connection connection,
			int experimentId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sections WHERE sections.sample_id IN (SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?)");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Sections removed for experiment " + experimentId + ".");
	}
	
	private static void removeAliases(Connection connection,
			int experimentId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM sample_aliases WHERE sample_aliases.sample_id IN (SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?)");
		
		try {
			statement.setInt(1, experimentId);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Aliases removed for experiment " + experimentId + ".");
	}
	
	private static void removeSamples(Connection connection,
			int experimentId) throws SQLException, ParseException {
		// First get a list of the sample ids
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT experiments_samples.sample_id FROM experiments_samples WHERE experiments_samples.experiment_id = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setInt(1, experimentId);

			 table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		List<Integer> ids = new ArrayList<Integer>();
		
		for (int i = 0; i < table.getRowCount(); ++i) {
			ids.add(table.getInt(i, 0));
		}
		
		statement = connection.prepareStatement("DELETE FROM experiments_samples WHERE experiments_samples.experiment_id = ?");
		
		try {
			statement.setInt(1, experimentId);

			statement.execute();
		} finally {
			statement.close();
		}
		
		// Now delete the individual samples
		
		for(int id : ids) {
			statement = connection.prepareStatement("DELETE FROM samples WHERE samples.id = ?");
			
			try {
				statement.setInt(1, id);

				statement.execute();
			} finally {
				statement.close();
			}
		}
		
		System.err.println("Samples removed for experiment " + experimentId + ".");
	}
	
	private static void removeExperimentEntry(Connection connection,
			int experimentId) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("DELETE FROM experiments WHERE experiments.id = ?");
		
		try {
			statement.setInt(1, experimentId);

			statement.execute();
		} finally {
			statement.close();
		}
		
		System.err.println("Experiment " + experimentId + " removed.");
	}
}
