package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.Test;
import com.zhongyang.java.system.page.Page;

public interface TestService {
	public List<Test> getTest(Page<Test> page);
}
