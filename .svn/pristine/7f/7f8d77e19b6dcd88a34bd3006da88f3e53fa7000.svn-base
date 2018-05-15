<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html ng-app="userApp" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/css/myCss.css${version}"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <script src="${ctx}/resources/js/dataMange/dblist_black.js${version}"></script>
    <title>黑名单管理</title>
</head>
<body>

                <div id="See_data_style">
                    <div class="commonManager ">
                        <div class="addCommon clearfix">
                            <ul class="clearfix">
                                <li>
                                    <label class="label_name">黑名单名称</label>
                                    <label for="name">
                                        <input name="user_account"  type="text"  id="name"/>
                                       <%-- <i style="color: #F60;">*</i>--%>
                                    </label>
                                </li>
                                <li>
                                    <label class="label_name">数据来源</label>
                                    <label>
                                        <select name="valueType" size="1" id="valueType">
                                            <option value="0">待选</option>
                                            <option value="1">外部</option>
                                            <option value="2">内部</option>
                                        </select>
                                    </label>
                                </li>
                                <li id="isEdit">
                                    <label class="label_name">名单类型</label>
                                    <label for="password">
                                        <input name="user_password"  type="text" id="password"/>
                                        <%--<i style="color: #F60;">*</i>--%>
                                    </label>
                                </li>
                                <li>
                                    <label class="label_name">黑名单描述</label>
                                    <label>
                                        <select name="fieldTypeId" size="1" id="fieldTypeId">
                                            <option value="">准入规则</option>
                                        </select>
                                    </label>
                                </li>
                                <li id="accuLi">
                                    <label>
                                        <input  name="r1" type="radio" value="Y" class="text_add"  id="isCatagory_Y"/>精准匹配&#12288;&#12288;
                                    </label>
                                    <label>
                                        <input  name="r1" type="radio" value="N" class="text_add"  id="isCatagory_N"/>模糊匹配
                                    </label>
                                </li>

                                <li style="/*clear:both*/width: 96%;">
                                    <label class="label_name">维护字段</label>&#12288;&#12288;
                                    <label style="float:right">
                                        <input name="r2" type="radio" value="N" class="text_add"  id="Mainten"/>and
                                    </label>

                                    <label style="float:right;margin-right:10px;">
                                        <input  name="r2" type="radio" value="Y" class="text_add"  id="Maintenance"/>or
                                    </label>


                                </li>

                                <li  style="width:570px;height:100px;border:1px solid #ccc;">

                                        <div  id="Maintenance_field">

                                        </div>
                                </li>

                                <li>

                                    <div style="margin-left:14px;">查询主键</div>

                                </li>

                                <li style="width:570px;height:100px;border:1px solid #ccc">
                                    <div  id="sel_id">

                                    </div>

                                </li>

                            </ul>
                        </div>
                    </div>
                    <div class="layui-layer-btn">
                        <a class="layui-layer-btn0" id="btn_save">保存</a>
                        <a class="layui-layer-btn1" id="btn_change">取消</a>
                        <input type="hidden" value="${param.id}" id="hid">
                        <input type="hidden" value="" id="queryFilde">

                    </div>
                </div>

            </div>
        </form>

    </div>
</div>
</body>
<script>


    //取消按钮
    $("#btn_change").click(function(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });

    //保存提交修改
    $("#btn_save").click(function () {
        role.listName = $("#name").val();
        role.dataSource =  $("#valueType").val();
        role.listAttribute =  $("#password").val();
        role.listDesc =  $("#fieldTypeId").val();
        role.maintenance =  $("#Maintenance").val();
        if($('input[name="r1"]').val()=="Y"){
            role.matchType = "1";
        }else if($('input[name="r1"]').val()=="N"){
            role.matchType = "0";
        }
        if($('input[name="r2"]').val()=="Y"){
            role.queryType = "1";
        }else if($('input[name="r2"]').val()=="N"){
            role.queryType = "0";
        }
        role.queryField =  $("#queryFilde").val();
        Comm.ajaxPost('datamanage/listmanage/update',JSON.stringify(role),function(result){
            /*var returnData = {};*/
            if(result.code==0){
                layer.msg(result.msg, {time: 2000}, function () {
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.$('#Data_list').dataTable().fnDraw(false);
                    parent.layer.close(index); //再执行关闭
                });
            }else{
                layer.msg(result.msg, {time: 2000})
            }
        },"application/json")
    })
</script>
</html>




