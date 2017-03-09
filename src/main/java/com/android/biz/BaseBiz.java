package com.android.biz;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import com.android.bean.BaseObject;

/**
 * 鼠标动作的处理类基类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseBiz extends BaseObject implements MouseListener,
		MouseWheelListener, MouseMotionListener, DropTargetListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void dragEnter(DropTargetDragEvent dtde) {

	}

	public void dragOver(DropTargetDragEvent dtde) {

	}

	public void dropActionChanged(DropTargetDragEvent dtde) {

	}

	public void dragExit(DropTargetEvent dte) {

	}

	public void drop(DropTargetDropEvent dtde) {

	}
}
