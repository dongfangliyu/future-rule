var node_2,sanBoxName=2,groupJson={},map_child=new Map(),pri_version=new Map(),pri_msg=new Map(),child_msg=new Map(),pri_state=0;
groupJson.conditions=new Array();
//主版本对应的子版本信息map_child
//子版本对应的版本信息child_msg
/************初始化页面信息**************/
getRunVersion();
function getRunVersion(){
    var id= $("input[name='engineId']").val();
    Comm.ajaxPost('engine/version',id, function(result){
            var data=result.data;
            for(var i=0;i<data.length;i++){
                var subArr=[];
                if(data[i].engineVersion.bootState==1){//主版本状态为1
                    var preVersionId=data[i].engineVersion.verId;
                    pri_state=1;
                    var newVersion=$('<div class="l_left_versionTxt runVersion ver_select"><input type="hidden" value="engineVersion" /><span style="margin-right:10px;" class="run_point"></span><span >正在运行 &nbsp;V <b>'+data[i].engineVersion.version+'</b></span></div>');
                    $(".l_run_version").append(newVersion);
                    if(data[i].subEngineVersionList.length<=0){
                        $(".l_right_versionTxt").empty();
                    }else{
                        var run_ver=$(".ver_select").find('b').html();
                        for(var j=0;j<data[i].subEngineVersionList.length;j++){
                            var newSubVersion=$('<div class="l_left_versionTxt stopVersion"><input type="hidden" value="engineVersion" /><span style="margin-right:10px;" class="stop_point"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>'+run_ver+'.'+data[i].subEngineVersionList[j].subVer+'</b></span></span></div>');
                            subArr.push(data[i].subEngineVersionList[j]);
                            $(".l_right_versionTxt").append(newSubVersion);
                            child_msg.put(data[i].subEngineVersionList[j].subVersion,data[i].subEngineVersionList[j]);
                        }
                    }
                    $("input[name='initEngineVersionId']").val(preVersionId);
                    pageInit();
                }else{//主版本状态为0
                    var newVersion=$('<div class="l_left_versionTxt stopVersion"><span style="margin-right:10px;" class="stop_point"></span><span>未部署</span><span style="margin-left:20px;">修订版本 &nbsp;V <b>'+data[i].engineVersion.version+'</b></span></div>');
                    $(".l_run_version").append(newVersion);
                    if(data[i].subEngineVersionList.length>0){
                        for(var j=0;j<data[i].subEngineVersionList.length;j++){
                            subArr.push(data[i].subEngineVersionList[j]);
                        }
                    }
                }
                map_child.put(data[i].engineVersion.version,subArr);
                pri_version.put(data[i].engineVersion.version,data[i].engineVersion.bootState);
                pri_msg.put(data[i].engineVersion.version,data[i].engineVersion);
                if(i==data.length-1&&pri_state==0&&data[i].engineVersion.bootState==0){//所有版本状态为0，默认选中第一个版本
                    if(i!=0){
                        if(data[i].subEngineVersionList.length>0){
                            var run_ver=$(".ver_select").find('b').html();
                            for(var j=0;j<data[i].subEngineVersionList.length;j++){
                                subArr.push(data[i].subEngineVersionList[j]);
                                child_msg.put(data[i].subEngineVersionList[j].subVersion,data[i].subEngineVersionList[j]);
                            }
                        }
                    }
                    $(".l_run_version").find(".l_left_versionTxt").eq(0).find(".stop_point").attr("class","run_point");
                    $(".l_run_version").find(".l_left_versionTxt").eq(0).addClass("ver_select");
                    if(data[0].subEngineVersionList.length<=0){
                        $(".l_right_versionTxt").empty();
                    }else{
                        var run_ver=$(".ver_select").find('b').html();
                        for(var j=0;j<data[0].subEngineVersionList.length;j++){
                            var newSubVersion=$('<div  class="l_left_versionTxt stopVersion"><input type="hidden" value="engineVersion" /><span style="margin-right:10px;" class="stop_point"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>'+run_ver+'.'+data[0].subEngineVersionList[j].subVer+'</b></span></span></div>');
                            $(".l_right_versionTxt").append(newSubVersion);
                            if($(".l_right_versionTxt").find(".l_left_versionTxt").length>3){
                                $(".l_right_versionTxt").css({'overflow':'auto'});
                            }
                        }
                    }
                    $("#arrange").html("部署").attr("class","arranging operateBtn");
                    var preVersionIds=data[0].engineVersion.verId;
                    $("input[name='initEngineVersionId']").val(preVersionIds);
                    pageInit();
                }
            }
        },"application/json")
}
/**********点击部署及停止部署操作********/
$("#arrange").on("click",function(){
    var child=false;
    var className=$(this).attr("class").split(" ")[0];
    if(className=='arranging'){
        if($(".ver_select").find(".child_point").length>0){
            child=true;
        }
        var engineMsg=$("input[name=initEngineVersionId]").val();
        var param={versionId:engineMsg};
        Comm.ajaxPost("engineVersion/deploy",param,function(result){
            layer.closeAll();
            if(child==true){
                location.reload();
            }else{
                layer.msg("主版本开始部署！",{time:1000});
                var runVersion=$(".runVersion").find("b").html();//获取当前正在运行版本的版本号
                var runState=$('<span style="margin-right:10px;" class="stop_point"></span><span>未部署<span style="margin-left:20px;">修订版本 &nbsp;V <b>'+runVersion+'</b></span></span>');//改变当前正在运行的版本的状态
                if($(".runVersion").length>0){
                    $(".runVersion").empty().append(runState).addClass("stopVersion").removeClass("runVersion");//更新当前正在运行的版本状态
                    pri_msg.get(runVersion).bootState=0;
                }
                var priVersion=$(".ver_select").find("b").html();//当前将要部署的主版本号
                pri_msg.get(priVersion).bootState=1;//改变部署版本的运行状态信息
                var newRunVersion=$('<input type="hidden" value="engineVersion" /><span style="margin-right:10px;" class="run_point"></span><span >正在运行 &nbsp;V <b>'+priVersion+'</b></span>');//生成当前将要部署的版本状态
                $(".ver_select").empty().append(newRunVersion).addClass("runVersion").removeClass("stopVersion");
                $("#arrange").html("停止部署").attr("class","stopArrange operateBtn");
            }
        })
    }else if(className=='stopArrange'){//停止部署
        var selec_version=$(".ver_select").find("b").html();
        var engineMsg=pri_msg.get(selec_version).verId;
        var param={versionId:engineMsg};
        Comm.ajaxPost("engineVersion/undeploy",param,function(result){
            layer.closeAll();
            layer.msg("停止部署！",{time:1000});
            var runVersion=$(".runVersion").find("b").html();//获取当前正在运行版本的版本号
            var runState=$('<span style="margin-right:10px;"  class="run_point"></span><span>未部署<span style="margin-left:20px;">修订版本 &nbsp;V <b>'+runVersion+'</b></span></span>');//改变当前正在运行的版本的状态
            $(".runVersion").empty().append(runState).addClass("stopVersion").removeClass("runVersion");//更新当前正在运行的版本状态
            pri_msg.get(runVersion).bootState=0;
            $("#arrange").html("部署").attr("class","arranging operateBtn");
        })
    }
});
/**********点击新建操作*********/
$("#adds").on("click",function(){
    var engineId= $("input[name='engineId']").val();
    if($(".ver_select").find(".child_point").length>0){
        var presentVersion=$(".ver_select").find("b").html().split(".")[0];
    }else{
        var presentVersion=$(".ver_select").find("b").html();
    }
    var param={'engineId':engineId,'version':presentVersion};
    Comm.ajaxPost("engineVersion/add",param,function(result){
        layer.closeAll();
        layer.msg("新建成功！");
        var data=result.data;
        if($(".l_stop_version").find(".l_left_versionTxt ").length>0){
            var pri_lastId=$(".l_stop_version").find(".l_left_versionTxt").last().find("b").html().split(".")[0];//新建版本所属主版本
            var child_lastId=parseInt($(".l_stop_version").find(".l_left_versionTxt ").last().find("b").html().split(".")[1])+1;//新建子版本号
        }else{
            var pri_lastId=$(".ver_select").find("b").html();//新建版本所属主版本
            var child_lastId=1;
        }
        $(".ver_select").removeClass("ver_select");
        $(".l_stop_version").find(".run_point").removeClass("run_point").addClass("stop_point");
        var newVersionHtml=$('<div  class="l_left_versionTxt stopVersion ver_select"><span class="child_point run_point" style="margin-right:10px;"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>'+pri_lastId+'.'+child_lastId+'</b></span></span></div>');
        $(".l_right_versionTxt").append(newVersionHtml);
        $("input[name=initEngineVersionId]").val(data.versionId);
        var newObj={verId:data.versionId,subVer:child_lastId};
        map_child.get(pri_lastId).push(newObj);
        $("#arrange").html("部署").attr("class","arranging operateBtn");
        pageInit();
    })
});
/**************清空画布***********/
$("#clearAll").on("click",function(){
    layer.confirm('是否清空画布？', {btn: ['确定', '取消']}, function () {
        var versionId=$("input[name=initEngineVersionId]").val();
        var param={'versionId':versionId};
        Comm.ajaxPost("engineVersion/clear", param, function(data){
            $(".bigCircle").hide();
            scene.clear();
            pageInit();
            layer.msg(data.msg);
        })
    })

});
/**************点击保存操作***********/
$("#versionSave").on("click",function(){
    $(".c-prompt-content-add").empty();
    var preVersionId=$("input[name=initEngineVersionId]").val();
    var param={'versionId':preVersionId};
    Comm.ajaxGet("decision_flow/saveVersion", param, function(result){
            layer.msg("生成子版本!");
            var data=result.data;
            if($(".l_stop_version").find(".l_left_versionTxt ").length>0){
                var pri_lastId=$(".l_stop_version").find(".l_left_versionTxt").last().find("b").html().split(".")[0];//新建版本所属主版本
                var child_lastId=parseInt($(".l_stop_version").find(".l_left_versionTxt ").last().find("b").html().split(".")[1])+1;//新建子版本号
            }else{
                var pri_lastId=$(".ver_select").find("b").html();//新建版本所属主版本
                var child_lastId=1;
            }
            $(".ver_select").removeClass("ver_select");
            $(".l_stop_version").find(".run_point").removeClass("run_point").addClass("stop_point");
            var newVersionHtml=$('<div class="l_left_versionTxt stopVersion ver_select"><span class="child_point run_point" style="margin-right:10px;"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>'+pri_lastId+'.'+child_lastId+'</b></span></span></div>');
            $(".l_right_versionTxt").append(newVersionHtml);
            $("input[name=initEngineVersionId]").val(data.verId);
            var newObj={verId:data.verId,subVer:child_lastId};
            map_child.get(pri_lastId).push(newObj);
            child_msg.put(child_lastId,newObj);
            $("#arrange").html("部署").attr("class","arranging operateBtn");
            pageInit();
        });
    var disSave=[];
    var allNodes=scene.getDisplayedNodes();
    for(var i=0;i<allNodes.length;i++){
        if(allNodes[i].type!=1&&(allNodes[i].inLinks==null||(allNodes[i].inLinks!=null&&allNodes[i].inLinks.length==0))){
            disSave.push(allNodes[i].text);
        }
    }
    var disSaves=disSave.unique3();
    if(disSave.length!=0){//判断是否有节点未进行连线
        $(".c-hide-prompt").show();
        var newContent=$('<div class="c-prompt-content" style="color:red;"></div>');
        $(".c-prompt-content-add").append(newContent);
        $(".c-prompt-content").last().empty().append('您的以下节点未进行连线：'+disSaves.join(','));
    }
    //判断沙盒与分群分组与连线是否匹配
    var types = [3,7];
    var preVersionId=$("input[name=initEngineVersionId]").val();
    var param_type={types:types,versionId:preVersionId};
    Comm.ajaxPost("engineVersion/getTypedNodes", param_type, function(result){
            var data=result.data;
            if(data.hasOwnProperty("sanbox")){
                if(data.sanbox==0){
                    $(".c-hide-prompt").show();
                    var newContent=$('<div class="c-prompt-content" style="color:red;"></div>');
                    $(".c-prompt-content-add").append(newContent);
                    $(".c-prompt-content").last().empty().append('您所设置的沙盒比例分组数与所连线数量不匹配！');
                }
            }
            if(data.hasOwnProperty("group")){
                if(data.group==0){
                    $(".c-hide-prompt").show();
                    var newContent=$('<div class="c-prompt-content" style="color:red;"></div>');
                    $(".c-prompt-content-add").append(newContent);
                    $(".c-prompt-content").last().empty().append('您所设置的客户分群分组数与所连线数量不匹配！');
                }
            }
        })
});
$(".l_decision_options").on("click","ul li",function(){
    $(".l_decision_options li").removeClass("selected");
    $(this).addClass("selected");
});
/**************通用删除***********/
$(".delete").on("click",function(){
    //判断引擎是否部署
    if(isDeploy()){
        return;
    }
    var dataId=($(".look").attr("dataId"));
    var id =node_2.id;
    var param;
    order_count--;
    if(node_2.id==0){
        scene.remove(node_2);
        $(".bigCircle").hide();
    }else{
        if(dataId == 4){
            param ={"nodeId":id,"type":1};
        }else if(dataId == 5){
            param ={"nodeId":id,"type":2};
        }else{
            param ={"nodeId":id}
        }
        if(dataId==-1){
            layer.msg("开始节点不能删除",{time:1000});
            $(".bigCircle").hide();
            return;
        }else{
            deleteNode(param);
        }
    }
});
/**************通用复制***********/
$(".copy").on("click",function(){
    //判断引擎是否部署
    if(isDeploy()){
        return;
    }
    var dataId=($(".look").attr("dataId"));
    var id =node_2.id;
    var param;
    if(dataId == 4){
        param ={"nodeId":id,"type":1};
    }else if(dataId == 5){
        param ={"nodeId":id,"type":2};
    }else{
        param ={"nodeId":id}
    }
    copyNode(param,node_2);
});
/**************通用编辑***********/
$(".operate").on("click",function(){
    var dataId=($(".look").attr("dataId"));
    switch (dataId) {
        case '-1':
            console.log("开始节点");
            $(".bigCircle").hide();
            break;
        case '12':
            console.log("黑名单");
            blackInit(node_2.id,'Y');
            $(".bigCircle").hide();
            break;
        case '1':
            console.log("白名单");
            whiteInit(node_2.id,'Y');
            $(".bigCircle").hide();
            break;
        case '2':
            console.log("沙盒比例");
            sandboxedit();
            $(".bigCircle").hide();
            break;
        case '3':
            console.log("客户分群");
            groupedit(node_2.id,'Y');
            $(".bigCircle").hide();
            break;
        case '4':
            console.log("规则集");
            lookOrOperate(node_2.id,"G");
            $(".bigCircle").hide();
            break;
        case '5':
            console.log("评分卡");
            getpage();
            $(".bigCircle").hide();
            break;
        case '7':
            console.log("决策选项");
            tagOption(node_2.id);
            $(".bigCircle").hide();
            //决策选项输入区间的验证
            var section=/^(\(|\[)([1-9][0-9]*|0{1})(\.([0-9]+))?\,([1-9][0-9]*|0{1})(\.([0-9]+))?(\)|\])$/;
            $(".l_inputs").on("focus",function(){
                $(this).addClass("l_foc")
            });
            $(".l_inputs").on("blur",function(){
                var sectionContent=$(".l_foc").val();
                var sectionContent=$(this).val();
                if(section.test(sectionContent)==false){
                    layer.msg("您的输入不符合要求，请重新输入",{time:1000});
                    $(this).val('');
                }
            });
            break;
        case '11':
            console.log("复杂规则");
            lookOrOperate(node_2.id,"F");
            $(".bigCircle").hide();
            break;
        default:
            break;
    }
});
/**************通用查看***********/
$(".look").on("click",function(){
    var dataId=($(this).attr("dataId"));
    switch (dataId) {
        case '-1':
            console.log("开始节点");
            $(".bigCircle").hide();
            break;
        case '12':
            console.log("黑名单");
            blackInit(node_2.id,'N');
            $(".bigCircle").hide();
            break;
        case '1':
            console.log("白名单");
            whiteInit(node_2.id,'N');
            $(".bigCircle").hide();
            break;
        case '2':
            console.log("沙盒比例");
            sandboxedit();
            $(".bigCircle").hide();
            break;
        case '3':
            console.log("客户分群");
            groupedit(node_2.id,0);
            $(".bigCircle").hide();
            break;
        case '4':
            console.log("规则集");
            lookOrOperate(node_2.id,"G");
            $(".bigCircle").hide();
            break;
        case '5':
            console.log("评分卡");
            getpage();
            $(".bigCircle").hide();
            break;
        case '7':
            console.log("决策选项");
            tagOption(node_2.id);
            $(".bigCircle").hide();
            //决策选项输入区间的验证
            var section=/^(\(|\[)([1-9][0-9]*|0{1})(\.([0-9]+))?\,([1-9][0-9]*|0{1})(\.([0-9]+))?(\)|\])$/;
            $(".l_inputs").on("focus",function(){
                $(this).addClass("l_foc")
            })
            $(".l_inputs").on("blur",function(){
                var sectionContent=$(".l_foc").val();
                if(section.test(sectionContent)==false){
                    layer.msg("您的输入不符合要求，请重新输入",{time:1000});
                    $(this).val('');
                }
            })
            break;
        case '11':
            console.log("复杂规则");
            lookOrOperate(node_2.id,"F");
            $(".bigCircle").hide();
            break;
        default:
            break;
        // case '15'://demo测试---------------------------------
        //     console.log("自定义名单");
        //     openCustomDb(node_2.id);//打开名单页面
        //     $(".bigCircle").hide();
        //     break;//-----------------------------------------
    }
});
/**************通用关闭选项***********/
$(".blue").on("click",function(){
    $(".bigCircle").hide();
});
/**************通用显示选项***********/
$(".hovers").hover(function(){
    $(this).css("background","#398DEE");
    $(this).find(".shows").hide();
    $(this).find(".hides").show();
},function(){
    $(this).css("background","#FFFFFF");
    $(this).find(".shows").show();
    $(this).find(".hides").hide();
});
/**************数据填写弹框***********/
var fielNum;
var  infor;
$("#clickWrite").on("click",function(){
    $(".write-data-content").empty();
    var engineMsg=$("input[name=initEngineVersionId]").val();
    var engineVersion={
        verId:engineMsg
    };
    var sing;
    $(".writeHistory").hide();
    // $(".writeCustom").hide();//自定义名单
    Comm.ajaxPost("engine/engineField",JSON.stringify(engineVersion),function (result) {
        var data=result.data;
        var fields=data.fields;
        if(data.type!=undefined&&data.type==13){
            $("#hisWrite").find("tr").each(function() {
                $(this).empty();
            });
            $(".writeHistory").show();
            fielNum=data.complex.length;
            for(var i=0;i<4;i++){
                for(var j=0;j<=fielNum;j++){
                    if(i==0&&j==0){
                        var ths=$('<th style="text-align: center;padding:10px 0;padding-left: 12px;">序号</th>');
                        $("#hisWrite").find("tr").eq(0).append(ths);
                    }else if(i==0&&j!=0){
                        var ths=$('<th style="text-align: center;padding:10px 0;">'+data.complex[j-1].cnName+'</th>');
                        $("#hisWrite").find("tr").eq(0).append(ths);
                    }else{
                        if(j==0){
                            var tds=$('<td><input  type="text" value="'+i+'" style="outline:none;background:white;border: none;width:30px;text-align: center" readonly/></td>');
                            $("#hisWrite").find("tr").eq(i).append(tds);
                        }else{
                            if(data.complex[j-1].valueType=="3"){
                                var restrainScope=data.complex[j-1].restrainScope;
                                if(restrainScope){
                                    var restrainScopeArr=restrainScope.split(",");
                                    var arr=[];
                                    for(var t=0;t<restrainScopeArr.length;t++){
                                        arr.push(restrainScopeArr[t].split(":"));
                                    }
                                    var options="";
                                    for(var q=0;q<restrainScopeArr.length;q++){
                                        options+='<option value="'+arr[q][1]+'" data="'+arr[q][1]+'">'+arr[q][0]+'</option>';
                                    }
                                    $("#hisWrite").find("tr").eq(i).append("<td><select style='margin:.5em' name='"+data.complex[j-1].enName+"'>"+options+"</select></td>");
                                }
                            }else{
                                var tds=$('<td><input name='+data.complex[j-1].enName+' type="text" value="" style="margin:.5em"/></td>');
                                $("#hisWrite").find("tr").eq(i).append(tds);
                            }
                        }
                    }
                }
            }
        }
        $.each(fields,function(index,value){
            var str="";
            infor=fields[index];
            var  enumerateStr=infor.restrainScope;
                if(enumerateStr!=""&&enumerateStr!=null){
                    var nameArr= enumerateStr.split(",");
                    var t_t = "";
                    for(var i=0;i<nameArr.length;i++){
                        var arr_r = nameArr[i].split(":");
                        for(var j=0;j<arr_r.length-1;j++){
                            var tt =  "<option value="+arr_r[1]+">"+arr_r[0]+"</option>";
                            t_t+= tt;
                        }
                    }
                }
                if(infor.valueType==3){
                    str=$('<div class="write-data-every"><span name="'+infor.enName+'" title="'+infor.cnName+'">'+infor.cnName+'</span>'+
                        '<select class="c-drop-down write-data-input">'+t_t+'</select></div>')
                }else{
                    str=$('<div class="write-data-every"><span  name="'+infor.enName+'" title="'+infor.cnName+'">'+infor.cnName+'</span><input type="text" name="test" class="write-data-input"/></div><form id="write_form_id">');
                }
                $(".write-data-content").append(str);
        });
        var contentDiv=$(".write-data-content").text();
        if(contentDiv==""){
            $(".write-data-content").append("<div style='text-align: left;margin-bottom: 1em;'><span style='margin-left: .2em;'>规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;则：</span>您的引擎没有字段...</div>");
            if(data.type==undefined||data.type==null){
                sing=0;
            }else{
                sing=1;
            }
        }else{
            sing=1;
        }
        //自定义名单
        // Comm.ajaxGet("CustomDb/isCustom",engineVersion,function (result) {
        //     $("#customDbName").val("");//自定义名单
        //     $(".writeCustom").show();
        // });
    },"application/json","","","",false);
    showDataLayer(sing);
});
/*****************点击子版本切换信息****************/
$(document).on("click",".l_stop_version .l_left_versionTxt",function(){
    $("#arrange").html("部署");
    $("#arrange").attr("class","arranging operateBtn");
    $(".ver_select").removeClass("ver_select");
    $(this).addClass("ver_select");
    $(".l_stop_version").find(".run_point").removeClass("run_point").addClass("stop_point");
    $(this).find(".stop_point").addClass("run_point").removeClass("stop_point");
    var display_ver=$(".ver_select").find("b").html().split('.')[0];
    var child_ver=$(".ver_select").find("b").html().split('.')[1];
    var verLen=map_child.get(display_ver).length;
    for(var i=0;i<verLen;i++){
        if(map_child.get(display_ver)[i].subVer==child_ver){
            var	pri_verId=map_child.get(display_ver)[i].verId;
            //子版本信息map_child.get(display_ver)[i]
        }
    }
    $("input[name=initEngineVersionId]").val(pri_verId);
    pageInit();
});
/*****************点击切换子版本信息****************/
$(document).delegate(".l_run_version .l_left_versionTxt","click",function(){
    $(".l_right_versionTxt").empty();
    var versionNum=$(this).find("b").html();
    var sub_version=map_child.get(versionNum);
    var presentVersionId=pri_msg.get(versionNum).verId;
    $("input[name=initEngineVersionId]").val(presentVersionId);
    if($(this).hasClass("runVersion")){
        $("#arrange").html("停止部署");
        $("#arrange").attr("class","stopArrange operateBtn");
        $(".l_left_versionTxt").removeClass("ver_select");
        $(this).addClass("ver_select");
        pageInit();
    }else{
        $("#arrange").html("部署");
        $("#arrange").attr("class","arranging operateBtn");
        $(".l_left_versionTxt").removeClass("ver_select");
        $(this).addClass("ver_select");
        pageInit();
    }
    $(".run_point").attr("class","stop_point");
    $(this).find(".stop_point").attr("class","run_point");
    if(sub_version.length>0){
        for(var i=0;i<sub_version.length;i++){
            if(sub_version[i].subVer){
                var newSubVersion=$('<div  class="l_left_versionTxt stopVersion"><span class="child_point stop_point" style="margin-right:10px;"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>'+versionNum+'.'+sub_version[i].subVer+'</b></span></span></div>');
            }else{
                var newSubVersion=$('<div  class="l_left_versionTxt stopVersion"><span class="child_point stop_point" style="margin-right:10px;"></span><span>未部署<span class="child_point" style="margin-left:20px;">子版本 &nbsp;V <b>'+versionNum+'.'+sub_version[i].subVersion+'</b></span></span></div>');
            }
            $(".l_right_versionTxt").append(newSubVersion);
        }
    }
});
/*****************数据填写弹出框***************/
function showDataLayer(sing) {
    $("#customDbName").val("");
    var height,btn;
    if(sing==1){
        btn=[ '执行', '取消' ];
        height=40;
        var s=$(".write-data-content .write-data-every").size();
        if(s<=5){
            height=130+height*s;
        }else{
            height=320;
        }
    }else{
        btn=[ '执行', '取消' ];
        height=150;
    }
    layer.open({
        type : 1,
        title : '数据填写',
        maxmin : true,
        shadeClose : false,
        area : [ '350px', height+'px' ],
        content : $('.write-data-message'),
        btn : btn,
        yes : function(index, layero) {
            var valObj="";
            var valName="";
            var mapObj=[];
            if($(".ver_select").find(".child_point").length>0){
                var versionNum=$(".ver_select").find("b").html().split(".")[0];
            }else{
                var versionNum=$(".ver_select").find("b").html();
            }
            var engineMsg=$("input[name=initEngineVersionId] ").val();
            for(var i=0;i<$(".write-data-every").length;i++){
                var inputMsg=$(".write-data-input").eq(i).val();
                var nameMsg=$(".write-data-every").eq(i).find("span").attr("name");
                valObj=inputMsg;
                valName=nameMsg;
                mapObj[i]='{"'+valName+'":"'+valObj+'"}';
            }
            // //------------自定义名单
            // var customDb = $("#customDbName").val();
            // if(customDb != ""){
            //     var custom = '{"customDb":"'+customDb+'"}';
            //     mapObj.push(custom)
            // }
            // //------------自定义名单end
            if(hisData!=null&&hisData.length>0&&$(".writeHistory").is(':visible')){
                hisData=hisData;
            }else{
                hisData=null;
            }
            mapObj=JSON.stringify(mapObj);
            Comm.ajaxPost("engine/pageCheck",{versionId:engineMsg,type:2, valueScope:mapObj, complex:hisData},function (result) {
                layer.closeAll();
                var data=result.data;
                if(data.id == null || data.id == 0){
                    layer.msg("执行失败",{time:1000},function(){
                        errorNodeList = data.errorNodeList ;
                        if(errorNodeList !=null && errorNodeList != undefined){
                            var str = "【";
                            for (var i = 0; i < errorNodeList.length; i++) {
                                str += errorNodeList[i].nodeName +"、"
                            }
                            str = str.substring(0,str.length-1);
                            str +="】节点存在问题，请修改后重新执行";
                            layer.msg(str);
                        }
                    });
                    return;
                }else{
                    $(".write-data-content").empty();
                    window.open(_ctx+"/engine/lookOver?resultSetId="+data.id,'_blank');
                }
            })
        },
        cancel:function () {
            $(".write-data-content").empty();
        }
    });
}
var hisData=[];
function showHistory() {
    var size=235;
    var width=685;
    var ln=$("#hisWrite tbody tr:eq(0) td").size();
    if(ln<=3){
        if(ln==1){
            width=size;
        }else{
            width=(ln-1)*size;
        }
    }else{
        width=width;
    }
    var dataLayer=layer.open({
        type : 1,
        title : '复杂规则填写',
        area : [ width+'px', '230px' ],
        content : $('.write-history-order'),
        btn :["保存","取消"],
        yes : function(index, layero) {
                hisData=[];
                for(var i=1;i<4;i++){
                    var obj={};
                    for(var j=0;j<=fielNum;j++){
                        if(j==0){
                            obj['lineId']=$(".history_table tr").eq(i).find("input,select").eq(j).val();
                        }else{
                            obj[$(".history_table tr").eq(i).find("input,select").eq(j).attr("name")]=$(".history_table tr").eq(i).find("input,select").eq(j).val();
                        }
                    }
                    hisData.push(obj);
                }
                hisData=JSON.stringify(hisData);
                layer.close(dataLayer);
        }
    });
}
/***************引入评分卡弹框页面***************/
function getpage(){
    var isShowScoreFileds=$("#isShowScoreFileds").val();
    if(isShowScoreFileds){
        g_scoreManage.tableUser.ajax.reload();
    }else{
        g_scoreManage.tableUser = $('#scoreFields_list').dataTable($.extend({
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
                var queryFilter = g_scoreManage.getQueryCondition(data);
                Comm.ajaxPost('cardNode/cardList',JSON.stringify(queryFilter),function(result){
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
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        if(data.checked){
                            return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" cName="'+data.name+'" checked="'+data.checked+'">'
                        }else{
                            return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" cName="'+data.name+'" >'
                        }

                    }
                },
                {
                    "data":"id",
                    "searchable":false,"orderable" : false
                },
                {"data": "name","orderable" : false,"searchable":false},
                {"data": "desc","orderable" : false,"searchable":false},
                {
                    "data":"createTime",
                    "searchable":false,
                    "orderable" : false,
                    "render" : function(data, type, row, meta) {
                        if(data==null){
                            return "";
                        }else return json2TimeStamp(data);
                    }
                }
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#btn_search_score").click(function() {
                    g_scoreManage.fuzzySearch = true;
                    g_scoreManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset_score").click(function() {
                    $('input[name="checkscores"]').val("");
                    g_scoreManage.fuzzySearch = true;
                    g_scoreManage.tableUser.ajax.reload();
                });
                $("#scoreFields_list tbody").delegate( 'tr input','change',function(e){
                    var isChecked=$(this).attr('isChecked');
                    var selectArray = $("#scoreFields_list tbody input:checked");
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
                });
                $("#scoreFields_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        if(!target.parentNode.children[1]){
                            return;
                        }
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        var selectArray = $("#scoreFields_list tbody input:checked");
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
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_scoreManage.tableUser.on("order.dt search.dt", function() {
            g_scoreManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        $("#isShowScoreFileds").val(1);
    }
    showScoreFields();
}
/***************layer弹出评分卡***************/
function showScoreFields(){
    var indexs=layer.open({
        type : 1,
        title : '评分卡列表',
        maxmin : true,
        shadeClose : false,
        area : [ '630px', '410px' ],
        content : $('.c-createusers-dialogs'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            //判断引擎是否部署
            if(isDeploy()){
                return;
            }
            //评分卡id
            var card_id =$("#scoreFields_list tbody input:checked").val();
            //评分卡名称
            var card_name = $("#scoreFields_list tbody input:checked").attr("cName");
            //获取版本号
            var initEngineVersionId = $("input[name=initEngineVersionId]").val();
            //参数
            var params = '{"dataId":'+node_dataId+',"url":"'+node_url+'","type":'+node_type+'}';
            //判断是否选中
            if(card_id!=null && card_id != undefined){
                if(node_2.id==0){//保存节点
                    var param={
                        cardId:card_id,
                        initEngineVersionId:initEngineVersionId,
                        nodeName:card_name,
                        nodeCode:node_code,
                        nodeType:node_type,
                        nodeOrder:node_order,
                        nodeX:node_x,
                        nodeY:node_y,
                        nodeJson:card_id,
                        params:params
                    };
                    Comm.ajaxPost("decision_flow/save",JSON.stringify(param),function (result) {
                        layer.closeAll();
                        if(result.code==0){
                            layer.msg(result.msg,{time:1000});
                            node_2.text=card_name;
                            node_2.id = result.data;
                        }else{
                            layer.msg("保存失败",{time:1000});
                        }
                    },"application/json")
                }else{//修改节点
                    var param={
                        id:node_2.id,
                        cardId:card_id,
                        initEngineVersionId:initEngineVersionId,
                        nodeName:card_name,
                        nodeType:node_type,
                        nodeCode:node_code,
                        nodeOrder:node_order,
                        nodeX:node_x,
                        nodeY:node_y,
                        nodeJson:card_id,
                        params:params
                    };
                    Comm.ajaxPost("decision_flow/update",JSON.stringify(param),function (result) {
                        layer.closeAll();
                        if(result.code==0){
                            layer.msg(result.msg,{time:1000});
                            node_2.text=card_name;
                        }else{
                            layer.msg("保存失败",{time:1000});
                        }
                    },"application/json")
                }

            }else{
                layer.msg("请选择评分卡",{time:1000});
            }
        }
    });
}
/***************客户分群***************/
function groupedit(nodeId,isEdit){
    $("#groupNodeId").val(nodeId);
    var param = {"nodeId":nodeId};
    if(node_2.id!=0){
        Comm.ajaxPost("decision_flow/getNode",param,function (result) {
            var data=result.data;
            if(data!=null){
                var	htmlObj=JSON.parse(data.params);
                var html=htmlObj.html;
                var groupSelHtml=htmlObj.groupSelHtml;
                if (html!=null && !html==""){
                    $(".c_content").empty();
                    $(".c_content").append(html);
                    $(".c-group-content").empty();
                    $(".c-group-content").append(groupSelHtml);
                }
                showSwarm();
            }else {
                $(".c_content").empty();
                groupInit();
            }
        })
    }else {
        $(".c_content").empty();
        groupInit();
    }
}
/***************沙盒编辑***************/
function sandboxedit(){
    var param = {"nodeId":node_2.id};
    var	obj=null;
    riverNum=0;
    real_riverNum=0;
    if(node_2.id!=0){
        Comm.ajaxPost("decision_flow/getNode",param,function(reslut){
            var data=reslut.data;
            if(data!=null){
                var json=data.nodeJson;
                if (json!=null && json!="") {
                    obj= JSON.parse(data.nodeJson);
                }
                try {
                    $(".c-setSanbox").remove();
                    var newSanrate='<div class="c-sanbox-content c-setSanbox">';
                    for(var i=0;i<obj.length;i++){
                        riverNum++;
                        real_riverNum++;
                        sandbox =obj[i].sandbox;
                        proportion=obj[i].proportion;
                        if (i==0){
                            newSanrate+='<div class="c-sanbox-lie">'+
                                '<div class="c-enter-proportion">沙盒'+sandbox+':</div>'+
                                '<input name="sanBox" type="text" value="'+proportion+'" style="margin-left:4px;" required="" maxlength="2" />'+
                                '<div id="hidden_'+(i+1)+'" style="display:none">1</div>'+
                                '<span style="margin-left:3px;">%</span>'+
                                '<div class="c-sanbox-img">'+
                                '添加'+
                                '</div>'+
                                '</div>';
                        }else{
                            newSanrate+='<div class="c-sanbox-lie">'+
                                '<div class="c-enter-proportion">沙盒'+sandbox+':</div>'+
                                '<input name="sanBox" type="text" value="'+proportion+'"  style="margin-left:4px;" required="" maxlength="2"/>'+
                                '<div id="hidden_'+(i+1)+'" style="display:none">1</div>'+
                                '<span style="margin-left:2px;">%</span>'+
                                '<div class="c-sanbox-delete" id="div_del_'+(i+1)+'">'+
                                '<div class="fs6 c-remove"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>'+
                                '</div>'+
                                '</div>';
                        }
                    }
                    newSanrate+='</div>';
                    $(".sanbox-popup .c-sanbox-head").after(newSanrate);
                    showShaPan();
                }catch(exception){
                    $(".c-setSanbox").remove();
                    initSaPan();
                    riverNum=2
                }finally {
                }
            }else {
                initSaPan();
                riverNum=2;
            }
        })
    }else {
        initSaPan();
        riverNum=2;

    }
}
/***************决策选项弹出框***************/
function tagOption(nodeId){
    layer.open({
        type : 1,
        title : '决策选项',
        maxmin : true,
        shadeClose : false,
        area : [ '750px', '380px' ],
        content : $('.c-decision'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            //判断引擎是否部署
            if(isDeploy()){
                return;
            }
            var initEngineVersionId =$("input[name=initEngineVersionId]").val();
            var nodeId = node_2.id;
            var param = new Object();
            param.initEngineVersionId =initEngineVersionId;
            param.id =nodeId;
            param.nodeX = node_x;
            param.nodeY = node_y;
            param.nodeName = node_name;
            param.nodeCode = node_code;
            param.nodeType = node_type;
            param.nodeOrder = node_order;
            var inputs  = $("#d-option").find("input").length;
            var condition_type = getCondition_type();
            var input ="[";
            $("#d-option").find("input").each(function(index,element){
                var obj = $(this);
                input += '{"field_id":'+$(obj).attr("dataId");
                var code = $(obj).attr("fieldCode");
                if(!code){
                    layer.msg("请选择后保存！",{time:1000});
                    return;
                }else{
                    if(code.indexOf("SC_") > -1 && code.indexOf("_score") == -1){
                        code = code+"_score";
                    }
                }
                input += ',"field_code":"'+code;
                input += '","field_name":"'+$(obj).attr("value");
                if(condition_type == 2){
                    input +='","segemnts":['+$(element).attr("data")+']'
                }else{
                    input += '"';
                }
                input += ',"field_scope":"'+$(obj).attr("valueScope");
                input += '","field_type":'+$(obj).attr("valueType")+'}';
                input +=","
            });
            input = input.substring(0,input.length -1);
            input +="]";
            if(input.indexOf("undefined")!=-1){
                layer.msg("请选择后保存！",{time:1000});
                return;
            }
            var outObj = $("#d-option-out").find('input');
            var output = '{"field_id":'+$(outObj).attr("dataId")+',"field_code":"'+$(outObj).attr("fieldCode")+'","field_name":"'+$(outObj).attr("value")+'","field_type":'+$(outObj).attr("valueType")+',"field_scope":"'+$(outObj).attr("valueScope")+'"}'
            var str = "[";
            if(inputs == 1){
                if(condition_type == 1){
                    str += assembledJson_1();
                }else{
                    str += getFormulaJson("d-option-1");
                }
            }else if(inputs == 2){
                if(condition_type == 1){
                    str += assembledJson_2();
                }else{
                    str += getFormulaJson("d-option-2");
                }
            }else{
                str += getFormulaJson("d-option-3");
            }
            str +="]";
            if(output.indexOf("undefined")!=-1){
                layer.msg("请选择后保存！",{time:1000});
                return;
            }
            param.params = '{"dataId":"'+node_dataId+'","url":"'+node_url+'","type":"'+node_type+'"}';
            param.nodeJson ='{"inputs":'+inputs+',"condition_type":'+condition_type+',"input":'+input+',"output":'+output+',"conditions":'+str+'}';
            var url;
            if(nodeId !=0 ){
                url ="decision_flow/update";
            }else{
                url ="decision_flow/save";
            };
            Comm.ajaxPost(url,JSON.stringify(param),function (result) {
                layer.closeAll();
                layer.msg(result.msg,{time:1000});
                if(result.code == 0){
                    if(result.data){
                        node_2.id = result.data;
                    }
                }
            },"application/json")
        },
        success:function(){
            getField(nodeId);
            lookOrOperateForDecisionOption(nodeId);
        }
    });
}
var mapObj = new Map();
var mapObj_1 = new Map();
var lineIn;
/******************初始化*****************/
function pageInit(){
    lineIn=[];
    var id = $("input[name='initEngineVersionId']").val();
    var param ={"id":id};
    Comm.ajaxGet('decision_flow/getNodeList',param, function(result){
        var data=result.data;
        scene.clear();
        mapObj_1.clear();
        mapObj.clear();
        var array = data.engineNodeList;
        for(var b=0;b<array.length;b++){
            mapObj_1.put(array[b].nodeCode,array[b]);
        }
        lineIn=lineIn.concat(array);
        var array_1 = new Array();
        order_count=data.maxOrder+1;
        for (var i = 0; i < array.length; i++) {
            var i_node = $.parseJSON(array[i].params);
            var node = createNode(Number(i_node.dataId), array[i].nodeName, i_node.url,Number(array[i].nodeX), Number(array[i].nodeY),i_node.type,2);
            node.id = array[i].nodeId;
            node.node_order = array[i].nodeOrder;
            node.node_code  = array[i].nodeCode;
            if(array[i].nextNodes!=null && array[i].nextNodes !=undefined){
                if(array[i].nextNodes.indexOf(",") > -1){
                    var ary = array[i].nextNodes.split(",");
                    for (var s = 0; s < ary.length; s++) {
                        var element=mapObj_1.get(ary[s]);
                        lineIn.removeByValue(element);
                        node.next_node.push(ary[s]);
                    }
                }else if(array[i].nextNodes!=''){
                    var element=mapObj_1.get(array[i].nextNodes);
                    lineIn.removeByValue(element);
                    node.next_node.push(array[i].nextNodes);
                }
            }
            array_1.push(node);
            var nodeString=node.node_code;
            mapObj.put(nodeString,node);
        }
        initLink();
    },"application/json");
}
function initLink(){
    for(var i=0;i<lineIn.length;i++){
        var beginNode = mapObj.get(lineIn[i].nodeCode);
        var nextNodes = beginNode.next_node;
        if(nextNodes.length > 0){
            for (var item = 0; item < nextNodes.length; item++) {
                var endNode = mapObj.get(beginNode.next_node[item]);
                if(endNode != undefined){
                    initDrawLine(beginNode, endNode, mapObj);
                }
            }
        }
    }
}
/******************连XIAN****************/
function initDrawLine(beginNode, endNode,mapObj){
    var link = new JTopo.Link(beginNode,endNode);
    link.arrowsRadius = 10;
    link.strokeColor='0,0,0';
    link.dashedPattern = 2;
    link.lineWidth='0.5';
    link.fontColor='0,0,0';
    scene.add(link);
    var param;
    if(beginNode.dataId==3 || beginNode.dataId==2){
        param = {"nodeId":beginNode.id};
        Comm.ajaxGet('decision_flow/getNode',param, function(result){
            var datas=result.data;
            var nodeJsonObj =JSON.parse(datas.nodeJson);
            if(datas!=null&& beginNode.dataId ==3){
                var conditions=nodeJsonObj.conditions;
                for(var i=0;i<conditions.length;i++){
                    if(conditions[i].nextNode==link.nodeZ.node_code){
                        link.text=conditions[i].group_name;
                    }
                }
                var html=JSON.parse(datas.params);
                html=html.html;
                groupSelHtml=html.groupSelHtml;
                $(".c_content").empty();
                $(".c_content").append(html);
            }
            if(datas!=null && beginNode.dataId ==2){
                for(var i=0;i<nodeJsonObj.length;i++){
                    if(nodeJsonObj[i].nextNode==link.nodeZ.node_code){
                        link.text = nodeJsonObj[i].proportion+"%";
                    }
                }
            }
        },"","","","",false);
    }
    if(endNode.next_node!=undefined && endNode.next_node.length>0){
        for (var i = 0; i <endNode.next_node.length; i++) {
            initDrawLine(endNode, mapObj.get(endNode.next_node[i]), mapObj);
        }
    }
    link.addEventListener("click", function(event){
        selink=link;
        var nodeA_x=link.nodeA.x;
        var nodeZ_x=link.nodeZ.x;
        var nodeA_y=link.nodeA.y;
        var nodeZ_y=link.nodeZ.y;
        var imgX=nodeA_x+(nodeZ_x-nodeA_x)/2;
        var imgY=nodeA_y+(nodeZ_y-nodeA_y)/2;
        $("#lineDel").css({
            display:"block",
            left:imgX+sceneX,
            top:imgY+sceneY
        });
        removeId=link.nodeZ.id;
        lastId=link.nodeA.id;
        $("#lineDel").unbind('click').on("click",function(){
            if(isDeploy()){
                return;
            }
            var nodeId=selink.nodeZ.id;
            var nodeZ_code=selink.nodeZ.node_code;
            var param={'currentNodeId':removeId,'preNodeId':lastId};
            Comm.ajaxPost('decision_flow/removeLink',JSON.stringify(param),function(result){
                selink.nodeA.next_node.removeByValue(nodeZ_code);
                layer.msg("删除成功！",{time:1000});
                scene.remove(link);
                scene.remove(selink);
                $("#lineDel").hide();
                beginNode=null;
            },"application/json")
        })
    },true);
}
/******************删除节点功能*****************/
var removeId;
var lastId;
function deleteNode(param){
    var param_1 = new Object();
    removeId=node_2.id;
    if(node_2.inLinks==null||(node_2.inLinks!=null&&node_2.inLinks.length==0)){
        lastId=-1;
    }else{
        lastId=node_2.inLinks[0].nodeA.id;
    }
    param_1.currentNodeId = removeId;
    param_1.preNodeId = lastId;
    Comm.ajaxPost("decision_flow/removeNode",JSON.stringify(param_1),function(reslut){
        if(node_2.inLinks!=null&&node_2.inLinks.length>0){
            node_2.inLinks[0].nodeA.next_node.removeByValue(node_2.node_code);
        }
        layer.msg(reslut.msg);
        scene.remove(node_2);
        $(".bigCircle").hide();
    },"application/json")
}
/******************复制节点功能*****************/
function copyNode(param,node_2){
    if(node_2.type==1){
        layer.msg("开始节点不能复制！");
        $(".bigCircle").hide();
    }else{
        if(param.nodeId !=null && param.nodeId !=''){
            Comm.ajaxPost('decision_flow/copy',JSON.stringify(param),function(result){
                var data=result.data;
                if(result.code ==0){
                    var c_node = $.parseJSON(data.params);
                    var node = data;
                    $(".bigCircle").hide();
                    var r_node  = createNode(Number(c_node.dataId), node.nodeName, c_node.url,Number(node.nodeX), Number(node.nodeY),c_node.type,2);
                    r_node.id = node.nodeId;
                    r_node.node_order = order_count;
                    if(order_count == 1){
                        r_node.node_code = "ND_START";
                    }else{
                        r_node.node_code = "ND_"+order_count;
                    }
                    layer.msg("复制成功");
                }else{
                    layer.msg("复制失败");
                }
            },"application/json")
        }else{
            $(".bigCircle").hide();
            createNode(node_2.dataId, node_2.text, node_2.url, node_2.x+50, node_2.y+50,node_2.type,1);
            layer.msg("复制成功");
        }
    }
}
/******************沙盒比例部分*****************/
var riverNum=2;
var real_riverNum=0;
$(".sanbox-popup").on("click",".c-remove",function(e){//沙盒比例删除
    var deleteId=node_2.id;
    var sNum=$(this).parents(".c-sanbox-lie").find(".c-enter-proportion").text().replace("沙盒","").replace(":","");
    var param={"engineNodeId":deleteId,"branch":sNum};
    if(node_2.id!=0){
        Comm.ajaxPost("decision_flow/validateBranch",JSON.stringify(param),function(reslut){
            var data=reslut.data;
            if(data.result==1){
                layer.msg("请先删除相关连线！");
            }else{
                if($(e.target).parents(".dialog").find(".c-sanbox-lie").length>1){
                    $(e.target).parents(".c-sanbox-lie").remove();
                }else {
                    return;
                }
            }
        },"application/json")
    }else{
        if($(this).parents(".dialog").find(".c-sanbox-lie").length>1){
            $(this).parents(".c-sanbox-lie").remove();
        }else {
            return;
        }
    }
});
$(".sanbox-popup").on("click",".c-sanbox-img",function(){
    var sNm=$(this).parents(".dialog").find(".c-sanbox-lie").last().find(".c-enter-proportion").text().replace("沙盒","").replace(":","");
    sNm++;
    sanBoxName++;
    var newRate=$('<div class="c-sanbox-lie">'+
        '<div class="c-enter-proportion">沙盒'+sNm+':</div>'+
        '<input index="'+sanBoxName+'" name="sanBox_'+sanBoxName+'" type="text" style="margin-left:4px; " required="" maxlength="2"/>'+
        '<div id="hidden_'+riverNum+'" style="display:none">0</div>'+
        '<span style="margin-left:3px;">%</span>'+
        '<div class="c-sanbox-delete" id="div_del_'+(riverNum)+'">'+
        '<div class="fs6 c-remove"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>'+
        '</div>');
    $(".c-setSanbox").append(newRate);
    $("#div_del_"+(riverNum-1)).html('');
    $("#div_del_"+(riverNum-1)).html('<div class="fs6 c-remove"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>');
});
/******************沙盒比例初始化*****************/
function initSaPan(){
    $(".c-setSanbox").remove();
    var newSanPan=$('<div class="c-sanbox-content c-setSanbox"><div class="c-sanbox-lie">'+
        '<div class="c-enter-proportion">沙盒1:</div>'+
        '<input index="1" name="sanBox_1" type="text" value="" style="margin-left:4px;" required="" maxlength="2" />'+
        '<div id="hidden_1" style="display:none">0</div>'+
        '<span style="margin-left:3px;">%</span>'+
        '<div class="c-sanbox-img">添加</div></div>'+
        '<div class="c-sanbox-lie"><div class="c-enter-proportion">沙盒2:</div>'+
        '<input index="2" name="sanBox_2" type="text"  value="" style="margin-left:4px;" required="" maxlength="2" />'+
        '<div id="hidden_2" style="display:none">0</div>'+
        '<span style="margin-left:3px;">%</span><div class="c-sanbox-delete" id="div_del_2">'+
        '<div class="fs6 c-remove"><span class="icon-delData"  style="color:#fd6154;font-weight: 900;"></span></div>'+
        '</div></div>');
    $(".sanbox-popup .c-sanbox-head").after(newSanPan);
    showShaPan();
}
/******************沙盘弹出框*****************/
function showShaPan(){
    layer.open({
        type : 1,
        title : '沙盒比例',
        area : [ '320px', '220px' ],
        content : $('.sanbox-popup'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            //判断引擎是否部署
            if(isDeploy()){
                return;
            }
            var sanPanNodeJsonString ="[";
            $(".sanbox-popup .c-sanbox-lie").each(function(index,element){
                var val=$(element).find("input").val();
                sanPanNodeJsonString +='{"proportion":"'+val;
                var text = $(element).find(".c-enter-proportion").text().replace("沙盒",'');
                sanPanNodeJsonString +='","sandbox":"'+text.replace(":",'');
                sanPanNodeJsonString +='"},'
            });
            if(sanPanNodeJsonString.length>1){
                sanPanNodeJsonString = sanPanNodeJsonString.substring(0,sanPanNodeJsonString.length -1)
            }
            sanPanNodeJsonString +="]";
            var sanPanNodeJsonStringArr=JSON.parse(sanPanNodeJsonString);
            var reg=/^([0-9]){1,2}$/;
            var sum=0;
            for(var t=0;t<sanPanNodeJsonStringArr.length;t++){
                if(!sanPanNodeJsonStringArr[t].proportion){
                    layer.msg("请填写后再保存!");
                    return;
                }
                var num=Number(sanPanNodeJsonStringArr[t].proportion);
                if(!reg.test(num)){
                    layer.msg('请填写两位整数数字!');
                    return ;
                }
                sum+=num;
            }
            if(sum!=100){
                layer.msg('沙盒比例总和为100!');
                return;
            }
            var initEngineVersionId =$("input[name=initEngineVersionId]").val();
            var nodeId = node_2.id;
            var param = new Object();
            param.initEngineVersionId =initEngineVersionId;
            param.id =nodeId;
            param.nodeX = node_x;
            param.nodeY = node_y;
            param.nodeName = node_name;
            param.nodeCode = node_code;
            param.nodeType = node_type;
            param.nodeOrder = node_order;
            param.params = '{"dataId":"'+node_dataId+'","url":"'+node_url+'","type":"'+node_type+'" }';
            var url;
            if(nodeId !=0 ){
                Comm.ajaxGet("decision_flow/getNode",{"nodeId":nodeId},function(result){
                    var data=result.data;
                    var nextNodes=data.nextNodes;
                    if(data!=null){
                        var sanPanNodeJsonString_0 = JSON.parse(sanPanNodeJsonString);
                        var sanPanNodeJsonString_1 = JSON.parse(data.nodeJson);
                        for(var i=0;i<sanPanNodeJsonString_0.length;i++){
                            for (var j = 0; j < sanPanNodeJsonString_1.length; j++) {
                                if(sanPanNodeJsonString_0[i].sandbox == sanPanNodeJsonString_1[j].sandbox){
                                    sanPanNodeJsonString_0[i].nextNode = sanPanNodeJsonString_1[j].nextNode;
                                    break;
                                }
                            }
                        }
                        sanPanNodeJsonString = JSON.stringify(sanPanNodeJsonString_0);
                        url ="decision_flow/update";
                        param.nextNodes = nextNodes;
                        param.nodeJson = sanPanNodeJsonString;
                        Comm.ajaxPost(url,JSON.stringify(param),function(data){
                            if(data.code == 0){
                                layer.msg(data.msg);
                            }
                        },"application/json");
                    }
                },false);
            }else{
                url ="decision_flow/save";
                param.nodeJson = sanPanNodeJsonString;
                Comm.ajaxPost(url,JSON.stringify(param),function(data){
                    if(data.code == 0){
                        layer.msg(data.msg);
                        node_2.id = data.data;
                    }
                },"application/json");
            }
            layer.closeAll();
        }
    });
}
/*********客户分群添加条件**********/
$("body" ).on("click",".addCondition",function(){
    var newClientSelect=$('<div class="c-select-one c-add" style="">'+
        '<select  class="l_before l_relations l_relation datas" style="width:69px;margin-right:3px;background-position: 50px 0px;">'+
        '<option data="&&" value="且">且</option><option data="||" value="或">或</option></select></div>');
    $(this).parent().prev(".c-swarm-interior-left").find(".c-contains-outside").find(".c-contained-within:last-child").append(newClientSelect);
    var newClientCondition=$('<div class="c-contained-within"><div class="c-select-two">'+
        '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" value="待选" />'+
        '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">'+
        '<option data="0" value="待选">待选</option></select></div>'+
        '<div class="c-swarm-name"><input type="text" class="datas" value=""/></div><div class="c-select-one">'+
        '<select  class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">'+
        '<option data="0" value="待选">待选</option><option data="&&" value="且">且</option><option data="||" value="或">或</option>'+
        '</select></div><div class="c-select-two">'+
        '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" value="待选">'+
        '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">'+
        '<option data="0" value="待选">待选</option>'+
        '</select></div><div class="c-swarm-name"><input type="text" class="datas" value=""/></div></div>');
    $(this).parents(".c-swarm-interior").find(".c-contains-outside").append(newClientCondition);
});
/*********客户分群输入框赋值**********/
$(document).on("blur","input,select",function(){
    $(this).attr("value",$(this).val());
});
/*********客户分群初始化**********/
function groupInit(){
    var initCount=0;
    $(".c-swarm-content").empty();
    for(var i=0;i<2;i++){
        initCount++;
        var newGroup='<div class="c-swarm-interior"><div class="c-swarm-interior-left">'+
            '<div class="c-swarm-name c-title">分组<b class="datas groupNum">'+initCount+'</b></div>'+
            '<div class="c-contains-outside"><div class="c-contained-within"><div class="c-select-two">'+
            '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" />'+
            '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">'+
            '<option data="0" value="待选">待选</option></select></div><div class="c-swarm-name">'+
            '<input type="text" class="datas" value=""/></div><div class="c-select-one">'+
            '<select  class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">'+
            '<option data="0" value="待选">待选</option><option data="&&" value="且">且</option><option data="||" value="或">或</option>'+
            '</select></div><div class="c-select-two">'+
            '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text"/>'+
            '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">'+
            '<option data="0" value="待选">待选</option></select></div>'+
            '<div class="c-swarm-name"><input type="text" class="datas" value=""/></div></div></div></div>'+
            '<div class="c-swarm-name  c-positon-img"><span class="addCondition"></span><span style="margin-left: 14px;" class="delCondition"></span>'+
            '</div></div></div>';
        $(".c-swarm-content").append(newGroup);
    }
    showSwarm();
}
/*********显示分群管理**********/
function showSwarm(){
    $(".c-swarm-iexternal").show();
    $(".c-positon-img").show();
    layer.open({
        type : 1,
        title : '客户分群',
        maxmin : true,
        shadeClose : false,
        area : [ '790px', '300px' ],
        content : $('.c-swarm-dialog'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            //判断引擎是否部署
            if(isDeploy()){
                return;
            }
            groupJson.fields=new Array();
            groupJson.conditions=[];
            var length=$(".dialog").find(".c-swarm-name").find("b").length;
            for(var i=0;i<length;i++){
                debugger
                var formulas=[];
                var len=$(".dialog").find(".c-contains-outside").eq(i).find(".c-contained-within").length;
                for(var j=0;j<len;j++){
                    for(var t=0;t<8;t++){
                        switch (t) {
                            case 0:
                                var field_code1=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).attr("field_code");
                                if(field_code1.length<1){
                                    layer.msg("请选择字段");
                                    return;
                                }
                                var field_code=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).attr("field_code");
                                var field_name=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).val();
                                var field_id=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).attr("fieldId");
                                var field_type=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(0).attr("field_type");
                                var fields={field_code:field_code,field_name:field_name,field_id:field_id,field_type:field_type};
                                var push=true;
                                if(groupJson.fields.length>0){
                                    for(var n=0;n<groupJson.fields.length;n++){
                                        if(groupJson.fields[n].field_id==fields.field_id){
                                            push=false;
                                        }
                                    }
                                    if(push){
                                        groupJson.fields.push(fields);
                                    }
                                }else{
                                    groupJson.fields.push(fields);
                                }
                                formulas[j]={field_code1:field_code1};
                                continue;
                            case 1:
                                var operator1=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find("select.datas").find("option:selected").attr("data");
                                formulas[j]={field_code1:field_code1,operator1:operator1};
                                continue;
                            case 2:
                                if($(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(2).find("option").length>0){
                                    var value1=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(2).find("option:selected").attr("data");
                                    if(value1.length<1){
                                        layer.msg("请输入值");
                                        return;
                                    }
                                }else{
                                    var value1=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(2).val();
                                    if(value1.length<1){
                                        layer.msg("请输入值");
                                        return;
                                    }
                                }
                                formulas[j]={field_code1:field_code1,operator1:operator1,value1:value1};
                                continue;
                            case 3:
                                var a=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(3).find("option:selected").attr("data");
                                if($(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(3).find("option:selected").attr("data")=='0'){
                                    var relative_operator='';
                                    formulas[j]={field_code1:field_code1,operator1:operator1,value1:value1,relative_operator:''};
                                    t=8;
                                    j=len;
                                    break;
                                }else{
                                    var	relative_operator=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(3).find('option:selected').attr("data");
                                    formulas[j]={field_code1:field_code1,operator1:operator1,value1:value1,relative_operator:relative_operator};
                                    continue;
                                }
                            case 4:
                                var field_code2=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).attr("field_code");
                                var field_code=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).attr("field_code");
                                var field_name=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).val();
                                var field_id=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).attr("fieldId");
                                var field_type=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(4).attr("field_type");
                                var fields={field_code:field_code,field_name:field_name,field_id:field_id,field_type:field_type};
                                var push=true;
                                if(groupJson.fields.length>0){
                                    for(var m=0;m<groupJson.fields.length;m++){
                                        if(groupJson.fields[m].field_id==fields.field_id){
                                            push=false;
                                        }
                                    }
                                    if(push){
                                        groupJson.fields.push(fields);
                                    }

                                }else{
                                    groupJson.fields.push(fields);
                                }
                                formulas[j]={field_code1:field_code1,operator1:operator1,value1:value1,relative_operator:relative_operator,field_code2:field_code2};
                                continue;
                            case 5:
                                var operator2=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(5).find("option:selected").attr("value");
                                formulas[j]={field_code1:field_code1,operator1:operator1,value1:value1,relative_operator:relative_operator,field_code2:field_code2,operator2:operator2};
                                continue;
                            case 6:
                                if($(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(6).siblings("select").find("option").length>0){
                                    var value2=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(6).siblings("select").find("option:selected").attr("data");
                                }else{
                                    var value2=$(".c-contains-outside").eq(i).find(".c-contained-within").eq(j).find(".datas").eq(6).val();
                                }

                                formulas[j]={field_code1:field_code1,operator1:operator1,value1:value1,relative_operator:relative_operator,
                                    field_code2:field_code2,operator2:operator2,value2:value2};
                                continue;
                            case 7:
                                if(j==len-1){
                                    continue;
                                }else{
                                    var sign=$(".c-swarm-interior-left").eq(i).find(".l_relation").eq(j).find("option:selected").attr("data");
                                    formulas[j]={field_code1:field_code1,operator1:operator1,value1:value1,relative_operator:relative_operator,
                                        field_code2:field_code2,operator2:operator2,value2:value2,sign:sign};
                                    continue;
                                }

                            default:
                                break;
                        }
                    }
                }
                groupJson.conditions[i]={
                    group_name:"分组"+$(".c-swarm-name").find("b").eq(i).html(),
                    nextNode:'',
                    formulas:formulas
                };
            }
            var initEngineVersionId =$("input[name=initEngineVersionId]").val();
            var nodeId = node_2.id;
            var param = new Object();
            var html = $(".c_content").html();
            var groupSelHtml=$(".c-group-content").html();
            param.initEngineVersionId =initEngineVersionId;
            param.id =nodeId;
            param.nodeX = node_x;
            param.nodeY = node_y;
            param.nodeName = node_name;
            param.nodeCode = node_code;
            param.nodeType = node_type;
            param.nodeOrder = node_order;
            var object= {dataId:node_dataId,url:node_url,type:node_type ,html:html,groupSelHtml:groupSelHtml};
            object=JSON.stringify(object);
            param.params=object;
            if(nodeId !=0 ){
                Comm.ajaxGet("decision_flow/getNode",{nodeId:nodeId},function (result) {
                    var data=result.data;
                    if(data!=null){
                        groupJson_1 = JSON.parse(data.nodeJson);
                        var conditions=groupJson.conditions;
                        var conditions_1=groupJson_1.conditions;
                        for(var i=0;i<conditions.length;i++){
                            for (var j = 0; j < conditions_1.length; j++) {
                                if(conditions[i].group_name == conditions_1[j].group_name){
                                    conditions[i].nextNode = conditions_1[j].nextNode;
                                    break;
                                }
                            }
                        }
                        groupJson.conditions = conditions;
                        param.nodeJson=JSON.stringify(groupJson);
                        Comm.ajaxPost("decision_flow/update",JSON.stringify(param),function (result) {
                            layer.closeAll();
                            if(result.code==0){
                                layer.msg(result.msg);
                            }
                        },"application/json")
                    }
                },false);
            }else{
                param.nodeJson=JSON.stringify(groupJson);
                Comm.ajaxPost("decision_flow/save",JSON.stringify(param),function(data){
                    layer.closeAll();
                    layer.msg(data.msg);
                    if(data.code == 0){
                        node_2.id = data.data;
                    }
                },"application/json");
            }
        }
    });
}
/*********添加分组**********/
$("body").on("click",".c-swarm-name-add",function(){
    var grouopNum=$(".groupNum").last().html();
    grouopNum++;
    var newGroupHtml=$('<div class="c-swarm-interior"><div class="c-swarm-interior-left">'+
        '<div class="c-swarm-name c-title">分组<b class="datas groupNum">'+grouopNum+'</b></div>'+
        '<div class="c-contains-outside"><div class="c-contained-within"><div class="c-select-two">'+
        '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" value="待选"/>'+
        '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">'+
        '<option data="0" value="待选">待选</option></select>'+
        '</div><div class="c-swarm-name"><input type="text" class="datas" value=""/></div><div class="c-select-one">'+
        '<select  class="l_before l_relations datas" style="width:80px;margin-left:5px;background-position: 60px 0px;">'+
        '<option data="0" value="待选">待选</option><option data="&&" value="且">且</option><option data="||" value="1">或</option>'+
        '</select></div><div class="c-select-two">'+
        '<input class="c-swarm-input datas" fieldId="" field_type="" field_code="" type="text" value="待选"/>'+
        '<select  class="l_before datas" style=" margin-left:4px;width: 80px;height:29px;background-position: 40px 0px;">'+
        '<option data="0" value="待选">待选</option>'+
        '</select></div><div class="c-swarm-name"><input type="text" class="datas" value=""/></div></div></div></div>'+
        '<div class="c-swarm-name c-positon-img"><span class="addCondition"></span><span style="margin-left: 14px;" class="delCondition"></span>'+
        '</div></div></div>');
    $(".c-swarm-interior").last().after(newGroupHtml);
});
/*********删除条件**********/
var deleteId;
var groupBranchName;
$("body" ).on("click",".delCondition",function(e){
    deleteId=node_2.id;
    var groupNum=$(this).parents(".c-swarm-interior").find(".c-swarm-name b").html();
    groupBranchName='分组'+groupNum;
    var param={"engineNodeId":deleteId,"branch":groupBranchName};
    if(node_2.id!=0){
        Comm.ajaxPost("decision_flow/validateBranch",JSON.stringify(param),function (reslut) {
           var data=reslut.data;
            if(data.result==1){
                layer.msg("请先删除相关连线！");
            }else{
                if($(e.target).parents(".c-swarm-interior").find(".l_relation").length==0&&$(".c-swarm-interior").length>1){
                    $(e.target).parents(".c-swarm-interior").remove();
                }else if($(e.target).parents(".c-swarm-interior").find(".c-contained-within").length>1){
                    $(e.target).parents(".c-swarm-interior").find(".l_relation").last().parent().remove();
                    $(e.target).parents(".c-swarm-interior").find(".c-contained-within").last().remove();
                }
            }
        },"application/json")
    }else{
        if($(e.target).parents(".c-swarm-interior").find(".l_relation").length==0&&$(".c-swarm-interior").length>1){
            $(e.target).parents(".c-swarm-interior").remove();
        }else if($(e.target).parents(".c-swarm-interior").find(".c-contained-within").length>1){
            $(e.target).parents(".c-swarm-interior").find(".l_relation").last().parent().remove();
            $(e.target).parents(".c-swarm-interior").find(".c-contained-within").last().remove();
        }
    }
});
/*********失去焦点时改变改变select的value**********/
$(".c-swarm-dialog" ).on("blur",".l_before",function(){
    var valList=$(this).find("option:selected").val();
    $(this).find("option:selected").attr("value",valList);
    $(this).find("option").removeAttr("selected");
    $(this).find("option[value='"+valList+"']").attr("selected","true");
    $(this).val(valList);
})
$(".c-swarm-dialog").on("click",".c-swarm-input",function(){
    showFieldsList($(this));
    $(".c-swarm-input").removeClass("c-select-input");
    $(this).addClass("c-select-input");
    $(".selWord").show();
})
/*********选择字段**********/
function showFieldsList(me){
    var isShowFileds=$("#isShowFileds").val();
    if(isShowFileds){
        g_cumManage.tableUser.ajax.reload();
    }else{
        g_cumManage.tableUser = $('#swarmFields_list').dataTable($.extend({
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
                var queryFilter = g_cumManage.getQueryCondition(data);
                Comm.ajaxPost('decision_flow/getFieldList',JSON.stringify(queryFilter),function(result){
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
                    'data':'valueType',
                    'class':'hidden',"searchable":false,"orderable" : false
                },
                {
                    "className" : "childBox",
                    "orderable" : false,
                    "data" : null,
                    "width" : "60px",
                    "searchable":false,
                    "render" : function(data, type, row, meta) {
                        return '<input type="checkbox" value="'+data.id+'" valueType="'+data.valueType+'" restrainScope="'+data.restrainScope+'" enName="'+data.enName+'" style="cursor:pointer;" cnName="'+data.cnName+'" isChecked="false">'
                    }
                },
                {"data": "cnName","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#btn_search").click(function() {
                    g_cumManage.fuzzySearch = true;
                    g_cumManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset").click(function() {
                    $('#ziduanchaxun input[name="Parameter_search"]').val("");
                    g_cumManage.fuzzySearch = true;
                    g_cumManage.tableUser.ajax.reload();
                });
                $("#swarmFields_list tbody").delegate( 'tr input','change',function(e){
                    var isChecked=$(this).attr('isChecked');
                    var selectArray = $("#swarmFields_list tbody input:checked");
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
                    $("#hiddenFields").html($(this).attr('valueType'));
                    $("#hiddenRestrainScope").html($(this).attr('restrainScope'));
                    $("#enName").html($(this).attr('enName'));
                    $("#showFields").html(fieldsId+'/'+fieldsHtml);
                    $("#showFieldsPlace").val(fieldsId+'/'+fieldsHtml);
                });
                $("#swarmFields_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        if(!target.parentNode.children[1]){
                            return;
                        }
                        var input=target.parentNode.children[1].children[0];
                        var isChecked=$(input).attr('isChecked');
                        var selectArray = $("#swarmFields_list tbody input:checked");
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
                        $("#hiddenFields").html($(input).attr('valueType'));
                        $("#hiddenRestrainScope").html($(input).attr('restrainScope'));
                        $("#enName").html($(input).attr('enName'));
                        $("#showFields").html(fieldsId+'/'+fieldsHtml);
                        $("#showFieldsPlace").val(fieldsId+'/'+fieldsHtml);
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        g_cumManage.tableUser.on("order.dt search.dt", function() {
            g_cumManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        $("#isShowFileds").val(1);
    }
    openFieldsList(me);
}
/************layer选择字段弹框**********/
function openFieldsList(me){
    var layerOne=layer.open({
        type : 1,
        title : '输入参数',
        area : [ '750px', '450px' ],
        content : $(".group-dialog"),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            layer.close(layerOne);
            var showOne=$("#fieldsStyle").css("display");
            var showTwo=$("#showScored").css("display");
            var FieldsValueId;
            var FieldsValueCnname;
            var valueType;
            var enName;
            var restrainScope;
            if(showOne=="block"){
                FieldsValueId=$("#swarmFields_list tbody input:checked").attr("value");
                FieldsValueCnname=$("#swarmFields_list tbody input:checked").attr("cnname");
                valueType=$("#swarmFields_list tbody input:checked").attr("valuetype");
                enName=$("#swarmFields_list tbody input:checked").attr("enname");
                restrainScope=$("#swarmFields_list tbody input:checked").attr("restrainscope");
            }
            if(showTwo=="block"){
                FieldsValueId=$("#optionScoreds_list tbody input:checked").attr("value");
                FieldsValueCnname=$("#optionScoreds_list tbody input:checked").attr("cName");
                valueType=$("#optionScoreds_list tbody input:checked").attr("valuetype");
                enName=$("#optionScoreds_list tbody input:checked").attr("enname");
                enName=enName+"_score";
                restrainScope=$("#optionScoreds_list tbody input:checked").attr("restrainscope");
            }
            $(me).attr("value",FieldsValueCnname);
            $(me).attr("fieldid",FieldsValueId);
            $(me).attr("field_code",enName);
            $(me).attr("field_type",valueType);
            $(me).attr("valueScope",restrainScope);
            $(me).attr("dataValue",FieldsValueId+"|"+enName);
            $(me).next().children().remove();
            var input='<input type="text" class="datas" value="">';
            if(valueType==1){//数值
                $(me).parent().next().empty().append(input).show();
                $(me).parent().next().children("select").hide().remove();
                $(me).next().append('<option data=">" value=">">大于</option><option data="<" value="<">小于</option><option data=">=" value=">=">大于等于</option><option data="<=" value="<=">小于等于</option><option data="==" value="==">等于</option><option data="!=" value="!=">不等于</option>');
            }else if(valueType==2){//字符
                $(me).parent().next().empty().append(input).show();
                $(me).parent().next().children("select").hide().remove();
                $(me).next().append('<option data="contains" value="contains">like</option><option data="notContains" value="notContains">not like</option><option data="equals" value="equals">等于</option><option data="notEquals" value="notEquals">不等于</option>');
            }else if(valueType==3){//枚举
                var restrainScope=$("#hiddenRestrainScope").html();
                if(restrainScope){
                    var restrainScopeArr=restrainScope.split(",");
                    var arr=[];
                    for(var i=0;i<restrainScopeArr.length;i++){
                        arr.push(restrainScopeArr[i].split(":"));
                    }
                }
                $(me).next().append('<option data="==" value="==">等于</option><option data="!=" value="!=">不等于</option>');
                $(me).parent().next().children().hide().remove();
                $(me).parent().next().append("<select class='l_before datas' style='margin-left: 10px;width: 80px;height: 29px;background-position: 40px 0px;'></select>");
                for(var j=0;j<restrainScopeArr.length;j++){
                    $(me).parent().next().find("select").append('<option  value="'+arr[j][1]+'" data="'+arr[j][1]+'">'+arr[j][0]+'</option>');
                }
            }
        },
        success:function(){
            $("#fieldsStyle").show();
        }

    });
}
/*******自定义方法删除数组制指定元素*****/
Array.prototype.removeByValue = function(val) {
    for(var i=0; i<this.length; i++) {
        if(this[i] == val) {
            this.splice(i, 1);
            break;
        }
    }
};
Array.prototype.unique3 = function(){
    var res = [];
    var json = {};
    for(var i = 0; i < this.length; i++){
        if(!json[this[i]]){
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
};
/*******客户分群数据查询条件*****/
var g_cumManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.engineId = $("input[name='engineId']").val();

        //自行处理查询参数
        param.fuzzySearch = g_cumManage.fuzzySearch;
        if (g_cumManage.fuzzySearch) {
            param.searchKey = $("#ziduanchaxun input[name='Parameter_search']").val();
        }

        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/*******评分卡数据查询条件*****/
var g_scoreManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.engineId = $("input[name='engineId']").val();
        param.nodeId = node_2.id;
        param.status = "1";
        //自行处理查询参数
        param.fuzzySearch = g_scoreManage.fuzzySearch;
        if (g_scoreManage.fuzzySearch) {
            param.scorecardName=$("input[name='checkscores']").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/*******切换功能*****/
$("#option_fd").on("click",function(){
    var href=$(this).attr("hrefs");
    $(this).addClass("active_active").parent(".c-decisions-switcher").siblings(".c-decisions-switcher").find("a").removeClass("active_active");
    $("#"+href).show().siblings(".Manager_style").hide();
});
$("#option_sc").on("click",function(){
    var href=$(this).attr("hrefs");
    $(this).addClass("active_active").parent(".c-decisions-switcher").siblings(".c-decisions-switcher").find("a").removeClass("active_active");
    $("#"+href).show().siblings(".Manager_style").hide();
    getscores();
});
/*******删除增加节点******/
function deleteJieDian() {
    var id = $("#l_decisions").find(".selected").attr("dataid");
    if(!id){
        layer.msg("请选择要删除节点!");
        return;
    }
    var engineId=$("input[name='engineId']").val();
    var nodeType = $("#l_decisions").find(".selected").attr("type");
    if(nodeType == 2 || nodeType == 3 || nodeType == 4 || nodeType == 5 || nodeType == 6 || nodeType == 7 || nodeType == 8 || nodeType == 9 || nodeType == 10 || nodeType == 11 || nodeType == 12 || nodeType == 13){
        layer.msg("基本节点不可删除！",{time:1000});
    }else{
        Comm.ajaxGet("customNode/deleteNode",{id:id},function(result){
            layer.closeAll();
            layer.msg(result.msg,{time:1000});
            nodes = [];
            $("#l_decisions").empty();
            getNodes(engineId);
            getNode(engineId);
        })
    }
}
/*******添加自定义节点******/
function addJieDian() {
    $("#text").val("");
    layer.open({
        type : 1,
        title : "添加节点",
        area : [ '300px', '130px' ],
        content : $('#addNodes'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            var text = $("#text").val();
            var url = "/resources/images/decision/createDiy.png";
            var engineId=$("input[name='engineId']").val();
            var param = {
                text:text,
                url:url,
                type:nodeType+1,
                engineId:engineId
            };
            Comm.ajaxPost('customNode/addNode',JSON.stringify(param),function(result){
                if(result.code == 0 ) {
                    layer.closeAll();
                    layer.msg(result.msg,{time:1000});
                    nodes = [];
                    $("#l_decisions").empty();
                    getNodes(engineId);
                    getNode(engineId);
                }
            },"application/json")
        }

    });
}
//客户分组 tab切
/*******切换功能*****/
$("#option_field").on("click",function(){
    var href=$(this).attr("hrefs");
    $(this).addClass("active_active").parent(".c-decisions-switcher").siblings(".c-decisions-switcher").find("a").removeClass("active_active");
    $("#"+href).show().siblings(".Manager_style").hide();
});
$("#option_scored").on("click",function(){
    var href=$(this).attr("hrefs");
    $(this).addClass("active_active").parent(".c-decisions-switcher").siblings(".c-decisions-switcher").find("a").removeClass("active_active");
    $("#"+href).show().siblings(".Manager_style").hide();
    getgroupsScores();
});
/*************客户分组输入评分卡卡数据查询条件**************/
var groupsScoresManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        var nodeid=$("#groupNodeId").val();
        param.engineId = $("input[name='engineId']").val();
        param.opType=1;
        if(variableType==1){
            param.isOutput=0;
        }else{
            param.isOutput=1;
        }
        param.nodeId=nodeid;
        //自行处理查询参数
        param.fuzzySearch = groupsScoresManage.fuzzySearch;
        if (groupsScoresManage.fuzzySearch) {

        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/*************客户分组输入评分卡****************/
function getgroupsScores(){
    var isGroupScore=$("#isGroupScore").val();
    if(isGroupScore){
        groupsScoresManage.tableUser.ajax.reload();
    }else{
        groupsScoresManage.tableUser = $('#optionScoreds_list').dataTable($.extend({
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
                var queryFilter = groupsScoresManage.getQueryCondition(data);
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
                        return '<input type="checkbox" value="'+data.id+'" style="cursor:pointer;" isChecked="false" cName="'+data.name+'" valuetype="1"   enName="'+data.code+'" restrainscope="">'
                    }
                },
                {"data": "name","orderable" : false,"searchable":false}
            ],
            "createdRow": function ( row, data, index,settings,json ) {
            },
            "initComplete" : function(settings,json) {
                $("#btn_search_score").click(function() {
                    groupsScoresManage.fuzzySearch = true;
                    groupsScoresManage.tableUser.ajax.reload();
                });
                $("#btn_search_reset_score").click(function() {
                    $('input[name="Parameter_search"]').val("");
                    groupsScoresManage.fuzzySearch = true;
                    groupsScoresManage.tableUser.ajax.reload();
                });
                $("#optionScoreds_list tbody").delegate( 'tr input','click',function(e){
                    var isChecked=$(this).attr('isChecked');
                    var selectArray = $("#optionScoreds_list tbody input:checked");
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
                    $("#enName2").html($(input).attr('enName'));
                    $("#valueType2").html($(input).attr('valuetype'));
                });
                $("#optionScoreds_list tbody").delegate( 'tr','click',function(e){
                    var target=e.target;
                    if(target.nodeName=='TD'){
                        if(target.parentNode.children[1]){
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
                    }
                });
            }
        }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
        groupsScoresManage.tableUser.on("order.dt search.dt", function() {
            groupsScoresManage.tableUser.column(0, {
                search : "applied",
                order : "applied"
            }).nodes().each(function(cell, i) {
                cell.innerHTML = i + 1
            })
        }).draw();
        $("#isGroupScore").val(1);
    }
}

/************ 修改引擎之前先判断引擎是否部署 ************/
function isDeploy() {
    var className=$("#arrange").attr("class").split(" ")[0];
    if(className=='stopArrange'){//引擎已部署
        layer.msg("请先停止部署引擎！",{time:1000});
        return true;
    }else{
        return false;
    }
}






