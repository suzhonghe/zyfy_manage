package com.zhongyang.java.pojo;

import java.util.Date;

/**
 * 
* @Title: Article.java 
* @Package com.zhongyang.java.pojo 
* @Description:文章实体
* @author 苏忠贺   
* @date 2016年3月3日 下午1:24:04 
* @version V1.0
 */
public class Article {
	
	private String id;//ID
	
	private String columnId;//所属栏目ID

	private String columnName;//所属栏目名称
	
	private String title;//文章标题
	
	private String author;//文章作者
	
	private String source;//来源
	
	private String sourceLink;//来源链接
	
	private String content;//文章内容
	
	private Date submitTime;//发布时间
	
	private int index;//文章位置序号
	

	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceLink() {
		return sourceLink;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
}
