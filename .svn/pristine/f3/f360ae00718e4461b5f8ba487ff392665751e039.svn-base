package com.zw.rule.core;


public interface ResponseCode {


    Integer SUCCESS = 0;


    Integer ERROR = 1;


    Integer INVALID_PARAM = 2;


    Integer EXSIED = 3;

    Integer LOCKED = 4;


    Integer PASSWORD_ERROR = 5;


    int NEED_LOGIN = 6;


    ThreadLocal<Integer> INSUFFICIENT_PRIVILEGES = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 7;
        }
    };


    Integer NOT_FOUND = 404;

}
