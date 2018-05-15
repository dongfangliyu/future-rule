package com.zw.rule.datamanage.po;

import java.io.Serializable;

/**
 * 表字段信息实体
 * @author zongpeng
 * @Time 2017-5-4
 */
public class TblColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    private String colName;//字段名

    private String cnName;

    private String fieldValue;

    private String colComment;//字段描述

    private String colOrder;//第几列

    public TblColumn() {
    }

    public String getColName() {
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColComment() {
        return this.colComment;
    }

    public void setColComment(String colComment) {
        this.colComment = colComment;
    }

    public String getColOrder() {
        return this.colOrder;
    }

    public void setColOrder(String colOrder) {
        this.colOrder = colOrder;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    @Override
    public String toString() {
        return "TblColumn{" +
                "colName='" + colName + '\'' +
                ", cnName='" + cnName + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                ", colComment='" + colComment + '\'' +
                ", colOrder='" + colOrder + '\'' +
                '}';
    }
}
