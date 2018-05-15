package com.zw.rule.knowledge.service.impl;

import com.zw.rule.knowledge.service.KnowledgeTreeRelService;
import com.zw.rule.mapper.knowledge.KnowledgeTreeRelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service
public class KnowledgeTreeRelServiceImpl implements KnowledgeTreeRelService {

    @Resource
    private KnowledgeTreeRelMapper knowledgeTreeRelMapper;

    /**
     * 对t_engine_knowledge_tree_rel表 批量添加数据
     * @param param
     * idList   List<Long> 知识树id集合
     * engineId  long       引擎id
     * @return
     * 方法通过单元测试
     */
    public boolean insertRel(Map<String, Object> param) {
        int count = knowledgeTreeRelMapper.insertRel(param);
        return count > 0;
    }

    /**
     * 对t_engine_knowledge_tree_rel表 删除数据
     * @param engineId  long  引擎id
     * @return
     * 方法通过单元测试
     */
    public boolean deleteRel(Long engineId) {
        int count = knowledgeTreeRelMapper.deleteRel(engineId);
        return count > 0;
    }

    /**
     * 根据engineId查询treeId集合
     * @param engineId  long  引擎id
     * @return
     * 方法通过单元测试
     */
    public List<Long> findTreeIdsByEngineId(Long engineId) {
        return knowledgeTreeRelMapper.findTreeIdsByEngineId(engineId);
    }
}
