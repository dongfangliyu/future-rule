package com.zw.rule.knowledge.po;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class ScoreCardJson {
    private String ouput;
    private Integer index;
    private String formula;
    private String formula_show;
    private String fields;

    public ScoreCardJson() {
    }

    public String getOuput() {
        return this.ouput;
    }

    public void setOuput(String ouput) {
        this.ouput = ouput;
    }

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormula_show() {
        return this.formula_show;
    }

    public void setFormula_show(String formula_show) {
        this.formula_show = formula_show;
    }

    public String getFields() {
        if(this.fields != null) {
            this.fields = this.fields.substring(1, this.fields.length() - 1).trim();
        }

        return this.fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
