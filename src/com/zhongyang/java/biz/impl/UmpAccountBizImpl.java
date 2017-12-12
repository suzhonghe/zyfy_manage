package com.zhongyang.java.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.biz.UmpAccountBiz;
import com.zhongyang.java.pojo.UmpAccount;
import com.zhongyang.java.service.UmpAccountService;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.vo.LoanVo;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月2日 下午5:21:08
* 类说明：用户UmpAccount
*/
@Service
public class UmpAccountBizImpl  implements UmpAccountBiz{
	
	@Autowired
	private UmpAccountService umpAccountService;
	
    /*
     * (non-Javadoc)
     * @see com.zhongyang.java.biz.UmpAccountBiz#byUserIdUmpAccount()
     * 根据用户Id查询用户umpaccount信息
     */
	@Override
	public String queryInfoByUmpAccount(LoanVo loanVo) {
		
		try {
			if(loanVo!=null){
				//获得联动优势需要的参数
				String project_id=loanVo.getLoan().getId();//标的ID
				String project_name=loanVo.getLoan().getTitle();//标的名称
				String project_amount=loanVo.getLoan().getAmount().toString();//标的金额
				
				String userId = loanVo.getLoan().getLoanUserId();//获得借款人ID
				loanVo.getUmpAccount().setUserId(userId);//为UmpAccount里的userId赋值
			}
			else{
				throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未知异常，请重试");
			}
			UmpAccount umpAccount = umpAccountService.getUmpAccountByUserId(loanVo.getUmpAccount());
			return null;
			
		} catch (Exception e) {
			throw new UException(SystemEnum.UNKNOW_EXCEPTION,"未知错误"); 
		}
	}

}
