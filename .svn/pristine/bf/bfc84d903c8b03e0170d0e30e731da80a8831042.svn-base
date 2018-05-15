package com.zw.rule.web.util;


import com.zw.base.util.RegexUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;


/**
 * Cookie工具类
 *
 * @author Liew Apr 8, 2016
 */
public class CookieUtil {

    public static void addCookie(String name, String value, int maxValue, PageContext pageContext, boolean subDomain) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        addCookie(name, value, maxValue, request, response, subDomain);
    }

    public static void addCookie(String name, String value, HttpServletRequest request, HttpServletResponse response, boolean subDomain) {
        addCookie(name, value, -1, request, response, subDomain);
    }

    public static void addCookie(String name, String value, PageContext pageContext, boolean subDomain) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        addCookie(name, value, -1, request, response, subDomain);
    }

    public static void addCookie(String name, String value, int maxValue, HttpServletRequest request, HttpServletResponse response, boolean subDomain) {
        if (response == null) {
            return;
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxValue);
        if (subDomain) {
            String serverName = request.getServerName();
            String domain = getDomainOfServerName(serverName);
            if (domain != null && domain.indexOf('.') != -1) {
                cookie.setDomain('.' + domain);
            }
        }
        response.addCookie(cookie);
    }

    public static void removeCookie(String name, PageContext pageContext, boolean subDomain) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        removeCookie(name, request, response, subDomain);
    }

    public static void removeCookie(String name, HttpServletRequest request, HttpServletResponse response, boolean subDomain) {
        addCookie(name, "", 0, request, response, subDomain);
    }

    public static String getCookieByName(String name, PageContext pageContext) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        String cookieValue = getCookieByName(name, request, response);
        return cookieValue;
    }

    public static String getCookieByName(String name, HttpServletRequest request, HttpServletResponse response) {
        if (request == null) {
            return "";
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Cookie cookie = null;
        String cookieName = null;
        String cookieValue = null;
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            cookieName = cookie.getName();
            if (name.equals(cookieName)) {
                cookieValue = cookie.getValue();
                break;
            }
        }
        return cookieValue;
    }

    public static boolean isExist(String name, PageContext pageContext) {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        return isExist(name, request);
    }

    public static boolean isExist(String name, HttpServletRequest request) {
        boolean isExist = false;
        if (request == null) {
            return isExist;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return isExist;
        }
        Cookie cookie = null;
        String cookieName = null;
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            cookieName = cookie.getName();
            if (name.equals(cookieName)) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    /**
     * 获取用户报文的根域名
     */
    private static String getDomainOfServerName(String host) {
        if (RegexUtil.isIp(host))
            return null;
        String[] names = host.split(",");
        int len = names.length;
        if (len == 1)
            return null;
        if (len == 3) {
            return builderString(names[len - 2], names[len - 1]);
        }
        if (len > 3) {
            String dp = names[len - 2];
            if (dp.equalsIgnoreCase("com") || dp.equalsIgnoreCase("gov") || dp.equalsIgnoreCase("net") || dp.equalsIgnoreCase("edu")
                    || dp.equalsIgnoreCase("org"))
                return builderString(names[len - 3], names[len - 2], names[len - 1]);
            else
                return builderString(names[len - 2], names[len - 1]);
        }
        return host;
    }

    private static String builderString(String... ps) {
        StringBuilder s = new StringBuilder();
        for (int idx = 0; idx < ps.length; idx++) {
            if (idx > 0)
                s.append('.');
            s.append(ps[idx]);
        }
        return s.toString();
    }

}