package com.android.utils;

import java.io.File;
import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XMLUtil {
	private XMLUtil() {

	}

	// private static DocumentBuilderFactory factory = DocumentBuilderFactory
	// .newInstance();
	//
	// /**
	// * 读取xml
	// *
	// * @param xmlFile
	// * @return
	// * @throws SAXException
	// * @throws IOException
	// * @throws ParserConfigurationException
	// */
	// public static Document getDocument(File xmlFile) throws SAXException,
	// IOException, ParserConfigurationException {
	// return factory.newDocumentBuilder().parse(xmlFile);
	// }
	//
	// /**
	// * 创建xml
	// *
	// * @return
	// * @throws ParserConfigurationException
	// */
	// public static Document createNewDocument()
	// throws ParserConfigurationException {
	// return factory.newDocumentBuilder().newDocument();
	// }
	//
	// /**
	// * 获取根节点
	// *
	// * @param doc
	// * @return
	// */
	// public static Element getRootElement(Document doc) {
	// return doc.getDocumentElement();
	// }
	//
	// /**
	// * 获取所有子节点
	// *
	// * @param node
	// * @return
	// */
	// public static List<Element> childs(Node node) {
	// NodeList children = node.getChildNodes();
	// List<Element> result = new LinkedList<Element>();
	// for (int i = 0; i < children.getLength(); i++) {
	// Node c = children.item(i);
	// if (c.getNodeType() != Node.ELEMENT_NODE) {
	// continue;
	// }
	// result.add((Element) c);
	// }
	// return result;
	// }
	//
	// /**
	// * 写入xml
	// *
	// * @param doc
	// * @param file
	// * @throws TransformerException
	// * @throws IOException
	// */
	// public static void writeXml(Document doc, File file)
	// throws TransformerException, IOException {
	// TransformerFactory tr = TransformerFactory.newInstance();
	// tr.setAttribute("indent-number", new Integer(4));// 设置缩进长度为4
	// Transformer trs = tr.newTransformer();
	// trs.setOutputProperty(OutputKeys.INDENT, "yes");// 设置自动换行
	// DOMSource dom = new DOMSource(doc);
	// StreamResult st = new StreamResult(file);
	// trs.setOutputProperty(OutputKeys.ENCODING, "utf-8");
	// trs.transform(dom, st);
	// }
	//
	// public static void setText(Document doc, Element node, String text) {
	// Text textNode = doc.createTextNode(text);
	// node.appendChild(textNode);
	// }
	//
	// public static void main(String[] args) throws
	// ParserConfigurationException,
	// TransformerException, IOException {
	// Document doc = createNewDocument();
	// Element root = doc.createElement("root");
	// doc.appendChild(root);
	// for (int i = 0; i < 10; i++) {
	// Element shapeElement = doc.createElement("shape");
	// root.appendChild(shapeElement);
	// Element cElement = doc.createElement("text");
	// shapeElement.appendChild(cElement);
	// setText(doc, cElement, "text" + i);
	// cElement = doc.createElement("type");
	// setText(doc, cElement, i + "");
	// shapeElement.appendChild(cElement);
	// }
	//
	// writeXml(doc, new File("e:\\test2.txt"));
	// }
	//
	// /**
	// * 加载国际化
	// *
	// * @param filePath
	// * @return
	// */
	// public static Properties loadLanguage(String filePath) {
	// Log.info("读取国际化文件");
	// Properties p = new Properties();
	// File f = new File(filePath);
	// InputStream is = null;
	// try {
	// is = new BufferedInputStream(new FileInputStream(f));
	// p.loadFromXML(is);
	// } catch (FileNotFoundException e) {
	// p = new Properties();
	// saveLanguage(filePath, p);
	// Log.info("如果没有国际化文件，就初始化一个！");
	// e.printStackTrace();
	// } catch (InvalidPropertiesFormatException e) {
	// p = new Properties();
	// e.printStackTrace();
	// } catch (IOException e) {
	// p = new Properties();
	// e.printStackTrace();
	// } finally {
	// try {
	// is.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return p;
	// }
	//
	// /**
	// * 保存国际化
	// *
	// * @param filePath
	// * @param map
	// */
	// public static void saveLanguage(String filePath, Properties map) {
	// Log.info("保存国际化文件");
	// OutputStream os = null;
	// try {
	// os = new BufferedOutputStream(new FileOutputStream(new File(
	// filePath)));
	// map.storeToXML(os, "smm", "utf-8");
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// os.flush();
	// os.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }

	/**
	 * 格式化写入XML
	 * 
	 * @param doc
	 * @param file
	 * @throws Exception
	 */
	public static void outXML(Document doc, File file) throws Exception {
		OutputFormat xmlFormat = new OutputFormat();
		xmlFormat.setEncoding("UTF-8");
		// 设置换行
		xmlFormat.setNewlines(true);
		// 生成缩进
		xmlFormat.setIndent(true);
		// 使用4个空格进行缩进, 可以兼容文本编辑器
		xmlFormat.setIndent("    ");

		XMLWriter writer = null;
		try {
			writer = new XMLWriter(new FileOutputStream(file), xmlFormat);
			writer.write(doc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		writer.flush();
		writer.close();

	}
}
