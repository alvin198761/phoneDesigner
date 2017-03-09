package com.android.gui.drag;

import java.awt.AlphaComposite;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.android.bean.shape.data.BaseDataShape;
import com.android.reource.ResourceUtil;

/**
 * 用来画图形做跨组件的视觉效果的面板
 * 
 * @author 唐植超
 * 
 */
public class ShapeGhostGlassPane extends JPanel {

	private static final long serialVersionUID = 1L;
	private AlphaComposite composite;
	private List<BaseDataShape> draggeds = new ArrayList<BaseDataShape>();
	private int state;
	private Point location = new Point(0, 0);

	public static final int STATE_ACCEPT = 1;
	public static final int STATE_UNACCEPT = 2;
	public static final int STATE_NOMAR = -1;

	public ShapeGhostGlassPane(JComponent targetComp) {
		setOpaque(false);
		// 半透明效果
		composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setImage(List<BaseDataShape> draggeds) {
		this.draggeds.clear();
		if (draggeds == null) {
			return;
		}
		this.draggeds.addAll(draggeds);
	}

	public void setPoint(Point location) {
		this.location = location;
	}

	public void paintComponent(Graphics g) {
		if (draggeds.isEmpty()) {
			return;
		}
		// 实现多个拖动
		if (this.draggeds.size() == 1) {
			BaseDataShape dragged = this.draggeds.get(0);
			int x = (int) (location.getX() - (dragged.getW() / 2));
			int y = (int) (location.getY() - (dragged.getH() / 2));

			// 在蒙版上画图
			Graphics2D g2 = (Graphics2D) g;
			// 抗锯齿 抖动
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setComposite(composite);
			dragged.setX(x);
			dragged.setY(y); 
			dragged.draw(g2);
		} else {
			for (BaseDataShape dragged : this.draggeds) {
				// 在蒙版上画图
				Graphics2D g2 = (Graphics2D) g;
				// 抗锯齿 抖动
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setComposite(composite);
				dragged.draw(g2);
			}
		}

		switch (state) {
		case STATE_ACCEPT:
			setCursor(ResourceUtil.rightCursor);
			break;
		case STATE_UNACCEPT:
			setCursor(ResourceUtil.wrongCursor);
			break;
		case STATE_NOMAR:
			setCursor(Cursor.getDefaultCursor());
			break;
		}
	}

	public int getStatus() {
		return this.state;
	}

	public final void update(Graphics g) {
		this.paintComponent(g);
	}
}