package com.android.gui.model;

import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.ProjectTreeNode;
import com.android.gui.action.app.BaseAction;
import com.android.lang.Application;

/**
 * 项目树
 * 
 * @author Administrator
 * 
 */
public class ProjectTreeModel implements TreeModel {

	private ProjectTreeNode root = new ProjectTreeNode() {

		@Override
		public Icon getIcon() {
			return null;
		}

		@Override
		public List<BaseAction> getActions() {
			return null;
		}

		public String toString() {
			return "";
		}

		@Override
		public JComponent getPropertiesComponent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setSelect(boolean select) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent == root) {
			return Application.projects.get(index);
		}
		if (parent instanceof ProjectEntity) {
			return ((ProjectEntity) parent).getViewAt(index);
		}
		if (parent instanceof AndroidPageContainer) {
			return ((AndroidPageContainer) parent).getComponents().get(index);
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent == root) {
			return Application.projects.size();
		}
		if (parent instanceof ProjectEntity) {
			return ((ProjectEntity) parent).getViewCount();
		}
		if (parent instanceof AndroidPageContainer) {
			return ((AndroidPageContainer) parent).getComponents().size();
		}
		return 0;
	}

	@Override
	public boolean isLeaf(Object node) {
		return (node instanceof BaseAndroidComponent)
				&& !(node instanceof AndroidPageContainer);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {

	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent == root) {
			return Application.projects.indexOf(child);
		}
		if (parent instanceof ProjectEntity) {
			return ((ProjectEntity) parent).viewIndexOf(child);
		}
		if (parent instanceof AndroidPageContainer) {
			return ((AndroidPageContainer) parent).getComponents().indexOf(
					child);
		}
		return -1;
	}

	/**
	 * Adds a listener for the TreeModelEvent posted after the tree changes.
	 * 
	 * @see #removeTreeModelListener
	 * @param l
	 *            the listener to add
	 */
	public void addTreeModelListener(TreeModelListener l) {
		listenerList.add(TreeModelListener.class, l);
	}

	/**
	 * Removes a listener previously added with <B>addTreeModelListener()</B>.
	 * 
	 * @see #addTreeModelListener
	 * @param l
	 *            the listener to remove
	 */
	public void removeTreeModelListener(TreeModelListener l) {
		listenerList.remove(TreeModelListener.class, l);
	}

	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * 改变树的数据
	 */
	public void firePropertyChange() {
		this.fireTreeStructureChanged(root, new TreePath(root));
	}

	/**
	 * 改变树的数据
	 */
	public void firePropertyChange(Object[] treePath) {
		this.fireTreeStructureChanged(root, new TreePath(treePath));
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node being changed
	 * @param path
	 *            the path to the root node
	 * @param childIndices
	 *            the indices of the changed elements
	 * @param children
	 *            the changed elements
	 * @see EventListenerList
	 */
	protected void fireTreeNodesChanged(Object source, Object[] path,
			int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
			}
		}
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source
	 *            the node where the tree model has changed
	 * @param path
	 *            the path to the root node
	 * @param childIndices
	 *            the indices of the affected elements
	 * @param children
	 *            the affected elements
	 * @see EventListenerList
	 */
	protected void fireTreeStructureChanged(Object source, Object[] path,
			int[] childIndices, Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
	}

	/*
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created using the
	 * parameters passed into the fire method.
	 * 
	 * @param source the node where the tree model has changed
	 * 
	 * @param path the path to the root node
	 * 
	 * @see EventListenerList
	 */
	private void fireTreeStructureChanged(Object source, TreePath path) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		TreeModelEvent e = null;
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e = new TreeModelEvent(source, path);
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
	}

}
