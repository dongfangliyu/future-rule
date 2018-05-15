roleApp.service('roleResService', ['$http', '$q', 'baseService',
        function($http, $q, baseService) {
            return {
                //角色权限保存
                saveRes: function(json) {
                    var url = _ctx + '/rest/roleRes/save';
                    return baseService.post(url,json);
                },
                list: function(json) {
                    var url = _ctx + '/rest/roleRes/list';
                    return baseService.post(url,json);
                }
            }
        }
]);
