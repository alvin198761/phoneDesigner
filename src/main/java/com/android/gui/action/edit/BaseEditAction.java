package com.android.gui.action.edit;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import com.android.bean.ui.IDrawEditable;
import com.android.gui.action.app.BaseAction;

public abstract class BaseEditAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected IDrawEditable editor;

	public BaseEditAction(String text, IDrawEditable editor) {
		super(text);
		this.editor = editor;
	}

	public BaseEditAction(Icon icon, String text, IDrawEditable editor) {
		super(icon, text);
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	protected void canSave() {
		editor.setSaved(false);
		editor.firePropertyChange(IDrawEditable.firePropertyChangeEditAction,
				true, false);
	}

}
