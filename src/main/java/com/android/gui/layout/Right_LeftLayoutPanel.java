package com.android.gui.layout;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;

public class Right_LeftLayoutPanel {

	/**
	 * 
	 */
	private JSplitPane splitPanel;

	public Right_LeftLayoutPanel(JSplitPane splitPanel) {
		this.splitPanel = splitPanel;

	}

	public void doChangeLayout() {
		this.splitPanel.removeAll();
		this.splitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.splitPanel
				.setDividerLocation(Application.mainFrame.getWidth() - 250);
		splitPanel.setLeftComponent(CenterPanelManager.mainPanel);
		splitPanel.setRightComponent(CenterPanelManager.shapeSplitPanel);
		//
		CenterPanelManager.shapeSplitPanel
				.setOrientation(JSplitPane.VERTICAL_SPLIT);
		CenterPanelManager.shapeSplitPanel.setDividerLocation(250);
		CenterPanelManager.shapeSplitPanel.updateUI();
		//
		CenterPanelManager.mainPanel.removeAll();
		CenterPanelManager.mainPanel
				.add(CenterPanelManager.operationSplitPanel);
		CenterPanelManager.mainPanel.add(CenterPanelManager.propertyPanel,
				BorderLayout.WEST);
		//
		CenterPanelManager.operationSplitPanel
				.setDividerLocation(Application.mainFrame.getHeight() - 200);
		CenterPanelManager.operationSplitPanel.updateUI();
		//
		splitPanel.updateUI();
	}

}
