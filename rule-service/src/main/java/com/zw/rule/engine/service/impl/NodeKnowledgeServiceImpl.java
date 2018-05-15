//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zw.rule.engine.service.impl;


import java.util.List;
import java.util.Map;


import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.engine.service.NodeKnowledgeService;
import com.zw.rule.knowledge.po.Scorecard;
import com.zw.rule.mapper.engine.NodeKnowledgeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NodeKnowledgeServiceImpl implements NodeKnowledgeService {
    @Resource
    private NodeKnowledgeMapper nodeKnowledgeMapper;

    public NodeKnowledgeServiceImpl() {
    }
    public int batchInsert(List<NodeKnowledge> record) {
        return nodeKnowledgeMapper.batchInsert(record);
    }

    public List<NodeKnowledge> queryNodeKnowledgeListByNodeId(Map<String, Object> paramMap) {
        return nodeKnowledgeMapper.queryNodeKnowledgeListByNodeId(paramMap);
    }

    public NodeKnowledge queryByNodeId(Long nodeId, Long knowledgeType) {
        return nodeKnowledgeMapper.selectByNodeId(nodeId, knowledgeType);
    }

    public List<Engine> queryEnginesByRuleId(Map<String, Object> param) {
        return nodeKnowledgeMapper.queryEnginesByRuleId(param);
    }

    public List<Scorecard> queryScorecardList(Map<String, Object> param) {
        return nodeKnowledgeMapper.queryScorecardList(param);
    }
}
