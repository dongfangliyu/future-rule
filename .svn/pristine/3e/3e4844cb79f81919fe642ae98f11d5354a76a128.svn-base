<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file ="../common/taglibs.jsp"%>
	<%@ include file="../common/importDate.jsp"%>
	<link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
	<link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
	<link rel="stylesheet" href="${ctx}/resources/css/dataMsg.css${version}"/>
	<link rel="stylesheet" href="${ctx}/resources/css/menu.css${version}" />
	<link rel="stylesheet" href="${ctx}/resources/assets/atwho/jquery.atwho.css${version}"/>
	<script type="text/javascript" src="${ctx}/resources/js/equation/jquery.atwho.js${version}"></script>
	<script type="text/javascript" src="${ctx}/resources/js/equation/jquery.caret.js${version}"></script>
	<script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
	<script src="${ctx}/resources/assets/jstree/jstree.checkbox.js${version}"></script>
	<script src="${ctx}/resources/assets/jstree/checkboxTree.js${version}"></script>
	<script src="${ctx}/resources/js/validate/map.js${version}"></script>
	<script src="${ctx}/resources/js/rulesMange/jsTreeInit.js${version}"></script>
	<script src="${ctx}/resources/js/rulesMange/rule-collection.js${version}"></script>
	<title>规则管理</title>
	<style type="text/css">
		#select_type{  padding-left: 37px;  width: 114px;  margin-left: 10px;  }
		#editContent{  padding:8px 78px 24px;  }
		.clearfix li{float:left;width:50%;margin:1em 0}
		#Priority{width:163px;height:28px;}
		.iconaddData,.iconclearData{  width: 20px;  height: 20px;  display: inline-block;  background: url(${ctx}/resources/images/blackadd.png) center center;  background-size: cover;  cursor: pointer;  margin-right: 1.5em;  vertical-align: middle;  margin-bottom: 7px;  }
		.iconclearData{ width: 20px;  height: 20px;  display: inline-block;background: url(${ctx}/resources/images/blackClear-enabled.png) center center;  background-size: cover;  cursor: pointer;  margin-right: 1.5em;  vertical-align: middle;  margin-bottom: 7px; }
		.iconclearData_disabled{width: 20px;  height: 20px;  display: inline-block;background: url(${ctx}/resources/images/blackClear-disabled.png) center center;  background-size: cover;  cursor: pointer;  margin-right: 1.5em;  vertical-align: middle;  margin-bottom: 7px; }
		#fieldsStyle{position: fixed;top:40px;z-index: 5;background: #fff;width: 100%;}
		#first_logical_select,#last_logical_select,#inputParameter_one_select,#inputParameter_two_select,#inputParameter_three_select,.add_last_logical_select,.add_inputParameter_one_select,.add_inputParameter_two_select,.add_inputParameter_three_select{width:100px}
		#input_parameter_style .pagination ul li{width:53px;margin:0}
		#last_logical_select{margin-left:87px}
		#checkBox_btn button{margin:0px;padding:0px;border:0px;width:49%;height:35px;font-size: 12pt;background-color: #c6c6c6;color: #000}
		#checkBox_sure:hover{background-color: #009f95;color: #fff}
		#checkBox_cancel:hover{background-color: #E11C27;color: #fff}
		.c-popup-engine{  overflow:auto;  overflow-x:hidden;  border:1px solid #E6E6E6;  margin-left:5px;  }
		.c-popup-redact { width: 240px;  line-height: 30px;  margin: 0 0 0 15px;  font-size: 14px;  color: #777777;  font-weight: 600;  float: left;  }
		.c-popup-redact a {  float: right;  font-size: 14px;  color: #398dff;  padding-right: 15px;  }
		.c-popup-a{  display:inline-block;  margin-top:8px;  }
		#input_parameter_style .page_jump{display: none;}
		select[name=fieldId]{outline: none!important;appearance:none; -moz-appearance:none; -webkit-appearance:none;  height: 26px;line-height:17px;position: relative;top:-1px;}
		.field-bounced-table table{margin:1em 0;width:100%;}
		.field-bounced-table table td{width:30%;text-align: center;}
		.field-bounced-first{overflow: hidden;margin: 1em 0;}
		.c-bounced-grid{float: left;width: 30%;text-align: center;}
		.field-bounced-first .c-bounced-grid input{width:120px!important;margin-left: 30px;}
		.field-bounced-first .c-bounced-grid:nth-child(2) input{margin-left: 40px;}
		.field-bounced-first .c-bounced-grid:last-child{position: relative;top: 4px;left: 40px;}
		#conditions select{margin-right:4px;}
		#OutRuleLi select[name=fieldId]{height:29px;}
		#conditions input{color:#858585!important;border:1px solid #d5d5d5;height:30px;padding-left: 3px}
		#Parameter_list_info{display: none}
	</style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
	<div id="treeMenu" style="width:15%;float: left;box-shadow: 2px 2px 2px #888;overflow-x: auto">
		<div style="height: 40px;line-height: 40px;width:100%">
			<div style="text-align: center;background: #0679CC;color: white;">
				知识库
			</div>
		</div>
		<div id="leftTree" style="max-height:400px;overflow-y: auto">
			<div id="checkBox_btn" style="margin:0px;padding:0px;width: 99%;height:35px;float: right;display: none;text-align: center;">
				<button id="checkBox_sure">确&nbsp;&nbsp;定</button>
				<button id="checkBox_cancel">取&nbsp;&nbsp;消</button>
			</div>
			<div id="jsTreeCheckBox" style="display: none;">
			</div>
			<div id="jstree"></div>
			<input type="hidden" id="hdNodeId" value="">
		</div>
	</div>
	<div id="content" style="width: 85%;float: right;height: 100%;overflow: auto;">
		<div class="Res Manager_style">
			<div class="table_res_list">
				<form id="form">
					<input type="hidden" name="eid" value="${engineId}">
					<input type="hidden" name="fieldContent" value="">
					<input type="hidden" name="outcontent" value="">
					<input type="hidden" name="content" value="">
					<input type="hidden" name="score" value="">
					<input type="hidden" name="name" value="">
					<input type="hidden" name="lastLogical" value="">
					<input type="hidden" name="ruleAudit" value="">
					<input type="hidden" name="parentId" value="">
					<input type="hidden" name="parentIds" value="">
                    <input type="hidden" name="folderId" value="">
				</form>
				<form id="form1" name="form1" method="post" action="">
					<input type="hidden" value="" id="showFieldsPlace">
					<div class="Manager_style add_user_info search_style" style="margin: 1em;">
						<ul class="search_content clearfix">
							<button class="btn btn-primary addBtn" type="button" id="addBtn" onclick="updateRus(1,null)" style="display: none">新增</button>
							<input type="text" name="rule_search" id="rule_search" placeholder="名称" style="vertical-align: middle"/>
							<button id="btn_search" type="button" class="queryBtn" style="top:0">查询</button>
							<button id="btn_search_reset" type="button" class="queryBtn" style="top:0">查询重置</button>
							<button id="upload" type="button" class="queryBtn" style="top:0" onclick="leadLoad()">导入</button>
							<button id="export" type="button" class="queryBtn" style="top:0" onclick="exportRule()">导出</button>
						</ul>
					</div>
					<c:import url="createRule.jsp"/>
					<c:import url="createScorecard.jsp"/>
					<div id="input_parameter_style" style="display: none">
						<div class="addCommon clearfix">
							<div style="padding-top: 8px;" id="fieldsStyle">
								<label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >
									当前选项是:
									<b id="showFields">/</b><b id="hiddenFields" style="display: none"></b>
									<b id="hiddenRestrainScope" style="display: none"></b>
									<b id="enName" style="display: none"></b>
								</label>
								<ul style="float: right">
									<input type="text" name="Parameter_search" id="Parameter_search" placeholder="名称" style="vertical-align: middle"/>
									<button id="param_search" type="button" class="queryBtn" style="top:0">查询</button>
									<button id="param_search_reset" type="button" class="queryBtn" style="top:0">查询重置</button>
								</ul>
							</div>
							<hr>
							<div>

							<div style="width: 40%;float:left">
								<table style="text-align: center;cursor: pointer;margin-top: 20px" id="Folder_list" class="table table-striped table-bordered table-hover">
									<thead>
									<tr>
										<th></th>
										<th>ID</th>
										<th>目录</th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</div>
							<div style="width: 60%;float:left">
							<table style="text-align: center;cursor: pointer;margin-top: 20px" id="Parameter_list" class="table table-striped table-bordered table-hover">
								<thead><tr><th></th><th></th><th>请选择</th></tr></thead>
								<tbody></tbody>
							</table>
							</div>
						</div>
						</div>
					</div>
					<%--导入选择文件--%>
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
				</form>
				<table style="text-align: center;cursor: pointer;display: none;font-size:13px;" id="rule_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
					<thead>
					<tr>
						<th>序号</th>
						<th>规则代码</th>
						<th>规则名称</th>
						<th>规则描述</th>
						<th>状态</th>
						<th>创建人</th>
						<th>创建时间</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
					</tbody>
				</table>

				<table style="text-align: center;cursor: pointer;display: none;font-size:13px;" id="scorecard_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
					<thead>
					<tr>
						<th>序号</th>
						<th>评分卡代码</th>
						<th>评分卡名称</th>
						<th>评分卡描述</th>
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
<!-- 删除字段影响引擎提示框 -->
<div id="dialog_engine" style="display: none">
	<div class="commonManager ">
		<div class="addCommon clearfix">
			<ul class="clearfix">
				<div style="margin-bottom: 1em;">
					<img style="margin-right: 4em;" src="${ctx}/resources/images/prompt.png" />
					<span class="c-reminder-span">删除或停用本字段影响以下内容</span>
				</div>
				<div class ="c-popup-engine">
				</div>
			</ul>
		</div>
	</div>
</div>
<script>
	var layerIndex,jstree,jsTreeCheckBox,currentNode;
	var trash1,trash2,trash3;
	var engineId = $("input[name=eid]").val();
	var parentId,parentIds;
	var treeTypeFlag=false;
	var field_isOutput_array,field_isInput_array;
	var map = new Map();
	var engineIdFlag = true;
	var recFlag = false;//点击的是否是回收站

	$('#jstree').bind("activate_node.jstree", function (obj, e) {
		var menuId = e.node.id;//得到被点击节点的id
		$("#hdNodeId").val(menuId);//记录被点击节点的id
	});
</script>
</body>
</html>