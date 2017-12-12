package com.zhongyang.java.vo;

import java.util.List;

import com.zhongyang.java.pojo.BannerPhoto;

public class BannerVO {
	
	private BannerPhotoVo bannerPhotoVo;
	
	private List<BannerPhoto>bannerPhotos;

	public BannerPhotoVo getBannerPhotoVo() {
		return bannerPhotoVo;
	}

	public void setBannerPhotoVo(BannerPhotoVo bannerPhotoVo) {
		this.bannerPhotoVo = bannerPhotoVo;
	}

	public List<BannerPhoto> getBannerPhotos() {
		return bannerPhotos;
	}

	public void setBannerPhotos(List<BannerPhoto> bannerPhotos) {
		this.bannerPhotos = bannerPhotos;
	}
}
