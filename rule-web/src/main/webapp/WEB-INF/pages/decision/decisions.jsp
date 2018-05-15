<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>引擎管理主页</title>
    <%--<%@include file ="../common/taglibs.jsp"%>--%>
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace.min.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace-rtl.min.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/css/ace-skins.min.css${version}"/>
    <%--<link rel="stylesheet" href="${ctx}/resources/css/dataTables.bootstrap.css${version}">--%>
    <%--<link rel="stylesheet" href="${ctx}/resources/css/dataTables.fontAwesome.css${version}">--%>
    <%--<link rel="stylesheet" href="${ctx}/resources/css/jquery.dataTables.min.css${version}">--%>
    <style>
        body{
            margin:0;
            padding:0;
        }
        .main-content{
            margin-left: 50px;
        }
        .c-sanbox-lie{
            margin:1em 0;
        }
        .c-enter-proportion{
            float:left;
            margin-left: 1.5em;
        }
        .c-sanbox-img{
            cursor: pointer;
            color:#398DEE;
            float: right;
            margin-right: 1.5em;
        }
        .icon-delData{
            cursor: pointer;
            display: inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/delete.png") no-repeat center center;
        }
        .c-sanbox-delete{
            float: right;
            margin-right: 2em;
            cursor: pointer;
        }
        .c-swarm-interior .c-swarm-interior-left .c-swarm-name{
            float: left;
        }
        .c-title{
            margin-left:1.5em
        }
        .c-swarm-interior .c-positon-img{
            position: absolute;
            top:30px;
            right: 0;
            margin-right: 1em;
            cursor: pointer;
        }
        .c-contains-outside{
            margin:5px auto;
        }
        .c-contained-within{
            overflow: hidden;
            clear: both;
            margin-left: 1.5em;
        }
        .c-swarm-dialog input{
            width:80px;
        }
        .c-contained-within div[class^="c-select-"]{
            float:left;
        }
        .c-swarm-interior-left{
            position: relative;
        }
        .c-swarm-interior{
            position: relative;
            line-height: 30px;
        }
        span.addCondition{
            display:inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/add.png") no-repeat center center;
        }
        span.delCondition{
            display:inline-block;
            width:19px;
            height:19px;
            background: url("${ctx}/resources/images/rules/delete.png") no-repeat center center;
        }
       .c-black-frame,.c-white-frame{
           float:left;
           margin-left:1.5em;
       }
        .c-black-frame input,.c-white-frame input{
            margin-right:1em;
        }
        .c-black-price,.c-white-price{
            cursor: pointer;
        }
        input,select{
            border-radius: 3px;
        }
        .c-swarm-iexternal{
            position: absolute;
            top: 2px;
            right: 7px;
            line-height: 30px;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 0 .5em;
            background: #2E8DED;
            color: #fff;
            cursor: pointer;
        }

        #fieldsStyle{position: fixed;top:40px;z-index: 5;background: #fff;width: 100%;}
        #first_logical_select,#last_logical_select,#inputParameter_one_select,#inputParameter_two_select,#inputParameter_three_select,.add_last_logical_select,.add_inputParameter_one_select,.add_inputParameter_two_select,.add_inputParameter_three_select{width:100px}
        #input_parameter_style .pagination ul li{width:53px;margin:0}
        #option_popups .c-options-createuser{
            margin:1em auto;
            position: fixed;
            height: 60px;
            width: 100%;
            top: 40px;
        }
        #option_popups .c-decisions-switcher:first-child{
            float: left;
            width: 50%;
        }
        .active_active{
            color:#2E8DED;
        }
        .sanbox-popups .c-sanbox-lie-input{
            margin:1em 0;
        }
        .sanbox-popups .c-sanbox-lie-input input{
            margin: 5px 5px 0 0!important;
            position: relative;
            left: 30%;
        }
        .c-swarms .c-sanbox-lie-input{
            margin:1em 0;
            line-height: 12px;
        }
        .c-swarms .c-sanbox-lie-input input{
            position: relative;
            left: 40%;
        }
        .write-data-every{
            margin: 1em 0;
        }
        .left-menu li{
            height: 50px;
        }
    </style>
</head>
<body>
<input type="hidden" name="initEngineVersionId" value="${initEngineVersionId}" initEngineVersionId="${initEngineVersionId}">
<input type="hidden" name="engineId" value="${engineId}">
<input type="hidden" id="getCtxs" value="${ctx}">
<div class="main-container">
    <div class="main-container-inner">
        <c:import url="leftMenu.jsp" ></c:import>
        <c:import url="content.jsp"></c:import>
    </div>
    <!--评分卡弹出框-->
    <div class="scoreCard dialog" style="display: none;"></div>
</div>
<%--<script type="text/javascript" src="${ctx}/resources/js/validate/jquery.validate.min.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/resources/js/validate/messages_zh.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/resources/js/decision/danxuan.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/resources/js/validate/jquery.serialize-object.js" ></script>--%>
<%--<script type="text/javascript" src="${ctx}/resources/js/validate/map.js"></script>--%>
<%--<script type="text/javascript" src="${ctx}/resources/js/lib/jtopo/jtopo-0.4.8-min.js" charset="utf-8"></script>--%>
<%--<script src="${ctx}/resources/js/lib/dataTable/jquery.dataTables.js${version}"></script>--%>
<%--<script src="${ctx}/resources/js/lib/dataTable/dataTables.bootstrap.js${version}"></script>--%>
<%--<script src="${ctx}/resources/js/common/timeFormat.js${version}"></script>--%>
</body>
</html>
