package com.zhongyang.java.system.uitl;

public class statusUtil {

    
    
	public static String getStr(String key){
    	if(key==null || "".equals(key)){
    		return "";	
    	}
    	if(key.equals("INITIALIZED")){
    		return "初始化";
    	}
    	if(key.equals("AUDITING")){
    		return "待结算";
    	}
    	if(key.equals("FAILED")){
    		return "失败";
    	}
    	if(key.equals("SETTLED")){
    		return "已结算";
    	}
    	if(key.equals("CLEARED")){
    		return "已还清";
    	}
    	return "";
    }

}
