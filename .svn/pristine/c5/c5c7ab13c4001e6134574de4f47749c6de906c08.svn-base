package com.zw.rule.knowledge.service;

import com.zw.rule.knowledge.po.Rule;
import com.zw.rule.knowledge.po.RuleExcel;
import com.zw.rule.knowledge.po.RuleNode;

import java.util.List;
import java.util.Map;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017/5/11
 ********************************************************/
public interface RuleService {
    List<Rule> getRuleList(Map<String, Object> map);

    List<Rule> getRuleListByType(Map<String, Object> var1);

    boolean insertRule(Map<String, Object> map);

    boolean updateRule(Map<String, Object> map);

    boolean updateRuleStatus(Map<String, Object> map);

    Rule findById(Long id);

    int batchAddRule(Map<String, Object> map, List<Rule> list);

    List<RuleExcel> getRuleExcelData(Map<String, Object> map);

    List<Long> getRuleIdsByParentId(Map<String, Object> map);

    int countOnlyRuleName(Map<String, Object> map);

    int countOnlyRuleCode(Map<String, Object> map);

    List<String> getFieldIdsByRuleId(List<Long> ruleIdList);

    List<Rule> getNodeAddOrSubRulesByNodeId(Long var1);

    void deleteRelateRule(Long nodeId);

    void saveRuleNode(Map<String, Object> map);

    List<RuleNode> selectRuleNodeByNodeId(Long nodeId);
}
