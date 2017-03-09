package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.ui.IDrawEditable;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

public class CutAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public CutAction(IDrawEditable editor) {
		super(ResourceUtil.cut_icon, "剪切（X）", editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击 剪切动作");
		Application.drawPaneClipBoard.cut(editor);
		this.editor.firePropertyChange("firePropertyChangeEditAction", true,
				false);
	}

	@Override
	public boolean isEnabled() {
		return Application.drawPaneClipBoard.canCut(editor);
	}

}
