package com.android.bean.entity;//package com.android.bean.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.android.bean.BaseObject;
//
///**
// * 按钮事件对象
// * 
// * @author Administrator
// * 
// */
//public class MouseActionEntity extends BaseObject {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	public static final String ontouch = "ontouch";
//	public static String ontouchin = "ontouchin";
//	public static String ontouchout = "ontouchout";
//
//	public MouseActionEntity(String mouseType) {
//		this.mouseType = mouseType;
//		if (this.mouseType.equals(ontouch)) {
//			setName("ontouch");
//		} else if (this.mouseType.equals(ontouchin)) {
//			setName("ontouchin");
//		} else if (this.mouseType.equals(ontouchout)) {
//			setName("ontouchout");
//		}
//	}
//
//	// 事件类型，目前有三种，鼠标进入，鼠标滑过，鼠标移出
//	private String mouseType;
//	// 需要传递的参数
//	private List<String> parameters = new ArrayList<String>();
//	// 隐藏 hide，显示 show ，下一页 next，返回 back
//	private String forwardType;
//	// 跳转页面
//	private String forwardPage;
//	// 是否动画
//	private String pageAnimated;
//	// 頁面標題
//	private String pageTitle;
//
//	public String getForwardType() {
//		return forwardType;
//	}
//
//	public void setForwardType(String forwardType) {
//		this.forwardType = forwardType;
//	}
//
//	public String getForwardPage() {
//		return forwardPage;
//	}
//
//	public void setForwardPage(String forwardPage) {
//		this.forwardPage = forwardPage;
//	}
//
//	public String getPageAnimated() {
//		return pageAnimated;
//	}
//
//	public void setPageAnimated(String pageAnimated) {
//		this.pageAnimated = pageAnimated;
//	}
//
//	public String getPageTitle() {
//		return pageTitle;
//	}
//
//	public void setPageTitle(String pageTitle) {
//		this.pageTitle = pageTitle;
//	}
//
//	public String getMouseType() {
//		return mouseType;
//	}
//
//	public void setMouseType(String mouseType) {
//		this.mouseType = mouseType;
//	}
//
//	public List<String> getParameters() {
//		return parameters;
//	}
//
//	public void setParameters(List<String> parameters) {
//		this.parameters = parameters;
//	}
//
//	@Override
//	public String getName() {
//		return super.getName();
//	}
//
//	public String getParameterString() {
//		StringBuilder sb = new StringBuilder();
//		for (String str : this.parameters) {
//			if (sb.length() > 0) {
//				sb.append(",");
//			}
//			sb.append(str);
//		}
//		return sb.toString();
//	}
//
//}
