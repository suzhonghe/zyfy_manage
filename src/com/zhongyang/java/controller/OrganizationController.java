package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.OrganizationBiz;
import com.zhongyang.java.pojo.Organization;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;

@Controller
public class OrganizationController extends BaseController{
	
	@Autowired
	private OrganizationBiz organizationBiz;
	
	@FireAuthority(authorities=Authorities.ORGADD)
	@RequestMapping(value="/addOrganization")
	public @ResponseBody Message addOrganization(Organization organization){
		return organizationBiz.addOrganization(organization);
	}
	
	@FireAuthority(authorities=Authorities.ORGMODIFY)
	@RequestMapping(value="/modifyOrganization")
	public @ResponseBody Message modifyOrganization(Organization organization){
		return organizationBiz.modifyOrganization(organization);
	}
	
	@RequestMapping(value="/queryOrganizationByParams")
	public @ResponseBody Organization queryOrganizationByParams(Organization organization){
		return organizationBiz.queryOrganizationByParams(organization);
	}
	
	@FireAuthority(authorities=Authorities.ORGMANAGE)
	@RequestMapping(value="/queryAllOrganizations")
	public @ResponseBody Organization queryAllOrganizations(){
		return organizationBiz.queryAllOrganizations();
	}
}
