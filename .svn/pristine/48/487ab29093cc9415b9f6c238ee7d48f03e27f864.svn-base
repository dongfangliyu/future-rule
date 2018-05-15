package com.zw.rule.mapper.knowledge;

import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.knowledge.po.RuleField;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public interface RuleFieldMapper extends BaseMapper<RuleField> {
    List<RuleField> getFieldList(Long id);

    int insertField(List<RuleField> list);

    boolean updateField(List<RuleField> list);

    boolean deleteField(List<RuleField> list);

    List<RuleField> getNodeByList(NodeKnowledge var1);

    List<RuleField> selectNodeByRuleList(NodeKnowledge var1);

    List<RuleField> selectByRuleList(Map<String, Object> var1);
}
