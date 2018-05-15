var base = angular.module( "base", []);
base.factory("$jsonToFormData",function() {
	function transformRequest( data, getHeaders ) {
		var headers = getHeaders();
		headers["content-type"] = "application/x-www-form-urlencoded; charset=utf-8";
		return $.param(data);
	}
	return( transformRequest );
})
.service('baseService', ['$http','$q','$jsonToFormData', function($http, $q, $jsonToFormData) {
	return {
		get: function (url) {
			var deferred = $q.defer();
			$http.get(url).success(function (response) {
				if (response.code == 1) {
					alertError(response.msg);
					return;
				}
				if (response.code == 0) {
					deferred.resolve(response.data);
				} else {
					deferred.reject(response);
				}
			})
				.error(function (response, status) {
					deferred.reject(status);
					// 根据 状态全局提示
				});
			return deferred.promise;
		},
		postForm: function (url, param) {
			var deferred = $q.defer();
			$.post(url, param)
				.success(function (response) {
					//系统发生内部错误
					if (response.code == 1) {
						alertError(response.msg);
						return;
					}
					//系统正常执行
					if (response.code == 0) {
						deferred.resolve(response);
					}
				})
				.error(function (response, status) {
					deferred.reject(status);
					//TODO 根据返回的错误状态(status)显示对应的全局提示
				});
			return deferred.promise;
		},
		post: function (url, param) {
			var deferred = $q.defer();
			if (!param) {
				param = {};
			}
			$http.post(url, param).success(function (response) {
				//系统发生内部错误
				if (response.code == 1) {
					alertError(response.msg);
					return;
				}
				//系统正常执行
				if (response.code == 0) {
					deferred.resolve(response);
				} else {
					deferred.reject(response);
				}
			}).error(function (response, status) {
					deferred.reject(status);
				});
			return deferred.promise;
		},
        postWithPage: function (url, requestPage, paramFilter) {
			var deferred = $q.defer();
            paramFilter.page = requestPage;
			$http.post(url, paramFilter).success(function (response) {
				//系统发生内部错误
				if (response.code == 1) {
					alertError(response.msg);
					return;
				}
				//系统正常执行
				if (response.code == 0) {
					deferred.resolve(response);
				} else {
					deferred.reject(response);
				}
			}).error(function (response, status) {
					deferred.reject(status);
				});
			return deferred.promise;
		}
	};
}])
/**
 * 分页指令
 * viewTemplate    0: 上下分页的模板  1: 查看更多的模板  2： 空  没有这个属性默认0
 *
 * requestPageParams ： 接收的搜索条件
 *
 * returnPageRows： 接收返回的数据
 * 
 * url: 请求接口
 * 
 * clickSearch: boolean 是否触发搜索事件
 * example:1. clickSearch = !clickSearch 根据搜索条件来查询  
 *         2. clickSearch = clickSearch + 'F5CurrentPage' 刷新当前页
 * 
 * startNoRequest: 开始时 不搜索 
 *
 * 
 */
