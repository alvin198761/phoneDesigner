package com.android.gui.model;

import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import com.android.bean.entity.ProjectEntity;
import com.android.lang.Application;
import com.runner.entity.ManuEntity;
import com.runner.entity.ManualActionEntity;
/**
 * 
 * @author Administrator
 *
 */
public class ActionCMDListMode implements ListModel {

	private List<ManuEntity> content;

	public ActionCMDListMode(ProjectEntity project, ManualActionEntity action) {
		content = Application.eventDataIOBiz.loadManuEntity(project);
	}

	@Override
	public int getSize() {
		return content.size();
	}

	@Override
	public Object getElementAt(int index) {
		return content.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub

	}

}
