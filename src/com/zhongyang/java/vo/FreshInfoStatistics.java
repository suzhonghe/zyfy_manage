package com.zhongyang.java.vo;

import java.util.List;

public class FreshInfoStatistics {
	
	private List<FreshInfo>freshInfos;

	public FreshInfoStatistics() {
		super();
	}

	public FreshInfoStatistics(List<FreshInfo> freshInfos) {
		super();
		this.freshInfos = freshInfos;
	}

	public List<FreshInfo> getFreshInfos() {
		return freshInfos;
	}

	public void setFreshInfos(List<FreshInfo> freshInfos) {
		this.freshInfos = freshInfos;
	}
}
