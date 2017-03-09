package com.android.gui.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.gui.action.edit.BaseEditAction;
import com.android.reource.ResourceUtil;

public class SavePageAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public SavePageAction(AndroidPageContainer editor) {
		super(ResourceUtil.save_Icon, "保存视图（S）", editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击保存页面 "+ this.editor.toString());
		editor.doSave();
		editor.setSaved(true);
		firePropertyChange();
	}

	@Override
	public boolean isEnabled() {
		return !editor.isSaved();
	}

}
