package com.zw.rule.knowledge.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class RuleField implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String logicalSymbol;//逻辑符号
    private String cnName;//字段中文名
    private String operator;//逻辑运算符
    private String fieldValue;//字段值
    private Long ruleId;//规则id
    private String fieldId;//字段id和字段英文名|拼接
    private String enName;//字段英文名
    private Integer valueType;//字段存值类型,1：数值型,2：字符型,3：枚举型
    private String restrainScope;//字段约束范围
    private String[] values;

    public RuleField() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogicalSymbol() {
        return this.logicalSymbol;
    }

    public void setLogicalSymbol(String logicalSymbol) {
        this.logicalSymbol = logicalSymbol;
    }

    public String getCnName() {
        return this.cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Long getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
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
