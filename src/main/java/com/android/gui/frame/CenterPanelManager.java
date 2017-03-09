package com.android.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.bean.ui.ProjectTreeNode;
import com.android.gui.action.app.BaseAction;
import com.android.gui.action.app.MenuAction;
import com.android.gui.action.file.OpenAction;
import com.android.gui.action.newfile.NewProjectMenuAction;
import com.android.gui.comp.JTitlePanel;
import com.android.gui.comp.JXTabbedPane;
import com.android.gui.drawpane.DrawPane;
import com.android.gui.layout.Left_RightLayoutPanel;
import com.android.gui.layout.Right_LeftLayoutPanel;
import com.android.gui.layout.Top_BottomLayoutPanel;
import com.android.lang.Application;

/**
 * 中间的操作去面板管理
 * 
 * @author issuser
 * 
 */
public class CenterPanelManager {

	private static List<IDrawEditable> pageViewList = new ArrayList<IDrawEditable>();
	public static JSplitPane shapeSplitPanel;
	public static JSplitPane operationSplitPanel;
	public static JPanel propertyPanel;

	public static Left_RightLayoutPanel left_RightLayoutPanel;
	public static Right_LeftLayoutPanel right_LeftLayoutPanel;
	public static Top_BottomLayoutPanel top_BottomLayoutPanel;
	
	public static Map<JComponent,JScrollPane> editorMap  = new HashMap<JComponent, JScrollPane>();
	// 中间是属性，画图，控制台
	public static JPanel mainPanel = new JPanel();

	private CenterPanelManager() {
	}

	private static JTree projectTree;
	public static JTextArea console;
	// private static JScrollPane propertiesScrollPane;
	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-DD HH-mm-ss");

	private static JLabel projectLabel;
	private static JLabel sizeLabel;
	private static JLabel locationLabel;

