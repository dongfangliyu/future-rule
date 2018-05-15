<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/lookOver.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <title>政策结果集详情</title>
</head>
<body>
<input type="hidden"  id="leftjsp_id">
<div class="out-content" style="width:1038px; margin:0 auto;">
    <div class="c-result-matter">
        <div class="c-batchTest-content">
            <div class="c-batchTest-head">
                <div class="c-batchTest-headline">
                    <span class="c-batchTest-results">政策结果集详情</span>
                </div>
            </div>
            <div class="c-batchTest-body">
                <div class="c-batchTest-black rules-details-content">
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    init()
    function init(){
        var ruleJsonDom = window.parent.opener.document.getElementById("ruleJson");
        var ruleJson=$(ruleJsonDom).val();
        console.log(ruleJson);
        var jsonStr =JSON.parse(ruleJson);
        var addOrSubRules = null,deny_rules = null;
        if(jsonStr.addOrSubRules!=''){
            addOrSubRules = $.parseJSON(jsonStr.addOrSubRules)
        }
        if(jsonStr.deny_rules!=''){
            deny_rules = $.parseJSON(jsonStr.deny_rules);
        }

        //拒绝规则
        if(deny_rules !=''  && deny_rules !=null){
            var rule_1 = deny_rules.rules;
            for (var i = 0; i < rule_1.length; i++) {
                var len = $(".rules-details-content").find(".priority_"+rule_1[i].priority).length;
                if(len == 0){
                    var html='<div class="c-refused-rule priority_'+rule_1[i].priority+'">'+
                            '<div class="c-refusedHead">'+
                            '<div class="refusedRule-left">'+
                            '<span>拒绝规则/优先级</span>'+
                            '</div>'+
                            '<div class="refusedRule-right">'+
                            '<div class="refusedRule-input">执行方式:</div>'+
                            '<div class="refusedRule-serial">'+
                            '<span class="c-checkbox serial"></span>'+
                            '<span>串行</span>'+
                            '</div>'+
                            '<div class="refusedRule-serial">'+
                            '<span class="c-checkbox parallel"></span>'+
                            '<span>并行</span>'+
                            '</div>'+
                            '</div>'+
                            '</div>'+
                            '<div class="c-refusedRule-one">'+
                            '<div class="c-show-list">'+
                            '<div class="c-every-last">序号</div>'+
                            '<div class="c-every-last">拒绝规则名称</div>'+
                            '<div class="c-every-last">拒绝规则代码</div>'+
                            '</div>'+
                            '<div class="emptylistOne">'+
                            '</div>'+
                            '</div>'+
                            '</div>';

                    $(".rules-details-content").append(html);
                    $(".rules-details-content").find(".priority_"+rule_1[i].priority).find('.refusedRule-left span').text("拒绝规则/优先级"+rule_1[i].priority)
                    if(deny_rules.isSerial > 0){
                        $(".rules-details-content").find(".priority_"+rule_1[i].priority).find('.refusedRule-serial .serial').addClass("l_back");
                    }else{
                        $(".rules-details-content").find(".priority_"+rule_1[i].priority).find('.refusedRule-serial .parallel').addClass("l_back");
                    }
                }

                var str = '<div class="c-show-list-name">'+
                        '<a>'+rule_1[i].id+'</a>'+
                        '<a>'+rule_1[i].name+'</a>'+
                        '<a>'+rule_1[i].code+'</a>'+
                        '</div> '
                $(".rules-details-content").find(".priority_"+rule_1[i].priority).find(".emptylistOne").append(str);
            }
        }

        //加减分规则
        if(addOrSubRules !=''   && addOrSubRules !=null){
            var rule_2 = addOrSubRules.rules;
            var len = $(".rules-details-content").find(".addOrSub").length;
            if(len == 0){
                var html = '<div class="c-refused-rule addOrSub">'+
                        '<div class="c-refusedHead">'+
                        '<div class="refusedRule-left">'+
                        '<span>加减分规则</span>'+
                        '</div>'+
                        '<div class="refusedRule-right">'+
                        '<div class="refusedRule-input">减分阈值：</div>'+
                        '<div class="refusedRule-input">'+
                        '<input id="threshold" type="text" />'+
                        '</div>'+
                        '</div>'+
                        '</div>'+
                        '<div class="c-refusedRule-one">'+
                        '<div class="c-show-list">'+
                        '<div class="c-every-last">序号</div>'+
                        '<div class="c-every-last">拒绝规则名称</div>'+
                        '<div class="c-every-last">拒绝规则代码</div>'+
                        '</div>'+
                        '<div class="emptylistOne">'+
                        '</div>'+
                        '</div>'+
                        '</div>'
                $(".rules-details-content").append(html);
            }

            $("#threshold").val(addOrSubRules.threshold);
            for (var i = 0; i < rule_2.length; i++) {
                var str = '<div class="c-show-list-name">'+
                        '<a>'+rule_2[i].id+'</a>'+
                        '<a>'+rule_2[i].name+'</a>'+
                        '<a>'+rule_2[i].code+'</a>'+
                        '</div> '
                $(".rules-details-content").find(".addOrSub .emptylistOne").append(str);
            }
        }
    }
</script>
</body>
</html>