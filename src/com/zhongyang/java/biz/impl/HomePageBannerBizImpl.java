package com.zhongyang.java.biz.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.HomePageBannerBiz;
import com.zhongyang.java.pojo.HomePageBanner;
import com.zhongyang.java.service.HomePageBannerService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;

/**
 * 
* @Title: HomePageBannerBizImpl.java 
* @Package com.zhongyang.java.biz.impl 
* @Description:首页banner业务实现
* @author 苏忠贺   
* @date 2016年1月19日 下午3:37:04 
* @version V1.0
 */
@Service
public class HomePageBannerBizImpl implements HomePageBannerBiz{
	
	@Autowired
	private HomePageBannerService homePageBannerService;
	
	@Transactional
	public Message addHomePageBanner(List<HomePageBanner> homePageBanners) {
		Message message=new Message();
		try {
			HomePageBanner hpb=new HomePageBanner();
			homePageBannerService.deleteByParams(hpb);
			for (HomePageBanner homePageBanner : homePageBanners) {
				hpb.setId(UUID.randomUUID().toString().toUpperCase());
				hpb.setAccessAddress(homePageBanner.getAccessAddress());
				hpb.setJumpAddress(homePageBanner.getJumpAddress());
				hpb.setSerialNumber(hpb.getSerialNumber());
				hpb.setPhotoName(homePageBanner.getPhotoName());
				Date date = new Date();
				Timestamp timeStamp = new Timestamp(date.getTime());
				hpb.setTime(timeStamp);
				hpb.setType(homePageBanner.getType());
				homePageBannerService.addPhoto(hpb);
			}
			message.setCode(1);
			message.setMessage("成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "首页banner添加失败");
		}
	}

	@Override
	public List<HomePageBanner> queryHomePageBanner(String type) {
		try {
			HomePageBanner homePageBanner=new HomePageBanner();
			homePageBanner.setPhotoName(type);
			return homePageBannerService.queryAllPhotos(homePageBanner);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "首页banner查询失败");
		}
	}


}
