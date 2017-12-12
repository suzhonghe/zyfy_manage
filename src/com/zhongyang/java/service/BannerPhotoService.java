package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.BannerPhoto;
/**
 * 
* @Title: BannerPhotoService.java 
* @Package com.zhongyang.java.dao 
* @Description:Banner业务接口
* @author 苏忠贺   
* @date 2016年1月18日 上午11:19:16 
* @version V1.0
 */
public interface BannerPhotoService {
	/**
	 * 
	* @Title: addPhoto 
	* @Description:添加Banner 
	* @return int    返回类型 
	* @throws
	 */
	public int addPhoto(BannerPhoto bannerPhoto)throws Exception;
	/**
	 * 
	* @Title: queryByParams 
	* @Description:根据条件查询banner 
	* @return BannerPhoto    返回类型 
	* @throws
	 */
	public List<BannerPhoto> queryByParams(BannerPhoto bannerPhoto)throws Exception;
	/**
	 * 
	* @Title: deleteByParams 
	* @Description:根据条件删除banner
	* @return void    返回类型 
	* @throws
	 */
	public void deleteByParams(BannerPhoto bannerPhoto)throws Exception;
	
	/**
	 * 
	* @Title: batchUpdateBannerPhotos 
	* @Description:批量修改
	* @return int    返回类型 
	* @throws
	 */
	public int batchUpdateBannerPhotos(List<BannerPhoto> bannerPhotos)throws Exception;
	/**
	 * 
	* @Title: modifyByParams 
	* @Description:根据参数查询banner
	* @return int    返回类型 
	* @throws
	 */
	public int modifyByParams(BannerPhoto bannerPhoto)throws Exception;
}
