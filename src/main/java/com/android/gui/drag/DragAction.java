package com.android.gui.drag;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.android.bean.ui.IDrawEditable;

/**
 * 拖拽动作
 * 
 * @author 唐植超
 * 
 */
public class DragAction implements MouseMotionListener, MouseListener {

	private IDrawEditable targetComp;

	public DragAction(ShapeGhostGlassPane glassPane, IDrawEditable targetComp) {
		this.targetComp = targetComp;
	}

	public void mouseDragged(MouseEvent e) {
		targetComp.getDragBiz().mouseDragged(e);
		//System.out.println("1------");
	}

	public void mouseMoved(MouseEvent e) {
		targetComp.getDragBiz().mouseMoved(e);
		//System.out.println("2------");
	}

	public void mouseClicked(MouseEvent e) {
		targetComp.getDragBiz().mouseClicked(e);
		//System.out.println("3------");
	}

	public void mouseEntered(MouseEvent e) {
		targetComp.getDragBiz().mouseEntered(e);
		//System.out.println("4------");
	}

	public void mouseExited(MouseEvent e) {
		targetComp.getDragBiz().mouseExited(e);
		//System.out.println("5------");
	}

	public void mousePressed(MouseEvent e) {
		targetComp.getDragBiz().mousePressed(e);
		//System.out.println("6------");
	}

	public void mouseReleased(MouseEvent e) {
		targetComp.getDragBiz().mouseReleased(e);
		//System.out.println("7------");
	}
}