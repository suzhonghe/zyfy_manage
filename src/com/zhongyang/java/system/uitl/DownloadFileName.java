package com.zhongyang.java.system.uitl;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * 
* @Title: DownloadFileName.java 
* @Package com.zhongyang.java.system.uitl 
* @Description:解决导出文件名字乱码问题
* @author 苏忠贺   
* @date 2016年1月26日 下午2:30:14 
* @version V1.0
 */
public class DownloadFileName {
	/**
	 * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
	 * 
	 * @throws UnsupportedEncodingException
	 */
	 public static String encodeChineseDownloadFileName(HttpServletRequest request, String fileNames) {  
	    String codedfilename = null;  
	    try {  
	        String agent = request.getHeader("USER-AGENT");  
	        if (null != agent && -1 != agent.indexOf("MSIE") || null != agent  
	                && -1 != agent.indexOf("Trident")) {// ie  
	
	            String name = java.net.URLEncoder.encode(fileNames, "UTF8");  
	
	            codedfilename = name;  
	        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等  
	
	
	            codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");  
	        }  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return codedfilename;  
	}
}
