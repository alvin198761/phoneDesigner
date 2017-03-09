package com.android.lang;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.TreePath;

import application.SplashWindow;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.bean.ui.ProjectTreeNode;
import com.android.gui.drawpane.DrawPaneClipboard;
import com.android.gui.frame.CenterPanelManager;
import com.android.gui.frame.MainFrame;
import com.android.gui.frame.MenuBarManager;
import com.android.gui.frame.StatusbarManager;
import com.android.gui.frame.ToolbarManager;
import com.android.gui.model.ProjectTreeModel;
import com.android.utils.Log;
import com.dao.xml.CompilerBiz;
import com.dao.xml.DataIoBiz;
import com.dao.xml.EventDataIOBiz;
import com.dao.xml.impl.CompilerBizDom4jImpl;
import com.dao.xml.impl.DataIoBizDom4jImpl;
import com.dao.xml.impl.EventDataIOBizImpl;

/**
 * 应用级别的类
 * 
 * @author issuser
 * 
 */
public class Application {

	private Application() {

	}

	public static String version = "1.4";

	// 操作类型
	public static final String OPER_NONE = "none";
	public static final String OPER_MUILT_SELECT = "muilt_select";
	public static final String OPER_DRAWSELECTBOX = "drawselectbox";
	public static final String OPER_DRAGSHAPE = "drag";
	public static final String OPER_RESIZE = "resize";
	public static final String OPER_SELECTALL = "selectAll";
	// 工作目录
	public static File workSpace;
	// 剪贴板
	public static DrawPaneClipboard drawPaneClipBoard;
	// 保存文件的路径
	public static File saveFile;
	// 住窗体控件
	public static JFrame mainFrame;
	// 工具栏
	public static Box toolbarBox;
	// 底部的状态条
	public static Box statusBox;
	// 上面的菜单条
	public static JMenuBar menuBar;
	// 主操作面板
	public static JSplitPane mainSplitPanel;
	// 中心的面板
	public static JComponent contentPane;
	// 打开时，默认面板
	public static JPanel defaultPanel;
	// 保存类型
	public static String saveType;
	// 编辑器tab
	public static JTabbedPane tabPane;
	// 项目列表
	public static List<ProjectEntity> projects = new ArrayList<ProjectEntity>();
	// 项目树的model
	public static ProjectTreeModel treeModel = new ProjectTreeModel();
	// 当前选中的节点
	public static ProjectTreeNode currentNode;
	// 系统默认动作
	public static ActionManager sysActionManager;
	// 编译器，反编译器
	public static CompilerBiz compiler = new CompilerBizDom4jImpl();
	// 工程的保存和回显
	public static DataIoBiz dataio = new DataIoBizDom4jImpl();
	// 事件交互
	public static EventDataIOBiz eventDataIOBiz = new EventDataIOBizImpl();

	// 生成唯一ID
	public static String getTimeId() {
		return UUID.randomUUID().toString();
	}

	public static void start(SplashWindow s) {
		s.updateInfor("初始化剪贴板", 5);
		Application.drawPaneClipBoard = new DrawPaneClipboard();
		//
		s.updateInfor("初始化窗体", 5);
		Application.mainFrame = new MainFrame();
		// 实例化状态条
		s.updateInfor("初始化状态条", 5);
		StatusbarManager.createStatusBar();
		// 实例化菜单
		s.updateInfor("初始化菜单条", 5);
		sysActionManager = new ActionManager();
		MenuBarManager.createMenuBar();
		//
		s.updateInfor("初始化工具条", 5);
		Application.toolbarBox = Box.createHorizontalBox();
		ToolbarManager.createToolbar(null, toolbarBox);
		// 界面信息实例化
		s.updateInfor("初始化界面", 5);
		CenterPanelManager.createContentPanel();
		((MainFrame) Application.mainFrame).initGui();
		s.setVisible(false);
		s.updateInfor("加载工作空间中的项目", 5);
		loadWorkSpace(s);
		s.updateInfor("加载完成", 5);
		Application.mainFrame.setVisible(true);
		s = null;
		Log.info("界面启动成功");
	}

