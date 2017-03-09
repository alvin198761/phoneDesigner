package com.runner.gui.comp;//package com.runner.gui.comp;
//
//import java.awt.Graphics2D;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.swing.Icon;
//
//import com.android.bean.shape.data.comp.BaseAndroidButton;
//import com.android.gui.action.BaseAction;
//import com.runner.event.ButtonActionEventBean;
//import com.runner.event.ButtonActionListener;
//
//public class RunButton extends BaseAndroidButton implements
//		ButtonActionListener {
//
//	private BaseAndroidButton button;
//
//	public RunButton(BaseAndroidButton button) {
//		super(button.getTagIndex());
//		this.button = button;
//	}
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private List<ButtonActionListener> listeners = new ArrayList<ButtonActionListener>();
//
//	@Override
//	public List<BaseAction> getActions() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void drawDefaultStyle() {
//		this.button.drawDefaultStyle();
//
//	}
//
//	@Override
//	public void drawHoverStyle() {
//		this.button.drawHoverStyle();
//	}
//
//	@Override
//	public void drawPressStyle() {
//		this.button.drawPressStyle();
//	}
//
//	@Override
//	protected void drawShape(Graphics2D g) {
//		this.button.draw(g);
//	}
//
//	@Override
//	public void doClick(ButtonActionEventBean event) {
//		doPress(event);
//		for (ButtonActionListener listener : this.listeners) {
//			listener.doClick(event);
//		}
//		doHover(event);
//	}
//
//	@Override
//	public void doHover(ButtonActionEventBean event) {
//		drawHoverStyle();
//		for (ButtonActionListener listener : this.listeners) {
//			listener.doHover(event);
//		}
//	}
//
//	@Override
//	public void doPress(ButtonActionEventBean event) {
//		drawPressStyle();
//		for (ButtonActionListener listener : this.listeners) {
//			listener.doPress(event);
//		}
//	}
//
//	@Override
//	public void doOut(ButtonActionEventBean event) {
//		drawDefaultStyle();
//		for (ButtonActionListener listener : this.listeners) {
//			listener.doPress(event);
//		}
//	}
//
//	@Override
//	public Icon getContrlIcon() {
//		return null;
//	}
//}
