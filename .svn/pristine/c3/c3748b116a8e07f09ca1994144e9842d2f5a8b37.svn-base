package com.zw.rule.jeval.function;

import com.zw.rule.jeval.ArgumentTokenizer;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class FunctionHelper {
    public FunctionHelper() {
    }

    public static String trimAndRemoveQuoteChars(String arguments, char quoteCharacter) throws FunctionException {
        String trimmedArgument = arguments.trim();
        if(trimmedArgument.charAt(0) == quoteCharacter) {
            trimmedArgument = trimmedArgument.substring(1, trimmedArgument.length());
            if(trimmedArgument.charAt(trimmedArgument.length() - 1) == quoteCharacter) {
                trimmedArgument = trimmedArgument.substring(0, trimmedArgument.length() - 1);
                return trimmedArgument;
            } else {
                throw new FunctionException("Value does not end with a quote.");
            }
        } else {
            throw new FunctionException("Value does not start with a quote.");
        }
    }

    public static ArrayList getDoubles(String arguments, char delimiter) throws FunctionException {
        ArrayList returnValues = new ArrayList();

        try {
            ArgumentTokenizer e = new ArgumentTokenizer(arguments, delimiter);

            while(e.hasMoreTokens()) {
                String token = e.nextToken().trim();
                returnValues.add(new Double(token));
            }

            return returnValues;
        } catch (Exception var5) {
            throw new FunctionException("Invalid values in string.", var5);
        }
    }

    public static ArrayList getStrings(String arguments, char delimiter) throws FunctionException {
        ArrayList returnValues = new ArrayList();

        try {
            ArgumentTokenizer e = new ArgumentTokenizer(arguments, delimiter);

            while(e.hasMoreTokens()) {
                String token = e.nextToken();
                returnValues.add(token);
            }

            return returnValues;
        } catch (Exception var5) {
            throw new FunctionException("Invalid values in string.", var5);
        }
    }

    public static ArrayList getOneStringAndOneInteger(String arguments, char delimiter) throws FunctionException {
        ArrayList returnValues = new ArrayList();

        try {
            ArgumentTokenizer e = new ArgumentTokenizer(arguments, delimiter);

            for(int tokenCtr = 0; e.hasMoreTokens(); ++tokenCtr) {
                String token;
                if(tokenCtr == 0) {
                    token = e.nextToken();
                    returnValues.add(token);
                } else {
                    if(tokenCtr != 1) {
                        throw new FunctionException("Invalid values in string.");
                    }

                    token = e.nextToken().trim();
                    returnValues.add(new Integer((new Double(token)).intValue()));
                }
            }

            return returnValues;
        } catch (Exception var6) {
            throw new FunctionException("Invalid values in string.", var6);
        }
    }

    public static ArrayList getTwoStringsAndOneInteger(String arguments, char delimiter) throws FunctionException {
        ArrayList returnValues = new ArrayList();

        try {
            ArgumentTokenizer e = new ArgumentTokenizer(arguments, delimiter);

            for(int tokenCtr = 0; e.hasMoreTokens(); ++tokenCtr) {
                String token;
                if(tokenCtr != 0 && tokenCtr != 1) {
                    if(tokenCtr != 2) {
                        throw new FunctionException("Invalid values in string.");
                    }

                    token = e.nextToken().trim();
                    returnValues.add(new Integer((new Double(token)).intValue()));
                } else {
                    token = e.nextToken();
                    returnValues.add(token);
                }
            }

            return returnValues;
        } catch (Exception var6) {
            throw new FunctionException("Invalid values in string.", var6);
        }
    }

    public static ArrayList getOneStringAndTwoIntegers(String arguments, char delimiter) throws FunctionException {
        ArrayList returnValues = new ArrayList();

        try {
            ArgumentTokenizer e = new ArgumentTokenizer(arguments, delimiter);

            for(int tokenCtr = 0; e.hasMoreTokens(); ++tokenCtr) {
                String token;
                if(tokenCtr == 0) {
                    token = e.nextToken().trim();
                    returnValues.add(token);
                } else {
                    if(tokenCtr != 1 && tokenCtr != 2) {
                        throw new FunctionException("Invalid values in string.");
                    }

                    token = e.nextToken().trim();
                    returnValues.add(new Integer((new Double(token)).intValue()));
                }
            }

            return returnValues;
        } catch (Exception var6) {
            throw new FunctionException("Invalid values in string.", var6);
        }
    }
}
