package org.edb.manager.app.modules.groups;

import java.util.List;

import org.abh.common.database.DatabaseResultsTable;
import org.edb.manager.app.DatabaseTableModel;

public class GroupsTableModel extends DatabaseTableModel {
	public static final String[] HEADER = {"Id", "Name"};
	
	public GroupsTableModel(DatabaseResultsTable table) {
		super(table);
	}
	
	@Override
	public List<String> getColumnAnnotationText(int column) {
		return super.getColumnAnnotationText(HEADER, column);
	}
}
