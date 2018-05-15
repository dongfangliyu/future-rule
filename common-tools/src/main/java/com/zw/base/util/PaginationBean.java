package com.zw.base.util;

import java.io.Serializable;
/**
 * 分页Bean
 * @author new
 *
 */
public class PaginationBean implements Serializable {
	private static final long serialVersionUID = 3561793419970358011L;
	/**
	 * 总记录数
	 */
	private long resultCount; 
	/**
	 * 总页数
	 */
	private long pageCount;		
	/**
	 * 每页的大小
	 */
	private int pageSize;	   
	/**
	 * 当前页
	 */
	private int pageNum;		
	/**
	 * 每页开始
	 */
	private int beginRow;		
	/**
	 * 每页结束
	 */
	private int endRow;			
	
	public int getBeginRow() {
		return beginRow;
	}
	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getResultCount() {
		return resultCount;
	}
	public void setResultCount(long resultCount) {
		this.resultCount = resultCount;
	}
}
