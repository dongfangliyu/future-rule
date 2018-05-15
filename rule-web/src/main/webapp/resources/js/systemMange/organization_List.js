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
            param.name = $("input[name='name']").val();
            param.tel = $("input[name='telephone']").val();
            param.creator = $("input[name='author']").val();
        }
        paramFilter.param = param;
        page.firstIndex = data.start == null ? 0 : data.start;
        page.pageSize = data.length  == null ? 10 : data.length;
        paramFilter.page = page;
        return paramFilter;
    }
};
$(function (){
    Comm.ajaxPost(
        'user/getPermission',"{}",
        function(data){
            var data=data.data;
            var html='';
            for(var i=0;i<data.length;i++){
                if(data[i] =="/sysOrganization/add"){
                    $("#addBtn").show();
                }
                if(data[i] =="/sysOrganization/update"){
                    $("#updateBtn").show();
                }
                if(data[i] =="/sysOrganization/delete") {
                    $("#deleteBtn").show();
                }
            }

        },"application/json"
    );
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
            Comm.ajaxPost('sysOrganization/list', JSON.stringify(queryFilter), function (result) {
                if (result.code==1) {
                    layer.msg(result.msg,{icon:2,offset:'200px',time:1000});
                    return;
                }
                //封装返回数据
                var returnData = {};
                var resData = result.data;
                var resPage = result.page;

                //$('#pagesize').val(resPage.pageSize);

                returnData.draw = data.draw;
                returnData.recordsTotal = resPage.resultCount;
                returnData.recordsFiltered = resPage.resultCount;
                returnData.data = resData;
                callback(returnData);
            },"application/json");
        },
        "order": [],
        "columns": [
            {"data": null ,"searchable":false,"orderable" : false},
            {"data": "name","orderable" : false},
            {"data": "code","orderable" : false},
            {"data": "email","orderable" : false},
            {"data": "tel","orderable" : false},
            {"data" : "status",
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    if(data==1){
                        return '启用'
                    }else{
                        return '停用'
                    }
                }
            },
            {"data": "creator","orderable" : false},
            {"data" : "birth",
                "searchable":false,
                "orderable" : false,
                "render" : function(data, type, row, meta) {
                    return json2TimeStamp(data);
                }
            },
            // {"data": "token","orderable" : false},
            {
                "data": "null",
                "orderable": false,
                "defaultContent":""
            }
        ],
        "createdRow": function ( row, data, index,settings,json ) {
            var btnDel = $('<a class="tabel_btn_style" onclick="updateOrger(0,\''+data.id+'\')">修改</a>&nbsp;<a class="tabel_btn_style_dele" onclick="deleteOrger('+data.id+')">删除</a>');
            $('td', row).eq(8).append(btnDel);
        },
        "initComplete" : function(settings,json) {
            //操作
            $("#orger_list").on("click",".operate",function(e){
                var target = e.target||window.event.target;
                var userId = $(target).parents("tr").children().eq(1).children("input").val();
                updateOrger(0,userId);
            });
            //全选
            $("#orgerCheckBox_All").click(function(J) {
                if (!$(this).prop("checked")) {
                    $("#orger_list tbody tr").find("input[type='checkbox']").prop("checked", false)
                } else {
                    $("#orger_list tbody tr").find("input[type='checkbox']").prop("checked", true)
                }
            });
            //搜索
            $("#btn_search").click(function() {
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //重置
            $("#btn_search_reset").click(function() {
                $("input[name='name']").val("");
                $("input[name='telephone']").val("");
                $("input[name='author']").val("");
                g_userManage.fuzzySearch = true;
                g_userManage.tableUser.ajax.reload();
            });
            //单选行
            $("#orger_list tbody").delegate( 'tr','click',function(e){
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
//添加、编辑组织信息
function updateOrger(sign,userId) {
    if(userId && sign==0){
        Comm.ajaxPost(
            'sysOrganization/detail',
            userId,
            function(data){
                layer.closeAll();
                var user=data.data;
                $('input[name="orger_account"]').val(user.name);
                $('input[name="orger_code"]').val(user.code);
                $('input[name="orger_mobile"]').val(user.tel);
                $('input[name="orger_email"]').val(user.email);
                if(user.status==1){
                    $("#orger_qiyong").attr('selected','selected');
                }else{
                    $("#orger_tingyong").attr('selected','selected');
                }
                layer.open({
                    type : 1,
                    title : '修改组织信息',
                    maxmin : true,
                    shadeClose : false,
                    area : [ '570px', '260px' ],
                    content : $('#Add_orger_style'),
                    btn : [ '保存', '取消' ],
                    yes : function(index, layero) {
                        if ($('input[name="orger_account"]').val() == "") {
                            layer.msg("组织名不能为空",{time:2000});
                            return;
                        }
                        if($('input[name="orger_code"]').val()==''){
                            layer.msg("组织代码不能为空",{time:2000});
                            return;
                        }
                        if ($('input[name="orger_mobile"]').val() == "") {
                            layer.msg("手机号码不能为空",{time:2000});
                            return;
                        }
                        var name=$('input[name="orger_account"]').val();
                        var code=$('input[name="orger_code"]').val();
                        /*if(code){
                            var msg=checkorgcode(code);
                            if(!msg){
                                layer.msg("组织机构代码不正确",{time:2000});
                                return;
                            }
                        }*/
                        var email=$('input[name="orger_email"]').val();
                        var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                        if(email){
                            if(!emailReg.test(email)){
                                layer.msg("邮箱格式不正确",{time:2000});
                                return;
                            }
                        }
                        var mobile=$('input[name="orger_mobile"]').val();
                        var mobileReg=/^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
                        if(!mobileReg.test(mobile)){
                            layer.msg("手机号码格式不正确",{time:2000});
                            return;
                        }
                        var isLock=$("#isLock").val();
                        var sysOrganization={
                            id:userId,
                            name:name,
                            code:code,
                            email:email,
                            tel:mobile,
                            status:isLock
                        };
                        Comm.ajaxPost(
                            'sysOrganization/edit',JSON.stringify(sysOrganization),
                            function(data){
                                layer.closeAll();
                                layer.msg(data.msg,{time:1000},function () {
                                    $('#orger_list').dataTable().fnDraw(false);
                                });
                            }, "application/json"
                        );
                    }
                });
            },"application/json");
    }else{
        $('input[name="orger_account"]').val("");
        $('input[name="orger_code"]').val("");
        $('input[name="orger_mobile"]').val("");
        $('input[name="orger_email"]').val("");
        $("#orger_qiyong").attr('selected','selected');
        layer.open({
            type : 1,
            title : '添加组织',
            maxmin : true,
            shadeClose : false,
            area : [ '570px', '260px' ],
            content : $('#Add_orger_style'),
            btn : [ '保存', '取消' ],
            yes : function(index, layero) {
                if ($('input[name="orger_account"]').val() == "") {
                    layer.msg("组织名不能为空",{time:2000});
                    return;
                }
                if($('input[name="orger_code"]').val()==''){
                    layer.msg("组织代码不能为空",{time:2000});
                    return;
                }
                if ($('input[name="orger_mobile"]').val() == "") {
                    layer.msg("手机号码不能为空",{time:2000});
                    return;
                }
                var name=$('input[name="orger_account"]').val();
                var code=$('input[name="orger_code"]').val();
                /*if(code){
                    var msg=checkorgcode(code);
                    if(!msg){
                        layer.msg("组织机构代码不正确",{time:2000});
                        return;
                    }
                }*/
                var email=$('input[name="orger_email"]').val();
                var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
                if(email){
                    if(!emailReg.test(email)){
                        layer.msg("邮箱格式不正确",{time:2000});
                        return;
                    }
                }
                var mobile=$('input[name="orger_mobile"]').val();
                var mobileReg=/^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$/;
                if(!mobileReg.test(mobile)){
                    layer.msg("手机号码格式不正确",{time:2000});
                    return;
                }
                var isLock=$("#isLock").val();
                var sysOrganization={
                    name:name,
                    code:code,
                    email:email,
                    tel:mobile,
                    status:isLock
                };
                Comm.ajaxPost(
                    'sysOrganization/add',JSON.stringify(sysOrganization),
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
//校验组织机构代码是否符合校验规则
function checkorgcode(orgcode){
        var ws = [3, 7, 9, 10, 5, 8, 4, 2];
        var str = '0123456789abcdefghijklmnopqrstuvwxyz';
        var reg = /^([0-9a-z]){8}-[0-9|x]$/;
        if (!reg.test(orgcode)) {
            return false;
        }
        var sum = 0;
        for (var i = 0; i < 8; i++) {
            sum += str.indexOf(orgcode.charAt(i)) * ws[i];
        }
        var c9 = 11 - (sum % 11);
        c9 = c9 == 10 ? 'x' : c9;
        return c9 == orgcode.charAt(9);
}
//删除组织
function deleteOrger(id){
    var userIds = new Array();
    userIds.push(id);
    layer.confirm('是否删除用户？', {
        btn : [ '确定', '取消' ]
    }, function() {
        Comm.ajaxPost(
            'sysOrganization/delete', JSON.stringify(userIds),
            function(data){
                layer.closeAll();
                layer.msg(data.msg,{time:1000},function () {
                    g_userManage.tableUser.ajax.reload(function(){
                        $("#orgerCheckBox_All").attr("checked",false);
                    });
                });
            },"application/json");
    });
}
//停用/启用
/*function updateStateOrger(sign){
    var selectArray = $("#orger_list tbody input:checked");
    if(!selectArray || selectArray.length==0){
        layer.msg("请选择用户");
        return;
    }
    var userIds = new Array();
    $.each(selectArray,function(i,e){
        var val = $(this).val();
        userIds.push(val);
    });
    if(userIds.length==0){
        return;
    }
    Comm.ajaxPost(
        'sysOrganization/updateStates', JSON.stringify(userIds),
        function(data){
            layer.closeAll();
            layer.msg(data.msg,{time:1000},function () {
                g_userManage.tableUser.ajax.reload();
            });
        },"application/json"
    );
}*/
