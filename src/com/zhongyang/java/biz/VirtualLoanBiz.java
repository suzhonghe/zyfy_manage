package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;

/**
 * 体验标接口
 * @author Administrator
 *
 */
public interface VirtualLoanBiz {
	
	public Message saveVirtualLoan(VirtualLoan virtualLoan);

	public Message updateVirtualLoan(VirtualLoan virtualLoan);

	public Page<VirtualLoan> getAllVirtualLoan(Page<VirtualLoan> page);

	public VirtualLoan getVirtualLoanById(String id);
}
