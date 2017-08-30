package com.toutiao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	public static SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd");//设置日期格式

	
	
	public static String getTime(){
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		return date;
	}
	public static String getDate(){
		String date = dt.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		return date;
	}

}
