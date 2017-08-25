package com.dayuanit.atm.util;

import java.util.List;

public class PageUtils<T> {
	
	public static final int PRE_PAGE_NUM = 2;
	
	private int currentPageNum;
	
	private int totalPageNum;
	
	private List<T> data;
	
	public PageUtils() {
		
	}
	
	public PageUtils(int currentPageNum, int total) {
		this.currentPageNum = currentPageNum;
		this.totalPageNum = getTotalPageNum(total);
	}
		
	public int getTotalPageNum(int total) {
		return (total % PRE_PAGE_NUM) == 0 ? total / PRE_PAGE_NUM : (total / PRE_PAGE_NUM + 1);
	}
	
	public int getSetOff() {
		return (currentPageNum - 1) * PRE_PAGE_NUM;
	}
	
	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
