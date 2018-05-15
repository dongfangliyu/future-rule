<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="java.util.*" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>引擎管理主页</title>
		<style>
			.left-menu{
				position:fixed;
				height:100%;
			}
			.img-left{
				text-align: center;
				padding: 1.2em 0em;
				height:50.19px;
			}
			.left-menu ul{
				background: #1E679C;
				height:100%;
			}
			.left-menu ul li{
				width:52px;
				list-style: none;
				position: relative;
			}
			.left-menu .animate a{
				display: inline-block;
				width: 75px;
				text-decoration: none;
				padding:1.2em 0;
				color:#fff;
			}
			.animate{
				display: none;
				position: absolute;
				top:0px;
				left:50px;
				background: #255B83;
			}
			.liActive{
				background:#255B83!important;
			}
            .img-left b{
                cursor: pointer;
            }
		</style>
	</head>
	<body>
		<div class="left-menu" isShow="1">
			<ul>
				<li class="liActive">
					<div class="img-left" id="decisions-id">
						<b  class="a_href" onclick="getHrefshow(this)" url="" jspName="decisions">
						  <img src="${ctx}/resources/images/left-menu/flowDesign.png"/>
						  <div class="glyph fs1">
							<div class="clearfix pbs">
	                           <span class="icon-ceshi"></span>
	                        </div>
				          </div>
						</b>
					</div>
					<div class="animate">
						<a href="###">引擎设计</a>
					</div>
				</li>
				<li>
					<div class="img-left" id="rule-collection-id">
						<b  class="a_href" onclick="getHrefshow(this)">
							 <img src="${ctx}/resources/images/left-menu/rulesDesign.png"/>
							<div class="glyph fs1">
								<div class="clearfix pbs">
		                           <span class="icon-knowledge"></span>
		                        </div>   
				          </div> 
						</b>
					</div>
					<div class="animate">
						<a href="###">规则映射</a>
					</div>
				</li>
				<li>
					<div class="img-left" id="fieldmapping-id">
						<b  class="a_href" onclick="getHrefshow(this)">
							<img src="${ctx}/resources/images/left-menu/rulesYingshe.png"/>
							<div class="glyph fs1">
								<div class="clearfix pbs">
		                           <span class="icon-fields"></span>
		                        </div>   
				            </div>
						</b>
					  </div>
					<div class="animate">
						<a href="###">字段映射</a>
					</div>
				</li>
				<li>
					<div class="img-left" id="measurement-id">
						<b  class="a_href" onclick="getHrefshow(this)">
							<img src="${ctx}/resources/images/left-menu/test.png"/>
							<div class="glyph fs1">
								<div class="clearfix pbs">
									<span class="icon-ceshi2"></span>
								</div>
							</div>
						</b>
					</div>
					<div class="animate">
						<a href="###">批量测试</a>
					</div>
				</li>
				<li>
					<div class="img-left" id="batchTest-id">
						<b  class="a_href" onclick="getHrefshow(this)">
							<img src="${ctx}/resources/images/left-menu/data.png"/>
							<div class="glyph fs1">
								<div class="clearfix pbs">
									<span class="icon-result"></span>
								</div>
							</div>
						</b>
					</div>
					<div class="animate">
						<a href="###">数据统计</a>
					</div>
				</li>
			</ul>
		</div>
	</body>
	<script src="${ctx}/resources/js/lib/jquery/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".img-left").on("mouseenter", function() {
                $(this).parent().css("background","#255B83");
				$(this).parent().find(".animate").show();
			});
			$(".left-menu li").on("mouseleave", function() {
                $(this).css("background","#1E679C");
				$(this).find(".animate").hide();
			});
	        //默认选中头菜单
			var parentId = getQueryString("parentId");
		 	var urls = [
				"${ctx}/decision_flow/decisionsPage?id=${engineId}&flag=0",
				"${ctx}/knowledge/tree?engineId=${engineId}",
				"${ctx}/engineManage/fieldmapping?engineId=${engineId}",
				"${ctx}/engine/engineTest?engineId=${engineId}",
				"${ctx}/engine/toresult?engineId=${engineId}"
		 	];
		 	for(var i=0;i<urls.length;i++){
				var url = urls[i]+"&&parentId="+parentId;
				 $(".a_href").eq(i).attr("url",url);
			}
			function getQueryString(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
				var r = window.location.search.substr(1).match(reg);
				if (r != null) return unescape(r[2]); return null;
			}
			//左菜单默认选中
			var left_name = $("#leftjsp_id").val();
			switch(left_name){ 
			case "decisions.jsp": 
					$("#decisions-id").addClass("l_imgSelect");				
					break; 
			case "rule-collection.jsp": 
					$("#rule-collection-id").addClass("l_imgSelect");
					break; 
			case "fieldmapping.jsp": 
					$("#fieldmapping-id").addClass("l_imgSelect");
					break; 
			case "resultList.jsp": 
					$("#batchTest-id").addClass("l_imgSelect");
					break; 
			case "measurement.jsp": 
					$("#measurement-id").addClass("l_imgSelect");
					break; 
			default: 
					break;
			}
		});
		function getHrefshow(me) {
            $(me).parent().parent().addClass("liActive").siblings(".liActive").removeClass("liActive");
            $("#iframejsp").attr("src",$(me).attr("url"));
		}
	</script>
</html>