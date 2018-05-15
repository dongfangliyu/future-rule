package com.zw.rule.web.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.po.Role;
import com.zw.rule.po.User;
import com.zw.rule.service.RoleService;
import com.zw.rule.service.UserRoleService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;


@Controller
@RequestMapping("role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @GetMapping("listPage")
    public String list() {
        return "systemMange/roleList";
    }

    @ResponseBody
    @PostMapping("list")
    @WebLogger("查询角色列表")
    public Response list(@RequestBody ParamFilter queryFilter) {
        User user=(User)UserContextUtil.getAttribute("currentUser");
        Integer organId=(int)user.getOrgId();
        Map<String, Object> Param = queryFilter.getParam();
        if (UserContextUtil.getAttribute("roleId").equals("1")){
            Param.put("orgId", null);
        }else {
            Param.put("orgId", organId);
        }
        queryFilter.setParam(Param);
        List<Role> roleList = roleService.getList(queryFilter);
        Page page = queryFilter.getPage();
        return new Response(roleList, page);
    }

    @ResponseBody
    @PostMapping("save")
    @WebLogger("添加角色")
    public Response save(@RequestBody Role role) {
        checkNotNull(role, "角色信息不能为空");
        Response response = new Response();
        User user = (User) UserContextUtil.getAttribute("currentUser");
        if (Strings.isNullOrEmpty(role.getRoleId())) {
            role.setOrgId((int)((User)UserContextUtil.getAttribute("currentUser")).getOrgId());
            roleService.add(role);
        } else {
            roleService.update(role);
        }
        response.setMsg("添加成功！");
        return response;
    }

    @ResponseBody
    @PostMapping("add")
    public Response add(@RequestBody Role role) {
        checkNotNull(role, "角色信息不能为空");
        User user=(User)UserContextUtil.getAttribute("currentUser");
        //Integer organId=(int)user.getOrgId();
        //role.setOrganId(organId);
        role.setAuthor(user.getNickName());
        roleService.add(role);
        return new Response("添加成功！");
    }

    @ResponseBody
    @RequestMapping("update")
    @WebLogger("编辑角色")
    public Response update(@RequestBody Role role) {
        checkNotNull(role, "角色信息不能为空");
        roleService.update(role);
        return new Response("修改成功！");
    }

    @ResponseBody
    @PostMapping("delete")
    @WebLogger("删除角色")
    public Response delete(@RequestBody List<String> roleIds) {
        checkArgument((roleIds != null && roleIds.size() > 0), "不能为空");
        roleService.delete(roleIds);
        return new Response();
    }


    @ResponseBody
    @PostMapping("detail")
    @WebLogger("查询角色详细")
    public Response detail(@RequestBody  String roleId) {
        Role role = roleService.getRoleById(roleId);
        return new Response(role);
    }

    @ResponseBody
    @PostMapping("getRoleMap")
    public Response getRoleMap(@RequestBody long userId) {
        //checkArgument(!Strings.isNullOrEmpty(userId), "用户编号不能为空");
        Map<String, Object> Param = Maps.newHashMap();
        Integer organId=null;
        Param.put("userId", userId);
        //判断是不是超级管理员
        if (!UserContextUtil.getAttribute("roleId").equals("1")){
            organId=(int)((User)UserContextUtil.getAttribute("currentUser")).getOrgId();
        }
        Response response = new Response();
        List list = roleService.getRoleMap(userId,organId);
        Map<String, Object> resultMap = Maps.newHashMap();
        List<String> roleIds = userRoleService.getRoleIdsByUserId(userId);
        resultMap.put("roleIds", roleIds);
        resultMap.put("roleList", list);
        response.setData(resultMap);
        return response;
    }
}
