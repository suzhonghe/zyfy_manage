package com.zhongyang.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.CmsColumnBiz;
import com.zhongyang.java.pojo.CmsColumn;
import com.zhongyang.java.sys.uitl.Authorities;
import com.zhongyang.java.sys.uitl.FireAuthority;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.vo.CmsColumnVO;

/**
 * 
* @Title: CmsColumnController.java 
* @Package com.zhongyang.java.controller 
* @Description:栏目控制器
* @author 苏忠贺   
* @date 2016年3月3日 上午10:46:58 
* @version V1.0
 */
@Controller
public class CmsColumnController extends BaseController{
	
	@Autowired
	private CmsColumnBiz cmsColumnBiz;
	
	@FireAuthority(authorities=Authorities.COLUMNADD)
	@RequestMapping(value="/addCmsColumn")
	public @ResponseBody Message addCmsColumn(CmsColumn cmsColumn){
		return cmsColumnBiz.addCmsColumn(cmsColumn);
	}
	
	@FireAuthority(authorities=Authorities.COLUMNUPD)
	@RequestMapping(value="/modifyCmsColumnByParams")
	public @ResponseBody Message modifyByParams(CmsColumn cmsColumn){
		return cmsColumnBiz.modifyByParams(cmsColumn);
	}
	
	@FireAuthority(authorities=Authorities.COLUMNDEL)
	@RequestMapping(value="/deleteCmsColumnByParams")
	public @ResponseBody Message deleteByParams(CmsColumn cmsColumn){
		return cmsColumnBiz.deleteByParams(cmsColumn);
	}
	
	@FireAuthority(authorities=Authorities.COLUMNLIST)
	@RequestMapping(value="/queryCmsColumnByParams")
	public @ResponseBody List<CmsColumnVO> queryByParams(CmsColumn cmsColumn){
		return cmsColumnBiz.queryByParams(cmsColumn);
	}
}
