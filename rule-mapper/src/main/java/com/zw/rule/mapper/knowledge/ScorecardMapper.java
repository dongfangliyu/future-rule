package com.zw.rule.mapper.knowledge;

import com.zw.rule.engine.po.NodeKnowledge;
import com.zw.rule.knowledge.po.Scorecard;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public interface ScorecardMapper extends BaseMapper<Scorecard> {
    List<Scorecard> getScorecardList(Map<String, Object> map);

    int updateScorecardStatus(Map<String, Object> map);

    int countOnlyScName(Map<String, Object> map);

    int countOnlyScCode(Map<String, Object> map);

    List<Scorecard> checkByField(Map<String, Object> map);

    List<Scorecard> selectNodeByScCodeList(NodeKnowledge var1);
}
