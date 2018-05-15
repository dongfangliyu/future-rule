package com.zw.rule.datamanage.po;

import com.zw.rule.base.BaseEntity;
import com.zw.rule.base.BasePage;

import java.io.Serializable;

/**
 * Created by shengkf on 2017/5/3.
 */
public class FieldType extends BasePage implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;  //字段类型编号
    private String fieldType; //字段类型名
    private Integer parentId; //父节点编号
    private Integer isCommon; //是否组织定义的通用字段类型
    private FieldType[] children;
    private String isParent = "true";
    private Integer engineId; //引擎id
    private String icon;

    public FieldType() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsCommon() {
        return this.isCommon;
    }

    public void setIsCommon(Integer isCommon) {
        this.isCommon = isCommon;
    }

    public FieldType[] getChildren() {
        return this.children;
    }

    public void setChildren(FieldType[] children) {
        this.children = children;
    }

    public String getIsParent() {
        return this.isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public Integer getEngineId() {
        return this.engineId;
    }

    public void setEngineId(Integer engineId) {
        this.engineId = engineId;
    }

    public String getIcon() {
        this.icon = "../resource/images/authority/folder.png";
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
