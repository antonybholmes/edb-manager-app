package edu.columbia.rdf.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.core.path.Path;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;

public class Search {
	public static int createSearchCategory(Connection connection,
			String name) throws SQLException {
		return createSearchCategory(connection, Path.createRootPath(name));
	}
	
	public static int createSearchCategory(Connection connection,
			Path path) throws SQLException {
		
		String name = path.toString();
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT search_categories.id FROM search_categories WHERE search_categories.name = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setString(1, name);
	
			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		statement = connection.prepareStatement("INSERT INTO search_categories (name) VALUES (?)");
		
		try {
			statement.setString(1, name);
			
			statement.execute();
		}
		finally {
			statement.close();
		}
		
		return createSearchCategory(connection, path);
	}

	public static void createSearchKeywords(Connection connection,
			int sampleId,
			Set<String> keywords,
			Type field) throws SQLException {
		for (String keyword : keywords) {
			createSampleSearchKeyword(connection, sampleId, keyword, field);
		}
	}
	
	public static void createSampleSearchKeyword(Connection connection,
			int sampleId,
			String keyword, 
			Type tag) throws SQLException {
		int keywordId = createKeyword(connection, keyword);
		
		//System.err.println("Creating key " + keyword + " " + keywordId);
		
		//int searchSampleId = createSearchSample(connection, sampleId, keywordId);
		
		createSearchSample(connection, sampleId, keywordId, keyword, tag);
	}
	
	public static int createPrefix(Connection connection, String prefix) throws SQLException {
		
		String p = prefix.toLowerCase();
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT search_prefixes.id FROM search_prefixes WHERE search_prefixes.name = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setString(1, p);
	
			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		statement = connection.prepareStatement("INSERT INTO search_prefixes (name) VALUES (?)");
		
		try {
			statement.setString(1, p);
			
			statement.execute();
		}
		finally {
			statement.close();
		}
		
		return createPrefix(connection, p);
	}
	
	public static int createKeyword(Connection connection,
			String keyword) throws SQLException {
		
		
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT keywords.id FROM keywords WHERE keywords.name = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setString(1, keyword.toLowerCase());
	
			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		statement = connection.prepareStatement("INSERT INTO keywords (name) VALUES (?)");
		
		try {
			statement.setString(1, keyword.toLowerCase());
			
			statement.execute();
		} finally {
			statement.close();
		}
		
		return createKeyword(connection, keyword);
	}
	
	public static int createSearchSample(Connection connection,
			int sampleId,
			int keywordId,
			String keyword,
			Type field) throws SQLException {
		
		int searchFieldKeywordId = 
				createSearchTagKeyword(connection, field, keywordId, keyword);
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT tags_samples_search.id FROM tags_samples_search WHERE tags_samples_search.tag_keyword_search_id = ? AND tags_samples_search.sample_id = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setInt(1, searchFieldKeywordId);
			statement.setInt(2, sampleId);
	
			//System.err.println(statement);
			
			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		statement = connection.prepareStatement("INSERT INTO tags_samples_search (tag_keyword_search_id, sample_id) VALUES (?, ?)");
		
		try {
			statement.setInt(1, searchFieldKeywordId);
			statement.setInt(2, sampleId);
			
			statement.execute();
		}
		finally {
			statement.close();
		}
		
		return createSearchSample(connection, sampleId, keywordId, keyword, field);
	}
	
	public static int createSearchTagKeyword(Connection connection,
			Type field,
			int keywordId,
			String keyword) throws SQLException {
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT tags_keywords_search.id FROM tags_keywords_search WHERE tags_keywords_search.tag_id = ? AND tags_keywords_search.keyword_id = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setInt(1, field.getId());
			statement.setInt(2, keywordId);
	
			table = JDBCConnection.getTable(statement);
			
			System.err.println("high " + statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		statement = connection.prepareStatement("INSERT INTO tags_keywords_search (tag_id, keyword_id) VALUES (?, ?)");
		
		try {
			statement.setInt(1, field.getId());
			statement.setInt(2, keywordId);
			//statement.setString(3, keyword.toLowerCase());
			
			System.err.println("high2 " + statement);
			
			statement.execute();
		}
		finally {
			statement.close();
		}
		
		return createSearchTagKeyword(connection, field, keywordId, keyword);
	}
	
	public static int createSearchExperiment(Connection connection,
			int experimentId,
			int prefixId) throws SQLException {
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT search_experiments.id FROM search_experiments WHERE search_experiments.search_prefix_id = ? AND search_experiments.experiment_id = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setInt(1, prefixId);
			statement.setInt(2, experimentId);
	
			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		statement = connection.prepareStatement("INSERT INTO search_experiments (search_prefix_id, experiment_id) VALUES (?, ?)");
		
		try {
			statement.setInt(1, prefixId);
			statement.setInt(2, experimentId);
			
			statement.execute();
		}
		finally {
			statement.close();
		}
		
		return createSearchExperiment(connection, experimentId, prefixId);
	}
}
