package edu.columbia.rdf.edb.manager.app.modules.samples.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jebtk.database.DatabaseResultsTable;

import edu.columbia.rdf.edb.manager.app.DatabaseTableModel;

public class ExperimentPermissionsTableModel extends DatabaseTableModel {
  public static final String[] HEADER = { "", "Id", "Name" };

  List<Boolean> mHasPermission;

  public ExperimentPermissionsTableModel(DatabaseResultsTable table,
      Set<Integer> permissions) {
    super(table);

    mHasPermission = new ArrayList<Boolean>(table.getRowCount());

    for (int i = 0; i < table.getRowCount(); ++i) {
      mHasPermission.add(permissions.contains(table.getInt(i, 0)));
    }
  }

  @Override
  public Object getValueAt(int row, int column) {
    switch (column) {
    case 0:
      return mHasPermission.get(row);
    default:
      return super.getValueAt(row, column - 1);
    }
  }

  @Override
  public void setValueAt(int row, int column, Object o) {
    if (column == 0) {
      mHasPermission.set(row, (Boolean) o);
    }
  }

  @Override
  public int getColCount() {
    return 3;
  }

  @Override
  public boolean getIsCellEditable(int row, int column) {
    return column == 0;
  }

  @Override
  public List<String> getColumnAnnotationText(int column) {
    return super.getColumnAnnotationText(HEADER, column);
  }

  public void selectAll(boolean selectAll) {
    for (int i = 0; i < mHasPermission.size(); ++i) {
      mHasPermission.set(i, selectAll);
    }

    fireDataUpdated();
  }

}
