package com.zw.rule.knowledge.service;

import com.zw.rule.knowledge.po.RuleField;

import java.util.List;

/**
 * Created by zhangtao on 2017/5/11 0011.
 */
public interface RuleFieldService {
    List<RuleField> getFieldList(Long ruleId);

    RuleField findById(Long id);

    boolean insertField(List<RuleField> list);

    boolean updateField(List<RuleField> list);

    boolean deleteField(List<RuleField> list);
}
