package com.android.gui.action.newfile;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.gui.action.app.BaseAction;
import com.android.gui.dialog.NewAndroidViewDialog;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;
import com.android.utils.DialogUtil;

public class NewAndroidViewMenuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectEntity project;

	public NewAndroidViewMenuAction(ProjectEntity project) {
		super(ResourceUtil.new_icon, "添加视图（V）");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift N"));
		this.project = project;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击新建页面");
		NewAndroidViewDialog dialog = new NewAndroidViewDialog(
				Application.mainFrame);
		dialog.setLocationRelativeTo(Application.mainFrame);
		dialog.setVisible(true);
		AndroidPageContainer entity = dialog.getView();
		if (entity == null) {
			return;
		}
		for (int i = 0; i < project.getViewCount(); i++) {
			AndroidPageContainer page = project.getViewAt(i);
			if (page.getName().equals(entity.getName())) {
				DialogUtil.promptWarning("改视图已经存在！");
				return;
			}
		}
		project.addView(entity);
		if (dialog.isRootView()) {
			project.setRootPage(entity,false);
		}
		project.setSaved(false);
		try {
			Application.dataio.savePage(project, entity);
			Application.treeModel.firePropertyChange();
			CenterPanelManager.selectNode(new TreePath(new Object[] {
					Application.treeModel.getRoot(), project, entity }));
			CenterPanelManager.consoleLog("创建  '" + entity.getName() + "'");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public boolean isEnabled() {
		return true;
	}
}
