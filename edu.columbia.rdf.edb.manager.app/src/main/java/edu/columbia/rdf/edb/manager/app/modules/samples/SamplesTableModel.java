package edu.columbia.rdf.edb.manager.app.modules.samples;

import java.util.List;

import org.jebtk.database.DatabaseResultsTable;

import edu.columbia.rdf.edb.manager.app.DatabaseTableModel;

public class SamplesTableModel extends DatabaseTableModel {
  public static final String[] HEADER = { "Id", "Experiment Id", "Name",
      "Description" };

  public SamplesTableModel(DatabaseResultsTable table) {
    super(table);
  }

  @Override
  public List<String> getColumns().getAnnotation(int column) {
    return super.getColumns().getAnnotation(HEADER, column);
  }
}
