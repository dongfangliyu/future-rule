package com.zw.rule.mapper.knowledge;

import com.zw.rule.knowledge.po.KnowledgeTreeRel;
import com.zw.rule.mapper.common.BaseMapper;

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
public interface EngineRuleRelMapper extends BaseMapper<KnowledgeTreeRel> {

    int insertRel(Map<String, Object> map);

    int deleteRel(Map<String, Object> map);

    List<Long> getRuleIdsByEngineId(Long engineId);
}
