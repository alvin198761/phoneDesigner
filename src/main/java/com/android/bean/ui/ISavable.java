package com.android.bean.ui;

import java.io.File;

import com.android.bean.IBean;

public interface ISavable extends IBean {
	/**
	 * 是否保存
	 * @return
	 */
	boolean isSaved();
	/**
	 * 执行保存
	 */
	void doSave();
	/**
	 * 设置保存标志位
	 * @param saved
	 */
	void setSaved(boolean saved);
	/**
	 * 从文件中读取数据来设置属性
	 * @param file
	 */
	void doRead(File file);

}
