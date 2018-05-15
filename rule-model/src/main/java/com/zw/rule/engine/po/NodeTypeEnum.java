package com.zw.rule.engine.po;

/**
 * Created by Administrator on 2017/5/12.
 */
public enum NodeTypeEnum {
    START(1, "开始"),
    POLICY(2, "规则集"),
    CLASSIFY(3, "客户分群"),
    SCORECARD(4, "评分卡"),
    BLACKLIST(5, "黑名单"),
    WHITELIST(6, "白名单"),
    SANDBOX(7, "沙盒比例"),
    DECISION(9, "决策选项"),
    NODE_COMPLEXRULE(13, "复杂规则");

    private int value;
    private String type;

    private NodeTypeEnum(int value, String type) {
        this.value = value;
        this.type = type;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

