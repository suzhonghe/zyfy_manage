package com.zhongyang.java.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhongyang.java.dao.ArticleDao;
import com.zhongyang.java.pojo.Article;
import com.zhongyang.java.service.ArticleService;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ArticleVO;
/**
 * 
* @Title: ArticleServiceImpl.java 
* @Package com.zhongyang.java.service.impl 
* @Description:业务实现
* @author 苏忠贺   
* @date 2016年3月3日 下午2:39:18 
* @version V1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public int addArticle(Article article) throws Exception {
		return articleDao.insertArticle(article);
	}

	@Override
	public int modifyByParams(Article article) throws Exception {
		return articleDao.updateByParams(article);
	}

	@Override
	public void deleteByParams(Article article) throws Exception {
		articleDao.deleteByParams(article);
	}

	@Override
	public List<Article> queryByParams(Page<ArticleVO> page) throws Exception {
		return articleDao.selectByParams(page);
	}

	@Override
	public Article getArticleById(String id) {
		return articleDao.getArticleById(id);
	}

	@Override
	public void updateAllArtIndex(String columnId) {
		articleDao.updateAllArtIndex(columnId);
		
	}

}
