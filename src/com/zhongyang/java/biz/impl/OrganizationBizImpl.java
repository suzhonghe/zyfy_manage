package com.zhongyang.java.biz.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.OrganizationBiz;
import com.zhongyang.java.pojo.Organization;
import com.zhongyang.java.service.OrganizationService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
@Service
public class OrganizationBizImpl implements OrganizationBiz{
	
	private static Logger logger = Logger.getLogger(OrganizationBizImpl.class);
	
	@Autowired
	private OrganizationService organizationService;

	@Override
	public Message addOrganization(Organization organization) {
		Message message=new Message();
		try {
			organization.setParantOrgId(organization.getId());
			organization.setId(UUID.randomUUID().toString().toUpperCase());
			organization.setCreateTime(new Date());
			organization.setLevel(organization.getLevel()+1);
			organization.setDel(false);
			int count = organizationService.addOrganization(organization);
			if(count>0){
				message.setCode(1);
				message.setMessage("添加成功");
			}
			else{
				message.setCode(0);
				message.setMessage("添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "机构数据添加异常，请尝试");
		}
		return message;
	}

	@Override
	public Message modifyOrganization(Organization organization) {
		Message message=new Message();
		try {
			if(organization==null||organization.getId()==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收异常");
			}
			int status = organizationService.modifyOrganization(organization);
			if(status>0){
				message.setCode(1);
				message.setMessage("修改机构信息成功");
			}
			else{
				message.setCode(0);
				message.setMessage("修改机构信息失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "更新机构信息异常，请尝试");
		}
		return message;
	}

	@Override
	public Organization queryOrganizationByParams(Organization organization){
		try {
			return organizationService.queryOrganizationByParams(organization);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询异常");
		}
	}

	@Override
	public Organization queryAllOrganizations() {
		try {
			Organization organization=new Organization();
			organization.setLevel(0);
			List<Organization> result = organizationService.queryAllOrganizations(organization);
			return result.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
