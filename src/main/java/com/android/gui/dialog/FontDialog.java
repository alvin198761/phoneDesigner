package com.android.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.android.lang.Application;

/**
 * 字体对话框
 * 
 * @author TANG
 * 
 */
public class FontDialog extends BaseDailog {
	private static final long serialVersionUID = 1L;
	private FontBiz fontbiz = new FontBiz();
	private final JPanel contentPanel = new JPanel();
	private JLabel previewLabel;
	private JList fontNameList;
	private JList fontTypeList;
	private JList fontSizeList;
	/**
	 * 是否应用
	 */
	private boolean applity = false;

	/**
	 * Create the dialog.
	 */
	public FontDialog(Font compFont) {
		setResizable(false);
		setModal(true);
		setIconImage(Application.mainFrame.getIconImage());
		setTitle("字体设置");
		setBounds(100, 100, 524, 407);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		JLabel fontNameLabel = new JLabel("字体");
		GridBagConstraints gbc_fontNameLabel = new GridBagConstraints();
		gbc_fontNameLabel.anchor = GridBagConstraints.WEST;
		gbc_fontNameLabel.gridwidth = 8;
		gbc_fontNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fontNameLabel.gridx = 0;
		gbc_fontNameLabel.gridy = 0;
		contentPanel.add(fontNameLabel, gbc_fontNameLabel);
		JLabel fontTypeLabel = new JLabel("字型");
		GridBagConstraints gbc_fontTypeLabel = new GridBagConstraints();
		gbc_fontTypeLabel.anchor = GridBagConstraints.WEST;
		gbc_fontTypeLabel.gridwidth = 3;
		gbc_fontTypeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fontTypeLabel.gridx = 12;
		gbc_fontTypeLabel.gridy = 0;
		contentPanel.add(fontTypeLabel, gbc_fontTypeLabel);
		JLabel fontSizeLabel = new JLabel("大小");
		GridBagConstraints gbc_fontSizeLabel = new GridBagConstraints();
		gbc_fontSizeLabel.anchor = GridBagConstraints.WEST;
		gbc_fontSizeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_fontSizeLabel.gridx = 15;
		gbc_fontSizeLabel.gridy = 0;
		contentPanel.add(fontSizeLabel, gbc_fontSizeLabel);
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 10;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		contentPanel.add(scrollPane, gbc_scrollPane);
		fontNameList = new JList(new FontPropertiesListModel(
				fontbiz.getSystemFontNames()));
		fontNameList.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						setPreView();
					}
				});
		fontNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(fontNameList);
		JScrollPane scrollPane_1 = new JScrollPane();

		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridwidth = 4;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 11;
		gbc_scrollPane_2.gridy = 1;
		contentPanel.add(scrollPane_1, gbc_scrollPane_2);
		fontTypeList = new JList(new FontPropertiesListModel(
				fontbiz.getFontType()));
		fontTypeList.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						setPreView();
					}
				});
		fontTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(fontTypeList);
		JScrollPane scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 15;
		gbc_scrollPane_1.gridy = 1;
		contentPanel.add(scrollPane_2, gbc_scrollPane_1);
		fontSizeList = new JList(new FontPropertiesListModel(
				fontbiz.getFontSize()));
		fontSizeList.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						setPreView();
					}
				});
		fontSizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(fontSizeList);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 15;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 2;
		contentPanel.add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		previewLabel = new JLabel("Alvin");
		previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(previewLabel);
