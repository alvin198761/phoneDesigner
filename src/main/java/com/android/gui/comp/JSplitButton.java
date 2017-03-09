package com.android.gui.comp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

public class JSplitButton extends JButton {
	private static final long serialVersionUID = -7431955200320586601L;
	private JLabel textLabel = new JLabel();
	private JLabel arrowButton = new JLabel();
	private JPanel separatePanel = new JPanel(new BorderLayout());
	private JPanel textPanel = new JPanel(new GridBagLayout());
	private JPanel arrowButtonPanel = new JPanel(new GridBagLayout());
	private JSeparator separator = new JSeparator(JSeparator.NORTH);
	private boolean separatorVisible = true;
	private boolean locationLeft = true;
	private JPopupMenu optionPopMenu;
	private JPopupMenu buttonPopMenu;
	private List<ActionListener> listeners = new ArrayList<ActionListener>();
	private static Color DEFAULT_BORDER_COLOR = Color.LIGHT_GRAY;
	private Color borderColor = DEFAULT_BORDER_COLOR;

	public JSplitButton() {
		decorate();
	}

	public JSplitButton(String text) {
		textLabel.setText(text);
		decorate();
	}

	public JSplitButton(Icon icon) {
		textLabel.setIcon(icon);
		decorate();
	}

	public JSplitButton(String text, Icon icon) {
		textLabel.setText(text);
		textLabel.setIcon(icon);
		decorate();
	}

