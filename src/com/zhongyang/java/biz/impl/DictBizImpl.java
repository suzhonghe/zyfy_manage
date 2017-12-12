package com.zhongyang.java.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.DictBiz;
import com.zhongyang.java.pojo.Dict;
import com.zhongyang.java.service.DictService;

@Service
public class DictBizImpl implements DictBiz {

	@Autowired
	private DictService dictService;
	@Override
	public List<Dict> getAllDict() {
		return dictService.getAllDict();
	}

	@Override
	@Transactional
	public int addDict(Dict dict) {
		int i = dictService.addDict(dict);
		dict.setCode("C"+String.format("%03d",dict.getId()));
		dictService.updateDict(dict);
		return i;
	}

}
