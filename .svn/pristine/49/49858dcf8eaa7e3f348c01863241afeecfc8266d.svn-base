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
public class Replace implements Function {
    public Replace() {
    }

    public String getName() {
        return "replace";
    }

    public FunctionResult execute(Evaluator evaluator, String arguments) throws FunctionException {
        String result = null;
        String exceptionMessage = "One string argument and two character arguments are required.";
        ArrayList values = FunctionHelper.getStrings(arguments, ',');
        if(values.size() != 3) {
            throw new FunctionException(exceptionMessage);
        } else {
            try {
                String e = FunctionHelper.trimAndRemoveQuoteChars((String)values.get(0), evaluator.getQuoteCharacter());
                String argumentTwo = FunctionHelper.trimAndRemoveQuoteChars((String)values.get(1), evaluator.getQuoteCharacter());
                String argumentThree = FunctionHelper.trimAndRemoveQuoteChars((String)values.get(2), evaluator.getQuoteCharacter());
                boolean oldCharacter = true;
                if(argumentTwo.length() != 1) {
                    throw new FunctionException(exceptionMessage);
                }

                char oldCharacter1 = argumentTwo.charAt(0);
                boolean newCharacter = true;
                if(argumentThree.length() != 1) {
                    throw new FunctionException(exceptionMessage);
                }

                char newCharacter1 = argumentThree.charAt(0);
                result = e.replace(oldCharacter1, newCharacter1);
            } catch (FunctionException var11) {
                throw new FunctionException(var11.getMessage(), var11);
            } catch (Exception var12) {
                throw new FunctionException(exceptionMessage, var12);
            }

            return new FunctionResult(result, 1);
        }
    }
}

