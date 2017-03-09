package com.android.bean.entity;

import com.android.bean.BaseObject;

/**
 * 分辨率对象
 * 
 * @author Administrator
 * 
 */
public class ResEntity extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 索引
	private int index;
	// 横屏还是竖屏
	private int mode;
	// 宽
	private int width;
	// 高
	private int height;
	// 电话名称
	private String phoneName;
	
	public ResEntity() {
	}
 
	public String getPhoneName() {
		return phoneName;
	}

	public void setPhoneName(String phoneName) {
		this.phoneName = phoneName;
	}

	private static String[] mode_array = new String[] { "竖", "横" };
 

	public ResEntity(int index, int mode, String phoneName, int width,
			int height) {
		super();
		this.index = index;
		this.mode = mode;
		this.width = width;
		this.height = height;
		this.phoneName = phoneName;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return mode_array[this.mode] + "-" + phoneName + "(" + width + "*"
				+ height + ")";
	}

}
