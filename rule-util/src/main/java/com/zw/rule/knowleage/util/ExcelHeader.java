package com.zw.rule.knowleage.util;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class ExcelHeader {
    public static final String[] RULE_HEADER = new String[]{"规则名称", "规则代码", "规则描述", "优先级", "指标详情", "评分","规则内容","字段编号及名称","规则代码包","type","ruleAudit","ruleType","lastLogical"};
    public static final String[] RULE_ClASS = new String[]{"name", "code", "description", "priority", "fieldContent","score", "content","fieldId","ruleContent","type","ruleAudit","ruleType","lastLogical"};
    public static final String[] SCORECARD_HEADER = new String[]{"评分卡名称", "评分卡代码", "评分卡描述", "版本号", "指标详情", "评分卡规则内容"};
    public static final String[] SCORECARD_ClASS = new String[]{"name", "code", "description", "version", "fieldContent", "content"};

    public ExcelHeader() {
    }
}
