<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 选择字段弹框 -->
<div class="c-createusers-dialog selWord dialog group-dialog" id="" style="display: none">
	<div  class="c-options-createuser" style="margin: 1em auto;position: fixed;height: 60px;width: 100%;top: 40px;" align="center">
		<div class="c-decisions-switcher" style="float: left;width: 50%" >
			<a  id="option_field" class="active active_active" href="#####" hrefs="fieldsStyle">字段</a>
		</div>
		<div class="c-decisions-switcher" style="border-right: none;">
			<a class="active" id="option_scored" href="####" hrefs="showScored">评分卡</a>
		</div>
	</div>
	<input type="hidden" id="groupNodeId">
	<input type="hidden" id="isGroupScore">
	<input type="hidden" value="" id="showFieldsPlace">
	<div class="Manager_style add_user_info search_style" style="padding-top: 15px;margin-top: 30px;" id="fieldsStyle">
		<label style="text-align: left;font-size: 15px;margin-left: 1em;float: left;line-height: 30px" >
			当前选项是:
			<b id="showFields">/</b>
			<b id="hiddenFields" style="display: none"></b>
			<b id="hiddenRestrainScope" style="display: none"></b>
			<b id="enName" style="display: none"></b>
		</label>
		<div>
			<ul style="float: right" id="ziduanchaxun">
				<input type="hidden" id="isShowFileds">
				<input type="text" name="Parameter_search" id="Parameter_search" placeholder="搜索" style="vertical-align: middle"/>
				<button type="button" class="queryBtn" style="top:0" id="btn_search">查询</button>
				<button type="button" class="queryBtn" style="top:0" id="btn_search_reset">查询重置</button>
			</ul>
		</div>
		<table style="text-align: center;cursor: pointer;margin-top: 70px" id="swarmFields_list" class="table table-striped table-bordered table-hover">
			<thead>
			<tr>
				<th></th>
				<th></th>
				<th>请选择</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<div class="Manager_style add_user_info search_style" id="showScored" style="display: none;margin-top: 30px">
		<table style="text-align: center;cursor: pointer;margin-top: 20px" id="optionScoreds_list" class="table table-striped table-bordered table-hover">
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
<!-- 选评分卡  字段弹框 -->
<div class="c-createusers-dialogs  dialog option_popup" id="option_popup" style="display: none">
	<div  class="tab-content option_popup_content" id="option_popup_content" style="display: none">
	</div>
	<div class="Manager_style add_user_info search_style">
		<input type="hidden" id="isShowScoreFileds">
		<ul class="clearfix">
			<li style="margin-left:1.5em;float:left ;margin-top:1.5em">
				<label class="lf">评分卡名称</label>
				<label>
					<input name="checkscores"  type="text" class="text_add"/>
				</label>
			</li>
			<li style="float:right;margin-top:1.0em">
				<button id="btn_search_score"  type="button" class="queryBtn">查询</button>
				<button id="btn_search_reset_score"  type="button" class="queryBtn">查询重置</button>
			</li>
		</ul>
		<table style="text-align: center;cursor: pointer;margin-top: 20px" id="scoreFields_list" class="table table-striped table-bordered table-hover">
			<thead>
			<tr>
				<th></th>
				<th>选择</th>
				<th>Id</th>
				<th>名称</th>
				<th>描述</th>
				<th>创建时间</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</div>

