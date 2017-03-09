package com.android.gui.dialog;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JTextPane;

import com.android.lang.Application;

public class HelpDailog extends BaseDailog {

	private static final long serialVersionUID = 1L;

	public HelpDailog() {
		setSize(600, 400);
		setTitle("帮助");
		mainPanel.setLayout(new BorderLayout(10, 0));
		JTextPane text = new JTextPane();
		text.setText("Help me");
		text.setEditable(false);
		mainPanel.add(text);
		setLocationRelativeTo(Application.mainFrame);
		buttonPanel.add(new JButton(closeAction));
	}
}
