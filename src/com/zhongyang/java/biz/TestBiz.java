package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.Test;

public interface TestBiz {
	public List<Test> getTest();
	
	public List<Test> getException();
}
