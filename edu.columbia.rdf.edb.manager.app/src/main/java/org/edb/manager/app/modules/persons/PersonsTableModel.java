package org.edb.manager.app.modules.persons;

import java.util.List;

import org.abh.common.database.DatabaseResultsTable;
import org.edb.manager.app.DatabaseTableModel;

public class PersonsTableModel extends DatabaseTableModel {
	public static final String[] HEADER = {"Id", 
		"Public Id",
		"Type",
		"First Name", 
		"Last Name", 
		"Affiliation", 
		"Phone", 
		"Address", 
		"Email",
		"Salt",
		"API Key"};
	
	public PersonsTableModel(DatabaseResultsTable table) {
		super(table);
	}
	
	@Override
	public List<String> getColumnAnnotationText(int column) {
		return super.getColumnAnnotationText(HEADER, column);
	}
}
