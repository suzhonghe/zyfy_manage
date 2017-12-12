package com.zhongyang.java.biz.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.StatisticBiz;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.Organization;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.OrganizationService;
import com.zhongyang.java.service.StatisticService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DateUtil;
import com.zhongyang.java.system.uitl.UploadExcelUtil;
import com.zhongyang.java.vo.FreshInfo;
import com.zhongyang.java.vo.FreshInfoStatistics;
import com.zhongyang.java.vo.InvestLevel;
import com.zhongyang.java.vo.InvestLevelCount;
import com.zhongyang.java.vo.InvestLoan;
import com.zhongyang.java.vo.InviteStatisticVo;
import com.zhongyang.java.vo.ManagerData;
import com.zhongyang.java.vo.MarkStatisticVo;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.RegisterUser;
import com.zhongyang.java.vo.RegisterUserInfo;
import com.zhongyang.java.vo.UserInvest;
import com.zhongyang.java.vo.UserInvestLoan;
import com.zhongyang.java.vo.UserStatistics;

@Service
public class StatisticBizImpl implements StatisticBiz {
	Logger logger = Logger.getLogger(StatisticBizImpl.class);
	@Autowired
	private StatisticService statisticService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private LoanService loanService;

	// 查询明细
	@Override
	public Page<InviteStatisticVo> getAllInviteUser(Page<InviteStatisticVo> page) {
		try {
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Map<String, Object> map = new HashMap<String, Object>();
			if (page.getParams().get("startTime") != null && !"".equals(page.getParams().get("startTime").toString())) {
				map.put("startTime", page.getParams().get("startTime"));
			}
			if (page.getParams().get("endTime") != null && !"".equals(page.getParams().get("endTime").toString())) {
				map.put("endTime", page.getParams().get("endTime"));
			}
			if (page.getParams().get("mobile") != null && !"".equals(page.getParams().get("mobile"))) {
				String mobile = cipher.encrypt(page.getParams().get("mobile").toString());
				User user = userService.getUserByMobile(mobile);
				if (page.getParams().get("type") != null && "INVITE".equals(page.getParams().get("type"))) {
					map.put("referralId", user.getId());
				} else {
					map.put("id", user.getId());
				}
			} else {
				return null;
			}
			page.setParams(map);
			List<InviteStatisticVo> list = statisticService.getAllInviteUser(page);
			for (InviteStatisticVo vo : list) {
				String mobile = cipher.decrypt(vo.getMobile());
				String stamb = mobile.substring(0, 3) + "***" + mobile.substring(mobile.length() - 4, mobile.length());
				vo.setMobile(stamb);
			}
			page.setResults(list);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未知错误！");
		}
	}

