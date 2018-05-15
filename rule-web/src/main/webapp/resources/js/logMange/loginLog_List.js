$(function (){
    g_userManage.tableUser = $('#Log_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('loginLog/list', JSON.stringify(queryFilter), function (result) {
                if (result.code==1) {
                    layer.msg(result.msg,{icon:2,offset:'200px',time:1000});
                    return;
                }
                //封装返回数据
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;

                $('#pagesize').val(resPage.pageSize);

                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.resultCount;
                returnData.recordsFiltered = resPage.resultCount;
                returnData.data = resData;
                callback(returnData);
            },"application/json");
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {"data": null ,"searchable":false,"orderable" : false},
            {"data": "loginAccount","orderable" : false},
            {"data": null,"defaultContent":"","orderable" : false},
            {"data": "status","orderable" : false},
            {"data": "loginIp","orderable" : false},
            {"data": "remark","orderable" : false}
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var loginTime = data.loginTime;
            $("td", row).eq(2).append(json2TimeStamp(loginTime));
        },
        "initComplete" : function(settings,json) {
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            $("#btn_search_reset").click(function() {
                $("#loginAccount").val("");
                $("#loginTime").val("");
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableUser.on("order.dt search.dt", function() {
        g_userManage.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
});
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.loginAccount = $("#loginAccount").val();
            param.loginTime = $("#loginTime").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;
    }
};