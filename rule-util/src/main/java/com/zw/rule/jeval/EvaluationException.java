package com.zw.rule.jeval;



public class EvaluationException extends Exception {
    private static final long serialVersionUID = -3010333364122748053L;

    public EvaluationException(String message) {
        super(message);
    }

    public EvaluationException(Exception exception) {
        super(exception);
    }

    public EvaluationException(String message, Exception exception) {
        super(message, exception);
    }
}

