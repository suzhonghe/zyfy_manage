package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.CorporationUserBiz;
import com.zhongyang.java.pojo.CorporationUser;
import com.zhongyang.java.vo.CorporationUserVo;

@Controller
public class CorporationUserController extends BaseController{
	
	@Autowired
	private CorporationUserBiz corporationUserBiz;
	
	@RequestMapping(value="/queryAllCorporationUserByCondition")
	public @ResponseBody List<CorporationUserVo> queryAllCorporationUserByCondition(CorporationUser corporationUser){
		return corporationUserBiz.queryAllCorporationUser(corporationUser);
	}
}
