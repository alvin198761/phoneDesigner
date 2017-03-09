package com.android.gui.action.toolbar;//package com.android.gui.action.toolbar;
//
//import java.awt.Cursor;
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//
//import javax.swing.Action;
//import javax.swing.KeyStroke;
//
//import com.android.bean.shape.data.BaseDataShape;
//import com.android.gui.action.BaseAction;
//import com.android.lang.Application;
//import com.android.reource.ResourceUtil;
//
//public class DefaultCursorAction extends BaseAction {
//
//	private static final long serialVersionUID = 1L;
//
//	public DefaultCursorAction() {
//		super(ResourceUtil.defaultCurs_icon, "Defult cursor");
//		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
//		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift D"));
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		Application.operStatus = Application.OPER_NONE;
////		Application.drawPanel.setCursor(Cursor.getDefaultCursor());
//		for (BaseDataShape shape : Application.shapes) {
//			shape.setLineHover(false);
//		}
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return Application.saveType != null;
//	}
//
//}
