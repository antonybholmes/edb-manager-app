package edu.columbia.rdf.edb.manager.app.modules.samples.permissions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.query.OrderByQuery;
import org.jebtk.database.query.TableQuery;
import org.jebtk.modern.combobox.ModernComboBox;

public class PersonCombo extends ModernComboBox {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<Integer> mIds = new ArrayList<Integer>();

  public PersonCombo(Connection connection) throws SQLException {
    TableQuery query = new TableQuery(connection);

    OrderByQuery personsQuery = query.select("id", "first_name", "last_name")
        .from("persons").order("first_name", "last_name");

    DatabaseResultsTable results = personsQuery.fetch();

    for (int i = 0; i < results.getRowCount(); ++i) {
      addMenuItem(results.getString(i, 1) + " " + results.getString(i, 2));

      mIds.add(results.getInt(i, 0));
    }

    setSelectedIndex(0);
  }

  public int getSelectedId() {
    return mIds.get(getSelectedIndex());
  }
}
