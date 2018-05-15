<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file ="../common/taglibs.jsp"%>
    <%@ include file="../common/importDate.jsp"%>
    <link rel="stylesheet" href="${ctx}/resources/assets/select-mutiple/css/select-multiple.css${version}"/>
    <link rel="stylesheet" href="${ctx}/resources/assets/jstree/themes/default/style.css" />
    <link rel="stylesheet" href="${ctx}/resources/assets/atwho/jquery.atwho.css${version}"/>
    <script src="${ctx}/resources/assets/jstree/jstree.min.js${version}"></script>
    <script src="${ctx}/resources/assets/jstree/checkboxTree.js${version}" type="text/javascript"></script>
    <script src="${ctx}/resources/assets/atwho/jquery.atwho.js${version}"></script>
    <script src="${ctx}/resources/assets/atwho/jquery.caret.js${version}"></script>
    <script src="${ctx}/resources/js/common/customValid.js${version}"></script>
    <script src="${ctx}/resources/js/engineMange/engineField.js${version}"></script>
    <script src="${ctx}/resources/js/dataMange/field_validate.js${version}"></script>
    <script src="${ctx}/resources/js/engineMange/engineFieldJstree.js${version}"></script>
    <title>字段映射</title>
    <style type="text/css">
        .c-popup-engine{
            width:530px;
            height:300px;
            overflow:auto;
            overflow-x:hidden;
            border:1px solid #E6E6E6;
            margin-left:5px;
        }
        .c-popup-redact a {
            float: right;
            font-size: 14px;
            color: #398dff;
            padding-right: 30px;
        }
        .iconaddData,.iconclearData{
            width: 20px;
            height: 20px;
            display: inline-block;
            background: url(${ctx}/resources/images/blackadd.png) center center;
            background-size: cover;
            cursor: pointer;
            vertical-align: middle;
        }
        .iconclearData{
            border:1px solid #FD6154;
            border-radius: 50%;
            background: url(${ctx}/resources/images/blackClear.png) center center;
            background-size: cover;
            vertical-align: middle;
        }
        #otherInfoTab{margin: 0px;}
        #otherInfoTab a{
            text-decoration: none;
        }
        #checkBox_btn button{
            margin: 0px;
            padding: 0px;
            border: 0px;
            width: 49%;
            height: 35px;
            font-size: 12pt;
            background-color: #c6c6c6;
            color: #000
        }
        .c-popup-redact {
            width: 240px;
            height: 30px;
            line-height: 30px;
            margin: 0 0 0 15px;
            font-size: 14px;
            color: #777777;
            font-weight: 600;
            float: left;
        }
        #btn{
            display: inline-block;
        }
        #checkBox_sure:hover{background-color: #009f95;color: #fff}
        #checkBox_cancel:hover{background-color: #E11C27;color: #fff}
    </style>
