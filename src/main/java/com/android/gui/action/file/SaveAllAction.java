package com.android.gui.action.file;

import java.awt.event.ActionEvent;

import com.android.bean.entity.ProjectEntity;
import com.android.gui.action.app.BaseAction;
import com.android.lang.Application;
/**
 * 保存所有的项目
 * @author Administrator
 *
 */
public class SaveAllAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaveAllAction() {
		super("保存所有");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(ProjectEntity project : Application.projects){
			if(!project.isSaved()){
				project.doSave();
			}
		}
		firePropertyChange();
	}
	
	@Override
	public boolean isEnabled() {
		for(ProjectEntity project : Application.projects){
			if(!project.isSaved()){
				return true;
			}
		}
		return false;
	}

}
