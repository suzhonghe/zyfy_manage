package com.zhongyang.java.dao;

import com.zhongyang.java.pojo.Proof;

/**
 * 
* @Title: ProofDao.java 
* @Package com.zhongyang.java.dao 
* @Description:证明材料dao 
* @author 苏忠贺   
* @date 2015年12月2日 下午1:58:11 
* @version V1.0
 */
public interface ProofDao {

	/**
	 * 
	* @Title: insertProof 
	* @Description:插入一条记录
	* @return void    返回类型 
	* @throws
	 */
	public void insertProof(Proof proof)throws Exception;
	/**
	 * 
	* @Title: selectProofByProjectId 
	* @Description: 根据项目Id查询Proof
	* @return Proof    返回类型 
	* @throws
	 */
	public Proof selectProofByProjectId(String Id)throws Exception;
	/**
	 * 
	* @Title: deleteProofById 
	* @Description:很据id删除Proof 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteProofById(String id)throws Exception;
	
}
