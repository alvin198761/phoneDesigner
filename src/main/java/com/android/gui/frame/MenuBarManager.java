package com.android.gui.frame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.gui.action.file.ExitAction;
import com.android.gui.action.file.OpenAction;
import com.android.gui.action.file.SaveAllAction;
import com.android.gui.action.help.AboutAction;
import com.android.gui.action.help.HelpAction;
import com.android.gui.action.newfile.NewProjectMenuAction;
import com.android.gui.action.project.WorkspaceManagerAction;
import com.android.lang.Application;

/**
 * 菜单条管理
 * 
 * @author Administrator
 * 
 */
public class MenuBarManager {
	private MenuBarManager() {
	}

	/**
	 * 创建菜单条
	 */
	public static void createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		// 文件
		menubar.add(createFileMenu());
		// 编辑
		menubar.add(createEditMenu());
		// 视图
		menubar.add(createViewMenu());
		// 工程
		menubar.add(createProjectMenu());
		// 帮组
		menubar.add(createHelpMenu());
		Application.menuBar = menubar;
	}

	private static JMenu createProjectMenu() {
		JMenu projectMenu = new JMenu("工程（P）");
		projectMenu.setMnemonic('P');
		projectMenu.add(Application.sysActionManager
				.getAction(WorkspaceManagerAction.class));
		if (Application.currentNode instanceof ProjectEntity) {
			ProjectEntity project = (ProjectEntity) Application.currentNode;
			projectMenu.add(project.getActions().get(3));
			projectMenu.add(project.getActions().get(4));
			projectMenu.add(project.getActions().get(5));
			projectMenu.add(project.getActions().get(6));
		}
		return projectMenu;
	}

	/**
	 * 视图菜单
	 * 
	 * @return
	 */
	private static JMenu createViewMenu() {
		JMenu viewMenu = new JMenu("视图（V）");
		viewMenu.setMnemonic('V');
		// helpMenu.add(ActionManager.getAction(HelpAction.class));
		return viewMenu;
	}

	/**
	 * 帮助菜单
	 * 
	 * @return
	 */
	private static JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu("帮助（H）");
		helpMenu.setMnemonic('H');
		helpMenu.add(Application.sysActionManager.getAction(AboutAction.class));
		helpMenu.add(Application.sysActionManager.getAction(HelpAction.class));
		return helpMenu;
	}

	/**
	 * 编辑菜单
	 * 
	 * @return
	 */
	private static JMenu createEditMenu() {
		JMenu editMenu = new JMenu("编辑（E）");
		editMenu.setMnemonic('E');
		if (Application.currentNode != null) {
			if (Application.currentNode instanceof ProjectEntity) {
				editMenu.add(Application.currentNode.getActions().get(2));
			} else if (Application.currentNode instanceof AndroidPageContainer) {
				for (int i = 2; i < Application.currentNode.getActions().size(); i++) {
					editMenu.add(Application.currentNode.getActions().get(i));
				}
			} else if (Application.currentNode instanceof BaseAndroidComponent) {
				for (int i = 0; i < Application.currentNode.getActions().size(); i++) {
					editMenu.add(Application.currentNode.getActions().get(i));
				}
			}
		}
		return editMenu;
	}

	/**
	 * 文件菜单
	 * 
	 * @return
	 */
	private static JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("文件（F）");
		fileMenu.setMnemonic('F');
		// 新建
		fileMenu.add(Application.sysActionManager
				.getAction(NewProjectMenuAction.class));
		fileMenu.add(Application.sysActionManager.getAction(OpenAction.class));
		if (Application.currentNode != null) {
			fileMenu.addSeparator();
			if (Application.currentNode instanceof ProjectEntity) {
				fileMenu.add(Application.currentNode.getActions().get(0));
				fileMenu.add(Application.currentNode.getActions().get(1));
				// fileMenu.add(Application.currentNode.getActionManager().getAction(SaveAction.class));
			} else if (Application.currentNode instanceof AndroidPageContainer) {
				// System.out.println(Application.currentNode + "-----");
				fileMenu.add(Application.currentNode.getActions().get(1));
				fileMenu.add(Application.currentNode.getActions().get(0));
			}
			fileMenu.addSeparator();
		}
		fileMenu.add(Application.sysActionManager.getAction(SaveAllAction.class));
		fileMenu.add(Application.sysActionManager.getAction(ExitAction.class));
		return fileMenu;
	}
}
