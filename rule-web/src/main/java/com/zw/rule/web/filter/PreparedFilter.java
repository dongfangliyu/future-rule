package com.zw.rule.web.filter;

import com.google.common.base.Strings;
import com.zw.rule.web.util.ServletUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PreparedFilter implements Filter {

    private String contextPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contextPath = filterConfig.getServletContext().getContextPath();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;
        if (!Strings.isNullOrEmpty(contextPath)) {
            req.setAttribute("ctx", contextPath);
        }
        String requestUrl = ServletUtil.getRequestUrl(httpReq);
        if (!ServletUtil.endsWithAny(requestUrl)) {
            ServletUtil.setRequest(httpReq);
            ServletUtil.setResponse(httpResp);
            try {
                chain.doFilter(req, resp);
            } finally {
                ServletUtil.clearServletContext();
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
    }
}
