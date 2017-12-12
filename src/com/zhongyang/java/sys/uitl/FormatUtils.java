package com.zhongyang.java.sys.uitl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {
	
	
	public static String simpleFormat(Date date){
		if(date == null)
			return null;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		
		return simpleDateFormat.format(date);
	}
	public static String simpleFormats(Date date){
		if(date == null)
			return null;
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd");
		
		return simpleDateFormat.format(date);
	}
	
	public static String millisDateFormat(Date date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sdf = null;
		if(date != null)
		sdf = simpleDateFormat.format(date);
		return sdf;
	}
	
	public static Date dateFormat(String date){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sdf = null;
		if(date != null)
			try {
				sdf = simpleDateFormat.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return sdf;
	}
}
