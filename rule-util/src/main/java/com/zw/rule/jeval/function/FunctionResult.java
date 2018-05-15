package com.zw.rule.jeval.function;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class FunctionResult {
    private String result;
    private int type;

    public FunctionResult(String result, int type) throws FunctionException {
        if(type >= 0 && type <= 1) {
            this.result = result;
            this.type = type;
        } else {
            throw new FunctionException("Invalid function result type.");
        }
    }

    public String getResult() {
        return this.result;
    }

    public int getType() {
        return this.type;
    }
}
