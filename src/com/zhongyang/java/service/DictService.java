package com.zhongyang.java.service;

import java.util.List;

import com.zhongyang.java.pojo.Dict;

/**
 * 字典表
 * @author Administrator
 *
 */
public interface DictService {
	/**
	 * 查询所有字典数据
	 * @return
	 */
	public List<Dict> getAllDict();
	
	/**
	 * 新增字典数据
	 */
	public int addDict(Dict dict);
	/**
	 *更新字典数据
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
