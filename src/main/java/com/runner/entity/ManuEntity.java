package com.runner.entity;

public class ManuEntity {

	private int fnum;
	private String ftext;
	private int rnum;
	private String rtext;
	private int dnum;
	private String dtext;

	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getFnum() {
		return fnum;
	}

	public void setFnum(int fnum) {
		this.fnum = fnum;
	}

	public String getFtext() {
		return ftext;
	}

	public void setFtext(String ftext) {
		this.ftext = ftext;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public String getRtext() {
		return rtext;
	}

	public void setRtext(String rtext) {
		this.rtext = rtext;
	}

	public int getDnum() {
		return dnum;
	}

	public void setDnum(int dnum) {
		this.dnum = dnum;
	}

	public String getDtext() {
		return dtext;
	}

	public void setDtext(String dtext) {
		this.dtext = dtext;
	}

	@Override
	public String toString() {
		return ftext + rtext + dtext;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ManuEntity)) {
			return false;
		}
		if (!obj.toString().equals(this.toString())) {
			return false;
		}
		ManuEntity compier = (ManuEntity) obj;
		return this.fnum == compier.fnum && this.rnum == compier.rnum
				&& this.dnum == compier.dnum;
	}

	public String toCMDs() {
		return fnum + "," + rnum + "," + dnum;
	}

}
