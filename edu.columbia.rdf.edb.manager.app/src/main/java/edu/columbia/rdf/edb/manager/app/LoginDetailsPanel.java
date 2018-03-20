package edu.columbia.rdf.edb.manager.app;

import java.io.UnsupportedEncodingException;

import org.jebtk.modern.panel.MatrixPanel;
import org.jebtk.modern.text.ModernTextBorderPanel;
import org.jebtk.modern.text.ModernTextField;
import org.jebtk.modern.text.ModernWhiteLabel;

import edu.columbia.rdf.edb.manager.app.tools.EDBLogin;

public class LoginDetailsPanel extends MatrixPanel {
  private static final long serialVersionUID = 1L;

  private static final int[] ROWS = { WIDGET_HEIGHT };
  private static final int[] COLS = { 80, 400 };

  private ModernTextField mServerField = new LoginTextField();
  private ModernTextField mDbField = new LoginTextField();
  private ModernTextField mUserField = new LoginTextField();
  private ModernTextField mPasswordField = new LoginTextField();

  public LoginDetailsPanel(EDBLogin login) {
    super(ROWS, COLS, PADDING, PADDING);

    // serverField.setEditable(false);
    // portField.setEditable(false);
    // userField.setEditable(false);
    // passwordField.setEditable(false);

    // mServerField.setBorder(BORDER);
    // mServerField.setOpaque(false);

    mPasswordField.setBorder(BORDER);

    add(new ModernWhiteLabel("Server"));
    add(new ModernTextBorderPanel(mServerField));
    add(new ModernWhiteLabel("Database"));
    add(new ModernTextBorderPanel(mDbField));
    add(new ModernWhiteLabel("User"));
    add(new ModernTextBorderPanel(mUserField));
    add(new ModernWhiteLabel("Password"));
    add(new ModernTextBorderPanel(mPasswordField));

    mServerField.setText(login.getServer());
    mDbField.setText(login.getDb());
    mUserField.setText(login.getKey());
    mPasswordField.setText(login.getPassword());
  }

  public final EDBLogin getLoginDetails()
      throws NumberFormatException, UnsupportedEncodingException {
    return new EDBLogin(mServerField.getText(), mDbField.getText(),
        mUserField.getText(), mPasswordField.getText());
  }

  /*
   * public final void paintComponent(Graphics g) { Graphics2D g2 =
   * (Graphics2D)g;
   * 
   * g2.setColor(DialogButton.COLOR_2);
   * 
   * int x = 0;//ModernTheme.getInstance().getClass("widget").getInt("padding")
   * * 2;
   * 
   * g2.drawLine(x, 0, x, getHeight()); }
   */
}
