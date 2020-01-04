package edu.columbia.rdf.edb.manager.app.modules.persons.groups;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Box;

import org.jebtk.core.collections.CollectionUtils;
import org.jebtk.core.text.TextUtils;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.database.query.InsertQuery;
import org.jebtk.database.query.SelectFromQuery;
import org.jebtk.database.query.SelectWhereQuery;
import org.jebtk.database.query.TableQuery;
import org.jebtk.database.query.ValuesQuery;
import org.jebtk.database.query.WhereQuery;
import org.jebtk.modern.AssetService;
import org.jebtk.modern.BorderService;
import org.jebtk.modern.ModernWidget;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.dialog.MessageDialogType;
import org.jebtk.modern.dialog.ModernMessageDialog;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.graphics.icons.RunVectorIcon;
import org.jebtk.modern.panel.HBox;
import org.jebtk.modern.panel.ModernPanel;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.table.ModernRowTable;

import edu.columbia.rdf.edb.manager.app.MainManagerWindow;

public class PersonGroupsPanel extends ModernPanel {

  private static final long serialVersionUID = 1L;

  private Connection mConnection;

  private ModernRowTable mTable = new PersonGroupsTable();

  private ModernButton mNewButton = new ModernButton("New...");

  private ModernButton mUpdateButton = new ModernButton("Update",
      AssetService.getInstance().loadIcon(RunVectorIcon.class, 16));

  private ModernButton mSelectAllButton = new ModernButton("Select All");

  private MainManagerWindow mWindow;

  private SelectFromQuery mExperimentsQuery;

  private WhereQuery mDeleteQuery;

  private TableQuery mQuery;

  private WhereQuery mRemoveExperimentPermissions;

  private InsertQuery mAddPermissionQuery;

  private PersonGroupsCombo mPersonsCombo;

  private SelectWhereQuery mExperimentPermissions;

  private PersonGroupsTableModel mModel;

  private boolean mSelectAll = true;

