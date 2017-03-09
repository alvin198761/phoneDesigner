package com.runner.entity;

import java.util.ArrayList;
import java.util.List;

import com.android.bean.BaseObject;
/**
 * 鼠标动作
 * @author Administrator
 *
 */
public class OntouchAction extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private List<ManuActionItemDTO> sendCMDs = new ArrayList<ManuActionItemDTO>();
	private PageActionEntity pageEntity;
	public static final String ontouch = "ontouch";
	public static String ontouchin = "ontouchin";
	public static String ontouchout = "ontouchout";

	public OntouchAction(String type) {
		this.type = type;
		if (this.type.equals(ontouch)) {
			setName("ontouch");
		} else if (this.type.equals(ontouchin)) {
			setName("ontouchin");
		} else if (this.type.equals(ontouchout)) {
			setName("ontouchout");
		}
	}
	 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<ManuActionItemDTO> getSendCMDs() {
		return sendCMDs;
	}
	public void setSendCMDs(List<ManuActionItemDTO> sendCMDs) {
		this.sendCMDs = sendCMDs;
	}
	public PageActionEntity getPageEntity() {
		return pageEntity;
	}
	public void setPageEntity(PageActionEntity pageEntity) {
		this.pageEntity = pageEntity;
	}
	
	

}
