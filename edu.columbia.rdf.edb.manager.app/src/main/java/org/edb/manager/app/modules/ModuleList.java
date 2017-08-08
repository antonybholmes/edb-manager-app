package org.edb.manager.app.modules;

import org.abh.common.ui.list.ModernList;

public class ModuleList extends ModernList<Module> {

	private static final long serialVersionUID = 1L;

	public ModuleList() {
		setCellRenderer(new ModuleListRenderer());
		
		setDragReorderEnabled(true);
		
		setRowHeight(36);
	}
}
