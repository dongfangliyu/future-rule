package com.zw.rule.jeval.function.math;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionHelper;
import com.zw.rule.jeval.function.FunctionResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/5/5.
 */
public class Average implements Function {
    public Average() {
    }

    public String getName() {
        return "avg";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        Double result = null;
        ArrayList numbers = FunctionHelper.getDoubles(arguments, ',');
        int count = numbers.size();
        if(count < 2) {
            throw new FunctionException("Two numeric arguments are required at least.");
        } else {
            double sum = 0.0D;

            BigDecimal b1;
            BigDecimal b2;
            for(Iterator var9 = numbers.iterator(); var9.hasNext(); sum = b1.add(b2).doubleValue()) {
                Double num = (Double)var9.next();
                b1 = new BigDecimal(Double.toString(sum));
                b2 = new BigDecimal(Double.toString(num.doubleValue()));
            }

            result = new Double(sum / (double)count);
            return new FunctionResult(result.toString(), 0);
        }
    }
}

