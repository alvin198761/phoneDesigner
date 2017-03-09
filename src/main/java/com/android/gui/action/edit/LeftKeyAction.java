package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import com.android.bean.ui.IDrawEditable;
import com.android.gui.action.app.BaseAction;

public class LeftKeyAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDrawEditable editor;

	public LeftKeyAction(IDrawEditable editor) {
		super("left");
		putValue(Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0));
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		double x = this.editor.getCurrentSelectShape().getX();
		x -= 1;
		this.editor.getCurrentSelectShape().setX(x);
		this.editor.repaint();
	}

	@Override
	public boolean isEnabled() {
		return this.editor.getCurrentSelectShape() != null;
	}

}
