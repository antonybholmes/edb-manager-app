package edu.columbia.rdf.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.SQLException;

import org.jebtk.bioinformatics.annotation.Type;
import org.jebtk.core.path.Path;

public class Tags {
	public static Type createTag(Connection connection,
			String path) throws SQLException {
		
		return createTag(connection, Path.createRootPath(path));
	}
	
	public static Type createTag(Connection connection,
			Path path) throws SQLException {
		
		return Types.createType(connection, "tags", path);
	}
}
