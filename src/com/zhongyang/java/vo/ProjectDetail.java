package com.zhongyang.java.vo;

import java.util.ArrayList;
import java.util.List;

import com.zhongyang.java.pojo.Project;

public class ProjectDetail {
	
	private Project project;
	
	private String legalPersonPhotoUrl;//法人身份证
	
	private String enterpriseInfoPhotoUrl;//企业信息
	
	private String assetsPhotoUrl;//合同文件
	
	private String contractPhotoUrl;//资产信息
	
	private String othersPhotoUrl;//其他资料
	
	private List<ProductVo>productVos=new ArrayList<ProductVo>();
	
	private List<CorporationUserVo>corporationUserVo=new ArrayList<CorporationUserVo>();

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getLegalPersonPhotoUrl() {
		return legalPersonPhotoUrl;
	}

	public void setLegalPersonPhotoUrl(String legalPersonPhotoUrl) {
		this.legalPersonPhotoUrl = legalPersonPhotoUrl;
	}

	public String getEnterpriseInfoPhotoUrl() {
		return enterpriseInfoPhotoUrl;
	}

	public void setEnterpriseInfoPhotoUrl(String enterpriseInfoPhotoUrl) {
		this.enterpriseInfoPhotoUrl = enterpriseInfoPhotoUrl;
	}

	public String getAssetsPhotoUrl() {
		return assetsPhotoUrl;
	}

	public void setAssetsPhotoUrl(String assetsPhotoUrl) {
		this.assetsPhotoUrl = assetsPhotoUrl;
	}

	public String getContractPhotoUrl() {
		return contractPhotoUrl;
	}

	public void setContractPhotoUrl(String contractPhotoUrl) {
		this.contractPhotoUrl = contractPhotoUrl;
	}

	public String getOthersPhotoUrl() {
		return othersPhotoUrl;
	}

	public void setOthersPhotoUrl(String othersPhotoUrl) {
		this.othersPhotoUrl = othersPhotoUrl;
	}

	public List<ProductVo> getProductVos() {
		return productVos;
	}

	public void setProductVos(List<ProductVo> productVos) {
		this.productVos = productVos;
	}

	public List<CorporationUserVo> getCorporationUserVo() {
		return corporationUserVo;
	}

	public void setCorporationUserVo(List<CorporationUserVo> corporationUserVo) {
		this.corporationUserVo = corporationUserVo;
	}
}
