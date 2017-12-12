package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.FreshAmountDao;
import com.zhongyang.java.pojo.FreshAmount;
import com.zhongyang.java.service.FreshAmountService;
@Service
public class FreshAmountServiceImpl implements FreshAmountService {

	@Autowired
	public FreshAmountDao freshAmountDao;

	@Override
	public int addFreshAmount(FreshAmount freshAmount) {
		return freshAmountDao.insertFreshAmount(freshAmount);
	}

	@Override
	public int addBatchFreshAmount(List<FreshAmount> list) {
		return freshAmountDao.insertBatchFreshAmount(list);
	}

	@Override
	public int modifyFreshAmount(FreshAmount freshAmount) {
		return freshAmountDao.updateFreshAmount(freshAmount);
	}

	@Override
	public int modifyFreshAmounts() {
		return freshAmountDao.updateFreshAmounts();
	}

}
