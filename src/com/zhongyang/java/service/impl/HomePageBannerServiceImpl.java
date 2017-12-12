package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.HomePageBannerDao;
import com.zhongyang.java.pojo.HomePageBanner;
import com.zhongyang.java.service.HomePageBannerService;
/**
 * 
* @Title: HomePageBannerServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:业务接口实现 
* @author 苏忠贺   
* @date 2016年1月18日 下午1:24:35 
* @version V1.0
 */
@Service
public class HomePageBannerServiceImpl implements HomePageBannerService {
	
	@Autowired
	private HomePageBannerDao HomePageBannerDao;
	
	@Override
	public int addPhoto(HomePageBanner homePageBanner) throws Exception {
		return HomePageBannerDao.insertPhoto(homePageBanner);
	}

	@Override
	public List<HomePageBanner> queryAllPhotos(HomePageBanner homePageBanner) throws Exception {
		return HomePageBannerDao.sellectAllPhotos(homePageBanner);
	}

	@Override
	public HomePageBanner queryByParams(HomePageBanner homePageBanner) throws Exception {
		return HomePageBannerDao.selectByParams(homePageBanner);
	}

	@Override
	public void deleteByParams(HomePageBanner homePageBanner) throws Exception {
		HomePageBannerDao.deleteByParams(homePageBanner);
	}

}
