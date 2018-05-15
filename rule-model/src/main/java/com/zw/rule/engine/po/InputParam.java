package com.zw.rule.engine.po;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public class InputParam {
    private Map<String, Object> inputParam;
    private List<Result> result;

    public InputParam() {
    }

    public Map<String, Object> getInputParam() {
        return this.inputParam;
    }

    public void setInputParam(Map<String, Object> inputParam) {
        this.inputParam = inputParam;
    }

    public List<Result> getResult() {
        return this.result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
