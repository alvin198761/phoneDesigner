package com.android.gui.comp.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;

public class RadioButtonListCellRenderer implements ListCellRenderer{

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JRadioButton button = new JRadioButton(value.toString());
		button.setSelected(isSelected);
		if(isSelected){
			button.setBackground(SystemColor.control);
		}else{
			button.setBackground(Color.white);
		}
		return button;
	}

}
