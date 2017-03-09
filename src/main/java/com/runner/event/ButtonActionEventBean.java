package com.runner.event;

import java.util.EventObject;

import com.android.bean.shape.data.comp.BaseAndroidButton;

public class ButtonActionEventBean extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ButtonActionEventBean(BaseAndroidButton source ) {
		super(source);
	}

	public BaseAndroidButton getButton() {
		return (BaseAndroidButton) this.source;
	}

 
}
