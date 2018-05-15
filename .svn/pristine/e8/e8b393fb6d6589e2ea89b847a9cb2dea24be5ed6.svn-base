package com.zw.rule.core;


public abstract class MessageUtils implements ResponseCode {
    public static boolean isSuccess(Response response) {
        return response != null ? response.getCode() == SUCCESS : false;
    }

    /**
     * 设置 SUCCESS 到 response
     */
    public static Response success(Response response) {
        response.setCode(SUCCESS);
        return response;
    }

    /**
     * 设置 INVALID_REQUEST 到 response
     */
    public static Response systemError(Response response) {
        response.setCode(ERROR);
        response.setMsg("系统发生错误,请联系管理员!");
        return response;
    }


    /**
     * 设置 NEED_LOGIN 到 response
     */
    public static Response needLogin(Response response) {
        response.setCode(NEED_LOGIN);
        return response;
    }

    /**
     * 设置 INSUFFICIENT_PRIVILEGES 到 response
     */
    public static Response insufficientPrivileges(Response response) {
        response.setCode(INSUFFICIENT_PRIVILEGES.get());
        return response;
    }

}
