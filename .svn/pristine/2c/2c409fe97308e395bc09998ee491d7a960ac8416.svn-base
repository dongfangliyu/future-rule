package com.zw.rule.jeval;



import java.util.Enumeration;

public class ArgumentTokenizer implements Enumeration {
    public final char defaultDelimiter = 44;
    private String arguments = null;
    private char delimiter = 44;

    public ArgumentTokenizer(String arguments, char delimiter) {
        this.arguments = arguments;
        this.delimiter = delimiter;
    }

    public boolean hasMoreElements() {
        return this.hasMoreTokens();
    }

    public boolean hasMoreTokens() {
        return this.arguments.length() > 0;
    }

    public Object nextElement() {
        return this.nextToken();
    }

    public String nextToken() {
        int charCtr = 0;
        int size = this.arguments.length();
        int parenthesesCtr = 0;

        String returnArgument;
        for(returnArgument = null; charCtr < size; ++charCtr) {
            if(this.arguments.charAt(charCtr) == 40) {
                ++parenthesesCtr;
            } else if(this.arguments.charAt(charCtr) == 41) {
                --parenthesesCtr;
            } else if(this.arguments.charAt(charCtr) == this.delimiter && parenthesesCtr == 0) {
                returnArgument = this.arguments.substring(0, charCtr);
                this.arguments = this.arguments.substring(charCtr + 1);
                break;
            }
        }

        if(returnArgument == null) {
            returnArgument = this.arguments;
            this.arguments = "";
        }

        return returnArgument;
    }
}
