 var roleApp = angular.module('roleApp', ['formDirective']);
roleApp.controller('roleCtrl', ['$rootScope', '$scope','baseService','roleService','roleResService',function ($rootScope,$scope,baseService,roleService,roleResService) {
	$scope.requestParam = {};
	var layerIndex;
	//添加或者修改角色
	$scope.updateRole = function(sign){
		var titleName = "添加角色";
		$scope.role = {};
		if(!sign){
			// 编辑
			titleName = "编辑角色";
			var roleIdArr =  $.getChkValueArr("subRoleChkbox");
			if(roleIdArr && roleIdArr.length==1){
				baseService.post(_ctx+"/role/detail",roleIdArr[0]).then(function(response){
					$scope.role = response.data;
					openLayer(titleName);
				});
			}else{
				layer.alert('请选择一个！');
			}
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
			content : $('#Add_Roles_style')
		});
	}
	
	$scope.$on("afterSaveEvent",function(event,data){
		if(!data.r){
			layer.close(layerIndex);
		}
	});
	
	$scope.assignPerission = function(){
		var selectArray = $("#Role_list tbody input:checked");
		if(!selectArray || selectArray.length!=1){
			layer.alert('请选择一个！');
			return;
		}
		var roleId =  $(selectArray[0]).val();
		var roleMenuIds = [];
		//获取角色对应的资源
		baseService.post(_ctx+"/roleMenu/getMenuByRole",roleId).then(function(response){
			roleMenuIds = response.data;
			// 显示树
			showCheckboxTree( _ctx+"/menu/getTree","tree",roleMenuIds);
			openMenuTree(roleId);
		});
	}
	
	function openMenuTree(roleId){
		layer.open({
			type : 1,
			title : '资源权限',
			shadeClose : true, //点击遮罩关闭层
			offset:["20px"],
			area : ['250px','450px'],
			content : $('#Assigned_Roles_style'),
			btn : [ '保存', '取消' ],
			yes : function(index, layero) {
				var menuIds = getCheckboxTreeSelNode("tree");
                var param = {"roleId":roleId,"menuIds":menuIds};
				baseService.postForm(_ctx+"/roleMenu/add",param).then(function(response){
					layer.alert('保存成功！');
					layer.close(index);
				});
			}
		});
	}
	
 }]);