package com.android.gui.action.layout;

import java.awt.event.ActionEvent;

import javax.swing.JToggleButton;

import com.android.gui.action.app.BaseAction;
import com.android.gui.frame.CenterPanelManager;

/**
 * 隐藏右边
 * 
 * @author Administrator
 * 
 */
public class HideRightAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HideRightAction() {
		super("右边");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		CenterPanelManager.left_RightLayoutPanel.doChangeLayout();
		JToggleButton btn = (JToggleButton) e.getSource();
		if (btn.isSelected()) {
			CenterPanelManager.left_RightLayoutPanel.doHideRight();
		} else {
			CenterPanelManager.left_RightLayoutPanel.doShowRight();

		}
	}

}
