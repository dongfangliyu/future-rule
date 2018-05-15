package com.zw.rule.datamanage.po;

import com.zw.rule.base.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 * 黑白名单实体
 * @author zongpeng
 * @Time 2017-5-4
 */
public class ListDb extends BasePage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String listType;//名单库区分,黑名单:b,白名单:w

    private String listName;//名单库名称

    private Integer dataSource;//数据来源：外部黑(白)名单:1、内部黑(白)名单:2、待选:0

    private String listAttribute;//名单库类型属性

    private String listDesc;//名单库描述

    private String maintenance;//名单库表中列字段，字段id逗号分隔

    private Integer matchType;//检索匹配类型，精确匹配:1，模糊匹配:0

    private Integer queryType;//查询字段间逻辑，and:1，or:0

    private String queryField;//查询主键，字段编号逗号分割

    private String queryFieldCn;//查询主键名

    private Long orgId;//组织id

    private Integer status;//名单状态，启用:1，停用:0，删除:-1

    private Long userId;//创建该名单的用户编号

    private Date createTime;//创建时间

    private String nickName;//昵称

    public ListDb() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getListAttribute() {
        return listAttribute;
    }

    public void setListAttribute(String listAttribute) {
        this.listAttribute = listAttribute;
    }

    public String getListDesc() {
        return listDesc;
    }

    public void setListDesc(String listDesc) {
        this.listDesc = listDesc;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public String getQueryField() {
        return queryField;
    }

    public void setQueryField(String queryField) {
        this.queryField = queryField;
    }

    public String getQueryFieldCn() {
        return queryFieldCn;
    }

    public void setQueryFieldCn(String queryFieldCn) {
        this.queryFieldCn = queryFieldCn;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}