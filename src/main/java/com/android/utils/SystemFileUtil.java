package com.android.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.android.bean.shape.data.BaseDataShape;
import com.android.bean.shape.data.BaseLineShape;
import com.android.lang.Application;
import com.android.lang.SMMSystem;

/**
 * 解析本程序生成的文件，和将本程序中的图保存成文件
 * 
 * @author issuser
 * 
 */
public class SystemFileUtil {

	private SystemFileUtil() {
	}

	public static void analyzerFile(File file) throws Exception {
//		String data = new String(unZip(file));
//		Map<String, BaseDataShape> map = new HashMap<String, BaseDataShape>();
//		String[] dataArray = data.split(SMMSystem.line);
//		Application.saveType = dataArray[0];
////		try {
////			Application.shapePanel.changeType(Application.saveType);
////		} catch (Exception e1) {
////			e1.printStackTrace();
////		}
//		BaseDataShape shape;
//		for (String str : dataArray) {
//			String[] shapeArray = str.split("\\s");
//			if (shapeArray.length == 1) {
//				continue;
//			}
//			shape = toShape(shapeArray, map);
//			Application.addShape(shape);
////			shape.draw(Application.g2d);
//		}
//		Application.repaint();
	}

//	private static BaseDataShape toShape(String[] shapeArray,
//			Map<String, BaseDataShape> map) {
//		// id tyep text x y w h
//		// id type text bid bw eid ew
//		String type = shapeArray[1];
//		BaseDataShape shape = null;
//		if (type.equals(ShapeHelper.ACTIVE_ACTION)) {
//			shape = new ActiveActionShape();
//		} else if (type.equals(ShapeHelper.ACTIVE_CONDITION)) {
//			shape = new ActiveConditionShape();
//		} else if (type.equals(ShapeHelper.ACTIVE_START)) {
//			shape = new ActiveStartShape();
//		} else if (type.equals(ShapeHelper.ACTIVE_END)) {
//			shape = new ActiveEndShape();
//		} else if (type.equals(ShapeHelper.ACTIVE_INTEGERFACE)) {
//			shape = new ActiveInterfaceShape();
//		} else if (type.equals(ShapeHelper.ACTIVE_LANE)) {
//			shape = new ActiveLaneShape();
//		} else if (type.equals(ShapeHelper.ACTIVE_LINE)) {
//			shape = new ActiveLineShape();
//		}
//		shape.setId(shapeArray[0]);
//		shape.setText(shapeArray[2]);
//		if (shape instanceof BaseLineShape) {
//			BaseLineShape line = (BaseLineShape) shape;
//			line.setX2(shape.getX() + shape.getW());
//			line.setY2(shape.getY() + shape.getH());
//			line.setBeginShape(map.get(shapeArray[3]), shapeArray[4]);
//			line.setEndShape(map.get(shapeArray[5]), shapeArray[6]);
//		} else {
//			shape.setX(Double.parseDouble(shapeArray[3]));
//			shape.setY(Double.parseDouble(shapeArray[4]));
//			shape.setW(Double.parseDouble(shapeArray[5]));
//			shape.setH(Double.parseDouble(shapeArray[6]));
//			map.put(shape.getId(), shape);
//		}
//		return shape;
//	}

	/**
	 * 压缩后保存
	 * 
	 * @param shapes
	 * @param file
	 * @return
	 */
	public static boolean saveFile(List<BaseDataShape> shapes, File file) {
		StringBuilder data = new StringBuilder();
		StringBuilder line = new StringBuilder();
		data.append(Application.saveType);
		data.append(SMMSystem.line);
		// 将线的内容写在后面
		for (BaseDataShape shape : shapes) {
			if (shape instanceof BaseLineShape) {
				line.append(shape.toData());
				line.append(SMMSystem.line);
				continue;
			}
			data.append(shape.toData());
			data.append(SMMSystem.line);
		}
		data.append(line);
		try {
			zip(file, data.toString().trim().getBytes());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 压缩并保存文件
	 * 
	 * @param file
	 * @param data
	 * @throws Exception
	 */
	public static void zip(File file, byte[] data) throws Exception {
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new GZIPOutputStream(
					new FileOutputStream(file)));
			os.write(data);
		} finally {
			os.flush();
			os.close();
		}
	}

	/**
	 * 解压
	 * 
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static byte[] unZip(File file) throws FileNotFoundException,
			IOException {
		InputStream is = null;
		try {
			is = new GZIPInputStream(new BufferedInputStream(
					new FileInputStream(file)));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i;
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
			byte[] buff = baos.toByteArray();
			baos.flush();
			baos.close();
			return buff;
		} finally {
			is.close();
		}
	}

	public static void main(String[] args) {
		// System.out.println(Long.toHexString(1348120672478l));
		System.out.println(Long.parseLong("139e240f8de", 16));
	}
}
