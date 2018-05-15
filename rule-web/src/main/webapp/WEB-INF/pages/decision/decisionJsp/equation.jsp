<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="time" value="<%=new Date().getTime()%>"/>
<c:set var="version" value="?v=${time}"/>
<script type="text/javascript" src="${ctx}/resources/js/equation/jquery.atwho.js${version}"></script>
<div class="c-field-equation">
    <div class="c-field-operator-head" style="border-bottom:none;">
        <div class="c-operational-character" dataId='0'>
            <img src="${ctx}/resources/images/scorecard/1.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/1_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-character" dataId='1'>
            <img src="${ctx}/resources/images/scorecard/2.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/2_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-character" dataId='2'>
            <img src="${ctx}/resources/images/scorecard/3.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/3_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-character" dataId='3'>
            <img src="${ctx}/resources/images/scorecard/4.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/4_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='4'>
            <img src="${ctx}/resources/images/scorecard/5.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/5_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='5'>
            <img src="${ctx}/resources/images/scorecard/6.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/6_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='6'>
            <img src="${ctx}/resources/images/scorecard/7.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/7_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-character" dataId='7'>
            <img src="${ctx}/resources/images/scorecard/8.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/8_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='8'>
            <img src="${ctx}/resources/images/scorecard/10.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/10_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='9'>
            <img src="${ctx}/resources/images/scorecard/11.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/11_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo shows">
            <img src="${ctx}/resources/images/scorecard/19.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/19_1.png" class="c-operator-hide">
        </div>
    </div>
    <div class="c-field-operator-head"style="display:none;">
        <div class="c-operational-characterTwo" dataId='10'>
            <img src="${ctx}/resources/images/scorecard/12.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/12_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='11'>
            <img src="${ctx}/resources/images/scorecard/13.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/13_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='12'>
            <img src="${ctx}/resources/images/scorecard/14.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/14_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='13'>
            <img src="${ctx}/resources/images/scorecard/15.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/15_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='14'>
            <img src="${ctx}/resources/images/scorecard/16.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/16_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='15'>
            <img src="${ctx}/resources/images/scorecard/17.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/17_1.png" class="c-operator-hide">
        </div>
        <div class="c-operational-characterTwo" dataId='16'>
            <img src="${ctx}/resources/images/scorecard/18.png" class="c-operator-show">
            <img src="${ctx}/resources/images/scorecard/18_1.png" class="c-operator-hide">
        </div>
    </div>
    <!-- 公式部分 -->
    <div class="includeFormula" style="width: 558px; overflow: auto; max-height: 175px;overflow-x: hidden;">
    </div>
</div>