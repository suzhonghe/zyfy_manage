package com.zhongyang.java.biz;

import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpSettleWithdrawRecord;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.FundRecordSettle;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.RechargeRecordVO;
import com.zhongyang.java.vo.TenderRecordVO;
import com.zhongyang.java.vo.TransferRecordVO;
import com.zhongyang.java.vo.WithdrawRecordVO;

/**
 * 
* @Title: SettleRecordBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:对账记录业务处理接口 
* @author 苏忠贺   
* @date 2016年1月13日 下午3:38:39 
* @version V1.0
 */
public interface SettleRecordBiz {
	/**
	* @Title: queryRechargeRecord 
	* @Description:充值对账记录查询 
	* @return PagerVO<UmpSettleRechargeRecord>    返回类型 
	* @throws
	 */
	public PagerVO<RechargeRecordVO> queryRechargeRecord(PagerVO<UmpSettleRechargeRecord>pager);
	/**
	 * 
	* @Title: queryWithdrawRecord 
	* @Description:提现对账记录查询
	* @return PagerVO<RechargeRecordVO>    返回类型 
	* @throws
	 */
	public PagerVO<WithdrawRecordVO> queryWithdrawRecord(PagerVO<UmpSettleWithdrawRecord>pager);
	/**
	 * 
	* @Title: queryTransferRecord 
	* @Description:转账对账记录查询
	* @return PagerVO<TransferRecordVO>    返回类型 
	* @throws
	 */
	public PagerVO<TransferRecordVO> queryTransferRecord(PagerVO<UmpSettleTransferRecord>pager);
	/**
	 * 
	* @Title: queryTenderRecord 
	* @Description:标的转账记录查询 
	* @return PagerVO<TenderRecordVO>    返回类型 
	* @throws
	 */
	public PagerVO<TenderRecordVO> queryTenderRecord(PagerVO<UmpSettleTenderRecord>pager);
	
	public Page<FundRecordSettle> queryPlatFormRecord(Page<FundRecord>page);
}
