package com.android.gui.action.project;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.android.bean.entity.ProjectEntity;
import com.android.gui.action.app.BaseAction;
import com.android.lang.Application;

/**
 * 导出zip文件
 * 
 * @author Administrator
 * 
 */
public class ExportProjectToZipAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectEntity project;

	public ExportProjectToZipAction(ProjectEntity project) {
		super("导出ZIP");
		this.project = project;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileFilter(new FileNameExtensionFilter("*.zip File", "zip"));
		jfc.showSaveDialog(Application.mainFrame);
		if (jfc.getSelectedFile() == null) {
			return;
		}
		String path = jfc.getSelectedFile().getAbsolutePath();
		if (!path.endsWith(".zip")) {
			path += ".zip";
		}
		String binDir = this.project.getProjectPath().concat(File.separator)
				.concat("app");
		Application.compiler.exportZIP(binDir, path);
	}

}
