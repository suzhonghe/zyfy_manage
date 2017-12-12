package com.zhongyang.java.system.uitl;

import java.math.BigDecimal;

import com.zhongyang.java.pojo.Loan;

/**
 * @author 作者:zhaofq
 * @version 创建时间：2015年12月25日 下午2:28:35 类说明：还款利息算法
 */
public class ArithmeticInterest {
    //一次性付本息算法：还款利息和投资利息都是一次性还，公式：利率*本金*期限/12；除12是为了把年华利率转为为月利率
	public BigDecimal getArithmeticInterest(BigDecimal rate, BigDecimal loanAmount, int longtime, String str) {
		String mont = Integer.toString(longtime);
		BigDecimal years = new BigDecimal(12);
		BigDecimal months = new BigDecimal(mont);
		// 1，先把利率换算为小数类型：比如把5% 换算为0.05
		BigDecimal retaToP = rate.divide(new BigDecimal(100));
		// 2,一定金额年华利息：利息*标的金额
		BigDecimal tretaAmount = retaToP.multiply(loanAmount);
		// 3，月化利息:总利息/12
		BigDecimal monthsRate = new BigDecimal(0.00);
		if (str.endsWith("invtr")) {// 投资人算法
			monthsRate = (tretaAmount.multiply(months)).divide(years, 2, BigDecimal.ROUND_DOWN);
		} else {// 标的算法
			monthsRate = (tretaAmount.multiply(months)).divide(years, 2, BigDecimal.ROUND_HALF_EVEN);
		}
		return monthsRate;
	}
	
	 //按月付息到期还本算法：还款利息和投资利息都是一次性还，公式：利率*本金/12；除12是为了把年华利率转为为月利率
		public BigDecimal getArithmeticMonthInterest(BigDecimal rate, BigDecimal loanAmount, int longtime, String str) {
			String mont = Integer.toString(longtime);
			BigDecimal years = new BigDecimal(12);
			BigDecimal months = new BigDecimal(mont);
			// 1，先把利率换算为小数类型：比如把5% 换算为0.05
			BigDecimal retaToP = rate.divide(new BigDecimal(100));
			// 2,一定金额年华利息：利息*标的金额
			BigDecimal tretaAmount = retaToP.multiply(loanAmount);
			// 3，月化利息:总利息/12
			BigDecimal monthsRate = new BigDecimal(0.00);
			if (str.endsWith("invtr")) {// 投资人算法
				monthsRate = tretaAmount.divide(years, 2, BigDecimal.ROUND_DOWN);
			} else {// 标的算法
				monthsRate = tretaAmount.divide(years, 2, BigDecimal.ROUND_HALF_EVEN);
			}
			return monthsRate;
		}

	// 标的总利息
	public BigDecimal getArithmeticTotal(Loan loanInfo) {
		String mont = loanInfo.getMonths().toString();
		BigDecimal years = new BigDecimal(12);
		BigDecimal months = new BigDecimal(mont);
		// 1，先把利率换算为小数类型：比如把5% 换算为0.05
		BigDecimal retaToP = loanInfo.getRate().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		// 2,一定金额年华利息：利息*标的金额
		BigDecimal tretaAmount = retaToP.multiply(loanInfo.getAmount());
		// 3，月化利息:总利息/12
		BigDecimal monthsRate = tretaAmount.divide(years, 2, BigDecimal.ROUND_HALF_EVEN);
		// 4，总利=月化利息*标的期限
		BigDecimal tatolAmounRate = (monthsRate.multiply(months)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return tatolAmounRate;

	}

	// 借款利息之和
	public BigDecimal getDueInAmountTotle(Loan loanInfo, BigDecimal amount) {
		String mont = loanInfo.getMonths().toString();
		BigDecimal years = new BigDecimal(12);
		BigDecimal months = new BigDecimal(mont);
		// 1，先把利率换算为小数类型：比如把5% 换算为0.05
		BigDecimal retaToP = loanInfo.getRate().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		// 2,一定金额年华利息：利息*投资金额
		BigDecimal tretaAmount = retaToP.multiply(amount);
		// 3，月化利息:总利息/12
		BigDecimal monthsRate = tretaAmount.divide(years, 2, BigDecimal.ROUND_HALF_EVEN);
		// 4，总利=月化利息*标的期限
		BigDecimal tatolAmounRate = (monthsRate.multiply(months)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return tatolAmounRate;
	}

	// 一次新付本息标的还款计划生成数据：返回的是总li
	public static void main(String[] args) {

	}

}
