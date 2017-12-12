package com.zhongyang.java.vo;

public class ParamsPojo {
	
	private String start;
	
	private String end;
	
	private int idauthenticated;
	
	private int ifinvest;
	
	private int crowd=1;//人群：1：所有人；2：线上人员；3：线下推荐

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int isIdauthenticated() {
		return idauthenticated;
	}

	public void setIdauthenticated(int idauthenticated) {
		this.idauthenticated = idauthenticated;
	}

	public int isIfinvest() {
		return ifinvest;
	}

	public void setIfinvest(int ifinvest) {
		this.ifinvest = ifinvest;
	}

	public int getCrowd() {
		return crowd;
	}

	public void setCrowd(int crowd) {
		this.crowd = crowd;
	}
}
