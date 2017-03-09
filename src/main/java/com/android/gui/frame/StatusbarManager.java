package com.android.gui.frame;

import javax.swing.Box;

import com.android.lang.Application;

public class StatusbarManager {

	private StatusbarManager() {
	}

	public static void createStatusBar() {
		Box box = Box.createHorizontalBox();
		Application.statusBox = box;
	}

}