	// 明细导出
	@Override
	public ResponseEntity<byte[]> exportInviteUserData(HttpServletRequest request, Page<InviteStatisticVo> page) {
		try {
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "邀请人姓名", "手机号", "投资金额(元)", "投资项目", "项目周期(月)", "项目利率(%)", "投资时间", "投标状态",
					"年化投资金额(元)" });
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "inviteName", "mobile", "investAmount", "title", "months", "rate",
					"submitTime", "status", "yearAmount" });
			List<InviteStatisticVo> list = statisticService.getAllInviteUser(page);
			for (InviteStatisticVo vo : list) {
				if (0 != vo.getAddRate()) {
					String rates = vo.getRate() + "+" + String.valueOf(vo.getAddRate());
					vo.setRate(rates);
				}
				String cc = cipher.decrypt(vo.getMobile());
				String stamb = cc.substring(0, 3) + "***" + cc.substring(cc.length() - 4, cc.length());
				vo.setMobile(stamb);
			}
			String name = "公司员工邀请信息明细";
			ResponseEntity<byte[]> responseEntity;
			responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, list, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "员工邀请信息导出失败!");
		}
	}

	// 查询总额
	@Override
	public Page<MarkStatisticVo> getMarkStatisticByOrg(Page<MarkStatisticVo> page, Employee employee) {
		try {
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			Organization organization = new Organization();
			List<String> result = new ArrayList<String>();
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			System.out.println(System.currentTimeMillis() + "查询开始时间");
			if (page.getParams().get("organizationId") != null) {
				String orgId = (String) page.getParams().get("organizationId");
				if (employee.getOrgId() != null && !"".equals(employee.getOrgId())) {
					Organization org = new Organization();
					org.setId(employee.getOrgId());
					List<String> orgs = new ArrayList<String>();
					List<String> res = getRecursion(orgs, org);
					boolean flag = false;
					for (String str : res) {
						if (str.equals(orgId)) {
							flag = true;
						}
					}
					if (!flag) {
						return page;
					}
				}

			}
			if (employee.getLoginName() != null && "wangjx".equals(employee.getLoginName())) {
				if (page.getParams().get("organizationId") != null
						&& !"".equals(page.getParams().get("organizationId").toString())) {
					organization.setId(page.getParams().get("organizationId").toString());
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
					if (page.getParams().get("organizationId") != null
							&& !"".equals(page.getParams().get("organizationId").toString())
							&& list1.contains(page.getParams().get("organizationId"))) {
						organization.setId(page.getParams().get("organizationId").toString());
						List<String> results = new ArrayList<String>();
						List<String> list = getRecursion(results, organization);
						map.put("list", list);
					} else {
						map.put("list", list1);
					}
				} else {
					organization.setLevel(0);
					List<Organization> orgList = organizationService.queryAllOrganizations(organization);
					List<String> list = getRecursion(result, orgList.get(0));
					if (page.getParams().get("organizationId") != null
							&& !"".equals(page.getParams().get("organizationId").toString())
							&& list.contains(page.getParams().get("organizationId"))) {
						organization.setId(page.getParams().get("organizationId").toString());
						organization.setLevel(null);
						List<String> results = new ArrayList<String>();
						List<String> list1 = getRecursion(results, organization);
						map.put("list", list1);
					} else {
						map.put("list", list);
					}

				}
			}

			if (page.getParams().get("startTime") != null && !"".equals(page.getParams().get("startTime"))) {
				map.put("startTime", page.getParams().get("startTime"));
			} else {
				map.put("startTime", DateUtil.getFirstDayOfMonth());
			}
			if (page.getParams().get("endTime") != null && !"".equals(page.getParams().get("endTime"))) {
				map.put("endTime", page.getParams().get("endTime"));
			} else {
				map.put("endTime", DateUtil.getLastDayOfMonth());
			}
			map.put("status", page.getParams().get("status"));
			page.setParams(map);
			params.put("list", map.get("list"));
			params.put("startTime", map.get("startTime"));
			params.put("endTime", map.get("endTime"));
			System.out.println("startTime:" + new Date());
			List<MarkStatisticVo> list2 = statisticService.getEmployStatistic(params);
			System.out.println("endTime:" + new Date());
			System.out.println("startTime:" + new Date());
			List<MarkStatisticVo> list = statisticService.getMarkStatisticByOrg(page);
			int totalPage = page.getTotalPage();

			page.setPageSize(Integer.MAX_VALUE);

			BigDecimal totalAmount = new BigDecimal("0.00");
			BigDecimal totalYearAmount = new BigDecimal("0.00");

			map.put("totalAmount", totalAmount);
			map.put("totalYearAmount", totalYearAmount);
			page.setParams(map);
			for (MarkStatisticVo vo : list) {
				for (MarkStatisticVo v : list2) {
					if (vo.getMobile().equals(v.getMobile())) {
						if (vo.getTotalAmount() == null || vo.getYearAmount() == null) {
							vo.setTotalAmount(new BigDecimal("0.00"));
							vo.setYearAmount(new BigDecimal("0.00"));
						}
						vo.setTotalAmount(vo.getTotalAmount().add(v.getTotalAmount()));
						vo.setYearAmount(vo.getYearAmount().add(v.getYearAmount()));
					}
				}
				vo.setMobile(cipher.decrypt(vo.getMobile()));
			}
			page.setResults(list);
			page.setTotalPage(totalPage);
			return page;
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

	// 总额导出
	@Override
	public ResponseEntity<byte[]> exportMarkStatisticData(HttpServletRequest request, Page<MarkStatisticVo> page) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			List<String[]> headNames = new ArrayList<String[]>();
			headNames.add(new String[] { "员工姓名", "手机号", "机构名称", "总投资金额(元)", "总年化投资金额(元)" });
			List<String[]> fieldNames = new ArrayList<String[]>();
			fieldNames.add(new String[] { "staffName", "mobile", "orgName", "totalAmount", "yearAmount" });
			params.put("list", page.getParams().get("list"));
			params.put("startTime", page.getParams().get("startTime"));
			params.put("endTime", page.getParams().get("endTime"));
			List<MarkStatisticVo> list2 = statisticService.getEmployStatistic(params);
			List<MarkStatisticVo> list = statisticService.getMarkStatisticByOrg(page);
			for (MarkStatisticVo vo : list) {
				for (MarkStatisticVo v : list2) {
					if (vo.getMobile().equals(v.getMobile())) {
						if (vo.getTotalAmount() == null || vo.getYearAmount() == null) {
							vo.setTotalAmount(new BigDecimal("0.00"));
							vo.setYearAmount(new BigDecimal("0.00"));
						}
						vo.setTotalAmount(vo.getTotalAmount().add(v.getTotalAmount()));
						vo.setYearAmount(vo.getYearAmount().add(v.getYearAmount()));
					}
				}
				vo.setMobile(cipher.decrypt(vo.getMobile()));
			}
			String name = "公司员工绩效信息统计";
			ResponseEntity<byte[]> responseEntity;
			responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, list, name);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "员工绩效信息导出失败!");
		}
	}

	@Override
	public List<MarkStatisticVo> getEmployStatistic(Page<MarkStatisticVo> page) {
		return null;
	}

	@Override
	public ResponseEntity<byte[]> exportEmployStatisticData(HttpServletRequest request, Page<MarkStatisticVo> page) {
		return null;
	}

	@Override
	public RegisterUser queryRegisterUser(ParamsPojo params) {
		RegisterUser registerUser = new RegisterUser();
		Message message = new Message();
		// 最後釋放開
		if (params.getStart() == null || "".equals(params.getStart()) || params.getEnd() == null
				|| "".equals(params.getEnd())) {
			message.setCode(0);
			message.setMessage("开始或结束时间不能为空");
			registerUser.setMessage(message);
			return registerUser;
		}
		try {
			// 注册列表
			List<UserInvest> userInfos = this.userInfos(params);
			registerUser.setRegisterUserInfo(new RegisterUserInfo(userInfos));

			// 综合列表1，注册人数统计
			UserStatistics userStatistics = this.userStatistics(params);
			registerUser.setUserStatistics(userStatistics);

			// 综合统计表2，不同类型标的投资额度及人数统计
			List<InvestLoan> investLoans = this.userInvestByLoan(params);
			registerUser.setUserInvestLoan(new UserInvestLoan(investLoans));

			// 综合表3，红包体验金发放情况
			List<FreshInfo> freshInfos = this.freshInfos(params);
			registerUser.setFreshInfoStatistics(new FreshInfoStatistics(freshInfos));

			// 综合表4，投资规模人数统计
			List<InvestLevelCount> investLevelcount = this.investLevelCount(params);
			registerUser.setInvestLevel(new InvestLevel(investLevelcount));
			message.setCode(1);
			message.setMessage("成功");
			registerUser.setMessage(message);

		} catch (GeneralSecurityException e) {
			logger.info(e.fillInStackTrace());
			e.printStackTrace();
			message.setCode(0);
			message.setMessage("未知错误");
			registerUser.setMessage(message);
		}
		return registerUser;
	}

	List<InvestLevelCount> investLevelCount(ParamsPojo params) {
		List<InvestLevelCount> ilc = new ArrayList<InvestLevelCount>();
		List<Map<String, Object>> res = userService.queryInvestLevelCount(params);
		for (Map<String, Object> map : res) {
			InvestLevelCount lc = new InvestLevelCount();
			lc.setLevel((Integer) map.get("level"));
			lc.setCount(((Long) map.get("count")).intValue());
			ilc.add(lc);
		}
		return ilc;
	}

	List<FreshInfo> freshInfos(ParamsPojo params) {
		List<FreshInfo> freshInfos = new ArrayList<FreshInfo>();
		List<Map<String, Object>> list = userService.queryFreshInfos(params);
		if (list.size() > 0) {

			FreshInfo f1 = new FreshInfo();
			f1.setType("体验券");
			FreshInfo f2 = new FreshInfo();
			f2.setType("月月发");
			FreshInfo f3 = new FreshInfo();
			f3.setType("季度红");
			FreshInfo f4 = new FreshInfo();
			f4.setType("半年财");
			FreshInfo f5 = new FreshInfo();
			f5.setType("全年盈");
			for (Map<String, Object> map : list) {
				Integer months = (Integer) map.get("months");// 标的期限 0所有标的，。。。
				Integer status = (Integer) map.get("status");// 使用状态：
																// 0未使用，1已使用，2已作废
				BigDecimal amount = (BigDecimal) map.get("amount");// 兑现金额
				Long count = (Long) map.get("count");// 对应红包或体验券数量
				if (months != 0 && months != 1 && months != 3 && months != 6 && months != 12)
					continue;
				switch (months) {
				case 0:

					f1.setTotalCount(f1.getTotalCount() + count.intValue());
					if (status == 1) {
						if (f1.getAmount() == null) {
							f1.setAmount(new BigDecimal(0));
						}
						f1.setAmount(f1.getAmount().add(amount));
						f1.setUseCount(f1.getUseCount() + count.intValue());
					}
					break;
				case 1:

					f2.setTotalCount(f2.getTotalCount() + count.intValue());
					if (status == 1) {
						if (f2.getAmount() == null) {
							f2.setAmount(new BigDecimal(0));
						}
						f2.setAmount(f2.getAmount().add(amount));
						f2.setUseCount(f2.getUseCount() + count.intValue());
					}
					break;
				case 3:

					f3.setTotalCount(f3.getTotalCount() + count.intValue());
					if (status == 1) {
						if (f3.getAmount() == null) {
							f3.setAmount(new BigDecimal(0));
						}
						f3.setAmount(f3.getAmount().add(amount));
						f3.setUseCount(f3.getUseCount() + count.intValue());
					}
					break;
				case 6:

					f4.setTotalCount(f4.getTotalCount() + count.intValue());
					if (status == 1) {
						if (f4.getAmount() == null) {
							f4.setAmount(new BigDecimal(0));
						}
						f4.setAmount(f4.getAmount().add(amount));
						f4.setUseCount(f4.getUseCount() + count.intValue());
					}
					break;
				case 12:

					f5.setTotalCount(f5.getTotalCount() + count.intValue());
					if (status == 1) {
						if (f5.getAmount() == null) {
							f5.setAmount(new BigDecimal(0));
						}
						f5.setAmount(f5.getAmount().add(amount));
						f5.setUseCount(f5.getUseCount() + count.intValue());
					}
					break;

				default:
					break;
				}

			}
			freshInfos.add(f1);
			freshInfos.add(f2);
			freshInfos.add(f3);
			freshInfos.add(f4);
			freshInfos.add(f5);
		}
		return freshInfos;
	}

	List<InvestLoan> userInvestByLoan(ParamsPojo params) {
		List<InvestLoan> investLoans = new ArrayList<InvestLoan>();
		List<Map<String, Object>> list = userService.queryUserInvestByLoan(params);
		for (Map<String, Object> map : list) {
			Integer months = (Integer) map.get("months");
			BigDecimal totalAmount = (BigDecimal) map.get("totalAmount");
			Long totalPerson = (Long) map.get("totalPerson");
			if (months == 0)
				continue;
			InvestLoan investLoan = new InvestLoan();
			switch (months) {
			case 1:
				investLoan.setType("月月发");
				break;
			case 3:
				investLoan.setType("季度红");
				break;
			case 6:
				investLoan.setType("半年财");
				break;
			case 12:
				investLoan.setType("全年盈");
				break;
			default:
				continue;
			}
			if (totalAmount == null) {
				investLoan.setScaleAmount(new BigDecimal(0));
				investLoan.setYearAmount(new BigDecimal(0));
			} else {
				investLoan.setScaleAmount(totalAmount);
				investLoan.setYearAmount(totalAmount.multiply(new BigDecimal(months)).divide(new BigDecimal(12), 2,
						BigDecimal.ROUND_UP));
			}
			if (totalPerson == null) {
				investLoan.setTotalPerson(0);
			} else {
				investLoan.setTotalPerson(totalPerson.intValue());
			}

			investLoans.add(investLoan);
		}
		return investLoans;
	}

	List<UserInvest> userInfos(ParamsPojo params) throws GeneralSecurityException {
		List<UserInvest> res=new ArrayList<UserInvest>();
		List<UserInvest> userInfos = userService.queryUserInfos(params);
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		for (UserInvest userInvest : userInfos) {
			if(params.isIfinvest()==0&&userInvest.getCount()>0)continue;
			userInvest.setMobile(cipher.decrypt(userInvest.getMobile()));
			res.add(userInvest);
		}
		return res;
	}

	UserStatistics userStatistics(ParamsPojo params) {
		Map<String, Long> res = userService.queryConditionUser(params);
		Long registUsers = (Long) res.get("registUsers");
		Long authenUsers = (Long) res.get("authenUsers");
		params.setIdauthenticated(1);
		params.setIfinvest(1);
		List<UserInvest> count = userService.queryUserInfos(params);
		UserStatistics userStatistics = new UserStatistics();
		userStatistics.setRegisterUserCount(registUsers.intValue());
		userStatistics.setAuthenUserCount(authenUsers.intValue());
		userStatistics.setNoAutherUserCount(registUsers.intValue() - authenUsers.intValue());
		userStatistics.setInvestUserCount(count.size());
		return userStatistics;
	}

	@Override
	public ResponseEntity<byte[]> queryRegisterUserDate(HttpServletRequest request, ParamsPojo params) {
		String name = "平台资金记录统计";
		System.out.println("开始导出excle-------" + System.currentTimeMillis());
		ResponseEntity<byte[]> responseEntity = null;
		try {
			List<List<String>> headRegisterData = new ArrayList<List<String>>();
			List<List<String>> headTypesData = new ArrayList<List<String>>();
			List<List<String>> headRedsData = new ArrayList<List<String>>();
			List<List<String>> headIntervalData = new ArrayList<List<String>>();
			// 综合列表1，注册人数统计
			UserStatistics userStatistics = this.userStatistics(params);

			// 综合统计表2，不同类型标的投资额度及人数统计
			List<InvestLoan> investLoans = this.userInvestByLoan(params);

			// 综合表3，红包体验金发放情况
			List<FreshInfo> freshInfos = this.freshInfos(params);
			List<InvestLevelCount> investLevelcount = this.investLevelCount(params);
			if (null != userStatistics) {
				List<String> rowRegData = new ArrayList<String>();
				rowRegData.add(String.valueOf(userStatistics.getRegisterUserCount()));// 注册(人)
				rowRegData.add(String.valueOf(userStatistics.getAuthenUserCount()));// 已认证(人)
				rowRegData.add(String.valueOf(userStatistics.getNoAutherUserCount()));// 未认证(人)
				rowRegData.add(String.valueOf(userStatistics.getInvestUserCount()));// 已投资(人)
				headRegisterData.add(rowRegData);
			}
			if (investLoans.size() > 0) {
				for (int i = 0; i < investLoans.size(); i++) {
					List<String> rowTpyData = new ArrayList<String>();
					if ("月月发".endsWith(investLoans.get(i).getType().toString())) {
						rowTpyData.add(investLoans.get(i).getType().toString());// 类型
						rowTpyData.add(investLoans.get(i).getScaleAmount().toString());
						rowTpyData.add(investLoans.get(i).getYearAmount().toString());
						rowTpyData.add(String.valueOf(investLoans.get(i).getTotalPerson()));
						headTypesData.add(rowTpyData);
					}
					if ("季度红".endsWith(investLoans.get(i).getType().toString())) {
						rowTpyData.add(investLoans.get(i).getType().toString());// 类型
						rowTpyData.add(investLoans.get(i).getScaleAmount().toString());
						rowTpyData.add(investLoans.get(i).getYearAmount().toString());
						rowTpyData.add(String.valueOf(investLoans.get(i).getTotalPerson()));
						headTypesData.add(rowTpyData);
					}
					if ("半年财".endsWith(investLoans.get(i).getType().toString())) {
						rowTpyData.add(investLoans.get(i).getType().toString());// 类型
						rowTpyData.add(investLoans.get(i).getScaleAmount().toString());
						rowTpyData.add(investLoans.get(i).getYearAmount().toString());
						rowTpyData.add(String.valueOf(investLoans.get(i).getTotalPerson()));
						headTypesData.add(rowTpyData);
					}
					if ("全年盈".endsWith(investLoans.get(i).getType().toString())) {
						rowTpyData.add(investLoans.get(i).getType().toString());// 类型
						rowTpyData.add(investLoans.get(i).getScaleAmount().toString());
						rowTpyData.add(investLoans.get(i).getYearAmount().toString());
						rowTpyData.add(String.valueOf(investLoans.get(i).getTotalPerson()));
						headTypesData.add(rowTpyData);
					}

				}
			}

			if (freshInfos.size() > 0) {
				for (int i = 1; i < freshInfos.size(); i++) {
					List<String> rowRedData = new ArrayList<String>();
					if ("月月发".endsWith(freshInfos.get(i).getType().toString())) {
						rowRedData.add("月月发");// 类型
						rowRedData.add(String.valueOf(freshInfos.get(i).getTotalCount()) + "/"
								+ String.valueOf(freshInfos.get(i).getUseCount()));
						if (null != freshInfos.get(i).getAmount()) {
							rowRedData.add(freshInfos.get(i).getAmount().toString());
							headRedsData.add(rowRedData);
						} else {
							rowRedData.add("0");
							headRedsData.add(rowRedData);
						}

					} else if ("季度红".endsWith(freshInfos.get(i).getType().toString())) {
						rowRedData.add("季度红");// 类型
						rowRedData.add(String.valueOf(freshInfos.get(i).getTotalCount()) + "/"
								+ String.valueOf(freshInfos.get(i).getUseCount()));
						if (null != freshInfos.get(i).getAmount()) {
							rowRedData.add(freshInfos.get(i).getAmount().toString());
							headRedsData.add(rowRedData);
						} else {
							rowRedData.add("0");
							headRedsData.add(rowRedData);
						}
					} else if ("半年财".endsWith(freshInfos.get(i).getType().toString())) {
						rowRedData.add("半年财");// 类型
						rowRedData.add(String.valueOf(freshInfos.get(i).getTotalCount()) + "/"
								+ String.valueOf(freshInfos.get(i).getUseCount()));
						if (null != freshInfos.get(i).getAmount()) {
							rowRedData.add(freshInfos.get(i).getAmount().toString());
							headRedsData.add(rowRedData);
						} else {
							rowRedData.add("0");
							headRedsData.add(rowRedData);
						}
					} else if ("全年盈".endsWith(freshInfos.get(i).getType().toString())) {
						rowRedData.add("全年盈");// 类型
						rowRedData.add(String.valueOf(freshInfos.get(i).getTotalCount()) + "/"
								+ String.valueOf(freshInfos.get(i).getUseCount()));
						if (null != freshInfos.get(i).getAmount()) {
							rowRedData.add(freshInfos.get(i).getAmount().toString());
							headRedsData.add(rowRedData);
						} else {
							rowRedData.add("0");
							headRedsData.add(rowRedData);
						}
					} else {
						rowRedData.add("体验券");// 类型
						rowRedData.add(String.valueOf(freshInfos.get(i).getTotalCount()) + "/"
								+ String.valueOf(freshInfos.get(i).getUseCount()));
						if (null != freshInfos.get(i).getAmount()) {
							rowRedData.add(freshInfos.get(i).getAmount().toString());
							headRedsData.add(rowRedData);
						} else {
							rowRedData.add("0");
							headRedsData.add(rowRedData);
						}
					}
				}

			}
			if (investLevelcount.size() > 0) {
				List<String> rowIntData = new ArrayList<String>();
				String leve1 = "0";
				String leve2 = "0";
				String leve3 = "0";
				String leve4 = "0";
				String leve5 = "0";
				for (int i = 0; i < investLevelcount.size(); i++) {
					if (1 == investLevelcount.get(i).getLevel()) {
						leve1 = String.valueOf(investLevelcount.get(i).getCount());
					}
					if (2 == investLevelcount.get(i).getLevel()) {
						leve2 = String.valueOf(investLevelcount.get(i).getCount());
					}
					if (3 == investLevelcount.get(i).getLevel()) {
						leve3 = String.valueOf(investLevelcount.get(i).getCount());
					}
					if (4 == investLevelcount.get(i).getLevel()) {
						leve4 = String.valueOf(investLevelcount.get(i).getCount());
					}
					if (5 == investLevelcount.get(i).getLevel()) {
						leve5 = String.valueOf(investLevelcount.get(i).getCount());
					}

				}
				rowIntData.add(leve1);
				rowIntData.add(leve2);
				rowIntData.add(leve3);
				rowIntData.add(leve4);
				rowIntData.add(leve5);
				headIntervalData.add(rowIntData);
			}

			responseEntity = UploadExcelUtil.uploadExcle(request, headRegisterData, headTypesData, headRedsData,
					headIntervalData);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseEntity;
	}

	@Override
	public ManagerData queryManagerData(String date) {
		 
		Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,Integer.valueOf(date.substring(0, 4)));
        //设置月份
        cal.set(Calendar.MONTH, Integer.valueOf(date.substring(date.length()-2, date.length()))-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        
        Date d=cal.getTime();
		
		ManagerData managerDate = loanService.queryLoanAmount(d);

		int loanSerson = loanService.queryLoanPerson(d);
		managerDate.setLoanPerson(loanSerson);
		managerDate.setInPerson(loanService.queryInPerson(d));
		
		int allDays = loanService.queryLimitDays(d);
		managerDate.setLimitDays(new BigDecimal(allDays).divide(new BigDecimal(loanSerson),4,BigDecimal.ROUND_HALF_UP).doubleValue());
		managerDate.setRate(new BigDecimal(loanService.queryRate(d)).setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue());
		managerDate.setLegonAmount(managerDate.getLoanAmount().divide(new BigDecimal(loanSerson),6,BigDecimal.ROUND_HALF_UP));

		return managerDate;
	}
}
