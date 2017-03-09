package com.android.bean.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.android.gui.drawpane.UndoableEditManager;

/**
 * 绘制控件必须实现的接口
 * @author Administrator
 *
 */
public interface IDrawComponent {
//	/**
//	 * 撤销恢复管理
//	 * @return
//	 */
//	UndoableEditManager getUndoManager();
	/**
	 * 内存图
	 * @return
	 */
	BufferedImage getImage();
	/**
	 * 渲染器
	 * @return
	 */
	Graphics2D getG2d();
}
