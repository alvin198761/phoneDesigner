package com.android.gui.action.app;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;

/**
 * 菜单类型的动作，其实他不做任何动作，只是他有一些子动作
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction {
	
	private List<Action> actions = new ArrayList<Action>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MenuAction(String text) {
		super(text);
	}

	public MenuAction(Icon icon, String text) {
		super(icon, text);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	public List<Action> getActions(){
		return this.actions;
	}

}
 