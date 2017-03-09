package com.android.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.android.lang.SMMSystem;

public class RegexUtil {

	private RegexUtil() {

	}

	private final static String shapeRegex = "(\\w+)=\\\"([^\\\"]+)\\\"";
	private final static String shapeDataRegex = "((" + shapeRegex + "\\s*)+("
			+ SMMSystem.line + ")*)+";

	/**
	 * 不是什么高深的东西，就是把正则的用法；练习一下，
	 * 
	 * @param data
	 * @return
	 */
	public static Map<String, String> analyzerStr2Shape(String data) {
		// data =
		// "type=\"1\" text=\"qqq\" x=\"12\" y=\"23\" w=\"100\" h=\"50\"";
		Pattern pa = Pattern.compile(shapeRegex);
		Matcher mc = pa.matcher(data);
		Map<String, String> result = new HashMap<String, String>();
		while (mc.find()) {
			result.put(mc.group(1), mc.group(2));
		}
		return result;
	}

	public static boolean testStr(String text) {
		return text.matches(shapeDataRegex);
	}

}
