<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/lookOver.css${version}"/>
    <script src="${ctx}/resources/js/engineMange/lookOver.js${version}"></script>
    <title>结果集</title>
    <style>
        .emptylistThree li{
            height: 21px;
        }
    </style>
</head>
<body>
<input type="hidden"  id="leftjsp_id">
<div class="out-content" style="width:1038px; margin:0 auto;">
    <div class="c-result-matter">
        <div class="c-batchTest-content">
            <div class="c-batchTest-head">
                <div class="c-batchTest-headline">
                    <span class="c-batchTest-results">引擎执行报告</span>
                </div>
            </div>
            <div class="c-batchTest-body">
                <div class="c-batchTest-line">
                    <div class="c-batchTest-user">
                        <label>引擎ID：</label>
                        <span id="engineId"></span>
                    </div>
                    <div class="c-batchTest-name">
                        <label>引擎名称：</label>
                        <span id="engineName"></span>
                    </div>
                    <div class="c-batchTest-identifying">
                        <label>引擎标识：</label>
                        <span id="engineCode"></span>
                    </div>
                </div>
                <div class="c-batchTest-data">
                    <div class="c-batchTest-metre">
                        <label>决策建议：</label>
                        <span id="result"></span>
                    </div>
                    <div class="c-batchTest-metre">
                        <label>信用评分：</label>
                        <span id="scorecardscore"></span>分
                    </div>

                </div>
                <div class="c-batchTest-black">
                    <div class="c-blacklist">
                        <span>黑名单</span>
                    </div>
                    <div class="c-show-list" id="blackList">
                        <div class="c-every-last">名单类型</div>
                        <div class="c-every-last">名单名称</div>
                        <div class="c-every-lastTwo">名单描述</div>
                    </div>
                    <div class="emptylistOne">
                        <ul>

                        </ul>
                    </div>
                </div>
                <div class="c-batchTest-black">
                    <div class="c-blacklist">
                        <span>白名单</span>
                    </div>
                    <div class="c-show-list" id="whiteList">
                        <div class="c-every-last">名单类型</div>
                        <div class="c-every-last">名单名称</div>
                        <div class="c-every-lastTwo">名单描述</div>
                    </div>
                    <div class="emptylistTwo">
                        <ul>

                        </ul>
                    </div>
                </div>
                <div class="c-batchTest-black">
                    <div class="c-blacklist">
                        <span>硬性拒绝规则</span>
                    </div>
                    <div class="c-show-list" id="refuseRule">
                        <div class="c-every-last">规则ID</div>
                        <div class="c-every-last">规则名称</div>
                        <div class="c-every-last">命中原因</div>
                        <div class="c-every-last">规则编码</div>
                    </div>
                    <div class="emptylistThree">
                        <ul></ul>
                    </div>
                </div>
                <div class="c-batchTest-black">
                    <div class="c-blacklist">
                        <span>加减分规则</span>
                    </div>
                    <div class="c-show-list" id="pmRule">
                        <div class="c-every-last">规则ID</div>
                        <div class="c-every-last">规则名称</div>
                        <div class="c-every-last">命中原因</div>
                        <div class="c-every-last">规则编码</div>
                    </div>
                    <div class="emptylistFour">
                        <ul></ul>
                    </div>
                </div>
                <div class="c-batchTest-black">
                    <div class="c-blacklist">
                        <span>拒绝规则列表</span>
                    </div>
                    <div class="c-show-list" id="refuseRuleList">
                        <div class="c-every-last">规则ID</div>
                        <div class="c-every-last">规则名称</div>
                        <div class="c-every-last">规则描述</div>
                        <div class="c-every-last">规则参数</div>
                    </div>
                    <div class="emptylistSixe">
                        <ul></ul>
                    </div>
                </div>
                <div class="c-batchTest-black">
                    <div class="c-blacklist">
                        <span>加减分规则列表</span>
                    </div>
                    <div class="c-show-list" id="pmRuleList">
                        <div class="c-every-last">规则ID</div>
                        <div class="c-every-last">规则名称</div>
                        <div class="c-every-last">规则描述</div>
                        <div class="c-every-last">规则参数</div>
                    </div>
                    <div class="emptylistFive">
                        <ul></ul>
                    </div>
                </div>

                <hr/>
                <%--<div class="c-batchTest-black">--%>
                <%--<div class="c-blacklist">--%>
                <%--<span>自定义名单</span>--%>
                <%--</div>--%>
                <%--<div class="c-show-list" id="customDb">--%>
                <%--<div class="c-every-last" style="width: 100%;">姓名</div>--%>
                <%--</div>--%>
                <%--<div class="emptylistFive">--%>
                <%--<ul>--%>

                <%--</ul>--%>
                <%--</div>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>
</div>
<input type="hidden" value="${param.resultSetId}" id="resultSetId">
</body>
</html>