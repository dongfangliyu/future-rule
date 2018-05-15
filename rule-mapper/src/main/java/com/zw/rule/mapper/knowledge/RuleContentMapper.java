package com.zw.rule.mapper.knowledge;

import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.knowledge.po.RuleContent;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;

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
public interface RuleContentMapper extends BaseMapper<RuleContent> {
    List<RuleContent> getRuleContentList(Long id);

    int insertRuleContent(List<RuleContent> list);

    boolean updateRuleContent(List<RuleContent> list);

    boolean deleteRuleContent(List<RuleContent> list);

    List<RuleContent> selectNodeByRuleList(NodeKnowledge nodeKnowledge);
}
