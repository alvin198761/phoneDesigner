package com.android.lang;

import java.util.HashMap;
import java.util.Map;

import com.android.gui.action.app.BaseAction;

/**
 * 动作管理
 * 
 * @author issuser
 * 
 */
public class ActionManager {

	public ActionManager() {
	}

	private Map<Class<? extends BaseAction>, BaseAction> actionMap = new HashMap<Class<? extends BaseAction>, BaseAction>();

	public BaseAction getAction(Class<? extends BaseAction> clazz) {
		BaseAction action = actionMap.get(clazz);
		if (action != null) {
			return action;
		}
		try {
			action = clazz.newInstance();
			actionMap.put(clazz, action);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return actionMap.get(clazz);
	}
	
	public void putAcion(BaseAction action){
		this.actionMap.put(action.getClass(), action)		;
		
	}

}
