package com.zw.rule.jeval;


import com.zw.rule.jeval.function.Function;
import com.zw.rule.jeval.function.FunctionException;
import com.zw.rule.jeval.function.FunctionResult;
import com.zw.rule.jeval.operator.Operator;

public class ExpressionTree {
    private Object leftOperand = null;
    private Object rightOperand = null;
    private Operator operator = null;
    private Operator unaryOperator = null;
    private Evaluator evaluator = null;

    public ExpressionTree(Evaluator evaluator, Object leftOperand, Object rightOperand, Operator operator, Operator unaryOperator) {
        this.evaluator = evaluator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
        this.unaryOperator = unaryOperator;
    }

    public Object getLeftOperand() {
        return this.leftOperand;
    }

    public Object getRightOperand() {
        return this.rightOperand;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public Operator getUnaryOperator() {
        return this.unaryOperator;
    }

    public String evaluate(boolean wrapStringFunctionResults) throws EvaluationException {
        String rtnResult = null;
        String leftResultString = null;
        Double leftResultDouble = null;
        if(this.leftOperand instanceof ExpressionTree) {
            leftResultString = ((ExpressionTree)this.leftOperand).evaluate(wrapStringFunctionResults);

            try {
                leftResultDouble = new Double(leftResultString);
                leftResultString = null;
            } catch (NumberFormatException var19) {
                leftResultDouble = null;
            }
        } else if(this.leftOperand instanceof ExpressionOperand) {
            ExpressionOperand rightResultString = (ExpressionOperand)this.leftOperand;
            leftResultString = rightResultString.getValue();
            leftResultString = this.evaluator.replaceVariables(leftResultString);
            if(!this.evaluator.isExpressionString(leftResultString)) {
                try {
                    leftResultDouble = new Double(leftResultString);
                    leftResultString = null;
                } catch (NumberFormatException var18) {
                    throw new EvaluationException("Expression is invalid.", var18);
                }

                if(rightResultString.getUnaryOperator() != null) {
                    leftResultDouble = new Double(rightResultString.getUnaryOperator().evaluate(leftResultDouble.doubleValue()));
                }
            } else if(rightResultString.getUnaryOperator() != null) {
                throw new EvaluationException("Invalid operand for unary operator.");
            }
        } else if(this.leftOperand instanceof ParsedFunction) {
            ParsedFunction rightResultString1 = (ParsedFunction)this.leftOperand;
            Function rightResultDouble = rightResultString1.getFunction();
            String doubleResult = rightResultString1.getArguments();
            doubleResult = this.evaluator.replaceVariables(doubleResult);
            if(this.evaluator.getProcessNestedFunctions()) {
                doubleResult = this.evaluator.processNestedFunctions(doubleResult);
            }

            try {
                FunctionResult function = rightResultDouble.execute(this.evaluator, doubleResult);
                leftResultString = function.getResult();
                if(function.getType() == 0) {
                    Double arguments = new Double(leftResultString);
                    if(rightResultString1.getUnaryOperator() != null) {
                        arguments = new Double(rightResultString1.getUnaryOperator().evaluate(arguments.doubleValue()));
                    }

                    leftResultString = arguments.toString();
                } else if(function.getType() == 1) {
                    if(wrapStringFunctionResults) {
                        leftResultString = this.evaluator.getQuoteCharacter() + leftResultString + this.evaluator.getQuoteCharacter();
                    }

                    if(rightResultString1.getUnaryOperator() != null) {
                        throw new EvaluationException("Invalid operand for unary operator.");
                    }
                }
            } catch (FunctionException var17) {
                throw new EvaluationException(var17.getMessage(), var17);
            }

            if(!this.evaluator.isExpressionString(leftResultString)) {
                try {
                    leftResultDouble = new Double(leftResultString);
                    leftResultString = null;
                } catch (NumberFormatException var16) {
                    throw new EvaluationException("Expression is invalid.", var16);
                }
            }
        } else if(this.leftOperand != null) {
            throw new EvaluationException("Expression is invalid.");
        }

        String rightResultString2 = null;
        Double rightResultDouble1 = null;
        if(this.rightOperand instanceof ExpressionTree) {
            rightResultString2 = ((ExpressionTree)this.rightOperand).evaluate(wrapStringFunctionResults);

            try {
                rightResultDouble1 = new Double(rightResultString2);
                rightResultString2 = null;
            } catch (NumberFormatException var15) {
                rightResultDouble1 = null;
            }
        } else if(this.rightOperand instanceof ExpressionOperand) {
            ExpressionOperand doubleResult1 = (ExpressionOperand)this.rightOperand;
            rightResultString2 = ((ExpressionOperand)this.rightOperand).getValue();
            rightResultString2 = this.evaluator.replaceVariables(rightResultString2);
            if(!this.evaluator.isExpressionString(rightResultString2)) {
                try {
                    rightResultDouble1 = new Double(rightResultString2);
                    rightResultString2 = null;
                } catch (NumberFormatException var14) {
                    throw new EvaluationException("Expression is invalid.", var14);
                }

                if(doubleResult1.getUnaryOperator() != null) {
                    rightResultDouble1 = new Double(doubleResult1.getUnaryOperator().evaluate(rightResultDouble1.doubleValue()));
                }
            } else if(doubleResult1.getUnaryOperator() != null) {
                throw new EvaluationException("Invalid operand for unary operator.");
            }
        } else if(this.rightOperand instanceof ParsedFunction) {
            ParsedFunction doubleResult2 = (ParsedFunction)this.rightOperand;
            Function function1 = doubleResult2.getFunction();
            String arguments1 = doubleResult2.getArguments();
            arguments1 = this.evaluator.replaceVariables(arguments1);
            if(this.evaluator.getProcessNestedFunctions()) {
                arguments1 = this.evaluator.processNestedFunctions(arguments1);
            }

            try {
                FunctionResult nfe = function1.execute(this.evaluator, arguments1);
                rightResultString2 = nfe.getResult();
                if(nfe.getType() == 0) {
                    Double resultDouble = new Double(rightResultString2);
                    if(doubleResult2.getUnaryOperator() != null) {
                        resultDouble = new Double(doubleResult2.getUnaryOperator().evaluate(resultDouble.doubleValue()));
                    }

                    rightResultString2 = resultDouble.toString();
                } else if(nfe.getType() == 1) {
                    if(wrapStringFunctionResults) {
                        rightResultString2 = this.evaluator.getQuoteCharacter() + rightResultString2 + this.evaluator.getQuoteCharacter();
                    }

                    if(doubleResult2.getUnaryOperator() != null) {
                        throw new EvaluationException("Invalid operand for unary operator.");
                    }
                }
            } catch (FunctionException var13) {
                throw new EvaluationException(var13.getMessage(), var13);
            }

            if(!this.evaluator.isExpressionString(rightResultString2)) {
                try {
                    rightResultDouble1 = new Double(rightResultString2);
                    rightResultString2 = null;
                } catch (NumberFormatException var12) {
                    throw new EvaluationException("Expression is invalid.", var12);
                }
            }
        } else if(this.rightOperand != null) {
            throw new EvaluationException("Expression is invalid.");
        }

        double doubleResult3;
        if(leftResultDouble != null && rightResultDouble1 != null) {
            doubleResult3 = this.operator.evaluate(leftResultDouble.doubleValue(), rightResultDouble1.doubleValue());
            if(this.getUnaryOperator() != null) {
                doubleResult3 = this.getUnaryOperator().evaluate(doubleResult3);
            }

            rtnResult = (new Double(doubleResult3)).toString();
        } else if(leftResultString != null && rightResultString2 != null) {
            rtnResult = this.operator.evaluate(leftResultString, rightResultString2);
        } else {
            if(leftResultDouble == null || rightResultDouble1 != null) {
                throw new EvaluationException("Expression is invalid.");
            }

            doubleResult3 = -1.0D;
            if(this.unaryOperator == null) {
                throw new EvaluationException("Expression is invalid.");
            }

            doubleResult3 = this.unaryOperator.evaluate(leftResultDouble.doubleValue());
            rtnResult = (new Double(doubleResult3)).toString();
        }

        return rtnResult;
    }
}
