
package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.VirtualRecordBiz;
import com.zhongyang.java.pojo.VirtualRecord;
import com.zhongyang.java.system.page.Page;

@Controller
public class VirtualRecordController extends BaseController {
	
	@Autowired
	private VirtualRecordBiz virtualRecordBiz;
	
	@RequestMapping(value="/queryVirtualRecordByParams")
	public @ResponseBody Page<VirtualRecord> queryVirtualRecordByParams(Page<VirtualRecord> page){
		return virtualRecordBiz.queryVirtualRecordByParams(page);
	}
	
}
