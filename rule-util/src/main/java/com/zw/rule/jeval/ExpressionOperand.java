package com.zw.rule.jeval;


import com.zw.rule.jeval.operator.Operator;

public class ExpressionOperand {
    private String value = null;
    private Operator unaryOperator = null;

    public ExpressionOperand(String value, Operator unaryOperator) {
        this.value = value;
        this.unaryOperator = unaryOperator;
    }

    public String getValue() {
        return this.value;
    }

    public Operator getUnaryOperator() {
        return this.unaryOperator;
    }
}
