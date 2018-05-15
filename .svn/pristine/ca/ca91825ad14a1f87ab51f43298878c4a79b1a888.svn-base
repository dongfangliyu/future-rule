<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/dataMsg.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/dataMange/dblist_white.js${version}"></script>
    <title>白名单管理</title>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <div class="Manager_style add_user_info search_style">
                <div id="Add_mainSea_style" style="display: none">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">
                                <li style="text-align: left;">
                                    <label>当前选项是:</label>
                                    <div style="display:inline-block;" id="showCode"></div>/
                                    <div style="display:inline-block;" id="showName"></div>
                                </li>
                                <li>
                                    <div>
                                        <input type="text" placeholder="搜索" class="whiteSea">
                                        <img src="../../resources/images/search.png" alt="" class="search_btn">
                                    </div>
                                </li>
                                <li class="choose">选择</li>
                            </ul>
                        </div>
                    </div>
                    <div class="layui-layer-btn">
                        <a class="layui-layer-btn0" onclick="save()">保存</a>
                        <a class="layui-layer-btn1">取消</a>
                    </div>
                </div>
            </div>
        </form>
        <%--输入参数初始化--%>
        <div class="Manager_style">
            <div class="Data_list">
                <table style="cursor:pointer;" id="Param_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
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
<script>

</script>
</html>