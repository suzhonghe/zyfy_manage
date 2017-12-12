package com.zhongyang.java.biz;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.zhongyang.java.pojo.FundRecord;
import com.zhongyang.java.pojo.UmpSettleRechargeRecord;
import com.zhongyang.java.pojo.UmpSettleTenderRecord;
import com.zhongyang.java.pojo.UmpSettleTransferRecord;
import com.zhongyang.java.pojo.UmpSettleWithdrawRecord;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.LoanListVo;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.ProjectVo;

/**
 * 
* @Title: UploadExcel.java 
* @Package com.zhongyang.java.biz 
* @Description:导出excel
* @author 苏忠贺   
* @date 2016年1月25日 上午10:29:39 
* @version V1.0
 */
public interface UploadExcelBiz {
	
	public ResponseEntity<byte[]> uploadProject(HttpServletRequest request,PagerVO<ProjectVo>pager);
	
	public ResponseEntity<byte[]> uploadPublishedLoans(HttpServletRequest request,PagerVO<LoanListVo>pager);
	
	public ResponseEntity<byte[]> uploadRechargeRecords(HttpServletRequest request,PagerVO<UmpSettleRechargeRecord>pager);
	
	public ResponseEntity<byte[]> uploadWithdrawRecords(HttpServletRequest request,PagerVO<UmpSettleWithdrawRecord>pager);
	
	public ResponseEntity<byte[]> uploadTransferRecords(HttpServletRequest request,PagerVO<UmpSettleTransferRecord>pager);
	
	public ResponseEntity<byte[]> uploadTenderRecords(HttpServletRequest request,PagerVO<UmpSettleTenderRecord>pager);
	
	public ResponseEntity<byte[]> uploadPlatFormRecord(HttpServletRequest request,Page<FundRecord>page);
}
