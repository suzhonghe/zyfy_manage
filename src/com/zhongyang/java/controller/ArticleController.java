package com.zhongyang.java.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhongyang.java.biz.ArticleBiz;
import com.zhongyang.java.pojo.Article;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ArticleVO;

/**
 * 
* @Title: ArticleController.java 
* @Package com.zhongyang.java.controller 
* @Description:CMS文章控制器
* @author 苏忠贺   
* @date 2016年3月3日 下午4:04:24 
* @version V1.0
 */
@Controller
public class ArticleController extends BaseController{
	
	@Autowired
	private ArticleBiz articleBiz;
	
	@RequestMapping(value="/addArticle")
	public @ResponseBody Message addArticle(Article article){
		return articleBiz.addArticle(article);
	}
	
	@RequestMapping(value="/modifyArticleByParams")
	public @ResponseBody Message modifyByParams(Article article){
		return articleBiz.modifyByParams(article);
	}
	
	@RequestMapping(value="/deleteArticleByParams")
	public @ResponseBody Message deleteByParams(Article article){
		return articleBiz.deleteByParams(article);
	}
	
	@RequestMapping(value="/queryArticleByParams")
	public @ResponseBody Page<ArticleVO> queryByParams(Page<ArticleVO> page){
		return articleBiz.queryByParams(page);
	}
	
	//文章位置移动
	@RequestMapping("/posDisplace")
	@ResponseBody
	public Message changePlace(HttpServletRequest request){
		String[] str = request.getParameterValues("str[]");
		return articleBiz.changePlace(str);
	}
	
	@RequestMapping("/editArticle")
	@ResponseBody
	public Article getArticleById(String id){
		return articleBiz.getArticleById(id);
	}
	
}
