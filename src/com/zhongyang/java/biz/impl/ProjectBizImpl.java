package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.ProjectBiz;
import com.zhongyang.java.pojo.CorporationUser;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.pojo.ProofPhoto;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.CorporationUserService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.ProductService;
import com.zhongyang.java.service.ProjectService;
import com.zhongyang.java.service.ProofPhotoService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.vo.CorporationUserVo;
import com.zhongyang.java.vo.LoanContent;
import com.zhongyang.java.vo.LoanVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ProductVo;
import com.zhongyang.java.vo.ProjectDetail;
import com.zhongyang.java.vo.ProjectDetailVo;
import com.zhongyang.java.vo.ProjectVo;
import com.zhongyang.java.vo.loan.LoanStatus;

@Service
public class ProjectBizImpl implements ProjectBiz {
	
	private static Logger logger=Logger.getLogger(ProjectBizImpl.class);
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProofPhotoService proofPhotoService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CorporationUserService corporationUserService;
	
	@Autowired
	private LoanService loanService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.ProjectBiz#addOneProject(com.zhongyang.java.vo.
	 * LoanVo, java.util.List)
	 */
	@Override
	@Transactional
	public Message addOneProject(LoanVo loanVo) {
		Message message = new Message();
		Date date = new Date();
		try {			
			logger.info(sdf.format(date)+"添加项目");
			if (loanVo == null || loanVo.getProject() == null) {
				logger.info("未获得页面信息");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到项目信息，请重试");
			}
			loanVo.getProject().setId(UUID.randomUUID().toString().toUpperCase());//ID
			loanVo.getProject().setPublishedAmount(new BigDecimal(0));//已发标金额
			if(loanVo.getProject().getAmount()==null)throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
//			loanVo.getProject().setAmount(BigDecimalAlgorithm.mul(loanVo.getProject().getAmount(), new BigDecimal(10000)));//项目总额
			loanVo.getProject().setSurplusAmount(loanVo.getProject().getAmount());//剩余可发标金额
			loanVo.getProject().setAutoable(0);//不自动标的
			loanVo.getProject().setStatus(0);//启用状态
			loanVo.getProject().setPureRequest(0);//不是仅有借款申请
			loanVo.getProject().setHidden(1);//对所有人可见
			
			if(loanVo.getUser()!=null&&loanVo.getUser().getId()!=null){
				loanVo.getProject().setUserId(loanVo.getUser().getId());//用户ID
			}
			if(loanVo.getProject().getProductId()==null)throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到产品ID参数，请重试");
			
			Product queryProduct = productService.queryProductById(loanVo.getProject().getProductId());
			if(queryProduct==null)throw new UException(SystemEnum.UNKNOW_EXCEPTION, "产品信息获取失败，请重试");
			if(queryProduct.getMaxInvestAmount()!=null){
				loanVo.getProject().setMaxAmount(queryProduct.getMaxInvestAmount());//最大投资额
			}
			if(queryProduct.getMinInvestAmount()!=null){
				loanVo.getProject().setMinAmount(queryProduct.getMinInvestAmount());//最小投资额
			}
			loanVo.getProject().setStepAmount(new BigDecimal(100));//投资增量
			
			Timestamp timeStamp = new Timestamp(date.getTime());
			loanVo.getProject().setTimeSubmit(timeStamp);//项目创建时间
			// 添加项目记录
			projectService.addOneProject(loanVo.getProject());
			logger.info("添加项目信息到数据库");
			// 图片处理
			String[] legalPersonPhoto = loanVo.getLegalPersonPhoto();
			if(legalPersonPhoto.length!=0){
				photoSave("法人身份证",legalPersonPhoto,loanVo.getProject().getId());
			}
			String[] enterpriseInfoPhoto = loanVo.getEnterpriseInfoPhoto();
			if(enterpriseInfoPhoto.length!=0){
				photoSave("企业信息",enterpriseInfoPhoto,loanVo.getProject().getId());
			}
			String[] assetsPhoto = loanVo.getAssetsPhoto();
			if(assetsPhoto.length!=0){
				photoSave("合同文件",assetsPhoto,loanVo.getProject().getId());
			}
			String[] contractPhoto = loanVo.getContractPhoto();
			if(contractPhoto.length!=0){
				photoSave("资产信息",contractPhoto,loanVo.getProject().getId());
			}
			String[] othersPhoto = loanVo.getOthersPhoto();
			if(othersPhoto.length!=0){
				photoSave("其他资料",othersPhoto,loanVo.getProject().getId());
			}
			message.setCode(1);
			message.setMessage("项目添加成功！！！");
			logger.info("添加项目成功");
			return message;
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.info("添加项目异常，事务回滚");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.ProjectBiz#queryUserByLoginName(java.lang.String)
	 */
	@Override
	public User queryUserByLoginName(String loginName) {
		DESTextCipher cipher = DESTextCipher.getDesTextCipher();
		User user=null;
		try {
			Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]*)?$");  
		    Matcher match=pattern.matcher(loginName);  
		    boolean flag = match.matches();
		    User quseryUser=new User();
		    if(flag){
		    	quseryUser.setMobile(cipher.encrypt(loginName));
		    }
		    else{
		    	quseryUser.setLoginname(loginName);				
		    }
		    user = userService.queryByParams(quseryUser);
			if (user != null) {
				if(user.getIdnumber()!=null){
					//解密身份证号
					String idNumber = cipher.decrypt(user.getIdnumber());
					user.setIdnumber(idNumber);
				}
				if(user.getMobile()!=null){
					//解密手机号
					String tel = cipher.decrypt(user.getMobile());
					user.setMobile(tel);
				}				
			} 
			else {
				throw new UException(SystemEnum.USER_NOT_EXISTS, "用户不存在");
			}
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.USER_NOT_EXISTS, e.getMessage());
		}
	}

	@Override
	public ProjectDetail queryProjectById(String id) {
		ProjectDetail projectDetail=new ProjectDetail();
		Date date=new Date();
		try {
			logger.info(sdf.format(date)+"根据ID查询项目详细信息");
			Page<Product>page=new Page<Product>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			List<Product> products = productService.queryAllProduct(page);
			if(products==null)throw new UException(SystemEnum.USER_NOT_EXISTS, "数据查询失败");
			for (Product product : products) {
				ProductVo productVo=new ProductVo();
				if(product.getName()==null||product.getId()==null)throw new UException(SystemEnum.USER_NOT_EXISTS, "数据查询失败");
				productVo.setId(product.getId());
				productVo.setProductName(product.getName());
				projectDetail.getProductVos().add(productVo);
			}
			Project project = projectService.queryProjectById(id);
			List<CorporationUser> corporationUsers = corporationUserService.queryAllCorporationUser(null);
			List<CorporationUserVo>corporationUserVos=new ArrayList<CorporationUserVo>();
			for (CorporationUser corporationUser : corporationUsers) {
				CorporationUserVo cuv=new CorporationUserVo();
				cuv.setUserId(corporationUser.getUserId());
				cuv.setName(corporationUser.getName());
				cuv.setShortname(corporationUser.getShortname());
				cuv.setType(corporationUser.getType());
				corporationUserVos.add(cuv);
			}
			projectDetail.setCorporationUserVo(corporationUserVos);
			//根据项目ID查询的到对应的图片
			logger.info("根据项目ID查询对应的证明图片信息");
			ProofPhoto proof=new ProofPhoto();
			proof.setProjectId(id);
			List<ProofPhoto> ptoofPhotos = proofPhotoService.queryByParams(proof);
			projectDetail.setProject(project);
			for (ProofPhoto proofPhoto : ptoofPhotos) {
				//拼接身份证图片存储地址url
				if("法人身份证".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getLegalPersonPhotoUrl()!=null){
						projectDetail.setLegalPersonPhotoUrl(projectDetail.getLegalPersonPhotoUrl()+","+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setLegalPersonPhotoUrl(proofPhoto.getUrlPath());
					}
				}
				//拼接企业信心图片存储地址url
				if("企业信息".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getEnterpriseInfoPhotoUrl()!=null){
						projectDetail.setEnterpriseInfoPhotoUrl(projectDetail.getEnterpriseInfoPhotoUrl()+","+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setEnterpriseInfoPhotoUrl(proofPhoto.getUrlPath());
					}
				}
				//拼接合同文件存储地址url
				if("合同文件".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getAssetsPhotoUrl()!=null){
						projectDetail.setAssetsPhotoUrl(projectDetail.getAssetsPhotoUrl()+","+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setAssetsPhotoUrl(proofPhoto.getUrlPath());
					}
				}
				//拼接资产信息图片存储地址url
				if("资产信息".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getContractPhotoUrl()!=null){
						projectDetail.setContractPhotoUrl(projectDetail.getContractPhotoUrl()+","+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setContractPhotoUrl(proofPhoto.getUrlPath());
					}
				}
				//拼接其他资料存储地址url
				if("其他资料".equals(proofPhoto.getPhotoName())){
					if(projectDetail.getOthersPhotoUrl()!=null){
						projectDetail.setOthersPhotoUrl(projectDetail.getOthersPhotoUrl()+","+proofPhoto.getUrlPath());
					}
					else{
						projectDetail.setOthersPhotoUrl(proofPhoto.getUrlPath());
					}
				}
			}
			logger.info("返回项目信息");
			return projectDetail; 
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	@Override
	public PagerVO<ProjectVo> queryAllProjects(PagerVO<ProjectVo>pager) {
		Date date=new Date();
		try {
			logger.info(sdf.format(date)+"按条件查询项目信息");
			PagerVO<ProjectVo>pagerVo=new PagerVO<ProjectVo>();
			List<ProjectVo> projectVo = new ArrayList<ProjectVo>();
			Page<ProjectVo>page=new Page<ProjectVo>();
			if(pager.getStart()!=0){
				page.setPageNo(pager.getStart());
			}
			else{
				page.setPageNo(1);
			}
			if(pager.getLength()!=0){
				page.setPageSize(pager.getLength());
			}
			else{
				page.setPageSize(10);
			}
			
			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				if("".equals(pager.getField())){
					page.getParams().put("timeSubmit","TIMESUBMIT");
				}
				else{
					if(!pager.getField().contains("timeSubmit")){
						page.getParams().put("timeSubmit","TIMESUBMIT");
					}
				}
				for (int i = 0; i < fileds.length; i++) {
					if("".equals(values[i]))continue;
					page.getParams().put(fileds[i],values[i]);
				}
				if(pager.getSort()!=null){
					page.getParams().put("sort",pager.getSort());
				}
				else{
					page.getParams().put("sort","desc");
				}
			}
			Map<String,Object>map=new HashMap<String,Object>();
			if(pager.getStartTime()!=0){
				page.setStartTime(new Date(pager.getStartTime()));
				map.put("startTime", page.getStartTime());
			}
			if(pager.getEndTime()!=0){
				page.setEndTime(new Date(pager.getEndTime()));
				map.put("endTime", page.getEndTime());
			}
			int count = projectService.queryCount(map);
			logger.info("查询结果数量："+count+"条");
			pagerVo.setRecordsTotal(count);
			int totalPage=count%page.getPageSize();
			if(totalPage==0){
				pagerVo.setTotalPage(count/page.getPageSize());
			}
			else{
				pagerVo.setTotalPage(count/page.getPageSize()+1);
			}
			System.out.println(count/page.getPageSize()+"================");
			List<Project> queryAllProjects = projectService.queryAllProjects(page);
			logger.info("封装查询结果");
			if(queryAllProjects!=null){
				for (Project project : queryAllProjects) {
					ProjectVo pv = new ProjectVo();
					pv.setId(project.getId());
					pv.setAmount(project.getAmount());//项目金额
					pv.setGuaranteeRealm(project.getGuaranteeRealm());//担保实体
					pv.setMethod(project.getMethod());//偿还方式
					pv.setMonths(project.getMonths());//借款期限 月
					pv.setSurplusAmount(project.getSurplusAmount());//剩余可发标金额
					pv.setTitle(project.getTitle());//项目名称
					pv.setStatus(project.getStatus());//状态
					pv.setDays(project.getDays());//借款期限 日
					pv.setTimeSubmit(sdf.format(project.getTimeSubmit()));//项目创建时间
					User user = new User();
					user.setId(project.getLoanUserId());
					User us = userService.getUserById(user);
					pv.setUserName(us.getName());//借款人姓名
					projectVo.add(pv);
				}
			}	
			pagerVo.setData(projectVo);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询项目异常");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public Message modifyProjectStatus(Project project) {
		Message message=new Message();
		Date date=new Date();
		try {
			logger.info(sdf.format(date)+"修改项目状态");
			if(project==null){
				logger.info("未获得项目信息");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未接收到数据");
			}
			Project queryProject = projectService.queryProjectById(project.getId());
			if(queryProject==null){
				logger.info("根据ID未获得项目项目信息");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询数据出现问题");
			}
			if(0==queryProject.getStatus()){
				project.setStatus(1);
			}
			else {
				project.setStatus(0);
			}
			projectService.modifyProject(project);
			message.setCode(1);
			message.setMessage("修改成功");
			logger.info("成功修改项目状态");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("修改项目状态异常");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改项目状态失败，请重试");
		}
		
	}

	@Override
	@Transactional
	public Message modifyProject(LoanVo loanVo) {
		Message message=new Message();
		Date date=new Date();
		try {
			logger.info(sdf.format(date)+"修改项目信息");
			Project queryProject = projectService.queryProjectById(loanVo.getProject().getId());
			if(queryProject.getPublishedAmount().doubleValue()!=0){
				message.setCode(0);
				message.setMessage("已做过标的申请不允许修改");
				logger.info("已做过标的申请不允许修改");
				return message;
			}
			loanVo.getProject().setSurplusAmount(loanVo.getProject().getAmount());
			
			String[] legalPersonPhoto = loanVo.getLegalPersonPhoto();
			photoSave("法人身份证",legalPersonPhoto,loanVo.getProject().getId());
			
			String[] enterpriseInfoPhoto = loanVo.getEnterpriseInfoPhoto();
			photoSave("企业信息",enterpriseInfoPhoto,loanVo.getProject().getId());
	
			String[] assetsPhoto = loanVo.getAssetsPhoto();
			photoSave("合同文件",assetsPhoto,loanVo.getProject().getId());
			
			String[] contractPhoto = loanVo.getContractPhoto();
			photoSave("资产信息",contractPhoto,loanVo.getProject().getId());
			
			String[] othersPhoto = loanVo.getOthersPhoto();
			photoSave("其他资料",othersPhoto,loanVo.getProject().getId());
			
			projectService.modifyProject(loanVo.getProject());
			message.setCode(1);
			message.setMessage("修改项目成功");
			logger.info("修改项目成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("修改项目异常");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改项目失败，请重试");
		}
	}
	public void photoSave(String photoNme,String[] pic, String projectId) throws Exception {
		ProofPhoto proof=new ProofPhoto();
		proof.setProjectId(projectId);
		proof.setPhotoName(photoNme);
		List<ProofPhoto> result = proofPhotoService.queryByParams(proof);
		if(result!=null){
			proofPhotoService.deleteByParams(proof);
		}
		
		List<ProofPhoto>proofPhotos=new ArrayList<ProofPhoto>();
		if(pic.length!=0){
			for (String urlAddress : pic) {
	        	ProofPhoto proofPhoto = new ProofPhoto();
				proofPhoto.setId(UUID.randomUUID().toString().toUpperCase());
				proofPhoto.setPhotoName(photoNme);//图片名称
				proofPhoto.setProjectId(projectId);//对应项目ID
				proofPhoto.setSubmitTime(new Date());//创建时间
				proofPhoto.setUrlPath(urlAddress);
				proofPhotos.add(proofPhoto);
			}
	        proofPhotoService.addProofPhoto(proofPhotos);
			
		}
        
	}

	@Override
	public ProjectDetailVo queryProjectDetailById(String projectId,LoanStatus status) {
		ProjectDetailVo pdv=new ProjectDetailVo();
		ProjectDetail projectDetail = queryProjectById(projectId);
		pdv.setProjectDetail(projectDetail);
		pdv.setTotalAmount(new BigDecimal(0));
		try {
			List<LoanContent>loanContents=new ArrayList<LoanContent>();
			Loan lo=new Loan();
			lo.setProjectId(projectId);
			if(status!=null&&!status.equals("")){
				lo.setStatus(status);
			}
			
			List<Loan> loans = loanService.queryLoanByProjectId(lo);
			for (Loan loan : loans) {
				LoanContent lc=new LoanContent();
				lc.setAmount(loan.getAmount());
				lc.setBidAmount(loan.getBidAmount());
				lc.setMethod(loan.getMethod().getKey());
				lc.setMonths(loan.getMonths());
				lc.setSerial(loan.getSerial());
				lc.setStatus(loan.getStatus().getKey());
				lc.setTimeOpen(loan.getTimeOpen());
				lc.setTimeSettled(loan.getTimeSettled());
				lc.setTitle(loan.getTitle());
				loanContents.add(lc);
				pdv.setTotalAmount(BigDecimalAlgorithm.add(pdv.getTotalAmount(), loan.getAmount()));
			}
			pdv.setLoans(loanContents);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdv;
	}
}
