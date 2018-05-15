package com.zw.rule.jeval.operator;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class SubtractionOperator extends AbstractOperator {
    public SubtractionOperator() {
        super("-", 5, true);
    }

    public double evaluate(double leftOperand, double rightOperand) {
        Double rtnValue = new Double(leftOperand - rightOperand);
        return rtnValue.doubleValue();
    }

    public double evaluate(double operand) {
        return -operand;
    }
}
