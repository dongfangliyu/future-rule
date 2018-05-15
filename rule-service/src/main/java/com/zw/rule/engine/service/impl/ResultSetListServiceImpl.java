package com.zw.rule.engine.service.impl;


import com.zw.rule.engine.po.ResultSetList;
import com.zw.rule.engine.service.ResultSetListService;
import com.zw.rule.mapper.engine.ResultSetListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
@Service
public class ResultSetListServiceImpl  implements ResultSetListService {
    @Resource
    private ResultSetListMapper resultSetListMapper;

    public ResultSetListServiceImpl() {
    }

    public int addResultSetList(ResultSetList resultSetList) {
        return resultSetListMapper.addResultSetList(resultSetList);
    }

    public ResultSetList queryResultSetListById(ResultSetList resultSetList) {
        return resultSetListMapper.queryResultSetListById(resultSetList);
    }

    public List<ResultSetList> queryResultSetListByResultsetId(Long resultsetId) {
        return resultSetListMapper.queryResultSetListByResultsetId(resultsetId);
    }
}