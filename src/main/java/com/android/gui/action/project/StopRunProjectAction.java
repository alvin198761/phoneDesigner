package com.android.gui.action.project;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import com.android.bean.entity.ProjectEntity;
import com.android.gui.action.app.BaseAction;

public class StopRunProjectAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProjectEntity project;
	
	public StopRunProjectAction(ProjectEntity project) {
		super("停止");
		this.project = project;
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl f11"));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		project.firePropertyChange("stopRunning", true, false);
	}

	@Override
	public boolean isEnabled() {
		return project.isRunning();
	}
}
