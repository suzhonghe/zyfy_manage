package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.CmsColumn;
/**
 * 
* @Title: CmsColumnService.java 
* @Package com.zhongyang.java.service 
* @Description:栏目service
* @author 苏忠贺   
* @date 2016年3月2日 下午4:25:23 
* @version V1.0
 */
public interface CmsColumnService {
	
	public int addCmsColumn(CmsColumn cmsColumn)throws Exception;
	
	public int modifyByParams(CmsColumn cmsColumn)throws Exception;
	
	public int deleteByParams(CmsColumn cmsColumn)throws Exception;
	
	public List<CmsColumn> queryByParams(CmsColumn cmsColumn)throws Exception;
	
}
