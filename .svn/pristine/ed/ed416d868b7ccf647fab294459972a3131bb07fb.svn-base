package com.zw.rule.jeval.operator;

import com.zw.rule.jeval.EvaluationException;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public interface Operator {
    double evaluate(double var1, double var3);

    String evaluate(String var1, String var2) throws EvaluationException;

    double evaluate(double var1);

    String getSymbol();

    int getPrecedence();

    int getLength();

    boolean isUnary();
}
