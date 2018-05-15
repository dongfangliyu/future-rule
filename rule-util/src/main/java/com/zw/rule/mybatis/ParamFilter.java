package com.zw.rule.mybatis;


import com.zw.rule.mybatis.page.Page;

import java.util.Map;


public class ParamFilter {

    private Page page;

    private Map<String,Object> param;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ParamFilter{" +
                "page=" + page +
                ", param=" + param +
                '}';
    }
}
