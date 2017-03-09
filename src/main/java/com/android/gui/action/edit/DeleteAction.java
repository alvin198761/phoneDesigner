package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.ui.IDrawEditable;
import com.android.reource.ResourceUtil;

public class DeleteAction extends BaseEditAction {
	private static final long serialVersionUID = 1L;

	public DeleteAction(IDrawEditable editor) {
		super(ResourceUtil.del_icon, "删除（D）", editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_DELETE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("DELETE"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击删除所选控件");
		editor.removeSelectShape();
		canSave();
	}

	@Override
	public boolean isEnabled() {
		for (BaseDataShape shape : editor.getComponents()) {
			if (shape.isSelect()) {
				return true;
			}
		}
		return false;
	}

}
