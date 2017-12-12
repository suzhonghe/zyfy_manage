package com.zhongyang.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ExperienceAmountDao;
import com.zhongyang.java.pojo.ExperienceAmount;
import com.zhongyang.java.service.ExperienceAmountService;

@Service
public class ExperienceAmountServiceImpl implements ExperienceAmountService{
	
	@Autowired
	private ExperienceAmountDao experienceAmountDao;

	@Override
	public int addExperienceAmount(ExperienceAmount experienceAmount) {
		return experienceAmountDao.addExperienceAmount(experienceAmount);
	}

	@Override
	public int modifyExperienceAmount(ExperienceAmount experienceAmount) {
		return experienceAmountDao.updateExperienceAmount(experienceAmount);
	}

	@Override
	public ExperienceAmount queryByParams(ExperienceAmount experienceAmount) {
		return experienceAmountDao.selectByParams(experienceAmount);
	}

	@Override
	public int modifyExperiences() {
		return experienceAmountDao.updateExperiences();
	}
}
