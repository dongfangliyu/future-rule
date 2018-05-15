/**
 * Created by Administrator on 2017/5/2 0002.
 */
function logout() {
    layer.confirm('是否确定退出系统？', {
        btn: ['是', '否']
    }, function () {
        location.href = _ctx + "/logout";
    });
}
function changePwd() {
    $('#password').val("");
    $('#Nes_pas').val("");
    $('#c_mew_pas').val("");
    layer.open({
        type: 1,
        title: '修改密码',
        area: ['300px', '200px'],
        content: $('#change_Pass'),
        btn: ['确认','取消'],
        yes: function (index) {
            var Oldpwd = $('#password').val();
            var Newpwd = $('#Nes_pas').val();
            var ComfireNewpwd = $('#c_mew_pas').val();
            if (!Oldpwd || !Newpwd || !ComfireNewpwd) {
                layer.msg('密码不能为空!', {time: 2000});
                return;
            }
            if (Newpwd != ComfireNewpwd) {
                layer.msg('新密码与确认密码不一致!', {time: 2000});
                return;
            }
            Comm.ajaxPost(
                'user/changePwd',
                {
                    originPwd: Oldpwd,
                    confirmPwd: ComfireNewpwd,
                    newPwd: Newpwd
                },
                function (data) {
                    layer.closeAll();
                    layer.msg(data.msg, {time: 2000});
                }
            );
        }
    });
}