package com.android.bean.ui;

import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.android.gui.action.app.BaseAction;

/**
 * 树节点接口
 * 
 * @author Administrator
 * 
 */
public interface ProjectTreeNode {
	/**
	 * 显示的文本
	 * 
	 * @return
	 */
	String toString();

	/**
	 * 所有动作
	 * 
	 * @return
	 */
	List<BaseAction> getActions();

	/**
	 * 图标
	 * 
	 * @return
	 */
	Icon getIcon();

	/**
	 * 节点属性修改
	 * 
	 * @return
	 */

	JComponent getPropertiesComponent();

	/**
	 * 选中该节点
	 * 
	 * @param select
	 */
	void setSelect(boolean select);

}
