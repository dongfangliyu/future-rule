package com.zw.rule.engine.service;

import com.zw.rule.engine.po.EngineNode;
import com.zw.rule.engine.po.EngineVersion;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */
public interface EngineVersionService {
    int deployEngine(Long versionId);

    int undeployEngine(Long versionId);

    List<EngineVersion> queryEngineVersionListByEngineId(Long engineId);

    int addEngineVersion(EngineVersion engineVersion);

    int update(EngineVersion engineVersion);

    EngineVersion queryLatestEngineSubVersion(EngineVersion engineVersion);

    EngineVersion queryLatestEngineVersion(EngineVersion engineVersion);

    Long saveEngineVersion(EngineVersion engineVersion, List<EngineNode> nodeList);

    EngineVersion queryDeploiedVersionByEngineId(Long engineId);

    EngineVersion queryByPrimaryKey(Long versionId);

    boolean clear(Long versionId);

    List<EngineVersion> queryEngineVersionByEngineId(Map<String, Object> paramMap);

    List<EngineNode> getNodeListByEngineInfo(Map<String, Object> paramMap);
}

