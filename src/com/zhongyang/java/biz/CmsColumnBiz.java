package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.CmsColumn;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.CmsColumnVO;

/**
 * 
* @Title: CmsColumnBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:栏目biz
* @author 苏忠贺   
* @date 2016年3月2日 下午4:25:03 
* @version V1.0
 */
public interface CmsColumnBiz {
	
	public Message addCmsColumn(CmsColumn cmsColumn);
	
	public Message modifyByParams(CmsColumn cmsColumn);
	
	public Message deleteByParams(CmsColumn cmsColumn);
	
	public List<CmsColumnVO> queryByParams(CmsColumn cmsColumn);
	
}
