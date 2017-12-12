package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpAgreement;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ClientFundRecordVo;
import com.zhongyang.java.vo.fund.BusinessRechargeVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月21日 上午11:11:12
* 类说明：标的交易记录
*/
public interface ClientFundRecordService {
    /**
     * 
     * @param clientFundRecord
     * @return
     * @throws Exception
     * 标的平台管理费收取记录
     */
	public int  addClientFundRecord(ClientFundRecord clientFundRecord) throws Exception;
    /**
     * 
     * @param settleFundRecordVo
     * 更新平台管理费状态
     * @return 
     */
	public int updateClentFundByOrderId(ClientFundRecord clientFundRecord) throws Exception;
	
	 /**
     * 
     * @param settleFundRecordVo
     * 查询平台自己记录
     * @return 
     */
	public ClientFundRecord getClientFundRecordByOrderId(ClientFundRecord clientFundRecord) throws Exception;
	
	 /**
     * 
     * @param settleFundRecordVo
     * 查询平台自己记录状态为：审核中(AUDITING)
     * @return 
     */
	public List<ClientFundRecord> getClientFundRecordByStatus(ClientFundRecord clientFundRecord) throws Exception;

	 /**
    * 
    * @param settleFundRecordVo
    * 查询平台自己记录状态为：审核中(AUDITING)资金和
    * @return 
	 * @throws Exception 
    */
	public ClientFundRecord getClientFundRecordByStatusAmountSum(ClientFundRecord clientFundRecord) throws Exception;
	
	/**
     * 
     * @param settleFundRecordVo
     * 查询平台资金记录
     * @return 
     */
	public ClientFundRecord getClentFundByOrderId(ClientFundRecord clientFundRecord) throws Exception;
	
	/**
     * 
     * @param settleFundRecordVo
     * 查询平台资金记录
     * @return 
     */
	public List<ClientFundRecord> getClientFundRecord(Page<ClientFundRecord> page) throws Exception;
	
	/**
     * 
     * @param settleFundRecordVo
     * 查询平台资金记录数
     * @return 
     */
	public int getClientFundRecordCount(Map<String, Object> params) throws Exception;
	/**
	 * 
	 * @param page
	 * 查询平台资金记录数导出excle
	 * @return
	 */
	public List<ClientFundRecordVo> ClientFundRecordOutExcle(Page<ClientFundRecord> page)throws Exception;

	
}
