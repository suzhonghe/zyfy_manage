package com.zhongyang.java.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongyang.java.pojo.UntredtedApplication;

/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月14日 下午5:00:43
* 类说明
*/
@Service
public interface UntredtedApplicationDao {

	public int addUntredtedApplication(UntredtedApplication untredtedApplication) throws Exception;

	public List<UntredtedApplication> getUntredtedApplicationByStutas(UntredtedApplication untredtedApplication) throws Exception;

	public UntredtedApplication getUntredtedApplicationById(UntredtedApplication untredtedApplication) throws Exception;

	public int deleteUntredtedApplicationByOrderId(UntredtedApplication untredtedApplication);

	public int deleteApplicationId(String id)throws Exception;
}
