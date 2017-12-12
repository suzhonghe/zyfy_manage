package com.zhongyang.java.dao;

import java.util.List;

import com.zhongyang.java.pojo.Article;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ArticleVO;

/**
 * 
* @Title: ArticleDao.java 
* @Package com.zhongyang.java.dao 
* @Description: 
* @author 苏忠贺   
* @date 2016年3月3日 下午1:34:23 
* @version V1.0
 */
public interface ArticleDao {

	public int insertArticle(Article article)throws Exception;
	
	public int updateByParams(Article article)throws Exception;
	
	public void deleteByParams(Article article)throws Exception;
	
	public List<Article> selectByParams(Page<ArticleVO> page)throws Exception;
	
	public Article getArticleById(String id);
	//调整文章顺序之前先删除原有序号
	public void updateAllArtIndex(String columnId);
}
