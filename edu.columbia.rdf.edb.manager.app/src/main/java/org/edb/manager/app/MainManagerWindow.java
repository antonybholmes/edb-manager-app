package org.edb.manager.app;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.abh.common.Plugin;
import org.abh.common.PluginService;
import org.abh.common.database.JDBCConnection;
import org.abh.common.io.FileUtils;
import org.abh.common.io.Io;
import org.abh.common.io.PathUtils;
import org.abh.common.io.Temp;
import org.abh.common.ui.ModernComponent;
import org.abh.common.ui.UI;
import org.abh.common.ui.UIService;
import org.abh.common.ui.clipboard.ClipboardRibbonSection;
import org.abh.common.ui.contentpane.ModernHContentPane;
import org.abh.common.ui.contentpane.SizableContentPane;
import org.abh.common.ui.event.ModernClickEvent;
import org.abh.common.ui.event.ModernClickListener;
import org.abh.common.ui.graphics.icons.RunVectorIcon;
import org.abh.common.ui.help.ModernAboutDialog;
import org.abh.common.ui.io.FileDialog;
import org.abh.common.ui.io.RecentFilesService;
import org.abh.common.ui.panel.CardPanel;
import org.abh.common.ui.ribbon.RibbonLargeButton;
import org.abh.common.ui.ribbon.RibbonMenuItem;
import org.abh.common.ui.status.ModernStatusBar;
import org.abh.common.ui.tabs.TabsModel;
import org.abh.common.ui.tabs.TabsViewPanel;
import org.abh.common.ui.widget.ModernWidget;
import org.abh.common.ui.window.ModernRibbonWindow;
import org.edb.manager.app.modules.Module;
import org.edb.manager.app.modules.ModulesPanel;
import org.edb.manager.app.tools.ChipSeq;
import org.edb.manager.app.tools.DatabaseService;
import org.edb.manager.app.tools.EDBLogin;
import org.edb.manager.app.tools.Experiments;
import org.edb.manager.app.tools.Microarray;
import org.edb.manager.app.tools.RnaSeq;
import org.edb.manager.app.tools.Samples;
import org.edb.manager.app.tools.VFS;

import edu.columbia.rdf.edb.Species;


public class MainManagerWindow extends ModernRibbonWindow implements ModernClickListener {
	private static final long serialVersionUID = 1L;

	private ModernStatusBar mStatusBar = new ModernStatusBar();

	private Connection mConnection;

	private ModulesPanel mModulesPanel;

	private ModernHContentPane mContentPane = 
			new ModernHContentPane();
	
	private TabsModel mTabsModel = new TabsModel();
	
	private TabsViewPanel mViewPanel = new TabsViewPanel(mTabsModel);

	
	public MainManagerWindow(EDBLogin login) throws SQLException, ClassNotFoundException {
		super(new ManagerInfo());

		mConnection = DatabaseService.getConnection(login);

		createMenus();

		createRibbon();

		createUi();

		createMenus();

		init();
	}

	@Override
	public void init() {
		setSize(1024, 768);

		UI.centerWindowToScreen(this);
	}

