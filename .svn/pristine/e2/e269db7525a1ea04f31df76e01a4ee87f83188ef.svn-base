package com.zw.rule.jeval.operator;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class AdditionOperator extends AbstractOperator{
    public AdditionOperator() {
        super("+", 5, true);
    }

    public double evaluate(double leftOperand, double rightOperand) {
        Double rtnValue = new Double(leftOperand + rightOperand);
        return rtnValue.doubleValue();
    }

    public String evaluate(String leftOperand, String rightOperand) {
        String rtnValue = new String(leftOperand.substring(0, leftOperand.length() - 1) + rightOperand.substring(1, rightOperand.length()));
        return rtnValue;
    }

    public double evaluate(double operand) {
        return operand;
    }
}
