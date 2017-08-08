package org.edb.manager.app;

import org.abh.common.AppVersion;
import org.abh.common.ui.UIService;
import org.abh.common.ui.help.GuiAppInfo;



public class ManagerInfo extends GuiAppInfo {
	public ManagerInfo() {
		super("EDB Manager", 
				new AppVersion(3), 
				"Copyright (C) 2016-${year} Antony Holmes.", 
				UIService.getInstance().loadIcon(ManagerIcon.class, 32),
				UIService.getInstance().loadIcon(ManagerIcon.class, 128),
				"Experiment Database Manager");
	}
}
