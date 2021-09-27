package com.jinzhi.bean;

// 这是分页要用到的所有数据，在BookDao中的设置分页功能时会用到

import java.util.List;

public class Pager<T> {
	
	private int nowPage;                 // 当前页
	
	private int sumRow;                  // 总记录数
	
	private int pageSize = 5;            // 每页显示多少条
	
	private int sumPage;                 // 总页数
	
	private List<T> pageData;            // 当前页应该存的书籍数据

	
	
	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getSumRow() {
		return sumRow;
	}

	public void setSumRow(int sumRow) {
		this.sumRow = sumRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getSumPage() {
		return sumPage;
	}

	public void setSumPage(int sumPage) {
		this.sumPage = sumPage;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	
}