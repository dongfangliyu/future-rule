package com.zw.rule.knowledge.po;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class ScorecardRuleContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long scorecardId;
    private String cnName;
    private String enName;
    private String fieldValue;
    private Long fieldId;
    private Integer valueType;
    private String restrainScope;
    private String[] values;
    private ScoreCardJson sCardJson;

    public ScorecardRuleContent() {
    }

    public String getRestrainScope() {
        return restrainScope;
    }

    public void setRestrainScope(String restrainScope) {
        this.restrainScope = restrainScope;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScorecardId() {
        return this.scorecardId;
    }

    public void setScorecardId(Long scorecardId) {
        this.scorecardId = scorecardId;
    }


    public String getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Long getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getValueType() {
        return this.valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }


    public String[] getValues() {
        if(Integer.valueOf(3).equals(this.valueType)) {
            this.values = this.restrainScope.split(",");
        } else {
            this.values = new String[]{this.restrainScope};
        }

        return this.values;
    }

    public ScoreCardJson getsCardJson() {
        this.sCardJson = (ScoreCardJson) JSONObject.parseObject(this.fieldValue, ScoreCardJson.class);
        return this.sCardJson;
    }

}
