package com.zhongyang.java.system.uitl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月28日 上午9:38:54
* 类说明:还款日期计算
*/
public class RepaymentTime {
	//int j 集合数量
		//int newDate 当前日期
        public static void main(String args[]) {
        	int j=12;
        	j=j+1;
        	getNextmonths(j);
		}
		public static String[] getNextmonths(int j) {
			String[] last12Months = new String[j];
	        Calendar cal = Calendar.getInstance();
	        Date dNow = new Date(); //当前时间
			Date dBefore = new Date();
			Calendar calendar = Calendar.getInstance(); //得到日历
//			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)+1); //逐次往后推1个月 
			for(int i = 1;i<j;i++){
				calendar.setTime(dNow);//把当前时间赋给日历
				calendar.add(calendar.MONTH, i); //设置为前3月
				dBefore = calendar.getTime(); //得到前3月的时间
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
				last12Months[i] = sdf.format(dBefore); //格式化前3月的时间
		        System.out.println("第"+i+"笔:" + last12Months[i]);
			}
	       
	        return last12Months;  
		}
		
}
