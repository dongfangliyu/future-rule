package com.zw.rule.jeval.function.string;

import com.zw.rule.jeval.EvaluationException;
import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionResult;

/**
 * Created by Administrator on 2017/5/5.
 */
public class Eval implements Function {
    public Eval() {
    }

    public String getName() {
        return "eval";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        String result = null;

        try {
            result = evaluator.evaluate(arguments, false, true);
        } catch (EvaluationException var7) {
            throw new FunctionException(var7.getMessage(), var7);
        }

        byte resultType = 0;

        try {
            Double.parseDouble(result);
        } catch (NumberFormatException var6) {
            resultType = 1;
        }

        return new FunctionResult(result, resultType);
    }
}
