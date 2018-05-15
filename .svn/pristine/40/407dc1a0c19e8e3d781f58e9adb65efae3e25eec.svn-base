package com.zw.rule.web.util;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.zw.base.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Servlet工具类
 *
 * @author liew
 */
public class ServletUtil {

    private static Logger logger = LoggerFactory.getLogger(ServletUtil.class);

    private static Set<String> suffixSet = Sets.newHashSet("js", ".css", ".html", ".jpg", ".png", ".gif", ".jpeg");

    public static boolean endsWithAny(String string) {
        if (Strings.isNullOrEmpty(string)) {
            return false;
        }
        for (String str : suffixSet) {
            if (string.endsWith(str)) {
                return true;
            }
        }
        return false;
    }

    public static String getRequestUrl(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String queryString = req.getQueryString();
        queryString = StringUtils.isEmpty(queryString) ? "" : "?" + queryString;
        return req.getRequestURI() + queryString;
    }

    /**
     * Constant for the HTTP request object.
     */
    private static final String HTTP_REQUEST = "org.framework.web.ServletConstants.HttpServletRequest";

    /**
     * Constant for the HTTP response object.
     */
    private static final String HTTP_RESPONSE = "org.framework.web.ServletConstants.HttpServletResponse";

    /**
     * Constant for the HTTP session object.
     */
    private static final String HTTP_SESSION = "org.framework.web.ServletConstants.HttpSession";

    /**
     * Constant for the HTTP request ip address.
     */
    private static final String HTTP_REMOTE_ADDR = "org.framework.web.ServletConstants.RemoteAddr";


    private static ThreadLocal<Map> servletContext = ThreadLocal.withInitial(() -> new HashMap());

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) servletContext.get().get(HTTP_REQUEST);
    }


    public static void setRequest(HttpServletRequest request) {
        servletContext.get().put(HTTP_REQUEST, request);
    }


    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) servletContext.get().get(HTTP_RESPONSE);
    }


    public static void setResponse(HttpServletResponse response) {
        servletContext.get().put(HTTP_RESPONSE, response);
    }

    public static void setSession(HttpSession session) {
        servletContext.get().put(HTTP_SESSION, session);
    }


    public static HttpSession getSession(boolean create) {
        HttpServletRequest request = getRequest();
        HttpSession session = request != null ? request.getSession(create) : null;
        if ((session != null)) {
            servletContext.get().put(HTTP_SESSION, session);
            return session;
        }
        return null;
    }

    /**
     * 获得客户端端的session id
     */
    public static String getSessionId() {
        HttpSession httpSession = getSession(false);
        return (httpSession != null ? httpSession.getId() : "null");
    }

    /**
     * 获得客户端端的真实IP
     */
    @SuppressWarnings({"rawtypes"})
    public static String getRemoteAddr() {
        String ip = (String) servletContext.get().get(HTTP_REMOTE_ADDR);
        if (ip != null) {
            return ip;
        }
        return null;

    }

    public static void clearServletContext() {
        servletContext.get().clear();
    }


    /**
     * 获取IP地址
     */
    public static String getIpAddr() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "127.0.0.1";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Cdn-Src-Ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取http端口
     */
    public static int getHttpPort(HttpServletRequest req) {
        try {
            return new URL(req.getRequestURL().toString()).getPort();
        } catch (MalformedURLException excp) {
            return 80;
        }
    }

    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    public static void write(HttpServletResponse response, Map<String, Object> retMap) {
        PrintWriter pw = null;
        try {
            response.setContentType("text/html;charset=UTF-8");
            pw = response.getWriter();
            pw.write(JsonUtil.parseObject2Str(retMap));
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            if (null != pw) {
                pw.flush();
                pw.close();
            }
        }
    }

}
