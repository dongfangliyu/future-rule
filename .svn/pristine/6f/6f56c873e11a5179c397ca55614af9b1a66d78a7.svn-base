package com.zw.rule.jeval.operator;

import com.zw.rule.jeval.EvaluationException;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class AbstractOperator implements Operator{
    private String symbol = null;
    private int precedence = 0;
    private boolean unary = false;

    public AbstractOperator(String symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
    }

    public AbstractOperator(String symbol, int precedence, boolean unary) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.unary = unary;
    }

    public double evaluate(double leftOperand, double rightOperand) {
        return 0.0D;
    }

    public String evaluate(String leftOperand, String rightOperand) throws EvaluationException {
        throw new EvaluationException("Invalid operation for a string.");
    }

    public double evaluate(double operand) {
        return 0.0D;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public int getLength() {
        return this.symbol.length();
    }

    public boolean isUnary() {
        return this.unary;
    }

    public boolean equals(Object object) {
        if(object == null) {
            return false;
        } else if(!(object instanceof AbstractOperator)) {
            throw new IllegalStateException("Invalid operator object.");
        } else {
            AbstractOperator operator = (AbstractOperator)object;
            return this.symbol.equals(operator.getSymbol());
        }
    }

    public String toString() {
        return this.getSymbol();
    }
}
