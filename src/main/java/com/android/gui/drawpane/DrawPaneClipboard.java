package com.android.gui.drawpane;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.LinkedList;
import java.util.List;

import javax.activation.DataHandler;

import com.android.bean.shape.BaseShape;
import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.lang.Application;
import com.android.utils.Log;

public class DrawPaneClipboard {

	/**
	 * 画图板内置粘贴板
	 */
	private Clipboard board = new Clipboard("DrawPanel");
	/**
	 * 剪贴板支持的数据类型
	 */
	public static DataFlavor SHAPE_LIST_FLAVOR;

	static {
		try {
			SHAPE_LIST_FLAVOR = new DataFlavor(
					"application/x-java-serialized-object; class = java.util.LinkedList");
		} catch (Exception e) {
			System.out.print("粘贴板载入失败!");
		}
	}

	public DrawPaneClipboard() {
	}

	public void copy(IDrawEditable editor) {
		DataHandler data = new DataHandler(copyBaseDataShapeList(
				editor.getSelectComponent(), 0),
				"application/x-java-serialized-object; class = java.util.ArrayList");
		board.setContents(data, null);
		editor.setSaved(false);
	}

	/**
	 * 深度拷贝
	 * 
	 * @param list
	 * @return
	 */
	private List<BaseAndroidComponent> copyBaseDataShapeList(
			List<BaseAndroidComponent> list, int trans) {
		List<BaseAndroidComponent> result = new LinkedList<BaseAndroidComponent>();
		for (BaseAndroidComponent shape : list) {
			BaseAndroidComponent copyShape;
			try {
				copyShape = (BaseAndroidComponent) Application.dataio.cloneComponent(shape);
				copyShape.setX(shape.getX() + trans);
				result.add(copyShape);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public void paste(IDrawEditable editor) {
		Transferable value = board.getContents(SHAPE_LIST_FLAVOR);
		if (value == null) {
			return;
		}
		try {
			if (!(value.getTransferData(SHAPE_LIST_FLAVOR) instanceof LinkedList)) {
				return;
			}
			// undoredo
			editor.getDrawPanel().postUndoRedoEdit();
			// 获取数据
			List<BaseAndroidComponent> list = (LinkedList<BaseAndroidComponent>) value
					.getTransferData(SHAPE_LIST_FLAVOR);
			list = (LinkedList<BaseAndroidComponent>) copyBaseDataShapeList(
					list, 15);
			// // 删除原来是图形
			for (BaseShape shape : editor.getComponents()) {
				shape.setSelect(false);
			}
			// // 添加新的 图形
			editor.clearSelect();
			editor.getDrawPanel().reDraw();
			editor.addDraw(list);
			editor.setSaved(false);
			editor.repaint();
			copy(editor);
		} catch (Exception e1) {
			e1.printStackTrace();
			Log.info("粘贴板数据读取失败!");
		}
	}

	public void cut(IDrawEditable editor) {
		DataHandler data = new DataHandler(copyBaseDataShapeList(
				editor.getSelectComponent(), 0),
				"application/x-java-serialized-object; class = java.util.ArrayList");
		board.setContents(data, null);
		// editor.getDrawPanel().postUndoRedoEdit();
		editor.removeSelectShape();
		editor.repaint();
		editor.setSaved(false);
	}

	public boolean canCopy(IDrawEditable editor) {
		if (!editor.isOpen()) {
			return false;
		}
		for (BaseDataShape shape : editor.getComponents()) {
			if (shape.canCopy()) {
				return true;
			}
		}
		return false;
	}

	public boolean canPaste(IDrawEditable editor) {
		if (!editor.isOpen()) {
			return false;
		}
		Transferable value = board.getContents(SHAPE_LIST_FLAVOR);
		if (value != null) {
			try {
				return (value.getTransferData(SHAPE_LIST_FLAVOR) instanceof LinkedList);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public boolean canCut(IDrawEditable editor) {
		return canCopy(editor);
	}

}
