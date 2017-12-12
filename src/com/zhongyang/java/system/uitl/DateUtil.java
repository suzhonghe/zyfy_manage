package com.zhongyang.java.system.uitl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 得到几天前的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	public static String simpleFormat(Date date) {
		if (date == null)
			return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
		return simpleDateFormat.format(date);
	}

	public static String millisDateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date != null)
			return simpleDateFormat.format(date);
		return null;
	}
	
	/**
	 * 获得当月的第一天
	 * @return
	 */
	public static String getFirstDayOfMonth(){
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	     Calendar c = Calendar.getInstance();    
	     c.add(Calendar.MONTH, 0);
	     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	     return  format.format(c.getTime());
	}
	
	/**
	 * 获得当前月的最后一天
	 * @return
	 */
	public static String getLastDayOfMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		return last;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(getLastDayOfMonth());
	}

}
