package com.zw.base.util;

import com.zw.dto.UserInfoDTO;
import org.apache.log4j.Logger;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年02月17日<br>
 * </strong>
 * <p>
 * <strong>Copyright (C) Vbill Co.,Ltd.<br>
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
public class TraceLoggerUtil {
    private static Logger logger = Logger.getLogger("trace");

    private static String getTrace() {
        String msg = "";
        try {
            UserInfoDTO userInfoDTO = (UserInfoDTO) ThreadLocalHelper.getMap().get(ThreadLocalHelper.USER_INFO_DTO);
            if (userInfoDTO != null) {
                msg += "[" + userInfoDTO.getHttpReqId() + "]";
                msg += " [" + userInfoDTO.getUserId() + "]\r\n";
            } else {
                msg += "[null]";
                msg += " [null]\r\n";
            }
        } catch (Exception ex) {
            logger.warn("获取用户上下文信息出现异常！", ex);
        }
        return msg;
    }

    public static void debug(String message) {
        logger.debug(getTrace() + message);
    }

    public static void debug(String message, Throwable t) {
        logger.debug(getTrace() + message, t);
    }

    public static void info(String message) {
        logger.info(getTrace() + message);
    }

    public static void info(String message, Throwable t) {
        logger.info(getTrace() + message, t);
    }

    public static void error(String message) {
        logger.error(getTrace() + message);
    }

    public static void error(String message, Throwable t) {
        logger.error(getTrace() + message, t);
    }
}
