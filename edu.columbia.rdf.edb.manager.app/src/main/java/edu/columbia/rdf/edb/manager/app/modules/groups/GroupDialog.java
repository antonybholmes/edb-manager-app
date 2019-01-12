package edu.columbia.rdf.edb.manager.app.modules.groups;

import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.Box;

import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.modern.UI;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.MatrixPanel;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernClipboardTextField;
import org.jebtk.modern.text.ModernTextBorderPanel;
import org.jebtk.modern.text.ModernTextField;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

/**
 * Allows a matrix group to be edited.
 * 
 * @author Antony Holmes
 *
 */
public class GroupDialog extends ModernDialogTaskWindow
    implements ModernClickListener {
  private static final long serialVersionUID = 1L;

  private ModernTextField mNameField = new ModernClipboardTextField();

  public GroupDialog(ModernWindow parent) {
    super(parent);

    setTitle("New Group");

    createUi();

    setup();
  }

  public GroupDialog(ModernWindow parent, DatabaseResultsTable table) {
    this(parent);

    setTitle("Edit Group");

    try {
      loadGroup(table);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private void loadGroup(DatabaseResultsTable table)
      throws SQLException, ParseException {
    mNameField.setText(table.getDataAsString(0, "name"));
  }

  private void setup() {
    setSize(600, 200);

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    Box box = VBox.create();

    int[] rows = { ModernWidget.WIDGET_HEIGHT };
    int[] cols = { 100, 400 };

    MatrixPanel matrixPanel = new MatrixPanel(rows, cols, ModernWidget.PADDING,
        ModernWidget.PADDING);

    matrixPanel.add(new ModernAutoSizeLabel("Name"));
    matrixPanel.add(new ModernTextBorderPanel(mNameField));

    box.add(matrixPanel);

    setCard(box);
  }

  public String getName() {
    return mNameField.getText();
  }
}
