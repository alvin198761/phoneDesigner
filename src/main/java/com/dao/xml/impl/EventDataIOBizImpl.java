package com.dao.xml.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.android.bean.entity.ProjectEntity;
import com.dao.xml.EventDataIOBiz;
import com.runner.entity.EventTreeNode;
import com.runner.entity.ManuEntity;
import com.runner.entity.ManualActionEntity;
import com.runner.entity.PageActionEntity;
import com.runner.entity.StringNode;

public class EventDataIOBizImpl implements EventDataIOBiz {

	@Override
	public List<EventTreeNode> loadTreeNodes(ProjectEntity project) {
		String path = project.getProjectPath().concat(File.separator)
				.concat("page").concat(File.separator).concat("event")
				.concat(File.separator).concat("ActionTree.xml");
		XPath xpath = XPathFactory.newInstance().newXPath();
		List<EventTreeNode> result = new ArrayList<EventTreeNode>();
		try {
			Node root = (Node) xpath.evaluate("/actions", new InputSource(
					new FileInputStream(new File(path))), XPathConstants.NODE);
			// 交互动作
			Node node = (Node) xpath.evaluate("./manual-action", root,
					XPathConstants.NODE);
			String text = (String) xpath.evaluate("@text", node,
					XPathConstants.STRING);
			List<EventTreeNode> children = new ArrayList<EventTreeNode>();
			NodeList nodes = (NodeList) xpath.evaluate("./action", node,
					XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node sn = nodes.item(i);
				ManualActionEntity action = new ManualActionEntity();
				action.setTemplet((String) xpath.evaluate("@template", sn,
						XPathConstants.STRING));
				action.setTarget((String) xpath.evaluate("@target", sn,
						XPathConstants.STRING));
				action.setTitle(sn.getTextContent());
				children.add(action);
			}
			result.add(new StringNode(text, children));
			// 跳转动作
			node = (Node) xpath.evaluate("./page-action", root,
					XPathConstants.NODE);
			text = (String) xpath
					.evaluate("@text", node, XPathConstants.STRING);
			children = new ArrayList<EventTreeNode>();
			nodes = (NodeList) xpath.evaluate("./action", node,
					XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node sn = nodes.item(i);
				PageActionEntity action = new PageActionEntity();
				action.setTitle(sn.getTextContent());
				action.setType((String) xpath.evaluate("@type", sn,
						XPathConstants.STRING));
				children.add(action);
			}
			result.add(new StringNode(text, children));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ManuEntity> loadManuEntity(ProjectEntity project) {
		String path = project.getProjectPath().concat(File.separator)
				.concat("page").concat(File.separator).concat("event")
				.concat(File.separator).concat("DeviceTree.xml");
		XPath xpath = XPathFactory.newInstance().newXPath();
		List<ManuEntity> result = new ArrayList<ManuEntity>();
		try {
			Node root = (Node) xpath.evaluate("/system", new InputSource(
					new FileInputStream(new File(path))), XPathConstants.NODE);
			NodeList floowNodes = (NodeList) xpath.evaluate("./floor", root,
					XPathConstants.NODESET);
			for (int i = 0; i < floowNodes.getLength(); i++) {
				Node floowNode = floowNodes.item(i);
				NodeList rootDeviceNodes = (NodeList) xpath.evaluate("./room ",
						floowNode, XPathConstants.NODESET);
				for (int j = 0; j < rootDeviceNodes.getLength(); j++) {
					Node roomNode = rootDeviceNodes.item(j);
					NodeList deviceNodes = (NodeList) xpath.evaluate(
							"./deivce", roomNode, XPathConstants.NODESET);
					for (int k = 0; k < deviceNodes.getLength(); k++) {
						Node deviceNode = deviceNodes.item(k);
						ManuEntity manu = new ManuEntity();
						manu.setFnum(Integer.parseInt((String) xpath.evaluate(
								"@fnum", floowNode, XPathConstants.STRING)));
						manu.setFtext((String) xpath.evaluate("@name",
								floowNode, XPathConstants.STRING));
						manu.setRnum(Integer.parseInt((String) xpath.evaluate(
								"@rnum", roomNode, XPathConstants.STRING)));
						manu.setRtext((String) xpath.evaluate("@name",
								roomNode, XPathConstants.STRING));
						manu.setDnum(Integer.parseInt((String) xpath.evaluate(
								"@dnum", deviceNode, XPathConstants.STRING)));
						manu.setDtext(deviceNode.getTextContent());
						result.add(manu);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
