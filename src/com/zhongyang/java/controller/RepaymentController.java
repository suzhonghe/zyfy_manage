package com.zhongyang.java.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.RepaymentBiz;
import com.zhongyang.java.pojo.LoanRepayment;
import com.zhongyang.java.pojo.RepayQuery;
import com.zhongyang.java.pojo.User;
import com.zhongyang.java.service.LoanService;
import com.zhongyang.java.service.RepaymentService;
import com.zhongyang.java.service.UserService;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.system.uitl.DateUtil;
import com.zhongyang.java.vo.CallBackRepay;
import com.zhongyang.java.vo.PagerVO;
import com.zhongyang.java.vo.RepaymentInfo;
import com.zhongyang.java.vo.ReturnRepayment;

@Controller
public class RepaymentController  extends BaseController {
	
	@Autowired
	private RepaymentBiz repaymentBiz;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/repayQuery")
	public  @ResponseBody PagerVO repayQuery(PagerVO pagerVo){
		return repaymentBiz.repayQuery(pagerVo);
	}

	/**
	 * 还款查询列表
	 * @param page
	 * @return
	 */
	@FireAuthority(authorities=Authorities.REPAYLIST)
	@RequestMapping(value="/repaymentQuery")
	public @ResponseBody Page<RepayQuery> repaymentQuery(Page<RepayQuery> page)	{
		return repaymentBiz.repaymentQuery(page);
	}
	
	@FireAuthority(authorities=Authorities.REPAYLIST)
	@RequestMapping("/uploadRepayQueryExcel")
	@ResponseBody
	public ResponseEntity<byte[]> exportRepayQueryData(HttpServletRequest request) {
		try {
			Page<RepayQuery> page = new Page<RepayQuery>();
			Map<String, Object> map = new HashMap<String, Object>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			if (request.getParameter("startTime") != null && !"".equals(request.getParameter("startTime"))) {
				map.put("startTime", request.getParameter("startTime"));
			}
			if (request.getParameter("endTime") != null && !"".equals(request.getParameter("endTime"))) {
				map.put("endTime", request.getParameter("endTime"));
			}
			if (request.getParameter("status") != null && !"".equals(request.getParameter("status"))) {
				map.put("status", request.getParameter("status"));
			}
			if (request.getParameter("name") != null && !"".equals(request.getParameter("name"))) {
				String str = new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");
				List<User> list = userService.getUserByName(str);
				if(list!=null && list.size()>0){
					map.put("loanUserId", list.get(0).getId());
				}
			}
			page.setParams(map);
			return repaymentBiz.exportRepayQueryData(request, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
    @RequestMapping(value="/getRepaymentInfo")
    public  @ResponseBody RepaymentInfo getRepaymentInfo(RepaymentInfo repaymentInfo){
        return repaymentBiz.getRepaymentInfo(repaymentInfo);
    }
/**
 * 还款请求
 * @param id
 * @return
 */
    @FireAuthority(authorities=Authorities.REPAYMENT)
    @RequestMapping(value="/repay")
	public  @ResponseBody Message repay(String id){
        return repaymentBiz.repay(id);
	}

    /**
     * 联动优势还款请求回调，资金转入标的账户
     * @param callBackRepay
     * @return
     */
    @RequestMapping(value="/callBackRepay")
    public  @ResponseBody String callBackRepay(CallBackRepay callBackRepay){
        return repaymentBiz.callBackRepay(callBackRepay);
    }
    /**
     * 资金从标的账户转入投资人账户
     * @param callBackRepayTop
     * @return
     */
    
    @RequestMapping(value="/callBackRepayTop")
    public  @ResponseBody String callBackRepayTop(CallBackRepay callBackRepay){
        return repaymentBiz.callBackRepayTop(callBackRepay);
    }
    @RequestMapping(value="/queryByLoanId")
    public  @ResponseBody List<ReturnRepayment> queryByLoanId(LoanRepayment loanRepayment){
        return repaymentBiz.queryByLoanId(loanRepayment);
    }
    //还款（借款人）利息管理费回调
    @RequestMapping(value="/callBackBorrowerFee")
    public  @ResponseBody String callBackBorrowerFee(CallBackRepay callBackRepay){
        return repaymentBiz.callBackBorrowerFee(callBackRepay);
    }

    @RequestMapping(value="/callBackRepayInvestFee")
    public @ResponseBody String callbackRepayInvestFee(CallBackRepay callbackRepay){
    	return repaymentBiz.callBackRepayInvestFee(callbackRepay);
    }
	
    @FireAuthority(authorities=Authorities.SUBREPAY)
    @RequestMapping(value="/repayByPlatform")
    public @ResponseBody Message repayByPlatform(String repaymentId){
    	
    	return repaymentBiz.repayByPlatform(repaymentId);
    }
    
    @RequestMapping(value="/repayByPlatformCallBack")
    public @ResponseBody String repayByPlatformCallBack(CallBackRepay callBackRepay){
    	return repaymentBiz.repayByPlatformCallBack(callBackRepay);
    }
    @FireAuthority(authorities=Authorities.PREPAY)
    @RequestMapping(value="/repayInAdvance")
    public @ResponseBody Message repayInAdvance(String id){
//			 repaymentBiz.repayInAdvance(id);
//	    	 return repaymentBiz.repay(id);
    	return repaymentBiz.repayAdvance(id);
    }
        
}
