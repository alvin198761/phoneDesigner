package com.runner.entity;

import java.util.List;

public class StringNode implements EventTreeNode {

	private String text;
	private List<EventTreeNode> children;

	public StringNode(String text, List<EventTreeNode> children) {
		this.text = text;
		this.children = children;
	}

	@Override
	public List<EventTreeNode> children() {
		return children;
	}

	@Override
	public void setChildren(List<EventTreeNode> children) {
	}

	@Override
	public String getTitle() {
		return text;
	}

	@Override
	public String toString() {
		return this.getTitle();
	}

}
