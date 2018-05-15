<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .c-optional-rules{margin-bottom: 10px;}
    #add_rule_style input{line-height: 20px!important}
    #add_rule_style select{height: 30px!important;}
</style>
<div id="add_rule_style" style="display: none">
    <div class="addCommon clearfix">
        <ul class="clearfix">
            <li>
                <label class="label_name">规则代码</label>
                <label for="rule_account">
                    <input name="rule_account" type="text" id="rule_account"/>
                </label>
            </li>
            <li>
                <label class="label_name">规则名称</label>
                <label for="rule_name">
                    <input name="rule_name" type="text" id="rule_name"/>
                </label>
            </li>
            <li>
                <label class="label_name">优先级&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <label>
                    <select name="Priority" size="1" id="Priority">
                        <option value="0">0</option>
                        <option value="1" >1</option>
                        <option value="2" >2</option>
                        <option value="3" >3</option>
                        <option value="4" >4</option>
                        <option value="5" >5</option>
                        <option value="6" >6</option>
                        <option value="7" >7</option>
                        <option value="8" >8</option>
                        <option value="9" >9</option>
                    </select>
                </label>
            </li>
            <li>
                <label class="label_name">规则描述</label>
                <label for="rule_describe">
                    <input name="rule_describe"  type="text" id="rule_describe"/>
                </label>
            </li>
        </ul>
        <hr style="border:0; border-top:1px solid #E6E6E6;">
        <div class="c-rules-condition">
            <div style="margin: 10px 0;font-size: 14px;text-align: left;padding-left: 2em;"><span>条件区域</span></div>
            <div id="conditions">
            </div>
            <div id="c-condition-select" style="display:none;text-align: left;padding-left: 109px;padding-top: 10px;">
                <select id ="last_logical" style="width: 90px;">
                    <option value=")">)</option>
                    <option value="))">))</option>
                    <option value ="-1">空置</option>
                </select>
            </div>
        </div>
        <ul class="clearfix" id="OutRuleLi">
            <li style="width: 100%;text-align: left;margin: 0;padding: 0">
                <hr>
                <label class="label_name" style="margin-left: 2em">输出</label>
            </li>
            <li style="text-align: left;padding-left:2em;width:100%" class="c-optional-rules">
                <span class="iconaddData" onclick="addOutLi(this)"></span>
                <span class="iconclearData_disabled"></span>
                <select size="1" style="width: 100px" name="fieldId">
                    <option value="0">审批建议</option>
                </select>
                =<select name="ruleAudit" size="1" style="width: 100px;margin-left: 10px;" id="approveSuggest">
                <option value=5>通过</option>
                <option value=2>拒绝</option>
                <%--<option value=3>人工审批</option>--%>
                <%--<option value=4>简化审批流程</option>--%>
            </select>
            </li>
            <li style="text-align: left;padding-left:2em;width:100%" class="c-optional-rules">
                <span class="iconaddData" onclick="addOutLi(this)"></span>
                <span class="iconclearData_disabled"></span>
                <select name="fieldId" size="1" style="width: 100px">
                    <option value="0">信用得分</option>
                </select>
                =<input type="text" style="width: 100px" class="ruleInput" id="rule_score" name="fieldValue">
            </li>
        </ul>
    </div>
</div>
<script>
    var logical ='<option value="&&">and</option>'+
            '<option value="||"> or </option>'+
//            '<option value="("> ( </option>'+
//            '<option value=")"> ) </option>'+
            '<option value="&&("> and (</option>'+
            '<option value=")&&">) and </option>'+
            '<option value=")&&("> )and( </option>'+
            '<option value="||("> or( </option>'+
            '<option value=")||"> )or </option>'+
            '<option value=")||("> )or(</option>'+
            '<option value="-1">空置</option>';
    var  digitalType = '<option value=">">大于</option>'+
            '<option value="<">小于</option>'+
            '<option value=">=">大于等于</option>'+
            '<option value="<=">小于等于</option>'+
            '<option value="==">等于</option>'+
            '<option value="!=">不等于</option>';
    var  characterType = '<option value="contains">like</option>'+
            '<option value="not contains">not like</option>'+
            '<option value="==">等于</option>'+
            '<option value="!=">不等于</option>';
    var  enumerationType ='<option value="==">等于</option>'+
            '<option value="!=">不等于</option>';
    var add_condition = '<div class="c-optional-rules">'+
            '<span class="iconaddData" onclick="addCondition(this)"></span>'+
            '<span class="iconclearData" onclick="delCondition(this)"></span>'+
            '<select name="logicalSymbol" class="sel" style="width:90px;">'+logical+'</select>'+
            '<select onclick="getInputParameter(this,0)" name="fieldId" class="sel" style="width:130px;"></select>'+
            '<select name="operator" class="sel" style="width:100px;"></select>'+
            '<input name="fieldValue" style="width:140px;display: none"/>'+
            '<select name="fieldValue" class="sel" style="width:140px;"></select>'+
            '</div>';
    var add_rule_out = '<li style="text-align: left;padding-left:2em;width:100%;" class="c-optional-rules">'+
            '<span class="iconaddData" onclick="addOutLi(this)"></span>'+
            '&nbsp;<span class="iconclearData" onclick="deleteOutLi(this)"></span>'+
            '&nbsp;<select name="fieldId" size="1" style="width: 100px" onclick="getInputParameter(this,1)">'+
            '</select>'+
            '&nbsp;=<select name="fieldValue" size="1" style="width: 100px;margin-left: 10px;">'+
            '</select>'+
            '<input type="text" style="display: none" class="ruleInput" value="" name="fieldValue"></li>';
</script>