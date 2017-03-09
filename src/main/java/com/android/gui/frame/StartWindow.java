package com.android.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import com.android.reource.ResourceUtil;
import com.android.utils.Log;

/**
 * 简单实现启动及退出时的等待界面
 * 
 */
public class StartWindow {
	private JFrame frame = new JFrame();
	private JProgressBar progressBar;

	public StartWindow() {
		frame.setIconImage(ResourceUtil.logo_img);
		frame.setTitle("Android Designer");
	}

	private void prepareSplash() {
		int imgWidth = ResourceUtil.bg_icon.getIconWidth();
		int imgHeight = ResourceUtil.bg_icon.getIconHeight();
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(.3f, .3f, .3f, .5f));
		progressBar.setBorder(null);
		// 去掉窗口上的标题框
		frame.setUndecorated(true);
		// 放入一个透明的面板
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(panel);
		// 将窗体设置为透明
		// AWTUtilities.setWindowOpaque(frame, false);
		JLabel l = new JLabel(ResourceUtil.bg_icon);
		l.setOpaque(false);
		panel.add(BorderLayout.CENTER, l);
		panel.add(BorderLayout.SOUTH, progressBar);
		panel.setPreferredSize(new Dimension(imgWidth, imgHeight));
		progressBar.setOpaque(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	/**
	 * 设置进度百分比，最大值100
	 * 
	 * @param percent
	 */
	public void setValue(int percent) {
		if (percent < 0 || percent > 100) {
			throw new RuntimeException("percent is invalid.");
		}
		progressBar.setValue(percent);
	}

	/**
	 * 调用入口
	 */
	public void startSplash() {
		prepareSplash();
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);
	}

	/**
	 * 结束
	 */
	public void stopSplash() {
		frame.setAlwaysOnTop(false);
		frame.dispose();
	}

	public void putProcessString(String value) {
		progressBar.setString(value);
		Log.info(value);
	}
}