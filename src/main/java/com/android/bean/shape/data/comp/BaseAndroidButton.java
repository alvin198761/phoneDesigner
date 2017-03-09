package com.android.bean.shape.data.comp;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JComponent;

import com.runner.entity.OntouchAction;

public abstract class BaseAndroidButton extends BaseAndroidComponent {

	public static final int MOUSE_TYPE_DEFAULT = 0;
	public static final int MOUSE_TYPE_HOVER = 1;
	public static final int MOUSE_TYPE_PRESS = 2;

	protected OntouchAction[] mouseActions = new OntouchAction[3];

	// 默认颜色颜色
	private Color defaultBackGroundColor = SystemColor.control;
	// 点击时背景颜色
	private Color pressColor = SystemColor.control.darker().darker();
	// 鼠标滑过的时候，背景颜色
	private Color selectColor = SystemColor.control.darker();
	//
	private int mouseType = MOUSE_TYPE_DEFAULT;

	public Color getDefaultBackGroundColor() {
		return defaultBackGroundColor;
	}

	public void setDefaultBackGroundColor(Color defaultBackGroundColor) {
		this.defaultBackGroundColor = defaultBackGroundColor;
	}

	public Color getPressColor() {
		return pressColor;
	}

	public void setPressColor(Color pressColor) {
		this.pressColor = pressColor;
	}

	public Color getSelectColor() {
		return selectColor;
	}

	public void setSelectColor(Color selectColor) {
		this.selectColor = selectColor;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseAndroidButton(String tag) {
		super(tag);
		mouseActions[0] = new OntouchAction(OntouchAction.ontouch);
		mouseActions[1] = new OntouchAction(OntouchAction.ontouchin);
		mouseActions[2] = new OntouchAction(OntouchAction.ontouchout);
	}

	public void drawDefaultStyle() {
		setMouseType(MOUSE_TYPE_DEFAULT);
	}

	public void drawHoverStyle() {
		setMouseType(MOUSE_TYPE_HOVER);
	}

	public void drawPressStyle() {
		setMouseType(MOUSE_TYPE_PRESS);
	}

	@Override
	public JComponent getPropertiesComponent() {
		return null;
	}

	public OntouchAction[] getMouseActions() {
		return mouseActions;
	}

	public void addAction(OntouchAction action) {
		if (action.getType().equals(OntouchAction.ontouch)) {
			mouseActions[0] = action;
		} else if (action.getType().equals(OntouchAction.ontouchin)) {
			mouseActions[1] = action;
		} else if (action.getType().equals(OntouchAction.ontouchout)) {
			mouseActions[2] = action;
		}

	}

	public int getMouseType() {
		return mouseType;
	}

	public void setMouseType(int mouseType) {
		this.mouseType = mouseType;
	}

}
