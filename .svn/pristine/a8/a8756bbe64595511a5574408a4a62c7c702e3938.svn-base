package com.zw.rule.datamanage.service;

import com.zw.rule.datamanage.po.CustList;
import com.zw.rule.datamanage.po.ListDb;
import com.zw.rule.datamanage.po.NodeListDb;
import com.zw.rule.datamanage.po.TblColumn;

import java.util.List;
import java.util.Map;

/**
 * 黑白名单管理
 * @author zongpeng
 * @Time 2017-5-4
 */
public interface ListDbService {

    List<ListDb> queryByUser(Map<String, Object> param);

    boolean updateStatus(Map<String, Object> param);

    Integer queryByListDbName(Map<String, Object> param);

    boolean addListDb(ListDb listDb, Map<String, Object> paramMap);

    ListDb findById(Map<String, Object> param);

    boolean updateListDb(Map<String, Object> param);

    List<CustList> queryCustList(Map<String, Object> param);

    Integer isExists(Map<String, Object> param);

    List<CustList> findTop4Column(Map<String, Object> param);

    List<TblColumn> getColumnList(Map<String, Object> param);

    CustList queryCustListById(Map<String, Object> param);

    List<CustList> queryValidColumnData(Map<String, Object> param);

    int addCustList(Map<String, Object> param);

    int updateCustList(Map<String, Object> param);

    boolean deleteCustList(Map<String, Object> param);

    Integer queryCustListByQueryKey(Map<String, Object> param);

    ListDb queryListDbById(Map<String, Object> param);

    List<ListDb> queryListDbByIds(Map<String, Object> param);

    boolean addNodeListDb(Map<String, Object> param);

    boolean updateNodeListDb(Map<String, Object> param);

    NodeListDb queryNodeListDbByNodeId(Map<String, Object> param);

    String querySearchKeyIdListByNodeId(String nodeId , Long userId);

    boolean findByQueryKey(Map<String, Object> paramMap , Long userId , Long organId);

    boolean importExcel(String param, Map<String, Object> paramMap);

    String findFieldsByListDbIds(Map<String, Object> param);

    String getQueryFieldCn(String queryField);

    NodeListDb queryDbListByNodeId(Long nodeId);
}
