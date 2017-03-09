package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.ui.IDrawEditable;
import com.android.reource.ResourceUtil;

public class RedoAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public RedoAction(IDrawEditable editor) {
		super(ResourceUtil.redo_icon, "恢复（R）", editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl Y"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击撤销动作");
		editor.getDrawPanel().redo();
		editor.firePropertyChange(IDrawEditable.firePropertyChangeEditAction,
				true, false);
	}

	@Override
	public boolean isEnabled() {
		// try {
		// System.out.println((editor.getDrawPanel() != null) + "---"
		// + (editor.getDrawPanel().canRedo()));
		// } catch (Exception e) {
		// }
		return editor.getDrawPanel() != null && editor.getDrawPanel().canRedo();
	}

}
