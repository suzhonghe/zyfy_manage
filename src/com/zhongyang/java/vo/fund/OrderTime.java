package com.zhongyang.java.vo.fund;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月8日 下午1:44:47
* 类说明
*/
public class OrderTime {
	
//YYYYMMDD订单生成
	public static String simpleDF() {
		Date nowTime=new Date(); 
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String cOrderTime= sd.format(nowTime);
		return cOrderTime;
		
	}
	
	//YYYYMMDD订单生成
	public static String getTmeo() {
		Date nowTime=new Date(); 
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String cOrderTime= sd.format(nowTime);
		return cOrderTime;
		
	}
	
	//YYYYMMDD用户资金userFund最后一次修改
		public static Date getLastUpdateTime() {
			Date nowTime=new Date(); 
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String lastTime= sd.format(nowTime);
			java.util.Date lastDate = null;
			try {
				lastDate = sd.parse(lastTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return lastDate;
		}
		
		//YYYYMMDD订单生成
		public static String formatTmie(Date date) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String cOrderTime= sd.format(date);
			return cOrderTime;
			
		}
		//YYYYMMDD订单生成
		public static Date formatDate(Date date) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date cOrderTime= new Date(sd.format(date));
			return cOrderTime;
			
		}
		//YYYYMMDD订单生成
		public static String getwhenTime(Date date1,Date date2) {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 Date d1;
			 String cOrderTime = null;
			try {
				 d1 = sd.parse(sd.format(date1));
				 Date d2 = sd.parse(sd.format(date2));
				 long diff = d2.getTime() - d1.getTime();
				cOrderTime= Long.toString(diff);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return cOrderTime;
			
		}
		
}
