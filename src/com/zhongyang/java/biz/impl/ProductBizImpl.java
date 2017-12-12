package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.ProductBiz;
import com.zhongyang.java.pojo.Product;
import com.zhongyang.java.service.ProductService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.PagerVO;

/**
 * 
* @Title: ProductBizImpl.java 
* @Package com.zhongyang.java.biz.impl 
* @Description: 产品业务处理接口实现
* @author 苏忠贺   
* @date 2015年12月17日 下午3:59:00 
* @version V1.0
 */
@Service
public class ProductBizImpl implements ProductBiz{
	
	private static Logger logger=Logger.getLogger(ProductBizImpl.class);
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private ProductService productService;
	
	@Override
	@Transactional
	public Message addProduct(Product product) {
		Date date=new Date();
		Message message=new Message();
		try {
			logger.info(sdf.format(date)+"申请添加产品");
			if(product==null){
				logger.info("未获得页面产品相关信息");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据接收异常，请重试");
			}
			product.setId(UUID.randomUUID().toString().toUpperCase());
			if(product.getRate()==null||product.getRepayMethod()==null||product.getMaxInvestAmount()==null||
					product.getMinInvestAmount()==null||product.getMonths()==null||product.getName()==null){
				logger.info("页面获得的数据不全"+product.toString());
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据填写不全，请重试");
			}
			product.setRate(product.getRate());
			product.setStatus(0);
			product.setTimeCreate(new Date());
			productService.addProduct(product);
			message.setCode(1);
			message.setMessage("添加成功");
			logger.info("添加成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("添加产品信息异常");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "产品添加失败");
		}
	}

	@Override
	public PagerVO<Product> queryAllProducts(PagerVO<Product>pager) {
		Date date=new Date();
		try {
			logger.info(sdf.format(date)+"按条件查询产品信息");
			PagerVO<Product> pagerVo=new PagerVO<Product>();
			Page<Product>page=new Page<Product>();
			if(pager.getStart()!=0){
				page.setPageNo(pager.getStart());
			}
			else{
				page.setPageNo(1);
			}
			if(pager.getLength()!=0){
				page.setPageSize(pager.getLength());
			}
			else{
				page.setPageSize(Integer.MAX_VALUE);
			}
			page.setStartTime(new Date(pager.getStartTime()));
			page.setEndTime(new Date(pager.getEndTime()));
			if(pager.getField()!=null&&pager.getValue()!=null){
				String[] fileds = pager.getField().split(",");
				String[] values = pager.getValue().split(",");
				if("".equals(pager.getField())){
					page.getParams().put("timeCreate","TIMECREATE");
				}
				else{
					if(!pager.getField().contains("timeCreate")){
						page.getParams().put("timeCreate","TIMECREATE");
					}
				}
				for (int i = 0; i < fileds.length; i++) {
					if("".equals(values[i]))continue;
					page.getParams().put(fileds[i],values[i]);
				}
				if(pager.getSort()!=null){
					page.getParams().put("sort",pager.getSort());
				}
				else{
					page.getParams().put("sort","desc");
				}
			}
			Map<String,Object>map=new HashMap<String,Object>();
			if(pager.getStartTime()!=0){
				map.put("startTime", pager.getStartTime());
			}
			if(pager.getEndTime()!=0){
				map.put("endTime", pager.getEndTime());
			}
			int count = productService.queryCount(map);
			logger.info("查询总记录数"+count+"条");
			System.out.println(count+"============================================");
			pagerVo.setRecordsTotal(count);
			int totalPage=count%page.getPageSize();
			System.out.println(totalPage+"======================");
			if(totalPage==0){
				pagerVo.setTotalPage(count/page.getPageSize());
			}
			else{
				pagerVo.setTotalPage(count/page.getPageSize()+1);
			}
			List<Product> products = productService.queryAllProduct(page);
			logger.info("查询结果封装到返回值里");
			pagerVo.setData(products);
			return pagerVo;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询出现异常");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询产品数据失败，请重试");
		}
	}

	@Override
	@Transactional
	public Message modifyProduct(Product product) {
		Date date=new Date();
		Message message=new Message();
		try {
			logger.info(sdf.format(date)+"修改产品");
			if(product==null||product.getId()==null){
				logger.info("未获得修改后的产品信息");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据接收异常，请重试");
			}
			productService.modifyProduct(product);
			logger.info("修改成功");
			message.setCode(1);
			message.setMessage("修改成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("修改产品出现异常");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改产品失败，请重试");
		}
	}

	@Override
	public List<Product> queryProductsByStatus(Integer status) {
		try {
			if(status==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未接收到参数");
			}
			return productService.queryProductsByStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询数据出现问题");
		}
	}

	@Override
	public Product queryProductById(String id) {
		try {
			if(id==null){
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "未接收到参数");
			}
			return productService.queryProductById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询数据出现问题");
		}
	}

	@Override
	@Transactional
	public Message modifyProductStatus(String productId) {
		Date date=new Date();
		Message message=new Message();
		try {
			logger.info(sdf.format(date)+"修改产品状态");
			if(productId==null){
				logger.info("未获得修改产品的ID");
				throw new UException(SystemEnum.UNKNOW_EXCEPTION, "参数接收出现问题");
			}
			Product product=new Product();
			Product selectProduct = productService.queryProductById(productId);
			if(selectProduct==null||selectProduct.getStatus()==null)throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询数据出现问题");
			product.setId(selectProduct.getId());
			if(selectProduct.getStatus()==0){
				product.setStatus(1);
			}
			else {
				product.setStatus(0);
			}
			productService.modifyProduct(product);
			message.setCode(1);
			message.setMessage("修改成功");
			logger.info("状态修改成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("修改产品状态出现异常");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改产品状态失败，请重试");
		}
	}

	@Override
	public List<String> queryAllProductName() {
		List<String>names=new ArrayList<String>();
		Date date=new Date();
		try {
			logger.info(sdf.format(date)+"查询所有产品名称");
			Page<Product>page=new Page<Product>();
			page.setPageNo(1);
			page.setPageSize(Integer.MAX_VALUE);
			page.getParams().put("timeCreate","TIMECREATE");
			page.getParams().put("sort","desc");
			List<Product> products = productService.queryAllProduct(page);
			for (Product product : products) {
				names.add(product.getName());
			}
			return names;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询产品出现问题");
			logger.info("异常信息"+e.getMessage());
			logger.info(e,e.fillInStackTrace());
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "数据查询失败，请重试");
		}
	}
}
