package com.android.gui.action.file;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.gui.action.app.BaseAction;
import com.android.gui.dialog.RemoveProjectDialog;
import com.android.gui.frame.CenterPanelManager;
import com.android.utils.FileUtils;

/**
 * 删除项目功能
 * 
 * @author Administrator
 * 
 */
public class RemoveProjectAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectEntity project;

	public RemoveProjectAction(ProjectEntity project) {
		super("删除项目");
		this.project = project;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击删除项目");
		RemoveProjectDialog dialog = new RemoveProjectDialog(project);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		if (dialog.getDialogStatus() == 0) {
			return;
		}
		if (dialog.isDeleteDir()) {
			FileUtils.removeDir(project.getProjectPath());
		} else {
			project.setShow(false);
		}
		CenterPanelManager.removeProject(project);
	}

}
