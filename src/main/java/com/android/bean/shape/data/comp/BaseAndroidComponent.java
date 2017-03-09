package com.android.bean.shape.data.comp;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.android.bean.shape.ShapeHelper;
import com.android.bean.shape.ctrl.CtrlLineShape;
import com.android.bean.shape.ctrl.CtrlResizeShape;
import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.ui.IDrawEditable;
import com.android.bean.ui.ProjectTreeNode;
import com.android.gui.action.app.BaseAction;
import com.android.gui.action.edit.CopyAction;
import com.android.gui.action.edit.CutAction;
import com.android.gui.action.edit.DeleteAction;
import com.android.gui.action.edit.MoveDownAction;
import com.android.gui.action.edit.MoveUpAction;
import com.android.gui.action.edit.PasteAction;
import com.android.gui.action.edit.PropertyAction;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

/**
 * 所有安卓控件的父类
 * 
 * @author Administrator
 * 
 */

public abstract class BaseAndroidComponent extends BaseDataShape implements
		ProjectTreeNode {

	public static final int H_TEXT_ALIGN_LEFT = 0;
	public static final int H_TEXT_ALIGN_MIDLLE = 1;
	public static final int H_TEXT_ALIGN_RIGHT = 2;

	public static final int V_TEXT_ALIGN_TOP = 3;
	public static final int V_TEXT_ALIGN_CENTER = 4;
	public static final int V_TEXT_ALIGN_BOTTOM = 5;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 对应标签名称
	private String tagName;
	// 标签的属性列表
	protected Map<String, String> attributeMap = new HashMap<String, String>();
	// 控件的z轴，
	protected int zindex;
	// // 控件的tab焦点索引
	// private int tabIndex;
	// 动作
	protected List<BaseAction> actions;
	// 文字对其
	private int textAlign = H_TEXT_ALIGN_MIDLLE;
	// 文字颜色
	private Color textColor = Color.black;
	// 文字大小
	private int fontWeight = 12;
	// 边框厚度
	private int borderWeight = 1;
	// 边框样式 圆角还是尖角
	private int borderCornerRadius = 1;
	//
	private String tagIndex;
	//
	private Font font = new Font("宋体", Font.PLAIN, 12);
	// 源图形，在克隆的时候临时保存
	transient private BaseAndroidComponent sourceComponent;

	public BaseAndroidComponent(String tagName) {
		ctrlable = true;
		conectable = false;
		this.tagName = tagName;
	}

	public int getZindex() {
		return zindex;
	}

	public void setZindex(int zindex) {
		this.zindex = zindex;
	}

	public String getTagName() {
		return tagName;
	}

	public String getTagIndex() {
		return tagIndex;
	}

	public void setTagIndex(String tagIndex) {
		this.tagIndex = tagIndex;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}

	@Override
	protected void drawSelectBox(Graphics2D g) {
		if (MODE_RUN.equals(model)) {
			return;
		}
		Stroke s = g.getStroke();
		BasicStroke bs = new BasicStroke(.8f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 0, new float[] { 6, 4, 6, 4 }, 0);
		g.setColor(Color.red);
		g.setStroke(bs);
		g.draw(getBounds2D());
		g.setStroke(s);
	}

	protected void createBox() {
		if (!this.ctrlable) {
			return;
		}
		resizeMap = new HashMap<String, CtrlResizeShape>();
		ctrlLineMap = new HashMap<String, CtrlLineShape>();
		List<String> list = ShapeHelper.getResizeWay();
		for (String str : list) {
			resizeMap.put(str, new CtrlResizeShape(this, str));
		}
		list = ShapeHelper.getCtrlWay();
		for (String str : list) {
			ctrlLineMap.put(str, new CtrlLineShape(this, str));
		}
		endInit();
	}

	@Override
	protected void endInit() {
		for (Entry<String, CtrlResizeShape> entry : resizeMap.entrySet()) {
			entry.getValue().setEnable(true);
		}
	}

	/**
	 * 颜色转成 16进制
	 * 
	 * @param color
	 * @return
	 */
	public static String color2String(Color color) {
		String tmp;
		String colorStr = "#";
		tmp = Integer.toHexString(color.getRed());
		if (tmp.length() == 1) {
			tmp = "0" + tmp;
		}
		colorStr += tmp;
		tmp = Integer.toHexString(color.getGreen());
		if (tmp.length() == 1) {
			tmp = "0" + tmp;
		}
		colorStr += tmp;
		tmp = Integer.toHexString(color.getBlue());
		if (tmp.length() == 1) {
			tmp = "0" + tmp;
		}
		colorStr += tmp;
		return colorStr;
	}

	/**
	 * 16进制转颜色
	 * 
	 * @param str
	 * @return
	 */
	public static Color string2Color(String str) {
		int r, g, b;
		String tmp = str.substring(1, 3);
		r = Integer.valueOf(tmp, 16);
		tmp = str.substring(3, 5);
		g = Integer.valueOf(tmp, 16);
		tmp = str.substring(5, 7);
		b = Integer.valueOf(tmp, 16);
		return new Color(r, g, b);
	}

	@Override
	public Icon getIcon() {
		return ResourceUtil.comp_icon;
	}

	@Override
	public void setModel(String model) {
		super.setModel(model);
		if (MODE_RUN.equals(model)) {
			ctrlable = false;
			editable = false;
		}
	}

	@Override
	public JComponent getPropertiesComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseAction> getActions() {
		if (actions == null) {
			actions = new ArrayList<BaseAction>();
			IDrawEditable page = Application.getPageByComponent(this);
			if (page != null) {
				actions.add(new CutAction(page));
				actions.add(new CopyAction(page));
				actions.add(new PasteAction(page));
				actions.add(new DeleteAction(page));
				actions.add(new MoveUpAction(page, this));
				actions.add(new MoveDownAction(page, this));
				actions.add(new PropertyAction(page));
			}
		}
		return actions;
	}

	protected void fireActionChange() {
		if (getActions() == null) {
			return;
		}
		for (BaseAction action : getActions()) {
			action.firePropertyChange();
		}
	}

	public int getH_TextAlign() {
		return textAlign;
	}

	public void setH_TextAlign(int textAlign) {
		this.textAlign = textAlign;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public int getFontWeight() {
		return fontWeight;
	}

	public void setFontWeight(int fontWeight) {
		this.fontWeight = fontWeight;
	}

	public int getBorderWeight() {
		return borderWeight;
	}

	public void setBorderWeight(int borderWeight) {
		this.borderWeight = borderWeight;
	}

	public int getBorderCornerRadius() {
		return borderCornerRadius;
	}

	public void setBorderCornerRadius(int borderCornerRadius) {
		this.borderCornerRadius = borderCornerRadius;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * 控件在列表中的图片
	 * 
	 * @return
	 */
	public abstract Icon getContrlIcon();

	public void setSourceComponent(BaseAndroidComponent sourceComponent) {
		this.sourceComponent = sourceComponent;
	}

	public BaseAndroidComponent getSourceComponent() {
		return this.sourceComponent;
	}

	@Override
	public void setX(double x) {
		super.setX(x);
		firePropertyChange("boxChange", true, false);
	}

	@Override
	public void setY(double y) {
		super.setY(y);
		firePropertyChange("boxChange", true, false);
	}

	@Override
	public void setH(double h) {
		super.setH(h);
		firePropertyChange("boxChange", true, false);
	}

	@Override
	public void setW(double w) {
		super.setW(w);
		firePropertyChange("boxChange", true, false);
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		firePropertyChange("textChange", true, false);
	}

}
