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
public class Substring implements Function {
    public Substring() {
    }

    public String getName() {
        return "substring";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        String result = null;
        String exceptionMessage = "One string argument and two integer arguments are required.";
        ArrayList values = FunctionHelper.getOneStringAndTwoIntegers(arguments, ',');
        if(values.size() != 3) {
            throw new FunctionException(exceptionMessage);
        } else {
            try {
                String e = FunctionHelper.trimAndRemoveQuoteChars((String)values.get(0), evaluator.getQuoteCharacter());
                int beginningIndex = ((Integer)values.get(1)).intValue();
                int endingIndex = ((Integer)values.get(2)).intValue();
                result = e.substring(beginningIndex, endingIndex);
            } catch (FunctionException var9) {
                throw new FunctionException(var9.getMessage(), var9);
            } catch (Exception var10) {
                throw new FunctionException(exceptionMessage, var10);
            }

            return new FunctionResult(result, 1);
        }
    }
}

