package org.edb.manager.app.modules.samples;

import java.util.List;

import org.abh.common.database.DatabaseResultsTable;
import org.edb.manager.app.DatabaseTableModel;

public class SamplesTableModel extends DatabaseTableModel {
	public static final String[] HEADER = 
		{"Id", "Experiment Id", "Name", "Description"};
	
	public SamplesTableModel(DatabaseResultsTable table) {
		super(table);
	}
	
	@Override
	public List<String> getColumnAnnotationText(int column) {
		return super.getColumnAnnotationText(HEADER, column);
	}
}
