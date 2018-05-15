package com.zw.rule.engine.service;

import com.zw.rule.engine.po.EngineNode;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineNodeService {
    boolean addNode(EngineNode engineNode);

    List<EngineNode> queryNodeListByVerId(Long id);

    EngineNode findById(Long id);

    boolean deleteNode(EngineNode engineNode);

    boolean updateNode(EngineNode engineNode, Long targetId);

    boolean saveEngineNode(EngineNode engineNode);

    boolean deleteNode(Map<String, Object> map);

    boolean updateNodeForNextOrderAndParams(List<EngineNode> list);

    int queryMaxNodeOrder(Long versionId);

    boolean updateNodeForMove(EngineNode engineNode);

    int renameNode(Map<String, Object> map);

    Map<String, Object> deleteNodes(List<Long> list, String array);

    List<EngineNode> queryNodesByNextNode(Map<String, Object> map);

    boolean updateNextNodes(EngineNode engineNode);

    void deleteNode(Long engineNodeId, Long preEngineNodeId);

    void removeLink(Long engineNodeId, Long preEngineNodeId);

    List<EngineNode> queryEngineTypedNodeListByEngineVerId(Long versionId, List<Integer> types);
}

