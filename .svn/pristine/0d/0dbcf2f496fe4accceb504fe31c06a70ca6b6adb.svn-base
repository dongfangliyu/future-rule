<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="time" value="<%=new Date().getTime()%>"/>
<c:set var="version" value="?v=${time}"/>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="${ctx}/resources/assets/atwho/jquery.atwho.css">
	<script src="${ctx}/resources/assets/atwho/jquery.atwho.js"></script>
	<script src="${ctx}/resources/assets/atwho/jquery.caret.js"></script>
	<link rel="stylesheet" href="${ctx}/resources/css/decision.css${version}">
    <title>决策选项</title>
</head>
<body>
    <input type="hidden" id="getPath" value="${ctx}">
	<div id="options">
		<div class="c-decision-variable" id="ser-value">
			<div class="c-variable-content" id ="d-option">
				<div class="c-variable-add" style="margin-top:10px;">
					<span>输入变量</span>
					<%--<img src="${ctx}/resources/images/rules/add.png" id="add_option" />--%>
					<%--<img src="${ctx}/resources/images/rules/delete.png" id ="delete_option" />--%>
				</div>
				<div class="l_selects" style="float:left; width:400px; overflow:hidden;">
					<div class="select_option" style="width:130px; float:left; margin-top:10px;margin-left: 45px;">
						<input  data="" class="l_before_input input_variable"  style="width:124px;background-position:105px 0px;">
					</div>
				</div>
			</div>
			<div class="c-export-variable" id="d-option-out">
				<span>输出变量</span>
					<input name="output" id="output" data="" class="l_before_input input_variable"  style="width:124px;margin-left:81px;">
			</div>
		</div>
		<div class="c-decision_region-content" id="d-option-1" >
			<div id="myTab1" class="decision-TabControl-head" role="tablist">
				<div class="c-decision-switcher">
					<a href="#decision_region" id ="decision_region_tag" class="" role="tab" data-toggle="tab">决策区域</a>
				</div>
				<div class="c-decision-switcher" style="border-right: none;">
					<a href="#equation_editing" id ="equation_editing_tag" class=""  role="tab" data-toggle="tab">公式编辑</a>
				</div>
			</div>
			<div id="myTabContent1" class="tab-content">
				<div class="tab-pane active" id="decision_region" style="">
					<div class="c-field-condition">
					</div>
				</div>
				<div class="tab-pane" id="equation_editing" >
					<%@ include file="equation.jsp"%>
				</div>
				</div>
			</div>
		</div>
		<div class="c-decision_region-content" id="d-option-2" >
			<div id="myTab2" class="decision-TabControl-head" role="tablist">
				<div class="c-decision-switcher">
					<a href="#decision_regionTwo"  id ="decision_regionTwo_tag" class="" role="tab" data-toggle="tab">决策区域</a>
				</div>
				<div class="c-decision-switcher" style="border-right: none;">
					<a href="#equation_editingTwo" id ="equation_editingTwo_tag" class=""  role="tab" data-toggle="tab">公式编辑</a>
				</div>
			</div>
			<div id="myTabContent2" class="tab-content">
				<div class="tab-pane active" id="decision_regionTwo">
					<div class="c-decision_region">
						<div class="c-first-line">
							<div class="c-front-portion-head">
								<div class="c-variate-a">决策变量A</div>
							</div>
							<div class ="c-option-a"></div>
						</div>
						<div class="c-next-line" style="overflow-x:scroll;max-width:390px;">
							<div class="c-queen-portion-head">
								<div class="c-variate-b">
									<span>决策变量B</span>
								</div>
								<div class="c-section-decision" id="des-b" style="width:1000px;">
									<div class="c-section-left" style="width:80px;">
										<img src="${ctx}/resources/images/rules/add.png"  class ="l_op_b_addData"/>
										<img src="${ctx}/resources/images/rules/delete.png"  class ="l_op_b_delData"/>
									</div>
								</div>
							</div>
							<div class="c-option-c"></div>
						</div>
					  </div>
				   </div>
				 <div class="tab-pane" id="equation_editingTwo">
					 <%@ include file="equation.jsp"%>
				 </div>
			</div>
		</div>
		<div class="c-decision_region-content" id="d-option-3" >
		   <p style="padding: 10px 0 0 20px;color:#777777;">公式编辑</p>
			<%@ include file="equation.jsp"%>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/resources/js/equation/jquery.atwho.js${version}"></script>
	<script type="text/javascript" src="${ctx}/resources/js/equation/jquery.caret.js${version}"></script>
	<%--<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/equation/equation.js${version}"></script>--%>
	<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/equation/equations.js${version}"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/decision/condition.js${version}" ></script>
	<script type="text/javascript" src="${ctx}/resources/js/validate/map.js${version}"></script>
    <script type="text/javascript" src="${ctx}/resources/js/decision/decision_options.js${version}"></script>
</body>
</html>