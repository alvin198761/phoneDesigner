package com.dao.xml;

import java.util.List;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.AndroidTempletView;
import com.android.bean.shape.data.comp.BaseAndroidComponent;

/**
 * 数据保存的接口
 * 
 * @author Administrator
 * 
 */
public interface DataIoBiz {
	//
	String project_file_conf = "project.conf";

	/**
	 * 保存项目
	 * 
	 * @param project
	 */
	void saveProject(ProjectEntity project) throws Exception;

	/**
	 * 读取项目
	 * 
	 * @param projectDir
	 * @return
	 */
	ProjectEntity readProject(String projectDir) throws Exception;

	/**
	 * 保存视图
	 * 
	 * @param project
	 * 
	 * @param projectDir
	 * @return
	 */
	void savePage(ProjectEntity project, AndroidPageContainer page)
			throws Exception;

	/**
	 * 读取页面
	 * 
	 * @param pageXML
	 * @return
	 */
	AndroidPageContainer readPage(String pageXML) throws Exception;

	/**
	 * 保存模板
	 * 
	 * @param projectDir
	 * @return
	 */
	void saveTempletView(AndroidTempletView templetView) throws Exception;

	/**
	 * 读取模板
	 * 
	 * @param templetFile
	 * @return
	 */
	AndroidTempletView readTempletView(String templetFile) throws Exception;

	/**
	 * 创建项目
	 * 
	 * @param project
	 * @return
	 * @throws Exception
	 */
	int createProject(ProjectEntity project) throws Exception;

	/**
	 * 克隆图形列表
	 * 
	 * @param components
	 * @return
	 * @throws Exception
	 */
	List<BaseAndroidComponent> cloneComponents(
			List<BaseAndroidComponent> components) throws Exception;

	/**
	 * 克隆一个图形
	 * 
	 * @param comp
	 * @return
	 * @throws Exception
	 */
	BaseAndroidComponent cloneComponent(BaseAndroidComponent comp) throws Exception;
}
