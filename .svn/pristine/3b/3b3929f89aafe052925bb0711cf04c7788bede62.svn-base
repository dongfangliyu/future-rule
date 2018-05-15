<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
	<%@include file ="../common/taglibs.jsp"%>
	<%@ include file="../common/importDate.jsp"%>
	<script src="${ctx}/resources/js/engineMange/engineList.js${version}"></script>
	<title>引擎管理</title>
	<style>
	</style>
</head>
<body>
<div class="page-content">
	<div class="commonManager">
		<input type="hidden" value="${userId}" id="userId">
		<input type="hidden" value="${roleId}" id="roleId">
		<form id="form1" name="form1" method="post" action="">
			<div class="Manager_style add_user_info search_style">
				<ul class="search_content clearfix">
					<li><label class="lf">引擎名称</label>
						<label>
							<input name="name" type="text" placeholder="名称" class="text_add"/>
						</label>
					</li>
					<button id="btn_search"  type="button" class="queryBtn">查询</button>
					<button id="btn_search_reset"  type="button" class="queryBtn">查询重置</button>
					<button id="addBtn" class="btn btn-primary addBtn " type="button" onclick="updateOrger(1)">添加引擎</button>
				</ul>
				<div id="Add_orger_style" style="display: none">
					<div class="addCommon clearfix">
						<ul class="clearfix">
							<li style="width:393px">
								<label class="label_name">引擎编码</label>
								<label>
									<input name="orger_code" style="width:300px;" type="text" value="engine_0001" id="orger_code" readonly/>
								</label>
							</li>
							<li style="width:393px">
								<label class="label_name">引擎名称</label>
								<label for="orger_name">
									<input name="orger_name" style="width:290px;margin-left: 14px" type="text"  id="orger_name" maxlength="50"/>
								</label>
								<i style="color: #F60;">*</i>
							</li>

							<li>
								<label class="label_name">权限类型</label>
								<label>
									<select name="jurisdiction"  size="1" id="jurisdiction">
										<option value="1">公共</option>
										<option value="2">角色</option>
										<option value="3">私有</option>
									</select>
								</label>
							</li>
							<li style="width:393px">
								<label class="label_name">引擎描述</label>
								<label for="remark" style="margin-top: 16px">
									<textarea name="remark"  type="text" style="width:300px;" id="remark" rows="4" cols="25" maxlength="150"></textarea>
								</label>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</form>
		<div class="Manager_style">
			<div class="Organization_list">
				<table style="cursor:pointer;" id="orger_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
					<thead>
					<tr>
						<th style="display:none">ID</th>
						<th>引擎ID</th>
						<th>引擎编码</th>
						<th>引擎名称</th>
						<th>权限类型</th>
						<th>引擎描述</th>
						<th>最后更新时间</th>
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
</html>