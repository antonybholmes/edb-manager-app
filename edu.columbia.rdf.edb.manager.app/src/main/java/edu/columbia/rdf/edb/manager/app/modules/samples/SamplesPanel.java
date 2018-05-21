package edu.columbia.rdf.edb.manager.app.modules.samples;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Box;

import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.query.OrderByQuery;
import org.jebtk.database.query.TableQuery;
import org.jebtk.database.query.WhereQuery;
import org.jebtk.modern.BorderService;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.dataview.ModernDataModel;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.table.ModernRowTable;

import edu.columbia.rdf.edb.manager.app.MainManagerWindow;

public class SamplesPanel extends ModernPanel {

  private static final long serialVersionUID = 1L;

  private static final String SAMPLES_SQL = "SELECT samples.id, samples.experiment_id, samples.name, samples.description FROM samples ORDER BY samples.name";

  private Connection mConnection;

  private ModernRowTable mTable = new ModernRowTable();

  private ModernButton mNewButton = new ModernButton("New...");

  private ModernButton mEditButton = new ModernButton(
      AssetService.getInstance().loadIcon("edit_bw", 16));

  private ModernButton mDeleteButton = new ModernButton(
      AssetService.getInstance().loadIcon("trash_bw", 16));

  private OrderByQuery mSamplesQuery;

  private WhereQuery mDeleteQuery;

  public SamplesPanel(Connection connection, MainManagerWindow window)
      throws SQLException {
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

    mSamplesQuery = new TableQuery(mConnection)
        .select("id", "experiment_id", "name", "description").from("samples")
        .order("name");

    mDeleteQuery = new TableQuery(mConnection).delete().from("samples")
        .where("id");

    refresh();
  }

  private void refresh() throws SQLException {
    DatabaseResultsTable results = mSamplesQuery.fetch();

    System.err.println(mSamplesQuery.getSql());

    ModernDataModel model = new SamplesTableModel(results);

    mTable.setModel(model);
  }
}
