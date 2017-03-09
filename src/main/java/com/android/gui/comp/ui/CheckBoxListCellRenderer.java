package com.android.gui.comp.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.runner.entity.ManuActionItemDTO;
import com.runner.entity.ManuEntity;
import com.runner.entity.PageActionEntity;

public class CheckBoxListCellRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JCheckBox button = new JCheckBox(value.toString());
		if (value instanceof ManuActionItemDTO) {
			button.setSelected(((ManuActionItemDTO) value).isSelected());
		} else if (value instanceof ManuEntity) {
			button.setSelected(((ManuEntity) value).isSelected());
		} else if (value instanceof PageActionEntity) {
			PageActionEntity pageAction = (PageActionEntity) value;
			button.setSelected(pageAction.isSelected());
			button.setText(pageAction.getTitle() + " "
					+ pageAction.getForwardPage());
		}
		if (isSelected) {
			button.setBackground(SystemColor.control);
		} else {
			button.setBackground(Color.white);
		}
		return button;
	}

}
