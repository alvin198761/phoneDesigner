package com.android.bean;

import java.beans.PropertyChangeListener;

public interface IBean {

	public void addPropertyChangeListener(PropertyChangeListener listener);

	public void firePropertyChange(String name, Object oldValue, Object newValue);

	public void removePropertyChangeListener(PropertyChangeListener listener);
	
	public String getName();
	
	public String getId();

}
