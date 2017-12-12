package com.zhongyang.java.biz;

import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.BindcardSearch;
import com.zhongyang.java.vo.BindcardSearchCondition;
import com.zhongyang.java.vo.PtpMerQuery;
import com.zhongyang.java.vo.PtpMerQueryConditon;
import com.zhongyang.java.vo.TranseqSearch;
import com.zhongyang.java.vo.TranseqSearchCondition;
import com.zhongyang.java.vo.TransferSearchConditon;
import com.zhongyang.java.vo.UmpLoanInfo;
import com.zhongyang.java.vo.UmpTransferSearch;
import com.zhongyang.java.vo.UmpUserFund;
import com.zhongyang.java.vo.UmpUserInfo;

public interface UmpSearchBiz {
	
	public UmpLoanInfo searchLoan(String loanId);
	
	public UmpTransferSearch transferSearch(TransferSearchConditon transferSearchCondition);
	
	public PtpMerQuery queryPtpMer(PtpMerQueryConditon ptpMerQueryCondition);
	
	public BindcardSearch queryBindcard(BindcardSearchCondition bindcardSearchCondition);
	
	public TranseqSearch transeq_search(TranseqSearchCondition transeqSearchCondition);
	
	public UmpUserInfo umpUserSearch(String userId);
	
	public Page<UmpUserFund> queryUserFund(Page<UmpUserFund>page);
}
