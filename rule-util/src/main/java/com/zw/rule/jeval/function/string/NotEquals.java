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
public class NotEquals implements Function {
    public NotEquals() {
    }

    public String getName() {
        return "notEquals";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        String result = null;
        String exceptionMessage = "Two string arguments are required.";
        ArrayList strings = FunctionHelper.getStrings(arguments, ',');
        if(strings.size() != 2) {
            throw new FunctionException(exceptionMessage);
        } else {
            try {
                String e = FunctionHelper.trimAndRemoveQuoteChars((String)strings.get(0), evaluator.getQuoteCharacter());
                String argumentTwo = FunctionHelper.trimAndRemoveQuoteChars((String)strings.get(1), evaluator.getQuoteCharacter());
                if(e.equals(argumentTwo)) {
                    result = "0.0";
                } else {
                    result = "1.0";
                }
            } catch (FunctionException var8) {
                throw new FunctionException(var8.getMessage(), var8);
            } catch (Exception var9) {
                throw new FunctionException(exceptionMessage, var9);
            }

            return new FunctionResult(result, 0);
        }
    }
}
