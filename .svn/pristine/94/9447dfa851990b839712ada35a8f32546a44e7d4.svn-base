package com.zw.rule.jeval;

import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionResult;
import com.zw.rule.jeval.function.math.MathFunctions;
import com.zw.rule.jeval.function.string.StringFunctions;
import com.zw.rule.jeval.operator.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Evaluator {
    private List operators;
    private Map functions;
    private Map variables;
    private char quoteCharacter;
    private Operator openParenthesesOperator;
    private Operator closedParenthesesOperator;
    private boolean loadMathVariables;
    private boolean loadMathFunctions;
    private boolean loadStringFunctions;
    private boolean processNestedFunctions;
    private String previousExpression;
    private Stack previousOperatorStack;
    private Stack previousOperandStack;
    private Stack operatorStack;
    private Stack operandStack;
    private VariableResolver variableResolver;

    public Evaluator() {
        this('\'', true, true, true, true);
    }

    public Evaluator(char quoteCharacter, boolean loadMathVariables, boolean loadMathFunctions, boolean loadStringFunctions, boolean processNestedFunctions) {
        this.operators = new ArrayList();
        this.functions = new HashMap();
        this.variables = new HashMap();
        this.quoteCharacter = 39;
        this.openParenthesesOperator = new OpenParenthesesOperator();
        this.closedParenthesesOperator = new ClosedParenthesesOperator();
        this.previousExpression = null;
        this.previousOperatorStack = null;
        this.previousOperandStack = null;
        this.operatorStack = null;
        this.operandStack = null;
        this.variableResolver = null;
        this.installOperators();
        this.loadMathVariables = loadMathVariables;
        this.loadSystemVariables();
        this.loadMathFunctions = loadMathFunctions;
        this.loadStringFunctions = loadStringFunctions;
        this.loadSystemFunctions();
        this.setQuoteCharacter(quoteCharacter);
        this.processNestedFunctions = processNestedFunctions;
    }

    public char getQuoteCharacter() {
        return this.quoteCharacter;
    }

    public void setQuoteCharacter(char quoteCharacter) {
        if(quoteCharacter != 39 && quoteCharacter != 34) {
            throw new IllegalArgumentException("Invalid quote character.");
        } else {
            this.quoteCharacter = quoteCharacter;
        }
    }

    public void putFunction(Function function) {
        this.isValidName(function.getName());
        Function existingFunction = (Function)this.functions.get(function.getName());
        if(existingFunction == null) {
            this.functions.put(function.getName(), function);
        } else {
            throw new IllegalArgumentException("A function with the same name already exists.");
        }
    }

    public Function getFunction(String functionName) {
        return (Function)this.functions.get(functionName);
    }

    public void removeFunction(String functionName) {
        if(this.functions.containsKey(functionName)) {
            this.functions.remove(functionName);
        } else {
            throw new IllegalArgumentException("The function does not exist.");
        }
    }

    public void clearFunctions() {
        this.functions.clear();
        this.loadSystemFunctions();
    }

    public Map getFunctions() {
        return this.functions;
    }

    public void setFunctions(Map functions) {
        this.functions = functions;
    }

    public void putVariable(String variableName, String variableValue) {
        this.isValidName(variableName);
        this.variables.put(variableName, variableValue);
    }

    public String getVariableValue(String variableName) throws EvaluationException {
        String variableValue = null;
        if(this.variableResolver != null) {
            try {
                variableValue = this.variableResolver.resolveVariable(variableName);
            } catch (FunctionException var4) {
                throw new EvaluationException(var4.getMessage(), var4);
            }
        }

        if(variableValue == null) {
            variableValue = (String)this.variables.get(variableName);
        }

        if(variableValue == null) {
            throw new EvaluationException("Can not resolve variable with name equal to \"" + variableName + "\".");
        } else {
            return variableValue;
        }
    }

    public void removeVaraible(String variableName) {
        if(this.variables.containsKey(variableName)) {
            this.variables.remove(variableName);
        } else {
            throw new IllegalArgumentException("The variable does not exist.");
        }
    }

    public void clearVariables() {
        this.variables.clear();
        this.loadSystemVariables();
    }

    public Map getVariables() {
        return this.variables;
    }

    public void setVariables(Map variables) {
        this.variables = variables;
    }

    public VariableResolver getVariableResolver() {
        return this.variableResolver;
    }

    public void setVariableResolver(VariableResolver variableResolver) {
        this.variableResolver = variableResolver;
    }

    public String evaluate(String expression) throws EvaluationException {
        return this.evaluate(expression, true, true);
    }

    public String evaluate() throws EvaluationException {
        String expression = this.previousExpression;
        if(expression != null && expression.length() != 0) {
            return this.evaluate(expression, true, true);
        } else {
            throw new EvaluationException("No expression has been specified.");
        }
    }

    public String evaluate(String expression, boolean keepQuotes, boolean wrapStringFunctionResults) throws EvaluationException {
        this.parse(expression);
        String result = this.getResult(this.operatorStack, this.operandStack, wrapStringFunctionResults);
        if(this.isExpressionString(result) && !keepQuotes) {
            result = result.substring(1, result.length() - 1);
        }

        return result;
    }

    public String evaluate(boolean keepQuotes, boolean wrapStringFunctionResults) throws EvaluationException {
        String expression = this.previousExpression;
        if(expression != null && expression.length() != 0) {
            return this.evaluate(expression, keepQuotes, wrapStringFunctionResults);
        } else {
            throw new EvaluationException("No expression has been specified.");
        }
    }

    public boolean getBooleanResult(String expression) throws EvaluationException {
        String result = this.evaluate(expression);

        try {
            Double exception = new Double(result);
            return exception.doubleValue() == 1.0D;
        } catch (NumberFormatException var4) {
            return false;
        }
    }

    public double getNumberResult(String expression) throws EvaluationException {
        String result = this.evaluate(expression);
        Double doubleResult = null;

        try {
            doubleResult = new Double(result);
        } catch (NumberFormatException var5) {
            throw new EvaluationException("Expression does not produce a number.", var5);
        }

        return doubleResult.doubleValue();
    }

    public void parse(String expression) throws EvaluationException {
        boolean parse = true;
        if(!expression.equals(this.previousExpression)) {
            this.previousExpression = expression;
        } else {
            parse = false;
            this.operatorStack = (Stack)this.previousOperatorStack.clone();
            this.operandStack = (Stack)this.previousOperandStack.clone();
        }

        try {
            if(parse) {
                this.operandStack = new Stack();
                this.operatorStack = new Stack();
                boolean e = false;
                boolean haveOperator = false;
                Operator unaryOperator = null;
                int numChars = expression.length();
                int charCtr = 0;

                while(true) {
                    while(charCtr < numChars) {
                        Operator operator = null;
                        int operatorIndex = -1;
                        if(EvaluationHelper.isSpace(expression.charAt(charCtr))) {
                            ++charCtr;
                        } else {
                            NextOperator nextOperator = this.getNextOperator(expression, charCtr, (Operator)null);
                            if(nextOperator != null) {
                                operator = nextOperator.getOperator();
                                operatorIndex = nextOperator.getIndex();
                            }

                            if(operatorIndex > charCtr || operatorIndex == -1) {
                                charCtr = this.processOperand(expression, charCtr, operatorIndex, this.operandStack, unaryOperator);
                                e = true;
                                haveOperator = false;
                                unaryOperator = null;
                            }

                            if(operatorIndex == charCtr) {
                                if(!nextOperator.getOperator().isUnary() || !haveOperator && charCtr != 0) {
                                    charCtr = this.processOperator(expression, operatorIndex, operator, this.operatorStack, this.operandStack, e, unaryOperator);
                                    unaryOperator = null;
                                } else {
                                    charCtr = this.processUnaryOperator(operatorIndex, nextOperator.getOperator());
                                    if(unaryOperator != null) {
                                        throw new EvaluationException("Consecutive unary operators are not allowed (index=" + charCtr + ").");
                                    }

                                    unaryOperator = nextOperator.getOperator();
                                }

                                if(!(nextOperator.getOperator() instanceof ClosedParenthesesOperator)) {
                                    e = false;
                                    haveOperator = true;
                                }
                            }
                        }
                    }

                    this.previousOperatorStack = (Stack)this.operatorStack.clone();
                    this.previousOperandStack = (Stack)this.operandStack.clone();
                    break;
                }
            }

        } catch (Exception var11) {
            this.previousExpression = "";
            throw new EvaluationException(var11.getMessage(), var11);
        }
    }

    private void installOperators() {
        this.operators.add(this.openParenthesesOperator);
        this.operators.add(this.closedParenthesesOperator);
        this.operators.add(new AdditionOperator());
        this.operators.add(new SubtractionOperator());
        this.operators.add(new MultiplicationOperator());
        this.operators.add(new DivisionOperator());
        this.operators.add(new EqualOperator());
        this.operators.add(new NotEqualOperator());
        this.operators.add(new LessThanOrEqualOperator());
        this.operators.add(new LessThanOperator());
        this.operators.add(new GreaterThanOrEqualOperator());
        this.operators.add(new GreaterThanOperator());
        this.operators.add(new BooleanAndOperator());
        this.operators.add(new BooleanOrOperator());
        this.operators.add(new BooleanNotOperator());
        this.operators.add(new ModulusOperator());
    }

    private int processOperand(String expression, int charCtr, int operatorIndex, Stack operandStack, Operator unaryOperator) throws EvaluationException {
        String operandString = null;
        boolean rtnCtr = true;
        int rtnCtr1;
        if(operatorIndex == -1) {
            operandString = expression.substring(charCtr).trim();
            rtnCtr1 = expression.length();
        } else {
            operandString = expression.substring(charCtr, operatorIndex).trim();
            rtnCtr1 = operatorIndex;
        }

        if(operandString.length() == 0) {
            throw new EvaluationException("Expression is invalid.");
        } else {
            ExpressionOperand operand = new ExpressionOperand(operandString, unaryOperator);
            operandStack.push(operand);
            return rtnCtr1;
        }
    }

    private int processOperator(String expression, int originalOperatorIndex, Operator originalOperator, Stack operatorStack, Stack operandStack, boolean haveOperand, Operator unaryOperator) throws EvaluationException {
        int operatorIndex = originalOperatorIndex;
        Operator operator = originalOperator;
        if(haveOperand && originalOperator instanceof OpenParenthesesOperator) {
            NextOperator rtnCtr = this.processFunction(expression, originalOperatorIndex, operandStack);
            operator = rtnCtr.getOperator();
            operatorIndex = rtnCtr.getIndex() + operator.getLength();
            rtnCtr = this.getNextOperator(expression, operatorIndex, (Operator)null);
            if(rtnCtr == null) {
                return operatorIndex;
            }

            operator = rtnCtr.getOperator();
            operatorIndex = rtnCtr.getIndex();
        }

        ExpressionOperator rtnCtr1;
        if(operator instanceof OpenParenthesesOperator) {
            rtnCtr1 = new ExpressionOperator(operator, unaryOperator);
            operatorStack.push(rtnCtr1);
        } else if(operator instanceof ClosedParenthesesOperator) {
            rtnCtr1 = null;
            if(operatorStack.size() > 0) {
                rtnCtr1 = (ExpressionOperator)operatorStack.peek();
            }

            while(rtnCtr1 != null && !(rtnCtr1.getOperator() instanceof OpenParenthesesOperator)) {
                this.processTree(operandStack, operatorStack);
                if(operatorStack.size() > 0) {
                    rtnCtr1 = (ExpressionOperator)operatorStack.peek();
                } else {
                    rtnCtr1 = null;
                }
            }

            if(operatorStack.isEmpty()) {
                throw new EvaluationException("Expression is invalid.");
            }

            ExpressionOperator expressionOperator = (ExpressionOperator)operatorStack.pop();
            if(!(expressionOperator.getOperator() instanceof OpenParenthesesOperator)) {
                throw new EvaluationException("Expression is invalid.");
            }

            if(expressionOperator.getUnaryOperator() != null) {
                Object operand = operandStack.pop();
                ExpressionTree tree = new ExpressionTree(this, operand, (Object)null, (Operator)null, expressionOperator.getUnaryOperator());
                operandStack.push(tree);
            }
        } else {
            if(operatorStack.size() > 0) {
                rtnCtr1 = (ExpressionOperator)operatorStack.peek();

                while(rtnCtr1 != null && rtnCtr1.getOperator().getPrecedence() >= operator.getPrecedence()) {
                    this.processTree(operandStack, operatorStack);
                    if(operatorStack.size() > 0) {
                        rtnCtr1 = (ExpressionOperator)operatorStack.peek();
                    } else {
                        rtnCtr1 = null;
                    }
                }
            }

            rtnCtr1 = new ExpressionOperator(operator, unaryOperator);
            operatorStack.push(rtnCtr1);
        }

        int rtnCtr2 = operatorIndex + operator.getLength();
        return rtnCtr2;
    }

    private int processUnaryOperator(int operatorIndex, Operator operator) {
        int rtnCtr = operatorIndex + operator.getSymbol().length();
        return rtnCtr;
    }

    private NextOperator processFunction(String expression, int operatorIndex, Stack operandStack) throws EvaluationException {
        int parenthesisCount = 1;
        NextOperator nextOperator = null;

        int nextOperatorIndex;
        for(nextOperatorIndex = operatorIndex; parenthesisCount > 0; nextOperatorIndex = nextOperator.getIndex()) {
            nextOperator = this.getNextOperator(expression, nextOperatorIndex + 1, (Operator)null);
            if(nextOperator == null) {
                throw new EvaluationException("Function is not closed.");
            }

            if(nextOperator.getOperator() instanceof OpenParenthesesOperator) {
                ++parenthesisCount;
            } else if(nextOperator.getOperator() instanceof ClosedParenthesesOperator) {
                --parenthesisCount;
            }
        }

        String arguments = expression.substring(operatorIndex + 1, nextOperatorIndex);
        ExpressionOperand operand = (ExpressionOperand)operandStack.pop();
        Operator unaryOperator = operand.getUnaryOperator();
        String functionName = operand.getValue();

        try {
            this.isValidName(functionName);
        } catch (IllegalArgumentException var13) {
            throw new EvaluationException("Invalid function name of \"" + functionName + "\".", var13);
        }

        Function function = (Function)this.functions.get(functionName);
        if(function == null) {
            throw new EvaluationException("A function is not defined (index=" + operatorIndex + ").");
        } else {
            ParsedFunction parsedFunction = new ParsedFunction(function, arguments, unaryOperator);
            operandStack.push(parsedFunction);
            return nextOperator;
        }
    }

    private void processTree(Stack operandStack, Stack operatorStack) {
        Object rightOperand = null;
        Object leftOperand = null;
        Operator operator = null;
        if(operandStack.size() > 0) {
            rightOperand = operandStack.pop();
        }

        if(operandStack.size() > 0) {
            leftOperand = operandStack.pop();
        }

        operator = ((ExpressionOperator)operatorStack.pop()).getOperator();
        ExpressionTree tree = new ExpressionTree(this, leftOperand, rightOperand, operator, (Operator)null);
        operandStack.push(tree);
    }

    private String getResult(Stack operatorStack, Stack operandStack, boolean wrapStringFunctionResults) throws EvaluationException {
        String resultString = null;

        while(operatorStack.size() > 0) {
            this.processTree(operandStack, operatorStack);
        }

        if(operandStack.size() != 1) {
            throw new EvaluationException("Expression is invalid.");
        } else {
            Object finalOperand = operandStack.pop();
            if(finalOperand instanceof ExpressionTree) {
                resultString = ((ExpressionTree)finalOperand).evaluate(wrapStringFunctionResults);
            } else if(finalOperand instanceof ExpressionOperand) {
                ExpressionOperand parsedFunction = (ExpressionOperand)finalOperand;
                resultString = ((ExpressionOperand)finalOperand).getValue();
                resultString = this.replaceVariables(resultString);
                if(!this.isExpressionString(resultString)) {
                    Double function = null;

                    try {
                        function = new Double(resultString);
                    } catch (Exception var12) {
                        throw new EvaluationException("Expression is invalid.", var12);
                    }

                    if(parsedFunction.getUnaryOperator() != null) {
                        function = new Double(parsedFunction.getUnaryOperator().evaluate(function.doubleValue()));
                    }

                    resultString = function.toString();
                } else if(parsedFunction.getUnaryOperator() != null) {
                    throw new EvaluationException("Invalid operand for unary operator.");
                }
            } else {
                if(!(finalOperand instanceof ParsedFunction)) {
                    throw new EvaluationException("Expression is invalid.");
                }

                ParsedFunction parsedFunction1 = (ParsedFunction)finalOperand;
                Function function1 = parsedFunction1.getFunction();
                String arguments = parsedFunction1.getArguments();
                if(this.processNestedFunctions) {
                    arguments = this.processNestedFunctions(arguments);
                }

                arguments = this.replaceVariables(arguments);

                try {
                    FunctionResult fe = function1.execute(this, arguments);
                    resultString = fe.getResult();
                    if(fe.getType() == 0) {
                        Double resultDouble = new Double(resultString);
                        if(parsedFunction1.getUnaryOperator() != null) {
                            resultDouble = new Double(parsedFunction1.getUnaryOperator().evaluate(resultDouble.doubleValue()));
                        }

                        resultString = resultDouble.toString();
                    } else if(fe.getType() == 1) {
                        if(wrapStringFunctionResults) {
                            resultString = this.quoteCharacter + resultString + this.quoteCharacter;
                        }

                        if(parsedFunction1.getUnaryOperator() != null) {
                            throw new EvaluationException("Invalid operand for unary operator.");
                        }
                    }
                } catch (FunctionException var11) {
                    throw new EvaluationException(var11.getMessage(), var11);
                }
            }

            return resultString;
        }
    }

    private NextOperator getNextOperator(String expression, int start, Operator match) {
        int numChars = expression.length();
        int numQuoteCharacters = 0;

        for(int charCtr = start; charCtr < numChars; ++charCtr) {
            if(expression.charAt(charCtr) == this.quoteCharacter) {
                ++numQuoteCharacters;
            }

            if(numQuoteCharacters % 2 != 1) {
                int numOperators = this.operators.size();

                for(int operatorCtr = 0; operatorCtr < numOperators; ++operatorCtr) {
                    Operator operator = (Operator)this.operators.get(operatorCtr);
                    if(match == null || match.equals(operator)) {
                        if(operator.getLength() == 2) {
                            boolean nextOperator = true;
                            int var12;
                            if(charCtr + 2 <= expression.length()) {
                                var12 = charCtr + 2;
                            } else {
                                var12 = expression.length();
                            }

                            if(expression.substring(charCtr, var12).equals(operator.getSymbol())) {
                                NextOperator nextOperator1 = new NextOperator(operator, charCtr);
                                return nextOperator1;
                            }
                        } else if(expression.charAt(charCtr) == operator.getSymbol().charAt(0)) {
                            NextOperator var13 = new NextOperator(operator, charCtr);
                            return var13;
                        }
                    }
                }
            }
        }

        return null;
    }

    protected boolean isExpressionString(String expressionString) throws EvaluationException {
        if(expressionString.length() > 1 && expressionString.charAt(0) == this.quoteCharacter && expressionString.charAt(expressionString.length() - 1) == this.quoteCharacter) {
            return true;
        } else if(expressionString.indexOf(this.quoteCharacter) >= 0) {
            throw new EvaluationException("Invalid use of quotes.");
        } else {
            return false;
        }
    }

    public void isValidName(String name) throws IllegalArgumentException {
        if(name.length() == 0) {
            throw new IllegalArgumentException("Variable is empty.");
        } else {
            char firstChar = name.charAt(0);
            if(name.indexOf(39) > -1) {
                throw new IllegalArgumentException("A variable or function name can not contain a quote character.");
            } else if(name.indexOf(34) > -1) {
                throw new IllegalArgumentException("A variable or function name can not contain a quote character.");
            } else if(name.indexOf(123) > -1) {
                throw new IllegalArgumentException("A variable or function name can not contain an open brace character.");
            } else if(name.indexOf(125) > -1) {
                throw new IllegalArgumentException("A variable or function name can not contain a closed brace character.");
            } else if(name.indexOf(35) > -1) {
                throw new IllegalArgumentException("A variable or function name can not contain a pound sign character.");
            } else {
                Iterator operatorIterator = this.operators.iterator();

                while(operatorIterator.hasNext()) {
                    Operator operator = (Operator)operatorIterator.next();
                    if(name.indexOf(operator.getSymbol()) > -1) {
                        throw new IllegalArgumentException("A variable or function name can not contain an operator symbol.");
                    }
                }

                if(name.indexOf("!") > -1) {
                    throw new IllegalArgumentException("A variable or function name can not contain a special character.");
                } else if(name.indexOf("~") > -1) {
                    throw new IllegalArgumentException("A variable or function name can not contain a special character.");
                } else if(name.indexOf("^") > -1) {
                    throw new IllegalArgumentException("A variable or function name can not contain a special character.");
                } else if(name.indexOf(",") > -1) {
                    throw new IllegalArgumentException("A variable or function name can not contain a special character.");
                }
            }
        }
    }

    private void loadSystemFunctions() {
        if(this.loadMathFunctions) {
            MathFunctions stringFunctions = new MathFunctions();
            stringFunctions.load(this);
        }

        if(this.loadStringFunctions) {
            StringFunctions stringFunctions1 = new StringFunctions();
            stringFunctions1.load(this);
        }

    }

    private void loadSystemVariables() {
        if(this.loadMathVariables) {
            this.putVariable("E", (new Double(2.718281828459045D)).toString());
            this.putVariable("PI", (new Double(3.141592653589793D)).toString());
        }

    }

    public String replaceVariables(String expression) throws EvaluationException {
        int openIndex = expression.indexOf(EvaluationConstants.OPEN_VARIABLE);
        if(openIndex < 0) {
            return expression;
        } else {
            String replacedExpression;
            int openBraceIndex1;
            for(replacedExpression = expression; openIndex >= 0; openIndex = replacedExpression.indexOf(EvaluationConstants.OPEN_VARIABLE)) {
                boolean openBraceIndex = true;
                if(openIndex >= 0) {
                    openBraceIndex1 = replacedExpression.indexOf(EvaluationConstants.CLOSED_VARIABLE, openIndex + 1);
                    if(openBraceIndex1 <= openIndex) {
                        break;
                    }

                    String variableName = replacedExpression.substring(openIndex + EvaluationConstants.OPEN_VARIABLE.length(), openBraceIndex1);

                    try {
                        this.isValidName(variableName);
                    } catch (IllegalArgumentException var8) {
                        throw new EvaluationException("Invalid variable name of \"" + variableName + "\".", var8);
                    }

                    String variableValue = this.getVariableValue(variableName);
                    String variableString = EvaluationConstants.OPEN_VARIABLE + variableName + EvaluationConstants.CLOSED_VARIABLE;
                    replacedExpression = EvaluationHelper.replaceAll(replacedExpression, variableString, variableValue);
                }
            }

            openBraceIndex1 = replacedExpression.indexOf(EvaluationConstants.OPEN_VARIABLE);
            if(openBraceIndex1 > -1) {
                throw new EvaluationException("A variable has not been closed (index=" + openBraceIndex1 + ").");
            } else {
                return replacedExpression;
            }
        }
    }

    protected String processNestedFunctions(String arguments) throws EvaluationException {
        StringBuffer evaluatedArguments = new StringBuffer();
        if(arguments.length() > 0) {
            Evaluator argumentsEvaluator = new Evaluator(this.quoteCharacter, this.loadMathVariables, this.loadMathFunctions, this.loadStringFunctions, this.processNestedFunctions);
            argumentsEvaluator.setFunctions(this.getFunctions());
            argumentsEvaluator.setVariables(this.getVariables());
            argumentsEvaluator.setVariableResolver(this.getVariableResolver());
            ArgumentTokenizer tokenizer = new ArgumentTokenizer(arguments, ',');

            ArrayList evalautedArgumentList;
            String evaluatedArgumentIterator;
            for(evalautedArgumentList = new ArrayList(); tokenizer.hasMoreTokens(); evalautedArgumentList.add(evaluatedArgumentIterator)) {
                evaluatedArgumentIterator = tokenizer.nextToken().trim();

                try {
                    evaluatedArgumentIterator = argumentsEvaluator.evaluate(evaluatedArgumentIterator);
                } catch (Exception var8) {
                    throw new EvaluationException(var8.getMessage(), var8);
                }
            }

            Iterator evaluatedArgumentIterator1 = evalautedArgumentList.iterator();

            while(evaluatedArgumentIterator1.hasNext()) {
                if(evaluatedArguments.length() > 0) {
                    evaluatedArguments.append(',');
                }

                String evaluatedArgument = (String)evaluatedArgumentIterator1.next();
                evaluatedArguments.append(evaluatedArgument);
            }
        }

        return evaluatedArguments.toString();
    }

    public boolean isLoadMathVariables() {
        return this.loadMathVariables;
    }

    public boolean getLoadMathFunctions() {
        return this.loadMathFunctions;
    }

    public boolean getLoadStringFunctions() {
        return this.loadStringFunctions;
    }

    public boolean getProcessNestedFunctions() {
        return this.processNestedFunctions;
    }
}

