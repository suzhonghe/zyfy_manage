package com.zhongyang.java.system.uitl;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月10日 上午10:45:18
* 类说明
*/
public class BigDecimalAlgorithm {
	/**
	 * 提供精确加法计算的add方法
	 * @param value1 被加数
	 * @param value2 加数
	 * @return 两个参数的和
	 */
	public static BigDecimal add(BigDecimal value1,BigDecimal value2) {
		
		return value1.add(value2);
		
	}
	/**
	 * 提供精确减法运算的sub方法
	 * @param value1 被减数
	 * @param value2 减数
	 * @return 两个参数的差
	 */
	public static BigDecimal sub(BigDecimal value1,BigDecimal value2){
		BigDecimal aa = value1.subtract(value2);
		int cc = aa.compareTo(new BigDecimal(0));
		if(cc < 0){
			return new BigDecimal(0.00);	
		}else{
			return aa;
		}
	}
	public static BigDecimal subt(BigDecimal value1,BigDecimal value2){
		BigDecimal aa = value1.subtract(value2);
		return aa;
	}
	
	/**
	 * 提供精确乘法运算的mul方法
	 * @param value1 被乘数
	 * @param value2 乘数
	 * @return 两个参数的积
	 */
	public static BigDecimal mul(BigDecimal value1,BigDecimal value2){

	return value1.multiply(value2);
	}
	
	/**
	 * 提供精确除法法运算的div方法
	 * @param value1 被除数
	 * @param value2 除数
	 * @return 两个参数的
	 */
	public static BigDecimal div(BigDecimal value1,BigDecimal value2){

	return value1.divide(value2);
	}
	
	public static BigDecimal divScale(BigDecimal value1,BigDecimal value2,int scale, RoundingMode roundingMode){

		return value1.divide(value2,scale,roundingMode);
	}
	
	
	/**
	 * String类型转化为BigDecimal类型
	 * @param account
	 * @return BigDecimal
	 */
	public static BigDecimal sToBd(String account){
		
		BigDecimal bdAccount = new BigDecimal(account);
		return bdAccount;
		
	}
	
	/**
	 * 把数据库整数类型转为小数。如：5%转为0.05
	 * @param bigDecimal
	 * @return BigDecimal
	 */
	public static BigDecimal sToBdNUm(BigDecimal amount){
		BigDecimal num = new BigDecimal(100.00);
		return amount.divide(num);
		
	}
	
	/**
	 * 算出应扣的费
	 * @param loanAmount 
	 * @param bigDecimal
	 * @return BigDecimal
	 */
	public static BigDecimal amountFee(BigDecimal amountFee, BigDecimal loanAmount){
		BigDecimal num = new BigDecimal(100.00);
		BigDecimal amountFeeT = new BigDecimal(0.00);
		BigDecimal amountFeeP = amountFee.divide(num);
		amountFeeT = amountFeeP.multiply(loanAmount).multiply(num).setScale(0);//换算为分并且保留整数
		return amountFeeT;
		
	}
	/**
	 * 把元单位换算为分单位
	 * @param loanAmount 
	 */
	public static BigDecimal yuanTofen(String amount){
		BigDecimal num = new BigDecimal(100.00);
		BigDecimal amBigDecimal = new BigDecimal(amount);
		BigDecimal amountfen = amBigDecimal.multiply(num).setScale(0);//换算为分并且保留整数
		return amountfen;
		
	}
	
	/**
	 * 把分单位换算为元单位
	 * @param loanAmount 
	 */
	public static BigDecimal fenToyuan(String amount){
		BigDecimal num = new BigDecimal(100.00);
		BigDecimal amBigDecimal = new BigDecimal(amount);
		BigDecimal amountyuan = amBigDecimal.divide(num).setScale(2);//换算为分并且保留整数
		return amountyuan;
		
	}
	
	
}
