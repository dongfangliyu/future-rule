package com.zw.rule.engine.service;

import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.knowledge.po.Scorecard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface NodeKnowledgeService {
    int batchInsert(List<NodeKnowledge> record);

    List<NodeKnowledge> queryNodeKnowledgeListByNodeId(Map<String, Object> map);

    NodeKnowledge queryByNodeId(Long nodeId, Long knowledgeType);

    List<Engine> queryEnginesByRuleId(Map<String, Object> map);

    List<Scorecard> queryScorecardList(Map<String, Object> map);
}
