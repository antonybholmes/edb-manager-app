package org.edb.manager.app.modules.persons;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.Box;

import org.abh.common.collections.CollectionUtils;
import org.abh.common.cryptography.Cryptography;
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

public class PersonsPanel extends ModernPanel {

	private static final long serialVersionUID = 1L;

	private Connection mConnection;

	private ModernRowTable mTable = new ModernRowTable();

	private ModernButton mNewButton = new ModernButton("New...");

	private ModernButton mEditButton = 
			new ModernButton(UIService.getInstance().loadIcon("edit_bw", 16));

	private ModernButton mDeleteButton = 
			new ModernButton(UIService.getInstance().loadIcon("trash_bw", 16));

	private MainManagerWindow mWindow;

	private SelectFromQuery mPersonsQuery;

	private WhereQuery mDeleteQuery;

	private TableQuery mQuery;

	private SelectWhereQuery mPersonQuery;

	private WhereQuery mQueryUpdateFirstName;

	private WhereQuery mQueryUpdateAffiliation;

	private WhereQuery mQueryUpdateLastName;

	private WhereQuery mQueryUpdatePhone;

	private WhereQuery mQueryUpdateAddress;

	private WhereQuery mQueryUpdateEmail;

	private OrderByQuery mOrderPersonsQuery;

	private WhereQuery mQueryUpdateType;

	private WhereQuery mQueryPublicId;

	private WhereQuery mQuerySalt;

	private WhereQuery mQueryPassword;

	private WhereQuery mQueryApiKey;

	private InsertQuery mAddPersonQuery;

	private InsertQuery mAddGroupQuery;

	private SelectWhereQuery mPersonIdQuery;

	private SelectWhereQuery mGroupIdQuery;