</head>
<body>
<div class="page-content" style="height:100%;padding:0;">
    <div id="treeMenu" style="width:15%;float: left;box-shadow: 2px 2px 2px #888;overflow-x: auto">
        <div style="height: 40px;line-height: 40px;width:100%">
            <div style="text-align: center;background: #0679CC;color: white;">
                目录管理
            </div>
            <input type="hidden" id="hdNodeId" value="">
        </div>
        <div id="checkBox_btn" style="margin:0px;padding:0px;width: 99%;height:35px;float: right;display: none;text-align: center;">
            <button id="checkBox_sure">确&nbsp;&nbsp;定</button>
            <button id="checkBox_cancel">取&nbsp;&nbsp;消</button>
        </div>
        <div id="jsTreeCheckBox" style="display: none;">
        </div>
        <div style="max-height:400px;overflow-y: auto">
            <div id="jstree"></div>
        </div>
    </div>
    <%--新增弹出框--%>
    <div id="Add_data_style" style="display: none">
            <div class="commonManager ">
                <div class="addCommon clearfix c-field-middle">
                    <input type="hidden" class="hide" name="searchKey" value="" id="searchKey">
                    <input type="hidden" class="hide" name="fieldTypeId" value="" id="fieldTypeId">
                    <input type="hidden" name="id" value="" id="id">
                    <input type="hidden" name="formula" id="formula" />
                    <input type="hidden" name="formulaShow" value="" id="formulaShow" />
                    <input type="hidden" name="engineId" value="${param.engineId}" id="engineId" />
                    <input type="hidden" name="formulaFields" value=""/>
                    <ul class="clearfix">
                        <li>
                            <label class="label_name">字段名称</label>
                            <label for="en_name">
                                <input name="en_name"  type="text"  id="en_name"/>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">字段中文名</label>
                            <label for="cn_name">
                                <input name="cn_name"  type="text"  id="cn_name"/>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">字段来源</label>
                            <label>
                                <select name="value_type" size="1" id="value_type">
                                </select>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">值类型</label>
                            <label>
                                <select name="valueType" onchange='setDiv()' size="1" id="valueType">
                                    <option value="1">数值型</option>
                                    <option value="2">字符型</option>
                                    <option value="3">枚举型</option>
                                </select>
                            </label>
                        </li>
                        <li>
                            <label class="label_name">衍生变量</label>
                            <select name="isDerivative" size="1"  class="select" id="isDerivative" onchange="setDerivative()">
                                <option value=1  >是</option>
                                <option value=0 selected = "selected">否</option>
                            </select>
                        </li>
                        <li>
                            <label class="label_name">输出</label>
                            <select name="isOutput" size="1"  class="select" id="isOutput">
                                <option value=1  >是</option>
                                <option value=0 selected = "selected">否</option>
                            </select>
                        </li>

                    </ul>
                    <div class="Remark" style="padding-top: 20px;">
                        <label class="label_name">字段范围</label>
                        <label style="padding-left: 0px">
                            <input name="restrainScope" type="text" id="restrainScope" onblur="setSelectList()" style="width: 430px;margin-right: 97px;">
                        </label>
                    </div>
                    <input type="hidden" name="fieldContent" id="fieldContent"/>
                    <input type="hidden" name="formulaHidden" id="formulaHidden"/>
                    <input type="hidden" name="derType" id="derType"/>
                    <div id="derivedFields" style="display: none">
                        <div class="Remark" style="padding-top: 20px; height: 40px;width: 450px;">
                            <label class="label_name" style="color:#398DEF;" onclick="setTextDiv(1)" id="region" >条件区域</label>
                            <label class="label_name" onclick="setTextDiv(2)" id="formul">公式编辑</label>
                    </div>
                        <div class="Remark" style="width: 620px;" id="regionDiv" >
                        <div style="width: 540px; height: 10px"></div>
                            <div style="width: 540px;overflow: hidden" id="divRow1" name="divRow">
                                <div class="Remark" style="width: 160px;border-right:1px dashed #000;float:left" id="divRowLeft1">
                                    <label>
                                        <span class="iconaddData" onclick="addTermRulsLi(this,1)"></span>
                                        <span class="iconclearData" onclick="deleteTermRulsLi(this,1)"></span>
                                        <input type="text" name="conditionValue" style="width: 80px">
                                        <select name="conditionValue" size="1"  class="select" style="width: 80px;display: none">
                                        </select>
                                    </label>
                                </div>
                                <div class="Remark" style="width:460px;float:right;text-align:left;" id="divRowRight1">
                                    <label>
                                        <span class="iconaddData" onclick="addTermRulsLi(this,2,'divRowRight1')"></span>
                                        <span class="iconclearData" onclick="deleteTermRulsLi(this,2,'divRowRight1')"></span>
                                        <select name="fieldByUser" size="1"  class="select" onchange='setSelect(this)'  style="width: 80px">

                                        </select>
                                        <select name="fieldByUserWhere" size="1"  class="select" style="width: 80px">

                                        </select>
                                        <input type="text" style="width: 80px">
                                        <select  size=\"1\"  style='display: none;width: 80px' class=\"select\"> </select>
                                    </label>
                                </div>
                            </div>
                    </div>
                        <div class="Remark" style="width:100%;display: none;" id="formulaDiv">
                        <div style="width: 600px;padding-top: 5px;">
                            <ul style=" /* text-align: center; */width: 600px;margin: 0 auto;">
                                <li class="c-operational-character" onclick="character(this)" dataId="0" style="width: 15px;height:17px;background: url(${ctx}/resources/images/scorecard/1.png) center center;"></li>
                                <li class="c-operational-character" onclick="character(this)" dataId="1" style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/2.png) center center;"></li>
                                <li class="c-operational-character" onclick="character(this)" dataId="2" style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/3.png) center center;"></li>
                                <li class="c-operational-character" onclick="character(this)" dataId="3" style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/4.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="4" style="width: 31px;height:17px;background: url(${ctx}/resources/images/scorecard/5.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="5" style="width: 15px;height:17px;background: url(${ctx}/resources/images/scorecard/6.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="6" style="width: 25px; height:17px;background: url(${ctx}/resources/images/scorecard/7.png) center center;"></li>
                                <li class="c-operational-character" onclick="character(this)" dataId="7"  style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/8.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)"  dataId="8" style="width: 25px; height:17px;background: url(${ctx}/resources/images/scorecard/10.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)"  dataId="9" style="width: 32px; height:17px;background: url(${ctx}/resources/images/scorecard/11.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)"  dataId="10" style="width: 30px; height:17px;background: url(${ctx}/resources/images/scorecard/12.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="11" style="width: 13px;height:17px;background: url(${ctx}/resources/images/scorecard/13.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)"  dataId="12" style="width: 25px;height:17px;background: url(${ctx}/resources/images/scorecard/14.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="13" style="width: 27px;height:17px;background: url(${ctx}/resources/images/scorecard/15.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="14" style="width: 37px;height:17px;background: url(${ctx}/resources/images/scorecard/16.png) center center;"></li>
                                <li class="c-operational-characterTwo" onclick="characterTwo(this)" dataId="15" style="width: 10px;height:17px;background: url(${ctx}/resources/images/scorecard/17.png) center center;"></li>
                                <li style="width: 17px;height:17px;background: url(${ctx}/resources/images/scorecard/18.png) center center;"></li>
                                <li style="width: 2px;height:17px;background: url(${ctx}/resources/images/scorecard/19.png) center center;"></li>
                            </ul>
                            <div class="Remark" style="padding-top: 20px;" id="formulaEdit1">
                                <textarea class="inputor" onclick="setCursor('inputor')" id="inputor"  farr="" cols="" rows="" style="width: 600px; height: 130px; padding: 0px;" maxlength="1024"></textarea>
                            </div>
                            <div class="Remark" style="padding-top: 10px;" id="formulaEdit2">
                            </div>
                        </div>
                    </div>
                    </div>
                </div>
            </div>
        </div>

    <!-- 删除字段影响引擎提示框 -->
    <%--<div class="c_dialog dialog" id="dialog_engine">
        <div class="c_dialog_content">
            <div class="c-popup-prompt">
                <div class="c-reminder-img"><img style="float: right;" src="${ctx}/resources/images/prompt.png" /></div>
                <div class="c-reminder-span">删除或停用本字段影响以下内容</div>
            </div>
            <div class ="c-popup-engine">
            </div>
        </div>
    </div>--%>
    <div id="dialog_engine" style="display: none">
        <div class="commonManager ">
            <div class="addCommon clearfix">
                <ul class="clearfix">
                    <div style="margin-bottom: 1em;">
                        <img style="margin-right: 4em;" src="${ctx}/resources/images/prompt.png" />
                        <span>删除或停用本字段影响以下内容</span>
                    </div>
                    <div class ="c-popup-engine">
                    </div>
                </ul>
            </div>
        </div>
    </div>

    <div id="menu-edit" style="display:none">
            <form name="form" id="myForm" method="post">
                <div class="add_menu">
                    <div id="Adding_menu">
                        <div  id="editContent" class="page-content">
                            <table cellpadding="0" cellspacing="0" width="100%">
                                <input type="hidden" id="hdParentId" >
                                <input type="hidden" id="hdFieldType" >
                                <input type="hidden" id="hdFieldTypeid" >
                                <tr>
                                    <td class="label_name">文件夹名称</td>
                                    <td>
                                        <input name="name" type="text" class="addtext" value=""  title="" ht-validate="{maxlength:100}" />
                                        <i style="color: #F60;">*</i>
                                    </td>
                                </tr>
                                <tr>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    <div id="content" style="width: 85%;float: right;height: 100%;overflow: auto;">
        <div class="Res Manager_style">
            <div class="table_res_list">
                <form id="form1" name="form1" method="post" action="">
                    <div class="Manager_style add_user_info search_style" style="margin: 1em">
                        <div id="btn" style="display: none">
                        <ul class="search_content clearfix">
                            <button class="btn btn-primary addBtn" type="button" id="addBtn" onclick="addData(1)">新增字段</button>
                        </ul>
                        </div>
                        <div style="float: right;display:inline-block;margin-right: 20px;">
                            <ul>
                                <input type="text" name="Parameter_search" id="Parameter_search" placeholder="字段名称" style="vertical-align: middle"/>
                                <button type="button" class="queryBtn" id="paramSearch" style="top:0">查询</button>
                                <button onclick="paramSearchReset()" type="button" class="queryBtn" style="top:0">查询重置</button>
                            </ul>
                        </div>
                    </div>
                </form>
                <table style="text-align: center;cursor: pointer" id="Res_list" cellpadding="0" cellspacing="0" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>字段名称</th>
                        <th>字段中文名</th>
                        <th>字段来源</th>
                        <th>字段约束范围</th>
                        <th>是否外部数据</th>
                        <th>衍生变量</th>
                        <th>输出</th>
                        <th>状态</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    <input type="hidden" id="canAdd" >
                    <input type="hidden" id="listType" >
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>