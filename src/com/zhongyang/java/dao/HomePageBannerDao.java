package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.HomePageBanner;
/**
 * 
* @Title: HomePageBannerDao.java 
* @Package com.zhongyang.java.dao 
* @Description:首页bannerDAO 
* @author 苏忠贺   
* @date 2016年1月18日 上午11:20:21 
* @version V1.0
 */
public interface HomePageBannerDao {
	
	public int insertPhoto(HomePageBanner homePageBanner)throws Exception;
	
	public List<HomePageBanner> sellectAllPhotos(HomePageBanner homePageBanner)throws Exception;
	
	public HomePageBanner selectByParams(HomePageBanner homePageBanner)throws Exception;
	
	public void deleteByParams(HomePageBanner homePageBanner)throws Exception;
}
