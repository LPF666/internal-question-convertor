package com.eebbk.internal.question.convertor.vo;

public class Page {

	/**
	 * 当前页
	 */
	private int page;
	
	/**
	 * 每页显示的记录数
	 */
	private int rows;
	
	/**
	 * 排名字段
	 */
	private String sort;
	
	/**
	 * 按什么排序 asc desc
	 */
	private String order;
	
	private int start;
	
	

	public int getStart() {
		if(page == 1){
			start =0;
		}else{
			start = (page-1)*rows;
		}
		
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
