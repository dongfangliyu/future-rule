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
public class CharAt implements Function {
    public CharAt() {
    }

    public String getName() {
        return "charAt";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        String result = null;
        String exceptionMessage = "One string and one integer argument are required.";
        ArrayList values = FunctionHelper.getOneStringAndOneInteger(arguments, ',');
        if(values.size() != 2) {
            throw new FunctionException(exceptionMessage);
        } else {
            try {
                String e = FunctionHelper.trimAndRemoveQuoteChars((String)values.get(0), evaluator.getQuoteCharacter());
                int index = ((Integer)values.get(1)).intValue();
                char[] character = new char[]{e.charAt(index)};
                result = new String(character);
            } catch (FunctionException var9) {
                throw new FunctionException(var9.getMessage(), var9);
            } catch (Exception var10) {
                throw new FunctionException(exceptionMessage, var10);
            }

            return new FunctionResult(result, 1);
        }
    }
}

