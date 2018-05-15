package com.zw.rule.datamanage.po;

import com.zw.rule.base.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shengkf on 2017/5/4.
 */
public class FieldTypeUser extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer fieldTypeId; //字段类型编号
    private Long organId; //归属的组织编号
    private Integer engineId;  //引擎表主键id，用来与引擎绑定，该字段为空代表组织内通用字段
    private Long userId; //创建该字段类型的用户编号
    private Date createTime;  //该字段分类的创建时间

    public FieldTypeUser() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFieldTypeId() {
        return this.fieldTypeId;
    }

    public void setFieldTypeId(Integer fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    public Long getOrganId() {
        return this.organId;
    }

    public void setOrganId(Long organId) {
        this.organId = organId;
    }

    public Integer getEngineId() {
        return this.engineId;
    }

    public void setEngineId(Integer engineId) {
        this.engineId = engineId;
    }

    public Long getUserId() {
        return this.userId;
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
}
