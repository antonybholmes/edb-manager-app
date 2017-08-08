package org.edb.manager.app.modules.groups;

import org.abh.common.ui.table.ModernRowTable;
import org.abh.common.ui.table.ModernTableCheckboxCellEditor;
import org.abh.common.ui.table.ModernTableCheckboxCellRenderer;

public class GroupsTable extends ModernRowTable {
	private static final long serialVersionUID = 1L;

	public GroupsTable() {
		getColumnModel().setWidth(1, 300);
	}
}
