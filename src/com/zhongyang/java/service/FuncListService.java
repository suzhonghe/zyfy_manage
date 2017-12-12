package com.zhongyang.java.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zhongyang.java.pojo.SysFuncList;


public interface FuncListService {

	List<SysFuncList> selectAll();
}
