package com.zw.rule.jeval.tools;


import com.zw.rule.jeval.EvaluationException;
import com.zw.rule.jeval.Evaluator;

import java.util.HashMap;
import java.util.Map;

public class JevalUtil
{
    public static Boolean evaluateBoolean(String expression, Map<String, Object> params)
            throws EvaluationException
    {
        Evaluator evaluator = getEvaluator(params);
        return Boolean.valueOf(evaluator.getBooleanResult(expression));
    }

    public static Double evaluateNumric(String expression, Map<String, Object> params)
            throws EvaluationException
    {
        Evaluator evaluator = getEvaluator(params);
        return Double.valueOf(evaluator.getNumberResult(expression));
    }

    public static String evaluateString(String expression, Map<String, Object> params)
            throws EvaluationException
    {
        Evaluator evaluator = getEvaluator(params);
        return evaluator.evaluate(expression, false, true);
    }

    private static Evaluator getEvaluator(Map<String, Object> params)
    {
        Evaluator evaluator = new Evaluator();
        if ((params != null) && (!params.isEmpty())) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (null != entry.getValue()) {
                    evaluator.putVariable((String)entry.getKey(), entry.getValue().toString());
                }
            }
        }
        return evaluator;
    }

    public static String getNumericInterval(String expression, String param)
    {
        StringBuffer result = new StringBuffer();

        param = "#{" + param + "}";
        if ((!expression.startsWith("(")) && (!expression.startsWith("[")) &&
                (!expression.endsWith(")")) && (!expression.endsWith("]")))
        {
            result.append(param).append(" ").append("==").append(" ").append(expression);
            return result.toString();
        }
        String exp = expression.substring(1, expression.length() - 1);
        String[] segments = null;
        if (exp.startsWith(","))
        {
            segments = new String[1];
            segments[0] = exp.substring(1);
        }
        else
        {
            segments = exp.split(",");
        }
        if (segments.length == 1)
        {
            if (expression.substring(1, expression.length() - 1).startsWith(","))
            {
                if (expression.endsWith(")")) {
                    result.append(param).append(" ").append("<").append(" ").append(segments[0]);
                } else if (expression.endsWith("]")) {
                    result.append(param).append(" ").append("<=").append(" ").append(segments[0]);
                }
            }
            else if (expression.startsWith("(")) {
                result.append(param).append(" ").append(">").append(" ").append(segments[0]);
            } else if (expression.startsWith("[")) {
                result.append(param).append(" ").append(">=").append(" ").append(segments[0]);
            }
        }
        else if (segments.length == 2)
        {
            if (expression.startsWith("(")) {
                result.append(param).append(" ").append(">").append(" ").append(segments[0]);
            } else if (expression.startsWith("[")) {
                result.append(param).append(" ").append(">=").append(" ").append(segments[0]);
            }
            result.append(" ").append("&&").append(" ");
            if (expression.endsWith(")")) {
                result.append(param).append(" ").append("<").append(" ").append(segments[1]);
            } else if (expression.endsWith("]")) {
                result.append(param).append(" ").append("<=").append(" ").append(segments[1]);
            }
        }
        return result.toString();
    }

    public static Map<String, Object> convertVariables(Map<String, Integer> fieldTypeMap, Map<String, Object> fieldMap)
    {
        Map<String, Object> variablesMap = new HashMap();
        String key;
        Integer value;
        if ((fieldMap != null) && (!fieldMap.isEmpty()))
        {
            key = "";
            value = null;
            for (Map.Entry<String, Object> entry : fieldMap.entrySet())
            {
                key = (String)entry.getKey();
                value = (Integer)fieldTypeMap.get(key);
                if (value == null)
                {
                    variablesMap.put(key, fieldMap.get(key));
                }
                else if (2 == value.intValue())
                {
                    String variableValue = "'" + fieldMap.get(key).toString() + "'";
                    variablesMap.put(key, variableValue);
                }
                else
                {
                    variablesMap.put(key, fieldMap.get(key));
                }
            }
        }
        return variablesMap;
    }
}


