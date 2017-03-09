package com.android.gui.action.layout;

import java.awt.event.ActionEvent;

import javax.swing.JToggleButton;

import com.android.gui.action.app.BaseAction;
import com.android.gui.frame.CenterPanelManager;

public class HideBottomAction extends BaseAction {

	public HideBottomAction() {
		super("下边");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
//		CenterPanelManager.top_BottomLayoutPanel.doChangeLayout();
		JToggleButton btn = (JToggleButton) e.getSource();
		if (btn.isSelected()) {
			CenterPanelManager.left_RightLayoutPanel.doHideBottom();
		} else {
			CenterPanelManager.left_RightLayoutPanel.doShowBottom();

		}
	}

}
