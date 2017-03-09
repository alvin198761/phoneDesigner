package com.android.gui.property;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidOsShapeButton;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.ui.IDrawEditable;
import com.android.gui.comp.ImagePreview;
import com.android.gui.comp.JPropertiesTextField;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;
import com.android.utils.DialogUtil;
import com.android.utils.FileUtils;

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
public class OsShapeButtonPropertiesPanel extends javax.swing.JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JTextField nameText;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JScrollPane jScrollPane2;
	private JLabel hightLightIconLabel;
	private JButton clearDefaultImageIconBtn;
	private JButton cleanPressIconButton;
	private JButton clearSelectIconButton;
	private JScrollPane jScrollPane3;
	private JButton pressIconButton;
	private JLabel jLabel8;
	private JLabel selectIconLabel;
	private JButton selectIconButton;
	private JLabel jLabel7;
	private JLabel defaultIconLabel;
	private JButton defualtIconButton;
	private JScrollPane jScrollPane1;
	private JLabel jLabel6;
	private JTextField hText;
	private JTextField wText;
	private JTextField yText;
	private JTextField xText;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 * 
	 * @param button
	 */

	private IDrawEditable page;
	private AndroidOsShapeButton button;

	public OsShapeButtonPropertiesPanel(AndroidOsShapeButton button) {
		super();
		this.button = button;
		this.page = Application.getPageByComponent(button);
		initGUI();
		initData();
		initAction();
		this.setPreferredSize(new java.awt.Dimension(220, 622));
		button.addPropertyChangeListener("boxChange", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				 xText.setText(((int)OsShapeButtonPropertiesPanel. this.button.getX()) + "");
				 yText.setText(((int) OsShapeButtonPropertiesPanel. this.button.getY()) + "");
				 wText.setText(((int)OsShapeButtonPropertiesPanel. this. button.getW()) + "");
				 hText.setText(((int)OsShapeButtonPropertiesPanel. this. button.getH()) + "");
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
		this.defualtIconButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(DialogUtil.chooserFile);
				ImagePreview preview = new ImagePreview(jfc);
				jfc.addPropertyChangeListener(preview);
				jfc.setAccessory(preview);
				jfc.showOpenDialog(Application.mainFrame);
				if (preview.getImage() == null) {
					button.setDefaultImage(null);
					page.setSaved(false);
					return;
				}
				DialogUtil.chooserFile = jfc.getSelectedFile().getParentFile();
				String realPath = "page/image/"
						+ jfc.getSelectedFile().getName();
				ProjectEntity pro = Application
						.getProjectByPage((AndroidPageContainer) page);
				String path = pro.getProjectPath().concat(File.separator)
						.concat(realPath);
				try {
					FileUtils.copy(jfc.getSelectedFile().getAbsolutePath(),
							path);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				button.setDefaultImage(realPath);
				page.repaint();
				ImageIcon icon = new ImageIcon(path);
				defaultIconLabel.setIcon(icon);
				page.setSaved(false);
			}
		});
		this.clearDefaultImageIconBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				button.setDefaultImage(null);
				page.setSaved(false);

				String path = Application.getProjectByPage(
						(AndroidPageContainer) page).getProjectPath();
				path = path.concat(File.separator).concat(
						button.getDefaultImage());
				ImageIcon icon = new ImageIcon(new File(path).getAbsolutePath());
				defaultIconLabel.setIcon(icon);
				page.repaint();
			}
		});
		this.selectIconButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(DialogUtil.chooserFile);
				ImagePreview preview = new ImagePreview(jfc);
				jfc.addPropertyChangeListener(preview);
				jfc.setAccessory(preview);
				jfc.showOpenDialog(Application.mainFrame);
				if (preview.getImage() == null) {
					button.setSelecedImage(null);
					page.setSaved(false);
					return;
				}
				DialogUtil.chooserFile = jfc.getSelectedFile().getParentFile();
				String realPath = "page/image/"
						+ jfc.getSelectedFile().getName();
				ProjectEntity pro = Application
						.getProjectByPage((AndroidPageContainer) page);
				String path = pro.getProjectPath().concat(File.separator)
						.concat(realPath);
				try {
					FileUtils.copy(jfc.getSelectedFile().getAbsolutePath(),
							path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				button.setSelecedImage(realPath);
				page.repaint();
				ImageIcon icon = new ImageIcon(path);
				selectIconLabel.setIcon(icon);
				page.setSaved(false);
			}
		});
		this.clearSelectIconButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				button.setSelecedImage(null);
				page.setSaved(false);
				page.repaint();

				String path = Application.getProjectByPage(
						(AndroidPageContainer) page).getProjectPath();
				path = path.concat(File.separator).concat(
						button.getSelecedImage());
				ImageIcon icon = new ImageIcon(new File(path).getAbsolutePath());
				selectIconLabel.setIcon(icon);
			}
		});

		this.pressIconButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(DialogUtil.chooserFile);
				ImagePreview preview = new ImagePreview(jfc);
				jfc.addPropertyChangeListener(preview);
				jfc.setAccessory(preview);
				jfc.showOpenDialog(Application.mainFrame);
				if (preview.getImage() == null) {
					button.setPressImage(null);
					page.setSaved(false);
					return;
				}
				DialogUtil.chooserFile = jfc.getSelectedFile().getParentFile();
				String realPath = "page/image/"
						+ jfc.getSelectedFile().getName();
				ProjectEntity pro = Application
						.getProjectByPage((AndroidPageContainer) page);
				String path = pro.getProjectPath().concat(File.separator)
						.concat(realPath);
				try {
					FileUtils.copy(jfc.getSelectedFile().getAbsolutePath(),
							path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				button.setPressImage(realPath);
				page.repaint();
				ImageIcon icon = new ImageIcon(path);
				hightLightIconLabel.setIcon(icon);
				page.setSaved(false);
			}
		});

		this.cleanPressIconButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				button.setPressImage(null);
				page.setSaved(false);
				page.repaint();

				String path = Application.getProjectByPage(
						(AndroidPageContainer) page).getProjectPath();
				path = path.concat(File.separator).concat(
						button.getPressImage());
				ImageIcon icon = new ImageIcon(new File(path).getAbsolutePath());
				hightLightIconLabel.setIcon(icon);
			}
		});

	}

	public void initData() {
		this.nameText.setText(this.button.getName());
		this.xText.setText(Integer.toString((int) this.button.getX()));
		this.yText.setText(Integer.toString((int) this.button.getY()));
		this.wText.setText(Integer.toString((int) this.button.getW()));
		this.hText.setText(Integer.toString((int) this.button.getH()));
		ProjectEntity project = Application
				.getProjectByPage((AndroidPageContainer) page);
		String path = project.getProjectPath().concat(File.separator);
		if (this.button.getDefaultImage() == null) {
			this.defaultIconLabel.setIcon(ResourceUtil.defaultImage_Icon);
		} else {
			ImageIcon icon = new ImageIcon(path + this.button.getDefaultImage());
			this.defaultIconLabel.setIcon(icon);
		}
		if (this.button.getSelecedImage() == null) {
			this.selectIconLabel.setIcon(ResourceUtil.defaultImage_Icon);
		} else {
			ImageIcon icon = new ImageIcon(path + this.button.getSelecedImage());
			this.selectIconLabel.setIcon(icon);
		}
		if (this.button.getPressImage() == null) {
			this.hightLightIconLabel.setIcon(ResourceUtil.defaultImage_Icon);
		} else {
			ImageIcon icon = new ImageIcon(path + this.button.getPressImage());
			this.hightLightIconLabel.setIcon(icon);
		}
	}

	private void initGUI() {
		try {
			// this.setPreferredSize(new java.awt.Dimension(283, 611));
			this.setLayout(null);
			{
				jLabel1 = new JLabel();
				this.add(jLabel1);
				jLabel1.setText("名称：");
				jLabel1.setBounds(12, 12, 41, 17);
			}
			{
				nameText = new JTextField();
				this.add(nameText);
				nameText.setBounds(65, 9, 124, 24);
			}
			{
				jLabel2 = new JLabel();
				this.add(jLabel2);
				jLabel2.setText("X轴：");
				jLabel2.setBounds(12, 55, 35, 17);
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
				xText.setBounds(53, 52, 35, 24);
			}
			{
				jLabel3 = new JLabel();
				this.add(jLabel3);
				jLabel3.setText("Y轴：");
				jLabel3.setBounds(105, 56, 35, 17);
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
				yText.setBounds(140, 55, 49, 24);
			}
			{
				jLabel4 = new JLabel();
				this.add(jLabel4);
				jLabel4.setText("宽度：");
				jLabel4.setBounds(12, 88, 40, 17);
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
				wText.setBounds(58, 85, 30, 24);
			}
			{
				jLabel5 = new JLabel();
				this.add(jLabel5);
				jLabel5.setText("高度：");
				jLabel5.setBounds(102, 89, 38, 17);
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
				hText.setBounds(140, 88, 46, 24);
			}
			{
				jLabel6 = new JLabel();
				this.add(jLabel6);
				jLabel6.setText("默认背景：");
				jLabel6.setBounds(7, 121, 69, 17);
			}
			{
				defualtIconButton = new JButton();
				this.add(defualtIconButton);
				defualtIconButton.setText("选择");
				defualtIconButton.setBounds(76, 118, 63, 24);
			}
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1);
				jScrollPane1.setBounds(12, 144, 177, 135);
				{
					defaultIconLabel = new JLabel();
					jScrollPane1.setViewportView(defaultIconLabel);
					defaultIconLabel.setText("");
				}
			}
			{
				jLabel7 = new JLabel();
				this.add(jLabel7);
				jLabel7.setText("选中后背景：");
				jLabel7.setBounds(12, 285, 76, 17);
			}
			{
				selectIconButton = new JButton();
				this.add(selectIconButton);
				selectIconButton.setText("选择");
				selectIconButton.setBounds(88, 282, 62, 24);
			}
			{
				jScrollPane2 = new JScrollPane();
				this.add(jScrollPane2);
				jScrollPane2.setBounds(12, 308, 177, 125);
				{
					selectIconLabel = new JLabel();
					jScrollPane2.setViewportView(selectIconLabel);
					selectIconLabel.setText("");
				}
			}
			{
				jLabel8 = new JLabel();
				this.add(jLabel8);
				jLabel8.setText("高亮背景：");
				jLabel8.setBounds(12, 445, 70, 17);
			}
			{
				pressIconButton = new JButton();
				this.add(pressIconButton);
				pressIconButton.setText("选择");
				pressIconButton.setBounds(82, 442, 62, 24);
			}
			{
				jScrollPane3 = new JScrollPane();
				this.add(jScrollPane3);
				jScrollPane3.setBounds(12, 477, 177, 122);
				{
					hightLightIconLabel = new JLabel();
					jScrollPane3.setViewportView(hightLightIconLabel);
					hightLightIconLabel.setText("");
				}
			}
			{
				clearDefaultImageIconBtn = new JButton();
				this.add(clearDefaultImageIconBtn);
				clearDefaultImageIconBtn.setText("清除");
				clearDefaultImageIconBtn.setBounds(144, 118, 65, 24);
			}
			{
				clearSelectIconButton = new JButton();
				this.add(clearSelectIconButton);
				clearSelectIconButton.setText("清除");
				clearSelectIconButton.setBounds(155, 282, 62, 24);
			}
			{
				cleanPressIconButton = new JButton();
				this.add(cleanPressIconButton);
				cleanPressIconButton.setText("清除");
				cleanPressIconButton.setBounds(149, 442, 62, 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
