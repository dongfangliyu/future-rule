package com.zw.rule.jeval.tools;

import java.util.Collections;
import java.util.List;

public class ListPageUtil<T> {
    private List<T> data;
    private int lastPage;
    private int nowPage;
    private int nextPage;
    private int pageSize;
    private int totalPage;
    private int totalCount;

    public int getPageSize() {
        return this.pageSize;
    }

    public List<T> getData() {
        return this.data;
    }

    public int getLastPage() {
        return this.lastPage;
    }

    public int getNowPage() {
        return this.nowPage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public ListPageUtil(List<T> data, int nowPage, int pageSize) {
        if(data != null && !data.isEmpty()) {
            this.data = data;
            this.pageSize = pageSize;
            this.nowPage = nowPage;
            this.totalCount = data.size();
            this.totalPage = (this.totalCount + pageSize - 1) / pageSize;
            this.lastPage = nowPage - 1 > 1?nowPage - 1:1;
            this.nextPage = nowPage >= this.totalPage?this.totalPage:nowPage + 1;
        } else {
            throw new IllegalArgumentException("data must be not empty!");
        }
    }

    public List<T> getPagedList() {
        int fromIndex = (this.nowPage - 1) * this.pageSize;
        if(fromIndex >= this.data.size()) {
            return Collections.emptyList();
        } else if(fromIndex < 0) {
            return Collections.emptyList();
        } else {
            int toIndex = this.nowPage * this.pageSize;
            if(toIndex >= this.data.size()) {
                toIndex = this.data.size();
            }

            return this.data.subList(fromIndex, toIndex);
        }
    }
}

