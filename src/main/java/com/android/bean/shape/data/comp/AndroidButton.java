package com.android.bean.shape.data.comp;

import com.android.bean.shape.ShapeHelper;
import com.android.gui.property.ButtonPropertiesPanel;
import com.android.reource.ResourceUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * 按钮控件
 * 
 * @author Administrator
 * 
 */
public class AndroidButton extends BaseAndroidButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int defaultWidth = 75;
	private static final int defaultHeight = 25;
	// 取值： 左 中 右
	private int h_textAlign = H_TEXT_ALIGN_LEFT;
	// 取值，上 中 下
	private int v_textAlign = V_TEXT_ALIGN_CENTER;
	private JComponent properties;
	private ButtonPropertiesPanel buttonPropertiesPanel;


	public AndroidButton() {
		super("button");
		type = ShapeHelper.ANDROID_BUTTON;
		text = "Button";
		name = "Button";
		editable = true;
		select = false;
		this.w = defaultWidth;
		this.h = defaultHeight;
	}

	@Override
	protected void drawShape(Graphics2D g) {
		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
		if (this.getMouseType() == MOUSE_TYPE_HOVER) {
			g.setColor(getSelectColor());
		} else if (this.getMouseType() == MOUSE_TYPE_PRESS) {
			g.setColor(getPressColor());
		} else {
			g.setColor(getDefaultBackGroundColor());
		}
		g.fill(rect);
		g.setColor(borderColor);
		g.draw(rect);
		shape = rect;
	}

	@Override
	protected void drawText(Graphics2D g) {
		Font f = g.getFont();
		g.setColor(this.getTextColor());
		g.setFont(this.getFont());
		Rectangle2D rect = this.getBounds2D();
		FontMetrics fm = g.getFontMetrics();
		// 文字总宽度
		int fontW = fm.charsWidth(text.toCharArray(), 0,
				text.toCharArray().length);
		// 文字高度
		int fontH = fm.getAscent() - 20;
		int tw = 0;
		String showText = text;
		for (int i = 0; i < text.length(); i++) {
			tw += fm.charWidth(text.charAt(i));
			if (tw >= getW()) {
				if (i < 2) {
					showText = "...";
				} else {
					showText = text.substring(0, i - 2) + "...";
				}
				break;
			}
		}
		double y = rect.getCenterY() - (fontH >> 1);
		double x = 0;
		if (h_textAlign == H_TEXT_ALIGN_LEFT) {
			x = rect.getX() + 1;
		} else if (h_textAlign == H_TEXT_ALIGN_RIGHT) {
			// 居右
			x = rect.getX()
					+ getW()
					- fm.charsWidth(showText.toCharArray(), 0,
							showText.toCharArray().length);
		} else if (h_textAlign == H_TEXT_ALIGN_MIDLLE) {
			// 居中
			x = rect.getCenterX() - (fontW >> 1);
			if (x < getX()) {
				x = getX() + 1;
			}
		}
		g.setColor(getTextColor());
		g.drawString(showText, (float) x, (float) y);
		g.setFont(f);
	}

	@Override
	public JComponent getPropertiesComponent() {
		if (properties == null) {
			JTabbedPane tab = new JTabbedPane();
			buttonPropertiesPanel = new ButtonPropertiesPanel(this);
		//	eventPanel = new ButtonEventPanel(this);
			tab.addTab("属性", buttonPropertiesPanel);
			tab.addTab("交互", new JScrollPane(new JTextArea("涉及与其他公司协议，该功能删除")));
			properties = tab;
		}
		buttonPropertiesPanel.initData();
		return properties;
	}

	public int getH_TextAlign() {
		return h_textAlign;
	}

	public void setH_TextAlign(int textAlign) {
		this.h_textAlign = textAlign;
	}

	@Override
	public Icon getContrlIcon() {
		return ResourceUtil.button_icon;
	}

	@Override
	public void drawDefaultStyle() {
		setMouseType(MOUSE_TYPE_DEFAULT);
	}

	@Override
	public void drawHoverStyle() {
		setMouseType(MOUSE_TYPE_HOVER);
	}

	@Override
	public void drawPressStyle() {
		setMouseType(MOUSE_TYPE_PRESS);
	}

	public int getV_textAlign() {
		return v_textAlign;
	}

	public void setV_textAlign(int v_textAlign) {
		this.v_textAlign = v_textAlign;
	}

}
