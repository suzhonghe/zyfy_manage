package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.vo.FundRecordVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.UserFundVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午5:22:40
* 类说明：用户资金
*/
public interface UserFundBiz {
    
	//通过用户Id查询用户资金
	public UserFundVo byUserId(UserFundVo userFundVo);
	
	public PagerVO<FundRecordVo> getFundRecordDetaileList(PagerVO<FundRecord> pager);

	public UserFundVo getUserFundAccount(String userId);
}
