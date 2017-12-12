package com.zhongyang.java.biz;

import com.zhongyang.java.vo.fund.SettleFundRecordVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午3:58:35
* 类说明:标的交易
*/
public interface SettleOrderBiz {
    /*
     * 标的结算--扣除管理费
     */
	public String addSettleOrder(SettleFundRecordVo settleFundRecordVo);

}
