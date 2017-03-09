package com.android.bean.shape.data.comp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.android.bean.shape.ShapeHelper;
import com.android.gui.property.ImageViewPropertiesPanel;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

/**
 * 图片框
 * 
 * @author Administrator
 * 
 */
public class AndroidImageView extends BaseAndroidComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private static final int defaultWidth = 75;
	// private static final int defaultHeight = 25;
	private JComponent properties;
	private String backgroudImage = "page/image/defaultImageIcon.png";
	// scale是表示按照比例显示是yes，如果是no是平铺
	private String scale = "yes";
	transient private Image bgImage;

	public AndroidImageView() {
		super("imageview");

		type = ShapeHelper.ANDROID_IMAGEVIEW;
		text = "";
		name = "ImageView";
		editable = true;
		select = false;
		this.w = 50;
		this.h = 50;
	}

	@Override
	protected void drawShape(Graphics2D g) {
		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
		if (bgImage != null) {
			g.drawImage(this.bgImage, (int) x, (int) y, (int) w, (int) h, null);
		} else {
			this.bgImage = Application.getImage(this, backgroudImage);
			g.drawImage(this.bgImage, (int) x, (int) y, (int) w, (int) h, null);
		}
		// if (scale.equals("yes")) {
		// g.drawImage(image, (int) x, (int) y, (int) w, (int) h, null);
		// } else {
		// for (int i = 0; i <= getW(); i += image.getWidth(null)) {
		// for (int j = 0; j <= getH(); j += image.getHeight(null)) {
		// g.drawImage(defaultIcon.getImage(), (int) x + i, (int) y
		// + j, null);
		// }
		// }
		// }
		shape = rect;
	}

	@Override
	public JComponent getPropertiesComponent() {
		if (properties == null) {
			properties = new ImageViewPropertiesPanel(this);
		}
		((ImageViewPropertiesPanel)properties).initData();
		return properties;
	}

	public String getBackgroudImage() {
		if (backgroudImage == null) {
			return "page/image/defaultImageIcon.png";
		}
		return backgroudImage;
	}

	public void setBackgroudImage(String backgroudImage) {
		if (backgroudImage == null || "NONE".equals(backgroudImage)) {
			this.backgroudImage = "page/image/defaultImageIcon.png";
			this.bgImage = null;
			return;
		}
		if (!this.backgroudImage.equals(backgroudImage)) {
			this.backgroudImage = backgroudImage;
			this.bgImage = null;
		}

	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	@Override
	protected void drawText(Graphics2D g) {
		// TODO Auto-generated method stub
		// super.drawText(g);
	}

	public Image getBgImage() {
		return this.bgImage;
	}

	@Override
	public Icon getContrlIcon() {
		return ResourceUtil.imageView_icon;
	}
}
