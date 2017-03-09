package com.runner.entity;

import java.util.List;

public interface EventTreeNode {

	List<EventTreeNode> children();
	
	void setChildren(List<EventTreeNode> children);
	
	String getTitle();
	
}