	/**
	 * 加载工作空间中所有的项目
	 * 
	 * @param s
	 */
	private static void loadWorkSpace(SplashWindow s) {
		File[] fs = workSpace.listFiles();
		if (fs == null) {
			return;
		}
		loop: for (File f : fs) {
			if (f.isFile()) {
				continue;
			}
			// 排除已经添加的项目
			for (ProjectEntity pr : projects) {
				if (pr.getName().equals(f.getName())) {
					continue loop;
				}
			}
			File xmlFile = new File(f.getAbsolutePath().concat(File.separator)
					.concat(DataIoBiz.project_file_conf));
			if (!xmlFile.exists()) {
				continue;
			}
			s.updateInfor("加载项目" + f.getName(), 2);
			try {
				final ProjectEntity project = dataio.readProject(f
						.getAbsolutePath());
				if (project == null) {
					return;
				}
				openProject(project);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 新建一个项目
	 * 
	 * @param project
	 */
	public static void addProject(ProjectEntity project) {
		projects.add(project);
		currentNode = project;
		// 新建文件夹
		new File(project.getProjectPath()).mkdirs();
		// 写入项目配置文件
		try {
			// 往tree节点中新建一个节点
			reInitGUI();
			CenterPanelManager.selectNode(new TreePath(new Object[] {
					treeModel.getRoot(), project }));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开项目
	 * 
	 * @param project
	 */
	public static void openProject(ProjectEntity project) {
		projects.add(project);
		currentNode = project;
		// 打开项目下所有的文件
		File projectDir = new File(project.getProjectPath()
				.concat(File.separator).concat("page").concat(File.separator));
		File[] fs = projectDir.listFiles();
		if (fs != null) {
			for (File f : fs) {
				if (!f.getName().toLowerCase().endsWith(".pag")) {
					continue;
				}
				AndroidPageContainer page;
				try {
					page = Application.dataio.readPage(f.getAbsolutePath());
					if (page == null) {
						continue;
					}
					project.addView(page);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		try {
			reInitGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CenterPanelManager.selectNode(new TreePath(new Object[] {
				treeModel.getRoot(), project }));
	}

	private static void reInitGUI() throws Exception {
		// 换内容
		treeModel.firePropertyChange();
		if (Application.defaultPanel != null) {
			//
			Application.contentPane.remove(Application.defaultPanel);
			Application.contentPane.add(Application.mainSplitPanel);
			Application.contentPane.updateUI();
			Application.defaultPanel = null;
		}
	}

	// /**
	// * 设置当前操作的面板
	// *
	// * @param drawPanel
	// */
	// public static void setCurrentDrawPanel(DrawPane drawPanel) {
	// Application.drawPanel = drawPanel;
	// // Application.image = drawPanel.image;
	// // Application.g2d = drawPanel.g2d;
	// Application.dragManager = new ShapeDragManager(Application.mainFrame,
	// Application.drawPanel);
	// Application.undoManager = Application.undoManagerMap.get(drawPanel);
	// try {
	// shapePanel.changeType("changeToAndroidDesginer");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// repaint();
	// }
	/**
	 * 根据页面找到对应的項目
	 * 
	 * @param page
	 * @return
	 */
	public static ProjectEntity getProjectByPage(AndroidPageContainer page) {
		for (ProjectEntity project : projects) {
			if (project.containsPage(page)) {
				return project;
			}
		}
		return null;
	}

	/***
	 * 获取到操作面板
	 * 
	 * @param baseAndroidComponent
	 * @return
	 */
	public static IDrawEditable getPageByComponent(
			BaseAndroidComponent baseAndroidComponent) {
		List<IDrawEditable> viewList = CenterPanelManager.getEditPage();
		for (IDrawEditable editor : viewList) {
			if (editor.getComponents().contains(baseAndroidComponent)) {
				return editor;
			}
		}
		return null;
	}

	public static Image getImage(BaseAndroidComponent component,
			String backgroudImage) {
		IDrawEditable page = null;
		if (component.getSourceComponent() != null) {
			page = getPageByComponent(component.getSourceComponent());
		} else {
			page = getPageByComponent(component);

		}
		ProjectEntity pro = null;
		if (page != null) {
			pro = getProjectByPage((AndroidPageContainer) page);
		} else {
			if (Application.currentNode instanceof ProjectEntity) {
				pro = (ProjectEntity) Application.currentNode;
			} else if (Application.currentNode instanceof AndroidPageContainer) {
				pro = getProjectByPage((AndroidPageContainer) Application.currentNode);
			} else if (Application.currentNode instanceof BaseAndroidComponent) {
				IDrawEditable editor = getPageByComponent((BaseAndroidComponent) Application.currentNode);
				pro = getProjectByPage((AndroidPageContainer) editor);
			}
		}
		String imagePath = pro.getProjectPath().concat(File.separator)
				.concat(backgroudImage);
		ImageIcon icon = new ImageIcon(imagePath);
		return icon.getImage();
	}
}
