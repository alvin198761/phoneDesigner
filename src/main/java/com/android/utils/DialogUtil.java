/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.android.utils;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * 
 * @author Chris
 */
public class DialogUtil {

	public static final int SELECT_YES = JOptionPane.YES_OPTION;
	public static final int SELECT_NO = JOptionPane.NO_OPTION;
	public static final int SELECT_CANCEL = JOptionPane.CANCEL_OPTION;
	public static File chooserFile = new File("");

	public static void promptMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Info",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void promptWarning(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Warn",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void promptError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static int confirmDialog(String msg) {
		int res = JOptionPane.showConfirmDialog(null, msg, "Confirm(y/n)",
				JOptionPane.YES_NO_OPTION);
		return res;
	}

	public static int chooserConfigDialog(String msg) {
		int res = JOptionPane.showConfirmDialog(null, msg, "Confirm(y/n/c)",
				JOptionPane.YES_NO_CANCEL_OPTION);
		return res;
	}

	public static void promptMessage(String msg, Component parent) {
		JOptionPane.showMessageDialog(null, msg, "Info",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void promptWarning(String msg, Component parent) {
		JOptionPane.showMessageDialog(null, msg, "Warn",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void promptError(String msg, Component parent) {
		JOptionPane.showMessageDialog(null, msg, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public static int confirmDialog(String msg, Component parent) {
		return JOptionPane.showConfirmDialog(null, msg, "Confirm(y/n)",
				JOptionPane.YES_NO_OPTION);
	}

	public static int chooserConfigDialog(String msg, Component parent) {
		return JOptionPane.showConfirmDialog(parent, msg, "Confirm(y/n/c)",
				JOptionPane.YES_NO_CANCEL_OPTION);
	}

	public static String inputDialog(String title, Component parent,
			String value) {
		return JOptionPane.showInputDialog(parent, title, value);
	}

}
