package com.zw.rule.jeval.operator;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class BooleanNotOperator extends AbstractOperator{
    public BooleanNotOperator() {
        super("!", 0, true);
    }

    public double evaluate(double operand) {
        return operand == 1.0D?0.0D:1.0D;
    }
}
