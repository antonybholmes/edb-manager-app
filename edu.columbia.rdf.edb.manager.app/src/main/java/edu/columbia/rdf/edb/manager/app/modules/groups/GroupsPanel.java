package edu.columbia.rdf.edb.manager.app.modules.groups;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.Box;

import org.jebtk.core.collections.CollectionUtils;
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
import org.jebtk.modern.BorderService;
import org.jebtk.modern.AssetService;
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

public class GroupsPanel extends ModernPanel {

  private static final long serialVersionUID = 1L;

  private Connection mConnection;

  private ModernRowTable mTable = new GroupsTable();

  private ModernButton mNewButton = new ModernButton("New...");

  private ModernButton mEditButton = new ModernButton(
      AssetService.getInstance().loadIcon("edit_bw", 16));

  private ModernButton mDeleteButton = new ModernButton(
      AssetService.getInstance().loadIcon("trash_bw", 16));

  private MainManagerWindow mWindow;

  private SelectFromQuery mGroupsQuery;

  private WhereQuery mDeleteQuery;

  private TableQuery mQuery;

  private SelectWhereQuery mGroupQuery;

  private WhereQuery mQueryUpdateName;

  private OrderByQuery mOrderQuery;

  private InsertQuery mAddQuery;

  public GroupsPanel(Connection connection, MainManagerWindow window)
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
          create();
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
          edit();
        } catch (Exception e1) {
          e1.printStackTrace();

          ModernMessageDialog.createDialog(mWindow,
              "There was an error editing the group",
              MessageDialogType.WARNING);
        }
      }
    });

    mDeleteButton.addClickListener(new ModernClickListener() {
      @Override
      public void clicked(ModernClickEvent e) {

        mWindow.createOkCancelDialog(
            "Are you sure you want to delete the selected groups?",
            new DialogEventListener() {
              @Override
              public void statusChanged(DialogEvent e) {
                if (e.getStatus() == ModernDialogStatus.OK) {
                  try {
                    delete();
                  } catch (Exception e1) {
                    e1.printStackTrace();

                    mWindow.createDialog(
                        "There was an error deleting the groups",
                        MessageDialogType.WARNING);
                  }
                }
              }
            });
      }
    });

    mQuery = new TableQuery(mConnection);

    mGroupsQuery = mQuery.select("id", "name").from("groups");

    mOrderQuery = mGroupsQuery.order("name");

    mGroupQuery = mGroupsQuery.where("id");

    mAddQuery = mQuery.insert("groups").cols("name");

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

    GroupDialog dialog = new GroupDialog(mWindow, personTable);

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
