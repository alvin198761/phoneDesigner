package com.android.gui.action.file;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.gui.action.app.BaseAction;
import com.android.lang.Application;
import com.android.lang.SMMSystem;

public class ExitAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	public ExitAction() {
		super("退出（E）");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击退出动作");
		List<ProjectEntity> nosaveProjects = new ArrayList<ProjectEntity>();
		String savedProjects = "";
		for (ProjectEntity project : Application.projects) {
			if (project.isSaved()) {
				continue;
			}
			savedProjects += "," + project.toString();
			nosaveProjects.add(project);
		}
		if (!nosaveProjects.isEmpty()) {
			String text = "一下项目未保存：[" + savedProjects.substring(1) + "]";
			int res = JOptionPane.showConfirmDialog(null, text, "confirm",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (res == JOptionPane.YES_OPTION) {
				for (ProjectEntity project : nosaveProjects) {
					project.doSave();
				}
			} else if (res == JOptionPane.CANCEL_OPTION) {
				return;
			}

		}
		SMMSystem.saveConfig();
		System.exit(0);
	}

}
