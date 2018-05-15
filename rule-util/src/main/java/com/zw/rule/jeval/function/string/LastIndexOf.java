package com.zw.rule.jeval.function.string;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionHelper;
import com.zw.rule.jeval.function.FunctionResult;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/5.
 */
public class LastIndexOf implements Function {
    public LastIndexOf() {
    }

    public String getName() {
        return "lastIndexOf";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        Integer result = null;
        String exceptionMessage = "Two string arguments and one integer argument are required.";
        ArrayList values = FunctionHelper.getTwoStringsAndOneInteger(arguments, ',');
        if(values.size() != 3) {
            throw new FunctionException(exceptionMessage);
        } else {
            try {
                String e = FunctionHelper.trimAndRemoveQuoteChars((String)values.get(0), evaluator.getQuoteCharacter());
                String argumentTwo = FunctionHelper.trimAndRemoveQuoteChars((String)values.get(1), evaluator.getQuoteCharacter());
                int index = ((Integer)values.get(2)).intValue();
                result = new Integer(e.lastIndexOf(argumentTwo, index));
            } catch (FunctionException var9) {
                throw new FunctionException(var9.getMessage(), var9);
            } catch (Exception var10) {
                throw new FunctionException(exceptionMessage, var10);
            }

            return new FunctionResult(result.toString(), 0);
        }
    }
}