package com.zw.rule.datamanage.po;

import java.io.Serializable;

/**
 * Created by zhangtao on 2017/5/4 0004.
 */
public class FieldSubCondVo implements Serializable {
    private Integer fieldId;
    private String operator;
    private String fieldValue;
    private String logical;
    private Integer valueType;
    private String valueScope;
    private String[] values;
    private String fieldCn;

    public FieldSubCondVo() {
    }

    public Integer getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
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

    public String getLogical() {
        return this.logical;
    }

    public void setLogical(String logical) {
        this.logical = logical;
    }

    public Integer getValueType() {
        return this.valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public String getValueScope() {
        return this.valueScope;
    }

    public void setValueScope(String valueScope) {
        this.valueScope = valueScope;
    }

    public String[] getValues() {
        if(this.valueType.intValue() == 3) {
            this.values = this.valueScope.split(",");
        } else {
            this.values = new String[]{this.valueScope};
        }

        return this.values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String getFieldCn() {
        return this.fieldCn;
    }

    public void setFieldCn(String fieldCn) {
        this.fieldCn = fieldCn;
    }
}
