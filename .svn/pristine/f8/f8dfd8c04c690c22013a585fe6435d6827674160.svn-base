<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.c-black-name div.showName{text-align: left;margin:1.5em;font-size:1em;}
    .c-black-price{ text-align: left;margin:1.5em;padding-bottom:1em; border-bottom: 1px dashed #ddd;}
</style>
<div class="c-black-dialog dialog" style="display:none">
	<div class="c-black-head move_part">
		<input type="hidden" class="hide" name="canEdit" value="${canEdit}">
		<input type="hidden" class="hide" name="nodeId" value="${nodeId}">
		<input type="hidden" class="hide" name="innerNodeListDb" value="${innerNodeListDb}">
		<input type="hidden" class="hide" name="outerNodeListDb" value="${outerNodeListDb}">
	</div>
	<div class="c-black-content">
		<div class="c-black-interior"></div>
        <hr>
		<div class="c-black-iexternal"></div>
	</div>
</div>
<script type="text/javascript">
var nodeId;
function blackInit(nodeId,isEdit){
	$(".c-black-interior").html(" ");
	$(".cc-black-iexternal").html(" ");
	$("input[name=nodeId]").val(nodeId);
    var InnerNodeListArr = new Array();
    var OuterNodeListArr = new Array();
	if(nodeId>0){
		var paramMap={"nodeId":nodeId,"listType":"b"};
		Comm.ajaxPost("bwListNode/findBlackList",JSON.stringify(paramMap),function(result){
			var data=result.data;
			InnerNodeListArr = data.innerNodeListDb;
			OuterNodeListArr = data.outerNodeListDb;
		},"application/json","","","",false)
	}
    if(isEdit=='Y'){
		var paramMap={"status":1,"listType":"b"};
		Comm.ajaxPost("datamanage/listmanage/findListDbByUser",JSON.stringify(paramMap),function(result){
			var data=result.data;
			var innerArray = data.insideListDbs;
			var html = '<div class="c-black-name"><div class="showName">内部黑名单:</div></div><input type="hidden" class="hide" name="innerDbs" value="">';
			if(innerArray.length>0){
				html += '<div class="c-black-name-right">';
				for(var i=0; i<innerArray.length; i++){
					var flag = 0;
					for(var j=0; j<InnerNodeListArr.length ;j++){
						if(innerArray[i].id==InnerNodeListArr[j].id){
							flag = 1;
						}
					}
					html += '<div class="c-external-wrapper"><div class="c-black-frame ';
					if(flag==1){
						html += 'l_back ';
					}
					html += '" msg="'+innerArray[i].id+'"><input type="checkbox" isChecked="false" class="checkThis"></div></div>'
							+ '<div class="c-black-price">'+innerArray[i].listName+'</div></div>';
					if(i==innerArray.length-1){
						html += '</div>';
					}
				}
			}
			$(".c-black-interior").html("").append(html);
			var outerArray = data.outsideListDbs;
			var htmlO = '<div class="c-black-name"><div class="showName">外部黑名单:</div></div><input type="hidden" class="hide" name="outerDbs" value="">';
			if(outerArray.length>0){
				htmlO += '<div class="c-black-name-right">';
				for(var i=0; i<outerArray.length; i++){
					var flag = 0;
					for(var j=0; j<OuterNodeListArr.length ;j++){
						if(outerArray[i].id==OuterNodeListArr[j].id){
							flag = 1;
						}
					}
					htmlO += '<div class="c-external-wrapper"><div class="c-black-frame ';
					if(flag==1){
						htmlO += 'l_back ';
					}
					htmlO += '" msg="'+outerArray[i].id+'"><input type="checkbox"  isChecked="false"  class="checkThis"></div></div>'
							+ '<div class="c-black-price">'+outerArray[i].listName+'</div></div>';
					if(i==outerArray.length-1) {
						html += '</div>';
					}

				}
			}
			$(".c-black-iexternal").html("").append(htmlO);
            var divList= $(".c-black-frame");
            $(divList).each(function(index,elem){
                if($(this).hasClass("l_back")){
                    $(this).find("input").attr("checked","checked").attr("isChecked","true");
                }
            })
		},"application/json");
    }else if(isEdit=='N'){
    	var html = '<div class="c-black-name"><div class="showName">内部黑名单:</div></div><input type="hidden" class="hide" name="innerDbs" value="">';
		if(InnerNodeListArr.length>0){
			html += '<div class="c-black-name-right">';
			for(var i=0; i<InnerNodeListArr.length; i++){
				html += '<div class="c-external-wrapper"><div class="c-black-frame l_back';
				html += '" msg="'+InnerNodeListArr[i].id+'"><input type="checkbox" isChecked="true" checked  class="checkThis"></div>'
			          + '<div class="c-black-price">'+InnerNodeListArr[i].listName+'</div></div>';
			    if(i==InnerNodeListArr.length-1){
					html += '</div>';
				}
			}
		}
	    $(".c-black-interior").html("").append(html);
		var htmlO = '<div class="c-black-name"><div  class="showName">外部黑名单:</div></div><input type="hidden" class="hide" name="outerDbs" value="">';
		if(OuterNodeListArr.length>0){
			htmlO += '<div class="c-black-name-right">';
			for(var i=0; i<OuterNodeListArr.length; i++){
				htmlO += '<div class="c-external-wrapper"><div class="c-black-frame l_back';
				htmlO += '" msg="'+OuterNodeListArr[i].id+'"><input type="checkbox"  isChecked="true" checked  class="checkThis"></div>'
			          + '<div class="c-black-price">'+OuterNodeListArr[i].listName+'</div></div>';
				if(i==OuterNodeListArr.length-1){
					html += '</div>';
				}
			}
		}
	    $(".c-black-iexternal").html("").append(htmlO);
	    $("#blackSave").removeClass("blackEditSave").addClass("blackLookSave");
    }
	openEditBlack(isEdit);
}
function openEditBlack(isEdit){
    var title="选择黑名单";
    if(isEdit=="N"){
        title="查看黑名单"
    }
	layer.open({
		type : 1,
		title : title,
		maxmin : true,
		shadeClose : false,
		area : [ '370px', '300px' ],
		content : $('.c-black-dialog'),
		btn : [ '保存', '取消' ],
		yes : function(index, layero) {
			//判断引擎是否部署
			if(isDeploy()){
				return;
			}
            var inputList=$(".c-black-content").find("input[type='checkbox']");
            var arr=[];
            $(inputList).each(function(index,element){
                if($(this).attr("checked")){
                    arr.push("1");
                }
            });
			if(arr.length>0&&isEdit=="Y"){
				var innerArray = new Array();
				$(".c-black-interior").find(".c-black-frame").each(function(index,element){
					if($(this).hasClass("l_back")){
						var innerSelId = $(this).attr("msg");
						innerArray.push(innerSelId);
					}
				});
				$("input[name= 'innerDbs']").val(innerArray);
				var outerArray = new Array();
				$(".c-black-iexternal").find(".c-black-frame").each(function(index,element){
					if($(this).hasClass("l_back")){
						var outerSelId = $(this).attr("msg");
						outerArray.push(outerSelId);
					}
				});
				$("input[name= 'outerDbs']").val(outerArray);
				var _innerDbs = $("input[name= 'innerDbs']").val();
				var _outerDbs = $("input[name= 'outerDbs']").val();
				nodeId = $("input[name=nodeId]").val();
				if(nodeId==null||nodeId==''){
					nodeId = 0;
				}
				if(nodeId !=0 ){
					_url = "bwListNode/update";
				}else{
					_url = "bwListNode/create";
				}
				var params = '{"dataId":'+node_dataId+',"url":"'+node_url+'","type":'+node_type+'}';
				var versionId = $("input[name=initEngineVersionId]").val();
				var _params = {
					"nodeId":nodeId,
					"insideListdbs":_innerDbs,
					"outsideListdbs":_outerDbs,
					"node_dataId":node_dataId,
					"verId":versionId,
					"node_url":node_url,
					"node_order":node_order,
					"node_code":node_code,
					"node_x":node_x,
					"node_y":node_y,
					"node_name":node_name,
					"node_type":node_type,
					"nodeJson":'',
					"params":params
				};
				Comm.ajaxPost(_url,JSON.stringify(_params),
					function(result){
						layer.closeAll();
                        var data=result.data;
                        var type=typeof(data);
                        layer.msg("保存成功！",{time:1000});
                        if(type=="number"){
                            node_2.id = result.data;
                            $("input[name=nodeId]").val(result.data);
                        }else{
                            node_2.id = result.data.nodeId;
                            $("input[name=nodeId]").val(result.data.nodeId);
                        }
					},"application/json");
			}else{
				layer.closeAll();
			}
		}
	});
}
$(".c-black-content").on("click",".c-black-price",function(){
    var msg=$(this).prev(".c-external-wrapper").find(".c-black-frame").attr("msg");
	var isChecked=$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("isChecked");
	if(isChecked=="false"){
		$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("checked","checked");
		$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("isChecked","true");
        $(this).prev(".c-external-wrapper").find(".c-black-frame").addClass("l_back");
	}else{
		$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("checked",false);
		$(this).prev(".c-external-wrapper").find(".c-black-frame").find("input").attr("isChecked","false");
        $(this).prev(".c-external-wrapper").find(".c-black-frame").removeClass("l_back");
	}
})
$(".c-black-content").on("click",".checkThis",function(){
    var isChecked=$(this).attr("isChecked");
    if(isChecked=="false"){
        $(this).parent().addClass("l_back");
        $(this).attr("isChecked","true");
        $(this).attr("checked","checked");
    }else{
        $(this).parent().removeClass("l_back");
        $(this).attr("isChecked","false");
        $(this).attr("checked",false);
    }
})
</script>