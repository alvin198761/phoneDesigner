package com.android.gui.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.android.bean.entity.ProjectEntity;
import javax.swing.JButton;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 * 删除和关闭项目
 * 
 * @author Administrator
 * 
 */
public class RemoveProjectDialog extends BaseDailog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox jCheckBox1;
	private JButton cancelBtn;
	private JButton okBtn;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JLabel jLabel1;
	private int status = 0;

	/**
	 * Auto-generated main method to display this JDialog
	 */

	public RemoveProjectDialog(ProjectEntity project) {
		initGUI();
		setTitle("删除项目");
		initData(project);
		initAction(project);
		getRootPane().setDefaultButton(okBtn);
	}

	private void initAction(ProjectEntity project) {
		this.okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 1;
				setVisible(false);
			}
		});
		this.cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	public boolean isDeleteDir() {
		return this.jCheckBox1.isSelected();
	}

	public int getDialogStatus() {
		return this.status;
	}

	private void initData(ProjectEntity project) {
		jLabel3.setText(project.getProjectPath());
	}

	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					jLabel1 = new JLabel();
					getContentPane().add(jLabel1);
					jLabel1.setText("你确定要删除该项目吗？");
					jLabel1.setBounds(20, 12, 169, 17);
				}
				{
					jCheckBox1 = new JCheckBox();
					getContentPane().add(jCheckBox1);
					jCheckBox1.setText("删除项目文件夹，如果该操作执行，项目将不可恢复！（不推荐）");
					jCheckBox1.setBounds(20, 51, 385, 21);
				}
				{
					jLabel2 = new JLabel();
					getContentPane().add(jLabel2);
					jLabel2.setText("项目路径：");
					jLabel2.setBounds(20, 92, 111, 17);
				}
				{
					jLabel3 = new JLabel();
					getContentPane().add(jLabel3);
					jLabel3.setText("");
					jLabel3.setBounds(40, 121, 344, 17);
				}
				{
					cancelBtn = new JButton();
					getContentPane().add(cancelBtn);
					cancelBtn.setText("取消");
					cancelBtn.setBounds(150, 162, 109, 24);
				}
				{
					okBtn = new JButton();
					getContentPane().add(okBtn);
					okBtn.setText("确定");
					okBtn.setBounds(291, 162, 100, 24);
				}
			}
			this.setSize(435, 236);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
