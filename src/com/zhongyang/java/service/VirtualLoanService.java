package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.system.page.Page;

/**
 * 体验标信息操作
 * @author Administrator
 *
 */
public interface VirtualLoanService {

	/**
	 * 新建体验标
	 * @param virtualLoan
	 * @return
	 */
	public int saveVirtualLoan(VirtualLoan virtualLoan);
	/**
	 * 更新体验标信息
	 * @param virtualLoan
	 * @return       
	 */
	public int updateVirtualLoan(VirtualLoan virtualLoan);
	/**
	 * 查询获得所有的体验标的信息
	 * @return
	 */
	public List<VirtualLoan> getAllVirtualLoan(Page<VirtualLoan> page);
	
	/**
	 * 根据id查询当前体验标信息
	 * @return
	 */
	public VirtualLoan getVirtualLoanById(String id);
	
	public VirtualLoan queryVirtualLoanByParams(VirtualLoan virtualLoan);
}
