package com.dao.xml;

import java.util.List;

import com.android.bean.entity.ProjectEntity;
import com.runner.entity.EventTreeNode;
import com.runner.entity.ManuEntity;

public interface EventDataIOBiz {
	/**
	 * 加载动作信息
	 * 
	 * @param project
	 * @return
	 */
	List<EventTreeNode> loadTreeNodes(ProjectEntity project);

	/**
	 * 加载交互对象
	 * 
	 * @param project
	 * @return
	 */
	List<ManuEntity> loadManuEntity(ProjectEntity project);

}
