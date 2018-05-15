/**
 * 页面表单通用指令。
 */
var directive = angular.module("formDirective", [ "base" ])
/**
 * 校验指令. 用法： <input type="text" ng-model="user.name"
 * ht-validate="{require:true}" />
 * 
 * 具体的规则： /js/common/CustomValid.js 的rules 内置规则。
 */
.directive('htValidate', [ function() {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			var validate = attr.htValidate,isFirst = true;

			var validateJson = eval('(' + validate + ')');

			var customValidator = function(value) {
				if (!validate)
					return true;
				handlTargetValue(validateJson);
				var validity = $.fn.validRules(value, validateJson, element,isFirst);
				ctrl.$setValidity("customValidate", validity);
				return validity ? value : undefined;
			};

			ctrl.$formatters.push(customValidator);
			ctrl.$parsers.push(customValidator);
			element.on("focus",function(){
				isFirst = false;
				customValidator(ctrl.$modelValue);
			});
			element.on("blur",function(){
				layer.closeAll("tips");
			});
			// 获取比较目标字段的值。 所有比较的都包含target对象eg:{eq:{target:data.mian.name}}
			var handlTargetValue = function(validateJson) {
				for (key in validateJson) {
					if (validateJson[key].target) {
						validateJson[key].targetVal = eval("scope." + dateRange.target);
					}
				}
			}
		}
	};
} ])
/**
 * 表单的常用保存指令，看例子说话： 
 * <input type="button" ng-model="data" ht-save="bOEnt/save"/>
 * 
 * ps:<form name ="form">元素必须是 name ="form"
 * 
 * 参数介绍： ng-model :代表保存对象 ht-save :是保存的url地址
 * 
 * 后台controller： 可以参照 BOEntController.save方法
 * 
 * 页面controller(ngjs的控制层): 我们可以捕获保存后抛出的事件进行个性化操作（也可以不捕获）
 * eg:
 * $scope.$on("afterSaveEvent",function(event,data){
 * console.info("我捕获了afterSaveEvent事件"); console.info(data); });
 * data.r是选择的"是"true 和 "否"false
 * beforeSaveEvent一样
 */
.directive('htSave', [ 'baseService', function(baseService) {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			element.on("click", function() {
				if (!scope.form.$valid) {
					layer.alert("表单输入不正确");
					return;
				}
				
				var configObj={};
				//读取配置。
				var config=attr.config;
				if(config){
					configObj=angular.fromJson(config);
				}
				
				var data={};//数据
				data.pass = true;//前置事件用来控制能否提交的参数
				scope.$root.$broadcast('beforeSaveEvent',data);
				// 表单验证
				if (!data.pass) return;
				
				var postData=scope[attr.ngModel];
				
				var rtn = baseService.post(attr.htSave, postData);
				rtn.then(function(data) {
					if (data.code == 0) {
						data.postData=postData;
						//如果设置了配置。
						if(configObj.afterSave){
							eval("scope." +configObj.afterSave +"(data)" );
							return;
						}
						
						layer.confirm(data.msg+",是否继续操作", function(index){
							data.r = true;
							// 发布保存事件用于给用户自定义操作
							scope.$root.$broadcast('afterSaveEvent', data);
							layer.close(index);
						},function(){
							// 发布保存事件用于给用户自定义操作
							scope.$root.$broadcast('afterSaveEvent', data);
						});
					} else {
						layer.alert(data.msg);
					}
				}, function(status) {
					layer.alert("请求失败");
				});
			});
		}
	};
} ])
/**
 * 表单的常用初始化数据指令，看例子说话： 
 * <form name="form"  ht-load="bOEnt/getObject?id=${param.id}" ng-model="data"></form>
 * ps:当初始化对象为空时不作任何操作的 参数介绍： ht-load ：能返回一个对象的请求后台地址 
 * ng-model :把获取的对象赋值到该对象
 * 
 * 后台controller： 可以参照 BOEntController.getObject方法
 * 
 * 页面controller(ngjs的控制层): 我们可以捕获初始化数据后抛出的事件进行个性化操作（也可以不捕获）eg:
 * $scope.$on("afterLoadEvent",function(event,data){
 * console.info("我捕获了afterLoadEvent事件"); console.info(data); });
 */
