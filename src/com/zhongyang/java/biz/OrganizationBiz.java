package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.Organization;
import com.zhongyang.java.system.Message;

/**
 * 
* @Title: OrganizationBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:机构biz
* @author 苏忠贺   
* @date 2016年3月9日 下午4:39:21 
* @version V1.0
 */
public interface OrganizationBiz {

	public Message addOrganization(Organization organization);
	
	public Message modifyOrganization(Organization organization);
	
	public Organization queryOrganizationByParams(Organization Organization);
	
	public Organization queryAllOrganizations();
}
