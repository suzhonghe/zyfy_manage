package com.zhongyang.java.service;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.OrganizationUser;
import com.zhongyang.java.vo.OrganizationUserVo;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.UmpUserFund;
import com.zhongyang.java.vo.UserInvest;
import com.zhongyang.java.vo.UserVo;


public interface UserService {
	
	 public int getUserNumByMobile(User user);
	 
	 public User getUserByMobile(String mobile);
	 
	 public User getUserByLoginName(String LoginName);
	 
	 public int addUser(User user);
	 /**
	  * 
	  * @param user
	  * @return
	  * 根据用户Id查询用户信息
	  */
	 public User getUserById(User user) throws Exception;
	 
	 public User queryByParams(User user)throws Exception;
	 
	 public List<User> queryByUser(Page<User> page)throws Exception;
	 
	 public int queryCountByParams(Map<String, Object> params)throws Exception;
	 
	 public int modifyUser(User user)throws Exception;
	 
	 public List<User> likeQuery(Page<User> page)throws Exception;

     //用户查询
	 public List<User> getUserList(Page<User> page) throws Exception;

	public int getUserListCount(Map<String, Object> params)throws Exception;

	public UserVo getUserDetaile(String userId)throws Exception;

	public int updateUserInfos(UserVo userVo) throws Exception;
	
	public List<OrganizationUser> queryByParams(Page<User> page)throws Exception;
	
	public List<User> queryUserListByParams(User user)throws Exception;
	
	public List<OrganizationUserVo> queryUsersInvestByParams(Map<String, Object> params)throws Exception;

	public List<User> getUserMobiles(Map<String, Object> map) throws Exception;
	
	public List<User> getUserByName(String name);
	
	//批量修改用户推荐人
	public void updateInviteUserBatch(String newReferralId,String oldReferralId,String newReferralRealm);
	
	public List<UmpUserFund> queryUserFund(Page<UmpUserFund> page);
	//查询当天投资/回款/充值/提现的用户的基本信息
	public List<User> getTodayInvestUserInfo(Page<User> page);

	// 客服统计 根据用户投资金额额度进行统计
	public List<Map<String, Object>> getUserInvestInfo(Page<Map<String, Object>> page);

	// 根据用户投资次数进行统计
	public List<Map<String, Object>> getUserInvestCount(Page<Map<String, Object>> page);
	
	public List<User>getUser(User user);
	
	public List<User> queryUserByParams(Page<User> page);

	public int getUserListCountZC(Map<String, Object> params) throws Exception;

	public List<User> getUserListZC(Page<User> page) throws Exception;

	public List<UserVo> queryUserByParamslist(List<Object> list);
	
	public List<UserInvest>queryUserInfos(ParamsPojo params);
	
	public Map<String,Long>queryConditionUser(ParamsPojo params);
	
	public List<Map<String,Object>>queryUserInvestByLoan(ParamsPojo params);
	
	public List<Map<String,Object>>queryFreshInfos(ParamsPojo params);
	
	public List<Map<String,Object>>queryInvestLevelCount(ParamsPojo params);
	
}
