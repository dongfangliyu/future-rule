package com.zw.rule.mapper.engine;

import com.zw.rule.engine.po.Engine;
import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.knowledge.po.Scorecard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface NodeKnowledgeMapper {
    int deleteByPrimaryKey(Integer var1);

    int insert(NodeKnowledge var1);

    int insertSelective(NodeKnowledge var1);

    NodeKnowledge selectByPrimaryKey(Integer var1);

    int updateByPrimaryKeySelective(NodeKnowledge var1);

    int updateByPrimaryKey(NodeKnowledge var1);

    List<NodeKnowledge> queryNodeKnowledgeListByNodeId(Map<String, Object> var1);

    int batchDeleteNodeKnowledge(List<NodeKnowledge> var1);

    int insertNode(NodeKnowledge var1);

    int batchInsert(List<NodeKnowledge> var1);

    NodeKnowledge selectByNodeId(@Param("nodeId") Long var1, @Param("knowledgeType") Long var2);

    NodeKnowledge findScorecardByNodeId(@Param("nodeId") Long var1);

    int deleteKnowledgesByNodeId(Map var1);

    List<Engine> queryEnginesByRuleId(Map<String, Object> var1);

    List<Scorecard> queryScorecardList(Map<String, Object> var1);

    int deleteKnowledgesBatchByNodeIds(@Param("knowledges") List<Long> var1);
}
