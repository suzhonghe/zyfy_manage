package com.zhongyang.java.service.impl;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.UserDao;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.OrganizationUser;
import com.zhongyang.java.vo.OrganizationUserVo;
import com.zhongyang.java.vo.ParamsPojo;
import com.zhongyang.java.vo.UmpUserFund;
import com.zhongyang.java.vo.UserInvest;
import com.zhongyang.java.vo.UserVo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public int getUserNumByMobile(User user) {
		return userDao.getCountModelByMobile(user);
	}

	@Override
	public User getUserByMobile(String mobile) {
		User user=new User();
		user.setMobile(mobile);
		return userDao.getUserByParam(user);
	}

	@Override
	public User getUserByLoginName(String LoginName) {
		User user=new User();
		user.setMobile(LoginName);
		return userDao.getUserByParam(user);
	}

	@Override
	public int addUser(User user) {	
		return userDao.addUser(user);
	}

	/*
	 * (non-Javadoc)
	 * @see com.zhongyang.java.service.UserService#getUserById(java.lang.String)
	 * 根据用户Id查询用户信息
	 */
	public User getUserById(User user) throws Exception{
		
		return userDao.getUserById(user);
	}

	@Override
	public User queryByParams(User user) throws Exception {
		return userDao.getUserByParam(user);
	}
	
	@Override
	public List<User> getUser(User user) {
		// TODO Auto-generated method stub
		return userDao.getUser(user);
	}

	@Override
	public List<User> queryByUser(Page<User> page) throws Exception {
		return userDao.selectByUser(page);
	}

	@Override
	public int queryCountByParams(Map<String, Object> params) throws Exception {
		return userDao.selecCountByParams(params);
	}

	@Override
	public int modifyUser(User user) throws Exception {
		return userDao.updateUser(user);
	}

	@Override
	public List<User> likeQuery(Page<User> page) throws Exception {
		return userDao.likeSelect(page);
	}
	public List<User> getUserList(Page<User> page) throws Exception {
		return userDao.getUserList(page);
	}

	@Override
	public int getUserListCount(Map<String, Object> params) throws Exception {
		return userDao.getUserListCount(params);
	}

	@Override
	public UserVo getUserDetaile(String userId) throws Exception {
		return userDao.getUserDetaile(userId);
	}

	@Override
	public int updateUserInfos(UserVo userVo) throws Exception {
		return userDao.updateUserInfos(userVo);
	}
	
	public List<OrganizationUser> queryByParams(Page<User> page)throws Exception{
		return userDao.selectByParams(page);
	}
	
	@Override
	public List<User> queryUserListByParams(User user) throws Exception {
		return userDao.selectUserListByParams(user);
	}

	@Override
	public List<OrganizationUserVo> queryUsersInvestByParams(Map<String, Object> params)throws Exception{
		return userDao.selectUsersInvestByParams(params);
	}

	@Override
	public List<User>  getUserMobiles(Map<String, Object> map) throws Exception {
		
		return userDao.getUserMobiles(map);
	}

	@Override
	public List<User> getUserByName(String name) {
		try {
			return userDao.getUserByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateInviteUserBatch(String newReferralId, String oldReferralId,String newReferralRealm) {
		userDao.updateInviteUserBatch(newReferralId, oldReferralId,newReferralRealm);
		
	}

	@Override
	public List<UmpUserFund> queryUserFund(Page<UmpUserFund> page) {
		return userDao.selectUserFund(page);
	}

	@Override
	public int getUserListCountZC(Map<String, Object> params) throws Exception {
		
		return userDao.getUserListCountZC(params);
	}

	@Override
	public List<User> getUserListZC(Page<User> page) throws Exception {
		return userDao.getUserListZC(page);
		}
	public List<User> getTodayInvestUserInfo(Page<User> page) {
		
		return userDao.getTodayInvestUserInfo(page);
	}

	@Override
	public List<Map<String, Object>> getUserInvestInfo(Page<Map<String, Object>> page) {
		
		return userDao.getUserInvestInfo(page);
	}

	@Override
	public List<Map<String, Object>> getUserInvestCount(Page<Map<String, Object>> page) {
		
		return userDao.getUserInvestCount(page);
	}

	@Override
	public List<User> queryUserByParams(Page<User> page) {
		return userDao.selectUserByParams(page);
	}

	@Override
	public List<UserVo> queryUserByParamslist(List<Object> list) {
		return userDao.queryUserByParamslist(list);
	}

	@Override
	public List<UserInvest> queryUserInfos(ParamsPojo params) {
		return userDao.selectUserInfos(params);
	}

	@Override
	public Map<String, Long> queryConditionUser(ParamsPojo params) {
		return userDao.selectConditionUser(params);
	}

	@Override
	public List<Map<String, Object>> queryUserInvestByLoan(ParamsPojo params) {
		return userDao.selectUserInvestByLoan(params);
	}

	@Override
	public List<Map<String, Object>> queryFreshInfos(ParamsPojo params) {
		return userDao.selectFreshInfos(params);
	}

	@Override
	public List<Map<String, Object>> queryInvestLevelCount(ParamsPojo params) {
		return userDao.selectInvestLevelCount(params);
	}
}
