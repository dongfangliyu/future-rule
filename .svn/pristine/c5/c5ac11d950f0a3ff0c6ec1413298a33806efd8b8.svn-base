var userApp = angular.module('userApp', ['base']);
userApp.controller('userCtrl', ['$rootScope', '$scope','userService',function ($rootScope,$scope,userService) {
	$('#multiselect').multiselect({});
	//搜索参数
	$scope.queryFilter = {};
	$scope.role = {	};
	$scope.isLockMap = [{isLock:0, name: "启用"}, {isLock: 1, name: "停用"}];
	//添加用户,1为添加，0为修改
	$scope.updateUser = function(sign){
		var selectArray = $("#User_list tbody input:checked");
		if(!selectArray || (selectArray.length!=1 && sign==0)){
			alertDialog("请选择一个");
			return;
		}
		var titleName = selectArray && selectArray.length>0 ? '修改用户':'添加用户';
		var userId = $(selectArray[0]).val();
		if(userId && sign==0){
			userService.detail(userId).then(function(response){
				var isLock = response.data.user.isLock;
				$scope.user = response.data.user;
				$scope.user.isLock = isLock.toString();
			});
		}else{
			$scope.user = {
				isLock :'0'
			};
		}
		layer.open({
			type : 1,
			title : titleName,
			maxmin : true,
			shadeClose : true, //点击遮罩关闭层
			area : [ '576px', '468px' ],
			content : $('#Add_user_style'),
			btn : [ '保存', '取消' ],
			yes : function(index, layero) {
				if ($("#name").val() == "") {
					alertDialog("登录用户名不能为空");
					return;
				}
				if ($("#password").val() == "") {
					alertDialog("密码不能为空");
					return;
				}
				if ($("#phone").val() == "") {
					alertDialog("手机号码不能为空");
					return;
				}
				if ($("#select_status").val() == "") {
					alertDialog("用户状态能为空");
					return;
				} else {
					var user = angular.copy($scope.user);
					var roleIds = [];
					$("#multiselect_to option").each(function(i,e){
						var selectVal = $(this).val();
						roleIds.push(selectVal);
					});
					if(!user){
						layer.msg('用户不能为空！', {
							time : 1000,
							icon : 1
						});
						return;
					}
					var isLockStr = user.isLock;
					user.isLock = parseInt(isLockStr);
					if(!userId){
						userService.addUser(user).then(function(response){
							layer.alert(response.msg, {
								title : '提示框',
								icon : 1,
							},function(){
								layer.close(index);
								window.location.reload();
							});
						});
					}else{
						userService.editUser(user).then(function(response){
							layer.alert('修改成功！', {
								title : '提示框',
								icon : 1,
							},function(){
								window.location.reload();
							});
							layer.close(index);
						},function(response){

						});
					}
				}
			}
		});
	}
	//重置密码
	$scope.resetPwd =function(){
		var selectArray = $("#User_list tbody input:checked");
		if(!selectArray || selectArray.length==0){
			alertDialog("请选择用户");
			return;
		}
		var userIds = new Array();
		$.each(selectArray,function(i,e){
			var val = $(this).val();
			userIds.push(val);
		});
		if(userIds.lenght==0){
			return;
		}
		layer.confirm('是否重置密码，重置后原密码将失效？', {
			btn : [ '重置', '取消' ]
		}, function() {
			userService.resetPwd(userIds).then(function(response){
				layer.msg(response.msg, {
					time : 1000,
					icon : 1
				});
			});
		});
	}
	
	//删除用户
	$scope.deleteUser = function(){
		var selectArray = $("#User_list tbody input:checked");
		if(!selectArray || selectArray.length==0){
			alertDialog("请选择用户");
			return;
		}
		var userIds = new Array();
		$.each(selectArray,function(i,e){
			var val = $(this).val();
			userIds.push(val);
		});
		if(userIds.lenght==0){
			return;
		}
		layer.confirm('是否删除用户？', {
			btn : [ '确定', '取消' ]
		}, function() {
			userService.deleteUser(userIds).then(function(resp){
				layer.msg(resp.msg, {
					time : 1000,
					icon : 1
				},function(){
					window.location.reload();
				});
			});
		});
	}

	$scope.asignRole = function(userId){
		var selectArray = $("#User_list tbody input:checked");
		if(!selectArray || selectArray.length!=1){
			alertDialog("请选择一个用户");
			return;
		}
		var userId;
		$.each(selectArray,function(i,e){
			var val = $(this).val();
			userId =val;
		});
		var includeRolesMap = [];
		var excludeRolesMap = [];
		userService.getRoleMap(userId).then(function(response){
			$scope.roleIds = response.data.roleIds;
			$.each(response.data.roleList,function(i,roleMap){
				var roleId = roleMap.roleId;
				if($scope.roleIds.indexOf(roleId)>=0){
					var roleObj = {'roleId':roleId,'name':roleMap.name};
					includeRolesMap.push(roleObj);
				}else{
					var roleObj = {'roleId':roleId,'name':roleMap.name};
					excludeRolesMap.push(roleObj);
				}
			});
			$scope.roleMap = excludeRolesMap;
			$scope.includeRoleMap =includeRolesMap;
		});
		layer.open({
		   type : 1,
		   title : "角色分配",
		   maxmin : true,
		   shadeClose : true, //点击遮罩关闭层
		   area : [ '576px', '468px' ],
		   content : $('#asignRole'),
		   btn : [ '保存', '取消' ],
		   yes : function(index, layero) {
			   var roleIds = [];
			   $("#multiselect_to option").each(function(i,e){
				   var selectVal = $(this).val();
				   roleIds.push(selectVal);
			   });
			   var param = {"userId":userId,"roleIds":roleIds};
				userService.saveUserRole(param).then(function(response){
					layer.alert(response.msg, {
						title : '提示框',
						icon : 6,
					},function(){
						layer.closeAll();
					});
				});
		   }});
	}
	
	$scope.selectAll = function($event){
		var target = $event.target
		if($(target).prop("checked")){
			$(".subUserChkbox").each(function(i,e){
				$(this).attr("checked",true);
			});
		}else{
			$(".subUserChkbox").each(function(i,e){
				$(this).attr("checked",false);
			});
		}
	}
 }]);