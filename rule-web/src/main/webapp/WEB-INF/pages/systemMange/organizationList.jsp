<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@include file ="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/systemMange/organization_List.js${version}"></script>
    <title>组织管理</title>
    <style>
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix">
                    <li>
                        <label class="lf">组织名称<input name="name"  type="text" class="text_add"/></label>
                    </li>
                    <li>
                        <label class="lf">手机号码<input name="telephone" type="text" class="text_add"/></label>
                    </li>
                    <li>
                        <label class="lf">创建者<input name="author"  type="text" class="text_add"/></label>
                    </li>
                    <button id="btn_search"  type="button" class="queryBtn">查询</button>
                    <button id="btn_search_reset"  type="button" class="queryBtn">查询重置</button>
                    <button class="btn btn-primary addBtn " type="button" onclick="updateOrger(1)">添加组织</button>
                </ul>
                <div id="Add_orger_style" style="display: none">
                    <div class="addCommon clearfix">
                        <ul class="clearfix">
                            <li>
                                <label class="label_name">组织名称</label>
                                <label for="orger_name">
                                    <input name="orger_account"  type="text"  id="orger_name" maxlength="30"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">组织代码</label>
                                <label for="orger_code">
                                    <input name="orger_code"  type="text"  id="orger_code" maxlength="30"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">组织邮箱</label>
                                <label for="orger_email">
                                    <input name="orger_email" type="text" id="orger_email" maxlength="30" style="margin-left:0"/>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">手机号</label>
                                <label for="orger_phone">
                                    <input name="orger_mobile"  type="text" id="orger_phone" maxlength="11"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">状态</label>
                                <label>
                                    <select name="isLock" id="isLock" style="height:30px">
                                        <option value=1 id="orger_qiyong">启用</option>
                                        <option value=0 id="orger_tingyong">停用</option>
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
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
                        <th>序号</th>
                        <th>组织名称</th>
                        <th>组织代码</th>
                        <th>组织邮箱</th>
                        <th>手机号</th>
                        <th>状态</th>
                        <th>创建者</th>
                        <th>创建时间</th>
                        <%--<th>唯一标识</th>--%>
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