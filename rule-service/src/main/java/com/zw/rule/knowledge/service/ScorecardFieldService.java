package com.zw.rule.knowledge.service;

import com.zw.rule.knowledge.po.ScorecardField;

import java.util.List;

/**
 * Created by zhangtao on 2017/5/11 0011.
 */
public interface ScorecardFieldService {
    List<ScorecardField> queryFieldList(Long id);

    ScorecardField findById(Long id);

    boolean addField(List<ScorecardField> list);

    boolean updateField(List<ScorecardField> list);

    boolean deleteField(List<ScorecardField> list);
}
