package edu.columbia.rdf.edb.manager.app.modules.persons;

import org.jebtk.database.DatabaseResultsTable;

import edu.columbia.rdf.edb.manager.app.DatabaseTableModel;

public class PersonsTableModel extends DatabaseTableModel {
  public static final String[] HEADER = { "Id", "Public Id",
      "First Name", "Last Name", "Affiliation", "Phone", "Address", "Email",
      "Salt", "API Key" };

  public PersonsTableModel(DatabaseResultsTable table) {
    super(table);
  }

  @Override
  public String getColumnName(int column) {
    return HEADER[column];
  }
}
