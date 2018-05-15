package com.zw.rule.mapper.engine;

import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.mapper.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineNodeMapper extends BaseMapper<EngineNode> {
    List<EngineNode> queryNodeListByVerId(@Param("verId") Long var1);

    int insertNodeAndReturnId(EngineNode var1);

    int deleteEngineNodeByNodeId(@Param("nodeId") Long var1);

    int updateNodeForNextOrderAndParams(List<EngineNode> var1);

    int queryMaxNodeOrder(@Param("verId") Long var1);

    int renameNode(Map<String, Object> var1);

    int deleteNodes(List<Long> var1);

    List<EngineNode> queryNodesByNextNode(Map<String, Object> var1);

    int updateNextNodes(EngineNode var1);

    int deleteNodesByNodeIds(@Param("commons") List<Long> var1);

    List<EngineNode> queryEngineTypedNodeListByEngineVerId(@Param("verId") Long var1, @Param("types") List<Integer> var2);

    int updateByPrimaryKey(EngineNode var1);

    int updateParentIdByNodeId(Map<String, Long> var1);

    int updateNodePosition(EngineNode var1);
}
