<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="homeApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="${ctx}/resources/assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace.min.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="${ctx}/resources/css/home.css"/>
<title></title>
<style type="text/css">
    .index_style  li{
        width: 32% !important;
    }
</style>
</head>
<body>
<div class="page-content"  ng-controller="homeCtrl">
    <div class="row">
        <div class="col-xs-12">
            <div class="index_style">
                <div class="frame xjdd_style">
                    <span class="title_name">用户信息</span>
                    <div class="content">
                        <ul class="xj_list clearfix">
                            <li>
                                <label class="label_name">用户名</label>
                                <div class="xinxi">
                                   <b>${account}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">角色</label>
                                <div class="xinxi">
                                   <b>${role}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">最后登录IP</label>
                                <div class="xinxi">
                                    <b>${lastLoginIp}</b>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--结束-->
                <div class="frame ddgl_style">
                    <span class="title_name">系统信息</span>
                    <div class="content">
                        <ul class="xj_list clearfix">
                            <li>
                                <label class="label_name">IP地址</label>
                                <div class="xinxi">
                                    <b>${systemInfo.hostIp}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">主机名</label>
                                <div class="xinxi">
                                    <b>${systemInfo.hostName}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">操作系统</label>
                                <div class="xinxi">
                                    <b>${systemInfo.osName}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">系统架构</label>
                                <div class="xinxi">
                                    <b>${systemInfo.arch}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">系统版本</label>
                                <div class="xinxi">
                                   <b>${systemInfo.osVersion}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">处理器个数</label>
                                <div class="xinxi">
                                    <b>${systemInfo.processors}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">Java版本</label>
                                <div class="xinxi">
                                    <b>${systemInfo.javaVersion}</b>
                                </div>
                            </li>
                            <li>
                                <label class="label_name">Java路径</label>
                                <div class="xinxi">
                                    <b>${systemInfo.javaHome}</b>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!--结束-->
            </div>
            <!-- 首页样式结束 -->
        </div>
    </div>
</div>
</body>
</html>
