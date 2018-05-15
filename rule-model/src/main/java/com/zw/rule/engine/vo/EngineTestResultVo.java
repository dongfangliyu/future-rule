package com.zw.rule.engine.vo;

import com.zw.rule.base.BasePage;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public class EngineTestResultVo extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String engineName;
    private String result;
    private String scorecardScore;
    private String blackHitReason;
    private String whiteHitReason;
    private String hardnessRefuseRuleHitReason;
    private String plusMinusRuleHitReason;

    public EngineTestResultVo() {
    }
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEngineName() {
        return this.engineName;
    }

    public void setEngineName(String engineName) {
        this.engineName = engineName;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getScorecardScore() {
        return this.scorecardScore;
    }

    public void setScorecardScore(String scorecardScore) {
        this.scorecardScore = scorecardScore;
    }

    public String getBlackHitReason() {
        return this.blackHitReason;
    }

    public void setBlackHitReason(String blackHitReason) {
        this.blackHitReason = blackHitReason;
    }

    public String getWhiteHitReason() {
        return this.whiteHitReason;
    }

    public void setWhiteHitReason(String whiteHitReason) {
        this.whiteHitReason = whiteHitReason;
    }

    public String getHardnessRefuseRuleHitReason() {
        return this.hardnessRefuseRuleHitReason;
    }

    public void setHardnessRefuseRuleHitReason(String hardnessRefuseRuleHitReason) {
        this.hardnessRefuseRuleHitReason = hardnessRefuseRuleHitReason;
    }

    public String getPlusMinusRuleHitReason() {
        return this.plusMinusRuleHitReason;
    }

    public void setPlusMinusRuleHitReason(String plusMinusRuleHitReason) {
        this.plusMinusRuleHitReason = plusMinusRuleHitReason;
    }
}
