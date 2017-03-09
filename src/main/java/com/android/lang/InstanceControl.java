package com.android.lang;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileLock;

/**
 * 应用实例控制类
 * 
 * @author issuser
 * 
 */
public class InstanceControl {

	// 判断该应用是否已启动
	public boolean isRunning() {
		FileLock lock = null;
		FileOutputStream out = null;
		try {
			// 获得实例标志文件
			File flagFile = new File("smm");
			// 如果不存在就新建一个
			if (!flagFile.exists())
				flagFile.createNewFile();
			out = new FileOutputStream(flagFile);
			// 获得文件锁
			lock = out.getChannel().tryLock();
			// 返回空表示文件已被运行的实例锁定
			if (lock == null)
				return false;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}