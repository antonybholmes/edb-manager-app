package edu.columbia.rdf.edb.manager.app.modules.groups;

import org.jebtk.database.DatabaseResultsTable;

import edu.columbia.rdf.edb.manager.app.DatabaseTableModel;

public class GroupsTableModel extends DatabaseTableModel {
  public static final String[] HEADER = { "Id", "Name" };

  public GroupsTableModel(DatabaseResultsTable table) {
    super(table);
  }

  @Override
  public String getColumnName(int column) {
    return HEADER[column];
  }
}
