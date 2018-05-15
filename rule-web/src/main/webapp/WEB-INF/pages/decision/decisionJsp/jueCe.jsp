<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="c-decision dialog" id="option_dialog"  style="display: none">
	<div class="c-decision-head move_part">
	</div>
	<div class="c-decision-content" id="c-decision-option">
	</div>
	<%@include file ="decisionOptions.jsp"%>
</div>
<%--决策选项 字段选项 评分卡--%>
<div style="display: none" id="option_popups">
	<div  class="c-options-createuser" align="center">
		<div class="c-decisions-switcher" >
			<a  id="option_fd" class="active active_active" href="#####" hrefs="showOne">字段</a>
		</div>
		<div class="c-decisions-switcher" style="border-right: none;">
			<a class="active" id="option_sc" href="####" hrefs="showTwo">评分卡</a>
		</div>
	</div>
	<div class="Manager_style add_user_info search_style" id="showOne" style="margin-top: 30px">
		<input type="hidden" id="isShowOptionFileds1">
		<input type="hidden" id="isShowOptionFileds1_out">
		<label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >
			当前选项是:
			<b id="showFields1">/</b>
			<b id="hiddenFields1" style="display: none"></b>
			<b id="valueType1" style="display: none"></b>
			<b id="hiddenRestrainScope1" style="display: none"></b>
			<b id="enName1" style="display: none"></b>
		</label>
		<div style="padding-top: 35px;" id="fieldsStyle1">
			<ul class="clearfix">
				<li style="margin-left:1.5em;margin-top:1.5em;float:left">
					<label class="lf">字段名称</label>
					<label>
						<input name="optionFields_list1Search"  id="optionFields_list1Search" type="text" class="text_add"/>
					</label>
				</li>
				<li style="float:right;margin-top:1.0em;">
					<button id="btn_search_option1"  type="button" class="queryBtn">查询</button>
					<button id="btn_search_reset_option1"  type="button" class="queryBtn">查询重置</button>
				</li>
			</ul>
		</div>
		<table style="text-align: center;cursor: pointer;margin-top: 20px" id="optionFields_list1" class="table table-striped table-bordered table-hover">
			<thead>
			<tr>
				<th></th>
				<th>选择</th>
				<th>名称</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<div class="Manager_style add_user_info search_style" id="showTwo" style="display: none;margin-top: 30px">
		<input type="hidden" id="isShowOptionFileds2">
		<input type="hidden" id="isShowOptionFileds2_out">
		<label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >
			当前选项是:
			<b id="showFields2">/</b>
			<b id="hiddenFields2" style="display: none"></b>
			<b id="valueType2" style="display: none"></b>
			<b id="hiddenRestrainScope2" style="display: none"></b>
			<b id="enName2" style="display: none"></b>
		</label>
		<div style="padding-top: 35px;" id="fieldsStyle2">
		</div>
		<table style="text-align: center;cursor: pointer;margin-top: 20px" id="optionFields_list2" class="table table-striped table-bordered table-hover">
			<thead>
			<tr>
				<th></th>
				<th>选择</th>
				<th>名称</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</div>

