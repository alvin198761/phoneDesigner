package com.android.gui.comp;

import java.awt.Cursor;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;

import com.android.bean.shape.ShapeHelper;
import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.gui.frame.BaseDrawComp;
/**
 * 用来拖拽的按钮，左边控件列表用的着
 * @author Administrator
 *
 */
public class ShapeButton extends JLabel implements BaseDrawComp {

	private static final long serialVersionUID = 1L;

	private BaseDataShape shape;
	public final static int icon_width = 75;

	public ShapeButton(BaseAndroidComponent comp) {
		this.shape = comp;
		setIcon(comp.getContrlIcon());
		setToolTipText(shape.getText());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public List<BaseAndroidComponent> getShapes() {
		return Arrays.asList((BaseAndroidComponent) ShapeHelper
				.createShapeFactory(shape.getType()));
	}

}