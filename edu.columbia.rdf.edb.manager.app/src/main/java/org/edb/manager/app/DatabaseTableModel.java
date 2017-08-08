package org.edb.manager.app;

import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.ui.table.ModernColumnHeaderTableModel;


/**
 * Extended table model.
 *
 * @author Antony Holmes Holmes
 */
public class DatabaseTableModel extends ModernColumnHeaderTableModel {

	private DatabaseResultsTable mTable;
	
	public DatabaseTableModel(DatabaseResultsTable table) {
		mTable = table;
	}
	
	@Override
	public int getRowCount() {
		return mTable.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return mTable.getColumnCount();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return mTable.getData(row, column);
	}
}
