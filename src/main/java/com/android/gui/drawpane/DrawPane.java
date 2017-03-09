package com.android.gui.drawpane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.gui.frame.BaseDrawComp;
import com.android.lang.Application;

/**
 * 画图板
 * 
 * @author issuser
 * 
 */
public class DrawPane extends JPanel implements BaseDrawComp, IDrawComponent {

	private static final long serialVersionUID = 1L;

	private double scale = 1;
	private double imageWidth;
	private double imageHeight;
	private double imagex = 0;
	private double imagey = 0;
	// 内存图片
	private BufferedImage image;
	// 画图对象
	private Graphics2D g2d;
	// 撤销恢复管理
	private UndoableEditManager undoRedoManager;
	// 图片大小的框
	private Rectangle2D imageBox = new Rectangle2D.Double();
	// 编辑器的大小
	private IDrawEditable editor;
	// 背景图片
	private Image bgImage;

	public double getImagex() {
		return imagex;
	}

	public void setImagex(double imagex) {
		this.imagex = imagex;
	}

	public double getImagey() {
		return imagey;
	}

	public void setImagey(double imagey) {
		this.imagey = imagey;
	}

	public DrawPane(IDrawEditable editor) {
		setBackground(Color.gray);
		this.editor = editor;
		new DrawPaneHelper(this);
		undoRedoManager = new UndoableEditManager(editor);
		undoRedoManager.addPropertyChangeListener(
				UndoableEditManager.operActionPropertyChange,
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						DrawPane.this.editor.firePropertyChange(
								IDrawEditable.firePropertyChangeEditAction,
								true, false);
					}
				});
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Log.info("绘制");
		super.paintComponent(g);
		imagex = (this.getWidth() - (int) imageWidth) >> 1;
		imagey = (this.getHeight() - (int) imageHeight) >> 1;
		// 抗锯齿 抖动
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// 双缓冲画图
		((Graphics2D) g).drawImage(image, (int) imagex, (int) imagey,
				(int) imageWidth, (int) imageHeight, this);
	}

	public void reDraw() {
		// Log.info("计算");
		g2d = image.createGraphics();
		// 抗锯齿 抖动
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(this.getBackground());
		g2d.fill(new Rectangle2D.Double(0, 0, image.getWidth(), image
				.getHeight()));
		// 将界面铺白
		g2d.setColor(this.editor.getBgColor());
		g2d.scale(scale, scale);
		g2d.fill(new Rectangle2D.Double(0, 0, image.getWidth(), image
				.getHeight()));
		if (this.bgImage != null) {
			// if (this.editor.getBgImageMode() == IDrawEditable.CENTER) {
			// g2d.drawImage(bgImage,
			// (this.getWidth() - bgImage.getWidth(null)) >> 1,
			// (this.getHeight() - bgImage.getHeight(null)) >> 1, this);
			g2d.drawImage(this.bgImage, 0, 0, image.getWidth(),
					image.getHeight(), this);
			// } else if (this.editor.getBgImageMode() ==
			// IDrawEditable.NO_REPEAT) {
			// g2d.drawImage(bgImage, 0, 0, this);
			// } else {
			// BufferedImage bi = new BufferedImage(
			// this.bgImage.getWidth(null),
			// this.bgImage.getHeight(null),
			// BufferedImage.TYPE_INT_RGB);
			// bi.createGraphics().drawImage(this.bgImage, 0, 0, this);
			// TexturePaint texturePaint = new TexturePaint(bi,
			// this.getVisibleRect());
			// g2d.setPaint(texturePaint);
			// }
			// g2d.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(),
			// this);
		}
		// int baseSize = 10;
		// 画一个网格点
		// int hCount = image.getHeight() / baseSize;
		Stroke defaultStroke = g2d.getStroke();
		this.editor.drawShapes(defaultStroke);
		// 画选择框
		if (editor.getSelectBox() != null) {
			g2d.setColor(Color.green);
			g2d.draw(editor.getSelectBox());
		}
	}

	@Override
	public final void update(Graphics g) {
		this.paintComponent(g);
	}

	@Override
	public List<BaseAndroidComponent> getShapes() {
//		if( this.editor.getMuiltSelectShapes() == null || this.editor.getMuiltSelectShapes().isEmpty()){
			return this.editor.getSelectComponent();
//		}else{
//			return this.editor.getMuiltSelectShapes();
//		}
	}
 

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
		imageWidth = image.getWidth() * scale;
		imageHeight = getHeight() * scale;
		this.setPreferredSize(new Dimension((int) imageWidth, (int) imageHeight));
	}

	public Rectangle2D getImageRect() {
		if (imagex < 0) {
			return this.getBounds();
		}
		imageBox.setRect(imagex, imagey, imageWidth, imageHeight);
		return imageBox;
	}

	public void initSize(ProjectEntity currentProject, AndroidPageContainer view) {
		this.imageWidth = currentProject.getRes().getWidth(); // >> 1;
		this.imageHeight = currentProject.getRes().getHeight();// >> 1;
		this.image = new BufferedImage((int) imageWidth, (int) imageHeight,
				BufferedImage.TYPE_3BYTE_BGR);
		this.setPreferredSize(new Dimension(
				currentProject.getRes().getWidth() + 20, currentProject
						.getRes().getHeight() + 20));
		this.updateUI();
		this.g2d = image.createGraphics();
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public Graphics2D getG2d() {
		return g2d;
	}

	public IDrawEditable getEditor() {
		return this.editor;
	}

	public void setBgImage(String backGroundimage) {
		if (backGroundimage == null || backGroundimage.trim().equals("")) {
			this.bgImage = null;
			return;
		}
		try {
			ProjectEntity pro = Application
					.getProjectByPage((AndroidPageContainer) this.editor);
			String path = pro.getProjectPath().concat(File.separator)
					.concat(backGroundimage);
			bgImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// bgImage =
		// Toolkit.getDefaultToolkit().createImage(this.editor.getBackGroundimage());
		MediaTracker tracker = new MediaTracker(this);
		tracker.addImage(bgImage, 0);
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// this.bgImage = new BufferedImage(img.getWidth(null),
		// img.getHeight(null), BufferedImage.TYPE_INT_BGR);
	}

	public void postUndoRedoEdit() {
		this.undoRedoManager.post(new DrawPaneEdit(this));
	}

	public void redo() {
		this.undoRedoManager.redo();
	}

	public boolean canRedo() {
		return this.undoRedoManager.canRedo();
	}

	public void undo() {
		this.undoRedoManager.undo();
	}

	public boolean canUndo() {
		return this.undoRedoManager.canUndo();
	}

	public Image getBackGroundImage() {
		return this.bgImage;
	}

}
