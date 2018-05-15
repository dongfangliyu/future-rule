package com.zw.rule.mapper.engine;

import com.zw.rule.engine.po.EngineResultSet;
import com.zw.rule.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年05月04日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:Administrator <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public interface EngineResultSetMapper extends BaseMapper<EngineResultSet> {
    int addResultSet(EngineResultSet var1);

    List<EngineResultSet> queryResultSetByList(EngineResultSet var1);

    List<EngineResultSet> queryEngineResultSetBySegment(Map var1);

    EngineResultSet queryResultSetById(EngineResultSet var1);

    List<EngineResultSet> queryResultSetDetailsById(long var1);

    List<EngineResultSet> queryBatchTestResultSetByEngineId(Map<String, Object> var1);

    List<EngineResultSet> queryBatchTestResultSetByBatchNo(Map<String, Object> var1);

    List<EngineResultSet> queryResultList(Map<String, Object> param);
}

