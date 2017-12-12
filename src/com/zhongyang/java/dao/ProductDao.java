package com.zhongyang.java.dao;

import java.util.List;
import java.util.Map;

import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.system.page.Page;

/**
 * 
* @Title: ProductDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 产品DAO 
* @author 苏忠贺   
* @date 2015年12月16日 下午3:53:17 
* @version V1.0
 */
public interface ProductDao {
	/**
	 * 
	* @Title: insertProduct 
	* @Description: 添加一条产品记录 
	* @return void    返回类型 
	* @throws
	 */
	public void insertProduct(Product product)throws Exception;
	/**
	 * 
	* @Title: selectAllProduct 
	* @Description:查询所有产品 
	* @return void    返回类型 
	* @throws
	 */
	public List<Product> selectAllProduct(Page<Product>page)throws Exception;
	/**
	 * 
	* @Title: selectCount 
	* @Description:根据条件查询结果数量
	* @return int    返回类型 
	* @throws
	 */
	public int selectCount(Map<String,Object>params)throws Exception;
	/**
	 * 
	* @Title: updateProduct 
	* @Description: 更新产品
	* @return void    返回类型 
	* @throws
	 */
	public void updateProduct(Product product)throws Exception;
	/**
	 * 
	* @Title: selectProductsByStatus 
	* @Description:根据状态查询产品列表 
	* @return List<Product>    返回类型 
	* @throws
	 */
	public List<Product> selectProductsByStatus(Integer status)throws Exception;
	/**
	 * 
	* @Title: selectProductById 
	* @Description:根据产品ID查询详情
	* @return Product    返回类型 
	* @throws
	 */
	public Product selectProductById(String id)throws Exception;
}
