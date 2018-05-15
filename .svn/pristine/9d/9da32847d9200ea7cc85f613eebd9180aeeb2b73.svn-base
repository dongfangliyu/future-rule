package com.zw.rule.engine.service;

import com.zw.rule.engine.po.EngineResultSet;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface ResultSetService {
    int addResultSet(EngineResultSet var1);

    List<EngineResultSet> queryResultSetByList(EngineResultSet resultSet);

    EngineResultSet queryResultSetById(EngineResultSet resultSet);

    List<EngineResultSet> queryResultSetDetailsById(long id);

    List<EngineResultSet> queryEngineResultSetBySegment(Map map);

    List<EngineResultSet> queryBatchTestResultSetByEngineId(Map<String, Object> map);

    List<EngineResultSet> queryBatchTestResultSetByBatchNo(Map<String, Object> map);

    List<EngineResultSet> queryResultList(Map<String, Object> param);
}
