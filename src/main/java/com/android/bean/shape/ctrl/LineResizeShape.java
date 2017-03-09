package com.android.bean.shape.ctrl;

import java.awt.geom.Point2D;

import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.BaseLineShape;

/**
 * 直线的控制
 * 
 * @author issuser
 * 
 */
public class LineResizeShape extends CtrlResizeShape {

	public LineResizeShape(BaseDataShape ctrl, String way) {
		super(ctrl, way);
	}

	private static final long serialVersionUID = 1L;

	// 下面
	protected void contrl13(Point2D p) {
		BaseLineShape line = (BaseLineShape) this.ctrl;
		double subX = line.getX2() - p.getX();
		line.setW(line.getX2() - line.getX() - subX);
		line.setH(p.getY() - line.getY());
	}

	// 控制上面
	protected void contrl8(Point2D p) {
		BaseLineShape line = (BaseLineShape) this.ctrl;
		line.setX(p.getX());
		line.setY(p.getY());
		line.setW(line.getX2() - line.getX());
		line.setH(line.getY2() - line.getY());
	}

}
