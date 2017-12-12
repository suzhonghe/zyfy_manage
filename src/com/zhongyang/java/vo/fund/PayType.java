package com.zhongyang.java.vo.fund;
/**
* @author 作者:zhaofq
* @version 创建时间：2016年1月11日 下午2:26:05
* 类说明
*/
public enum PayType  implements BaseEnum {
	/**
     * 借记卡网银
     */
    B2CDEBITBANK("借记卡网银"),
    /**
     * 企业网银
     */
    B2BBANK("企业网银"),
    /**
     * 借记卡快捷
     */
    DEBITCARD("借记卡快捷");
    
    private final String key;
    
    private PayType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }
}
