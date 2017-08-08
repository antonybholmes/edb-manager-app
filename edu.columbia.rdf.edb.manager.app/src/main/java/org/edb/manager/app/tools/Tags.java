package org.edb.manager.app.tools;

import java.sql.Connection;
import java.sql.SQLException;

import org.abh.common.bioinformatics.annotation.Type;
import org.abh.common.path.Path;

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
