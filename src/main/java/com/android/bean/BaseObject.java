package com.android.bean;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import com.android.utils.ObjectUtil;

/**
 * 所有的数据类的基类，拥有名称，编号，和消息支持
 * 
 * @author Administrator
 * 
 */
public abstract class BaseObject extends PropertyChangeSupport implements
		Serializable, IBean {

	private static final long serialVersionUID = 1L;

	protected String id;
	protected String name;

	public BaseObject() {
		super("");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BaseObject cloneObject() throws Exception {
		return (BaseObject) ObjectUtil.cloneObject(this);
	}

}
