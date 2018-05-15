package com.zw.rule.jeval.function.string;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionHelper;
import com.zw.rule.jeval.function.FunctionResult;

/**
 * Created by Administrator on 2017/5/5.
 */
public class Length implements Function {
    public Length() {
    }

    public String getName() {
        return "length";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        Integer result = null;
        String exceptionMessage = "One string argument is required.";

        try {
            String argumentOne = FunctionHelper.trimAndRemoveQuoteChars(arguments, evaluator.getQuoteCharacter());
            result = new Integer(argumentOne.length());
        } catch (FunctionException var7) {
            throw new FunctionException(var7.getMessage(), var7);
        } catch (Exception var8) {
            throw new FunctionException(exceptionMessage, var8);
        }

        return new FunctionResult(result.toString(), 0);
    }
}