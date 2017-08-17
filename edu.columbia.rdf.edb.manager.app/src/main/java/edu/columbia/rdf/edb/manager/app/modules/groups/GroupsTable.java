package edu.columbia.rdf.edb.manager.app.modules.groups;

import org.jebtk.modern.table.ModernRowTable;
import org.jebtk.modern.table.ModernTableCheckboxCellEditor;
import org.jebtk.modern.table.ModernTableCheckboxCellRenderer;

public class GroupsTable extends ModernRowTable {
	private static final long serialVersionUID = 1L;

	public GroupsTable() {
		getColumnModel().setWidth(1, 300);
	}
}
