package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.AwardManagerBiz;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.vo.InvestRecordVo;
import com.zhongyang.java.vo.PagerVO;

@Controller
public class AwardManagerController extends BaseController{
	
	@Autowired
	private AwardManagerBiz awardManagerBiz;
	
	@RequestMapping(value="/queryInvestRecordVoByParams")
	public @ResponseBody InvestRecordVo queryInvestRecordVoByParams(PagerVO<User>pager){
		return awardManagerBiz.queryByParams(pager);
	}
}
