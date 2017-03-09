package com.runner.entity;

import java.util.List;

/**
 * 跳转动作
 * 
 * @author Administrator
 * 
 */
public class PageActionEntity implements EventTreeNode {

	private String type;
	private String title;
	private String animated;// 是否有跳转动作
	private String forwardPage;// 要跳转的页面
	private String showTitle;// xml显示的title
	private boolean selected;

	public String getAnimated() {
		return animated;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getForwardPage() {
		return forwardPage;
	}

	public void setForwardPage(String forwardPage) {
		this.forwardPage = forwardPage;
	}

	public String getShowTitle() {
		return showTitle;
	}

	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}

	public void setAnimated(String animated) {
		this.animated = animated;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public List<EventTreeNode> children() {
		return null;
	}

	@Override
	public void setChildren(List<EventTreeNode> children) {
	}

	@Override
	public String toString() {
		return getTitle();
	}

}
