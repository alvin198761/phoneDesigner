package com.android.gui.drawpane;

import java.util.List;

import javax.swing.undo.AbstractUndoableEdit;

import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.lang.Application;

/**
 * 画图板Edit,用于undo
 * 
 * @author Administrator
 * 
 */
public class DrawPaneEdit extends AbstractUndoableEdit {

	private static final long serialVersionUID = 1L;
	protected DrawPane panel;
	/**
	 * 当前数据
	 */
	protected List<BaseAndroidComponent> allShapes;
	/**
	 * 备份数据
	 */
	protected List<BaseAndroidComponent> backAllShapes;

	public DrawPaneEdit(DrawPane panel) {
		this.panel = panel;
		try {
			allShapes = Application.dataio.cloneComponents(panel.getEditor().getComponents());
//			allShapes = (ArrayList<BaseAndroidComponent>) ObjectUtil
//					.cloneObject(panel.getEditor().getComponents());
			// clearSelectShape(allShapes);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void undo() {
		super.undo();
		try {
			backAllShapes =  Application.dataio.cloneComponents(panel.getEditor().getComponents());
//			backAllShapes = (ArrayList<BaseAndroidComponent>) ObjectUtil
//					.cloneObject(panel.getEditor().getComponents());
			// clearSelectShape(backAllShapes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		doExecute(allShapes);
	}

	//
	// private void clearSelectShape(ArrayList<BaseAndroidComponent> shapes) {
	// for(BaseAndroidComponent comp : shapes){
	// comp.setSelect(false);
	// }
	// }

	/**
	 * @wbp.parser.entryPoint
	 */
	public void redo() {
		super.redo();
		doExecute(backAllShapes);
	}

	private void doExecute(List<BaseAndroidComponent> shapes) {
		panel.getEditor().removeAllShapes();
		for (BaseAndroidComponent shape : shapes) {
			panel.getEditor().addUndoRedoShape(shape);
			shape.setSelect(false);
		}
		panel.getEditor().repaint();
	}
}
