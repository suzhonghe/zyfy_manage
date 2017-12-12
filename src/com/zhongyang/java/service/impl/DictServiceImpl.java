package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.DictDao;
import com.zhongyang.java.pojo.Dict;
import com.zhongyang.java.service.DictService;

@Service
public class DictServiceImpl implements DictService {

	@Autowired
	private DictDao dictDao;
	@Override
	public List<Dict> getAllDict() {
		return dictDao.getAllDict();
	}

	@Override
	public int addDict(Dict dict) {
		return dictDao.addDict(dict);
	}

	@Override
	public void updateDict(Dict dict) {
		dictDao.updateDict(dict);
		
	}

	@Override
	public Dict getDictByCode(String code) {
		return dictDao.getDictByCode(code);
	}

}
