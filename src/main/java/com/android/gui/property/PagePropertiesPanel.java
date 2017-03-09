package com.android.gui.property;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.gui.comp.ImagePreview;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;
import com.android.utils.FileUtils;

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
 * 页面属性修改
 * 
 * @author Administrator
 * 
 */
public class PagePropertiesPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel jLabel1;
	private JTextField jTextField1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JButton jButton1;
	private JButton clearBackImageBtn;
	private JLabel iconLabel;
	private JScrollPane jScrollPane1;
	private JButton jButton2;
	private JLabel jLabel4;
	private JTextField jTextField2;

	private AndroidPageContainer page;

	public PagePropertiesPanel(AndroidPageContainer page) {
		this.page = page;
		initGUI();
		this.setPreferredSize(new java.awt.Dimension(220, 444));
	}

	private void initGUI() {
		try {
			{
				this.setLayout(null);
				{
					jLabel1 = new JLabel();
					this.add(jLabel1);
					jLabel1.setText("视图名称：");
					jLabel1.setBounds(26, 91, 165, 17);
				}
				{
					jTextField1 = new JTextField();
					this.add(jTextField1);
					jTextField1.setText(page.getName());
					jTextField1.addFocusListener(new FocusAdapter() {
						@Override
						public void focusLost(FocusEvent e) {
							if (jTextField1.getText().trim().equals("")) {
								jTextField1.setText(page.getName());
							} else {
								page.setName(jTextField1.getText().trim());
								CenterPanelManager.changePageName(page);
								page.setSaved(false);
							}
						}
					});
					jTextField1.setBounds(26, 120, 171, 24);
				}
				{
					jLabel2 = new JLabel();
					this.add(jLabel2);
					jLabel2.setText("背景颜色：");
					jLabel2.setBounds(26, 156, 165, 17);
				}
				{
					jLabel3 = new JLabel();
					this.add(jLabel3);
					jLabel3.setText("#");
					jLabel3.setBounds(26, 185, 16, 17);
				}
				{
					jTextField2 = new JTextField();
					jTextField2.setEditable(false);
					this.add(jTextField2);
					jTextField2.setText(BaseAndroidComponent.color2String(
							page.getBgColor()).substring(1));
					// jTextField2.setForeground(page.getBgColor());
					jTextField2.setBounds(50, 182, 62, 24);
				}
				{
					jButton1 = new JButton();
					this.add(jButton1);
					jButton1.setText(">>");
					jButton1.setBounds(127, 182, 70, 24);
					jButton1.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							Color col = JColorChooser.showDialog(
									Application.mainFrame, page.toString()
											+ " 背景颜色", page.getBgColor());
							if (col == null) {
								return;
							}
							String text = BaseAndroidComponent
									.color2String(col);
							page.setBackGroundColor(text);
							jTextField2.setText(text.substring(1));
							page.setSaved(false);

						}
					});
				}
				{
					jLabel4 = new JLabel();
					this.add(jLabel4);
					jLabel4.setText("背景图片：");
					jLabel4.setBounds(26, 218, 70, 17);
				}
				{
					jButton2 = new JButton();
					this.add(jButton2);
					jButton2.setText("选择图片");
					jButton2.setBounds(100, 215, 100, 24);
					jButton2.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							JFileChooser jfc = new JFileChooser();
							ImagePreview preview = new ImagePreview(jfc);
							jfc.addPropertyChangeListener(preview);
							jfc.setAccessory(preview);
							jfc.showOpenDialog(Application.mainFrame);
							if (preview.getImage() == null) {
								page.setBackGroundimage(null);
								page.repaint();
								page.setSaved(false);
								return;
							}
							ProjectEntity pro = Application
									.getProjectByPage(page);
							String realPath = "page/image/"
									+ jfc.getSelectedFile().getName();
							String path = pro.getProjectPath()
									.concat(File.separator).concat(realPath);
							try {
								FileUtils.copy(jfc.getSelectedFile()
										.getAbsolutePath(), path);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							Icon icon = new ImageIcon(path);
							iconLabel.setIcon(icon);
							// 如果图片不在项目中，图片将复制到项目中
							// 这里是要填写项目的相对路径的
							page.setBackGroundimage(realPath);
							page.repaint();
							page.setSaved(false);
						}
					});
				}
				{
					jScrollPane1 = new JScrollPane();
					this.add(jScrollPane1);
					jScrollPane1.setBounds(26, 247, 182, 158);
					{
						iconLabel = new JLabel();
						jScrollPane1.setViewportView(iconLabel);
					}
				}
				{
					clearBackImageBtn = new JButton();
					this.add(clearBackImageBtn);
					clearBackImageBtn.setText("清空背景图片");
					clearBackImageBtn.setBounds(26, 420, 154, 24);
					clearBackImageBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							page.setBackGroundimage(null);
							iconLabel.setIcon(null);
							iconLabel.repaint();
							page.repaint();
							page.setSaved(false);
						}
					});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initData() {
		if (page.getDrawPanel() != null
				&& page.getDrawPanel().getBackGroundImage() != null) {
			iconLabel.setIcon(new ImageIcon(page.getDrawPanel()
					.getBackGroundImage()));
		}
	}
}
