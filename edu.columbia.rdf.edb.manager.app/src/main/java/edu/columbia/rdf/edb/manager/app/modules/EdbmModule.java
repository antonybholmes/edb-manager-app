package edu.columbia.rdf.edb.manager.app.modules;

import org.jebtk.modern.help.GuiAppInfo;

import edu.columbia.rdf.edb.manager.app.ManagerInfo;

public abstract class EdbmModule implements Module {

	@Override
	public GuiAppInfo getModuleInfo() {
		return new ManagerInfo();
	}
}
