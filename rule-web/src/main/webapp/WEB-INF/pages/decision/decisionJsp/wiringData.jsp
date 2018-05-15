<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- 数据填写 -->
<div class="write-data-message  dialog" style="display:none;position: absolute">
	<div class="write-data-content">
	</div>
	<div class="writeHistory" style="display:none;">
		<span>复杂规则：</span>
		<a id="write-order" style="cursor: pointer;margin-left: 3.2em;" onclick="showHistory()">填写数据</a>
	</div>
</div>
<%--填写历史记录--%>
<div class="write-history-order" style="display: none;">
	<div class="write-history-content">
		<table id="hisWrite" cellpadding="0" cellspacing="4" border="0" class="history_table">
			<thead><tr><td>行号</td></tr></thead>
			<tbody><tr></tr><tr></tr><tr></tr></tbody>
		</table>
	</div>
</div>

