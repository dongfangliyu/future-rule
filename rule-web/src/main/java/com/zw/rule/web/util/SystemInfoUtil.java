package com.zw.rule.web.util;

import com.zw.rule.pojo.SystemInfo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class SystemInfoUtil {

    public static SystemInfo getSystemInfo(){
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr = null;
        String ip = "";
        String hostName = "";
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            ip = "无法获取主机IP";
            hostName = "无法获取主机名";
        }
        if (null != addr) {
            try {
                ip = addr.getHostAddress();
            } catch (Exception e) {
                ip = "无法获取主机IP";
            }
            try {
                hostName = addr.getHostName();
            } catch (Exception e) {
                hostName = "无法获取主机名";
            }
        }
        SystemInfo systemInfo = new SystemInfo();
        systemInfo.setHostIp(ip);
        systemInfo.setArch(props.getProperty("os.arch"));
        systemInfo.setHostName(hostName);
        systemInfo.setJavaHome(props.getProperty("java.home"));
        systemInfo.setJavaUrl(props.getProperty("java.vendor.url"));
        systemInfo.setJavaVersion(props.getProperty("java.version"));
        systemInfo.setOsName(props.getProperty("os.name"));
        systemInfo.setOsVersion(props.getProperty("os.version"));
        systemInfo.setProcessors(String.valueOf(r.availableProcessors()));
        systemInfo.setVendor(props.getProperty("java.vendor"));
        systemInfo.setTmpdir(props.getProperty("java.io.tmpdir"));
        return systemInfo;
    }
}
