package com.android.gui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.utils.DialogUtil;

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
public class NewAndroidViewDialog extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JTextField viewNameText;
	private JButton jButton1;
	private JButton jButton2;
	private JCheckBox jCheckBox1;

	private AndroidPageContainer view;

	public NewAndroidViewDialog(JFrame frame) {
		super(frame, true);
		setIconImage(frame.getIconImage());
		setTitle("新建视图");
		initGUI();
		setResizable(false);
		this.jButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view = null;
				setVisible(false);
			}
		});

		this.jButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (confirm()) {
					setVisible(false);
				}
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				view = null;
				super.windowClosing(e);
			}
		});
		getRootPane().setDefaultButton(jButton2);
	}

	private boolean confirm() {
		if (this.viewNameText.getText().trim().equals("")) {
			DialogUtil.promptMessage("视图名称不能为空", this);
			return false;
		}
		view = new AndroidPageContainer();
		view.setName(this.viewNameText.getText().trim());
		return true;
	}

	public boolean isRootView() {
		return jCheckBox1.isSelected();
	}

	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					jLabel1 = new JLabel();
					getContentPane().add(jLabel1);
					jLabel1.setText("视图名称");
					jLabel1.setBounds(26, 30, 60, 17);
				}
				{
					viewNameText = new JTextField();
					getContentPane().add(viewNameText);
					viewNameText.setBounds(104, 27, 226, 24);
				}
				{
					jCheckBox1 = new JCheckBox();
					getContentPane().add(jCheckBox1);
					jCheckBox1.setText("定义为根视图");
					jCheckBox1.setBounds(104, 68, 115, 21);
				}
				{
					jButton1 = new JButton();
					getContentPane().add(jButton1);
					jButton1.setText("取 消");
					jButton1.setBounds(104, 168, 72, 24);
				}
				{
					jButton2 = new JButton();
					getContentPane().add(jButton2);
					jButton2.setText("创建视图");
					jButton2.setBounds(187, 168, 101, 24);
				}
			}
			this.setSize(392, 247);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AndroidPageContainer getView() {
		return this.view;
	}

}
