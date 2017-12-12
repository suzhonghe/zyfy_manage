package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.CmsColumn;

/**
 * 
* @Title: CmsColumnDao.java 
* @Package com.zhongyang.java.dao 
* @Description:栏目DAO
* @author 苏忠贺   
* @date 2016年3月2日 下午3:52:08 
* @version V1.0
 */
public interface CmsColumnDao {
	
	public int insertCmsColumn(CmsColumn cmsColumn)throws Exception;
	
	public int updateByParams(CmsColumn cmsColumn)throws Exception;
	
	public int deleteByParams(CmsColumn cmsColumn)throws Exception;
	
	public List<CmsColumn> selectByParams(CmsColumn cmsColumn)throws Exception;
	
}
