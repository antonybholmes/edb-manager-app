package edu.columbia.rdf.edb.manager.app.modules.samples;

import org.jebtk.database.DatabaseResultsTable;

import edu.columbia.rdf.edb.manager.app.DatabaseTableModel;

public class SamplesTableModel extends DatabaseTableModel {
  public static final String[] HEADER = { "Id", "Experiment Id", "Name",
      "Description" };

  public SamplesTableModel(DatabaseResultsTable table) {
    super(table);
  }

  @Override
  public String getColumnName(int column) {
    return HEADER[column];
  }
}
