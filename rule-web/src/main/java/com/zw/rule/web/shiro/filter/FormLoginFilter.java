package com.zw.rule.web.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.zw.rule.core.Response;
import com.zw.rule.core.ResponseCode;
import com.zw.rule.web.util.ServletUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FormLoginFilter extends AuthorizationFilter {

    private String unauthorizedUrl = "/login";

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        return subject.getPrincipal() != null;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws IOException {

        Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
            // if request ajax
            if (ServletUtil.isAjax(WebUtils.toHttp(request))) {
                try (PrintWriter out = response.getWriter()) {
                    Response response1 = new Response();
                    response1.setCode(ResponseCode.NEED_LOGIN);
                    response1.setMsg("会话失效, 请重新登录!");
                    out.write(JSON.toJSONString(response1));
                    out.flush();
                }
            } else {
                ((HttpServletRequest) request).getSession().setAttribute("TIME_OUT_MSG", "会话失效, 请重新登录!");
                //request.setAttribute("TIME_OUT_MSG", "会话失效, 请重新登录!");
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            // If subject is known but not authorized, redirect to the unauthorized URL if there is one
            // If no unauthorized URL is specified, just return an unauthorized HTTP status code
            String unauthorizedUrl = getUnauthorizedUrl();
            //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }
}
