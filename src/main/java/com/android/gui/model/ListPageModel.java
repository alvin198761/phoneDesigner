package com.android.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.runner.entity.PageActionEntity;

public class ListPageModel implements ListModel {

	
	private List<AndroidPageContainer> viewList = new ArrayList<AndroidPageContainer>();

	public ListPageModel(ProjectEntity project,AndroidPageContainer page, PageActionEntity action) {
		for(int i= 0 ; i < project.getViewCount();i++){
			if(project.getViewAt(i).equals(page)){
				continue;
			}
			viewList.add(project.getViewAt(i));
		}
		
	}

	@Override
	public int getSize() {
		return viewList.size();
	}

	@Override
	public Object getElementAt(int index) {
		return viewList.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {

	}

	@Override
	public void removeListDataListener(ListDataListener l) {

	}

}
