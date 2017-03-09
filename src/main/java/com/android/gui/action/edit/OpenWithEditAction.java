package com.android.gui.action.edit;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.android.bean.ui.IDrawEditable;
import com.android.gui.action.app.BaseAction;
import com.android.gui.frame.CenterPanelManager;

/**
 * 页面编辑
 * 
 * @author Administrator
 * 
 */
public class OpenWithEditAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IDrawEditable editor;

	public OpenWithEditAction(IDrawEditable editor) {
		super("在编辑器中打开");
		this.editor = editor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Logger.getLogger(this.getClass().getName()).info("打开 "+ this.editor.toString() +" 页面");
		CenterPanelManager.openWithEditor(this.editor);
	}

	@Override
	public boolean isEnabled() {
		return !this.editor.isOpen();
	}

}
