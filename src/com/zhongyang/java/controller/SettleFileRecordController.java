package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.SettleFileRecordBiz;
import com.zhongyang.java.pojo.SettleFileRecord;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.SettleFileRecordVO;

/**
 * 
* @Title: SettleFileRecordController.java 
* @Package com.zhongyang.java.controller 
* @Description:对账记录控制器
* @author 苏忠贺   
* @date 2016年3月4日 上午9:27:39 
* @version V1.0
 */
@Controller
public class SettleFileRecordController extends BaseController{
	
	@Autowired
	private SettleFileRecordBiz settleFileRecordBiz;
	
	@FireAuthority(authorities=Authorities.CHECKFILEMANAGER)
	@RequestMapping(value="/querySettleFileRecords")
	public @ResponseBody PagerVO<SettleFileRecordVO> queryByParams(PagerVO<SettleFileRecord> pager){
		return settleFileRecordBiz.queryByParams(pager);
	}
}
