package org.edb.manager.app.modules.persons.groups;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.database.query.OrderByQuery;
import org.abh.common.database.query.TableQuery;
import org.abh.common.ui.combobox.ModernComboBox;

public class PersonGroupsCombo extends ModernComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<Integer> mIds = new ArrayList<Integer>();
	
	public PersonGroupsCombo(Connection connection) throws SQLException {
		TableQuery query = new TableQuery(connection);
		
		OrderByQuery personsQuery = query.select("id",
				"first_name",
				"last_name")
				.from("persons")
				.order("first_name", "last_name");
		
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
