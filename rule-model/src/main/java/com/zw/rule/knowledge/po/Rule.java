package com.zw.rule.knowledge.po;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class Rule implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String code;
    private String desc;
    private Integer priority;
    private Long parentId;
    private Long userId;
    private Long creator;
    private String authorName;
    private Long orgId;
    private Long engineId;
    private Integer type;
    private Integer isNegation;
    private Integer status;
    public int ruleAudit;
    private List<RuleField> ruleFieldList;
    private List<RuleContent> ruleContentList;
    private Date createTime;
    private Date updateTime;
    public String content;
    private Integer ruleType;
    private Integer score;
    private String lastLogical;
    private String engineName;

    public String getEngineNodeName() {
        return engineNodeName;
    }

    public void setEngineNodeName(String engineNodeName) {
        this.engineNodeName = engineNodeName;
    }

    public Long getEngineNodeId() {
        return engineNodeId;
    }

    public void setEngineNodeId(Long engineNodeId) {
        this.engineNodeId = engineNodeId;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    private String engineNodeName;
    private Long engineNodeId;
    private int showType = 0;
    public Rule() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRuleAudit() {
        return this.ruleAudit;
    }

    public void setRuleAudit(int ruleAudit) {
        this.ruleAudit = ruleAudit;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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

    public Long getCreator() {
        return this.creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

    public List<RuleField> getRuleFieldList() {
        return this.ruleFieldList;
    }

    public void setRuleFieldList(List<RuleField> ruleFieldList) {
        this.ruleFieldList = ruleFieldList;
    }

    public List<RuleContent> getRuleContentList() {
        return this.ruleContentList;
    }

    public void setRuleContentList(List<RuleContent> ruleContentList) {
        this.ruleContentList = ruleContentList;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsNegation() {
        return this.isNegation;
    }

    public void setIsNegation(Integer isNegation) {
        this.isNegation = isNegation;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getRuleType() {
        if(this.ruleAudit == 2) {
            this.ruleType = Integer.valueOf(0);
        } else {
            this.ruleType = Integer.valueOf(1);
        }

        return this.ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getLastLogical() {
        return this.lastLogical;
    }

    public void setLastLogical(String lastLogical) {
        this.lastLogical = lastLogical;
    }

    public String getEngineName() {
        return this.engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public String toString() {
        return "Rule{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", priority=" + priority +
                '}';
    }
}
