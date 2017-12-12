package com.zhongyang.java.system;

import java.util.HashMap;
import java.util.Map;

public class ZSession {
	
	private static ZSession zSession;
	private static Map<String,Object> zSessionMap;
	
	public static ZSession getzSession() {
		if(zSession==null){
			zSession=new ZSession();
            zSessionMap=new HashMap<String,Object>();
		}
		return zSession;
	}

	public Map<String,Object> getzSessionMap() {
		return zSessionMap;
	}
	public void setzSessionMap(Map<String,Object> zSessionMap) {
		this.zSessionMap = zSessionMap;
	}
	public static void setzSession(ZSession zSession) {
		ZSession.zSession = zSession;
	}

	private ZSession() {}
}
