package com.zhongyang.java.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import com.zhongyang.java.system.Exception.UException;

/**
 * 
 * XML解析
 * 
 * @author Matthew
 *
 */
public class DomParseService {

	public static Map xmlTOmap(String xmlString){
		InputStream in;
		try {
			in = new FileInputStream(new File(xmlString));
		
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		Element rootElement = document.getRootElement();
		Map<String, Object> map = new HashMap<String, Object>();
		map=eleTOmap(map, rootElement);
		return (Map) map.get("SYSTEM");
		} catch (FileNotFoundException | DocumentException e) {
			throw new UException(e);
		}
	}

	
	public static Map eleTOmap(Map map, Element ele) {
		List<Element> elements = ele.elements();
		if (elements.size() == 0) {
			map.put(ele.getName(),ele.getText());
		}
		else {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			for (Element element : elements) {
				tempMap=eleTOmap(tempMap,element);
			}
			map.put(ele.getName(), tempMap);
		}
		return map;
	}
	

}
