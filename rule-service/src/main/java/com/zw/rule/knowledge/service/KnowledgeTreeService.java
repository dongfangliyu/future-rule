package com.zw.rule.knowledge.service;

import com.zw.rule.knowledge.po.KnowledgeTree;

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
public interface KnowledgeTreeService {
    List getTreeList(Map<String, Object> map);

    KnowledgeTree findById(Long id);

    boolean insertTree(KnowledgeTree knowledgeTree);

    int batchInsert(List<KnowledgeTree> list);

    boolean updateTree(KnowledgeTree knowledgeTree);

    boolean deleteStatusById(Map<String, Object> map);

    int batchCopyKnowledge(Map<String, Object> map);

    List<KnowledgeTree> getTreeDataForEngine(Map<String, Object> map);
}
