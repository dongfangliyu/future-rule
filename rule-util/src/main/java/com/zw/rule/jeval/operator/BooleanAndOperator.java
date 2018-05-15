package com.zw.rule.jeval.operator;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class BooleanAndOperator extends AbstractOperator{
    public BooleanAndOperator() {
        super("&&", 2);
    }

    public double evaluate(double leftOperand, double rightOperand) {
        return leftOperand == 1.0D && rightOperand == 1.0D?1.0D:0.0D;
    }
}
