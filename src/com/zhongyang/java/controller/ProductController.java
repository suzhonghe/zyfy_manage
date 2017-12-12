package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ProductBiz;
import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.PagerVO;
/**
 * 
* @Title: ProductController.java 
* @Package com.zhongyang.java.controller 
* @Description: 产品控制器 
* @author 苏忠贺   
* @date 2015年12月21日 上午10:48:32 
* @version V1.0
 */
@Controller
public class ProductController extends BaseController{
	
	@Autowired
	private ProductBiz productBiz;
	
	/**
	 * 
	* @Title: addProduct 
	* @Description:添加产品 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRDADD)
	@RequestMapping(value="/addProduct", method = RequestMethod.POST)
	public @ResponseBody Message addProduct(Product product){
		return productBiz.addProduct(product);
	}
	/**
	 * 
	* @Title: queryAllProducts 
	* @Description:查询所有产品 
	* @return List<Product>    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRDLIST)
	@RequestMapping(value="/queryAllProducts", method = RequestMethod.POST)
	public @ResponseBody PagerVO<Product> queryAllProducts(PagerVO<Product>pager){
		return productBiz.queryAllProducts(pager);
	}; 
	
	/**
	 * 
	* @Title: modifyProduct 
	* @Description:修改产品 
	* @return Message    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRDUPD)
	@RequestMapping(value="/modifyProduct", method = RequestMethod.POST)
	public @ResponseBody Message modifyProduct(Product product){
		return productBiz.modifyProduct(product);
	}
	/**
	 * 
	* @Title: queryProductByStatus 
	* @Description:根据状态查询产品列表 
	* @return List<Product>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryProductByStatus", method = RequestMethod.POST)
	public @ResponseBody List<Product> queryProductByStatus(Integer status){
		return productBiz.queryProductsByStatus(status);
	}
	/**
	 * 
	* @Title: queryProductById 
	* @Description:根据产品ID查询详情
	* @return Product    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryProductById", method = RequestMethod.POST)
	public @ResponseBody Product queryProductById(String id){
		return productBiz.queryProductById(id);
	}
	/**
	 * 
	* @Title: modifyProductStatus 
	* @Description:修改产品状态 
	* @return Product    返回类型 
	* @throws
	 */
	@FireAuthority(authorities=Authorities.PRDBAN)
	@RequestMapping(value="/modifyProductStatus", method = RequestMethod.POST)
	public @ResponseBody Message modifyProductStatus(String productId){
		return productBiz.modifyProductStatus(productId);
	}
	
	@RequestMapping(value="/queryAllProductName", method = RequestMethod.POST)
	public @ResponseBody List<String> queryAllProductName(){
		return productBiz.queryAllProductName();
	}
}
