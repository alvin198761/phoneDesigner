package com.android.gui.action.project;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.gui.action.app.BaseAction;
import com.android.gui.dialog.WorkSpaceManegerMentDialog;
/**
 * 工作空间管理
 * @author Administrator
 *
 */
public class WorkspaceManagerAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkspaceManagerAction() {
		super("工作空间管理（W）");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl W M"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("工作空间管理");
		WorkSpaceManegerMentDialog dialog = new WorkSpaceManegerMentDialog();
		dialog.setVisible(true);
	}
}
