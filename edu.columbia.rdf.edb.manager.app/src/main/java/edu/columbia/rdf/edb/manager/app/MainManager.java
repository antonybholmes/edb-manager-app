package edu.columbia.rdf.edb.manager.app;

import java.awt.FontFormatException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import org.jebtk.core.AppService;
import org.jebtk.core.PluginService;
import org.jebtk.core.settings.SettingsService;
import org.jebtk.modern.ColorTheme;
import org.jebtk.modern.theme.ThemeService;
import org.xml.sax.SAXException;

import edu.columbia.rdf.edb.EDBWLogin;
import edu.columbia.rdf.edb.manager.app.modules.experiments.ExperimentsModule;
import edu.columbia.rdf.edb.manager.app.modules.groups.GroupsModule;
import edu.columbia.rdf.edb.manager.app.modules.persons.PersonsModule;
import edu.columbia.rdf.edb.manager.app.modules.persons.groups.PersonGroupsModule;
import edu.columbia.rdf.edb.manager.app.modules.samples.SamplesModule;
import edu.columbia.rdf.edb.manager.app.tools.EDBLogin;

public class MainManager {
  public static final void main(String[] args)
      throws SAXException, IOException, ParserConfigurationException,
      KeyManagementException, NoSuchAlgorithmException, FontFormatException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      UnsupportedLookAndFeelException, SQLException, ParseException {
    main();
  }

  public static void main()
      throws SAXException, IOException, ParserConfigurationException,
      KeyManagementException, NoSuchAlgorithmException, FontFormatException,
      ClassNotFoundException, InstantiationException, IllegalAccessException,
      UnsupportedLookAndFeelException, SQLException, ParseException {
    AppService.getInstance().setAppInfo("edbm");

    ThemeService.getInstance().setTheme(ColorTheme.GREEN);

    // Network.disableSLLChecks();

    System.err.println("block " + SettingsService.getInstance().getString("edb.manager.server"));
    
    EDBWLogin l = EDBWLogin.loadFromSettings();
    
    EDBLogin login = new EDBLogin(l,
        SettingsService.getInstance().getString("edb.manager.server"),
        SettingsService.getInstance().getString("edb.manager.db"),
        SettingsService.getInstance().getString("edb.manager.user"),
        SettingsService.getInstance().getString("edb.manager.password"));

    PluginService.getInstance().addPlugin(PersonsModule.class);
    PluginService.getInstance().addPlugin(PersonGroupsModule.class);
    PluginService.getInstance().addPlugin(ExperimentsModule.class);
    //PluginService.getInstance().addPlugin(ExperimentPermissionsModule.class);
    PluginService.getInstance().addPlugin(SamplesModule.class);
    PluginService.getInstance().addPlugin(GroupsModule.class);

    // Connection connection = DatabaseService.getConnection();
    // SamplePermissions.createViews(connection, 87, 6);
    // VFS.createVfsPermissions(connection, 6);
    // connection.close();

    //ManagerLoginDialog window = new ManagerLoginDialog(login, appInfo);

    //window.setVisible(true);
    
    System.err.println(SettingsService.getInstance().getString("edb.manager.server"));
    
    MainManagerWindow window = new MainManagerWindow(login);

    window.setVisible(true);
  }
}
