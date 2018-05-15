var start2;
var end2;
var g_userManageResult = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var page = {};
        var param = {};
        param.type=$("#dataType").val();
        param.verId=$("#verId").val();
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        var engineId=$("#engineId").val();
        var startTime=$("#startTime").val();
        var endTime=$("#endTime").val();
        if(startTime!=""&&endTime!="") {
            var sta_date = new Date(startTime.replace(/-/g,"/"));
            var end_date = new Date(endTime.replace(/-/g,"/"));
            var num = (end_date-sta_date)/(1000*3600*24);
            if(num<0){
                layer.msg("开始日期不能大于结束日期");
                return;
            }
            param.startDate=$("#startTime").val()+" 00:00:00";
            param.endDate=$("#endTime").val()+" 24:00:00";
        }
        if($("#searchPid").val()){
            param.searchPid=$("#searchPid").val();
        }else{
            param.searchPid=null;
        }
        param.engineId = engineId;
        paramFilter.page = page;
        return paramFilter;
    }
};
/**明细查询事件**/
function selectResult() {
    var startTime=$("#startTime").val();
    var endTime=$("#endTime").val();
    if(startTime!=""&&endTime!="") {
        var sta_date = new Date(startTime.replace(/-/g,"/"));
        var end_date = new Date(endTime.replace(/-/g,"/"));
        var num = (end_date-sta_date)/(1000*3600*24);
        if(num<0){
            layer.msg("开始日期不能大于结束日期");
            return;
        }
    }
    getResult();
}
function resultReturn() {
    $("#engine_list").show();
    $("#result_list").hide();
    $("#startTime").val("");
    $("#endTime").val("");
    $("#searchPid").val("");
    $("#listWidth").css("width","100%");
    $("#showTab").hide();
}
/**获取结果集调用的代码**/
function getResult(){
    g_userManageResult.tableUser = $('#batchTable').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "destroy":true,//Cannot reinitialise DataTable,解决重新加载表格内容问题
        "ajax" : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
            var queryFilter = g_userManageResult.getQueryCondition(data);
            Comm.ajaxPost('engine/result',JSON.stringify(queryFilter),function(result){
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            },"application/json")
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {
                'data':'id',
                'class':'hidden',"searchable":false,"orderable" : false
            },
            {"data": "id","orderable" : false},
            {"data": 'batchNo',"orderable" : false},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.result==''||data.result==null){
                        return '- -'
                    }else{
                        return data.result;
                    }
                }
            },
            {
                    "data": null, "orderable": false,
                    "render": function (data, type, row, meta) {
                        return json2TimeStamp(data.createTime);
                    }
                },
            {
                "data": "null",
                "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var seeDel = $('<a class="tabel_btn_style" onclick="seeDetailedId('+data.id+')">查看</a>');
            $('td', row).eq(5).append(seeDel);
        },
        "initComplete" : function(settings,json) {//回调函数
            $("#batchList").on("click",".look",function(e){
                var target = e.target||window.event.target;
                var id=$(target).parent().prev().prev().prev().prev().html();
                window.open(_ctx+"/engine/lookOver?resultSetId="+id);
            });
            $('#reservation').daterangepicker(null, function(start, end, label) {
                var timeStr=$('#reservation').val();
                timeStr=timeStr.split("-");
                start2=timeStr[0]+"-"+timeStr[1]+"-"+timeStr[2];
                end2=timeStr[3]+"-"+timeStr[4]+"-"+timeStr[5];
                g_userManageResult.fuzzySearch = true;
                g_userManageResult.tableUser.ajax.reload();
            })
        }
    }, CONSTANT.DATA_TABLES.DEFAULT_OPTION)).api();
    g_userManageResult.tableUser.on("order.dt search.dt", function() {
        g_userManageResult.tableUser.column(0, {
            search : "applied",
            order : "applied"
        }).nodes().each(function(cell, i) {
            cell.innerHTML = i + 1
        })
    }).draw()
};
function seeDetailedId(id) {
    window.open(_ctx+"/engine/lookOver?resultSetId="+id);
}
/**获取结果集调用的代码**/
$(function(){
    /**1代表api调用
      2代表数据填写
      3代表批量测试
    */
    //API调用
    $("#interfaceDebug").click(function(){
        $("#interfaceDebug").css({color: "#1a67bd"});
        $("#dataWrite").css({color: "#1a1a1a"});
        $("#batchTest").css({color: "#1a1a1a"});
        $("#dataType").val("1");
        getResult();
    })
    //数据填写
    $("#dataWrite").click(function(){
        $("#dataWrite").css({color: "#1a67bd"});
        $("#interfaceDebug").css({color: "#1a1a1a"});
        $("#batchTest").css({color: "#1a1a1a"});
        $("#dataType").val("2");
        getResult();
    })
    //批量测试
    var tableCheck=false;
    $("#batchTest").click(function(){
        $("#batchTest").css({color: "#1a67bd"});
        $("#dataWrite").css({color: "#1a1a1a"});
        $("#interfaceDebug").css({color: "#1a1a1a"});
        $("#dataType").val("3");
        getResult();
    })
})
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
            {
                'data':null,
                'class': 'hidden', "searchable": false, "orderable": false
            },
            {"data": "verId", "orderable": false},
            {"data": "version", "orderable": false},

            {"data": "engineName", "orderable": false},
            {"data": "engineDesc", "orderable": false},
            {"data": "latestTime", "orderable": false},

            {
                "data": "null",
                "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="seeDetailed('+data.verId+')">查看明细</a>&nbsp;<a class="tabel_btn_style" onclick="seeReport('+data.verId+')">查看报表</a>');
            $('td', row).eq(6).append(btnDel);
        },
        "initComplete": function (settings, json) {
            //批量生成
            $("#testInfo_list").on("click",".productBatch",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
            });
            //批量导入
            $("#testInfo_list").on("click",".leadBatch",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).html();
            });
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
/**查看明细**/
function seeDetailed(verId) {
    $("#verId").val(verId);
    $("#result_list").show();
    $("#batchList").show();
    $("#listWidth").css("width","84.5%");
    $("#showTab").show();
    getResult();
    $("#engine_list").hide();
}
/**查看报表**/
function seeReport(verId) {
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        area: '516px',
        shadeClose: true,
        content: $('#chart'),
        success:function () {
            Comm.ajaxGet("engineVersion/statement","verId="+verId,function (result) {
                var data=result.data;
                var myChart = echarts.init(document.getElementById('chart'));
                var option = {
                    title : {
                        text: '引擎报表详情',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['通过数量','拒绝数量','其他']
                    },
                    series : [
                        {
                            name: '统计来源',
                            type: 'pie',
                            radius : '55%',
                            center: ['50%', '60%'],
                            data:[],
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ],
                    backgroundColor: '#fff'
                };
                if(result.code==0){
                    if(data){
                        option.legend.data.push("通过数量"+data.passCount,"拒绝数量"+data.refuseCount,"其他"+data.elseCount);
                        option.series[0].data.push({value:data.passCount,name:"通过数量"},{value:data.refuseCount,name:"拒绝数量"},{value:data.elseCount,name:"其他"});
                        myChart.setOption(option);
                    }
                }
            });
        }
    });
}
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