	public final void createRibbon() {

		//
		// Create the ribbon menu
		//

		//RibbongetRibbonMenu() getRibbonMenu() = new RibbongetRibbonMenu()(6);

		RibbonMenuItem menuItem;

		menuItem = new RibbonMenuItem("Download Files");
		getRibbonMenu().addTabbedMenuItem(menuItem);

		menuItem = new RibbonMenuItem("Export");
		getRibbonMenu().addTabbedMenuItem(menuItem);


		getRibbonMenu().addDefaultItems(getAppInfo());

		getRibbonMenu().setDefaultIndex(3);

		getRibbonMenu().addClickListener(this);

		//
		// Create the ribbon
		//

		//getRibbon() = new Ribbon2();
		getRibbon().setHelpButtonEnabled(getAppInfo());

		getRibbon().getToolbar("Home").addSection(new ClipboardRibbonSection(getRibbon()));

		//
		// Create the toolbars for the annotation tab
		//

		/*
		ModernButtonWidget button;

		button = new RibbonLargeButton2("Update", UIResources.getInstance().loadIcon(RunVectorIcon.class, 24));
		button.addClickListener(new ModernClickListener() {

			@Override
			public void clicked(ModernClickEvent e) {
				try {
					ExperimentPermissions.main(null);
					SamplePermissions.main(null);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}});


		getRibbon().getHomeToolbar().getSection("Tools").add(button);
		 */

		RibbonLargeButton button = new RibbonLargeButton("VFS", 
				UIService.getInstance().loadIcon(RunVectorIcon.class, 24));
		button.addClickListener(new ModernClickListener() {

			@Override
			public void clicked(ModernClickEvent e) {
				try {
					VFS.createVFS(DatabaseService.getConnection());
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}});

		//getRibbon().getHomeToolbar().getSection("Tools").add(button);

		button = new RibbonLargeButton("Microarray", 
				UIService.getInstance().loadIcon(RunVectorIcon.class, 24));
		button.addClickListener(new ModernClickListener() {

			@Override
			public void clicked(ModernClickEvent e) {
				try {
					gep();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}});
		getRibbon().getHomeToolbar().getSection("Tools").add(button);

		button = new RibbonLargeButton("ChIP-seq", 
				UIService.getInstance().loadIcon(RunVectorIcon.class, 24));
		button.addClickListener(new ModernClickListener() {

			@Override
			public void clicked(ModernClickEvent e) {
				try {
					chipSeq();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}});
		getRibbon().getHomeToolbar().getSection("Tools").add(button);

		button = new RibbonLargeButton("RNA-seq", UIService.getInstance().loadIcon(RunVectorIcon.class, 24));
		button.addClickListener(new ModernClickListener() {

			@Override
			public void clicked(ModernClickEvent e) {
				try {
					rnaSeq();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}});

		getRibbon().getHomeToolbar().getSection("Tools").add(button);

		button = new RibbonLargeButton("JSON", UIService.getInstance().loadIcon(RunVectorIcon.class, 24));
		button.addClickListener(new ModernClickListener() {

			@Override
			public void clicked(ModernClickEvent e) {
				try {
					json();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}});

		getRibbon().getHomeToolbar().getSection("Tools").add(button);
		
		button = new RibbonLargeButton("Peaks", UIService.getInstance().loadIcon(RunVectorIcon.class, 24));
		button.addClickListener(new ModernClickListener() {

			@Override
			public void clicked(ModernClickEvent e) {
				try {
					loadPeaksJson();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}});

		getRibbon().getHomeToolbar().getSection("Tools").add(button);
	}

	private void gep() throws SQLException, IOException, ParseException {
		Path dir = FileDialog.openDir(this, RecentFilesService.getInstance().getPwd());

		if (dir == null) {
			return;
		}
		
		Connection connection = DatabaseService.getConnection();

		Species organism = Experiments.createOrganism(connection, 
				"Human", 
				"Homo sapiens");

		try {
			Microarray.createExperiment(connection, organism, dir);
		} finally {
			connection.close();
		}

		RecentFilesService.getInstance().setPwd(dir);
	}

	private void chipSeq() throws SQLException, IOException, ParseException {
		Path dir = FileDialog.openDir(this, RecentFilesService.getInstance().getPwd());

		if (dir == null) {
			return;
		}
		
		Connection connection = DatabaseService.getConnection();

		try {
			ChipSeq.createExperiment(connection, dir);
		} finally {
			connection.close();
		}

		RecentFilesService.getInstance().setPwd(dir);
	}

	private void rnaSeq() throws SQLException, IOException, ParseException {
		Path dir = FileDialog.openDir(this, RecentFilesService.getInstance().getPwd());

		if (dir == null) {
			return;
		}
		
		Connection connection = DatabaseService.getConnection();

		try {
			RnaSeq.createExperiment(connection, dir);
		} finally {
			connection.close();
		}

		RecentFilesService.getInstance().setPwd(dir);
	}

	public void json() throws SQLException {
		sampleJson();
	}

	public void sampleJson() throws SQLException {

		Connection connection = DatabaseService.getConnection();

		try {
			Samples.sampleJson(connection);
			Samples.sampleGeoJson(connection);
			Samples.samplePersonsJson(connection);
		} finally {
			connection.close();
		}
	}


	public void loadPeaksJson() throws SQLException, IOException {

		Connection connection = DatabaseService.getConnection();

		Pattern idRegex = Pattern.compile("_([A-Z]{2}\\d+)_vs");

		try {

			Path dir = PathUtils.getPath("/ifs/scratch/cancer/Lab_RDF/abh2138/ChIP_seq/data/samples/hg19/rdf/json");

			List<Path> files = FileUtils.findAll(dir, "json");

			PreparedStatement findStatement = 
					connection.prepareStatement("SELECT samples.id FROM samples where name LIKE ?");

			String sql = "INSERT INTO chip_seq_peaks (sample_id, name, genome, read_length, json) VALUES (?1, '?2', 'hg19', 101, '?3')";
			
			try {
				for (Path file : files) {
					String name = PathUtils.getNameNoExt(file);

					Matcher matcher = idRegex.matcher(name);

					//System.err.println("name " + name);

					if (matcher.find()) {
						String cid = matcher.group(1);

						findStatement.setString(1, "%" + cid + "%");

						int id = JDBCConnection.getInt(findStatement);

						if (id != -1) {
							System.err.println("name " + name + " " + id);
							
							// We can use JDBC variables for the JSON type
							// in Postgresql so we must build the query 
							// manually
							String s = sql.replace("?1", Integer.toString(id))
									.replace("?2", name)
									.replace("?3", Io.getHead(file));
							
							//System.err.println(s);
							
							PreparedStatement insertStatement = 
									connection.prepareStatement(s);

							
							try {
								insertStatement.execute();
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								insertStatement.close();
							}
							
						}
					}
				}
			} finally {
				findStatement.close();
			}


		} finally {
			connection.close();
		}
	}


	@Override
	public final void createUi() {

		setBody(mContentPane);
		setFooter(mStatusBar);

		try {
			addModulesPane();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void addModulesPane() throws InstantiationException, IllegalAccessException {

		Module module;

		for (Plugin mc : PluginService.getInstance()) {
			module = (Module)mc.getPluginClass().newInstance();

			module.init(mConnection, this);

			mTabsModel.addTab(module.getName(), module.getPanel());
		}

		mModulesPanel = new ModulesPanel(mTabsModel);
	
		if (mContentPane.getModel().getLeftTabs().containsTab("Modules")) {
			return;
		}

		SizableContentPane sizePane = new SizableContentPane("Modules", 
				mModulesPanel, 
				300, 
				200, 
				600);

		mContentPane.getModel().addLeftTab(sizePane);
		
		ModernComponent tabsPanel = 
				new ModernComponent(new CardPanel(new ModernComponent(mViewPanel, ModernWidget.DOUBLE_BORDER)), ModernWidget.DOUBLE_BORDER);
		
		mContentPane.getModel().setCenterTab(tabsPanel);
		
	}


	public final void clicked(ModernClickEvent e) {
		if (e.getMessage().equals(UI.MENU_EXIT)) {
			close();
		} else if (e.getMessage().equals(UI.MENU_ABOUT)) {
			ModernAboutDialog.show(this, getAppInfo());
		} else {
			// do nothing
		}
	}

	@Override
	public void close() {
		Temp.deleteTempFiles();

		super.close();
	}
}
