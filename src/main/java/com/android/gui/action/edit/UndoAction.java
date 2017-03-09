package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.ui.IDrawEditable;
import com.android.reource.ResourceUtil;

public class UndoAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public UndoAction(IDrawEditable editor) {
		super(ResourceUtil.undo_icon, "撤销（U）", editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Z"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击撤销动作");
		editor.getDrawPanel().undo();
		editor.firePropertyChange(IDrawEditable.firePropertyChangeEditAction,
				true, false);
	}

	@Override
	public boolean isEnabled() {
		return editor.getDrawPanel() != null && editor.getDrawPanel().canUndo();
	}

}
