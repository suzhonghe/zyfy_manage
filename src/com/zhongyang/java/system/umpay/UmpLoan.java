package com.zhongyang.java.system.umpay;

import java.util.HashMap;
import java.util.Map;

import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;
import com.zhongyang.java.vo.LoanModify;
import com.zhongyang.java.vo.LoanPublished;
/**
 * 
* @Title: UmpLoan.java 
* @Package com.zhongyang.java.system.umpay 
* @Description:发标、标的更新接口
* @author 苏忠贺   
* @date 2015年12月17日 上午11:15:05 
* @version V1.0
 */
public class UmpLoan {
	/**
	 * @throws RetDataException 
	 * @throws ReqDataException 
	 * 
	* @Title: UmpLoanPublished 
	* @Description:发标
	* @return void    返回类型 
	* @throws
	 */
	public static Map<String, String> umpLoanPublished(LoanPublished loanPublished) throws ReqDataException, RetDataException{
		Map<String, String> map=new HashMap<String, String>();

		map.put("project_id", loanPublished.getProject_id());//必传
		map.put("project_name", loanPublished.getProject_name());//必传
		map.put("project_amount", loanPublished.getProject_amount());//必传
		map.put("loan_user_id", loanPublished.getLoan_user_id());//必传
		map.put("ctrl_over_invest","0");//限制标的超投
		if(loanPublished.getProject_expire_date()!=null){
			map.put("project_expire_date", loanPublished.getProject_expire_date());
		}
		if(loanPublished.getLoan_account_id()!=null){
			map.put("loan_account_id", loanPublished.getLoan_account_id());
		}
		if(loanPublished.getLoan_acc_type()!=null){
			map.put("loan_acc_type", loanPublished.getLoan_acc_type());
		}
		if(loanPublished.getWarranty_user_id()!=null){
			map.put("warranty_user_id", loanPublished.getWarranty_user_id());
		}
		if(loanPublished.getWarranty_account_id()!=null){
			map.put("warranty_account_id", loanPublished.getWarranty_account_id());
		}
		if(loanPublished.getReceive_user_id()!=null){
			map.put("receive_user_id", loanPublished.getReceive_user_id());
		}
		if(loanPublished.getReceive_account_id()!=null){
			map.put("receive_account_id", loanPublished.getReceive_account_id());
		}
		if(loanPublished.getReceive_acc_type()!=null){
			map.put("receive_acc_type", loanPublished.getReceive_acc_type());
		}

		//发送到联动优势
		UmpaySignature umpaySignature = new UmpaySignature("mer_bind_project",map);
		
		//获得联动响应数据
		Map<String, String> valueMap = umpaySignature.getSignature();
		
		return valueMap;
	}
	/**
	 * 
	* @Title: umpModifyLoan 
	* @Description:修改标的 
	* @return Map<String,String>    返回类型 
	* @throws
	 */
	public static Map<String,String> umpModifyLoan(LoanModify loanModify) throws ReqDataException, RetDataException{
		Map<String, String> map=new HashMap<String, String>();
		map.put("project_id", loanModify.getProject_id());
		map.put("change_type", loanModify.getChange_type());
		if(loanModify.getProject_state()!=null){
			map.put("project_state", loanModify.getProject_state());
		}
		if(loanModify.getProject_name()!=null){
			map.put("project_name", loanModify.getProject_name());
		}
		if(loanModify.getProject_amount()!=null){
			map.put("project_amount", loanModify.getProject_amount());//以分为单位
		}
		if(loanModify.getProject_expire_date()!=null){
			map.put("project_expire_date", loanModify.getProject_expire_date());//以分为单位
		}
		if(loanModify.getOption_type()!=null){
			map.put("option_type", loanModify.getOption_type());//以分为单位
		}
		if(loanModify.getLoan_user_id()!=null){
			map.put("loan_user_id", loanModify.getLoan_user_id());
		}
		if(loanModify.getLoan_account_id()!=null){
			map.put("loan_account_id", loanModify.getLoan_account_id());
		}
		if(loanModify.getLoan_acc_type()!=null){
			map.put("loan_acc_type", loanModify.getLoan_acc_type());
		}
		if(loanModify.getWarranty_user_id()!=null){
			map.put("warranty_user_id", loanModify.getWarranty_user_id());
		}
		if(loanModify.getWarranty_account_id()!=null){
			map.put("warranty_account_id", loanModify.getWarranty_account_id());
		}
		if(loanModify.getReceive_user_id()!=null){
			map.put("receive_user_id", loanModify.getReceive_user_id());
		}
		if(loanModify.getReceive_account_id()!=null){
			map.put("receive_account_id", loanModify.getReceive_account_id());
		}
		if(loanModify.getReceive_acc_type()!=null){
			map.put("receive_acc_type", loanModify.getReceive_acc_type());
		}
		//发送更新标的请求 
		UmpaySignature umpaySignature = new UmpaySignature("mer_update_project",map);
		Map<String, String> valueMap=umpaySignature.getSignature();
		return valueMap;
	}
}
