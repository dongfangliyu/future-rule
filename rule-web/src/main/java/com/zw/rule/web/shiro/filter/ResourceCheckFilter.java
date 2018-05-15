package com.zw.rule.web.shiro.filter;

import com.zw.rule.core.Response;
import com.zw.rule.web.util.ServletUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ResourceCheckFilter extends AccessControlFilter {

    private Logger logger = LoggerFactory.getLogger(ResourceCheckFilter.class);

    private String errorUrl;

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        String url = getPathWithinApplication(request);
        logger.debug("request url is ：" + url);
        //当url不在数据库中登记时，不进行校验
        Set allPermissions = (HashSet)UserContextUtil.getAttribute("allPermissions");
        if(!allPermissions.contains(url)){
            return true;
        }
        return "admin".equals(subject.getPrincipal()) || subject.isPermitted(url)||url.equals("/user/getPermission");
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        HttpServletResponse resp = (HttpServletResponse) response;
        if (ServletUtil.isAjax(WebUtils.toHttp(request))) {
            Map<String, Object> retMap = new HashMap<>();
            retMap.put("status", Response.ERROR);
            retMap.put("msg", "no permission");
            ServletUtil.write(WebUtils.toHttp(response), retMap);
        } else {
            HttpServletRequest req = (HttpServletRequest) request;
            String url = req.getContextPath() + this.errorUrl;
            resp.sendRedirect(url);
        }
        return false;
    }

}
