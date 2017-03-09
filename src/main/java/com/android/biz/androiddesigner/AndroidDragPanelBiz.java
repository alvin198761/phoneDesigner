package com.android.biz.androiddesigner;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.ShapeHelper;
import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.biz.BaseDragBiz;
import com.android.gui.frame.BaseDrawComp;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;

public class AndroidDragPanelBiz extends BaseDragBiz {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AndroidDragPanelBiz(IDrawEditable editor) {
		super(editor);
	}

	protected void sourceToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect) {
		try {
			this.editor.getDrawPanel().postUndoRedoEdit();
			// 添加到目标位置
			BaseDataShape copyShape = ShapeHelper
					.createShapeFactory(androidComponents.get(0).getType());
			Point p2 = (Point) e.getPoint().clone();
			SwingUtilities.convertPointToScreen(p2, c);
			SwingUtilities.convertPointFromScreen(p2,
					this.editor.getDrawPanel());
			double x = p2.x - copyShape.getW() / 2 - rect.getX();
			double y = p2.y - copyShape.getH() / 2 - rect.getY();
			copyShape.setX(x);
			copyShape.setY(y);
			if (copyShape.isSubSystem()) {
				copyShape.setY(0);
			}
			this.editor.clearSelect();
			// ---------设置默认属性
			((BaseAndroidComponent) copyShape).setSelect(true);
			((BaseAndroidComponent) copyShape).setTagIndex(this.editor
					.createTag() + "");
			String name = ((BaseAndroidComponent) copyShape).getName()
					+ ((BaseAndroidComponent) copyShape).getTagIndex();
			((BaseAndroidComponent) copyShape).setName(name);
			((BaseAndroidComponent) copyShape).setText(name);
			// -----
			this.editor.addShape((BaseAndroidComponent) copyShape);
			this.editor.getDragManager().getGlass().setVisible(false);
			this.editor.getDragManager().getGlass().setImage(null);
			this.editor.repaint();
			this.editor.setSaved(false);
			AndroidPageContainer page = (AndroidPageContainer) Application
					.getPageByComponent((BaseAndroidComponent) copyShape);
			ProjectEntity project = Application.getProjectByPage(page);
			TreePath treePath = new TreePath(new Object[] {
					Application.treeModel.getRoot(), project, page, copyShape

			});
			CenterPanelManager.selectNode(treePath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	protected void targetToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect) {
		this.editor.getDrawPanel().postUndoRedoEdit();
		if (androidComponents.size() == 1) {
			// 复制原来的坐标点
			Point p2 = (Point) e.getPoint().clone();
			// 转换到屏幕的坐标
			SwingUtilities.convertPointToScreen(p2, c);
			// 在转换成画板上面的坐标
			SwingUtilities.convertPointFromScreen(p2,
					this.editor.getDrawPanel());
			// 复制一个图形的时候，图形居中
			BaseAndroidComponent androidComponent = (BaseAndroidComponent) androidComponents
					.get(0);
			double x = p2.x - androidComponent.getW() / 2;
			double y = p2.y - androidComponent.getH() / 2;
			androidComponent.setX(x - rect.getX());
			androidComponent.setY(y - rect.getY());
			if (((BaseDrawComp) c).getShapes() == null
					&& ((BaseDrawComp) c).getShapes().isEmpty()) {
				return;
			}
			BaseDataShape sourceShape = ((BaseDrawComp) c).getShapes().get(0);
			sourceShape.setX(androidComponent.getX());
			sourceShape.setY(androidComponent.getY());
			sourceShape.draw(this.editor.getDrawPanel().getG2d());
			sourceShape.contrlLines();
			this.editor.repaint();
			this.editor.getDragManager().getGlass().setVisible(false);
			this.editor.getDragManager().getGlass().setImage(null);
			this.editor.setSaved(false);

		} else {
			// 拿到组件的原始位置
			Point cp = (Point) this.editor.getDrawPanel().getLocationOnScreen()
					.clone();
			// 转成glass位置
			SwingUtilities.convertPointFromScreen(cp, this.editor
					.getDragManager().getGlass());
			// Rectangle2D rect = this.editor.getDrawPanel()
			// .getImageRect();
			// cp.setLocation(cp.x + rect.getX(), cp.y + rect.getY());
			for (int k = 0; k < this.androidComponents.size(); k++) {
				BaseDataShape androidComponent = this.androidComponents.get(k);
				// 把图形的坐标转换成屏幕的坐标
				Point p2 = new Point((int) androidComponent.getX(),
						(int) androidComponent.getY());
				//
				BaseDataShape sourceShape = ((BaseDrawComp) c).getShapes().get(
						k);
				sourceShape.setX(p2.getX() - cp.getX() - rect.getX());
				sourceShape.setY(p2.getY() - cp.getY() - rect.getY());
				sourceShape.draw(this.editor.getDrawPanel().getG2d());
				sourceShape.contrlLines();
				this.editor.repaint();
				this.editor.getDragManager().getGlass().setVisible(false);
				this.editor.getDragManager().getGlass().setImage(null);
			}
			this.editor.setSaved(false);
		}
	}
}
