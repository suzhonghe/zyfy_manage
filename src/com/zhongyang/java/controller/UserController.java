package com.zhongyang.java.controller;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.UserBiz;
import com.zhongyang.java.pojo.ChangeInviteRecord;
import com.zhongyang.java.pojo.Employee;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.ChangeInviteRecordService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.GroupUserVO;
import com.zhongyang.java.vo.OrganizationUser;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.Registered;
import com.zhongyang.java.vo.UmpAccountVo;
import com.zhongyang.java.vo.UserInfoVo;
import com.zhongyang.java.vo.UserVo;

@Controller
public class UserController extends BaseController {
	
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private UserService userService;
	@Autowired
	private ChangeInviteRecordService changeInviteRecordService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Message getVerificationCode(HttpServletRequest req,Registered registered){
		return userBiz.addUser(req,registered);
	}
	
	@RequestMapping(value = "/mobileRepeat", method = RequestMethod.POST)
	public @ResponseBody Message getVerificationCode(String mobile){
		return userBiz.mobileRepeat(mobile);
	}
	/**
	 * 
	* @Title: getVerificationCode 
	* @Description:分组用户查询
	* @return Message    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/queryByGroupId")
	public @ResponseBody PagerVO<GroupUserVO> queryByGroupId(PagerVO<User> pager){
		return userBiz.queryByUser(pager);
	}
	/**
	 * 
	* @Title: queryUserByMobile 
	* @Description: 根据手机号查询用户信息
	* @return UserInfoVo    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/queryUserByMobile")
	public @ResponseBody UserInfoVo queryUserByMobile(String mobile){
		return userBiz.queryUserByMobile(mobile);
	}
	
	@RequestMapping(value = "/likeQueryUser")
	public @ResponseBody PagerVO<GroupUserVO> likeQueryUser(PagerVO<User> pager){
		return userBiz.likeQuery(pager);
	}
	 /* 用户查询列表
	 * @param pager
	 * @return
	 */
	@FireAuthority(authorities=Authorities.USERLIST)
	@RequestMapping(value = "/getUserList")
	public @ResponseBody PagerVO<UserVo> getUserList(PagerVO<User>pager){
		return userBiz.getUserList(pager);
	}
	
	/**
	 * 用户查询明细
	 * @param mobile
	 * @return
	 */
	@FireAuthority(authorities=Authorities.USERINFODETAILE)
	@RequestMapping(value = "/getUserDetaile")
	public @ResponseBody UserVo getUserDetaile(String  userId){
		return userBiz.getUserDetaile(userId);
	}
	
	/**
	 * 修改用户电话号码，推荐人电话号码，是否为员工
	 * @param mobile
	 * @return
	 */
	@FireAuthority(authorities=Authorities.USERINFOEDIT)
	@RequestMapping(value = "/updateUserInfo")
	public @ResponseBody Message updateUserInfo(UserVo userVo){
		return userBiz.updateUserInfo(userVo);
	}
	
	/**
	 * 修改用户状态(启动；停止)
	 * @param mobile
	 * @return
	 */
	@FireAuthority(authorities=Authorities.USERISTATUSEDIT)
	@RequestMapping(value = "/updateUserStatus")
	public @ResponseBody Message updateUserStatus(UserVo userVo){
		return userBiz.updateUserStatus(userVo);
	}
	/**
	 * 用户第三方账户信息查询
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/getUserUmpInfo")
	public @ResponseBody UmpAccountVo getUserUmpInfo(String userId){
		return userBiz.getUserUmpInfo(userId);
	}

	/* 
	* @Title: queryByParams 
	* @Description:根据机构查询用户
	* @return PagerVO<OrganizationUser>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/queryByOrganization")
	public @ResponseBody PagerVO<OrganizationUser> queryByOrganization(PagerVO<User> pager){
		return userBiz.queryByParams(pager);
	}
	@RequestMapping(value = "/modifyUser")
	public @ResponseBody Message modifyUser(User user){
		return userBiz.modifyUser(user);
	}
	
	
	/*
	 * 获取用户电话号码发送短信
	 * 
	 */
	
	
	/*
	 * 发送短信给用户
	 * 
	 */
	@RequestMapping(value = "/sensMessageToUser")
	public  @ResponseBody String sensMessageToUser(HttpServletRequest req,String sendTime,String messsge,String mobiles) {
//	public  @ResponseBody String sensMessageToUser(HttpServletRequest req,String messsge) {
//                messsge = "Hello word";
//                String sendTime = "2016-05-18 14:00:00";
		return userBiz.sensMessageToUser(messsge,sendTime,mobiles);
     
	}
	
	
	//批量修改推荐人
	@FireAuthority(authorities=Authorities.CHANGEINVITE)
	@RequestMapping(value = "/updateInviteUserBatch")
	@ResponseBody
	public Message updateInviteUserBatch(String oldMobile,String newMobile,HttpServletRequest request){
		HttpSession session = request.getSession();
		Employee employee = (Employee) session.getAttribute("zycfLoginEmp");
		ChangeInviteRecord cir = new ChangeInviteRecord();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Message message = new Message();
		if("".equals(oldMobile) || oldMobile==null || "".equals(newMobile) || newMobile==null){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数不可以为空！");
		}
		try{
			User oldUser = new User();
			oldUser.setMobile(cipher.encrypt(oldMobile));
			User newUser = new User();
			newUser.setMobile(cipher.encrypt(newMobile));
			oldUser=userService.queryByParams(oldUser);
			newUser=userService.queryByParams(newUser);
			if(oldUser==null || newUser == null){
				message.setMessage("未查询到手机号注册信息！请核实手机号");
				return message;
			}
			cir.setOldUserId(oldUser.getReferralId());//原推荐人id
			cir.setNewUserId(newUser.getId());
			cir.setOldPhoneNo(oldMobile);
			cir.setNewPhoneNo(newMobile);
			cir.setOperator(employee.getName());
			cir.setCreateTime(new Date());
			cir.setType("将##"+oldUser.getName()+"##推荐的用户批量修改为##"+newUser.getName()+"推荐");
			changeInviteRecordService.insertChangeInviteRecord(cir);
			return userBiz.updateInviteUserBatch(newUser.getId(), oldUser.getId(),newUser.getName());
		}catch(Exception e){
			e.printStackTrace();
		}
		message.setMessage("系统繁忙，请稍后重试！");
		return message;
	}
	
