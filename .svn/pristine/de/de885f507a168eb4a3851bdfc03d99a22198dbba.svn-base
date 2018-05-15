package com.zw.rule.jeval;



public class EvaluationHelper {
    public EvaluationHelper() {
    }

    public static String replaceAll(String expression, String oldString, String newString) {
        String replacedExpression = expression;
        if(expression != null) {
            byte charCtr = 0;
            int oldStringIndex = expression.indexOf(oldString, charCtr);

            while(oldStringIndex > -1) {
                StringBuffer buffer = new StringBuffer(replacedExpression.substring(0, oldStringIndex) + replacedExpression.substring(oldStringIndex + oldString.length()));
                buffer.insert(oldStringIndex, newString);
                replacedExpression = buffer.toString();
                int charCtr1 = oldStringIndex + newString.length();
                if(charCtr1 < replacedExpression.length()) {
                    oldStringIndex = replacedExpression.indexOf(oldString, charCtr1);
                } else {
                    oldStringIndex = -1;
                }
            }
        }

        return replacedExpression;
    }

    public static boolean isSpace(char character) {
        return character == 32 || character == 9 || character == 10 || character == 13 || character == 12;
    }
}

