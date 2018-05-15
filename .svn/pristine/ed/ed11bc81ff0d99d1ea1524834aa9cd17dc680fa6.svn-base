function queryList(queryFilter,url) {
    tableUser = $('#Res_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": false,
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "info": false,//页脚信息显示
        "ajax" : function(data, callback, settings) {
            Comm.ajaxGet(url, queryFilter, function (result) {
                if (result.code==1) {
                    layer.msg(result.msg,{time:1000});
                    return;
                }
                //封装返回数据
                var returnData = {};
                returnData.data = result.data;
                callback(returnData);
            });
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {"data": "menuId" ,'class':'hidden',"searchable":false,"orderable" : false},
            {"data": "name","orderable" : false},
            {"data": "parentId" ,'class':'hidden',"searchable":false,"orderable" : false},
            {"data": "parentName","orderable" : false},
            {"data": "type",
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data==1){
                        return '<lable class="label label-sm label-success arrowed arrowed-in">按钮</lable>'
                    }else{
                        return '<lable class="label label-sm label-warning arrowed arrowed-right">菜单</lable>'
                    }
                }
            },
            {"data": null,"defaultContent":"","orderable" : false},
            {"data": "url","orderable" : false},
            {"data": null,"defaultContent":"","orderable" : false},
            // {"data": null,"defaultContent":"","orderable" : false},
            {"data": "remark","orderable" : false}
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var isS = data.isShow;
            if(isS==1){
                $("td", row).eq(5).append("是");
            }else{
                $("td", row).eq(5).append("否");
            }
            $("td", row).eq(7).append(json2TimeStamp(data.createTime));
            //$("td", row).eq(8).append(json2TimeStamp(data.updateTime));
        },
        "initComplete" : function(settings,json) {
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    tableUser.draw( false );//刷新数据
}
var tableUser = null;