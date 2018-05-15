package com.zw.rule.mapper.engine;

import com.zw.rule.engine.po.EngineVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineVersionMapper {
    int deleteByPrimaryKey(long var1);

    int insert(EngineVersion var1);

    EngineVersion selectByPrimaryKey(long var1);

    int updateByPrimaryKeySelective(EngineVersion var1);

    int updateByPrimaryKey(EngineVersion var1);

    int undeployVersion(long var1);

    List<EngineVersion> queryEngineVersionListByEngineId(long var1);

    int insertEngineVersionAndReturnId(EngineVersion var1);

    EngineVersion queryLatestEngineVersion(EngineVersion var1);

    EngineVersion queryLatestEngineSubVersion(EngineVersion var1);

    int cleanSubVersionByVersion(Map var1);

    EngineVersion queryDeploiedVersionByEngineId(@Param("engineId") Long var1);

    EngineVersion findById(@Param("versionId") Long var1);

    List<EngineVersion> queryEngineVersionByEngineId(Map<String, Object> var1);

    EngineVersion getTargetEngineVersion(Map<String, Object> var1);
}
