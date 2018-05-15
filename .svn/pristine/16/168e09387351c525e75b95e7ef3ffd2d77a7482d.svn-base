package com.zw.rule.mapper.datamanage;

import com.zw.rule.datamanage.po.NodeListDb;
import com.zw.rule.mapper.common.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/4.
 */
public interface NodeListDbMapper extends BaseMapper<NodeListDb> {
    boolean createNodeListDb(Map<String, Object> param);

    boolean updateNodeListDb(Map<String, Object> param);

    NodeListDb findByNodeId(Map<String, Object> param);

    List<NodeListDb> findByNodeIds(Map<String, Object> param);

    String checkByListDb(Map<String, Object> param);

    int deleteFieldRefByNodeId(Long param);

    int deleteFieldRefsBatchByNodeIds(@Param("blackWhites") List<Long> param);

    int insertNodeListDb(NodeListDb param);

    NodeListDb findDbListByNodeId(@Param("nodeId")Long nodeId);
}
