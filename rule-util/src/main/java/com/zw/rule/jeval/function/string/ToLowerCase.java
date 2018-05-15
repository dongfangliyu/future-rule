package com.zw.rule.jeval.function.string;

import com.zw.rule.jeval.Evaluator;
import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionHelper;
import com.zw.rule.jeval.function.FunctionResult;

/**
 * Created by Administrator on 2017/5/5.
 */
public class ToLowerCase implements Function {
    public ToLowerCase() {
    }

    public String getName() {
        return "toLowerCase";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        String result = null;
        String exceptionMessage = "One string argument is required.";

        try {
            String argumentOne = FunctionHelper.trimAndRemoveQuoteChars(arguments, evaluator.getQuoteCharacter());
            result = argumentOne.toLowerCase();
        } catch (FunctionException var7) {
            throw new FunctionException(var7.getMessage(), var7);
        } catch (Exception var8) {
            throw new FunctionException(exceptionMessage, var8);
        }

        return new FunctionResult(result, 1);
    }
}