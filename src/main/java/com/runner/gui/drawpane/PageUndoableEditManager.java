package com.runner.gui.drawpane;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

import com.android.bean.BaseObject;
/**
 * 用来做上一页下一页效果
 * @author Administrator
 *
 */
public class PageUndoableEditManager extends BaseObject {

	public static String operActionPropertyChange = "UndoRedoActionPropertyChange";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Edit保存对象
	 */
	protected UndoableEditSupport undoSupport = new UndoableEditSupport();
	/**
	 * 撤销恢复管理器
	 */
	protected UndoManager undoManager = new UndoManager();

	public PageUndoableEditManager() {
		undoSupport.addUndoableEditListener(undoManager);
	}

	/**
	 * 删除所有Edit节点
	 */
	public void reset() {
		undoManager.discardAllEdits();
	}

	/**
	 * 提交Undo
	 * 
	 * @param edit
	 */
	public void post(UndoableEdit edit) {
		undoSupport.postEdit(edit);
	}

	public void undo() {
		if (canUndo()) {
			undoManager.undo();
		}
	}

	public void redo() {
		if (canRedo()) {
			undoManager.redo();
		}
	}

	public boolean canRedo() {
		return undoManager.canRedo();
	}

	public boolean canUndo() {
		return undoManager.canUndo();
	}
}
