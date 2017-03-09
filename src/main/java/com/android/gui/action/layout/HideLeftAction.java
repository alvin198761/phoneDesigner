package com.android.gui.action.layout;

import java.awt.event.ActionEvent;

import javax.swing.JToggleButton;

import com.android.gui.action.app.BaseAction;
import com.android.gui.frame.CenterPanelManager;

/**
 * 隐藏左边
 * 
 * @author Administrator
 * 
 */
public class HideLeftAction extends BaseAction {

	public HideLeftAction() {
		super("左边");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		// CenterPanelManager.right_LeftLayoutPanel.doChangeLayout();
		JToggleButton btn = (JToggleButton) e.getSource();
		if (btn.isSelected()) {
			CenterPanelManager.left_RightLayoutPanel.doHideLeft();
		} else {
			CenterPanelManager.left_RightLayoutPanel.doShowLeft();

		}
	}

}
