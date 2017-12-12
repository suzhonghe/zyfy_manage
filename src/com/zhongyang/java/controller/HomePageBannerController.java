package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.HomePageBannerBiz;
import com.zhongyang.java.pojo.HomePageBanner;
import com.zhongyang.java.system.Message;

/**
 * 
* @Title: HomePageBannerController.java 
* @Package com.zhongyang.java.controller 
* @Description:Banner控制器 
* @author 苏忠贺   
* @date 2016年1月19日 下午6:34:06 
* @version V1.0
 */
@Controller
public class HomePageBannerController extends BaseController{
	
	@Autowired
	private HomePageBannerBiz homePageBannerBiz;
	
	/**
	 * 
	* @Title: addHomePageBanner 
	* @Description:添加首页Banner 
	* @return Message    返回类型 
	* @throws
	 */
	@RequestMapping(value="/addHomePageBanner", method = RequestMethod.POST)
	public @ResponseBody Message addHomePageBanner(List<HomePageBanner> homePageBanners){
		return homePageBannerBiz.addHomePageBanner(homePageBanners);
	}
	
	/**
	 * 
	* @Title: queryHomePageBanner 
	* @Description:查询首页Banner
	* @return List<HomePageBanner>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryHomePageBanner", method = RequestMethod.POST)
	public @ResponseBody List<HomePageBanner> queryHomePageBanner(String type){
		return homePageBannerBiz.queryHomePageBanner(type);
	}
}
