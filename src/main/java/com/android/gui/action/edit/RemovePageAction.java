package com.android.gui.action.edit;

import java.awt.event.ActionEvent;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.gui.action.app.BaseAction;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;

/**
 * 删除视图
 * 
 * @author Administrator
 * 
 */
public class RemovePageAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private AndroidPageContainer page;

	public RemovePageAction(AndroidPageContainer page) {
		super("删除视图");
		this.page = page;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ProjectEntity project = Application.getProjectByPage(page);
		CenterPanelManager.removeView(project, this.page);
	}

}
