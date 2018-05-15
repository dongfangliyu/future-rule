<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/dataMsg.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/dataMange/white_details.js${version}"></script>
    <title>白名单管理</title>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <input type="hidden" class="hide" name="colArray" value="">
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix">
                    <button class="queryBtn" type="button" id="c-add-word" onclick="leadLoad()">导入</button>
                    <button type="button" class="queryBtn" onclick="down(0)">下载</button>
                    <button type="button" class="btn btn-primary addBtn" onclick="addDetail()" style="margin:-1px 0 0 .5em">新增</button>
                    <button type="button" class="btn-newDanger" onclick="deleteDetail()">删除</button>
                    <button type="button" class="queryBtn" onclick="down(1)" id="download">模板下载</button>
                    <button id="btn_search_reset"  type="button" class="queryBtn" style="float:right;top:1px;margin:0 .5em;">查询重置</button>
                    <button id="btn_search"  type="button" class="queryBtn" style="float: right;top:1px;">查询</button>
                    <input type="text" placeholder="搜索" name="searchDetail" id="searchDetail" style="margin-top:2px;"/>
                </ul>
                <div id="Add_detail_style" style="display: none">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">

                            </ul>
                        </div>
                    </div>
                </div>
                <div id="Edit_detail_style" style="display: none">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">

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
                                        <div class="batch-right">支持Excel2010(文件大小不超过10M)</div>
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
                <table style="cursor:pointer;" id="detail_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>
                            <input name="selectAll" id="selectAll" type="checkbox"  class="ace" isChecked="false" />
                            <span class="lbl" style="cursor:pointer;"></span>
                        </th>
                    </tr>
                    </thead>

                    <tbody>
                    </tbody>
                </table>
                <input type="hidden" value="${param.listDbId}" id="hid">
                <input type="hidden" value="${param.listType}" id="listType">
            </div>
        </div>
    </div>
</div>
</body>

</html>
