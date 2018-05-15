package com.zw.rule.util;

import com.zw.rule.po.Pager;

import java.io.Serializable;
import java.util.List;

public class PagerJsonResponse<T extends Serializable> {
    private int page;
    private int total;
    private int records;
    private int pagecount;
    private List<T> rows;

    public PagerJsonResponse() {
    }

    public PagerJsonResponse(List<T> rows, Pager pager) {
        this.rows = rows;
        this.total = pager.getTotalResult();
        this.records = pager.getTotalResult();
        this.page = pager.getPage();
        this.pagecount = pager.getPageCount();
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return this.records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPagecount() {
        return this.pagecount;
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }
}

