/**
 * Created by Administrator on 2017/5/2 0002.
 */
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
            param.account = $("[name='account']").val();
            param.trueName = $("[name='trueName']").val();
            param.tel = $("[name='mobile']").val();
        }
        paramFilter.param = param;

        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;

        return paramFilter;
    }
};
//初始化表格数据
$(function (){
    //获取权限集合
    Comm.ajaxPost(
        'user/getPermission',"{}",
        function(data){
            var data=data.data;
            var html='';
            for(var i=0;i<data.length;i++){
                if(data[i] =="/user/add"){
                    $("#addBtn").show();
                }
                if(data[i] =="/user/edit"){
                    $("#updateBtn").show();
                }
                if(data[i] =="/user/delete"){
                    $("#deleteBtn").show();
                }
                if(data[i] =="/user/resetPwd"){
                    $("#resetBtn").show();
                }
                if(data[i] =="/userRole/add"){
                    $("#assignBtn").show();
                }
            }

        },"application/json"
    );
    g_userManage.tableUser = $('#User_list').dataTable($.extend({
        'iDeferLoading':true,
        "bAutoWidth" : false,
        "Processing": true,
        "ServerSide": true,
        "sPaginationType": "full_numbers",
        "bPaginate": true,
        "bLengthChange": false,
        "dom": 'rt<"bottom"i><"bottom"flp><"clear">',
        "ajax" : function(data, callback, settings) {
            var queryFilter = g_userManage.getQueryCondition(data);
            Comm.ajaxPost('user/list',JSON.stringify(queryFilter),function(result){
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;
                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.resultCount;
                returnData.recordsFiltered = resPage.resultCount;
                returnData.data = resData;
                callback(returnData);
            },"application/json")

        },
        "order": [],
        "columns" :[
            {
                'data':null,
                "searchable":false,"orderable" : false
            },
            {"data":'organName',"searchable":false,"orderable" : false},
            {"data":'account',"searchable":false,"orderable" : false},
            {"data":'trueName',"searchable":false,"orderable" : false},
            {
                "data" : null,
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data.status==1){
                        return '启用'
                    }else{
                        return '停用'
                    }
                }
            },
            {"data":'tel',"searchable":false,"orderable" : false},
            {
                "data":"birth",
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data==null){
                        return "";
                    }else return json2TimeStamp(data);
                }
            },
            {"data":'remark',"searchable":false,"orderable" : false},
            {
                "data": "null",
                "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="updateUser(0,'+data.userId+')">修改</a>&nbsp;<a class="tabel_btn_style" onclick="asignRole('+data.userId+')">角色分配</a>&nbsp;<a class="tabel_btn_style_dele" onclick="resetPwd('+data.userId+')">重置密码</a>&nbsp;<a class="tabel_btn_style_dele" onclick="deleteUser('+data.userId+')">删除</a>');
            $('td', row).eq(8).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function(){
                    $("#userCheckBox_All").attr("checked",false);
                });
            });
            //全选
            $("#userCheckBox_All").click(function(J) {
                if (!$(this).prop("checked")) {
                    $("#User_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                } else {
                    $("#User_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                }
            });
            //重置
            $("#btn_search_reset").click(function() {
                $('input[name="account"]').val("");
                $('input[name="trueName"]').val("");
                $('input[name="mobile"]').val("");
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function(){
                    $("#userCheckBox_All").attr("checked",false);
                });
            });
            //操作
            $("#User_list").on("click",".operate",function(e){
                var target = e.target||window.event.target;
                var id = $(target).parents("tr").children().eq(1).children("input").val();
                updateUser(0,id);
            });
            //单选行
            $("#User_list tbody").delegate( 'tr','click',function(e){
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
                        }
                        $(input).attr('isChecked','false');
                    }
                }
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
//添加、编辑用户信息
function updateUser(sign,id) {
    if(sign==0){
        $('#organ').html("");
        Comm.ajaxPost(
            'user/detail', JSON.stringify(id),
            function(data){
                layer.closeAll();
                var user=data.data.user;
                var organStatus;
                Comm.ajaxPost(
                    'sysOrganization/findOne',"orgId="+user.orgId,function(data){
                        var organ=data.data;
                        organStatus = organ.status;//所属组织的状态
                        $('#organ').html('<option value="'+organ.id+'">'+organ.name+'</option>');
                    });
                $('input[name="user_account"]').val(user.account);
                $('input[name="user_mobile"]').val(user.tel);
                $('input[name="user_email"]').val(user.email);
                $('input[name="user_trueName"]').val(user.trueName);
                $("#organ").attr('disabled',true);
                if(user.status==1){
                    $("#qiyong").attr('selected','selected');
                }else{
                    $("#jinyong").attr('selected','selected');
                }
                $("#remark").val(user.remark);
                $("#isEdit").hide();
                layer.open({
                    type : 1,
                    title : '修改用户',
                    maxmin : true,
                    shadeClose : false,
                    area : [ '576px', '310px' ],
                    content : $('#Add_user_style'),
                    btn : [ '保存', '取消' ],
                    yes : function(index, layero) {
                        if ($('input[name="user_account"]').val() == "") {
                            layer.msg("登录用户名不能为空",{time:2000});
                            return;
                        }
                        if ($('input[name="user_mobile"]').val() == "") {
                            layer.msg("手机号码不能为空",{time:2000});
                            return;
                        }
                        var account=$('input[name="user_account"]').val();
                        var email=$('input[name="user_email"]').val();
                        var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                        if(email){
                            if(!emailReg.test(email)){
                                layer.msg("邮箱格式不正确",{time:2000});
                                return;
                            }
                        }
                        var tel=$('input[name="user_mobile"]').val();
                        var mobileReg=/^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
                        if(!mobileReg.test(tel)){
                            layer.msg("手机号码格式不正确",{time:2000});
                            return;
                        }
                        var trueName=$('input[name="user_trueName"]').val();
                        var remark=$("#remark").val();
                        var isLock=$("#isLock").val();
                        if(organStatus==0 && isLock==1){
                            layer.msg("所属组织停用，状态不可更改！",{time:1000},function () {
                                $("#jinyong").attr('selected','selected');
                            });
                            return
                        }
                        var user={
                            account:account,
                            email:email,
                            tel:tel,
                            trueName:trueName,
                            remark:remark,
                            userId:id,
                            isLock:parseInt(isLock),
                            status:parseInt(isLock)
                        };
                        Comm.ajaxPost(
                            'user/edit',JSON.stringify(user),
                            function(data){
                                layer.closeAll();
                                layer.msg(data.msg,{time:1000},function () {
                                    $('#User_list').dataTable().fnDraw(false);
                                });
                            }, "application/json"
                        );
                    }
                });
            },"application/json");
    }else{
        Comm.ajaxPost(
            'sysOrganization/validList',"{}",
            function(data){
                var data=data.data;
                var html='';
                for(var i=0;i<data.length;i++){
                    html+='<option value="'+data[i].id+'">'+data[i].name+'</option>'
                }
                $('#organ').html(html);
            },"application/json"
        );
        $("#organ").attr('disabled',false);
        $("#isEdit").show();
        $('input[name="user_account"]').val("");
        $('input[name="user_password"]').val("");
        $('input[name="user_mobile"]').val("");
        $('input[name="user_email"]').val("");
        $('input[name="user_trueName"]').val("");
        $("#remark").val("");
        $("#qiyong").attr('selected','selected');
        $("#organ").attr('disabled',false);
        layer.open({
            type : 1,
            title : '添加用户',
            maxmin : true,
            shadeClose : false,
            area : [ '576px', '370px' ],
            content : $('#Add_user_style'),
            btn : [ '保存', '取消' ],
            yes : function(index, layero) {
                if ($('input[name="user_account"]').val() == "") {
                    layer.msg("登录用户名/账号不能为空",{time:2000});
                    return;
                }
                if ($('input[name="user_password"]').val() == "") {
                    layer.msg("密码不能为空",{time:2000});
                    return;
                }
                if($('input[name="user_password"]').val().length<6){
                    layer.msg("密码长度需大于6位",{time:2000});
                    return;
                }
                if ($('input[name="user_mobile"]').val() == "") {
                    layer.msg("手机号码不能为空",{time:2000});
                    return;
                }
                var account=$('input[name="user_account"]').val();
                var password=$('input[name="user_password"]').val();
                var email=$('input[name="user_email"]').val();
                var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if(email){
                    if(!emailReg.test(email)){
                        layer.msg("邮箱格式不正确",{time:2000});
                        return;
                    }
                }
                var tel=$('input[name="user_mobile"]').val();
                var mobileReg=/^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
                if(!mobileReg.test(tel)){
                    layer.msg("手机号码格式不正确",{time:2000});
                    return;
                }
                var trueName=$('input[name="user_trueName"]').val();
                var remark=$("#remark").val();
                var isLock=$("#isLock").val();
                var organId=$('#organ option:selected').val();
                var user={
                    account:account,
                    password:password,
                    email:email,
                    tel:tel,
                    trueName:trueName,
                    remark:remark,
                    isLock:parseInt(isLock),
                    status:parseInt(isLock),
                    orgId:organId
                };
                Comm.ajaxPost(
                    'user/add',JSON.stringify(user),
                    function(data){
                        layer.closeAll();
                        layer.msg(data.msg,{time:1000},function () {
                            g_userManage.tableUser.ajax.reload(function(){
                                $("#userCheckBox_All").attr("checked",false);
                            });
                        });
                    },"application/json"
                );
            }
        });
    }

}
//启用或停止
/*function updateState(state) {
    var selectArray = $("#Data_list tbody input:checked");
    if (!selectArray || (selectArray.length <= 0)) {
        layer.msg("请选择一个用户", {time: 2000});
        return;
    }
    var param = {
        Ids: null,
        status: null
    }
    var roleIds = new Array();
    $.each(selectArray, function (i, e) {
        var val = $(this).val();
        roleIds.push(val);
    });
    if (roleIds.length == 0) {
        return;
    }

    param.Ids = roleIds;
    param.status = state;
    Comm.ajaxPost(
        'datamanage/listmanage/updateStatus', JSON.stringify(param),
        function (data) {
            layer.msg(data.msg, {time: 1000}, function () {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload(function(){
                    contentChange();//点击东西过长显示省略号
                });
            });
        }, "application/json"
    );

}*/
//删除用户信息
function deleteUser(userId){
    var userIds = new Array();
    userIds.push(userId);
    // var selectArray = $("#User_list tbody input:checked");
    // if(!selectArray || selectArray.length==0){
    //     layer.msg("请选择用户");
    //     return;
    // }
    // var userIds = new Array();
    // $.each(selectArray,function(i,e){
    //     var val = $(this).val();
    //     userIds.push(val);
    // });
    // if(userIds.length==0){
    //     return;
    // }
    layer.confirm('是否删除用户？', {
        btn : [ '确定', '取消' ]
    }, function() {
        Comm.ajaxPost(
            'user/delete', JSON.stringify(userIds),
            function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000},function () {
                    g_userManage.tableUser.ajax.reload(function(){
                        $("#userCheckBox_All").attr("checked",false);
                    });
                });
            },"application/json");
    });
}
//重置用户密码
function resetPwd(userId) {
    var userIds = new Array();
    userIds.push(userId);

    // var selectArray = $("#User_list tbody input:checked");
    // if(!selectArray || selectArray.length==0){
    //     layer.msg("请选择用户",{time:2000});
    //     return;
    // }
    // var userIds = new Array();
    // $.each(selectArray,function(i,e){
    //     var val = $(this).val();
    //     userIds.push(val);
    // });
    // if(userIds.lenght==0){
    //     return;
    // }
    layer.confirm('是否重置密码，重置后原密码将失效？', {
        btn : [ '重置', '取消' ]
    }, function() {
        Comm.ajaxPost(
            'user/resetPwd', JSON.stringify(userIds),
            function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000},function () {
                    g_userManage.tableUser.ajax.reload(function(){
                        $("#userCheckBox_All").attr("checked",false);
                    });
                });
            },"application/json"
        );
    });
}
//角色分配
function asignRole(userId) {
    Comm.ajaxPost('role/getRoleMap',JSON.stringify(userId),function(response){
            debugger
            $("#multiselect option").remove();
            $("#multiselect_to option").remove();
            var roleIds = response.data.roleIds;
            $.each(response.data.roleList,function(i,roleMap){
                var roleId = roleMap.roleId;
                if(roleIds.indexOf(roleId)>=0){
                    $("#multiselect_to").prepend("<option value='"+roleId+"'>"+roleMap.role_name+"</option>");
                }else{
                    $("#multiselect").prepend("<option value='"+roleId+"'>"+roleMap.role_name+"</option>");
                }
            });
        },"application/json");
    layer.open({
        type : 1,
        title : "角色分配",
        maxmin : true,
        area : [ '576px', '468px' ],
        content : $('#asignRole'),
        btn : [ '保存', '取消' ],
        yes : function(index, layero) {
            debugger
            var roleIds = [];
            $("#multiselect_to option").each(function(i,e){
                var selectVal = $(this).val();
                roleIds.push(selectVal);
            });
            var param = {"userId":userId,"roleIds":roleIds};
            Comm.ajaxPost('userRole/add',param, function(data){
                    layer.closeAll();
                    layer.msg(data.msg,{time:1000},function () {
                        g_userManage.tableUser.ajax.reload(function(){
                            $("#userCheckBox_All").attr("checked",false);
                        });
                    });
                })
        }});
}
//角色-单个按钮功能
function rightAll(){
    var options=$("#multiselect option");
    $("#multiselect_to").prepend(options);
}
function rightSelected(){
    var options=$("#multiselect option:selected");
    $("#multiselect_to").append(options);
    $("#multiselect_to option:selected").attr('selected',false);
}
function leftSelected(){
    var options=$("#multiselect_to option:selected");
    $("#multiselect").append(options);
    $("#multiselect option:selected").attr('selected',false);
}
function leftAll(){
    var options=$("#multiselect_to option");
    $("#multiselect").prepend(options);
}
