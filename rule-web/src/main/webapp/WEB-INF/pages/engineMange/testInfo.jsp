<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/dataMsg.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/engineMange/testInfo.js${version}"></script>
    <title>测试信息</title>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <button id="btn_search_reset" type="button" class="queryBtn" id="btn_search_reset" style="float:right;margin-top:13px;margin-right:3em;">查询重置</button>
            <button id="btn_search" type="button" class="queryBtn" style="float:right;margin-top:13px;margin-right: 5px">查询</button>
            <input type="text" placeholder="引擎名称" name="searchDetail" id="searchTest" style="height: 2.3em"/>
            <div class="Manager_style add_user_info search_style">
                <div id="produce_detail_style" style="display: none">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">
                                <li>
                                    <label class="label_name">用例数量：</label>
                                    <label for="rowCt">
                                        <input name="rowCt"  type="text" id="rowCt"/>
                                    </label>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div id="Lead_detail_style" style="display: none">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">
                                <li>
                                    <input type="text" placeholder="请选择本地文件" readonly id="fileName">
                                </li>
                                <li>
                                    <div class="select-file">
                                        <a class="a-upload ">请选择本地文件
                                            <input id="file" type="file" name="file" onchange="handleFile()">
                                        </a>
                                        <div class="batch-right">支持txt文件导入(文件大小不超过10M)</div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <%--白名单初始化--%>
        <div class="Manager_style">
            <div class="Data_list">
                <table style="cursor:pointer;" id="testInfo_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>序号</th>
                        <th>ID</th>
                        <th>版本号</th>
                        <th>引擎名称</th>
                        <th>描述</th>
                        <th>最后更新时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>
        <input type="hidden" value="${param.engineId}" id="engineId">
    </div>
</div>
</body>
</html>
