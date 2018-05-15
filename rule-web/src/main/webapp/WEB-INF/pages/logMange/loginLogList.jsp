<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>登录日志</title>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/logMange/loginLog_List.js${version}"></script>
</head>
<body>
<div class="page-content">
    <div class="log_Manager_style">
        <div class="Manager_style search_style">
            <ul class="search_content clearfix" >
                <li>
                    <label class="lf">登录帐号<input id="loginAccount" type="text" class="text_add"/></label>
                </li>
                <li style="width:250px;">
                    <label class="lf">登录日期<input readonly="true" class="eg-date" id="loginTime" type="text"onclick=" laydate()"/><span class="date-icon"><i class="icon-calendar"></i></span></label>
                </li>
                <button id="btn_search" type="button" class="queryBtn">查询</button>
                <button id="btn_search_reset" type="button" class="queryBtn">查询重置</button>
            </ul>
        </div>
        <div class="Manager_style">
            <div class="Log_list">
                <table id="Log_list" cellpadding="0" cellspacing="0"
                       class="table table-striped table-bordered table-hover" style="font-size: 13px;">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>登录帐号</th>
                        <th>登录日期</th>
                        <th>状态</th>
                        <th>登录IP</th>
                        <th>备注</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>