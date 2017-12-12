package com.zhongyang.java.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhongyang.java.biz.UploadExcelBiz;
import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpSettleWithdrawRecord;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.LoanListVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ProjectVo;

/**
 * 
* @Title: UploadExcelController.java 
* @Package com.zhongyang.java.controller 
* @Description:导出Excel控制器
* @author 苏忠贺   
* @date 2016年1月25日 上午11:11:26 
* @version V1.0
 */
@Controller
public class UploadExcelController extends BaseController{
	
	@Autowired
	private UploadExcelBiz uploadExcelBiz;
	
	@FireAuthority(authorities=Authorities.PRJUPLOAD)
	@RequestMapping(value="/uploadProject")
	public ResponseEntity<byte[]> uploadProject(HttpServletRequest request,PagerVO<ProjectVo>pager){
		return uploadExcelBiz.uploadProject(request, pager);
	}
	
	@FireAuthority(authorities=Authorities.BIDLISTUPLOAD)
	@RequestMapping(value="/uploadPublishedLoans")
	public ResponseEntity<byte[]> uploadPublishedLoans(HttpServletRequest request,PagerVO<LoanListVo>pager){
		return uploadExcelBiz.uploadPublishedLoans(request,pager);
	}
	
	@FireAuthority(authorities=Authorities.DERIVEFILE)
	@RequestMapping(value="/uploadRechargeRecords")
	public ResponseEntity<byte[]> uploadRechargeRecords(HttpServletRequest request,PagerVO<UmpSettleRechargeRecord>pager){
		return uploadExcelBiz.uploadRechargeRecords(request, pager);
	}
	
	@FireAuthority(authorities=Authorities.DERIVEFILE)
	@RequestMapping(value="/uploadWithdrawRecords")
	public ResponseEntity<byte[]> uploadWithdrawRecords(HttpServletRequest request,PagerVO<UmpSettleWithdrawRecord>pager){
		return uploadExcelBiz.uploadWithdrawRecords(request, pager);
	}
	
	@FireAuthority(authorities=Authorities.DERIVEFILE)
	@RequestMapping(value="/uploadTransferRecords")
	public ResponseEntity<byte[]> uploadTransferRecords(HttpServletRequest request,PagerVO<UmpSettleTransferRecord>pager){
		return uploadExcelBiz.uploadTransferRecords(request, pager);
	}
	
	@FireAuthority(authorities=Authorities.DERIVEFILE)
	@RequestMapping(value="/uploadTenderRecords")
	public ResponseEntity<byte[]> uploadTenderRecords(HttpServletRequest request,PagerVO<UmpSettleTenderRecord>pager){
		return uploadExcelBiz.uploadTenderRecords(request, pager);
	}
	
	@FireAuthority(authorities=Authorities.DERIVEFILE)
	@RequestMapping(value="/uploadPlatFormRecord")
	public ResponseEntity<byte[]> uploadPlatFormRecord(HttpServletRequest request,Page<FundRecord>page){
		return uploadExcelBiz.uploadPlatFormRecord(request, page);
	}
}
