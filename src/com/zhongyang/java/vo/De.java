package com.zhongyang.java.vo;

import java.util.HashMap;
import java.util.Map;

import com.zhongyang.java.system.ShortMessage;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年3月31日 上午11:26:18
* 类说明
*/
public class De {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map map = new HashMap<String, String>();
		map.put("info", "你好项目");
        map.put("mobiles", "15313928125");
//        ShortMessage.getShortMessage().getSendMSgTuUser();
		ShortMessage.getShortMessage().getSendToUserMsg(map);
	}

}
