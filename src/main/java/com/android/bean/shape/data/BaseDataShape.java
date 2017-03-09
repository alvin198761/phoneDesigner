package com.android.bean.shape.data;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.android.bean.shape.BaseShape;
import com.android.bean.shape.ShapeHelper;
import com.android.bean.shape.ctrl.CtrlLineShape;
import com.android.bean.shape.ctrl.CtrlResizeShape;

/**
 * 图形数据类，到界面上就是那些拖拽的控件
 * 
 * @author Administrator
 * 
 */
public abstract class BaseDataShape extends BaseShape {

	private static final long serialVersionUID = 1L;
	public static final String MODE_PREVIEW = "preview";
	public static final String MODE_DRAW = "draw";
	public static final String MODE_DRAG = "drag";
	public static final String MODE_MENU = "menu";
	public static final String MODE_RUN = "run";
	// 是否能编辑
	protected boolean editable = false;
	// 是否可以控制
	protected boolean ctrlable = true;
	// 是否能够连线
	protected boolean conectable = true;
	// 是否拥有子系统
	protected boolean subSystem = false;
	// 是否有线条拖到上面准备链接
	protected boolean lineHover = false;
	// 显示的文本
	protected String text = "";
	// 有几种模式 画图模式 预览模式 拖动模式 菜单模式
	protected String model = MODE_DRAW;
	// 所有的控制点
	protected Map<String, CtrlLineShape> ctrlLineMap;
	// 所有的改变大小的拖拽点
	protected Map<String, CtrlResizeShape> resizeMap;

	public BaseDataShape() {
		init();
		createBox();
	}

	public void addBeginLine(BaseLineShape line, String way) {
		ctrlLineMap.get(way).addBeginLine(line);
	}

	public void addEndLine(BaseLineShape line, String way) {
		ctrlLineMap.get(way).addEndLine(line);
	}

	public boolean canCopy() {
		return select;
	}

