package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import com.android.bean.ui.IDrawEditable;
import com.android.gui.action.app.BaseAction;

public class DownKeyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDrawEditable editor;
	public DownKeyAction(IDrawEditable editor) {
		super("down");
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		double y = this.editor.getCurrentSelectShape().getY();
		y += 1;
		this.editor.getCurrentSelectShape().setY(y);
		this.editor.repaint();
	}

	@Override
	public boolean isEnabled() {
		return this.editor.getCurrentSelectShape() != null;
	}

}
