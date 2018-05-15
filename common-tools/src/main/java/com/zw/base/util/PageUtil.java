package com.zw.base.util;
public class PageUtil {
	public PageUtil(){}

	/**
	 * 封装标准维护列表页面分页bean
	 * @return
	 */
	public PaginationBean getPageBean(long count,int pageNum){
		PaginationBean bean = new PaginationBean();
		bean.setResultCount(count);   //总记录
		bean.setPageCount(getPageCount(count));  //总页数
		bean.setPageSize(Consts.PAGE_SIZE); //每页大小
		bean.setBeginRow(getBegin(pageNum));   //当前页的起始行
		bean.setEndRow(getEnd(pageNum));		//当前页的结束行
		bean.setPageNum(pageNum);				//当前页
		return bean;
	}
	
	/**
	 * 返回分页开始的位置(应用于作业管理)
	 * @param pageBean
	 * @return
	 */
	public static int getBegin(int pageNum){
		int begin = 0;
		int size = Consts.PAGE_SIZE;
		begin = size*(pageNum-1);		
		return begin;
	}
	/**
	 * 返回分页结束的位置(应用于作业管理)
	 * @param pageBean
	 * @return
	 */
	public static int getEnd(int pageNum){
		int end = 0;
		int size = Consts.PAGE_SIZE;
		end = size*pageNum;
		return end;
	}
	/**
	 * 计算总页数每页10条
	 * @param resultCount
	 * @return
	 */
	public long getPageCount(long resultCount){
		long pageCount = 0;
		int size = Consts.PAGE_SIZE;
		if((resultCount % size) == 0 ){
			pageCount = resultCount/size;
		}else{
			pageCount = resultCount/size + 1;
		}
		return pageCount;
	}
}


