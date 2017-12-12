package com.zhongyang.java.biz.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.biz.LoanBiz;
import com.zhongyang.java.biz.UmpSearchBiz;
import com.zhongyang.java.pojo.CorporationUser;
import com.zhongyang.java.pojo.FailedLoanOrder;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.Invest;
import com.zhongyang.java.pojo.Loan;
import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.pojo.Project;
import com.zhongyang.java.pojo.ProofPhoto;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.pojo.UmpTender;
import com.zhongyang.java.pojo.UmpTenderTransferRecord;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.pojo.UserFund;
import com.zhongyang.java.service.CorporationUserService;
import com.zhongyang.java.service.FailedLoanOrderService;
import com.zhongyang.java.service.FundRecordService;
import com.zhongyang.java.service.InvestService;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.ProductService;
import com.zhongyang.java.service.ProjectService;
import com.zhongyang.java.service.ProofPhotoService;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.service.UmpTenderService;
import com.zhongyang.java.service.UmpTenderTransferRecordService;
import com.zhongyang.java.service.UserFundService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.system.DESTextCipher;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.ShortMessage;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.SystemPro;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.config.ApplicationBean;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.BigDecimalAlgorithm;
import com.zhongyang.java.system.umpay.UmpLoan;
import com.zhongyang.java.system.umpay.UmpaySignature;
import com.zhongyang.java.vo.CorporationUserVo;
import com.zhongyang.java.vo.LoanDetail;
import com.zhongyang.java.vo.LoanInvestVo;
import com.zhongyang.java.vo.LoanListVo;
import com.zhongyang.java.vo.LoanModify;
import com.zhongyang.java.vo.LoanPublished;
import com.zhongyang.java.vo.LoanVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ProductVo;
import com.zhongyang.java.vo.ProjectDetail;
import com.zhongyang.java.vo.UmpBidTransferVo;
import com.zhongyang.java.vo.UmpLoanInfo;
import com.zhongyang.java.vo.fund.FundRecordOperation;
import com.zhongyang.java.vo.fund.FundRecordStatus;
import com.zhongyang.java.vo.fund.FundRecordType;
import com.zhongyang.java.vo.fund.OrderTime;
import com.zhongyang.java.vo.loan.InvestStatus;
import com.zhongyang.java.vo.loan.LoanStatus;
import com.zhongyang.java.vo.loan.SelectLoanResultVo;

/**
 * 
 * @Title: LoanBizImpl.java
 * @Package com.zhongyang.java.biz.impl
 * @Description:标的业务处理实现类
 * @author 苏忠贺
 * @date 2015年12月4日 上午9:09:55
 * @version V1.0
 */
@Service
public class LoanBizImpl implements LoanBiz {

	static {
		Map<String, Object> sysMap = SystemPro.getProperties();
		ip = (String) sysMap.get("ZYCFMANAGER_IP");
	}

	private static String ip;

