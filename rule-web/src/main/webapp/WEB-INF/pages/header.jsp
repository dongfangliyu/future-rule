<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${ctx}/resources/js/common/Header.js${version}"></script>
<div class="navbar" id="navbar">
    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="${ctx}/index" class="navbar-brand">
                <img src="${ctx}/resources/images/logo.png"/>
            </a>
        </div>
        <div class="navbar-header pull-right" role="navigation">
            <div class="toolBar">
                <ul class="nav ace-nav">
                    <li><a href="javascript:void(0)" onclick="logout()">退出系统</a></li>
                    <li><a href="javascript:void(0)" onclick="changePwd()">修改密码</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- update password -->
<div class="change_Pass_style" id="change_Pass">
    <ul class="xg_style">
        <li>
            <label for="password" class="label_name">原&nbsp;&nbsp;密&nbsp;码</label>
            <input name="oldPwd" type="password" id="password"/>
        </li>
        <li>
            <label for="Nes_pas" class="label_name">新&nbsp;&nbsp;密&nbsp;码</label>
            <input name="newPwd" type="password" id="Nes_pas"/>
        </li>
        <li>
            <label for="c_mew_pas" class="label_name">确认密码</label><input name="confirmPwd" type="password" id="c_mew_pas"/>
        </li>
    </ul>
</div>