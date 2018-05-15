package com.zw.rule.base;

/**
 * Created by zhangtao on 2017/5/4 0004.
 */
public class BasePage {
    private int page;
    private int rows;
    private Integer curRow;
    private Integer endRow;
    private Integer total;

    public BasePage() {
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setPagination(int page, int rows) {
        this.page = page;
        this.rows = rows;
        this.curRow = Integer.valueOf((page - 1) * rows);
        this.endRow = Integer.valueOf(page * rows);
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCurRow(Integer curRow) {
        this.curRow = curRow;
    }

    public void setEndRow(Integer endRow) {
        this.endRow = endRow;
    }
}
