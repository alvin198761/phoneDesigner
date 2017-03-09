package com.android.gui.drawpane;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

import com.android.bean.BaseObject;
import com.android.bean.ui.IDrawEditable;
import com.android.lang.Application;

/**
 * undo管理
 * 
 * @author Administrator
 * 
 */
public class UndoableEditManager extends BaseObject {

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

	private IDrawEditable editor;

	public UndoableEditManager(IDrawEditable editor) {
		this.editor = editor;
		undoSupport.addUndoableEditListener(undoManager);
	}

	/**
	 * 删除所有Edit节点
	 */
	public void reset() {
		undoManager.discardAllEdits();
		firePropertyChange(operActionPropertyChange, true, false);
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
		undoManager.undo();
		editor.setCurrentResizeShape(null);
		editor.setCurrentSelectShape(null);
		editor.setOperStatus(Application.OPER_NONE);
		firePropertyChange(operActionPropertyChange, true, false);
	}

	public void redo() {
		undoManager.redo();
		editor.setCurrentResizeShape(null);
		editor.setCurrentSelectShape(null);
		editor.setOperStatus(Application.OPER_NONE);
		firePropertyChange(operActionPropertyChange, true, false);
	}

	public boolean canRedo() {
		return undoManager.canRedo() && editor.isOpen();
	}

	public boolean canUndo() {
		return undoManager.canUndo() && editor.isOpen();
	}
}