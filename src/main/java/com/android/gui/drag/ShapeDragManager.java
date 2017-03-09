package com.android.gui.drag;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import com.android.bean.ui.IDrawEditable;
import com.android.gui.drawpane.DrawPane;

/**
 * 实现图形拖拽的类
 * 
 * 参考swing hacks
 * 
 * @author 唐植超
 * 
 */
public class ShapeDragManager {
	private DrawPane target;
	private IDrawEditable editor;
	private ShapeGhostGlassPane glass = null;
	private Component dragSource;
	private Map<Component, DragAction> dragMap = new HashMap<Component, DragAction>();

	public void setDragSource(Component dragSource) {
		this.dragSource = dragSource;
	}

	/**
	 * @param frame
	 *            参数frame为要实现拖拽的frame，frame中一定只能有一个DragManage对象 ， <br>
	 *            如果有多个，就会导致冲突。 <br>
	 *            如果该窗口要实现拖拽功能，必须有一个DragManage对象，<br>
	 * 
	 * @param target
	 *            拖拽目标
	 */
	public ShapeDragManager(IDrawEditable editor) {
		this.editor = editor;
		this.target = editor.getDrawPanel();
		target.setLayout(null);
		this.glass = new ShapeGhostGlassPane(target);
		this.canDrag(target);
	}

	/**
	 * 指定能拖拽的组件
	 * 
	 * 
	 * @param souce
	 *            被拖拽的组件
	 */
	public void canDrag(Component source) {
		DragAction action = null;
		//加入拖拽动作
		action = new DragAction(glass, editor);
		source.addMouseListener(action);
		source.addMouseMotionListener(action);
		dragSource = source;
		//保存拖拽和控件的关系，便于删除
		dragMap.put(source, action);
	}

	public void removeDrag(Component source) {
		DragAction action = dragMap.get(source);
		if (action != null) {
			source.removeMouseListener(action);
			source.removeMouseMotionListener(action);
			action = null;
			System.gc();
		}
	}

	/**
	 * 返回拖拽源对象
	 * 
	 * @return
	 */
	public Component getDragSource() {
		return dragSource;
	}

	public ShapeGhostGlassPane getGlass() {
		return glass;
	}

}
