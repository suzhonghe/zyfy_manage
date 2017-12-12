package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.SettleFileBiz;
import com.zhongyang.java.biz.SettleRecordBiz;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpSettleWithdrawRecord;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.FundRecordSettle;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.RechargeRecordVO;
import com.zhongyang.java.vo.TenderRecordVO;
import com.zhongyang.java.vo.TransferRecordVO;
import com.zhongyang.java.vo.WithdrawRecordVO;

/**
 * 
* @Title: SettleFileController.java 
* @Package com.zhongyang.java.controller 
* @Description:对账控制器 
* @author 苏忠贺   
* @date 2016年1月7日 下午1:28:25 
* @version V1.0
 */
@Controller
public class SettleFileController extends BaseController{
	
	@Autowired
	private SettleFileBiz settleFileBiz;
	
	@Autowired
	private SettleRecordBiz settleRecordBiz;
	
	@FireAuthority(authorities=Authorities.DOWNLOADCHECKFILE)
	@RequestMapping(value="/downloadSettleFile")
	public @ResponseBody Message settleFile(String number,String startDate,String endDate){
		Message value=null;
		if("01".equals(number)){
			value= settleFileBiz.settleRecharge(number,startDate,endDate);
		}
		if("02".equals(number)){
			value=settleFileBiz.settleWithdraw(number,startDate,endDate);
		}
		if("04".equals(number)){
			value=settleFileBiz.settleTender(number,startDate,endDate);
		}
		if("05".equals(number)){
			value=settleFileBiz.settleTransfer(number,startDate,endDate);
		}
		return value;
	}
	/**
	 * 
	* @Title: queryRechargeRecord 
	* @Description:充值对账记录查询 
	* @return PagerVO<RechargeRecordVO>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.CHECKRECHARGE)
	@RequestMapping(value="/queryRechargeRecord")
	public @ResponseBody PagerVO<RechargeRecordVO> queryRechargeRecord(PagerVO<UmpSettleRechargeRecord>pager){
		return settleRecordBiz.queryRechargeRecord(pager);
	}
	/**
	 * 
	* @Title: queryWithdrawRecord 
	* @Description:提现对账记录查询
	* @return PagerVO<RechargeRecordVO>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.CHECKWITHDRAWL)
	@RequestMapping(value="/queryWithdrawRecord")
	public @ResponseBody PagerVO<WithdrawRecordVO> queryWithdrawRecord(PagerVO<UmpSettleWithdrawRecord>pager){
		return settleRecordBiz.queryWithdrawRecord(pager);
	}
	/**
	 * 
	* @Title: queryTransferRecord 
	* @Description:转账对账记录查询
	* @return PagerVO<TransferRecordVO>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.CHECKTRANSFER)
	@RequestMapping(value="/queryTransferRecord")
	public @ResponseBody PagerVO<TransferRecordVO> queryTransferRecord(PagerVO<UmpSettleTransferRecord>pager){
		return settleRecordBiz.queryTransferRecord(pager);
	}
	/**
	 * 
	* @Title: queryTenderRecord 
	* @Description:标的转账记录查询 
	* @return PagerVO<TenderRecordVO>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.CHECKBID)
	@RequestMapping(value="/queryTenderRecord")
	public @ResponseBody PagerVO<TenderRecordVO> queryTenderRecord(PagerVO<UmpSettleTenderRecord>pager){
		return settleRecordBiz.queryTenderRecord(pager);
	}
	
	@FireAuthority(authorities=Authorities.CHECKPLATFORMRECORD)
	@RequestMapping(value="/queryPlatFormRecord")
	public @ResponseBody Page<FundRecordSettle> queryPlatFormRecord(Page<FundRecord>page){
		return settleRecordBiz.queryPlatFormRecord(page);
	}
}
