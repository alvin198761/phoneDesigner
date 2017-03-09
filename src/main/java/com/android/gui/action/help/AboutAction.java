package com.android.gui.action.help;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import com.android.gui.action.app.BaseAction;
import com.android.gui.dialog.AboutDialog;

public class AboutAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private AboutDialog dialog = new AboutDialog();

	public AboutAction() {
		super("关于（B）");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift B"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.setVisible(true);
	}

}
