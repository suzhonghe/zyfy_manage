package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UntredtedApplicationDao;
import com.zhongyang.java.pojo.UntredtedApplication;
import com.zhongyang.java.service.UntredtedApplicationService;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月21日 上午11:11:42
* 类说明:未处理的转账申请
*/
@Service
public class UntredtedApplicationServiceImpl implements UntredtedApplicationService{
	
    @Autowired
    UntredtedApplicationDao untredtedApplicationDao;
    
	public int addUntredtedApplication(UntredtedApplication untredtedApplication) throws Exception{
		return untredtedApplicationDao.addUntredtedApplication(untredtedApplication);
	}

	
	public List<UntredtedApplication> getUntredtedApplicationByStutas(UntredtedApplication untredtedApplication) throws Exception {
		
		return untredtedApplicationDao.getUntredtedApplicationByStutas(untredtedApplication);
	}

	public UntredtedApplication getUntredtedApplicationById(UntredtedApplication untredtedApplication)
			throws Exception {
		return untredtedApplicationDao.getUntredtedApplicationById(untredtedApplication);
	}


	@Override
	public int deleteUntredtedApplicationByOrderId(UntredtedApplication untredtedApplication) throws Exception {
		 return untredtedApplicationDao.deleteUntredtedApplicationByOrderId(untredtedApplication);
	}


	@Override
	public int deleteApplicationId(String id) throws Exception {
		return untredtedApplicationDao.deleteApplicationId(id);
	}

}
