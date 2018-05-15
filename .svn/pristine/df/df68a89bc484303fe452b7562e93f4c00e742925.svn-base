<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>决策流</title>
		<%@include file ="../common/taglibs.jsp"%>
        <%@ include file="../common/importDate.jsp"%>
		<link rel="stylesheet" href="${ctx}/resources/css/rulesDetails.css${version}">
		<link rel="stylesheet" href="${ctx}/resources/css/bootstrap.min.css">
        <style>
            .icon-delData{  cursor: pointer;  display: inline-block;  width:19px;  height:19px;  background: url("${ctx}/resources/images/rules/delete.png") no-repeat center center;  }
            span.addCondition{  display:inline-block;  width:19px;  height:19px;  background: url("${ctx}/resources/images/rules/add.png") no-repeat center center;  }
            span.delCondition{  display:inline-block;  width:19px;  height:19px;  background: url("${ctx}/resources/images/rules/delete.png") no-repeat center center;  }
        </style>
	</head>
    <body>
        <textarea id="jtopo_textfield" style="width: 60px; z-index:10000; position: absolute; display: none;" onkeydown="if(event.keyCode==13)this.blur();" value=""></textarea>
        <input type="hidden" name="initEngineVersionId" value="${initEngineVersionId}">
        <input type="hidden" name="engineId" value="${engineId}">
        <input type="hidden" id="getCtx" value="${ctx}">
        <object id="getScene" style="display: none"></object>
        <object id="groupJson" style="display: none"></object>
        <object id="node_2" style="display: none"></object>
        <input type="hidden" id="node_x" value="">
        <input type="hidden" id="node_y" value="">
        <input type="hidden" id="node_name" value="">
        <input type="hidden" id="node_type" value="">
        <input type="hidden" id="node_code" value="">
        <input type="hidden" id="node_order" value="">
        <input type="hidden" id="node_dataId" value="">
        <input type="hidden" id="node_url" value="">
        <input type="hidden" id="sanbox" value="">
        <input type="hidden" id="l_lineText" value="">
        <input type="hidden" id="nextNode" value="">
        <object id="sanBoxJsonObj" style="display: none"></object>
        <object id="link" style="display: none"></object>
        <div class="outs">
                <div class="out-content">
                    <div class="left-decisionMenus">
                        <div class="l-leftTopMenu">
                            <p style="text-align: center;margin: 1em;font-size: 20px;font-weight: bold;padding-bottom: .8em;border-bottom: 1px solid #CDD2E1;">决策流程布局图</p>
                        </div>
                        <div class="l_decision_options">
                            <ul id="l_decisions">
                            </ul>
                        </div>
                    </div>
                    <div class="l_rights">
                        <div class="rightTopMenu">
                            <div style="margin: 1em;float: left">
                                <button  class="newBtn" onclick="deleteJieDian()">删除节点</button>
                                <button  class="newBtn" onclick="addJieDian()">添加节点</button>
                            </div>
                            <div style="margin: 1em;text-align: right">
                                <button id="clickWrite" class="operateBtn">数据填写</button>
                                <button id="versionSave" class="operateBtn">保存</button>
                                <button id="arrange" class="stopArrange operateBtn">停止部署</button>
                                <button id="clearAll" class="btn-newDanger">清空</button>
                                <button id="adds" class="operateBtn">新建</button>
                            </div>
                        </div>
                        <div class="rightOperateArea" style="background:url(${ctx}/resources/images/decision/decisionBcg.png)">
                        <img alt='' src='${ctx}/resources/images/decision/cha.png' style="display:none; z-index:10001; position:absolute;" id="lineDel" />
                            <canvas id="canvas" width="1033px" height="450px">
                            </canvas>
                            <div class="l_pictures_operate">
                                <div id="stageEnlarge">
                                     <div class="fs0">
                                         <img src="${ctx}/resources/images/decision/fangda.png" title="放大" />
                                     </div>
                                </div>
                                <div id="stageNarrow">
                                      <div class=" fs0">
                                          <img src="${ctx}/resources/images/decision/suoxiao.png" title="缩小" />
                                      </div>
                                 </div>
                            </div>
                            <div class="c-hide-prompt">
                                <div class="c-prompt-content-add">
                                </div>
                                <button class="c-prompt-button"></button>
                            </div>
                        </div>
                        <div class="l_version">
                            <div class="l_run_version" style="overflow:auto;">
                            </div>
                            <div class="l_stop_version" style="overflow: auto;">
                                <div class="l_right_versionTxt">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        <!-- 右键菜单 -->
        <c:import url="decisionJsp/rightMenu.jsp"></c:import>
        <!-- 填写数据 -->
        <c:import url="decisionJsp/wiringData.jsp"></c:import>
        <!-- 黑名单 -->
        <c:import url="decisionJsp/blacklist.jsp"></c:import>
        <!-- 白名单 -->
        <c:import url="decisionJsp/whitelist.jsp"></c:import>
        <!-- 沙盒比例 -->
        <c:import url="decisionJsp/shaPan.jsp"></c:import>
        <!-- 分群管理 -->
        <c:import url="decisionJsp/fenQun.jsp"></c:import>
        <!-- 选择字段弹框 -->
        <c:import url="decisionJsp/filedList.jsp"></c:import>
        <!-- 规则集 -->
        <c:import url="decisionJsp/decisionRules.jsp"></c:import>
        <!-- 决策选项 -->
        <c:import url="decisionJsp/jueCe.jsp"></c:import>
        <%--添加节点--%>
        <div id="addNodes" style="display: none;">
            <div class="labelStyle" id="name">
                <label class="label_name">节点名称</label>
                <label>
                    <input  id="text" type="text" >
                </label>
            </div>
        </div>
        <%------------------------------------demo自定义名单-------------------------------%>
        <div id="customDb" style="display: none;">
            <div class="labelStyle" style="margin: 0.5em 0;">
                <ul class="search_content clearfix">
                    <button class="btn btn-primary addBtn" type="button"  onclick="addCustom()">新增</button>
                </ul>
            </div>
            <div class="Manager_style" style="padding: 0 5px 0 5px;">
                <div class="Data_list">
                    <table style="cursor:pointer;" id="customList" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>名称</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%--添加自定义名单--%>
        <div id="addCustomDb" style="display: none;">
            <div class="labelStyle" >
                <label class="label_name">姓名</label>
                <label>
                    <input  id="customName" type="text" >
                </label>
            </div>
        </div>
        <%-------------------------------------demo自定义名单-------------------------------%>
	</body>
	<script  type="text/javascript" src="${ctx}/resources/js/lib/jtopo/jtopo-0.4.8-min.js" charset="utf-8"></script>
	<script  type="text/javascript" src="${ctx}/resources/js/lib/jtopo/toolbar.js"  charset="utf-8"></script>
	<script  type="text/javascript" src="${ctx}/resources/js/decision/create-decision.js${version}"  charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/resources/js/validate/map.js"></script>
	<script  type="text/javascript" src="${ctx}/resources/js/decision/decision_content_all.js${version}" ></script>
    <script>
        var nodeType;
        $(function () {
         /***初始化节点***/
            var engineId=$("input[name='engineId']").val();
            getNodes(engineId);
        });
        /***获取节点****/
        function getNodes(engineId) {
            Comm.ajaxGet("customNode/list",{engineId:engineId},function(data){
                var nodes = data.data;
                var text = "";
                for(var i = 0; i < nodes.length ; i++){
                    if(nodes[i].type == 8 || nodes[i].type == 10 || nodes[i].type == 11 || nodes[i].type == 12){
                        text += "<li hidden dataId='" +nodes[i].id+ "' code="+i+" type = "+nodes[i].type+"><img src='${ctx}"+nodes[i].url+"'/><span class=‘left_Name’>"+nodes[i].text+"</span></li>";
                    }else{
                        text += "<li dataId='" +nodes[i].id+ "' code="+i+" type = "+nodes[i].type+"><img src='${ctx}"+nodes[i].url+"'/><span class=‘left_Name’>"+nodes[i].text+"</span></li>";
                    }
                    nodeType = nodes[i].type;
                }
                $("#l_decisions").append(text);
            })
        }
    	/**决策流验证框动画**/
    	var clicks=true;
    	var content_one;
         $(".c-prompt-button").on("click",function(){
        	  var heightOut=0;
        	  for(var i=0;i<$(".c-prompt-content").length;i++){
        		   heightOut=heightOut+$(".c-prompt-content").eq(i).outerHeight();
        	  }
        	 if(clicks){
        		 content_one=$(".c-prompt-content").eq(0).outerHeight();
        		 $(".c-prompt-content").animate({height:"0"},function(){
        			 $(".c-prompt-content").hide();
        			 clicks=false;
        		 });
        	 }else{
        		  $(".c-prompt-content").animate({height:"31"},function(){
        			  $(".c-prompt-content").eq(0).animate({
             			 height:content_one
             		 });
        		  });
        		 $(".c-prompt-content").show();
        		 clicks=true;
        	 }
         });
        /**绑定选择***/
        $(".l_decision_options").on("click","ul li",function(){
            $(".l_decision_options li").removeClass("selected");
            $(this).addClass("selected");
        });
    </script>
</html>