.constant('pageConfig', {
    visiblePageCount: 5,
    firstText: '首页',
    lastText: '最后一页',
    prevText: '上一页',
    nextText: '下一页'
})
.directive('egPage', ['pageConfig','baseService',function(pageConfig,baseService){
	return {
        replace: true,
        restrict: 'EAC',
        scope: {
        	requestPageParams:"=",
        	returnPageRows:"=",
        	clickSearch:"="
        },
        template:'<div>'+
                 '<div class="page_style">'+
                 '<select ng-model="pageSize" size="1" name="pageSize" ng-change="changePS()" ng-options="ps as ps for ps in pageSizeArr "  ng-selected="ps==pageSize"  >'+
                 '</select>'+
                 '<a href="" ng-click="pageChange(1)" class="icon-step-backward page_btn"></a>'+
                 '<a href="" ng-click="pageChange(currentPage-1>0?currentPage-1:1)" class="icon-caret-left page_btn"></a>第'+
                 '<select ng-model="currentPage" ng-change="changePS()" ng-options="num as num for num in pagenums" ng-selected="num==currentPage" name="pageSelect" size="1" >'+
                 '</select>'+
                  '<label>共</label><strong ng-bind="pageCount"></strong><label>页</label>'+
                  '<a href="" ng-click="pageChange(currentPage+1<=pageCount?currentPage+1:pageCount)"  class=" icon-caret-right page_btn"></a>'+
                 '<a href="" ng-click="pageChange(pageCount)" class="icon-step-forward page_btn"></a>'+
                 '</div>'+
    		     '</div>',
        link: function(scope, element, attrs) {
          var nextFlag = false;
          scope.firstText = angular.isDefined(attrs.firstText) ? attrs.firstText : pageConfig.firstText;
          scope.lastText = angular.isDefined(attrs.lastText) ? attrs.lastText : pageConfig.lastText;
          scope.prevText = angular.isDefined(attrs.prevText) ? attrs.prevText : pageConfig.prevText;
          scope.nextText = angular.isDefined(attrs.nextText) ? attrs.nextText : pageConfig.nextText;
          scope.requestPage = {"pageNo":1,"pageSize":10};
          scope.currentPage = 1;
          scope.pageCount = -1;
          scope.resultCount = 0;
          scope.pagenums = [];
          scope.goPageNum = "2";
          scope.pageSizeArr = [10,20,30];
          scope.pageSize = scope.pageSizeArr[0];
          scope.returnData = attrs.returnPageRows;
          var startNoRequest  = angular.isDefined(attrs.startNoRequest)? true:false;
          
          scope.pageChange = function(page) {
        	nextFlag = true;
        	if(scope.pageCount == -1) return;
            if (page >= 1 && page <= scope.pageCount) {
              scope.currentPage = page;
            } else {
              scope.currentPage = 1;
            }
          };
          function build() {
            scope.onPageChange();
          }
          
          /**
           *  触发下拉框搜索
           */
          scope.changePS = function(){
        	  nextFlag = true;
          };
          /**
           * 初始显示的页码
           * @returns
           */
          function init(hight){
        	  scope.pagenums = [];
        	  for (var i=1; i <= hight; i++) {
                  scope.pagenums.push(i);
              }
          }
          scope.$watch('currentPage+pageCount+pageSize', function() {
	            if(nextFlag){
	            	build();
	            }
          });
          // 检测 搜索条件的变化
          scope.$watch('clickSearch', function() {
        	scope.returnPageRows = [];
        	if(scope.clickSearch  ){
        		if(scope.clickSearch.length>=39){
        			scope.clickSearch = 'F5CurrentPage';
        		}
        	}else{
        		scope.currentPage = 1;
        	}
          	if(!startNoRequest){
          		build();
          	}
          	startNoRequest = false;
          	nextFlag = false;
          },true);
       
          // 获取分页数据
          scope.onPageChange = function(){
        	  if(angular.isUndefined(attrs.url)){
        		  return;
        	  }
        	  scope.requestPage.pageNo = scope.currentPage;
        	  scope.requestPage.pageSize = scope.pageSize;
        	  scope.requestPage.resultCount = scope.resultCount;
        	  baseService.postWithPage(_ctx+attrs.url,scope.requestPage,scope.requestPageParams).then(function(response){
        		  if(response.code==0){
        			  scope.returnPageRows = [];
        			  if(response[attrs.returnPageRows]){
        				  scope.returnPageRows =scope.returnPageRows.concat(response[attrs.returnPageRows]);
        			  }
        			  if(!response.page) return;
        			  scope.currentPage = response.page.pageNo;
        			  scope.pageCount  = response.page.pageCount;
        			  scope.pageSize = response.page.pageSize;
        			  scope.resultCount = response.page.resultCount;
        			  init(scope.pageCount);
        		  }else{
//        			  egDialog.error(data.msg);  
        		  }
        		  nextFlag = false;
        	  },function(msg){
        		  console.log(msg);
        	  });
          };
        }
      };
}])
//日期插件指令
.directive('egDate', [function(){
        return {
            replace: true,
            restrict: 'EAC',
            scope: {

            },
            link: function(scope, element, attrs) {
				$(element).datetimepicker({
					  language:  'zh-CN',
					  format: "yyyy-mm-dd",
					  weekStart: 1,
					  todayBtn:  0,
					  autoclose: 1,
					  todayHighlight: 1,
					  startView: 2,
					  minView: 2,
					  forceParse: 0
				});
            }
        };
    }]);

function alertError(msg){
	layer.alert(msg+"! \r\n", {
		title : '提示框',
		icon : 5
	});
}

function alertDialog(msg){
	layer.alert(msg,{
		title: '提示框',
		icon:0,
	});
}

/**
 * 外部JS和angular 交互类。
 * 主要包括两个方法：
 * 1.获取当前上下文的scope。
 * 2.设置修改后的scope。
 */
var AngularUtil={};

/**
 * 获取当前Angularjs scope 。
 */
AngularUtil.getScope=function(){
	return angular.element($("[ng-controller]")[0]).scope();
}

/**
 * 保存外部js对scope的修改。
 */
AngularUtil.setData=function(scope){
	!scope.$$phase && scope.$digest();
};


/**
 * 获取当前环境中的 service
 * serviceName：指定的服务名称。
 * 这里需要注意的是，只能获取当前ng-controller注入模块中的service。
 */
AngularUtil.getService = function(serviceName){
	if(!this.$injector){
		this.$injector =angular.element($("[ng-controller]")).injector();
	}
	if(this.$injector.has(serviceName)) {
		return this.$injector.get(serviceName);
	}
	else {
		alert(serviceName+"angular环境中没有找到该service！");
	}
};
/**
 * 根据jquery表达式获取指定元素上的控件值。
 * 比如:获取id为 userId的控件的值。
 * var userId=CustForm.getModelVal("#userId");
 */
AngularUtil.getModelVal = function(element){
	var inputCtrl = $(element).data("$ngModelController");
	return inputCtrl.$modelValue;
};

/**
 * 通过载入方法名，调用scope中的某个方法
 * @param funStr
 */
AngularUtil.triggerScopeFun = function(funStr){
	var scope  = AngularUtil.getScope();
	var fun = scope[funStr];
	if(fun) return fun.call(this,arguments[1]);
}