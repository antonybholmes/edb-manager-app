package org.edb.manager.app.modules.experiments;

import java.util.List;

import org.abh.common.database.DatabaseResultsTable;
import org.edb.manager.app.DatabaseTableModel;

public class ExperimentsTableModel extends DatabaseTableModel {
	public static final String[] HEADER = {"Id", "Public Id", "Name", "Description"};
	
	public ExperimentsTableModel(DatabaseResultsTable table) {
		super(table);
	}
	
	@Override
	public List<String> getColumnAnnotationText(int column) {
		return super.getColumnAnnotationText(HEADER, column);
	}
}
