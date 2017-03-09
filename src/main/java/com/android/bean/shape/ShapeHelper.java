package com.android.bean.shape;

import java.awt.Component;
import java.awt.Cursor;
import java.util.LinkedList;
import java.util.List;

import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.comp.AndroidOsShapeButton;
import com.android.bean.shape.data.comp.AndroidButton;
import com.android.bean.shape.data.comp.AndroidImageView;
import com.android.bean.shape.data.comp.AndroidTextLable;
import com.android.bean.shape.data.comp.BaseAndroidComponent;

public class ShapeHelper {

	// 控制方位
	public static final String WAY_TOP = "8";
	public static final String WAY_TOP_RIGHT = "9";
	public static final String WAY_TOP_LEFT = "10";
	public static final String WAY_LEFT = "11";
	public static final String WAY_RIGHT = "12";
	public static final String WAY_BOTTOM = "13";
	public static final String WAY_BOTTOM_LEFT = "14";
	public static final String WAY_BOTTOM_RIGHT = "15";
	//
	public static final int CTRL_SHAPE_SIZE = 6;

	// 安卓控件
	public static final String ANDROID_LABEL = "16";
	public static final String ANDROID_BUTTON = "17";
	public static final String ANDROID_IMAGEVIEW = "18";
	public static final String ANDROID_OSSHAPEBUTTON = "19";

	public static List<String> getResizeWay() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(WAY_TOP);
		list.add(WAY_LEFT);
		list.add(WAY_RIGHT);
		list.add(WAY_BOTTOM);

		list.add(WAY_TOP_RIGHT);
		list.add(WAY_TOP_LEFT);
		list.add(WAY_BOTTOM_LEFT);
		list.add(WAY_BOTTOM_RIGHT);
		return list;
	}

	public static List<String> getCtrlWay() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(WAY_TOP);
		list.add(WAY_LEFT);
		list.add(WAY_RIGHT);
		list.add(WAY_BOTTOM);
		return list;
	}

	public static BaseAndroidComponent createMenuShapeByType(String type) {
		BaseAndroidComponent shape = null;
		// 安卓控件
		if (type.equals(ANDROID_LABEL)) {
			AndroidTextLable androidLabel = new AndroidTextLable();
			androidLabel.setX(0);
			androidLabel.setY(0);
			androidLabel.setW(60);
			androidLabel.setH(25);
			shape = androidLabel;
		} else if (type.equals(ANDROID_BUTTON)) {
			AndroidButton androidButton = new AndroidButton();
			androidButton.setX(0);
			androidButton.setY(0);
			androidButton.setW(60);
			androidButton.setH(25);
			shape = androidButton;
		} else if (type.equals(ANDROID_IMAGEVIEW)) {
			AndroidImageView imgView = new AndroidImageView();
			imgView.setX(0);
			imgView.setY(0);
			imgView.setW(60);
			imgView.setH(25);
			shape = imgView;
		} else if (type.equals(ANDROID_OSSHAPEBUTTON)) {
			AndroidOsShapeButton shapeButton = new AndroidOsShapeButton();
			shapeButton.setX(0);
			shapeButton.setY(0);
			shapeButton.setW(60);
			shapeButton.setH(25);
			shape = shapeButton;
		}
		if (shape != null) {
			shape.setModel(BaseDataShape.MODE_MENU);
		}
		return shape;
	}

	public static BaseDataShape createShapeFactory(String type) {
		if (type.equals(ANDROID_LABEL)) {
			return new AndroidTextLable();
		} else if (type.equals(ANDROID_BUTTON)) {
			return new AndroidButton();
		} else if (type.equals(ANDROID_IMAGEVIEW)) {
			return new AndroidImageView();
		} else if (type.equals(ANDROID_OSSHAPEBUTTON)) {
			return new AndroidOsShapeButton();
		}
		return null;
	}

	/**
	 * 各种方位的鼠标样式
	 * 
	 * @param c
	 * @param way
	 */
	public static void setCursor(Component c, String way) {
		if (way.equals(WAY_TOP)) {
			c.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_TOP_LEFT)) {
			c.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_TOP_RIGHT)) {
			c.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_LEFT)) {
			c.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_RIGHT)) {
			c.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_BOTTOM)) {
			c.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_BOTTOM_LEFT)) {
			c.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
			return;
		}
		if (way.equals(WAY_BOTTOM_RIGHT)) {
			c.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
			return;
		}
	}

	/**
	 * 安卓控件库
	 * 
	 * @return
	 */
	public static List<String> getAndroidComponents() {
		LinkedList<String> list = new LinkedList<String>();
		list.add(ANDROID_BUTTON);
		list.add(ANDROID_OSSHAPEBUTTON);
		list.add(ANDROID_LABEL);
		list.add(ANDROID_IMAGEVIEW);
		return list;
	}
}
