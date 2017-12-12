package com.zhongyang.java.biz.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhongyang.java.biz.ArticleBiz;
import com.zhongyang.java.pojo.Article;
import com.zhongyang.java.service.ArticleService;
import com.zhongyang.java.system.Message;
import com.zhongyang.java.system.SystemEnum;
import com.zhongyang.java.system.Exception.UException;
import com.zhongyang.java.system.page.Page;
import com.zhongyang.java.vo.ArticleVO;

@Service
public class ArticleBizImpl implements ArticleBiz {

	@Autowired
	private ArticleService articleService;

	@Override
	@Transactional
	public Message addArticle(Article article) {
		Message message = new Message();
		try {
			article.setId(UUID.randomUUID().toString().toUpperCase());
			article.setSubmitTime(new Date());
			articleService.addArticle(article);
			message.setCode(1);
			message.setMessage("文章发布成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "文章发布失败");
		}
	}

	@Override
	@Transactional
	public Message modifyByParams(Article article) {
		Message message = new Message();
		try {
			Article art =articleService.getArticleById(article.getId());
			if(art!=null && !art.getColumnId().equals(article.getColumnId())){
				article.setIndex(50);
			}
			articleService.modifyByParams(article);
			message.setCode(1);
			message.setMessage("文章修改成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "文章修改失败");
		}
	}

	@Override
	@Transactional
	public Message deleteByParams(Article article) {
		Message message = new Message();
		try {
			if (article.getId() != null) {
				articleService.deleteByParams(article);
			}
			message.setCode(1);
			message.setMessage("删除成功");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "文章删除失败");
		}
	}

	@Override
	public Page<ArticleVO> queryByParams(Page<ArticleVO> page) {
		try {
			
			List<ArticleVO> articleVOs = new ArrayList<ArticleVO>();
			List<Article> articles = articleService.queryByParams(page);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (Article art : articles) {
				ArticleVO articleVO = new ArticleVO();
				articleVO.setTitle(art.getTitle());
				articleVO.setId(art.getId());
				articleVO.setColumnName(art.getColumnName());
				articleVO.setSubmitTime(sdf.format(art.getSubmitTime()));
				articleVOs.add(articleVO);
			}
			page.setResults(articleVOs);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "查询文章失败");
		}
	}

	@Override
	@Transactional
	public Message changePlace(String[] str) {
		// List<String> list = new ArrayList<String>();
		// Collections.addAll(list, str);
		try {
			Article arts = articleService.getArticleById(str[0]);
			articleService.updateAllArtIndex(arts.getColumnId());
			Message message = new Message();
			Article art = new Article();
			for (int i = 0; i < str.length; i++) {
				art.setId(str[i].trim());
				art.setIndex((i + 1));
				articleService.modifyByParams(art);
			}
			message.setCode(1);
			message.setMessage("修改文章位置成功！");
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UException(SystemEnum.UNKNOW_EXCEPTION, "修改文章位置失败！");
		}
	}

	@Override
	public Article getArticleById(String id) {
		return articleService.getArticleById(id);
	}

}
