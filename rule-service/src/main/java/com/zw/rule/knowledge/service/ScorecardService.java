package com.zw.rule.knowledge.service;

import com.zw.rule.knowledge.po.Scorecard;
import com.zw.rule.knowledge.po.ScorecardExcel;
import com.zw.rule.knowledge.po.ScorecardRuleContent;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2017/5/11 0011.
 */
public interface ScorecardService {
    List<Scorecard> queryScorecardList(Map<String, Object> map);

    boolean addScorecard(Map<String, Object> map);

    boolean updateScorecard(Map<String, Object> map);

    boolean updateScorecardStatus(Map<String, Object> map);

    Scorecard findById(Long id);

    int batchAddScorecard(Map<String, Object> map, List<Scorecard> list);

    List<ScorecardExcel> queryRuleExcelData(Map<String, Object> map);

    int countOnlyScName(Map<String, Object> map);

    int countOnlyScCode(Map<String, Object> map);
}
