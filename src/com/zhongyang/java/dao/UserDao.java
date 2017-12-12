package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.OrganizationUser;
import com.zhongyang.java.vo.OrganizationUserVo;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.UmpUserFund;
import com.zhongyang.java.vo.UserInvest;
import com.zhongyang.java.vo.UserVo;

public interface UserDao {	
	/**
	 * 通过手机号码 查询用户是否存在
	 * @param mobile 手机号码
	 * @return 存在为1 不存在为0
	 */
	public int getCountModelByMobile(User user);
	
	public List<User> getUser(User user);
	
	public void updateUsers(List<User> list);
	
	public User getUserByParam(User user);
		
	public int addUser(User user);
    /**
     * 
     * @param user
     * @return
     * 根据userId查询用户
     */
	public User getUserById(User user);
	
	public List<User> selectByUser(Page<User> page)throws Exception;
	
	public int selecCountByParams(Map<String, Object> params)throws Exception;
	
	public int updateUser(User user)throws Exception;
	
	public List<UmpUserFund> selectUserFund(Page<UmpUserFund> page);
	
	public List<User> likeSelect(Page<User> page)throws Exception;

	 //用户查询
	public List<User> getUserList(Page<User> page) throws Exception;

	public int getUserListCount(Map<String, Object> params)throws Exception;

	public UserVo getUserDetaile(String userId)throws Exception;

	public int updateUserInfos(UserVo userVo) throws Exception;
	
	public List<OrganizationUser> selectByParams(Page<User> page)throws Exception;
	
	public List<User> selectUserListByParams(User user)throws Exception;
	
	public List<OrganizationUserVo> selectUsersInvestByParams(Map<String, Object> params)throws Exception;

	public List<User>  getUserMobiles(Map<String, Object> map)throws Exception;
	
	public List<User> getUserByName(String name) throws Exception;
	//批量修改用户推荐人
	public void updateInviteUserBatch(@Param("newReferralId") String newReferralId,@Param("oldReferralId") String oldReferralId,@Param("newReferralRealm") String newReferralRealm);

	public int getUserListCountZC(Map<String, Object> params) throws Exception;

	public List<User> getUserListZC(Page<User> page) throws Exception;
	
	//查询当天投资/回款/充值/提现的用户的基本信息
	public List<User> getTodayInvestUserInfo(Page<User> page);
	//客服统计 根据用户投资金额额度进行统计
	public List<Map<String,Object>> getUserInvestInfo(Page<Map<String,Object>> page);
	//根据用户投资次数进行统计
	public List<Map<String,Object>> getUserInvestCount(Page<Map<String,Object>> page);
 	
	
	public List<User> selectUserByParams(Page<User> page);

	public List<UserVo> queryUserByParamslist(List<Object> list);

	public List<UserVo> queryUserByParamslistCC(String ccid);
	
	public List<UserInvest>selectUserInfos(ParamsPojo params);
	
	public Map<String,Long>selectConditionUser(ParamsPojo params);
	
	public List<Map<String,Object>>selectUserInvestByLoan(ParamsPojo params);
	
	public List<Map<String,Object>>selectFreshInfos(ParamsPojo params);
	
	public List<Map<String,Object>>selectInvestLevelCount(ParamsPojo params);
	
	public List<User>selectFund();
}
