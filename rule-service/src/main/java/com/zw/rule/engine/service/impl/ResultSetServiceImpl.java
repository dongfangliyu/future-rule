package com.zw.rule.engine.service.impl;


import com.zw.rule.engine.po.EngineResultSet;
import com.zw.rule.engine.service.ResultSetService;
import com.zw.rule.mapper.engine.EngineResultSetMapper;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
@Service
public class ResultSetServiceImpl implements ResultSetService {
    @Resource
    private EngineResultSetMapper resultSetMapper;

    public ResultSetServiceImpl() {
    }

    public int addResultSet(EngineResultSet resultSet) {
        return resultSetMapper.addResultSet(resultSet);
    }

    public List<EngineResultSet> queryResultSetByList(EngineResultSet resultSet) {
        return resultSetMapper.queryResultSetByList(resultSet);
    }

    public EngineResultSet queryResultSetById(EngineResultSet resultSet) {
        return resultSetMapper.queryResultSetById(resultSet);
    }

    /**
     * 鏌ョ湅缁撴灉闆嗘煇鏉℃暟鎹殑璇︽儏
     * @param resultSetId
     * @return
     * 鍗曞厓娴嬭瘯閫氳繃
     */
    public List<EngineResultSet> queryResultSetDetailsById(long resultSetId) {
        return resultSetMapper.queryResultSetDetailsById(resultSetId);
    }

    /**
     * 缁撴灉闆嗗垪琛�
     * @param map{engineId: startDate: endDate: }
     * @return
     * 鍗曞厓娴嬭瘯閫氳繃
     */
    public List<EngineResultSet> queryEngineResultSetBySegment(Map map) {
        return resultSetMapper.queryEngineResultSetBySegment(map);
    }

    /**
     * 鎵归噺娴嬭瘯鍒楄〃
     * @param paramMap{engineId:  searchKey:  userId: }
     * @return
     * 鍗曞厓娴嬭瘯閫氳繃锛坰ql璇彞涓璼earchKey鍙傛暟鏈敤锛�
     */
    public List<EngineResultSet> queryBatchTestResultSetByEngineId(Map<String, Object> paramMap) {
        return resultSetMapper.queryBatchTestResultSetByEngineId(paramMap);
    }

    /**
     * 鎵归噺娴嬭瘯缁撴灉鍒楄〃
     * @param paramMap{engineId:  searchKey:  batchNo:  userId: }
     * @return
     * 鍗曞厓娴嬭瘯閫氳繃锛坰ql璇彞涓璼earchKey鍜宔ngineId鍙傛暟鏈敤锛�
     */
    public List<EngineResultSet> queryBatchTestResultSetByBatchNo(Map<String, Object> paramMap) {
        return resultSetMapper.queryBatchTestResultSetByBatchNo(paramMap);
    }

    /**
     * 鏍规嵁鐗堟湰鑾峰彇缁撴灉闆�
     * @param param
     * verId 鐗堟湰id
     * type 缁撴灉闆嗙被鍨� 1.api銆�2.椤甸潰濉啓銆�3.鎵归噺娴嬭瘯
     */
    @Override
    public List<EngineResultSet> queryResultList(Map<String, Object> param) {
        return resultSetMapper.queryResultList(param);
    }
}
