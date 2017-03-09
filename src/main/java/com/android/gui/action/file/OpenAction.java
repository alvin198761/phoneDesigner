package com.android.gui.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.gui.action.app.BaseAction;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;
import com.android.utils.DialogUtil;
import com.dao.xml.DataIoBiz;

public class OpenAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public OpenAction() {
		super(ResourceUtil.open_icon, "打开（O）");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击打开动作");
		// 到工作目录下吧所有的目录都读一遍，找到所有的project文件
		File[] fs = Application.workSpace.listFiles();
		if (fs == null || fs.length == 0) {
			DialogUtil.promptWarning("工作目录 "
					+ Application.workSpace.getAbsolutePath() + " 没有工程！");
			Logger.getLogger(this.getClass().getName()).info("工作空间下没有工程");
			return;
		}
		Vector<String> projects = new Vector<String>();
		for (File f : fs) {
			if (f.isFile()) {
				continue;
			}

			if (new File(f.getAbsolutePath().concat(File.separator)
					.concat(DataIoBiz.project_file_conf)).exists()) {
				projects.add(f.getName());
			}
		}
		// 如果CompilerBiz.project_file文件解析成功，就添加一个工程
		// 如果所有的工程都已将在tree中，就弹出提示
		// 弹出一个选择框，下拉选中一个工程
		if (projects.isEmpty()) {
			DialogUtil.promptWarning("工作目录 "
					+ Application.workSpace.getAbsolutePath() + " 没有工程！");
			return;
		}
		Object projectName = JOptionPane.showInputDialog(Application.mainFrame,
				"工作目录中所有工程：", "请选择工程", JOptionPane.INFORMATION_MESSAGE, null,
				projects.toArray(), projects.get(0));
		if (projectName == null) {
			return;
		}
		// 项目文件路径
		String proDir = Application.workSpace.getAbsolutePath()
				.concat(File.separator).concat(projectName.toString());
		try {
			final ProjectEntity project = Application.dataio
					.readProject(proDir);
			Application.openProject(project);
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					String pageDir = project.getProjectPath()
							.concat(File.separator).concat("page");
					File[] pf = new File(pageDir).listFiles();
					for (File f : pf) {
						if(f.isDirectory()){
							continue;
						}
						if(!f.getName().toLowerCase().endsWith(".pag")){
							continue;
						}
						AndroidPageContainer page;
						try {
							page = Application.dataio.readPage(f
									.getAbsolutePath());
							project.addOpenView(page);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
