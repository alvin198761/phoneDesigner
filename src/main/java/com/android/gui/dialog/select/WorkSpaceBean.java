package com.android.gui.dialog.select;

/**
 * 工作目录bean
 * 
 * @author 唐植超
 * 
 */
public class WorkSpaceBean {
	private String path;
	private boolean status = false;
	private boolean top = false;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean top) {
		this.top = top;
	}

	@Override
	public String toString() {
		return path;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			WorkSpaceBean w = (WorkSpaceBean) obj;
			return this.path.equals(w.getPath());
		} catch (Exception e) {
			// e.printStackTrace();
			return false;
		}

	}
}
