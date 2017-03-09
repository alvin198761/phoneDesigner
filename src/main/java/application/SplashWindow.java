package application;

import com.android.gui.dialog.select.SelectWorkSpaceDialog;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;
import com.android.utils.DialogUtil;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class SplashWindow extends JWindow {

	private static final Logger log = Logger.getLogger(SplashWindow.class);

	private static final long serialVersionUID = 3973547078533132832L;

	private JProgressBar bar;

	private JLabel imageLabel;

	public SplashWindow() {
		Icon image = ResourceUtil.bg_icon;
		setIconImage(ResourceUtil.logo_img);
		int w = image.getIconWidth();
		int h = image.getIconHeight();
		setSize(new Dimension(w, h + 8));
		getContentPane().setLayout(new BorderLayout());

		bar = new JProgressBar();
		bar.setStringPainted(true);
		bar.setString("启动  Android Designer...");
		bar.setValue(0);

		getContentPane().add(bar, BorderLayout.SOUTH);

		imageLabel = new JLabel();
		imageLabel.setIcon(image);
		getContentPane().add(imageLabel, BorderLayout.CENTER);
		setLocationRelativeTo(null);

		/*
		 * 显示工作目录选择框体
		 */
		setVisible(true);
		if (Application.workSpace == null) {
			SelectWorkSpaceDialog dialog = new SelectWorkSpaceDialog();
			dialog.setAlwaysOnTop(true);
			dialog.setLocationRelativeTo(this);
			dialog.setVisible(true);
			while (!dialog.isDirectIsCrete()) {
				dialog.setVisible(false);
				DialogUtil.promptError("该位置无法创建工作目录!");
				dialog.setVisible(true);
				dialog.setAlwaysOnTop(true);
			}
			setAlwaysOnTop(true);
		}
	}

	/**
	 * 更新启动画面
	 * 
	 * @param info
	 */
	public void updateInfor(String info, int addValue) {
		bar.setString(info);
		if (bar.getValue() + addValue <= 100) {
			bar.setValue(bar.getValue() + addValue);
		} else {
			bar.setValue(100);
		}
	}

	/**
	 * 更新启动画面
	 * 
	 * @param info
	 *            字符串
	 * @param time
	 *            睡眠时间
	 * @param addValue
	 *            增加多少
	 */

	public void updateInfor(String info, int time, int addValue) {
		// 启动开关 是否进行睡眠
		updateInfor(info, addValue);
		repaint();
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug(info);
	}
}