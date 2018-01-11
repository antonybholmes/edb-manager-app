package edu.columbia.rdf.edb.manager.app.modules.persons;

import org.jebtk.modern.combobox.ModernComboBox;

public class UserTypeCombo extends ModernComboBox {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public UserTypeCombo() {
    addMenuItem("Administrator");
    addMenuItem("Superuser");
    addMenuItem("Normal");
  }
}