	private static Logger logger = Logger.getLogger(LoanBizImpl.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private LoanService loanService;

	@Autowired
	private UmpTenderService umpTenderService;

	@Autowired

	private UmpAccountService umpAccountService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	InvestService investService;

	@Autowired
	private FailedLoanOrderService failedLoanOrderService;

	@Autowired
	private FundRecordService fundRecordService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProofPhotoService proofPhotoService;

	@Autowired
	private UmpTenderTransferRecordService uttrService;

	@Autowired
	private CorporationUserService corporationUserService;

	@Autowired
	private UmpSearchBiz umpSearch;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.LoanBiz#addOneLoan(com.zhongyang.java.pojo.Loan)
	 */
	@Override
	@Transactional
	public Message addOneLoan(LoanVo loanVo) {
		Message message = new Message();
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "标的申请");
			if (loanVo == null || loanVo.getLoan() == null || loanVo.getProject() == null) {
				logger.info("标的申请错误：未获得标的信息");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到标的信息，请重试");
			}
			Project queryProject = projectService.queryProjectById(loanVo.getProject().getId());
			if (BigDecimalAlgorithm.subt(queryProject.getSurplusAmount(), loanVo.getLoan().getAmount())
					.doubleValue() < 0) {
				message.setCode(0);
				message.setMessage("标的金额超出剩余可发标金额");
				logger.info("标的申请错误：标的金额超出剩余可发标金额");
				return message;
			}
			if (loanVo.getLoan().getTitle() == null || "".equals(loanVo.getLoan().getTitle()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到标的名称，请重试");
			if (loanVo.getLoan().getSerial() == null || "".equals(loanVo.getLoan().getSerial()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到标的唯一号，请重试");
			if (loanVo.getLoan().getLoanUserId() == null || "".equals(loanVo.getLoan().getLoanUserId()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到借款人ID，请重试");
			if (loanVo.getLoan().getMonths() == null || "".equals(loanVo.getLoan().getMonths()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到借款期限，请重试");
			if (loanVo.getLoan().getMethod() == null || "".equals(loanVo.getLoan().getMethod()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到偿还方式，请重试");
			if (loanVo.getLoan().getMinAmount() == null || "".equals(loanVo.getLoan().getMinAmount()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到起投金额，请重试");
			if (loanVo.getLoan().getMaxAmount() == null || "".equals(loanVo.getLoan().getMaxAmount()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到最大投资金额，请重试");
			if (loanVo.getLoan().getStepAmount() == null || "".equals(loanVo.getLoan().getStepAmount()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到投资增量，请重试");
			if (loanVo.getLoan().getAmount() == null || "".equals(loanVo.getLoan().getAmount()))
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得标的金额，请重试");
			// loanVo.getLoan().setAmount(BigDecimalAlgorithm.mul(loanVo.getLoan().getAmount(),
			// new BigDecimal(10000)));
			loanVo.getLoan().setId(UUID.randomUUID().toString().toUpperCase());// id
			loanVo.getLoan().setAutoSplitted(0);// 不自动发标
			loanVo.getLoan().setBidNumber(0);// 投标人数
			loanVo.getLoan().setBidAmount(new BigDecimal(0));
			loanVo.getLoan().setRewarded(0);
			loanVo.getLoan().setStatus(LoanStatus.INITIATED);// 标的状态
			if (loanVo.getProject().getId() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到项目ID，请重试");
			loanVo.getLoan().setProjectId(loanVo.getProject().getId());// 项目Id
			if (loanVo.getLoan().getProductId() != null) {
				Product product = productService.queryProductById(loanVo.getLoan().getProductId());
				loanVo.getLoan().setProductName(product.getName());
			}

			// 相关费率不能为空
			if (loanVo.getLoan().getRate() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
			if (loanVo.getLoan().getInvestInterestFee() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
			if (loanVo.getLoan().getLoanGuaranteeFee() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
			if (loanVo.getLoan().getLoanInterestFee() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
			if (loanVo.getLoan().getLoanManageFee() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
			if (loanVo.getLoan().getLoanRiskFee() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
			if (loanVo.getLoan().getLoanServiceFee() == null)
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到利率信息，请重试");
			loanVo.getLoan().setTimeSubmit(new Date());
			logger.info("添加标的信息到数据库");
			loanService.addOneLoan(loanVo.getLoan());

			Project project = new Project();
			project.setId(loanVo.getProject().getId());

			// 修改剩余可发标金额 当前数值页面要传递过来
			project.setSurplusAmount(
					BigDecimalAlgorithm.sub(queryProject.getSurplusAmount(), loanVo.getLoan().getAmount()).setScale(2,
							BigDecimal.ROUND_DOWN));
			// 修改已发标金额 、当前数值页面要传递过来
			project.setPublishedAmount(
					BigDecimalAlgorithm.add(queryProject.getPublishedAmount(), loanVo.getLoan().getAmount()));
			// 修改项目字段
			logger.info("修改项目剩余可发标金额以及已发标金额");
			projectService.modifyProject(project);

			message.setCode(1);
			message.setMessage("标的申请成功");
			logger.info("标的申请状态：标的申请成功");
			return message;

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("标的申请异常");
			logger.info("异常信息:" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zhongyang.java.biz.LoanBiz#queryLoanById(java.lang.String)
	 */
	@Override
	public LoanDetail queryLoanById(String id) {
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "根据ID查询标的详细信息");
			LoanDetail loanDetail = new LoanDetail();
			Loan loan = loanService.queryLoanById(id);
			// loan.setAmount(BigDecimalAlgorithm.div(loan.getAmount(), new
			// BigDecimal(10000)));
			loanDetail.setLoan(loan);
			logger.info("查询标的相关的项目信息");
			Product queryProduct = productService.queryProductById(loan.getProductId());
			loanDetail.setProduct(queryProduct);

			ProjectDetail projectDetail = new ProjectDetail();
			Page<Product> page = new Page<Product>();
			page.setPageSize(Integer.MAX_VALUE);
			page.getParams().put("timeCreate", "TIMECREATE");
			page.getParams().put("sort", "desc");
			List<Product> products = productService.queryAllProduct(page);
			List<ProductVo> pvs = new ArrayList<ProductVo>();
			for (Product product : products) {
				ProductVo pv = new ProductVo();
				pv.setId(product.getId());
				pv.setProductName(product.getName());
				pvs.add(pv);
			}
			projectDetail.setProductVos(pvs);
			List<CorporationUser> corporationUsers = corporationUserService.queryAllCorporationUser(null);
			List<CorporationUserVo> corporationUserVos = new ArrayList<CorporationUserVo>();
			for (CorporationUser corporationUser : corporationUsers) {
				CorporationUserVo cuv = new CorporationUserVo();
				cuv.setUserId(corporationUser.getUserId());
				cuv.setName(corporationUser.getName());
				cuv.setShortname(corporationUser.getShortname());
				cuv.setType(corporationUser.getType());
				corporationUserVos.add(cuv);
			}
			projectDetail.setCorporationUserVo(corporationUserVos);
			Project project = projectService.queryProjectById(loan.getProjectId());
			// 根据项目ID查询的到对应的图片
			logger.info("查询项目相关的正面材料图片");
			ProofPhoto proof = new ProofPhoto();
			proof.setProjectId(loan.getProjectId());
			List<ProofPhoto> ptoofPhotos = proofPhotoService.queryByParams(proof);
			projectDetail.setProject(project);
			for (ProofPhoto proofPhoto : ptoofPhotos) {
				// 拼接身份证图片存储地址url
				if ("法人身份证".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getLegalPersonPhotoUrl() != null) {
						projectDetail.setLegalPersonPhotoUrl(
								projectDetail.getLegalPersonPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setLegalPersonPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
				// 拼接企业信心图片存储地址url
				if ("企业信息".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getEnterpriseInfoPhotoUrl() != null) {
						projectDetail.setEnterpriseInfoPhotoUrl(
								projectDetail.getEnterpriseInfoPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setEnterpriseInfoPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
				// 拼接合同文件存储地址url
				if ("合同文件".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getAssetsPhotoUrl() != null) {
						projectDetail.setAssetsPhotoUrl(
								projectDetail.getAssetsPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setAssetsPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
				// 拼接资产信息图片存储地址url
				if ("资产信息".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getContractPhotoUrl() != null) {
						projectDetail.setContractPhotoUrl(
								projectDetail.getContractPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setContractPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
				// 拼接其他资料存储地址url
				if ("其他资料".equals(proofPhoto.getPhotoName())) {
					if (projectDetail.getOthersPhotoUrl() != null) {
						projectDetail.setOthersPhotoUrl(
								projectDetail.getOthersPhotoUrl() + "," + ip + proofPhoto.getUrlPath());
					} else {
						projectDetail.setOthersPhotoUrl(ip + proofPhoto.getUrlPath());
					}
				}
			}
			loanDetail.setProjectDtail(projectDetail);
			logger.info("返回查询结果");
			return loanDetail;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询出现异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询标的信息失败，请重试");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.LoanBiz#queryLoanByProjectId(java.lang.String)
	 */
	@Override
	public List<Loan> queryLoanByProjectId(String projectId) {
		try {
			Loan loan = new Loan();
			loan.setProjectId(projectId);
			return loanService.queryLoanByProjectId(loan);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询数据失败，请重试");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zhongyang.java.biz.LoanBiz#loanPublished(com.zhongyang.java.vo.
	 * LoanVo)
	 */
	@Override
	@Transactional
	public Message loanPublished(LoanVo loanVo) {
		Message message = new Message();
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "标的发布");
			if (loanVo == null || loanVo.getLoan() == null) {
				logger.info("标的信息获取异常");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得到标的信息，请重试");
			}
			logger.info("发布标的的ID：" + loanVo.getLoan().getId());
			Loan queryLoan = loanService.queryLoanById(loanVo.getLoan().getId());
			BigDecimal amount = (BigDecimalAlgorithm.mul(queryLoan.getAmount(), new BigDecimal(100))).setScale(0,
					BigDecimal.ROUND_DOWN);
			String uuid = queryLoan.getId().replace("-", "");
			String userId = queryLoan.getLoanUserId();// 获得借款人ID
			loanVo.getUmpAccount().setUserId(userId);// 为UmpAccount里的userId赋值
			UmpAccount umpAccount = umpAccountService.getUmpAccountByUserId(loanVo.getUmpAccount());

			// 发标
			// long time = queryLoan.getTimeOpen().getTime();
			// time=time+10*24*3600*1000;
			// Date expire_date=new Date(time);
			// SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			// String project_expire_date=sdf.format(expire_date);
			LoanPublished loanPublished = new LoanPublished();
			loanPublished.setProject_id(uuid);
			if (queryLoan.getTitle().contains("】")) {
				loanPublished
						.setProject_name(queryLoan.getTitle().substring(queryLoan.getTitle().lastIndexOf("】") + 1));
			} else {
				loanPublished.setProject_name(queryLoan.getTitle());
			}
			loanPublished.setProject_amount(amount.toString());
			loanPublished.setLoan_user_id(umpAccount.getAccountName());
			// loanPublished.setProject_expire_date(project_expire_date);
			logger.info("联动优势新建标的需要的参数:" + loanPublished.toString());
			// 返回数据
			logger.info("发送请求到联动优势，调用标的类发标接口");
			Map<String, String> valueMap = UmpLoan.umpLoanPublished(loanPublished);
			if (!"0000".equals(valueMap.get("ret_code"))) {
				logger.info("发标失败");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, valueMap.get("ret_msg"));
			}

			// 联动开立的标的平台账户号
			String project_account_id = valueMap.get("project_account_id");
			String mer_check_date = valueMap.get("mer_check_date");
			logger.info("联动开立的标的平台账户号：" + project_account_id);

			// 数据库插入一条addOneUmpTender记录
			UmpTender umpTender = new UmpTender();
			umpTender.setLoanid(queryLoan.getId());
			umpTender.setTimecreated(new Date());
			umpTender.setUmptenderaccountid(project_account_id);
			umpTender.setUmpcheckdate(mer_check_date);
			umpTender.setAmount(new BigDecimal(0));
			umpTenderService.addOneUmpTender(umpTender);
			logger.info("做联动投标信息记录：" + umpTender.toString());

			// 拼接开标请求更新标的参数
			LoanModify loanModifyFirst = new LoanModify();
			uuid = queryLoan.getId().replace("-", "");
			loanModifyFirst.setProject_id(uuid);
			loanModifyFirst.setProject_state("0");
			loanModifyFirst.setChange_type("01");
			logger.info("更新标的状态为开标，标的ID为:" + queryLoan.getId());
			logger.info("联动优势修改标的需要的参数:" + loanModifyFirst.toString());
			// 发送更新标的请求
			logger.info("发送请求到联动优势，调用标的类标的更新接口");
			Map<String, String> umpModifyLoanFisrst = UmpLoan.umpModifyLoan(loanModifyFirst);
			logger.info("标的更新为开标状态，返回结果状态码：" + umpModifyLoanFisrst.get("ret_code") + ",返回信息："
					+ umpModifyLoanFisrst.get("ret_msg"));
			if (!"0000".equals(umpModifyLoanFisrst.get("ret_code"))) {
				logger.info("标的更新为开标状态失败");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, umpModifyLoanFisrst.get("ret_msg"));
			}

			// 拼接投标中请求更新标的参数
			LoanModify loanModifySecond = new LoanModify();

			loanModifySecond.setProject_id(uuid);
			loanModifySecond.setProject_state("1");
			loanModifySecond.setChange_type("01");
			logger.info("更新标的状态为投资中，标的ID为:" + queryLoan.getId());
			logger.info("联动优势修改标的需要的参数:" + loanModifySecond.toString());
			// 发送更新标的请求
			logger.info("发送请求到联动优势，调用标的类标的更新接口");
			Map<String, String> umpModifyLoanSecond = UmpLoan.umpModifyLoan(loanModifySecond);

			logger.info("标的更新为投资中状态，返回结果状态码：" + umpModifyLoanSecond.get("ret_code") + ",返回信息："
					+ umpModifyLoanSecond.get("ret_msg"));
			if (!"0000".equals(umpModifyLoanSecond.get("ret_code"))) {
				logger.info("标的更新为投资中状态失败");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, umpModifyLoanSecond.get("ret_msg"));
			}
			Loan loan = new Loan();
			loan.setId(queryLoan.getId());
			loan.setStatus(LoanStatus.OPENED);
			Timestamp timeStamp = new Timestamp(date.getTime());
			loan.setTimeOpen(timeStamp);
			// 修改标的信息
			loanService.modifyLoan(loan);
			logger.info("修改平台标的状态为：" + LoanStatus.OPENED);

			message.setCode(1);
			message.setMessage("发布成功");
			logger.info("标的发布成功，可以进行投标");
			return message;

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("标的发布出现异常");
			logger.info("异常信息" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.LoanBiz#queryFinishedLoan(com.zhongyang.java.vo.
	 * loan.LoanStatus)
	 */
	@Override
	public PagerVO<LoanListVo> queryLoanList(PagerVO<LoanListVo> pager) {
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "条件查询标的列表");
			PagerVO<LoanListVo> pagerVo = new PagerVO<LoanListVo>();
			List<LoanListVo> loanListVo = new ArrayList<LoanListVo>();
			Page<Loan> page = new Page<Loan>();
			if (pager.getStart() != 0) {
				page.setPageNo(pager.getStart());
			} else {
				page.setPageNo(1);
			}
			if (pager.getLength() != 0) {
				page.setPageSize(pager.getLength());
			} else {
				page.setPageSize(5);
			}
			page.setStartTime(new Date(pager.getStartTime()));
			page.setEndTime(new Date(pager.getEndTime()));

			if (pager.getField() != null && pager.getValue() != null) {
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if ("xxx".equals(values[i]))
						continue;
					page.getParams().put(fileds[i], values[i]);
				}
			}
			if (pager.getSort() != null) {
				page.getParams().put("sort", pager.getSort());
			} else {
				page.getParams().put("sort", "desc");
			}
			Map<String, Object> params = page.getParams();
			if (page.getStartTime() != null) {
				params.put("startTime", page.getStartTime());
			}
			if (page.getEndTime() != null) {
				params.put("endTime", page.getEndTime());
			}
			logger.info("查询条件：" + page.toString());
			SelectLoanResultVo queryResult = loanService.queryResult(params);
			logger.info("查询结果数量：" + queryResult.getCount());
			int totalPage;
			if (queryResult.getCount() % page.getPageSize() == 0) {
				totalPage = queryResult.getCount() / page.getPageSize();
			} else {
				totalPage = (queryResult.getCount() / page.getPageSize()) + 1;
			}
			pagerVo.setTotalPage(totalPage);
			pagerVo.setRecordsTotal(queryResult.getCount());
			List<Loan> loans = loanService.queryLoanByStatus(page);
			if (loans != null) {
				for (Loan loan : loans) {
					LoanListVo llv = new LoanListVo();
					llv.setId(loan.getId());
					llv.setAmount(loan.getAmount());
					llv.setGuaranteeRealm(loan.getGuaranteeRealm());
					llv.setMethod(loan.getMethod());
					llv.setMonths(loan.getMonths());
					llv.setTitle(loan.getTitle());
					llv.setStatus(loan.getStatus());
					User user = new User();
					user.setId(loan.getLoanUserId());
					User us = userService.getUserById(user);
					llv.setUserName(us.getName());
					llv.setLoanUserId(loan.getLoanUserId());
					llv.setRate(loan.getRate());
					llv.setAddRate(loan.getAddRate());
					llv.setReason(loan.getOther());
					Timestamp timeStamp = new Timestamp(date.getTime());
					llv.setNowTime(timeStamp);
					if (loan.getTimeOpen() != null) {
						Timestamp timeOpen = new Timestamp(loan.getTimeOpen().getTime());
						llv.setTimeOpen(sdf.format(timeOpen));
						llv.setDivTime(timeOpen.getTime() - timeStamp.getTime());
					}
					if (loan.getTimeFailed() != null) {
						llv.setTimeFailed(sdf.format(loan.getTimeFailed()));
					}
					llv.setPlaning(
							BigDecimalAlgorithm
									.mul(BigDecimalAlgorithm.divScale(loan.getBidAmount(), loan.getAmount(), 6,
											RoundingMode.HALF_UP), new BigDecimal(100))
							.setScale(2, BigDecimal.ROUND_DOWN));
					loanListVo.add(llv);
				}
			}
			pagerVo.setData(loanListVo);
			logger.info("查询结果：" + pagerVo.toString());
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询出现异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败，请重试");
		}
	}

	@Override
	public PagerVO<LoanListVo> queryPublishedLoans(PagerVO<LoanListVo> pager) {
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "条件查询已发布标的列表");
			PagerVO<LoanListVo> pagerVo = new PagerVO<LoanListVo>();
			List<LoanListVo> loanListVo = new ArrayList<LoanListVo>();
			Page<Loan> page = new Page<Loan>();
			if (pager.getStart() != 0) {
				page.setPageNo(pager.getStart());
				pagerVo.setStart(pager.getStart());
			} else {
				page.setPageNo(1);
			}
			if (pager.getLength() != 0) {
				page.setPageSize(pager.getLength());
				pagerVo.setLength(pager.getLength());
			} else {
				page.setPageSize(5);
			}
			if (pager.getField() != null && pager.getValue() != null) {
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if ("xxx".equals(values[i]))
						continue;
					page.getParams().put(fileds[i], values[i]);
				}
			}
			if (pager.getSort() != null) {
				page.getParams().put("sort", pager.getSort());
			} else {
				page.getParams().put("sort", "desc");
			}
			page.setStartTime(new Date(pager.getStartTime()));
			page.setEndTime(new Date(pager.getEndTime()));
			if (page.getStartTime() != null) {
				page.getParams().put("startTime", page.getStartTime());
			}
			if (page.getEndTime() != null) {
				page.getParams().put("endTime", page.getEndTime());
			}
			logger.info("查询条件：" + page.toString());
			SelectLoanResultVo queryResult = loanService.queryResult(page.getParams());
			logger.info("查询结果数量：" + queryResult.getCount());
			int totalPage;
			if (queryResult.getCount() % page.getPageSize() == 0) {
				totalPage = queryResult.getCount() / page.getPageSize();
			} else {
				totalPage = (queryResult.getCount() / page.getPageSize()) + 1;
			}
			pagerVo.setTotalPage(totalPage);
			pagerVo.setRecordsTotal(queryResult.getCount());
			if (queryResult.getTotalAmount() != null) {
				pagerVo.setRecordsFiltered(queryResult.getTotalAmount().intValue());
			}

			List<Loan> loans = loanService.queryPublishedLoans(page);
			System.out.println(loans.size() + "========================================");
			if (loans != null) {
				for (Loan loan : loans) {
					LoanListVo llv = new LoanListVo();
					llv.setId(loan.getId());
					llv.setTitle(loan.getTitle());
					llv.setProductName(loan.getProductName());
					llv.setGuaranteeRealm(loan.getGuaranteeRealm());
					if (loan.getAmount() != null) {
						llv.setAmount(loan.getAmount());
					}
					llv.setStatus(loan.getStatus());
					if (loan.getTimeOpen() != null) {
						llv.setTimeOpen(sdf.format(loan.getTimeOpen()));
					}
					if (loan.getTimeSettled() != null) {
						llv.setTimeSettled(sdf.format(loan.getTimeSettled()));
					}
					Map<String, String> map = new HashMap<String, String>();
					UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(loan.getId());
					if (umpTender != null && umpTender.getUmptenderid() != null
							&& !"".equals(umpTender.getUmptenderid())) {
						map.put("project_id", umpTender.getUmptenderid());// 标的号
					} else {
						String project_id = loan.getId().replace("-", "");
						map.put("project_id", project_id);
					}
					UmpaySignature umpaySignature = new UmpaySignature("project_account_search", map);
					Map<String, String> values = umpaySignature.getSignature();
					logger.info("标的查询结果:" + values);
					if ("0000".equals(values.get("ret_code"))) {
						llv.setUmpAvailableAmount(BigDecimalAlgorithm
								.div(new BigDecimal(values.get("balance")), new BigDecimal(100)).toString());
					} else {
						llv.setUmpAvailableAmount("联动标的账户查询出错");
					}
					UmpTender tender = umpTender;
					llv.setZycfAvailableAmount(tender.getAmount());
					loanListVo.add(llv);
				}
			}
			pagerVo.setData(loanListVo);
			logger.info("查询结果：" + pagerVo.toString());
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询出现异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败，请重试");
		}
	}

	/**
	 * 查询已满标(状态：FINISHED)的标的
	 */
	public List<LoanInvestVo> getFinishedLoan() {
		List<LoanInvestVo> LoanInvestVo = new ArrayList<LoanInvestVo>();
		List<Loan> listLoan = new ArrayList<>();

		try {
			Loan loan = new Loan();
			loan.setStatus(LoanStatus.FINISHED);
			listLoan = loanService.getFinishedLoan(loan);
			if (listLoan.size() > 0) {

				for (int i = 0; i < listLoan.size(); i++) {
					LoanInvestVo vo = new LoanInvestVo();
					String loanUserId = listLoan.get(i).getLoanUserId();
					User userName = this.getUserById(loanUserId);// 查询借款人信息
					Invest invest = new Invest();
					invest.setLoanid(listLoan.get(i).getId());
					vo.setTitle(listLoan.get(i).getTitle());
					vo.setLoanid(listLoan.get(i).getId());
					vo.setAmount(listLoan.get(i).getAmount());
					vo.setName(userName.getName());
					vo.setBidAmount(listLoan.get(i).getBidAmount());
					vo.setStatus(listLoan.get(i).getStatus());
					vo.setBidNumber(listLoan.get(i).getBidNumber());
					OrderTime orderTime = new OrderTime();
					vo.setTimeFinished(
							orderTime.getwhenTime(listLoan.get(i).getTimeOpen(), listLoan.get(i).getTimeFinished()));
					LoanInvestVo.add(vo);
				}
			}
			return LoanInvestVo;
		} catch (Exception e) {
			System.out.println(e);
			throw new UException(SystemEnum.NO_INFORMATIONLOAN, "暂无可结算的标的");
		}

	}

	private User getUserById(String loanUserId) {
		try {
			User user = new User();
			user.setId(loanUserId);
			return userService.getUserById(user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_NAME, "不知道用户名字");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.LoanBiz#modifyLoan(com.zhongyang.java.pojo.Loan)
	 */
	@Override
	@Transactional
	public Message modifyUnpublishedLoan(Loan loan) {
		Message message = new Message();
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "修改未发布的标的信息");
			logger.info("需要修改的标的ID" + loan.getId());
			Loan queryLoan = loanService.queryLoanById(loan.getId());
			Project queryProject = projectService.queryProjectById(queryLoan.getProjectId());
			Project project = new Project();
			project.setId(queryProject.getId());
			if (loan.getAmount() != null) {
				// loan.setAmount(BigDecimalAlgorithm.mul(loan.getAmount(), new
				// BigDecimal(10000)));
				BigDecimal subAmount = BigDecimalAlgorithm.subt(queryLoan.getAmount(), loan.getAmount());

				System.out.println(subAmount);
				if (subAmount.doubleValue() > 0) {
					project.setSurplusAmount(BigDecimalAlgorithm.add(queryProject.getSurplusAmount(), subAmount));// 剩余可发标金额
					project.setPublishedAmount(BigDecimalAlgorithm.subt(queryProject.getPublishedAmount(), subAmount));// 已发标金额
					projectService.modifyProject(project);
					logger.info("标的金额发生改变，需要对应修改项目已发标金额以及剩余可发标金额");
				}
				if (subAmount.doubleValue() < 0) {
					subAmount = BigDecimalAlgorithm.subt(loan.getAmount(), queryLoan.getAmount());
					project.setSurplusAmount(BigDecimalAlgorithm.subt(queryProject.getSurplusAmount(), subAmount));// 剩余可发标金额
					project.setPublishedAmount(BigDecimalAlgorithm.add(queryProject.getPublishedAmount(), subAmount));// 已发标金额
					projectService.modifyProject(project);
					logger.info("标的金额发生改变，需要对应修改项目已发标金额以及剩余可发标金额");
				}
			}
			loanService.modifyLoan(loan);
			message.setCode(1);
			message.setMessage("修改标的成功");
			logger.info("标的修改成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("修改标的异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改标的信息失败，请重试");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.LoanBiz#cancleLoan(com.zhongyang.java.pojo.Loan)
	 */
	@Override
	@Transactional
	public Message cancleLoan(Loan loan, String reason) {
		Message message = new Message();
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "取消标的");
			logger.info("取消标的ID：" + loan.getId());
			if (loan.getId() == null) {
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未获得标的ID，请重试");
			}
			loan.setStatus(LoanStatus.CANCELED);
			loan.setTimeCancle(new Date());
			loan.setOther(reason);
			loanService.modifyLoan(loan);

			loan = loanService.queryLoanById(loan.getId());
			Project project = new Project();
			project.setId(loan.getProjectId());
			Project queryProject = projectService.queryProjectById(loan.getProjectId());
			project.setPublishedAmount(BigDecimalAlgorithm.sub(queryProject.getPublishedAmount(), loan.getAmount()));
			project.setSurplusAmount(BigDecimalAlgorithm.add(queryProject.getSurplusAmount(), loan.getAmount()));
			projectService.modifyProject(project);
			logger.info("取消标的后需要修改项目的已发标金额以及剩余可发标金额");
			message.setCode(1);
			message.setMessage("取消成功");
			logger.info("取消标的成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("取消标的异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "取消标的异常，查看日志信息");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.zhongyang.java.biz.LoanBiz#failedLoan(com.zhongyang.java.pojo.Loan)
	 */
	@Override
	public Message failedLoan(Loan loan, String reason) {
		Message message = new Message();
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "流标");
			logger.info("流标标的ID：" + loan.getId());
			loan = loanService.queryLoanById(loan.getId());
			Invest invest = new Invest();
			invest.setLoanid(loan.getId());
			invest.setStatus(InvestStatus.AUDITING);
			List<Invest> invests = investService.queryInvest(invest);
			logger.info("循环调用联动优势发起流标请求业务");
			// 订单生成日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			for (Invest iv : invests) {
				// 构造订单
				FailedLoanOrder failedLoanOrder = new FailedLoanOrder();

				failedLoanOrder.setId(UUID.randomUUID().toString().toUpperCase());
				// 投资记录ID
				failedLoanOrder.setInvestId(iv.getId());

				failedLoanOrder.setOrderDate(sdf.format(new Date()));
				// 生成订单号
				failedLoanOrder.setOrderId(ApplicationBean.order());

				logger.info("流标订单号：" + failedLoanOrder.getOrderId());
				// 记录时间
				failedLoanOrder.setTimeRecorded(new Date());
				// 订单金额
				failedLoanOrder.setAmount(iv.getAmount());
				// 订单状态
				failedLoanOrder.setStatus(FundRecordStatus.PROCESSING);// P:流标处理中
																		// S:流标成功
																		// F:流标失败
				// 标的Id
				failedLoanOrder.setLoanId(loan.getId());
				// 添加记录 TB_LOAN_ORDER
				failedLoanOrderService.addFailedLoanOrder(failedLoanOrder);

				// 创建联动信息对象
				UmpAccount umpAccount = new UmpAccount();
				umpAccount.setUserId(iv.getUserid());

				// 根据userId查询umpAccount TB_UMP_ACCOUNT
				umpAccount = umpAccountService.getUmpAccountByUserId(umpAccount);

				// 添加标的交易记录TB_UMP_TENDER_TRANSFER_RECORD
				UmpTenderTransferRecord uttRecord = new UmpTenderTransferRecord();
				uttRecord.setOrderid(failedLoanOrder.getOrderId());// 订单号
				uttRecord.setAmount(iv.getAmount());// 交易金额
				uttRecord.setLoanid(loan.getId());// 借款ID
				uttRecord.setStatus(FundRecordStatus.PROCESSING);// 状态
				uttRecord.setTenderaction(FundRecordOperation.OUT);// 标的操作
				uttRecord.setTransfertype(FundRecordType.FAILED_LOAN_REPAY);// 交易类型
				uttRecord.setTimecreated(new Date());// 创建日期
				uttRecord.setTimelastupdated(new Date());// 最后修改日期
				uttRecord.setUmpaccountid(umpAccount.getAccountId());// 联动帐号
				uttRecord.setUmpaccountname(umpAccount.getAccountName());// 联动帐户名称
				UmpTender umpTender = umpTenderService.queryUmpTenderByLoanId(loan.getId());
				uttRecord.setUmptenderaccountid(umpTender.getUmptenderaccountid());// 联动标的帐号
				uttRecord.setUserid(iv.getUserid());// 用户ID
				uttrService.addUmpTenderTransferRecord(uttRecord);
				logger.info("做标的交易记录，标的账户转出到个人账户");

				// 做资金解冻转入流水
				UserFund userFund = new UserFund();
				userFund.setUserid(iv.getUserid());
				UserFund fund = userFundService.byUserID(userFund);

				FundRecord fundRecord = new FundRecord();
				fundRecord.setId(UUID.randomUUID().toString().toUpperCase());
				fundRecord.setAmount(failedLoanOrder.getAmount());// 投资金额
				fundRecord.setDescription("流标处理中");
				fundRecord.setOperation(FundRecordOperation.IN);// 入账
				fundRecord.setOrderid(failedLoanOrder.getOrderId());// 订单号
				fundRecord.setStatus(FundRecordStatus.PROCESSING);// 处理中
				fundRecord.setTimerecorded(new Date());// 创建时间
				fundRecord.setType(FundRecordType.FAILED_LOAN_REPAY);// 资金记录类型
				fundRecord.setUserId(iv.getUserid());// 用户ID
				fundRecord.setAviAmount(fund.getAvailableAmount());
				fundRecordService.addFundRecord(fundRecord);
				logger.info("投资人" + failedLoanOrder.getInvestId() + "所投资金解冻转入到账户余额，记录处理中");

				// 准备联动参数
				Map<String, String> map = new HashMap<String, String>();
				// 协议参数 后台通知
				map.put("notify_url", ip + "/failedCallBack");
				// 业务参数
				map.put("order_id", failedLoanOrder.getOrderId());// 商户订单号
				map.put("mer_date", failedLoanOrder.getOrderDate());// 商户生成订单的日期
				if (umpTender != null && umpTender.getUmptenderid() != null && !"".equals(umpTender.getUmptenderid())) {
					map.put("project_id", umpTender.getUmptenderid());// 标的号
				} else {
					map.put("project_id", loan.getId().replace("-", ""));// 标的号
				}
				map.put("serv_type", "51");// 51流标后返款
				map.put("trans_action", "02");// 02标的转出
				map.put("partic_type", "01");// 取值范围：01投资者
				map.put("partic_acc_type", "01");// 01 个人
				map.put("partic_user_id", umpAccount.getAccountName());// 联动开立的用户号
				BigDecimal amount = BigDecimalAlgorithm.mul(iv.getAmount(), new BigDecimal(100));
				Integer intValue = amount.setScale(0, BigDecimal.ROUND_DOWN).intValue();
				System.out.println(intValue);
				map.put("amount", intValue.toString());// 金额 单位：分

				// 发送到联动优势
				UmpaySignature umpaySignature = new UmpaySignature("project_transfer", map);
				Map<String, String> signature = umpaySignature.getSignature();
				logger.info("订单" + failedLoanOrder.getOrderId() + "已发送请求到联动优势");
				logger.info("联动优势返回结果，状态码：" + signature.get("ret_code") + "，返回信息：" + signature.get("ret_msg"));
			}
			// 修改标的状态
			Loan ln = new Loan();
			ln.setId(loan.getId());
			ln.setStatus(LoanStatus.FAILED);
			ln.setTimeFailed(new Date());
			ln.setOther(reason);
			loanService.modifyLoan(ln);
			logger.info("标的" + loan.getId() + "已更改为流标状态");
			// 查询项目相关信息
			Project queryProject = projectService.queryProjectById(loan.getProjectId());
			// 修改项目相关信息
			Project project = new Project();
			project.setId(loan.getProjectId());
			project.setPublishedAmount(BigDecimalAlgorithm.sub(queryProject.getPublishedAmount(), loan.getAmount())
					.setScale(2, BigDecimal.ROUND_DOWN));
			project.setSurplusAmount(BigDecimalAlgorithm.add(queryProject.getSurplusAmount(), loan.getAmount())
					.setScale(2, BigDecimal.ROUND_DOWN));
			projectService.modifyProject(project);
			logger.info("标的对应项目" + loan.getProjectId() + "已发标金额、剩余可发标金额值做出调整");

			// 拼接开标请求更新标的参数
			LoanModify loanModify = new LoanModify();
			String uuid = loan.getId().replace("-", "");
			loanModify.setProject_id(uuid);
			loanModify.setProject_state("4");
			loanModify.setChange_type("01");
			logger.info("更新标的状态为结束，标的ID为:" + loan.getId());

			// 发送更新标的请求
			logger.info("发送请求到联动优势，调用标的类标的更新接口");
			Map<String, String> umpModifyLoanFisrst = UmpLoan.umpModifyLoan(loanModify);
			logger.info("标的更新为已结束状态，返回结果状态码：" + "返回信息："
					+ umpModifyLoanFisrst.get("ret_msg"));

			message.setCode(1);
			message.setMessage("流标成功");
			logger.info("流标成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("流标发生异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "流标异常，查看日志信息");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zhongyang.java.biz.LoanBiz#failedCallBack(com.zhongyang.java.vo.
	 * UmpBidTransferVo)
	 */
	@Override
	@Transactional
	public void failedCallBack(UmpBidTransferVo umpBidTransferVo) {
		Date date = new Date();
		try {
			logger.info(sdf.format(date) + "流标回调");
			// 响应数据
			Map<String, String> map = new HashMap<String, String>();
			String order_id = umpBidTransferVo.getOrder_id();// 订单号
			String ret_code = umpBidTransferVo.getRet_code();// 返回码
			String mer_date = umpBidTransferVo.getMer_date();// 订单日期
			String mer_check_date = umpBidTransferVo.getMer_check_date();// 对账日期
			String ret_msg = umpBidTransferVo.getRet_msg();
			String trade_no = umpBidTransferVo.getTrade_no();// 交易流水号
			// 准备回调参数
			map.put("order_id", order_id);
			map.put("mer_date", mer_date);
			map.put("ret_code", ret_code);

			// 响应联动优势
			UmpaySignature umpaySignature = new UmpaySignature(null, map);
			logger.info("流标回调结果响应给联动优势");
			umpaySignature.callBackSignature();

			// 修改流标订单
			FailedLoanOrder failedLoan = failedLoanOrderService.queryByOrderId(order_id);
			if (FundRecordStatus.SUCCESSFUL.equals(failedLoan.getStatus())) {
				return;
			}

			if (!"0000".equals(ret_code)) {

				FailedLoanOrder failedLoanOrder = new FailedLoanOrder();
				failedLoanOrder.setOrderId(failedLoan.getOrderId());
				failedLoanOrder.setStatus(FundRecordStatus.FAILED);
				failedLoanOrderService.modifyFailedLoanOrder(failedLoanOrder);
				logger.info("修改流标订单状态为FAILED");

				FundRecord record = new FundRecord();
				record.setOrderid(order_id);
				record.setStatus(FundRecordStatus.FAILED);
				record.setDescription("流标失败");
				fundRecordService.modifyFundRecord(record);

				// 修改标的交易记录TB_UMP_TENDER_TRANSFER_RECORD
				UmpTenderTransferRecord uttRecord = new UmpTenderTransferRecord();
				uttRecord.setOrderid(order_id);// 订单号
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				uttRecord.setMercheckdate(sdf.parse(mer_check_date));// 对帐日期
				uttRecord.setMerdate(sdf.parse(mer_date));// 交易日期
				uttRecord.setRetcode(ret_code);// 返回码
				uttRecord.setRetmsg(ret_msg);// 返回信息
				uttRecord.setStatus(FundRecordStatus.FAILED);// 状态
				uttRecord.setTradeno(trade_no);// 交易流水号
				uttrService.modifyUmpTenderTransferRecord(uttRecord);
				logger.info("订单号：" + order_id + "标的交易" + FundRecordStatus.FAILED);

				return;
			}

			Invest invest = new Invest();
			invest.setId(failedLoan.getInvestId());
			Invest queryInvest = investService.queryByInvest(invest);

			// 修改资金账户
			UserFund userFund = new UserFund();
			userFund.setUserid(queryInvest.getUserid());
			UserFund fund = userFundService.byUserID(userFund);
			userFund.setAvailableAmount(failedLoan.getAmount());
			userFund.setFrozenAmount(failedLoan.getAmount().multiply(new BigDecimal(-1)));
			userFund.setTimelastupdated(new Date());
			userFundService.modifyUserFund(userFund);
			logger.info("修改投资人" + invest.getUserid() + "账户余额、冻结金额。");

			FundRecord record = new FundRecord();
			record.setOrderid(order_id);
			record.setAviAmount(userFund.getAvailableAmount());
			record.setStatus(FundRecordStatus.SUCCESSFUL);
			record.setDescription("流标成功");
			fundRecordService.modifyFundRecord(record);

			FailedLoanOrder failedLoanOrder = new FailedLoanOrder();
			failedLoanOrder.setOrderId(failedLoan.getOrderId());
			failedLoanOrder.setStatus(FundRecordStatus.SUCCESSFUL);
			failedLoanOrderService.modifyFailedLoanOrder(failedLoanOrder);
			logger.info("修改流标订单状态为SUCCESSFUL");

			// 修改投资记录
			Invest updateInvest = new Invest();
			updateInvest.setId(failedLoan.getInvestId());
			updateInvest.setStatus(InvestStatus.FAILED);
			investService.modifyInvest(updateInvest);
			logger.info("修改对应投资记录状态" + invest.getId() + "======>" + FundRecordStatus.FAILED);

			Loan loan = loanService.queryLoanById(failedLoan.getLoanId());
			User user = new User();
			user.setId(queryInvest.getUserid());
			User queryUser = userService.getUserById(user);
			DESTextCipher cipher = DESTextCipher.getDesTextCipher();
			String mobile = cipher.decrypt(queryUser.getMobile());
			Map<String, Object> message = new HashMap<String, Object>();
			message.put("info", "“" + loan.getTitle() + "”" + "已被流标,投资金额将全额退回至平台资金账户");
			message.put("mobiles", mobile);
			ShortMessage.getShortMessage().sendToUserMsg(message);

			// 修改标的交易记录TB_UMP_TENDER_TRANSFER_RECORD
			UmpTenderTransferRecord uttRecord = new UmpTenderTransferRecord();
			uttRecord.setOrderid(order_id);// 订单号
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			uttRecord.setMercheckdate(sdf.parse(mer_check_date));// 对帐日期
			uttRecord.setMerdate(sdf.parse(mer_date));// 交易日期
			uttRecord.setRetcode(ret_code);// 返回码
			uttRecord.setRetmsg(ret_msg);// 返回信息
			uttRecord.setStatus(FundRecordStatus.SUCCESSFUL);// 状态
			uttRecord.setTradeno(trade_no);// 交易流水号
			uttrService.modifyUmpTenderTransferRecord(uttRecord);
			logger.info("订单号：" + order_id + "标的交易" + FundRecordStatus.SUCCESSFUL);

			UmpTender tender = new UmpTender();
			UmpTender queryTender = umpTenderService.queryUmpTenderByLoanId(failedLoan.getLoanId());
			tender.setLoanid(failedLoan.getLoanId());
			tender.setAmount(BigDecimalAlgorithm.sub(queryTender.getAmount(), failedLoan.getAmount()));
			umpTenderService.modifyUmpTender(tender);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("流标回调发生异常");
			logger.info("异常信息：" + e.getMessage());
			logger.error(e, e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "流标回调异常，查看日志信息");
		}
	}

	// 查询状态为FINISHED的投标用户
	public List<LoanInvestVo> getFinishedLoanInvesrUser(String longId) {
		try {
			List<LoanInvestVo> loanInvestVol = new ArrayList<>();

			Invest invest = new Invest();
			invest.setLoanid(longId);
			invest.setStatus(InvestStatus.AUDITING);
			List<Invest> invests = investService.getInvestByLoanId(invest);
			OrderTime orderTime = new OrderTime();
			if (invests.size() > 0) {
				for (int i = 0; i < invests.size(); i++) {
					LoanInvestVo LoanInvestVo = new LoanInvestVo();
					LoanInvestVo.setUserid(invests.get(i).getUserid());
					LoanInvestVo.setInvestAmount(invests.get(i).getAmount());
					LoanInvestVo.setSubmittime(orderTime.formatTmie(invests.get(i).getSubmittime()));
					User userUname = this.getUserById(LoanInvestVo.getUserid());
					LoanInvestVo.setInvestName(userUname.getName());
					loanInvestVol.add(LoanInvestVo);
				}
			}
			return loanInvestVol;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.NO_UUSERINVEST, "暂无用户投标");
		}
	}

	@Override
	public PagerVO<LoanVo> getUserLoanList(PagerVO<Loan> pager) {

		PagerVO<LoanVo> fundRecordPgVo = new PagerVO<LoanVo>();
		List<Loan> loanlist = new ArrayList<Loan>();
		List<LoanVo> loanlistvo = new ArrayList<LoanVo>();
		try {
			Page<FundRecord> page = new Page<FundRecord>();
			if (pager.getStart() != 0) {
				page.setPageNo(pager.getStart());
			} else {
				page.setPageNo(1);
			}
			if (pager.getLength() != 0) {
				page.setPageSize(pager.getLength());
			} else {
				page.setPageSize(5);
			}
			if (pager.getField() != null && pager.getValue() != null) {
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				for (int i = 0; i < fileds.length; i++) {
					if ("xxx".equals(values[i]))
						continue;
					page.getParams().put(fileds[i], values[i]);
				}
			}
			if (pager.getSort() != null) {
				page.getParams().put("sort", pager.getSort());
			} else {
				page.getParams().put("sort", "desc");
			}
			Map<String, Object> params = page.getParams();
			int count = loanService.getUserLoanCount(params);
			loanlist = loanService.getUserLoanList(page);
			if (loanlist.size() > 0) {
				for (int i = 0; i < loanlist.size(); i++) {
					LoanVo loanVo = new LoanVo();
					// loanVo.setId(loanlist.get(i).getId());// 标的Id
					loanVo.setLoanId(loanlist.get(i).getId());
					loanVo.setTitle(loanlist.get(i).getTitle());// 标题
					loanVo.setAmount(loanlist.get(i).getAmount());// 金额
					loanVo.setRate(loanlist.get(i).getRate());// 利率
					loanVo.setMonths(loanlist.get(i).getMonths());// 期限
					loanVo.setMethod(loanlist.get(i).getMethod().getKey());// 还款方式
					loanVo.setStatus(loanlist.get(i).getStatus().getKey());// 标的状态
					loanlistvo.add(loanVo);
				}
				fundRecordPgVo.setRecordsTotal(count);
				fundRecordPgVo.setData(loanlistvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e.fillInStackTrace());
		}
		return fundRecordPgVo;
	}

	@Override
	public Message modifyLoanStatus(String loanId, String status) {
		Message message = new Message();
		try {
			Loan result = loanService.queryLoanById(loanId);
			if (result == null) {
				message.setMessage("标的不存在");
				return message;
			}
			LoanModify loanModifySecond = new LoanModify();
			loanModifySecond.setProject_id(loanId.replace("-", ""));
			loanModifySecond.setProject_state(status);
			loanModifySecond.setChange_type("01");
			Map<String, String> umpModifyLoanSecond = UmpLoan.umpModifyLoan(loanModifySecond);
			message.setMessage(umpModifyLoanSecond.get("ret_msg"));

			if (!"0000".equals(umpModifyLoanSecond.get("ret_code"))) {
				return message;
			}
			Loan loan = new Loan();
			loan.setId(loanId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if ("0".equals(status) || "1".equals(status)) {
				UmpTender umpTender = new UmpTender();
				umpTender.setLoanid(loanId);
				UmpTender res = umpTenderService.queryByParams(umpTender);
				if (res == null) {
					UmpLoanInfo searchLoan = umpSearch.searchLoan(loanId);
					umpTender.setUmptenderaccountid(searchLoan.getProject_account_id());
					umpTender.setUmpcheckdate(umpModifyLoanSecond.get("mer_check_date"));
					umpTender.setTimecreated(sdf.parse(umpModifyLoanSecond.get("mer_check_date")));
					umpTender.setAmount(new BigDecimal(0));
					umpTenderService.addOneUmpTender(umpTender);
				}

			}
			if ("1".equals(status)) {
				if (result.getTimeOpen() == null) {
					loan.setStatus(LoanStatus.OPENED);
					loan.setTimeOpen(new Date());
					loanService.modifyLoan(loan);
				}
			}

			if ("2".equals(status)) {
				loan.setStatus(LoanStatus.SETTLED);
				Date timeFinished = result.getTimeFinished();
				if (timeFinished != null) {
					loan.setTimeSettled(new Date(timeFinished.getTime() + 30 * 60 * 1000));
				}
				loanService.modifyLoan(loan);
			}

		} catch (Exception e) {
			logger.info(e.fillInStackTrace());
			e.printStackTrace();
		}
		return message;
	}

}
