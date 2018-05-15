menuApp.service('menuService', ['$http', '$q', 'baseService',
        function($http, $q, baseService) {
            return {
                // 添加订单
                addResource: function(resource) {
                    var url = _ctx + '/rest/resources/add';
                    return baseService.post(url,resource);
                },
                //根据菜单号删除订单
                deleteResource: function(json) {
                    var url = _ctx + '/rest/resources/delete';
                    return baseService.post(url,json);
                },
              //根据菜单号查询详细
                detail: function(resId, callbackSuccess, callbackFail) {
                    var url = _ctx + '/rest/resources/detail';
                    return baseService.post(url,resId);
                },
              //根据菜单号查询详细
                editResource: function(resource) {
                    var url = _ctx + '/rest/resources/edit';
                    return baseService.post(url,resource);
                },
                //查询对应的ResourceMap
                getResourceMap : function(){
                	var url = _ctx + '/rest/resources/getSelectResTree';
                    return baseService.post(url);
                },
                //根据父Id获取子资源
                getSubResByParentId : function(parentId){
                	var url = _ctx + '/rest/meta/list';
                	var json = {'resId':parentId};
                    return baseService.post(url,json);
                }
            }
        }
]);
