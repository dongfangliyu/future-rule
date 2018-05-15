package com.zw.rule.web.controller;

import com.zw.rule.core.Response;
import com.zw.rule.po.User;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.service.UserService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.ServletUtil;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("listPage")
    public String list() {
        return "systemMange/userList";
    }


    @ResponseBody
    @PostMapping("list")
    @WebLogger("查询用户列表")
    public Response list(@RequestBody ParamFilter queryFilter) {
        User user=(User)UserContextUtil.getAttribute("currentUser");
        long orgId=user.getOrgId();
        Map<String, Object> Param = queryFilter.getParam();
        if (UserContextUtil.getAttribute("roleId").equals("1")){
            Param.put("orgId", null);
        }else {
            Param.put("orgId", orgId);
        }
        queryFilter.setParam(Param);
        List userList = userService.getList(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(userList, page);
    }

    @ResponseBody
    @PostMapping("add")
    @WebLogger("添加用户")
    public Response add(@RequestBody User user) {
        checkNotNull(user, "用户不能为空");
        String nickname = UserContextUtil.getNickName();
        user.setCreator(nickname);
        user.setUpdateBy(nickname);
        user.setLatestIp(ServletUtil.getIpAddr());
        userService.add(user);
        return new Response("添加成功");
    }


    @ResponseBody
    @PostMapping("edit")
    @WebLogger("编辑用户")
    public Response edit(@RequestBody User user) {
        String nickname = UserContextUtil.getNickName();
        user.setUpdateBy(nickname);
        user.setLatestIp(ServletUtil.getIpAddr());
        userService.update(user);
        return new Response("修改成功");
    }

    @ResponseBody
    @PostMapping("delete")
    @WebLogger("删除用户")
    public Response delete(@RequestBody List<Long> userIds) {
        checkArgument((userIds != null && userIds.size() > 0), "用户编号不能为空");
        userService.delete(userIds);
        return new Response("删除成功");
    }

    @ResponseBody
    @PostMapping("resetPwd")
    @WebLogger("重置密码")
    public Response resetPwd(@RequestBody List<Long> userIds) {
        checkArgument((userIds != null && userIds.size() > 0), "用户编号不能为空");
        userService.updateDefaultPwd(userIds);
        return new Response("重置成功");
    }


    @ResponseBody
    @PostMapping("changePwd")
    @WebLogger("更改密码")
    public Response changePwd(String originPwd, String confirmPwd, String newPwd) {
        long userId = UserContextUtil.getUserId();
        userService.updatePwd(originPwd, newPwd, confirmPwd, userId);
        return new Response("更改密码成功");
    }

    @ResponseBody
    @PostMapping("detail")
    @WebLogger("查询用户详细")
    public Response detail(@RequestBody long userId) {
        Map user = userService.getDetail(userId);
        return new Response(user);
    }

    @ResponseBody
    @PostMapping("getPermission")
    public Response getPermission(@RequestBody ParamFilter queryFilter) {
        //List userList = userService.getList(queryFilter);
        String account=UserContextUtil.getAttribute("account").toString();
        List userList=userService.getPermission(account);
        return new Response(userList);
    }
}