	public PersonsPanel(Connection connection, MainManagerWindow window) throws SQLException {
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
					createPerson();
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
					editPerson();
				} catch (Exception e1) {
					e1.printStackTrace();
					
					ModernMessageDialog.createDialog(mWindow, 
							"There was an error editing the user", 
							MessageDialogType.WARNING);
				}
			}});

		mDeleteButton.addClickListener(new ModernClickListener() {
			@Override
			public void clicked(ModernClickEvent e) {

				mWindow.createOkCancelDialog("Are you sure you want to delete the selected users?", 
						new DialogEventListener() {
					@Override
					public void statusChanged(DialogEvent e) {
						if (e.getStatus() == ModernDialogStatus.OK) {
							try {
								delete();
							} catch (Exception e1) {
								e1.printStackTrace();

								mWindow.createDialog("There was an error deleting the users", MessageDialogType.WARNING);
							}
						}
					}});
			}});

		mQuery = new TableQuery(mConnection);

		mPersonsQuery = mQuery.select("id",
				"public_uuid",
				"user_type_id",
				"first_name",
				"last_name",
				"affiliation",
				"phone",
				"address",
				"email",
				"salt",
				"api_key")
				.from("persons");
		
		mPersonIdQuery = mQuery.select("id")
				.from("persons")
				.where("first_name", "last_name");
		
		mGroupIdQuery = mQuery.select("id")
				.from("groups")
				.where("name");
		
		mOrderPersonsQuery = mPersonsQuery.order("last_name").col("first_name");
		
		mPersonQuery = mPersonsQuery.where("id");
		
		mAddPersonQuery = mQuery.insert("persons")
				.cols("first_name",
						"last_name",
						"affiliation",
						"phone",
						"address",
						"email",
						"user_type_id",
						"password_hash_salted",
						"salt",
						"public_uuid",
						"api_key");
		
		mAddGroupQuery = mQuery.insert("groups_persons")
				.cols("group_id", "person_id");

		
		mQueryUpdateFirstName = mQuery.update("persons").set("first_name").where("id");
		mQueryUpdateLastName = mQuery.update("persons").set("last_name").where("id");
		mQueryUpdateAffiliation = mQuery.update("persons").set("affiliation").where("id");
		mQueryUpdatePhone = mQuery.update("persons").set("phone").where("id");
		mQueryUpdateAddress = mQuery.update("persons").set("address").where("id");
		mQueryUpdateEmail = mQuery.update("persons").set("email").where("id");

		mQueryUpdateType = mQuery.update("persons").set("user_type_id").where("id");
		mQueryPublicId = mQuery.update("persons").set("public_uuid").where("id");
		mQuerySalt = mQuery.update("persons").set("salt").where("id");
		mQueryPassword = mQuery.update("persons").set("password_hash_salted").where("id");
		mQueryApiKey = mQuery.update("persons").set("api_key").where("id");
		
		mDeleteQuery = mQuery.delete().from("persons").where("id");

		refresh();

	}

	private void refresh() throws SQLException {
		System.err.println(mOrderPersonsQuery);
		
		DatabaseResultsTable results = mOrderPersonsQuery.fetch();

		ModernDataModel model = new PersonsTableModel(results);

		mTable.setModel(model);
	}
	
	private void editPerson() throws SQLException, CryptographyException {
		int index = mTable.getSelectionModel().first();
		
		if (index == -1) {
			return;
		}
		
		int id = mTable.getIntValueAt(index, 0);
		
		DatabaseResultsTable personTable = mPersonQuery.values(id).fetch();
		
		PersonDialog dialog = new PersonDialog(mWindow,
				personTable);

		dialog.setVisible(true);

		if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
			return;
		}

		mQueryUpdateFirstName.values(dialog.getFirstName(), id).execute();
		mQueryUpdateLastName.values(dialog.getLastName(), id).execute();		
		mQueryUpdateAffiliation.values(dialog.getAffiliation(), id).execute();
		mQueryUpdatePhone.values(dialog.getPhone(), id).execute();
		mQueryUpdateAddress.values(dialog.getAddress(), id).execute();
		mQueryUpdateEmail.values(dialog.getEmail(), id).execute();
		
		if (!TextUtils.isNullOrEmpty(dialog.getPublicId())) {
			mQueryPublicId.values(dialog.getPublicId(), id).execute();
		}
		
		mQueryUpdateType.values(dialog.getPersonType(), id).execute();
		
		if (!TextUtils.isNullOrEmpty(dialog.getPassword())) {
			mQueryPassword.values(Cryptography.getSHA512Hash(dialog.getPassword(), dialog.getSalt()), id).execute();
			mQuerySalt.values(dialog.getSalt(), id).execute();
		}
		
		if (!TextUtils.isNullOrEmpty(dialog.getApiKey())) {
			mQueryApiKey.values(dialog.getApiKey(), id).execute();
		}
		
		refresh();
	}

	private void createPerson() throws SQLException, CryptographyException {
		PersonDialog dialog = new PersonDialog(mWindow);

		dialog.setVisible(true);

		if (dialog.getStatus() == ModernDialogStatus.CANCEL) {
			return;
		}
		
		String password = TextUtils.EMPTY_STRING;
		
		if (!TextUtils.isNullOrEmpty(dialog.getPassword())) {
			password = Cryptography.getSHA512Hash(dialog.getPassword(), dialog.getSalt());
		}
		
		
		// Make all users normal since the user type field is ignored
		int userType = 2;
		
		mAddPersonQuery.values(dialog.getFirstName(), 
				dialog.getLastName(),
				dialog.getAffiliation(),
				dialog.getPhone(),
				dialog.getAddress(),
				dialog.getEmail(),
				userType,
				password,
				dialog.getSalt(),
				dialog.getPublicId(),
				dialog.getApiKey()).execute();
		
		// Create the group link
		
		DatabaseResultsTable table = mPersonIdQuery
				.values(dialog.getFirstName(), dialog.getLastName())
				.fetch();

		int userId = table.getInt(0, 0);
		
		String group;
		
		switch(dialog.getPersonType()) {
		case 0:
			group = "Administrator";
			break;
		case 1:
			group = "Superuser";
			break;
		default:
			group = "Normal";
			break;
		}
		
		table = mGroupIdQuery
				.values(group)
				.fetch();
		
		int groupId = table.getInt(0, 0);
		
		mAddGroupQuery.values(groupId, userId).execute();
		
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
