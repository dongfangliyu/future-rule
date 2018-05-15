package com.zw.rule.https;

/**
 * Created by Administrator on 2017/5/15.
 */
public class HttpsException extends Exception {
    private int statusCode = -1;
    private int errorCode = -1;
    private String request;
    private String error;
    private static final long serialVersionUID = -2623309261327598087L;

    public HttpsException(String msg) {
        super(msg);
    }

    public HttpsException(Exception cause) {
        super(cause);
    }

    public HttpsException(String msg, Exception cause) {
        super(msg, cause);
    }

    public HttpsException(String msg, Exception cause, int statusCode) {
        super(msg, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getRequest() {
        return this.request;
    }

    public String getError() {
        return this.error;
    }
}
