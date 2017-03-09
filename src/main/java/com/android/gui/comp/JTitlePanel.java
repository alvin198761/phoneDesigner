package com.android.gui.comp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.android.bean.shape.ShapeHelper;
import com.android.bean.ui.IDrawEditable;
import com.android.lang.Application;

/**
 * @author issuser
 * 
 */
public class JTitlePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel titleButton;
	private JComponent contentPanel;
	private String type;

	// public JTitlePanel() {
	// this("", new JPanel());
	// }

	public JTitlePanel(String title, JComponent contentPanel) {
		titleButton = new JLabel(title);
		titleButton.setHorizontalAlignment(JLabel.CENTER);
		this.contentPanel = contentPanel;
		setLayout(new BorderLayout(0, 5));
		add(BorderLayout.NORTH, titleButton);
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setMaximumSize(new Dimension(200, 300));
		add(scrollPane);
	}

	public void changeType(String type, IDrawEditable editor) throws Exception {
		if (type.equals(this.type)) {
			return;
		}
		this.type = type;
		contentPanel.removeAll();
		System.gc();
		if (type.equals("changeToAndroidDesginer")) {
			changeToAndroidDesginer(editor);
			updateUI();
			Application.saveType = type;
		}
	}

	// protected void changeToActivity() {
	// titleButton.setText(Language.getValue("Activity"));
	// changeShapeButtons(ShapeHelper.getActiveTypeList());
	// }
	//
	// protected void changeToSeri() {
	// titleButton.setText(Language.getValue("Seri"));
	// changeShapeButtons(ShapeHelper.getSeriTypeList());
	// }

	protected void changeToAndroidDesginer(IDrawEditable editor) {
		titleButton.setText("组件列表");
		changeShapeButtons(ShapeHelper.getAndroidComponents(), editor);
	}

	private void changeShapeButtons(List<String> list, IDrawEditable editor) {
		int x = 0;
		int y = 10;
		int size = 80;
		int h = 0;
		int w = 5 * 2 + ShapeButton.icon_width * 2;
		for (int i = 0; i < list.size(); i++) {
			x = i % 2 * size + 10;
			if (i % 2 == 1) {
				x += 5;
			}
			if (i % 2 == 0) {
				h += size + 5;
			}
			ShapeButton button = new ShapeButton(
					ShapeHelper.createMenuShapeByType(list.get(i)));
			button.setBounds(x, y, size, size);

			contentPanel.add(button);
			if (i % 2 == 1) {
				y += size + 5;
			}
			editor.getDragManager().canDrag(button);
		}
		this.contentPanel.setPreferredSize(new Dimension(w, h + 50));
	}

}
