package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.HomePageBanner;
/**
 * 
* @Title: HomePageBannerService.java 
* @Package com.zhongyang.java.dao 
* @Description:首页banner业务接口
* @author 苏忠贺   
* @date 2016年1月18日 上午11:20:21 
* @version V1.0
 */
public interface HomePageBannerService {
	
	public int addPhoto(HomePageBanner homePageBanner)throws Exception;
	
	public List<HomePageBanner> queryAllPhotos(HomePageBanner homePageBanner)throws Exception;
	
	public HomePageBanner queryByParams(HomePageBanner homePageBanner)throws Exception;
	
	public void deleteByParams(HomePageBanner homePageBanner)throws Exception;
}
