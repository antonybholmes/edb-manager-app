package org.edb.manager.app.modules;

import java.sql.Connection;

import org.abh.common.NameProperty;
import org.abh.common.ui.ModernComponent;
import org.abh.common.ui.help.GuiAppInfo;
import org.edb.manager.app.MainManagerWindow;

public interface Module extends NameProperty {
	public GuiAppInfo getModuleInfo();
	
	/**
	 * Each module is given access to the app so that it can manipulate
	 * the UI and add new functions.
	 * 
	 * @param window
	 */
	public void init(Connection connection, MainManagerWindow window);
	
	public ModernComponent getPanel();
}
