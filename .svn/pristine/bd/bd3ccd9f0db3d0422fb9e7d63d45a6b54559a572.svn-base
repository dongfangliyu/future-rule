package com.zw.rule.jeval.function.math;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionHelper;
import com.zw.rule.jeval.function.FunctionResult;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Administrator on 2017/5/5.
 */
public class Max implements Function {
    public Max() {
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        Double result = null;
        ArrayList numbers = FunctionHelper.getDoubles(arguments, ',');
        int count = numbers.size();
        if(count < 2) {
            throw new FunctionException("Two numeric arguments are required at least.");
        } else {
            result = (Double) Collections.max(numbers);
            return new FunctionResult(result.toString(), 0);
        }
    }

    public String getName() {
        return "max";
    }
}
