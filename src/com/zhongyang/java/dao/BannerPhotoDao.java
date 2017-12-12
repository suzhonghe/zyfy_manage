package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.BannerPhoto;
/**
 * 
* @Title: BannerPhotoDao.java 
* @Package com.zhongyang.java.dao 
* @Description:BannerDAO 
* @author 苏忠贺   
* @date 2016年1月18日 上午11:19:16 
* @version V1.0
 */
public interface BannerPhotoDao {
	/**
	 * 
	* @Title: insertPhoto 
	* @Description:添加一天banner 
	* @return int    返回类型 
	* @throws
	 */
	public int insertPhoto(BannerPhoto bannerPhoto)throws Exception;

	/**
	 * 
	* @Title: selectByParams 
	* @Description:根据条件查询某一条banner 
	* @return BannerPhoto    返回类型 
	* @throws
	 */
	public List<BannerPhoto> selectByParams(BannerPhoto bannerPhoto)throws Exception;
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
	public int batchUpdateBannerPhotos(List<BannerPhoto> list)throws Exception;
	/**
	 * 
	* @Title: updateByParams 
	* @Description:根据参数修改banner
	* @return int    返回类型 
	* @throws
	 */
	public int updateByParams(BannerPhoto bannerPhoto)throws Exception;
}
