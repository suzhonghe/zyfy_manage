package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.Dict;

public interface DictDao {
	
	/**
	 * 查询所有字典列表
	 * @return
	 */
	public List<Dict> getAllDict();
	/**
	 * 新增触发方式
	 */
	public int addDict(Dict dict);
	/**
	 * 更新字典数据
	 * @param dict
	 */
	public void updateDict(Dict dict);
	/**
	 * 根据code查询当前触发方式
	 * @param code
	 * @return
	 */
	public Dict getDictByCode(String code);
}
