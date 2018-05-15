<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <script src="${ctx}/resources/js/common/customValid.js"></script>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js"></script>
    <script src="${ctx}/resources/assets/jstree/checkboxTree.js" type="text/javascript"></script>
    <script src="${ctx}/resources/js/systemMange/Dict_Ctrl.js${version}"></script>
    <title>字典管理</title>
    <style type="text/css">
        #menu-edit .add_menu tr{
            margin-bottom:10px;
        }
        #menu-edit .add_menu td{
            line-height:45px !important;
        }
       #Res_list thead th{
           color:#307ecc;
           font-size: 13px;
       }
    </style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
    <div id="treeMenu" style="width:15%;float: left;box-shadow: 2px 2px 2px #888;overflow-x: auto">
        <div style="height: 40px;line-height: 40px;width:100%">
            <div style="text-align: center;background: #0679CC;color: white;">
                字典管理
            </div>
        </div>
        <div style="max-height:400px;overflow-y: auto">
            <div id="jstree"></div>
        </div>
    </div>
   <div id="content" style="width: 85%;float: right;height: 100%;overflow: auto;">
        <div class="Res commonManager">
            <div class="table_res_list">
                <table style="text-align: center;" id="Res_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>字典Code</th>
                        <th>字典值</th>
                        <%--<th>是否目录</th>--%>
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
    <form name="form" id="myForm" method="post">
        <div id="Add_Dic_style" style="display: none">
            <div class="commonManager ">
                <div class="addCommon">
                    <ul class="clearfix">
                        <li>
                            <label class="label_name">父分类</label>
                            <select name="selectType" type="text" class="text_add" placeholder=""></select>
                        </li>
                        <li>
                            <label class="label_name">字典Code</label>
                            <label>
                                <input name="name_code" type="text" value="" class="text_add" id="nameCode" />
                            </label>
                        </li>
                        <li>
                            <label class="label_name">字典名称</label>
                            <label>
                                <input name="name_dict"  type="text" value="" class="text_add" />
                            </label>
                        </li>
                        <li>
                            <label class="label_name">是否分类</label>&#12288;&#12288;
                            <label>
                                <input name="isCatagory"  type="radio" value="Y" class="text_add"  id="isCatagory_Y"/>是&#12288;&#12288;
                            </label>
                            <label>
                                <input name="isCatagory"  type="radio" value="N" class="text_add"  id="isCatagory_N"/>否
                            </label>
                        </li>
                    </ul>
                    <div class="Remark" style="padding-top: 20px;">
                        <label class="label_name">备注</label>
                        <label>
                            <textarea name="remark"  cols="" rows="" style="width: 456px; height: 100px; padding: 5px;"></textarea>
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <input type="hidden" id="parentId">
        <input type="hidden" id="id">
        <input type="hidden" id="type">
        <input type="hidden" id="parent">
    </form>
</div>
</body>
<script>
    function getTree(){
        var layerIndex,jstree,currentNode;
        jstree = $('#jstree').jstree({
                "core" : {
                    "animation" : 0,
                    "check_callback" : true,
                    "themes" : { "stripes" : true },
                    'data' : function (obj, callback) {
                        var jsonstr="[]";
                        var jsonarray = eval('('+jsonstr+')');
                        Comm.ajaxGet("dict/getTree", null, function (result) {
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
                    "#" : {
                        "max_children" : 1,
                        "valid_children" : ["root"]
                    },
                    "root" : {
                        "icon" : "${ctx}/resources/3.3.3/assets/images/tree_icon.png",
                        "valid_children" : ["default"]
                    },
                    "default" : {
                        "valid_children" : ["default","file"]
                    },
                    "file" : {
                        "icon" : "glyphicon glyphicon-file",
                        "valid_children" : []
                    }
                },
                "plugins" : [ "contextmenu","search" ,"types"],
                "contextmenu":{
                    "items":{
                        "create":null,
                        "rename":null,
                        "remove":null,
                        "ccp":null,
                        "addDictType":{
                            "label":"&#12288;添&#12288;加&#12288;",
                            "_disabled":function(data){
                                var　selectData = getSelectItem(data);
                                return selectData.original.isCatagory=="N";
                            },
                            "action":function(data){
                                openDictEdit("add",getSelectItem(data).id);
                                $("#type").val(getSelectItem(data).type);
                                $("#parent").val(getSelectItem(data).parent);
                            }
                        },
                        "editDictType":{
                            "label":"&#12288;编&#12288;辑&#12288;",
                            "_disabled":function(data){
                                var　selectData = getSelectItem(data);
                                return selectData.parent=="#";
                            },
                            "action":function(data){
                                openDictEdit("edit",getSelectItem(data).id);
                            }
                        },
                        "delDictType":{
                            "separator_before":true,
                            "label":"&#12288;删&#12288;除&#12288;",
                            "_disabled":function(data){
                                var　selectData = getSelectItem(data);
                                return selectData.parent=="#";
                            },
                            "action":function(data){
                                deleteById(getSelectItem(data).id);
                            }
                        }
                    }}});
            function getSelectItem(data){
                var inst = $.jstree.reference(data.reference),
                        obj = inst.get_node(data.reference);
                currentNode = obj;
                return obj;
            }
            $('#jstree').bind("activate_node.jstree", function (obj, e) {
                var menuId = e.node.id;
                if(menuId){
                    queryList("parentId="+menuId,"dict/getByParentId");
                }
            });
            $('#jstree').bind("show_contextmenu.jstree", function (obj, e) {
                // 如果是顶级菜单， 只允许添加分类
                if(e.node.parent=="#"){
                    $(".vakata-contextmenu-disabled").hide();
                }
                if(e.node.original.isCatagory=="N"){
                    $(".vakata-contextmenu-disabled").hide();
                }

            });
            function openDictEdit(action,id){
                $("#id").val(id);
                if(action == "edit"){
                    update([0,id]);
                }else{
                    var dict = {};
                    dict.parentId = Number(id);
                    update(1,dict.parentId);
                }
            }
    }
    getTree();
</script>
</html>

