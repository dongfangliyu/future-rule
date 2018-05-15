package com.zw.rule.engine.service;

import com.zw.rule.engine.po.ResultSetList;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface ResultSetListService {
    int addResultSetList(ResultSetList resultSetList);

    ResultSetList queryResultSetListById(ResultSetList resultSetList);

    List<ResultSetList> queryResultSetListByResultsetId(Long resultestId);
}
