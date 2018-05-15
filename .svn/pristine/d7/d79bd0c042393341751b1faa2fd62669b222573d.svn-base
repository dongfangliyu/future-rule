$("#decision_region .c-field-condition").on("change","select[name='field_code']",function(e){
    e.stopPropagation();//阻止冒泡
    var field = $(this).find("option:selected").get(0);
    renderOptions(field);
})
$("#decision_region .c-field-condition").on("click",".l_addData",function(e){
    e.stopPropagation();//阻止冒泡
    var valueType_out = $("#d-option-out input").attr("valueType");
    var valueScope_out = $("#d-option-out input").attr("valuescope");
    appendOptions(valueType_out,valueScope_out);
    $("#decision_region .c-field-condition").append(newData);
    $("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition select[name=field_code]").html('').html(getOptions());
    var field = $("#decision_region .c-field-condition .c-inclusion:last-child").find('select[name=field_code] option:selected').get(0)
    renderOptions(field);
});
$("#decision_region .c-field-condition").on("click",".l_addData2",function(e){
    e.stopPropagation();//阻止冒泡
    $(this).parents(".c-optional-rules").append(newSubData);
    if($(this).parents(".c-repetition").find('select[name= sign]').length > 0){
        $(this).parents(".c-optional-rules").find(".c-repetition:last-child").prev().append('<select class="l_before" name ="sign" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+sign+'</select>')
    }else{
        $(this).parents(".c-repetition").append('<select class="l_before" name ="sign" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+sign+'</select>')
    }
    $("#decision_region .c-field-condition").find(".c-inclusion .c-optional-rules .c-repetition select[name=field_code]").html('').html(getOptions());
    var field = $(this).parents(".c-optional-rules").find(".c-repetition:last-child").find('select[name=field_code] option:selected').get(0)
    renderOptions(field);
});
$("#decision_region .c-field-condition").on("click",".l_delData",function(e){
    e.stopPropagation();//阻止冒泡
    $(this).parents(".c-inclusion").remove();
});
$("#decision_region .c-field-condition").on("click",".l_delData2",function(e){
    e.stopPropagation();//阻止冒泡
    $(this).parents(".c-repetition").prev().find("select[name=sign]").remove();
    $(this).parents(".c-repetition").remove();
});
/**初始化选项**/
var path=$("#getPath").val();
var sign ='<option value="and">且</option><option value="or">或</option>';
var  digitalType = '<option value=">">大于</option>'+
	 '<option value="<">小于</option><option value=">=">大于等于</option>'+
	 '<option value="<=">小于等于</option><option value="==">等于</option><option value="!=">不等于</option>';
var  characterType = '<option value="contains">包含</option>'+
	  '<option value="notContains">不包含</option><option value="equals">等于</option>'+
	  '<option value="notEquals">不等于</option>';
var  enumerationType ='<option value="==">等于</option><option value="!=">不等于</option>';
var newData,newSubData;
function renderOptions(field){
    var valueType = $(field).attr("valuetype");
    var valueScope = $(field).attr("valuescope");
    $(field).parent().parent('.c-repetition').find('select[name ="operator"]').html('').html(getOperators(valueType,valueScope))
    if(valueType != '3'){
        $(field).parent().parent('.c-repetition').find('select[name ="result"]').replaceWith(function(){return '<input type="text" name="result" >'} )
    }else{
        var obj =$(field).parent().parent('.c-repetition').find('select[name ="result"]');
        if($(obj).prop("outerHTML") == undefined){
            $(field).parent().parent('.c-repetition').find('input[name ="result"]').replaceWith(function(){return '<select class="l_before" name ="result" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;"></select>'} )
        }
        $(field).parent().parent('.c-repetition').find('select[name ="result"]').html('').html(getFieldsValue(valueType,valueScope))
    }
}
function getOptions(){
    var dom = $("#d-option").find('input:eq(0)');
    var options="";
    if($(dom).val()!=''){
        options = '<option dataid="'+$(dom).attr("dataId")+'" valueType= "'+$(dom).attr("valueType")+'" valueScope ="'+$(dom).attr("valueScope")+'" value="'+$(dom).attr("fieldCode")+'" >'+$(dom).attr("fieldName")+'</opttion>';
    }
    return options
}
/**填充数据options**/
function appendOptions(valueType,valuescope){
    newData ='<div class="c-inclusion" style="margin: 0px 0 0 0px;"><div class="c-operation-inclusion">'+
		'<a href="###"><img src="'+path+'/resources/images/rules/add.png" class="l_addData" style="vertical-align: baseline;"/></a>'+
		'<a href="###"><img src="'+path+'/resources/images/rules/delete.png" class="l_delData" style="vertical-align: baseline;margin: 0 10px"/></a>'+
		'<div class="c-field-return"><input type="hidden" name="conditions" />';
	    if(valueType == 3){
			newData +='<select class="l_before" name="result" style="width:60px;  font-size: 12px;">';
			var arr =  valuescope.split(",");
			for (var i = 0; i < arr.length; i++) {
				var subArr = arr[i].split(":");
				newData +='<option value="'+subArr[1]+'">'+subArr[0]+'</option>';
			}
			newData +='</select>';
		}else{
			newData +='<input type="text" name="result">';
		}
	newData += '</div><div class="c-cut-off-rule"></div></div><div class="c-optional-rules">';
	newData += appendSubOptions();
	newData += '</div></div>';
} 
/**填充子选项**/
function appendSubOptions(){
	newSubData = '<div class="c-repetition">'+
        '<a href="###"><img src="'+path+'/resources/images/rules/add.png" class="l_addData2" style="vertical-align: text-bottom;"/></a>'+
        '<a href="###"><img src="'+path+'/resources/images/rules/delete.png" class="l_delData2" style="vertical-align: text-bottom;"/></a>'+
        '<select class="l_before" name ="field_code" style="width:110px;margin:10px 5px 0 0;background-position:90px 0px;"></select>'+
        '<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;"></select>'+
        '<select class="l_before" name ="result" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;"></select>'+
        '</div>';
 	return newSubData;
}
/**获取操作项**/
function getOperators(valueType){
    var getOperators;
    if(valueType == '1' || valueType == '4'){
		getOperators ='<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+digitalType+'</select>';
    }else if(valueType == '2'){
		getOperators ='<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+characterType+'</select>';
    }else if(valueType == '3'){
		getOperators ='<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">'+enumerationType+'</select>';
    }else{
		getOperators ='<select class="l_before" name ="operator" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;"></select>';
    }     
    return getOperators;
}
/**获取字段值**/
function getFieldsValue(valueType,valuescope){
     var html;
     var arr =  valuescope.split(",");
	html = '<select class="l_before" name ="result" style="width:57px;margin:10px 5px 0 0;background-position:37px 0px;">';
  	 for (var t = 0; t < arr.length; t++) {
  		    var subArr = arr[t].split(":");
		 html +='<option value="'+subArr[1]+'">'+subArr[0]+'</option>';
	 }
	html +='</select>';
    return html;
}



      