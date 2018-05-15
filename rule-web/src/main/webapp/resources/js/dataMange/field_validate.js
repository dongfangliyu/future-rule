/**校验英文名称**/
function validateFieldEnName(){
	var fieldEnName = $("#en_name").val();
	var flag = false;
	var Id = $("#id").val();
	var engineId = $("#engineId").val();
	fieldEnName = 'f_' + fieldEnName;
	var reg = new RegExp("^[A-Za-z0-9_]+$");
	if(fieldEnName == 'f_'){
		layer.msg("字段名称不能为空!");
		return false;
	}else if(!reg.test(fieldEnName)) {
		layer.msg("字段名称不合法!");
		return false;
	}else {
		flag = true;
	}
    // else{
	// 	var param = {};
	// 	var paramFilter = {};
	// 	param._fieldEn = fieldEnName;
	// 	param._Id = Id;
	// 	param._engineId = engineId;
	// 	paramFilter.param = param;
	// 	Comm.ajaxPost(
	// 		'datamanage/field/fieldEnAjaxValidate', JSON.stringify(paramFilter),
	// 		function(data){
	// 			if(data > 0){
	// 				layer.msg("字段名称已经存在");
	// 				return false;
	// 			}else{
	// 				flag = true;
	// 			}
	// 		},"application/json",	null,false
	// 	);
	// }
	return flag;
}
/**校验中文名称**/
function validateFieldCnName(){
	var fieldCnName = $("#cn_name").val();
	var flag = false;
	var Id = $("#id").val();
	var engineId = $("#engineId").val();
	var reg = new RegExp("^[\u4e00-\u9fa5()（）A-Za-z0-9_]+$");
	if(fieldCnName == ''){
		layer.msg("字段中文名不能为空!");
		return false;
	}else if(!reg.test(fieldCnName)){
		// layer.msg("字段中文名称不合法");
		// return false;
		flag = true;
	} else {
		flag = true;
	}
	// else{
	// 	var param = {};
	// 	var paramFilter = {};
	// 	param.fieldCn = fieldCnName;
	// 	param.Id = Id;
	// 	param._engineId = engineId;
	// 	paramFilter.param = param;
	// 	Comm.ajaxPost(
	// 		'datamanage/field/fieldCnAjaxValidate', JSON.stringify(paramFilter),
	// 		function(data){
	// 			if(data > 0){
	// 				layer.msg("字段中文名已经存在");
	// 				return false;
	// 			}else{
	// 				flag = true;
	// 			}
	// 		},"application/json",	null,false
	// 	);
	// }
	return flag;
}
/**校验取值域**/
function validateValueScope(){
	var valueScope = $("#restrainScope").val();
	var selValue = $("#valueType").val();
	var flag = false;
	if(selValue=='1'){//数值型
		var reg = new RegExp("^(\\(|\\[)(((-?\\d+\\.\\d+|-?\\d+)?,(-?\\d+\\.\\d+|-?\\d+)+)|((-?\\d+\\.\\d+|-?\\d+)+,(-?\\d+\\.\\d+|-?\\d+)?))(\\)|\\])$");
		if(valueScope == ''){
			layer.msg("字段范围不能为空!");
			return false;
		}else if(!reg.test(valueScope)){
			layer.msg("字段范围不合法，示例：(1,2]");
			return false;
		}else{
			flag = true;
		}
	}
    if(selValue=='2'){//字符型
    	flag = true;
	}
    if(selValue=='3'){//枚举型
    	var reg = new RegExp("^([\u4e00-\u9fa5()\\[\\]（）A-Za-z0-9\_\-]+:(\-)?[A-Za-z0-9]+(,)?)+$");
    	if(valueScope == ''){
			layer.msg("字段范围不能为空");
			return false;
		}else if(!reg.test(valueScope)){
			// layer.msg("输入不正确，示例：男:1,女:2");
			// return false;
			flag = true;
		}else{
			flag = true;
		}
	}
    return flag;
}

