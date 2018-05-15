roleApp.service('roleService', ['$http', '$q', 'baseService',
        function($http, $q, baseService) {
            return {
                detail: function(roleId) {
                    var url = _ctx + '/role/detail';
                    return baseService.post(url,roleId);
                },
                add: function(json) {
                    var url = _ctx + '/role/add';
                    return baseService.post(url,json);
                },
                edit: function(json) {
                    var url = _ctx + '/role/edit';
                    return baseService.post(url,json);
                },
                deleteRole: function(json) {
                    var url = _ctx + '/role/delete';
                    return baseService.post(url,json);
                }
            }
        }
]);
