package com.zhongyang.java.vo;


/**
 * 
* @Title: Article.java 
* @Package com.zhongyang.java.pojo 
* @Description:文章实体
* @author 苏忠贺   
* @date 2016年3月3日 下午1:24:04 
* @version V1.0
 */
public class ArticleVO {

	private String id;//文章ID
	private String columnName;//所属栏目名称
	
	private String title;//文章标题
	
	private String submitTime;//发布时间

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

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
