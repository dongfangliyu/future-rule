package com.zw.rule.mapper.datamanage;

import com.zw.rule.datamanage.po.ListDb;
import com.zw.rule.mapper.common.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/4.
 */
@Repository
public interface ListDbMapper extends BaseMapper<ListDb> {
    List<ListDb> findByUser(Map<String, Object> param);

    String checkByField(Map<String, Object> param);

    Integer isExists(Map<String, Object> param);

    boolean updateStatus(Map<String, Object> param);

    Integer findByListDbName(Map<String, Object> param);

    boolean createListDb(ListDb param);

    boolean createTable(Map<String, Object> param);

    boolean createIndex(Map<String, Object> param);

    ListDb findById(Map<String, Object> param);

    boolean updateListDb(Map<String, Object> param);

    ListDb findListDbById(Map<String, Object> param);

    ListDb findListDbByIdandByorganId(Map<String, Object> param);

    List<ListDb> findListDbByIds(Map<String, Object> param);

    String findFieldsByListDbIds(Map<String, Object> param);

    String getQueryFieldCn(String queryField);
}

