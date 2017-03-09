package com.android.gui.action.toolbar;//package com.android.gui.action.toolbar;
//
//import java.awt.Cursor;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//
//import javax.swing.Action;
//import javax.swing.KeyStroke;
//
//import com.android.gui.action.BaseAction;
//import com.android.lang.Application;
//import com.android.reource.ResourceUtil;
//
//public class SimpleLineAction extends BaseAction {
//
//	private static final long serialVersionUID = 1L;
//
//	public SimpleLineAction() {
//		super(ResourceUtil.line_icon, "Line");
//		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
//		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		Application.operStatus = Application.OPER_DRAWLINE;
////		Application.drawPanel.setCursor(Cursor
////				.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return Application.saveType != null;
//	}
//}
