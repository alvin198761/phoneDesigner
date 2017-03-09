package com.android.gui.action.newfile;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.UUID;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.gui.action.app.BaseAction;
import com.android.gui.dialog.NewProjectDialog;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;
import com.android.utils.DialogUtil;
import com.dao.xml.DataIoBiz;

/**
 * 新建工程用的动作
 * 
 * @author Administrator
 * 
 */
public class NewProjectMenuAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewProjectMenuAction() {
		super(ResourceUtil.new_icon, "新建项目（P）");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击新建项目");
		// 检查原来的项目是不是已经保存
		NewProjectDialog dialog = new NewProjectDialog(Application.mainFrame);
		dialog.setLocationRelativeTo(Application.mainFrame);
		dialog.setVisible(true);
		ProjectEntity project = dialog.getProject();
		if (project == null) {
			return;
		}
		// 判断项目是否存在
		if (checkExistProject(project)) {
			DialogUtil.promptWarning("该项目已经存在！");
			return;
		}
		project.setSaved(false);
		try {
			project.setId(UUID.randomUUID().toString());
			int res = Application.dataio.createProject(project);
			Application.addProject(project);
			CenterPanelManager.consoleLog("新建项目 '" + project.getName() + "' --"
					+ res);
		} catch (Exception e1) {
			e1.printStackTrace();
			CenterPanelManager
					.consoleLog("新建项目 '" + project.getName() + "' 失败");
		}

	}

	private boolean checkExistProject(ProjectEntity project) {
		for (ProjectEntity pro : Application.projects) {
			if (pro.getName().equals(project.getName())) {
				return true;
			}
		}
		String path = Application.workSpace.getAbsolutePath()
				.concat(File.separator).concat(project.getName())
				.concat(File.separator).concat(project.getName());
		path = path.concat(File.separator).concat(DataIoBiz.project_file_conf);
		return new File(path).exists();
	}

}
