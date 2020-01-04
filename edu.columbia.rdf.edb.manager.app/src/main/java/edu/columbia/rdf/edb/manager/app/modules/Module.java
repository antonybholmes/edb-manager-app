package edu.columbia.rdf.edb.manager.app.modules;

import java.sql.Connection;

import org.jebtk.core.NameGetter;
import org.jebtk.modern.ModernComponent;
import org.jebtk.modern.help.GuiAppInfo;

import edu.columbia.rdf.edb.manager.app.MainManagerWindow;

public interface Module extends NameGetter {
  public GuiAppInfo getModuleInfo();

  /**
   * Each module is given access to the app so that it can manipulate the UI and
   * add new functions.
   * 
   * @param window
   */
  public void init(Connection connection, MainManagerWindow window);

  public ModernComponent getPanel();
}
