package com.zw.rule.engine.po;

/**
 * Created by Administrator on 2017/5/12.
 */
public class Sandbox {
    private Integer sandbox;
    //比例
    private Integer proportion;
    private String nextNode;
    private Integer sum;
    private Integer startNumber;
    private Integer endNumberl;

    public Sandbox() {
    }

    public Integer getSum() {
        return this.sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getStartNumber() {
        return this.startNumber;
    }

    public void setStartNumber(Integer startNumber) {
        this.startNumber = startNumber;
    }

    public Integer getEndNumberl() {
        return this.endNumberl;
    }

    public void setEndNumberl(Integer endNumberl) {
        this.endNumberl = endNumberl;
    }

    public Integer getSandbox() {
        return this.sandbox;
    }

    public void setSandbox(Integer sandbox) {
        this.sandbox = sandbox;
    }

    public Integer getProportion() {
        return this.proportion;
    }

    public void setProportion(Integer proportion) {
        this.proportion = proportion;
    }

    public String getNextNode() {
        return this.nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }
}
