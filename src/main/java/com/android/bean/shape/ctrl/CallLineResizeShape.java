package com.android.bean.shape.ctrl;

import java.awt.geom.Point2D;

import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.BaseLineShape;

/**
 * 曲线的控制
 * 
 * @author issuser
 * 
 */
public class CallLineResizeShape extends CtrlResizeShape {

	public CallLineResizeShape(BaseDataShape ctrl, String way) {
		super(ctrl, way);
	}

	private static final long serialVersionUID = 1L;

	// 下面
	protected void contrl13(Point2D p) {
		BaseLineShape line = (BaseLineShape) this.ctrl;
		line.setW(p.getX() - line.getX());
		line.setH(p.getY() - line.getY());
	}

	// 控制上面
	protected void contrl8(Point2D p) {
		BaseLineShape line = (BaseLineShape) this.ctrl;
		// double y = line.getY2();
		double sub = ctrl.getY() - p.getY();

		line.setX(p.getX());
		line.setY(p.getY());
		line.setH(line.getH() + sub);

	}

}
