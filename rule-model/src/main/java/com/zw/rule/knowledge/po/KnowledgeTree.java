package com.zw.rule.knowledge.po;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017/5/11
 ********************************************************/
public class KnowledgeTree implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Long parentId;
    private Long userId;
    private Long orgId;
    private Long engineId;
    private Date createTime;
    private Integer type;
    private Integer treeType;
    private Integer status;
    private Date updateTime;
    private KnowledgeTree[] children;
    private String isParent = "true";
    private String icon = "";
    private String isLastNode = "";
    private Integer directoryType;

    public KnowledgeTree() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getEngineId() {
        return this.engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    @JsonIgnore
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonIgnore
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public KnowledgeTree[] getChildren() {
        return this.children;
    }

    public void setChildren(KnowledgeTree[] children) {
        this.children = children;
    }

    public String getIsParent() {
        return this.isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public Integer getTreeType() {
        return this.treeType;
    }

    public void setTreeType(Integer treeType) {
        this.treeType = treeType;
    }

    public String getIcon() {
        if(!Integer.valueOf(2).equals(this.treeType) && !Integer.valueOf(3).equals(this.treeType)) {
            this.icon = "../resource/images/authority/folder.png";
        } else {
            this.icon = "../resource/images/datamanage/cabage.png";
            this.isLastNode = "true";
        }

        return this.icon;
    }

    public Integer getDirectoryType() {
        return this.directoryType = this.type;
    }

    public String getIsLastNode() {
        if(Integer.valueOf(2).equals(this.treeType) || Integer.valueOf(3).equals(this.treeType)) {
            this.isLastNode = "true";
        }

        return this.isLastNode;
    }

    @Override
    public String toString() {
        return "KnowledgeTree{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", icon='" + icon + '\'' +
                '}';
    }
}
