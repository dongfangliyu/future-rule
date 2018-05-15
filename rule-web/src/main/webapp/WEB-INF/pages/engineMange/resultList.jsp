<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <%@include file ="../common/taglibs.jsp"%>
        <%@ include file="../common/importDate.jsp"%>
      <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
      <link rel="stylesheet" href="${ctx}/resources/css/myCss.css${version}"/>
      <link rel="stylesheet" href="${ctx}/resources/css/daterangepicker-bs3.css${version}"/>
      <link rel="stylesheet" href="${ctx}/resources/css/dataMsg.css${version}"/>
      <script src="${ctx}/resources/js/lib/laydate/moment.js${version}"></script>
      <script src="${ctx}/resources/js/lib/laydate/daterangepicker.js${version}"></script>
      <script src="${ctx}/resources/js/engineMange/resultList.js${version}"></script>
      <script src="${ctx}/resources/js/lib/laydate/laydate.js${version}"></script>
      <script src="${ctx}/resources/js/lib/Echarts/echarts.min.js${version}"></script>
      <%--<link rel="stylesheet" href="${ctx}/resources/css/jquery.jqplot.min.css${version}">--%>
      <%--<!--[if lt IE 9]>--%>
       <%--<script src="${ctx}/resources/js/lib/jqplot/excanvas.min.js${version}"></script>--%>
       <%--<![endif]-->--%>
      <%--<script src="${ctx}/resources/js/lib/jqplot/jquery.jqplot.min.js${version}"></script>--%>
      <%--<script src="${ctx}/resources/js/lib/jqplot/jqplot.donutRenderer.min.js${version}"></script>--%>
      <%--<script src="${ctx}/resources/js/lib/jqplot/jqplot.pieRenderer.min.js${version}"></script>--%>
      <%--<script src="${ctx}/resources/js/lib/jqplot/jqplot.categoryAxisRenderer.min.js${version}"></script>--%>
      <title>结果集</title>
</head>
<body style="clear:both;height:664px;">
    <div class="out-content">
        <input type="hidden" value="resultList.jsp" id="leftjsp_id">
        <div class="c-result-matter" id="showTab" style="display: none;">
            <div class="c-left-conent">
                <div class="c-left-list-head" id="list_head">
                    <div class="c-resultSet">结果集</div>
                </div>
                <div class="c-left-list">
                    <ul id="otherInfoTab">
                        <li>
                            <img src="../resources/images/folder.png">
                            <a href="###" style="color: #1a67bd" id="interfaceDebug">API调用</a>
                        </li>
                        <li>
                            <img src="../resources/images/folder.png">
                            <a href="#batch_test" data-toggle="tab" id="batchTest" style="color: #1a1a1a" >批量测试</a>
                        </li>
                        <li>
                            <img src="../resources/images/folder.png">
                            <a href="" data-toggle="tab" class="c-add-list" id="dataWrite" style="color: #1a1a1a" >数据填写</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="tab-content" style="width: 100%" id="listWidth">
                <div id="engine_list">
                    <button id="btn_search_reset" type="button" class="queryBtn" id="btn_search_reset" style="float:right;margin-top:13px;margin-right:3em;">查询重置</button>
                    <button id="btn_search" type="button" class="queryBtn" style="float:right;margin-top:13px;margin-right: 5px;">查询</button>
                    <input type="text" placeholder="引擎名称" name="searchDetail" id="searchTest" style="height:2.3em"/>
                    <div class="Manager_style">
                        <div class="Data_list">
                            <table style="cursor:pointer;" id="testInfo_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>ID</th>
                                            <th>版本号</th>
                                            <th>引擎名称</th>
                                            <th>描述</th>
                                            <th>最后更新时间</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div id="result_list" style="display: none">
                    <div style="float:right;margin-top:20px;">
                    <label class="lf">开始日期<input readonly="true" class="eg-date" id="startTime" type="text"onclick=" laydate()"/><span class="date-icon"><i class="icon-calendar"></i></span></label>
                    <label class="lf">结束日期<input readonly="true" class="eg-date" id="endTime" type="text"onclick=" laydate()"/><span class="date-icon"><i class="icon-calendar"></i></span></label>
                    <label class="lf"><input type="text" placeholder="Pid" name="searchPid" id="searchPid"/></label>
                    <label class="lf"><button id="btn_search1" type="button" class="btn btn-primary queryBtn"  style="margin: 0 1em;top: -1px;" onclick="selectResult()">查询</button></label><label class="lf"  ><button id="btn_search2" type="button" class="btn btn-primary queryBtn" style="margin: 0 1em;top: -1px;"  onclick="resultReturn()">返回</button></label>
                    <br/>
                    <br/>
                    <br/>
                </div>
                    <div class="Manager_resulList" id="batchList">
                    <div class="resulList_list">
                        <table style="cursor:pointer;" id="batchTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th></th>
                                <th>id</th>
                                <th>pid</th>
                                <th>结果</th>
                                <th>执行时间</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
                    <div class="Manager_resulList" id="checkList" style="width: 100%;">
                        <div class="resulList_list">
                            <span>&nbsp;&nbsp;返回</span>
                            <table style="cursor:pointer;" id="checkTable" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>ID</th>
                                        <th>引擎名称</th>
                                        <th>决策建议</th>
                                        <th>信用评分</th>
                                        <th>查看</th>
                                        <th>导出</th>
                                    </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
        </div>
    </div>
    <input type="hidden" value="1" id="dataType">
    <input type="hidden" value="${param.engineId}" id="engineId">
    <input type="hidden" value="" id="verId">
    <div id="chart" style="width:500px;height:300px;display: none;"></div>
</body>
</html>