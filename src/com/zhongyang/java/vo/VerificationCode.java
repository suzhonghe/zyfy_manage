package com.zhongyang.java.vo;

import java.awt.image.BufferedImage;

public class VerificationCode {
		
	private String code;
	private BufferedImage buffImg;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BufferedImage getBuffImg() {
		return buffImg;
	}
	public void setBuffImg(BufferedImage buffImg) {
		this.buffImg = buffImg;
	}
	public VerificationCode(String code, BufferedImage buffImg) {
		this.code = code;
		this.buffImg = buffImg;
	}
	public VerificationCode() {
	}
	@Override
	public String toString() {
		return "VerificationCode [code=" + code + ", buffImg=" + buffImg + "]";
	}
	
	
	
	
}