.directive('htLoad', [ 'baseService', function(baseService) {
	return {
		require : "ngModel",
		link : function(scope, element, attr, ctrl) {
			if (!attr.htLoad || attr.htLoad == "") {
				return;
			}
			var rtn = baseService.post(attr.htLoad);
			rtn.then(function(data, status) {
				if (!data) return;
				scope[attr.ngModel] = data;
				scope.$root.$broadcast('afterLoadEvent', data);// 发布加载事件用于给用户自定义操作
			}, function(status) {
				layer.alert("请求失败");
			});
		}
	};
} ])
/**
 * ht-select-ajax 动态加载select的options数据 
 * 例如： 
 * <select ng-model="form.typeId"  ng-options="(m.id) as m.text for m in formTypeList"
 * 	ht-select-ajax="{url:'${ctx}/platform/system/sysType/getByGroupKey.ht?groupKey=FORM_TYPE',field:'formTypeList'}">
 * <option value="">请选择</option> 
 * </select> 
 * 传入参数 url ： 请求地址 
 * field ： formTypeList
 * 对应于 ng-options 中的 formTypeList （两者必须相同）
 */
.directive('htSelectAjax', function($injector) {
	return {
		restrict : 'A',
		require :'?ngModel',
		link : function(scope, element, attrs,ctrl) {
			var baseService = $injector.get("baseService");
			var option = attrs["htSelectAjax"];
			option = eval("(" + option + ")");
			if (scope.$root.$$childHead[option.field])
				return;
			var def = baseService.get(option.url);
			def.then(function(data) {
				if (option.dataRoot) {
					data = data[option.dataRoot];
				}
				scope[option.field] = data;
				scope.$root.$$childHead[option.field] = scope[option.field];
				// select option 生成后，让控件从新更新视图
				window.setTimeout(function(){
					ctrl.$render();
				},10)
			}, function() {
			});
		}
	};
})
/**
 * 汉字转拼音，例如 A 填写了 你好，当A失去焦点时，B自动填充为nh fullpinyin:1 全拼，不填0默认首字母 
 * eg: 
 * <input  type="text" ng-model="chinese" value=汉字/> 
 * <input type="text" ng-model="pingyin"  ht-pinyin="chinese" type="0" fullpinyin="1"/>
 */
.directive('htPinyin', [ 'baseService', function(baseService) {
	return {
		restrict : 'A',
		require : "ngModel",
		scope : {
			ngModel : "="
		},
		link : function(scope, elm, attrs) {
			var type = attrs.fullpinyin || 0;

			// 利用jq方法绑定失去焦点事件
			$("[ng-model='" + attrs.htPinyin + "']", elm.parent().closest(".ng-scope")).blur(function() {
				if (elm.val()) return;
				
				var obj = $(this);

				var value = obj.val();
				if (!value) return;

				var param = { Chinese : value, type : type };
				var rtn = baseService.postForm(__ctx + "/pinyinServlet", param);
				rtn.then(function(data) {
					scope.ngModel = data;
					//延迟触发blur,ngModel 还未将值设置进input
					window.setTimeout(function(){
						elm.trigger("blur");
					},100);
				}, function(errorCode) {
				});
			});
		}
	};
} ])
/**
 * 功能说明:
 * htCheckbox 指令用于收集checkbox数据。
 * 在页面中使用 
 * 	属性指令：ht-checkbox
 * 	对应的值为scope对应的数据data.users。
 * 示例:
  	<div >
        <input type="checkbox" ht-checkbox ng-model="data.users"  value="1" />红
		<input type="checkbox" ht-checkbox ng-model="data.users"   value="2" />绿
		<input type="checkbox" ht-checkbox ng-model="data.users"   value="3" />蓝
		<span>{{data.users}}</span>
   </div>
   <script>
       var app=angular.module("app",["directive"]);
		app.controller('ctrl', ['$scope',function($scope){
			$scope.data={users:"1,2"};
			$scope.getData=function(){
				console.info($scope.data.users)
			}
		}])
    </script>
 */
