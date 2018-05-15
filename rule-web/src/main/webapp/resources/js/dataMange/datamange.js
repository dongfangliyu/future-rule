/****页面初始化加载所有表的内容***/
$(function(){
    queryList();
    $("#btn").show();
   /***@输出出现下拉框*/
    var param = {};
    var queryFilter = {};
    param.engineId = $("#engineId").val();
    queryFilter.param = param;
    var array = new Array();
    Comm.ajaxPost("datamanage/field/findFieldByUser",JSON.stringify(queryFilter),function(result){
        var resData = result.data.fieldList;
        for (var i = 0; i < resData.length; i++) {
            array.push(resData[i].cnName);
        }
    },"application/json","","","",false);
    $.fn.atwho.debug = true;
    var atConfig = {
        at: "@",
        data: array,
        headerTpl: '<div class="atwho-header">字段列表</div>',
        limit: 200
    };
    $(".atwho-header").parent().css("top","495px").css("left","105px");
    $inputor = $('#inputor').atwho(atConfig);
    $(document).on("focus",".inputor",function() {
        $(".inputor").removeAttr("id");
        $(this).attr("id","inputor");
        $inputor = $('#inputor').atwho(atConfig);
    });
});
/***选择项***/
var logical = '<option value="&&">and</option><option value="||">or</option>'+
    '<option value="(">(</option><option value=")">)</option>'+
    '<option value="&&(">and(</option><option value=")&&">)and</option>'+
    '<option value="(">(</option><option value="||(">or(</option>'+
    '<option value=")||">)or</option><option value=")||(">)or(</option>'+
    '<option value="空置">空置</option>';
var  digitalType = '<option value=">">大于</option>'+
    '<option value="<">小于</option><option value=">=">大于等于</option>'+
    '<option value="<=">小于等于</option><option value="==">等于</option>'+
    '<option value="!=">不等于</option>';
var  characterType = '<option value="contains">like</option>'+
    '<option value="not contains">not like</option><option value="==">等于</option>'+
    '<option value="!=">不等于</option>';
