package edu.columbia.rdf.edb.manager.app.modules;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

import org.jebtk.core.text.TextUtils;
import org.jebtk.modern.list.ModernList;
import org.jebtk.modern.list.ModernListCellRenderer;
import org.jebtk.modern.theme.ThemeService;



/**
 * Renders a file as a list item.
 * 
 * @author Antony Holmes Holmes
 *
 */
public class ModuleListRenderer extends ModernListCellRenderer {
	private static final long serialVersionUID = 1L;
	private static final int ORB_WIDTH = 8;
	
	private String mText = "";
	private int mRow;

	public static final Color LINE_COLOR = 
			ThemeService.getInstance().colors().getHighlight(2);
	
	public static final Color COLOR = 
			ThemeService.getInstance().colors().getHighlight(6);
	
	
	private static final int NUM_WIDTH = 20;
	

	@Override
	public void drawForegroundAAText(Graphics2D g2) {
		int x = DOUBLE_PADDING;
		int x2 = getWidth() - ORB_WIDTH - DOUBLE_PADDING;
		
		//fill(g2, mFillColor);
		
		//g2.setColor(COLOR);
		g2.setColor(TEXT_COLOR);
		
		/*
		String t = mRow + ".";
		
		g2.drawString(t, 
				x + NUM_WIDTH - g2.getFontMetrics().stringWidth(t) - PADDING, 
				getTextYPosCenter(g2, getHeight()));
		
		x += NUM_WIDTH;

		
		*/
		
		int tw = x2 - x - PADDING;

		String t = TextUtils.EMPTY_STRING;
		
		// Keep truncating the text until it fits into the available space.
		for (int i = mText.length(); i >= 0; --i) {
			t = TextUtils.truncate(mText, i);

			if (g2.getFontMetrics().stringWidth(t) <= tw) {
				break;
			}
		}

		g2.drawString(t, x, getTextYPosCenter(g2, getHeight()));
	}

	@Override
	public Component getCellRendererComponent(ModernList<?> list,
			Object value,
			boolean highlight,
			boolean isSelected,
			boolean hasFocus,
			int row) {

		mText = ((Module)value).getName(); 
		mRow = row + 1;

		return super.getCellRendererComponent(list, 
				value, 
				highlight, 
				isSelected, 
				hasFocus, 
				row);
	}
}