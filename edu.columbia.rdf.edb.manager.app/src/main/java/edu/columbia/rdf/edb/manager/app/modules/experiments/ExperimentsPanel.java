package edu.columbia.rdf.edb.manager.app.modules.experiments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.Box;

import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.JDBCConnection;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.BorderService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.dataview.ModernDataModel;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.table.ModernRowTable;

import edu.columbia.rdf.edb.manager.app.MainManagerWindow;

public class ExperimentsPanel extends ModernPanel {

  private static final long serialVersionUID = 1L;

  private static final String EXPERIMENTS_SQL = "SELECT experiments.id, experiments.public_id, experiments.name, experiments.description FROM experiments ORDER BY experiments.name";

  private Connection mConnection;

  private ModernRowTable mTable = new ModernRowTable();

  private ModernButton mNewButton = new ModernButton("New...");

  private ModernButton mEditButton = new ModernButton(
      AssetService.getInstance().loadIcon("edit_bw", 16));

  private ModernButton mDeleteButton = new ModernButton(
      AssetService.getInstance().loadIcon("trash_bw", 16));

  public ExperimentsPanel(Connection connection, MainManagerWindow window) {
    mConnection = connection;

    Box box = HBox.create();

    box.add(mNewButton);
    box.add(createHGap());
    box.add(mEditButton);
    box.add(createHGap());
    box.add(mDeleteButton);

    box.setBorder(
        BorderService.getInstance().createTopBottomBorder(DOUBLE_PADDING));

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
