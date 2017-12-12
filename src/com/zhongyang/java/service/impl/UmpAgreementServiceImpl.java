package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UmpAgreementDao;
import com.zhongyang.java.pojo.UmpAgreement;
import com.zhongyang.java.service.UmpAgreementService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月3日 下午3:47:12
* 类说明：用户协议信息
*/
@Service
public class UmpAgreementServiceImpl implements UmpAgreementService {
    @Autowired
    UmpAgreementDao umpAgreementDao;
	@Override
	public UmpAgreement byUserUmpAgreement(UmpAgreement umpAgreement) {
		
		return umpAgreementDao.byUserUmpAgreement(umpAgreement);
	}
}
