<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/dataMsg.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/dataMange/dblist_black.js${version}"></script>
    <title>黑名单管理</title>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix">
                    <button class="btn btn-primary addBtn" type="button" id="c-add-word" onclick="addORdetail(1)">新增黑名单</button>
                </ul>
                <div id="Add_data_style" style="display: none">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">
                                <li>
                                    <label class="label_name">黑名单名称</label>
                                    <label for="listName">
                                        <input name="listName"  type="text"  id="listName"/>
                                    </label>
                                </li>
                                <li>
                                    <label class="label_name">数据来源</label>
                                    <label>
                                        <select name="dataSource"  id="dataSource">
                                            <option value="0">待选</option>
                                            <option value="1">外部</option>
                                            <option value="2">内部</option>
                                        </select>
                                    </label>
                                </li>
                                <li id="isEdit">
                                    <label class="label_name">名单类型</label>
                                    <label for="listAttr">
                                        <input name="listAttr"  type="text" id="listAttr"/>
                                    </label>
                                </li>
                                <li>
                                    <label class="label_name">黑名单描述</label>
                                    <label for="listDesc">
                                        <input name="listDesc"  type="text" id="listDesc"/>
                                    </label>
                                </li>
                                <li id="accuLi">
                                    <label style="float:left;">
                                        <input name="matchType"  type="radio" value="1" class="text_add"  id="precise"/>精准匹配&#12288;&#12288;
                                    </label>
                                    <label style="margin-left:-4em;">
                                        <input name="matchType"  type="radio" value="0" class="text_add"  id="dim"/>模糊匹配
                                    </label>
                                </li>
                                <li class="maintence">
                                    <label class="label_name">维护字段</label>&#12288;&#12288;
                                    <label>
                                        <input name="queryType"  type="radio" value="0" class="text_add"  id="maybe"/>or
                                    </label>
                                    <label>
                                        <input name="queryType"  type="radio" value="1" class="text_add"  id="draw"/>and
                                    </label>
                                </li>
                                <li id="maintenIncre">
                                    <div id="callout">
                                        <span class="blackadd" onclick="blackAdd()"></span>
                                    </div>
                                </li>
                                <li class="maintence mainSea">查询主键</li>
                                <li id="searchIncre">

                                </li>
                            </ul>
                        </div>
                    </div>
                    <input type="hidden" value="${param.id}" id="hid">
                    <input type="hidden" value="" id="queryFilde">
                </div>
                <div id="Add_mainSea_style" style="display: none">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">
                                <li style="text-align: left;width:58%;">
                                    <label>当前选项是:</label>
                                    <div style="display:inline-block;" id="showCode"></div>/
                                    <div style="display:inline-block;" id="showName"></div>
                                </li>
                                <li style="width:38%;">
                                    <div>
                                        <input type="text" placeholder="字段名称" class="whiteSea" name="searchKey" style="width:85%;">
                                        <img src="../../resources/images/search.png" alt="" class="search_btn">
                                    </div>
                                </li>
                                <li class="choose">选择</li>
                            </ul>
                        </div>
                        <%-- 输入参数初始化--%>
                        <div class="Manager_style">
                            <div class="Data_list">
                                <table style="cursor:pointer;" id="Param_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="Add_affliSea_style" style="display: none">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">
                                <li style="text-align: left;width:97%;">
                                    <label>当前选项是:</label>
                                    <div style="display:inline-block;" id="chooseCode"></div>/
                                    <div style="display:inline-block;" id="chooseVal"></div>
                                </li>
                                <li class="choose">选择</li>
                                <li id="affliValue"></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </form>
        <%--黑名单初始化--%>
        <div class="Manager_style">
            <div class="Data_list">
                <table style="cursor:pointer;" id="Data_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>名称</th>
                        <th>查询主键</th>
                        <th></th>
                        <th>描述</th>
                        <th>数据来源</th>
                        <th>状态</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
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