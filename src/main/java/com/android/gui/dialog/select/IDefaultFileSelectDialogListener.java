package com.android.gui.dialog.select;

import java.io.File;

/**
 * 当使用文件选择窗体的时候,必须继承此接口 实现统一的出口
 * 
 * @author viki
 * 
 */
public interface IDefaultFileSelectDialogListener {
	/**
	 * 获取最终用户选择的文件 通过 过滤器筛选过,所以 必将返回加好后缀的
	 * 
	 * @return null == 未选择
	 */
	File getFinalSelectFile();

	/**
	 * 获取最终用户选择的文件们 通过 过滤器筛选过,所以 必将返回加好后缀的
	 * 
	 * @return null == 未选择
	 */
	File[] getFinalSelectFiles();

	/**
	 * 获取最终用户选择的文件路径 不建议使用
	 * 
	 * @return null == 未选择
	 */
	@Deprecated
	String getFinalSelectFilePath();

	/**
	 * 获取最终用户选择的文件路径 不建议使用
	 * 
	 * @return null == 未选择
	 */
	@Deprecated
	String getFinalSelectFilePaths();
}
