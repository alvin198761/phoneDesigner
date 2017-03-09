package com.android.utils;

public class Log {

	private Log() {

	}

	private static void p(String text) {
		System.out.println(text);
	}

	public static void debug(String text) {
		p(text);
	}

	public static void info(String text) {
		p(text);
	}

	public static void warn(String text) {
		p(text);
	}

	public static void error(String text) {
		p(text);
	}
}
