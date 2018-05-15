package com.zw.rule.knowledge.po;

import java.io.Serializable;

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
public class RuleContent implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String cnName;
    private String fieldValue;
    private String fieldId;
    private Long ruleId;
    private String enName;
    private Integer valueType;
    private String restrainScope;
    private String[] values;

    public RuleContent() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnName() {
        return this.cnName;
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

    public String getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public Long getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getValueType() {
        return this.valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public String getRestrainScope() {
        return this.restrainScope;
    }

    public void setRestrainScope(String restrainScope) {
        this.restrainScope = restrainScope;
    }

    public String[] getValues() {
        if(Integer.valueOf(3).equals(this.valueType)) {
            this.values = this.restrainScope.split(",");
        } else {
            this.values = new String[]{this.restrainScope};
        }

        return this.values;
    }

    public String getEnName() {
        return this.enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
