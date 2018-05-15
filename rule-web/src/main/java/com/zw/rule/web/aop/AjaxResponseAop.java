package com.zw.rule.web.aop;

import com.zw.rule.core.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <strong>Title : <br>
 * </strong> <strong>Description : </strong>@类注释说明写在此处@<br>
 * <strong>Create on : 2017年05月02日<br>
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
@Aspect
@Component
public class AjaxResponseAop {
    private Logger logger = LoggerFactory.getLogger(AjaxResponseAop.class);

    @Around(value = "@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object object = pjp.proceed();
        if(object!=null) {
            if (!(object instanceof Response)) {
                Response response = new Response();
                response.setData(object);
                return response;
            }
        }
        return object;
    }
}
