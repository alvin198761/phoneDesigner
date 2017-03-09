package com.android.bean.shape.data.comp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.*;

import com.android.bean.shape.ShapeHelper;
import com.android.gui.action.app.BaseAction;
import com.android.gui.property.OsShapeButtonPropertiesPanel;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

/**
 * 不规则按钮
 * 
 * @author Administrator
 * 
 */
public class AndroidOsShapeButton extends BaseAndroidButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int defaultWidth = 75;
	private static final int defaultHeight = 25;
	private JComponent properties;
	private OsShapeButtonPropertiesPanel shapeButtonPropertiesPanel;
	private JScrollPane eventPanel;

	private String defaultImage = "page/image/defaultImageIcon.png";
	private String pressImage = "page/image/defaultImageIcon.png";
	private String selecedImage = "page/image/defaultImageIcon.png";

	private transient Image defaultImageData;
	private transient Image pressImageData;
	private transient Image selecedImageData;

	// 选中状态
	private boolean statusSelected = false;
	// 点击透明区域幼小
	private boolean lucency = true;

	public boolean isStatusSelected() {
		return statusSelected;
	}

	public void setStatusSelected(boolean statusSelected) {
		this.statusSelected = statusSelected;
	}

	public boolean isLucency() {
		return lucency;
	}

	public void setLucency(boolean lucency) {
		this.lucency = lucency;
	}

	public String getDefaultImage() {
		if (defaultImage == null) {
			return "page/image/defaultImageIcon.png";
		}
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage) {
		this.defaultImageData = null;
		this.defaultImage = defaultImage;
	}

	public String getPressImage() {
		if (pressImage == null) {
			return "page/image/defaultImageIcon.png";
		}
		return pressImage;
	}

	public void setPressImage(String pressImage) {
		this.pressImageData = null;
		this.pressImage = pressImage;
	}

	public String getSelecedImage() {
		if (selecedImage == null) {
			return "page/image/defaultImageIcon.png";
		}
		return selecedImage;
	}

	public void setSelecedImage(String selecedImage) {
		this.selecedImageData = null;
		this.selecedImage = selecedImage;
	}

	public AndroidOsShapeButton() {
		super("oshapebutton");
		type = ShapeHelper.ANDROID_OSSHAPEBUTTON;
		text = "";
		name = "OsShapeButton";
		editable = true;
		select = false;
		this.w = defaultWidth;
		this.h = defaultHeight;
	}

	@Override
	public List<BaseAction> getActions() {
		return super.getActions();
	}

	@Override
	protected void drawText(Graphics2D g) {
		// super.drawText(g);
	}

	@Override
	protected void drawShape(Graphics2D g) {
		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
		if (getMouseType() == MOUSE_TYPE_HOVER) {
			if (this.selecedImageData == null) {
				g.drawImage(getSelectedImageData(), (int) x, (int) y, (int) w,
						(int) h, null);
			} else {
				g.drawImage(selecedImageData, (int) x, (int) y, (int) w,
						(int) h, null);
			}
		} else if (getMouseType() == MOUSE_TYPE_PRESS) {
			if (this.pressImageData == null) {
				g.drawImage(getPressImageData(), (int) x, (int) y, (int) w,
						(int) h, null);
			} else {
				g.drawImage(pressImageData, (int) x, (int) y, (int) w, (int) h,
						null);
			}
		} else {
			if (this.defaultImageData == null) {
				g.drawImage(getDefaultImageData(), (int) x, (int) y, (int) w,
						(int) h, null);
			} else {
				g.drawImage(defaultImageData, (int) x, (int) y, (int) w,
						(int) h, null);
			}

		}
		shape = rect;
	}

	private Image getDefaultImageData() {
		if (this.defaultImageData == null) {
			this.defaultImageData = Application.getImage(this, getDefaultImage());
		}
		return this.defaultImageData;
	}

	private Image getSelectedImageData() {
		if (this.selecedImageData == null) {
			this.selecedImageData = Application.getImage(this, getSelecedImage());
		}
		return this.selecedImageData;
	}

	private Image getPressImageData() {
		if (this.pressImageData == null) {
			this.pressImageData = Application.getImage(this, getPressImage());
		}
		return this.pressImageData;
	}

	@Override
	public JComponent getPropertiesComponent() {
		if (properties == null) {
			JTabbedPane tab = new JTabbedPane();
			shapeButtonPropertiesPanel = new OsShapeButtonPropertiesPanel(this);
			eventPanel = new JScrollPane(new JTextArea("与其他公司有协议，被删除"));
			tab.addTab("属性", shapeButtonPropertiesPanel);
			tab.addTab("交互", eventPanel);
			properties = tab;
		}
		shapeButtonPropertiesPanel.initData();
		return properties;
	}

	@Override
	public Icon getContrlIcon() {
		return ResourceUtil.osShapeButton_icon;
	}

}
