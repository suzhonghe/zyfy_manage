package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.VirtualLoanDao;
import com.zhongyang.java.pojo.VirtualLoan;
import com.zhongyang.java.service.VirtualLoanService;
import com.zhongyang.java.system.page.Page;

/**
 * 体验标操作实现类
 * @author Administrator
 *
 */
@Service
public class VirtualLoanServiceImpl implements VirtualLoanService {
	
	@Autowired
	public VirtualLoanDao virtualLoanDao; 
	@Override
	public int saveVirtualLoan(VirtualLoan virtualLoan) {
		return virtualLoanDao.saveVirtualLoan(virtualLoan);
	}

	@Override
	public int updateVirtualLoan(VirtualLoan virtualLoan) {
		
		return virtualLoanDao.updateVirtualLoan(virtualLoan);
	}

	@Override
	public List<VirtualLoan> getAllVirtualLoan(Page<VirtualLoan> page) {
		
		return virtualLoanDao.getAllVirtualLoan(page);
	}

	@Override
	public VirtualLoan getVirtualLoanById(String id) {
		return virtualLoanDao.getVirtualLoanById(id);
	}

	@Override
	public VirtualLoan queryVirtualLoanByParams(VirtualLoan virtualLoan) {
		return virtualLoanDao.selectVirtualLoanByParams(virtualLoan);
	}
}
