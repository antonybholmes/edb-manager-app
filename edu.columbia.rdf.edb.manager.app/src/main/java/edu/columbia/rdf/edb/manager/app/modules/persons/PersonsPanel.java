package edu.columbia.rdf.edb.manager.app.modules.persons;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.Box;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.cryptography.Cryptography;
import org.jebtk.core.cryptography.CryptographyException;
import org.jebtk.core.text.TextUtils;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.query.InsertQuery;
import org.jebtk.database.query.OrderByQuery;
import org.jebtk.database.query.SelectFromQuery;
import org.jebtk.database.query.SelectWhereQuery;
import org.jebtk.database.query.TableQuery;
import org.jebtk.database.query.ValuesQuery;
import org.jebtk.database.query.WhereQuery;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.BorderService;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.dataview.ModernDataModel;
import org.jebtk.modern.dialog.DialogEvent;
import org.jebtk.modern.dialog.DialogEventListener;
import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernDialogStatus;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.table.ModernRowTable;

import edu.columbia.rdf.edb.manager.app.MainManagerWindow;

public class PersonsPanel extends ModernPanel {

  private static final long serialVersionUID = 1L;

  private Connection mConnection;

  private ModernRowTable mTable = new ModernRowTable();

  private ModernButton mNewButton = new ModernButton("New...");

  private ModernButton mEditButton = new ModernButton(
      AssetService.getInstance().loadIcon("edit_bw", 16));

  private ModernButton mDeleteButton = new ModernButton(
      AssetService.getInstance().loadIcon("trash_bw", 16));

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

  private WhereQuery mQueryPublicId;

  private WhereQuery mQuerySalt;

  private WhereQuery mQueryPassword;

  private WhereQuery mQueryApiKey;

  private InsertQuery mAddPersonQuery;

  private InsertQuery mAddGroupQuery;

  private SelectWhereQuery mPersonIdQuery;

  private SelectWhereQuery mGroupIdQuery;

  public PersonsPanel(Connection connection, MainManagerWindow window)
      throws SQLException {
    mConnection = connection;
    mWindow = window;

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
      }
    });

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
      }
    });

    mDeleteButton.addClickListener(new ModernClickListener() {
      @Override
      public void clicked(ModernClickEvent e) {

        mWindow.createOkCancelDialog(
            "Are you sure you want to delete the selected users?",
            new DialogEventListener() {
              @Override
              public void statusChanged(DialogEvent e) {
                if (e.getStatus() == ModernDialogStatus.OK) {
                  try {
                    delete();
                  } catch (Exception e1) {
                    e1.printStackTrace();

                    mWindow.createDialog(
                        "There was an error deleting the users",
                        MessageDialogType.WARNING);
                  }
                }
              }
            });
      }
    });

    mQuery = new TableQuery(mConnection);

    mPersonsQuery = mQuery.select("id",
        "public_uuid",
        "first_name",
        "last_name",
        "affiliation",
        "phone",
        "address",
        "email",
        "salt",
        "api_key").from("persons");

    mPersonIdQuery = mQuery.select("id").from("persons").where("first_name",
        "last_name");

    mGroupIdQuery = mQuery.select("id").from("groups").where("name");

    mOrderPersonsQuery = mPersonsQuery.order("last_name").col("first_name");

    mPersonQuery = mPersonsQuery.where("id");

    mAddPersonQuery = mQuery.insert("persons").cols("first_name",
        "last_name",
        "affiliation",
        "phone",
        "address",
        "email",
        "password_hash_salted",
        "salt",
        "public_uuid",
        "api_key");

    mAddGroupQuery = mQuery.insert("groups_persons").cols("group_id",
        "person_id");

    mQueryUpdateFirstName = mQuery.update("persons").set("first_name")
        .where("id");
    mQueryUpdateLastName = mQuery.update("persons").set("last_name")
        .where("id");
    mQueryUpdateAffiliation = mQuery.update("persons").set("affiliation")
        .where("id");
    mQueryUpdatePhone = mQuery.update("persons").set("phone").where("id");
    mQueryUpdateAddress = mQuery.update("persons").set("address").where("id");
    mQueryUpdateEmail = mQuery.update("persons").set("email").where("id");

    mQueryPublicId = mQuery.update("persons").set("public_uuid").where("id");
    mQuerySalt = mQuery.update("persons").set("salt").where("id");
    mQueryPassword = mQuery.update("persons").set("password_hash_salted")
        .where("id");
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

    PersonDialog dialog = new PersonDialog(mWindow, personTable);

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

    if (!TextUtils.isNullOrEmpty(dialog.getPassword())) {
      mQueryPassword.values(
          Cryptography.getSHA512Hash(dialog.getPassword(), dialog.getSalt()),
          id).execute();
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
      password = Cryptography.getSHA512Hash(dialog.getPassword(),
          dialog.getSalt());
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
        .values(dialog.getFirstName(), dialog.getLastName()).fetch();

    int userId = table.getInt(0, 0);

    String group;

    switch (dialog.getPersonType()) {
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

    table = mGroupIdQuery.values(group).fetch();

    int groupId = table.getInt(0, 0);

    mAddGroupQuery.values(groupId, userId).execute();

    refresh();
  }

  private void delete() throws SQLException, ParseException {
    List<Integer> rows = CollectionUtils.toList(mTable.getSelectionModel());

    // PreparedStatement statement = mConnection.prepareStatement(DELETE_SQL);

    for (int row : rows) {
      int id = TextUtils.parseInt(mTable.getValueAt(row, 0).toString());

      ValuesQuery dq = mDeleteQuery.values(id);

      System.err.println(dq.getSql());

      dq.execute();
    }

    refresh();
  }
}
