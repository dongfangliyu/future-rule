package com.zw.rule.jeval;


import com.zw.rule.jeval.operator.Operator;

class NextOperator {
    private Operator operator = null;
    private int index = -1;

    public NextOperator(Operator operator, int index) {
        this.operator = operator;
        this.index = index;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public int getIndex() {
        return this.index;
    }
}