/**初始化**/
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
            param.searchString = $("[name='name']").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  = 10 ;
        paramFilter.page = page;

        return paramFilter;
    }
};
/**初始化表格数据**/
$(function (){
    //获取权限集合
    Comm.ajaxPost(
        'user/getPermission',"{}",
        function(data){
            var data=data.data;
            var html='';
            for(var i=0;i<data.length;i++){
                if(data[i] =="/engine/add"){
                    $("#addBtn").show();
                }
                if(data[i] =="/engine/update"){
                    $("#updateBtn").show();
                }
            }},"application/json");
    g_userManage.tableUser = $('#orger_list').dataTable($.extend({
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
            Comm.ajaxPost('engine/list', JSON.stringify(queryFilter), function (result) {
                if (result.code==1) {
                    layer.msg(result.msg,{icon:2,offset:'200px',time:1000});
                    return;
                }
                //封装返回数据
                var returnData = {};
                var resData = result.data;

                $('#pagesize').val(resData.pageSize);

                returnData.draw = data.draw;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
            },"application/json");
        },
        "order": [],
        "columns": [
            {"data": null ,'class':'hidden',"searchable":false,"orderable" : false},
            {"data": "id","orderable" : false},
            {"data": "code","orderable" : false},
            {"data": "name","orderable" : false},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.jurisdiction=='1'){
                        return '公共'
                    }else if(data.jurisdiction=='2'){
                        return '角色'
                    }else if(data.jurisdiction=='3'){
                        return '私有'
                    }
                }
            },
            {"data": "desc","orderable" : false},
            {"data" : "updateTime",
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data);
                }
            },
            {
                "className" : "cell-operation",
                "data": null,
                "defaultContent":"",
                "orderable" : false
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="updateOrger(0,'+data.id+')">修改</a>&nbsp;<a class="tabel_btn_style" onclick="deleteOrger('+data.id+')">删除</a>&nbsp;<a class="tabel_btn_style" onclick="intoFlow('+data.id+')">引擎流程</a>');
            $('td', row).eq(7).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            $("#btn_search_reset").click(function() {
                $("input[name='name']").val("");
                g_userManage.fuzzySearch = false;
                g_userManage.tableUser.ajax.reload();
            });
            $("#orger_list tbody").delegate( 'tr','dblclick',function(e){
                var target=e.target;
                var id;//引擎ID
                if(target.nodeName=='TD'){
                    var tr=target.parentNode.children[1];
                    id = $(tr).html();
                }
                window.location=_ctx+"/decision_flow/decisionsPage?id="+id+"&parentId=1&flag=1";
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
/**删除引擎**/
function deleteOrger(id) {
    layer.confirm('是否删除该引擎？', {
        btn : [ '确定', '取消' ]
    }, function() {
        Comm.ajaxPost('engine/delete',"id="+id,function(data){
            layer.msg(data.msg,{time:1000},function () {
                g_userManage.tableUser.ajax.reload();
            })
        });
    });
}
/**添加、编辑组织信息**/
function updateOrger(sign,id) {
    if(id && sign==0){
            layer.open({
                type : 1,
                title : '修改信息',
                maxmin : true,
                shadeClose : false,
                area : [ '460px', '320px' ],
                content : $('#Add_orger_style'),
                btn : [ '保存', '取消' ],
                success : function(index, layero) {
                    Comm.ajaxPost('engine/initupdate',"id="+id,function(data){
                        var eng=data.data.data;
                        $('#orger_code').val(eng.code);
                        $('#orger_name').val(eng.name);
                        $('#remark').val(eng.desc);
                        $('#jurisdiction').val(eng.jurisdiction);
                    });
                },
                yes : function(index, layero) {
                    if ($('#orger_name').val() == "") {
                        layer.msg("名称不能为空",{time:2000});
                        return;
                    }
                    var name=$('#orger_name').val();
                    var desc=$('#remark').val();
                    var jurisdiction=$('#jurisdiction').val();
                    var roleId=$('#roleId').val();
                    var engine={id:id, name:name, desc:desc, jurisdiction:jurisdiction, roleId:roleId};
                    Comm.ajaxPost(
                        'engine/update',JSON.stringify(engine),
                        function(data){
                            layer.closeAll();
                            layer.msg(data.msg,{time:1000},function () {
                                $('#orger_list').dataTable().fnDraw(false);
                            });
                        }, "application/json");
                }
            });
    }else{
        $('#orger_name').val("");
        $('#remark').val("");
        var codeUUID;
        Comm.ajaxGet(
            'engine/getUUID',{},
            function(data){
                codeUUID = data.data.uuid;
                $("#orger_code").val(codeUUID);
            });

        layer.open({
            type : 1,
            title : '添加引擎',
            maxmin : true,
            shadeClose : false,
            area : [ '460px', '320px' ],
            offset: '150px',
            content : $('#Add_orger_style'),
            btn : [ '保存', '取消' ],
            yes : function(index, layero) {
                if ($('#orger_name').val() == "") {
                    layer.msg("引擎名称不能为空",{time:2000});
                    return;
                }
                var name=$('#orger_name').val();
                var desc=$('#remark').val();
                var jurisdiction=$('#jurisdiction').val();
                var roleId=$('#roleId').val();
                var engine={
                    code:codeUUID,
                    name:name,
                    desc:desc,
                    jurisdiction:jurisdiction,
                    roleId:roleId
                };
                Comm.ajaxPost(
                    'engine/update',JSON.stringify(engine),
                    function(data){
                        layer.closeAll();
                        layer.msg(data.msg,{time:1000},function () {
                            g_userManage.tableUser.ajax.reload();
                        });
                    },"application/json"
                );
            }
        });
    }
}
/**进入流程页面**/
function intoFlow(id){
    window.location=_ctx+"/decision_flow/decisionsPage?id="+id+"&parentId=1&flag=1";
}
