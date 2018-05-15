/**页面初始化datatable赋值**/
$(function () {
    g_userManage.tableUser = $('#testInfo_list').dataTable($.extend({
        'iDeferLoading': true,
        "bAutoWidth": false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax": function (data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('engine/engineTestList', JSON.stringify(queryFilter), function (result) {
                var returnData = {};
                var resData = result.data;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            }, "application/json")
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {'data':null, "searchable": false, "orderable": false},
            {"data": "verId", "orderable": false},
            {"data": "version", "orderable": false},
            {"data": "engineName", "orderable": false},
            {"data": "engineDesc", "orderable": false},
            {"data": "latestTime", "orderable": false},
            {"data": "null", "orderable": false, "defaultContent":""}
        ],
        "createdRow": function (row, data, index, settings, json) {
            var btnDel = $('<a class="tabel_btn_style" onclick="batchProduct(\''+data.verId+'\')">批量生成</a>&nbsp;<a class="tabel_btn_style" onclick="batchLead('+data.verId+')">批量执行</a>');
            $('td', row).eq(6).append(btnDel);
        },
        "initComplete": function (settings, json) {
            //查询
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //重置
            $("#btn_search_reset").click(function() {
                $("#searchTest").val("");
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
            param.searchKey = $("#searchTest").val();
        }
        var engineId=$("#engineId").val();
        param.engineId = engineId;
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
/**批量生成**/
function batchProduct(id){
    $("#nullCtRatio").val("");
    $("#elseCtRatio").val("");
    $("#rowCt").val("");
    var productLayer=layer.open({
        type : 1,
        title : "批量生成",
        maxmin : true,
        area : [ '350px', '150px' ],
        content: $('#produce_detail_style'),
        btn : [ '生成', '取消' ],
        yes : function(index, layero) {
            var param={};
            param.verId=id;
            var count=$("#rowCt").val();
            window.open(_ctx+"/engine/engineTestExport?verId="+id+"&count="+count);
            layer.close(productLayer)
        },
        cancel: function(index, layero){
            layer.close(productLayer)
        }
    });
}
/**批量导入  把选择的文件名放在input框中**/
function handleFile() {
    $("#fileName").val($("#file").val());
}
function batchLead(id){//导入
    $("#fileName").val("");
    var file = document.getElementById("file");
    // for IE, Opera, Safari, Chrome
    if (file.outerHTML) {
        file.outerHTML = file.outerHTML;
    } else { // FF(包括3.5)
        file.value = "";
    }
    var leadLayer=layer.open({
        type : 1,
        title : "批量执行",
        maxmin : true,
        area : [ '500px', '200px' ],
        content: $('#Lead_detail_style'),
        btn : [ '执行', '取消' ],
        yes : function(index, layero) {
            if( $("#fileName").val()==""){
                layer.msg("请选择文件",{time: 1000},function(){
                });
                return;
            }
            if($('#file')[0].files[0].type!='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'&&$('#file')[0].files[0].type!='application/vnd.ms-excel'){
                layer.msg("文件格式为Excel!请重新选择!",{time: 2000},function(){});
                return;
            }
            var formData = new FormData();
            formData.append("filename",$('#file')[0].files[0]);//文件名
            formData.append("verId",id);//版本id
            Comm.ajaxPost('engine/engineTestUpload',formData,function (data) {
                var size = data.data.length;
                if (size > 0) {
                    layer.msg("成功执行【"+size+"】条数据测试",{time:2000});
                }
            },false,false,false);
            layer.close(leadLayer)
        },
        cancel: function(index, layero){
            layer.close(leadLayer)
        }
    });
}
