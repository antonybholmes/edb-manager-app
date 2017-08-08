package org.edb.manager.app.tools;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.database.JDBCConnection;
import org.abh.common.io.Io;
import org.abh.common.io.PathUtils;

public class Files {
	public static int createSampleFile(Connection connection,
			java.nio.file.Path dataDirectory,
			int experimentId,
			int sampleId,
			String fileName) throws SQLException, IOException, ParseException {
		PreparedStatement statement = 
				connection.prepareStatement("SELECT sample_files.id FROM sample_files WHERE sample_files.sample_id = ? AND sample_files.name = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setInt(1, sampleId);
			statement.setString(2, fileName);
		
			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		// java.nio.filePath does not exist so must create it
		
		statement = connection.prepareStatement("SELECT experiments.public_id FROM experiments WHERE experiments.id = ?");

		try {
			statement.setInt(1, experimentId);

			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}

		String publicId = table.getString(0, 0);


		// local files
		int locationId = 1;

		Path path = dataDirectory.resolve(publicId).resolve(fileName);

		System.err.println(path);

		int type = createFileType(connection, fileName);

		statement = connection.prepareStatement("INSERT INTO sample_files (sample_id, name, path, file_type_id, file_location_id) VALUES (?, ?, ?, ?, ?)");

		try {
			statement.setInt(1, sampleId);
			statement.setString(2, fileName);
			statement.setString(3, PathUtils.toString(path));
			statement.setInt(4, type);
			statement.setInt(5, locationId);

			statement.execute();
		} finally {
			statement.close();
		}
		
		return createSampleFile(connection, dataDirectory, experimentId, sampleId, fileName);
	}
	
	public static int createDirectory(Connection connection,
			int sampleId,
			java.nio.file.Path dataDirectory) throws SQLException, IOException {
		
		
		return createDirectory(connection, sampleId, PathUtils.toString(dataDirectory));
	}
	
	public static int createDirectory(Connection connection,
			int sampleId,
			String dataDirectory) throws SQLException, IOException {
		PreparedStatement statement = 
				connection.prepareStatement("SELECT directories.id FROM directories WHERE directories.sample_id = ? AND directories.name = ?");
		
		int id = -1;
		
		try {
			statement.setInt(1, sampleId);
			statement.setString(2, dataDirectory);
		
			id = JDBCConnection.getInt(statement);
		} finally {
			statement.close();
		}
		
		if (id != -1) {
			return id;
		}

		statement = connection.prepareStatement("INSERT INTO directories (sample_id, name) VALUES (?, ?)");

		try {
			statement.setInt(1, sampleId);
			statement.setString(2, dataDirectory);

			statement.execute();
		} finally {
			statement.close();
		}
		
		return createDirectory(connection, sampleId, dataDirectory);
	}
	
	
	
	
	public static int createSampleFile2(Connection connection,
			int sampleId,
			String fileName) throws SQLException, IOException, ParseException {
		return createSampleFile2(connection,
				sampleId,
				fileName,
				Io.getFileExt(fileName));
	}
	
	public static int createSampleFile2(Connection connection,
			int sampleId,
			String fileName,
			String fileType) throws SQLException, IOException, ParseException {
		PreparedStatement statement = 
				connection.prepareStatement("SELECT files.id FROM files WHERE files.sample_id = ? AND files.name = ?");
		
		System.err.println("file " + " " + fileName + " " + sampleId);
		
		int id = -1;
		
		try {
			statement.setInt(1, sampleId);
			statement.setString(2, fileName);
		
			id = JDBCConnection.getInt(statement);
		} finally {
			statement.close();
		}
		
		if (id != -1) {
			return id;
		}

		//java.nio.filePath path = new File(dataDirectory, fileName);

		//System.err.println(path);

		int type = createFileType(connection, fileType);

		statement = connection.prepareStatement("INSERT INTO files (sample_id, name, file_type_id) VALUES (?, ?, ?)");

		try {
			statement.setInt(1, sampleId);
			statement.setString(2, fileName);
			statement.setInt(3, type);

			statement.execute();
		} finally {
			statement.close();
		}
		
		return createSampleFile2(connection, sampleId, fileName, fileType);
	}
	
	public static int createFileType(Connection connection,
			String fileName) throws SQLException, ParseException {
		
		String type = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT file_types.id FROM file_types WHERE file_types.name = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setString(1, type);

			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		statement = connection.prepareStatement("INSERT INTO file_types (name) VALUES (?)");
		
		try {
			statement.setString(1, type);
			
			statement.execute();
		} finally {
			statement.close();
		}
		
		return createFileType(connection, type);
	}
}