	public static void createContentPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));

		JPanel defaultPanel = new JPanel();
		defaultPanel.setBackground(Color.gray);

		JSplitPane mainSplitPanel = new JSplitPane();
		mainSplitPanel.setOneTouchExpandable(true);
		mainSplitPanel.setBorder(null);
		//
		left_RightLayoutPanel = new Left_RightLayoutPanel(mainSplitPanel);
		//
		right_LeftLayoutPanel = new Right_LeftLayoutPanel(mainSplitPanel);
		//
		top_BottomLayoutPanel = new Top_BottomLayoutPanel(mainSplitPanel);

		// mainSplitPanel.setDividerLocation(200);
		JTabbedPane leftTabPane = new JTabbedPane();
		leftTabPane.setBorder(null);
		// 左边是工程管理和控件栏
		shapeSplitPanel = new JSplitPane();
		shapeSplitPanel.setOneTouchExpandable(true);
		shapeSplitPanel.setDividerSize(15);
		shapeSplitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		projectTree = new JTree(Application.treeModel);
		initTree(projectTree);
		projectTree.setRootVisible(false);
		shapeSplitPanel.setTopComponent(new JTitlePanel("工程列表",
				new JScrollPane(projectTree)));
		shapeSplitPanel.setBottomComponent(new JPanel());
		shapeSplitPanel.setDividerLocation(400);
		// mainSplitPanel.setLeftComponent(shapeSplitPanel);

		mainPanel.setLayout(new BorderLayout());
		// 画图
		operationSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		operationSplitPanel.setOneTouchExpandable(true);
		operationSplitPanel.setDividerSize(15);

		Application.tabPane = new JXTabbedPane();
		initTab(Application.tabPane);
		mainPanel.add(operationSplitPanel);
		// 控制台
		operationSplitPanel.setDividerLocation(550);
		operationSplitPanel.setTopComponent(Application.tabPane);
		console = new JTextArea(2, 10);
		console.setEditable(false);
		console.setLineWrap(true);
		operationSplitPanel.setBottomComponent(console);
		// 属性
		JPanel properties = new JPanel();
		properties.setPreferredSize(new Dimension(200, 100));
		propertyPanel = new JPanel(new BorderLayout());
		propertyPanel.setBorder(BorderFactory.createEtchedBorder());
		// propertiesScrollPane = new JScrollPane();
		mainPanel.add(propertyPanel, BorderLayout.EAST);
		propertyPanel.add(new JTitlePanel("属性列表", properties),
				BorderLayout.CENTER);

		// mainSplitPanel.setRightComponent(mainPanel);
		left_RightLayoutPanel.doChangeLayout();
		// right_LeftLayoutPanel.doChangeLayout( );
		// top_BottomLayoutPanel.doChangeLayout( );
		// 右边没有

		// 上面是工具条
		panel.add(Application.toolbarBox, BorderLayout.NORTH);
		// 下面是状态条
		panel.add(Application.statusBox, BorderLayout.SOUTH);
		// 中间是分割栏
		panel.add(defaultPanel, BorderLayout.CENTER);
		// 地下室状态条
		JToolBar statusBar = new JToolBar();
		statusBar.setFloatable(false);
		statusBar.add(new JLabel("    "));
		projectLabel = new JLabel();
		projectLabel.setSize(300, 25);
		statusBar.add(projectLabel);
		statusBar.addSeparator();
		statusBar.add(new JLabel("    "));
		sizeLabel = new JLabel();
		sizeLabel.setSize(300, 25);
		statusBar.add(sizeLabel);
		statusBar.addSeparator();
		statusBar.add(new JLabel("    "));
		locationLabel = new JLabel();
		locationLabel.setSize(200, 25);
		statusBar.add(locationLabel);
		panel.add(statusBar, BorderLayout.SOUTH);
		Application.contentPane = panel;
		// Application.contentPane = mainSplitPanel;
		Application.mainSplitPanel = mainSplitPanel;
		Application.defaultPanel = defaultPanel;
	}

	private static void initTab(final JTabbedPane tabPane) {
		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabPane.getSelectedIndex();
				if (index == -1) {
					return;
				}
				IDrawEditable view = pageViewList.get(index);
				try {
					view.getComponentsView().changeType(
							"changeToAndroidDesginer", view);
					CenterPanelManager.changeComponentView(view);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Application.mainFrame.setGlassPane(view.getDragManager()
						.getGlass());
				view.repaint();
			}
		});

	}

	private static void initTree(final JTree projectTree) {
		projectTree.getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener() {

					@Override
					public void valueChanged(TreeSelectionEvent e) {
						Application.currentNode = (ProjectTreeNode) projectTree
								.getLastSelectedPathComponent();
						MenuBarManager.createMenuBar();
						Application.mainFrame.setJMenuBar(Application.menuBar);
						Application.mainFrame.getJMenuBar().updateUI();
						ToolbarManager.createToolbar(Application.currentNode,
								Application.toolbarBox);
						if (Application.currentNode != null) {
							if (Application.currentNode instanceof BaseAndroidComponent
									&& !(Application.currentNode instanceof AndroidPageContainer)) {
								BaseAndroidComponent component = ((BaseAndroidComponent) Application.currentNode);
								IDrawEditable page = Application
										.getPageByComponent(component);
								if (page != null && pageViewList.contains(page)) {
									changePropertyPane(Application.currentNode);
									page.clearSelect();
									component.setSelect(true);
									page.setCurrentSelectShape(component);
									page.repaint();
									// 打开页面
									openWithEditor(page);
								}
							} else if (Application.currentNode instanceof ProjectEntity) {
								changePropertyPane(Application.currentNode);
								setProject((ProjectEntity) Application.currentNode);
								setProjectSize((ProjectEntity) Application.currentNode);
							} else if (Application.currentNode instanceof AndroidPageContainer) {
								changePropertyPane(Application.currentNode);
								openWithEditor((IDrawEditable) Application.currentNode);
							}
						}
					}
				});

		projectTree.addMouseListener(new MouseAdapter() {
			//
			// @Override
			// public void mouseClicked(MouseEvent e) {
			// if (e.isMetaDown()) {
			// return;
			// }
			// if (e.getClickCount() < 2) {
			// return;
			// }
			// TreePath path = projectTree.getPathForLocation(e.getPoint().x,
			// e.getPoint().y);
			// if (path == null) {
			// return;
			// }
			// // 如果选中的是页面
			// if (path.getLastPathComponent() instanceof AndroidPageContainer)
			// {
			// // 双击一个视图节点
			// // 获取视图节点对应的画图面板
			// // 如果获取到，就选中
			// // 如果没有获取到，就新建一个，添加进来
			// AndroidPageContainer view = (AndroidPageContainer) path
			// .getLastPathComponent();
			// openWithEditor(view);
			// }
			// }

			@Override
			public void mousePressed(MouseEvent e) {
				if (!e.isMetaDown()) {
					return;
				}
				TreePath path = projectTree.getPathForLocation(e.getX(),
						e.getY());
				if (path == null) {
					getMenu(null).show(projectTree, e.getX(), e.getY());
					return;
				}
				projectTree.setSelectionPath(path);
				if (e.getButton() == 3) {
					getMenu((ProjectTreeNode) path.getLastPathComponent())
							.show(projectTree, e.getX(), e.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				this.mousePressed(e);
			}

			private JPopupMenu getMenu(ProjectTreeNode node) {
				JPopupMenu popuMenu = new JPopupMenu();
				if (node == null) {
					popuMenu.add(new NewProjectMenuAction());
					popuMenu.add(new OpenAction());
				} else {
					for (BaseAction action : node.getActions()) {
						if (action instanceof MenuAction) {
							popuMenu.add(createMenuAction((MenuAction) action));
							continue;
						}
						popuMenu.add(action);
					}
				}
				return popuMenu;
			}

		});

		projectTree.setCellRenderer(new DefaultTreeCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree,
					Object value, boolean sel, boolean expanded, boolean leaf,
					int row, boolean hasFocus) {
				JLabel lal = (JLabel) super.getTreeCellRendererComponent(tree,
						value, sel, expanded, leaf, row, hasFocus);
				ProjectTreeNode node = (ProjectTreeNode) value;
				lal.setIcon(node.getIcon());
				lal.setText(node.toString());
				lal.setPreferredSize(new Dimension(200, 25));
				return lal;
			}

		});

		projectTree.setShowsRootHandles(true);
	}

	public static JMenu createMenuAction(MenuAction menuAction) {
		JMenu menu = new JMenu(menuAction);
		for (Action action : menuAction.getActions()) {
			if (action instanceof MenuAction) {
				menu.add(createMenuAction((MenuAction) action));
				continue;
			}
			menu.add(action);
		}
		return menu;
	}

	protected static void changeComponentView(IDrawEditable view) {
		shapeSplitPanel.setBottomComponent(view.getComponentsView());
		shapeSplitPanel.updateUI();
	}

	public static void selectNode(TreePath path) {
		projectTree.setSelectionPath(path);
	}

	public static void moveUp(ProjectEntity project, IDrawEditable page,
			BaseAndroidComponent component) {
		int index = page.getComponents().indexOf(component);
		page.getComponents().remove(index);
		page.getComponents().add(index - 1, component);
		Application.treeModel.firePropertyChange(new Object[] {
				Application.treeModel.getRoot(), project, page });
		TreePath path = new TreePath(new Object[] {
				Application.treeModel.getRoot(), project, page, component });
		projectTree.collapsePath(path);
		selectNode(path);
	}

	public static void moveDown(ProjectEntity project, IDrawEditable page,
			BaseAndroidComponent component) {
		int index = page.getComponents().indexOf(component);
		page.getComponents().remove(index);
		page.getComponents().add(index + 1, component);
		Application.treeModel.firePropertyChange(new Object[] {
				Application.treeModel.getRoot(), project, page });
		TreePath path = new TreePath(new Object[] {
				Application.treeModel.getRoot(), project, page, component });
		projectTree.collapsePath(path);
		selectNode(path);
	}

	public static void removeEditor(int index) {
		if (index == -1) {
			return;
		}
		IDrawEditable editor = pageViewList.remove(index);
		if (editor == null) {
			return;
		}
		editor.disposeDraw();
	}

	/**
	 * 打开到编辑器
	 * 
	 * @param editor
	 */
	public static void openWithEditor(IDrawEditable editor) {
		if (pageViewList.contains(editor)) {
			// 选中
			Application.tabPane.setSelectedIndex(pageViewList.indexOf(editor));
			return;
		}
		editor.initDrawPane();
		DrawPane drawPanel = editor.getDrawPanel();
		// 获取到项目节点
		pageViewList.add(editor);
		Application.tabPane
				.addTab(editor.getName(), new JScrollPane(drawPanel));
		try {
			editor.getComponentsView().changeType("changeToAndroidDesginer",
					editor);
			CenterPanelManager.changeComponentView(editor);
			editor.setOpen(true);
			AndroidPageContainer page = (AndroidPageContainer) editor;
			page.getPropertiesComponent();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Application.tabPane.updateUI();
		Application.tabPane
				.setSelectedIndex(Application.tabPane.getTabCount() - 1);
		Application.mainFrame.setGlassPane(editor.getDragManager().getGlass());
		editor.repaint();
		editor.getDrawPanel().requestFocusInWindow();
	}

	/**
	 * 控制台输出
	 * 
	 * @param text
	 */
	public static void consoleLog(String text) {
		String str = format.format(new Date());
		Logger.getLogger(CenterPanelManager.class).info(str);
		console.append(str);
		str = "  ".concat(text).concat(System.getProperty("line.separator"));
		Logger.getLogger(CenterPanelManager.class).info(str);
		console.append(str);
	}

	/**
	 * 在页面上添加一个组件
	 * 
	 * @param androidPageContainer
	 */
	public static void pageShapesChanged(ProjectTreeNode node) {
		Enumeration<TreePath> expandedPaths = projectTree
				.getExpandedDescendants(new TreePath(
						new Object[] { Application.treeModel.getRoot() }));
		Application.treeModel.firePropertyChange();
		while (expandedPaths.hasMoreElements()) {
			projectTree.expandPath(expandedPaths.nextElement());
		}
		ProjectEntity project = Application
				.getProjectByPage((AndroidPageContainer) node);
		selectNode(new TreePath(new Object[] { Application.treeModel.getRoot(),
				project, node }));
	}

	/**
	 * 改变属性面板
	 * 
	 * @param node
	 */
	public static void changePropertyPane(ProjectTreeNode node) {
		propertyPanel.removeAll();
		if (node.getPropertiesComponent() != null) {
			propertyPanel.add(
					new JTitlePanel("属性列表", node.getPropertiesComponent()),
					BorderLayout.CENTER);
		}
		propertyPanel.updateUI();
		propertyPanel.repaint();
	}

	/**
	 * 修改页面的名称
	 * 
	 * @param page
	 */
	public static void changePageName(AndroidPageContainer page) {
		Application.treeModel.firePropertyChange();
		selectNode(new TreePath(new Object[] { Application.treeModel.getRoot(),
				Application.getProjectByPage(page), page }));
	}

	public static List<IDrawEditable> getEditPage() {
		return pageViewList;
	}

	/**
	 * 修改控件名稱
	 * 
	 * @param comp
	 * @param page
	 */
	public static void changeComponentName(BaseAndroidComponent comp,
			IDrawEditable page) {
		projectTree.repaint();

	}

	/**
	 * 删除一个项目节点
	 * 
	 * @param project
	 */
	public static void removeProject(ProjectEntity project) {
		for (int i = 0; i < project.getViewCount(); i++) {
			int index = pageViewList.indexOf(project.getViewAt(i));
			if (index != -1) {
				Application.tabPane.removeTabAt(index);
			}
		}
		Application.projects.remove(project);
		Application.treeModel.firePropertyChange();
	}

	public static void setProject(ProjectEntity project) {
		projectLabel.setText("工程：" + project.getName());
	}

	public static void setProjectSize(ProjectEntity project) {
		sizeLabel.setText("宽：" + project.getRes().getWidth() + " 高："
				+ project.getRes().getHeight());
	}

	public static void setMouseLocation(Point point, DrawPane panel) {
		locationLabel.setText("x:" + (point.getX() - (int) panel.getImagex())
				+ " y:" + (point.getY() - (int) panel.getImagey()));
	}

	/**
	 * 删除视图
	 * 
	 * @param project
	 * @param page
	 */
	public static void removeView(ProjectEntity project,
			AndroidPageContainer page) {
		int index = pageViewList.indexOf(page);
		removeEditor(index);
		project.removeView(page);
		project.setSaved(false);
		String pagePath = project.getProjectPath().concat(File.separator)
				.concat("page").concat(File.separator).concat(page.getName())
				.concat(".pag");
		File f = new File(pagePath);
		System.gc();
		if (!f.delete()) {
			f.deleteOnExit();
		}
		Application.treeModel.firePropertyChange();
	}

	public static boolean isSelectedNode(BaseDataShape shape) {
		return projectTree.getLastSelectedPathComponent() == shape;
	}
}
