package com.zw.rule.jeval.function.math;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionResult;

/**
 * Created by Administrator on 2017/5/5.
 */
public class Round implements Function {
    public Round() {
    }

    public String getName() {
        return "round";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        Long result = null;
        Double number = null;

        try {
            number = new Double(arguments);
        } catch (Exception var6) {
            throw new FunctionException("Invalid argument.", var6);
        }

        result = new Long(Math.round(number.doubleValue()));
        return new FunctionResult(result.toString(), 0);
    }
}
