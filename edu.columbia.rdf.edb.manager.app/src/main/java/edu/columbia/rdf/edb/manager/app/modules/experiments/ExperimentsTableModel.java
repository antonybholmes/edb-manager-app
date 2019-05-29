package edu.columbia.rdf.edb.manager.app.modules.experiments;

import org.jebtk.database.DatabaseResultsTable;

import edu.columbia.rdf.edb.manager.app.DatabaseTableModel;

public class ExperimentsTableModel extends DatabaseTableModel {
  public static final String[] HEADER = { "Id", "Public Id", "Name",
      "Description" };

  public ExperimentsTableModel(DatabaseResultsTable table) {
    super(table);
  }

  @Override
  public String getColumnName(int column) {
    return HEADER[column];
  }
}
