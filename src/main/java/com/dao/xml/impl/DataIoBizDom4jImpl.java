package com.dao.xml.impl;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.android.bean.entity.ProjectEntity;
import com.android.bean.entity.ResEntity;
import com.android.bean.shape.ShapeHelper;
import com.android.bean.shape.data.comp.AndroidButton;
import com.android.bean.shape.data.comp.AndroidImageView;
import com.android.bean.shape.data.comp.AndroidOsShapeButton;
import com.android.bean.shape.data.comp.AndroidPageContainer;
import com.android.bean.shape.data.comp.AndroidTempletView;
import com.android.bean.shape.data.comp.AndroidTextLable;
import com.android.bean.shape.data.comp.BaseAndroidComponent;
import com.android.utils.FileUtils;
import com.android.utils.XMLUtil;
import com.dao.xml.DataIoBiz;
import com.runner.entity.ManuActionItemDTO;
import com.runner.entity.ManuEntity;
import com.runner.entity.ManualActionEntity;
import com.runner.entity.OntouchAction;
import com.runner.entity.PageActionEntity;

/**
 * 保存的方案，该实现是用文件保存的方式
 * 
 * @author Administrator
 * 
 */
public class DataIoBizDom4jImpl implements DataIoBiz {

	@Override
	public void saveProject(ProjectEntity project) throws Exception {
		File proFile = new File(project.getProjectPath().concat(File.separator)
				.concat(project_file_conf));
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("project");
		// 创建项目的属性保持稳健
		root.addAttribute("id", project.getId());
		root.addAttribute("name", project.getName());
		root.addAttribute("dir", project.getProjectPath());
		root.addAttribute("os", Integer.toString(project.getOsType()));
		root.addAttribute("siteMap", Boolean.toString(project.isShowSiteMap()));
		root.addAttribute("show", Boolean.toString(project.isShow()));
		// 分辨率
		Element resElement = root.addElement("res");
		resElement.addAttribute("index",
				Integer.toString(project.getRes().getIndex()));
		resElement.addAttribute("mode",
				Integer.toString(project.getRes().getMode()));
		resElement.addAttribute("width",
				Integer.toString(project.getRes().getWidth()));
		resElement.addAttribute("height",
				Integer.toString(project.getRes().getHeight()));
		resElement.addAttribute("phoneName", project.getRes().getPhoneName());
		// 保存项目文件
		XMLUtil.outXML(doc, proFile);
	}

	@Override
	public ProjectEntity readProject(String projectDir) throws Exception {
		File proFile = new File(projectDir.concat(File.separator).concat(
				project_file_conf));
		SAXReader reader = new SAXReader();
		Document doc = reader.read(proFile);
		Element root = doc.getRootElement();
		ProjectEntity project = new ProjectEntity();
		project.setId(root.attributeValue("id"));
		project.setName(root.attributeValue("name"));
		project.setProjectPath(root.attributeValue("dir"));
		project.setOsType(Integer.parseInt(root.attributeValue("os")));
		project.setShowSiteMap(Boolean.parseBoolean(root
				.attributeValue("siteMap")));
		project.setShow(Boolean.parseBoolean(root.attributeValue("show")));
		ResEntity res = new ResEntity();
		Element resElement = root.element("res");
		res.setIndex(Integer.parseInt(resElement.attributeValue("index")));
		res.setMode(Integer.parseInt(resElement.attributeValue("mode")));
		res.setWidth(Integer.parseInt(resElement.attributeValue("width")));
		res.setHeight(Integer.parseInt(resElement.attributeValue("height")));
		res.setPhoneName(resElement.attributeValue("phoneName"));
		project.setRes(res);
		return project;
	}

	@Override
	public void saveTempletView(AndroidTempletView templetView)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public AndroidTempletView readTempletView(String templetFile)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createProject(ProjectEntity project) throws Exception {
		File dir = new File(project.getProjectPath());
		if (dir.exists()) {
			return 1;
		}
		// 创建项目的目录
		if (!dir.mkdirs()) {
			return 2;
		}
		// 创建page 目录, 创建image目录
		File tmp = new File(dir, "page".concat(File.separator).concat("image"));
		if (!tmp.mkdirs()) {
			return 3;
		}
		FileUtils.exportFile(tmp.getAbsolutePath(), "defaultImageIcon.png");
		// 创建事件保存文件夹
		tmp = new File(dir, "page".concat(File.separator).concat("event"));
		if (!tmp.mkdirs()) {
			return 4;
		}
		saveProject(project);
		// 保存事件的文件夹
		//FileUtils.exportFile(tmp.getAbsolutePath(), "ActionTree.xml");
		// 设备
		//FileUtils.exportFile(tmp.getAbsolutePath(), "DeviceTree.xml");
		return 0;
	}

