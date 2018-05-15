// /**
//  * Created by Administrator on 2017/5/3 0003.
//  */
var g_userManage = {
    tableUser: null,
    currentItem: null,
    fuzzySearch: false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.loginAccount = $("#operateAccount").val();
            param.operateTime = $("#operateTime").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;
    }
};

$(function () {
    g_userManage.tableUser = $('#webLog_list').dataTable($.extend({
        'iDeferLoading': true,
        "bAutoWidth": false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax": function (data, callback, settings) {
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('webLog/list', JSON.stringify(queryFilter), function (result) {
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.resultCount;
                returnData.recordsFiltered = resPage.resultCount;
                returnData.data = resData;
                callback(returnData);
            }, "application/json")

        },
        "order": [],
        "columns": [
            {"data": null ,"searchable":false,"orderable" : false},
            {"data": 'loginAccount', "searchable": false, "orderable": false},
            {"data": 'method', "searchable": false, "orderable": false},
            {"data": 'methodArgs', "searchable": false, "orderable": false},
            {"data": 'methodDesc', "searchable": false, "orderable": false},
            {
                "data": null,
                "searchable": false,
                "orderable": false,
                "render": function (data, type, row, meta) {
                    return json2TimeStamp(data.operateTime);
                }
            },
            {"data": 'status', "searchable": false, "orderable": false},
            {"data": 'operateIp', "searchable": false, "orderable": false},
            {"data": 'remark', "searchable": false, "orderable": false}
        ],
        "createdRow": function (row, data, index, settings, json) {
        },
        "initComplete": function (settings, json) {
            $("#btn_search").click(function () {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            $("#btn_search_reset").click(function() {
                $("#operateAccount").val("");
                $("#operateTime").val("");
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManage.tableUser.on("order.dt search.dt", function () {
        g_userManage.tableUser.column(0, {
            search: "applied",
            order: "applied"
        }).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
});