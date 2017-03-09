package com.android.gui.layout;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;

/**
 * 上下布局
 * 
 * @author Administrator
 * 
 */
public class Top_BottomLayoutPanel {

	/**
	 * 
	 */
	private JSplitPane splitPanel;

	public Top_BottomLayoutPanel(JSplitPane splitPanel) {
		this.splitPanel = splitPanel;
	}

	public void doChangeLayout() {
		this.splitPanel.removeAll();
		splitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPanel.setDividerLocation(250);
		splitPanel.setTopComponent(CenterPanelManager.shapeSplitPanel);
		splitPanel.setBottomComponent(CenterPanelManager.mainPanel);
		//
		CenterPanelManager.shapeSplitPanel
				.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		CenterPanelManager.shapeSplitPanel
				.setDividerLocation(Application.mainFrame.getWidth() >> 1);
		CenterPanelManager.shapeSplitPanel.updateUI();
		//
		CenterPanelManager.mainPanel.removeAll();
		CenterPanelManager.mainPanel
				.add(CenterPanelManager.operationSplitPanel);
		CenterPanelManager.mainPanel.add(CenterPanelManager.propertyPanel,
				BorderLayout.EAST);
		//
		splitPanel.updateUI();
	}

}
