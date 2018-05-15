<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <link rel="stylesheet" href="${ctx}/resources/css/menu.css" />
    <script src="${ctx}/resources/js/common/customValid.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/checkboxTree.js${version}" type="text/javascript"></script>
    <script src="${ctx}/resources/js/systemMange/menu_List.js${version}"></script>
    <title>菜单管理</title>
    <style type="text/css">
        #menu-edit .add_menu tr{
            margin-bottom:10px;
        }
        #menu-edit .add_menu td{
            line-height:45px !important;
        }
        #select_type{
            padding-left: 37px;
            width: 114px;
            margin-left: 10px;
        }
        #editContent{
            padding:8px 78px 24px;
        }
    </style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
    <div id="treeMenu" style="width:15%;float: left;box-shadow: 2px 2px 2px #888;overflow-x: auto">
        <div style="height: 40px;line-height: 40px;width:100%">
            <div style="text-align: center;background: #0679CC;color: white;">
                菜单管理
            </div>
        </div>
        <div style="max-height:400px;overflow-y: auto">
            <div id="jstree"></div>
        </div>
        <div id="menu-edit" style="display:none">
            <form name="form" id="myForm" method="post">
                <div class="add_menu">
                    <div id="Adding_menu">
                        <div  id="editContent" class="page-content">
                            <table cellpadding="0" cellspacing="0" width="100%">
                                <input type="hidden" id="parentId" >
                                <tr>
                                    <td class="label_name">菜单名称</td>
                                    <td>
                                        <input name="name" type="text" class="addtext" value="" style="width: 300px" title="" ht-validate="{maxlength:100}" />
                                        <i style="color: #F60;">*</i>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label_name">是否显示</td>
                                    <td>
                                        <div class="radio ">
                                            <label>
                                                <input type="radio" class="ace" name="isShow" value="1" checked/>
                                                <span class="lbl">是</span>
                                            </label>
                                        </div>
                                        <div class="radio ">
                                            <label>
                                                <input type="radio" class="ace" name="isShow" value="0" />
                                                <span class="lbl">否</span>
                                            </label>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label_name">类型</td>
                                    <td>
                                        <select name="type" size="1" id="select_type">
                                            <option value="0" >菜单</option>
                                            <option value="1" >按钮</option>
                                        </select>
                                        <i style="color: #F60;">*</i>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label_name">URL地址</td>
                                    <td>
                                        <input name="url" title="" type="text" value="" style="width: 300px" ht-validate="{required:false}" />
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label_name">icon</td>
                                    <td>
                                        <input name="icon" title="" type="text"  value="" style="width: 200px" ht-validate="{string:false}" />
                                        <a href="http://www.bootcss.com/p/font-awesome/#icons-new" target="_blank" style="text-decoration: none;" class="dropdown-toggle">查看图标</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="label_name">描述</td>
                                    <td>
                                        <input name="remark" type="text" value="" style="width: 300px"  title=""/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div id="content" style="width: 85%;float: right;height: 100%;overflow: auto;">
        <div class="Res Manager_style">
            <div class="table_res_list">
                <table style="text-align: center;" id="Res_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>菜单ID</th>
                        <th>菜单名称</th>
                        <th>上级</th>
                        <th>上级菜单</th>
                        <th>菜单类型</th>
                        <th>是否显示</th>
                        <th>URL地址</th>
                        <th>创建日期</th>
                        <%--<th>最后更新日期</th>--%>
                        <th>描述</th>
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
<script>
    var layerIndex,jstree,currentNode,mid,pid,act,menuId;
    (function(){
        jstree = $('#jstree').jstree({
            "core" : {
                "animation" : 0,
                "check_callback" : true,
                "themes" : { "stripes" : true },
                'data' : function (obj, callback) {
                    var jsonstr="[]";
                    var jsonarray = eval('('+jsonstr+')');
                    Comm.ajaxGet("menu/getTree", null, function (result) {
                        var arrays= result.data;
                        for(var i=0 ; i<arrays.length; i++){
                            var arr = {
                                "id":arrays[i].id,
                                "parent":arrays[i].parent=="root"?"#":arrays[i].parent,
                                "text":arrays[i].text,
                                "icon":arrays[i].icon,
                                "type":arrays[i].type
                            }
                            jsonarray.push(arr);
                        }
                    },null,false);
                    callback.call(this, jsonarray);
                }
            },
            "types" : {
                "file" : {
                    "icon" : "glyphicon glyphicon-file",
                    "valid_children" : []
                }
            },
            "plugins" : [ "contextmenu","types" ],
            "contextmenu":{
                "items":{
                    "create":null,
                    "rename":null,
                    "remove":null,
                    "ccp":null,
                    "editMenu":{
                        "label":"编辑",
                        "action":function(data){
                            mid = getSelectItem(data).id;
                            openMenuEdit("edit",mid);
                        }
                    },
                    "addMenu":{
                        "label":"添加同级菜单",
                        "action":function(data){
                            pid = getSelectItem(data).parent;
                            openMenuEdit("add",pid);
                        }
                    },
                    "addSubMenu":{
                        "label":"添加子菜单",
                        /*"_disabled":function(data){
                            var　selectData = getSelectItem(data);
                            return selectData.original.type;
                        },*/
                        "action":function(data){
                            var　selectData = getSelectItem(data);
                            pid = selectData.id;
                            openMenuEdit("add",pid);
                        }
                    },
//                    "moveMenu":{
//                        "label":"移动菜单",
//                        "action":function(data){
//                            layer.msg("正在开发中......");
//                        }
//                    },
                    "delMenu":{
                        "separator_before":true,
                        "label":"删除菜单",
                        "action":function(data){
                            var param = new Array();
                            param.push(getSelectItem(data).id);
                            Comm.ajaxPost("menu/delete", JSON.stringify(param), function (result) {
                                layer.msg(result.msg,{time:1000},function(){
                                    $.jstree.reference("#jstree").refresh();
                                    queryList("parentId="+menuId,"menu/getByParentId");
                                });
                            },"application/json");
                        }
                    }
                }
            }
        });
        function getSelectItem(data){   //右键点击后的函数
            var inst = $.jstree.reference(data.reference),
                    obj = inst.get_node(data.reference);
            currentNode = obj;
            return obj;
        }
        $('#jstree').bind("activate_node.jstree", function (obj, e) {
            menuId = e.node.id;     //得到被点击节点的id
            if(menuId){
                queryList("parentId="+menuId,"menu/getByParentId");
            }
        });
        $('#jstree').bind("show_contextmenu.jstree", function (obj, e) {
            // 如果是按钮级别的菜单， 隐藏
            if(e.node.original.type){
                $(".vakata-contextmenu-disabled").hide();
            }
        });

        function openMenuEdit(action,id){
            act = action;
            var title = action == "edit"? "编辑":"添加";
            if(action == "edit"){
                Comm.ajaxPost("menu/detail", id, function (result) {
                    if (result.code==1) {
                        layer.msg(result.msg,{time:1000});
                        return;
                    }
                    var detailData = result.data;
                    $("input[name='name']").val(detailData.name);
                    var oRadio = document.getElementsByName("isShow");
                    for(var i=0;i<oRadio.length;i++) //循环
                    {
                        if(oRadio[i].value==detailData.isShow) //比较值
                        {
                            oRadio[i].checked=true; //修改选中状态
                            break; //停止循环
                        }
                    }
                    $("#select_type").val(detailData.type);
                    $("input[name='url']").val(detailData.url);
                    $("input[name='icon']").val(detailData.icon);
                    $("input[name='remark']").val(detailData.remark);
                },"application/json");
            }
            // 添加菜单
            if(action == "add"){
                $("#parentId").val(id);
                $("input[name='name']").val("");
                $("#select_type").val("");
                $("input[name='url']").val("");
                $("input[name='icon']").val("");
                $("input[name='remark']").val("");
            }
            layerIndex = layer.open({
                type : 1,
                title : title,
                shadeClose : false, //点击遮罩关闭层
                area : [ '550px', '350px' ],
                content : $('#menu-edit'),
                btn:["保存","取消"],
                yes:function () {
                    var menu = {};
                    var url = "";
                    if(act == "edit"){
                        url = "menu/edit";
                        menu.menuId = mid;
                    }else if(act == "add"){
                        url = "menu/add";
                        menu.parentId = pid;
                    }
                    menu.name = $("input[name='name']").val();
                    menu.url = $("input[name='url']").val();
                    menu.isShow = $("input[name='isShow']:checked").val();
                    menu.type = $("#select_type").val();
                    menu.icon = $("input[name='icon']").val();
                    menu.remark = $("input[name='remark']").val();
                    if((menu.type=="1"||menu.type==1) && (menu.url=="" || menu.url==null)){
                        layer.msg("类型为按钮，URL不能为空！",{time:1000});
                        return;
                    }
                    Comm.ajaxPost(url, JSON.stringify(menu), function (result) {
                        layer.msg(result.msg,{time:1000},function(){
                            $.jstree.reference("#jstree").refresh();
                            queryList("parentId="+menuId,"menu/getByParentId");
                        });
                        document.getElementById("myForm").reset();
                        layer.close(layerIndex);
                    },"application/json");
                }
            });
        }
    })();
</script>
</html>