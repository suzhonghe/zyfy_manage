package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.ClientFundRecord;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ClientFundRecordVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月21日 下午4:12:36
* 类说明
*/
public interface ClientFundRecordDao {
    /*
     * 平台资金记录
     */
	public int addClientFundRecord(ClientFundRecord clientFundRecord) throws Exception;
    /*
     * 平台资金记录根据orderID更新
     */
	public int updateClentFundByOrderId(ClientFundRecord clientFundRecord) throws Exception;

	 /*
     * 平台资金记录根据orderID查询
     */
	public ClientFundRecord getClientFundRecordByOrderId(ClientFundRecord clientFundRecord) throws Exception;
	 /*
     * 平台资金记录根据状态查询
     */
	public List<ClientFundRecord> getClientFundRecordByStatus(ClientFundRecord clientFundRecord) throws Exception;
	
	public ClientFundRecord getClientFundRecordByStatusAmountSum(ClientFundRecord clientFundRecord) throws Exception;

    /*
     * 平台资金记录根据orderID查询
     */
	public ClientFundRecord getClentFundByOrderId(ClientFundRecord clientFundRecord) throws Exception;
	 /*
      * 平台资金记录查询数
      */
	public int getClientFundRecordCount(Map<String, Object> params) throws Exception;
	 /*
      * 平台资金记录查询
      */
	public List<ClientFundRecord> getClientFundRecord(Page<ClientFundRecord> page) throws Exception;
	 /*
      * 平台资金记录查询导出excle
      */
	public List<ClientFundRecordVo> ClientFundRecordOutExcle(Page<ClientFundRecord> page) throws Exception;
}
