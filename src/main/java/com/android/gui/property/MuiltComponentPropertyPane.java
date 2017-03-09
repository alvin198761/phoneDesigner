package com.android.gui.property;

import java.awt.Dimension;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.android.gui.comp.JNumberField;

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
public class MuiltComponentPropertyPane extends javax.swing.JPanel {
	private JLabel jLabel1;
	private JTextField jTextField1;
	private JLabel jLabel2;
	private JTextField jTextField2;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
		
	public MuiltComponentPropertyPane() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new Dimension(252, 416));
			this.setLayout(null);
			{
				jLabel1 = new JLabel();
				this.add(jLabel1);
				jLabel1.setText("宽：");
				jLabel1.setBounds(12, 19, 41, 17);
			}
			{
				jTextField1 = new JNumberField();
				this.add(jTextField1);
				jTextField1.setText("");
				jTextField1.setBounds(65, 16, 161, 24);
			}
			{
				jLabel2 = new JLabel();
				this.add(jLabel2);
				jLabel2.setText("高：");
				jLabel2.setBounds(12, 48, 41, 17);
			}
			{
				jTextField2 = new JNumberField();
				this.add(jTextField2);
				jTextField2.setText("");
				jTextField2.setBounds(65, 45, 161, 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
