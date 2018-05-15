package com.zw.rule.mybatis.page;

public class Page{
	public static final String KEY_PAGE = "page.pageNo";
	public static final String KEY_PAGESIZE = "page.pageSize";

	public static int MAX_RECORDS = 1000000; // 100w
	public static int DEFAULT_PAGESIZE = 10;

	private int resultCount; // 总记录数, 可写
	private int pageSize; // 每页记录数, 初始化时传入
	private int firstIndex; // 当前页的第一条记录在所有记录中的位置
	private int lastIndex;

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public Page() {
	}

	public int getPageSize( ) {
		return pageSize;
	}

	public void setPageSize( int pageSize ) {
		if ( pageSize < 1 || pageSize > MAX_RECORDS ){
			return;
		}
		this.pageSize = pageSize;
	}

	public int getResultCount( ) {
		return resultCount;
	}

	public void setResultCount( int resultCount ) {
		this.resultCount = resultCount;
	}

	public int getFirstIndex( ) {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public void setDefaultPageSize( int defaultPageSize ) {
		Page.DEFAULT_PAGESIZE = defaultPageSize;
	}

	@Override
	public String toString() {
		return "Page{" +
				"firstIndex=" + firstIndex +
				", pageSize=" + pageSize +
				'}';
	}
}
