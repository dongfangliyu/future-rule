var layerIndex,jstree,currentNode,act,jsTreeCheckBox;
var flag;//区别是两个树的查询;
$(function(){
    //树的显示与隐藏
    var click=true;
    $("#otherInfoTab").click(function(){
        if(click==true){
            $("#jstree").hide();
            click=false;
        }else if(click==false){
            $("#jstree").show();
            click=true;
        }
    });
    //遍历树
    jstree = $('#jstree').jstree({
        "core" : {
            "animation" : 0,
            "check_callback" : true,
            "themes" : { "stripes" : true },
            'data' : function (obj, callback) {
                var array = new Array();
                var arr;
                var arr1;
                arr = {
                    "id":"0",
                    "parent":"#",
                    "text":"全部字段",
                    "icon":"icon-folder-close"
                };
                arr1={
                    "id":"rec",
                    "parent":"#",
                    "text":"回收站",
                    "icon":"icon-folder-close"
                };
                array.push(arr);
                // array.push(arr1);
                var engineId = $("#engineId").val();
                var status = "1";
                var param={
                    engineId:engineId,
                    status:status
                };
                Comm.ajaxGet("datamanage/field/listTree",param , function (result) {
                    var arrays= result.data.kArray;
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
                            "isCommon":arrays[i].isCommon,
                            "icon":"icon-folder-close",
                            "type":txt
                        };
                        array.push(arr);
                    }
                },null,false);
                callback.call(this, array);
            }
        },
        "types" : {
            "file" : {
                "icon" : "glyphicon glyphicon-file",
                "valid_children" : []
            }
        },
        "plugins" : [ "contextmenu","types" ],
        "contextmenu":
        {
            "items": {
                "create": null,
                "rename": null,
                "remove": null,
                "ccp": null,
                "editMenu": {
                    "label": "修改节点",
                    "_disabled":function(data){
                        var selectData = getSelectItem(data);
                        if(selectData.id=="0" ){
                            return true;
                        }else return false;
                    },
                    "action": function (data) {
                        var selectData = getSelectItem(data);
                        openMenuEdit("edit", selectData);
                    }
                },
                "deleteMenu": {
                    "label": "删除节点",
                    "_disabled":function(data){
                        var selectData = getSelectItem(data);
                        if(selectData.id=="0"){
                            return true;
                        }else return false;
                    },
                    "action": function (data) {
                        var selectData = getSelectItem(data);
                        jsTreeRemove(selectData);
                    }
                },
                "addSubMenu": {
                    "label": "添加节点",
                    "action": function (data) {
                        var selectData = getSelectItem(data);
                        openMenuEdit("son", selectData);
                    }
                },
                "ComMenu": {
                    "label": "添加通用字段",
                    "_disabled":function(data){
                        var selectData = getSelectItem(data);
                        if(selectData.id=="0"){
                            return false;
                        }else return true;
                    },
                    "action": function (data) {
                        $("#checkBox_btn").show();
                        $("#jsTreeCheckBox").show();
                        $("#jstree").hide();
                        showRuleCheckBox();
                    }
                }
            }
        }
    });
    //右键点击后的函数
    function getSelectItem(data){
        var inst = $.jstree.reference(data.reference),
            obj = inst.get_node(data.reference);
        currentNode = obj;
        return obj;
    }
    //显示隐藏按钮,加载右边列表数据
    $('#jstree').bind("activate_node.jstree", function (obj, e) {
        var engineId = $("#engineId").val();
        var menuId = e.node.id;     //得到被点击节点的id
        var isCommon = e.node.original.isCommon;//判断是不是外部字段
        $("#fieldTypeId").val(menuId);
        $("#hdNodeId").val(menuId);//记录被点击节点的id
        flag=1;
        if(menuId){
            if(menuId == "rec"){
                $("#addBtn").hide();
                $("#deleteBtn").hide();
                $("#assignBtn").hide();
            } else if(e.node.original.isCommon == "1"){
                $("#addBtn").hide();
                $("#deleteBtn").show();
                $("#assignBtn").show();
            }else if(menuId == "0"){
                $("#addBtn").show();
                $("#deleteBtn").show();
                $("#assignBtn").show();
            }else{
                $("#addBtn").show();
                $("#deleteBtn").show();
                $("#assignBtn").show();
            }
            queryList(menuId,isCommon,flag);
        }
    });
    //根据不同状态，显示有点菜单
    $('#jstree').bind("show_contextmenu.jstree", function (obj, e) {
        // 如果是按钮级别的菜单， 隐藏
        if(e.node.original.type){
            $(".vakata-contextmenu-disabled").hide();
        }
        //如果是外部字段和回收站隐藏按钮
        if(e.node.original.isCommon == "1" ||e.node.id == "rec" ){
            $(".vakata-context ").hide();
        }
        //如果是全部字段，则只让他显示添加节点和添加通用字段
        if(e.node.id == "0"){
            $(".vakata-contextmenu-disabled ").hide();
        }
    });
    //打开菜单编辑页面
    function openMenuEdit(action,id){
        act = action;
        var title = action == "edit"? "编辑":"添加";
        $("input[name='name']").val("");
        if(action == "edit"){
            $("input[name='name']").val(id.text);
            $("#hdFieldTypeid").val(id.id);
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
            yes:function () {
                //保存添加树节点，修改文件夹操作
                var paramFilter = {};
                var param = {};
                var url = "";
                param.engineId =$("#engineId").val();
                if(act == "edit"){
                    url = "datamanage/field/updateTree";
                    param.id = $("#hdFieldTypeid").val()=="#"?"0":$("#hdFieldTypeid").val();
                }else if(act == "son"){
                    url = "datamanage/field/addTree";
                    param.parentId = $("#hdParentId").val()== "0"?"0":$("#hdParentId").val();
                }
                param.fieldType = $("input[name='name']").val();
                if(param.fieldType==""){
                    layer.alert("文件夹名不能为空！");
                }else{
                    paramFilter.param=param;
                    Comm.ajaxPost(url, JSON.stringify(paramFilter), function (result) {
                        layer.msg(result.data.msg,{icon:1,offset:'200px',time:1000},function(){
                            $.jstree.reference("#jstree").refresh();
                        });
                        document.getElementById("myForm").reset();
                        layer.close(layerIndex);
                    },"application/json");
                }
            }
        });
    }
});
/**删除树节点**/
function jsTreeRemove(selectData) {
    var nodeId = selectData.id;
    var param = {};
    var engineId = $("#engineId").val();
    param.engineId = engineId;
    param.pid = nodeId;
    Comm.ajaxPost('engineManage/fieldmapping/checkFieldType',JSON.stringify(param),
        function(data){
            var resData = data.data;
            if(data.beUsed){
                var hasCheck = false;
                //通用字段和引擎字段
                var strField = "";
                for (var i = 0; i < resData.fieldList.length; i++) {
                    strField +='<div class="c-popup-redact" style="width:500px;">';
                    if(resData.fieldList[i].engineId==null){
                        strField += '数据管理-类型:';
                    }else{
                        strField += '引擎管理-类型:';
                    }
                    strField += resData.fieldList[i].fieldType + '-名称:' + resData.fieldList[i].fieldCn +''+
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+resData.fieldList[i].engineId+'" onClick="fieldGo(this)" >点击修改</a>'+
                        '</div>';
                }
                if(strField!=""){
                    $(".c-popup-engine").append(strField);
                    hasCheck = true;
                }
                //数据库管理-黑白名单库
                var strBWListDb = "";
                for (var i = 0; i < resData.listDbList.length; i++) {
                    strBWListDb +='<div class="c-popup-redact" style="width:500px;">';
                    if(resData.listDbList[i].listType=='b')
                        strBWListDb += '数据库管理-黑名单名称:';
                    else
                        strBWListDb += '数据库管理-白名单名称:';
                    strBWListDb += resData.listDbList[i].listName +
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" dataid="'+resData.listDbList[i].id+'" listType="'+resData.listDbList[i].listType+'" onClick="bwListGo(this)">点击修改</a>'+
                        '</div>';
                }
                if(strBWListDb!=""){
                    $(".c-popup-engine").append(strBWListDb);
                    hasCheck = true;
                }
                //规则管理（包含引擎管理知识库-规则管理）
                var strRule = "";
                for (var i = 0; i < resData.ruleList.length; i++) {
                    strRule +='<div class="c-popup-redact" style="width:500px;">';
                    if (resData.ruleList[i].engineId==null)
                        strRule += '规则管理-规则集-名称：';
                    else
                        strRule += '引擎管理-知识库-规则集-名称：';
                    strRule += resData.ruleList[i].name +
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+resData.ruleList[i].engineId+'" onClick="ruleManageGo(this)" >点击修改</a>'+
                        '</div>';
                }
                if(strRule!=""){
                    $(".c-popup-engine").append(strRule);
                    hasCheck = true;
                }
                //引擎管理知识库-评分卡
                var strScorecard = "";
                for (var i = 0; i < resData.scorecardList.length; i++) {
                    strScorecard +='<div class="c-popup-redact" style="width:500px;">'+
                        '引擎管理-知识库-评分卡-名称:' + data.scorecardList[i].name +
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+resData.scorecardList[i].engineId+'" onClick="ruleManageGo(this)" >点击修改</a>'+
                        '</div>';
                }
                if(strScorecard!=""){
                    $(".c-popup-engine").append(strScorecard);
                    hasCheck = true;
                }
                //引擎管理决策流-黑(白)名单
                var strNodeListDb = "";
                for (var i = 0; i < resData.nodelistDbList.length; i++) {
                    strNodeListDb +='<div class="c-popup-redact" style="width:500px;">'+
                        '引擎管理-决策流:' + resData.nodelistDbList[i].engineName + '-黑(白)名单节点:' + resData.nodelistDbList[i].nodeName +
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+resData.nodelistDbList[i].engineId+'" onClick="decisionFlowGo(this)" >点击修改</a>'+
                        '</div>';
                }
                if(strNodeListDb!=""){
                    $(".c-popup-engine").append(strNodeListDb);
                    hasCheck = true;
                }
                //最后判断打开对话框
                if(hasCheck){
                    $(".c_dialog").show();
                }
            }else{
                //执行删除节点及子节点
                var paramdete = {};
                paramdete.engineId = engineId;
                paramdete.fieldIds = resData.fieldIds;
                paramdete.fieldTypeIds = resData.fieldTypeIds;
                Comm.ajaxPost('engineManage/fieldmapping/deleteNode',JSON.stringify(paramdete),function(data){
                    layer.msg(data.msg,{time:1000},function(){
                        layer.close(layerIndex);
                        $.jstree.reference("#jstree").refresh();
                        document.getElementById("myForm").reset();
                    })
                },"application/json");
            }
        }, "application/json");
}
/**显示添加通用字段页面**/
function showRuleCheckBox() {
    $('#jsTreeCheckBox').data('jstree', false).empty();//清空实例，jstree重新生成
    jsTreeCheckBox = $('#jsTreeCheckBox').jstree({
        "core" : {
            "animation" : 0,
            "check_callback" : true,
            "themes" : { "stripes" : true },
            'data' : function (obj, callback) {
                var array = new Array();
                Comm.ajaxGet("datamanage/field/listTree", null, function (result) {
                    $("#btn").hide();//隐藏按钮
                    var arrays= result.data.kArray;
                    for(var i=0 ; i<arrays.length; i++){
                        var arr = {
                            "id":arrays[i].id ,
                            "parent":arrays[i].parentId=="0"?"#":arrays[i].parentId,
                            "text":arrays[i].fieldType,
                            "icon":false
                        };
                        array.push(arr);
                    }
                },null,false);
                callback.call(this, array);
            }
        },
        "checkbox" : {
            "keep_selected_style" : false,
            "real_checkboxes" : true,
            "three_state":false //父子节点选择互不影响
        },
        "plugins" : [ "checkbox" ]
    }).bind('loaded.jstree', function(obj, e){
        var engineId = $("#engineId").val();
        var paramData = {engineId:engineId,"isCommon":0};
        Comm.ajaxPost("engineManage/fieldmapping/getNodes",JSON.stringify(paramData),function(result){
            var ids = new Array();
            if(result.data.selNodes!=null){
                ids = result.data.selNodes.split(",");
            }
            var inst = e.instance;
            for(var i=0; i<ids.length; i++){
                var obj = inst.get_node(ids[i]);
                inst.select_node(obj);
            }
        },"application/json");
    });
    $("#checkBox_cancel").on("click",function () {
        $("#checkBox_btn").hide();
        $("#jsTreeCheckBox").hide();
        $("#jstree").show();
    });
    //点击组织通用树显示右边列表数据
    $('#jsTreeCheckBox').bind("activate_node.jstree", function (obj, e) {
        var no = e.node;//得到被点击的节点对象
        var id = no.id;//得到被点击的节点id
        var inst = e.instance;
        if(id){
            var ids=$("#jsTreeCheckBox").jstree("get_checked");//取得所有选中的节点id
            parentIds = ids.join(",");
            var no_parents = no.parents;//该节点对象的父节点id
            var no_child = no.children_d;//该节点对象的所有子节点id
            if(no.state.selected == true){
                inst.select_node(no_parents);
            }else {
                if(no_child.length>0){
                    for(var i=0;i<no_child.length;i++){
                        for(var j=0;j<ids.length;j++){
                            if(no_child[i]==ids[j]){
                                inst.uncheck_node(no_child[i]);
                            }
                        }
                    }
                }
            }
        }
        flag =0;//树标志
        var isCommon = 1;//外部字段
         var menuId = e.node.id;     //得到被点击节点的id
        queryList(menuId,isCommon,flag);
    });
    //点击确定按钮，添加通用字段
    $("#checkBox_sure").on("click",function () {
        //取得所有选中的节点id，返回数组
        var ids=$("#jsTreeCheckBox").jstree("get_checked");
        var nodes = $("#jsTreeCheckBox").jstree().get_checked(true);//得到被点击节点对象，数组
        for(var i=0; i<nodes.length;i++){
            var node_parents = nodes[i].parents;//得到被点击节点的父节点id，数组
            for(var j=0; j<node_parents.length;j++){
                if(node_parents[j].indexOf("#")<0){
                    if($.inArray(node_parents[j],ids)<0){
                        ids.push(node_parents[j]);
                    }
                }
            }
        }
        if(ids.length==0){
            layer.msg("请选择通用字段");
            return;
        }
        ids = ids.join(",");//以","隔开
        var param = {
            fieldTypeIds:ids,
            engineId:$("#engineId").val(),
            status:1
        };
        //添加通用字段
        Comm.ajaxPost("engineManage/fieldmapping/copyField", JSON.stringify(param), function (result) {
            layer.msg(result.msg,{icon:1,time:1000},function(){
                $("#checkBox_btn").hide();
                $("#jsTreeCheckBox").hide();
                $("#jstree").show();
                $("#btn").show();
                $.jstree.reference("#jstree").refresh();
            });
        },"application/json");

    });
}