package com.zhongyang.java.service;

import com.zhongyang.java.pojo.Proof;

/**
 * 
* @Title: ProofService.java 
* @Package com.zhongyang.java.service 
* @Description: 证明材料业务管理 接口
* @author 苏忠贺   
* @date 2015年12月2日 下午2:26:36 
* @version V1.0
 */
public interface ProofService {
	/**
	 * 
	* @Title: addOneProof 
	* @Description:插入一条记录 
	* @return void    返回类型 
	* @throws
	 */
	public void addOneProof(Proof proof)throws Exception;
	/**
	 * 
	* @Title: queryProofByProjectId 
	* @Description:根据项目Id查询Proof
	* @return Proof    返回类型 
	* @throws
	 */
	public Proof queryProofByProjectId(String Id)throws Exception;
	/**
	 * 
	* @Title: deleteProofById 
	* @Description: 很据id删除Proof
	* @return void    返回类型 
	* @throws
	 */
	public void deleteProofById(String id)throws Exception;
}
