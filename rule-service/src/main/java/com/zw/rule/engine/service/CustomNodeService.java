package com.zw.rule.engine.service;

import com.zw.rule.engine.po.CustomNode;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/20.
 */
public interface CustomNodeService {
    List<CustomNode> getNodeList(Long userId);

    void addNode(CustomNode customNode);

    void updateNode(CustomNode customNode);

    CustomNode getNode(Long id);

    void deleteNode(Long id);

    int isExists(Map<String , Object> param);
}
