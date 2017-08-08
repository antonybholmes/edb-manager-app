package org.edb.manager.app.modules.experiments;

import java.sql.Connection;

import org.abh.common.ui.ModernComponent;
import org.edb.manager.app.MainManagerWindow;
import org.edb.manager.app.modules.EdbmModule;

public class ExperimentsModule extends EdbmModule {

	private ExperimentsPanel mPanel;

	@Override
	public void init(Connection connection, MainManagerWindow window) {
		mPanel = new ExperimentsPanel(connection, window);
	}

	@Override
	public ModernComponent getPanel() {
		return mPanel;
	}

	@Override
	public String getName() {
		return "Experiments";
	}
}
