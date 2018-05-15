<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css${version}" />
    <link rel="stylesheet" href="${ctx}/resources/css/role.css${version}" />
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <script src="${ctx}/resources/js/common/customValid.js${version}"></script>
    <script src="${ctx}/resources/js/common/util.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.checkbox.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/checkboxTree.js${version}" type="text/javascript"></script>
    <script src="${ctx}/resources/js/systemMange/roleList.js${version}"></script>
    <title>角色管理</title>
</head>
<body>
<div class="page-content">
    <form id="form1" name="form1" method="post" action="">
        <div class="Role_Manager_style">
            <div class="Manager_style search_style">
                <ul class="search_content clearfix">
                    <li><label class="lf">角色标识</label>
                        <label>
                            <input id="sign" name="sign" type="text" class="text_add"/>
                        </label>
                    </li>
                    <li><label class="lf">角色名称</label>
                        <label>
                            <input id="name" name="name" type="text" class="text_add"/>
                        </label>
                    </li>
                    <button id="btn_search" type="button" class="queryBtn">查询</button>
                    <button id="btn_search_reset" type="button" class="queryBtn">查询重置</button>
                    <button  type="button" class="btn btn-primary addBtn" shiro:hasPermission="/role/add" id="addBtn"  onclick="updateRole(1)">添加角色</button>
                </ul>
                <div id="Assigned_Roles_style" style="display: none">
                    <div id="tree"></div>
                </div>
            </div>
            <div id="Add_Roles_style" style="display: none">
                <div class="role_Manager_style ">
                    <div class="add_role_style">
                        <ul class="clearfix">
                            <li>
                                <label class="label_name">标识</label>
                                <label>
                                    <input name="role_key" id="role_key" type="text" value="" class="text_add"  maxlength="64"/>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>
                            <li>
                                <label class="label_name">名称</label>
                                <label>
                                    <input name="role_name" id="role_name" type="text" value="" class="text_add"   maxlength="64"/>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>
                            <li>
                                <label class="label_name">状态</label>
                                <label style="width: 173px">
                                    <select name="status" size="1" id="status" class="select">
                                        <option value=1 id="qiyong" selected = "selected">启用</option>
                                        <option value=0 id="jinyong">停用</option>
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>
                            <li>
                                <label class="label_name">所属机构</label>
                                <label style="width: 173px">
                                    <select name="organ" size="1" id="organ" class="select" >
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>
                        </ul>
                        <div class="Remark" style="padding-top: 20px;">
                            <label class="label_name">备注</label>
                            <label>
                                <textarea name="role_remark" id="role_remark"  cols="" rows="" style="width: 456px; height: 100px; padding: 5px;" maxlength="512"></textarea>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <div class="Manager_style" >
        <div class="Role_list">
            <table id="Role_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover" style="cursor:pointer;font-size:13px;">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>标识</th>
                    <th>名称</th>
                    <th>状态</th>
                    <th>创建日期</th>
                    <%--<th>更新日期</th>--%>
                    <th>备注 </th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>