	public void containsLineHover(Point2D p) {
		setLineHover(contains(p));
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap.entrySet()) {
			entry.getValue().setLineHover(entry.getValue().contains(p));
		}
	}

	public CtrlResizeShape contansCtrlBox(Point2D p) {
		if (resizeMap == null) {
			return null;
		}
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			if (entry.getValue().contains(p)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public void contrlLines() {
		if (!conectable) {
			return;
		}
		List<BaseLineShape> lines = getAllLines();
		for (BaseLineShape line : lines) {
			line.changePoint();
		}
		// 移动线条的位置
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap.entrySet()) {
			entry.getValue().contrlLine();
		}
	}

	protected void createBox() {
		return;
	}

	@Override
	public void draw(Graphics2D g) {
		// 画图形
		drawShape(g);
		if (select && !MODE_RUN.equals(model)) {
			// 画一个淡绿色的框框，表示好看
			drawSelectBox(g);
			// 控制拖动
			drawResize(g);
		}
		do {
			if (MODE_MENU.equals(model))
				break;
			if (!conectable)
				break;
			if (MODE_RUN.equals(model))
				break;
			// 控制线
			drawCtrl(g);
		} while (false);
		do {
			if (!editable && !model.equals(MODE_RUN))
				break;
			if (model.equals(MODE_MENU))
				break;
			if (text == null)
				break;
			if (text.trim().equals(""))
				break;
			drawText(g);
		} while (false);
		drawLineHoverBox(g);
	}

	protected void drawCtrl(Graphics2D g) {
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap
				.entrySet()) {
			entry.getValue().draw(g);
		}
	}

	protected void drawLineHoverBox(Graphics2D g) {
		if (!conectable) {
			return;
		}
		if (!lineHover) {
			return;
		}
		g.setColor(Color.red);
		g.draw(getBounds2D());
	}

	protected void drawResize(Graphics2D g) {
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			entry.getValue().draw(g);
		}
	}

	protected abstract void drawSelectBox(Graphics2D g);

	protected abstract void drawShape(Graphics2D g);

	protected void drawText(Graphics2D g) {
		g.setColor(Color.black);
		Rectangle2D rect = this.getBounds2D();
		FontMetrics fm = g.getFontMetrics();
		int fontW = fm.charsWidth(text.toCharArray(), 0, text.length());
		int fontH = fm.getAscent() - 20;
		double x = rect.getCenterX() - (fontW >> 1);
		double y = rect.getCenterY() - (fontH >> 1);
		g.drawString(text, (float) x, (float) y);
	}

	protected void endInit() {
		return;
	}

	public List<BaseLineShape> getAllLines() {
		if (!conectable) {
			return new LinkedList<BaseLineShape>();
		}
		List<BaseLineShape> lines = new LinkedList<BaseLineShape>();
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap.entrySet()) {
			lines.addAll(entry.getValue().getLines());
		}
		return lines;
	}

	public String getModel() {
		return model;
	}

	public Point2D getPoisationByWay(String way) {
		Rectangle2D rect = this.getBounds2D();
		double hx = rect.getX() + rect.getWidth() / 2;
		double hy = rect.getY() + rect.getHeight() / 2;

		double endX = rect.getX() + rect.getWidth();
		double endY = rect.getY() + rect.getHeight();
		int chw = ShapeHelper.CTRL_SHAPE_SIZE >> 1;
		if (ShapeHelper.WAY_TOP.equals(way)) {
			return new Point2D.Double(hx - chw, y - chw);
		}
		if (ShapeHelper.WAY_TOP_LEFT.equals(way)) {
			return new Point2D.Double(x - chw, y - chw);
		}
		if (ShapeHelper.WAY_TOP_RIGHT.equals(way)) {
			return new Point2D.Double(endX - chw, y - chw);
		}
		if (ShapeHelper.WAY_LEFT.equals(way)) {
			return new Point2D.Double(x - chw, hy - chw);
		}
		if (ShapeHelper.WAY_RIGHT.equals(way)) {
			return new Point2D.Double(endX - chw, hy - chw);
		}
		if (ShapeHelper.WAY_BOTTOM.equals(way)) {
			return new Point2D.Double(hx - chw, endY - chw);
		}
		if (ShapeHelper.WAY_BOTTOM_LEFT.equals(way)) {
			return new Point2D.Double(x - chw, endY - chw);
		}
		if (ShapeHelper.WAY_BOTTOM_RIGHT.equals(way)) {
			return new Point2D.Double(endX - chw, endY - chw);
		}
		return null;
	}

	public String getText() {
		return text;
	}

	public String getWay(BaseLineShape line) {
		for (Entry<String, CtrlLineShape> entry : ctrlLineMap.entrySet()) {
			if (entry.getValue().getLines().contains(line)) {
				return entry.getKey();
			}
		}
		return "0";
	}

	// 初始化的虚方法
	protected void init() {
		return;
	}

	public boolean isEditable() {
		return editable;
	}

	public boolean isLineHover() {
		return lineHover;
	}

	public boolean isSubSystem() {
		return subSystem;
	}

	@Override
	public void setH(double h) {
		if (h < 5) {
			this.h = 5;
			return;
		}
		this.h = h;
	}

	/**
	 * 鼠标拖着线移到控件上，
	 * 
	 * @param lineHover
	 */
	public void setLineHover(boolean lineHover) {
		if (!conectable) {
			this.lineHover = false;
			return;
		}
		if (this.lineHover == lineHover) {
			return;
		}
		this.lineHover = lineHover;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void setW(double w) {
		if (w < 5) {
			w = 5;
			return;
		}
		this.w = w;
	}

	public String toData() {
		return id + " " + type + " " + text + " " + x + " " + y + " " + w + " "
				+ h;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * 删除控件 的线条
	 * 
	 * @param line
	 */
	public void removeLine(BaseDataShape line) {
		for (String way : ShapeHelper.getCtrlWay()) {
			CtrlLineShape ctrl = this.ctrlLineMap.get(way);
			if (ctrl == null) {
				continue;
			}
			if (ctrl.removeLine(line)) {
				break;
			}
		}
	}
}
