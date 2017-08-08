package org.edb.manager.app.modules.groups;


import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.Box;

import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.ui.UI;
import org.abh.common.ui.dialog.ModernDialogTaskWindow;
import org.abh.common.ui.event.ModernClickListener;
import org.abh.common.ui.panel.MatrixPanel;
import org.abh.common.ui.panel.VBox;
import org.abh.common.ui.text.ModernAutoSizeLabel;
import org.abh.common.ui.text.ModernClipboardTextField;
import org.abh.common.ui.text.ModernTextBorderPanel;
import org.abh.common.ui.text.ModernTextField;
import org.abh.common.ui.widget.ModernWidget;
import org.abh.common.ui.window.ModernWindow;


/**
 * Allows a matrix group to be edited.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class GroupDialog extends ModernDialogTaskWindow implements ModernClickListener {
	private static final long serialVersionUID = 1L;
	
	private ModernTextField mNameField = new ModernClipboardTextField();

	public GroupDialog(ModernWindow parent) {
		super(parent);
		
		setTitle("New Group");
		
		createUi();
		
		setup();
	}
	
	public GroupDialog(ModernWindow parent,
			DatabaseResultsTable table) {
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

	private void loadGroup(DatabaseResultsTable table) throws SQLException, ParseException {
		mNameField.setText(table.getDataAsString(0, "name"));
	}

	private void setup() {
		setSize(600, 200);
		
		UI.centerWindowToScreen(this);
	}


	private final void createUi() {
		Box box = VBox.create();
		
		int[] rows = {ModernWidget.WIDGET_HEIGHT};
		int[] cols = {100, 400};
		
		MatrixPanel matrixPanel = new MatrixPanel(rows, 
				cols, 
				ModernWidget.PADDING, 
				ModernWidget.PADDING);
		
		matrixPanel.add(new ModernAutoSizeLabel("Name"));
		matrixPanel.add(new ModernTextBorderPanel(mNameField));
		
		box.add(matrixPanel);
		
		setDialogCardContent(box);
	}

	public String getName() {
		return mNameField.getText();
	}
}