  public PersonGroupsPanel(Connection connection, MainManagerWindow window)
      throws SQLException {
    mConnection = connection;
    mWindow = window;

    Box box = HBox.create();

    mPersonsCombo = new PersonGroupsCombo(connection);

    UI.setSize(mPersonsCombo, 200, ModernWidget.WIDGET_HEIGHT);

    box.add(mUpdateButton);
    box.add(UI.createHGap(5));
    box.add(mSelectAllButton);
    box.add(UI.createHGap(40));
    box.add(mPersonsCombo);

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
          // createPerson();
        } catch (Exception e1) {
          ModernMessageDialog.createDialog(mWindow,
              "There was an error adding the user",
              MessageDialogType.WARNING);
        }
      }
    });

    mUpdateButton.addClickListener(new ModernClickListener() {
      @Override
      public void clicked(ModernClickEvent e) {
        try {
          update();
        } catch (SQLException e1) {
          e1.printStackTrace();
        } catch (ParseException e1) {
          e1.printStackTrace();
        }
      }
    });

    mSelectAllButton.addClickListener(new ModernClickListener() {
      @Override
      public void clicked(ModernClickEvent e) {

        mModel.selectAll(mSelectAll);

        mSelectAll = !mSelectAll;
      }
    });

    mQuery = new TableQuery(mConnection);

    mExperimentsQuery = mQuery.select("id", "name").from("groups");

    mExperimentPermissions = mQuery.select("id", "person_id", "group_id")
        .from("groups_persons").where("person_id");

    mRemoveExperimentPermissions = mQuery.delete().from("groups_persons")
        .where("person_id");

    mAddPermissionQuery = mQuery.insert("groups_persons").cols("group_id",
        "person_id");

    mPersonsCombo.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        try {
          refresh();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
      }
    });

    refresh();
  }

  private void refresh() throws SQLException {
    int id = mPersonsCombo.getSelectedId();

    DatabaseResultsTable results = mExperimentPermissions.values(id).fetch();

    Set<Integer> permissions = new HashSet<Integer>();

    for (int i = 0; i < results.getRowCount(); ++i) {
      permissions.add(results.getInt(i, 2));
    }

    results = mExperimentsQuery.fetch();

    mModel = new PersonGroupsTableModel(results, permissions);

    mTable.setModel(mModel);
  }

  private void update() throws SQLException, ParseException {
    int pid = mPersonsCombo.getSelectedId();

    // Remove existing links
    mRemoveExperimentPermissions.values(pid).execute();

    for (int i = 0; i < mModel.getRowCount(); ++i) {
      int eid = mModel.getValueAsInt(i, 1);

      boolean permission = mModel.getValueAsBool(i, 0);

      System.err.println(mAddPermissionQuery.toString());

      if (permission) {
        mAddPermissionQuery.values(eid, pid).execute();
      }
    }
  }

  /*
   * private void editPerson() throws SQLException, CryptographyException { int
   * index = mTable.getSelectionModel().first();
   * 
   * if (index == -1) { return; }
   * 
   * int id = mTable.getIntValueAt(index, 0);
   * 
   * DatabaseResultsTable personTable = mPersonQuery.values(id).fetch();
   * 
   * ExperimentPermissionsDialog dialog = new
   * ExperimentPermissionsDialog(mWindow, personTable);
   * 
   * dialog.setVisible(true);
   * 
   * if (dialog.getStatus() == ModernDialogStatus.CANCEL) { return; }
   * 
   * mQueryUpdateFirstName.values(dialog.getFirstName(), id).execute();
   * mQueryUpdateLastName.values(dialog.getLastName(), id).execute();
   * mQueryUpdateAffiliation.values(dialog.getAffiliation(), id).execute();
   * mQueryUpdatePhone.values(dialog.getPhone(), id).execute();
   * mQueryUpdateAddress.values(dialog.getAddress(), id).execute();
   * mQueryUpdateEmail.values(dialog.getEmail(), id).execute();
   * 
   * if (!TextUtils.isNullOrEmpty(dialog.getPublicId())) {
   * mQueryPublicId.values(dialog.getPublicId(), id).execute(); }
   * 
   * mQueryUpdateType.values(dialog.getPersonType(), id).execute();
   * 
   * if (!TextUtils.isNullOrEmpty(dialog.getPassword())) {
   * mQueryPassword.values(Cryptography.getSHA512Hash(dialog.getPassword(),
   * dialog.getSalt()), id).execute(); mQuerySalt.values(dialog.getSalt(),
   * id).execute(); }
   * 
   * if (!TextUtils.isNullOrEmpty(dialog.getApiKey())) {
   * mQueryApiKey.values(dialog.getApiKey(), id).execute(); }
   * 
   * refresh(); }
   * 
   * private void createPerson() throws SQLException, CryptographyException {
   * ExperimentPermissionsDialog dialog = new
   * ExperimentPermissionsDialog(mWindow);
   * 
   * dialog.setVisible(true);
   * 
   * if (dialog.getStatus() == ModernDialogStatus.CANCEL) { return; }
   * 
   * String password;
   * 
   * if (!TextUtils.isNullOrEmpty(dialog.getPassword())) { password =
   * Cryptography.getSHA512Hash(dialog.getPassword(), dialog.getSalt()); } else
   * { password = null; }
   * 
   * mAddPersonQuery.values(dialog.getFirstName(), dialog.getLastName(),
   * dialog.getAffiliation(), dialog.getPhone(), dialog.getAddress(),
   * dialog.getEmail(), dialog.getPersonType(), password, dialog.getSalt(),
   * dialog.getPublicId(), dialog.getApiKey()).execute();
   * 
   * refresh(); }
   */

  private void delete() throws SQLException, ParseException {
    List<Integer> rows = CollectionUtils.toList(mTable.getSelectionModel());

    // PreparedStatement statement = mConnection.prepareStatement(DELETE_SQL);

    for (int row : rows) {
      int id = TextUtils.parseInt(mTable.getValueAt(row, 0).toString());

      ValuesQuery dq = mDeleteQuery.values(id);

      System.err.println(dq.getSql());

      dq.execute();
    }

    // refresh();
  }
}
