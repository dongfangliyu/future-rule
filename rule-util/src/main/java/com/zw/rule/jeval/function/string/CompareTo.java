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
public class CompareTo implements Function {
    public CompareTo() {
    }

    public String getName() {
        return "compareTo";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        Integer result = null;
        String exceptionMessage = "Two string arguments are required.";
        ArrayList strings = FunctionHelper.getStrings(arguments, ',');
        if(strings.size() != 2) {
            throw new FunctionException(exceptionMessage);
        } else {
            try {
                String e = FunctionHelper.trimAndRemoveQuoteChars((String)strings.get(0), evaluator.getQuoteCharacter());
                String argumentTwo = FunctionHelper.trimAndRemoveQuoteChars((String)strings.get(1), evaluator.getQuoteCharacter());
                result = new Integer(e.compareTo(argumentTwo));
            } catch (FunctionException var8) {
                throw new FunctionException(var8.getMessage(), var8);
            } catch (Exception var9) {
                throw new FunctionException(exceptionMessage, var9);
            }

            return new FunctionResult(result.toString(), 0);
        }
    }
}