//
//		JButton colorButton = new JButton(new BaseAction("颜色设置") {
//
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Color selectColor = JColorChooser.showDialog(FontDialog.this,
//						"颜色设置", FontDialog.this.getPreviewLabel()
//								.getForeground());
//				if (selectColor == null) {
//					return;
//				}
//				FontDialog.this.getPreviewLabel().setForeground(selectColor);
//			}
//		});
//		GridBagConstraints gbc_colorButton = new GridBagConstraints();
//		gbc_colorButton.gridwidth = 3;
//		gbc_colorButton.insets = new Insets(0, 0, 0, 5);
//		gbc_colorButton.gridx = 6;
//		gbc_colorButton.gridy = 3;
		// contentPanel.add(colorButton, gbc_colorButton);
		JButton okButton = new JButton("确定");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applity = true;
				cancel();
			}
		});
		GridBagConstraints gbc_okButton = new GridBagConstraints();
		gbc_okButton.gridwidth = 3;
		gbc_okButton.insets = new Insets(0, 0, 0, 5);
		gbc_okButton.gridx = 11;
		gbc_okButton.gridy = 3;
		contentPanel.add(okButton, gbc_okButton);
		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applity = false;
				cancel();
			}
		});
		GridBagConstraints gbc_cancelButton = new GridBagConstraints();
		gbc_cancelButton.gridx = 15;
		gbc_cancelButton.gridy = 3;
		contentPanel.add(cancelButton, gbc_cancelButton);
		int index = fontbiz.getSystemFontNames()
				.indexOf(compFont.getFontName());
		fontNameList.setSelectedIndex(index);
		index = fontbiz.getFontSize().indexOf(compFont.getSize()+"");
		fontSizeList.setSelectedIndex(index);
		index = fontbiz.getStyleIndex(compFont.getStyle());
		fontTypeList.setSelectedIndex(index);
		setLocationRelativeTo(Application.mainFrame);
	}

	/**
	 * 设置预览图
	 */
	private void setPreView() {
		FontPropertiesListModel mode = (FontPropertiesListModel) fontNameList
				.getModel();
		String name = "";
		int style;
		String size = "";
		Object temp = mode.getElementAt(fontNameList.getSelectedIndex());
		if (temp == null) {
			name = mode.getElementAt(0).toString();
		} else {
			name = temp.toString();
		}
		mode = (FontPropertiesListModel) fontTypeList.getModel();
		style = fontTypeList.getSelectedIndex();
		mode = (FontPropertiesListModel) fontSizeList.getModel();
		temp = mode.getElementAt(fontSizeList.getSelectedIndex());
		if (temp == null) {
			size = mode.getElementAt(0).toString();
		} else {
			size = temp.toString();
		}
		previewLabel.setFont(fontbiz.getFont(name, style, size));
	}

	public Font getChoiceFont() {
		return this.previewLabel.getFont();
	}

	public String getFontText() {
		int size = fontbiz.getFontSize().indexOf(
				this.previewLabel.getFont().getSize()+"");
		int style = fontbiz.getStyleIndex(this.previewLabel.getFont()
				.getStyle());
		return this.previewLabel.getFont().getName() + "+"
				+ fontSizeList.getModel().getElementAt(size)
				+ "+" + fontTypeList.getModel().getElementAt(style);
	}

	/**
	 * 退出
	 */
	private void cancel() {
		setVisible(false);
	}

	public JLabel getPreviewLabel() {
		return previewLabel;
	}

	public boolean isApplity() {
		return applity;
	}

	public void setApplity(boolean applity) {
		this.applity = applity;
	}

	class FontPropertiesListModel extends AbstractListModel {

		private static final long serialVersionUID = 1L;
		/**
		 * model内容
		 */
		private List<String> content;

		public FontPropertiesListModel(List<String> content) {
			if (content == null) {
				content = new ArrayList<String>();
			}
			this.content = content;
		}

		public Object getElementAt(int index) {
			if (index == -1) {
				return null;
			}
			return content.get(index);
		}

		public int getSize() {
			return content.size();
		}

	}

	class FontBiz {
		/**
		 * 得到当前平台支持的字体
		 * 
		 * @return
		 */
		public List<String> getSystemFontNames() {
			String[] fontName = GraphicsEnvironment
					.getLocalGraphicsEnvironment()
					.getAvailableFontFamilyNames();
			List<String> list = Arrays.asList(fontName);
			Collections.reverse(list);
			return list;
		}

		/**
		 * 字体类型
		 * 
		 * @return
		 */
		public List<String> getFontType() {
			List<String> typeList = new ArrayList<String>();
			typeList.add("常规");
			typeList.add("倾斜");
			typeList.add("粗体");
			typeList.add("粗体" + "倾斜");
			return typeList;
		}

		/**
		 * 字体大小
		 * 
		 * @return
		 */
		public List<String> getFontSize() {
			List<String> fontSizeList = new ArrayList<String>();
			int i = 0;
			for (i = 8; i < 24; i++) {
				fontSizeList.add(i + "");
			}
			for (; i <= 72; i += 12) {
				fontSizeList.add(i + "");
			}
			return fontSizeList;
		}

		/**
		 * 得到字体
		 * 
		 * @param name
		 * @param type
		 * @param size
		 * @return
		 */
		public Font getFont(String name, int type, String size) {
			Font font = null;
			int style = Font.PLAIN;
			if (type == 0) {
				style = Font.PLAIN;
			} else if (type == 1) {
				style = Font.ITALIC;
			} else if (type == 2) {
				style = Font.BOLD;
			} else if (type == 3) {
				style = Font.BOLD + Font.ITALIC;
			}
			font = new Font(name, style, Integer.parseInt(size));
			return font;
		}

		public int getStyleIndex(int fontStyle) {
			switch (fontStyle) {
			case Font.PLAIN:
				return 0;
			case Font.ITALIC:
				return 1;
			case Font.BOLD:
				return 2;
			case Font.BOLD + Font.ITALIC:
				return 3;
			default:
				return 0;
			}
		}
	}
}
