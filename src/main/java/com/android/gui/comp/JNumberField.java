package com.android.gui.comp;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.Serializable;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * <p>
 * Title: OpenSwing
 * </p>
 * <p>
 * Description: JNumberField 数字格式输入框
 * </p>
 * 履历:<BR>
 * 2005/04/17 1.将最大长度,小数位长度修正成符合数据库定义的规范,如:NUMBER(10,2)<BR>
 * 即:最大长度10,小数位长度2,其中整数最大可能长度只能为8;<BR>
 * 2005/04/21 修正了不能输入负数的BUG<BR>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
 * @version 1.0
 */
public class JNumberField extends JTextField implements ActionListener,
		FocusListener, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JNumberField() {
		this(true);
	}

	public JNumberField(boolean addAction) {
		this(16, 0, addAction);
	}

	public JNumberField(int intPartLen) {
		this(intPartLen, true);
	}

	public JNumberField(int intPartLen, boolean addAction) {
		this(intPartLen, 0, addAction);
	}

	public JNumberField(int maxLen, int decLen) {
		this(maxLen, decLen, true);
	}

	public JNumberField(int maxLen, int decLen, boolean addAction) {
		setPreferredSize(new Dimension(150, 25));
		setDocument(new NumberDocument(maxLen, decLen));
		super.setHorizontalAlignment(JTextField.RIGHT);
		if (addAction)
			addActionListener(this);
		addFocusListener(this);
	}

	public JNumberField(int maxLen, int decLen, double minRange,
			double maxRange, boolean addAction) {
		setPreferredSize(new Dimension(150, 25));
		setDocument(new NumberDocument(maxLen, decLen, minRange, maxRange));
		super.setHorizontalAlignment(JTextField.RIGHT);
		if (addAction)
			addActionListener(this);
		addFocusListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		transferFocus();
	}

	public void focusGained(FocusEvent e) {
		selectAll();
	}

	public void focusLost(FocusEvent e) {
	}

	// public static void main(String[] args) {
	// JFrame frame = OpenSwingUtil.createDemoFrame("JNumberField Demo");
	// frame.getContentPane().setLayout(new GridLayout(10, 2));
	// frame.getContentPane().add(new JLabel("New JNumberField()"));
	// frame.getContentPane().add(new JNumberField());
	// frame.getContentPane().add(new JLabel("New JNumberField(2)"));
	// frame.getContentPane().add(new JNumberField(2));
	// frame.getContentPane().add(new JLabel("New JNumberField(8,2)"));
	// frame.getContentPane().add(new JNumberField(8, 2));
	// frame.getContentPane().add(new JLabel("New JNumberField(5,2,-10,100)"));
	// frame.getContentPane().add(new JNumberField(5, 2, -10, 100, false));
	// frame.setVisible(true);
	// }
}

class NumberDocument extends PlainDocument {
	int maxLength = 16;
	int decLength = 0;
	double minRange = -Double.MAX_VALUE;
	double maxRange = Double.MAX_VALUE;

	public NumberDocument(int maxLen, int decLen) {
		maxLength = maxLen;
		decLength = decLen;
	}

	/**
	 * @param decLen
	 *            int 小数位长度
	 * @param maxLen
	 *            int 最大长度(含小数位)
	 * @param minRange
	 *            double 最小值
	 * @param maxRange
	 *            double 最大值
	 */
	public NumberDocument(int maxLen, int decLen, double minRange,
			double maxRange) {
		this(maxLen, decLen);
		this.minRange = minRange;
		this.maxRange = maxRange;
	}

	public NumberDocument(int decLen) {
		decLength = decLen;
	}

	public NumberDocument() {
	}

	public void insertString(int offset, String s, AttributeSet a)
			throws BadLocationException {
		String str = getText(0, getLength());

		if (
		// 不能为f,F,d,D
		s.equals("F")
				|| s.equals("f")
				|| s.equals("D")
				|| s.equals("d")
				// 第一位是0时,第二位只能为小数点
				|| (str.trim().equals("0") && !s.substring(0, 1).equals(".") && offset != 0)
				// 整数模式不能输入小数点
				|| (s.equals(".") && decLength == 0)) {
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		String strIntPart = "";
		String strDecPart = "";
		String strNew = str.substring(0, offset) + s
				+ str.substring(offset, getLength());
		strNew = strNew.replaceFirst("-", ""); // 控制能输入负数
		int decPos = strNew.indexOf(".");
		if (decPos > -1) {
			strIntPart = strNew.substring(0, decPos);
			strDecPart = strNew.substring(decPos + 1);
		} else {
			strIntPart = strNew;
		}
		if (strIntPart.length() > (maxLength - decLength)
				|| strDecPart.length() > decLength
				|| (strNew.length() > 1 && strNew.substring(0, 1).equals("0") && !strNew
						.substring(1, 2).equals("."))) {
			Toolkit.getDefaultToolkit().beep();
			return;
		}

		try {
			if (!strNew.equals("") && !strNew.equals("-")) {// 控制能输入负数
				double d = Double.parseDouble(strNew);
				if (d < minRange || d > maxRange) {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			Toolkit.getDefaultToolkit().beep();
			return;
		}
		super.insertString(offset, s, a);
	}

}
