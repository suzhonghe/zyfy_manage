package com.zhongyang.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.TimerBiz;
import com.zhongyang.java.system.Message;
/**
 * 
* @Title: TimerTaskController.java 
* @Package com.zhongyang.java.controller 
* @Description:定时任务控制器 
* @author 苏忠贺   
* @date 2015年12月30日 下午6:09:35 
* @version V1.0
 */
@Scope("prototype")
@Controller
public class TimerTaskController extends BaseController{
	
	@Autowired
	private TimerBiz timerBiz;
	/**
	 * 
	* @Title: timerBidPublished 
	* @Description:定时发标
	* @return Message    返回类型 
	* @throws
	 */
	@RequestMapping(value="/timerBidPublished", method = RequestMethod.POST)
	public @ResponseBody Message timerBidPublished(String loanId,String publishedTime){
		return timerBiz.timerBidPublished(loanId, publishedTime);
	}
}
