package com.android.gui.comp;

import java.awt.event.KeyEvent;

public abstract class JPropertiesTextField extends JNumberField {

	private static final long serialVersionUID = 1L;

	@Override
	protected void processKeyEvent(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (getText().trim().equals("")) {
				return;
			}
			changeText();
		}
		super.processKeyEvent(e);
	}

	public abstract void changeText();

}
