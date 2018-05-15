package com.zw.rule.mapper.knowledge;

import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.knowledge.po.Rule;
import com.zw.rule.knowledge.po.RuleNode;
import com.zw.rule.mapper.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

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
public interface RuleMapper extends BaseMapper<Rule> {
    List<Rule> getRuleList(Map<String, Object> map);

    int updateRuleStatus(Map<String, Object> map);

    List<Rule> getNodeByRuleList(NodeKnowledge nodeKnowledge);

    List<Rule> selectnodeByInRoleid(List<Long> id);

    List<Long> getRuleIdsByParentId(Map<String, Object> map);

    List<Rule> checkByField(Map<String, Object> map);

    int countOnlyRuleName(Map<String, Object> map);

    int countOnlyRuleCode(Map<String, Object> map);

    List<String> getFieldIdsByRuleId(List<Long> ruleIdList);

    List<Rule> getRuleListByType(Map<String, Object> var1);

    List<Rule> getNodeAddOrSubRulesByNodeId(Long var1);

    List<Rule> selectRulesByNodeId(@Param("nodeId")Long nodeId);

    void deleteRelateRule(@Param("nodeId") Long nodeId);

    void saveRuleNode(Map<String, Object> map);

    List<RuleNode> selectRuleNodeByNodeId(@Param("nodeId")Long nodeId);

    void saveVerRuleNode(RuleNode ruleNode);
}
