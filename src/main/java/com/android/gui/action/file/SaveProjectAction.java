package com.android.gui.action.file;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.gui.action.app.BaseAction;

/**
 * 保存项目
 * 
 * @author Administrator
 * 
 */
public class SaveProjectAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectEntity project;

	public SaveProjectAction(ProjectEntity prject) {
		super("保存项目");
		this.project = prject;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info(
				"点击保存项目 " + this.project.toString());
		if (this.project.isSaved()) {
			this.project.doSave();
		}
		for (int i = 0; i < project.getViewCount(); i++) {
			AndroidPageContainer page = project.getViewAt(i);
			if (!page.isSaved()) {
				page.doSave();
				page.setSaved(true);
			}
		}
		project.setSaved(true);
		firePropertyChange();
	}

	@Override
	public boolean isEnabled() {
		return !this.project.isSaved();
	}

}
