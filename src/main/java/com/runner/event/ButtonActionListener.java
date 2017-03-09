package com.runner.event;

import java.util.EventListener;

public interface ButtonActionListener extends EventListener {

	String click = "click";
	String hover = "hover";
	String press = "press";
	String out = "out";

	/**
	 * 点击，按下然后放开后的操作
	 * 
	 * @param event
	 */
	void doClick(ButtonActionEventBean event);

	/**
	 * 鼠标悬停的操作
	 * 
	 * @param event
	 */
	void doHover(ButtonActionEventBean event);

	/**
	 * 按下鼠标
	 * 
	 * @param event
	 */
	void doPress(ButtonActionEventBean event);

	/**
	 * 鼠标移出控件
	 * 
	 * @param event
	 */
	void doOut(ButtonActionEventBean event);

}
