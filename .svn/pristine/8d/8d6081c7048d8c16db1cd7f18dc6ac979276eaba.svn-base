<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <link rel="stylesheet" href="${ctx}/resources/css/dataMsg.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/atwho/jquery.atwho.css${version}"/>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
    <script src="${ctx}/resources/assets/atwho/jquery.atwho.js${version}"></script>
    <script src="${ctx}/resources/assets/atwho/jquery.caret.js${version}"></script>
    <script src="${ctx}/resources/js/common/customValid.js${version}"></script>
    <script src="${ctx}/resources/js/dataMange/field_validate.js${version}"></script>
    <script src="${ctx}/resources/js/dataMange/datamange.js${version}"></script>
    <title>通用字段</title>
    <style type="text/css">
        .c-popup-engine{
            height:265px;
            overflow:auto;
            overflow-x:hidden;
            border:1px solid #E6E6E6;
        }
        .c-popup-redact {
            width: 240px;
            line-height: 30px;
            margin: 0 0 0 15px;
            font-size: 14px;
            color: #777777;
            font-weight: 600;
            float: left;
        }
        .c-popup-redact a {
            float: right;
            font-size: 14px;
            color: #398dff;
            padding-right: 15px;
        }
        .c-popup-redact a:hover{
            text-decoration: none;
        }
        .c-popup-a{
            display:inline-block;
            margin-top:8px;
        }
        .c-popup-hint {
            width: 545px;
            height: 10px;
            line-height: 10px;
            margin: 10px 5px 0 385px;
            font-size: 12px;
            color: #999999;
        }

        .iconaddData,.iconclearData{
            width: 20px;
            height: 20px;
            display: inline-block;
            background: url(${ctx}/resources/images/blackadd.png) center center;
            background-size: cover;
            cursor: pointer;
            position: relative;
            top:4px;
        }
        .iconclearData{
            border:1px solid #FD6154;
            border-radius: 50%;
            background: url(${ctx}/resources/images/blackClear-enabled.png) center center;
            background-size: cover;
        }
        .c-popup-a{
            margin-top:0px!important;
        }
        .c-optional-rules-rules{
            margin-left: .5em;
        }
    </style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
    <div id="treeMenu" style="width:15%;float: left;box-shadow: 2px 2px 2px #888;overflow-x: auto">
        <div style="height: 40px;line-height: 40px;width:100%">
            <div style="text-align: center;background: #0679CC;color: white;">
                目录管理
            </div>
            <input type="hidden" id="hdNodeId" value="">
        </div>
        <div style="max-height:400px;overflow-y: auto">
            <div id="jstree"></div>
        </div>
        <%--新增弹出框--%>
        <div id="Add_data_style" style="display: none">
            <div class="commonManager ">
                <div class="addCommon clearfix c-field-middle">
                    <input type="hidden" class="hide" name="searchKey" value="" id="searchKey">
                    <input type="hidden" class="hide" name="fieldTypeId" value="" id="fieldTypeId">
                    <input type="hidden" name="id" value="" id="id">
                    <input type="hidden" name="formula" id="formula" />
                    <input type="hidden" name="formulaShow" value="" id="formulaShow" />
                    <input type="hidden" name="engineId" value="" id="engineId" />
                    <input type="hidden" name="formulaFields" value=""/>
                    <ul class="clearfix">
                        <li>
                            <label class="label_name">字段名称</label>
                            <label for="en_name">
                                <input name="en_name"  type="text"  id="en_name" placeholder="字段英文名"/>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">字段中文名</label>
                            <label for="cn_name">
                                <input name="cn_name"  type="text"  id="cn_name" placeholder="字段中文名"/>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">字段来源</label>
                            <label>
                                <select name="value_type" size="1" id="value_type">
                                </select>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">值类型</label>
                            <label>
                                <select name="valueType" onchange='setDiv()' size="1" id="valueType">
                                    <option value="1">数值型</option>
                                    <option value="2">字符型</option>
                                    <option value="3">枚举型</option>
                                    <!-- <option value="0">待选</option> -->
                                    <!-- <option value="4">小数型</option> -->
                                </select>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">衍生变量</label>
                            <select name="isDerivative" size="1"  class="select" id="isDerivative" onchange="setDerivative()">
                                <option value=1  >是</option>
                                <option value=0 selected = "selected">否</option>
                            </select>
                        </li>
                        <li>
                            <label class="label_name">输出</label>
                            <select name="isOutput" size="1"  class="select" id="isOutput">
                                <option value=1  >是</option>
                                <option value=0 selected = "selected">否</option>
                            </select>
                        </li>
                    </ul>
                    <div class="Remark" style="padding-top: 20px;">
                        <label class="label_name">字段范围</label>
                        <label style="padding-left: 0px">
                            <input name="restrainScope" type="text" id="restrainScope" onblur="setSelectList()" style="width: 430px;margin-right: 97px;" placeholder="数值型样例:(0,100);枚举型样例:男:0,女:1">
                        </label>
                    </div>
                    <input type="hidden" name="fieldContent" id="fieldContent"/>
                    <input type="hidden" name="formulaHidden" id="formulaHidden"/>
                    <input type="hidden" name="derType" id="derType"/>
                    <div id="derivedFields" style="display: none">
                        <div class="Remark" style="padding-top: 20px; height: 40px;width: 450px;">
                        <label class="label_name" style="color:#398DEF;" onclick="setTextDiv(1)" id="region" >条件区域</label><label class="label_name" onclick="setTextDiv(2)" id="formul">公式编辑</label>
                    </div>
                        <div class="Remark" style="width: 620px;" id="regionDiv" >
                        <div style="width: 540px; height: 10px"></div>
                            <div style="width: 540px;overflow: hidden" id="divRow1" name="divRow">
                                <div class="Remark" style="width: 160px;border-right:1px dashed #000;float:left" id="divRowLeft1">
                                    <label>
                                        <span class="iconaddData" onclick="addTermRulsLi(this,1)"></span>
                                        <span class="iconclearData" onclick="deleteTermRulsLi(this,1)"></span>
                                        <input type="text" name="conditionValue" style="width: 80px">
                                        <select name="conditionValue" size="1"  class="select" style="width: 80px;display: none">
                                        </select>
                                    </label>
                                </div>
                                <div class="Remark" style="width:460px;float:right;text-align:left;" id="divRowRight1">
                                    <label>
                                        <span class="iconaddData" onclick="addTermRulsLi(this,2,'divRowRight1')"></span>
                                        <span class="iconclearData" onclick="deleteTermRulsLi(this,2,'divRowRight1')"></span>
                                        <select name="fieldByUser" size="1"  class="select" onchange='setSelect(this)'  style="width: 80px">

                                        </select>
                                        <select name="fieldByUserWhere" size="1"  class="select" style="width: 80px">

                                        </select>
                                        <input type="text" style="width: 80px">
                                        <select  size=\"1\"  style='display: none;width: 80px' class=\"select\"> </select>
                                    </label>
                                </div>
                            </div>
                    </div>
                        <div class="Remark" style="width:100%;display: none;" id="formulaDiv">
                        <div style="width: 600px;padding-top: 5px;">
                            <ul style=" /* text-align: center; */width: 600px;margin: 0 auto;">
                                <li class="c-operational-character" onclick="character(this)" dataId="0" style="width: 15px;height:17px;background: url(${ctx}/resources/images/scorecard/1.png) center center;"></li>
                                <li class="c-operational-character" onclick="character(this)" dataId="1" style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/2.png) center center;"></li>
                                <li class="c-operational-character" onclick="character(this)" dataId="2" style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/3.png) center center;"></li>
                                <li class="c-operational-character" onclick="character(this)" dataId="3" style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/4.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="4" style="width: 31px;height:17px;background: url(${ctx}/resources/images/scorecard/5.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="5" style="width: 15px;height:17px;background: url(${ctx}/resources/images/scorecard/6.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="6" style="width: 25px; height:17px;background: url(${ctx}/resources/images/scorecard/7.png) center center;"></li>
                                <li class="c-operational-character" onclick="character(this)" dataId="7"  style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/8.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)"  dataId="8" style="width: 25px; height:17px;background: url(${ctx}/resources/images/scorecard/10.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)"  dataId="9" style="width: 32px; height:17px;background: url(${ctx}/resources/images/scorecard/11.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)"  dataId="10" style="width: 30px; height:17px;background: url(${ctx}/resources/images/scorecard/12.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="11" style="width: 13px;height:17px;background: url(${ctx}/resources/images/scorecard/13.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)"  dataId="12" style="width: 25px;height:17px;background: url(${ctx}/resources/images/scorecard/14.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="13" style="width: 27px;height:17px;background: url(${ctx}/resources/images/scorecard/15.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="14" style="width: 37px;height:17px;background: url(${ctx}/resources/images/scorecard/16.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="15" style="width: 10px;height:17px;background: url(${ctx}/resources/images/scorecard/17.png) center center;"></li>
                                <li style="width: 17px;height:17px;background: url(${ctx}/resources/images/scorecard/18.png) center center;"></li>
                                <li style="width: 2px;height:17px;background: url(${ctx}/resources/images/scorecard/19.png) center center;"></li>
                            </ul>
                            <div class="Remark" style="padding-top: 20px;" id="formulaEdit1">
                                <textarea class="inputor" onclick="setCursor('inputor')" id="inputor"  farr="" cols="" rows="" style="width: 600px; height: 130px; padding: 0px;" maxlength="1024"></textarea>
                            </div>
                            <div class="Remark" style="padding-top: 10px;" id="formulaEdit2">
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 编辑字段影响引擎提示框 -->
        <div id="dialog_engine" style="display: none">
            <img style="margin-right: 4em;width: 5%;position: absolute;right: 0px;margin-right: 1em; margin-top: 1em;" src="${ctx}/resources/images/prompt.png" />
            <div class ="c-popup-engine">
            </div>
        </div>
        <!-- 删除或停用字段影响引擎提示框 -->
        <div id="dialog_engine_stop" style="display: none">
            <img style="margin-right: 4em;width: 5%;position: absolute;right: 0px;margin-right: 1em; margin-top: 1em;" src="${ctx}/resources/images/prompt.png" />
            <div class ="c-popup-engine">
            </div>
        </div>
        <div id="menu-edit" style="display:none">
            <form name="form" id="myForm" method="post">
                <div class="add_menu">
                    <div id="Adding_menu">
                        <div  id="editContent" class="page-content">
                            <table cellpadding="0" cellspacing="0" width="100%">
                                <input type="hidden" id="hdParentId" >
                                <input type="hidden" id="hdFieldType" >
                                <input type="hidden" id="hdFieldTypeid" >
                                <tr>
                                    <td class="label_name">文件夹名称</td>
                                    <td>
                                        <input name="name" type="text" class="addtext" value=""  title="" ht-validate="{maxlength:100}" maxlength="100" />
                                        <i style="color: #F60;">*</i>
                                    </td>
                                </tr>
                                <tr>
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
                <form id="form1" name="form1" method="post" action="">
                    <div class="Manager_style add_user_info search_style" style="margin: 1em">
                        <div id="btn" style="display: none">
                        <ul class="search_content clearfix">
                                <button class="btn btn-primary addBtn" type="button" id="addBtn" onclick="addData(1)">新增字段</button>
                                <button class="btn btn-primary addBtn" type="button" id="export" onclick="exportData()">导出</button>
                                <button class="btn btn-primary addBtn" type="button" id="upload" onclick="leadLoad()">导入</button>
                            </ul>
                        </div>
                    </div>
                </form>
                <table style="text-align: center; cursor: pointer;" id="Res_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th></th>
                        <th>字段名称</th>
                        <th>字段中文名</th>
                        <th>字段来源</th>
                        <th>字段约束范围</th>
                        <th>是否外部数据</th>
                        <th>衍生变量</th>
                        <th>输出</th>
                        <th>状态</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%--导入选择文件--%>
    <div id="Lead_detail_style" style="display: none">
        <div class="commonManager ">
            <div class="addCommon clearfix">
                <ul class="clearfix">
                    <li>
                        <input type="text" placeholder="请选择本地文件" readonly id="fileName">
                    </li>
                    <li>
                        <div class="select-file">
                            <a class="a-upload ">请选择本地文件
                                <input id="file" type="file" name="file" onchange="handleFile()">
                            </a>
                            <div class="batch-right">支持Excel2010(文件大小不超过10M)</div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var layerIndex,jstree,currentNode,mid,fid,act;
    (function(){
        jstree = $('#jstree').jstree({
            "core" : {
                "animation" : 0,
                "check_callback" : true,
                "themes" : { "stripes" : true },
                'data' : function (obj, callback) {
                    //var jsonarray=new Array();
                    var jsonstr="[]";
                    var jsonarray = eval('('+jsonstr+')');
                    var arr;
                    arr = {
                        "id":"0",
                        "parent":"#",
                        "text":"全部字段",
                        "icon":"icon-folder-close"
                    };
                    jsonarray.push(arr);
                    Comm.ajaxGet("datamanage/field/listTree", null, function (result) {
                        var arrays= result.data.kArray;
                        //for(var i=0 ; i<arrays.length; i++){
                        for(var i=0 ; i<arrays.length; i++){
                            var txt="";
                            if (arrays[i].parentId=="0"){
                                txt="default";
                            }else
                            {
                                txt="file";
                            }
                            var arr = {
                                "id":arrays[i].id,
                                "parent":arrays[i].parentId=="0"?"0":arrays[i].parentId,
                                "text":arrays[i].fieldType,
                                "icon":"icon-folder-close",
                                "type":txt
                            }
                            jsonarray.push(arr);
                        }
                        $("#hdNodeId").val("");
                        $("#btn").hide();
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
                "items": {
                    "create": null,
                    "rename": null,
                    "remove": null,
                    "ccp": null,
                    "addSubMenu": {
                        "label": "新建节点",
                        "action": function (data) {
                            var selectData = getSelectItem(data);
                            //pid = selectData.id;
                            openMenuEdit("son", selectData);
                        }
                    },
                    "editMenu": {
                        "label": "编辑节点",
                        "_disabled":function(data){
                            var selectData = getSelectItem(data);
                            if(selectData.id=="0" ){
                                return true;
                            }else return false;
                        },
                        "action": function (data) {
                            var selectData = getSelectItem(data);
                            //mid = getSelectItem(data).id;
                            console.log(selectData.id);
                            openMenuEdit("edit", selectData);
                        }
                    },
                    "deleteMenu": {
                        "label": "删除节点",
                        "_disabled":function(data){
                            var selectData = getSelectItem(data);
                            if(selectData.id=="0" ){
                                return true;
                            }else return false;
                        },
                        "action": function (data) {
                            var selectData = getSelectItem(data);
                            //mid = getSelectItem(data).id;
                            console.log(selectData.id);
                            deleteMenuEdit(selectData.id);
                        }
                    },
                   /* "addMenu": {
                        "label": "添加同级节点",
                        "_disabled":function(data){
                            var selectData = getSelectItem(data);
                            if(selectData.id=="0"){
                                return true;
                            }else return false;
                        },
                        "action": function (data) {
                            var selectData = getSelectItem(data);
                            //pid = getSelectItem(data).parent;
                            openMenuEdit("add", selectData);
                        }
                    },*/
                }
            }
        });
        function getSelectItem(data){   //右键点击后的函数
            var inst = $.jstree.reference(data.reference),
                    obj = inst.get_node(data.reference);
            currentNode = obj;
            return obj;
            console.log(obj);
        }
        $('#jstree').bind("activate_node.jstree", function (obj, e) {
            var menuId = e.node.id;//得到被点击节点的id
            $("#hdNodeId").val(menuId);//记录被点击节点的id
            $("#btn").show();
            $("#addBtn").show();
            if(menuId){
                //queryList("parentId="+menuId,"menu/getByParentId");
                queryList(menuId,"datamanage/field/list");
            }
            if(menuId=="0"){
                queryList();
            }

        });
        $('#jstree').bind("show_contextmenu.jstree", function (obj, e) {
            // 如果是按钮级别的菜单， 隐藏
            if(e.node.original.type){
                $(".vakata-contextmenu-disabled").hide();
            }
            if(e.node.id == "0"){
                $(".vakata-contextmenu-disabled ").hide();
            }
        });
        //删除节点
        function deleteMenuEdit(id) {
            layer.confirm("确定删除该节点？", {btn:['确定','取消']},function () {
                var paramFilter = {};
                var param = {};
                param.id=id;
                paramFilter.param=param;
                Comm.ajaxPost("datamanage/field/deleteTree", JSON.stringify(paramFilter), function (result) {
                    layer.msg(result.data.msg,{time:1000},function(){
                        $.jstree.reference("#jstree").refresh();
                    });
                    document.getElementById("myForm").reset();
                    layer.close(layerIndex);
                    queryList(id,"datamanage/field/list");
                },"application/json");
            });
        }
        function openMenuEdit(action,id){
            act = action;
            var title = action == "edit"? "编辑":"添加";
            $("input[name='name']").val("");
            if(action == "edit"){
                //var detailData = result.data;
                $("input[name='name']").val(id.text);
                $("#hdFieldTypeid").val(id.id);
                $("#hdParentId").val(id.parent);
            }
            // 添加同级菜单
            if(action == "add"){
                $("#hdParentId").val(id.parent);
            }
            //添加子级文件夹
            if(action=="son"){
                $("#hdParentId").val(id.id);
            }
            layerIndex = layer.open({
                type : 1,
                title : title,
                shadeClose : true, //点击遮罩关闭层
                area : [ '290px', '140px' ],
                content : $('#menu-edit'),
                btn:["保存","取消"],
                yes:function(){
                    var paramFilter = {};
                    var param = {};
                    var url = "";
                    if(act == "edit"){
                        url = "datamanage/field/updateTree";
                        param.id = $("#hdFieldTypeid").val()=="#"?"0":$("#hdFieldTypeid").val();
                        param.parentId = $("#hdParentId").val()=="#"?"0":$("#hdParentId").val();
                    }else if(act == "add"||act == "son"){
                        url = "datamanage/field/addTree";
                        param.parentId = $("#hdParentId").val()=="#"?"0":$("#hdParentId").val();

                    }
                    param.fieldType = $("input[name='name']").val();
                    if(param.fieldType==""){
                        layer.alert("文件夹名不能为空！");
                    }else{
                        paramFilter.param=param;
                        Comm.ajaxPost(url, JSON.stringify(paramFilter), function (result) {
                            layer.msg(result.data.msg,{time:1000},function(){
                                $.jstree.reference("#jstree").refresh();
                            });
                            document.getElementById("myForm").reset();
                            layer.close(layerIndex);
                        },"application/json");
                    }
                }
            });
        }
    })();

</script>
</html>