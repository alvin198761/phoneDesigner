package com.android.bean.entity;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.android.bean.BaseObject;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.ui.ISavable;
import com.android.bean.ui.ProjectTreeNode;
import com.android.gui.action.app.BaseAction;
import com.android.gui.action.app.MenuAction;
import com.android.gui.action.file.RemoveProjectAction;
import com.android.gui.action.file.SaveProjectAction;
import com.android.gui.action.newfile.NewAndroidViewMenuAction;
import com.android.gui.action.project.CompilerProjectAction;
import com.android.gui.action.project.ExportProjectToZipAction;
import com.android.gui.action.project.RunProjectAction;
import com.android.gui.action.project.StopRunProjectAction;
import com.android.gui.property.ProjectPropertiesPanel;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

/**
 * 项目对象
 * 
 * @author Administrator
 * 
 */
public class ProjectEntity extends BaseObject implements ProjectTreeNode,
		ISavable {

	public static final String projectSavedChange = "projectSavedChange";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 项目路径
	private String projectPath;
	// 分辨率
	private ResEntity res;
	// 操作系统
	private int osType;
	// 视图列表
	private List<AndroidPageContainer> viewList = new LinkedList<AndroidPageContainer>();
	// 动作
	private List<BaseAction> actions;
	// 是否显示该项目，如果后期有关闭项目的功能，那么，关闭的项目需要打开才能显示
	private boolean show;
	// 项目熟悉感模板
	private ProjectPropertiesPanel propertiesPanel;
	// 是否全屏 0=yes 1=no
	private int fullscreen = 0;
	// 显示导航
	private boolean showSiteMap = false;
	// 是否运行
	private boolean running = false;
	// 是否保存
	private boolean saved = true;

	public ProjectEntity() {
		addPropertyChangeListener("propertyChange",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						doSave();
					}
				});
	}

	public int getFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(int fullscreen) {
		this.fullscreen = fullscreen;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isSaved() {
		for (AndroidPageContainer page : this.viewList) {
			if (!page.isSaved()) {
				return false;
			}
		}
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
		firePropertyChange(projectSavedChange, this, false);
		fireActionChange();
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public int getOsType() {
		return osType;
	}

	public ResEntity getRes() {
		return res;
	}

	public void setRes(ResEntity res) {
		this.res = res;
	}

	public void setOsType(int osType) {
		this.osType = osType;
	}

	public boolean isShowSiteMap() {
		return showSiteMap;
	}

	public void setShowSiteMap(boolean showSiteMap) {
		this.showSiteMap = showSiteMap;
	}

	public void setViewList(List<AndroidPageContainer> viewList) {
		this.viewList = viewList;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public List<BaseAction> getActions() {
		if (actions == null) {
			actions = new ArrayList<BaseAction>();
			actions.add(new NewAndroidViewMenuAction(this));
			actions.add(new SaveProjectAction(this));
			actions.add(new RemoveProjectAction(this));
			MenuAction menuAction = new MenuAction("运行");
			actions.add(menuAction);
			RunProjectAction action = new RunProjectAction(this, 100);
			menuAction.getActions().add(action);
			action = new RunProjectAction(this, 75);
			menuAction.getActions().add(action);
			action = new RunProjectAction(this, 50);
			menuAction.getActions().add(action);
			action = new RunProjectAction(this, 25);
			menuAction.getActions().add(action);
			actions.add(new StopRunProjectAction(this));
			actions.add(new CompilerProjectAction(this));
			actions.add(new ExportProjectToZipAction(this));
		}
		return actions;
	}

	@Override
	public Icon getIcon() {
		return ResourceUtil.proj_icon;
	}

	@Override
	public void doSave() {
		try {
			Application.dataio.saveProject(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doRead(File file) {
		ProjectEntity project = null;
		try {
			project = Application.dataio.readProject(this.projectPath);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		this.setSaved(true);
		this.setName(project.getName());
		this.setProjectPath(project.getProjectPath());
		this.setOsType(project.getOsType());
		this.setRes(project.getRes());
		this.setShow(project.isShow());
		this.setViewList(project.viewList);
	}

	public AndroidPageContainer getRootPage() {
		for (AndroidPageContainer page : this.viewList) {
			if (page.isRoot()) {
				return page;
			}
		}
		return null;
	}

	@Override
	public JComponent getPropertiesComponent() {
		if (propertiesPanel == null) {
			propertiesPanel = new ProjectPropertiesPanel(this);
		}
		propertiesPanel.initData();
		return propertiesPanel;
	}

	@Override
	public void setSelect(boolean select) {

	}

	public void addView(AndroidPageContainer page) {
		this.viewList.add(page);
		firePropertyChange("addPageViewChange", true, false);
		fireActionChange();
	}

	public boolean containsPage(AndroidPageContainer page) {
		return this.viewList.contains(page);
	}

	public AndroidPageContainer getViewAt(int index) {
		return this.viewList.get(index);
	}

	public int getViewCount() {
		return this.viewList.size();
	}

	public int viewIndexOf(Object child) {
		return this.viewList.indexOf(child);
	}

	/**
	 * 设置根视图
	 * 
	 * @param page
	 */
	public void setRootPage(AndroidPageContainer page, boolean flag) {
		for (AndroidPageContainer p : this.viewList) {
			p.setRoot(false);
			p.setSaved(flag);
		}
		page.setRoot(true);
		this.setSaved(flag);
		fireActionChange();
	}

	private void fireActionChange() {
		for (BaseAction action : this.getActions()) {
			action.firePropertyChange();
		}
	}

	public void setMode(String mode) {
		for (int i = 0; i < getViewCount(); i++) {
			for (int j = 0; j < getViewAt(i).getComponentCount(); j++) {
				getViewAt(i).getComponentAt(j).setModel(mode);
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
		fireActionChange();
	}

	public boolean pageAllSaved() {
		for (AndroidPageContainer page : this.viewList) {
			if (!page.isSaved()) {
				return false;
			}
		}
		return true;
	}

	public void addOpenView(AndroidPageContainer page) {
		this.viewList.add(page);
	}

	public void removeView(AndroidPageContainer page) {
		this.viewList.remove(page);
	}

}
