package com.zw.rule.knowledge.service.impl;

import com.zw.rule.knowledge.service.EngineRuleRelService;
import com.zw.rule.mapper.knowledge.EngineRuleRelMapper;
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
public class EngineRuleRelServiceImpl implements EngineRuleRelService {

    @Resource
    private EngineRuleRelMapper engineRuleRelMapper;

    /**
     * 对t_engine_rule_rel表 批量添加数据
     * @param param
     * idList   List<Long> 规则id集合
     * engineId  long       引擎id
     * @return
     * 方法通过单元测试
     */
    public boolean insertRel(Map<String, Object> param) {
        int count = engineRuleRelMapper.insertRel(param);
        return count > 0;
    }

    /**
     * 对t_engine_rule_rel表 批量删除数据
     * @param param
     * idList   List<Long> 规则id集合
     * engineId  long       引擎id
     * @return
     * 方法通过单元测试
     */
    public boolean deleteRel(Map<String, Object> param) {
        int count = engineRuleRelMapper.deleteRel(param);
        return count > 0;
    }

    /**
     * 根据engineId查询ruleId集合
     * @param engineId  long  引擎id
     * @return
     * 方法通过单元测试
     */
    public List<Long> getRuleIdsByEngineId(Long engineId) {
        return engineRuleRelMapper.getRuleIdsByEngineId(engineId);
    }
}
