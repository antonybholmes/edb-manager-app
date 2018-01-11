package edu.columbia.rdf.edb.manager.app.modules.experiments;

import java.sql.Connection;

import org.jebtk.modern.ModernComponent;

import edu.columbia.rdf.edb.manager.app.MainManagerWindow;
import edu.columbia.rdf.edb.manager.app.modules.EdbmModule;

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
