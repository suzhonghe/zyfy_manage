package com.zhongyang.java.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zhongyang.java.biz.BannerBiz;
import com.zhongyang.java.pojo.BannerPhoto;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.BannerVO;

@Controller
public class BannerController extends BaseController{
	
	@Autowired
	private BannerBiz bannerBiz;
	
	/**
	 * 
	* @Title: uploadFile 
	* @Description: 上传banner图片控制器 
	* @return Message    返回类型 
	* @throws
	 */
	//@FireAuthority(authorities=Authorities.BANNERUPLOAD)
	@RequestMapping(value ="/uploadBannerPhoto")
	public void uploadFile(HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file,String type,HttpServletResponse response) {
		bannerBiz.uploadBannerPhoto(request, file, type,response);
	}
	
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value = "/deleteBannerPhoto", method = RequestMethod.POST)
	public @ResponseBody Message uploadFile(String pathAddress) {
		return bannerBiz.deleteBannerPhoto(pathAddress);
	}
	
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value="/queryBannerPhotos")
	public @ResponseBody List<BannerPhoto> queryBannerListPhotos(String type){
		return bannerBiz.queryBannerPhotos(type);
	}
	
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value="/saveBannerPhoto")
	public @ResponseBody Message saveBannerPhoto(@RequestBody BannerVO bannerVo){
		return bannerBiz.saveBannerPhoto(bannerVo);
	}
	
	@FireAuthority(authorities=Authorities.BANNERMANAGE)
	@RequestMapping(value="/modifyBannerPhoto")
	public @ResponseBody Message modifyBannerPhoto(BannerPhoto bannerPhoto,String type){
		return bannerBiz.modifyBannerPhoto(bannerPhoto,type);
	}

}
