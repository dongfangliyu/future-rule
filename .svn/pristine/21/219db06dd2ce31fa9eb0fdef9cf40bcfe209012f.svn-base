<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
   #formula li{margin:1em .4em!important;}
   #scorecard li:last-child{width: 93%; text-align: left; margin-left: 1.6em;}
   #scorecard li:last-child label:last-child{width:84%}
   #scorecard li:last-child label:last-child input{width:100%}
</style>
<div id="add_score_style" style="display: none">
    <div class="addCommon clearfix">
        <ul class="clearfix" id="scorecard">
            <li>
                <label class="label_name">评分卡代码</label>
                <label for="scorecard_code">
                    <input name="scorecard_code" type="text" id="scorecard_code"/>
                </label>
            </li>
            <li>
                <label class="label_name">评分卡名称</label>
                <label for="scorecard_name">
                    <input name="scorecard_name" type="text" id="scorecard_name"/>
                </label>
            </li>
            <li>
                <label class="label_name">评分卡描述</label>
                <label for="scorecard_desc">
                    <input name="scorecard_desc" type="text" id="scorecard_desc"/>
                </label>
            </li>
        </ul>
        <hr style="border:0; border-top:1px solid #E6E6E6;margin: 5px auto">
        <div class="Remark" style="width:100%;" id="formulaDiv">
            <p style="margin-left: 15px;width: 65px;font-size: 15px">公式编辑</p>
            <div style="width: 98%;border: 1px solid #ccc;margin: 0 auto;">
                <ul id="formula" style="text-align: center;width: 100%;overflow: hidden;cursor: pointer;background: #F0F0F0;">
                    <li class="c-operational-character" onclick="character(this)" dataId="0"  style="width: 15px;height:17px;background: url(${ctx}/resources/images/scorecard/1.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="1"  style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/2.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="2"  style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/3.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="3"  style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/4.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="4"  style="width: 31px;height:17px;background: url(${ctx}/resources/images/scorecard/5.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="5"  style="width: 15px;height:17px;background: url(${ctx}/resources/images/scorecard/6.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="6"  style="width: 25px; height:17px;background: url(${ctx}/resources/images/scorecard/7.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="7"  style="width: 20px; height:17px;background: url(${ctx}/resources/images/scorecard/8.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="8"  style="width: 25px; height:17px;background: url(${ctx}/resources/images/scorecard/10.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="9"  style="width: 32px; height:17px;background: url(${ctx}/resources/images/scorecard/11.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="10" style="width: 30px; height:17px;background: url(${ctx}/resources/images/scorecard/12.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="11" style="width: 13px;height:17px;background: url(${ctx}/resources/images/scorecard/13.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="12" style="width: 25px;height:17px;background: url(${ctx}/resources/images/scorecard/14.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="13" style="width: 27px;height:17px;background: url(${ctx}/resources/images/scorecard/15.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="14" style="width: 37px;height:17px;background: url(${ctx}/resources/images/scorecard/16.png) center center;"></li>
                    <li class="c-operational-character" onclick="character(this)" dataId="15" style="width: 10px;height:17px;background: url(${ctx}/resources/images/scorecard/17.png) center center;"></li>
                    <li style="width: 17px;height:17px;background: url(${ctx}/resources/images/scorecard/18.png) center center;"></li>
                    <li style="width: 2px;height:17px;background: url(${ctx}/resources/images/scorecard/19.png) center center;"></li>
                </ul>
                <div class="Remark" style="padding-top: 5px;height: 250px;overflow-y: auto">
                    <ul class="clearfix" id="OutScoreLi">
                        <li style="width: 100%;text-align: left;margin: 0;padding: 0">
                            <label class="label_name" style="margin-left: 1em">输出</label>
                        </li>
                        <li style="text-align: left;padding-left:2em;width:100%;line-height: 45px" class="c-optional-rules">
                            <span class="iconaddData" onclick="addOutLi(this)"></span>
                            <span class="iconclearData_disabled"></span>
                            <select name="fieldId" size="1" style="width: 100px">
                                <option dataid="0" valueScope="" valueType ="1" value="score">信用得分</option>
                            </select>
                            =<textarea  style="width: 300px" class="scoreInput" name="score_score" intervalJson="" onclick="setCursor(this.id)"></textarea>
                        </li>
                        <li style="text-align: left;padding-left:2em;width:100%;line-height: 45px" class="c-optional-rules">
                            <span class="iconaddData" onclick="addOutLi(this)"></span>
                            <span class="iconclearData_disabled"></span>
                            <select name="fieldId" size="1" style="width: 100px">
                                <option dataid="0" valueScope="" valueType ="1" value="pd">PD</option>
                            </select>
                            =<textarea style="width: 300px" class="scoreInput" name="score_PD" intervalJson="" onclick="setCursor(this.id)"></textarea>
                        </li>
                        <li style="text-align: left;padding-left:2em;width:100%;line-height: 45px" class="c-optional-rules">
                            <span class="iconaddData" onclick="addOutLi(this)"></span>
                            <span class="iconclearData_disabled"></span>
                            <select name="fieldId" size="1" style="width: 100px">
                                <option dataid="0" valueScope="" valueType ="1" value="odds">ODDS</option>
                            </select>
                            =<textarea style="width: 300px" class="scoreInput" name="score_ODDS" intervalJson="" onclick="setCursor(this.id)"></textarea>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var add_score_out = '<li style="text-align: left;padding-left:2em;width:100%;line-height: 45px" class="c-optional-rules">'+
            '<span class="iconaddData" onclick="addOutLi(this)"></span>'+
            '&nbsp;<span class="iconclearData" onclick="deleteOutLi(this)"></span>'+
            '&nbsp;<select name="fieldId" size="1" style="width: 100px" onclick="getInputParameter(this,2)">'+
            '</select>'+
            '&nbsp;=<textarea type="text" name="fieldValue" style="width: 300px;" class="scoreInput" value="" intervalJson="" onclick="setCursor(this.id)"></textarea></li>';
</script>