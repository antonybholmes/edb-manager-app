package org.edb.manager.app;

import java.awt.FontFormatException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import org.abh.common.AppService;
import org.abh.common.PluginService;
import org.abh.common.settings.SettingsService;
import org.abh.common.ui.ColorTheme;
import org.abh.common.ui.help.GuiAppInfo;
import org.abh.common.ui.theme.ThemeService;
import org.edb.manager.app.modules.experiments.ExperimentsModule;
import org.edb.manager.app.modules.experiments.permissions.ExperimentPermissionsModule;
import org.edb.manager.app.modules.groups.GroupsModule;
import org.edb.manager.app.modules.persons.PersonsModule;
import org.edb.manager.app.modules.persons.groups.PersonGroupsModule;
import org.edb.manager.app.modules.samples.SamplesModule;
import org.edb.manager.app.tools.EDBLogin;
import org.xml.sax.SAXException;


public class MainManager {
	public static final void main(String[] args) throws SAXException, IOException, ParserConfigurationException, KeyManagementException, NoSuchAlgorithmException, FontFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, ParseException {
		main();
	}

	public static void main() throws SAXException, IOException, ParserConfigurationException, KeyManagementException, NoSuchAlgorithmException, FontFormatException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException, ParseException {
		AppService.getInstance().setAppInfo("edbm");
		
		ThemeService.getInstance().setTheme(ColorTheme.GREEN);

		//Network.disableSLLChecks();

		GuiAppInfo appInfo = new ManagerInfo();

		EDBLogin login = new EDBLogin(SettingsService.getInstance().getAsString("edb.manager.server"),
				SettingsService.getInstance().getAsString("edb.manager.db"),
				SettingsService.getInstance().getAsString("edb.manager.user"),
				SettingsService.getInstance().getAsString("edb.manager.password"));

		PluginService.getInstance().addPlugin(PersonsModule.class);
		PluginService.getInstance().addPlugin(PersonGroupsModule.class);
		PluginService.getInstance().addPlugin(ExperimentsModule.class);
		PluginService.getInstance().addPlugin(ExperimentPermissionsModule.class);
		PluginService.getInstance().addPlugin(SamplesModule.class);
		PluginService.getInstance().addPlugin(GroupsModule.class);

		
		//Connection connection = DatabaseService.getConnection();
		//SamplePermissions.createViews(connection, 87, 6);
		//VFS.createVfsPermissions(connection, 6);
		//connection.close();
		
		ManagerLoginDialog window = new ManagerLoginDialog(login, appInfo);
		
		window.setVisible(true);
	}
}
