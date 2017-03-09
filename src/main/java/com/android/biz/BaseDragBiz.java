package com.android.biz;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.gui.drag.ShapeGhostGlassPane;
import com.android.gui.frame.BaseDrawComp;
import com.android.lang.Application;

/**
 * 拖动处理的父类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseDragBiz extends BaseBiz {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean flag;
	protected long startTime;
	protected IDrawEditable editor;
	protected Point oldPoint = new Point(0, 0);
	protected List<BaseDataShape> androidComponents;

	public BaseDragBiz(IDrawEditable editor) {
		this.editor = editor;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.isMetaDown()) {
			return;
		}
		oldPoint = (Point) e.getPoint().clone();
		// System.out.println("mousePressed");
		startTime = System.currentTimeMillis();
		flag = false;
		if (Application.OPER_DRAWSELECTBOX.equals(this.editor.getOperStatus())) {
			return;
		}
		try {
			// 克隆所有的被选中的图形
			List<BaseAndroidComponent> sourceShapes = (List<BaseAndroidComponent>) ((BaseDrawComp) e
					.getComponent()).getShapes();
			if (sourceShapes == null) {
				// System.out.println("shapes null");
				return;
			}
			Component c = e.getComponent();
			androidComponents = new ArrayList<BaseDataShape>();

			if (c.equals(this.editor.getDrawPanel())) {
				for (BaseAndroidComponent sourceShape : sourceShapes) {
					// 复制每一个被选中的图形
					if (!sourceShape.isSelect()) {
						continue;
					}
					BaseAndroidComponent androidComponent = Application.dataio
							.cloneComponent(sourceShape);
					androidComponent.setSourceComponent(sourceShape);
					androidComponent.setSelect(false);
					this.androidComponents.add(androidComponent);
				}
			} else {
				this.androidComponents.add(sourceShapes.get(0));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			this.androidComponents = null;
			return;
		}
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	public void mouseDragged(MouseEvent e) {
		if (e.isMetaDown()) {
			return;
		}
		if (this.androidComponents == null) {
			return;
		}
		if (System.currentTimeMillis() - startTime < 80) {
			// 减少重新绘制的次数，
			return;
		}
		if (Application.OPER_DRAWSELECTBOX.equals(this.editor.getOperStatus())) {
			// 过滤状态
			return;
		}
		startTime = System.currentTimeMillis();
		flag = true;
		Component c = e.getComponent();
		if (c.equals(this.editor.getDrawPanel())
				&& Application.OPER_RESIZE.equals(this.editor.getOperStatus())) {
			// 修改控件大小的时候，这里不需要操作，为了防止操作混乱，这里过滤
			this.editor.getDragManager().getGlass().setImage(null);
			this.editor.getDragManager().getGlass().repaint();
			return;
		}
		if (Application.OPER_NONE.equals(this.editor.getOperStatus())
				|| Application.OPER_MUILT_SELECT.equals(this.editor
						.getOperStatus())) {
			// System.out.println(this.editor.getOperStatus() + "   ====");
			// 如果当前操作状态为 none 或者是多选状态，把这些数据传入glass pane上面，绘制
			if (!this.editor.getDragManager().getGlass().isVisible()) {
				// 创建图片
				BufferedImage image = new BufferedImage(c.getWidth(),
						c.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				c.paint(g);
				this.editor.getDragManager().getGlass().setVisible(true);
				if (this.androidComponents.size() == 1) {
					// 转换坐标
					Point p = (Point) e.getPoint().clone();
					SwingUtilities.convertPointToScreen(p, c);
					SwingUtilities.convertPointFromScreen(p, this.editor
							.getDragManager().getGlass());
					this.editor.getDragManager().getGlass().setPoint(p);
				} else {
					// 多个拖动,先整理好空间的坐标位置，然后放进去
					double subX = e.getPoint().getX() - oldPoint.getX();
					double subY = e.getPoint().getY() - oldPoint.getY();
					// 拿到组件的原始位置
					Point cp = SwingUtilities.convertPoint(c, new Point(0, 0),
							this.editor.getDragManager().getGlass());

					// Point sp = c.getLocationOnScreen();
					//
					// Point tp =
					// Application.tabPane.getParent().getLocationOnScreen();
					// cp.x += sp.x - tp.x;
					// cp.y += sp.y - tp.y;

					Rectangle2D rect = this.editor.getDrawPanel()
							.getImageRect();
					cp.setLocation(cp.x + rect.getX(), cp.y + rect.getY());
					//
					this.editor.getDragManager().getGlass().setPoint(cp);
					for (BaseDataShape androidComponent : this.androidComponents) {
						//
						androidComponent.setX(androidComponent.getX() + subX
								+ cp.getX());
						androidComponent.setY(androidComponent.getY() + subY
								+ cp.getY());
					}
					oldPoint = (Point) e.getPoint().clone();
				}
			} else {
				// 创建图片
				BufferedImage image = new BufferedImage(c.getWidth(),
						c.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				c.paint(g);
				this.editor.getDragManager().getGlass().setVisible(true);
				if (this.androidComponents.size() == 1) {
					// 转换坐标
					Point p = (Point) e.getPoint().clone();
					SwingUtilities.convertPointToScreen(p, c);
					SwingUtilities.convertPointFromScreen(p, this.editor
							.getDragManager().getGlass());
					this.editor.getDragManager().getGlass().setPoint(p);
				} else {
					// 多个拖动,先整理好空间的坐标位置，然后放进去
					double subX = e.getPoint().getX() - oldPoint.getX();
					double subY = e.getPoint().getY() - oldPoint.getY();
					for (BaseDataShape androidComponent : this.androidComponents) {
						androidComponent.setX(androidComponent.getX() + subX);
						androidComponent.setY(androidComponent.getY() + subY);
						// System.out.println(androidComponent.getX()
						// + "   ===   " + androidComponent.getY());
					}
					oldPoint = (Point) e.getPoint().clone();
				}
			}

			this.editor.getDragManager().getGlass()
					.setImage(new ArrayList<BaseDataShape>(androidComponents));
			this.editor.getDragManager().getGlass().repaint();
			// System.out.println(this.editor.getOperStatus());
			return;
		}
		// 单个图形拖拽
		if (Application.OPER_NONE.equals(this.editor.getOperStatus())
				|| Application.OPER_DRAGSHAPE.equals(this.editor
						.getOperStatus())) {
			if (!this.editor.getDragManager().getGlass().isVisible()) {
				BufferedImage image = new BufferedImage(c.getWidth(),
						c.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				c.paint(g);
				this.editor.getDragManager().getGlass().setVisible(true);
				Point cp = SwingUtilities.convertPoint(c, new Point(0, 0),
						this.editor.getDragManager().getGlass());
				this.editor.getDragManager().getGlass().setPoint(cp);
				this.editor.getDragManager().getGlass()
						.setImage(this.androidComponents);
				this.editor.getDragManager().getGlass().repaint();
				return;
			}
		}
		Point p = (Point) e.getPoint().clone();
		SwingUtilities.convertPointToScreen(p, c);
		Point eventPoint = (Point) p.clone();
		SwingUtilities.convertPointFromScreen(p, this.editor.getDragManager()
				.getGlass());

		// 鼠标是否在目标控件的上方
		SwingUtilities.convertPointFromScreen(eventPoint,
				this.editor.getDrawPanel());
		int state = this.editor.getDrawPanel().getImageRect()
				.contains(eventPoint) ? ShapeGhostGlassPane.STATE_ACCEPT
				: ShapeGhostGlassPane.STATE_UNACCEPT;
		this.editor.getDragManager().getGlass().setState(state);
		this.editor.getDragManager().getGlass().setPoint(p);
		this.editor.getDragManager().getGlass().repaint();
	}

	public void mouseReleased(MouseEvent e) {
		if (e.isMetaDown()) {
			return;
		}
		this.editor.getDragManager().getGlass()
				.setCursor(Cursor.getDefaultCursor());
		if (!flag) {
			this.editor.getDragManager().getGlass().setImage(null);
			this.editor.getDragManager().getGlass().repaint();
			this.editor.getDragManager().getGlass().setVisible(false);
			return;
		}
		this.editor.getDragManager().setDragSource(null);
		System.gc();
		final Component c = e.getComponent();
		final Point p = (Point) e.getPoint().clone();
		SwingUtilities.convertPointToScreen(p, c);
		final Point eventPoint = (Point) p.clone();
		final Rectangle2D rect = this.editor.getDrawPanel().getImageRect();
		SwingUtilities.convertPointFromScreen(p, this.editor.getDragManager()
				.getGlass());
		SwingUtilities.convertPointFromScreen(eventPoint,
				this.editor.getDrawPanel());
		this.editor.getDragManager().getGlass().setPoint(p);
		this.editor.getDragManager().getGlass().repaint();
		/*
		 * 移动控件有一下几种情况
		 * 
		 * 1、控件在原来的位置之上，没动
		 * 
		 * 2、控件从原来的位置移动到目标控件的位置
		 * 
		 * 3、控件已经在目标的位置上，然后自动在目标的位置
		 * 
		 * 4、控件在目标的位置上移动的其他控件上
		 */
		// 还没有进入目标组件
		// 源头不在画图板上，图形和鼠标都不在目标画布上面
		final boolean flag1 = (!c.equals(this.editor.getDrawPanel()) && !this.editor
				.getDrawPanel().contains(eventPoint));
		// 源头不在画图板上面，鼠标到了画布里面，但是没有进入白色区域
		final boolean flag2 = ((!c.equals(this.editor.getDrawPanel()) && this.editor
				.getDrawPanel().contains(eventPoint)) && !this.editor
				.getDrawPanel().getImageRect().contains(eventPoint));
		// 源头在画图板上面，但是拖出白色区域
		final boolean flag3 = (c.equals(this.editor.getDrawPanel()) && !this.editor
				.getDrawPanel().getImageRect().contains(eventPoint));
		if (flag1 || flag2) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// 没有接受
					BaseDragBiz.this.editor.getDragManager().getGlass()
							.setState(ShapeGhostGlassPane.STATE_NOMAR);
					// 拿到组件的原始位置
					Point cp = (Point) c.getLocationOnScreen().clone();
					// 转成glass位置
					SwingUtilities
							.convertPointFromScreen(cp, BaseDragBiz.this.editor
									.getDragManager().getGlass());
					// 当前组件在glass上的位置
					int x = cp.x;
					int y = cp.y;
					// 拿到当前显示的图片的位置
					int cx = p.x - (c.getWidth() / 2);
					int cy = p.y - (c.getHeight() / 2);

					int subX = (x - cx) / 10;
					int subY = (y - cy) / 10;

					x = p.x - (c.getWidth() / 2);
					y = p.y - (c.getHeight() / 2);

					for (int i = 0; i < 11; i++) {
						x += subX;
						y += subY;
						if (i == 10) {
							x = cp.x;
							y = cp.y;
						}
						cx = x + (c.getWidth() / 2);
						cy = y + (c.getHeight() / 2);
						BaseDragBiz.this.editor.getDragManager().getGlass()
								.setPoint(new Point(cx, cy));
						BaseDragBiz.this.editor.getDragManager().getGlass()
								.repaint();
						try {
							Thread.sleep(20);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
					BaseDragBiz.this.editor.getDragManager().getGlass()
							.setVisible(false);
					BaseDragBiz.this.editor.getDragManager().getGlass()
							.setImage(null);
				}
			}).start();
			return;
		}
		if (flag3) {
			// 这里记录图形的
			new Thread(new Runnable() {
				@Override
				public void run() {
					// 没有接受
					editor.getDragManager().getGlass()
							.setState(ShapeGhostGlassPane.STATE_NOMAR);
					List<BaseAndroidComponent> sourceShapes = ((BaseDrawComp) c)
							.getShapes();
					if (sourceShapes == null) {
						return;
					}
					for (int k = 0; k < sourceShapes.size(); k++) {
						// 画板在屏幕上的位置
						BaseAndroidComponent sourceShape = sourceShapes.get(k);
						// shape的位置
						Point cp = new Point((int) (sourceShape.getX()),
								(int) (sourceShape.getY()));
						// 转化到屏幕
						SwingUtilities.convertPointToScreen(cp, c);
						// 转成glass位置
						SwingUtilities.convertPointFromScreen(cp,
								BaseDragBiz.this.editor.getDragManager()
										.getGlass());
						// 拿到组件的位置
						int x = cp.x;
						int y = cp.y;
						// 拿到当前鼠标的位置
						SwingUtilities.convertPointToScreen(eventPoint, c);
						SwingUtilities.convertPointFromScreen(eventPoint,
								BaseDragBiz.this.editor.getDragManager()
										.getGlass());
						int cx = eventPoint.x;
						int cy = eventPoint.y;

						int subX = (x - cx) / 10;
						int subY = (y - cy) / 10;

						x = eventPoint.x;
						y = eventPoint.y;

						for (int i = 0; i < 11; i++) {
							x += subX;
							y += subY;

							if (i == 10) {
								x = cp.x;
								y = cp.y;
							}

							cx = (int) (x + (androidComponents.get(k).getW() / 2));
							cy = (int) (y + (androidComponents.get(k).getH() / 2));
							BaseDragBiz.this.editor.getDragManager().getGlass()
									.setPoint(new Point(cx, cy));
							BaseDragBiz.this.editor.getDragManager().getGlass()
									.repaint();
							BaseDragBiz.this.editor.setSaved(false);
							try {
								Thread.sleep(20);
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
						BaseDragBiz.this.editor.getDragManager().getGlass()
								.setVisible(false);
						BaseDragBiz.this.editor.getDragManager().getGlass()
								.setImage(null);
					}
				}
			}).start();
			return;
		}
		// 移动到目标的位置
		if (!c.equals(this.editor.getDrawPanel())
				&& this.editor.getDrawPanel().getImageRect()
						.contains(eventPoint)) {
			// 又多了一种情况
			// 必须在image上
			// 复制出新的组件
			sourceToTarget(e, c, rect);
			return;
		}
		// 控件已经在目标的位置上，然后自动在目标的位置
		if (c.equals(this.editor.getDrawPanel())
				&& this.editor.getDrawPanel().contains(eventPoint)
				&& !Application.OPER_RESIZE.equals(this.editor.getOperStatus())) {
			targetToTarget(e, c, rect);
			return;
		}
		// 这属于改变控件大小以后
		if (c.equals(this.editor.getDrawPanel())
				&& this.editor.getDrawPanel().contains(eventPoint)
				&& Application.OPER_RESIZE.equals(this.editor.getOperStatus())) {
			this.editor.setOperStatus(Application.OPER_NONE);
			return;
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * 从原来的组件拖到目标组件
	 * 
	 * @param e
	 * @param c
	 * @param rect
	 */
	protected abstract void sourceToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect);

	/**
	 * 目标组件拖到目标组件
	 * 
	 * @param e
	 * @param c
	 * @param rect
	 */
	protected abstract void targetToTarget(MouseEvent e, final Component c,
			final Rectangle2D rect);

}
