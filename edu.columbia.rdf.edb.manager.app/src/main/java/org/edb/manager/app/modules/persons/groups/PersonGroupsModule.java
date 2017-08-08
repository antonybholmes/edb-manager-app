package org.edb.manager.app.modules.persons.groups;

import java.sql.Connection;
import java.sql.SQLException;

import org.abh.common.ui.ModernComponent;
import org.edb.manager.app.MainManagerWindow;
import org.edb.manager.app.modules.EdbmModule;

public class PersonGroupsModule extends EdbmModule {

	private PersonGroupsPanel mPanel;

	@Override
	public void init(Connection connection, MainManagerWindow window) {
		try {
			mPanel = new PersonGroupsPanel(connection, window);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ModernComponent getPanel() {
		return mPanel;
	}

	@Override
	public String getName() {
		return "Person Groups";
	}
}
