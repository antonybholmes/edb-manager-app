package org.edb.manager.app.modules.groups;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.Box;

import org.abh.common.collections.CollectionUtils;
import org.abh.common.cryptography.CryptographyException;
import org.abh.common.database.DatabaseResultsTable;
import org.abh.common.database.query.InsertQuery;
import org.abh.common.database.query.OrderByQuery;
import org.abh.common.database.query.SelectFromQuery;
import org.abh.common.database.query.SelectWhereQuery;
import org.abh.common.database.query.TableQuery;
import org.abh.common.database.query.ValuesQuery;
import org.abh.common.database.query.WhereQuery;
import org.abh.common.text.TextUtils;
import org.abh.common.ui.BorderService;
import org.abh.common.ui.UIService;
import org.abh.common.ui.button.ModernButton;
import org.abh.common.ui.dataview.ModernDataModel;
import org.abh.common.ui.dialog.DialogEvent;
import org.abh.common.ui.dialog.DialogEventListener;
import org.abh.common.ui.dialog.MessageDialogType;
import org.abh.common.ui.dialog.ModernDialogStatus;
import org.abh.common.ui.dialog.ModernMessageDialog;
import org.abh.common.ui.event.ModernClickEvent;
import org.abh.common.ui.event.ModernClickListener;
import org.abh.common.ui.panel.HBox;
import org.abh.common.ui.panel.ModernPanel;
import org.abh.common.ui.scrollpane.ModernScrollPane;
import org.abh.common.ui.table.ModernRowTable;
import org.edb.manager.app.MainManagerWindow;

public class GroupsPanel extends ModernPanel {

	private static final long serialVersionUID = 1L;

	private Connection mConnection;

	private ModernRowTable mTable = new GroupsTable();

	private ModernButton mNewButton = new ModernButton("New...");

	private ModernButton mEditButton = 
			new ModernButton(UIService.getInstance().loadIcon("edit_bw", 16));

	private ModernButton mDeleteButton = 
			new ModernButton(UIService.getInstance().loadIcon("trash_bw", 16));

	private MainManagerWindow mWindow;

	private SelectFromQuery mGroupsQuery;

	private WhereQuery mDeleteQuery;

	private TableQuery mQuery;

	private SelectWhereQuery mGroupQuery;

	private WhereQuery mQueryUpdateName;
	
	private OrderByQuery mOrderQuery;

	private InsertQuery mAddQuery;

	public GroupsPanel(Connection connection, MainManagerWindow window) throws SQLException {
		mConnection = connection;
		mWindow = window;

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



		mNewButton.addClickListener(new ModernClickListener() {
			@Override
			public void clicked(ModernClickEvent arg0) {
				try {
					create();
				} catch (Exception e1) {
					e1.printStackTrace();
					
					ModernMessageDialog.createDialog(mWindow, 
							"There was an error adding the user", 
							MessageDialogType.WARNING);
				}
			}});
		
		mEditButton.addClickListener(new ModernClickListener() {
			@Override
			public void clicked(ModernClickEvent e) {
				try {
					edit();
				} catch (Exception e1) {
					e1.printStackTrace();
					
					ModernMessageDialog.createDialog(mWindow, 
							"There was an error editing the group", 
							MessageDialogType.WARNING);
				}
			}});

		mDeleteButton.addClickListener(new ModernClickListener() {
			@Override
			public void clicked(ModernClickEvent e) {

				mWindow.createOkCancelDialog("Are you sure you want to delete the selected groups?", 
						new DialogEventListener() {
					@Override
					public void statusChanged(DialogEvent e) {
						if (e.getStatus() == ModernDialogStatus.OK) {
							try {
								delete();
							} catch (Exception e1) {
								e1.printStackTrace();

								mWindow.createDialog("There was an error deleting the groups", 
										MessageDialogType.WARNING);
							}
						}
					}});
			}});

		mQuery = new TableQuery(mConnection);

		mGroupsQuery = mQuery.select("id",
				"name")
				.from("groups");
		
		mOrderQuery = mGroupsQuery.order("name");
		
		mGroupQuery = mGroupsQuery.where("id");
		
		mAddQuery = mQuery.insert("groups")
				.cols("name");

		
		mQueryUpdateName = mQuery.update("groups").set("name").where("id");
		
		mDeleteQuery = mQuery.delete().from("groups").where("id");

		refresh();

	}

	private void refresh() throws SQLException {
		System.err.println(mOrderQuery);
		
		DatabaseResultsTable results = mOrderQuery.fetch();

		ModernDataModel model = new GroupsTableModel(results);

		mTable.setModel(model);
	}
	
	private void edit() throws SQLException, CryptographyException {
		int index = mTable.getSelectionModel().first();
		
		if (index == -1) {
			return;
		}
		
		int id = mTable.getIntValueAt(index, 0);
		
		DatabaseResultsTable personTable = mGroupQuery.values(id).fetch();
		
		GroupDialog dialog = new GroupDialog(mWindow,
				personTable);

		dialog.setVisible(true);

		if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
			return;
		}

		mQueryUpdateName.values(dialog.getName(), id).execute();
		
		refresh();
	}

	private void create() throws SQLException, CryptographyException {
		GroupDialog dialog = new GroupDialog(mWindow);

		dialog.setVisible(true);

		if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
			return;
		}
		
		mAddQuery.values(dialog.getName()).execute();

		refresh();
	}

	private void delete() throws SQLException, ParseException {
		List<Integer> rows = CollectionUtils.toList(mTable.getSelectionModel());

		//PreparedStatement statement = mConnection.prepareStatement(DELETE_SQL);

		for (int row : rows) {
			int id = TextUtils.parseInt(mTable.getValueAt(row, 0).toString());

			ValuesQuery dq = mDeleteQuery.values(id);

			System.err.println(dq.getSql());

			dq.execute();
		}

		refresh();
	}
}
