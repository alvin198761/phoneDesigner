package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.ui.IDrawEditable;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

public class SelectAllAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public SelectAllAction(IDrawEditable editor) {
		super(ResourceUtil.selectAll_icon, "全选（A）", editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl A"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击全选动作");
		editor.selectAllShape();
		editor.setOperStatus(Application.OPER_SELECTALL);
		editor.firePropertyChange(IDrawEditable.firePropertyChangeEditAction,
				true, false);
	}

	@Override
	public boolean isEnabled() {
		return !editor.isSelectAll() && editor.isOpen();
	}

}
