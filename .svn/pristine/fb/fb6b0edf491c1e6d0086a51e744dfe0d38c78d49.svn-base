/**初始化**/
function gethead() {
    var param={};
    param.listType = $("#listType").val();
    param.listDbId = $("#hid").val();
    param.searchKey = $("#search").val();
    Comm.ajaxPost('datamanage/userlist/getcolumnlist', JSON.stringify(param), function (result) {
        var returnData = {};
        var resData = result.data;
        var columnList=resData.columnList;
        var html="";
        for(var i=1;i<5;i++){
            if(columnList[i].cnName==""||(columnList[i].cnName==null)){
                html+="";
            }else{
                html+="<th class='columnVal'>"+columnList[i].cnName+"</th>"
            }
        }
        $(html+"<th id='createMan'>创建人</th><th>创建时间</th><th>操作</th>").appendTo($("#detail_list tr"));
        //构造编辑查询字段列表
        for(var j=0; j<columnList.length; j++){
            colArray.push(columnList[j].colName);
        }
        //判断值为空时，隐藏
        hideSpace();
        $("input[name= 'colArray']").val(colArray);
        //加载表格
        table();
    }, "application/json")
}
/**头部th**/
$().ready(function(){
    gethead();
})
var g_userManage = {
    tableUser: null,
    currentItem: null,
    fuzzySearch:false,
    getQueryCondition: function (data) {
        var paramFilter = {};
        var page = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {
            param.searchKey = $("#searchDetail").val();
        }
        param.listType = $("#listType").val();
        param.listDbId = $("#hid").val();
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length == null ? 10 : data.length;

        paramFilter.page = page;

        return paramFilter;
    }
};
/**判断table值为空时，隐藏**/
function hideSpace(){
    var columnVal=$("#detail_list thead .columnVal").length;
    var trlist=$("#detail_list tbody tr");
    for(var i=0;i<trlist.length;i++){
        var tdlist=$(trlist[i]).children();
        for(var j=columnVal+2;j<tdlist.length-3;j++){
            if(tdlist[j].innerHTML==""){
                $(tdlist[j]).hide();
            }
        }
    }
}
function table(){
    g_userManage.tableUser = $('#detail_list').dataTable($.extend({
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
            Comm.ajaxPost('datamanage/userlist/list', JSON.stringify(queryFilter), function (result) {
                var returnData = {};
                var columnList = [];
                var resData = result.data;
                returnData.recordsTotal = resData.total;
                returnData.recordsFiltered = resData.total;
                returnData.data = resData.list;
                callback(returnData);
                hideSpace();
            }, "application/json");
        },
        "order": [],//取消默认排序查询,否则复选框一列会出现小箭头
        "columns": [
            {'data':'id', 'class': 'hidden', "searchable": false, "orderable": false},
            {
                "className": "childBox",
                "orderable": false,
                "data": null,
                "searchable": false,
                "render": function (data, type, row, meta) {
                    return '<input type="checkbox" id="operataId" value="' + data.id + '" style="cursor:pointer;" isChecked="false">'
                }
            },
            {"data": "t0", "orderable": false},
            {"data": "t1", "orderable": false},
            {"data": "t2", "orderable": false},
            {"data": "t3", "orderable": false},
            {"data": "nickName", "orderable": false},
            {
                "data": null, "orderable": false,
                "render": function (data, type, row, meta) {
                    return json2TimeStamp(data.created);
                }
            },
            {
                "data": "null", "orderable": false, "width": "300px",
                "render": function (data, type, row, meta) {
                    var img='<a class="operateDetail tabel_btn_style">修改</a>';
                    return img;
                }
            }
        ],
        "createdRow": function (row, data, index, settings, json) {
        },
        "initComplete": function (settings, json) {
            //全选
            $("#selectAll").click(function(J) {
                if (!$(this).prop("checked")) {
                    $("#detail_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                } else {
                    $("#detail_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                }
            });
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function () {
                    //判断值为空时，隐藏
                    hideSpace();
                });
            });
            //重置搜索
            $("#btn_search_reset").click(function() {
                $("#searchDetail").val("");
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function () {
                    //判断值为空时，隐藏
                    hideSpace();
                });
            });
            //单选行选择并赋值
            $("#detail_list tbody").delegate( 'tr','click',function(e){
                var target=e.target;
                if(target.nodeName=='TD'){
                    if(!target.parentNode.children[1]){
                        return;
                    }
                    var input=target.parentNode.children[1].children[0];
                    var isChecked=$(input).attr('isChecked');
                    if(isChecked=='false'){
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);
                        }else{
                            $(input).attr('checked','checked');
                        }
                        $(input).attr('isChecked','true');
                    }else{
                        if($(input).attr('checked')){
                            $(input).attr('checked',false);

                        }else{
                            $(input).attr('checked','checked');
                            var id = $(target).parents("tr").children().eq(1).children("input").val();
                            var val = $(target).parents("tr").children().eq(2).html();
                            $("#showCode").html(id);
                            $("#showName").html(val);
                        }
                        $(input).attr('isChecked','false');
                    }
                }
            });
            //操作
            $("#detail_list").on("click",".operateDetail",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                editDetail(id);
            });
            g_userManage.tableUser.ajax.reload(function () {
                //判断值为空时，隐藏
                hideSpace();
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
}
/**全局变量，存放到表里查询的字段列表,id,t0,t1,...**/
var colArray = new Array();
/**新增**/
function addDetail(){
    var addLayer=layer.open({
        type : 1,
        title : "添加",
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '800px', '300px' ],
        offset:'150px',
        content: $('#Add_detail_style'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){
            var param={};
            param.listType = $("#listType").val();
            param.listDbId = $("#hid").val();
            param.searchKey = $("#search").val();
            Comm.ajaxPost('datamanage/userlist/getcolumnlist', JSON.stringify(param), function (result) {
                var resData = result.data;
                var columnList=resData.columnList;
                var html="";
                for(var i=1;i<columnList.length-3;i++){
                    html+="<li> <label class='label_name'>"+columnList[i].cnName+"</label> <label for='listName"+i+"'><input name='listName"+i+"'  type='text'  id='listName"+i+"'/>";
                }
                $("#Add_detail_style ul").html(html);
            }, "application/json")
        },
        yes : function(index, layero) {
            var param={};
            param.listType = $("#listType").val();
            param.listDbId = $("#hid").val();
            var lilist= $("#Add_detail_style ul li");
            $.each(lilist,function(key,value){
                var liValue=$(value).find("input").val();
                param["t"+key] = liValue;
            });
            Comm.ajaxPost('datamanage/userlist/save', JSON.stringify(param), function (data) {
                if(data.data.count==1){
                    layer.msg(data.msg,{time: 1000},function(){
                        layer.close(addLayer);
                        g_userManage.tableUser.ajax.reload(function(){
                            //判断值为空时，隐藏
                            hideSpace();
                        });
                    });
                }else if(data.data.count == 0){
                    layer.msg(data.msg,{time: 1000},function(){

                    });
                    return;
                }else if(data.data.count == 2){
                    layer.msg(data.msg,{time: 1000},function(){

                    });
                    return;
                }
            }, "application/json")
        },
        cancel: function(index, layero){
            layer.close(index)
        }
    });
}
/**删除某一项**/
function deleteDetail() {
    var selectArray = $("#detail_list tbody input:checked");
    var msg="是否删除该条信息？";
    if (!selectArray || (selectArray.length <= 0)) {
        layer.msg("请选择一条明细！", {time: 2000});
        return;
    }
    var param = {
        Ids: null
    };
    param.searchKey = $("#search").val();
    param.listType = $("#listType").val();
    param.listDbId = $("#hid").val();
    var roleIds = new Array();
    $.each(selectArray, function (i, e) {
        var val = $(this).val();
        roleIds.push(val);
    });
    if (roleIds.length == 0) {
        return;
    }
    if(roleIds.length>1){
        msg="是否删除所选信息？";
    }
    param.Ids = roleIds;
    layer.confirm(msg, {
        btn: ['确定', '取消']
    }, function () {
        Comm.ajaxPost(
            'datamanage/userlist/delete', JSON.stringify(param),
            function (data) {
                if(data.code==0){
                    layer.msg("删除成功", {time: 1000}, function () {
                        layer.closeAll();
                        g_userManage.tableUser.ajax.reload(function(){
                            //判断值为空时，隐藏
                            hideSpace();
                        });
                    });
                }
            }, "application/json"
        );
    });
}
/**编辑客户信息**/
function editDetail(id){
    var editLayer=layer.open({
        type : 1,
        title : "客户信息",
        maxmin : true,
        shadeClose : true, //点击遮罩关闭层
        area : [ '800px', '300px' ],
        content: $('#Edit_detail_style'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){
            var param = {id: null, listType: null, listDbId:null, colArray:null};
            param.id = id;
            param.listDbId = $("#hid").val();
            param.listType = $("#listType").val();//名单类型
            param.colArray=$("input[name= 'colArray']").val();
            Comm.ajaxPost('datamanage/userlist/edit', JSON.stringify(param), function (result) {
                var dataList=result.data.dataList
                var html="";
                for(var i=1;i<dataList.length-3;i++){
                    html+="<li> <label class='label_name'>"+dataList[i].cnName+"</label> <label for='detailName"+i+"'><input name='detailName"+i+"'  type='text'  id='detailName"+i+"' value='"+dataList[i].fieldValue+"'/>";
                }
                $("#Edit_detail_style ul").html(html);

            }, "application/json")
        },
        yes : function(index, layero) {
            var param = {id: null, listType: null, listDbId:null,};
            param.id = id;
            param.listDbId = $("#hid").val();
            param.listType = $("#listType").val();//名单类型
            var lilist= $("#Edit_detail_style ul li");

            $.each(lilist,function(key,value){
                var liValue=$(value).find("input").val();
                param["t"+key] = liValue;
            });
            Comm.ajaxPost('datamanage/userlist/update', JSON.stringify(param), function (data) {
                if(data.data.count==1){
                    layer.msg(data.msg, {time: 1000}, function () {
                        layer.close(editLayer);
                        g_userManage.tableUser.ajax.reload(function(){
                            //判断值为空时，隐藏
                            hideSpace();
                        });
                    });
                }else if(data.data.count == 0){
                    layer.msg(data.msg,{time: 1000},function(){

                    });
                    return;
                }else if(data.data.count == 2) {
                    layer.msg(data.msg,{time: 1000},function(){

                    });
                    return;
                }
            }, "application/json")
        },
        cancel: function(index, layero){
            layer.close(editLayer);
        }
    });
}
/**下载或者模板下载**/
function down(sign){
    if(sign==0){//下载
        //window.open(_ctx+"/datamanage/userlist/down?listType=w&listDbId="+$("#hid").val()+"&downType=downData");
        window.open(_ctx+"/datamanage/userlist/down?listType="+$("#listType").val()+"&listDbId="+$("#hid").val()+"&downType=downData");
    }else if(sign==1){//模板下载
        window.open(_ctx+"/datamanage/userlist/down?listType="+$("#listType").val()+"&listDbId="+$("#hid").val()+"&downType=downTemplate");
    }
}
/**导入**/
function handleFile() {
    $("#fileName").val($("#file").val());
}
function leadLoad() {
    var leadLayer = layer.open({
        type: 1,
        title: "批量导入",
        maxmin: true,
        shadeClose: true, //点击遮罩关闭层
        area: ['500px', '200px'],
        content: $('#Lead_detail_style'),
        btn: ['保存', '取消'],
        success: function (index, layero) {
            $("#fileName").val("");
            var file = document.getElementById("file");
            // for IE, Opera, Safari, Chrome
            if (file.outerHTML) {
                file.outerHTML = file.outerHTML;
            } else { // FF(包括3.5)
                file.value = "";
            }
        },
        yes: function (index, layero) {
            var formData = new FormData();
            formData.append("file",$('#file')[0].files[0]);
            if( $("#fileName").val()==""){
                layer.msg("请选择文件",{time: 2000},function(){});
                return;
            }
            if($('#file')[0].files[0].type!='application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'&&$('#file')[0].files[0].type!='application/vnd.ms-excel'){
                layer.msg("文件格式为Excel!请重新选择!",{time: 2000},function(){});
                return;
            }
            Comm.ajaxPost('datamanage/userlist/springUpload?listType='+$("#listType").val()+'&listDbId='+$("#hid").val(), formData,function (data) {
                if (data.data != null) {
                    layer.msg("数据有误,稍后会自动导出下载",{time: 2000},function(){
                        layer.close(leadLayer);
                        window.open(_ctx+"/datamanage/userlist/downDupCustList?fileName="+data.data+"&listDbId="+
                            $("#hid").val(),"客户信息重复数据下载窗口");
                    })
                    return;
                }
                layer.close(leadLayer);
                g_userManage.tableUser.ajax.reload(function () {
                    //判断值为空时，隐藏
                    hideSpace();
                });
            },false,false,false);
        },
        cancel: function (index, layero) {
            layer.close(leadLayer);
        }
    });
}





