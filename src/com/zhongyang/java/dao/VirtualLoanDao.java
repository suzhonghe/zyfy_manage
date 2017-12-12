package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.system.page.Page;

public interface VirtualLoanDao {
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
	
	public VirtualLoan selectVirtualLoanByParams(VirtualLoan virtualLoan);
	
}
