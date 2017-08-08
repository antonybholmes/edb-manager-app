package org.edb.manager.app.tools;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.database.JDBCConnection;

public class GEO {
	/**
	 * Creates a sample type such as Microarray to describe
	 * what the sample is.
	 * 
	 * @param connection
	 * @param accession
	 * @return
	 * @throws SQLException
	 * @throws SQLException 
	 * @throws ParseException 
	 */
	public static int createPlatform(Connection connection,
			String accession) throws SQLException {
		
		return createAccession(connection, "geo_platforms", accession);
	}
	
	public static int createSeries(Connection connection,
			String accession) throws SQLException {
		
		return createAccession(connection, "geo_series", accession);
	}
	
	public static int createSample(Connection connection,
			int geoSeriesId, 
			int geoPlatformId,
			int sampleId,
			String accession) throws SQLException, ParseException {
		if (geoSeriesId == -1 || 
				geoPlatformId == -1 || 
				sampleId == -1 || 
				accession == null || 
				accession.length() == 0) {
			return -1;
		}
		
		PreparedStatement statement = 
				connection.prepareStatement("SELECT geo_samples.id FROM geo_samples WHERE geo_samples.name = ?");
		
		DatabaseResultsTable table;
		
		try {
			statement.setString(1, accession);
		
			table = JDBCConnection.getTable(statement);
		} finally {
			statement.close();
		}
		
		if (table.getRowCount() == 1) {
			return table.getInt(0, 0);
		}
		
		statement = connection.prepareStatement("INSERT INTO geo_samples (geo_series_id, geo_platform_id, sample_id, name) VALUES (?, ?, ?, ?)");
		
		try {
			statement.setInt(1, geoSeriesId);
			statement.setInt(2, geoPlatformId);
			statement.setInt(3, sampleId);
			statement.setString(4, accession);
			
			statement.execute();
		} finally {
			statement.close();
		}
		
		return createSample(connection, geoSeriesId, geoPlatformId, sampleId, accession);
	}
	
	public static int createAccession(Connection connection, String type, String name) throws SQLException {
		if (name == null || name.length() == 0) {
			return -1;
		}
		
		String sql = new StringBuilder().append("SELECT ").append(type).append(".id FROM ").append(type).append(" WHERE LOWER(").append(type).append(".name) = LOWER(?)").toString();
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
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
		
		sql = new StringBuilder().append("INSERT INTO ").append(type).append(" (name) VALUES (?)").toString();
		
		//System.err.println(sql);
		
		statement = connection.prepareStatement(sql);
		
		try {
			statement.setString(1, name);
		
			statement.execute();
		} finally {
			statement.close();
		}
		
		return createAccession(connection, type, name);
	}
}
