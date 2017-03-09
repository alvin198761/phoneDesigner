package com.android.gui.drawpane;

import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import com.android.bean.ui.IDrawEditable;
import com.android.bean.ui.ProjectTreeNode;
import com.android.gui.action.app.BaseAction;
import com.android.gui.action.edit.DownKeyAction;
import com.android.gui.action.edit.LeftKeyAction;
import com.android.gui.action.edit.RightKeyAction;
import com.android.gui.action.edit.UpKeyAction;
import com.android.gui.frame.CenterPanelManager;

public class DrawPaneHelper implements MouseListener, MouseMotionListener,
		MouseWheelListener, DropTargetListener {

	private IDrawEditable editor;

	protected DrawPaneHelper(DrawPane panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
		// panel.addMouseWheelListener(this);
		editor = panel.getEditor();
		if (editor instanceof ProjectTreeNode) {
			// 追加快捷键
			ProjectTreeNode node = (ProjectTreeNode) editor;
			for (BaseAction action : node.getActions()) {
				panel.getInputMap().put(
						(KeyStroke) action.getValue(Action.ACCELERATOR_KEY),
						action.getValue(Action.NAME));
				panel.getActionMap().put(action.getValue(Action.NAME), action);
			}
			// up
			UpKeyAction upKeyAction = new UpKeyAction(editor);
			panel.getInputMap().put(
					(KeyStroke) upKeyAction.getValue(Action.ACCELERATOR_KEY),
					upKeyAction.getValue(Action.NAME));
			panel.getActionMap().put(upKeyAction.getValue(Action.NAME),
					upKeyAction);
			// down
			DownKeyAction downKeyAction = new DownKeyAction(editor);
			panel.getInputMap().put(
					(KeyStroke) downKeyAction.getValue(Action.ACCELERATOR_KEY),
					downKeyAction.getValue(Action.NAME));
			panel.getActionMap().put(downKeyAction.getValue(Action.NAME),
					downKeyAction);
			// left
			LeftKeyAction leftKeyAction = new LeftKeyAction(editor);
			panel.getInputMap().put(
					(KeyStroke) leftKeyAction.getValue(Action.ACCELERATOR_KEY),
					leftKeyAction.getValue(Action.NAME));
			panel.getActionMap().put(leftKeyAction.getValue(Action.NAME),
					leftKeyAction);
			// right
			RightKeyAction rightKeyAction = new RightKeyAction(editor);
			panel.getInputMap()
					.put((KeyStroke) rightKeyAction
							.getValue(Action.ACCELERATOR_KEY),
							rightKeyAction.getValue(Action.NAME));
			panel.getActionMap().put(rightKeyAction.getValue(Action.NAME),
					rightKeyAction);
		}
		new DropTarget(panel, DnDConstants.ACTION_COPY_OR_MOVE, this);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		editor.getDrawPaneBiz().mouseWheelMoved(e);
	}

	public void mouseDragged(MouseEvent e) {
		editor.getDrawPaneBiz().mouseDragged(e);
	}

	public void mouseMoved(MouseEvent e) {
		editor.getDrawPaneBiz().mouseMoved(e);
		Point p = e.getLocationOnScreen();
		SwingUtilities.convertPointFromScreen(p, this.editor.getDrawPanel());
		CenterPanelManager.setMouseLocation(p, editor.getDrawPanel());
	}

	public void mouseClicked(MouseEvent e) {
		editor.getDrawPaneBiz().mouseClicked(e);
	}

	public void mousePressed(MouseEvent e) {
		if (e.isMetaDown()) {
			return;
		}
		editor.getDrawPaneBiz().mousePressed(e);
	}

	private void showMenu(List<BaseAction> actions, Point point) {
		JPopupMenu menu = new JPopupMenu();
		for (Action action : actions) {
			menu.add(action);
		}
		menu.show(editor.getDrawPanel(), point.x, point.y);
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isMetaDown()) {
			if (editor.getCurrentSelectShape() != null) {
				showMenu(editor.getCurrentSelectShape().getActions(),
						e.getPoint());
			}
			return;
		}
		editor.getDrawPaneBiz().mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e) {
		editor.getDrawPaneBiz().mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		editor.getDrawPaneBiz().mouseExited(e);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		editor.getDrawPaneBiz().dragEnter(dtde);
	}

	public void dragOver(DropTargetDragEvent dtde) {
		editor.getDrawPaneBiz().dragOver(dtde);
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		editor.getDrawPaneBiz().dropActionChanged(dtde);
	}

	public void dragExit(DropTargetEvent dtde) {
		editor.getDrawPaneBiz().dragExit(dtde);
	}

	public void drop(DropTargetDropEvent dtde) {
		editor.getDrawPaneBiz().drop(dtde);
	}

}