var  enumerationType = '<option value="==">等于</option><option value="!=">不等于</option>';
/**衍生字段条件区域第一，二个下拉值**/
var whereSelectValue1=null;
var whereSelectValue2=null;
var dataSelectList=null;
var dataSelectListHtml="";
/**枚举选项值**/
var enumerationselect={push:Array.prototype.push};
/**枚举选项值Html**/
var  enumerationselectHtml="";
/**新增编辑字段**/
function addData(sign,id){
    if(sign=="1") {
        InitDom(null,id);
        layer.open({
            type: 1,
            title: '添加字段',
            maxmin: true,
            shadeClose: true,
            area: ['650px', '468px'],
            content: $('#Add_data_style'),
            btn: ['保存', '取消'],
            yes: function (index, layero) {
                //校验是否通过标志
                var validFlag = false;
                var _fieldEn = $("#en_name").val();
                var _fieldCn = $("#cn_name").val();
                if (validateFieldEnName()) {
                    if (validateFieldCnName()) {
                        if (validateValueScope()) {
                            validFlag = true;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
                var IsChecked=true;
                $(".c-inclusion").each(function () {
                    $(this).find("input[name='conditionValue']:not(:hidden),input[name='fieldValue']").each(function (index, element) {
                            if(!$(element).val()){
                                IsChecked=false;
                            }
                    })
                });
                // if(!IsChecked&&$("#derivedFields").css('display')=="block"){
                //     layer.msg("请填写条件区域内容!");
                //     return;
                // }
                //先同步select框里的value值到隐藏字段
                $("#fieldTypeId").val($("#value_type").val());
                //字段条件区域序列化
                var str = "[";
                if ($("#isDerivative").val() == "1") {
                    $(".c-inclusion").each(function () {
                        var substr = "[";
                        $(this).find(".c-optional-rules .c-optional-rules-rules").each(function (index, element) {
                            if ($(element).find('select').val() != '' && $(element).find('input').val() != '') {
                                //content = $(element).find('select,input').serializeJSON();
                                var namelist = $(element).find('select,input');
                                var content = "{";
                                for (var j = 0; j < namelist.length; j++) {
                                    if ($($(namelist)[j]).css("display") != "none") {
                                        var name = $(namelist)[j].getAttribute("name");
                                        var value = $($(namelist)[j]).val();
                                        if (name) {
                                            if (value == null) {
                                                value = "";
                                            }
                                            content += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                        }
                                    }
                                }
                                substr += content.substring(0, content.length - 1) + "},";
                            }
                        });
                        substr = substr.substring(0, substr.length - 1);
                        substr = substr + "]";
                        if (substr == "[]")
                            substr = "";

                        $(this).find('.c-operation-inclusion input[name = fieldContent2]').val(substr);

                        var subContent = "";
                        if ($(this).find('.c-operation-inclusion select').val() != '' && $(this).find('.c-operation-inclusion input').val() != '') {
                            var namelist = $(this).find('.c-operation-inclusion select,input');
                            subContent = subContent + "{";
                            for (var j = 0; j < namelist.length; j++) {
                                if ($($(namelist)[j]).css("display") != "none") {
                                    var name = $(namelist)[j].getAttribute("name");
                                    var value = $($(namelist)[j]).val();
                                    if (name) {
                                        if (value == null) {
                                            value = "";
                                        }
                                        if (name == "fieldValue") {
                                            if ((j + 1) == namelist.length) {
                                                subContent += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                            }
                                        } else {
                                            subContent += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                        }
                                    }
                                }
                                else {
                                    if ("fieldContent2" == $(namelist)[j].getAttribute("name")) {
                                        var name = $(namelist)[j].getAttribute("name");
                                        var value = $($(namelist)[j]).val();
                                        if (name) {
                                            if (value == null) {
                                                value = "";
                                            }
                                            subContent += "\"" + name + "\"" + ":" +  value  + ",";
                                        }
                                    }
                                }
                            }
                            subContent = subContent.substring(0, subContent.length - 1) + "},";
                        }
                        str += subContent;
                    });
                }
                str = str.substring(0, str.length - 1);
                if (str != "") {
                    str = str + "]";
                }
                if (str == "[]" || $("#isDerivative").val() == 0) {
                    str = "";
                }

                $("#fieldContent").val(str);
                // if(!IsChecked&&$("#derivedFields").css('display')=="block"){
                //     layer.msg("请填写条件区域内容!");
                //     return;
                // }

                //字段公式区域序列化
                if ($("#isDerivative").val() == 1) {
                    var gsValue = "[";
                    //$("input[name = 'formulaHidden']").val(fStr);
                    if ($("#valueType").val() == "3") {
                        //[{"fvalue":"","formula":"12231235453*s++123456qrt()ln()","idx":"0","farr":""}]"
                        //gsValue="{\"fvalue\":\"\",\"formula\":\""+$("#inputor").val()+"\",\"idx\":\"0\",\"farr\":\"\"}]";
                        var idx = 0;
                        $(".editWhere2").each(function () {
                            if ($(this).find('select').val() != '' && $(this).find('input').val() != '') {
                                var namelist = $(this).find('select,input');
                                var content = "{";
                                for (var j = 0; j < namelist.length; j++) {
                                    if ($($(namelist)[j]).css("display") != "none") {
                                        var name = $(namelist)[j].getAttribute("name");
                                        var value = $($(namelist)[j]).val();
                                        if (name) {
                                            if (value == null) {
                                                value = "";
                                            }
                                            content += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                        }
                                    }
                                    else {
                                        debugger
                                        var name = $(namelist)[j].getAttribute("name");
                                        var value = $($(namelist)[j]).val();
                                        if (name == "farr") {
                                            if (value == null) {
                                                value = "";
                                            }
                                            content += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                        }
                                    }
                                }
                                content += "\"idx\":\"" + idx + "\",";
                                gsValue += content.substring(0, content.length - 1) + "},";
                                idx++;
                            }
                        });
                        gsValue = gsValue.substring(0, gsValue.length - 1) + "]";
                        if(gsValue=="]"){
                            gsValue="";
                        }
                        $("#formulaHidden").val(gsValue);
                    } else {
                        if ($("#inputor").val() != "") {
                            var arr = $("#inputor").val().split("@");
                            var farr = '[';
                            for(var i = 0;i < (arr.length-1)/2;i++){
                                farr += '{"cnName":"'+arr[i*2+1]+'","fieldCond":""},';
                            }
                            farr = farr.substring(0,farr.length-1) + ']';
                            gsValue = "[{\"fvalue\":\"\",\"formula\":\"" + $("#inputor").val() + "\",\"idx\":\"0\",\"farr\":"+farr+"}]";
                            $("#formulaHidden").val(gsValue);
                        }
                    }
                }else {
                    $("#formulaHidden").val("");
                }
                if($("#regionDiv").css('display')=="block" && $("#isDerivative").val() == 1){
                    if(!IsChecked){
                        layer.msg("请填写条件区域内容!");
                        return;
                    }
                    $("#formulaHidden").val("");
                }
                if($("#formulaDiv").css('display')=="block"){
                    $("#fieldContent").val("");
                }
                if (validFlag) {

                    //英文字段名前加f_ 代表字段与评分卡sc_前缀的命名区分开
                    $("en_name").val('f_' + _fieldEn);

                    //英文字段名前加f_ 代表字段与评分卡sc_前缀的命名区分开
                    $("en_name").val('f_' + _fieldEn);

                    var fieldVo = {};
                    var param = {};
                    var paramFilter = {};
                    param.searchKey = $("#searchKey").val();
                    param.fieldTypeId = $("#fieldTypeId").val();
                    param.id = $("#id").val();
                    param.formula = $("#formula").val();
                    param.formulaShow = $("#formulaShow").val();
                    param.engineId = $("#engineId").val();
                    param.formulaFields = $("#formulaFields").val();
                    param.enName = $("#en_name").val();
                    param.cnName = $("#cn_name").val();
                    param.isDerivative = $("#isDerivative").val();
                    param.isOutput = $("#isOutput").val();
                    param.restrainScope = $("#restrainScope").val();
                    param.fieldContent = $("#fieldContent").val();
                    param.formulaHidden = $("#formulaHidden").val();
                    param.valueType=$("#valueType").val();
                    fieldVo.enName= $("#en_name").val();
                    fieldVo.cnName= $("#cn_name").val();
                    fieldVo.fieldTypeId = $("#fieldTypeId").val();
                    param.fieldVo=fieldVo;
                    paramFilter.param = param;
                    Comm.ajaxPost(
                        'datamanage/field/save', JSON.stringify(paramFilter),
                        function (data) {
                            if(data.msg=="保存成功"){
                                layer.msg(data.msg, {time: 1000}, function () {
                                    layer.closeAll();
                                    //queryList($("#hdNodeId").val(),"datamanage/field/list");
                                    tableUser.draw( false );//刷新数据
                                });
                            }else{
                                layer.msg(data.msg, {time: 1000}, function () {
                                });
                                return;
                            }
                        }, "application/json"
                    );
                }
            }
        });
    }else
        {
            layer.closeAll();
            InitDom(id);
            layer.open({
                type: 1,
                title: '修改字段',
                maxmin: true,
                shadeClose: true,
                area: ['650px', '468px'],
                content: $('#Add_data_style'),
                btn: ['保存', '取消'],
                yes: function (index, layero) {
                    //校验是否通过标志
                    var validFlag = false;

                    var _fieldEn = $("#en_name").val();
                    var _fieldCn = $("#cn_name").val();

                    if (validateFieldEnName()) {
                        if (validateFieldCnName()) {
                            if (validateValueScope()) {
                                validFlag = true;
                            } else {
                                return;
                            }
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                    var IsChecked=true;
                    $(".c-inclusion").each(function () {
                        $(this).find("input[name='conditionValue']:not(:hidden),input[name='fieldValue']").each(function (index, element) {
                            if(!$(element).val()){
                                IsChecked=false;
                            }
                        })
                    });
                    // if(!IsChecked&&$("#derivedFields").css('display')=="block"){
                    //     layer.msg("请填写条件区域内容!");
                    //     return;
                    // }
                    //先同步select框里的value值到隐藏字段
                    $("#fieldTypeId").val($("#value_type").val());
                    //字段条件区域序列化
                    var str = "[";
                    if ($("#isDerivative").val() == "1") {
                        $(".c-inclusion").each(function () {
                            var substr = "[";
                            $(this).find(".c-optional-rules .c-optional-rules-rules").each(function (index, element) {
                                if ($(element).find('select').val() != '' && $(element).find('input').val() != '') {
                                    //content = $(element).find('select,input').serializeJSON();
                                    var namelist = $(element).find('select,input');
                                    var content = "{";
                                    for (var j = 0; j < namelist.length; j++) {
                                        if ($($(namelist)[j]).css("display") != "none") {
                                            var name = $(namelist)[j].getAttribute("name");
                                            var value = $($(namelist)[j]).val();
                                            if (name) {
                                                if (value == null) {
                                                    value = "";
                                                }
                                                content += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                            }
                                        }
                                    }
                                    substr += content.substring(0, content.length - 1) + "},";
                                }
                            });
                            substr = substr.substring(0, substr.length - 1);
                            substr = substr + "]";
                            if (substr == "[]")
                                substr = "";

                            $(this).find('.c-operation-inclusion input[name = fieldContent2]').val(substr);

                            var subContent = "";
                            if ($(this).find('.c-operation-inclusion select').val() != '' && $(this).find('.c-operation-inclusion input').val() != '') {
                                var namelist = $(this).find('.c-operation-inclusion select,input');
                                subContent = subContent + "{";
                                for (var j = 0; j < namelist.length; j++) {
                                    if ($($(namelist)[j]).css("display") != "none") {
                                        var name = $(namelist)[j].getAttribute("name");
                                        var value = $($(namelist)[j]).val();
                                        if (name) {
                                            if (value == null) {
                                                value = "";
                                            }
                                            if (name == "fieldValue") {
                                                if ((j + 1) == namelist.length) {
                                                    subContent += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                                }
                                            } else {
                                                subContent += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                            }
                                        }
                                    }
                                    else {
                                        if ("fieldContent2" == $(namelist)[j].getAttribute("name")) {
                                            var name = $(namelist)[j].getAttribute("name");
                                            var value = $($(namelist)[j]).val();
                                            if (name) {
                                                if (value == null) {
                                                    value = "";
                                                }
                                                subContent += "\"" + name + "\"" + ":"  + value  + ",";
                                            }
                                        }
                                    }
                                }
                                subContent = subContent.substring(0, subContent.length - 1) + "},";
                            }
                            str += subContent;
                        });
                    }
                    str = str.substring(0, str.length - 1);
                    if (str != "") {
                        str = str + "]";
                    }
                    if (str == "[]" || $("#isDerivative").val() == 0) {
                        str = "";
                    }

                    $("#fieldContent").val(str);

                    //字段公式区域序列化
                    if ($("#isDerivative").val() == 1) {
                        var gsValue = "[";
                        //$("input[name = 'formulaHidden']").val(fStr);
                        if ($("#valueType").val() == "3") {
                            //[{"fvalue":"","formula":"12231235453*s++123456qrt()ln()","idx":"0","farr":""}]"
                            //gsValue="{\"fvalue\":\"\",\"formula\":\""+$("#inputor").val()+"\",\"idx\":\"0\",\"farr\":\"\"}]";
                            var idx = 0;
                            $(".editWhere2").each(function () {
                                if ($(this).find('select').val() != '' && $(this).find('input').val() != '') {
                                    var namelist = $(this).find('select,input');
                                    var content = "{";
                                    for (var j = 0; j < namelist.length; j++) {
                                        if ($($(namelist)[j]).css("display") != "none") {
                                            var name = $(namelist)[j].getAttribute("name");
                                            var value = $($(namelist)[j]).val();
                                            if (name) {
                                                if (value == null) {
                                                    value = "";
                                                }
                                                content += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                            }
                                        }
                                        else {
                                            var name = $(namelist)[j].getAttribute("name");
                                            var value = $($(namelist)[j]).val();
                                            if (name == "farr") {
                                                if (value == null) {
                                                    value = "";
                                                }
                                                content += "\"" + name + "\"" + ":" + "\"" + value + "\"" + ",";
                                            }
                                        }
                                    }
                                    content += "\"idx\":\"" + idx + "\",";
                                    gsValue += content.substring(0, content.length - 1) + "},";
                                    idx++;
                                }
                            })
                            gsValue = gsValue.substring(0, gsValue.length - 1) + "]";
                            $("#formulaHidden").val(gsValue);
                        } else {
                            if ($("#inputor").val() != "") {
                                var arr = $("#inputor").val().split("@");
                                var farr = '[';
                                for(var i = 0;i < (arr.length-1)/2;i++){
                                    farr += '{"cnName":"'+arr[i*2+1]+'","fieldCond":""},';
                                }
                                farr = farr.substring(0,farr.length-1) + ']';
                                gsValue = "[{\"fvalue\":\"\",\"formula\":\"" + $("#inputor").val() + "\",\"idx\":\"0\",\"farr\":"+farr+"}]";
                                $("#formulaHidden").val(gsValue);
                            }
                        }
                    }
                    if($("#derivedFields").css('display')=="block" && $("#isDerivative").val() == 1){
                        if(!IsChecked){
                            layer.msg("请填写条件区域内容!");
                            return;
                        }
                        $("#formulaHidden").val("");
                    }
                    if($("#formulaDiv").css('display')=="block"){
                        $("#fieldContent").val("");
                    }
                    var param = {};
                    var paramFilter = {};
                    param.fieldId = $("#id").val();
                    paramFilter.param = param;
                    var  hasCheck=false;
                    Comm.ajaxPost('datamanage/field/checkField', JSON.stringify(paramFilter), function (data) {
                            data=data.data;
                            //通用字段和引擎字段
                            var strField = "";
                            $(".c-popup-engine").html('');
                            for (var i = 0; i < data.fieldList.length; i++) {
                                strField +='<div class="c-popup-redact" style="width:500px;">';
                                if(data.fieldList[i].engineId==null)
                                    strField += '<span>数据管理-类型:</span>';
                                else
                                    strField += '<span>引擎管理-名称:'+ data.fieldList[i].engineName +'-字段映射-类型:';
                                strField += data.fieldList[i].fieldType + '-名称:' + data.fieldList[i].fieldCn+'</span>'+
                                    '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+data.fieldList[i].engineId+'" onClick="fieldGo(this)" >点击修改</a>'+
                                    '</div>';
                            }
                            if(strField!=""){
                                $(".c-popup-engine").append(strField);
                                hasCheck = true;
                            }
                            //数据库管理-黑白名单库
                            var strBWListDb = "";
                            for (var i = 0; i < data.listDbList.length; i++) {
                                strBWListDb +='<div class="c-popup-redact" style="width:500px;">';
                                if(data.listDbList[i].listType=='b')
                                    strBWListDb += '<span>数据库管理-黑名单名称:</span>';
                                else
                                    strBWListDb += '<span>数据库管理-白名单名称:';
                                strBWListDb += data.listDbList[i].listName +'</span>'+
                                    '<a href="javascript:void(0)" class="c-popup-a upadte_engine" dataid="'+data.listDbList[i].id+'"  onClick="bwListGo(this)">点击修改</a>'+
                                    '</div>';
                            }
                            if(strBWListDb!=""){
                                $(".c-popup-engine").append(strBWListDb);
                                hasCheck = true;
                            }
                            //规则管理（包含引擎管理知识库-规则管理）
                            var strRule = "";
                            for (var i = 0; i < data.ruleList.length; i++) {
                                strRule +='<div class="c-popup-redact" style="width:500px;">';
                                if (data.ruleList[i].engineId==null)
                                    strRule += '<span>规则管理-规则集-名称：</span>';
                                else
                                    strRule += '<span>引擎管理-名称:'+ data.ruleList[i].engineName +'-知识库-规则集-名称：';
                                strRule += data.ruleList[i].name +'</span>'+
                                    '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+data.ruleList[i].engineId+'" onClick="ruleManageGo(this)" >点击修改</a>'+
                                    '</div>';
                            }
                            if(strRule!=""){
                                $(".c-popup-engine").append(strRule);
                                hasCheck = true;
                            }
                            //引擎管理知识库-评分卡
                            var strScorecard = "";
                            for (var i = 0; i < data.scorecardList.length; i++) {
                                strScorecard +='<div class="c-popup-redact" style="width:500px;"><span>'+
                                    '引擎管理-名称:'+ data.scorecardList[i].engineName +'-知识库-评分卡-名称:' + data.scorecardList[i].name +
                                    '</span><a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+data.scorecardList[i].engineId+'" onClick="ruleManageGo(this)" >点击修改</a>'+
                                    '</div>';
                            }
                            if(strScorecard!=""){
                                $(".c-popup-engine").append(strScorecard);
                                hasCheck = true;
                            }
                            //引擎管理决策流-黑(白)名单
                            var strNodeListDb = "";
                            for (var i = 0; i < data.nodelistDbList.length; i++) {
                                strNodeListDb +='<div class="c-popup-redact" style="width:500px;"><span>'+
                                    '引擎管理-名称:' + data.nodelistDbList[i].engineName + '-决策流-黑(白)名单节点:' + data.nodelistDbList[i].nodeName +
                                    '</span><a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+data.nodelistDbList[i].engineId+'" onClick="decisionFlowGo(this)" >点击修改</a>'+
                                    '</div>';
                            }
                            if(strNodeListDb!=""){
                                $(".c-popup-engine").append(strNodeListDb);
                                hasCheck = true;
                            }
                            //最后判断打开对话框
                            if(hasCheck){//编辑影响字段列表
                                layer.open({
                                    type:1,
                                    title: '编辑本字段影响以下内容',
                                    area: ['650px', '310px'],
                                    content: $('#dialog_engine')
                                })
                            }
                            if (validFlag&&!hasCheck) {
                                //英文字段名前加f_ 代表字段与评分卡sc_前缀的命名区分开
                                $("en_name").val('f_' + _fieldEn);
                                param = {};
                                paramFilter = {};
                                var fieldVo = {};
                                param.searchKey = $("#searchKey").val();
                                param.fieldTypeId = $("#fieldTypeId").val();
                                param.id = $("#id").val();
                                param.formula = $("#formula").val();
                                param.formulaShow = $("#formulaShow").val();
                                param.engineId = $("#engineId").val();
                                param.formulaFields = $("#formulaFields").val();
                                param.enName = $("#en_name").val();
                                param.cnName = $("#cn_name").val();
                                param.isDerivative = $("#isDerivative").val();
                                param.isOutput = $("#isOutput").val();
                                param.restrainScope = $("#restrainScope").val();
                                param.fieldContent = $("#fieldContent").val();
                                param.formulaHidden = $("#formulaHidden").val();
                                param.valueType=$("#valueType").val();
                                //param.searchKey = $("#searchKey").val();
                                //param.Id = $("#id").val();
                                //param._engineId = _engineId;
                                param.Id = $("#id").val();
                                fieldVo.enName= $("#en_name").val();
                                fieldVo.cnName= $("#cn_name").val();
                                fieldVo.fieldTypeId = $("#fieldTypeId").val();
                                param.fieldVo=fieldVo;
                                paramFilter.param = param;

                                Comm.ajaxPost(
                                    'datamanage/field/update', JSON.stringify(paramFilter),
                                    function (data) {
                                        layer.closeAll();
                                        layer.msg(data.msg, {time: 1000}, function () {
                                            tableUser.draw( false );//刷新数据
                                            //queryList($("#hdNodeId").val(),"datamanage/field/list");
                                        });
                                    }, "application/json"
                                );
                            }else{
                                //layer.closeAll();
                            }
                        }, "application/json"
                    );

                    // if ($("#role_key").val() == "") {
                    //     layer.msg("角色标识不能为空",{time:2000});
                    //     return;
                    // }
                    // if ($("#role_name").val() == "") {
                    //     layer.msg("角色名称不能为空",{time:2000});
                    //     return;
                    // }
                }
            });
        }
}
/**启用停用删除字段**/
function resetData(sign,id){
    var param = {};
    var paramFilter = {};
    var ids = new Array();
    ids.push(id);
    param.ids = ids;
    param.status = sign;
    paramFilter.param = param;
    var url="datamanage/field/updateStatus";
    if(sign==-1){
        layer.confirm('是否删除该字段？', {btn : [ '确定', '取消' ]},
            function() {
                ajax1(paramFilter,url);
            }
        );
    }else{
        this.ajax1(paramFilter,url);
    }
}
function ajax1(paramFilter,url) {
    Comm.ajaxPost(url, JSON.stringify(paramFilter),
        function(data){
            var data = data.data;
            var hasCheck = false;
            status = paramFilter.param.status;
            if(status!='1'){ //'启用'不做校验
                //通用字段和引擎字段
                var strField = "";
                $(".c-popup-engine").html('');
                for (var i = 0; i < data.fieldList.length; i++) {
                    strField +='<div class="c-popup-redact" style="width:500px;">';
                    if(data.fieldList[i].engineId==null)
                        strField += '数据管理-类型:';
                    else
                        strField += '引擎管理-名称:'+ data.fieldList[i].engineName +'-字段映射-类型:';
                    strField += data.fieldList[i].fieldType + '-名称:' + data.fieldList[i].fieldCn +''+
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+data.fieldList[i].engineId+'" onClick="fieldGo(this)" >点击修改</a>'+
                        '</div>';
                }
                if(strField!=""){
                    $(".c-popup-engine").append(strField);
                    hasCheck = true;
                }
                //数据库管理-黑白名单库
                var strBWListDb = "";
                for (var i = 0; i < data.listDbList.length; i++) {
                    strBWListDb +='<div class="c-popup-redact" style="width:500px;">';
                    if(data.listDbList[i].listType=='b')
                        strBWListDb += '数据库管理-黑名单名称:';
                    else
                        strBWListDb += '数据库管理-白名单名称:';
                    strBWListDb += data.listDbList[i].listName +
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" dataid="'+data.listDbList[i].id+'" listType="'+data.listDbList[i].listType+'" onClick="bwListGo(this)">点击修改</a>'+
                        '</div>';
                }
                if(strBWListDb!=""){
                    $(".c-popup-engine").append(strBWListDb);
                    hasCheck = true;
                }
                //规则管理（包含引擎管理知识库-规则管理）
                var strRule = "";
                for (var i = 0; i < data.ruleList.length; i++) {
                    strRule +='<div class="c-popup-redact" style="width:500px;">';
                    if (data.ruleList[i].engineId==null)
                        strRule += '规则管理-规则集-名称：';
                    else
                        strRule += '引擎管理-名称:'+ data.ruleList[i].engineName +'-知识库-规则集-名称：';
                    strRule += data.ruleList[i].name +
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+data.ruleList[i].engineId+'" onClick="ruleManageGo(this)" >点击修改</a>'+
                        '</div>';
                }
                if(strRule!=""){
                    $(".c-popup-engine").append(strRule);
                    hasCheck = true;
                }
                //引擎管理知识库-评分卡
                var strScorecard = "";
                for (var i = 0; i < data.scorecardList.length; i++) {
                    strScorecard +='<div class="c-popup-redact" style="width:500px;">'+
                        '引擎管理-名称:'+ data.scorecardList[i].engineName +'-知识库-评分卡-名称:' + data.scorecardList[i].name +
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+data.scorecardList[i].engineId+'" onClick="ruleManageGo(this)" >点击修改</a>'+
                        '</div>';
                }
                if(strScorecard!=""){
                    $(".c-popup-engine").append(strScorecard);
                    hasCheck = true;
                }
                //引擎管理决策流-黑(白)名单
                var strNodeListDb = "";
                for (var i = 0; i < data.nodelistDbList.length; i++) {
                    strNodeListDb +='<div class="c-popup-redact" style="width:500px;">'+
                        '引擎管理-名称:' + data.nodelistDbList[i].engineName + '-决策流-黑(白)名单节点:' + data.nodelistDbList[i].nodeName +
                        '<a href="javascript:void(0)" class="c-popup-a upadte_engine" engineId="'+data.nodelistDbList[i].engineId+'" onClick="decisionFlowGo(this)" >点击修改</a>'+
                        '</div>';
                }
                if(strNodeListDb!=""){
                    $(".c-popup-engine").append(strNodeListDb);
                    hasCheck = true;
                }
            }

            //最后判断打开对话框
            if(hasCheck){
                layer.open({
                    type : 1,
                    title : '删除或停用字段影响以下内容',
                    area : [ '545px', '310px' ],
                    content : $("#dialog_engine_stop"),
                    cancel: function(index, layero){
                        layer.closeAll();
                    }
                });
            }else{
                g_userManage.fuzzySearch = true;
                layer.msg("操作成功",{time:1000},function () {
                    $("#selectAll").attr("checked",false);
                    tableUser.draw( false );//刷新数据
                });
            }
        },"application/json");
}
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(menuId,data) {
        var paramFilter = {};
        var page = {};
        var param = {};


        param.fieldTypeId = menuId;
        param.isCommon=1;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        if (page.pageSize==-1)
        {
            page.pageSize=10;
        }
        paramFilter.page = page;

        return paramFilter;
    }
};
var layerIndex;
var selfid;
function update(sign,id){
    $('#nameCode').val("");
    $('input[name="name_dict"]').val("");
    $('input[name="isCatagory"]:checked').attr("checked",false);
    $('textarea[name="remark"]').val("");
    var titleName = "添加";
    if(sign!=1){
        selfid=sign[1];titleName = "编辑";
        Comm.ajaxGet("dict/detail?id="+sign[1],"", function(data){
                var dict = data.data;
                var isCatagory=dict.isCatagory;
                if(isCatagory=='Y'){
                    $('#isCatagory_Y').attr('checked','checked');
                }else{
                    $('#isCatagory_N').attr('checked','checked');
                }
                $('input[name="name_code"]').val(dict.code);
                $('input[name="name_dict"]').val(dict.name);
                $('textarea[name="remark"]').val(dict.remark);
            }
        );
        openLayer(titleName,id,selfid);
    }else{
        selfid=null;
        openLayer(titleName,id,selfid);
    }
}
function openLayer(titleName,id,selfid){
    var id=id;
    layerIndex = layer.open({
        type : 1,
        title : titleName,
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '600px', '' ],
        content : $('#Add_Dic_style')
    });
    assortSelect(id,selfid);
}
/***保存**/
function save(){
    var selectType=$('select[name="selectType"]').val();
    var nameCode=$('input[name="name_code"]').val();
    var name=$('input[name="name_dict"]').val();
    var isCatagory=$('input[type="radio"]:checked').val();
    var remark=$('textarea[name="remark"]').val();
    var id=selfid;
    var dict={
        id:id,
        code:nameCode,
        name:name,
        isCatagory:isCatagory,
        remark:remark,
        parentId:selectType
    }
    Comm.ajaxPost(
        'dict/add',JSON.stringify(dict),
        function(data){
            layer.msg("保存成功",{time:1000},function(){
                $.jstree.reference("#jstree").refresh();
            });
        }, "application/json"
    );
}
function assortSelect(ids,selfid){
    var id;
    if(ids){id=ids;
    }else{id=Number(selfid)}
    Comm.ajaxGet("dict/getCatagory", '', function(data){
            var dataList=data.data;
            var html="";
            for(var i=0;i<dataList.length;i++){
                if(($.contains($("#"+id) ,$("ul") ))==false){
                    var dataId=$("#"+id).parent().parent().attr("id");
                    if(dataId==dataList[i].id){
                        html+="<option value='"+dataList[i].id+"' selected>"+dataList[i].name+"</option>"
                    }
                    $('select[name="selectType"]').css("background","#F8F8F8");
                    $('select[name="selectType"]').attr("disabled",true);
                }else{
                    if(id==dataList[i].id){
                        html+="<option value='"+dataList[i].id+"' selected>"+dataList[i].name+"</option>"
                    }else{
                        html+="<option value='"+dataList[i].id+"'>"+dataList[i].name+"</option>"
                    }
                }
            }
            $('select[name="selectType"]').html(html);
        })
}
function queryList(menuId,url) {
    tableUser = $('#Res_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManage.getQueryCondition(menuId,data);
            Comm.ajaxPost('datamanage/field/list',JSON.stringify(queryFilter),function(result){
                var returnData = {};
                var resData = result.data;
                resData = resData.pager;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            },"application/json")
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {
                'data':null,
                "searchable": false,
                "orderable": false,
                "class":'hidden'
            },
            {"data": "enName","orderable" : false},
            {"data": "cnName","orderable" : false},
            {"data": "fieldType","orderable" : false},
            {"data": "restrainScope","orderable" : false},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.isCommon==1){
                        return '是'
                    }else{
                        return '否'
                    }
                }
            },
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.isDerivative==1){
                        return '是'
                    }else{
                        return '否'
                    }
                }
            },
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.isOutput==1){
                        return '是'
                    }else{
                        return '否'
                    }
                }
            },
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.status==1){
                        return '启用'
                    }else
                    {
                        if(data.status==0){
                            return '停用'
                        }else
                        {
                            return '删除'
                        }
                    }
                }
            },
            {"data": "nickName","orderable" : false},
            {
                "data":null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.createTime);
                }
            },
            {
                "data": "null",
                "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="addData(2,'+data.id+')">修改</a>&nbsp;<a class="tabel_btn_style" onclick="resetData(1,'+data.id+')">启用</a>&nbsp;<a class="tabel_btn_style_dele" onclick="resetData(0,'+data.id+')">停用</a>&nbsp;<a class="tabel_btn_style_dele" onclick="resetData(-1,'+data.id+')">删除</a>');
            $('td', row).eq(11).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            //单选行选择
            $("#Res_list tbody").delegate( 'tr','click',function(e){
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
            //全选
            $("#selectAll").click(function(J) {
                if (!$(this).prop("checked")) {
                    $("#Res_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                } else {
                    $("#Res_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                }
            });
            //操作
            $("#Res_list").on("click",".operate",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                addData(2,id);
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    tableUser.draw( false );//刷新数据
}
var tableUser = null;
function setDiv() {
     var  value=$("#valueType").val();
    //var selectNode=sign.next('select');
    if(value=="3") {
        $("#formulaEdit2").show();
        $("#formulaEdit1").hide();
        $("input[name='conditionValue']").hide();
        $("select[name='conditionValue']").show();
        setSelectList();
        setCursor('divRowid1');
    }else{
        $("#formulaEdit1").show();
        $("#formulaEdit2").hide();
        $("input[name='conditionValue']").show();
        $("select[name='conditionValue']").hide();
        setCursor('inputor');
    }
}
function  setTextDiv(sign) {
    if (sign=="1"){
        $("#regionDiv").show();
        $("#region").css({"color":"#398DEF"});
        $("#formulaDiv").hide();
        $("#formul").css({"color":"initial"});
    }else {
        $("#regionDiv").hide();
        $("#region").css({"color":"initial"});
        $("#formulaDiv").show();
        $("#formul").css({"color":"#398DEF"});
    }
}
function InitDom(sign,id) {
    if(sign==null){
        var paramFilter = {};
        var param = {};
        param.fieldTypeId = $("#hdNodeId").val()=="0"?"":$("#hdNodeId").val();
        paramFilter.param=param;
        //获取字段类型数据
        Comm.ajaxPost(
            'datamanage/field/preadd',JSON.stringify(paramFilter),
            function(data){
                var  dataList=data.data;
                var fieldTypeList=dataList.fieldTypeList;
                var html="";
                if(dataList.fieldTypeId!=null&dataList.fieldType!=null){
                    html+="<option value='"+dataList.fieldTypeId+"' selected>"+dataList.fieldType+"</option>";
                }
                for(var i=0;i<fieldTypeList.length;i++) {
                    html+="<option value='"+fieldTypeList[i].id+"'>"+fieldTypeList[i].fieldType+"</option>"
                }
                $("#value_type").html(html);
            }, "application/json"
        );
        //获取条件区域下拉框数据
        Comm.ajaxPost(
            'datamanage/field/findFieldByUser',JSON.stringify(paramFilter),
            function(data){
                //初始化值类型
                $("#valueType").val("1");
                //初始化条件区域
                var  dataList=data.data;
                var  html="";
                dataSelectList=data.data.fieldList;
                for(var i=0;i<dataSelectList.length;i++) {
                    //$("#fieldSource").html("<option value='"+dataList[i].fieldTypeId+"' selected>"+dataList[i].fieldType+"</option>");
                    //<restrainScope   valuetype    id   cnName
                    //<option valuescope="[1,200]" valuetype="1" value="565">年龄</option>
                    html+="<option valuescope='"+dataSelectList[i].restrainScope+"' valuetype='"+dataSelectList[i].valueType+"' value='"+dataSelectList[i].id+"'>"+dataSelectList[i].cnName+"</option>"
                }
                dataSelectListHtml=html;
                var  fieldByUserWhereHtml="";
                if (dataSelectList.length>0) {
                    fieldByUserWhereHtml=getOperator(dataSelectList[0].valueType);
                }
                $("[name='fieldId']").html(html);
                $("Div[name='divRow']").remove();
                $('#regionDiv').append("<div style=\"width: 620px;overflow: hidden\" name=\"divRow\" class=\"c-inclusion\" id=\"divRow1\"> <div class=\"Remark c-operation-inclusion\" style=\"width: 160px;border-right:1px dashed #000;float:left\"  id=\"divRowLeft1\"> <label style='margin:.2em 0;'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,1,'')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,1,'')\"></span> <input type=\"hidden\" name=\"fieldContent2\"> <input type=\"text\" name=\"conditionValue\" style=\"width: 80px\"> <select name=\"conditionValue\" size=\"1\"  class=\"select\" style=\"width: 80px;display: none\"> </select></label> </div> <div class=\"Remark c-optional-rules\" style=\"width:460px;float:right;text-align:left;\"  id=\"divRowRight1\"> <label style='margin:.2em 0;'> <div class='c-optional-rules-rules'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,2,'divRowRight1')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span> <select name=\"fieldId\" size=\"1\"  onchange='setSelect(this)'  class=\"select\" style=\"width: 80px\"> "+html+"</select> <select name=\"operator\" size=\"1\"  class=\"select\" style=\"width: 80px\"> "+fieldByUserWhereHtml+" </select> <input type=\"text\" name=\"fieldValue\" style=\"width: 80px;height:31px\"> <select  size=\"1\"  name=\"logical\" style='display: none;width: 80px' class=\"select\" > "+logical+"</select></div> </label> </div> </div>");
                divRowid=1;

                //初始化公式编辑
                $("#formulaEdit1").show();
                $("#inputor").val("");
                $(".editWhere2").remove();
                $('#formulaEdit2').append(" <label style='margin:.2em 0;'><div class='editWhere2'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,3,'formulaEdit2')\"></span> <span class=\"iconclearData\"  style=\"border-color:#ddd\"></span>             <select  size=\"1\" name=\"fvalue\"  class=\"inputor\"   style=\"width:220px\"> "+enumerationselectHtml+"</select> <span>    =  </span> <input type=\"text\"  onclick=\"setCursor('divRowid1')\" name='formula' id='divRowid1' style=\"width: 220px\" > <input type=\"hidden\" name=\"farr\"/> </div> </div></label>");
                $('#formulaEdit2').hide();
                divRowid2=1;
                //初始化 条件区域 公式编辑tab
                setTextDiv(1);
                setCursor('inputor');
            }, "application/json"
        );
        //初始化文本框值
        $('#en_name').attr("value",'');
        $('#cn_name').attr("value",'');
        $("#isDerivative").val("0");
        $('#isOutput').val("0");
        $('#restrainScope').val("");
        setDerivative();
    }else {
        var paramFilter = {};
        var param = {};
        param.id=sign;
        paramFilter.param=param;
        //获取字段类型数据
        Comm.ajaxPost(
            'datamanage/field/edit/'+sign,JSON.stringify(paramFilter),
            function(data){
                var  dataList=data.data;
                // var  engineId=dataList.engineId
                //公式编辑
                var  fieldFormulaList=dataList.fieldFormulaList;
                // var  fieldTypeId=dataList.fieldTypeId
                var  fieldVo=dataList.fieldVo;
                //下拉框选项值
                var  flist=dataList.flist;
                var  hasFormula=dataList.hasFormula;
                //公式值
                $("#formulaHidden").val(hasFormula);
                //字段范围
                var  scopeList=dataList.scopeList;
                // var  searchKey=dataList.searchKey;
                $("#engineId").val(dataList.engineId);
                $("#searchKey").val(dataList.searchKey);
                $("#en_name").val(fieldVo.enName);
                $("#cn_name").val(fieldVo.cnName);
                $("#fieldTypeId").val(dataList.fieldTypeId);
                $("#value_type").html("<option value='"+fieldVo.fieldTypeId+"'>"+fieldVo.fieldType+"</option>");
                $("#valueType").val(fieldVo.valueType);
                $("#isDerivative").val(fieldVo.isDerivative);
                $("#isOutput").val(fieldVo.isOutput);
                $("#restrainScope").val(fieldVo.restrainScope);
                $("#id").val(fieldVo.id);
                divRowid=1;
                //条件区域行
                $("Div[name='divRow']").remove();
                if($("#isDerivative").val()=="1"){
                    $("#derivedFields").show();
                    if(fieldVo.fieldCondList.length>0||fieldFormulaList.length>0) {
                        var html="";
                        whereSelectValue1="";
                        dataSelectListHtml="";
                        dataSelectList=flist;
                        for(var i=0;i<dataSelectList.length;i++) {
                            html+="<option valuescope='"+dataSelectList[i].restrainScope+"' valuetype='"+dataSelectList[i].valueType+"' value='"+dataSelectList[i].id+"'>"+dataSelectList[i].cnName+"</option>"
                        }
                        dataSelectListHtml=html;
                        var  showText=""
                        enumerationselectHtml="";
                        if(fieldVo.valueType=="3"){
                            for(var i=0;i<scopeList.length;i++) {
                                var scopeListarr=scopeList[i].split(':');
                                enumerationselectHtml+="<option  value='"+scopeListarr[1]+"'>"+scopeListarr[0]+"</option>"
                            }
                            showText="<input type=\"text\" name=\"conditionValue\"  style=\"width: 80px;display: none\"> <select name=\"conditionValue\" size=\"1\"  class=\"select conditionValueUpdate\" style=\"width: 80px;\">"+enumerationselectHtml+" </select>";
                            divRowid=1;
                            //条件区域
                            if (fieldVo.fieldCondList==0){
                                $('#regionDiv').append("<div style=\"width: 620px;overflow: hidden\" name=\"divRow\" class=\"c-inclusion\" id=\"divRow"+divRowid+"\"> <div class=\"Remark c-operation-inclusion\" style=\"width: 160px;border-right:1px dashed #000;float:left\"  id=\"divRowLeft"+divRowid+"\"> <label style='margin:.2em 0;'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,1,'')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,1,'')\"></span> <input type=\"hidden\" name=\"fieldContent2\"> "+ showText+"</label> </div> <div class=\"Remark c-optional-rules\" style=\"width:460px;float:right;text-align:left;\"  id=\"divRowRight"+divRowid+"\">  </div> </div>");
                            }
                            for(var i=0;i<fieldVo.fieldCondList.length;i++){
                                var fieldSubCond="";
                                if(fieldVo.fieldCondList[i].fieldSubCond.length>0){
                                    fieldSubCond=getOperator(fieldVo.fieldCondList[i].fieldSubCond[0].valueType)
                                }
                                var divRowRightId="divRowRight"+divRowid;
                                $('#regionDiv').append("<div style=\"width: 620px;overflow: hidden\" name=\"divRow\" class=\"c-inclusion\" id=\"divRow"+divRowid+"\"> <div class=\"Remark c-operation-inclusion\" style=\"width: 160px;border-right:1px dashed #000;float:left\"  id=\"divRowLeft"+divRowid+"\"> <label style='margin:.2em 0;'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,1,'')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,1,'')\"></span> <input type=\"hidden\" name=\"fieldContent2\"> "+ showText+"</label> </div> <div class=\"Remark c-optional-rules\" style=\"width:460px;float:right;text-align:left;\"  id=\"divRowRight"+divRowid+"\">  </div> </div>");
                                //条件数据行
                                var  conditionContent=JSON.parse(fieldVo.fieldCondList[i].conditionContent);
                                for(var j=0;j<conditionContent.length;j++){
                                    var andShow="";
                                    if(j<=conditionContent.length-2){
                                        andShow="";
                                    }else {
                                        andShow="display: none;";
                                    }
                                    $("#"+divRowRightId).append("<label style='margin:.2em 0;'> <div class='c-optional-rules-rules'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,2,'divRowRight1')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span> <select name=\"fieldId\" class='fieldIdUpdate' size=\"1\"  onchange='setSelect(this)'  class=\"select\" style=\"width: 80px\"> "+html+"</select> <select name=\"operator\" size=\"1\"  class=\"select operatorValue\" style=\"width: 80px\">"+fieldSubCond+"</select> <input type=\"text\" name=\"fieldValue\" class='fieldValue' style=\"width: 80px;height:31px\"> <select  size=\"1\"  name=\"logical\" style='width: 80px;"+andShow+"' class=\"select logicalValue\" > "+logical+"</select></div> </label>");
                                    var  inputfieldId=$(".fieldIdUpdate:last");
                                    var  inputfieldValue=$(".fieldValue:last");
                                    var  logicalValue=$(".logicalValue:last");
                                    var  operatorValue=$(".operatorValue:last");
                                    var s1=conditionContent[j].fieldId;
                                    var s3=conditionContent[j].fieldValue;
                                    var s4=conditionContent[j].logical;
                                    var s5=conditionContent[j].operator;
                                    inputfieldId.val(s1);
                                    inputfieldValue.val(s3);
                                    logicalValue.val(s4);
                                    operatorValue.val(s5);
                                }

                                var  conditionValue=$(".conditionValueUpdate:last");
                                var s2=fieldVo.fieldCondList[i].conditionValue;
                                conditionValue.val(s2);
                                divRowid++;
                            }
                            //公式编辑
                            $("#formulaEdit2").show();
                            $("#formulaEdit1").hide();
                            $("#inputor").val("");
                            $(".editWhere2").remove();
                            divRowid2=1;
                            if(fieldFormulaList.length==0){
                                var span="";
                                if(i==0){
                                    span="<span class=\"iconclearData\"  style=\"border-color:#ddd\"></span>";
                                }else {
                                    span="<span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span>";
                                }
                                $('#formulaEdit2').append( "<label style='margin:.2em 0;'> <div class='editWhere2'><span class=\"iconaddData\" onclick=\"addTermRulsLi(this,3,'formulaEdit2')\"></span> <span class=\"iconclearData\"  style=\"border-color:#ddd\"></span> <select  size=\"1\"  name=\"fvalue\" class=\"select  selectFvalue\"  style=\"width:220px\"> "+enumerationselectHtml+"</select> <span>    =  </span> <input type=\"text\" name='formula' id='divRowid"+divRowid2+"' onclick=\"setCursor(divRowid"+divRowid2+"')\" style=\"width: 220px\" value=''><input type=\"hidden\" name=\"farr\"/></div> </label>");
                            }
                            for(var i=0;i<fieldFormulaList.length;i++){
                                var span="";
                                if(i==0){
                                    span="<span class=\"iconclearData\"  style=\"border-color:#ddd\"></span>";
                                }else {
                                    span="<span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span>";
                                }
                                $('#formulaEdit2').append( "<label style='margin:.2em 0;'> <div class='editWhere2'><span class=\"iconaddData\" onclick=\"addTermRulsLi(this,3,'formulaEdit2')\"></span> "+span+" <select  size=\"1\"  name=\"fvalue\" class=\"select  selectFvalue\"  style=\"width:220px\"> "+enumerationselectHtml+"</select> <span>    =  </span> <input type=\"text\" name='formula' id='divRowid"+divRowid2+" onclick=\"setCursor(divRowid"+divRowid2+"')\" style=\"width: 220px\" value='"+fieldFormulaList[i].formula+"'><input type=\"hidden\" name=\"farr\"/></div> </label>");
                                var  inputfieldId=$(".selectFvalue:last");
                                var s1=fieldFormulaList[i].fvalue;
                                inputfieldId.val(s1);
                                setCursor("divRowid1");
                                divRowid2++;
                            }
                            //setDiv();
                            //初始化 条件区域 公式编辑tab
                            setTextDiv(2);
                            setCursor('inputor');
                        }
                        else {
                            showText="<input type=\"text\" name=\"conditionValue\" class='conditionValueUpdateInput'  style=\"width: 80px;\"> <select name=\"conditionValue\" size=\"1\"  class=\"select\" style=\"width: 80px;display: none\"> </select>";
                            divRowid=1;
                            //条件区域
                            if(fieldVo.fieldCondList==0){
                                $('#regionDiv').append("<div style=\"width: 620px;overflow: hidden\" name=\"divRow\" class=\"c-inclusion\" id=\"divRow"+divRowid+"\"> <div class=\"Remark c-operation-inclusion\" style=\"width: 160px;border-right:1px dashed #000;float:left\"  id=\"divRowLeft"+divRowid+"\"> <label style='margin:.2em 0;'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,1,'')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,1,'')\"></span> <input type=\"hidden\" name=\"fieldContent2\"> "+ showText+"</label> </div> <div class=\"Remark c-optional-rules\" style=\"width:460px;float:right;text-align:left;\"  id=\"divRowRight"+divRowid+"\">  </div> </div>");
                                $("#divRowRight"+divRowid).append("<label style='margin:.2em 0;'> <div class='c-optional-rules-rules'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,2,'divRowRight1')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span> <select name=\"fieldId\" class='fieldIdUpdate' size=\"1\"  onchange='setSelect(this)'  class=\"select\" style=\"width: 80px\"> "+html+"</select> <select name=\"operator\" size=\"1\"  class=\"select operatorValue\" style=\"width: 80px\">"+fieldSubCond+"</select> <input type=\"text\" name=\"fieldValue\" class='fieldValue' style=\"width: 80px;height: 31px\"> <select  size=\"1\"  name=\"logical\" style='width: 80px;display: none' class=\"select logicalValue\" ></select></div> </label>");
                            }
                            for(var i=0;i<fieldVo.fieldCondList.length;i++){
                                var fieldSubCond="";
                                if(fieldVo.fieldCondList[i].fieldSubCond.length>0){
                                    fieldSubCond=getOperator(fieldVo.fieldCondList[i].fieldSubCond[0].valueType)
                                }
                                var divRowRightId="divRowRight"+divRowid;
                                $('#regionDiv').append("<div style=\"width: 620px;overflow: hidden\" name=\"divRow\" class=\"c-inclusion\" id=\"divRow"+divRowid+"\"> <div class=\"Remark c-operation-inclusion\" style=\"width: 160px;border-right:1px dashed #000;float:left\"  id=\"divRowLeft"+divRowid+"\"> <label style='margin:.2em 0;'><span class=\"iconaddData\" onclick=\"addTermRulsLi(this,1,'')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,1,'')\"></span> <input type=\"hidden\" name=\"fieldContent2\"> "+ showText+"</label> </div> <div class=\"Remark c-optional-rules\" style=\"width:460px;float:right;text-align:left;\"  id=\"divRowRight"+divRowid+"\">  </div> </div>");
                                //条件数据行
                                var  conditionContent=JSON.parse(fieldVo.fieldCondList[i].conditionContent);
                                for(var j=0;j<conditionContent.length;j++){
                                    var andShow="";
                                    if(j<=conditionContent.length-2){
                                        andShow="";
                                    }else {
                                        andShow="display: none;";
                                    }
                                    $("#"+divRowRightId).append("<label style='margin:.2em 0;'> <div class='c-optional-rules-rules'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,2,'divRowRight1')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span> <select name=\"fieldId\" class='fieldIdUpdate' size=\"1\"  onchange='setSelect(this)'  class=\"select\" style=\"width: 80px\"> "+html+"</select> <select name=\"operator\" size=\"1\"  class=\"select operatorValue\" style=\"width: 80px\">"+fieldSubCond+"</select> <input type=\"text\" name=\"fieldValue\" class='fieldValue' style=\"width: 80px;height:31px\"> <select  size=\"1\"  name=\"logical\" style='width: 80px;"+andShow+"' class=\"select logicalValue\" > "+logical+"</select></div> </label>");
                                    var  inputfieldId=$(".fieldIdUpdate:last");
                                    var  inputfieldValue=$(".fieldValue:last");
                                    var  logicalValue=$(".logicalValue:last");
                                    var  operatorValue=$(".operatorValue:last");
                                    var s1=conditionContent[j].fieldId;
                                    var s3=conditionContent[j].fieldValue;
                                    var s4=conditionContent[j].logical;
                                    var s5=conditionContent[j].operator;
                                    inputfieldId.val(s1);
                                    inputfieldValue.val(s3);
                                    logicalValue.val(s4);
                                    operatorValue.val(s5);
                                }

                                var  conditionValue=$(".conditionValueUpdateInput:last");
                                var s2=fieldVo.fieldCondList[i].conditionValue;
                                conditionValue.val(s2);
                                divRowid++;
                            }
                            //公式编辑
                            $("#formulaEdit2").hide();
                            $("#formulaEdit1").show();
                            $("#inputor").val("");
                            $("#editWhere2").remove();
                            divRowid2=1;
                            if(fieldFormulaList.length>0){
                                $("#inputor").val(fieldFormulaList[0].formula);
                            }else {
                                $("#inputor").val("");
                            }
                            $('#formulaEdit2').append(" <label style='margin:.2em 0;'><div class='editWhere2'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,3,'formulaEdit2')\"></span> <span class=\"iconclearData\"  style=\"border-color:#ddd\"></span>             <select  size=\"1\" name=\"fvalue\"  class=\"inputor\"   style=\"width:220px\"> "+enumerationselectHtml+"</select> <span>    =  </span> <input type=\"text\"  onclick=\"setCursor('divRowid1')\" name='formula' id='divRowid1' style=\"width: 220px\" > <input type=\"hidden\" name=\"farr\"/> </div> </div></label>");
                            //setDiv();
                            //初始化 条件区域 公式编辑tab
                            setTextDiv(2);
                            setCursor('inputor');
                        }
                    }
                }
                else {
                    $("#derivedFields").hide();
                    if(true) {
                        var html="";
                        whereSelectValue1="";
                        dataSelectListHtml="";
                        dataSelectList=flist;
                        for(var i=0;i<dataSelectList.length;i++) {
                            html+="<option valuescope='"+dataSelectList[i].restrainScope+"' valuetype='"+dataSelectList[i].valueType+"' value='"+dataSelectList[i].id+"'>"+dataSelectList[i].cnName+"</option>"
                        }
                        dataSelectListHtml=html;
                        var  showText=""
                        enumerationselectHtml="";
                        if(fieldVo.valueType=="3"){
                            for(var i=0;i<scopeList.length;i++) {
                                var scopeListarr=scopeList[i].split(':');
                                enumerationselectHtml+="<option  value='"+scopeListarr[1]+"'>"+scopeListarr[0]+"</option>"
                            }
                            showText="<input type=\"text\" name=\"conditionValue\"  style=\"width: 80px;display: none\"> <select name=\"conditionValue\" size=\"1\"  class=\"select conditionValueUpdate\" style=\"width: 80px;\">"+enumerationselectHtml+" </select>";
                            divRowid=1;
                            //条件区域
                            if (fieldVo.fieldCondList==0){
                                $('#regionDiv').append("<div style=\"width: 620px;overflow: hidden\" name=\"divRow\" class=\"c-inclusion\" id=\"divRow"+divRowid+"\"> <div class=\"Remark c-operation-inclusion\" style=\"width: 160px;border-right:1px dashed #000;float:left\"  id=\"divRowLeft"+divRowid+"\"> <label style='margin:.2em 0;'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,1,'')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,1,'')\"></span> <input type=\"hidden\" name=\"fieldContent2\"> "+ showText+"</label> </div> <div class=\"Remark c-optional-rules\" style=\"width:460px;float:right;text-align:left;\"  id=\"divRowRight"+divRowid+"\">  </div> </div>");
                            }

                            // //公式编辑
                            // $("#formulaEdit2").show();
                            // $("#formulaEdit1").hide();
                            $("#inputor").val("");
                            $("#editWhere2").remove();
                            divRowid2=1;
                            if(fieldFormulaList.length==0){
                                var span="";
                                if(i==0){
                                    span="<span class=\"iconclearData\"  style=\"border-color:#ddd\"></span>";
                                }else {
                                    span="<span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span>";
                                }
                                $('#formulaEdit2').append( "<label style='margin:.2em 0;'> <div class='editWhere2'><span class=\"iconaddData\" onclick=\"addTermRulsLi(this,3,'formulaEdit2')\"></span> <span class=\"iconclearData\"  style=\"border-color:#ddd\"></span> <select  size=\"1\"  name=\"fvalue\" class=\"select  selectFvalue\"  style=\"width:220px\"> "+enumerationselectHtml+"</select> <span>    =  </span> <input type=\"text\" name='formula' id='divRowid"+divRowid2+"' onclick=\"setCursor(divRowid"+divRowid2+"')\" style=\"width: 220px\" value=''><input type=\"hidden\" name=\"farr\"/></div> </label>");
                            }

                        }
                        else {
                            showText="<input type=\"text\" name=\"conditionValue\" class='conditionValueUpdateInput'  style=\"width: 80px;\"> <select name=\"conditionValue\" size=\"1\"  class=\"select\" style=\"width: 80px;display: none\"> </select>";
                            divRowid=1;
                            //条件区域
                            if(fieldVo.fieldCondList==0){
                                $('#regionDiv').append("<div style=\"width: 620px;overflow: hidden\" name=\"divRow\" class=\"c-inclusion\" id=\"divRow"+divRowid+"\"> <div class=\"Remark c-operation-inclusion\" style=\"width: 160px;border-right:1px dashed #000;float:left\"  id=\"divRowLeft"+divRowid+"\"> <label style='margin:.2em 0;'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,1,'')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,1,'')\"></span> <input type=\"hidden\" name=\"fieldContent2\"> "+ showText+"</label> </div> <div class=\"Remark c-optional-rules\" style=\"width:460px;float:right;text-align:left;\"  id=\"divRowRight"+divRowid+"\">  </div> </div>");
                                $("#divRowRight"+divRowid).append("<label style='margin: .2em 0'><div class='c-optional-rules-rules'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,2,'divRowRight1')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span> <select name=\"fieldId\" class='fieldIdUpdate' size=\"1\"  onchange='setSelect(this)'  class=\"select\" style=\"width: 80px\"> "+html+"</select> <select name=\"operator\" size=\"1\"  class=\"select operatorValue\" style=\"width: 80px\">"+fieldSubCond+"</select> <input type=\"text\" name=\"fieldValue\" class='fieldValue' style=\"width: 80px;height:31px\"> <select  size=\"1\"  name=\"logical\" style='width: 80px;display: none' class=\"select logicalValue\" ></select></div> </label>");
                            }
                            $("#inputor").val("");
                            $(".editWhere2").remove();
                            divRowid2=1;
                            if(fieldFormulaList.length>0){
                                $("#inputor").val(fieldFormulaList[0].formula);
                            }else {
                                $("#inputor").val("");
                            }
                            $('#formulaEdit2').append(" <label style='margin:.2em 0;'><div class='editWhere2'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,3,'formulaEdit2')\"></span> <span class=\"iconclearData\"  style=\"border-color:#ddd\"></span><select  size=\"1\" name=\"fvalue\"  class=\"inputor\"   style=\"width:220px\"> "+enumerationselectHtml+"</select> <span>    =  </span> <input type=\"text\"  onclick=\"setCursor('divRowid1')\" name='formula' id='divRowid1' style=\"width: 220px\" > <input type=\"hidden\" name=\"farr\"/> </div> </div></label>");
                        }
                    }
                    }
            }, "application/json"
        );
    }
}
function getOperator(valueType){
    var operator;
    if(valueType == '1' || valueType == '4'){
        operator =digitalType;
    }else if(valueType == '2'){
        operator =characterType;
    }else if(valueType == '3'){
        operator =enumerationType;
    }
    return operator;
}
function  setSelect(me) {
    me=$(me);
    var selectNode=me.next('select');
    var valueType=me.find("option:selected").attr("valuetype");
    selectNode.find("option").remove();
    selectNode.html(getOperator(valueType));
}
var divRowid=1;
var divRowid2=1;
/**增加条件区域**/
function addTermRulsLi(me,sign,sign1) {
    var  fieldByUserWhereHtml="";
    if (dataSelectList.length>0) {
        fieldByUserWhereHtml=getOperator(dataSelectList[0].valueType);
    }
    var  fieldType=$("#valueType");
    //var selectNode=sign.next('select');
    var value=fieldType.find("option:selected").attr("value");
    var inputShow="display: ;";
    var selectShow="display: none;";
    if(value=="3") {
        inputShow="display: none;";
        selectShow="display: ;";
    }
    if(sign=="1"){
        divRowid++;
        $('#regionDiv').append("<div style=\"width: 620px;overflow: hidden\" name=\"divRow\" class=\"c-inclusion\" id=\"divRow"+divRowid+"\"> <div class=\"Remark c-operation-inclusion\" style=\"width: 160px;border-right:1px dashed #000;float:left\"  id=\"divRowLeft"+divRowid+"\"><label style='margin:.2em 0;'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,1,'')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,1,'')\"></span> <input type=\"hidden\" name=\"fieldContent2\"> <input type=\"text\" name=\"conditionValue\" style=\"width: 80px;"+inputShow+"\"> <select name=\"conditionValue\" size=\"1\"  class=\"select\" style=\"width: 80px;"+selectShow+"\"> "+enumerationselectHtml+"</select></label> </div> <div class=\"Remark c-optional-rules\" style=\"width:460px;float:right;text-align:left;\"  id=\"divRowRight"+divRowid+"\"><label style='margin: .2em 0'><div class='c-optional-rules-rules'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,2,'divRowRight"+divRowid+"')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight"+divRowid+"')\"></span> <select name=\"fieldId\" size=\"1\"  onchange='setSelect(this)'  class=\"select\" style=\"width: 80px\"> "+dataSelectListHtml+"</select> <select name=\"operator\" size=\"1\"  class=\"select\" style=\"width: 80px\"> "+fieldByUserWhereHtml+" </select> <input type=\"text\" name=\"fieldValue\" style=\"width: 80px;height:31px\"> <select  size=\"1\"  name=\"logical\" style='display: none;width: 80px' class=\"select\" > "+logical+"</select></div> </label> </div> </div>");
    }
    if(sign=="2"){
        $('#'+sign1+ '  select:last').css({"display":""})
        $('#'+sign1).append("<label style='margin:.2em 0;'><div class='c-optional-rules-rules'> <span class=\"iconaddData\" onclick=\"addTermRulsLi(this,2,'"+sign1+"')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'"+sign1+"')\"></span> <select name=\"fieldId\" size=\"1\"  onchange='setSelect(this)'  class=\"select\" style=\"width: 80px\"> "+dataSelectListHtml+"</select> <select name=\"operator\" size=\"1\"  class=\"select\" style=\"width: 80px\"> "+fieldByUserWhereHtml+" </select> <input type=\"text\" name=\"fieldValue\" style=\"width: 80px;height:31px\"> <select  size=\"1\"  name=\"logical\" style='display: none;width: 80px' class=\"select\" > "+logical+"</select> </div></label> ");
    }
    if(sign=="3")
    {
        $('#'+sign1).append( "<label style='margin:.2em 0;'> <div class='editWhere2'><span class=\"iconaddData\" onclick=\"addTermRulsLi(this,3,'formulaEdit2')\"></span> <span class=\"iconclearData\" onclick=\"deleteTermRulsLi(this,2,'divRowRight1')\"></span> <select  size=\"1\"  name=\"fvalue\" class=\"select\"  style=\"width:220px\"> "+enumerationselectHtml+"</select> <span>    =  </span> <input type=\"text\" name='formula' id='divRowid"+divRowid2+" onclick=\"setCursor(divRowid"+divRowid2+"')\" style=\"width: 220px\"><input type=\"hidden\" name=\"farr\"/></div> </label>");
        setCursor("divRowid1");
    }
}
function deleteTermRulsLi(me,sign,sign1) {
    if(sign=="1"){
        var targetLi=me.parentNode.parentNode.parentNode;
        $(targetLi).remove();
        var liLength=$("#TermRulsLi li").size();
        if(liLength==1) {
            isFirstTerm = 0;
        }
    }
    if(sign=="2"){
        var targetLi=me.parentNode;
        var targetLi1=targetLi.parentNode.size;
        $(targetLi).remove();
        var liLength=$("#TermRulsLi1 li").size();
        if(liLength==1) {
            isFirstTerm = 0;
        }
        $('#'+sign1+ '  select:last').css({"display":"none"})
    }
}
/**是否衍生字段**/
function setDerivative() {
    if($("#isDerivative").val()=="1") {
        setDiv();
        $("#derivedFields").show();
    }else{
        $("#derivedFields").hide();
    }
}
/**修改枚举变量**/
function setSelectList() {
    var  value=$("#restrainScope").val();
    if($("#valueType").val()=="3") {
        var reg = new RegExp("^([\u4e00-\u9fa5()\\[\\]（）A-Za-z0-9\_\-]+:[0-9]+(,)?)+$");
        if(value == ''){
            //layer.tips("字 段 范 围不能为空",$("#fieldRange"), { time: 2000 });
            //layer.msg("字 段 范 围不能为空",{time:2000});
            return false;
        }else if(!reg.test(value)){
            //layer.tips("输入不合法，示例：男:1,女:2",$("#fieldRange"), { time: 2000 });
            //layer.msg("输入不合法，示例：男:1,女:2",{time:2000});
            return false;
        }else{
            var arr=value.split(',');
            enumerationselect={
                push:Array.prototype.push
            };
            for(var i=0;i<arr.length;i++){
                var arr1=arr[i].split(':');
                if(arr1.length>0) {
                    var  va1=arr1[0];
                    var  va2=arr1[1];
                    enumerationselect.push({
                        key:arr1[0],
                        val:arr1[1]
                    });
                }
            }
            enumerationselectHtml="";
            for(var i=0;i<enumerationselect.length;i++){
                 var  ss=enumerationselect[i];
                enumerationselectHtml+="<option  value='"+enumerationselect[i].val+"'>"+enumerationselect[i].key+"</option>"
            }
            $("select[name='conditionValue']").html(enumerationselectHtml);
            $("select[name='fvalue']").html(enumerationselectHtml);
        }
    }
}
/**获取光标位置**/
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
$(".c-operational-character , .c-operational-characterTwo").on("mouseenter",function(){
    $(this).find(".c-operator-show").hide();
    $(this).find(".c-operator-hide").show();
})
$(".c-operational-character , .c-operational-characterTwo").on("mouseleave",function(){
    $(this).find(".c-operator-show").show();
    $(this).find(".c-operator-hide").hide();
});
var dataId;
var cursor;
var cursorid;
function setCursor(id) {
    cursorid=id;
    cursor=$("#"+id);
    cursor.focus();
}
function character(sign){
    if (cursor!=null){
    dataId=$(sign).attr("dataId");
    var strIndex=cursor.getCursorPosition();
    var content=cursor.val();
    var str1=content.slice(0,strIndex);
    var str2=content.slice(strIndex,content.length);
        var txtFocus = document.getElementById(cursorid);
    switch (dataId) {
        case '0':
            str1=str1+'+';
            break;
        case '1':
            str1=str1+'-';
            break;
        case '2':
            str1=str1+'*';
            break;
        case '3':
            str1=str1+'/';
            break;
        case '7':
            str1=str1+'()';
            break;
        default:

            break;
    }
    var newContent=str1+str2;
        cursor.val(newContent);
    if(dataId==7){
        var position=str1.length-1;
        //txtFocus.setSelectionRange(position,position);//设置光标位置，不适用IE
        cursor.focus();
    }else{
        var position=str1.length;
        txtFocus.setSelectionRange(position,position);
        cursor.focus();
    }
    }
};
function characterTwo(sign) {
    if (cursor!=null){
        dataId=$(sign).attr("dataId");
    var strIndex=cursor.getCursorPosition();
    var content=cursor.val();
    var str1=content.slice(0,strIndex);
    var str2=content.slice(strIndex,content.length);
    var txtFocus = document.getElementById(cursorid);
    switch (dataId) {
        case '4':
            str1=str1+'sqrt()';
            break;
        case '5':
            str1=str1+'ln()';
            break;
        case '6':
            str1=str1+'avg()';
            break;
        case '8':
            str1=str1+'abs()';
            break;
        case '9':
            str1=str1+'max()';
            break;
        case '10':
            str1=str1+'min()';
            break;
        case '11':
            str1=str1+'lg()';
            break;
        case '12':
            str1=str1+'exp()';
            break;
        case '13':
            str1=str1+'ceil()';
            break;
        case '14':
            str1=str1+'floor()';
            break;
        case '15':
            str1=str1+'PI';
            break;
        default:
            break;
    };
    var newContent=str1+str2;
        cursor.val(newContent);
    var position=str1.length;
        txtFocus.setSelectionRange(position,position);
        cursor.focus();
}}
/***字段根据引擎是否为空分别跳转到数据管理模块和字段映射模块**/
function fieldGo(obj){
    var _engineId = obj.attributes['engineId'].nodeValue;
    var _url;
    if(_engineId=='null'){
        _url = _ctx+"/datamanage/field";
    }
    else{
        _url = _ctx+"/engineManage/fieldmapping?engineId="+_engineId;
    }
    window.open(_url);
}
/**黑白名单跳转数据管理模块**/
function bwListGo(obj){
    var _dataid = obj.attributes['dataid'].nodeValue;
    var listType = obj.attributes['listType'].nodeValue;
    var _url;
    if(_dataid!='null'){
        if(listType == "b"){
            _url = _ctx+"/datamanage/listmanage/black";
        }else if(listType == "w"){
            _url = _ctx+"/datamanage/listmanage/white";
        }
    }
    window.open(_url);
}
/**规则根据引擎是否为空分别跳转规则管理-规则集模块和引擎管理的知识库模块**/
function ruleManageGo(obj){
    var _engineId = obj.attributes['engineId'].nodeValue,_url;
    if(_engineId!='null'){
        _url = _ctx+"/knowledge/tree?engineId="+_engineId;
    }else{
        _url = _ctx+"/knowledge/tree";
    }
    window.open(_url);
}
/**引擎管理的节点跳转到决策流模块**/
function decisionFlowGo(obj){
    var _engineId = obj.attributes['engineId'].nodeValue;
    var _url;
    if(_engineId!='null'){
        _url = _ctx+"/decision_flow/decisionsPage?flag=1&&id="+_engineId;
    }
    window.open(_url);
}
/***导出字段***/
function exportData(){
    var fieldTypeId = $("#hdNodeId").val();//文件夹id，字段类型编号
    window.open( _ctx+"/datamanage/field/down?fieldTypeId="+fieldTypeId);
}
/**导入字段**/
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
            var nodeId = $("#hdNodeId").val();
            formData.append("nodeId",nodeId);
            Comm.ajaxPost('datamanage/field/uploadField',formData,function (data) {
                layer.close(leadLayer);
                tableUser.draw( false );//刷新数据
            },false,false,false);
        },
        cancel: function (index, layero) {
            layer.close(leadLayer);
        }
    });
}

