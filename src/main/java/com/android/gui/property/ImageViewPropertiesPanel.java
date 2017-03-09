package com.android.gui.property;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidImageView;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.ui.IDrawEditable;
import com.android.gui.comp.ImagePreview;
import com.android.gui.comp.JPropertiesTextField;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;
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
public class ImageViewPropertiesPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JTextField nameText;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JButton jButton2;
	private JComboBox displayCombobox;
	private JLabel jLabel9;
	private JLabel iconLabe;
	private JScrollPane jScrollPane1;
	private JButton jButton1;
	private JLabel jLabel7;
	private JTextField hText;
	private JLabel jLabel6;
	private JTextField wText;
	private JTextField yText;
	private JTextField xText;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */

	private AndroidImageView androidImageView;
	private IDrawEditable page;

	public ImageViewPropertiesPanel(AndroidImageView androidImageView) {
		super();
		this.androidImageView = androidImageView;
		this.page = Application.getPageByComponent(androidImageView);
		initGUI();
		initData();
		initAction();
		this.setPreferredSize(new java.awt.Dimension(220, 444));
		androidImageView.addPropertyChangeListener("boxChange", new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				 xText.setText(((int)ImageViewPropertiesPanel. this.androidImageView.getX()) + "");
				 yText.setText(((int) ImageViewPropertiesPanel. this.androidImageView.getY()) + "");
				 wText.setText(((int)ImageViewPropertiesPanel. this. androidImageView.getW()) + "");
				 hText.setText(((int)ImageViewPropertiesPanel. this. androidImageView.getH()) + "");
			}
			
		});
	}

	private void initAction() {
		this.nameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				androidImageView.setName(nameText.getText());
				CenterPanelManager.changeComponentName(androidImageView, page);
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
				androidImageView.setX(Integer.parseInt(xText.getText().trim()));
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
				androidImageView.setY(Integer.parseInt(yText.getText().trim()));
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
				androidImageView.setW(Integer.parseInt(wText.getText().trim()));
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
				androidImageView.setH(Integer.parseInt(hText.getText().trim()));
				page.repaint();
				page.setSaved(false);
			}
		});
		this.jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(DialogUtil.chooserFile);
				ImagePreview preview = new ImagePreview(jfc);
				jfc.addPropertyChangeListener(preview);
				jfc.setAccessory(preview);
				jfc.showOpenDialog(Application.mainFrame);
				if (preview.getImage() == null) {
					androidImageView.setBackgroudImage(null);
					page.setSaved(false);
					return;
				}
				ProjectEntity pro = Application
						.getProjectByPage((AndroidPageContainer) page);
				String path = pro.getProjectPath().concat(File.separator)
						.concat("page").concat(File.separator).concat("image")
						.concat(File.separator)
						.concat(jfc.getSelectedFile().getName());
				DialogUtil.chooserFile = jfc.getSelectedFile().getParentFile();
				try {
					FileUtils.copy(jfc.getSelectedFile().getAbsolutePath(),
							path);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				androidImageView.setBackgroudImage("page" + File.separator
						+ "image" + File.separator
						+ jfc.getSelectedFile().getName());
				page.repaint();
				ImageIcon icon = new ImageIcon(jfc.getSelectedFile()
						.getAbsolutePath());
				iconLabe.setIcon(icon);
				page.setSaved(false);
			}
		});

		this.jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				androidImageView.setBackgroudImage(null);
				page.setSaved(false);
				page.repaint();
				String path = Application.getProjectByPage(
						(AndroidPageContainer) page).getProjectPath();
				path = path.concat(File.separator).concat(
						androidImageView.getBackgroudImage());
				ImageIcon icon = new ImageIcon(new File(path).getAbsolutePath());
				iconLabe.setIcon(icon);
//				iconLabe.repaint();
			}
		});
	}

	public void initData() {
		this.nameText.setText(androidImageView.toString());
		this.xText.setText(((int) androidImageView.getX()) + "");
		this.yText.setText(((int) androidImageView.getY()) + "");
		this.wText.setText(((int) androidImageView.getW()) + "");
		this.hText.setText(((int) androidImageView.getH()) + "");
		ProjectEntity project = Application
				.getProjectByPage((AndroidPageContainer) page);
		//
		iconLabe.setIcon(new ImageIcon(project.getProjectPath()
				.concat(File.separator)
				.concat(androidImageView.getBackgroudImage())));
		if (androidImageView.getScale().equals("yes")) {
			this.displayCombobox.setSelectedIndex(0);
		} else {
			this.displayCombobox.setSelectedIndex(1);
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
				nameText.setBounds(63, 25, 129, 24);
			}
			{
				jLabel3 = new JLabel();
				this.add(jLabel3);
				jLabel3.setText("X轴:");
				jLabel3.setBounds(26, 64, 25, 17);
			}
			{
				xText = new JPropertiesTextField() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void changeText() {
						androidImageView.setX(Integer.parseInt(xText.getText()
								.trim()));
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

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void changeText() {
						androidImageView.setY(Integer.parseInt(yText.getText()
								.trim()));
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
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void changeText() {
						androidImageView.setW(Integer.parseInt(wText.getText()
								.trim()));
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
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void changeText() {
						androidImageView.setH(Integer.parseInt(hText.getText()
								.trim()));
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
				jLabel7.setText("图片");
				jLabel7.setBounds(12, 136, 41, 17);
			}
			{
				jButton1 = new JButton();
				this.add(jButton1);
				jButton1.setText("选择");
				jButton1.setBounds(65, 133, 69, 24);
			}
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1);
				jScrollPane1.setBounds(12, 174, 189, 134);
				{
					iconLabe = new JLabel();
					jScrollPane1.setViewportView(iconLabe);
				}
			}
			{
				jLabel9 = new JLabel();
				this.add(jLabel9);
				jLabel9.setText("显示模式：");
				jLabel9.setBounds(19, 324, 68, 17);
			}
			{
				ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(
						new String[] { "缩放", "平铺" });
				displayCombobox = new JComboBox();
				this.add(displayCombobox);
				displayCombobox.setModel(jComboBox1Model);
				displayCombobox.setBounds(94, 320, 114, 24);
			}
			{
				jButton2 = new JButton();
				this.add(jButton2);
				jButton2.setText("清除");
				jButton2.setBounds(139, 133, 70, 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
