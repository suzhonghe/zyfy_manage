package com.zhongyang.java.service;

import com.zhongyang.java.pojo.ExperienceAmount;

public interface ExperienceAmountService {
	
	public int addExperienceAmount(ExperienceAmount experienceAmount);
	
	public int modifyExperienceAmount(ExperienceAmount experienceAmount);
	
	public ExperienceAmount queryByParams(ExperienceAmount experienceAmount);
	
	public int modifyExperiences();
}
