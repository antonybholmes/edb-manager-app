package edu.columbia.rdf.edb.manager.app;

import org.jebtk.core.AppVersion;
import org.jebtk.modern.UIService;
import org.jebtk.modern.help.GuiAppInfo;

public class ManagerInfo extends GuiAppInfo {
  public ManagerInfo() {
    super("EDB Manager", new AppVersion(3),
        "Copyright (C) 2016-${year} Antony Holmes.",
        UIService.getInstance().loadIcon(ManagerIcon.class, 32),
        UIService.getInstance().loadIcon(ManagerIcon.class, 128),
        "Experiment Database Manager");
  }
}
