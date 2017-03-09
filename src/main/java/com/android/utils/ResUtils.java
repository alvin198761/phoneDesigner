package com.android.utils;

import com.android.bean.entity.ResEntity;

public class ResUtils {

	private ResUtils() {
	}

	/**
	 * 分辨率
	 * 
	 * @return
	 */
	public static ResEntity[] getResEntitys() {
		// { "竖", "横" }
		ResEntity[] entitys = new ResEntity[10];
		entitys[0] = new ResEntity(0, 0, "Iphone4/4s", 640, 960);
		entitys[1] = new ResEntity(1, 1, "Iphone4/4s", 960, 640);
		//
		entitys[2] = new ResEntity(2, 0, "Iphone5/5s", 640, 1136);
		entitys[3] = new ResEntity(3, 1, "Iphone5/5s", 1136, 640);
		//
		entitys[4] = new ResEntity(4, 0, "Iphone6", 750, 1334);
		entitys[5] = new ResEntity(5, 1, "Iphone6", 1334, 750);
		//
		entitys[6] = new ResEntity(6, 0, "Iphone6 pro", 1080, 1920);
		entitys[7] = new ResEntity(7, 1, "Iphone6 pro", 1920, 1080);
		//
		entitys[8] = new ResEntity(8, 0, "三星Galaxy Node", 1080, 1920);
		entitys[9] = new ResEntity(9, 1, "三星Galaxy Node", 1920, 1080);
		return entitys;
	}

	/**
	 * 运行平台
	 * 
	 * @return
	 */
	public static String[] getOsType() {
		return new String[] { "IOS", "Android" };

	}
}
