package edu.columbia.rdf.edb.manager.app.modules.groups;

import java.util.List;

import org.jebtk.database.DatabaseResultsTable;

import edu.columbia.rdf.edb.manager.app.DatabaseTableModel;

public class GroupsTableModel extends DatabaseTableModel {
  public static final String[] HEADER = { "Id", "Name" };

  public GroupsTableModel(DatabaseResultsTable table) {
    super(table);
  }

  @Override
  public List<String> getColumns().getAnnotation(int column) {
    return super.getColumns().getAnnotation(HEADER, column);
  }
}
