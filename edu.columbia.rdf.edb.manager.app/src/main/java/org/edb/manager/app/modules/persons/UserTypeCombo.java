package org.edb.manager.app.modules.persons;

import org.abh.common.ui.combobox.ModernComboBox;

public class UserTypeCombo extends ModernComboBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public UserTypeCombo() {
		addMenuItem("Normal");
		addMenuItem("Administrator");
		addMenuItem("Superuser");
	}
}
