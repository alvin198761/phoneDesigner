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

/**
 * 控件上移一个节点
 * 
 * @author Administrator
 * 
 */
public class MoveUpAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;
	private BaseAndroidComponent comp;

	public MoveUpAction(IDrawEditable editor, BaseAndroidComponent comp) {
		super("上移", editor);
		this.comp = comp;
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_UP);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击 上移动作");
		ProjectEntity project = Application
				.getProjectByPage((AndroidPageContainer) editor);
		CenterPanelManager.moveUp(project, editor, comp);
		this.editor.firePropertyChange("firePropertyChangeEditAction", true,
				false);
	}

	@Override
	public boolean isEnabled() {
		return this.editor.getComponents().indexOf(comp) > 0;
	}
}
