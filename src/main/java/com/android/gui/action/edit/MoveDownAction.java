package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;

public class MoveDownAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;
	private BaseAndroidComponent comp;

	public MoveDownAction(IDrawEditable editor, BaseAndroidComponent comp) {
		super("下移", editor);
		this.comp = comp;
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_DOWN);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击 下移动作");
		ProjectEntity project = Application
				.getProjectByPage((AndroidPageContainer) editor);
		CenterPanelManager.moveDown(project, editor, comp);
		this.editor.firePropertyChange("firePropertyChangeEditAction", true,
				false);
	}

	@Override
	public boolean isEnabled() {
		return this.editor.getComponents().indexOf(comp) < this.editor
				.getComponents().size() - 1;
	}
}