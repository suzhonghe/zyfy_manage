package com.zhongyang.java.system.uitl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
* @author 作者:zhaofq
* @version 创建时间：2015年12月3日 下午4:21:13
* 类说明
*/
public class NumberUtils {
	/**
     * 将数据字符串转为BigDecimal
     *
     * @param number
     * @return
     */
    public static BigDecimal parse(String number) {
        //排除逗号
        String string = number.trim().replaceAll(",", "");

        return new BigDecimal(string);
    }

    /**
     * check whether amount is between from and to
     *
     * @param from   can not larger than to ,return false if so
     * @param to
     * @param amount
     * @return
     */
    public static boolean between(BigDecimal from, BigDecimal to, int amount) {
        return between(from, to, BigDecimal.valueOf(amount));
    }

    /**
     * check whether amount is between from and to
     *
     * @param from   can not larger than to ,return false if so
     * @param to
     * @param amount
     * @return
     */
    public static boolean between(int from, int to, BigDecimal amount) {
        return between(new BigDecimal(from), new BigDecimal(to), amount);
    }

    /**
     * check whether amount is between from and to
     *
     * @param from   can not larger than to ,return false if so
     * @param to
     * @param amount
     * @return
     */
    public static boolean between(BigDecimal from, BigDecimal to, BigDecimal amount) {
        if (from.compareTo(to) > 0) {
            return false;
            //throw new IllegalArgumentException();
        }

        return amount.compareTo(from) >= 0 && amount.compareTo(to) <= 0;
    }

    /**
     * check whether amount is between from and to
     *
     * @param from   can not larger than to ,return false if so
     * @param to
     * @param amount
     * @return
     */
    public static boolean between(int from, int to, int amount) {
        if (from < to) {
            return false;
            //throw new IllegalArgumentException();
        }

        return amount >= from && amount <= to;
    }

    /**
     * standard usage for convert BidDecimal to String in creditcloud
     *
     * @param amount
     * @return
     */
    public String decimalToString(BigDecimal amount) {
        return amount.setScale(DEFAULT_SCALE).toPlainString();
    }
    
    /**
     * 将利率rate转化为string，最多保留小数点后一位0</p>
     * rate:800->8.0%
     * rate:1100->11.0%
     * rate:1111->11.11%
     *
     * @param rate
     * @return
     */
    public static String rateToString(int rate) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(rate % 10 == 0 ? 1 : 2);
        return nt.format(rate / 10000D);
    }
    int DEFAULT_SCALE = 2;

    MathContext DEFAULT_MATHCONTEXT = MathContext.DECIMAL64;

    BigDecimal ZERO = new BigDecimal(BigInteger.ZERO, DEFAULT_SCALE, DEFAULT_MATHCONTEXT);
    
    /**
     * 默认使用银行家舍入法
     */
    RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

}

