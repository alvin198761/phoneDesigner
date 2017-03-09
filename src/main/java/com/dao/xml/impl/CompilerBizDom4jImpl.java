package com.dao.xml.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.entity.ResEntity;
import com.android.bean.shape.ShapeHelper;
import com.android.bean.shape.data.comp.AndroidButton;
import com.android.bean.shape.data.comp.AndroidImageView;
import com.android.bean.shape.data.comp.AndroidOsShapeButton;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.AndroidTextLable;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.shape.data.comp.BaseAndroidContainer;
import com.android.utils.FileUtils;
import com.android.utils.XMLUtil;
import com.android.utils.ZipUtils;
import com.dao.xml.CompilerBiz;
import com.runner.entity.ManuActionItemDTO;
import com.runner.entity.OntouchAction;

import javax.swing.*;

/**
 * 
 * 项目编译器，这里是用dom4j实现
 * 
 * @author Administrator
 * 
 */
public class CompilerBizDom4jImpl implements CompilerBiz {
	@Override
	public boolean exportZIP(String compilerPath, String exportPath) {
		JOptionPane.showMessageDialog(null,"不能公开，已经删除");
		return false;
	}

	@Override
	public boolean importZip(String zipProjectPath, String targetDir) {
		return false;
	}

	@Override
	public boolean compiler(ProjectEntity project) {
		JOptionPane.showMessageDialog(null,"不能公开，已经删除");
		return false;
	}

	@Override
	public ProjectEntity deComipler(String proDir) {
		return null;
	}

	@Override
	public AndroidPageContainer deCompilerPage(String pageXML) {
		return null;
	}

	@Override
	public void compilerPage(ProjectEntity project, AndroidPageContainer androidPageContainer) throws Exception {

	}
}
