package com.android.gui.property;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.android.bean.shape.data.comp.AndroidButton;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.bean.ui.IDrawEditable;
import com.android.gui.comp.JPropertiesTextField;
import com.android.gui.dialog.FontDialog;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ButtonPropertiesPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField nameText;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JLabel jLabel11;
	private JButton defaultBgButton;
	private JTextField defaultBgText;
	private JButton fontButton;
	private JTextField fontText;
	private JLabel jLabel15;
	private JToggleButton jToggleButton3;
	private JToggleButton jToggleButton2;
	private JToggleButton jToggleButton1;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JLabel jLabel8;
	private JButton textColorButton;
	private JTextField textColorText;
	private JLabel jLabel7;
	private JTextField titleText;
	private JButton pressBgButton;
	private JTextField pressBgText;
	private JLabel jLabel14;
	private JLabel jLabel13;
	private JButton hoverBgButton;
	private JTextField hoverBgText;
	private JLabel jLabel12;
	private JLabel jLabel6;
	private JTextField hText;
	private JTextField wText;
	private JTextField yText;
	private JTextField xText;
	private JLabel jLabel1;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	private AndroidButton button;
	private IDrawEditable page;

	public ButtonPropertiesPanel(AndroidButton button) {
		super();
		this.button = button;
		this.page = Application.getPageByComponent(button);
		initGUI();
		ButtonGroup bg = new ButtonGroup();
		bg.add(jToggleButton1);
		bg.add(jToggleButton2);
		bg.add(jToggleButton3);
		initData();
		initAction();
		this.setPreferredSize(new java.awt.Dimension(220, 444));
		
		button.addPropertyChangeListener("boxChange", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				 xText.setText(Integer.toString((int) ButtonPropertiesPanel. this.button.getX()));
				 yText.setText(Integer.toString((int) ButtonPropertiesPanel.this.button.getY()));
				 wText.setText(Integer.toString((int) ButtonPropertiesPanel.this.button.getW()));
				 hText.setText(Integer.toString((int) ButtonPropertiesPanel.this.button.getH()));
			}
		});
	}

	private void initAction() {
		this.nameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				button.setName(nameText.getText());
				CenterPanelManager.changeComponentName(button, page);
				page.setSaved(false);
			}
		});
		this.xText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField text = (JTextField) e.getComponent();
				if (text.getText().trim().equals("")) {
					return;
				}
				button.setX(Integer.parseInt(xText.getText()));
				page.repaint();
				page.setSaved(false);
			}
		});
		this.yText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField text = (JTextField) e.getComponent();
				if (text.getText().trim().equals("")) {
					return;
				}
				button.setY(Integer.parseInt(yText.getText()));
				page.repaint();
				page.setSaved(false);
			}
		});
		this.wText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField text = (JTextField) e.getComponent();
				if (text.getText().trim().equals("")) {
					return;
				}
				button.setW(Integer.parseInt(wText.getText()));
				page.repaint();
				page.setSaved(false);
			}
		});
		this.hText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				JTextField text = (JTextField) e.getComponent();
				if (text.getText().trim().equals("")) {
					return;
				}
				button.setH(Integer.parseInt(hText.getText()));
				page.repaint();
				page.setSaved(false);
			}
		});
		this.titleText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				button.setText(titleText.getText());
				page.repaint();
				page.setSaved(false);
			}
		});
		this.textColorText.setEditable(false);
		this.textColorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color col = JColorChooser.showDialog(Application.mainFrame,
						button.toString() + " 文字颜色", button.getTextColor());
				if (col == null) {
					return;
				}
				button.setTextColor(col);
				page.repaint();
				textColorText.setText(BaseAndroidComponent.color2String(col));
				// textColorText.setText("R:" + col.getRed() + " G:" +
				// col.getGreen()
				// + " B:" + col.getBlue());
				textColorText.setForeground(col);
				page.setSaved(false);
				// page.repaint();
			}
		});
		initToggle(jToggleButton1);
		initToggle(jToggleButton2);
		initToggle(jToggleButton3);
		//
		this.defaultBgText.setEditable(false);
		this.defaultBgButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color col = JColorChooser.showDialog(Application.mainFrame,
						button.toString() + " 默认背景颜色",
						button.getDefaultBackGroundColor());
				if (col == null) {
					return;
				}
				button.setDefaultBackGroundColor(col);
				page.repaint();
				defaultBgText.setText(BaseAndroidComponent.color2String(col));
				defaultBgText.setForeground(col);
				page.setSaved(false);
			}
		});
		this.hoverBgText.setEditable(false);
		this.hoverBgButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color col = JColorChooser.showDialog(Application.mainFrame,
						button.toString() + " 选中背景颜色", button.getSelectColor());
				if (col == null) {
					return;
				}
				button.setSelectColor(col);
				// page.repaint();
				hoverBgText.setText(BaseAndroidComponent.color2String(col));
				hoverBgText.setForeground(col);
				page.setSaved(false);
			}
		});
		this.pressBgText.setEditable(false);
		this.pressBgButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color col = JColorChooser.showDialog(Application.mainFrame,
						button.toString() + " 高亮背景颜色", button.getPressColor());
				if (col == null) {
					return;
				}
				button.setPressColor(col);
				// page.repaint();
				pressBgText.setText(BaseAndroidComponent.color2String(col));
				pressBgText.setForeground(col);
				page.setSaved(false);
			}
		});
		fontButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FontDialog dialog = new FontDialog(button.getFont());

				dialog.setVisible(true);
				button.setFont(dialog.getChoiceFont());
				int height = page.getDrawPanel()
						.getFontMetrics(button.getFont()).getHeight();
				if (height > button.getH()) {
					button.setH(height + 2);
				}
				page.repaint();
				fontText.setText(dialog.getFontText());
				page.setSaved(false);
			}
		});
	}

	private void initToggle(JToggleButton btn) {

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jToggleButton1.isSelected()) {
					button.setH_TextAlign(BaseAndroidComponent.H_TEXT_ALIGN_LEFT);
				} else if (jToggleButton2.isSelected()) {
					button.setH_TextAlign(BaseAndroidComponent.H_TEXT_ALIGN_MIDLLE);
				} else {
					button.setH_TextAlign(BaseAndroidComponent.H_TEXT_ALIGN_RIGHT);
				}
				page.repaint();
				page.setSaved(false);
			}
		});

	}

	public void initData() {
		this.nameText.setText(this.button.getName());
		this.xText.setText(Integer.toString((int) this.button.getX()));
		this.yText.setText(Integer.toString((int) this.button.getY()));
		this.wText.setText(Integer.toString((int) this.button.getW()));
		this.hText.setText(Integer.toString((int) this.button.getH()));
		this.textColorText.setText(BaseAndroidComponent
				.color2String(this.button.getTextColor()));
		this.titleText.setText(this.button.getText());
		switch (button.getH_TextAlign()) {
		case BaseAndroidComponent.H_TEXT_ALIGN_LEFT:
			jToggleButton1.setSelected(true);
			break;
		case BaseAndroidComponent.H_TEXT_ALIGN_MIDLLE:
			jToggleButton2.setSelected(true);
			break;
		case BaseAndroidComponent.H_TEXT_ALIGN_RIGHT:
			jToggleButton3.setSelected(true);
			break;
		}
		this.defaultBgText.setText(BaseAndroidComponent
				.color2String(this.button.getDefaultBackGroundColor()));
		this.hoverBgText.setText(BaseAndroidComponent.color2String(this.button
				.getSelectColor()));
		this.pressBgText.setText(BaseAndroidComponent.color2String(this.button
				.getPressColor()));
	}

	private void initGUI() {
		try {
			// this.setPreferredSize(new java.awt.Dimension(275, 436));
			this.setLayout(null);
			{
				jLabel1 = new JLabel();
				this.add(jLabel1);
				jLabel1.setText("名称：");
				jLabel1.setBounds(18, 15, 41, 17);
			}
			{
				nameText = new JTextField();
				this.add(nameText);
				nameText.setBounds(71, 12, 131, 24);
			}
			{
				jLabel2 = new JLabel();
				this.add(jLabel2);
				jLabel2.setText("X轴：");
				jLabel2.setBounds(18, 52, 41, 17);
			}
			{
				xText = new JPropertiesTextField() {

					@Override
					public void changeText() {
						button.setX(Integer.parseInt(xText.getText()));
						page.repaint();
						page.setSaved(false);
					}
				};
				this.add(xText);
				xText.setBounds(57, 49, 51, 24);
			}
			{
				jLabel3 = new JLabel();
				this.add(jLabel3);
				jLabel3.setText("Y轴：");
				jLabel3.setBounds(120, 52, 41, 17);
			}
			{
				yText = new JPropertiesTextField() {

					@Override
					public void changeText() {
						button.setY(Integer.parseInt(yText.getText()));
						page.repaint();
						page.setSaved(false);
					}
				};
				this.add(yText);
				yText.setBounds(156, 49, 54, 24);
			}
			{
				jLabel4 = new JLabel();
				this.add(jLabel4);
				jLabel4.setText("宽：");
				jLabel4.setBounds(18, 86, 41, 17);
			}
			{
				wText = new JPropertiesTextField() {

					@Override
					public void changeText() {
						button.setW(Integer.parseInt(wText.getText()));
						page.repaint();
						page.setSaved(false);
					}
				};
				this.add(wText);
				wText.setBounds(58, 83, 51, 24);
			}
			{
				jLabel5 = new JLabel();
				this.add(jLabel5);
				jLabel5.setText("高：");
				jLabel5.setBounds(121, 86, 31, 17);
			}
			{
				hText = new JPropertiesTextField() {

					@Override
					public void changeText() {
						button.setH(Integer.parseInt(hText.getText()));
						page.repaint();
						page.setSaved(false);
					}
				};
				this.add(hText);
				hText.setBounds(156, 83, 52, 24);
			}
			{
				jLabel6 = new JLabel();
				this.add(jLabel6);
				jLabel6.setText("文本：");
				jLabel6.setBounds(18, 122, 41, 17);
			}
			{
				titleText = new JTextField();
				this.add(titleText);
				titleText.setBounds(71, 119, 137, 24);
			}
			{
				jLabel7 = new JLabel();
				this.add(jLabel7);
				jLabel7.setText("颜色：");
				jLabel7.setBounds(18, 204, 41, 17);
			}
			{
				textColorText = new JTextField();
				this.add(textColorText);
				textColorText.setBounds(40, 228, 67, 24);
			}
			{
				textColorButton = new JButton();
				this.add(textColorButton);
				textColorButton.setText("选择");
				textColorButton.setBounds(113, 228, 62, 24);
			}
			{
				jLabel8 = new JLabel();
				this.add(jLabel8);
				jLabel8.setText("#");
				jLabel8.setBounds(18, 231, 10, 17);
			}
			{
				jLabel9 = new JLabel();
				this.add(jLabel9);
				jLabel9.setText("默认背景颜色：");
				jLabel9.setBounds(14, 291, 128, 17);
			}
			{
				jLabel10 = new JLabel();
				this.add(jLabel10);
				jLabel10.setText("#");
				jLabel10.setBounds(35, 314, 10, 17);
			}
			{
				defaultBgText = new JTextField();
				this.add(defaultBgText);
				defaultBgText.setBounds(57, 311, 67, 24);
			}
			{
				defaultBgButton = new JButton();
				this.add(defaultBgButton);
				defaultBgButton.setText("选择");
				defaultBgButton.setBounds(150, 311, 62, 24);
			}
			{
				jLabel11 = new JLabel();
				this.add(jLabel11);
				jLabel11.setText("选中后的背景：");
				jLabel11.setBounds(12, 345, 98, 17);
			}
			{
				jLabel12 = new JLabel();
				this.add(jLabel12);
				jLabel12.setText("#");
				jLabel12.setBounds(25, 371, 10, 17);
			}
			{
				hoverBgText = new JTextField();
				this.add(hoverBgText);
				hoverBgText.setBounds(47, 368, 67, 24);
			}
			{
				hoverBgButton = new JButton();
				this.add(hoverBgButton);
				hoverBgButton.setText("选择");
				hoverBgButton.setBounds(140, 368, 62, 24);
			}
			{
				jLabel13 = new JLabel();
				this.add(jLabel13);
				jLabel13.setText("高亮状态背景：");
				jLabel13.setBounds(4, 398, 85, 17);
			}
			{
				jLabel14 = new JLabel();
				this.add(jLabel14);
				jLabel14.setText("#");
				jLabel14.setBounds(25, 418, 10, 17);
			}
			{
				pressBgText = new JTextField();
				this.add(pressBgText);
				pressBgText.setBounds(47, 415, 67, 24);
			}
			{
				pressBgButton = new JButton();
				this.add(pressBgButton);
				pressBgButton.setText("选择");
				pressBgButton.setBounds(140, 418, 62, 24);
			}
			{
				jToggleButton1 = new JToggleButton();
				this.add(jToggleButton1);
				jToggleButton1.setText("L");
				jToggleButton1.setBounds(18, 260, 47, 24);
			}
			{
				jToggleButton2 = new JToggleButton();
				this.add(jToggleButton2);
				jToggleButton2.setText("M");
				jToggleButton2.setBounds(76, 260, 57, 24);
			}
			{
				jToggleButton3 = new JToggleButton();
				this.add(jToggleButton3);
				jToggleButton3.setText("R");
				jToggleButton3.setBounds(138, 260, 50, 24);
			}
			{
				jLabel15 = new JLabel();
				this.add(jLabel15);
				jLabel15.setText("文字字体：");
				jLabel15.setBounds(18, 151, 71, 17);
			}
			{
				fontText = new JTextField();
				this.add(fontText);
				fontText.setBounds(18, 174, 107, 24);
				fontText.setEditable(false);
			}
			{
				fontButton = new JButton();
				this.add(fontButton);
				fontButton.setText("选择");
				fontButton.setBounds(137, 174, 62, 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
