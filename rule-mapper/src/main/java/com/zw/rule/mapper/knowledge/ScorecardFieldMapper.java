package com.zw.rule.mapper.knowledge;

import com.zw.rule.knowledge.po.ScorecardField;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public interface ScorecardFieldMapper extends BaseMapper<ScorecardField> {
    List<ScorecardField> queryFieldList(Long id);

    boolean addField(List<ScorecardField> list);

    boolean updateField(List<ScorecardField> list);

    boolean deleteField(List<ScorecardField> list);
}
