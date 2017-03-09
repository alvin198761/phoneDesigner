package com.android.gui.action.project;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.gui.action.app.BaseAction;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;
import com.android.utils.DialogUtil;
import com.runner.gui.RunnerDialog;

/**
 * 项目运行
 * 
 * @author Administrator
 * 
 */
public class RunProjectAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectEntity project;
	private double scale;

	public RunProjectAction(ProjectEntity project, int scale) {
		super(scale + "%");
		this.project = project;
		this.scale = scale * 1.0 / 100;
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("f5"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!project.isSaved()) {
			DialogUtil.promptError("项目中有需要保存的页面，请先保存！");
			return;
		}
		if (project.getViewCount() == 0) {
			DialogUtil.promptError("没有可以运行的视图！");
			return;
		}
		if (project.getRootPage() == null) {
			DialogUtil.promptError("请设置root视图，不然没有运行入口！");
			return;
		}
		// if (project.isRunning()) {
		// DialogUtil.promptError("项目正在运行！");
		// return;
		// }
		final RunnerDialog dialog = new RunnerDialog();
		project.addPropertyChangeListener("stopRunning",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						project.setRunning(false);
						dialog.setVisible(false);
						dialog.dispose();
					}
				});
		dialog.setVisible(true);
		try {
			dialog.setTitle("正在运行：" + project.getProjectPath());
			CenterPanelManager.consoleLog("开始解析。。。");
			ProjectEntity compilerProject = Application.dataio
					.readProject(project.getProjectPath());
			//
			String pageDir = compilerProject.getProjectPath()
					.concat(File.separator).concat("page");
			File[] pf = new File(pageDir).listFiles();
			for (File f : pf) {
				if (f.isDirectory()) {
					continue;
				}
				if (!f.getName().toLowerCase().endsWith(".pag")) {
					continue;
				}
				AndroidPageContainer page;
				try {
					page = Application.dataio.readPage(f.getAbsolutePath());
					compilerProject.addOpenView(page);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			for (int i = 0; i < compilerProject.getViewCount(); i++) {
				if (compilerProject.getViewAt(i).getName()
						.equals(project.getRootPage().getName())) {
					compilerProject.setRootPage(compilerProject.getViewAt(i),
							true);
					break;
				}
			}
			CenterPanelManager.consoleLog("解析完成，开始运行");
			project.setRunning(true);
			dialog.run(compilerProject, project,scale);
		} catch (Exception e1) {
			e1.printStackTrace();
			Logger.getLogger(this.getClass()).error("运行出错", e1);
		}
		dialog.setAlwaysOnTop(true);
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
