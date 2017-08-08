package org.edb.manager.app.tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.database.JDBCConnection;

import edu.columbia.rdf.edb.Person;

public class VFS {
	private static final String FILES_SQL = "Select vfs.id FROM vfs";
	private static final String ROOT_DIR = "/";
	private static final int FILE_TYPE = 2;
	private static final int DIR_TYPE = 1;

	public static void main(String[] args) throws SQLException, ParseException {
		Connection connection = DatabaseService.getConnection();

		try {
			Person personId = Persons.createPerson(connection,
					"Lab_RDF", 
					"Lab_RDF",
					"Columbia University",
					"212-851-5270",
					"1130 St. Nicholas Ave., NY NY 10032",
					"abh2138@columbia.edu");

			//createVfsPermissions(connection, personId.getId());
		} finally {
			connection.close();
		}
	}

	public static void createVFS(Connection connection) throws SQLException, IOException, ParseException {
		PreparedStatement statement;

		// root dir
		//int rootVfsId = createVfsRoot(connection); //createVfsDir(connection, "/");
		
		// root experiment folder
		int exsVfsRootId = createVfsExDir(connection); //createVfsDir(connection, rootVfsId, "Experiments");


		statement = connection.prepareStatement("SELECT experiments.id, experiments.public_id FROM experiments");

		DatabaseResultsTable experimentTable = null;

		try {
			experimentTable = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}

		for (int ei = 0; ei < experimentTable.getRowCount(); ++ei) {
			int exId = experimentTable.getInt(ei, 0);

			String publicName = experimentTable.getString(ei, 1);

			System.err.println(publicName);

			// create some folders

			int exVfsId = createVfsDir(connection, exsVfsRootId, publicName);

			createExperimentFileLink(connection, exId, exVfsId);

			int samplesVfsRootId = createSamplesDir(connection, exVfsId);

			createExperimentFileLink(connection, exId, samplesVfsRootId);
			
			// insert samples

			statement = connection.prepareStatement("SELECT samples.id, samples.name FROM samples WHERE experiment_id = ?");

			DatabaseResultsTable vfsTable = null;

			try {
				statement.setInt(1, exId);
				vfsTable = JDBCConnection.getTable(statement);
			} finally {
				statement.close();
			}

			for (int si = 0; si < vfsTable.getRowCount(); ++si) {
				int sid = vfsTable.getInt(si, 0);
				String name = vfsTable.getString(si, 1);

				int sampleVfsRootId = createVfsDir(connection, 
						samplesVfsRootId, 
						name);
				
				createExperimentFileLink(connection, exId, sampleVfsRootId);
				createSampleFileLink(connection, sid, sampleVfsRootId);

				// files

				statement = connection.prepareStatement("SELECT files.name FROM files WHERE sample_id = ?");

				DatabaseResultsTable vfsTable2 = null;

				try {
					statement.setInt(1, sid);
					vfsTable2 = JDBCConnection.getTable(statement);
				} finally {
					statement.close();
				}

				for (int fi = 0; fi < vfsTable2.getRowCount(); ++fi) {
					String file = vfsTable2.getString(fi, 0);
					
					int vfsId = createVfsFile(connection, sampleVfsRootId, file);
					
					// Path
					updatePath(connection, vfsId, "/" + publicName + "/" + file);

					// Meta data

					//Type field = Fields.createField(connection, "VFS/Name");
					
					//createVfsField(connection, vfsId, field, file);
					//addVfsSearchTerm(connection, vfsId, field, file);

					//field = Fields.createField(connection, "VFS/Version");
					//createVfsField(connection, vfsId, field, 1);

					// link samples to files

					createSampleFileLink(connection, sid, vfsId);

					// link experiment to files

					createExperimentFileLink(connection, exId, vfsId);
				}
			}
		}

		// dirs


		for (int ei = 0; ei < experimentTable.getRowCount(); ++ei) {
			int exId = experimentTable.getInt(ei, 0);

			String publicId = experimentTable.getString(ei, 1);

			System.err.println(publicId);

			// create some folders

			int exVfsId = createVfsDir(connection, exsVfsRootId, publicId);

			int samplesVfsRootId = createVfsDir(connection, exVfsId, "Samples");

			// insert samples

			statement = connection.prepareStatement("SELECT samples.id, samples.name FROM samples WHERE experiment_id = ?");

			DatabaseResultsTable vfsTable = null;

			try {
				statement.setInt(1, exId);
				vfsTable = JDBCConnection.getTable(statement);
			} finally {
				statement.close();
			}

			for (int si = 0; si < vfsTable.getRowCount(); ++si) {
				int sid = vfsTable.getInt(si, 0);
				String name = vfsTable.getString(si, 1);

				int sampleVfsRootId = createVfsDir(connection, samplesVfsRootId, name);
				
				// files

				statement = connection.prepareStatement("SELECT directories.name FROM directories WHERE sample_id = ?");

				DatabaseResultsTable vfsTable2 = null;

				try {
					statement.setInt(1, sid);
					vfsTable2 = JDBCConnection.getTable(statement);
				} finally {
					statement.close();
				}

				for (int fi = 0; fi < vfsTable2.getRowCount(); ++fi) {
					String file = vfsTable2.getString(fi, 0);

					statement = connection.prepareStatement("UPDATE vfs SET path = ? WHERE name = ? AND id = ?");


					try {
						statement.setString(1, file);
						statement.setString(2, name);
						statement.setInt(3, sampleVfsRootId);

						System.err.println(statement);

						statement.execute();
					} finally {
						statement.close();
					}
				}
			}
		}
	}
	
