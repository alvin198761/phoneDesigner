package com.android.lang;

import java.io.File;

import javax.swing.UIManager;

import application.SplashWindow;

import com.android.reource.ResourceUtil;
import com.android.utils.Log;

public class SMMSystem {

	private SMMSystem() {
	}

	// 系统名称
	public static String sysName = "SMM";
	// 版本号
	public static String version = "V-0.2";
	// 系统作者
	public static String author = "Alvin";
	// 作者邮箱
	public static String mail = "alvin198761@163.com";
	// 官网地址
	public static String url = "http://www.mgxd.com";
	// 帮助文档的路径
	public static String helpPath = "help.html";
	// 关于的文件路径
	public static String about = "about.html";
	// 升级的服务器的ip
	public static String serverIp = "127.0.0.1";
	// 升级访问的端口
	public static String serverPort = "8761";
	// 系统的快捷键配置
	public static String hotKeyConfig = "hotkey.xml";
	// 跨操作系统的变量
	public static final String line = System.getProperty("line.separator");
	public static final String realPath = System.getProperty("user.dir");
	public static final String fileSeparator = System
			.getProperty("file.separator");
	// 系统配置文件
	public static final String lang_dir = realPath.concat(fileSeparator)
			.concat("lang").concat(fileSeparator);
	public static final String conf_dir = realPath.concat(fileSeparator)
			.concat("conf").concat(fileSeparator);
	// 启动时间
	private static long startTime = System.currentTimeMillis();
	// 虚拟机启动对象控制
	public static final InstanceControl instance = new InstanceControl();

	public static void initPath() {
		File f = new File(lang_dir);
		if (!f.exists()) {
			f.mkdirs();
			// 创建语言文件
			// Language.createLanguageFile();
		}
		f = new File(conf_dir);
		if (!f.exists()) {
			f.mkdirs();
			// 创建默认配置文件
			ConfigManager.createConfigFile();
		}
	}

	// 保存配置
	public static void saveConfig() {
		Log.info("save config");
		// Language.saveLanguage();  
	}

	// 加载配置
	public static void loadConfig() {
		initPath();
		Log.info("load config");
		ConfigManager.loadSystemConfig();
	}

	// 初始化系统
	public static void start() {
		// 加载语言
		// Language.loadLanguage();
		// 判断是否已经启动
		// if (SMMSystem.isRunning()) {
		// DialogUtil.promptError("SMM is running");
		// System.gc();
		// return;
		// }
		// 设置皮肤
		try {
			// "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 启动窗体
		final SplashWindow s = new SplashWindow();
		s.updateInfor("加载国际化文件", 100, 5);
		// // 加载资源
		ResourceUtil.loadResource();
		s.updateInfor("加载资源文件", 100, 5);
		// // 加载配置
		SMMSystem.loadConfig();
		s.updateInfor("加载资源配置", 100, 5);
		Application.start(s);
		// 是否需要监听用户的操作习惯
		userActionListener();
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					// 判断是否需要升级
					update();
					// 是否需要弹出广告
					advertising();
					// 一小时判断一次
					try {
						Thread.sleep(60 * 1000 * 60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();
	}

	public static boolean isRunning() {
		return instance.isRunning();
	}

	// 更新产品版本
	private static void update() {
		Log.info("是否需要更新产品");
	}

	// 打广告
	private static void advertising() {
		Log.info("是否需要打广告");
	}

	// 用户操作习惯 这个似乎很重要，我这里只是留好路子，目前没能力搞
	private static void userActionListener() {
		Log.info("是否需要监听用户操作习惯 ");
	}

	// 产生系统ID
	public static String getCreateTimeId() {
		return Long.toHexString(startTime++) + "";
	}

}
