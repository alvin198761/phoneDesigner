package com.android.bean.shape.data.comp;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.BaseShape;
import com.android.bean.shape.ctrl.CtrlResizeShape;
import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.BaseLineShape;
import com.android.bean.ui.IDrawEditable;
import com.android.biz.BaseDragBiz;
import com.android.biz.BaseDrawPanelBiz;
import com.android.biz.androiddesigner.AndroidDragPanelBiz;
import com.android.biz.androiddesigner.AndroidDrawPanelBiz;
import com.android.gui.action.app.BaseAction;
import com.android.gui.action.edit.*;
import com.android.gui.action.file.SavePageAction;
import com.android.gui.comp.JTitlePanel;
import com.android.gui.drag.ShapeDragManager;
import com.android.gui.drawpane.DrawPane;
import com.android.gui.frame.CenterPanelManager;
import com.android.gui.property.PagePropertiesPanel;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 页面容器
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class AndroidPageContainer extends BaseAndroidContainer implements
		IDrawEditable {

	// 屏幕方向， protrait 竖，landscape 横
	private String screenOrientation;
	// -----title
	private String titleBgColor;
	private String show;
	// ------end title
	// ------body
	private String backGroundimage;
	// end body
	// is root
	private boolean root;
	// 是否保存
	transient private boolean saved = true;
	// 画图板
	transient private DrawPane drawpanel;
	// 拖拽管理
	transient private ShapeDragManager dragManager;
	// 当前选中的图形
	transient private BaseAndroidComponent currentSelectShape;
	// 当前控制图形
	transient private CtrlResizeShape currentResizeShape;
	// 操作状态
	private String operStatus;
	// 画图处理
	transient private BaseDrawPanelBiz drawPaneBiz;
	// 拖放处理
	transient private BaseDragBiz dragBiz;
	// 是否打开编辑器
	transient private boolean open;
	// 选择框
	transient private Rectangle2D selectBox = new Rectangle2D.Double(-1, -1,
			-1, -1);
	// 控件列表
	transient private JTitlePanel componentsView;
	// 属性面板
	transient private PagePropertiesPanel propertiesPanel;
	//
	private int tag = 0;
	//
	private int bgImageMode = CENTER;
	//
//	private List<BaseAndroidComponent> muilt_shapes = new ArrayList<BaseAndroidComponent>();

	public int getBgImageMode() {
		return bgImageMode;
	}

	public void setBgImageMode(int bgImageMode) {
		this.bgImageMode = bgImageMode;
	}

	public String getScreenOrientation() {
		return screenOrientation;
	}

	public void setScreenOrientation(String screenOrientation) {
		this.screenOrientation = screenOrientation;
	}

	public String getTitleBgColor() {
		return titleBgColor;
	}

	public void setTitleBgColor(String titleBgColor) {
		this.titleBgColor = titleBgColor;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getBackGroundimage() {
		if (backGroundimage == null) {
			return "NONE";
		}
		return backGroundimage;
	}

	public void setBackGroundimage(String backGroundimage) {
		if ("NONE".equals(backGroundimage)) {
			this.backGroundimage = null;
			return;
		}
		this.backGroundimage = backGroundimage;
		if (this.drawpanel != null) {
			this.drawpanel.setBgImage(backGroundimage);
		}
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public AndroidPageContainer() {
		super("page");
		addPropertyChangeListener(firePropertyChangeEditAction,
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						fireActionChange();
					}
				});
	}

	@Override
	protected void drawShape(Graphics2D g) {

	}

	@Override
	public Map<String, String> getAttributeMap() {
		attributeMap.put("width", Integer.toString((int) getW()));
		attributeMap.put("height", Integer.toString((int) getH()));
		attributeMap.put("backgroundcolor", color2String(getBgColor()));
		attributeMap.put("backgroundimage", getBackGroundimage());
		return attributeMap;
	}

	@Override
	public List<BaseAction> getActions() {
		if (actions == null) {
			actions = new ArrayList<BaseAction>();
			actions.add(new SavePageAction(this));
			actions.add(new OpenWithEditAction(this));
			actions.add(new RemovePageAction(this));
			actions.add(new UndoAction(this));
			actions.add(new RedoAction(this));
			actions.add(new SelectAllAction(this));
			// actions.add(new CutAction(this));
			// actions.add(new CopyAction(this));
			// actions.add(new PasteAction(this));
			// actions.add(new DeleteAction(this));
		}
		return actions;
	}

	@Override
	public Icon getIcon() {
		return ResourceUtil.page_icon;
	}

	@Override
	public boolean isSaved() {
		return saved;
	}

	@Override
	public void doSave() {
		ProjectEntity project = Application.getProjectByPage(this);
		try {
			Application.dataio.savePage(project, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setSaved(boolean saved) {
		this.saved = saved;
		ProjectEntity project = Application.getProjectByPage(this);
		if (project == null) {
			return;
		}
		project.setSaved(saved);
		fireActionChange();
	}

	@Override
	public DrawPane getDrawPanel() {
		return drawpanel;
	}

	@Override
	public ShapeDragManager getDragManager() {
		return dragManager;
	}

	@Override
	public BaseAndroidComponent getCurrentSelectShape() {
		return currentSelectShape;
	}

	@Override
	public CtrlResizeShape getCurrentResizeShape() {
		return currentResizeShape;
	}

	@Override
	public ArrayList<BaseAndroidComponent> getComponents() {
		return this.children;
	}

	@Override
	public String getOperStatus() {
		return operStatus;
	}

	@Override
	public void setOperStatus(String operStatus) {
		this.operStatus = operStatus;
	}

	@Override
	public String getSavePath() {
		return null;
	}

	@Override
	public boolean isOpen() {
		return open;
	}

	@Override
	public void setOpen(boolean open) {
		this.open = open;
		// fireActionChange();
	}

	@Override
	public BaseDrawPanelBiz getDrawPaneBiz() {
		return this.drawPaneBiz;
	}

	@Override
	public BaseDragBiz getDragBiz() {
		return this.dragBiz;
	}

	@Override
	public void initDrawPane() {
		ProjectEntity project = Application.getProjectByPage(this);
		this.operStatus = Application.OPER_NONE;
		this.drawpanel = new DrawPane(this);
		if (this.getBackGroundimage() != null
				&& !this.getBackGroundimage().equals("NONE")) {
			this.drawpanel.setBgImage(this.getBackGroundimage());
		}
		this.drawpanel.initSize(project, this);
		this.drawPaneBiz = new AndroidDrawPanelBiz(this);
		this.dragBiz = new AndroidDragPanelBiz(this);
		this.dragManager = new ShapeDragManager(this);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		this.componentsView = new JTitlePanel("控件列表", panel);
	}

	@Override
	public void disposeDraw() {
		this.drawPaneBiz = null;
		this.dragBiz = null;
		this.dragManager = null;
		this.drawpanel = null;
		this.componentsView.removeAll();
		this.componentsView.updateUI();
		this.componentsView = null;
		this.setOpen(false);
		System.gc();
	}

	@Override
	public void setCurrentSelectShape(BaseDataShape shape) {
		this.currentSelectShape = (BaseAndroidComponent) shape;
		// fireActionChange();
	}

	@Override
	public void setCurrentResizeShape(CtrlResizeShape currentResizeShape) {
		this.currentResizeShape = currentResizeShape;
	}

	@Override
	public Rectangle2D getSelectBox() {
		return this.selectBox;
	}

	@Override
	public void repaint() {
		if (this.drawpanel == null) {
			return;
		}
		this.drawpanel.reDraw();
		this.drawpanel.repaint();
	}

	@Override
	public void clearSelect() {
		// this.getDrawPanel().postUndoRedoEdit();
		Iterator<BaseAndroidComponent> list = this.getComponents().iterator();
		BaseShape shape = null;
		while (list.hasNext()) {
			shape = list.next();
			shape.setSelect(false);
		}
		currentSelectShape = null;
		currentResizeShape = null;
		// fireActionChange();
	}

	@Override
	public void addShape(BaseAndroidComponent component) {
		// this.getDrawPanel().postUndoRedoEdit();
		this.getComponents().add(component);
		currentSelectShape = component;
		CenterPanelManager.pageShapesChanged(this);
		// fireActionChange();
	}

	@Override
	public void drawShapes(Stroke stroke) {
		// 开始画图
		drawpanel.getG2d().setStroke(stroke);
		for (BaseDataShape shape : getComponents()) {
			if (currentSelectShape == shape) {
				continue;
			}
			shape.draw(drawpanel.getG2d());
		}
		if (currentSelectShape != null) {
			currentSelectShape.draw(drawpanel.getG2d());
		}
	}

	@Override
	public void removeAllShapes() {
		// getDrawPanel().postUndoRedoEdit();
		getComponents().clear();
		currentSelectShape = null;
		// fireActionChange();
	}

	@Override
	public boolean isSelectAll() {
		for (int i = getComponentCount() - 1; i >= 0; i--) {
			BaseDataShape shape = getComponentAt(i);
			if (!shape.isSelect()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void addDraw(List<BaseAndroidComponent> list) {
		for (BaseAndroidComponent shape : list) {
			shape.setSelect(true);
			shape.setTagIndex(createTag() + "");
		}
		drawShapes(getDrawPanel().getG2d().getStroke());
		getComponents().addAll(list);
		// getDrawPanel().postUndoRedoEdit();
		getDrawPanel().repaint();
		CenterPanelManager.pageShapesChanged(this);
		// fireActionChange();
	}

	@Override
	public void removeSelectShape() {
		// if (getComponentCount() > 0) {
		// getDrawPanel().postUndoRedoEdit();
		// }
		for (int i = getComponentCount() - 1; i >= 0; i--) {
			BaseDataShape shape = getComponentAt(i);
			if (!shape.isSelect()) {
				continue;
			}
			getComponents().remove(i);
			List<BaseLineShape> lines = shape.getAllLines();
			for (BaseLineShape line : lines) {
				getComponents().remove(line);
				if (line.getBeginShape() != null) {
					line.getBeginShape().removeLine(line);
				}
				if (line.getEndShape() != null) {
					line.getEndShape().removeLine(line);
				}
			}
		}
		currentSelectShape = null;
		drawpanel.reDraw();
		drawpanel.repaint();
		CenterPanelManager.pageShapesChanged(this);
		// fireActionChange();
	}

	@Override
	public void selectAllShape() {
		// getDrawPanel().postUndoRedoEdit();
		for (int i = getComponentCount() - 1; i >= 0; i--) {
			BaseDataShape shape = getComponentAt(i);
			shape.setSelect(true);
		}
		repaint();
		if (getComponentCount() != 1) {
			currentSelectShape = null;
		} else {
			currentSelectShape = getComponentAt(0);
		}
		// fireActionChange();
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public JTitlePanel getComponentsView() {
		return componentsView;
	}

	@Override
	public void doRead(File file) {
		AndroidPageContainer page;
		try {
			page = Application.dataio.readPage(file.getAbsolutePath());
			// 目前就想单这些属性，多的以后再说吧
			this.setAttributeMap(page.getAttributeMap());
			this.setBackGroundimage(page.getBackGroundimage());
			this.setRoot(page.isRoot());
			this.setW(page.getW());
			this.setH(page.getH());
			this.setScreenOrientation(page.getScreenOrientation());
			this.setText(page.getTagIndex());
			this.setTextColor(page.getTextColor());
			this.setTagIndex(page.getTagIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public JComponent getPropertiesComponent() {
		if (propertiesPanel == null) {
			propertiesPanel = new PagePropertiesPanel(this);
		}
		propertiesPanel.initData();
		return propertiesPanel;
	}

	public void setBackGroundColor(String color) {
		this.bgColor = string2Color(color);
		this.repaint();
		setSaved(false);
	}

	@Override
	public synchronized int createTag() {
		return tag++;
	}

	@Override
	public List<BaseAndroidComponent> getSelectComponent() {
		List<BaseAndroidComponent> shapes = new ArrayList<BaseAndroidComponent>();
		for (int i = 0; i < this.getComponentCount(); i++) {
			if (this.getComponentAt(i).canCopy()) {
				shapes.add(this.getComponentAt(i));
			}
		}
		return shapes;
	}

	public void addOpenShape(BaseAndroidComponent comp) {
		this.children.add(comp);
	}

	@Override
	public Icon getContrlIcon() {
		return null;
	}

	@Override
	public void addUndoRedoShape(BaseAndroidComponent component) {
		this.getComponents().add(component);
		currentSelectShape = component;
		CenterPanelManager.pageShapesChanged(this);
	}
//
//	@Override
//	public List<BaseAndroidComponent> getMuiltSelectShapes() {
//		return muilt_shapes;
//	}
}
