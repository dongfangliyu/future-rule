package com.zw.rule.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.zw.rule.core.Response;
import com.zw.rule.core.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class AppExceptionHandler implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object object, Exception exception) {
        logger.error("控制器层出现异常", exception);
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            return new ModelAndView("common/error");
        } else {// JSON格式返回
            try (PrintWriter writer = response.getWriter()) {
                response.setCharacterEncoding("UTF-8");
                // 返回客户端系统异常
                Response response1 = new Response();
                response1.setCode(ResponseCode.ERROR);
                String errorMsg = exception.getMessage();
                if(errorMsg.indexOf(":")>-1){
                    errorMsg = errorMsg.split(":")[1];
                }
                response1.setMsg(errorMsg);
                writer.write(JSON.toJSONString(response1));
                writer.flush();
            } catch (IOException e) {
                logger.error("", e);
            }
            return null;
        }
    }
}
