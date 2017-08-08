package org.edb.manager.app.modules.samples;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Box;

import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.database.query.OrderByQuery;
import org.abh.common.database.query.TableQuery;
import org.abh.common.database.query.WhereQuery;
import org.abh.common.ui.BorderService;
import org.abh.common.ui.UIService;
import org.abh.common.ui.button.ModernButton;
import org.abh.common.ui.dataview.ModernDataModel;
import org.abh.common.ui.panel.HBox;
import org.abh.common.ui.panel.ModernPanel;
import org.abh.common.ui.scrollpane.ModernScrollPane;
import org.abh.common.ui.table.ModernRowTable;
import org.edb.manager.app.MainManagerWindow;

public class SamplesPanel extends ModernPanel {

	private static final long serialVersionUID = 1L;

	private static final String SAMPLES_SQL = 
			"SELECT samples.id, samples.experiment_id, samples.name, samples.description FROM samples ORDER BY samples.name";

	private Connection mConnection;

	private ModernRowTable mTable = new ModernRowTable();

	private ModernButton mNewButton = new ModernButton("New...");

	private ModernButton mEditButton = 
			new ModernButton(UIService.getInstance().loadIcon("edit_bw", 16));

	private ModernButton mDeleteButton = 
			new ModernButton(UIService.getInstance().loadIcon("trash_bw", 16));

	private OrderByQuery mSamplesQuery;

	private WhereQuery mDeleteQuery;

	public SamplesPanel(Connection connection, MainManagerWindow window) throws SQLException {
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

		mSamplesQuery = new TableQuery(mConnection).select("id", 
				"experiment_id", 
				"name", 
				"description")
				.from("samples")
				.order("name");

		mDeleteQuery = new TableQuery(mConnection).delete().from("samples").where("id");

		refresh();
	}

	private void refresh() throws SQLException {
		DatabaseResultsTable results = mSamplesQuery.fetch();

		System.err.println(mSamplesQuery.getSql());

		ModernDataModel model = new SamplesTableModel(results);

		mTable.setModel(model);
	}
}
