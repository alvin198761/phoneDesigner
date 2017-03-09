package com.runner.entity;

/**
 * 一个动作
 * 
 * @author Administrator
 * 
 */
public class ManuActionItemDTO {
	// 交互对象
	private ManuEntity manuEntity;
	// 交互动作
	private ManualActionEntity manuAction;
	// 是否选中
	private boolean selected;

	public ManuActionItemDTO(ManualActionEntity manuAction,
			ManuEntity manuEntity) {
		this.manuEntity = manuEntity;
		this.manuAction = manuAction;
	}

	public ManuEntity getManuEntity() {
		return manuEntity;
	}

	public void setManuEntity(ManuEntity manuEntity) {
		this.manuEntity = manuEntity;
	}

	public ManualActionEntity getManuAction() {
		return manuAction;
	}

	public void setManuAction(ManualActionEntity manuAction) {
		this.manuAction = manuAction;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return manuEntity.toString() + " " + manuAction.toString();
	}

}
