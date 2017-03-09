package com.android.gui.action.edit;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.android.bean.ui.IDrawEditable;
import com.android.lang.Application;
import com.android.reource.ResourceUtil;

public class CopyAction extends BaseEditAction {

	private static final long serialVersionUID = 1L;

	public CopyAction(IDrawEditable editor) {
		super(ResourceUtil.copy_icon, "复制（C）", editor);
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl C"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("点击 复制 动作");
		Application.drawPaneClipBoard.copy(this.editor);
		this.editor.firePropertyChange("firePropertyChangeEditAction", true,
				false);
	}

	@Override
	public boolean isEnabled() {
		return Application.drawPaneClipBoard.canCopy(this.editor);
	}

}
