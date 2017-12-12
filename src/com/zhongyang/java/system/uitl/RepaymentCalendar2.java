package com.zhongyang.java.system.uitl;

import java.util.Calendar;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月11日 上午11:10:53
* 类说明
*/
public class RepaymentCalendar2 {
	//int j 集合数量
	//int newDate 当前日期
	public static String[] getNextmonths(int j,int newDate) {
		String[] last12Months = new String[j];
        Calendar cal = Calendar.getInstance();
        for(int i=0; i<j; i++){
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //逐次往后推1个月 
            if(i < last12Months.length){
            	 switch (newDate) {
            	 case 29://就结算当天是29好的的算法：只要天数等于或者大于29的都取当天，否则取某月最大的
					 int bb29 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月最大天数
           		     if(bb29 <= 29){
                 	 String lastMonth31=String.valueOf(cal.get(Calendar.MONTH)+1).length()==2?String.valueOf(cal.get(Calendar.MONTH)+1):"0"+String.valueOf(cal.get(Calendar.MONTH)+1);
           			 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (lastMonth31+"-"+cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                	     System.out.println(last12Months[i]+"当结算日期是29号的时候，要检查下月是否有29号");
                	 }else if(bb29 == 30){
                 		 String lastMonth31=String.valueOf(cal.get(Calendar.MONTH)+1).length()==2?String.valueOf(cal.get(Calendar.MONTH)+1):"0"+String.valueOf(cal.get(Calendar.MONTH)+1);
                		 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (lastMonth31+"-"+cal.get(Calendar.DATE));
                		 System.out.println(last12Months[i]+"当结算日期是29号的时候，要检查下月是否有29号,等30就取当前日期");
                	 }else if(bb29 > 30){
                		String lastMonth31=String.valueOf(cal.get(Calendar.MONTH)+1).length()==2?String.valueOf(cal.get(Calendar.MONTH)+1):"0"+String.valueOf(cal.get(Calendar.MONTH)+1);
                		last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (lastMonth31+"-"+cal.get(Calendar.DATE));
                       		 System.out.println(last12Months[i]+"当结算日期是29号的时候，要检查下月是否有29号,大约30就取当前日期");
                	 }
					break;
				 case 30://就结算当天是30好的的算法:只要天数等于或者大约30的都取当天，否则取某月最大的
					 int bb30 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月最大天数
           		     if(bb30 < 30){
            		 String lastMonth31=String.valueOf(cal.get(Calendar.MONTH)+1).length()==2?String.valueOf(cal.get(Calendar.MONTH)+1):"0"+String.valueOf(cal.get(Calendar.MONTH)+1);
           			 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (lastMonth31+"-"+cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                	     System.out.println(last12Months[i]+"如果cc等于30，说名结算当天是30号而这某月只有29天，只能取最大的一天为29");
                	 }else if(bb30 == 30){
           		    	String lastMonth31=String.valueOf(cal.get(Calendar.MONTH)+1).length()==2?String.valueOf(cal.get(Calendar.MONTH)+1):"0"+String.valueOf(cal.get(Calendar.MONTH)+1);
                		 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (lastMonth31+"-"+cal.get(Calendar.DATE));
                		 System.out.println(last12Months[i]+"如果等于30说明这个月也是30天，所以取30号当天");
                	 }else if(bb30 > 30){
          		    	String lastMonth31=String.valueOf(cal.get(Calendar.MONTH)+1).length()==2?String.valueOf(cal.get(Calendar.MONTH)+1):"0"+String.valueOf(cal.get(Calendar.MONTH)+1);
                		last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (lastMonth31+"-"+cal.get(Calendar.DATE));
                       		 System.out.println(last12Months[i]+"如果当月等于31说明这个月是31天，所以需要从大队");
                	 }
					break;
				 case 31://就结算当天是31好的的算法:如果结算当天是31号，那么就取每月最后一天
      		    	 String lastMonth31=String.valueOf(cal.get(Calendar.MONTH)+1).length()==2?String.valueOf(cal.get(Calendar.MONTH)+1):"0"+String.valueOf(cal.get(Calendar.MONTH)+1);
           			 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (lastMonth31+"-"+cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                	     System.out.println(last12Months[i]+"如果cc等于31，说名结算当天是31号，所以只能取每月最后一天");
                	     
					break;
				default:
      		    	 String lastMonth=String.valueOf(cal.get(Calendar.MONTH)+1).length()==2?String.valueOf(cal.get(Calendar.MONTH)+1):"0"+String.valueOf(cal.get(Calendar.MONTH)+1);
           		     String lastday=String.valueOf(cal.get(Calendar.DATE)).length()==2?String.valueOf(cal.get(Calendar.DATE)):"0"+String.valueOf(cal.get(Calendar.DATE));
					 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (lastMonth+"-"+lastday);
		                	     System.out.println(last12Months[i]+"说明当前结算日期不是29，30，31号");
					break;
				}
            }
        }    
        return last12Months;  
	}
	    //int j 集合数量
		//int newDate 当前日期
		public static String getBulletmonths(int j,int newDate) {
			String[] last12Months = new String[j];
	        Calendar cal = Calendar.getInstance();
	        for(int i=0; i<j; i++){
	            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //逐次往后推1个月 
	            if(i < last12Months.length){
	            	 switch (newDate) {
	            	 case 29://就结算当天是29好的的算法：只要天数等于或者大于29的都取当天，否则取某月最大的
						 int bb29 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月最大天数
	           		     if(bb29 <= 29){
	           			 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1+"-"
	           	         +cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	                	     System.out.println(last12Months[i]+"当结算日期是29号的时候，要检查下月是否有29号");
	                	 }else if(bb29 == 30){
	                		 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1+"-"
	                		 +cal.get(Calendar.DATE));
	                		 System.out.println(last12Months[i]+"当结算日期是29号的时候，要检查下月是否有29号,等30就取当前日期");
	                	 }else if(bb29 > 30){
	                		last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1+"-"
	                				+cal.get(Calendar.DATE));
	                       		 System.out.println(last12Months[i]+"当结算日期是29号的时候，要检查下月是否有29号,大约30就取当前日期");
	                	 }
						break;
					 case 30://就结算当天是30好的的算法:只要天数等于或者大约30的都取当天，否则取某月最大的
						 int bb30 = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月最大天数
	           		     if(bb30 < 30){
	           			 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1+"-"
	           	         +cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	                	     System.out.println(last12Months[i]+"如果cc等于30，说名结算当天是30号而这某月只有29天，只能取最大的一天为29");
	                	 }else if(bb30 == 30){
	                		 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1+"-"
	                		 +cal.get(Calendar.DATE));
	                		 System.out.println(last12Months[i]+"如果等于30说明这个月也是30天，所以取30号当天");
	                	 }else if(bb30 > 30){
	                		last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1+"-"
	                				+cal.get(Calendar.DATE));
	                       		 System.out.println(last12Months[i]+"如果当月等于31说明这个月是31天，所以需要从大队");
	                	 }
						break;
					 case 31://就结算当天是31好的的算法:如果结算当天是31号，那么就取每月最后一天
	           			 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1+"-"
	           	         +cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	                	     System.out.println(last12Months[i]+"如果cc等于31，说名结算当天是31号，所以只能取每月最后一天");
	                	     
						break;
					default:
						 last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1+"-"
			           	         +cal.get(Calendar.DATE));
			                	     System.out.println(last12Months[i]+"说明当前结算日期不是29，30，31号");
						break;
					}
	            }
	        }    
	        String lastMonths = last12Months[last12Months.length-1];
	        return lastMonths;  
		}
}
