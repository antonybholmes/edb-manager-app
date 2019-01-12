package edu.columbia.rdf.edb.manager.app;

import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.modern.table.ModernTableModel;

/**
 * Extended table model.
 *
 * @author Antony Holmes
 */
public class DatabaseTableModel extends ModernTableModel {

  private DatabaseResultsTable mTable;

  public DatabaseTableModel(DatabaseResultsTable table) {
    mTable = table;
  }

  @Override
  public int getRowCount() {
    return mTable.getRowCount();
  }

  @Override
  public int getColCount() {
    return mTable.getColumnCount();
  }

  @Override
  public Object getValueAt(int row, int column) {
    return mTable.getData(row, column);
  }
}
