package com.zw.rule.demo.pojo;

import com.zw.rule.base.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 * 自定义名单实体
 * @author zongpeng
 * @Time 2017-6-21
 */
public class CustomDb extends BasePage implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long engineId;//引擎id

    private Date createTime;

    private Long createBy;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getEngineId() {
        return engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
}
