var indexApp = angular.module('indexApp', ['base']);
indexApp.controller('indexCtrl', ['$rootScope', '$scope','indexService',function ($rootScope,$scope,indexService) {
	//退出登录
	$scope.logout = function(){
	   layer.confirm('是否确定退出系统？', {
		   btn: ['是','否']
		}, function(){
		  location.href= _ctx + "/logout";
		 });
	};
	//修改密码
	$scope.changePwd = function(){
		$scope.compositePwd = {};
		layer.open({
		type: 1,
		title:'修改密码',
		area: ['300px','300px'],
		shadeClose: true,
		content: $('#change_Pass'),
		btn:['确认修改'],
		yes:function(index){
			   if (!$scope.compositePwd || !$scope.compositePwd.originPwd || !$scope.compositePwd.confirmPwd || !$scope.compositePwd.newPwd){
                   alertDialog("密码不能为空!");
                   return;
		      }
			    if($scope.compositePwd.newPwd!=$scope.compositePwd.confirmPwd) {
                    alertDialog("新密码与确认密码不一致!");
                    return;
		    	}
			  indexService.changePwd($scope.compositePwd).then(function(){
				  layer.alert('修改成功！',{
					   title: '提示框',
						icon:1
				  });
				   layer.close(index);
				  });
			 }
		    });
	}
 }]);