package edu.columbia.rdf.edb.manager.app.modules.persons;

import java.awt.Dimension;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.Box;

import org.jebtk.core.cryptography.Cryptography;
import org.jebtk.database.DatabaseResultsTable;
import org.jebtk.modern.UI;
import org.jebtk.modern.button.ModernButton;
import org.jebtk.modern.combobox.ModernComboBox;
import org.jebtk.modern.dialog.ModernDialogFlatButton;
import org.jebtk.modern.dialog.ModernDialogTaskWindow;
import org.jebtk.modern.event.ModernClickEvent;
import org.jebtk.modern.event.ModernClickListener;
import org.jebtk.modern.panel.MatrixPanel;
import org.jebtk.modern.panel.VBox;
import org.jebtk.modern.text.ModernAutoSizeLabel;
import org.jebtk.modern.text.ModernClipboardTextField;
import org.jebtk.modern.text.ModernTextBorderPanel;
import org.jebtk.modern.text.ModernTextField;
import org.jebtk.modern.widget.ModernWidget;
import org.jebtk.modern.window.ModernWindow;

import edu.columbia.rdf.edb.manager.app.tools.Persons;

/**
 * Allows a matrix group to be edited.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class PersonDialog extends ModernDialogTaskWindow
    implements ModernClickListener {
  private static final long serialVersionUID = 1L;

  private ModernTextField mFirstNameField = new ModernClipboardTextField();
  private ModernTextField mLastNameField = new ModernClipboardTextField();
  private ModernTextField mAffiliationField = new ModernClipboardTextField();
  private ModernTextField mPhoneField = new ModernClipboardTextField();
  private ModernTextField mAddressField = new ModernClipboardTextField();
  private ModernTextField mEmailField = new ModernClipboardTextField();

  private ModernButton mGenPublicIdButton = new ModernDialogFlatButton(
      "Generate");

  private ModernTextField mPassField = new ModernClipboardTextField();
  private ModernTextField mSaltField = new ModernClipboardTextField();
  private ModernTextField mPublicIdField = new ModernClipboardTextField();
  private ModernTextField mKeyField = new ModernClipboardTextField();

  private ModernButton mGenSaltButton = new ModernDialogFlatButton("Generate");

  private ModernButton mGenKeyButton = new ModernDialogFlatButton("Generate");

  private ModernComboBox mTypeCombo = new UserTypeCombo();

  public PersonDialog(ModernWindow parent) {
    super(parent);

    setTitle("New Person");

    createUi();

    setup();
  }

  public PersonDialog(ModernWindow parent, DatabaseResultsTable table) {
    this(parent);

    setTitle("Edit Person");

    try {
      loadPerson(table);
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  private void loadPerson(DatabaseResultsTable table)
      throws SQLException, ParseException {
    mFirstNameField.setText(table.getDataAsString(0, "first_name"));
    mLastNameField.setText(table.getDataAsString(0, "last_name"));
    mAddressField.setText(table.getDataAsString(0, "address"));
    mAffiliationField.setText(table.getDataAsString(0, "affiliation"));
    mPhoneField.setText(table.getDataAsString(0, "phone"));
    mEmailField.setText(table.getDataAsString(0, "email"));

    mSaltField.setText(table.getDataAsString(0, "salt"));
    mPublicIdField.setText(table.getDataAsString(0, "public_uuid"));
    mKeyField.setText(table.getDataAsString(0, "api_key"));

    mTypeCombo.setSelectedIndex(table.getDataAsInt(0, "user_type_id") - 2);
  }

  private void setup() {
    mPublicIdField.setText(Cryptography.generateRandomId());
    mSaltField.setText(Cryptography.getSalt512());
    mKeyField.setText(Cryptography.generateRandAlphaNumId64());

    mPublicIdField.setEditable(false);

    mGenPublicIdButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        mPublicIdField.setText(Persons.generatePersonUUID());
      }
    });

    mGenSaltButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        mSaltField.setText(Cryptography.getSalt512());
      }
    });

    mGenKeyButton.addClickListener(new ModernClickListener() {

      @Override
      public void clicked(ModernClickEvent e) {
        mKeyField.setText(Cryptography.generateRandAlphaNumId64());
      }
    });

    setSize(new Dimension(900, 480));

    UI.centerWindowToScreen(this);
  }

  private final void createUi() {
    Box box = VBox.create();

    int[] rows = { ModernWidget.WIDGET_HEIGHT };
    int[] cols = { 100, 600, 80 };

    MatrixPanel matrixPanel = new MatrixPanel(rows, cols, ModernWidget.PADDING,
        ModernWidget.PADDING);

    matrixPanel.add(new ModernAutoSizeLabel("Public Id"));
    matrixPanel.add(new ModernTextBorderPanel(mPublicIdField));
    matrixPanel.add(mGenPublicIdButton);
    matrixPanel.add(new ModernAutoSizeLabel("Type"));
    matrixPanel.add(mTypeCombo);
    matrixPanel.addEmpty();
    matrixPanel.add(new ModernAutoSizeLabel("First Name"));
    matrixPanel.add(new ModernTextBorderPanel(mFirstNameField));
    matrixPanel.addEmpty();
    matrixPanel.add(new ModernAutoSizeLabel("Last Name"));
    matrixPanel.add(new ModernTextBorderPanel(mLastNameField));
    matrixPanel.addEmpty();
    matrixPanel.add(new ModernAutoSizeLabel("Affiliation"));
    matrixPanel.add(new ModernTextBorderPanel(mAffiliationField));
    matrixPanel.addEmpty();
    matrixPanel.add(new ModernAutoSizeLabel("Phone"));
    matrixPanel.add(new ModernTextBorderPanel(mPhoneField));
    matrixPanel.addEmpty();
    matrixPanel.add(new ModernAutoSizeLabel("Address"));
    matrixPanel.add(new ModernTextBorderPanel(mAddressField));
    matrixPanel.addEmpty();
    matrixPanel.add(new ModernAutoSizeLabel("Email"));
    matrixPanel.add(new ModernTextBorderPanel(mEmailField));
    matrixPanel.addEmpty();
    matrixPanel.add(new ModernAutoSizeLabel("Password"));
    matrixPanel.add(new ModernTextBorderPanel(mPassField));
    matrixPanel.addEmpty();
    matrixPanel.add(new ModernAutoSizeLabel("Salt"));
    matrixPanel.add(new ModernTextBorderPanel(mSaltField));
    matrixPanel.add(mGenSaltButton);
    matrixPanel.add(new ModernAutoSizeLabel("Key"));
    matrixPanel.add(new ModernTextBorderPanel(mKeyField));
    matrixPanel.add(mGenKeyButton);
    box.add(matrixPanel);

    setCard(box);
  }

  public String getFirstName() {
    return mFirstNameField.getText();
  }

  public String getLastName() {
    return mLastNameField.getText();
  }

  public String getAffiliation() {
    return mAffiliationField.getText();
  }

  public String getPhone() {
    return mPhoneField.getText();
  }

  public String getAddress() {
    return mAddressField.getText();
  }

  public String getEmail() {
    return mEmailField.getText();
  }

  public String getPublicId() {
    return mPublicIdField.getText();
  }

  public String getPassword() {
    return mPassField.getText();
  }

  public String getKey() {
    return mKeyField.getText();
  }

  public int getPersonType() {
    return mTypeCombo.getSelectedIndex(); // + 2;
  }

  public String getSalt() {
    return mSaltField.getText();
  }

  public String getApiKey() {
    return mKeyField.getText();
  }
}
