indexApp.service('indexService', ['$http', '$q', 'baseService',
        function($http, $q, baseService) {
            return {
                //主页右侧更改密码
                changePwd: function(compositePwd) {
                    var url = _ctx + '/user/changePwd';
                    return baseService.postForm(url,compositePwd);
                }
            }
        }
]);
