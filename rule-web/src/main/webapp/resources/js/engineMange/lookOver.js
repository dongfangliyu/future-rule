$(function(){
    var id=$("#resultSetId").val();
    var param={};
    param.resultSetId=id;
    Comm.ajaxPost('engine/pageinput', param, function (data) {
        var erlist=data.data.er;
        var blackList=data.data.blackList;//黑名单
        var whiteList=data.data.whiteList;//白名单
        var deadRules=data.data.deadRules;//硬性拒绝规则
        var focusRules=data.data.focusRules;//加减分规则
        var addOrSubRules=data.data.addOrSubRules;//加减分规则列表
        var denyRules=data.data.denyRules;//拒绝规则列表
        $("#engineId").html(erlist.engineId);
        $("#engineCode").html(erlist.engineCode);
        $("#engineName").html(erlist.engineName);
        $("#result").html(erlist.result);
        $("#scorecardscore").html(erlist.scorecardscore);
        //黑名单
        var blackhtml="";
        if(blackList==null||blackList.length==0){
            blackhtml+="<p class='target'>未命中</p>";
            $(".emptylistOne ul").html(blackhtml);
        }else{
            for(var i=0;i<blackList.length;i++){
                blackhtml+="<li>"+blackList[i].type+"</li><li>"+blackList[i].name+"</li><li>"+blackList[i].desc+"</li>";
            }
            $(".emptylistOne ul").html(blackhtml);
            var blackliList=$(".emptylistOne ul li");
            for(var j=0;j<blackliList.length;j++){
                if($(blackliList[j]).html()=='null'){
                    $(blackliList[j]).html("");
                }
            }
        }
        //白名单
        var whitehtml="";
        if(whiteList==null||whiteList.length==0){
            whitehtml+="<p class='target'>未命中</p>";
            $(".emptylistTwo ul").html(whitehtml);
        }else{
            for(var i=0;i<whiteList.length;i++){
                whitehtml+="<li>"+whiteList[i].type+"</li><li>"+whiteList[i].name+"</li><li>"+whiteList[i].desc+"</li>";
            }
            $(".emptylistTwo ul").html(whitehtml);
            var whiteliList=$(".emptylistTwo ul li");
            for(var j=0;j<whiteliList.length;j++){
                if($(whiteliList[j]).html()=='null'){
                    $(whiteliList[j]).html("");
                }
            }
        }

        //硬性拒绝规则
        var refusehtml="";
        if(deadRules==null||deadRules.length==0){
            refusehtml+="<p class='target'>未命中</p>";
            $(".emptylistThree ul").html(refusehtml);
        }else{
            for(var i=0;i<deadRules.length;i++){
                refusehtml+="<li>"+deadRules[i].id+"</li><li>"+deadRules[i].name+"</li><li>"+deadRules[i].desc+"</li><li>"+deadRules[i].code+"</li>"
            }
            $(".emptylistThree ul").html(refusehtml);
            var refuseliList=$(".emptylistThree ul li");
            for(var j=0;j<refuseliList.length;j++){
                if($(refuseliList[j]).html()=='null'){
                    $(refuseliList[j]).html("");
                }
            }
        }
        //加减分规则
        var rulehtml="";
        if(focusRules==null||focusRules.length==0){
            rulehtml+="<p class='target'>未命中</p>";
            $(".emptylistFour ul").html(rulehtml);
        }else{
            for(var i=0;i<focusRules.length;i++){
                rulehtml+="<li>"+focusRules[i].id+"</li><li>"+focusRules[i].name+"</li><li>"+focusRules[i].desc+"</li><li>"+focusRules[i].code+"</li>";
                $(".emptylistFour ul").html(rulehtml);
                var ruleliList=$(".emptylistFour ul li");
                for(var j=0;j<ruleliList.length;j++){
                    if($(ruleliList[j]).html()=='null'){
                        $(ruleliList[j]).html("");
                    }
                }
            }
        }
        //加减分规则列表
        var rulehtmlList="";
        if(addOrSubRules==null||addOrSubRules.length==0){
            rulehtmlList+="<p class='target'>无加减分规则列表</p>";
            $(".emptylistFive ul").html(rulehtmlList);
        }else{
            for(var i=0;i<addOrSubRules.length;i++){
                var fieldInfo=getFieldInfo(addOrSubRules[i].fieldInfo);
                rulehtmlList+="<li>"+addOrSubRules[i].id+"</li><li>"+addOrSubRules[i].name+"</li><li>"+addOrSubRules[i].desc+"</li><li>"+fieldInfo+"</li>";
                $(".emptylistFive ul").html(rulehtmlList);
                var ruleliList=$(".emptylistFive ul li");
                for(var j=0;j<ruleliList.length;j++){
                    if($(ruleliList[j]).html()=='null'){
                        $(ruleliList[j]).html("");
                    }
                }
            }
        }
        //拒绝规则列表
        var refusehtmlList="";
        if(denyRules==null||denyRules.length==0){
            refusehtmlList+="<p class='target'>无拒绝规则列表</p>";
            $(".emptylistSixe ul").html(refusehtmlList);
        }else{
            for(var i=0;i<denyRules.length;i++){
                var fieldInfo=getFieldInfo(denyRules[i].fieldInfo);
                refusehtmlList+="<li>"+denyRules[i].id+"</li><li>"+denyRules[i].name+"</li><li>"+denyRules[i].desc+"</li><li>"+fieldInfo+"</li>";
                $(".emptylistSixe ul").html(refusehtmlList);
                var ruleliList=$(".emptylistSixe ul li");
                for(var j=0;j<ruleliList.length;j++){
                    if($(ruleliList[j]).html()=='null'){
                        $(ruleliList[j]).html("");
                    }
                }
            }
        }

        //自定义名单
        // var customDb=data.data.customDb;//自定义名单
        // var customHtml="";
        // if(customDb==null||customDb.length==0){
        //     customHtml +="<p class='target'>未命中</p>";
        //     $(".emptylistFive ul").html(customHtml);
        // }else{
        //     for(var i=0;i<customDb.length;i++){
        //         customHtml +="<li style='text-align: center'>"+customDb[i].name+"</li>";
        //         $(".emptylistFive ul").html(customHtml);
        //         var customDbList=$(".emptylistFive ul li");
        //         for(var i=0;i<customDbList.length;i++){
        //             if($(customDbList[i]).html()=='null'){
        //                 $(customDbList[i]).html("");
        //             }
        //         }
        //     }
        // }

    })
})
function getFieldInfo(field) {
    var arr=field.split("|");
    var allList=[];
    for(var t=0;t<arr.length;t++){
        if(arr[t]){
            allList.push(arr[t]);
        }
    }
    return allList;
}
