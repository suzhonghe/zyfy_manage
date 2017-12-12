package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.ExperienceAmount;

public interface ExperienceAmountDao {
	
	public int addExperienceAmount(ExperienceAmount experienceAmount);
	
	public int updateExperienceAmount(ExperienceAmount experienceAmount);
	
	public ExperienceAmount selectByParams(ExperienceAmount experienceAmount);
	
	public int updateExperiences();
}
