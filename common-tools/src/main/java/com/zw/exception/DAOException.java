package com.zw.exception;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月21日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) zw.<br>
 * </strong>
 * <p>
 *
 * @author department:技术开发部 <br>
 *         username:zh-pc <br>
 *         email: <br>
 * @version <strong>zw有限公司-运营平台</strong><br>
 *          <br>
 *          <strong>修改历史:</strong><br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 *          <br>
 *          <br>
 */
public class DAOException extends RuntimeException {

    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(Throwable throwable) {
        super(throwable);
    }

    public DAOException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
