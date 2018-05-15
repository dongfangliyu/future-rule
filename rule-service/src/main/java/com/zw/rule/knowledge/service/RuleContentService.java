package com.zw.rule.knowledge.service;

import com.zw.rule.knowledge.po.RuleContent;

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
public interface RuleContentService {
    List<RuleContent> getRuleContentList(Long id);

    RuleContent findById(Long id);

    boolean insertRuleContent(List<RuleContent> list);

    boolean updateRuleContent(List<RuleContent> list);

    boolean deleteRuleContent(List<RuleContent> list);
}
