package com.zw.rule.jeval.operator;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class LessThanOrEqualOperator extends AbstractOperator{
    public LessThanOrEqualOperator() {
        super("<=", 4);
    }

    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand <= rightOperand?1.0D:0.0D;
    }

    public String evaluate(String leftOperand, String rightOperand) {
        return leftOperand.compareTo(rightOperand) <= 0?"1.0":"0.0";
    }
}
