package com.android.gui.property;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.android.bean.shape.data.comp.AndroidTextLable;
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
public class LabelPropertiesPanel extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JTextField nameText;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JTextArea jTextArea1;
	private JTextField colorText;
	private JLabel jLabel8;
	private JTextField fontText;
	private JLabel jLabel2;
	private JToggleButton jToggleButton3;
	private JToggleButton jToggleButton2;
	private JToggleButton jToggleButton1;
	private JButton colorButton;
	private JButton fontButton;
	private JToggleButton bottomButton;
	private JToggleButton centerButton;
	private JToggleButton topButton;
	private JScrollPane jScrollPane1;
	private JLabel jLabel7;
	private JTextField hText;
	private JLabel jLabel6;
	private JTextField wText;
	private JTextField yText;
	private JTextField xText;

	private AndroidTextLable label;
	private IDrawEditable page;

	public LabelPropertiesPanel(AndroidTextLable label) {
		super();
		this.label = label;
		this.page = Application.getPageByComponent(label);
		initGUI();
		ButtonGroup bg = new ButtonGroup();
		bg.add(jToggleButton1);
		bg.add(jToggleButton2);
		bg.add(jToggleButton3);
		initData();
		initAction();
		this.setPreferredSize(new java.awt.Dimension(220, 444));
		label.addPropertyChangeListener("boxChange",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						xText.setText(((int) LabelPropertiesPanel.this.label
								.getX()) + "");
						yText.setText(((int) LabelPropertiesPanel.this.label
								.getY()) + "");
						wText.setText(((int) LabelPropertiesPanel.this.label
								.getW()) + "");
						hText.setText(((int) LabelPropertiesPanel.this.label
								.getH()) + "");
					}

				});

		label.addPropertyChangeListener("textChange",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						jTextArea1.setText(LabelPropertiesPanel.this.label
								.getText());
					}
				});
	}

	private void initAction() {
		this.nameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				label.setName(nameText.getText());
				CenterPanelManager.changeComponentName(label, page);
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
				label.setX(Integer.parseInt(xText.getText().trim()));
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
				label.setY(Integer.parseInt(yText.getText().trim()));
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
				label.setW(Integer.parseInt(wText.getText().trim()));
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
				label.setH(Integer.parseInt(hText.getText().trim()));
				page.repaint();
				page.setSaved(false);
			}
		});
		this.jTextArea1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				label.setText(jTextArea1.getText().trim());
				page.repaint();
				page.setSaved(false);
			}
		});
		// ---
		initHToggle(jToggleButton1);
		initHToggle(jToggleButton2);
		initHToggle(jToggleButton3);
		//
		initVToggle(topButton);
		initVToggle(centerButton);
		initVToggle(bottomButton);

		fontButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FontDialog dialog = new FontDialog(label.getFont());

				dialog.setVisible(true);
				label.setFont(dialog.getChoiceFont());
				int height = page.getDrawPanel()
						.getFontMetrics(label.getFont()).getHeight();
				if (height > label.getH()) {
					label.setH(height + 2);
				}
				page.repaint();
				fontText.setText(dialog.getFontText());
				page.setSaved(false);
			}
		});
		colorButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Color col = JColorChooser.showDialog(Application.mainFrame,
						label.toString() + " 文字颜色", label.getTextColor());
				if (col == null) {
					return;
				}
				label.setTextColor(col);
				page.repaint();
				// colorText.setText("R:" + col.getRed() + " G:" +
				// col.getGreen()
				// + " B:" + col.getBlue());
				colorText.setText(BaseAndroidComponent.color2String(col));
				colorText.setForeground(col);
				// page.repaint();
				page.setSaved(false);
			}
		});
	}

	private void initHToggle(JToggleButton btn) {
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jToggleButton1.isSelected()) {
					label.setH_TextAlign(BaseAndroidComponent.H_TEXT_ALIGN_LEFT);
				} else if (jToggleButton2.isSelected()) {
					label.setH_TextAlign(BaseAndroidComponent.H_TEXT_ALIGN_MIDLLE);
				} else {
					label.setH_TextAlign(BaseAndroidComponent.H_TEXT_ALIGN_RIGHT);
				}
				page.repaint();
				page.setSaved(false);
			}
		});
	}

	private void initVToggle(JToggleButton btn) {
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (topButton.isSelected()) {
					label.setV_textAlign(BaseAndroidComponent.V_TEXT_ALIGN_TOP);
				} else if (centerButton.isSelected()) {
					label.setV_textAlign(BaseAndroidComponent.V_TEXT_ALIGN_CENTER);
				} else {
					label.setV_textAlign(BaseAndroidComponent.V_TEXT_ALIGN_BOTTOM);
				}
				page.repaint();
				page.setSaved(false);
			}
		});
	}

	private void initData() {
		this.nameText.setText(label.toString());
		this.xText.setText(((int) label.getX()) + "");
		this.yText.setText(((int) label.getY()) + "");
		this.wText.setText(((int) label.getW()) + "");
		this.hText.setText(((int) label.getH()) + "");
		this.jTextArea1.setText(label.getText().trim());
		switch (label.getH_TextAlign()) {
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

		switch (label.getV_textAlign()) {
		case BaseAndroidComponent.V_TEXT_ALIGN_TOP:
			topButton.setSelected(true);
			break;
		case BaseAndroidComponent.V_TEXT_ALIGN_CENTER:
			centerButton.setSelected(true);
			break;
		case BaseAndroidComponent.V_TEXT_ALIGN_BOTTOM:
			bottomButton.setSelected(true);
			break;
		}
	}

	private void initGUI() {
		try {
			this.setLayout(null);
			{
				jLabel1 = new JLabel();
				this.add(jLabel1);
				jLabel1.setText("名称：");
				jLabel1.setBounds(18, 28, 41, 17);
			}
			{
				nameText = new JTextField();
				this.add(nameText);
				nameText.setBounds(71, 25, 121, 24);
			}
			{
				jLabel3 = new JLabel();
				this.add(jLabel3);
				jLabel3.setText("X轴:");
				jLabel3.setBounds(26, 64, 25, 17);
			}
			{
				xText = new JPropertiesTextField() {

					@Override
					public void changeText() {
						label.setX(Integer.parseInt(xText.getText().trim()));
						page.repaint();
						page.setSaved(false);
					}
				};
				this.add(xText);
				xText.setBounds(63, 61, 37, 24);
			}
			{
				jLabel4 = new JLabel();
				this.add(jLabel4);
				jLabel4.setText("Y轴:");
				jLabel4.setBounds(109, 64, 35, 17);
			}
			{
				yText = new JPropertiesTextField() {

					@Override
					public void changeText() {
						label.setY(Integer.parseInt(yText.getText().trim()));
						page.repaint();
						page.setSaved(false);

					}
				};
				this.add(yText);
				yText.setBounds(151, 61, 41, 24);
			}
			{
				jLabel5 = new JLabel();
				this.add(jLabel5);
				jLabel5.setText("宽度:");
				jLabel5.setBounds(12, 100, 36, 17);
			}
			{
				wText = new JPropertiesTextField() {

					@Override
					public void changeText() {
						label.setW(Integer.parseInt(wText.getText().trim()));
						page.repaint();
						page.setSaved(false);
					}
				};
				this.add(wText);
				wText.setBounds(60, 97, 49, 24);
			}
			{
				jLabel6 = new JLabel();
				this.add(jLabel6);
				jLabel6.setText("高度:");
				jLabel6.setBounds(117, 100, 33, 17);
			}
			{
				hText = new JPropertiesTextField() {

					@Override
					public void changeText() {
						label.setH(Integer.parseInt(hText.getText().trim()));
						page.repaint();
						page.setSaved(false);
					}
				};
				this.add(hText);
				hText.setBounds(156, 97, 52, 24);
			}
			{
				jLabel7 = new JLabel();
				this.add(jLabel7);
				jLabel7.setText("文本：");
				jLabel7.setBounds(15, 130, 41, 17);
			}
			{
				fontButton = new JButton();
				this.add(fontButton);
				fontButton.setText("字体");
				fontButton.setBounds(146, 309, 63, 24);
			}
			{
				colorButton = new JButton();
				this.add(colorButton);
				colorButton.setText("颜色");
				colorButton.setBounds(146, 376, 63, 24);
			}
			{
				jToggleButton1 = new JToggleButton();
				this.add(jToggleButton1);
				jToggleButton1.setText("L");
				jToggleButton1.setBounds(-1, 197, 64, 24);
			}
			{
				jToggleButton2 = new JToggleButton();
				this.add(jToggleButton2);
				jToggleButton2.setText("M");
				jToggleButton2.setBounds(68, 197, 73, 24);
			}
			{
				jToggleButton3 = new JToggleButton();
				this.add(jToggleButton3);
				jToggleButton3.setText("R");
				jToggleButton3.setBounds(146, 197, 63, 24);
			}
			{
				jLabel2 = new JLabel();
				this.add(jLabel2);
				jLabel2.setText("文本字体：");
				jLabel2.setBounds(12, 282, 73, 17);
			}
			{
				fontText = new JTextField();
				this.add(fontText);
				fontText.setBounds(15, 309, 125, 24);
				fontText.setEditable(false);
			}
			{
				jLabel8 = new JLabel();
				this.add(jLabel8);
				jLabel8.setText("文字颜色：");
				jLabel8.setBounds(12, 345, 68, 17);
			}
			{
				colorText = new JTextField();
				this.add(colorText);
				colorText.setBounds(15, 376, 125, 24);
				colorText.setEditable(false);
			}
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1);
				jScrollPane1.setBounds(60, 133, 148, 58);
				{
					jTextArea1 = new JTextArea() {
						@Override
						protected void processKeyEvent(KeyEvent e) {
							if (e.getKeyCode() == KeyEvent.VK_ENTER) {
								if (getText().trim().equals("")) {
									return;
								}
								label.setText(jTextArea1.getText());
								page.repaint();
								page.setSaved(false);
							}
							super.processKeyEvent(e);
						}
					};
					jScrollPane1.setViewportView(jTextArea1);
					jTextArea1.setText("jTextArea1");
					jTextArea1.setBounds(27, 367, 58, 34);
				}
			}
			ButtonGroup vBtnG = new ButtonGroup();
			{
				topButton = new JToggleButton();
				vBtnG.add(topButton);
				this.add(topButton);
				topButton.setText("T");
				topButton.setBounds(0, 232, 63, 24);
			}
			{
				centerButton = new JToggleButton();
				vBtnG.add(centerButton);
				this.add(centerButton);
				centerButton.setText("C");
				centerButton.setBounds(68, 232, 73, 24);
			}
			{
				bottomButton = new JToggleButton();
				vBtnG.add(bottomButton);
				this.add(bottomButton);
				bottomButton.setText("B");
				bottomButton.setBounds(146, 232, 63, 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
