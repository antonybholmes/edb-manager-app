package edu.columbia.rdf.edb.manager.app.modules.groups;

import java.sql.Connection;
import java.sql.SQLException;

import org.jebtk.modern.ModernComponent;

import edu.columbia.rdf.edb.manager.app.MainManagerWindow;
import edu.columbia.rdf.edb.manager.app.modules.EdbmModule;

public class GroupsModule extends EdbmModule {

  private GroupsPanel mPanel;

  @Override
  public void init(Connection connection, MainManagerWindow window) {
    try {
      mPanel = new GroupsPanel(connection, window);
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
    return "Groups";
  }
}
