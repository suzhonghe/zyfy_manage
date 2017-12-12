package com.zhongyang.java.service;
import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.system.page.Page;


/**
 * 
* @Title: LoanRequestDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 产品管理dao接口
* @author 苏忠贺   
* @date 2015年12月1日 下午3:27:25 
* @version V1.0
 */
public interface ProductService {
	/**
	 * 
	* @Title: insertProduct 
	* @Description: 添加一条产品记录 
	* @return void    返回类型 
	* @throws
	 */
	public void addProduct(Product product)throws Exception;
	/**
	 * 
	* @Title: selectAllProduct 
	* @Description:查询所有产品 
	* @return void    返回类型 
	* @throws
	 */
	public List<Product> queryAllProduct(Page<Product>page)throws Exception;
	/**
	 * 
	* @Title: selectCount 
	* @Description:根据条件查询结果数量 
	* @return int    返回类型 
	* @throws
	 */
	public int queryCount(Map<String,Object>params)throws Exception;
	/**
	 * 
	* @Title: modifyProduct 
	* @Description: 修改产品 
	* @return void    返回类型 
	* @throws
	 */
	public void modifyProduct(Product product)throws Exception;
	/**
	 * 
	* @Title: queryProductsByStatus 
	* @Description:根据状态查询产品列表 
	* @return List<Product>    返回类型 
	* @throws
	 */
	public List<Product> queryProductsByStatus(Integer status)throws Exception;
	/**
	 * 
	* @Title: selectProductById 
	* @Description:根据产品ID查询详情 
	* @return Product    返回类型 
	* @throws
	 */
	public Product queryProductById(String id)throws Exception;
}