	@Override
	public AndroidPageContainer readPage(String pageXML) throws Exception {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(pageXML));
		Element root = doc.getRootElement();
		AndroidPageContainer page = new AndroidPageContainer();
		page.setId(root.attributeValue("id"));
		page.setName(root.attributeValue("name"));
		page.setBgColor(BaseAndroidComponent.string2Color(root
				.attributeValue("bgcolor")));
		page.setRoot(Boolean.parseBoolean(root.attributeValue("root")));
		page.setBackGroundimage(root.attributeValue("bgImage"));
		Element components = root.element("components");
		for (Object obj : components.elements()) {
			Element el = (Element) obj;
			String type = el.attributeValue("type");
			if (type.equals(ShapeHelper.ANDROID_LABEL)) {
				page.addOpenShape(readComponentLabel(el));
			} else if (type.equals(ShapeHelper.ANDROID_BUTTON)) {
				page.addOpenShape(readComponentButton(el));
			} else if (type.equals(ShapeHelper.ANDROID_IMAGEVIEW)) {
				page.addOpenShape(readComponentImageView(el));
			} else if (type.equals(ShapeHelper.ANDROID_OSSHAPEBUTTON)) {
				page.addOpenShape(readComponentOsShapeButton(el));
			}
		}
		return page;
	}

	@Override
	public void savePage(ProjectEntity project, AndroidPageContainer page)
			throws Exception {
		String pagePath = project.getProjectPath().concat(File.separator)
				.concat("page").concat(File.separator).concat(page.getName())
				.concat(".pag");
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("view");
		root.addAttribute("id", page.getId());
		root.addAttribute("name", page.getName());
		root.addAttribute("bgcolor",
				BaseAndroidComponent.color2String(page.getBgColor()));
		root.addAttribute("bgImage", page.getBackGroundimage());
		root.addAttribute("root", Boolean.toString(page.isRoot()));
		Element components = root.addElement("components");
		// 控件
		for (int i = 0; i < page.getComponentCount(); i++) {
			BaseAndroidComponent comp = page.getComponentAt(i);
			if (comp.getType().equals(ShapeHelper.ANDROID_LABEL)) {
				saveComponentLabel(components, (AndroidTextLable) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_IMAGEVIEW)) {
				saveComponentImageView(components, (AndroidImageView) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_BUTTON)) {
				saveComponentButton(components, (AndroidButton) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_OSSHAPEBUTTON)) {
				saveComponentOsSHapeButton(components,
						(AndroidOsShapeButton) comp);
				continue;
			}
			throw new Exception("未识别的控件类型" + comp.getType());
		}
		XMLUtil.outXML(doc, new File(pagePath));
	}

	/**
	 * 图形按钮
	 * 
	 * @param components
	 * @param button
	 */
	private void saveComponentOsSHapeButton(Element components,
			AndroidOsShapeButton button) {
		Element tag = components.addElement(button.getTagName());
		tag.addAttribute("name", button.getName());
		tag.addAttribute("type", button.getType());
		tag.addAttribute("id", button.getId());
		tag.addAttribute("tag", button.getTagIndex());
		tag.addAttribute("x", Double.toString(button.getX()));
		tag.addAttribute("y", Double.toString(button.getY()));
		tag.addAttribute("w", Double.toString(button.getW()));
		tag.addAttribute("h", Double.toString(button.getH()));
		// bg
		tag.addAttribute("defaultBgImage", button.getDefaultImage());
		tag.addAttribute("hoverBgImage", button.getSelecedImage());
		tag.addAttribute("pressBgImage", button.getPressImage());
		// action
		Element actionsElement = tag.addElement("actions");
		for (OntouchAction action : button.getMouseActions()) {
			Element actionElement = actionsElement.addElement(action.getType());
			for (ManuActionItemDTO entity : action.getSendCMDs()) {
				Element sendElement = actionElement.addElement("send");
				sendElement.addAttribute("title", entity.getManuAction()
						.getTitle());
				sendElement.addAttribute("target", entity.getManuAction()
						.getTarget());
				sendElement.addAttribute("templet", entity.getManuAction()
						.getTemplet());
				Element cmdElement = sendElement.addElement("cmd");
				cmdElement.addAttribute("fnum",
						Integer.toString(entity.getManuEntity().getFnum()));
				cmdElement.addAttribute("ftext", entity.getManuEntity()
						.getFtext());
				cmdElement.addAttribute("rnum",
						Integer.toString(entity.getManuEntity().getRnum()));
				cmdElement.addAttribute("rtext", entity.getManuEntity()
						.getRtext());
				cmdElement.addAttribute("dnum",
						Integer.toString(entity.getManuEntity().getDnum()));
				cmdElement.addAttribute("dtext", entity.getManuEntity()
						.getDtext());
			}
			if (action.getPageEntity() != null) {
				Element forwardElement = actionElement.addElement("forward");
				forwardElement.addAttribute("forwardType", action
						.getPageEntity().getType());
				forwardElement.addAttribute("forwardPage", action
						.getPageEntity().getForwardPage());
				forwardElement.addAttribute("animated", action.getPageEntity()
						.getAnimated());
			}
		}
	}

	/**
	 * 读取图形按钮
	 * 
	 * @param el
	 * @return
	 */
	private BaseAndroidComponent readComponentOsShapeButton(Element el) {
		AndroidOsShapeButton button = new AndroidOsShapeButton();
		button.setName(el.attributeValue("name"));
		button.setId(el.attributeValue("id"));
		button.setTagIndex(el.attributeValue("tag"));
		button.setX(Double.parseDouble(el.attributeValue("x")));
		button.setY(Double.parseDouble(el.attributeValue("y")));
		button.setW(Double.parseDouble(el.attributeValue("w")));
		button.setH(Double.parseDouble(el.attributeValue("h")));
		// bg
		button.setDefaultImage(el.attributeValue("defaultBgImage"));
		button.setSelecedImage(el.attributeValue("hoverBgImage"));
		button.setPressImage(el.attributeValue("pressBgImage"));
		// action
		Element actionsElement = el.element("actions");
		for (Object obj : actionsElement.elements()) {
			Element actionEl = (Element) obj;
			OntouchAction action = new OntouchAction(actionEl.getName());
			for (Object sendObj : actionEl.elements("send")) {
				Element sendElement = (Element) sendObj;
				//
				String title = sendElement.attributeValue("title");
				String target = sendElement.attributeValue("target");
				String templet = sendElement.attributeValue("templet");
				ManualActionEntity manuAction = new ManualActionEntity();
				manuAction.setTitle(title);
				manuAction.setTarget(target);
				manuAction.setTemplet(templet);

				Element cmdElement = sendElement.element("cmd");
				ManuEntity entity = new ManuEntity();
				entity.setFnum(Integer.parseInt(cmdElement
						.attributeValue("fnum")));
				entity.setFtext(cmdElement.attributeValue("ftext"));
				entity.setRnum(Integer.parseInt(cmdElement
						.attributeValue("rnum")));
				entity.setRtext(cmdElement.attributeValue("rtext"));
				entity.setDnum(Integer.parseInt(cmdElement
						.attributeValue("dnum")));
				entity.setDtext(cmdElement.attributeValue("dtext"));

				// ManualActionEntity manuAction = new ManualActionEntity();
				// manuAction.setTitle(title);
				// StringNode node = new StringNode(title, null);
				ManuActionItemDTO dto = new ManuActionItemDTO(manuAction,
						entity);
				action.getSendCMDs().add(dto);
			}
			Element forwardElement = actionEl.element("forward");
			if (forwardElement != null) {
				PageActionEntity pageEntity = new PageActionEntity();
				pageEntity
						.setType(forwardElement.attributeValue("forwardType"));
				pageEntity.setForwardPage(forwardElement
						.attributeValue("forwardPage"));
				pageEntity.setAnimated(forwardElement
						.attributeValue("animated"));
				action.setPageEntity(pageEntity);
			}
			button.addAction(action);
		}
		return button;
	}

	/**
	 * 普通按钮
	 * 
	 * @param components
	 * @param button
	 */
	private void saveComponentButton(Element components, AndroidButton button) {
		Element tag = components.addElement(button.getTagName());
		tag.addAttribute("name", button.getName());
		tag.addAttribute("type", button.getType());
		tag.addAttribute("id", button.getId());
		tag.addAttribute("tag", button.getTagIndex());
		tag.addAttribute("x", Double.toString(button.getX()));
		tag.addAttribute("y", Double.toString(button.getY()));
		tag.addAttribute("w", Double.toString(button.getW()));
		tag.addAttribute("h", Double.toString(button.getH()));
		// ------text
		tag.addAttribute("text", button.getText());
		tag.addAttribute("textAlign", Integer.toString(button.getH_TextAlign()));
		tag.addAttribute("textVAlign", Integer.toString(button.getV_textAlign()));
		// font
		Element fontElement = tag.addElement("font");
		fontElement.addAttribute("name", button.getFont().getFontName());
		fontElement.addAttribute("size",
				Integer.toString(button.getFont().getSize()));
		fontElement.addAttribute("style",
				Integer.toString(button.getFont().getStyle()));
		//
		tag.addAttribute("textColor",
				BaseAndroidComponent.color2String(button.getTextColor()));
		// bg
		tag.addAttribute("defaultBgColor", BaseAndroidComponent
				.color2String(button.getDefaultBackGroundColor()));
		tag.addAttribute("hoverColor",
				BaseAndroidComponent.color2String(button.getSelectColor()));
		tag.addAttribute("pressColor",
				BaseAndroidComponent.color2String(button.getPressColor()));
		// action
		Element actionsElement = tag.addElement("actions");
		for (OntouchAction action : button.getMouseActions()) {
			Element actionElement = actionsElement.addElement(action.getType());
			for (ManuActionItemDTO entity : action.getSendCMDs()) {
				Element sendElement = actionElement.addElement("send");
				sendElement.addAttribute("title", entity.getManuAction()
						.getTitle());
				sendElement.addAttribute("target", entity.getManuAction()
						.getTarget());
				sendElement.addAttribute("templet", entity.getManuAction()
						.getTemplet());
				Element cmdElement = sendElement.addElement("cmd");
				cmdElement.addAttribute("fnum",
						Integer.toString(entity.getManuEntity().getFnum()));
				cmdElement.addAttribute("ftext", entity.getManuEntity()
						.getFtext());
				cmdElement.addAttribute("rnum",
						Integer.toString(entity.getManuEntity().getRnum()));
				cmdElement.addAttribute("rtext", entity.getManuEntity()
						.getRtext());
				cmdElement.addAttribute("dnum",
						Integer.toString(entity.getManuEntity().getDnum()));
				cmdElement.addAttribute("dtext", entity.getManuEntity()
						.getDtext());
			}
			if (action.getPageEntity() != null) {
				Element forwardElement = actionElement.addElement("forward");
				forwardElement.addAttribute("forwardType", action
						.getPageEntity().getType());
				forwardElement.addAttribute("forwardPage", action
						.getPageEntity().getForwardPage());
				forwardElement.addAttribute("animated", action.getPageEntity()
						.getAnimated());
			}
		}
	}

	/**
	 * 读取普通按钮控件
	 * 
	 * @param el
	 * @return
	 */
	private BaseAndroidComponent readComponentButton(Element el) {
		AndroidButton button = new AndroidButton();
		button.setName(el.attributeValue("name"));
		button.setId(el.attributeValue("id"));
		button.setTagIndex(el.attributeValue("tag"));
		button.setX(Double.parseDouble(el.attributeValue("x")));
		button.setY(Double.parseDouble(el.attributeValue("y")));
		button.setW(Double.parseDouble(el.attributeValue("w")));
		button.setH(Double.parseDouble(el.attributeValue("h")));
		// text
		button.setText(el.attributeValue("text"));
		button.setH_TextAlign(Integer.parseInt(el.attributeValue("textAlign")));
		Element fontElement = el.element("font");
		String name = fontElement.attributeValue("name");
		int size = Integer.parseInt(fontElement.attributeValue("size"));
		int style = Integer.parseInt(fontElement.attributeValue("style"));
		Font font = new Font(name, style, size);
		button.setFont(font);
		button.setTextColor(BaseAndroidComponent.string2Color(el
				.attributeValue("textColor")));
		// bg
		button.setDefaultBackGroundColor(BaseAndroidComponent.string2Color(el
				.attributeValue("defaultBgColor")));
		button.setSelectColor(BaseAndroidComponent.string2Color(el
				.attributeValue("hoverColor")));
		button.setPressColor(BaseAndroidComponent.string2Color(el
				.attributeValue("pressColor")));
		// action
		Element actionsElement = el.element("actions");
		for (Object obj : actionsElement.elements()) {
			Element actionEl = (Element) obj;
			OntouchAction action = new OntouchAction(actionEl.getName());
			for (Object sendObj : actionEl.elements("send")) {
				Element sendElement = (Element) sendObj;
				Element cmdElement = sendElement.element("cmd");
				ManuEntity entity = new ManuEntity();
				entity.setFnum(Integer.parseInt(cmdElement
						.attributeValue("fnum")));
				entity.setFtext(cmdElement.attributeValue("ftext"));
				entity.setRnum(Integer.parseInt(cmdElement
						.attributeValue("rnum")));
				entity.setRtext(cmdElement.attributeValue("rtext"));
				entity.setDnum(Integer.parseInt(cmdElement
						.attributeValue("dnum")));
				entity.setDtext(cmdElement.attributeValue("dtext"));
				//
				String title = sendElement.attributeValue("title");
				String target = sendElement.attributeValue("target");
				String templet = sendElement.attributeValue("templet");
				ManualActionEntity manuAction = new ManualActionEntity();
				manuAction.setTitle(title);
				manuAction.setTarget(target);
				manuAction.setTemplet(templet);
				ManuActionItemDTO dto = new ManuActionItemDTO(manuAction,
						entity);
				action.getSendCMDs().add(dto);
			}
			Element forwardElement = actionEl.element("forward");
			if (forwardElement != null) {
				PageActionEntity pageEntity = new PageActionEntity();
				pageEntity
						.setType(forwardElement.attributeValue("forwardType"));
				pageEntity.setForwardPage(forwardElement
						.attributeValue("forwardPage"));
				pageEntity.setAnimated(forwardElement
						.attributeValue("animated"));
				action.setPageEntity(pageEntity);
			}
			button.addAction(action);
		}
		return button;
	}

	/**
	 * 保存图片视图
	 * 
	 * @param components
	 * @param imageView
	 */
	private void saveComponentImageView(Element components,
			AndroidImageView imageView) {
		Element tag = components.addElement(imageView.getTagName());
		tag.addAttribute("name", imageView.getName());
		tag.addAttribute("type", imageView.getType());
		tag.addAttribute("id", imageView.getId());
		tag.addAttribute("tag", imageView.getTagIndex());
		tag.addAttribute("x", Double.toString(imageView.getX()));
		tag.addAttribute("y", Double.toString(imageView.getY()));
		tag.addAttribute("w", Double.toString(imageView.getW()));
		tag.addAttribute("h", Double.toString(imageView.getH()));
		// image
		tag.addAttribute("image", imageView.getBackgroudImage());
		tag.addAttribute("imageMode", imageView.getScale());
	}

	/**
	 * 读取图片视图控件
	 * 
	 * @param el
	 * @return
	 */
	private BaseAndroidComponent readComponentImageView(Element el) {
		AndroidImageView imageView = new AndroidImageView();
		imageView.setName(el.attributeValue("name"));
		imageView.setId(el.attributeValue("id"));
		imageView.setTagIndex(el.attributeValue("tag"));
		imageView.setX(Double.parseDouble(el.attributeValue("x")));
		imageView.setY(Double.parseDouble(el.attributeValue("y")));
		imageView.setW(Double.parseDouble(el.attributeValue("w")));
		imageView.setH(Double.parseDouble(el.attributeValue("h")));
		// Image
		imageView.setBackgroudImage(el.attributeValue("image"));
		imageView.setScale(el.attributeValue("imageMode"));
		return imageView;
	}

	/**
	 * 文本控件保存
	 * 
	 * @param components
	 * @param label
	 */
	private void saveComponentLabel(Element components, AndroidTextLable label) {
		Element tag = components.addElement(label.getTagName());
		tag.addAttribute("name", label.getName());
		tag.addAttribute("type", label.getType());
		tag.addAttribute("id", label.getId());
		tag.addAttribute("tag", label.getTagIndex());
		tag.addAttribute("x", Double.toString(label.getX()));
		tag.addAttribute("y", Double.toString(label.getY()));
		tag.addAttribute("w", Double.toString(label.getW()));
		tag.addAttribute("h", Double.toString(label.getH()));
		// ------text
		// tag.addAttribute("text", label.getText());
		tag.addAttribute("textAlign", Integer.toString(label.getH_TextAlign()));
		tag.addAttribute("textVAlign", Integer.toString(label.getV_textAlign()));
		tag.addCDATA(label.getText());
		// font
		Element fontElement = tag.addElement("font");
		fontElement.addAttribute("name", label.getFont().getFontName());
		fontElement.addAttribute("size",
				Integer.toString(label.getFont().getSize()));
		fontElement.addAttribute("style",
				Integer.toString(label.getFont().getStyle()));
		//
		tag.addAttribute("textColor",
				BaseAndroidComponent.color2String(label.getTextColor()));
	}

	/**
	 * 读取文本控件
	 * 
	 * @param el
	 * @return
	 */
	private BaseAndroidComponent readComponentLabel(Element el) {
		AndroidTextLable lal = new AndroidTextLable();
		lal.setName(el.attributeValue("name"));
		lal.setId(el.attributeValue("id"));
		lal.setTagIndex(el.attributeValue("tag"));
		lal.setX(Double.parseDouble(el.attributeValue("x")));
		lal.setY(Double.parseDouble(el.attributeValue("y")));
		lal.setW(Double.parseDouble(el.attributeValue("w")));
		lal.setH(Double.parseDouble(el.attributeValue("h")));
		// text
		lal.setText(el.getStringValue());
		lal.setH_TextAlign(Integer.parseInt(el.attributeValue("textAlign")));
		Element fontElement = el.element("font");
		String name = fontElement.attributeValue("name");
		int size = Integer.parseInt(fontElement.attributeValue("size"));
		int style = Integer.parseInt(fontElement.attributeValue("style"));
		Font font = new Font(name, style, size);
		lal.setFont(font);
		lal.setTextColor(BaseAndroidComponent.string2Color(el
				.attributeValue("textColor")));
		return lal;
	}

	@Override
	public List<BaseAndroidComponent> cloneComponents(
			List<BaseAndroidComponent> components) throws Exception {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("components");
		// 转成xml
		for (BaseAndroidComponent comp : components) {
			if (comp.getType().equals(ShapeHelper.ANDROID_LABEL)) {
				saveComponentLabel(root, (AndroidTextLable) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_IMAGEVIEW)) {
				saveComponentImageView(root, (AndroidImageView) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_BUTTON)) {
				saveComponentButton(root, (AndroidButton) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_OSSHAPEBUTTON)) {
				saveComponentOsSHapeButton(root, (AndroidOsShapeButton) comp);
				continue;
			}
			throw new Exception("未识别的控件类型" + comp.getType());
		}
		// /
		List<BaseAndroidComponent> copyComponents = new ArrayList<BaseAndroidComponent>();
		// 重新加载
		for (Object obj : root.elements()) {
			Element el = (Element) obj;
			String type = el.attributeValue("type");
			if (type.equals(ShapeHelper.ANDROID_LABEL)) {
				copyComponents.add(readComponentLabel(el));
			} else if (type.equals(ShapeHelper.ANDROID_BUTTON)) {
				copyComponents.add(readComponentButton(el));
			} else if (type.equals(ShapeHelper.ANDROID_IMAGEVIEW)) {
				copyComponents.add(readComponentImageView(el));
			} else if (type.equals(ShapeHelper.ANDROID_OSSHAPEBUTTON)) {
				copyComponents.add(readComponentOsShapeButton(el));
			}
		}
		return copyComponents;
	}

	@Override
	public BaseAndroidComponent cloneComponent(BaseAndroidComponent comp)
			throws Exception {
		BaseAndroidComponent copyShape = null;
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("components");
		do {
			if (comp.getType().equals(ShapeHelper.ANDROID_LABEL)) {
				saveComponentLabel(root, (AndroidTextLable) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_IMAGEVIEW)) {
				saveComponentImageView(root, (AndroidImageView) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_BUTTON)) {
				saveComponentButton(root, (AndroidButton) comp);
				continue;
			}
			if (comp.getType().equals(ShapeHelper.ANDROID_OSSHAPEBUTTON)) {
				saveComponentOsSHapeButton(root, (AndroidOsShapeButton) comp);
				continue;
			}
			throw new Exception("未识别的控件类型" + comp.getType());
		} while (false);
		// 重新加载
		do {
			Element el = (Element) root.elements().get(0);
			String type = el.attributeValue("type");
			if (type.equals(ShapeHelper.ANDROID_LABEL)) {
				copyShape = readComponentLabel(el);
				continue;
			} else if (type.equals(ShapeHelper.ANDROID_BUTTON)) {
				copyShape = readComponentButton(el);
				continue;
			} else if (type.equals(ShapeHelper.ANDROID_IMAGEVIEW)) {
				copyShape = readComponentImageView(el);
				continue;
			} else if (type.equals(ShapeHelper.ANDROID_OSSHAPEBUTTON)) {
				copyShape = readComponentOsShapeButton(el);
				continue;
			}
			throw new Exception("未识别的控件类型" + comp.getType());
		} while (false);
		return copyShape;
	}

}
