package com.zhongyang.java.biz;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.InviteStatisticVo;
import com.zhongyang.java.vo.ManagerData;
import com.zhongyang.java.vo.MarkStatisticVo;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.RegisterUser;

public interface StatisticBiz {

	/**
	 * 根据分公司查询获得员工邀请人数及邀请的人年化投资金额数据
	 * @return
	 */
	public Page<InviteStatisticVo> getAllInviteUser(Page<InviteStatisticVo> page);
	/**
	 * 公司员工邀请数据导出
	 * @param request
	 * @param page
	 * @return
	 */
	public ResponseEntity<byte[]> exportInviteUserData(HttpServletRequest request,Page<InviteStatisticVo> page);
	
	/**
	 * 根据机构统计投资金额
	 * @param page
	 * @return
	 */
	public Page<MarkStatisticVo> getMarkStatisticByOrg(Page<MarkStatisticVo> page,Employee employee);
	
	/**
	 * 公司员工邀请数据导出
	 * @param request
	 * @param page
	 * @return
	 */
	public ResponseEntity<byte[]> exportMarkStatisticData(HttpServletRequest request,Page<MarkStatisticVo> page);
	
	/**
	 * 统计公司员工个人投资记录信息
	 * @param page
	 * @return
	 */
	public List<MarkStatisticVo> getEmployStatistic(Page<MarkStatisticVo> page);
	
	/**
	 * 员工个人投资信息统计
	 * @param request
	 * @param page
	 * @return
	 */
	public ResponseEntity<byte[]> exportEmployStatisticData(HttpServletRequest request,Page<MarkStatisticVo> page);

	public RegisterUser queryRegisterUser(ParamsPojo params);
	
	public ResponseEntity<byte[]> queryRegisterUserDate(HttpServletRequest request, ParamsPojo params);

	public ManagerData queryManagerData(String date);
}
