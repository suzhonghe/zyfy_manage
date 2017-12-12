package com.zhongyang.java.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.zhongyang.java.pojo.BannerPhoto;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.BannerVO;

public interface BannerBiz {
	
	public void uploadBannerPhoto(HttpServletRequest request,MultipartFile file,String type,HttpServletResponse response);
	
	public Message deleteBannerPhoto(String accessAddress);
	
	public List<BannerPhoto> queryBannerPhotos(String type);
	
	public Message saveBannerPhoto(BannerVO bannerVo);
	
	public Message modifyBannerPhoto(BannerPhoto bannerPhoto,String type);
	
}
