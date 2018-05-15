package com.zw.rule.web.util;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public abstract class UserContextUtil {

    public static Session getSession(){
        Subject currentSubjetct = SecurityUtils.getSubject();
        return currentSubjetct.getSession();
    }

    public static long getUserId(){
        return (long) getAttribute("userId");
    }
    public static long getOrganId(){
        return (long) getAttribute("organId");
    }
    public static String getNickName(){
        return (String) getAttribute("nickName");
    }
    public static String getAccount(){
        return (String) getAttribute("account");
    }
    public static String getCurrentRoleId(){
        return (String) getAttribute("roleId");
    }

    public static Object getAttribute(String key){
        return getSession().getAttribute(key);
    }

    public static void setAttribute(String key,Object value){
         getSession().setAttribute(key,value);
    }
}
