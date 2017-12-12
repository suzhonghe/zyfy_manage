package com.zhongyang.java.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ProductDao;
import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.service.ProductService;
import com.zhongyang.java.system.page.Page;

/**
 * 
* @Title: ProductAerviceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:项目管理接口实现
* @author 苏忠贺   
* @date 2015年12月17日 下午3:50:36 
* @version V1.0
 */
@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;

	@Override
	public void addProduct(Product product) throws Exception {
		productDao.insertProduct(product);
	}

	@Override
	public List<Product> queryAllProduct(Page<Product>page) throws Exception {
		return productDao.selectAllProduct(page);
	}

	@Override
	public void modifyProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}

	@Override
	public List<Product> queryProductsByStatus(Integer status) throws Exception {
		return productDao.selectProductsByStatus(status);
	}

	@Override
	public Product queryProductById(String id) throws Exception {
		return productDao.selectProductById(id);
	}

	@Override
	public int queryCount(Map<String,Object>params) throws Exception {
		return productDao.selectCount(params);
	}

}
