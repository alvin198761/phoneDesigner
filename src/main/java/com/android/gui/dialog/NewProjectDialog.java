package com.android.gui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.entity.ResEntity;
import com.android.gui.comp.JNumberField;
import com.android.lang.Application;
import com.android.utils.DialogUtil;
import com.android.utils.ResUtils;

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
public class NewProjectDialog extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JTextField projectNameText;
	private JLabel jLabel2;
	private JButton jButton1;
	private JLabel jLabel5;
	private JButton jButton3;
	private JButton jButton2;
	private JComboBox osCombobox;
	private JComboBox resCombobox;
	private JTextField heightText;
	private JLabel jLabel4;
	private JTextField widthText;
	private JLabel jLabel3;
	private JTextField pathText;

	private ProjectEntity project;

	public NewProjectDialog(JFrame frame) {
		super(frame, true);
		setIconImage(frame.getIconImage());
		initGUI();
		setResizable(false);
		this.getRootPane().setDefaultButton(jButton3);
		this.setTitle("新建工程");
		this.resCombobox.setModel(new DefaultComboBoxModel(ResUtils
				.getResEntitys()));
		this.osCombobox
				.setModel(new DefaultComboBoxModel(ResUtils.getOsType()));
		this.pathText.setText(Application.workSpace.getAbsolutePath()
				+ File.separator);
		this.pathText.setEditable(false);
		initAction();
		resCombobox.setSelectedIndex(0);
		//
		ResEntity entity = (ResEntity) resCombobox.getSelectedItem();
		widthText.setText(entity.getWidth() + "");
		heightText.setText(entity.getHeight() + "");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				project = null;
				super.windowClosing(e);
			}
		});
		this.projectNameText.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void removeUpdate(DocumentEvent e) {
						pathText.setText(Application.workSpace
								.getAbsolutePath()
								+ File.separator
								+ projectNameText.getText().trim());
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						pathText.setText(Application.workSpace
								.getAbsolutePath()
								+ File.separator
								+ projectNameText.getText().trim());
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						pathText.setText(Application.workSpace
								.getAbsolutePath()
								+ File.separator
								+ projectNameText.getText().trim());
					}
				});
	}

	private void initAction() {
		this.jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(Application.workSpace);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.showOpenDialog(NewProjectDialog.this);
				File f = chooser.getSelectedFile();
				if (f == null) {
					return;
				}
				pathText.setText(f.getAbsolutePath() + File.separator
						+ projectNameText.getText());
			}
		});
		this.jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				project = null;
				setVisible(false);
			}
		});
		this.jButton3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comfirm()) {
					setVisible(false);
				}
			}

		});
		this.resCombobox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				ResEntity entity = (ResEntity) e.getItem();
				widthText.setText(entity.getWidth() + "");
				heightText.setText(entity.getHeight() + "");
			}
		});
	}

	private boolean comfirm() {
		if (this.projectNameText.getText().trim().equals("")) {
			DialogUtil.promptMessage("项目名称不能为空！", this);
			return false;
		}
		if (this.pathText.getText().trim().equals("")) {
			DialogUtil.promptMessage("项目路径不能为空！", this);
			return false;
		}
		if (this.pathText.getText().equals(
				Application.workSpace.getAbsolutePath().concat(File.separator))) {
			DialogUtil.promptMessage("项目路径不能是工作目录的路径！", this);
			return false;
		}
		ResEntity entity = (ResEntity) this.resCombobox.getSelectedItem();
		project = new ProjectEntity();
		project.setName(this.projectNameText.getText().trim());
		project.setProjectPath(this.pathText.getText().trim());
		project.setOsType(this.osCombobox.getSelectedIndex());
		entity.setWidth(Integer.parseInt(this.widthText.getText().trim()));
		entity.setHeight(Integer.parseInt(this.heightText.getText().trim()));
		if (entity.getWidth() > entity.getHeight()) {
			// 横
			entity.setMode(1);
		} else {
			// 竖
			entity.setMode(0);
		}
		project.setRes(entity);
		return true;
	}

	public ProjectEntity getProject() {
		return this.project;
	}

	private void initGUI() {
		try {
			getContentPane().setLayout(null);

			jLabel1 = new JLabel();
			getContentPane().add(jLabel1);
			jLabel1.setText("工程名称：");
			jLabel1.setBounds(41, 31, 60, 17);

			projectNameText = new JTextField();
			getContentPane().add(projectNameText);
			projectNameText.setBounds(106, 28, 355, 24);

			jLabel2 = new JLabel();
			getContentPane().add(jLabel2);
			jLabel2.setText("工程路径：");
			jLabel2.setBounds(41, 67, 60, 17);

			pathText = new JTextField();
			getContentPane().add(pathText);
			pathText.setBounds(106, 64, 253, 24);

			jButton1 = new JButton();
			getContentPane().add(jButton1);
			jButton1.setText("选择目录");
			jButton1.setBounds(371, 64, 90, 24);

			jLabel3 = new JLabel();
			getContentPane().add(jLabel3);
			jLabel3.setText("分辨率：");
			jLabel3.setBounds(53, 103, 48, 17);

			widthText = new JNumberField();
			getContentPane().add(widthText);
			widthText.setBounds(106, 100, 71, 24);

			jLabel4 = new JLabel();
			getContentPane().add(jLabel4);
			jLabel4.setText("X");
			jLabel4.setBounds(183, 103, 10, 17);

			heightText = new JNumberField();
			getContentPane().add(heightText);
			heightText.setBounds(205, 100, 80, 24);

			ComboBoxModel resComboboxModel = new DefaultComboBoxModel(
					new String[] { "Item One", "Item Two" });
			resCombobox = new JComboBox();
			getContentPane().add(resCombobox);
			resCombobox.setModel(resComboboxModel);
			resCombobox.setBounds(297, 99, 164, 24);

			jLabel5 = new JLabel();
			getContentPane().add(jLabel5);
			jLabel5.setText("运行平台：");
			jLabel5.setBounds(41, 138, 60, 17);

			ComboBoxModel osComboboxModel = new DefaultComboBoxModel(
					new String[] { "Item One", "Item Two" });
			osCombobox = new JComboBox();
			getContentPane().add(osCombobox);
			osCombobox.setModel(osComboboxModel);
			osCombobox.setBounds(106, 134, 355, 24);

			jButton2 = new JButton();
			getContentPane().add(jButton2);
			jButton2.setText("取 消");
			jButton2.setBounds(161, 181, 77, 24);

			jButton3 = new JButton();
			getContentPane().add(jButton3);
			jButton3.setText("创建工程");
			jButton3.setBounds(260, 181, 104, 24);

			this.setSize(515, 263);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
