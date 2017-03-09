package com.runner.gui.drawpane;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidButton;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.runner.entity.PageActionEntity;
import com.runner.gui.RootRunPane;

/**
 * 一个页面的展示
 * 
 * @author Administrator
 * 
 */
public class PagePane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AndroidPageContainer page;
	private ProjectEntity project;
	private Image backgroundImage;
	private RootRunPane rootRunPanel;

	public PagePane(AndroidPageContainer page, ProjectEntity project,
			RootRunPane rootRunPanel) {
		this.page = page;
		this.project = project;
		this.rootRunPanel = rootRunPanel;
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				BaseAndroidComponent component = getComponentByMouse(e
						.getPoint());
				if (component == null) {
					return;
				}
				if (component instanceof BaseAndroidButton) {
					((BaseAndroidButton) component).drawHoverStyle();
					repaint();
					PageActionEntity pactionEntity = ((BaseAndroidButton) component)
							.getMouseActions()[2].getPageEntity();
					if (pactionEntity == null) {
						return;
					}
					String page = pactionEntity.getForwardPage();
					String type = pactionEntity.getType();
					if ("hide".equals(type)) {
						System.out.println("hide:" + page);
					} else if ("show".equals(type)) {

						System.out.println("show:" + page);
					} else if ("forward".equals(type)) {
						System.out.println("forward:" + page);

					} else if ("next".equals(type)) {
						System.out.println("next:" + page);

					}
					// System.out.println("鼠标按下");
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				BaseAndroidComponent component = getComponentByMouse(e
						.getPoint());
				if (component == null) {
					return;
				}
				if (component instanceof BaseAndroidButton) {
					((BaseAndroidButton) component).drawPressStyle();
					repaint();
					PageActionEntity pactionEntity = ((BaseAndroidButton) component)
							.getMouseActions()[0].getPageEntity();
					if (pactionEntity == null) {
						return;
					}
					String pageURL = pactionEntity.getForwardPage();
					String type = pactionEntity.getType();
					if ("hide".equals(type)) {
						System.out.println("hide:" + pageURL);
						PagePane.this.rootRunPanel.hidePage(pageURL,
								PagePane.this.page);
					} else if ("show".equals(type)) {
						System.out.println("show:" + pageURL);
						PagePane.this.rootRunPanel.showPage(pageURL,
								PagePane.this.page);
					} else if ("forward".equals(type)) {
						System.out.println("forward:" + pageURL);
						PagePane.this.rootRunPanel.forwardPage(pageURL,
								PagePane.this.page);
					} else if ("next".equals(type)) {
						System.out.println("next:" + pageURL);
						PagePane.this.rootRunPanel.nextPage(pageURL,
								PagePane.this.page);
					} else if ("back".equals(type)) {
						System.out.println("back:" + pageURL);
						PagePane.this.rootRunPanel.backPage(pageURL,
								PagePane.this.page);
					}
					// System.out.println("鼠标按下");
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2) {
					return;
				}
				BaseAndroidComponent component = getComponentByMouse(e
						.getPoint());
				if (component == null) {
					return;
				}
				if (component instanceof BaseAndroidButton) {
					// ((BaseAndroidButton) component).drawPressStyle();
					// repaint();
					PageActionEntity pactionEntity = ((BaseAndroidButton) component)
							.getMouseActions()[1].getPageEntity();
					if (pactionEntity == null) {
						return;
					}
					String page = pactionEntity.getForwardPage();
					String type = pactionEntity.getType();
					if ("hide".equals(type)) {
						System.out.println("hide:" + page);
					} else if ("show".equals(type)) {

						System.out.println("show:" + page);
					} else if ("forward".equals(type)) {
						System.out.println("forward:" + page);

					} else if ("next".equals(type)) {
						System.out.println("next:" + page);

					} else if ("back".equals(type)) {
						System.out.println("back:" + page);
					}
					// System.out.println("鼠标按下");
				}
			}

		});

		addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				BaseAndroidComponent component = getComponentByMouse(e
						.getPoint());
				if (component == null) {
					return;
				}
				if (component instanceof BaseAndroidButton) {
					((BaseAndroidButton) component).drawHoverStyle();
				}
			}

		});
	}

	private BaseAndroidComponent getComponentByMouse(Point point) {
		BaseAndroidComponent component = null;
		for (int i = this.page.getComponentCount() - 1; i >= 0; i--) {
			if (this.page.getComponentAt(i).contains(point)
					&& component == null) {
				component = this.page.getComponentAt(i);
				continue;
			}
			if (this.page.getComponentAt(i) instanceof BaseAndroidButton) {
				((BaseAndroidButton) this.page.getComponentAt(i))
						.drawDefaultStyle();
				// System.out.println(this.page.getComponentAt(i));
				repaint();
			}

		}
		return component;
	}

	@Override
	protected void paintComponent(Graphics gh) {
		Graphics2D g = (Graphics2D) gh;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// g.scale(.5, .5);
		// 背景
		// int x = (int) (this.getWidth() - page.getW()) / 2;
		// int y = (int) (this.getHeight() - page.getH()) / 2;

		Rectangle rect = new Rectangle(0, 0, getWidth(), getHeight());
		g.setColor(page.getBgColor());
		// g.draw(rect);
		g.fill(rect);
		// title 暂时不实现

		// 背景图片
		if (backgroundImage == null) {
			String path = this.project.getProjectPath().concat(File.separator)
					.concat(page.getBackGroundimage());
			ImageIcon icon = new ImageIcon(path);
			this.backgroundImage = icon.getImage();
		}
		g.drawImage(this.backgroundImage, 0, 0, getWidth(), getHeight(), this);
		// 控件
		for (BaseAndroidComponent comp : this.page.getComponents()) {
			comp.draw(g);
		}
	}

	@Override
	public final void update(Graphics g) {
		this.paintComponent(g);
	}

	@Override
	public String toString() {
		return page.toString();
	}

}
