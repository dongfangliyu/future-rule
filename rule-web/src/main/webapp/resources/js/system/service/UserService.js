userApp.service('userService', ['$http', '$q', 'baseService',
        function($http, $q, baseService) {
            return {
            	addUser: function(user) {
                    var url = _ctx + '/user/add';
                    return baseService.post(url,user);
                },
                deleteUser: function(json) {
                    var url = _ctx + '/user/delete';
                    return baseService.post(url,json);
                },
                resetPwd: function(json) {
                    var url = _ctx + '/user/resetPwd';
                    return baseService.post(url,json);
                },
                editUser: function(user) {
                    var url = _ctx + '/user/edit';
                    return baseService.post(url,user);
                },
                detail: function(userId) {
                    var url = _ctx + '/user/detail';
                    return baseService.post(url,userId);
                },
                getRoleMap: function(userId) {
                    var url = _ctx + '/role/getRoleMap';
                    return baseService.post(url,userId);
                },
                saveUserRole: function(param) {
                    var url = _ctx + '/userRole/add';
                    return baseService.postForm(url,param);
                }
            }
        }
]);
