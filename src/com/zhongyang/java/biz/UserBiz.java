package com.zhongyang.java.biz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.GroupUserVO;
import com.zhongyang.java.vo.OrganizationUser;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.Registered;
import com.zhongyang.java.vo.UmpAccountVo;
import com.zhongyang.java.vo.UserInfoVo;
import com.zhongyang.java.vo.UserVo;

public interface UserBiz {
	public Message addUser(HttpServletRequest req,Registered registered);
	
	public Message mobileRepeat(String mobile);
	
	public PagerVO<GroupUserVO> queryByUser(PagerVO<User> pager);
	
	public Message modifyUser(User user);
	
	public UserInfoVo queryUserByMobile(String mobile);
	
	public PagerVO<GroupUserVO> likeQuery(PagerVO<User> pager);

    //用户查询
	public PagerVO<UserVo> getUserList(PagerVO<User>pager);

	public UserVo getUserDetaile(String userId);

	public Message updateUserInfo(UserVo userVo);

	public Message updateUserStatus(UserVo userVo);
	
	public UmpAccountVo getUserUmpInfo(String userId);

	public PagerVO<OrganizationUser> queryByParams(PagerVO<User> pager);

	public List<String> getUserMobiles(String startTime, String endTiem);

	public String sensMessageToUser(String messsge,String sendTime,String mobiles);
	
	//批量修改用户推荐人
	public Message updateInviteUserBatch(String newReferralId,String oldReferralId,String newReferralRealm);
	//查询当天投资/回款/充值/提现的用户的基本信息
	public Page<User> getTodayInvestUserInfo(Page<User> page);
	// 客服统计
	public Page<Map<String, Object>> getUserInvest(Page<Map<String, Object>> page);

	public void modifyAllUsers();
	
	public List<String> queryBirthDateUsers(String statTime,String endTiem);
	
	public List<String> queryInvestUsers(String statTime,String endTiem);
}	
