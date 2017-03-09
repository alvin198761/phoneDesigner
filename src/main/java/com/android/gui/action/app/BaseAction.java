package com.android.gui.action.app;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

public abstract class BaseAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public BaseAction(String text) {
		text = createByText(text);
		putValue(Action.NAME, text);
		putValue(Action.SHORT_DESCRIPTION, text);
	}

	public BaseAction(Icon icon, String text) {
		this(text);
		putValue(Action.LARGE_ICON_KEY, icon);
		putValue(Action.SMALL_ICON, icon);
	}

	private String createByText(String text) {
		return text;
	}

	public abstract void actionPerformed(ActionEvent e);

//	public boolean isEnabled() {
//		return true;
//	}

	public final void firePropertyChange() {
		firePropertyChange("enabled", true, false);
	}

}
