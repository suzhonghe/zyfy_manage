package com.zhongyang.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongyang.java.pojo.UntredtedApplication;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月21日 上午11:11:12
* 类说明：未处理的转账申请
*/
@Service
public interface UntredtedApplicationService {
    /**
     * 
     * @param clientFundRecord
     * @return
     * @throws Exception
     * 添加未处理的转账申请
     */
	public int addUntredtedApplication(UntredtedApplication untredtedApplication) throws Exception;
    /**
     * 查询未处理的转账申请
     * @return 
     */
	public List<UntredtedApplication> getUntredtedApplicationByStutas(UntredtedApplication untredtedApplication) throws Exception;
	/**
     * 查询未处理的转账申请byId
     * @return 
     */
	public UntredtedApplication getUntredtedApplicationById(UntredtedApplication untredtedApplication) throws Exception;
	/**
     * 删除已经批准的申请记录
     * @return 
     */
	public int deleteUntredtedApplicationByOrderId(UntredtedApplication untredtedApplication) throws Exception;
	/**
     * 删除已经批准的申请记录更具id
     * @return 
	 * @throws Exception 
     */
	public int deleteApplicationId(String id) throws Exception;

}
