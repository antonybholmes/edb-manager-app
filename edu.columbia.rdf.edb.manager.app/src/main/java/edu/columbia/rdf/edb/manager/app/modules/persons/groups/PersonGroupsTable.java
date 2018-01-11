package edu.columbia.rdf.edb.manager.app.modules.persons.groups;

import org.jebtk.modern.table.ModernRowTable;
import org.jebtk.modern.table.ModernTableCheckboxCellEditor;
import org.jebtk.modern.table.ModernTableCheckboxCellRenderer;

public class PersonGroupsTable extends ModernRowTable {
  private static final long serialVersionUID = 1L;

  public PersonGroupsTable() {
    getRendererModel().setCol(0, new ModernTableCheckboxCellRenderer());
    getEditorModel().setCol(0, new ModernTableCheckboxCellEditor());
    getColumnModel().setWidth(0, 32);
    getColumnModel().setWidth(2, 300);
  }
}
