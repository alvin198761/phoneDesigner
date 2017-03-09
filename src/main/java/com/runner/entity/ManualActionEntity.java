package com.runner.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 交互动作
 * 
 * @author Administrator
 * 
 */
public class ManualActionEntity implements EventTreeNode {

	private String templet;
	private String target;
	private String title;
	private List<EventTreeNode> children = new ArrayList<EventTreeNode>();
	private List<ManuEntity> manuList = new ArrayList<ManuEntity>();

	public String getTemplet() {
		return templet;
	}

	public void setTemplet(String templet) {
		this.templet = templet;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public List<EventTreeNode> children() {
		return this.children;
	}

	@Override
	public void setChildren(List<EventTreeNode> children) {
		this.children.clear();
		this.children.addAll(children);
	}

	@Override
	public String toString() {
		return getTitle();
	}

	public List<ManuEntity> getManuList() {
		return manuList;
	}

	public void setManuList(List<ManuEntity> manuList) {
		this.manuList = manuList;
	}

	public List<EventTreeNode> getChildren() {
		return children;
	}

}
