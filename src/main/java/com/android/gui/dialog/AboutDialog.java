package com.android.gui.dialog;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JTextPane;

import com.android.lang.Application;

/**
 * 关于
 * 
 * @author issuser
 * 
 */
public class AboutDialog extends BaseDailog {

	private static final long serialVersionUID = 1L;

	public AboutDialog() {
		setSize(400, 300);
		setTitle("关于");
		mainPanel.setLayout(new BorderLayout(10, 0));
//		mainPanel.add(BorderLayout.WEST, new JLabel(new ImageIcon(
//				ResourceUtil.myface_img)));
		JTextPane text = new JTextPane();
		text.setText( "About me  \n 姓名：唐植超\nQQ:2273410177\nMail:alvin198761@163.com" );
		text.setEditable(false);
		mainPanel.add(text);
		setLocationRelativeTo(Application.mainFrame);
		buttonPanel.add(new JButton(closeAction));
	}

}
