package com.android.bean.ui;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import com.android.bean.IBean;
import com.android.bean.shape.ctrl.CtrlResizeShape;
import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.biz.BaseDragBiz;
import com.android.biz.BaseDrawPanelBiz;
import com.android.gui.comp.JTitlePanel;
import com.android.gui.drag.ShapeDragManager;
import com.android.gui.drawpane.DrawPane;

/**
 * 画图的接口
 * 
 * @author Administrator
 * 
 */
public interface IDrawEditable extends ISavable, IBean {

	String firePropertyChangeEditAction = "firePropertyChangeEditAction";
	String pageSavedChange = "pageSavedChange";
	// 不平铺
	int NO_REPEAT = 0;
	// 居中
	int CENTER = 1;
	// 平铺
	int REPEAT = 2;

	/**
	 * 画图板
	 * 
	 * @return
	 */
	DrawPane getDrawPanel();

	/**
	 * 初始化画图板
	 */
	void initDrawPane();

	/**
	 * 销毁画图板
	 */
	void disposeDraw();

	/**
	 * 图形拖拽实现
	 * 
	 * @return
	 */
	ShapeDragManager getDragManager();

	/**
	 * 当前选中的图形
	 * 
	 * @return
	 */
	BaseAndroidComponent getCurrentSelectShape();

	/**
	 * 当前选中改变大小的图形
	 * 
	 * @return
	 */
	CtrlResizeShape getCurrentResizeShape();

	/**
	 * 所有的控件
	 * 
	 * @return
	 */
	ArrayList<BaseAndroidComponent> getComponents();

	/**
	 * 当前操作状态
	 * 
	 * @return
	 */
	String getOperStatus();

	/**
	 * 当前操作状态设置
	 * 
	 * @param operStatus
	 */
	void setOperStatus(String operStatus);

	/**
	 * 是否保存
	 * 
	 * @return
	 */
	boolean isSaved();

	/**
	 * 设置是否保存
	 * 
	 * @param saved
	 */
	void setSaved(boolean saved);

	/**
	 * 保存路径
	 * 
	 * @return
	 */
	String getSavePath();

	/**
	 * 是否打开
	 * 
	 * @return
	 */
	boolean isOpen();

	/**
	 * 设置是否打开
	 * 
	 * @param open
	 */
	void setOpen(boolean open);

	/**
	 * 绘制操作处理
	 * 
	 * @return
	 */
	BaseDrawPanelBiz getDrawPaneBiz();

	/**
	 * 拖拽操作处理
	 * 
	 * @return
	 */
	BaseDragBiz getDragBiz();

	/**
	 * 设置当前选中的图形
	 * 
	 * @param shape
	 */
	void setCurrentSelectShape(BaseDataShape shape);

	/**
	 * 控制图形设置
	 * 
	 * @param contansCtrlBox
	 */
	void setCurrentResizeShape(CtrlResizeShape contansCtrlBox);

	/**
	 * 获取选择框
	 * 
	 * @return
	 */
	Rectangle2D getSelectBox();

	/**
	 * 重绘制
	 */
	void repaint();

	/**
	 * 清空选择项
	 */
	void clearSelect();

	/**
	 * 添加控件
	 * 
	 * @param component
	 */
	void addShape(BaseAndroidComponent component);

	/**
	 * 画组件
	 * 
	 * @param defaultStroke
	 */
	void drawShapes(Stroke defaultStroke);

	/**
	 * 删除所有的图形
	 */
	void removeAllShapes();

	/**
	 * 是否全选
	 * 
	 * @return
	 */
	boolean isSelectAll();

	/**
	 * 添加图形
	 * 
	 * @param list
	 */
	void addDraw(List<BaseAndroidComponent> list);

	/**
	 * 删除选中的图形
	 */
	void removeSelectShape();

	/**
	 * 全选
	 */
	void selectAllShape();

	/**
	 * 控件列表
	 * 
	 * @return
	 */
	JTitlePanel getComponentsView();

	/**
	 * 控件的唯一标号
	 * 
	 * @return
	 */
	int createTag();

	double getW();

	double getH();

	Color getBgColor();

	String getBackGroundimage();

	int getBgImageMode();

	void setBgImageMode(int bgImageMode);

	List<BaseAndroidComponent> getSelectComponent();

	void addUndoRedoShape(BaseAndroidComponent shape);
	
//	 List<BaseAndroidComponent> getMuiltSelectShapes();
}
