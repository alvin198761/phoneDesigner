package com.android.biz;

import java.awt.Cursor;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.TreePath;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.ShapeHelper;
import com.android.bean.shape.ctrl.CtrlResizeShape;
import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;
import com.android.utils.Log;

/**
 * 画图处理的父类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseDrawPanelBiz extends BaseBiz {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Point2D startPoint = new Point2D.Double();
	protected long startTime;
	protected Point2D mousePoint = new Point2D.Double();
	protected IDrawEditable editor;

	public BaseDrawPanelBiz(IDrawEditable editor) {
		this.editor = editor;
	}

	public void mouseEntered(MouseEvent e) {
		this.editor.getDrawPanel().requestFocus();
	}

	public void mouseExited(MouseEvent e) {
		this.editor.getDrawPanel().requestFocus(false);
	}

	protected BaseDataShape getLineHoverShape() {
		for (BaseDataShape shape : this.editor.getComponents()) {
			if (shape.isLineHover()) {
				return shape;
			}
		}
		return null;
	}

	public void mouseMoved(MouseEvent e) {
		if (e.isMetaDown()) {
			return;
		}
		if (this.editor.getCurrentSelectShape() == null) {
			this.editor.getDrawPanel().setCursor(Cursor.getDefaultCursor());
			return;
		}
		Rectangle2D rect = this.editor.getDrawPanel().getImageRect();
		Point2D p = new Point2D.Double();
		p.setLocation(e.getPoint().getX() - rect.getX(), e.getPoint().getY()
				- rect.getY());
		for (BaseAndroidComponent com : this.editor.getComponents()) {
			if (!com.isSelect()) {
				continue;
			}
			CtrlResizeShape resize = com.contansCtrlBox(p);
			if (resize == null) {
				continue;
			}
			ShapeHelper.setCursor(this.editor.getDrawPanel(), resize.getWay());
			return;
		}
		this.editor.getDrawPanel().setCursor(Cursor.getDefaultCursor());

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		// // 滚动鼠标，放大缩小
		// if (!e.isControlDown()) {
		// return;
		// }
		// double scale = this.editor.getDrawPanel().getScale();
		// if (scale <= .5 && scale >= 2) {
		// return;
		// }
		// int value = e.getWheelRotation();
		// if (value == 1) {
		// BaseAction action = ActionManager.getAction(ZoomInAction.class);
		// if (action.isEnabled()) {
		// action.actionPerformed(null);
		// }
		// }
		// if (value == -1) {
		// BaseAction action = ActionManager.getAction(ZoomOutAction.class);
		// if (action.isEnabled()) {
		// ActionManager.getAction(ZoomOutAction.class).actionPerformed(
		// null);
		// }
		// }
	}

	public void mouseClicked(MouseEvent e) {
		// if (e.isMetaDown()) {
		// return;
		// }
		// if (e.getClickCount() == 2
		// && this.editor.getCurrentSelectShape() != null
		// && this.editor.getCurrentSelectShape().isEditable()) {
		// //
		// ActionManager.getAction(PropertyAction.class).actionPerformed(null);
		// }
	}

	public void mouseDragged(MouseEvent e) {
		if (e.isMetaDown()) {
			return;
		}
		if (System.currentTimeMillis() - startTime < 100) {
			// 减少重新绘制的次数，
			return;
		}

		startTime = System.currentTimeMillis();
		do {
			Rectangle2D rect = this.editor.getDrawPanel().getImageRect();
			Point2D p = new Point2D.Double(e.getPoint().getX() - rect.getX(), e
					.getPoint().getY() - rect.getY());
			if (this.editor.getOperStatus().equals(Application.OPER_RESIZE)) {
				// 改变控件大小
				this.editor.getCurrentResizeShape().contrl(p);
				ShapeHelper.setCursor(this.editor.getDrawPanel(), this.editor
						.getCurrentResizeShape().getWay());
				this.editor.setSaved(false);
				// Log.info("改变控件大小");
				break;
			}
			// 选择框
			if (this.editor.getOperStatus().equals(
					Application.OPER_DRAWSELECTBOX)) {
				double x = Math.min(startPoint.getX(), e.getPoint().getX())
						- rect.getX();
				double y = Math.min(startPoint.getY(), e.getPoint().getY())
						- rect.getY();
				double w = Math.abs(startPoint.getX() - e.getPoint().getX());
				double h = Math.abs(startPoint.getY() - e.getPoint().getY());
				this.editor.getSelectBox().setRect(x, y, w, h);
				break;
			}
		} while (false);
		this.editor.repaint();
	}

	public void mousePressed(MouseEvent e) {
		if (e.isMetaDown()) {
			return;
		}
		Rectangle2D rect = this.editor.getDrawPanel().getImageRect();
		mousePoint.setLocation(e.getPoint().getX() - rect.getX(), e.getPoint()
				.getY() - rect.getY());
		startTime = System.currentTimeMillis();
		// 实现多选
		if (e.isControlDown()) {
			this.editor.setOperStatus(Application.OPER_MUILT_SELECT);
			Log.info("多选");
			List<BaseAndroidComponent> list = this.editor.getComponents();
			Iterator<BaseAndroidComponent> shapes = list.iterator();
			BaseAndroidComponent shape = null;
			// 讲选中的控件放入列表
			while (shapes.hasNext()) {
				shape = shapes.next();
				if (shape.isSelect()) {
					continue;
				}
				shape.setSelect(shape.contains(mousePoint));
			}
			this.editor.setCurrentSelectShape(shape);
			this.editor.repaint();
			return;
		}
		// 如果是多选状态，先检查有没有点击到已经选中的控件
		if (Application.OPER_MUILT_SELECT.equals(this.editor.getOperStatus())) {
			// //System.out.println(this.editor.getOperStatus()
			// +" eeeeeeeeeeeeeeeeeeee");
			List<BaseAndroidComponent> list = this.editor.getComponents();
			Iterator<BaseAndroidComponent> shapes = list.iterator();
			BaseAndroidComponent shape = null;
			// 判断当前点击的控件有没有在原来的列表中存在
			while (shapes.hasNext()) {
				shape = shapes.next();
				if (!shape.contains(mousePoint)) {
					continue;
				}
				this.editor.setCurrentSelectShape(shape);
				this.editor.repaint();
				if (this.editor.getSelectComponent().contains(shape)) {
					// //System.out.println(this.editor.getOperStatus()
					// +" ---- ");
					return;
				} else {
					this.editor.clearSelect();
					break;
				}
			}
		}
		// //System.out.println(this.editor.getOperStatus() +" xxxxxxxxxxxxx ");
		// 有没有点击到当前控制点上
		do {
			if (this.editor.getCurrentResizeShape() == null)
				break;
			if (!this.editor.getCurrentResizeShape().contains(mousePoint)) {
				this.editor.setCurrentResizeShape(null);
				break;
			}
			// 改变控件大小的状态
			this.editor.setOperStatus(Application.OPER_RESIZE);
			// Log.info("改变大小");
			return;
		} while (false);
		// //System.out.println(this.editor.getOperStatus()
		// +" yyyyyyyyyyyyyyyyyyyy ");
		// 有没有点击到当前的选中的图形上
		do {
			if (this.editor.getCurrentSelectShape() == null)
				break;
			this.editor.setCurrentResizeShape(this.editor
					.getCurrentSelectShape().contansCtrlBox(mousePoint));
			if (this.editor.getCurrentResizeShape() != null) {
				this.editor.setOperStatus(Application.OPER_RESIZE);
				return;
			}
			if (!this.editor.getCurrentSelectShape().contains(mousePoint)) {
				this.editor.setCurrentSelectShape(null);
				break;
			}
			// Log.info("选中图形");
			this.editor.getCurrentSelectShape().setSelect(true);
			this.editor.repaint();
			this.editor.setOperStatus(Application.OPER_DRAGSHAPE);
			// ActionManager.firePropertyChangeEditAction();
			return;
		} while (false);
		//System.out.println("1111111");
		this.editor.setOperStatus(Application.OPER_NONE);
		// Log.info("重新选中");
		// 都没有选中 就先清空所有的图形选中状态
		List<BaseAndroidComponent> list = this.editor.getComponents();
		this.editor.clearSelect();
		this.editor.firePropertyChange(
				IDrawEditable.firePropertyChangeEditAction, true, false);
		Iterator<BaseAndroidComponent> shapes = list.iterator();
		BaseAndroidComponent shape = null;
		while (shapes.hasNext()) {
			shape = shapes.next();
			shape.setSelect(shape.contains(mousePoint));
			if (shape.isSelect()) {
				// this.editor.getComponents().remove(shape);
				// this.editor.addShape(shape);

				this.editor.setCurrentSelectShape(shape);
				this.editor.repaint();
				if (!CenterPanelManager.isSelectedNode(shape)) {
					AndroidPageContainer page = (AndroidPageContainer) Application
							.getPageByComponent((BaseAndroidComponent) shape);
					ProjectEntity project = Application.getProjectByPage(page);
					TreePath treePath = new TreePath(new Object[] {
							Application.treeModel.getRoot(), project, page,
							shape

					});
					CenterPanelManager.selectNode(treePath);
				}
				// ActionManager.firePropertyChangeEditAction();
				//System.out.println("2222222");
				return;
			}
		}
		// 一个都没选中 开始画选择框
		startPoint.setLocation(e.getPoint().getX(), e.getPoint().getY());
		this.editor.setOperStatus(Application.OPER_DRAWSELECTBOX);
		this.editor.clearSelect();
		// ActionManager.firePropertyChangeEditAction();
		//System.out.println("333333");
	}

	public void drop(DropTargetDropEvent dtde) {
		Log.info("拖入文件");
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isMetaDown()) {
			// showPopuMenu(e.getPoint());
			return;
		}
		if (this.editor.getOperStatus().equals(Application.OPER_RESIZE)) {
			this.editor.getCurrentResizeShape().getCtrl().contrlLines();
			this.editor.repaint();
			this.editor.getCurrentSelectShape().getPropertiesComponent();
			//System.out.println("777777");
			return;
		}
		if (this.editor.getOperStatus().equals(Application.OPER_DRAWSELECTBOX)) {
			if (this.editor.getSelectBox().getWidth() > -1) {
				for (BaseDataShape shape : this.editor.getComponents()) {
					shape.setSelect(shape.intersects(this.editor.getSelectBox()));
					// this.editor.getMuiltSelectShapes().add(
					// (BaseAndroidComponent) shape);
				}
			}
			this.editor.getSelectBox().setRect(-1, -1, -1, -1);
			this.editor.repaint();
			// ActionManager.firePropertyChangeEditAction();
			// 如果选中了多个，就变成多选模式
			if (this.editor.getSelectComponent().size() > 1) {
				this.editor.setOperStatus(Application.OPER_MUILT_SELECT);
				//System.out.println("101010101");
				return;
			}
			//System.out.println("8888888");
			this.editor.setOperStatus(Application.OPER_NONE);
			return;
		}
		if (this.editor.getOperStatus().equals(Application.OPER_MUILT_SELECT)) {
			return;
		}
		this.editor.setOperStatus(Application.OPER_NONE);
		//System.out.println("9999");
	}

}
