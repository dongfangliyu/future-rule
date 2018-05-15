$(function (){
    queryList("knowledge/rule/list",true);
    var paramData = {
        engineId:engineId,
        isOutput:0
    }
    Comm.ajaxPost("datamanage/field/getEngineFields",paramData,function(result){
        field_isInput_array = result.data;
        if(field_isInput_array!=null && field_isInput_array.length>0){
            for (var i = 0; i < field_isInput_array.length; i++) {
                var key = "\@"+field_isInput_array[i].field.cnName+"\@";
                var value = "\#{"+field_isInput_array[i].field.enName+"\}";
                map.put(key,value);
            };
        }
    },null,null,null,null,false)//同步
});
var tableList,isFirstRuleFields=1;/**********加载字段列表时，初始化标识**********/
/**********列表数据查询条件**********/
var g_userManage = {
    tableRule : null,
    tableScore:null,
    tableField: null,
    ruleSearch : false,
    scorecardSearch : false,
    fieldSearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        if(engineIdFlag){
            param.engineId = $("input[name=eid]").val();
        }
        if (g_userManage.ruleSearch) {
            param.ruleName = $("input[name='rule_search']").val();
        }
        if (g_userManage.scorecardSearch) {
            param.scorecardName = $("input[name='rule_search']").val();
        }
        if (g_userManage.fieldSearch) {
            param.searchKey = $("input[name='Parameter_search']").val();
        }else{
            var folderId=$("input[name='folderId']").val();
            if(folderId!="")
            {
                param.folderId=folderId
            }
        }

        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        //$("input[name='folderId']").val("");

        return paramFilter;
    }
};
/********文件夹目录获取*********/
var g_userManage_Folder = {
    tableRule : null,
    tableScore:null,
    tableField: null,
    ruleSearch : false,
    scorecardSearch : false,
    fieldSearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 5 : data.length;
        paramFilter.page = page;
        param.engineId = engineId;
        paramFilter.param = param;
        return paramFilter;
    }
};
/********显示列表数据********/
function queryList(url,editFlag) {
    var $id;
    if(url.indexOf("rule")>=0){
        $id="#rule_list";
        $("#scorecard_list_wrapper").hide();
        $("#rule_list_wrapper").show();
        $($id).show();
        if(g_userManage.tableRule){
            g_userManage.tableRule.ajax.reload();
        }
    }else{
        $id="#scorecard_list";
        $("#rule_list_wrapper").hide();
        $("#scorecard_list_wrapper").show();
        $($id).show();
        if(g_userManage.tableScore){
            g_userManage.tableScore.ajax.reload();
        }
    }
    tableList = $($id).dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "bFilter": false,
        "bDestroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage.getQueryCondition(data);
            if(parentIds != null && parentIds != ""){
                queryFilter.param.parentIds = parentIds;
            }
            Comm.ajaxPost(url,JSON.stringify(queryFilter),function(result){
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
                'data':null,
                "searchable":false,"orderable" : false
            },
            {"data":'code',"searchable":false,"orderable" : false},
            {"data":'name',"searchable":false,"orderable" : false},
            {"data":'desc',"searchable":false,"orderable" : false},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.status==1){
                        return '启用'
                    }else{
                        return '停用'
                    }
                }
            },
            {"data":'authorName',"searchable":false,"orderable" : false},
            {
                "data":null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.createTime);
                }
            },
            // {
            //     "data":null,
            //     "searchable":false,
            //     "orderable" : false,
            //     "render" : function(data, type, row, meta) {
            //         return json2TimeStamp(data.updateTime);
            //     }
            // },
            {
                "data": "null",
                "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="updateRus(0,'+data.id+')">修改</a>&nbsp;<a class="tabel_btn_style" onclick="updateStatusRus(1,'+data.id+')">启用</a>&nbsp;<a class="tabel_btn_style_dele" onclick="updateStatusRus(0,'+data.id+')">停用</a>&nbsp;<a class="tabel_btn_style_dele" onclick="updateStatusRus(-1,'+data.id+')">删除</a>');
            $('td', row).eq(7).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            //全选
            $(".selectAll").click(function(J) {
                if (!$(this).prop("checked")) {
                    $("tbody tr").find("input[type='checkbox']").prop("checked", false)
                } else {
                    $("tbody tr").find("input[type='checkbox']").prop("checked", true)
                }
            });
            //搜索
            $("#btn_search").click(function() {
                if(treeTypeFlag){
                    g_userManage.scorecardSearch=true;
                }else{
                    g_userManage.ruleSearch = true;
                }
                tableList.ajax.reload();
            });
            //重置
            $("#btn_search_reset").click(function() {
                $('input[name="rule_search"]').val("");
                if(treeTypeFlag){
                    g_userManage.scorecardSearch=false;
                }else{
                    g_userManage.ruleSearch = false;
                }
                tableList.ajax.reload();
            });
            //导出
            var status = "1";

            $($id+" tbody").delegate( 'tr', 'click',function(e){
                var target=e.target;
                if(target.nodeName=='TD'){
                    if(!target.parentNode.children[1]){
                        return;
                    }
                    var input=target.parentNode.children[1].children[0];
                    var isChecked=$(input).attr('isChecked');
                    if(isChecked=='false'){
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);
                        }else{
                            $(input).attr('checked','checked');
                        }
                        $(input).attr('isChecked','true');
                    }else{
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);
                        }else{
                            $(input).attr('checked','checked');
                        }
                        $(input).attr('isChecked','false');
                    }
                }
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    tableList.on("order.dt search.dt", function() {
        tableList.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
    if(url.indexOf("rule")>=0){
        g_userManage.tableRule=tableList;
    }else{
        g_userManage.tableScore=tableList;
    }
}

/*********规则导出*********/
function exportRule() {
    var parentId = $("#hdNodeId").val();//选中文件夹id;
    if(parentId==""){
        layer.msg("请选择要导出的文件夹",{time:1000});
        return false;
    }
    window.open(_ctx+"/knowledge/rule/export?parentId="+parentId+"&status="+1);
}

/*********添加、编辑规则or评分卡信息*********/
function updateRus(sign,id) {
    var $add_id,save_url,edit_url,update_url;
    if(treeTypeFlag) {
        $add_id = "#add_score_style";
        save_url = "knowledge/scorecard/save";
        edit_url = "knowledge/scorecard/edit";
        update_url = "knowledge/scorecard/update";
        resetData("score")
    }else {
        $add_id = "#add_rule_style";
        save_url = "knowledge/rule/save";
        edit_url = "knowledge/rule/edit";
        update_url = "knowledge/rule/update";
        resetData("rule")
    }
    if(id && sign==0){
        var editLayer=layer.open({
            type : 1,
            title : '修改',
            maxmin : true,
            shadeClose : false,
            area : [ '600px', '540px' ],
            content : $($add_id),
            btn : [ '保存', '取消' ],
            success : function(index, layero) {
                if($add_id.indexOf("score")>=0){
                    atSelect();
                }
                var param={
                    id:id,
                    engineId:engineId
                };
                Comm.ajaxPost(edit_url,JSON.stringify(param),function(data){
                    var result = data.data;
                    var scorecard = result.scorecard;
                    var rule=result.rule;
                    if(rule){
                        $('input[name="rule_account"]').val(rule.code);
                        $('input[name="rule_name"]').val(rule.name);
                        $('#Priority').val(rule.priority);
                        $('input[name="rule_describe"]').val(rule.desc);
                        $("input[name='score']").val(rule.score);
                        $("#rule_score").val(rule.score);
                        $("#approveSuggest").val(rule.ruleAudit);
                        if(rule.lastLogical){
                            $("#c-condition-select").show();
                            $("#last_logical").val(rule.lastLogical).show();
                            if(rule.lastLogical==")") {
                                $("select[name=logicalSymbol]").val("(");
                            }
                            else $("select[name=logicalSymbol]").val("((");
                        }else{
                            $("#c-condition-select").hide();
                        }
                        var ruleFieldList=rule.ruleFieldList;//条件区域的数据
                        var fieldListL = ruleFieldList.length;
                        if(fieldListL>0){
                            for(var i=0;i<fieldListL;i++) {
                                if (ruleFieldList[i].valueType == 3) {
                                    $("#conditions .c-optional-rules:eq("+i+") select[name=fieldValue]").show();
                                    $("#conditions .c-optional-rules:eq("+i+") input[name=fieldValue]").hide();
                                    var keyValues = ruleFieldList[i].restrainScope;
                                    if (keyValues) {
                                        var restrainScopeArr = keyValues.split(",");
                                        var arr = [];
                                        for (var t = 0; t < restrainScopeArr.length; t++) {
                                            arr.push(restrainScopeArr[t].split(":"));
                                        }
                                        $("#conditions .c-optional-rules:eq("+i+") select[name=fieldValue]").children().remove();
                                        for (var j = 0; j < arr.length; j++) {
                                            $("#conditions .c-optional-rules:eq("+i+") select[name=fieldValue]").append('<option value="' + arr[j][1] + '">' + arr[j][0] + '</option>');
                                        }
                                        $("#conditions .c-optional-rules:eq("+i+") select[name=fieldValue]").val(ruleFieldList[i].fieldValue);
                                    }
                                    $("#conditions .c-optional-rules:eq("+i+") select[name=operator]").append(enumerationType);
                                } else {
                                    if (ruleFieldList[i].valueType == 1) {
                                        $("#conditions .c-optional-rules:eq("+i+") select[name=operator]").append(digitalType);
                                    }
                                    if (ruleFieldList[i].valueType == 2) {
                                        $("#conditions .c-optional-rules:eq("+i+") select[name=operator]").append(characterType);
                                    }
                                    $("#conditions .c-optional-rules:eq("+i+") select[name=fieldValue]").hide();
                                    $("#conditions .c-optional-rules:eq("+i+") input[name=fieldValue]").show().val(ruleFieldList[i].fieldValue);
                                }
                                $("#conditions .c-optional-rules:eq("+i+") select[name=fieldId]").append('<option value="' + ruleFieldList[i].fieldId + '" valueType="'+ruleFieldList[i].valueType+'" selected>' + ruleFieldList[i].cnName + '</option>');
                                $("#conditions .c-optional-rules:eq("+i+") select[name=operator]").val(ruleFieldList[i].operator);
                                if(i>=0){
                                    if(ruleFieldList[i].logicalSymbol==""){
                                        $("#conditions .c-optional-rules:eq("+i+") select[name=logicalSymbol]").val("-1");
                                    }else{
                                        $("#conditions .c-optional-rules:eq("+i+") select[name=logicalSymbol]").val(ruleFieldList[i].logicalSymbol);
                                    }
                                }
                                if(i<fieldListL-1){
                                    $("#conditions").append(add_condition);
                                }
                            }
                        }
                        var ruleContentList=rule.ruleContentList;//输出区域的数据
                        var contentListL = ruleContentList.length;
                        if(contentListL>0){
                            for(var i=0;i<contentListL-2;i++) {
                                $('#OutRuleLi').append(add_rule_out);
                                var eq = Number(i)+Number(2);
                                $("#OutRuleLi .c-optional-rules:eq("+eq+") select[name=fieldId]").append('<option value="' + ruleContentList[i].fieldId + '">' + ruleContentList[i].cnName + '</option>');
                                if (ruleContentList[i].valueType == 3) {
                                    $("#OutRuleLi .c-optional-rules:eq("+eq+") select[name=fieldValue]").show();
                                    $("#OutRuleLi .c-optional-rules:eq("+eq+") input[name=fieldValue]").hide();
                                    var keyValues = ruleContentList[i].restrainScope;
                                    if (keyValues) {
                                        var restrainScopeArr = keyValues.split(",");
                                        var arr = [];
                                        for (var t = 0; t < restrainScopeArr.length; t++) {
                                            arr.push(restrainScopeArr[t].split(":"));
                                        }
                                        $("#OutRuleLi .c-optional-rules:eq("+eq+") select[name=fieldValue]").children().remove();
                                        for (var j = 0; j < arr.length; j++) {
                                            $("#OutRuleLi .c-optional-rules:eq("+eq+") select[name=fieldValue]").append('<option value="' + arr[j][1] + '">' + arr[j][0] + '</option>');
                                        }
                                        $("#OutRuleLi .c-optional-rules:eq("+eq+") select[name=fieldValue]").val(ruleContentList[i].fieldValue);
                                    }
                                }else{
                                    $("#OutRuleLi .c-optional-rules:eq("+eq+") select[name=fieldValue]").hide();
                                    $("#OutRuleLi .c-optional-rules:eq("+eq+") input[name=fieldValue]").show().val(ruleContentList[i].fieldValue);
                                }
                            }
                        }
                    }
                    if(scorecard){
                        $('input[name="scorecard_code"]').val(scorecard.code);
                        $('input[name="scorecard_name"]').val(scorecard.name);
                        $('input[name="scorecard_desc"]').val(scorecard.desc);


                        $("textarea[name=score_score]").val(result.scoreJson.formula_show);
                        $("textarea[name=score_score]").attr("intervalJson",result.scoreJson.fields);

                        if(result.pdJson){
                            $("textarea[name=score_PD]").val(result.pdJson.formula_show);
                            $("textarea[name=score_PD]").attr("intervalJson",result.pdJson.fields);
                        }else{
                            $("textarea[name=score_PD]").val("");
                            $("textarea[name=score_PD]").attr("intervalJson","");
                        }
                        if(result.oddsJson){
                            $("textarea[name=score_ODDS]").val(result.oddsJson.formula_show);
                            $("textarea[name=score_ODDS]").attr("intervalJson",result.oddsJson.fields);
                        }else{
                            $("textarea[name=score_ODDS]").val("");
                            $("textarea[name=score_ODDS]").attr("intervalJson","");
                        }



                        var sContentList=scorecard.sContentList;//输出区域的数据
                        var scoreL = sContentList.length;
                        if(scoreL>0){
                            for(var i=0;i<scoreL;i++) {
                                $('#OutScoreLi').append(add_score_out);
                                var fieldValue = JSON.parse(sContentList[i].fieldValue);
                                var eq = Number(i)+Number(3);
                                $("#OutScoreLi .c-optional-rules:eq("+eq+") select[name=fieldId]").append('<option dataid="' + fieldValue.output.field_id + '" valueType="' + fieldValue.output.field_type + '" value="' + fieldValue.output.field_code + '">' + fieldValue.output.field_name + '</option>')
                                $("#OutScoreLi .c-optional-rules:eq("+eq+") input[name=fieldValue]").val(fieldValue.formula_show);
                                $("#OutScoreLi .c-optional-rules:eq("+eq+") input[name=fieldValue]").attr("intervalJson",sContentList[i].sCardJson.fields);
                            }
                        }
                    }
                },"application/json");
            },
            yes : function(index, layero) {
                var paramData,teType,$list;
                if($add_id.indexOf("score")>=0){
                    paramData=confirm_score(2);
                    teType = 2;
                    $list = "#scorecard_list";
                }else {
                    paramData=confirm_rule(2);
                    teType = 1;
                    $list="#rule_list";
                }
                paramData.id=id;
                var flag = checkRuleIsUse(2,id,teType);
                if(flag){
                    return;
                }
                if(paramData){
                    Comm.ajaxPost(update_url,JSON.stringify(paramData),function(data){
                            layer.msg(data.msg,{time:1000},function () {
                                layer.close(editLayer);
                                $($list).dataTable().fnDraw(false);
                            });
                    },"application/json");
                }else{
                    return;
                }
            }
        });
    }else{
        var addLayer = layer.open({
            type : 1,
            title : '新增',
            maxmin : true,
            shadeClose : false,
            area : [ '600px', '540px' ],
            content : $($add_id),
            btn : [ '保存', '取消' ],
            success : function(index, layero) {
                $("textarea[name=score_score]").val("");
                $("textarea[name=score_score]").attr("intervalJson","");

                $("textarea[name=score_PD]").val("");
                $("textarea[name=score_PD]").attr("intervalJson","");

                $("textarea[name=score_ODDS]").val("");
                $("textarea[name=score_ODDS]").attr("intervalJson","");

                if($add_id.indexOf("score")>=0){
                    atSelect();
                }
            },
            yes : function(index, layero) {
                var paramData;
                if($add_id.indexOf("score")>=0){
                    tableList=g_userManage.tableScore;
                    paramData=confirm_score(1);
                }else {
                    tableList=g_userManage.tableRule;
                    paramData=confirm_rule(1);
                }
                if(paramData){
                    Comm.ajaxPost(save_url,JSON.stringify(paramData),
                        function(data){
                            layer.msg(data.msg,{time:1000},function () {
                                layer.close(addLayer);
                                tableList.ajax.reload();
                            });
                        },"application/json"
                    );
                }else{
                    return;
                }
            }
        });
        //自动生成规则代码
        var ruleAccountId="GZ";
        var mydate = new Date();
        ruleAccountId+=mydate.getFullYear(); //获取当前年份(4位)
        month=mydate.getMonth(); //获取当前月份(0-11,0代表1月)
        day=mydate.getDate(); //获取当前日(1-31)
        if(month+1<10) {
            month=month+1;
            ruleAccountId=ruleAccountId+"0"+month;
        }else {
            ruleAccountId=ruleAccountId+month;
        }
        if(day<10)
        {
            ruleAccountId=ruleAccountId+"0"+day;
        }else {
            ruleAccountId=ruleAccountId+day;
        }
        ruleAccountId+=randomNum(0,9);
        ruleAccountId+=randomNum(0,9);
        ruleAccountId+=randomNum(0,9);
        ruleAccountId+=randomNum(0,9);
        $('input[name="rule_account"]').val(ruleAccountId);
    }
}
/******产生随机数****/
function randomNum(minNum,maxNum){
    switch(arguments.length){
        case 1:
            return parseInt(Math.random()*minNum+1);
            break;
        case 2:
            return parseInt(Math.random()*(maxNum-minNum+1)+minNum);
            break;
        default:
            return 0;
            break;
    }
}
/******添加、修改规则集或评分卡先初始化弹窗****/
function resetData(str){
    if(str=="rule"){
        $('#rule_account').val("");//清空规则代码
        $('#rule_name').val(""); //清空规则名称
        $('#Priority').val(""); //清空规则优先级
        $('#rule_describe').val("");//清空规则描述
        $("#conditions").html("");//清空规则条件区域
        $("#c-condition-select").hide();//隐藏条件区域最后一个logical select
        $(".ruleInput").val("");//清空规则输出区域信用得分
        /*************************** 规则集-条件区域 ****************************/
        $("#conditions").append(add_condition);
        $("#conditions").find(".c-optional-rules:eq(0) select[name=logicalSymbol]").html('').html('<option value="-1">空置</option><option value="(">(</option><option value="((">((</option>');
        $("#conditions .c-optional-rules:eq(0)").on("change","select[name='logicalSymbol']",function(e){
            e.stopPropagation();
            var val = $(e.target).find('option:selected').val();
            if(val=="(" || val=="(("){
                $("#c-condition-select").show();
                if(val=="(")$("#last_logical").val(")")
                else $("#last_logical").val("))");
            }else{
                $("#c-condition-select").hide();
            }
        });
        var conditionSize = $("#conditions .c-optional-rules").size();
        if(conditionSize>1){
            for(var i=conditionSize-1;i>0;i--){
                $("#conditions .c-optional-rules:eq("+i+")").remove();
            }
        }
        /*************************** 规则集-输出区域 **************************/
        var outLiSize = $("#OutRuleLi li").size();
        if(outLiSize>3){
            for(var i=outLiSize-1;i>2;i--){
                $("#OutRuleLi li:eq("+i+")").remove();
            }
        }
    }else{
        $(".scoreInput").val("");//清空评分卡输出区域input
        $("#scorecard_code").val("");//清空评分卡代码
        $("#scorecard_name").val("");//清空评分卡名称
        $("#scorecard_desc").val("");//清空评分卡描述
        /*************************** 规则集-输出区域 **************************/
        var outLiSize = $("#OutScoreLi li").size();
        if(outLiSize>4){
            for(var i=outLiSize-1;i>3;i--){
                $("#OutScoreLi li:eq("+i+")").remove();
            }
        }
    }
}
/******启用、停用、删除规则或评分卡信息****/
function updateStatusRus(status,id){
    var teType,_url,$list,parentId;
    var selectArray;
    if(treeTypeFlag){
        teType = 2;
        _url='knowledge/scorecard/updateStatus';
        selectArray = $("#scorecard_list tbody input:checked");
        $list = "#scorecard_list";
    } else {
        teType = 1;
        _url='knowledge/rule/updateStatus';
        selectArray = $("#rule_list tbody input:checked");
        $list = "#rule_list";
    }
    var param={
        status:status,
        idList:null
    };
    var idList = new Array();
    idList.push(id);
    //不是启用
    if(status!=1){
        var flag = checkRuleIsUse(status,idList.join(","),teType);
        if(flag)return
    }
    if(status==1 && recFlag){//启用回收站里的数据
        param.parentId = 0;
    }
    param.idList=idList;
    var msg = "";
    if(status==1) msg = "是否确定启用？";
    if(status==0) msg = "是否确定停用？";
    if(status==-1) msg = "是否确定删除该信息？";
    layer.confirm(msg,{btn : [ '确定', '取消' ]}, function() {
        Comm.ajaxPost(_url, JSON.stringify(param),function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000},function () {
                    $($list).dataTable().fnDraw(false);
                });
            },"application/json"
        );
    });
}
/******检查要删除或修改或停用的规则有没有正在被某个决策流使用****/
function checkRuleIsUse(status,ids,type){
    var flag = false;
    var getE_url = "knowledge/rule/getEnginesByRuleId";
    var _param = new Object();
    _param.ruleIds = ids;//ruleIds不代表就是规则id，也可是评分卡id
    _param.type = type;//判断是评分卡还是规则集
    if(engineId != null && engineId != ''){
        _param.engineId = engineId;
    }
    if(status == 0 || status == -1 || status == 2) {
        Comm.ajaxPost(getE_url, JSON.stringify(_param),function(data){
            var array = data.data.engineList;
            if(type == 1){
                if(status == 0){ //停用
                    $(".c-reminder-span").html("停用规则会影响以下引擎")
                }
                if(status == -1){  //删除
                    $(".c-reminder-span").html("删除规则会影响以下引擎")
                }
                if(status == 2){  //编辑
                    $(".c-reminder-span").html("修改规则会影响以下引擎")
                }
            }
            if(type == 2){
                if(status == 0){ //停用
                    $(".c-reminder-span").html("停用评分卡会影响以下引擎")
                }
                if(status == -1){ //删除
                    $(".c-reminder-span").html("删除评分卡会影响以下引擎")
                }
                if(status == 2){  //编辑
                    $(".c-reminder-span").html("修改评分卡会影响以下引擎")
                }
            }
            if(array.length > 0){
                flag = true;
                $("#dialog_engine").show();
                var layerOne=layer.open({
                    type: 1,
                    title: '输入参数',
                    area: [ '545px', '300px' ],
                    content: $('#dialog_engine'),
                    btn:["确定","取消"],
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
                    },
                    yes:function () {
                        layer.close(layerOne);
                    }
                })
            }else{
                flag = false;
            }
        },"application/json","","","",false);
    }
    return flag;
}
/****增加条件区域***/
function addCondition(me) {
    $("#conditions").append(add_condition);
}
/****删除条件区域***/
function delCondition(me) {
    var targetLi=me.parentNode;
    $(targetLi).remove();
}
/****增加输出区域***/
function addOutLi(me) {
    if(treeTypeFlag){
        $('#OutScoreLi').append(add_score_out);
    }else {
        $('#OutRuleLi').append(add_rule_out);
    }
}
/****删除输出区域***/
function deleteOutLi(me) {
    var targetLi=me.parentNode;
    $(targetLi).remove();
}
/*****输入参数 获取参数列表****/
function getInputParameter(me,sing){
    $('select[name="fieldId"]').attr("disabled","disabled");
    var layerOne=layer.open({
        type : 1,
        title : '输入参数',
        area : [ '730px', '400px' ],
        content : $('#input_parameter_style'),
        btn : [ '保存', '取消' ],
        success : function(index, layero) {
            getUrlsTableFieldsFolder();
            getUrlsTableFields();
            $('select[name="fieldId"]').removeAttr("disabled");
        },
        yes : function(index, layero) {
            layer.close(layerOne);
            var showFieldsPlace=$("#showFieldsPlace").val().split("/");
            var valueType=$("#hiddenFields").html();
            var enName=$("#enName").html();
            $(me).children().remove();
            $(me).next().children().remove();
            $(me).next().next().children().remove();
            var optionStr = "<option dataid='"+showFieldsPlace[0]+"' valueType='"+valueType+"' value='";
            if(treeTypeFlag) optionStr += enName;
            else optionStr += showFieldsPlace[0]+"|"+enName;
            optionStr += "'>"+showFieldsPlace[1]+"</option>";
            $(me).append(optionStr);
            if(sing==0){//条件区域
                if(valueType==3){//枚举
                    $(me).siblings("input[name=fieldValue]").hide();
                    $(me).siblings("select[name=fieldValue]").show();
                    var restrainScope=$("#hiddenRestrainScope").html();
                    if(restrainScope){
                        var restrainScopeArr=restrainScope.split(",");
                        var arr=[];
                        for(var i=0;i<restrainScopeArr.length;i++){
                            arr.push(restrainScopeArr[i].split(":"));
                        }
                    }
                    $(me).next().append('<option value="==">等于</option><option value="!=">不等于</option>');
                    $(me).siblings("select[name=fieldValue]").children().remove();
                    for(var j=0;j<restrainScopeArr.length;j++){
                        $(me).siblings("select[name=fieldValue]").append('<option value="'+arr[j][1]+'">'+arr[j][0]+'</option>');
                    }
                }else{
                    $(me).siblings("input[name=fieldValue]").show();
                    $(me).siblings("select[name=fieldValue]").hide();
                    if(valueType==1){//数值
                        $(me).next().append('<option value=">">大于</option><option value="<">小于</option><option value=">=">大于等于</option><option value="<=">小于等于</option><option value="==">等于</option><option value="!=">不等于</option>');
                    }
                    if(valueType==2){//字符
                        $(me).next().append('<option value="contains">like</option><option value="not contains">not like</option><option value="==">等于</option><option value="!=">不等于</option>');
                    }
                }

            }
            if(sing==1){//规则输出区域
                if(valueType==3){//枚举
                    $(me).next().show();
                    $(me).next().next().hide();
                    var restrainScope=$("#hiddenRestrainScope").html();
                    if(restrainScope){
                        var restrainScopeArr=restrainScope.split(",");
                        var arr=[];
                        for(var i=0;i<restrainScopeArr.length;i++){
                            arr.push(restrainScopeArr[i].split(":"));
                        }
                        for(var j=0;j<restrainScopeArr.length;j++){
                            $(me).next().append('<option value="'+arr[j][1]+'">'+arr[j][0]+'</option>');
                        }
                    }
                }else{
                    $(me).next().hide();
                    $(me).next().next().show();
                }
            }
        }
    });
}
function getUrlsTableFieldsFolder(){
    g_userManage_Folder.tableField = $('#Folder_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "iDisplayLength" : 5,
        "destroy":true,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage_Folder.getQueryCondition(data);
            Comm.ajaxPost('datamanage/field/listTreeFolder',JSON.stringify(queryFilter),function(result){
                var returnData = result.data;
                var resData = result.data.kArray;
                var resPage = result.data.pager;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.total;
                returnData.recordsFiltered = resPage.total;
                returnData.data = resData;
                $("input[name='folderId']").val(resData[0].id);
                callback(returnData);
            },"application/json","","","",false)
        },
        "order": [],
        "columns" :[
            {"data": null,"orderable" : false, 'class':'hidden',"searchable":false},
            {"data": "id","orderable" : false, 'class':'hidden',"searchable":false},
            {"data": "fieldType","orderable" : false,"searchable":false}
        ],
        "createdRow": function ( row, data, index,settings,json ) {
        },
        "initComplete" : function(settings,json) {
            $("#Folder_list tbody").delegate( 'tr','click',function(e){
                var target = e.target||window.event.target;
                if(target.nodeName=='TD'){
                    var id=$(target).prev().html();
                    $("input[name='folderId']").val(id);
                    g_userManage.tableField.ajax.reload();
                }
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage_Folder.tableField.on("order.dt search.dt", function() {
        g_userManage_Folder.tableField.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw();
}
/*****添加修改里的字段列表数据****/
function getUrlsTableFields(){
    if(isFirstRuleFields==1){
        g_userManage.tableField = $('#Parameter_list').dataTable($.extend({
            'iDeferLoading':true,
            "bAutoWidth" : false,
            "Processing": true,
            "ServerSide": true,
            "sPaginationType": "full_numbers",
            "bPaginate": true,
            "bLengthChange": false,
            "iDisplayLength" : 5,
            "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
            "ajax" : function(data, callback, settings) {
                var queryFilter = g_userManage.getQueryCondition(data);
                Comm.ajaxPost('knowledge/rule/getFieldList',JSON.stringify(queryFilter),function(result){
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                    $("#Folder_list_info").hide();
                },"application/json","","","",false)

            },
            "order": [],
            "columns" :[
                {
                    'data':'valueType',
                    'class':'hidden',"searchable":false,"orderable" : false
                },
                {
                    "data": "null",
                    "orderable": false,
                    "defaultContent":""
                },
                {"data": "cnName","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
                if(data.id==dataArr[0]){
                    var checkbox='<input type="checkbox" checked value="'+data.id+'" valueType="'+data.valueType+'" restrainScope="'+data.restrainScope+'" enName="'+data.enName+'" style="cursor:pointer;" isChecked="false">';
                }else{
                    var checkbox='<input type="checkbox" value="'+data.id+'" valueType="'+data.valueType+'" restrainScope="'+data.restrainScope+'" enName="'+data.enName+'" style="cursor:pointer;" isChecked="false">';
                }
                $('td', row).eq(1).append(checkbox);
            },
            "initComplete" : function(settings,json) {
                //搜索
                $("#param_search").click(function() {
                    g_userManage.fieldSearch = true;
                    g_userManage.tableField.ajax.reload();
                });
                //重置
                $("#param_search_reset").click(function() {
                    $('input[name="Parameter_search"]').val("");
                    g_userManage.fieldSearch = false;
                    g_userManage.tableField.ajax.reload();
                });
                $("#Parameter_list tbody").delegate( 'tr input','click',function(e){
                    var isChecked=$(this).attr('isChecked');
                    var selectArray = $("#Parameter_list tbody input:checked");
                    if(selectArray.length>0){
                        for(var i=0;i<selectArray.length;i++){
                            $(selectArray[i]).attr('checked',false);
                            $(this).attr('isChecked','false');
                        }
                    }
                    if(isChecked=='false'){
                        if($(this).attr('checked')){
                            $(this).attr('checked',false);
                        }else{
                            $(this).attr('checked','checked');
                        }
                        $(this).attr('isChecked','true');
                    }else{
                        if($(this).attr('checked')){
                            $(this).attr('checked',false);
                        }else{
                            $(this).attr('checked','checked');
                        }
                        $(this).attr('isChecked','false');
                    }
                    dataArr=[];
                    var fieldsId= $(this).val();
                    var fieldsHtml=$(this).parent().next().html();
                    $("#hiddenFields").html($(this).attr('valueType'));
                    $("#hiddenRestrainScope").html($(this).attr('restrainScope'));
                    $("#enName").html($(this).attr('enName'));
                    $("#showFields").html(fieldsId+'/'+fieldsHtml);
                    $("#showFieldsPlace").val(fieldsId+'/'+fieldsHtml);
                    dataArr.push($(this).val());
                });
                $("#Parameter_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        var selectArray = $("#Parameter_list tbody input:checked");
                        if(selectArray.length>0){
                            for(var i=0;i<selectArray.length;i++){
                                $(selectArray[i]).attr('checked',false);
                                $(input).attr('isChecked','false');
                            }
                        }
                        if(isChecked=='false'){
                            if($(input).attr('checked')){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','true');
                        }else{
                            if($(input).attr('checked')){
                                $(input).attr('checked',false);
                            }else{
                                $(input).attr('checked','checked');
                            }
                            $(input).attr('isChecked','false');
                        }
                        dataArr=[];
                        var fieldsId= $(input).val();
                        var fieldsHtml=$(input).parent().next().html();
                        $("#hiddenFields").html($(input).attr('valueType'));
                        $("#hiddenRestrainScope").html($(input).attr('restrainScope'));
                        $("#enName").html($(input).attr('enName'));
                        $("#showFields").html(fieldsId+'/'+fieldsHtml);
                        $("#showFieldsPlace").val(fieldsId+'/'+fieldsHtml);
                        dataArr.push($(input).val());
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_userManage.tableField.on("order.dt search.dt", function() {
            g_userManage.tableField.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        isFirstRuleFields=0;
    }else{
        g_userManage.tableField.ajax.reload();
        $("#showFields").html('/');
    }
}
/******添加修改规则的数据******/
function confirm_rule(sign){
    if($('input[name="rule_account"]').val()==""){
        layer.msg("规则代码不能为空",{time:2000});
        return false;
    }
    if($('input[name="rule_name"]').val()==""){
        layer.msg("规则名称不能为空",{time:2000});
        return false;
    }
    if($('input[name="rule_describe"]').val()==""){
        layer.msg("规则描述不能为空",{time:2000});
        return false;
    }
    if($('#Priority').val()==""){
        layer.msg("规则优先级不能为空",{time:2000});
        return false;
    }
    if($("#approveSuggest").val() != 2){
        if($('#rule_score').val()==""){
            layer.msg("信用得分不能为空",{time:2000});
            return false;
        }
    }

    var isCheck=true;
    $("#conditions .c-optional-rules").each(function(index,dom){
        var inputSelect=$(dom).find("input:not(:hidden),select:not(:hidden)");
        $(inputSelect).each(function(index,obj){
            if(!$(obj).val()){
                isCheck=false;
            }
        });
    });
    if(!isCheck){
        layer.msg("条件区域不能为空",{time:2000});
        return false;
    }
    $("input[name = 'fieldContent']").val(getJsonStringArray("#conditions .c-optional-rules"));
    $("input[name = 'outcontent']").val(getJsonStringArray("#OutRuleLi .c-optional-rules"));
    $("input[name = 'score']").val($("#rule_score").val());//信用得分
    $("input[name = 'ruleAudit']").val($("#approveSuggest option:selected").val());
    if(!$("#last_logical").is(":hidden")){
        $("input[name = 'lastLogical']").val($("#last_logical option:selected").val());
    }else{
        $("input[name = 'lastLogical']").val("");
    }
    $("input[name='parentId']").val(parentId);
    $("input[name='parentIds']").val(parentIds);
    var rules=getRuleStringArray("#conditions .c-optional-rules","1");
    var outputrules=getRuleStringArray("#OutRuleLi .c-optional-rules:gt(1)","2");
    var code=$("input[name = 'rule_account']").val();
    var salience =$("select[name = 'Priority']").val();
    var content= getRules(rules,outputrules,code,salience);
    $("input[name = 'content']").val(content);

    var rule={};
    rule.code=code;//规则代码
    rule.name=$('input[name="rule_name"]').val();////规则名称
    rule.desc=$('input[name="rule_describe"]').val();//规则描述
    rule.priority=salience;//规则优先级
    rule.engineId=engineId;
    if(sign==1){//1为添加
        rule.parentId=parentId;//父节点id
    }
    rule.content=content;//规则具体内容
    rule.ruleAudit=$("#approveSuggest").val();//审批建议
    rule.score=$("#rule_score").val();//得分
    if($("#last_logical").css("display")!="none"){
        rule.lastLogical=$("input[name = 'lastLogical']").val();//逻辑关系符
    }else{
        rule.lastLogical=null;//逻辑关系符
    }
    rule.fieldContent=$("input[name = 'fieldContent']").val();
    rule.outcontent=$("input[name = 'outcontent']").val();
    return rule;
}
function getJsonStringArray(obj){
    var length=$(obj).length;
    if(length>0){
        var strArr="[",content="";
        for(var i=0;i<length;i++){
            var nameList=$($(obj)[i]).find('select:not(:hidden),input:not(:hidden)');
            for(var j=0;j<nameList.length;j++){
                var name  = $(nameList)[j].getAttribute("name");
                var value = $($(nameList)[j]).val();
                if(name){content += "\""+name+"\""+":"+"\""+value+"\""+","}
            }
            if(i==0){
                content = "{"+content.substring(0,content.length-1)+"},{";
            }else{
                content = content.substring(0,content.length-1)+"},{";
            }
        }
        strArr += content.substring(0,content.length-2)+"]";
        return strArr;
    }

}
function getRuleStringArray(obj,type){
    var rule="";
    $(obj).each(function(index,element){
        var valueType = $(element).find("select[name='fieldId']").find('option').attr("valueType");
        var content  = $(this).find('select,input').serializeJson();
        if(content.fieldId){
            if(type=="1"){
                if(content.logicalSymbol!='-1'){
                    rule=rule+" "+ content.logicalSymbol;
                }
                if(valueType == '2'){
                    if(content.fieldId.indexOf("|")!=-1){
                        rule=rule+" this[\'"+content.fieldId.split("|")[1]+"\'] " +content.operator+" \""+content.fieldValue +"\" ";
                    }else{
                        rule=rule+" this[\'"+content.fieldId+"\'] " +content.operator+" \""+content.fieldValue +"\" ";
                    }
                }else{
                    if(content.fieldId.indexOf("|")!=-1){
                        rule=rule+" this[\'"+content.fieldId.split("|")[1]+"\'] " +content.operator+" "+content.fieldValue +" ";
                    }else{
                        rule=rule+" this[\'"+content.fieldId+"\'] " +content.operator+" "+content.fieldValue +" ";
                    }
                }
            }else{
                if(content.fieldId.indexOf("|")!=-1){
                    rule += "map.put(\""+content.fieldId.split("|")[1]+"\",\""+content.fieldValue+"\"); \r\n\t ";
                }else{
                    rule += "map.put(\""+content.fieldId+"\",\""+content.fieldValue+"\"); \r\n\t ";
                }

            }
        }
    });
    if(type=="1"){
        if($("select[name = 'logicalSymbol']").val() != '-1'){
            var last_logical = $("#last_logical").val();
            if(last_logical != '-1'){
                rule += last_logical;
            }
        }
    }
    return rule;
}
function getRules(rule,outputparam,code,salience){
    var ruleAudit=$("select[name='ruleAudit']").val();
    var type=1;
    var code=$("input[name = 'rule_account']").val();
    var name=$("input[name='rule_name']").val();
    if(ruleAudit=='2'){
        type='2';
    }
    var fieldcode="";
    var fieldvalue=$("#scoreValue").val();
    var enginerule = "package com.zw.rule.engine.po\\r\\n";
    enginerule += "import java.util.Map;\\r\\n";
    enginerule += "import java.util.List;\\r\\n";
    enginerule +="import java.util.ArrayList;\\r\\n";
    enginerule +="import java.util.HashMap;\r\n";
    enginerule += "import com.zw.rule.engine.po.InputParam;\\r\\n";
    enginerule += "import com.zw.rule.engine.po.Result;\\r\\n";
    enginerule += "import com.zw.rule.engine.po.RuleModel;\\r\\n";
    enginerule += "rule \""+code+"\"\\r\\n";
    enginerule += "salience "+salience+"\\r\\n";
    enginerule += "\\twhen\\r\\n";
    enginerule += "\\t$inputParam : InputParam();\\r\\n";
    enginerule +="Map("+rule+")   from $inputParam.inputParam;";
    enginerule += "\\tthen\\r\\n";
    enginerule += "\\t List<Result>  resultList =$inputParam.getResult();\\r\\n";
    enginerule += "\\t Result result =new Result(); \\r\\n";
    enginerule += "\\t result.setResultType(\""+type+"\"); \\r\\n";
    enginerule += "\\t result.setCode(\""+code+"\"); \\r\\n";
    enginerule += "\\t Map<String, Object> map =new HashMap<>(); \\r\\n";
    if(typeof(fieldvalue)!="undefined"&&null!=fieldvalue&&""!=fieldvalue){
        enginerule += "\\t map.put(\"score\","+fieldvalue+"); \\r\\n";
    }
    enginerule +=outputparam;
    enginerule += "\\t result.setMap(map); \\r\\n";
    enginerule+= " resultList.add(result); \r\n\t"
    enginerule +="\\t $inputParam.setResult(resultList); \\r\\n";
    enginerule += "end\\r\\n";
    return enginerule
}
//添加修改评分卡的数据
function confirm_score(sign){
    if($('input[name="scorecard_code"]').val()==""){
        layer.msg("评分卡代码不能为空",{time:2000});
        return false;
    }
    if($('input[name="scorecard_name"]').val()==""){
        layer.msg("评分卡名称不能为空",{time:2000});
        return false;
    }
    if($('input[name="scorecard_desc"]').val()==""){
        layer.msg("评分卡描述不能为空",{time:2000});
        return false;
    }
    if($('textarea[name=score_score]').val()==""){
        layer.msg("信用得分不能为空",{time:2000});
        return false;
    }
    var str="";
    if($("#OutScoreLi .c-optional-rules").length > 3){
        str +="[";
        $("#OutScoreLi .c-optional-rules:gt(2)").each(function(index,element){
            str += getFormulaJsonString(element);
        });
        str = str.substring(0,str.length -1);
        str = str +"]"
    }
    var scorecard={};
    var score = getFormulaJsonString($("#OutScoreLi .c-optional-rules:eq(0)"));
    var pd = getFormulaJsonString($("#OutScoreLi .c-optional-rules:eq(1)"));
    var odds = getFormulaJsonString($("#OutScoreLi .c-optional-rules:eq(2)"));

    if($("textarea[name=score_score]").val()!=''){
        scorecard.score = score.substring(0,score.length -1)
    }
    if($("textarea[name=score_PD]").val()!=''){
        scorecard.pd = pd.substring(0,pd.length -1)
    }
    if($("textarea[name=score_ODDS]").val()!=''){
        scorecard.odds = odds.substring(0,odds.length -1)
    }

    $("input[name='score']").val($("input[name=score_score]").val());//信用得分
    $("input[name='parentId']").val(parentId);
    $("input[name='parentIds']").val(parentIds);

    scorecard.code=$('input[name="scorecard_code"]').val();
    scorecard.name=$('input[name="scorecard_name"]').val();
    scorecard.desc=$('input[name="scorecard_desc"]').val();
    //scorecard.version =$("select[name=version] option:selected").val();//未找到在哪取值
    scorecard.content = str;
    scorecard.engineId=engineId;
    if(sign==1){//1为添加
        scorecard.parentId=parentId;//父节点id
    }
    return scorecard;
}
function getFormulaJsonString(ele){
    var string ="";
    string += '{"output":{"field_id":'+$(ele).find("select[name=fieldId] option:selected").attr("dataid");
    string += ',"field_code":"'+$(ele).find("select[name=fieldId] option:selected").val();
    string += '","field_name":"'+$(ele).find("select[name=fieldId] option:selected").text();
    string += '","field_type":'+$(ele).find("select[name=fieldId] option:selected").attr("valueType");
    string += '},';
    string += '"formula":"' +getReplaceFields($(ele).find(".scoreInput").val().replace(" ",""));
    string += '","formula_show":"'+$(ele).find(".scoreInput").val().replace(" ","");
    string += '","fields":['+$(ele).find(".scoreInput").attr("intervalJson")+']';
    string += '},';
    return string;
}
function getReplaceFields(str){
    var reg=/@[a-zA-Z0-9_\u4e00-\u9fa5()（）-]+@/;
    var patt = new RegExp(reg, 'g');
    var arr0=str.match(patt);
    if(arr0 !=null && arr0.length>0){
        for (var i=0;i<arr0.length;i++) {
            str=str.replace(reg,map.get(arr0[i]));
        }
    }
    return str;
}
/****************************************************** 公式编辑 **********************************************************/
//获取光标位置
(function($, undefined) {
    $.fn.getCursorPosition = function() {
        var el = $(this).get(0);
        var pos = 0;
        if ('selectionStart' in el) {
            pos = el.selectionStart;
        } else if ('selection' in document) {
            el.focus();
            var Sel = document.selection.createRange();
            var SelLength = document.selection.createRange().text.length;
            Sel.moveStart('character', -el.value.length);
            pos = Sel.text.length - SelLength;
        }
        return pos;
    }
})(jQuery);
var dataId,cursor,cursorId;
//获取光标位置
function setCursor(id) {
    cursorId=id;
    cursor=$("#"+id);
    cursor.focus();
}
//点击公式方法
function character(sign){
    if (cursor!=null){
        dataId=$(sign).attr("dataId");
        var strIndex=cursor.getCursorPosition();
        var content=cursor.val();
        var str1=content.slice(0,strIndex);
        var str2=content.slice(strIndex,content.length);
        //var txtFocus = cursor;
        var txtFocus = document.getElementById(cursorId);
        switch (dataId) {
            case '0': str1=str1+'+'; break;
            case '1': str1=str1+'-'; break;
            case '2': str1=str1+'*'; break;
            case '3': str1=str1+'/'; break;
            case '4': str1=str1+'sqrt()'; break;
            case '5': str1=str1+'ln()'; break;
            case '6': str1=str1+'avg()'; break;
            case '7': str1=str1+'()'; break;
            case '8': str1=str1+'abs()'; break;
            case '9': str1=str1+'max()'; break;
            case '10': str1=str1+'min()'; break;
            case '11': str1=str1+'lg()'; break;
            case '12': str1=str1+'exp()'; break;
            case '13': str1=str1+'ceil()'; break;
            case '14': str1=str1+'floor()'; break;
            case '15': str1=str1+'PI'; break;
            default:break;
        }
        var newContent=str1+str2;
        cursor.val(newContent);
        if(dataId==7){
            var position=str1.length-1;
            txtFocus.setSelectionRange(position,position);//设置光标位置，不适用IE
            cursor.focus();
        }else{
            var position=str1.length;
            txtFocus.setSelectionRange(position,position);
            cursor.focus();
        }
    }
};
/********************************************* @输出出现下拉框及区间设定 ************************************************/
var gobal_inputor;
/****绑定@***/
function atSelect() {
    $.fn.atwho.debug = true;
    var array = new Array();//输入字段数据
    if(field_isInput_array){
        for (var i = 0; i < field_isInput_array.length; i++) {
            array.push(field_isInput_array[i].field.cnName);
        };
        var at_config = {
            at: "@",
            data: array,
            headerTpl: '<div class="atwho-header">Field List<small>↑&nbsp;↓&nbsp;</small></div>',
            limit: 200
        }
        $inputor = $('.scoreInput').atwho(at_config);
        $(document).on("focus",".scoreInput",function() {
            $(".scoreInput").removeAttr("id");
            $(this).attr("id","inputor");
            gobal_inputor = $(this);
            $inputor = $('#inputor').atwho(at_config);
        });
    }
}
/*****公式*****/
function fieldsetting(){
    var text = subtext3;
    var index2=layer.open({
        type: 1,
        area: ['500px', '300px'],
        btn : [ '保存', '取消' ],
        content:'<div class="field-bounced-content">'+
        '<div class="field-bounced-section"></div><div class="field-bounced-table"><table>'+
        '<tr><td>区间</td><td>值</td><td>操作</td></tr></table></div></div>'+
        '<span id="checkSpan" style="margin:10px 0 0 23px;color:red;display: inline-block;"></span>'+
        '<div align="center" style="margin-top:30px;" class="areaButton"></div>',
        yes:function () {
            var intervalArray = new Array();
            var isEmpty=[];
            var segments ="[";
            $(".field-bounced-content").find(".field-bounced-first").each(function(index,element){
                var str="{";
                if(valueType == 3){
                    str += '"segment":"'+$(element).find("select[name=segment] option:selected").val();
                    intervalArray.push($(element).find('select[name=segment] option:selected').text())
                }else{
                    str += '"segment":"'+$(element).find("input[name=segment]").val();
                    intervalArray.push($(element).find('input[name=segment]').val());
                }
                str += '","value":'+$(element).find("input[name=value]").val();
                isEmpty.push($(element).find("input[name=value]").val());
                str +='},';
                segments +=str;
            });
            for(var t=0;t<isEmpty.length;t++){
                if(!isEmpty[t]){
                    layer.msg("值不能为空！");
                    return;
                }
            }
            //判断区间范围是否在字段范围之内
            if(valueType == 1){
                for(var j=0;j<intervalArray.length;j++){
                    if(!intervalArray[j]){
                        layer.msg("区间不能为空！");
                        return;
                    }
                }
                var numberValue = valueScope.split(",");
                var min = parseFloat(numberValue[0].substring(1));//字段最小值
                var max = parseFloat(numberValue[1].substring(0,numberValue[1].length-1));//字段最大值
                debugger
                if(intervalArray[0].indexOf(",")!=-1){
                    var intervalMin = parseFloat(intervalArray[0].split(",")[1].substring(0,intervalArray[0].split(",")[1].length-1));//区间最小值
                    var intervalMax = parseFloat(intervalArray[intervalArray.length-1].split(",")[0].substring(1));//区间最大值
                    if(intervalMin < min || intervalMax > max){
                        layer.msg("该字段范围: "+valueScope+" ,请输入正确的区间！");
                        return ;
                    }
                }else{
                    layer.msg("该字段范围: "+valueScope+" ,请输入正确的区间！");
                    return ;
                }
            }
            segments = segments.substring(0,segments.length -1);
            segments = segments +"]";
            intervalArray=intervalArray.sort();
            for(var i=0;i<intervalArray.length;i++){
                if (intervalArray[i] == intervalArray[i+1]){
                    layer.msg("区间有重叠,请核准!",{time:1000});
                    return ;
                }
            }
            if(valueType == 1){
                var flag = false;
                Comm.ajaxPost("datamanage/field/section",{"sections":intervalArray},function(data){
                    if(data.data == 1){
                        layer.msg(data.msg,{time:1000});
                        flag = true
                    }
                },null,null,null,null,false);//同步
                if(flag){
                    return;
                }
            }
            var  result ='{"field_id":'+fieldId;
            result += ',"field_code":"'+fieldCode;
            result += '","field_name":"'+fieldName;
            result += '","field_type":'+valueType;
            result +=',"segments":'+segments;
            result +='}';
            formula_json.push(result);
            if($(gobal_inputor).attr("intervalJson") !=''){
                var jsonStr = "["+$(gobal_inputor).attr("intervalJson")+"]";
                var jsonArray = JSON.parse(jsonStr);
                for(var i = 0; i < jsonArray.length; i++) {
                    if(fieldCode == jsonArray[i].field_code){
                        jsonArray.del(i)
                    }
                }
                var str =  JSON.stringify(jsonArray);
                var array_d =str.substring(1,str.length-1).concat(",").concat(formula_json);
                $(gobal_inputor).attr("intervalJson",array_d);
            }else{
                $(gobal_inputor).attr("intervalJson",formula_json);
            }
            layer.close(index2);
        },
        success:function () {
            layer.close(layer_index);
        }
    });
    var valueType,valueScope,fieldCode,fieldId,fieldName;
    for (var i = 0; i < field_isInput_array.length; i++) {
        if(text == field_isInput_array[i].field.cnName){
            valueType = field_isInput_array[i].field.valueType;
            valueScope = field_isInput_array[i].field.restrainScope;
            fieldCode = field_isInput_array[i].field.enName;
            fieldId = field_isInput_array[i].field.id;
            fieldName = field_isInput_array[i].field.cnName
        }
    }
    var iJosn = $(gobal_inputor).attr("intervalJson");
    $(".field-bounced-content .field-bounced-first").empty();

    if(iJosn == undefined || iJosn == "" || iJosn == "{}"){
        $(".field-bounced-content").append(addIntervalTwo(valueType,valueScope,fieldCode));
    }else{
        $(".field-bounced-content").append(addIntervalOne(valueType,valueScope,fieldCode));
    }

    //字段编辑动态
    $(document).unbind('click').on("click",".add_interval",function(e){
        e.stopPropagation();
        $(e.target).parents(".field-bounced-content").append(addIntervalTwo(valueType,valueScope,fieldCode));
    })

    $(document).on("click",".delete_interval",function(e){
        e.stopPropagation();
        $(e.target).parent().parent().remove();
    })
    var formula_json = new Array();
}
Array.prototype.del=function(index){
    if(isNaN(index)||index>=this.length){
        return false;
    }
    for(var i=0,n=0;i<this.length;i++){
        if(this[i]!=this[index]){
            this[n++]=this[i];
        }
    }this.length-=1;
};
function addIntervalOne(valueType,valueScope,fieldCode){
    var jsonStr = "["+$(gobal_inputor).attr("intervalJson")+"]";
    var jsonArray = JSON.parse(jsonStr);
    var jsonArray_1 = new Array();
    for(var i = 0; i < jsonArray.length; i++) {
        if(fieldCode == jsonArray[i].field_code){
            jsonArray_1.push(jsonArray[i])
        }
    }
    var str ="";
    if(jsonArray_1.length>0){
        var jarry =  jsonArray_1[0].segments;
        for (var i = 0; i < jarry.length; i++) {
            str +='<div class="field-bounced-first"><div class="c-bounced-grid">';
            if(valueType == 3){
                str +='<select name="segment" class="l_before" style="width:105px;height:30px;margin: 0px 0 0 5px">';
                var array =  valueScope.split(",");
                for (var j = 0; j < array.length; j++) {
                    var subArray = array[j].split(":");
                    if(jarry[i].segment == subArray[1]){
                        str +='<option selected="selected" value="'+subArray[1]+'">'+subArray[0]+'</option>'
                    }else{
                        str +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>'
                    }
                }
                str +='<select>'
            }else{
                str +='<input class="c-inputOne" name="segment" value="'+jarry[i].segment+'" type="text"/>';
            }
            str+='</div><div class="c-bounced-grid">'+
                '<input class="c-inputThree" name="value" value="'+jarry[i].value+'" type="text"/>'+
                '</div><div class="c-bounced-grid">'+
                '<img src="'+_ctx+'/resources/images/rules/add.png" style="margin-left: 40px;" class="add_interval"/>'+
                '<img src="'+_ctx+'/resources/images/rules/delete.png" style="margin-left: 16px;"  class="delete_interval"/>'+
                '</div></div>';
        }
    }else{
        $(".field-bounced-content").append(addIntervalTwo(valueType,valueScope,fieldCode));
    }
    return str;
}
function addIntervalTwo(valueType,valueScope,fieldCode){
    var str="";
    str +='<div class="field-bounced-first"><div class="c-bounced-grid">'+
        '<input type="hidden" name ="field_code" value="'+fieldCode+'">';
    if(valueType == 3){
        str +='<select name="segment" class="l_before" style="width:105px;height:30px;margin: 0px 0 0 5px">';
        var array =  valueScope.split(",");
        for (var j = 0; j < array.length; j++) {
            var subArray = array[j].split(":");
            str +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>'
        }
        str +='<select>'
    }else{
        str +='<input class="c-inputOne" name="segment" value="" type="text"/>';
    }
    str+='</div><div class="c-bounced-grid">'+
        '<input class="c-inputThree" name="value" value="" type="text"/>'+
        '</div><div class="c-bounced-grid">'+
        '<img src="'+_ctx+'/resources/images/rules/add.png" style="margin-left: 40px;" class="add_interval"/>'+
        '<img src="'+_ctx+'/resources/images/rules/delete.png" style="margin-left: 16px;"  class="delete_interval"/>'+
        '</div></div>';
    return str;
}
/****导入*****/
function handleFile() {
    $("#fileName").val($("#file").val());
}
function leadLoad() {
    var leadLayer = layer.open({
        type: 1,
        title: "批量导入",
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area: ['500px', '200px'],
        offset:'150px',
        content: $('#Lead_detail_style'),
        btn: ['保存', '取消'],
        success: function (index, layero) {
            $("#fileName").val("");
            var file = document.getElementById("file");
            // for IE, Opera, Safari, Chrome
            if (file.outerHTML) {
                file.outerHTML = file.outerHTML;
            } else { // FF(包括3.5)
                file.value = "";
            }
        },
        yes: function (index, layero) {
            var formData = new FormData();
            formData.append("filename",$('#file')[0].files[0]);
            if( $("#fileName").val()==""){
                layer.msg("请选择文件",{time: 1000},function(){
                });
                return;
            }
            if($('#file')[0].files[0].type!='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'&&$('#file')[0].files[0].type!='application/vnd.ms-excel'){
                layer.msg("文件格式为Excel!请重新选择!",{time: 2000},function(){});
                return;
            }
            var nodeId = $("#hdNodeId").val();//选中文件夹id
            formData.append("nodeId",nodeId);
            Comm.ajaxPost('knowledge/rule/upload?listType=',formData,function (data) {
                layer.close(leadLayer);
                g_userManage.tableRule.ajax.reload();
               /* g_userManage.tableUser.ajax.reload(function () {
                    //判断值为空时，隐藏
                    hideSpace();
                });*/
            },false,false,false);
        },
        cancel: function (index, layero) {
            layer.close(leadLayer);
        }
    });
}
/***************新增点击存储****************/
var dataArr=[];