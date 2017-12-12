package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.Organization;

/**
 * 
* @Title: OrganizationService.java 
* @Package com.zhongyang.java.service 
* @Description:机构业务接口
* @author 苏忠贺   
* @date 2016年3月9日 下午4:34:54 
* @version V1.0
 */
public interface OrganizationService {

	public int addOrganization(Organization organization)throws Exception;
	
	public int modifyOrganization(Organization organization)throws Exception;
	
	public Organization queryOrganizationByParams(Organization organization)throws Exception;
	
	public List<Organization> queryAllOrganizations(Organization organization)throws Exception;
}
