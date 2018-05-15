<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/systemMange/User_Ctrl.js${version}"></script>
    <title>用户管理</title>
    <style>
        #organ{outline: none!important;appearance:none; -moz-appearance:none; -webkit-appearance:none;height:28px;line-height:19px;}
        #email,#user_name{margin-left:0px!important;}
        #isLock{height:28px;}
    </style>
</head>
<body>
<div class="page-content">
    <div class="commonManager">
        <form id="form1" name="form1" method="post" action="">
            <div class="Manager_style add_user_info search_style">
                <ul class="search_content clearfix">
                    <li>
                        <label>帐号<input name="account"  type="text" class="text_add"/></label>
                    </li>
                    <li>
                        <label>姓名<input name="trueName" type="text" class="text_add"/></label>
                    </li>
                    <li>
                        <label>手机号码<input name="mobile"  type="text" class="text_add"/></label>
                    </li>
                    <button id="btn_search"  type="button" class="queryBtn">查询</button>
                    <button id="btn_search_reset"  type="button" class="queryBtn">查询重置</button>
                    <button class="btn btn-primary addBtn" type="button" id="addBtn" onclick="updateUser(1)" id="Add_user_btn">添加用户</button>
                </ul>
                <div id="Add_user_style" style="display: none">
                    <div class="addCommon clearfix">
                        <ul class="clearfix">
                            <li>
                                <label class="label_name">账号</label>
                                <label for="name">
                                    <input name="user_account"  type="text"  id="name"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li id="isEdit">
                                <label class="label_name">密码</label>
                                <label for="password">
                                    <input name="user_password"  type="password" id="password"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">手机号</label>
                                <label for="phone">
                                    <input name="user_mobile"  type="text" id="phone" maxlength="11"/>
                                    <i style="color: #F60;">*</i>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">邮箱</label>
                                <label for="email">
                                    <input name="user_email"  type="text" id="email" maxlength="30"/>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">真实姓名</label>
                                <label for="user_name">
                                    <input name="user_trueName"  type="text" id="user_name" maxlength="15"/>
                                </label>
                            </li>
                            <li>
                                <label class="label_name">状态</label>
                                <label>
                                    <select name="isLock" size="1" id="isLock">
                                        <option value=1 id="qiyong">启用</option>
                                        <option value=0 id="jinyong">停用</option>
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>
                            <li>
                                <label class="label_name">所属机构</label>
                                <label>
                                    <select name="organ" size="1" id="organ">
                                    </select>
                                </label>
                                <i style="color: #F60;">*</i>
                            </li>
                            <li style="width:524px;">
                                <label class="label_name">备注</label>
                                <textarea name="remark"  type="text" style="width:428px;margin-top:10px;margin-left:-4px;" id="remark" rows="4" cols="25" maxlength="150"></textarea>
                            </li>
                        </ul>
                    </div>
                </div>

                <div id="asignRole" style="height:100%;display: none;">
                    <div class="col-xs-5" style="height: 100%;" >
                        <select id="multiselect" style="height: 100%;"  class="form-control" size="8" multiple="multiple">
                            <option style="text-align:center;"></option>
                        </select>
                    </div>
                    <div class="col-xs-2" style="margin: 20% 0;">
                        <button type="button" id="multiselect_rightAll" class="btn btn-block" onclick="rightAll()"><i class="glyphicon glyphicon-forward"></i></button>
                        <button type="button" id="multiselect_rightSelected" class="btn btn-block" onclick="rightSelected()"><i class="glyphicon glyphicon-chevron-right"></i></button>
                        <button type="button" id="multiselect_leftSelected" class="btn btn-block" onclick="leftSelected()"><i class="glyphicon glyphicon-chevron-left"></i></button>
                        <button type="button" id="multiselect_leftAll" class="btn btn-block" onclick="leftAll()"><i class="glyphicon glyphicon-backward"></i></button>
                    </div>
                    <div class="col-xs-5" style="height: 100%;" >
                        <select  style="height: 100%;"  id="multiselect_to" class="form-control" size="8" multiple="multiple">
                            <option  style="text-align:center;"></option>
                        </select>
                    </div>
                </div>

            </div>
        </form>
        <div class="Manager_style">
            <div class="User_list">
                <table style="cursor:pointer;" id="User_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>组织名称</th>
                        <th>帐号</th>
                        <th>姓名</th>
                        <th>状态</th>
                        <th>手机号</th>
                        <th>创建日期</th>
                        <th>备注</th>
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