package com.zw.rule.engine.po;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/24.
 */
public class ComplexRule {
    private Map<String, Object> result;
    private String out;
    private Map<String, Object> returnResult;

    public ComplexRule() {
    }

    public Map<String, Object> getReturnResult() {
        return this.returnResult;
    }

    public void setReturnResult(Map<String, Object> returnResult) {
        this.returnResult = returnResult;
    }

    public Map<String, Object> getResult() {
        return this.result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public String getOut() {
        return this.out;
    }

    public void setOut(String out) {
        this.out = out;
    }
}
