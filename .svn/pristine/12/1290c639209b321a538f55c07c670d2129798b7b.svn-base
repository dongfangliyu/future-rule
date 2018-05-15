package com.zw.rule.mapper.knowledge;

import com.zw.rule.knowledge.po.KnowledgeTree;
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
public interface KnowledgeTreeMapper extends BaseMapper<KnowledgeTree> {
    List<KnowledgeTree> getTreeList(Map<String, Object> map);

    int batchInsert(List<KnowledgeTree> list);

    int deleteStatusById(Map<String, Object> map);

    List<KnowledgeTree> getTreeDataForEngine(Map<String, Object> map);
}
