var menuApp = angular.module('menuApp', ['formDirective']);
menuApp.filter('trustHtml', function ($sce) {
    return function (input) {
        return $sce.trustAsHtml(input);
    }
});
menuApp.controller('menuCtrl', ['$scope','baseService',function ($scope,baseService) {
	$scope.menuType = [{typeId: 0 , name: "菜单"}, {typeId: 1, name: "按钮"}];
	$scope.menus = [];
	$scope.getByMenuId = function(menuId){
		baseService.get(_ctx+"/menu/detail?menuId="+menuId).then(function(data){
				var type = data.type;
				$scope.menu = data;
				$scope.menu.type = type.toString();
		});
	}
	$scope.getParentByMenuId = function(menuId){
		baseService.get(_ctx+"/menu/getByParentId?parentId="+menuId).then(function(data){
				$scope.menus = data;
		});
	}

	$scope.deleteByMenuId = function(menuId){
		if(!menuId){
			return;
		}
		var param = new Array();
        param.push(menuId);
		baseService.post(_ctx+"/menu/delete",param).then(function(data){
            $scope.menus = [];
			$.jstree.reference("#jstree").refresh();
		});
	}

	$scope.$on("afterSaveEvent",function(event,data){
		if(!data.r){
			currentNode.state.selected = true;
			$scope.getParentByMenuId(currentNode.id);
			layer.close(layerIndex);
		}
		currentNode.state.selected = false;
		$.jstree.reference("#jstree").refresh();
	});
 }]);



