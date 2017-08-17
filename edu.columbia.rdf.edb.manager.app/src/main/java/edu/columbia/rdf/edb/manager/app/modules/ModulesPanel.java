package edu.columbia.rdf.edb.manager.app.modules;

import java.util.List;

import org.jebtk.modern.event.ModernSelectionListener;
import org.jebtk.modern.scrollpane.ModernScrollPane;
import org.jebtk.modern.tabs.BlockVertTabs;
import org.jebtk.modern.tabs.TabsModel;
import org.jebtk.modern.widget.ModernWidget;

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
