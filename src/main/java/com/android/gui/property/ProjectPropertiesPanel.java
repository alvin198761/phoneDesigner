package com.android.gui.property;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;

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
 * 项目属性修改
 * 
 * @author Administrator
 * 
 */
public class ProjectPropertiesPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectEntity project;
	private JCheckBox jCheckBox1;
	private JLabel jLabel4;
	private JTextField resText;
	private JComboBox jComboBox3;
	private JLabel jLabel3;
	private JComboBox jComboBox2;
	private JLabel jLabel2;
	private JComboBox jComboBox1;
	private JLabel jLabel1;

	private boolean flag = true;

	public ProjectPropertiesPanel(final ProjectEntity project) {
		this.project = project;
		this.setPreferredSize(new java.awt.Dimension(220, 100));
		initGUI();
		project.addPropertyChangeListener("addPageViewChange",
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						DefaultComboBoxModel mode = (DefaultComboBoxModel) jComboBox2
								.getModel();
						mode.setSelectedItem(project.getRootPage());
					}
				});
		jCheckBox1.setVisible(false);
	}

	private void initGUI() {
		this.setLayout(null);

		{
			jLabel1 = new JLabel();
			this.add(jLabel1);
			jLabel1.setText("显示方式：");
			jLabel1.setBounds(26, 80, 165, 17);
		}
		{
			ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(
					new String[] { "全屏显示", "保留状态栏" });
			jComboBox1 = new JComboBox();
			this.add(jComboBox1);
			jComboBox1.setModel(jComboBox1Model);
			jComboBox1.setBounds(26, 109, 165, 24);
			jComboBox1.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					project.setFullscreen(jComboBox1.getSelectedIndex());
					project.firePropertyChange("propertyChange", true, false);
					project.setSaved(false);
				}
			});
		}
		{
			jLabel2 = new JLabel();
			this.add(jLabel2);
			jLabel2.setText("根视图：");
			jLabel2.setBounds(26, 154, 165, 17);
		}
		{
			ComboBoxModel jComboBox2Model = new DefaultComboBoxModel() {

				private static final long serialVersionUID = 1L;

				@Override
				public int getSize() {
					return project.getViewCount();
				}

				@Override
				public Object getElementAt(int index) {
					return project.getViewAt(index);
				}
			};
			jComboBox2 = new JComboBox();
			this.add(jComboBox2);
			jComboBox2.setModel(jComboBox2Model);
			jComboBox2.setBounds(26, 183, 165, 24);
			jComboBox2.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						project.setRootPage((AndroidPageContainer) jComboBox2
								.getSelectedItem(), flag);
					}
					flag = false;
				}
			});

		}
		{
			jLabel3 = new JLabel();
			this.add(jLabel3);
			jLabel3.setText("屏幕方向：");
			jLabel3.setBounds(26, 227, 165, 17);
		}
		{
			ComboBoxModel jComboBox3Model = new DefaultComboBoxModel(
					new String[] { "竖屏", "横屏" });
			jComboBox3 = new JComboBox();
			this.add(jComboBox3);
			jComboBox3.setModel(jComboBox3Model);
			jComboBox3.setBounds(26, 263, 165, 24);
			jComboBox3.setSelectedIndex(project.getRes().getMode());
			jComboBox3.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					project.getRes().setMode(jComboBox3.getSelectedIndex());
					project.firePropertyChange("propertyChange", true, false);
					project.setSaved(false);
				}
			});

			jCheckBox1 = new JCheckBox();
			jCheckBox1.addChangeListener(new ChangeListener() {

				@Override
				public void stateChanged(ChangeEvent e) {
					project.setShowSiteMap(jCheckBox1.isSelected());
					project.setSaved(false);
				}
			});
			this.add(jCheckBox1);
			jCheckBox1.setText("显示导航栏");
			jCheckBox1.setBounds(26, 312, 85, 21);
		}
		{
			jLabel4 = new JLabel();
			this.add(jLabel4);
			jLabel4.setText("屏幕属性：");
			jLabel4.setBounds(26, 23, 130, 17);
		}
		{
			resText = new JTextField();
			this.add(resText);
			this.resText.setText("宽：" + project.getRes().getWidth() + " 高："
					+ project.getRes().getHeight());
			this.resText.setEnabled(false);
			resText.setBounds(26, 46, 165, 24);
		}
		this.setPreferredSize(new java.awt.Dimension(220, 429));
	}

	public void initData() {
		flag = true;
		jComboBox2.setSelectedItem(project.getRootPage());
	}
}
