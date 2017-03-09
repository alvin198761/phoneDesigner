package com.android.gui.frame;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.android.bean.ui.ProjectTreeNode;
import com.android.gui.action.app.BaseAction;
import com.android.gui.action.app.MenuAction;
import com.android.gui.action.file.OpenAction;
import com.android.gui.action.layout.HideLeftAction;
import com.android.gui.action.layout.HideRightAction;
import com.android.gui.action.layout.HideBottomAction;
import com.android.gui.action.newfile.NewProjectMenuAction;
import com.android.lang.Application;

public class ToolbarManager {
	private ToolbarManager() {
	}

	/**
	 * 文件工具条
	 * 
	 * @return
	 */
	private static JToolBar createFileToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		// toolbar.add(Application.sysActionManager.getAction(OpenAction.class));
		// toolbar.add(Application.sysActionManager.getAction(SaveAction.class));
		// toolbar.add(Application.sysActionManager.getAction(SaveAsAction.class));
		return toolbar;
	}

	// /**
	// * 编辑工具条
	// *
	// * @return
	// */
	// private static Component createEditToolbar() {
	// JToolBar toolbar = new JToolBar();
	// toolbar.setFloatable(false);
	// toolbar.add(Application.sysActionManager.getAction(UndoAction.class));
	// toolbar.add(Application.sysActionManager.getAction(RedoAction.class));
	// toolbar.addSeparator();
	// toolbar.add(Application.sysActionManager.getAction(CopyAction.class));
	// toolbar.add(Application.sysActionManager.getAction(CutAction.class));
	// toolbar.add(Application.sysActionManager.getAction(PasteAction.class));
	// toolbar.addSeparator();
	// toolbar.add(Application.sysActionManager.getAction(DeleteAction.class));
	// toolbar.add(Application.sysActionManager
	// .getAction(SelectAllAction.class));
	// return toolbar;
	// }

	// /**
	// * 缩放工具条
	// *
	// * @return
	// */
	// private static Component createScaleToolbar() {
	// JToolBar toolbar = new JToolBar();
	// toolbar.add(Application.sysActionManager.getAction(ZoomInAction.class));
	// toolbar.add(Application.sysActionManager.getAction(ZoomOutAction.class));
	// toolbar.add(Application.sysActionManager.getAction(ResetScaleAction.class));
	// toolbar.addSeparator();
	// toolbar.add(new ScaleCombbox());
	// return toolbar;
	// }

	/**
	 * 操作工具条
	 * 
	 * @return
	 */
	// private static Component createOperToolbar() {
	// JToolBar toolbar = new JToolBar();
	// toolbar.add(Application.sysActionManager.getAction(DefaultCursorAction.class));
	// toolbar.add(Application.sysActionManager.getAction(SimpleLineAction.class));
	// return toolbar;
	// }
	/**
	 * 页面布局的工具
	 * 
	 * @return
	 */
	private static Component createLayoutToolBar() {
		JToolBar toolbr = new JToolBar();
		// ButtonGroup bg = new ButtonGroup();
		JToggleButton btn = new JToggleButton(
				Application.sysActionManager.getAction(HideLeftAction.class));
		// bg.add(btn);
		toolbr.add(btn);
		//
		btn = new JToggleButton(
				Application.sysActionManager.getAction(HideBottomAction.class));
		// bg.add(btn);
		toolbr.add(btn);
		//
		btn = new JToggleButton(
				Application.sysActionManager.getAction(HideRightAction.class));
		// bg.add(btn);
		toolbr.add(btn);
		// btn.setSelected(true);

		toolbr.setFloatable(false);
		return toolbr;

	}

	public static void createToolbar(ProjectTreeNode node, Box hbox) {
		hbox.removeAll();
		hbox.updateUI();
		hbox.add(createFileToolbar());
		if (node == null) {
			JToolBar toolbar = new JToolBar();
			toolbar.add(Application.sysActionManager
					.getAction(OpenAction.class));
			toolbar.add(Application.sysActionManager
					.getAction(NewProjectMenuAction.class));
			toolbar.setFloatable(false);
			toolbar.updateUI();
			hbox.add(toolbar);
		} else {
			JToolBar toolbar = new JToolBar();
			for (BaseAction action : node.getActions()) {
				if (action instanceof MenuAction) {
					toolbar.add(createMenuActionButton((MenuAction) action));
					continue;
				}
				toolbar.add(action);
			}
			toolbar.setFloatable(false);
			toolbar.updateUI();
			hbox.add(toolbar);
		}
		hbox.add(Box.createHorizontalGlue());
		hbox.add(createLayoutToolBar());
		hbox.repaint();
		hbox.updateUI();
	}

	private static JButton createMenuActionButton(final MenuAction action) {
		final JButton btn = new JButton(action);
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = btn.getX();
				int y = btn.getY() + btn.getHeight();
				JPopupMenu popupMenu = new JPopupMenu();
				for (Action act : action.getActions()) {
					if (act instanceof MenuAction) {
						popupMenu.add(CenterPanelManager
								.createMenuAction((MenuAction) act));
						continue;
					}
					popupMenu.add(act);
				}
				popupMenu.show(btn.getParent(), x, y);
			}
		});
		return btn;
	}
}
