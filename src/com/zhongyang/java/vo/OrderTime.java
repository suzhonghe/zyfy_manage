package com.zhongyang.java.vo;

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
	public static String getTmeo() {
		Date nowTime=new Date(); 
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
		String cOrderTime= sd.format(nowTime);
		return cOrderTime;
		
	}
	
	//YYYYMMDD用户资金userFund最后一次修改
		public static Date getLastUpdateTime() {
			Date nowTime=new Date(); 
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			String lastTime= sd.format(nowTime);
			java.util.Date lastDate = null;
			try {
				lastDate = sd.parse(lastTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lastDate;
			
		}
}
