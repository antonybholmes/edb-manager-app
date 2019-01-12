package edu.columbia.rdf.edb.manager.app.modules.persons;

import java.util.List;

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
  public List<String> getColumns().getAnnotation(int column) {
    return super.getColumns().getAnnotation(HEADER, column);
  }
}
