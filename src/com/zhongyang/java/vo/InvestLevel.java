package com.zhongyang.java.vo;

import java.util.List;

public class InvestLevel {
	
	private List<InvestLevelCount> investLevelcount;

	public InvestLevel() {
		super();
	}

	public InvestLevel(List<InvestLevelCount> investLevelcount) {
		super();
		this.investLevelcount = investLevelcount;
	}

	public List<InvestLevelCount> getInvestLevelcount() {
		return investLevelcount;
	}

	public void setInvestLevelcount(List<InvestLevelCount> investLevelcount) {
		this.investLevelcount = investLevelcount;
	}

}
