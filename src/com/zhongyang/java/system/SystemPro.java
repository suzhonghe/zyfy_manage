package com.zhongyang.java.system;

import java.util.Map;

/**
 * 系统属性
 * @author Matthew
 *
 */

public class SystemPro {
	
	private static Map<String,Object> properties;
	
	static{	
		String url=SystemPro.class.getResource("/").getPath();
		properties=DomParseService.xmlTOmap(url+"systemPro.xml");
	}

	public static Map<String,Object> getProperties() {
		return properties;
	}
	
}
