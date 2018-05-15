package com.zw.rule.web.aop;

import com.google.common.base.Strings;
import com.zw.base.util.JsonUtil;
import com.zw.rule.core.IdGenerator;
import com.zw.rule.po.WebLog;
import com.zw.rule.service.WebLogService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.ServletUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;


@Aspect
@Component
public class WebLogAop {

    private Logger logger = LoggerFactory.getLogger(WebLog.class);

    @Resource
    private WebLogService webLogService;

    @Pointcut(value = "execution(public * com.zw.rule.web..controller.*.*(..)))")
    private void webLog() {

    }

    @Around("webLog()")
    public Object doWebLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnVal;
        Object target = joinPoint.getTarget();
        String methodName = joinPoint.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method;
        //通过反射获得拦截的method
        method = target.getClass().getMethod(methodName, parameterTypes);
        String id = null;
        try {
            if (method == null) {
                return joinPoint.proceed();
            }
            WebLogger annotation = method.getAnnotation(WebLogger.class);
            //如果方法上没有注解@WebLogger，返回
            if (annotation == null) {
                return joinPoint.proceed();
            }
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String methodToUse = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature()
                    .getName();
            logger.info("URL : " + request.getRequestURL().toString());
            logger.info("HTTP_METHOD : " + request.getMethod());
            logger.info("IP : " + request.getRemoteAddr());
            logger.info(
                    "CLASS_METHOD : " + methodToUse);
            logger.info("ARGS : " + JsonUtil.parseObject2Str(joinPoint.getArgs()));
            id = doWebLog(methodName, annotation.value(), Arrays.toString(joinPoint.getArgs()));
            returnVal = joinPoint.proceed();
            updateWebLog(id, "成功", "操作成功");
            logger.info("RESPONSE DATA : " + JsonUtil.parseObject2Str(returnVal));
        } catch (Exception e) {
            updateWebLog(id, "失败", e.getMessage());
            throw new RuntimeException(e);
        }
        return returnVal;
    }

    private String doWebLog(String method, String methodDesc, Object args) {
        String account = (String) UserContextUtil.getAttribute("account");
        String id = IdGenerator.getInstance().nextId();
        WebLog webLog = new WebLog();
        if (!Strings.isNullOrEmpty(methodDesc)) {
            webLog.setMethodDesc(methodDesc);
        }
        webLog.setLoginAccount(account);
        webLog.setMethodArgs(args.toString());
        webLog.setWebLogId(id);
        webLog.setMethod(method);
        webLog.setOperateTime(new Date());
        webLog.setOperateIp(ServletUtil.getIpAddr());

        webLogService.add(webLog);
        return id;
    }

    private void updateWebLog(String id, String status, String remark) {
        WebLog webLog = new WebLog();
        webLog.setWebLogId(id);
        webLog.setRemark(remark);
        webLog.setStatus(status);
        webLog.setOperateTime(new Date());
        webLogService.update(webLog);
    }

}
