package com.zw.rule.mapper.datamanage;

import com.zw.rule.datamanage.po.CustList;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangtao on 2017/5/4 0004.
 */
public interface CustListMapper extends BaseMapper<CustList> {
    List<CustList> findCustList(Map<String, Object> map);

    int checkDupCustList(Map<String, Object> map);

    int importOneCustList(CustList custList);

    List<CustList> findValidColumnData(Map<String, Object> map);

    List<CustList> searchTop4Column(Map<String, Object> map);

    CustList findById(Map<String, Object> map);

    boolean createCustList(Map<String, Object> map);

    boolean batchCreateCustList(Map<String, Object> map);

    boolean updateCustList(Map<String, Object> map);

    boolean deleteCustList(Map<String, Object> map);

    Integer findByQueryKey(Map<String, Object> map);

    Integer revFindByQueryKey(Map<String, Object> map);
}
