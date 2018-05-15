package com.zw.rule.jeval.function.math;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionHelper;
import com.zw.rule.jeval.function.FunctionResult;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/5.
 */
public class IEEEremainder implements Function {
    public IEEEremainder() {
    }

    public String getName() {
        return "IEEEremainder";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        Double result = null;
        ArrayList numbers = FunctionHelper.getDoubles(arguments, ',');
        if(numbers.size() != 2) {
            throw new FunctionException("Two numeric arguments are required.");
        } else {
            try {
                double e = ((Double)numbers.get(0)).doubleValue();
                double argumentTwo = ((Double)numbers.get(1)).doubleValue();
                result = new Double(Math.IEEEremainder(e, argumentTwo));
            } catch (Exception var9) {
                throw new FunctionException("Two numeric arguments are required.", var9);
            }

            return new FunctionResult(result.toString(), 0);
        }
    }
}
