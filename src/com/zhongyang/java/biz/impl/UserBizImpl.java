package com.zhongyang.java.biz.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.UserBiz;
import com.zhongyang.java.dao.UserDao;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpUser;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpUserService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.sys.uitl.FormatUtils;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.Password;
import com.zhongyang.java.system.ShortMessage;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.uitl.DateUtil;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.GroupUserVO;
import com.zhongyang.java.vo.OrganizationUser;
import com.zhongyang.java.vo.OrganizationUserVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.Registered;
import com.zhongyang.java.vo.UmpAccountVo;
import com.zhongyang.java.vo.UserInfoVo;
import com.zhongyang.java.vo.UserVo;

@Service
public class UserBizImpl  extends UtilBiz implements UserBiz{
	
	private static Logger logger = Logger.getLogger(UserBizImpl.class);
	
	@Autowired
	private  UserService userService;
	
	@Autowired
	private UmpAccountService umpAccountService;
    
	@Autowired
	private UmpUserService umpUserService;
	
	@Autowired
	private FundRecordService fundRecordService;
	
	@Override
	public Message addUser(HttpServletRequest req,Registered registered) {
		String salt= Password.getSalt(null);
		String userPassword=Password.getPassphrase(salt, registered.getPassphrase());
		User user=new User();
		UUID uuid = UUID.randomUUID();
		user.setId(uuid.toString());
		user.setMobile(registered.getMobile());
		user.setReferralId(registered.getReferral_id());
		user.setSource(registered.getSource());
		user.setSalt(salt);
		user.setPassphrase(userPassword);
		user.setRegisterdate(new Date());
		int status=userService.addUser(user);
		if(status>0){
			Message message =new Message();
			message.setCode(1);
			message.setMessage("添加成功");
			req.getSession().setAttribute("zycfLoginUser", user);
			return message;
		}else{
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"添加用户失败");
		}
	}

	@Override
	public Message mobileRepeat(String mobile) {
		User user=new User();
		user.setMobile(mobile);
		int obj= userService.getUserNumByMobile(user);
		Message message= new Message();
		if(obj>0){
			message.setCode(1);
			message.setMessage("用户名已存在！");
		}else{
			message.setCode(0);
		}
		return message;
	}
	//用户查询
	   public PagerVO<UserVo> getUserList(PagerVO<User>pager) {
	       PagerVO<UserVo>listUser=new PagerVO<UserVo>();
	       try {
	           List<User>  listUserObj =  new ArrayList<User>();
	           List<UserVo>  listUserObjvo =  new ArrayList<UserVo>();
	           Page<User>page=new Page<User>();
	           if(pager.getStart()!=0){
	               page.setPageNo(pager.getStart());
	           }
	           else{
	               page.setPageNo(1);
	           }
	           if(pager.getLength()!=0){
	               page.setPageSize(pager.getLength());
	           }
	           else{
	               page.setPageSize(5);
	           }
	           Map<String, Object> params = page.getParams();
	           DESTextCipher cipher = DESTextCipher.getDesTextCipher();
	           int count = 0;
			   if(pager.getStartTime()!=-28800000){//如果输入的是注册时间区间，则查询的是注册数据列表，否则查询的是用户列表
				    params.put("startTime", new Date(pager.getStartTime()));
					page.setStartTime(new Date(pager.getStartTime())); 
					params.put("endTime", new Date(pager.getEndTime()));
					page.setEndTime(new Date(pager.getEndTime()));
					count = userService.getUserListCountZC(params);
			        listUserObj = userService.getUserListZC(page);
			   }else{
		           Pattern pattern = Pattern.compile("[0-9]*"); 
		           if(pager.getField()!=null&&pager.getValue()!=null){
		               String[] fileds = pager.getField().split(",");
		               String[] values = pager.getValue().split(",");
		               for (int i = 0; i < fileds.length; i++) {
		                   if("xxx".equals(values[i]))continue;
		                   Matcher isNum = pattern.matcher(values[i]);
		                   if(isNum.matches()){
								 page.getParams().put("mobile",cipher.encrypt(values[i]));
							} 
							else{
								page.getParams().put("name",values[i]);
							}
		                   page.getParams().put(fileds[i],values[i]);
		               }
		           }
		           if(pager.getSort()!=null){
		               page.getParams().put("sort",pager.getSort());
		           }
		           else{
		               page.getParams().put("sort","desc");
		           }
		           count = userService.getUserListCount(params);
		           listUserObj = userService.getUserList(page);
			   }
	          
	          
	           if(listUserObj.size() > 0){
	               for (int i = 0; i <listUserObj.size(); i++) {
	                   UserVo userVo = new UserVo();
	                   userVo.setId(listUserObj.get(i).getId());
	                   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                   if(null != listUserObj.get(i).getMobile()){//用户电话号码
	                       String cc = cipher.decrypt(listUserObj.get(i).getMobile());
	                       String stamb = cc.substring(0, 3)+"***"+cc.substring(cc.length()-4, cc.length());
	                       userVo.setMobile(stamb);
	                   }
	                   if(null != listUserObj.get(i).getReferralId()){//推荐人电话号码
	                	   String cc = cipher.decrypt(listUserObj.get(i).getReferralId());
	                       String stamb = cc.substring(0, 3)+"***"+cc.substring(cc.length()-4, cc.length());
	                       userVo.setReferralId(stamb);
	                   }
	                   userVo.setLoginname(listUserObj.get(i).getLoginname());
	                   userVo.setName(listUserObj.get(i).getName());
	                   userVo.setReferralRealm(listUserObj.get(i).getReferralRealm());
	                   userVo.setRegisterdate(df.format(listUserObj.get(i).getRegisterdate()));
	                   if(null != listUserObj.get(i).getLastlogindate()){
	                       userVo.setLastlogindate(df.format(listUserObj.get(i).getLastlogindate()));
	                   }else{
	                       userVo.setLastlogindate("");
	                   }
	                   userVo.setEnabled(listUserObj.get(i).getEnabled());//是否启动
	                   listUserObjvo.add(userVo);
	               }
	               listUser.setRecordsTotal(count);
	               listUser.setData(listUserObjvo);
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	           logger.info(e,e.fillInStackTrace());
	       }
	       return listUser;
	   }
	 
	   //查询用户详细信息
	   public UserVo getUserDetaile(String userId) {
	       UserVo userVo = new UserVo();
	       UserVo userVoObj = new UserVo();
	       try {
	           DESTextCipher cipher = DESTextCipher.getDesTextCipher();
	           userVoObj = userService.getUserDetaile(userId);
	           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	           if(null !=userVoObj){
	               if(null == userVoObj.getName()){//姓名
	                   userVo.setName("");
	               }else{
	                   userVo.setName(userVoObj.getName());
	               }
	               if(null == userVoObj.getIdnumber()){//身份证号
	                   userVo.setIdnumber("");
	               }else{
	                   String inNumber = cipher.decrypt(userVoObj.getIdnumber());
	                   userVo.setIdnumber(inNumber);
	               }
	               String mobile = cipher.decrypt(userVoObj.getMobile());
	               userVo.setMobile(mobile);//电话号码
	               if(null == userVoObj.getEmail()){//Email
	                   userVo.setEmail("");
	               }else{
	                   userVo.setEmail(userVoObj.getEmail());
	               }
	               if(null == userVoObj.getLoginname()){//用户登录名
	                   userVo.setLoginname("");
	               }else{
	                   userVo.setLoginname(userVoObj.getLoginname());
	               }
	               if(null == userVoObj.getAccount()){//银行卡号
	                   userVo.setAccount("");
	               }else{
	                   //String account = cipher.decrypt(userVoObj.getAccount());
	                   userVo.setAccount(userVoObj.getAccount());
	               }
	               if(null ==userVoObj.getReferralId()){
	                   userVo.setReferralId("");//推荐人
	               }else{
	                   userVoObj.getReferralId();
	                   UserVo userVoName = this.getReferraName(userVoObj.getReferralId());
	                   userVo.setReferralId(cipher.decrypt(userVoName.getMobile()));//推荐人
	               }
	               Date registerdate = df.parse(userVoObj.getRegisterdate());
	               userVo.setRegisterdate(df.format(registerdate));//注册时间
	               
	               setZsession(df.format(registerdate).toString(),"registerdate");//用户注册时间存放到缓存
	               if(null == userVoObj.getLastlogindate()){
	                   userVo.setLastlogindate("");
	               }else{
	            	   String cc = userVoObj.getLastlogindate();
	            	   Date d = df.parse(cc);
	            	   
	                   userVo.setLastlogindate(df.format(d));
	               }
	               userVo.setEnterprise(userVoObj.getEnterprise());//是否为员工
	           }
	           
	       } catch (Exception e) {
	           e.printStackTrace();
	           logger.info(e,e.fillInStackTrace());
	       }
	       return userVo;
	   }
	//查询推荐人的电话号码，姓名
	private UserVo getReferraName(String referralId) {
		UserVo userVoObj = new UserVo();
		try {
			userVoObj = userService.getUserDetaile(referralId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return userVoObj;
	}

	@Override
	public PagerVO<GroupUserVO> queryByUser(PagerVO<User> pager) {
		try {
			PagerVO<GroupUserVO>pagerVo=new PagerVO<GroupUserVO>();
			List<GroupUserVO> groupUserVO = new ArrayList<GroupUserVO>();
			Page<User>page=new Page<User>();
			if(pager.getStart()!=0){
				page.setPageNo(pager.getStart());
			}
			else{
				page.setPageNo(1);
			}
			if(pager.getLength()!=0){
				page.setPageSize(pager.getLength());
			}
			else{
				page.setPageSize(10);
			}
			page.setStartTime(new Date(pager.getStartTime()));
			page.setEndTime(new Date(pager.getEndTime()));
			
			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if("xxx".equals(values[i]))continue;
					page.getParams().put(fileds[i],values[i]);
				}
			}
			if(pager.getSort()!=null){
				page.getParams().put("sort",pager.getSort());
			}
			else{
				page.getParams().put("sort","desc");
			}
			Map<String, Object> params = page.getParams();
			if(page.getStartTime()!=null){
				params.put("startTime", page.getStartTime());
			}
			if(page.getEndTime()!=null){
				params.put("endTime",page.getEndTime());
			}
			int count = userService.queryCountByParams(params);
			int totalPage;
			if(count%page.getPageSize()==0){
				totalPage=count/page.getPageSize();
			}
			else{
				totalPage=count/page.getPageSize()+1;
			}
			pagerVo.setTotalPage(totalPage);
			pagerVo.setRecordsTotal(count);
			List<User> users = userService.queryByUser(page);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			if(users!=null){
				for (User user : users) {
					GroupUserVO guv=new GroupUserVO();
					guv.setId(user.getId());
					guv.setGroupid(user.getGroupid());
					guv.setLastlogindate(sdf.format(user.getLastlogindate()));
					guv.setLoginname(user.getLoginname());
					String mobile = cipher.decrypt(user.getMobile());
					guv.setMobile(mobile.substring(0,mobile.length() - (mobile.substring(3)).length())+
							"****" + mobile.substring(7));
					guv.setName(user.getName());
					guv.setRegisterdate(sdf.format(user.getRegisterdate()));
					guv.setReferralId(user.getReferralId());
					guv.setReferralRealm(user.getReferralRealm());
					User us=new User();
					us.setId(user.getReferralId());
					User referralUser = userService.getUserById(us);
					if(referralUser!=null){
						String referralMobile=cipher.decrypt(referralUser.getMobile());
						guv.setReferralMobile(referralMobile.substring(0,referralMobile.length() - (referralMobile.substring(3)).length())+
								"****" + referralMobile.substring(7));
					}
					groupUserVO.add(guv);
				}
			}
			pagerVo.setData(groupUserVO);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败，请重试");
		}
	}

	@Override
	public Message modifyUser(User user) {
		Message message=new Message();
		if(user==null||user.getId()==null){
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收异常");
		}
		try {
			userService.modifyUser(user);
			message.setCode(1);
			message.setMessage("修改用户成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改用户信息失败");
		}
		return message;

	}

	@Override
	public UserInfoVo queryUserByMobile(String loginName) {
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try {
			Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]*)?$");  
		    Matcher match=pattern.matcher(loginName);  
		    boolean flag = match.matches();
		    User user=new User();
		    if(flag){
		    	user.setMobile(cipher.encrypt(loginName));
		    }
		    else{
		    	user.setLoginname(loginName);				
		    }
			User queryUser = userService.queryByParams(user);
			if(queryUser==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "用户不存在");
			}
			UserInfoVo userInfoVo=new UserInfoVo();
			userInfoVo.setId(queryUser.getId());
			if(queryUser.getIdnumber()!=null){
				userInfoVo.setIdnumber(cipher.decrypt(queryUser.getIdnumber()));
			}
			userInfoVo.setMobile(cipher.decrypt(queryUser.getMobile()));
			Boolean idauthenticated = queryUser.getIdauthenticated();
			if(idauthenticated){
				userInfoVo.setAuthentication("是");
			}
			else{
				userInfoVo.setAuthentication("否");
			}
			userInfoVo.setName(queryUser.getName());
			return userInfoVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public PagerVO<GroupUserVO> likeQuery(PagerVO<User> pager) {
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try {
			PagerVO<GroupUserVO>pagerVo=new PagerVO<GroupUserVO>();
			List<GroupUserVO> groupUserVO = new ArrayList<GroupUserVO>();
			Page<User>page=new Page<User>();
			if(pager.getStart()!=0){
				page.setPageNo(pager.getStart());
			}
			else{
				page.setPageNo(1);
			}
			if(pager.getLength()!=0){
				page.setPageSize(pager.getLength());
			}
			else{
				page.setPageSize(10);
			}
			page.setStartTime(new Date(pager.getStartTime()));
			page.setEndTime(new Date(pager.getEndTime()));
			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if("xxx".equals(values[i]))continue;
					page.getParams().put(fileds[i],values[i]);
				}
			}
			if(pager.getSort()!=null){
				page.getParams().put("sort",pager.getSort());
			}
			else{
				page.getParams().put("sort","desc");
			}
			Map<String, Object> params = page.getParams();
			if(page.getStartTime()!=null){
				params.put("startTime", page.getStartTime());
			}
			if(page.getEndTime()!=null){
				params.put("endTime",page.getEndTime());
			}
			int count = userService.queryCountByParams(params);
			int totalPage;
			if(count%page.getPageSize()==0){
				totalPage=count/page.getPageSize();
			}
			else{
				totalPage=count/page.getPageSize()+1;
			}
			pagerVo.setTotalPage(totalPage);
			pagerVo.setRecordsTotal(count);
			List<User> users = userService.likeQuery(page);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(users!=null){
				for (User user : users) {
					GroupUserVO guv=new GroupUserVO();
					guv.setId(user.getId());
					guv.setGroupid(user.getGroupid());
					guv.setLastlogindate(sdf.format(user.getLastlogindate()));
					guv.setLoginname(user.getLoginname());
					String mobile = cipher.decrypt(user.getMobile());
					guv.setMobile(mobile.substring(0,mobile.length() - (mobile.substring(3)).length())+
							"****" + mobile.substring(7));
					guv.setName(user.getName());
					guv.setRegisterdate(sdf.format(user.getRegisterdate()));
					guv.setReferralId(user.getReferralId());
					guv.setReferralRealm(user.getReferralRealm());
					User us=new User();
					us.setId(user.getReferralId());
					User referralUser = userService.getUserById(us);
					if(referralUser!=null){
						String referralMobile=cipher.decrypt(referralUser.getMobile());
						guv.setReferralMobile(referralMobile.substring(0,referralMobile.length() - (referralMobile.substring(3)).length())+
								"****" + referralMobile.substring(7));
					}
					groupUserVO.add(guv);
				}
			}
			pagerVo.setData(groupUserVO);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败，请重试");
		}
	}

	//用户信息修改
	public Message updateUserInfo(UserVo userVo) {
		Message message = new Message();
			try {
				ApplicationBean appen = new ApplicationBean();
				String orderId = appen.orderId();
				SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
				String ss = (String) getZsessionObject("registerdate");//更新的时候根据注册时间判断传入mer_cust_id；就平台的和新平台的不一样
				String str = "2016-06-21 23:59:59";
				Date dates = formatter.parse(ss);
				Date date =  formatter.parse(str);
				Map<String, String> map = new HashMap<String, String>();
				String uuid = userVo.getId();
				uuid = uuid.replace("-", "");
				if(date.after(dates)){//根据UBXXX查询对应的唯一号(32位)
					UmpUser umpUser = this.getOldUserIdByAccountName(userVo.getId());
					map.put("mer_cust_id", umpUser.getUserId());
			    }else{
			    	map.put("mer_cust_id", uuid);
			    }
				map.put("mer_cust_name", userVo.getName());
				map.put("identity_type", "IDENTITY_CARD");
				map.put("identity_code", userVo.getIdnumber());
				map.put("mobile_id",userVo.getMobile());
				map.put("order_id", orderId);
				removeZsessionObject("registerdate");
				logger.info("map"+map);
				UmpaySignature umpaySignature = new UmpaySignature("mer_register_person", map);
				Map<String, String> entity = umpaySignature.getSignature();
				if ("0000".equals(entity.get("ret_code"))) {
					int i =0;
					    DESTextCipher cipher = DESTextCipher.getDesTextCipher();
					    userVo.setMobile(cipher.encrypt(userVo.getMobile()));
						i = this.updateUserInfos(userVo);//修改标本地用户信息
						if(i != 0){
						message.setCode(0000);
					    }
				}else{
					    message.setCode(1111);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e,e.fillInStackTrace());
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改用户电话号码失败，请重试");
			} 
		return message;
	}
    @Transactional
	private int updateUserInfos(UserVo userVo) {
		int i = 0;
		try {
			userVo.setEnabled(userVo.getEnabled());//是否是员工
			i = userService.updateUserInfos(userVo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return i;
	}

	@Override
	@Transactional
	public Message updateUserStatus(UserVo userVo) {
		Message message = new Message();
		int i =0;
		    i = this.updateUserInfos(userVo);
		if(i != 0){
			message.setMessage("0000");
		}else{
			message.setMessage("1111");
		}
		return message;
	}

	@Override
	public UmpAccountVo getUserUmpInfo(String userId) {
		UmpAccountVo umpVo = new UmpAccountVo();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
			UmpAccount umpAccount = new UmpAccount();
			umpAccount = this.getUmpAccount(userId);
			Map<String, String> map = new HashMap<String, String>();
			String uuid = umpAccount.getUserId();
			uuid = uuid.replace("-", "");
			map.put("user_id", umpAccount.getAccountName());
			map.put("is_find_account", "01");
			map.put("is_select_agreement", "1");

			UmpaySignature umpaySignature = new UmpaySignature("user_search", map);
			Map<String, String> entity = null;
			entity = umpaySignature.getSignature();
			for(String key : entity.keySet()){
					umpVo.setAccountName(entity.get("plat_user_id"));//第三方账号Id
					BigDecimal strAmount = new BigDecimal(entity.get("balance"));
					BigDecimal dj = new BigDecimal(100.00);
					BigDecimal amount = strAmount.divide(dj).setScale(2, BigDecimal.ROUND_UP);
					umpVo.setAvailableAmount(amount.toString());//用户账户余额
					umpVo.setContactMobile(entity.get("contact_mobile"));//用户电话号码
					umpVo.setAccountState(entity.get("account_state"));//账户状态1-正常 2-挂失 3-冻结 4-锁定 9-销户
					umpVo.setCardId(entity.get("card_id"));//银行卡号
					umpVo.setCustName(entity.get("cust_name"));//用户姓名
					umpVo.setGateId(entity.get("gate_id"));//银行编号
					umpVo.setUserid(userId);
					umpVo.setAccountId(entity.get("account_id"));//用户umpAccountId
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return umpVo;
	}

	private UmpUser getOldUserIdByAccountName(String userId) {
		try {
			return umpUserService.getOldUserIdByAccountName(userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询用户旧userId失败"+userId);
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败，请重试");
		}
	}

	private UmpAccount getUmpAccount(String userId) {
		UmpAccount umpAccount = new UmpAccount();
		try {
			umpAccount.setUserId(userId);
			umpAccount = umpAccountService.getUmpAccountByUserId(umpAccount);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
		}
		return umpAccount;
	}
	public PagerVO<OrganizationUser> queryByParams(PagerVO<User> pager){
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		try {
			PagerVO<OrganizationUser>pagerVo=new PagerVO<OrganizationUser>();
			Page<User>page=new Page<User>();
			if(pager.getStart()!=0){
				page.setPageNo(pager.getStart());
			}
			else{
				page.setPageNo(1);
			}
			if(pager.getLength()!=0){
				page.setPageSize(pager.getLength());
			}
			else{
				page.setPageSize(10);
			}
			page.setStartTime(new Date(pager.getStartTime()));
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if(pager.getEndTime()==0){
				page.setEndTime(sdf.parse("2130-01-01"));
			}
			else{
				page.setEndTime(new Date(pager.getEndTime())); 
			}
			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if("xxx".equals(values[i]))continue;
					page.getParams().put(fileds[i],values[i]);
				}
			}
			if(pager.getSort()!=null){
				page.getParams().put("sort",pager.getSort());
			}
			else{
				page.getParams().put("sort","desc");
			}
			int count = userService.queryCountByParams(page.getParams());
			int totalPage;
			if(count%page.getPageSize()==0){
				totalPage=count/page.getPageSize();
			}
			else{
				totalPage=count/page.getPageSize()+1;
			}
			pagerVo.setTotalPage(totalPage);
			pagerVo.setRecordsTotal(count);
			List<OrganizationUser> ous = userService.queryByParams(page);
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("startTime", page.getStartTime());
			map.put("endTime", page.getEndTime());
			for (OrganizationUser organizationUser : ous) {
				BigDecimal toTalamount=new BigDecimal(0);
				organizationUser.setMobile(cipher.decrypt(organizationUser.getMobile()));
				map.put("referralId",organizationUser.getId() );
				User user=new User();
				user.setReferralId(organizationUser.getId());
				List<User> users = userService.queryUserListByParams(user);
				organizationUser.setTotalNumber(users.size());
				List<OrganizationUserVo> userInvests = userService.queryUsersInvestByParams(map);
				for (OrganizationUserVo organizationUserVo : userInvests) {
					if(organizationUserVo.getInvests()!=null){
						for(Invest invest : organizationUserVo.getInvests()){
							toTalamount=BigDecimalAlgorithm.add(toTalamount, invest.getAmount());
						}
					}
				}	
				organizationUser.setInviteAmount(toTalamount);
			}
			pagerVo.setData(ous);
			return pagerVo;
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败，请重试");
		}
	}

	@Override
	public List<String> getUserMobiles(String startTime, String endTiem) {
		List<User> userMobiles = new ArrayList<>();
		List<String> userMobilesVo = new ArrayList<String>();
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("startTime", FormatUtils.dateFormat(startTime+" 00:00:00"));
		map.put("endTime", FormatUtils.dateFormat(endTiem+" 23:59:59"));
		String[] str=new String[10];
		try {
			userMobiles = userService.getUserMobiles(map);
			if(userMobiles.isEmpty()){
			}else{
				DESTextCipher cipher = DESTextCipher.getDesTextCipher();
                    for(int i = 0;i< userMobiles.size();i++){
                    	String mobile = cipher.decrypt(userMobiles.get(i).getMobile());
                    	userMobilesVo.add(mobile);
                    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userMobilesVo;
	}

	@Override
	public String sensMessageToUser(String messsge,String sendTime,String mobiles) {

		Map<String,Object> mobileAndMsg = new HashMap<>();
		if("".equals(mobiles)){
			return "1111";
		}
		
		mobileAndMsg.put("mobiles", mobiles);
		mobileAndMsg.put("info", messsge);
		if(sendTime !="" && sendTime !=null){
			mobileAndMsg.put("stime", sendTime);
		}
		String codeMsg=ShortMessage.getShortMessage().getSendMsg(mobileAndMsg);
		String code= null;
		if(codeMsg.length()==18){
			code= "0000";
		}
		return code;
	}

	@Override
	public Message updateInviteUserBatch(String newReferralId, String oldReferralId,String newReferralRealm) {
		Message message = new Message();
		try{
			userService.updateInviteUserBatch(newReferralId, oldReferralId,newReferralRealm);
			message.setCode(0000);
			message.setMessage("批量变更推荐人信息成功！");
			return message;
		}catch(Exception e){
			e.printStackTrace();
			message.setCode(0001);
			message.setMessage("批量变更推荐人信息异常！");
		}
		return message;
	}
	//查询当天投资/回款/充值/提现的用户的基本信息
	@Override
	public Page<User> getTodayInvestUserInfo(Page<User> page) {
		try {
			List<User> list = userService.getTodayInvestUserInfo(page);
			page.setResults(list);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Page<Map<String, Object>> getUserInvest(Page<Map<String, Object>> page) {
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		List<Map<String,Object>> list =null;
		try{
			if (page.getParams().get("startTime") == null || "".equals(page.getParams().get("startTime"))) {
				page.getParams().put("startTime", DateUtil.getFirstDayOfMonth());
			}
			if (page.getParams().get("endTime") == null || "".equals(page.getParams().get("endTime"))) {
				page.getParams().put("endTime", DateUtil.getLastDayOfMonth());
			}
			if("AMOUNT".equals(page.getParams().get("type").toString())){
				 list  = userService.getUserInvestInfo(page);
			}else{
				 list  = userService.getUserInvestCount(page);
			}
			 for(Map<String,Object> s :list){
				 s.put("mobile",cipher.decrypt(s.get("mobile").toString()));
				 s.put("inviteMobile",cipher.decrypt(s.get("inviteMobile").toString()));
			 }
			page.setResults(list);
			return page;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void modifyAllUsers() {
		User us=new User();
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			List<User> users = userService.getUser(us);
			for (User user : users) {
				if(user.getIdnumber()!=null&&user.getBirthDate()==null){
					String idNum=cipher.decrypt(user.getIdnumber());
					String birthDate=idNum.substring(6, 14);
					user.setBirthDate(sdf.parse(birthDate));
					userService.modifyUser(user);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> queryBirthDateUsers(String statTime,String endTiem) {
		List<String> mobiles=new ArrayList<String>();
		Page<User>page=new Page<User>();
		page.setPageSize(Integer.MAX_VALUE);
		try {
			if("".equals(statTime)){
				page.getParams().put("startTime","01-01");
			}else{
				page.getParams().put("startTime", statTime.substring(5, 10));
			}
			if(endTiem!=null&&!"".equals(endTiem)){
				page.getParams().put("endTime", endTiem.substring(5, 10));
			}
			
			List<User> users = userService.queryUserByParams(page);
			
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			for (User user : users) {
				String mobile=cipher.decrypt(user.getMobile());
				mobiles.add(mobile);
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		return mobiles;
	}
	@Autowired
	private UserDao userDao;
	
	
	@Override
	public List<String> queryInvestUsers(String statTime, String endTiem) {
		List<String> mobiles=new ArrayList<String>();
		Page<User>page=new Page<User>();
		page.setPageSize(Integer.MAX_VALUE);
		try {
			if("".equals(statTime)){
				page.getParams().put("startTime","1970-01-01");
			}else{
				page.getParams().put("startTime", statTime);
			}
			if(endTiem!=null&&!"".equals(endTiem)){
				page.getParams().put("endTime", endTiem);
			}
			List<User> users = userDao.selectFund();			
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			for (User user : users) {
				String mobile=cipher.decrypt(user.getMobile());
				mobiles.add(mobile);
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace());
		}
		return mobiles;
	}
	
	/*@Override
	public ResponseEntity<byte[]>  mobileRepeatCCC(HttpServletRequest request) {
		List<String[]> headNames = new ArrayList<String[]>();
		headNames.add(new String[] {"身份证号","电话号码","姓名"});
		List<String[]> fieldNames = new ArrayList<String[]>();
		fieldNames.add(new String[] {"idnumber","mobile","name"});
		File file = new File("C:/Users/Administrator/Desktop/统计/变更记录/cc/需后台领导补充完善(1)dd.xls");
		  List<UserVo> usersss = new ArrayList<>();
		  ResponseEntity<byte[]> responseEntity = null;
		try {
			String[][] result =result = getData(file, 0);;
			List<Object> list = new ArrayList<>();
		       int rowLength = result.length;
		       for(int i=0;i<rowLength;i++) {
		    	   DD d = new DD();
		           for(int j=0;j<result[i].length;j++) {
		        	   d.setLogInName(result[i][0]);
		           }
		           list.add(d);
		       }
		       System.out.println(list.size());
		     
			   String ccid = "F2683775-060B-441B-AEBD-41DF9806C0FC"; 
		       List<UserVo> users = userService.queryUserByParamslistCC(ccid);
		       for(int c=0;c<users.size();c++) {
		    	   UserVo vo = new UserVo();
		    	   DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		    	   try {
		    		   if(null !=users.get(c).getIdnumber()){
		    			   vo.setIdnumber(cipher.decrypt(users.get(c).getIdnumber()));
		    		   }else{
		    			   vo.setIdnumber("没有认证");
		    		   }
		    		   if(null !=users.get(c).getName()){
		    				vo.setName(users.get(c).getName());
		    		   }else{
		    			   vo.setName("没有实名认证");
		    		   }
		    		   if(null !=users.get(c).getMobile()){
		    			   vo.setMobile(cipher.decrypt(users.get(c).getMobile()));
		    		   }else{
		    			   vo.setMobile("未绑定电话号码");
		    		   }
		    	
					
					String name="平台资金记录统计";
					responseEntity = UploadExcelUtil.upload(request, headNames, fieldNames, usersss, name);
				} catch (GeneralSecurityException | IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	   usersss.add(vo);  
	           }
		      System.out.println(usersss.size());
			Message message= new Message();
			
				message.setCode(1);
				message.setMessage("用户名已存在！");

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseEntity;
	
		
	       
	}*/
	public static void main(String[] args) throws Exception {
	      
	       }
/**
 * @param file 读取数据的源Excel
 * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
 * @return 读出的Excel中数据的内容
 * @throws FileNotFoundException
 * @throws IOException
 */
public static String[][] getData(File file, int ignoreRows)
       throws FileNotFoundException, IOException {
   List<String[]> result = new ArrayList<String[]>();
   int rowSize = 0;
   BufferedInputStream in = new BufferedInputStream(new FileInputStream(
          file));
   // 打开HSSFWorkbook
   POIFSFileSystem fs = new POIFSFileSystem(in);
   HSSFWorkbook wb = new HSSFWorkbook(fs);
   HSSFCell cell = null;
   for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
       HSSFSheet st = wb.getSheetAt(sheetIndex);
       // 第一行为标题，不取
       for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
          HSSFRow row = st.getRow(rowIndex);
          if (row == null) {
              continue;
          }
          int tempRowSize = row.getLastCellNum() + 1;
          if (tempRowSize > rowSize) {
              rowSize = tempRowSize;
          }
          String[] values = new String[rowSize];
          Arrays.fill(values, "");
          boolean hasValue = false;
          for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
              String value = "";
              cell = row.getCell(columnIndex);
              if (cell != null) {
                 // 注意：一定要设成这个，否则可能会出现乱码
//                 cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                 switch (cell.getCellType()) {
                 case HSSFCell.CELL_TYPE_STRING:
                     value = cell.getStringCellValue();
                     break;
                 case HSSFCell.CELL_TYPE_NUMERIC:
                     if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        if (date != null) {
                            value = new SimpleDateFormat("yyyy-MM-dd")
                                   .format(date);
                        } else {
                            value = "";
                        }
                     } else {
                        value = new DecimalFormat("0").format(cell
                               .getNumericCellValue());
                     }
                     break;
                 case HSSFCell.CELL_TYPE_FORMULA:
                     // 导入时如果为公式生成的数据则无值
                     if (!cell.getStringCellValue().equals("")) {
                        value = cell.getStringCellValue();
                     } else {
                        value = cell.getNumericCellValue() + "";
                     }
                     break;
                 case HSSFCell.CELL_TYPE_BLANK:
                     break;
                 case HSSFCell.CELL_TYPE_ERROR:
                     value = "";
                     break;
                 case HSSFCell.CELL_TYPE_BOOLEAN:
                     value = (cell.getBooleanCellValue() == true ? "Y"
                            : "N");
                     break;
                 default:
                     value = "";
                 }
              }
              if (columnIndex == 0 && value.trim().equals("")) {
                 break;
              }
              values[columnIndex] = rightTrim(value);
              hasValue = true;
          }

          if (hasValue) {
              result.add(values);
          }
       }
   }
   in.close();
   String[][] returnArray = new String[result.size()][rowSize];
   for (int i = 0; i < returnArray.length; i++) {
       returnArray[i] = (String[]) result.get(i);
   }
   return returnArray;
}

/**
 * 去掉字符串右边的空格
 * @param str 要处理的字符串
 * @return 处理后的字符串
 */
 public static String rightTrim(String str) {
   if (str == null) {
       return "";
   }
   int length = str.length();
   for (int i = length - 1; i >= 0; i--) {
       if (str.charAt(i) != 0x20) {
          break;
       }
       length--;
   }
   return str.substring(0, length);
}
}
