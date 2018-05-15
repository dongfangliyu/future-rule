package com.zw.rule.jeval.operator;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class BooleanOrOperator extends AbstractOperator{
    public BooleanOrOperator() {
        super("||", 1);
    }

    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand != 1.0D && rightOperand != 1.0D?0.0D:1.0D;
    }
}
