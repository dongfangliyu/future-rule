package com.zw.rule.mapper.engine;



import com.zw.rule.engine.po.ResultSetList;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface ResultSetListMapper {
    int addResultSetList(ResultSetList var1);

    ResultSetList queryResultSetListById(ResultSetList var1);

    int insertResultSetListByList(List<ResultSetList> var1);

    List<ResultSetList> queryResultSetListByResultsetId(Long var1);
}
