package com.zhongyang.java.biz;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.HomePageBanner;
import com.zhongyang.java.system.Message;

/**
 * 
* @Title: HomePageBannerBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:首页Banner业务处理接口
* @author 苏忠贺   
* @date 2016年1月19日 下午3:32:00 
* @version V1.0
 */
public interface HomePageBannerBiz {
	
	public Message addHomePageBanner(List<HomePageBanner> homePageBanners);
	
	public List<HomePageBanner> queryHomePageBanner(String type);

	
}
