package com.zw.rule.knowledge.po;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *
 *Filename：
 *
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017/5/11
 ********************************************************/
public class EngineRuleRel {
    private static final long serialVersionUID = 1L;
    private long id;
    private long engineId;
    private long ruleId;

    public EngineRuleRel() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getEngineId() {
        return this.engineId;
    }

    public void setEngineId(Long engineId) {
        this.engineId = engineId;
    }
}
