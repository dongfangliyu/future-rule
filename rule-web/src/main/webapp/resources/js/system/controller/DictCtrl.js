 var dictApp = angular.module('dictApp', ['formDirective']);
 dictApp.controller('dictCtrl', ['$rootScope', '$scope','baseService',function ($rootScope,$scope,baseService) {
	$scope.requestParam = {};
	$scope.dict = {};
	var layerIndex;
	//添加或者修改
	$scope.update = function(sign){
		var titleName = "添加";
		if(sign!=1){
			// 编辑
			titleName = "编辑";
			baseService.get(_ctx+"/dict/detail?id="+sign[1]).then(function(data){
				$scope.dict = data;
				if(Number($scope.dict.parentId)){
					$scope.dict.parentId = Number($scope.dict.parentId)
				}
				openLayer(titleName);
			});
		}else{
			openLayer(titleName);
		}
	}
	
	function openLayer(titleName){
		layerIndex = layer.open({
			type : 1,
			title : titleName,
			maxmin : true,
			shadeClose : true, //点击遮罩关闭层
			area : [ '600px', '' ],
			content : $('#Add_Dic_style')
		});
	}

	$scope.getParentByMenuId = function(parentId){
        baseService.get(_ctx+"/dict/getByParentId?parentId="+parentId).then(function(data){
            $scope.dictList = data;
        });
	}
	
	$scope.$on("afterSaveEvent",function(event,data){
		window.location.reload();
	});
	
	$scope.deleteById = function(id){
		  baseService.post(_ctx+"/dict/delete",[id]).then(function(data){
			  layer.alert("删除成功");
			  $.jstree.reference("#jstree").refresh();
	      });
	}
	
 }]);