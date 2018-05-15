package com.zw.rule.engine.po;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */

public class DecisionOptions {
    private String code;
    private String name;
    private Map<String, Object> inFields;
    private Map<String, Object> outFields;

    public Integer getfType() {
        return fType;
    }

    public void setfType(Integer fType) {
        this.fType = fType;
    }

    private Integer fType;

    public Long getNodId() {
        return nodId;
    }

    public void setNodId(Long nodId) {
        this.nodId = nodId;
    }

    public String getFieldScope() {
        return fieldScope;
    }

    public void setFieldScope(String fieldScope) {
        this.fieldScope = fieldScope;
    }

    private Long nodId;
    private String fieldScope;
    public DecisionOptions() {
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

    public Map<String, Object> getInFields() {
        return this.inFields;
    }

    public void setInFields(Map<String, Object> inFields) {
        this.inFields = inFields;
    }

    public Map<String, Object> getOutFields() {
        return this.outFields;
    }

    public void setOutFields(Map<String, Object> outFields) {
        this.outFields = outFields;
    }

    @Override
    public String toString() {
        return "DecisionOptions{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", inFields=" + inFields +
                ", outFields=" + outFields +
                '}';
    }
}
