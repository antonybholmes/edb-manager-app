package org.edb.manager.app;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import javax.swing.Box;

import org.abh.common.ui.BorderService;
import org.abh.common.ui.UI;
import org.abh.common.ui.event.ModernClickEvent;
import org.abh.common.ui.event.ModernClickListener;
import org.abh.common.ui.help.GuiAppInfo;
import org.abh.common.ui.panel.HBox;
import org.abh.common.ui.panel.ModernPanel;
import org.abh.common.ui.panel.VBox;
import org.abh.common.ui.text.ModernSplashTitleLabel;
import org.abh.common.ui.window.ModernSplashScreen;
import org.edb.manager.app.tools.EDBLogin;

import edu.columbia.rdf.edb.ui.login.LoginButton;


public class ManagerLoginDialog extends ModernSplashScreen implements ModernClickListener {
	private static final long serialVersionUID = 1L;

	private LoginDetailsPanel mLoginPanel;


	public ManagerLoginDialog(EDBLogin login,
			GuiAppInfo appInfo) {
		super(appInfo);
		
		setSize(640, 480);
		//setResizable(false);

		createUi(login);
	}

	public final void createUi(EDBLogin login) {

		ModernPanel content = new ModernPanel(ModernSplashScreen.COLOR);

		VBox vBox = VBox.create();

		vBox.setBorder(BorderService.getInstance().createBorder(30));

		vBox.add(new ModernSplashTitleLabel(getAppInfo().getName())); //getAppInfo().getName()));

		content.setHeader(vBox);
		
		vBox = VBox.create();

		vBox.setBorder(BorderService.getInstance().createBorder(30));

		//content.add(Ui.createVerticalGap(30));

		mLoginPanel = new LoginDetailsPanel(login);
		
		vBox.add(mLoginPanel);

		vBox.add(UI.createVGap(10));

		//box.add(Box.createHorizontalGlue());

		Box box = HBox.create();

		LoginButton button = new LoginButton();
		
		button.addClickListener(this);

		box.add(UI.createHGap(85));
		box.add(button);
		//box.add(Box.createHorizontalGlue());
		//box.add(mCacheButton);

		vBox.add(box);

		content.setBody(new ModernPanel(vBox));
		
		setBody(content);

		UI.centerWindowToScreen(this);


	}

	public final void clicked(ModernClickEvent e) {
		if (e.getMessage().equals(UI.BUTTON_SIGN_IN)) {
			
			try {
				EDBLogin login = mLoginPanel.getLoginDetails();
				
				MainManagerWindow mWindow = new MainManagerWindow(login);
				
				mWindow.setVisible(true);
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
			close();
		} else if (e.getMessage().equals(UI.MENU_CLOSE)) {
			close();
		} else {
			// do nothing
		}
	}
}
