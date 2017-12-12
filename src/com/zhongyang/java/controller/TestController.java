package com.zhongyang.java.controller;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.TestBiz;
import com.zhongyang.java.dao.UserDao;
import com.zhongyang.java.pojo.Test;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.sys.uitl.FormatUtils;
import com.zhongyang.java.system.DESTextCipher;

@Controller
public class TestController extends BaseController{
	
	@Autowired
	private TestBiz testBiz;
	
	@Autowired
	private UserDao userDao;
		
	@RequestMapping(value="/getException")
	public @ResponseBody List<Test> getException() {
		return testBiz.getException();
	}
	
	@RequestMapping(value="/getMobiles")
	public void getMobiles() throws GeneralSecurityException {
		List<User> users = userDao.getUser(null);
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		for (User user : users) {
			user.setPmo(cipher.decrypt(user.getMobile()));
		}
		userDao.updateUsers(users);
	}
	
	@RequestMapping(value="/upload")
	public @ResponseBody String upload(MultipartFile items_pic,String type,String name) throws IllegalStateException, IOException {
		String pictureFile_name =  items_pic.getOriginalFilename();
		//新文件名称
		String newFileName = UUID.randomUUID().toString()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
		
		//上传图片
		File uploadPic = new java.io.File("F:/upload/"+newFileName);
		
		if(!uploadPic.exists()){
			uploadPic.mkdirs();
		}
		//向磁盘写文件
		items_pic.transferTo(uploadPic);
//		Message message=new Message();
//		message.setMessage("/upload/"+newFileName);
//		String img="<img src="+"'/upload/"+newFileName+ "'>"+"</img>";
		String script="<script>parent.callback('/upload/"+newFileName+"','"+name+"')</script>";
		return script;
	}
	public static void main(String[] args) throws GeneralSecurityException, ReqDataException, RetDataException {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, 1);
		System.out.println(FormatUtils.millisDateFormat(calendar.getTime()));
		
		
		//		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
//		System.out.println(cipher.encrypt("13717408862"));
//		System.out.println(cipher.decrypt("fU3WtnnVbJbtL1yzW8EH7w=="));
		// 拼接投标中请求更新标的参数
//		LoanModify loanModifySecond = new LoanModify();
//
//		loanModifySecond.setProject_id("20160620651427484228062161433882");
//		loanModifySecond.setProject_state("2");
//		loanModifySecond.setChange_type("01");
//		Map<String, String> umpModifyLoanSecond = UmpLoan.umpModifyLoan(loanModifySecond);
		
	}
	
}