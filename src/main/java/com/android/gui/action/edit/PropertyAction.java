package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.ui.IDrawEditable;
import com.android.gui.frame.CenterPanelManager;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

public class PropertyAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public PropertyAction(IDrawEditable editor) {
		super(ResourceUtil.property_icon, "属性", editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击属性动作");
		AndroidPageContainer page = (AndroidPageContainer) Application
				.getPageByComponent(editor.getCurrentSelectShape());
		ProjectEntity project = Application.getProjectByPage(page);
		Object root = Application.treeModel.getRoot();
		CenterPanelManager.selectNode(new TreePath(new Object[] { root,
				project, page, editor.getCurrentSelectShape() }));
		// String text = "";
		// do {
		// text = DialogUtil.inputDialog("Text", null, editor
		// .getCurrentSelectShape().getText());
		// if (text == null) {
		// return;
		// }
		// if (text.trim().equals("")) {
		// DialogUtil.promptWarning("文本为空！");
		// }
		// } while (text.trim().equals(""));
		// editor.getCurrentSelectShape().setText(text);
		// editor.repaint();
	}

	@Override
	public boolean isEnabled() {
		return editor.getCurrentSelectShape() != null
				&& editor.getCurrentSelectShape().isEditable();
	}
}