	//单个修改用户推荐人
	@FireAuthority(authorities=Authorities.CHANGEINVITE)
	@RequestMapping(value = "/updateInviteUser")
	@ResponseBody
	public Message updateInviteUser(String mobile,String newMobile,HttpServletRequest request){
		HttpSession session = request.getSession();
		Employee employee = (Employee) session.getAttribute("zycfLoginEmp");
		ChangeInviteRecord cir = new ChangeInviteRecord();
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		Message message = new Message();
		if("".equals(mobile) || mobile==null || "".equals(newMobile) || newMobile==null){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数不可以为空！");
		}
		try{
			User oldUser = new User();
			oldUser.setMobile(cipher.encrypt(mobile));
			User newUser = new User();
			newUser.setMobile(cipher.encrypt(newMobile));
			oldUser=userService.queryByParams(oldUser);
			newUser=userService.queryByParams(newUser);
			if(oldUser==null || newUser == null){
				message.setMessage("未查询到手机号注册信息！请核实手机号");
				return message;
			}
			cir.setOldUserId(oldUser.getReferralId());//原推荐人id
			cir.setNewUserId(newUser.getId());
			cir.setOldPhoneNo(mobile);
			cir.setNewPhoneNo(newMobile);
			cir.setOperator(employee.getName());
			cir.setCreateTime(new Date());
			cir.setType("将##"+oldUser.getName()+"##的推荐人修改为##"+newUser.getName());
			changeInviteRecordService.insertChangeInviteRecord(cir);
			UserVo userVo = new UserVo();
			userVo.setId(oldUser.getId());
			userVo.setReferralId(newUser.getId());
			userVo.setReferralRealm(newUser.getName());
			userService.updateUserInfos(userVo);
			message.setCode(1);
			message.setMessage("修改客户推荐人信息成功！");
			return message;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		message.setMessage("系统繁忙，请稍后重试！");
		return message;
	}
	
	@RequestMapping(value = "/getTodayUserInfo")
	@ResponseBody
	public Page<User> getTodayInvestUserInfo(Page<User> page){
		Map<String,Object>  map = new HashMap<String,Object>();
		map.put("status", "INVEST");
		page.setParams(map);
		page.setPageNo(1);
		page.setPageSize(10);
		return userBiz.getTodayInvestUserInfo(page);
		
	}
	
	@RequestMapping(value = "/getUserMobiles")
	public  @ResponseBody List<String> getUserMobiles(HttpServletRequest req,String startTime,String endTiem) {
//	public  @ResponseBody List<User> getUserMobiles(HttpServletRequest req) {
//                String startTime = "2014-10-01";
//				String endTiem = "2015-10-01";
		return userBiz.getUserMobiles(startTime,endTiem);
     
	}
	
	//客服统计
	@FireAuthority(authorities=Authorities.SERVICESTIC)
	@RequestMapping(value = "/getUserInvestInfo")
	@ResponseBody
	public Page<Map<String, Object>> getUserInvestInfo(Page<Map<String, Object>> page){
		
		return userBiz.getUserInvest(page);
		
	}
	
	
	@RequestMapping(value = "/modifyAllUsers")
	public @ResponseBody void modifyAllUsers(User user){
		userBiz.modifyAllUsers();
	}
	
	@RequestMapping(value = "/queryBirthDateUsers",method = RequestMethod.POST)
	public @ResponseBody List<String> queryBirthDateUsers(String startTime,String endTiem){
		return userBiz.queryBirthDateUsers(startTime,endTiem);
	}
	
	@RequestMapping(value = "/queryInvestUsers",method = RequestMethod.POST)
	public @ResponseBody List<String> queryInvestUsers(String startTime,String endTiem){
		return userBiz.queryInvestUsers(startTime,endTiem);
	}
	
}
