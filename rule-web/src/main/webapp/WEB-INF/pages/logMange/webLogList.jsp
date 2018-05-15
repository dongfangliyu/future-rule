<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
    <script src="${ctx}/resources/js/logMange/WebLog_Ctrl.js${version}"></script>
    <title>操作日志</title>
</head>
<body>
<div id="container"></div>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <div class="Manager_style add_info search_style">
                <ul class="search_content clearfix">
                    <li>
                        <label class="lf">帐号<input name="account"  type="text" class="text_add" id="operateAccount"/></label>
                    </li>
                    <li style="width:250px;">
                        <label class="lf">操作日期<input type="text" readonly class="date eg-date" id="operateTime" onclick="laydate()"/><span class="date-icon"><i class="icon-calendar"></i></span></label>
                    </li>
                    <button id="btn_search"  type="button" class="queryBtn">查询</button>
                    <button id="btn_search_reset"  type="button" class="queryBtn">查询重置</button>
                </ul>
            </div>
        </form>
        <div class="commonManager">
            <div class="webLog_list">
                <table id="webLog_list" cellpadding="0" cellspacing="0"
                       class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>帐号</th>
                        <th>方法名</th>
                        <th>参数</th>
                        <th>方法描述</th>
                        <th>操作日期</th>
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