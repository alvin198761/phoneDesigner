package com.dao.xml;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;

/**
 * 编译接口
 * 
 * @author Administrator
 * 
 */
public interface CompilerBiz {

	String project_file_xml = "appmain.xml";

	/**
	 * 打包成zip
	 * 
	 * @param compilerPath
	 * @param exportPath
	 * @return
	 */
	boolean exportZIP(String compilerPath, String exportPath);

	/**
	 * 解压zip项目
	 * 
	 * @param zipProjectPath
	 * @param targetDir
	 * @return
	 */
	boolean importZip(String zipProjectPath, String targetDir);

	/**
	 * 编译接口
	 * 
	 * @param project
	 * @return
	 */
	boolean compiler(ProjectEntity project);

	/**
	 * 反编译项目
	 * 
	 * @param proDir
	 * @return
	 */
	ProjectEntity deComipler(String proDir);

	/**
	 * 反编译页面
	 * 
	 * @param pageXML
	 * @return
	 */
	AndroidPageContainer deCompilerPage(String pageXML);

	/**
	 * 编译某个页面
	 * @param project
	 * @param androidPageContainer
	 * @throws Exception
	 */
	void compilerPage(ProjectEntity project,
					  AndroidPageContainer androidPageContainer)throws Exception ;

}
