package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.OrganizationDao;
import com.zhongyang.java.pojo.Organization;
import com.zhongyang.java.service.OrganizationService;
/**
 * 
* @Title: OrganizationServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:机构service实现
* @author 苏忠贺   
* @date 2016年3月9日 下午4:36:54 
* @version V1.0
 */
@Service
public class OrganizationServiceImpl implements OrganizationService{
	
	@Autowired
	private OrganizationDao organizationDao;

	@Override
	public int addOrganization(Organization organization) throws Exception {
		return organizationDao.insertOrganization(organization);
	}

	@Override
	public int modifyOrganization(Organization organization) throws Exception {
		return organizationDao.updateOrganization(organization);
	}

	@Override
	public Organization queryOrganizationByParams(Organization organization)throws Exception{
		return organizationDao.selectOrganizationByParams(organization);
	}

	@Override
	public List<Organization> queryAllOrganizations(Organization organization) throws Exception {
		return organizationDao.selectAllOrganizations(organization);
	}
}
