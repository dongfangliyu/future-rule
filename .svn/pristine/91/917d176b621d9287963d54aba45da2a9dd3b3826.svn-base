<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="time" value="<%=new Date().getTime()%>"/>
<c:set var="version" value="?v=${time}"/>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>规则集详情</title>
	<link rel="stylesheet" href="${ctx}/resources/css/decision.css${version}">
	<style>
		#c-refuse-rule .l_back{margin-top: 11px;display: inline-block;vertical-align: middle;float: left;width: 15px;height: 15px;border: 1px solid #e6e6e6;cursor: pointer;background: url("${ctx}/resources/images/prop/check.png");}
	</style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;display: none">
	<div style="height: 285px;">
		<div id="leftMenu" style="width:35%;float: left;height: 285px;overflow: auto">
			<div style="height: 30px;line-height: 30px">
				<div style="text-align: center;background: #0679CC;color: white;">
					规则集
				</div>
			</div>
			<div id="showRul">
				<input type="hidden" id="isShowRules">
                <input type="hidden" id="sing">
				<div class="Manager_style add_user_info search_style">
					<table style="cursor:pointer;" id="ruls_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th></th>
							<th>名称</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="rightMenu" style="width:65%;float: right;">
			<div style="height: 30px;line-height: 30px">
				<div style="text-align: center;background: #0679CC;color: white;">
					规则集详情
				</div>
			</div>
			<div id="showRuls" style="height:285px;overflow: auto">
				<input type="hidden" id="parentIds">
				<input type="hidden" id="isShowRules_detail">
				<div class="Manager_style add_user_info search_style">
					<table style="cursor:pointer;" id="ruls_detail_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
						<thead>
						<tr>
							<th></th>
							<th>选择</th>
							<th>名称</th>
						</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div >
		<div id="myTabContent" class="tab-content" style="display: none;height: 160px;overflow: auto">
			<div class="tab-pane active" id="bulletin">
				<div class=""></div>
			</div>
			<div class="" id="rule">
				<div class="c-policy-radio">
					<div  id="rules"  style="width:585px; float:left;">
					</div>
				</div>
				<div class="c-selected-preview"  id="preview-rule">
					<div class="c-policy-preview">
						<div class="c-policy-preview-span">
							<span>已选规则</span>
							<button class="preview-span queryBtn" id ="preview">预览</button>
						</div>
						<div class="c-tier-one" style="display: none;overflow: hidden">
							<div class="c-reject-lfet" style="margin: 5px 0;">
								<div class="c-refuse-rule">拒绝规则</div>
								<div class="c-refuse-rule" id="c-refuse-rule">
									<div class="l_checkbox" style="margin:0px 3px 0 0;"></div>
									<span>串行</span>
								</div>
							</div>
							<div class="c-reject-right" id="refuse-rule">
								<div class="c-policy-priority" id="priority-0">
									<div class="left-priority" dataTitle="priority_0">优先级0</div>
									<div class="right-priority priority_0">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-1">
									<div class="left-priority" dataTitle="priority_1">优先级1</div>
									<div class="right-priority priority_1">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-2">
									<div class="left-priority" dataTitle="priority_2">优先级2</div>
									<div class="right-priority priority_2">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-3">
									<div class="left-priority" dataTitle="priority_3">优先级3</div>
									<div class="right-priority priority_3">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-4">
									<div class="left-priority" dataTitle="priority_4">优先级4</div>
									<div class="right-priority priority_4">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-5">
									<div class="left-priority" dataTitle="priority_5">优先级5</div>
									<div class="right-priority priority_5">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-6">
									<div class="left-priority" dataTitle="priority_6">优先级6</div>
									<div class="right-priority priority_6">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-7">
									<div class="left-priority" dataTitle="priority_7">优先级7</div>
									<div class="right-priority priority_7">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-8">
									<div class="left-priority" dataTitle="priority_8">优先级8</div>
									<div class="right-priority priority_8">
									</div>
								</div>
								<div class="c-policy-priority" style="border-top: 1px solid #E6E6E6;" id="priority-9">
									<div class="left-priority" dataTitle="priority_9">优先级9</div>
									<div class="right-priority priority_9">
									</div>
								</div>
							</div>
						</div>
						<div class="c-tier-two">
							<div class="c-subtract-left" id="addorSub-rule-left">
								<div class="c-deduction">加减分规则</div>
								<div class="c-deduction">
									<span style="margin:1em">阈值</span>
									<input id="threshold" value="" style="width:60px;"/>
								</div>
							</div>
							<div class="c-subtract-right" id="addorSub-rule">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="previewForm" method="post" target="newWin" action="${ctx}/decision_flow/previewRule">
		<input type="hidden" name ="id" >
		<input type = "hidden" name ="ruleJson" id="ruleJson">
	</form>
</div>
<div id="priorityList" style="background-color: rgb(230, 224, 224);width: 100%;display: none">
	<p id="priorityListName" style="margin-top:.5em;font-weight: bold"></p>
	<ul>
		<li><label><input type="radio" name="priorityList" id="priorityList_0" value="0">优先级0</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_1" value="1">优先级1</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_2" value="2">优先级2</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_3" value="3">优先级3</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_4" value="4">优先级4</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_5" value="5">优先级5</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_6" value="6">优先级6</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_7" value="7">优先级7</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_8" value="8">优先级8</label></li>
		<li><label><input type="radio" name="priorityList" id="priorityList_9" value="9">优先级9</label></li>
	</ul>
	<input type="hidden" id="priorityList_name">
	<input type="hidden" id="priorityList_val">
	<input type="hidden" id="priorityList_code">
	<input type="hidden" id="priorityList_priority">
	<input type="hidden" id="priorityList_dataParentId">
</div>
<script src="${ctx}/resources/js/decision/decision-rules.js${version}"></script>
</body>
</html>