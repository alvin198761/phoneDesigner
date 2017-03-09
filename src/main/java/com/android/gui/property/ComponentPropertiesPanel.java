package com.android.gui.property;
import java.awt.BorderLayout;

import java.awt.Dimension;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

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
public class ComponentPropertiesPanel extends JPanel {
	private JPanel buttonPanel;
	private JPanel jPanel1;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JButton jButton2;
	private JLabel jLabel6;
	private JButton jButton1;
	private JButton defaultIconButton;
	private JLabel defaultIconLabel;
	private JTextField hText;
	private JTextField wText;
	private JLabel jLabel2;
	private JTextField jTextField1;
	private JTextField xText;
	private JLabel jLabel1;
	private JToggleButton jToggleButton2;
	private JToggleButton jToggleButton1;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ComponentPropertiesPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public ComponentPropertiesPanel() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new Dimension(301, 485));
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			{
				buttonPanel = new JPanel();
				this.add(buttonPanel, BorderLayout.NORTH);
				{
					jToggleButton1 = new JToggleButton();
					buttonPanel.add(jToggleButton1);
					jToggleButton1.setText("jToggleButton1");
				}
				{
					jToggleButton2 = new JToggleButton();
					buttonPanel.add(jToggleButton2);
					jToggleButton2.setText("jToggleButton2");
				}
			}
			{
				jPanel1 = new JPanel();
				this.add(jPanel1, BorderLayout.CENTER);
				jPanel1.setLayout(null);
				{
					jLabel1 = new JLabel();
					jPanel1.add(jLabel1);
					jLabel1.setText("X:");
					jLabel1.setBounds(30, 25, 11, 17);
				}
				{
					xText = new JTextField();
					jPanel1.add(xText);
					xText.setBounds(53, 22, 80, 24);
				}
				{
					jTextField1 = new JTextField();
					jPanel1.add(jTextField1);
					jTextField1.setBounds(167, 22, 94, 24);
				}
				{
					jLabel2 = new JLabel();
					jPanel1.add(jLabel2);
					jLabel2.setText("W:");
					jLabel2.setBounds(30, 68, 15, 17);
				}
				{
					jLabel3 = new JLabel();
					jPanel1.add(jLabel3);
					jLabel3.setText("Y:");
					jLabel3.setBounds(145, 29, 15, 17);
				}
				{
					jLabel4 = new JLabel();
					jPanel1.add(jLabel4);
					jLabel4.setText("W:");
					jLabel4.setBounds(145, 68, 15, 17);
				}
				{
					wText = new JTextField();
					jPanel1.add(wText);
					wText.setBounds(54, 65, 79, 24);
				}
				{
					hText = new JTextField();
					jPanel1.add(hText);
					hText.setBounds(172, 65, 89, 24);
				}
				{
					defaultIconLabel = new JLabel();
					jPanel1.add(defaultIconLabel);
					defaultIconLabel.setText("\u6b63\u5e38\u65f6\u72b6\u6001");
					defaultIconLabel.setBounds(36, 123, 97, 84);
				}
				{
					defaultIconButton = new JButton();
					jPanel1.add(defaultIconButton);
					defaultIconButton.setText("\u9009\u62e9\u56fe\u7247");
					defaultIconButton.setBounds(172, 123, 62, 24);
				}
				{
					jLabel5 = new JLabel();
					jPanel1.add(jLabel5);
					jLabel5.setText("\u6b63\u5e38\u65f6\u72b6\u6001");
					jLabel5.setBounds(36, 123, 97, 84);
				}
				{
					jButton1 = new JButton();
					jPanel1.add(jButton1);
					jButton1.setText("\u9009\u62e9\u56fe\u7247");
					jButton1.setBounds(172, 239, 62, 24);
				}
				{
					jLabel6 = new JLabel();
					jPanel1.add(jLabel6);
					jLabel6.setText("\u6b63\u5e38\u65f6\u72b6\u6001");
					jLabel6.setBounds(30, 347, 97, 84);
				}
				{
					jButton2 = new JButton();
					jPanel1.add(jButton2);
					jButton2.setText("\u9009\u62e9\u56fe\u7247");
					jButton2.setBounds(172, 347, 62, 24);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
