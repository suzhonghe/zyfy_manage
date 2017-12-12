package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.InviteStatisticVo;
import com.zhongyang.java.vo.MarkStatisticVo;


public interface StatisticService {
	/**
	 * 根据分公司查询获得员工邀请人数及邀请的人年化投资金额数据
	 * @return
	 */
	public List<InviteStatisticVo> getAllInviteUser(Page<InviteStatisticVo> page);
	
	/**
	 * 根据机构统计投资金额
	 * @param page
	 * @return
	 */
	public List<MarkStatisticVo> getMarkStatisticByOrg(Page<MarkStatisticVo> page);
	
	/**
	 * 统计公司员工个人投资记录信息
	 * @param page
	 * @return
	 */
	public List<MarkStatisticVo> getEmployStatistic(Map<String,Object> params);
}
