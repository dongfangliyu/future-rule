/**
 * Created by Win7 on 2017/5/4.
 */
var layerIndex;
var selfid;
//编辑的赋值
function update(sign){
    debugger
    $('#nameCode').val("");
    $('input[name="name_dict"]').val("");
    $('input[name="isCatagory"]:checked').attr("checked",false);
    $('textarea[name="remark"]').val("");
    var titleName = "添加";
    if(sign!=1){
        selfid=sign[1];
        // 编辑
        titleName = "编辑";
        Comm.ajaxGet("dict/detail?id="+sign[1], "", function(data){
                var dict = data.data;
                var isCatagory=dict.isCatagory;
                if(isCatagory=='Y'){
                    $('#isCatagory_Y').attr('checked','checked');
                }else{
                    $('#isCatagory_N').attr('checked','checked');
                }
                $('input[name="name_code"]').val(dict.code);
                $('input[name="name_dict"]').val(dict.name);
                $('textarea[name="remark"]').val(dict.remark);
                $("#parentId").val(dict.parentId);
                openLayer(titleName);
            }
        );
    }else{
        selfid=null;
        openLayer(titleName);
    }
}
//打开弹框
function openLayer(titleName){
    layerIndex = layer.open({
        type : 1,
        title : titleName,
        maxmin : true,
        shadeClose : false, //点击遮罩关闭层
        area : [ '600px', '' ],
        content : $('#Add_Dic_style'),
        btn : [ '保存', '取消' ],
        success:function(index, layero){
            assortSelect(titleName);
        },
        yes:function(index, layero){
            var selectType=$('select[name="selectType"]').val();
            var nameCode=$('input[name="name_code"]').val();
            var name=$('input[name="name_dict"]').val();
            var isCatagory=$('input[type="radio"]:checked').val();
            var remark=$('textarea[name="remark"]').val();
            var id=selfid;
            var dict={
                id:id,
                code:nameCode,
                name:name,
                isCatagory:isCatagory,
                remark:remark,
                parentId:selectType
            }
            Comm.ajaxPost(
                'dict/add',JSON.stringify(dict),
                function(data){
                    layer.msg(data.msg,{time:1000},function(){
                        layer.close(layerIndex);
                        $.jstree.reference("#jstree").refresh();
                        var id=$("#id").val();
                        queryList("parentId="+id,"dict/getByParentId");;
                        document.getElementById("myForm").reset();
                    });
                }, "application/json"
            );
        }
    });

}
//初始化table表
function queryList(queryFilter,url){
    g_userManage.tableUser = $('#Res_list').dataTable($.extend({
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
                var returnData = {};
                var resData = result.data;
                returnData.data = resData;
                callback(returnData);
            }, "application/json")
        },
        "order": [],
        "columns" :[
            {
                'data':null,
                'class':'hidden',"searchable":false,"orderable" : false
            },
            {"data":'code',"searchable":false,"orderable" : false},
            {"data":'name',"searchable":false,"orderable" : false},
            // {
            //     "data" : null,
            //     "searchable":false,
            //     "orderable" : false,
            //     "render" : function(data, type, row, meta) {
            //         if(data.isCatagory=="Y"){
            //             return '是'
            //         }else{
            //             return '否'
            //         }
            //     }
            // },
            {
                "data":null,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data.createTime);
                }
            },
            // {
            //     "data":null,
            //     "orderable" : false,
            //     "render" : function(data, type, row, meta) {
            //         return json2TimeStamp(data.updateTime);
            //     }
            // },
            {"data":'remark',"searchable":false,"orderable" : false}
        ],
        "createdRow": function ( row, data, index,settings,json ) {
        },
        "initComplete" : function(settings,json) {

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
}
var g_userManage = {
    tableUser : null,
    currentItem : null,
    fuzzySearch : false,
    getQueryCondition : function(data) {
        var paramFilter = {};
        var param = {};

        //自行处理查询参数
        param.fuzzySearch = g_userManage.fuzzySearch;
        if (g_userManage.fuzzySearch) {

        }
        param.parentId=$("#menuId").val();
        paramFilter.param = param;

        return paramFilter;
    }
};
//删除
function deleteById(id){
    Comm.ajaxPost(
        'dict/delete',
        JSON.stringify([id]),
        function(data){
            layer.msg("删除成功",{time:2000});
            $.jstree.reference("#jstree").refresh();
        },"application/json"
    );
}
//编辑和新增
function assortSelect(titleName){
    Comm.ajaxGet(
        "dict/getCatagory",
        '',
        function(data){
            var dataList=data.data;
            if(titleName=="编辑"){
                var html="";
                var id=$("#parentId").val();
                for(var i=0;i<dataList.length;i++){
                    if(id==dataList[i].id){
                        html+="<option value='"+dataList[i].id+"' selected>"+dataList[i].name+"</option>"
                    }else{
                        html+="<option value='"+dataList[i].id+"'>"+dataList[i].name+"</option>"
                    }
                }
                $('select[name="selectType"]').html(html);
            }
            else if(titleName=="添加"){
                var id=$("#id").val();
                if($("#type").val()=="file"){
                    var parent=$("#parent").val();
                    var html="";
                    for(var i=0;i<dataList.length;i++){
                        if(parent==dataList[i].id){
                            html+="<option value='"+dataList[i].id+"' selected>"+dataList[i].name+"</option>"
                        }else{
                            html+="<option value='"+dataList[i].id+"'>"+dataList[i].name+"</option>"
                        }
                    }
                    $('select[name="selectType"]').html(html);
                }else{
                    var html="";
                    for(var i=0;i<dataList.length;i++){
                        if(id==dataList[i].id){
                            html+="<option value='"+dataList[i].id+"' selected>"+dataList[i].name+"</option>"
                        }else{
                            html+="<option value='"+dataList[i].id+"'>"+dataList[i].name+"</option>"
                        }
                    }
                    $('select[name="selectType"]').html(html);
                }
            }
        }
    )
}
