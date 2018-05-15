package com.zw.rule.jeval;



public class EvaluationResult {
    private String result;
    private char quoteCharacter;

    public EvaluationResult(String result, char quoteCharacter) {
        this.result = result;
        this.quoteCharacter = quoteCharacter;
    }

    public char getQuoteCharacter() {
        return this.quoteCharacter;
    }

    public void setQuoteCharacter(char quoteCharacter) {
        this.quoteCharacter = quoteCharacter;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isBooleanTrue() {
        return this.result != null && "1.0".equals(this.result);
    }

    public boolean isBooleanFalse() {
        return this.result != null && "0.0".equals(this.result);
    }

    public boolean isString() {
        return this.result != null && this.result.length() >= 2 && this.result.charAt(0) == this.quoteCharacter && this.result.charAt(this.result.length() - 1) == this.quoteCharacter;
    }

    public Double getDouble() throws NumberFormatException {
        return new Double(this.result);
    }

    public String getUnwrappedString() {
        return this.result != null && this.result.length() >= 2 && this.result.charAt(0) == this.quoteCharacter && this.result.charAt(this.result.length() - 1) == this.quoteCharacter?this.result.substring(1, this.result.length() - 1):null;
    }
}