	protected void decorate() {
		setLayout(new GridBagLayout());
		separatePanel.setOpaque(false);
		arrowButtonPanel.setOpaque(false);
		textPanel.setOpaque(false);
		// 这里主要是给分割条占一个位置，如果不约束的话，则在显示和隐藏分割条时，按钮界面就会扭动
		separatePanel.setPreferredSize(new Dimension(2, 10));
		separatePanel.add(separator, BorderLayout.CENTER);
		arrowButton.setIcon(new ImageIcon("./images/option_7xpng"));
		arrowButtonPanel.add(arrowButton, new GridBagConstraints(0, 0, 1, 1, 1,
				1, GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(0, 2, 0, 2), 0, 0));
		arrowButtonPanel.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 1));
		textPanel.add(textLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
						2, 0, 2), 0, 0));
		textPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 0));
		add(textPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
						0, 0, 0), 0, 0));

		add(separatePanel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
				new Insets(0, 0, 0, 0), 0, 0));
		add(arrowButtonPanel, new GridBagConstraints(2, 0, 1, 1, 1, 1,
				GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0,
						0, 0, 0), 0, 0));

		setMargin(new Insets(0, 0, 0, 0));
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				if (isEnabled()) {
					separator.setVisible(true);
				}
			}

			public void mouseExited(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						arrowButton.setIcon(new ImageIcon(
								"./images/option_7xpng"));
						if (!isSeparatorVisible()) {
							separator.setVisible(false);
						}
						textPanel.setBorder(BorderFactory.createEmptyBorder(1,
								1, 1, 0));
						arrowButtonPanel.setBorder(BorderFactory
								.createEmptyBorder(1, 0, 1, 1));
					}
				});

			}

			@Override
			public void mouseClicked(final MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						int x = separatePanel.getX();
						if (e.getPoint().x > x) {
							arrowButton.setIcon(new ImageIcon(
									"./images/option_7xpng"));
							if (optionPopMenu != null) {
								if (locationLeft) {
									optionPopMenu.show(JSplitButton.this, 0,
											JSplitButton.this.getHeight());
								} else {
									optionPopMenu.show(arrowButtonPanel, 0,
											arrowButtonPanel.getHeight());
								}
							}
						} else {
							for (ActionListener action : listeners) {
								ActionEvent actionEvent = new ActionEvent(
										JSplitButton.this,
										ActionEvent.ACTION_FIRST, "");
								action.actionPerformed(actionEvent);
							}
							if (buttonPopMenu != null) {
								buttonPopMenu.show(JSplitButton.this, 0,
										JSplitButton.this.getHeight());
							}
						}
					}
				});
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						arrowButton.setIcon(new ImageIcon(
								"./images/option_7xpng"));
						arrowButton.updateUI();
					}
				});
			}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = separatePanel.getX();
				if (e.getPoint().x > x) {
					arrowButtonPanel.setBorder(BorderFactory.createMatteBorder(
							1, 0, 1, 1, borderColor));
					textPanel.setBorder(BorderFactory.createEmptyBorder(1, 1,
							1, 0));
				} else {
					textPanel.setBorder(BorderFactory.createMatteBorder(1, 1,
							1, 0, borderColor));
					arrowButtonPanel.setBorder(BorderFactory.createEmptyBorder(
							1, 0, 1, 1));
				}
			}
		});
	}

	/**
	 * 获得中间的分割条是否显示。为true时表示，一直显示，为false时表示单鼠标进入才显示。默认为true，即一直显示
	 * 
	 * @return
	 */
	public boolean isSeparatorVisible() {
		return separatorVisible;
	}

	/**
	 * 设置中间的分割条是否显示。为true时表示，一直显示，为false时表示单鼠标进入才显示。默认为true，即一直显示
	 * 
	 * @param separatorVisible
	 */
	public void setSeparatorVisible(boolean separatorVisible) {
		this.separatorVisible = separatorVisible;
		separator.setVisible(separatorVisible);
	}

	/**
	 * 获取右边箭头按钮菜单的菜单显示对其方式。true表示与按钮左边对齐，false表示与箭头按钮对齐。默认为左对齐
	 * 
	 * @return
	 */
	public boolean isLocationLeft() {
		return locationLeft;
	}

	/**
	 * 设置右边箭头按钮菜单的菜单显示对其方式。true表示与按钮左边对齐，false表示与箭头按钮对齐。默认为左对齐
	 * 
	 * @param locationLeft
	 */
	public void setLocationLeft(boolean locationLeft) {
		this.locationLeft = locationLeft;
	}

	/**
	 * 获得鼠标进入按钮时绘制的边框的颜色。默认为Color.LIGHT_GRAY
	 * 
	 * @return
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * 设置鼠标进入按钮时绘制的边框的颜色。默认为Color.LIGHT_GRAY
	 * 
	 * @param borderColor
	 */
	public void setBorderColor(Color borderColor) {
		if (borderColor == null) {
			throw new IllegalArgumentException("边框颜色不能为空");
		}
		this.borderColor = borderColor;
	}

	/**
	 * 添加按钮监听事件，这里添加到的是按钮左边部分的事件监听。
	 */
	public void addActionListener(ActionListener action) {
		listeners.add(action);
	}

	/**
	 * 移除按钮监听事件。
	 */
	public void removeActionListener(ActionListener action) {
		listeners.remove(action);
	}

	/**
	 * 设置按钮的文本内容。这里设置到按钮左边部分。
	 */
	@Override
	public void setText(String text) {
		textLabel.setText(text);
	}

	/**
	 * 覆盖父类的设置图标方法。设置按钮的图标，这里是设置按钮左边部分图标。
	 */
	@Override
	public void setIcon(Icon icon) {
		textLabel.setIcon(icon);
	}

	/**
	 * 设置点击右边箭头按钮时的菜单
	 * 
	 * @param popupMenu
	 */
	public void setOptionPopMenu(JPopupMenu popupMenu) {
		this.optionPopMenu = popupMenu;
	}

	/**
	 * 获取右边箭头按钮的菜单
	 * 
	 * @return
	 */
	public JPopupMenu getOptionPopMenu() {
		return optionPopMenu;
	}

	/**
	 * 获得按钮左边部分菜单
	 * 
	 * @return
	 */
	public JPopupMenu getButtonPopMenu() {
		return buttonPopMenu;
	}

	/**
	 * 设置箭头左边部分菜单
	 * 
	 * @param buttonPopMenu
	 */
	public void setButtonPopMenu(JPopupMenu buttonPopMenu) {
		this.buttonPopMenu = buttonPopMenu;
	}
}