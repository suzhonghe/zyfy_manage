package com.zhongyang.java.biz;

import java.util.List;

import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.PagerVO;

/**
 * 
* @Title: ProductBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:产品业务处理接口 
* @author 苏忠贺   
* @date 2015年12月17日 下午3:57:13 
* @version V1.0
 */
public interface ProductBiz {
	/**
	 * 
	* @Title: addProduct 
	* @Description: 添加产品 
	* @return void    返回类型 
	* @throws
	 */
	public Message addProduct(Product product);
	/**
	 * 
	* @Title: queryAllProducts 
	* @Description:查询所有产品 
	* @return List<Product>    返回类型 
	* @throws
	 */
	public PagerVO<Product> queryAllProducts(PagerVO<Product>pager);
	/**
	 * 
	* @Title: modifyProduct 
	* @Description:修改产品 
	* @return Message    返回类型 
	* @throws
	 */
	public Message modifyProduct(Product product);
	/**
	 * 
	* @Title: queryProductsByStatus 
	* @Description:根据产品状态查询产品列表
	* @return List<Product>    返回类型 
	* @throws
	 */
	public List<Product> queryProductsByStatus(Integer status);
	/**
	 * 
	* @Title: queryProductById 
	* @Description：根据产品ID查询产品详情 
	* @return Product    返回类型 
	* @throws
	 */
	public Product queryProductById(String id);
	/**
	 * 
	* @Title: modifyProductStatus 
	* @Description:修改产品状态
	* @return Message    返回类型 
	* @throws
	 */
	public Message modifyProductStatus(String productId);
	/**
	 * 
	* @Title: queryAllProductName 
	* @Description:查询所有产品类型 
	* @return List<String>    返回类型 
	* @throws
	 */
	public List<String> queryAllProductName();
	
}