.directive('htCheckbox', [ function() {
	return {
		restrict : 'A',
		require : "ngModel",
		link : function(scope, element, attrs, ctrl) {
			var checkValue = attrs.value;
			
			//modelValue转viewValue的过程
			ctrl.$formatters.push(function(value) {
				if (!value) return false;
				
				var valueArr = value.split(",");
				if (valueArr.indexOf(checkValue) == -1) return false;
				
				return true;
			});
			
			//viewValue转modelValue的过程
			ctrl.$parsers.push(function(value){
				var valueArr = [];
				if(ctrl.$modelValue){
					valueArr = ctrl.$modelValue.split(",");
				}
				var index = valueArr.indexOf(checkValue);
				if (value) {
					// 如果checked modelValue 不含当前value
					if (index == -1) valueArr.push(checkValue);
				} else {
					if (index != -1) valueArr.splice(index, 1);
				}
				
				return valueArr.join(",");
			});
		}
	}
}])
/**
 * 列表全选指令，这个指令用于 ng-repeat 列表全选，反选。 指令的用法： 在列表项checkbox控件上增加指令
 * ht-checked="selectAll",属性值为全选checkbox的 ng-model属性。 
 * eg: <input type="checkbox"  ng-model="selectAll"/>
 * 
 * <tr  ng-repeat="item in data.defNameJson  track by $index">
 * <td> <input ng-model="item.selected" type="checkbox" ht-checked="selectAll">
 * </td>
 * </tr>
 * 
 */
.directive('htChecked', function() {
	return {
		restrict : 'A',
		require : "ngModel",
		scope : {
			ngModel : "="
		},
		link : function(scope, elm, attrs, ctrl) {
			scope.$parent.$watch(attrs.htChecked, function(newValue, oldValue) {
				if (newValue == undefined)
					return;
				ctrl.$setViewValue(newValue);
				ctrl.$render();
			});
		}
	};
})
/**
 * 删除  ht-del="{url:'/role/delete',checkboxName:'subRoleChkbox'}"
 */
.directive("htDel",[ 'baseService', function(baseService) {
	return {
		restrict : 'A',
		scope : {
			htDel : "="
		},
		link : function(scope, element, attrs, ctrl) {
			$(element).on("click",function(){
				var roleIdArr =  $.getChkValueArr(scope.htDel.checkboxName);
				if(roleIdArr&&roleIdArr.length>0){
					layer.confirm('是否删除选中的？', {
						btn : [ '确定', '取消' ]
					}, function() {
						 var url = _ctx + scope.htDel.url;
		                 baseService.post(url,roleIdArr).then(function(response){
							layer.msg('删除成功！', {
								time : 1000,
								icon : 1
							},function(){
								window.location.reload();
							});
						},function(){
							
						});
					});
				}else{
					layer.alert('请至少选择一个！');
				}
			});
		}
	};
}])
/**
 *日期控件。
 *控件用法：
 *<input type="text" ht-date="yyyy-MM-dd HH:mm:00" ng-model="date1" />
 *<input type="text" ht-date ng-model="date1" />
 *需要增加：ht-date 指令。
 * ht-date="yyyy-MM-dd HH:mm:ss"
 * 属性为日期格式。
 */
.directive('htDate', function() {
	var link = function(scope, element, attrs, $ctrl) {
		element.addClass("dateformat");
		var format=attrs.htDate || "yyyy-MM-dd";
		$ctrl.$formatters.push(function(value) {
			if(value){
				return new Date(value).format(format);
			}
		});
		return;
	};
	return {
		restrict : 'A',
		require : "ngModel",
		compile : function() {
			return link;
		}
	};
})
/**
 * 数字转成中文大写。
 * 用法：
 * <input class="inputText" type="text" ng-model="jinge"   />
 * 
 * {{jinge | cnCapital}}
 */
.filter('cnCapital', function() { 
	return function(input) {
		if(!input) return "";
		return $.convertCurrency(input);
	}; 
}); 
;


