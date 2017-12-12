package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.Test;
import com.zhongyang.java.system.page.Page;

public interface TestDao {
	public List<Test> getTest(Page<Test> page);
}
