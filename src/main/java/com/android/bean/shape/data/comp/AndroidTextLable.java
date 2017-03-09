package com.android.bean.shape.data.comp;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.android.bean.shape.ShapeHelper;
import com.android.gui.property.LabelPropertiesPanel;
import com.android.reource.ResourceUtil;

public class AndroidTextLable extends BaseAndroidComponent {

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
	// 文字宽度
	private int fontWegith;
	//
	private LabelPropertiesPanel propertiesPane;

	public AndroidTextLable() {
		super("textlable");
		type = ShapeHelper.ANDROID_LABEL;
		text = "Label";
		name = "Label";
		editable = true;
		select = false;
		this.w = defaultWidth;
		this.h = defaultHeight;
	}

	@Override
	protected void drawShape(Graphics2D g) {
		Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
		// g.setColor(Color.white);
		// g.fill(rect);
		// g.setColor(borderColor);
		// g.draw(rect);
		shape = rect;
	}

	@Override
	protected void drawText(Graphics2D g) {
		Font f = g.getFont();
		g.setColor(this.getTextColor());
		g.setFont(this.getFont());
		Rectangle2D rect = this.getBounds2D();
		FontMetrics fm = g.getFontMetrics();
		String[] textArray = text.trim().split("\n");
		int textHeight = fm.getHeight();
		int hgap = textHeight / 10;
		//
		int count = (int) getH() / (textHeight + hgap);
		if (count > textArray.length) {
			count = textArray.length;
		}
		double startY = rect.getCenterY() - ((textHeight + hgap) * count / 2);
		if (textArray.length == 1) {
			// 文字总宽度
			int fontW = fm.charsWidth(text.toCharArray(), 0,
					text.toCharArray().length);
			// 文字高度
			// int fontH = fm.getAscent() - 20;
			int tw = 0;
			String showText = text;
			for (int i = 0; i < text.trim().length(); i++) {
				tw += fm.charWidth(text.trim().charAt(i));
				if (tw >= rect.getWidth()) {
					if (i < 2) {
						showText = "...";
					} else {
						showText = text.trim().substring(0, i - 2) + "...";
					}
					break;
				}
			}
			double y = 0;
			if (v_textAlign == V_TEXT_ALIGN_CENTER) {
				// y = rect.getY() + (rect.getCenterY() - fontH / 2);
				y = rect.getCenterY();// + g.getFontMetrics().getHeight();
			} else if (v_textAlign == V_TEXT_ALIGN_TOP) {
				y = rect.getY() + (hgap + textHeight);
			} else if (v_textAlign == V_TEXT_ALIGN_BOTTOM) {
				y = rect.getY() + rect.getHeight() - 1;
			}
			//
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
		} else {
			for (int j = 0; j < count; j++) {
				String subText = textArray[j];
				// 文字总宽度
				int fontW = fm.charsWidth(subText.toCharArray(), 0,
						subText.toCharArray().length);
				// 文字高度
				int tw = 0;
				String showText = subText;
				for (int i = 0; i < subText.length(); i++) {
					tw += fm.charWidth(subText.charAt(i));
					if (tw >= getW()) {
						if (i < 2) {
							showText = "...";
						} else {
							showText = subText.substring(0, i - 2) + "...";
						}
						break;
					}
				}
				double y = 0;
				if (v_textAlign == V_TEXT_ALIGN_CENTER) {
					y = startY + (j * (hgap + textHeight)) + textHeight;
				} else if (v_textAlign == V_TEXT_ALIGN_TOP) {
					y = rect.getY() + ((j + 1) * (hgap + textHeight));
				} else if (v_textAlign == V_TEXT_ALIGN_BOTTOM) {
					y = rect.getY() + rect.getHeight() - 1
							- (j * (hgap + textHeight));
				}
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
			}
		}
		g.setFont(f);

	}

	public int getH_TextAlign() {
		return h_textAlign;
	}

	public void setH_TextAlign(int textAlign) {
		this.h_textAlign = textAlign;
	}

	public int getFontWeight() {
		return fontWegith;
	}

	@Override
	public JComponent getPropertiesComponent() {
		if (propertiesPane == null) {
			propertiesPane = new LabelPropertiesPanel(this);
		}
		return propertiesPane;
	}

	@Override
	public Icon getContrlIcon() {
		return ResourceUtil.label_icon;
	}

	public int getV_textAlign() {
		return v_textAlign;
	}

	public void setV_textAlign(int v_textAlign) {
		this.v_textAlign = v_textAlign;
	}

}
