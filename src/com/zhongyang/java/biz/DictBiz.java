package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.Dict;

public interface DictBiz {
	/**
	 * 查询所有字典数据
	 * 
	 * @return
	 */
	public List<Dict> getAllDict();

	/**
	 * 新增字典数据
	 */
	public int addDict(Dict dict);

}
