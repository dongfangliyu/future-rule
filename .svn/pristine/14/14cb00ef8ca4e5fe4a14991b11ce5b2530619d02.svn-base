package com.zw.rule.engine.vo;

import com.zw.rule.engine.po.EngineVersion;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class EngineVersionVo implements Comparable<EngineVersionVo>{
    private EngineVersion engineVersion;
    private List<EngineVersion> subEngineVersionList;

    public EngineVersionVo() {
    }

    public EngineVersion getEngineVersion() {
        return this.engineVersion;
    }

    public void setEngineVersion(EngineVersion engineVersion) {
        this.engineVersion = engineVersion;
    }

    public List<EngineVersion> getSubEngineVersionList() {
        return this.subEngineVersionList;
    }

    public void setSubEngineVersionList(List<EngineVersion> subEngineVersionList) {
        this.subEngineVersionList = subEngineVersionList;
    }

    public int compareTo(EngineVersionVo o) {
        return this.engineVersion.getVersion() != o.getEngineVersion().getVersion()?this.engineVersion.getVersion().intValue() - o.getEngineVersion().getVersion().intValue():-1;
    }
}
