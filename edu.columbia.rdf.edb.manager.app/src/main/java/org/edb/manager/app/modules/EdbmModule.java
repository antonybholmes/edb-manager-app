package org.edb.manager.app.modules;

import org.abh.common.ui.help.GuiAppInfo;
import org.edb.manager.app.ManagerInfo;

public abstract class EdbmModule implements Module {

	@Override
	public GuiAppInfo getModuleInfo() {
		return new ManagerInfo();
	}
}
