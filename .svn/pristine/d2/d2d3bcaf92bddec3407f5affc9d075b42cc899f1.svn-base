package com.zw.rule.po;



public class Pager {
    private int rows = 10;
    private int totalResult;
    private int page;
    private String sortField;
    private String order;

    public Pager() {
    }

    public Pager(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return (this.totalResult + this.rows - 1) / this.rows;
    }

    public int getTotalResult() {
        return this.totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getCurrentResult() {
        return this.page == 0?0:(this.page - 1) * this.rows;
    }

    public String getSortField() {
        return this.sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPageCount() {
        boolean pagecount = false;
        int pagecount1;
        if(this.totalResult % this.rows == 0) {
            pagecount1 = this.totalResult / this.rows;
        } else {
            pagecount1 = this.totalResult / this.rows + 1;
        }

        return pagecount1;
    }
}

