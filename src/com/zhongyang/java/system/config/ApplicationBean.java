package com.zhongyang.java.system.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.zhongyang.java.system.log.LogInterface;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月1日 下午3:19:35
* 类说明：配置文件读取,订单号生成
*/
public class ApplicationBean {

  Logger logger=Logger.getLogger(ApplicationBean.class.getName());
  
  
   /*
    * 用于缓存已经生成的ordId，防止重复
    */
    private final Set<String> orderIds = new HashSet<>();
    /*
     * 订单号长度
     */
    private static final int DEFAULT_ORDER_LENGTH = 20;
    /*
     * 返回Client内唯一的订单号
     * @return
     */
    @LogInterface(description="点单号")
    public synchronized String orderId() {
        String orderId = order();
        if (!orderIds.add(orderId)) {
        	logger.info("订单号已经存在)}："+orderId);
            System.out.println("订单号已经存在"+orderId);
            return orderId();
        } else {
            return orderId;
        }
    }


    /**
     * 生成随机数字顺序，具有默认长度 
     *
     * @return
     */
    public static String order() {
        return order(DEFAULT_ORDER_LENGTH);
    }

    
    public static String order(int length) {
        return RandomStringUtils.randomNumeric(length);
    }
    
    /**
     * 检查传入的OrderId是否格式正确且唯一，若格式不正确返回新的随机orderId
     *
     * @param orderId
     * @return
     */
    public String checkOrderId(String orderId) {
        if (StringUtils.isBlank(orderId) || !orderId.matches("\\d{20}")) {
            logger.warn("OrderId invalid.[orderId={}]"+orderId);
            return orderId();
        }
        if (!orderIds.add(orderId)) {
            logger.warn("OrderId already placed.[orderId={}]"+orderId);
            return orderId();
        } else {
            return orderId;
        }
    }
    

}
