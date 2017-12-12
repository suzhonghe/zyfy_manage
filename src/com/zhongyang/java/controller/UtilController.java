package com.zhongyang.java.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.system.Util;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.VerificationCode;

@Controller
public class UtilController extends BaseController {
	
	@RequestMapping(value = "/imgValalidate",method=RequestMethod.GET)
	public void getVerificationImg(HttpServletRequest req, HttpServletResponse resp) {
		VerificationCode vc = Util.getVerificationCode();
		req.getSession().setAttribute("code", vc.getCode());    
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		try {
			ServletOutputStream sos = resp.getOutputStream();
			ImageIO.write(vc.getBuffImg(), "jpeg", sos);
			sos.flush();
			sos.close();
		} catch (IOException e) {
			throw new UException(e);
		}
	}
	
	@RequestMapping(value = "/codeValalidate",method=RequestMethod.POST)
	public  @ResponseBody Map<String,Object> getVerificationCode(HttpServletRequest req) {
		Map<String,Object> map= new HashMap<String,Object>();
		Object obj=req.getSession().getAttribute("code");
		map.put("code", obj);
		return map;
	}
	
	
}
