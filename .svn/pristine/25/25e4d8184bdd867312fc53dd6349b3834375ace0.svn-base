/***初始化树***/
$(function (){
    jstree = $('#jstree').jstree({
        "core" : {
            "animation" : 0,
            "check_callback" : true,
            "themes" : { "stripes" : true },
            'data' : function (obj, callback) {
                var array = new Array(), treeType,type,arr,tt;
                if(engineId!=null && engineId !='') tt = 2;
                else tt=1;
                arr = {
                    "id":"rule_root",
                    "parent":"#",
                    "text":"规则集",
                    "icon":"icon-folder-open-alt",
                    "data":{
                        n_treeType:"0",
                        n_type:tt
                    }
                };
                array.push(arr);
                if(engineId!=null && engineId !=''){
                    arr = {
                        "id":"scorecard_root",
                        "parent":"#",
                        "text":"评分卡",
                        "icon":"icon-folder-open-alt",
                        "data":{
                            n_treeType:"1",
                            n_type:2
                        }
                    };
                    array.push(arr);
                    type = 2;treeType="0,1,2,3";//树分类treeType(0：规则集树，1：评分卡树， 2：规则集回收站，3：评分卡回收站)
                }else{
                    type = 1;treeType="0,2";
                }
                var param={treeType:treeType, engineId:engineId, type:type};
                Comm.ajaxGet("knowledge/tree/tree", param, function (result) {
                    var arrays= result.data;parentIds="0,";var icon = "";
                    for(var i=0 ; i<arrays.length; i++){
                        if(arrays[i].text == "回收站")icon = "icon-folder-open";
                        else icon = "icon-folder-open-alt";
                        if(arrays[i].text == "回收站" && arrays[i].type == 1){
                            trash1 = arrays[i].id;//无引擎规则集的回收站id
                        }
                        if(arrays[i].text == "回收站" && arrays[i].type == 2 && arrays[i].treeType == "2"){
                            trash2 = arrays[i].id;//规则集的回收站id
                        }
                        if(arrays[i].text == "回收站" && arrays[i].type == 2 && arrays[i].treeType == "3"){
                            trash3 = arrays[i].id;//评分卡的回收站id
                        }
                        var root;
                        if(arrays[i].type == 2 && (arrays[i].treeType == "1" || arrays[i].treeType == "3")){
                            root = "scorecard_root";
                        }else{
                            root = "rule_root";
                        }
                        arr = {
                            "id":arrays[i].id ,
                            "parent":arrays[i].parent=="0"?root:arrays[i].parent,
                            "text":arrays[i].text,
                            "icon":icon,
                            "data":{
                                n_treeType:arrays[i].treeType,
                                n_type:arrays[i].type
                            }
                        };
                        array.push(arr);
                        parentIds+=arrays[i].id+",";
                    }
                    parentIds=parentIds.substring(0,parentIds.length-1);
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
        "plugins" : [ "contextmenu","types"],
        "contextmenu":{ items: context_menu}
    }).on('loaded.jstree', function(e, data) {
        $('#jstree').jstree().open_node("rule_root");
    });
    $('#jstree').bind("activate_node.jstree", function (obj, e) {
        engineIdFlag = true;
        recFlag = false;
        var treeType = e.node.data.n_treeType;//树形分类
        if(treeType=="1" || treeType == "3"){//评分卡相关
            treeTypeFlag = true;
        }else treeTypeFlag = false;
        var id = e.node.id;//得到被点击节点的id
        parentId = id;//规则集or评分卡添加时用
        parentIds = e.node.children_d;//得到被点击节点下所有子节点的id,数组
        if(id){
            if(id.indexOf("root")<0){//不是根节点
                $('#addBtn').show();
                $('#deleteBtn').show();
                $('#resetBtn').show();
                $('#assignBtn').show();
                if(e.node.text=="回收站"){
                    recFlag = true;
                    $('#addBtn').hide();
                    $('#deleteBtn').hide();
                }
                var parents = e.node.parents;//当前点击节点的所有父节点id
                for(var i=0; i<parents.length;i++){
                    var no = $('#jstree').jstree("get_node", parents[i]);//根据节点id得到节点对象
                    if(no.text=="回收站"){
                        recFlag = true;
                        $('#addBtn').hide();
                        $('#deleteBtn').hide();
                    }
                }
                //引擎管理里添加的通用规则不可添加删除，可启停用
                if(e.node.data.n_treeType=="0" && e.node.data.n_type==1 && engineId !=null && engineId !=""){
                    engineIdFlag = false;
                    $('#addBtn').hide();
                    $('#deleteBtn').hide();
                }
            }else {
                $('#addBtn').hide();
                $('#deleteBtn').hide();
                $('#resetBtn').hide();
                $('#assignBtn').hide();
            }
            if(id.indexOf("root")>=0)id="0";
            if($.inArray(id,parentIds)<0){
                parentIds.push(id);//子节点数组再加上本身的id
            }
            parentIds = parentIds.join(",");//数组转字符串
            if(treeType=="1" || treeType == "3" || id == "scorecard_root"){
                $("#upload").hide();
                $("#export").hide();
                queryList("knowledge/scorecard/list",true);//评分卡列表
            }else {
                $("#upload").show();
                $("#export").show();
                queryList("knowledge/rule/list",true);//规则集列表
            }
        }
    });
    $('#jstree').bind("rename_node.jstree", function (obj, e) {
        var type;
        if(engineId!=null && engineId !='')type = 2;
        else type = 1;
        if(e.node.text=="回收站" || e.node.text=="规则集" || e.node.text=="评分卡"){
            layer.msg("该名称不能使用",{icon:1,offset:'200px',time:1000});
        }else{
              var nodes = {
                  "id": e.node.id.indexOf("root") > 0 ? "0" : e.node.id,
                  "parentId": e.node.parent.indexOf("root") > 0 ? "0" : e.node.parent,
                  "type":type,
                  "engineId":engineId,
                  "name": e.node.text,
                  "status":1
              };
            Comm.ajaxPost("knowledge/tree/update", JSON.stringify(nodes), function (result) {
                layer.msg(result.msg,{icon:1,offset:'200px',time:1000});
            },"application/json");
        }
    });
});
function context_menu(node){
    var tree = $('#jstree').jstree(true);
    /*var ids = node.children;
    var nid;
    for(var i=0; i<ids.length; i++){
        var no = $('#jstree').jstree("get_node", ids[i]);//根据节点id得到节点对象
        if(no.data.n_treeType=="0" && no.data.n_type==1){
            var nid = no.children_d;
            if($.inArray(ids[i],nid)<0){
                nid.push(ids[i]);
            }
        }
    }*/
    var items = {
        "Create": {
            "separator_before": false,
            "separator_after": false,
            "label": "新建节点",
            "action": function (obj) {
                saveData(tree,"add",node);
            }
        },
        "Rename": {
            "separator_before": false,
            "separator_after": false,
            "label": "编辑节点",
            "action": function (obj) {
                tree.edit(node);
            }
        },
        "Remove": {
            "separator_before": true,
            "separator_after": false,
            "label": "删除节点",
            "action": function (obj) {
                saveData(tree,"delete",node);
            }
        },
        "addRule": {
            "separator_before": true,
            "separator_after": false,
            "label": "添加通用规则",
            "action": function (obj) {
                $("#checkBox_btn").show();
                $("#jsTreeCheckBox").show();
                $("#jstree").hide();
                showRuleCheckBox();
            }
        }
    };
    var parents = node.parents;//当前右键点击节点的所有父节点id
    for(var i=0; i<parents.length;i++){
        var no = $('#jstree').jstree("get_node", parents[i]);//根据节点id得到节点对象
        if(no.text=="回收站"){
            delete items.Create;
            delete items.Rename;
            delete items.Remove;
            delete items.addRule;
        }
    }
    if(node.id.indexOf("root")>=0){
        delete items.Rename;
        delete items.Remove;
    }
    if(engineId==null || engineId=="" || node.id.indexOf("rule_root")<0){
        delete items.addRule;
    }
    if(node.data.n_treeType=="0" && node.data.n_type==1 && engineId !=null && engineId !="" && node.id.indexOf("root")<0 || node.text=="回收站"){
        delete items.Create;
        delete items.Rename;
        delete items.Remove;
        delete items.addRule;
    }
    return items;
}
function saveData(tree,str,node){
    var treeType = node.data.n_treeType;
    var type = node.data.n_type;
    var trash;
    if(treeType=="0")trash = trash2;
    if(treeType=="1")trash = trash3;
    if(type=="1")trash = trash1;
    var url="",nodes={};
    if(str=="add"){
        url="knowledge/tree/save";
        nodes ={
            "name": "新建节点",
            "parentId":node.id.indexOf("root") > 0 ? "0":node.id,
            "treeType":treeType,
            "type":type,
            "engineId":engineId
        };
        Comm.ajaxPost(url, JSON.stringify(nodes), function (result) {
            var data = result.data;
            layer.msg(result.msg,{icon:1,offset:'200px',time:1000},function(){
                if(str=="add") {
                    tree.create_node(node, {"icon":"icon-folder-open-alt","text":data.name,"id":data.id,"parent":data.parentId,"data":{"n_treeType":data.treeType,"n_type":data.type}});
                }
                if(str=="delete"){
                    tree.delete_node(node);
                    queryList("knowledge/rule/list",true);
                }
            });
        },"application/json");
    }
    if(str=="delete") {
        var ids;
        if(node.id.indexOf("root") < 0){
            ids = node.children_d;//自身节点及其所有子节点
            ids = ids.join(",");
        }
        layer.confirm("确定删除该节点？", {btn:['确定','取消']},function () {
            url = "knowledge/tree/delete";
            nodes = {
                "id": node.id.indexOf("root") >= 0 ? "0" : node.id,
                "ids":ids,
                "parentId": trash,
                "treeType": treeType,
                "type":type,
                "engineId": engineId,
                "status": -1
            };
            Comm.ajaxPost(url, JSON.stringify(nodes), function (result) {
                var data = result.data;
                layer.msg(result.msg,{icon:1,offset:'200px',time:1000},function(){
                    if(str=="add") {
                        tree.create_node(node, {"icon":"icon-folder-open-alt","text":data.name,"id":data.id,"parent":data.parentId,"data":{"n_treeType":data.treeType,"n_type":data.type}});
                    }
                    if(str=="delete"){
                        tree.delete_node(node);
                        queryList("knowledge/rule/list",true);
                    }
                });
            },"application/json");
        });

    }

}
/**展现通用规则集***/
function showRuleCheckBox() {
    //这个是关键，如果不清空实例，jstree不会重新生成
    $('#jsTreeCheckBox').data('jstree', false).empty();
    jsTreeCheckBox = $('#jsTreeCheckBox').jstree({
        "core" : {
            "animation" : 0,
            "check_callback" : true,
            "themes" : { "stripes" : true },
            'data' : function (obj, callback) {
                var array = new Array();
                var param={treeType:"0", type:1, status:1};
                Comm.ajaxGet("knowledge/tree/tree", param, function (result) {
                    var arrays= result.data;
                    for(var i=0 ; i<arrays.length; i++){
                        var arr = {
                            "id":arrays[i].id ,
                            "parent":arrays[i].parent=="0"?"#":arrays[i].parent,
                            "text":arrays[i].text,
                            "icon":false
                        };
                        array.push(arr);
                    }
                },null,false);
                callback.call(this, array);
            }
        },
        "callback ":{onsearch : function (n,t) {
            t.container.find('.search').removeClass('search');
            n.addClass('search');
        }},
        "checkbox" : {
            "keep_selected_style" : false,//是否默认选中
            "real_checkboxes" : false,
            "three_state":false //父子节点选择互不影响
        },
        "plugins" : [ "checkbox" ],
    }).bind('loaded.jstree', function(obj, e){
        $('#jsTreeCheckBox').jstree().open_all();
        var paramD = {engineId:engineId};
        Comm.ajaxPost("knowledge/tree/getTreeIds",JSON.stringify(paramD),function(result){
            var ids = result.data.idList;
            if(ids.length>0){
                var inst = e.instance;
                for(var i=0; i<ids.length; i++){
                    var obj = inst.get_node(ids[i]);
                    inst.select_node(obj);
                }
                parentIds = ids.join(",");
            }
            engineIdFlag = false;
            queryList("knowledge/rule/list",false);
        },"application/json");
    });
    $('#jsTreeCheckBox').bind("activate_node.jstree", function (obj, e) {
        engineIdFlag = false;
        var no = e.node,//得到被点击的节点对象
        id = no.id,//得到被点击的节点id
        inst = e.instance;//a.jstree.plugins.checkbox
        if(id){
            $('#addBtn').hide();
            $('#deleteBtn').hide();
            var ids=$("#jsTreeCheckBox").jstree("get_checked");//取得所有选中的节点id，返回数组
            parentIds = ids.join(",");//数组转字符串
            queryList("knowledge/rule/list",false);
            //var no = $('#jsTreeCheckBox').jstree("get_node", id);//根据节点id得到节点对象
            var no_parents = no.parents;//该节点对象的父节点id,数组
            var no_child = no.children_d;//该节点对象的所有子节点id,数组
            if(no.state.selected == true){
                inst.select_node(no_parents);
            }else {
                //inst.uncheck_node(no_parents);
                var parenIds = new Array();
                parenIds.push(no.id);
                if(no_child.length>0){
                    for(var i=0;i<no_child.length;i++){
                        for(var j=0;j<ids.length;j++){
                            if(no_child[i]==ids[j]){
                                parenIds.push(no_child[i]);
                                inst.uncheck_node(no_child[i]);
                            }
                        }
                    }
                }
                if($("#dialog_engine").is(":hidden")){
                    var _param = new Object();
                    _param.engineId = engineId;
                    _param.type =1;
                    var paramD={parentIds : parenIds.join(",")}
                    Comm.ajaxPost("knowledge/tree/getRuleIdsByParentId",paramD,function(result){
                        _param.ruleIds =result.data.ruleIdList.join(",");
                        if( _param.ruleIds != ''){
                            var array = new Array();
                            Comm.ajaxPost("knowledge/rule/getEnginesByRuleId",JSON.stringify(_param),function(result){
                                array = result.data.engineList;
                                $(".c-reminder-span").html("停用规则会影响以下引擎")
                                if(array.length > 0){
                                    var layerOne=layer.open({
                                        type: 1,
                                        title: '输入参数',
                                        maxmin: true,
                                        shadeClose: false,
                                        area: [ '545px', '412px' ],
                                        content: $('#dialog_engine'),
                                        success: function (index, layero) {
                                            var str = "";
                                            for (var i = 0; i < array.length; i++) {
                                                str +="";
                                                str +='<div class="c-popup-redact">'+
                                                    '引擎名称：<span>'+array[i].name+'</span>'+
                                                    '<a class="c-popup-a upadte_engine" dataid="'+array[i].id+'" href="#">点击修改</a>'+
                                                    '</div>';
                                            }
                                            $(".c-popup-engine").html('').append(str);
                                            $(".upadte_engine").click(function(){
                                                var id = $(this).attr("dataid");
                                                var _url =_ctx+"/decision_flow/decisionsPage?id="+id+"&flag=1";
                                                window.open(_url)
                                            })
                                        }
                                    })
                                }
                            },"application/json")
                        }
                    })
                }
            }
            /*var ids = new Array();
            ids.push(id);
            if(no_parents.length>1){
                for(var i=0;i<no_parents.length-1;i++){
                    ids.push(no_parents[i]);
                }
            }*/
        }
    });
    $("#checkBox_cancel").on("click",function () {
        $("#checkBox_btn").hide();
        $("#jsTreeCheckBox").hide();
        $("#jstree").show();
        $.jstree.reference("#jstree").refresh();
    });
    $("#checkBox_sure").on("click",function () {
        var ids=$("#jsTreeCheckBox").jstree("get_checked");//取得所有选中的节点id，返回数组
        /*var nodes = $("#jsTreeCheckBox").jstree().get_checked(true);//得到被点击节点对象，数组
        for(var i=0; i<nodes.length;i++){
            var node_parents = nodes[i].parents;//得到被点击节点的所有父节点id，数组
            for(var j=0; j<node_parents.length;j++){
                if(node_parents[j].indexOf("#")<0){
                    if($.inArray(node_parents[j],ids)<0){
                        ids.push(node_parents[j]);
                    }
                }
            }
            var node_child = nodes[i].children_d;//得到被点击节点的所有子节点id，数组
            if(node_child.length>0){
                for(var j=0; j<node_child.length;j++){
                    if($.inArray(node_child[j],ids)<0){
                        ids.push(node_child[j]);
                    }
                }
            }
        }*/
        ids = ids.join(",");
        var param = {ids:ids, engineId:engineId};
        Comm.ajaxPost("knowledge/tree/addRel", JSON.stringify(param), function (result) {
            layer.msg(result.msg,{icon:1,offset:'200px',time:1000},function(){
                $("#checkBox_btn").hide();
                $("#jsTreeCheckBox").hide();
                $("#jstree").show();
                $.jstree.reference("#jstree").refresh();
            });
        },"application/json");
    });
}