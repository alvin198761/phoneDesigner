package com.android.gui.layout;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;

/**
 * 左右布局
 * 
 * @author Administrator
 * 
 */
public class Left_RightLayoutPanel {

	/**
	 * 
	 */
	private JSplitPane splitPanel;

	public Left_RightLayoutPanel(JSplitPane splitPanel) {
		this.splitPanel = splitPanel;

	}

	public void doChangeLayout() {
		this.splitPanel.setDividerSize(15);
		this.splitPanel.removeAll();
		this.splitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		this.splitPanel.setDividerLocation(250);
		splitPanel.setLeftComponent(CenterPanelManager.shapeSplitPanel);
		splitPanel.setRightComponent(CenterPanelManager.mainPanel);
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
				BorderLayout.EAST);
		//
		CenterPanelManager.operationSplitPanel
				.setDividerLocation(Application.mainFrame.getHeight() - 200);
		CenterPanelManager.operationSplitPanel.updateUI();
		//
		splitPanel.updateUI();
	}

	private int leftLocation = 250;

	public void doHideLeft() {
		leftLocation = this.splitPanel.getDividerLocation();
		this.splitPanel.setLeftComponent(null);
		this.splitPanel.setDividerLocation(0);
		this.splitPanel.updateUI();

		// this.splitPanel.setContinuousLayout(false);
	}

	public void doShowLeft() {
		// TODO Auto-generated method stub
		this.splitPanel.setDividerLocation(leftLocation);
		splitPanel.setLeftComponent(CenterPanelManager.shapeSplitPanel);
		splitPanel.setRightComponent(CenterPanelManager.mainPanel);
		this.splitPanel.updateUI();

		// this.splitPanel.setContinuousLayout(true);
	}

	private int bottomLocation = 550;

	public void doHideBottom() {
		bottomLocation = CenterPanelManager.operationSplitPanel
				.getDividerLocation();
		CenterPanelManager.operationSplitPanel.setBottomComponent(null);
		this.splitPanel.setLastDividerLocation(0);
		this.splitPanel.updateUI();
	}

	public void doShowBottom() {
		CenterPanelManager.operationSplitPanel
				.setBottomComponent(CenterPanelManager.console);
		CenterPanelManager.operationSplitPanel
				.setDividerLocation(bottomLocation);
		CenterPanelManager.operationSplitPanel.updateUI();

	}

	public void doHideRight() {
		CenterPanelManager.mainPanel.remove(CenterPanelManager.propertyPanel);
		CenterPanelManager.mainPanel.updateUI();
	}

	public void doShowRight() {
		CenterPanelManager.mainPanel.add(CenterPanelManager.propertyPanel,
				BorderLayout.EAST);
		CenterPanelManager.mainPanel.updateUI();
	}

}
