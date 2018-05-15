var rulesArr=[];
var checkedRules=[];//已选中的规则集
/**规则集左边查询条件**/
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var engineId= $("input[name='engineId']").val();
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {

        }
        param.status = 1;
        param.type = 2;
        param.engineId = engineId;
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;

    }
};
/*8规则集右边查询条件**/
var g_userDetailManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var engineId= $("input[name='engineId']").val();
        //自行处理查询参数
        param.fuzzySearch = g_userDetailManage.fuzzySearch;
        if (g_userDetailManage.fuzzySearch) {

        }
        var parentIds=$("#parentIds").val();
        if(parentIds!="")
        {
            param.parentIds = parentIds;
        }
        param.status = 1;
        param.engineId = engineId;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;

    }
};
/**初始化规则集**/
function getRulInt(){
    var isShowRules=$("#isShowRules").val();
    if(!isShowRules){
        g_userManage.tableUser = $('#ruls_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "iDisplayLength" : 10,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('knowledge/tree/getTreeDataForEngine',JSON.stringify(queryFilter),function(result){
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                },"application/json","","","",false)
            },
            "order": [],
            "columns" :[
                {
                    "data":null,
                    "searchable":false,"orderable" : false,
                    "class":"hidden"
                },
                {
                    "className" : "childBox",
                    "orderable" : false,
                    "data" : null,
                    "class":"hidden",
                    "width" : "60px",
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" >'
                    }
                },
                {"data": "name","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {

            },
            "initComplete" : function(settings,json) {
                $("#btn_search_score").click(function() {
                    g_scoreManage.fuzzySearch = true;
                    g_scoreManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset_score").click(function() {
                    $('input[name="Parameter_search"]').val("");
                    g_scoreManage.fuzzySearch = true;
                    g_scoreManage.tableUser.ajax.reload();
                });
                $("#ruls_list tbody").delegate( 'tr input','change',function(e){
                    var selectArray = $("#ruls_list tbody input:checked");
                    if(selectArray.length>0){
                        for(var i=0;i<selectArray.length;i++){
                            $(selectArray[i]).attr('checked',false);
                        }
                    }
                    $(this).attr('checked',"checked");
                    $("#parentIds").val($(this).attr("value"));
                    getRulsInt();
                    //$("#myTabContent").hide();
                });
                $("#ruls_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        if(!target.parentNode.children[1]){
                            return;
                        }
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        var selectArray = $("#ruls_list tbody input:checked");
                        if(selectArray.length>0){
                            for(var i=0;i<selectArray.length;i++){
                                $(selectArray[i]).attr('checked',false);
                                $(input).attr('isChecked','false');
                            }
                        }
                        if(isChecked=='false'){
                            if($(input).attr('checked')=="checked"){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','true');
                        }else{
                            if($(input).attr('checked')=="checked"){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','false');
                        }
                        $("#parentIds").val($(input).attr("value"));
                        getRulsInt();
                        //$("#myTabContent").hide();
                        $("#ruls_detail_list_wrapper .bottom").show();
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableUser.on("order.dt search.dt", function() {
            g_userManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        $("#isShowRules").val(true);
    }else{
        g_userManage.tableUser.ajax.reload();
    }
}
/**初始化规则集详情**/
function getRulsInt(){
    var isShowRules=$("#isShowRules_detail").val();
    if(!isShowRules){
        g_userDetailManage.tableUser = $('#ruls_detail_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "iDisplayLength" : 10,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {
                var queryFilter = g_userDetailManage.getQueryCondition(data);
                Comm.ajaxPost('knowledge/rule/getRuleDataForEngine',JSON.stringify(queryFilter),function(result){
                    debugger
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                },"application/json","","","",false)

            },
            "order": [],
            "columns" :[
                {
                    "data":null,
                    "searchable":false,"orderable" : false,
                    "class":"hidden"
                },
                {
                    "className" : "childBox",
                    "orderable" : false,
                    "data" : null,
                    "width" : "60px",
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        var flag = false;
                        for(var i = 0;i < checkedRules.length;i++){
                            if(data.id == checkedRules[i]){
                                flag = true;
                            }
                        }
                        if(flag){
                            return '<input type="checkbox" checked value="'+data.id+'" style="cursor:pointer;" priority="'+data.priority+'" cname="'+data.name+'" dataParentId="'+data.parentId+'" code="'+data.code+'" ruleAudit="'+data.ruleAudit+'">'
                        }else{
                            return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" priority="'+data.priority+'" cname="'+data.name+'" dataParentId="'+data.parentId+'" code="'+data.code+'" ruleAudit="'+data.ruleAudit+'">'
                        }
                        // return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" priority="'+data.priority+'" cname="'+data.name+'" dataParentId="'+data.parentId+'" code="'+data.code+'" ruleAudit="'+data.ruleAudit+'">'
                    }
                },
                {"data": "name","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#btn_search_score").click(function() {
                    g_userDetailManage.fuzzySearch = true;
                    g_userDetailManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset_score").click(function() {
                    g_userDetailManage.fuzzySearch = true;
                    g_userDetailManage.tableUser.ajax.reload();
                });
                $("#ruls_detail_list tbody").delegate( 'tr input','change',function(e){
                    if($(this).attr("checked")){
                        getDetails($("#sing").val(),this);
                    }else{
                        showChose(this,$("#sing").val());
                    }
                });
                $("#ruls_detail_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        if(!target.parentNode.children[1]){
                            return;
                        }
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        if(isChecked=='false'){
                            if($(input).attr('checked')=="checked"){
                                $(input).attr('checked',false);
                                showChose(input,$("#sing").val());
                            }else{
                                $(input).attr('checked','checked');
                                getDetails($("#sing").val(),input);
                            }
                            $(input).attr('isChecked','true');
                        }else{
                            if($(input).attr('checked')=="checked"){
                                $(input).attr('checked',false);
                                showChose(input,$("#sing").val());
                            }else{
                                $(input).attr('checked','checked');
                                getDetails($("#sing").val(),input);
                            }
                            $(input).attr('isChecked','false');
                        }
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userDetailManage.tableUser.on("order.dt search.dt", function() {
            g_userDetailManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        $("#isShowRules_detail").val(true);
    }else{
        g_userDetailManage.tableUser.ajax.reload();
    }
}
/**展示数据**/
function showChose(input,sing) {
    var ruleAudit=$(input).attr("ruleAudit");
    var id=$(input).attr('value');
    var index=rulesArr.indexOf(id);
    var ln=$(input).attr('value').length;
    rulesArr.splice(index,ln);
    if(ruleAudit==5){
        if(sing=="F"){
            $("span[dataid=\'"+id+"\']").parent().prev().remove();
            $("span[dataid=\'"+id+"\']").parent().remove();
            var a=$(".c-subtract-right").size();
            if($(".c-subtract-right").children().size()<=0){
                $(".c-tier-two").hide();
            }
            if($(".c-tier-one").css("display")=="none"&&$(".c-tier-two").css("display")=="none"){
                $("#myTabContent").hide();
            }
        }else{
            $("span[dataid=\'"+id+"\']").prev().remove();
            $("span[dataid=\'"+id+"\']").remove();
            var a=$(".c-subtract-right").size();
            if($(".c-subtract-right").children().size()<=0){
                $(".c-tier-two").hide();
            }
            if($(".c-tier-one").css("display")=="none"&&$(".c-tier-two").css("display")=="none"){
                $("#myTabContent").hide();
            }
        }
    }else{
        var priority=$(input).attr("priority");
        var count = $("#priority-"+priority).find("span.l_back").length;
        if(count > 1){
            $("span[dataid=\'"+id+"\']").removeClass("l_back");
        }else{
            $("span[dataid=\'"+id+"\']").removeClass("l_back").parent().parent().parent().hide();
        }
        $("span[dataid=\'"+id+"\']").parent().remove();
        // $("span[dataid=\'"+id+"\']").prev().remove();
        // $("span[dataid=\'"+id+"\']").remove();
        var showLn=$(".c-policy-priority");
        var arr=[];
        for(var i=0;i<showLn.length;i++){
            if($(showLn[i]).css("display")=="block"){
                arr.push(1);
            }
        }
        if(arr.length<=0){
            $(".c-tier-one").hide();
        }
        if($(".c-tier-one").css("display")=="none"&&$(".c-tier-two").css("display")=="none"){
            $("#myTabContent").hide();
        }


    }
}
/**展示初始化数据**/
function lookOrOperate(nodeId,sing) {
    $("#sing").val(sing);
    layer.open({
        type : 1,
        title : '规则集详情',
        area : [ '90%', '90%' ],
        offset:["10px"],
        content : $('.page-content'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            //判断引擎是否部署
            if(isDeploy()){
                return;
            }
            var sing=$("#sing").val();
            var param;
            var url;
            if(sing=="F"){
                param = getParam_F();
            }else{
                param = getParam();
                if(param.addOrSubRules){
                    var addOrSubRules=JSON.parse(param.addOrSubRules);
                    var threshold=addOrSubRules.threshold;
                    if(!threshold){
                        layer.msg("请输入阈值!");
                        return;
                    }
                }
            }
            if(param.deny_rules!='' || param.addOrSubRules!=''){
                if(node_2.id !=0 ){
                    url ="decision_flow/update";
                }else{
                    url ="decision_flow/save";
                }
                Comm.ajaxPost(url,JSON.stringify(param),function(data){
                    layer.closeAll();
                    layer.msg(data.msg,{time:1000});
                    if(data.code == 0){
                        if(data.data){
                            node_2.id = data.data;
                        }
                        var relateRule=getNewRules();
                        var param={"relateRule":relateRule, "nodeId":node_2.id};
                        Comm.ajaxPost("knowledge/rule/saveRuleNode",JSON.stringify(param),function(data){},"application/json");
                    }
                },"application/json")
            }else{
                layer.msg("空节点无法保存，请至少选择一条规则或删除该节点！",{time:2000});
            }
        },
        success:function () {
            initClearRule();
            if(nodeId!=0){
                var param = {"nodeId":nodeId};
                Comm.ajaxPost("decision_flow/getNode",param,function (result) {
                    var data=result.data;
                    checkedRules.splice(0,checkedRules.length);
                    if(data!=null){
                        var jsonStr = $.parseJSON(data.nodeJson);
                        var addOrSubRules = null ,deny_rules = null;
                        if(jsonStr.addOrSubRules!=''){
                            addOrSubRules = $.parseJSON(jsonStr.addOrSubRules);
                            var rules = addOrSubRules.rules;
                            for(var i = 0;i < rules.length;i++){
                                checkedRules.push(rules[i].id);
                            }
                        }
                        if(jsonStr.deny_rules!=''){
                            deny_rules = $.parseJSON(jsonStr.deny_rules);
                            var rules = deny_rules.rules;
                            for(var i = 0;i < rules.length;i++){
                                checkedRules.push(rules[i].id);
                            }
                        }
                        initRulesRender(deny_rules,addOrSubRules,sing);
                    }else{
                        getRulInt(sing);
                        // getRulsInt();
                    }
                })
            }else{
                checkedRules.splice(0,checkedRules.length);
                getRulInt(sing);
                // getRulsInt();
            }
        }
    });
}
/**显示底部数据源**/
function getDetails(sing,me){
    var id=$(me).attr("value");
    if(sing=="F"){
        $(".c-tier-two #addorSub-rule-left").remove();
        $(".c-tier-two #addorSub-rule").remove();
        if(rulesArr.indexOf(id)==-1){
            rulesArr.push(id);
            var ruleAudit=$(me).attr("ruleAudit");
            if(ruleAudit==5){
                $(".c-tier-two").show();
                $(".c-tier-two").append("<div class='c-subtract-left'><div class='c-deduction'>加减分规则</div><div class='c-deduction'><span style='margin:1em'>阈值</span><input class='threshold' name='threshold' value='' style='width:60px;'/></div></div><div class='c-subtract-right addorSub-rule'><input type='checkbox' class='checkbox_input' checked disabled><span class='l_back' style='padding: 0 .5em' dataTitle='"+$(me).attr("cname")+"' dataId='"+$(me).attr("value")+"' dataParentId='"+$(me).attr("dataParentId")+"' dataPriority='"+$(me).attr("priority")+"' dataCode='"+$(me).attr("code")+"'>"+$(me).attr("cname")+"</span></div>");
            }else{
                var priority=$(me).attr("priority");
                $("#priority-"+priority).children(".right-priority").append("<label><input type='radio' class='checkbox_input'  checked onclick='showPriorityList("+priority+",\""+$(me).attr("cname")+"\",\""+$(me).attr("value")+"\",\""+$(me).attr("dataParentId")+"\",\""+$(me).attr("code")+"\")'><span style='padding: 0 .5em' class='l_back' dataTitle='"+$(me).attr("cname")+"' dataId='"+$(me).attr("value")+"' dataParentId='"+$(me).attr("dataParentId")+"' dataPriority='"+$(me).attr("priority")+"' dataCode='"+$(me).attr("code")+"'>"+$(me).attr("cname")+"</span></label>");
                $("#priority-"+priority).show();
                $(".c-tier-one").show();
            }
            $("#myTabContent").show();
        }
    }else{
        var ln=$("#addorSub-rule-left").size();
        if(ln==0){
            var html='<div class="c-subtract-left" id="addorSub-rule-left"><div class="c-deduction">加减分规则</div><div class="c-deduction"><span style="margin:1em">阈值</span><input id="threshold" value="" style="width:60px;"/></div></div><div class="c-subtract-right" id="addorSub-rule"></div>';
            $(".c-tier-two").empty().append(html);
        }
        if(rulesArr.indexOf(id)==-1){
            rulesArr.push(id);
            var ruleAudit=$(me).attr("ruleAudit");
            if(ruleAudit==5){
                $(".c-tier-two").show();
                $("#addorSub-rule").append("<label><input type='radio' class='checkbox_input' checked ><span class='l_back' style='padding: 0 .5em' dataTitle='"+$(me).attr("cname")+"' dataId='"+$(me).attr("value")+"' dataParentId='"+$(me).attr("dataParentId")+"' dataPriority='"+$(me).attr("priority")+"' dataCode='"+$(me).attr("code")+"'>"+$(me).attr("cname")+"</span></label>")
             }else{
                var priority=$(me).attr("priority");
                $("#priority-"+priority).children(".right-priority").append("<label><input type='radio' class='checkbox_input'  checked onclick='showPriorityList("+priority+",\""+$(me).attr("cname")+"\",\""+$(me).attr("value")+"\",\""+$(me).attr("dataParentId")+"\",\""+$(me).attr("code")+"\")'><span style='padding: 0 .5em' class='l_back' dataTitle='"+$(me).attr("cname")+"' dataId='"+$(me).attr("value")+"' dataParentId='"+$(me).attr("dataParentId")+"' dataPriority='"+$(me).attr("priority")+"' dataCode='"+$(me).attr("code")+"'>"+$(me).attr("cname")+"</span></label>");
                $("#priority-"+priority).show();
                $(".c-tier-one").show();
            }
            $("#myTabContent").show();
        }
    }
}
$("#preview").click(function(){
    openWin();
});
function openWin(){
    var param = getParam();
    $("#previewForm input[name=id]").val(node_2.id);
    $("#previewForm input[name=ruleJson]").val(JSON.stringify(param));
    window.open("about:blank","newWin","");
    $("#previewForm").submit();
}
function getParam(){
    var param = new Object();
    var nodeId = node_2.id;
    var initEngineVersionId =$("input[name=initEngineVersionId]").val();
    param.initEngineVersionId = initEngineVersionId;
    param.id = nodeId;
    param.nodeX = node_x;
    param.nodeY = node_y;
    param.nodeName = node_name;
    param.nodeCode = node_code;
    param.nodeType = node_type;
    param.nodeOrder = node_order;
    param.params ='{"dataId":"'+node_dataId+'","url":"'+node_url+'","type":"'+node_type+'"}';
    param.deny_rules = getDenyRulesToJson();
    param.addOrSubRules =  getAddOrSubToJson();
    return  param;
}
/**拒绝规则Json1*/
function getDenyRulesToJson(){
    var isSerial;
    if($("#c-refuse-rule").find('.l_back').length > 0) {
        isSerial = 1;
    }else{
        isSerial = 0;
    }
    var slectedRulesString = "";
    if($("#refuse-rule").find('.right-priority .l_back').length>0){
        slectedRulesString = '{"isSerial":'+isSerial;
        slectedRulesString +=',"rules":[';
        $("#refuse-rule").find('.right-priority .l_back').each(function(index,element){
            slectedRulesString +='{"id":'+$(element).attr("dataId")+','
            slectedRulesString +='"parentId":'+$(element).attr("dataParentId")+',';
            slectedRulesString +='"priority":'+$(element).attr("dataPriority")+',';
            slectedRulesString +='"code":"'+$(element).attr("dataCode")+'",';
            slectedRulesString +='"name":"'+$(element).attr("dataTitle")+'"}'+',';
        });
        slectedRulesString = slectedRulesString.substring(0,slectedRulesString.length-1);
        slectedRulesString +="]}"
    }
    return slectedRulesString;
}
/***拒绝规则Json2**/
function getDenyRulesToJsonTwo(){
    var isSerial;
    if($("#c-refuse-rule").find('.l_back').length > 0) {
        isSerial = 1;
    }else{
        isSerial = 0;
    }
    var slectedRulesStringTwo = "";
    if($("#refuse-rule").find('.right-priority .l_back').length>0){
        slectedRulesStringTwo = '{"isSerial":'+isSerial;
        slectedRulesStringTwo +=',"rules":[';
        $("#refuse-rule").find('.right-priority .l_back').each(function(index,element){
            slectedRulesStringTwo +='{"id":'+$(element).attr("dataId")+','
            slectedRulesStringTwo +='"parentId":'+$(element).attr("dataParentId")+',';
            slectedRulesStringTwo +='"priority":'+$(element).attr("dataPriority")+',';
            slectedRulesStringTwo +='"code":"'+$(element).attr("dataCode")+'",';
            slectedRulesStringTwo +='"name":"'+$(element).attr("dataTitle")+'"}'+',';
        });
        slectedRulesStringTwo = slectedRulesStringTwo.substring(0,slectedRulesStringTwo.length-1);
        slectedRulesStringTwo +="]}"
    }
    return slectedRulesStringTwo;
}
/**加减分规则json1**/
function getAddOrSubToJson(){
    var threshold = $("#threshold").val();
    var slectedAddRulesString = "";
    if($(".addorSub-rule").find('.l_back').length>0){
        var slectedAddRulesString = '{"threshold":"'+threshold;
        slectedAddRulesString +='","rules":[';
        $(".addorSub-rule").find('.l_back').each(function(index,element){
            slectedAddRulesString +='{"id":'+$(element).attr("dataId")+',';
            slectedAddRulesString +='"parentId":'+$(element).attr("dataParentId")+',';
            slectedAddRulesString +='"priority":'+$(element).attr("dataPriority")+',';
            slectedAddRulesString +='"code":"'+$(element).attr("dataCode")+'",';
            slectedAddRulesString +='"name":"'+$(element).attr("dataTitle")+'"}'+",";
        })
        slectedAddRulesString = slectedAddRulesString.substring(0,slectedAddRulesString.length-1);
        slectedAddRulesString +="]}"
    }
    if($("#addorSub-rule").find('.l_back').length>0){
        var slectedAddRulesString = '{"threshold":"'+threshold;
        slectedAddRulesString +='","rules":[';
        $("#addorSub-rule").find('.l_back').each(function(index,element){
            slectedAddRulesString +='{"id":'+$(element).attr("dataId")+',';
            slectedAddRulesString +='"parentId":'+$(element).attr("dataParentId")+',';
            slectedAddRulesString +='"priority":'+$(element).attr("dataPriority")+',';
            slectedAddRulesString +='"code":"'+$(element).attr("dataCode")+'",';
            slectedAddRulesString +='"name":"'+$(element).attr("dataTitle")+'"}'+",";
        })
        slectedAddRulesString = slectedAddRulesString.substring(0,slectedAddRulesString.length-1);
        slectedAddRulesString +="]}"
    }
    return slectedAddRulesString;
}
/**加减分规则json2**/
function getAddOrSubToJsonTwo(){
    var slectedAddRulesStringTwo="";
    if($(".c-tier-two").find('.l_back').length>0){
        slectedAddRulesStringTwo += '{"rules":[';
        $(".c-tier-two .c-subtract-right").each(function(index,element){
            var obj = $(element).find('.l_back');
            var threshold = $(element).prev(".c-subtract-left").find('.threshold').val();
            slectedAddRulesStringTwo +='{"id":'+$(obj).attr("dataId")+',';
            slectedAddRulesStringTwo +='"parentId":'+$(obj).attr("dataParentId")+',';
            slectedAddRulesStringTwo +='"priority":'+$(obj).attr("dataPriority")+',';
            slectedAddRulesStringTwo +='"code":"'+$(obj).attr("dataCode")+'",';
            slectedAddRulesStringTwo +='"name":"'+$(obj).attr("dataTitle")+'",';
            slectedAddRulesStringTwo +='"threshold":"'+threshold+'"}'+",";
        })
        slectedAddRulesStringTwo = slectedAddRulesStringTwo.substring(0,slectedAddRulesStringTwo.length-1);
        slectedAddRulesStringTwo +="]}"
    }
    return slectedAddRulesStringTwo;
}
function getParam_F(){
    var param = new Object();
    $("#cats_com").find(".c-policy-cat .selected-tag").removeClass('selected-tag');
    var nodeId = node_2.id;
    var initEngineVersionId =$("input[name=initEngineVersionId]").val();
    param.initEngineVersionId = initEngineVersionId;
    param.id = nodeId;
    param.nodeX = node_x;
    param.nodeY = node_y;
    param.nodeName = node_name;
    param.nodeCode = node_code;
    param.nodeType = node_type;
    param.nodeOrder = node_order;
    param.params ='{"dataId":"'+node_dataId+'","url":"'+node_url+'","type":"'+node_type+'"}';
    param.deny_rules = getDenyRulesToJsonTwo();
    param.addOrSubRules =  getAddOrSubToJsonTwo();
    return  param;
}
$("#myTabContent").on("click",".l_checkbox",function(){
    if($(this).hasClass("l_back")==false){
        $(this).addClass("l_back");
    }else{
        $(this).removeClass("l_back");
    }
});
function initRulesRender(deny_ruless,addOrSubRuless,sing) {
    var deny_rules;
    var addOrSubRules;
    if(deny_ruless){
        deny_rules=deny_ruless.rules;
        $("#parentIds").val(deny_rules[0].parentId);
    }
    if(addOrSubRuless){
        addOrSubRules=addOrSubRuless;
        $("#parentIds").val(addOrSubRules.rules[0].parentId);
    }
    getRulInt();
    getRulsInt();
    var html='<div class="c-subtract-left" id="addorSub-rule-left"><div class="c-deduction">加减分规则</div><div class="c-deduction"><span style="margin:1em">阈值</span><input id="threshold" value="" style="width:60px;"/></div></div><div class="c-subtract-right" id="addorSub-rule"></div>';
    $(".c-tier-two").empty().append(html);
    $("#refuse-rule").find("span").remove();
    $("#refuse-rule").find("input.checkbox_input").remove();
    $("#addorSub-rule").empty();
    $(".addorSub-rule").empty();
    $(".c-policy-priority").hide();
    $(".c-tier-two").hide();
    $(".c-tier-one").hide();
    if(deny_rules){
        if(deny_ruless.isSerial==1){
            $("#c-refuse-rule .l_checkbox").addClass("l_back");
        }
        for(var i=0;i<deny_rules.length;i++){
            var priority=deny_rules[i].priority;
            $("#priority-"+priority).children(".right-priority").append("<label><input type='radio' class='checkbox_input' checked onclick='showPriorityList("+deny_rules[i].priority+",\""+deny_rules[i].name+"\",\""+deny_rules[i].id+"\",\""+deny_rules[i].parentId+"\",\""+deny_rules[i].code+"\")'><span class='l_back' dataTitle='"+deny_rules[i].name+"' dataId='"+deny_rules[i].id+"' dataParentId='"+deny_rules[i].parentId+"' dataPriority='"+deny_rules[i].priority+"' dataCode='"+deny_rules[i].code+"'>"+deny_rules[i].name+"</span></label>");
            $("#priority-"+priority).show();
            $(".c-tier-one").show();
            $("#ruls_detail_list tbody tr input[value="+deny_rules[i].id+"]").attr("checked","checked");
            $("#ruls_detail_list tbody tr input[value="+deny_rules[i].id+"]").attr("ischecked","true");
            rulesArr.push(deny_rules[i].id);
        }
        $("#myTabContent").show();
    }
    if(sing=="G"){
        if(addOrSubRules){
            var asArray = addOrSubRules.rules;
            $("#threshold").val(addOrSubRules.threshold);
            if(asArray!=null && asArray.length>0){
                for (var int3 = 0; int3 < asArray.length; int3++) {
                    $(".c-tier-two").show();
                    $("#addorSub-rule").append("<label><input type='radio' class='checkbox_input' checked onclick='showPriorityList("+asArray[int3].priority+",\""+asArray[int3].name+"\",\""+asArray[int3].id+"\",\""+asArray[int3].parentId+"\",\""+asArray[int3].code+"\")'><span class='l_back' style='padding: 0 .5em' dataTitle='"+asArray[int3].name+"' dataId='"+asArray[int3].id+"' dataParentId='"+asArray[int3].parentId+"' dataPriority='"+asArray[int3].priority+"' dataCode='"+asArray[int3].code+"'>"+asArray[int3].name+"</span></label>");
                    $("#ruls_detail_list tbody tr input[value="+asArray[int3].id+"]").attr("checked","checked");
                    $("#ruls_detail_list tbody tr input[value="+asArray[int3].id+"]").attr("ischecked","true");
                    rulesArr.push(asArray[int3].id);
                }
            }
            $("#myTabContent").show();
        }
    }else{
        if(addOrSubRules){
            var asArray = addOrSubRules.rules;
            $(".c-tier-two").empty();
            if (asArray != null && asArray.length > 0) {
                for (var int3 = 0; int3 < asArray.length; int3++) {
                    $(".c-tier-two").show();
                    $(".c-tier-two").append("<div class='c-subtract-left'><div class='c-deduction'>加减分规则</div><div class='c-deduction'><span style='margin:1em'>阈值</span><input class='threshold' name='threshold' value='"+ asArray[int3].threshold+"' style='width:60px;'/></div></div><div class='c-subtract-right'><input type='checkbox' class='checkbox_input' checked disabled><span class='l_back' style='padding: 0 .5em' dataTitle='" + asArray[int3].name + "' dataId='" + asArray[int3].id + "' dataParentId='" + asArray[int3].parentId + "' dataPriority='" + asArray[int3].priority + "' dataCode='" + asArray[int3].code + "'>" + asArray[int3].name + "</span></div>")
                    $("#ruls_detail_list tbody tr input[value=" + asArray[int3].id + "]").attr("checked", "checked");
                    $("#ruls_detail_list tbody tr input[value=" + asArray[int3].id + "]").attr("ischecked", "true");
                    rulesArr.push(asArray[int3].id);
                }
            }
            $("#myTabContent").show();
        }
    }

}
function initClearRule() {
    rulesArr=[];
    $("#refuse-rule").find("span").remove();
    $("#refuse-rule").find("input.checkbox_input").remove();
    $("#addorSub-rule").empty();
    $(".c-policy-priority").hide();
    $(".c-tier-two").hide();
    $(".c-tier-one").hide();
    $("#myTabContent").hide();
    $("#threshold").val("");
    $(".threshold").val("");
    $("#ruls_list tbody").children().remove();
    $("#ruls_detail_list tbody").children().remove();
    $("#ruls_detail_list_wrapper .bottom").hide();
    $("#c-refuse-rule .l_checkbox").removeClass("l_back");
}
/****************调整优先级*********************/
function showPriorityList(num,name,value,dataParentId,code) {
    layer.open({
        type: 1,
        title: "调整优先级",
        closeBtn: 1,
        area: '460px',
        shadeClose: true,
        content: $('#priorityList'),
        success:function(){
            $("#priorityListName").html(name);
            $("#priorityList_"+num).attr("checked",true);
            $("#priorityList_name").val(name);
            $("#priorityList_val").val(value);
            $("#priorityList_code").val(code);
            $("#priorityList_priority").val(num);
            $("#priorityList_dataParentId").val(dataParentId);
        },
        end:function () {
           var val=$("input[name='priorityList']:checked").val();
            if(Number(val)==num){
                return;
            }else{
                $("#priority-"+val).children(".right-priority").append("<label><input type='radio' class='checkbox_input'  checked onclick='showPriorityList("+val+",\""+name+"\",\""+value+"\",\""+dataParentId+"\",\""+code+"\")'><span style='padding: 0 .5em' class='l_back' dataTitle='"+name+"' dataId='"+value+"' dataParentId='"+dataParentId+"' dataPriority='"+val+"' dataCode='"+code+"'>"+name+"</span></label>");
                $("#priority-"+val).show();
                $("input[value='"+value+"']").attr("priority",val);
                var ln=$("#priority-"+num).children(".right-priority").find("label").length;
                if(ln==1){
                    $("#priority-"+num).children(".right-priority").find("span[datatitle='"+name+"']").removeClass("l_back").parent("label").remove();
                    $("#priority-"+num).hide();
                }else{
                    $("#priority-"+num).children(".right-priority").find("span[datatitle='"+name+"']").removeClass("l_back").parent("label").remove();
                }
            }
        }
    });
}
/************************优先级调整****************************/
function getNewRules(){
    var Rule=[];
    if($("#refuse-rule").find('.right-priority .l_back').length>0){
        $("#refuse-rule").find('.right-priority .l_back').each(function(index,element){
            var rules = "";
            rules +=$(element).attr("dataId")+"|";
            rules +=$(element).attr("dataPriority");
            Rule.push(rules);
        });
    }
    return Rule;
}
