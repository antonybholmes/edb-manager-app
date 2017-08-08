package org.edb.manager.app.modules.experiments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.Box;

import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.database.JDBCConnection;
import org.abh.common.ui.BorderService;
import org.abh.common.ui.UIService;
import org.abh.common.ui.button.ModernButton;
import org.abh.common.ui.dataview.ModernDataModel;
import org.abh.common.ui.panel.HBox;
import org.abh.common.ui.panel.ModernPanel;
import org.abh.common.ui.scrollpane.ModernScrollPane;
import org.abh.common.ui.table.ModernRowTable;
import org.edb.manager.app.MainManagerWindow;

public class ExperimentsPanel extends ModernPanel {
	
	private static final long serialVersionUID = 1L;

	private static final String EXPERIMENTS_SQL = 
			"SELECT experiments.id, experiments.public_id, experiments.name, experiments.description FROM experiments ORDER BY experiments.name";
	
	private Connection mConnection;

	private ModernRowTable mTable = new ModernRowTable();

	private ModernButton mNewButton = new ModernButton("New...");
	
	private ModernButton mEditButton = 
			new ModernButton(UIService.getInstance().loadIcon("edit_bw", 16));
	
	private ModernButton mDeleteButton = 
			new ModernButton(UIService.getInstance().loadIcon("trash_bw", 16));
	
	public ExperimentsPanel(Connection connection, MainManagerWindow window) {
		mConnection = connection;

		Box box = HBox.create();
		
		box.add(mNewButton);
		box.add(createHGap());
		box.add(mEditButton);
		box.add(createHGap());
		box.add(mDeleteButton);
		
		box.setBorder(BorderService.getInstance().createTopBottomBorder(DOUBLE_PADDING));
		
		setHeader(box);
		
		ModernScrollPane scrollPane = new ModernScrollPane(mTable);
		
		setBody(scrollPane);
		
		setBorder(BORDER);
		
		try {
			refresh();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void refresh() throws SQLException {
		PreparedStatement statement = mConnection.prepareStatement(EXPERIMENTS_SQL);
		
		DatabaseResultsTable results = JDBCConnection.getTable(statement);

		ModernDataModel model = new ExperimentsTableModel(results);
		
		mTable.setModel(model);
	}
}
