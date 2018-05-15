package com.zw.rule.jeval.function.math;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionResult;

/**
 * Created by Administrator on 2017/5/5.
 */
public class Random implements Function {
    public Random() {
    }

    public String getName() {
        return "random";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        Double result = new Double(Math.random());
        return new FunctionResult(result.toString(), 0);
    }
}
