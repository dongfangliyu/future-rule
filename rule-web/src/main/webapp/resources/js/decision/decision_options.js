var path=$("#getPath").val(),map = new Map(),fieldIsOutputArray,fieldIsInputArray,sc_array;
var show_value,hide=false,variableType,nodeid;
var engineId =$("input[name='engineId']").val();
/**字段输入数据查询条件**/
var g_fieldManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.engineId = $("input[name='engineId']").val();
        param.opType=2;
        if(variableType==1){
            param.isOutput=0;
        }else{
            param.isOutput=1;
        }
        param.nodeId=nodeid;
        //自行处理查询参数
        param.fuzzySearch = g_fieldManage.fuzzySearch;
        if (g_fieldManage.fuzzySearch) {
            param.searchKey=$("#optionFields_list1Search").val();
        }

        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/**评分输入卡数据查询条件**/
var g_scoresssManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.engineId = $("input[name='engineId']").val();
        param.opType=1;
        if(variableType==1){
            param.isOutput=0;
        }else{
            param.isOutput=1;
        }
        param.nodeId=nodeid;
        //自行处理查询参数
        param.fuzzySearch = g_scoresssManage.fuzzySearch;
        if (g_scoresssManage.fuzzySearch) {

        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/**获取输入、输出变量数组*/
function getField(nodeId){
        $("#myTabContent1 .c-field-condition").children().remove();
        $(".l_before_input").val("");
        $(".c-field-condition").find("select").children().remove();
        nodeid=nodeId;
        Comm.ajaxPost("datamanage/field/getEngineFields",{"isOutput":0,"engineId":engineId},function (result) {
            fieldIsInputArray = result.data;
        },"","","","",false);
        Comm.ajaxPost("datamanage/field/getEngineFields",{"isOutput":1,"engineId":engineId},function (result) {
            fieldIsOutputArray = result.data;
        },"","","","",false);
        changeConditions();
        initOptions();
        if(fieldIsInputArray!=null){
            for (var i = 0; i < fieldIsInputArray.length; i++) {
                var key = "\@"+fieldIsInputArray[i].field.cnName+"\@";
                var value = "\#{"+fieldIsInputArray[i].field.enName+"\}";
                map.put(key,value);
            }
        }
}
/**设置字段**/
function setEquationField(){
    var arr = new Array();
    $("#d-option input").each(function(index,dom){
        arr.push({"name":$(dom).val()})
    })
    bindEquation(arr);
}
/**绑定@方法**/
function bindEquation(array){
    $.fn.atwho.debug = true;
    var atConfig = {
        at: "@",
        data: array,
        headerTpl: '<div class="atwho-header" style="text-align: center">字段列表</small></div>',
        limit: 200
    };
    $inputor = $('#inputor').atwho(atConfig);
    $(document).on("focus",".inputor",function() {
        $(".inputor").removeAttr("id");
        $(this).attr("id","inputor");
        $inputor = $('#inputor').atwho(atConfig);
    });
}
/**查看，编辑**/
function lookOrOperateForDecisionOption(nodeId){
    var input='<input type="text" name="result">';
    $("#decision_region .c-field-condition .c-inclusion:eq(0) .c-operation-inclusion").find("select").replaceWith(function () {
        return input;
    });
    var data = {"nodeId":nodeId};
    if(node_2.id!=0){
        Comm.ajaxPost("decision_flow/getNode",data,function(result){
            var data=result.data;
            if(data!=null){
                var nodeJson = $.parseJSON(data.nodeJson);
                if(nodeJson !=null && nodeJson!=''){
                    inputInit(nodeJson);
                    outputInit(nodeJson);
                    if(nodeJson.inputs == 1){
                        if(nodeJson.condition_type == 1){
                            initConditionInitOne(nodeJson);
                        }else{
                            $("#decision_region").removeClass("active");
                            $("#decision_region_tag").removeClass("selected_mark");
                            $("#equation_editing_tag").addClass("selected_mark");
                            $("#equation_editing").addClass("active");
                            outputChange(nodeJson.conditions,nodeJson.condition_type);
                            setEquationField();
                        }
                    }else if(nodeJson.inputs == 2){
                        if(nodeJson.condition_type == 1){
                            initConditionInitTwo(nodeJson);
                        }else{
                            $("#decision_regionTwo").removeClass("active");
                            $("#equation_editingTwo").addClass("active");
                            $("#decision_regionTwo_tag").removeClass("selected_mark")
                            $("#equation_editingTwo_tag").addClass("selected_mark")
                            outputChange(nodeJson.conditions,nodeJson.condition_type);
                            setEquationField();
                        }
                    }else{
                        $("#d-option-1").addClass("hidden");
                        $("#d-option-2").addClass("hidden");
                        $("#d-option-3").removeClass("hidden");
                        outputChange(nodeJson.conditions,nodeJson.condition_type);
                        setEquationField()
                    }
                }
            }
        })
    }
}
$("#d-option").delegate("input.l_before_input","click",function () {
    variableType = 1;
    showOptionsShow(this,1);
});
function showOptionsShow(me,sing) {
    var index1=layer.open({
        type : 1,
        title : '输入参数',
        area : [ '750px', '450px' ],
        content : $('#option_popups'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            var isShowOne=$("#showOne").css("display");
            var isShowTwo=$("#showTwo").css("display");
            if(isShowOne=="block"){
                var obj = $("#showOne").find("input:checked");
            }else{
                var obj = $("#showTwo").find("input:checked");
            }
            if(isShowOne=="block"&&obj.length>0){
                var val=$("#showFields1").html().split("/");
                var valuetype=$("#valueType1").html();
                var valuescope=$("#hiddenRestrainScope1").html();
                var fieldcode=$("#enName1").html();
                $(me).attr("value",val[1]);
                $(me).attr("dataid",val[0]);
                $(me).attr("valuetype",valuetype);
                $(me).attr("valuescope",valuescope);
                $(me).attr("fieldname",val[1]);
                $(me).attr("fieldcode",fieldcode);
                $(me).addClass("selected");
                var id = $(obj).attr("value");
                var valueType = $(obj).attr("valuetype");
                var valueScope = $(obj).attr("restrainscope");
                var fieldName = $(obj).attr("cname");
                var fieldKey = $(obj).attr("enname");
                if(variableType == 1){
                    var  conditionType = getCondition_type();
                    var len = $("#d-option input").length;
                    if( len ==1 && conditionType == 1){
                        var inputHtml = '<option dataid='+id+' valueType= '+valueType+' valueScope ='+valueScope+' value='+fieldKey+' >'+fieldName+'</opttion>';
                        $("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition").each(function(index,element){
                            $(element).find("select[name=field_code]").html("").html(inputHtml);
                            $(element).find("select[name=field_code]").attr("title",fieldName);
                            var field = $(element).find("select[name=field_code]").find("option:selected").get(0);
                            renderOptions(field);
                        });
                    }
                    if(conditionType == 2 || len >= 3){
                        setEquationField();
                    }
                    if( len == 2 &&  conditionType == 1){
                        var input_valueType = $("#d-option").find("input:eq(0)").attr("valueType");
                        var input_valuescope = $("#d-option").find("input:eq(0)").attr("valuescope");
                        var input_fieldname = $("#d-option").find("input:eq(0)").attr("fieldname");
                        $(".c-variate-a").text("").text(input_fieldname);
                        if(input_fieldname_1!=''){
                            $(".c-variate-a").text("").text(input_fieldname);
                        }
                        $("#d-option-2 .c-option-a").find('input,select').replaceWith(getInputOption(input_valueType,input_valuescope));
                        var input_valueType_1 = $("#d-option").find("input:eq(1)").attr("valueType");
                        var input_valuescope_1 = $("#d-option").find("input:eq(1)").attr("valuescope");
                        var input_fieldname_1 = $("#d-option").find("input:eq(1)").attr("fieldname");
                        if(input_fieldname_1!=''){
                            $(".c-variate-b").text("").text(input_fieldname_1);
                        }
                        $("#des-b").find('input,select').replaceWith(getInputOption(input_valueType_1,input_valuescope_1))
                    }
                }else{
                    outputChange(null,null);
                }
            }
            if(isShowTwo=="block"&&obj.length>0){
                var val=$("#showFields2").html().split("/");
                var valuetype=$("#valueType2").html();
                var fieldcode=$("#enName2").html();
                $(me).attr("value",val[1]);
                $(me).attr("dataid",val[0]);
                $(me).attr("valuetype",valuetype);
                $(me).attr("valuescope","");
                $(me).attr("fieldname",val[1]);
                $(me).attr("fieldcode",fieldcode);
                var inputHtml = '<option dataid='+val[0]+' valueType= '+valuetype+' valueScope ="" value='+fieldcode+' >'+val[1]+'</opttion>';
                $("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition").each(function(index,element){
                    $(element).find("select[name=field_code]").html("").html(inputHtml);
                    var field = $(element).find("select[name=field_code]").find("option:selected").get(0);
                    renderOptions(field);
                });
            }
            layer.close(index1);
        },
        success:function(){
            $("#showFields1").html("/");
            $("#valueType1").html("");
            $("#hiddenRestrainScope1").html("");
            $("#enName1").html("");
            $("#showFields2").html("/");
            $("#valueType2").html("");
            $("#hiddenRestrainScope2").html("");
            $("#enName2").html("");
            getFields();
        }
    });
}
$("#d-option-out input").click(function(){
    variableType = 2;
    showOptionsShow(this,2);
});
function initOptions(){
    var output_valueType = $("#d-option-out input").attr("valueType");
    var output_valuescope = $("#d-option-out input").attr("valuescope");
    appendOptions(output_valueType,output_valuescope);
    $("#decision_region .c-field-condition").append(newData);
    var field = $("#decision_region  .c-field-condition .c-optional-rules").eq(0).find('select[name=field_code] option:first').get(0);
    renderOptions(field);
}
/**切换条件区域内容**/
function changeConditions(){
    var length = $("#d-option").find('input').length;
    if(length == 1){
        $("#d-option-1").removeClass("hidden");
        $("#d-option-2").addClass("hidden");
        $("#d-option-3").addClass("hidden");
        $("#decision_region_tag").addClass("selected_mark");
        $("#equation_editing_tag").removeClass("selected_mark");
        $("#decision_region").addClass("active");
        $("#equation_editing").removeClass("active");
    }else if(length == 2){
        $("#d-option-1").addClass("hidden");
        $("#d-option-3").addClass("hidden");
        $("#d-option-2").removeClass("hidden").show();
        if($("#des-b .c-option-d").length <= 0){
            addOptionTwo();
        }
        if($(".c-option-a .c-front-portion").length <= 0){
            addOptionOne();
        }
        $("#decision_regionTwo_tag").addClass("selected_mark");
        $("#equation_editingTwo_tag").removeClass("selected_mark");
        $("#decision_regionTwo").addClass("active");
        $("#equation_editingTwo").removeClass("active");
    }else{
        $("#d-option-1").addClass("hidden");
        $("#d-option-2").addClass("hidden");
        $("#d-option-3").removeClass("hidden").show();
    }
    outputChange(null,null);
    hide=false;
}
/**添加输入变量**/
$("#add_option").unbind('click').click(function(){
    var str ='<div class="select_option" style="width:130px; float:left; margin-top:10px;"><input  data="" class="l_before_input input_variable"  style="width:124px;background-position:105px 0px;"></div>';
    $(".l_selects").append(str);
    $(".l_selects .select_option:last-child input").html('').html(getOptions());
    $("#d-option input").click(function(){
        variableType = 1;
        $("#ser-value").find('input').removeClass("selected");
        $("#option_popup").show();
        $(this).addClass("selected")
        $("#option_popup #option_fd").click();
        var text = $(this).val();
        $(".c-createusers-content input[type=radio][fieldName='"+text+"']").click();
    });
    changeConditions();
});
/**删除输入变量**/
$("#delete_option").click(function(){
    $("#d-option .l_selects .select_option:last-child").remove();
    changeConditions();
});
$("#decision_region_tag").click(function(){
    $(this).addClass("selected_mark")
    $("#equation_editing_tag").removeClass("selected_mark");
    outputChange(null,null);
});
$("#equation_editing_tag").click(function(){
    $(this).addClass("selected_mark")
    $("#decision_region_tag").removeClass("selected_mark");
    outputChange(null,null);
});
$("#decision_regionTwo_tag").click(function(){
    $(this).addClass("selected_mark")
    $("#equation_editingTwo_tag").removeClass("selected_mark");
    outputChange(null,null);
});
$("#equation_editingTwo_tag").click(function(){
    $(this).addClass("selected_mark");
    $("#decision_regionTwo_tag").removeClass("selected_mark");
    outputChange(null,null);
});
$(".shows").click(function(){
    if(hide==false){
        $(this).parent().next().show();
        $(this).parent().parent().css("height","265px");
        hide = true;
    }else{
        $(this).parent().next().hide();
        $(this).parent().parent().css("height","220px");
        hide = false;
    }
});
function outputChange(show_value,condition_type){
    if(condition_type == null){
        condition_type = getCondition_type();
    }
    var length = $("#d-option").find('input').length;
    var output_valueType = $("#d-option-out input").attr("valueType");
    var output_valuescope = $("#d-option-out input").attr("valuescope");
    if(condition_type == 2){
        setEquationField();
        if(length == 1 ){
            $("#d-option-1").find('.includeFormula').html('').html(addEquation(output_valueType,output_valuescope,show_value));
        }else if(length == 2){
            $("#d-option-2").find('.includeFormula').html('').html(addEquation(output_valueType,output_valuescope,show_value));
        }else if(length >= 3){
            $("#d-option-3").find('.includeFormula').html('').html(addEquation(output_valueType,output_valuescope,show_value));
        }
        setEquationField();
    }else{
        if(length == 1){
            getChange(output_valuescope,output_valueType);
        }else if(length == 2){
            $(".c-variate-a").text("").text($("#d-option").find('input').eq(0).val());
            $(".c-variate-b").text("").text($("#d-option").find('input').eq(1).val());
            $("#d-option-2 .c-option-c").find('input,select').replaceWith(getOutputOption())
        }
    }
}
/**公式部分添加输出**/
function addEquation(output_valueType,output_valuescope,show_value){
    var newEquation="";
    if(output_valueType == 3){
        var array =  output_valuescope.split(",");
        for (var i = 0; i < array.length; i++) {
            var subArray = array[i].split(":");
            newEquation +='<div class="formulas" style="overflow:hidden; height: 32px; float:left; margin-top:20px;">'+
                '<div class="leftOption" style="width: 200px; height:30px; float:left;">'+
                '<input type="text" data = "'+subArray[1]+'" value="'+subArray[0]+'" name = "result" style="width: 100px; font-size:12px; margin-left:20px;margin-right:10px;height:30px;border: 1px solid #e6e6e6;border-radius: 4px;text-align: center"/>'+
                '<span style="margin-top:35px;margin-left:14px;">=</span></div><div class="textArea" style="width: 270px; height: 30px; float:left;">';
                if(show_value!=null){
                    for (var j = 0; j < show_value.length; j++) {
                        if(subArray[1] == show_value[j].result){
                            newEquation +='<textarea id="inputor" name="formula" class="inputor" style="width: 260px; height: 30px; line-height:28px; resize:none;">'+show_value[j].formula_show+'</textarea>';
                        }
                    }
                }else{
                    newEquation +='<textarea id="inputor" name="formula" class="inputor" style="width: 260px; height: 30px; line-height:28px; resize:none;"></textarea>';
                }
                newEquation +='</div></div>';
            }
        }else{
            newEquation +='<div class="formulas" style="overflow:hidden; height: 32px; float:left; margin-top:20px;">'+
                '<input type="hidden" value="" name = "result" style="width: 100px; margin-left:20px;margin-right:10px;height:30px;border: 1px solid #e6e6e6;border-radius: 4px;text-align: center"/>';
            if(show_value!=null){
                newEquation +='<textarea id="inputor" name="formula" class="inputor" style=" margin-left:20px; width: 550px; height:170px;border:none; line-height:25px; resize:none;">'+show_value[0].formula_show+'</textarea>';
            }else{
                newEquation +='<textarea id="inputor" name="formula" class="inputor" style=" margin-left:20px; width: 550px; height:170px;border:none; line-height:25px; resize:none;"></textarea>';
            }
        newEquation +='</div>';
    }
    return newEquation;
}
var showRangeHtml='<div class="field-bounced-content">'+
    '<div class="field-bounced-section"></div><div class="field-bounced-table"><table>'+
    '<tr><td>区间</td><td>值</td><td>操作</td></tr></table></div></div>'+
    '<span id="checkSpan" style="margin:10px 0 0 23px;color:red;display: inline-block;"></span>'+
    '<div align="center" style="margin-top:30px;" class="areaButton"></div>';
function fieldsetting(){
    var text = subtext3;
    var EquationArrJson = [];
    var index2=layer.open({
        type: 1,
        area: ['500px', '300px'],
        btn : [ '保存', '取消' ],
        content:showRangeHtml,
        yes:function () {
            var intervalArray = new Array();
            var isEmpty=[];
            $(".field-bounced-content").find(".field-bounced-first").each(function(index,element){
                var content ='{';
                if($(element).find('select[name=segment] option:selected').prop("outerHTML") != undefined){
                    content += '"segment":"'+$(element).find('select[name=segment] option:selected').val();
                    intervalArray.push($(element).find('select[name=segment] option:selected').text());
                    content += '","segmentKey":"'+$(element).find('select[name=segment] option:selected').text();
                }else{
                    content += '"segment":"'+$(element).find('input[name=segment]').val();
                    intervalArray.push($(element).find('input[name=segment]').val())
                }
                content +='","value":"'+$(element).find('input[name=value]').val();
                isEmpty.push($(element).find("input[name=value]").val());
                content +='"}';
                EquationArrJson.push(content);
            });
            intervalArray=intervalArray.sort();
            for(var t=0;t<isEmpty.length;t++){
                if(!isEmpty[t]){
                    layer.msg("值不能为空！");
                    return;
                }
            }
            for(var i=0;i<intervalArray.length;i++){
                if (intervalArray[i] == intervalArray[i+1]){
                    layer.msg("区间有重叠,请核准!");
                    return ;
                }
            }
            if(valueType == 1){
                //判断区间范围是否在字段范围之内
                for(var j=0;j<intervalArray.length;j++){
                    if(!intervalArray[j]){
                        layer.msg("区间不能为空！");
                        return;
                    }
                }
                var numberValue = valueScope.split(",");
                var min =parseFloat( numberValue[0].substring(1));//字段最小值
                var max =parseFloat( numberValue[1].substring(0,numberValue[1].length-1));//字段最大值
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
                Comm.ajaxPost("datamanage/field/section",{"sections":intervalArray},function (result) {
                    var data=result.data;
                    if(data==0){
                        layer.close(index2);
                        layer.msg(result.msg);
                        $("#d-option input").each(function(index,element){
                            if($(element).val() == text){
                                $(element).attr("data","");
                                $(element).attr("data",EquationArrJson);
                            }
                        })
                    }else{
                        layer.msg(result.msg);
                        $("#d-option input").each(function(index,element){
                            if($(element).val() == text){
                                $(element).attr("data","");
                            }
                        });
                        return;
                    }
                });
            }else{
                layer.close(index2);
            }
        },
        success:function () {
            layer.close(layer_index);
        }
    });
    var valueType,valueScope,fieldCode,intervalJson;
    $("#d-option input").each(function(index,element){
        var fieldName = $(element).val();
        if(text == fieldName){
            valueType = $(element).attr("valueType");
            valueScope = $(element).attr("valueScope");
            fieldCode = $(element).attr("value");
            intervalJson = $(element).attr("data");
        }
    });
    if(intervalJson == ""){
        $(".field-bounced-content").append(addIntervalTwo(valueType,valueScope,fieldCode));
    }else{
        $(".field-bounced-content").append(addIntervalOne(valueType,valueScope,fieldCode,intervalJson));
    }
    $(document).unbind('click').on("click",".add_interval",function(e){
        e.stopPropagation();
        $(".field-bounced-content").append(addIntervalTwo(valueType,valueScope,fieldCode));
    });
    $(document).on("click",".delete_interval",function(e){
        e.stopPropagation();
        $(e.target).parent().parent().remove();
    });
}
function addIntervalOne(valueType,valueScope,fieldCode,intervalJson){
    var intervalJsonString = "["+intervalJson+"]";
    var intervalJsonArray = JSON.parse(intervalJsonString);
    var html ="";
    if(intervalJsonArray.length>0){
        for (var i = 0; i < intervalJsonArray.length; i++) {
            html +='<div class="field-bounced-first"><div class="c-bounced-grid">';
            if(valueType == 3){
                html +='<select name="segment" class="l_before datas" style="width:80px;height:22px;margin: 5px 0 0 5px">';
                var array =  valueScope.split(",");
                for (var j = 0; j < array.length; j++) {
                    var subArray = array[j].split(":");
                    if(intervalJsonArray[i].segment == subArray[1]){
                        html +='<option selected="selected" value="'+subArray[1]+'">'+subArray[0]+'</option>'
                    }else{
                        html +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>'
                    }
                }
                html +='<select>'
            }else{
                html +='<input class="c-inputOne" name="segment" value="'+intervalJsonArray[i].segment+'" type="text"/>';
            }
            html+='</div><div class="c-bounced-grid">'+
                '<input class="c-inputThree" name="value" value="'+intervalJsonArray[i].value+'" type="text"/></div>'+
                '<div class="c-bounced-grid">'+
                '<img src='+path+'/resources/images/rules/add.png class="add_interval"/>'+
                '<img src='+path+'/resources/images/rules/delete.png style="margin-left: 16px;"  class="delete_interval"/>'+
                '</div></div>';
        }
    }
    return html;
}
function addIntervalTwo(valueType,valueScope,fieldCode){
    var html="";
    html +='<div class="field-bounced-first"><div class="c-bounced-grid">';
    if(valueType == 3){
        html +='<select name="segment" class="l_before datas" style="width:80px;height:22px;margin: 5px 0 0 5px">';
        var array =  valueScope.split(",");
        for (var j = 0; j < array.length; j++) {
            var subArray = array[j].split(":");
            html +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>'
        }
        html +='<select>';
    }else{
        html +='<input class="c-inputOne" name="segment" value="" type="text"/>';
    }
    html+='</div><div class="c-bounced-grid"><input class="c-inputThree" name="value" value="" type="text"/>'+
        '</div><div class="c-bounced-grid">'+
        '<img src='+path+'/resources/images/rules/add.png class="add_interval"/>' +
        '<img src='+path+'/resources/images/rules/delete.png style="margin-left: 16px;"  class="delete_interval"/></div></div>';
    return html;
}
$(document).on("click",".formulaRemove",function(){
    $(this).parents(".formulas").remove();
});
function getChange(valuescope,valueType){
    var html="";
    if(valueType == 3){
        var array =  valuescope.split(",");
        for (var i = 0; i < array.length; i++) {
            var subArray = array[i].split(":");
            html +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>';
        }
        var obj = $("#decision_region .c-field-condition").find(".c-inclusion .c-field-return select[name=result]")
        if(obj.length > 0){
            $(obj).html('').html(html);
        }else{
            var html= '<select class="l_before datas" name="result" style="width:60px">'+ html+ '</select>';
            $("#decision_region .c-field-condition").find(".c-inclusion .c-field-return input[name=result]").replaceWith(html);
        }
    }else{
        var obj = $("#decision_region .c-field-condition").find(".c-inclusion .c-field-return input[name=result]")
        if(obj.length > 0){
            $(obj).val();
        }else{
            var html = '<input type="text" name="result">';
            $("#decision_region .c-field-condition").find(".c-inclusion .c-field-return select[name=result]").replaceWith(html);
        }
    }
    $("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition select[name=field_code]").html('').html(getOptions());
    var field =  $("#decision_region .c-field-condition").find(".c-inclusion:eq(0)").find(".c-optional-rules").find(".c-repetition:last-child").find('select[name=field_code] option:selected').get(0);
    renderOptions(field);
}
function addOptionOne(){
    var input_valueType = $("#d-option").find("input:eq(0)").attr("valueType");
    var input_valuescope = $("#d-option").find("input:eq(0)").attr("valuescope");
    var html='<div class="c-front-portion" style="padding-top:5px;">'+
        '<img src='+path+'/resources/images/rules/add.png  class="l_op_a_addData"/>'+
        '<img src='+path+'/resources/images/rules/delete.png style="margin:0 3px 0 10px;"  class="l_op_a_delData"/>'+
        getInputOption(input_valueType,input_valuescope)+'</div>';
    $("#d-option-2 .c-option-a").append(html);
    var length = $(".c-option-d").length;
    var htmlTwo ="";
    var output_option = getOutputOption();
    htmlTwo +='<div class="c-queen-portion" style="padding-left:78px;">';
    for (var p = length; p > 0; p--) {
        htmlTwo  +='<div class="c-option-e"><div class="turn-down">'+ output_option+ '</div></div>';
    }
    htmlTwo += '</div>';
    $("#d-option-2 .c-option-c").append(htmlTwo);
}
function getInputOption(input_valueType,input_valuescope){
    var optionHtml="";
    if(input_valueType != 3){
        optionHtml +='<input type="text" name="input"/>'
    }else{
        var arr =  input_valuescope.split(",");
        optionHtml += '<select name="input" class="l_before_option">';
        for (var i = 0; i < arr.length; i++) {
            var subArr = arr[i].split(":");
            optionHtml +='<option value="'+subArr[1]+'">'+subArr[0]+'</option>';
        }
        optionHtml +='</select>';
    }
    return optionHtml;
}
function getOutputOption(){
    var outputValueType = $("#d-option-out").find("input:eq(0)").attr("valueType");
    var outputValuescope = $("#d-option-out").find("input:eq(0)").attr("valuescope");
    var outOptions = "";
    if(outputValueType != 3){
        outOptions +='<input type="text" name="result"  />'
    }else{
        var arr =  outputValuescope.split(",");
        outOptions +='<select name="result" class="l_before_option">';
        for (var i = 0; i < arr.length; i++) {
            var subArr = arr[i].split(":");
            outOptions +='<option value="'+subArr[1]+'">'+subArr[0]+'</option>';
        }
        outOptions +="</select>";
    }
    return outOptions;
}
$("#d-option-2").on('click','.c-option-a .l_op_a_addData',function(){
    addOptionOne();
});
$("#d-option-2").on('click','.c-option-a .l_op_a_delData',function(){
    var index = $(this).parent().index();
    $(this).parent().remove();
    $("#d-option-2 .c-option-c").find('.c-queen-portion').eq(index).remove();
});
$("#d-option-2").on('click','#des-b .l_op_b_addData',function(){
    addOptionTwo();
});
function addOptionTwo(){
    var input_valueType = $("#d-option").find("input:eq(1)").attr("valueType");
    var input_valuescope = $("#d-option").find("input:eq(1)").attr("valuescope");
    var html = '<div class="c-option-d">'+
        '<div class="c-section-right" style="padding-left:5px; font-family: sans-serif; font-size:14px;">'+
        getInputOption(input_valueType,input_valuescope)+ '</div></div>';
        $("#des-b").append(html);
        var htmlTwo ='<div class="c-option-e"><div class="turn-down">'+ getOutputOption()+ '</div></div>';
        $(".c-option-c .c-queen-portion").append(htmlTwo);
}
$("#d-option-2").on('click','#des-b .l_op_b_delData',function(){
    $("#des-b").find('.c-option-d:last-child').remove();
    $(".c-option-c .c-queen-portion").each(function(index,element){
        $(element).find('.c-option-e:last-child').remove();
    });
});
function getCondition_type(){
    var inputLength  = $("#d-option").find("input").length;
    var conditionType;
    if(inputLength == 1){
        if($("#decision_region_tag").hasClass("selected_mark")){
            conditionType = 1; //公式编辑
        }else{
            conditionType = 2 ;  //决策区域
        }
    }else if(inputLength == 2){
        if($("#decision_regionTwo_tag").hasClass("selected_mark")){
            conditionType = 1;
        }else{
            conditionType = 2 ;
        }
    }else if(inputLength == 3){
        conditionType = 2;
    }
    return conditionType;
}
/**转json**/
function getFormulaJson(obj){
    var html="";
    $("#"+obj).find(".includeFormula .formulas").each(function(index,element){
        if($("#d-option-out").find("input").attr("valueType") == 3){
            html += '{"result":"'+$(element).find("input[name=result]").attr("data");
            html += '","resultKey":"'+$(element).find("input[name=result]").val();
        }else{
            html += '{"result":"'+$(element).find("input[name=result]").val()
        }
        var formula = $(element).find(".inputor").val();
        html += '","formula":"'+replaceField(formula.replace(" ",""));
        html += '","formula_show":"'+formula;
        html += '"},'
    });
    html = html.substring(0,html.length -1);
    return html;
}
function replaceField(str){
    var reg=/@[a-zA-Z0-9_\u4e00-\u9fa5()（）-]+@/;
    var testReg = new RegExp(reg,'g');
    var result=str.match(testReg);
    if(result !=null && result.length>0){
        for (var i=0;i<result.length;i++) {
            str=str.replace(reg,map.get(result[i]));
        }
    }
    return str;
}
function assembledJson_1(){
    var html="";
    $("#decision_region .c-field-condition .c-inclusion").each(function(index_1,element_1){
        var subHtml ="[";
        $(element_1).find(".c-optional-rules .c-repetition").each(function(index,element){
            var code = $(element).find('select[name=field_code] option:selected').val();
            if(code.indexOf("SC_") > -1 && code.indexOf("_score") == -1){
                code = code+"_score";
            }
            var content  = '{"field_code":"'+code;
            content  += '","operator":"'+$(element).find('select[name=operator] option:selected').val();
            if($(element).find('select[name=field_code] option:selected').attr("valueType") == 3){
                content  += '","result":"'+$(element).find('select[name=result] option:selected').val();
                content  += '","resultKey":"'+$(element).find('select[name=result] option:selected').text();
            }else{
                content  += '","result":"'+$(element).find('input[name=result]').val();
                content  += '","resultKey":"'+$(element).find('input[name=result]').val();
            }
            if(index == 0){
                content  += '"}';
            }else{
                content  += '","sign":"'+$(element).prev().find('select[name=sign] option:selected').val()+'"}';
            }
            subHtml +=content+","
        });
        subHtml = subHtml.substring(0,subHtml.length -1);
        subHtml = subHtml +"]";
        var subContent="";
        if($("#d-option-out").find('input').attr("valueType") == 3){
            var result=$(element_1).find('.c-operation-inclusion select[name="result"] option:selected').val();
            var resultKey=$(element_1).find('.c-operation-inclusion select[name="result"] option:selected').text();
            subContent = '{"result":"'+result+'","resultKey":"'+resultKey+'","formula":'+subHtml+'}'
        }else{
            var result = $(element_1).find('.c-operation-inclusion input[name="result"]').val();
            var resultKey=$(element_1).find('.c-operation-inclusion input[name="result"]').val();
            subContent = '{"result":"'+result+'","resultKey":"'+resultKey+'","formula":'+subHtml+'}'
        }
        html +=subContent+","
    })
    html = html.substring(0,html.length -1);
    return html;
}
function assembledJson_2(){
    var fieldCodeOne = $("#d-option").find("input:eq(0)").attr("fieldCode");
    if(fieldCodeOne.indexOf("SC_") > -1){
        fieldCodeOne = fieldCodeOne+"_score";
    }
    var valueTypeOne = $("#d-option").find("input:eq(0)").attr("valueType");
    var fieldCodeTwo = $("#d-option").find("input:eq(1)").attr("fieldCode");
    if(fieldCodeTwo.indexOf("SC_") > -1){
        fieldCodeTwo = fieldCodeTwo+"_score";
    }
    var valueTypeTwo = $("#d-option").find("input:eq(1)").attr("valueType");
    var outputValueType = $("#d-option-out").find("input:eq(0)").attr("valueType");
    var string = "";
    $("#d-option-2").find(".c-option-a .c-front-portion").each(function(index,element){
        $("#des-b .c-option-d").each(function(index_1,element_1){
            if(outputValueType == 3){
                string += '{"result":"'+$("#d-option-2 .c-option-c .c-queen-portion").eq(index).find(".c-option-e").eq(index_1).find("select[name=result] option:selected").val();
                string += '","resultKey":"'+$("#d-option-2 .c-option-c .c-queen-portion").eq(index).find(".c-option-e").eq(index_1).find("select[name=result] option:selected").text()
            }else{
                string += '{"result":"'+$("#d-option-2 .c-option-c .c-queen-portion").eq(index).find(".c-option-e").eq(index_1).find("input[name=result]").val();
                string += '","resultKey":"'+$("#d-option-2 .c-option-c .c-queen-portion").eq(index).find(".c-option-e").eq(index_1).find("input[name=result]").val()
            }
            string += '","index":"'+index+','+index_1;
            string += '","formula":{"field_code1":"'+fieldCodeOne;
            string += '","expression1":"';
            if(valueTypeOne == 3){
                string += $(element).find("select[name='input'] option:selected").val()
                string += '","expression1Key":"'+$(element).find("select[name='input'] option:selected").text()
            }else{
                string += $(element).find("input[name='input']").val()
            }
            string +='","field_code2":"'+fieldCodeTwo;
            string += '","expression2":"';
            if(valueTypeTwo == 3){
                string += $(element_1).find("select[name='input'] option:selected").val()
                string += '","expression2Key":"'+$(element).find("select[name='input'] option:selected").text()
            }else{
                string += $(element_1).find("input[name='input']").val()
            }
            string +='"}},'
        });
    });
    string = string.substring(0,string.length -1);
    return string;
}
/**初始化回显输入变量**/
function inputInit(nodeJson){
        var inputArray = nodeJson.input;
        for (var i = 0; i < inputArray.length; i++) {
            var segemnts="";
            if(nodeJson.condition_type == 2){
                segemnts = JSON.stringify(inputArray[i].segemnts);
                segemnts = segemnts.substring(1,segemnts.length-1);
            }
            var  fieldCode = inputArray[i].field_code;
            if(fieldCode.indexOf("_score") > -1){
                fieldCode = fieldCode.substring(0,fieldCode.lastIndexOf("_score"));
            }
            if(i == 0){
                var selectedObj =  $("#d-option").find('input:eq(0)');
                $(selectedObj).attr("data",segemnts);
                $(selectedObj).attr("dataId",inputArray[i].field_id);
                $(selectedObj).attr("valueType",inputArray[i].field_type);
                $(selectedObj).attr("valueScope",inputArray[i].field_scope);
                $(selectedObj).attr("fieldName",inputArray[i].field_name);
                $(selectedObj).attr("fieldCode",fieldCode);
                $(selectedObj).attr("value",inputArray[i].field_name);
            }else{
                var str = '<div class="select_option" style="width:130px; float:left; margin-top:10px;">'+
                    '<input fieldName="'+inputArray[i].field_name+'"  dataId="'+inputArray[i].field_id+'" valueType="'+inputArray[i].field_type+'" value="'+inputArray[i].field_name+'" valueScope="'+inputArray[i].field_scope+'" fieldCode="'+fieldCode+'" class="l_before_input input_variable"';
                if(segemnts !=''){
                    str +='data='+segemnts
                }else{
                    str +='data=""';
                }
                str +='style="width:124px;background-position:100px 0px;"></input></div>';
                $(".l_selects").append(str);
                $("#d-option input").click(function(){
                    variableType = 1;
                    $("#ser-value").find('input').removeClass("selected");
                    $("#option_popup").show();
                    $(this).addClass("selected");
                    $("#option_popup #option_fd").click();
                    $(".c-createusers-content input[type=radio][fieldName='"+$(this).val()+"']").click();
                });
            }
        }
        changeConditions();
}
/**输出变量回显初始化***/
function outputInit(nodeJson){
    var output =nodeJson.output;
    var selectedObj = $("#d-option-out").find('input:eq(0)');
    $(selectedObj).attr("dataId",output.field_id);
    $(selectedObj).attr("valueType",output.field_type);
    $(selectedObj).attr("valueScope",output.field_scope);
    $(selectedObj).attr("fieldName",output.field_name);
    $(selectedObj).attr("fieldCode",output.field_code);
    $(selectedObj).attr("value",output.field_name);
}
/**初始化条件区域回显**/
function initConditionInitOne(nodeJson){
    var input='<input type="text" name="result">';
    $("#decision_region .c-field-condition .c-inclusion:eq(0) .c-operation-inclusion").find("select").replaceWith(function () {
        return input;
    });
    $("#decision_region .c-field-condition .c-inclusion:eq(0)").find("select").html('').find('input').val('');
    $("#decision_region .c-field-condition .c-inclusion:gt(0)").remove();
    var inputArray = nodeJson.input;
    var output  = nodeJson.output;
    var conditions = nodeJson.conditions;
    var inputHtml = '<option dataid="'+inputArray[0].field_id+'" valueType= "'+inputArray[0].field_type+'" valueScope ="'+inputArray[0].field_scope+'" value="'+inputArray[0].field_code+'" >'+inputArray[0].field_name+'</opttion>';
    for (var i = 0; i < conditions.length; i++) {
        var dom = $("#decision_region .c-field-condition .c-inclusion:last-child");
        if(output.field_type == 3){
            var domOne =$(dom).parent().parent('.c-repetition').find('select[name ="result"]');
            if($(domOne).prop("outerHTML") == undefined){
                $(dom).find('.c-operation-inclusion').find('input[name ="result"]').replaceWith(function(){
                    var str="";
                    var array =  output.field_scope.split(",");
                    for (var i = 0; i < array.length; i++) {
                        var subArray = array[i].split(":")
                        str +='<option value="'+subArray[1]+'">'+subArray[0]+'</option>';
                    }
                    return '<select class="l_before datas" name="result" style="width:60px" value="1">'+str+'</select>'})
            }
            $(dom).find(".c-operation-inclusion select[name=result] option[value="+conditions[i].result+"]").attr("selected","selected");
        }else{
            $(dom).find(".c-operation-inclusion input[name=result]").val(conditions[i].result);
        }
        if(i != conditions.length-1){
            $(dom).find(".c-operation-inclusion .l_addData").click();
        }
        var fieldFormulaArr = conditions[i].formula;
        for (var t = 0; t < fieldFormulaArr.length; t++) {
            var subDom = $(dom).find(".c-optional-rules .c-repetition:last-child");
            $(subDom).find('select[name=field_code]').html('').html(inputHtml);
            renderOptions($(subDom).find('select[name=field_code] option:eq(0)').get(0));
            $(subDom).find('select[name=operator] option[value="'+fieldFormulaArr[t].operator+'"]').attr("selected", "selected");
            if(inputArray[0].field_type == 3){
                $(subDom).find('select[name=result] option[value="'+fieldFormulaArr[t].result+'"]').attr("selected", "selected");
            } else{
                $(subDom).find('input[name=result]').val(fieldFormulaArr[t].result);
            }
            if(t != fieldFormulaArr.length-1){
                $(subDom).find('.l_addData2').click();
            }
            if(t != 0){
                $(subDom).prev().find('select[name=sign] option[value="'+fieldFormulaArr[t].sign+'"]').attr("selected", "selected");
            }
        }
    }
}
/**当输入变量有两个时条件区域回显初始化**/
function initConditionInitTwo(nodeJson){
    var val;
    var output = nodeJson.output;
    var input = nodeJson.input;
    var conditions = nodeJson.conditions;
    $(".c-variate-a").text("").text(input[0].field_name);
    $(".c-variate-b").text("").text(input[1].field_name);
    for (var i = 0; i < conditions.length; i++) {
        var obj = conditions[i];
        var index = obj.index;
        var formula = obj.formula;
        var array = index.split(",");
        var input_1 = $("#d-option-2").find(".c-option-a .c-front-portion:eq("+parseInt(array[0])+")");
        if($(input_1).length == 0){
            $("#d-option-2 .c-option-a .c-front-portion:first-child .l_op_a_addData").click();
            input_1 = $("#d-option-2").find(".c-option-a .c-front-portion:eq("+parseInt(array[0])+")");
        }
        if(input[0].field_type == 3 ){
            $(input_1).find('select option[value='+formula.expression1+']').attr('selected','selected');
        }else{
            $(input_1).find("input").val(formula.expression1);
        }
        var input_2 = $("#des-b .c-option-d:eq("+parseInt(array[1])+")");
        if($(input_2).length == 0){
            $("#des-b .l_op_b_addData").click();
            input_2 = $("#des-b .c-option-d:eq("+parseInt(array[1])+")")
        }
        if(input[1].field_type == 3 ){
            $(input_2).find("select option[value="+formula.expression2+"]").attr("selected","selected");
        }else{
            $(input_2).find("input").val(formula.expression2);
        }
        if(output.field_type == 3){
            $(".c-option-c .c-queen-portion:eq("+parseInt(array[0])+")").find(".c-option-e:eq("+parseInt(array[1])+")").find("select option[value="+obj.result+"]").attr("selected","selected");
        }else{
            $(".c-option-c .c-queen-portion:eq("+parseInt(array[0])+")").find(".c-option-e:eq("+parseInt(array[1])+")").find("input").val(obj.result);
        }
        if(obj.index == '0,0'){
            val =obj.result
        }
    }
    if(output.field_type == 3){
        $(".c-option-c .c-queen-portion:eq(0)").find(".c-option-e:eq(0)").find('input,select').replaceWith(getOutputOption());
        $(".c-option-c .c-queen-portion:eq(0)").find(".c-option-e:eq(0)").find("select option[value="+val+"]").attr("selected","selected");
    }else{
        $(".c-option-c .c-queen-portion:eq(0)").find(".c-option-e:eq(0)").find('input,select').replaceWith(getOutputOption())
        $(".c-option-c .c-queen-portion:eq(0)").find(".c-option-e:eq(0)").find("input").val(val);
    }
}
/**字段输入数据**/
function getFields(){
    var isShowOptionFileds1=$("#isShowOptionFileds1").val();
    if(isShowOptionFileds1){
        g_fieldManage.tableUser.ajax.reload();
    }else{
        g_fieldManage.tableUser = $('#optionFields_list1').dataTable($.extend({
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
                var queryFilter = g_fieldManage.getQueryCondition(data);
                Comm.ajaxPost('decision_flow/getFieldOrScorecardForOption',JSON.stringify(queryFilter),function(result){
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    returnData.data = resData;
                    callback(returnData);
                },"application/json")
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
                        return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" cName="'+data.cnName+'" enName="'+data.enName+'" valueType="'+data.valueType+'" restrainScope="'+data.restrainScope+'">'}
                },
                {"data": "cnName","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#btn_search_option1").click(function() {
                    g_fieldManage.fuzzySearch = true;
                    g_fieldManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset_option1").click(function() {
                    $("#optionFields_list1Search").val("");
                    g_fieldManage.fuzzySearch = true;
                    g_fieldManage.tableUser.ajax.reload();
                });
                $("#optionFields_list1 tbody").delegate( 'tr input','click',function(e){
                    var isChecked=$(this).attr('isChecked');
                    var selectArray = $("#optionFields_list1 tbody input:checked");
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
                    var fieldsId= $(this).val();
                    var fieldsHtml=$(this).parent().next().html();
                    $("#showFields1").html(fieldsId+'/'+fieldsHtml);
                    $("#hiddenFields1").html(fieldsId+'/'+fieldsHtml);
                    $("#valueType1").html($(this).attr('valueType'));
                    $("#hiddenRestrainScope1").html($(this).attr('restrainScope'));
                    $("#enName1").html($(this).attr('enName'));
                });
                $("#optionFields_list1 tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        if(!target.parentNode.children[1]){
                            return;
                        }
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        var selectArray = $("#optionFields_list1 tbody input:checked");
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
                        var fieldsId= $(input).val();
                        var fieldsHtml=$(input).parent().next().html();
                        $("#showFields1").html(fieldsId+'/'+fieldsHtml);
                        $("#hiddenFields1").html(fieldsId+'/'+fieldsHtml);
                        $("#valueType1").html($(input).attr('valueType'));
                        $("#hiddenRestrainScope1").html($(input).attr('restrainScope'));
                        $("#enName1").html($(input).attr('enName'));
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_fieldManage.tableUser.on("order.dt search.dt", function() {
            g_fieldManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        $("#isShowOptionFileds1").val(1);
    }
}
/**评分输入卡数据**/
function getscores(){
    var isShowOptionFileds2=$("#isShowOptionFileds2").val();
    if(isShowOptionFileds2){
        g_scoresssManage.tableUser.ajax.reload();
    }else{
        g_scoresssManage.tableUser = $('#optionFields_list2').dataTable($.extend({
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
                var queryFilter = g_scoresssManage.getQueryCondition(data);
                Comm.ajaxPost('decision_flow/getFieldOrScorecardForOption',JSON.stringify(queryFilter),function(result){
                    var returnData = {};
                    var resData = result.data.list;
                    var resPage = result.data;
                    returnData.draw = data.draw;
                    returnData.recordsTotal = resPage.total;
                    returnData.recordsFiltered = resPage.total;
                    if(resData){
                        sc_array = resData;
                        if(sc_array!=null){
                            for (var i = 0; i < sc_array.length; i++) {
                                var key = "\@"+sc_array[i].name+"\@";
                                var value = "\#{"+sc_array[i].code+"\}";
                                map.put(key,value);
                            }
                        }
                        returnData.data = resData;
                        callback(returnData);
                    }
                },"application/json")

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
                        return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" cName="'+data.name+'" valuetype="1"   enName="'+data.code+'">'
                    }
                },
                {"data": "name","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#btn_search_score").click(function() {
                    g_scoresssManage.fuzzySearch = true;
                    g_scoresssManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset_score").click(function() {
                    $('input[name="Parameter_search"]').val("");
                    g_scoresssManage.fuzzySearch = true;
                    g_scoresssManage.tableUser.ajax.reload();
                });
                $("#optionFields_list2 tbody").delegate( 'tr input','click',function(e){
                    var isChecked=$(this).attr('isChecked');
                    var selectArray = $("#optionFields_list2 tbody input:checked");
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
                    var fieldsId= $(this).val();
                    var fieldsHtml=$(this).parent().next().html();
                    $("#showFields2").html(fieldsId+'/'+fieldsHtml);
                    $("#hiddenFields2").html(fieldsId+'/'+fieldsHtml);
                    $("#enName2").html($(this).attr('enName'));
                    $("#valueType2").html($(this).attr('valuetype'));
                });
                $("#optionFields_list2 tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        var selectArray = $("#optionFields_list2 tbody input:checked");
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
                        var fieldsId= $(input).val();
                        var fieldsHtml=$(input).parent().next().html();
                        $("#showFields2").html(fieldsId+'/'+fieldsHtml);
                        $("#hiddenFields2").html(fieldsId+'/'+fieldsHtml);
                        $("#enName2").html($(input).attr('enName'));
                        $("#valueType2").html($(input).attr('valuetype'));
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_scoresssManage.tableUser.on("order.dt search.dt", function() {
            g_scoresssManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        $("#isShowOptionFileds2").val(1);
    }
}



