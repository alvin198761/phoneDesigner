package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.ui.IDrawEditable;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

public class PasteAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public PasteAction(IDrawEditable editor) {
		super(ResourceUtil.paste_icon, "粘贴（P）",editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击粘贴动作");
		Application.drawPaneClipBoard.paste(editor);
		editor.firePropertyChange(IDrawEditable.firePropertyChangeEditAction,
				true, false);
	}

	@Override
	public boolean isEnabled() {
		return Application.drawPaneClipBoard.canPaste(editor);
	}

}
