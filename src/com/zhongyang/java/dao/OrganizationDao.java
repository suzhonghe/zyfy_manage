package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.Organization;

/**
 * 
* @Title: OrganizationDao.java 
* @Package com.zhongyang.java.dao 
* @Description:机构DAO
* @author 苏忠贺   
* @date 2016年3月9日 下午4:20:27 
* @version V1.0
 */
public interface OrganizationDao {

	public int insertOrganization(Organization organization)throws Exception;
	
	public int updateOrganization(Organization organization)throws Exception;
	
	public Organization selectOrganizationByParams(Organization organization)throws Exception;
	
	public List<Organization> selectAllOrganizations(Organization organization)throws Exception;
}
