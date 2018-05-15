package com.zw.rule.mapper.engine;

import com.zw.rule.engine.po.CustomNode;
import com.zw.rule.mapper.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 节点管理
 * @author zongpeng
 * @Time 2017-6-19
 */
@Repository
public interface CustomNodeMapper extends BaseMapper<CustomNode> {
    List<CustomNode> getNodeList(@Param("userId") Long userId);

    void addNode(CustomNode customNode);

    void updateNode(CustomNode customNode);

    CustomNode getNode(@Param("id")Long id);

    void deleteNode(@Param("id")Long id);

    int isExists(Map<String, Object> param);
}
