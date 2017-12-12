package com.zhongyang.java.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.CorporationUserBiz;
import com.zhongyang.java.pojo.CorporationUser;
import com.zhongyang.java.service.CorporationUserService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.CorporationUserVo;
@Service
public class CorporationUserBizImpl implements CorporationUserBiz{
	
	@Autowired
	private CorporationUserService corporationUserService;
	
	@Override
	public Message addCorporationUser(CorporationUser corporationUser){
		
		return null;
	}

	@Override
	public List<CorporationUserVo> queryAllCorporationUser(CorporationUser corporationUser){
		
		List<CorporationUserVo> corporationUserVo=new ArrayList<CorporationUserVo>();
		try {
			List<CorporationUser> CorporationUsers = corporationUserService.queryAllCorporationUser(corporationUser);
			for (CorporationUser cu : CorporationUsers) {
				CorporationUserVo cuv=new CorporationUserVo();
				cuv.setUserId(cu.getUserId());
				cuv.setName(cu.getName());
				cuv.setShortname(cu.getShortname());
				cuv.setType(cu.getType());
				corporationUserVo.add(cuv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return corporationUserVo;
	}

}
