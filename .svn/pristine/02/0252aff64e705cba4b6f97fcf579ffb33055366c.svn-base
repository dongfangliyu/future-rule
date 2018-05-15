package com.zw.rule.core;


import com.zw.rule.mybatis.page.Page;

public class Response implements ResponseCode {

    private Object data;
    
    private Object status;//用于兼容老的服务字段

    private Object result;//用于兼容老的服务字段

    private Object score;//用于兼容老的服务字段

    private Page page;

    //默认为0表示响应正常
    private int code = 0;

    private String msg;

    public Response() {
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public Response(Object data) {
        this.data = data;
    }

    public Response(Object data, Page page) {
        this.data = data;
        this.page = page;
    }

    public Response(String msg) {
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
