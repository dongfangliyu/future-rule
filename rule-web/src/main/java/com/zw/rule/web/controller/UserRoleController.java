package com.zw.rule.web.controller;

import com.google.common.base.Strings;
import com.zw.rule.core.Response;
import com.zw.rule.service.UserRoleService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkArgument;

@Controller
@RequestMapping("userRole")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    @ResponseBody
    @PostMapping("add")
    @WebLogger("添加用户-角色")
    public Response add(long userId, @RequestParam(name="roleIds[]",required=false)  String[] roleIds){
        //checkArgument(!Strings.isNullOrEmpty(userId),"用户编号不能为空");
        userRoleService.add(roleIds,userId);
        return new Response("保存成功");
    }
}
