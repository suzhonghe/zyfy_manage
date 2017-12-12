package com.zhongyang.java.biz;

import com.zhongyang.java.pojo.User;
import com.zhongyang.java.vo.InvestRecordVo;
import com.zhongyang.java.vo.PagerVO;

/**
 * 
* @Title: AwardManagerBiz.java 
* @Package com.zhongyang.java.biz 
* @Description:奖励管理接口 
* @author 苏忠贺   
* @date 2016年3月11日 下午5:21:25 
* @version V1.0
 */
public interface AwardManagerBiz {

	public InvestRecordVo queryByParams(PagerVO<User>pager);
}
