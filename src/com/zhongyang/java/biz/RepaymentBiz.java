package com.zhongyang.java.biz;




import com.zhongyang.java.pojo.RepayQuery;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.zhongyang.java.pojo.LoanRepayment;

import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.CallBackRepay;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.RepaymentInfo;
import com.zhongyang.java.vo.ReturnRepayment;

public interface RepaymentBiz {

		public PagerVO repayQuery(PagerVO pagerVo);

        public String callBackRepay(CallBackRepay callBackRepay);
		
		public Message repay(String id);

        public String callBackRepayTop(CallBackRepay callBackRepay);

        public RepaymentInfo getRepaymentInfo(RepaymentInfo RepaymentInfo);


    	public Page<RepayQuery> repaymentQuery(Page<RepayQuery> page);
    	
    	//投资用户手续费收取回调接口
    	public String callBackRepayInvestFee(CallBackRepay callbackRepay);
    	
    	//偿付接口
    	public Message repayByPlatform(String id);
    	
    	public String repayByPlatformCallBack(CallBackRepay callBackRepay);

        public List<ReturnRepayment> queryByLoanId(LoanRepayment loanRepayment);
        
        public Message repayInAdvance(String id);
        //借款人利息管理费回调借口
        public String callBackBorrowerFee(CallBackRepay callbackRepay);
        public Message repayAdvance(String id);
        //未到期还款导出
        public ResponseEntity<byte[]> exportRepayQueryData(HttpServletRequest request, Page<RepayQuery> page);
        public Message adjustRepay(String loanId,int per);

}
