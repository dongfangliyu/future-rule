<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sidebar" id="sidebar">
    <script type="text/javascript">
        try {
            ace.settings.check('sidebar', 'fixed')
        } catch (e) {
        }
    </script>
    <!-- #sidebar-shortcuts -->
    <ul class="nav nav-list" id="nav_list">
        <li class="home clickStyle">
            <%--<a href="javascript:void(0)" name="home.html" class="iframeurl" title="">--%>
            <a href="javascript:void(0)" name="engineHome" class="iframeurl" title="">
                <i class="icon-home"></i>
                <span class="menu-text"> 系统首页 </span>
            </a>
        </li>
        <c:forEach items="${menuList}" var="menu" >
            <li class="clickStyleOpen">
                <a href="#" class="dropdown-toggle">
                    <i class="${menu.icon}"></i>
                    <span class="menu-text" >${menu.name}</span>
                    <b class="arrow icon-angle-down"></b>
                </a>
                <ul class="submenu">
                        <c:forEach items="${menu.menuList}" var="subMenu" >
                            <li class="home" style="margin-left: 15px;">
                                    <a href="javascript:void(0)" class="iframeurl" title="${subMenu.name}" name="${ctx}${subMenu.url}">
                                        <i class="${subMenu.icon}" style="display: block;"></i>
                                        <p>${subMenu.name}</p>
                                    </a>
                            </li>
                        </c:forEach>
                </ul>
            </li>
        </c:forEach>
    </ul>
    <div class="sidebar-collapse" id="sidebar-collapse">
        <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
    </div>
</div>