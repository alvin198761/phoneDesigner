package com.android.bean.shape.data.comp;

import java.util.ArrayList;

/**
 * 容器控件
 * 
 * @author Administrator
 * 
 */
public abstract class BaseAndroidContainer extends BaseAndroidComponent {

	public static final String ADD_CHILD_CHANGE = "addChildChange";
	public static final String REMOVE_CHILD_CHANGE = "removeChildChange";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 容器里面的组件
	 */
	protected ArrayList<BaseAndroidComponent> children = new ArrayList<BaseAndroidComponent>();

	public BaseAndroidContainer(String tag) {
		super(tag);
	}

	/**
	 * 添加子组件
	 * 
	 * @param child
	 */
	public void remove(BaseAndroidComponent child) {
		this.children.remove(child);
		firePropertyChange(REMOVE_CHILD_CHANGE, child, children);
	}

	/**
	 * 获取某个组件
	 * 
	 * @param index
	 * @return
	 */
	public BaseAndroidComponent getComponentAt(int index) {
		return this.children.get(index);
	}

	/**
	 * 获取组件数量
	 * 
	 * @return
	 */
	public int getComponentCount() {
		return this.children.size();
	}

}
