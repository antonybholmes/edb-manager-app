package org.edb.manager.app.modules.persons.groups;

import org.abh.common.ui.table.ModernRowTable;
import org.abh.common.ui.table.ModernTableCheckboxCellEditor;
import org.abh.common.ui.table.ModernTableCheckboxCellRenderer;

public class PersonGroupsTable extends ModernRowTable {
	private static final long serialVersionUID = 1L;

	public PersonGroupsTable() {
		getRendererModel().setCol(0, new ModernTableCheckboxCellRenderer());
		getEditorModel().setCol(0, new ModernTableCheckboxCellEditor());
		getColumnModel().setWidth(0, 32);
		getColumnModel().setWidth(2, 300);
	}
}
