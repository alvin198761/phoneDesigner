package com.android.gui.frame;

import javax.swing.JFrame;

import com.android.lang.Application;
import com.android.reource.ResourceUtil;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 700);
		setLocationRelativeTo(null);
	}

	public void initGui() {
		setIconImage(ResourceUtil.logo_img);
		setTitle("Android Designer v "+Application.version);
		// 菜单
		setJMenuBar(Application.menuBar);
		setContentPane(Application.contentPane);
	}

}
