package com.zw.rule.engine.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public class Engine implements Serializable, Comparable<Engine> {
    private static final long serialVersionUID = -2525580015610495943L;
    private Long id;
    private String code;
    private String name;
    private String desc;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private Long creator;
    private Long userId;
    private Long orgId;
    private String searchString;
    private List<EngineVersion> engineVersionList;
    private String jurisdiction;
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Engine() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getJurisdiction() {
        return this.jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreator() {
        return this.creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public List<EngineVersion> getEngineVersionList() {
        return this.engineVersionList;
    }

    public void setEngineVersionList(List<EngineVersion> engineVersionList) {
        this.engineVersionList = engineVersionList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public int compareTo(Engine o) {
        return this == o?0:(o != null && o instanceof Engine?(this.id.longValue() <= o.id.longValue()?1:-1):1);
    }

    @Override
    public String toString() {
        return "Engine{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
