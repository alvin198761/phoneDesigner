package com.android.bean.entity;

import com.android.bean.BaseObject;

/**
 * 页面跳转的动作实体
 * 
 * @author Administrator
 * 
 */
public class PageForwardActionEntity extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type;
	private String text;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

}
