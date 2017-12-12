package com.zhongyang.java.vo;

import java.util.Date;
import java.util.List;


public class PagerVO<T> {
	private int totalPage;//总页数
	private int start=1;//当前页，位置从0开始
	private int length = 10;//页面显示最大记录数
	private String field;//查询的字段名称
	private String value;//查询的内容,按模糊查询
	private String sort;//排序类型，asc/desc
    private long startTime;
    private long endTime;
	private int recordsTotal;//总记录数 
    private int recordsFiltered;//数据过滤
    private List<T> data;//对应的当前页记录  
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * @return
	 * 返回总记录数
	 */
	public int getRecordsTotal() {
		return recordsTotal;
	}
	/**
	 * @param recordsTotal
	 */
	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	/**
	 * @return
	 * 返回过滤总数
	 */
	public int getRecordsFiltered() {
		return recordsFiltered;
	}
	/**
	 * @param recordsTotal
	 */
	public void setRecordsFiltered(int recordsTotal) {
		this.recordsFiltered = recordsTotal;
	}
	/**
	 * @return
	 * 返回结果集
	 */
	public List<T> getData() {
		return data;
	}
	/**
	 * @param data
	 */
	public void setData(List<T> data) {
		this.data = data;
	}
	/**
	 * @return Sort
	 * 返回排序类型，asc/desc
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * @param sort
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * @return
	 * 当前页，位置从0开始
	 */
	public int getStart() {
		return start;
	}
	/**
	 * @param start
	 */
	public void setStart(int start) {
		this.start = start;
	}
	/**
	 * @return
	 * 页面显示最大记录数
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * @return
	 * 查询的字段名称
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * @return
	 * 查询的内容,按模糊查询
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	public PagerVO() {}
	/**
	 * @param start
	 * @param length
	 * @param field
	 * @param value
	 */
	public PagerVO(int start, int length, String field, String value) {
		super();
		this.start = start;
		this.length = length;
		this.field = field;
		this.value = value;
	}

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
	
	
	
}
