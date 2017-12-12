package com.zhongyang.java.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.StatisticBiz;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.Organization;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.OrganizationService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DateUtil;
import com.zhongyang.java.vo.InviteStatisticVo;
import com.zhongyang.java.vo.ManagerData;
import com.zhongyang.java.vo.MarkStatisticVo;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.RegisterUser;

@Controller
public class StatisticController extends BaseController {
	
	@Autowired
	private StatisticBiz statisticBiz;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;

	//查询
	@FireAuthority(authorities=Authorities.MARKSTATIC)
	@RequestMapping("/queryStatisticByOrg")
	@ResponseBody
	public Page<MarkStatisticVo> queryStatisticByOrg(Page<MarkStatisticVo> page,HttpServletRequest request){
		Employee employee = (Employee)request.getSession().getAttribute("zycfLoginEmp");
		if(employee==null){
			return null;
		}
		return statisticBiz.getMarkStatisticByOrg(page,employee);
	}
	//导出excel
	@FireAuthority(authorities=Authorities.MARKSTATIC)
	@RequestMapping(value="/uploadInviteExcel")
	public ResponseEntity<byte[]> getMarkStatisticData(HttpServletRequest request) {
		try {
			Employee employee = (Employee)request.getSession().getAttribute("zycfLoginEmp");
			if(employee==null){
				return null;
			}
			Page<MarkStatisticVo> page = new Page<MarkStatisticVo>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			Organization organization = new Organization();
			List<String> result = new ArrayList<String>();
			Map<String, Object> map = new HashMap<String, Object>();
			if (employee.getLoginName() != null && "wangjx".equals(employee.getLoginName())) {
				if (request.getParameter("organizationId") != null
						&& !"".equals(request.getParameter("organizationId").toString())) {
					organization.setId(request.getParameter("organizationId").toString());
					List<String> list = getRecursion(result, organization);
					map.put("list", list);
				} else {
					organization.setLevel(0);
					List<Organization> orgList = organizationService.queryAllOrganizations(organization);
					List<String> list = getRecursion(result, orgList.get(0));
					map.put("list", list);
				}
			} else {
				if (employee.getOrgId() != null && !"".equals(employee.getOrgId())) {
					organization.setId(employee.getOrgId());
					List<String> list1 = getRecursion(result, organization);
					if (request.getParameter("organizationId") != null
							&& !"".equals(request.getParameter("organizationId").toString()) && list1.contains(request.getParameter("organizationId"))) {
						organization.setId(request.getParameter("organizationId").toString());
						List<String> results = new ArrayList<String>();
						List<String> list = getRecursion(results, organization);
						map.put("list", list);
					}else{
						map.put("list", list1);
					}
				} else {
					organization.setLevel(0);
					List<Organization> orgList = organizationService.queryAllOrganizations(organization);
					List<String> list = getRecursion(result, orgList.get(0));
					if (request.getParameter("organizationId") != null
							&& !"".equals(request.getParameter("organizationId").toString()) && list.contains(request.getParameter("organizationId"))) {
						organization.setId(request.getParameter("organizationId").toString());
						organization.setLevel(null);
						List<String> results = new ArrayList<String>();
						List<String> list1 = getRecursion(results, organization);
						map.put("list", list1);
					}else{
						map.put("list", list);
					}
					
				}
			}
			if (request.getParameter("startTime") != null && !"".equals(request.getParameter("startTime"))) {
				map.put("startTime", request.getParameter("startTime"));
			} else {
				map.put("startTime", DateUtil.getFirstDayOfMonth());
			}
			if (request.getParameter("endTime") != null && !"".equals(request.getParameter("endTime"))) {
				map.put("endTime", request.getParameter("endTime"));
			} else {
				map.put("endTime", DateUtil.getLastDayOfMonth());
			}
			map.put("status",request.getParameter("status"));
			page.setParams(map);
			return statisticBiz.exportMarkStatisticData(request, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	List<String> getRecursion(List<String> result, Organization organization) {
		try {
			organization = organizationService.queryOrganizationByParams(organization);
			result.add(organization.getId());
			if (organization.getChildren().size() != 0) {
				for (Organization org : organization.getChildren()) {
					getRecursion(result, org);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//邀请明细
	@FireAuthority(authorities=Authorities.MARKSTATIC)
	@RequestMapping("/queryUserStatisticDetail")
	@ResponseBody
	public Page<InviteStatisticVo> queryUserStatisticDetail(Page<InviteStatisticVo> page){
		
		return statisticBiz.getAllInviteUser(page);
		
	}
	
	//邀请明细导出
	@FireAuthority(authorities=Authorities.MARKSTATIC)
	@RequestMapping(value="/uploadUserInviteDetail")
	public ResponseEntity<byte[]> getUserInviteDetail(HttpServletRequest request) {
		try {
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Page<InviteStatisticVo> page = new Page<InviteStatisticVo>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			Organization organization = new Organization();
			List<String> result = new ArrayList<String>();
			Map<String, Object> map = new HashMap<String, Object>();
			if (request.getParameter("mobile") != null && !"".equals(request.getParameter("mobile"))) {
				String mobile = cipher.encrypt(request.getParameter("mobile").toString());
				User user = userService.getUserByMobile(mobile);
				if (request.getParameter("type") != null && "INVITE".equals(request.getParameter("type"))) {
					map.put("referralId", user.getId());
				} else {
					map.put("id", user.getId());
				}
			}
			if (request.getParameter("startTime") != null && !"".equals(request.getParameter("startTime"))) {
				map.put("startTime", request.getParameter("startTime"));
			} else {
				map.put("startTime", DateUtil.getFirstDayOfMonth());
			}
			if (request.getParameter("endTime") != null && !"".equals(request.getParameter("endTime"))) {
				map.put("endTime", request.getParameter("endTime"));
			} else {
				map.put("endTime", DateUtil.getLastDayOfMonth());
			}
			page.setParams(map);
			return statisticBiz.exportInviteUserData(request, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/queryRegisterUser")
	@FireAuthority(authorities=Authorities.USERSTATIC)
	public @ResponseBody RegisterUser queryRegisterUser(ParamsPojo params){
		return statisticBiz.queryRegisterUser(params);
	}
	//综合统计数据导出
	@RequestMapping(value = "/registerUserExprot")
	public ResponseEntity<byte[]>  queryRegisterUserDate(HttpServletRequest request,ParamsPojo params){
		return statisticBiz.queryRegisterUserDate(request,params);
	}
	
	@RequestMapping(value = "/queryManagerData")
	public @ResponseBody ManagerData queryManagerData(String date){
		return statisticBiz.queryManagerData(date);
	}
}