	public static int createSamplesDir(Connection c, int exVfsId) throws SQLException {
		return createVfsDir(c, exVfsId, "Samples");
	}
 	
	public static int createVfsExDir(Connection c) throws SQLException {
		return createVfsDir(c, createVfsRoot(c), "Experiments");
	}
	
	public static int createVfsRoot(Connection connection) throws SQLException {
		return createVfsDir(connection, -1, ROOT_DIR);
	}
	
	public static int createVfsDir(Connection connection,
			int parentId,
			String name) throws SQLException {
		return createVfsEntry(connection, parentId, name, DIR_TYPE);
	}

	public static int createVfsFile(Connection connection,
			int parentId,
			String name) throws SQLException {
		int vfsId = createVfsEntry(connection, parentId, name, FILE_TYPE);
		
		//Type field = Fields.createField(connection, "VFS/Name");

		//addVfsSearchTerm(connection, vfsId, field, name);
		
		return vfsId;
	}
	
	public static void updatePath(Connection connection,
			int vfsId,
			String path) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("UPDATE vfs SET path = ? WHERE id = ?");

		try {
			statement.setString(1, path);
			statement.setInt(2, vfsId);

			statement.execute();
		} finally {
			statement.close();
		}
	}

	public static int createVfsEntry(Connection connection,
			int parentId,
			String name,
			int type) throws SQLException {
		PreparedStatement statement = 
				connection.prepareStatement("SELECT vfs.id FROM vfs WHERE name = ? AND parent_id = ? AND type_id = ?");

		int id = -1;

		try {
			statement.setString(1, name);
			statement.setInt(2, parentId);
			statement.setInt(3, type);
			id = JDBCConnection.getInt(statement);
		} finally {
			statement.close();
		}

		if (id != -1) {
			return id;
		}

		statement = 
				connection.prepareStatement("INSERT INTO vfs (name, parent_id, type_id) VALUES (?, ?, ?)");

		try {
			statement.setString(1, name);
			statement.setInt(2, parentId);
			statement.setInt(3, type);
			statement.execute();
		} finally {
			statement.close();
		}

		return createVfsEntry(connection, parentId, name, type);
	}

	/**
	 * Link a sample to either a directory or a file.
	 * 
	 * @param connection
	 * @param sampleId
	 * @param vfsId
	 * @throws SQLException
	 */
	public static void createSampleFileLink(Connection connection,
			int sampleId, 
			int vfsId) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT sample_files.id FROM sample_files WHERE sample_id = ? AND vfs_id = ?");

		boolean exists = false;

		try {
			statement.setInt(1, sampleId);
			statement.setInt(2, vfsId);
			exists = JDBCConnection.hasRecords(statement);
		} finally {
			statement.close();
		}

		if (!exists) {
			statement = connection.prepareStatement("INSERT INTO sample_files (sample_id, vfs_id) VALUES (?, ?)");

			try {
				statement.setInt(1, sampleId);
				statement.setInt(2, vfsId);
				statement.execute();
			} finally {
				statement.close();
			}
		}
	}

	/**
	 * Link an experiment to a file (either a dir or file).
	 * 
	 * @param connection
	 * @param experimentId
	 * @param vfsId
	 * @throws SQLException
	 */
	public static void createExperimentFileLink(Connection connection,
			int experimentId, 
			int vfsId) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT experiment_files.id FROM experiment_files WHERE experiment_id = ? AND vfs_id = ?");

		boolean exists = false;

		try {
			statement.setInt(1, experimentId);
			statement.setInt(2, vfsId);
			exists = JDBCConnection.hasRecords(statement);
		} finally {
			statement.close();
		}

		if (!exists) {
			statement = connection.prepareStatement("INSERT INTO experiment_files (experiment_id, vfs_id) VALUES (?, ?)");

			try {
				statement.setInt(1, experimentId);
				statement.setInt(2, vfsId);
				statement.execute();
			} finally {
				statement.close();
			}
		}
	}

	/*
	public static int createVfsField(Connection connection,
			int sampleId, 
			Type field,
			String value) throws SQLException, ParseException {
		PreparedStatement statement = 
				connection.prepareStatement("SELECT vfs_tags.id FROM vfs_tags WHERE vfs_tags.vfs_id = ? AND vfs_tags.tag_id = ?");

		DatabaseResultsTable table;

		try {
			statement.setInt(1, sampleId);
			statement.setInt(2, field.getId());

			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}

		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}

		statement = connection.prepareStatement("INSERT INTO vfs_tags (vfs_id, tag_id, value) VALUES (?, ?, ?)");

		try {
			statement.setInt(1, sampleId);
			statement.setInt(2, field.getId());
			statement.setString(3, value);

			statement.execute();
		}
		finally {
			statement.close();
		}

		return createVfsField(connection, sampleId, field, value);
	}

	public static int createVfsField(Connection connection,
			int sampleId, 
			Type field,
			int value) throws SQLException, ParseException {
		PreparedStatement statement = 
				connection.prepareStatement("SELECT vfs_int_tags.id FROM vfs_int_tags WHERE vfs_int_tags.vfs_id = ? AND vfs_int_tags.tag_id = ?");

		DatabaseResultsTable table;

		try {
			statement.setInt(1, sampleId);
			statement.setInt(2, field.getId());

			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}

		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}

		statement = connection.prepareStatement("INSERT INTO vfs_int_tags (vfs_id, tag_id, value) VALUES (?, ?, ?)");

		try {
			statement.setInt(1, sampleId);
			statement.setInt(2, field.getId());
			statement.setInt(3, value);

			statement.execute();
		}
		finally {
			statement.close();
		}

		return createVfsField(connection, sampleId, field, value);
	}


	public static void addVfsSearchTerms(Connection connection,
			int vfsId, 
			Type field, 
			Set<String> keywords) throws SQLException, ParseException {
		for (String keyword : keywords) {
			addVfsSearchTerm(connection, vfsId, field, keyword);
		}
	}

	public static void addVfsSearchTerm(Connection connection,
			int vfsId, 
			Type field,
			String keyword) throws SQLException, ParseException {
		//int categoryId = Search.createSearchCategory(connection, path);

		//Type field = Samples.createField(connection, path);

		createVfsSearchKeyword(connection, vfsId, keyword, field);

		// automatically add it to all
		addVfsSearchTermAll(connection, vfsId, keyword);
	}

	public static void addVfsSearchTermAll(Connection connection,
			int vfsId, 
			String keyword) throws SQLException, ParseException {
		//int categoryId = Search.createSearchCategory(connection, path);

		Type field = Fields.createField(connection, "All");

		createVfsSearchKeyword(connection, vfsId, keyword, field);

		field = Fields.createField(connection, "VFS/All");

		createVfsSearchKeyword(connection, vfsId, keyword, field);
	}

	public static void createVfsSearchKeyword(Connection connection,
			int vfsId,
			String keyword, 
			Type field) throws SQLException, ParseException {
		int keywordId = Search.createKeyword(connection, keyword);

		createVfsSearch(connection, vfsId, keywordId, keyword, field);
	}

	public static int createVfsSearch(Connection connection,
			int vfsId,
			int keywordId,
			String keyword,
			Type field) throws SQLException, ParseException {

		int searchFieldKeywordId = 
				Search.createSearchFieldKeyword(connection, field, keywordId, keyword);

		PreparedStatement statement = 
				connection.prepareStatement("SELECT vfs_search.id FROM vfs_search WHERE vfs_search.search_field_keyword_id = ? AND vfs_search.vfs_id = ?");

		DatabaseResultsTable table;

		try {
			statement.setInt(1, searchFieldKeywordId);
			statement.setInt(2, vfsId);

			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}

		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}

		statement = connection.prepareStatement("INSERT INTO vfs_search (search_field_keyword_id, vfs_id) VALUES (?, ?)");

		try {
			statement.setInt(1, searchFieldKeywordId);
			statement.setInt(2, vfsId);

			statement.execute();
		}
		finally {
			statement.close();
		}

		return createVfsSearch(connection, vfsId, keywordId, keyword, field);
	}

	public static void createVfsPermissions(Connection connection,
			int personId) throws SQLException {
		List<Integer> ids = JDBCConnection.getIntList(connection, FILES_SQL);

		PreparedStatement statement = connection.prepareStatement("DELETE FROM vfs_permissions WHERE vfs_permissions.person_id = ?");

		try {
			statement.setInt(1, personId);

			statement.execute();
		} finally {
			statement.close();
		}

		statement = connection.prepareStatement("INSERT INTO vfs_permissions (vfs_id, person_id) VALUES (?, ?)");

		try {
			for (int id : ids) {
				statement.setInt(1, id);
				statement.setInt(2, personId);

				statement.execute();
			}
		} finally {
			statement.close();
		}
	}
	*/
}
