package org.edb.manager.app.modules;

import java.util.List;

import org.abh.common.ui.event.ModernSelectionListener;
import org.abh.common.ui.scrollpane.ModernScrollPane;
import org.abh.common.ui.tabs.BlockVertTabs;
import org.abh.common.ui.tabs.TabsModel;
import org.abh.common.ui.widget.ModernWidget;

public class ModulesPanel extends ModernWidget {
	private static final long serialVersionUID = 1L;
	
	//private ModuleList mList = new ModuleList();
	//private ModuleListModel mListModel = new ModuleListModel();
	
	public ModulesPanel(TabsModel tabsModel) {
		
		
		BlockVertTabs tabs = new BlockVertTabs(tabsModel, 40);
		
		///mListModel.addValues(modules);
		
		//mList.setModel(mListModel);
		
		
		setBody(tabs);
		
		setBorder(RIGHT_BORDER);
		
		//mList.setSelectedIndex(0);
		
		tabsModel.changeTab(0);
	}
	
	//public void addSelectionListener(ModernSelectionListener l) {
	//	mList.addSelectionListener(l);
	//}

	//public Module getSelectedModule() {
	//	return mList.getSelectedItem();
	//}
}
