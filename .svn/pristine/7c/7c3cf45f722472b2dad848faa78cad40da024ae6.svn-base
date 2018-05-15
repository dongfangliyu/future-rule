package com.zw.rule.engine.service.impl;

import com.zw.rule.engine.po.CustomNode;
import com.zw.rule.engine.service.CustomNodeService;
import com.zw.rule.mapper.engine.CustomNodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 节点管理
 * @author zongpeng
 * @Time 2017-6-19
 */
@Service
public class CustomNodeServiceImpl implements CustomNodeService {

    @Resource private CustomNodeMapper customNodeMapper;

    @Override
    public List<CustomNode> getNodeList(Long userId) {
        return customNodeMapper.getNodeList(userId);
    }

    @Override
    public void addNode(CustomNode customNode) {
        customNodeMapper.addNode(customNode);
    }

    @Override
    public void updateNode(CustomNode customNode) {
        customNodeMapper.updateNode(customNode);
    }

    @Override
    public CustomNode getNode(Long id) {
        return customNodeMapper.getNode(id);
    }

    @Override
    public void deleteNode(Long id) {
        customNodeMapper.deleteNode(id);
    }

    @Override
    public int isExists(Map<String, Object> param) {
        return customNodeMapper.isExists(param);
    }
}
