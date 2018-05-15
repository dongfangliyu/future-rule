package com.zw.rule.web.controller;

import com.zw.rule.core.Response;
import com.zw.rule.mybatis.ParamFilter;
import com.zw.rule.mybatis.page.Page;
import com.zw.rule.po.SysOrganization;
import com.zw.rule.po.User;
import com.zw.rule.service.SysOrganizationService;
import com.zw.rule.web.aop.annotaion.WebLogger;
import com.zw.rule.web.util.UserContextUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/******************************************************
 *Copyrights @ 2017，zhiwang  Co., Ltd.
 *
 *All rights reserved.
 *          规则引擎
 *Filename：
 *          组织管理controller
 *Description：
 *
 *Author:
 *		 李开艳
 *Finished：
 *		2017年5月5日
 ********************************************************/
@Controller
@RequestMapping({"sysOrganization"})
public class SysOrganizationController {

    @Resource
    public SysOrganizationService sysOrganizationService;

    @GetMapping("listPage")
    public String list() {
        return "systemMange/organizationList";
    }

    @ResponseBody
    @PostMapping("list")
    @WebLogger("查询组织列表")
    public Response list(@RequestBody ParamFilter queryFilter) {
        List<SysOrganization> menuList = new ArrayList<SysOrganization>();
        User user = (User) UserContextUtil.getAttribute("currentUser");
        String roleId = UserContextUtil.getCurrentRoleId();
        //判断用户是否为超级管理员
        if(roleId.equals("1")){
            menuList = sysOrganizationService.getAllSysOrganization(queryFilter);
        }else{
            menuList.add(sysOrganizationService.findById(user.getOrgId()));
        }
        Page page = queryFilter.getPage();
        return new Response(menuList,page);
    }

    @ResponseBody
    @PostMapping("validList")
    public Response validList() {
        List<SysOrganization> menuList = new ArrayList<SysOrganization>();
        User user = (User) UserContextUtil.getAttribute("currentUser");
        String roleId = UserContextUtil.getCurrentRoleId();
        //判断用户是否为超级管理员
        if(roleId.equals("1")){
            menuList = sysOrganizationService.getAllValidOrgan();
        }else{
            menuList.add(sysOrganizationService.findById(user.getOrgId()));
        }
        return new Response(menuList);
    }

    @ResponseBody
    @PostMapping("findOne")
    public Response findOne(long orgId) {
        SysOrganization sysOrganization = sysOrganizationService.findById(orgId);
        return new Response(sysOrganization);
    }

    @ResponseBody
    @PostMapping({"add"})
    @WebLogger("添加组织")
    public Response createSysOrganization(@RequestBody SysOrganization sysOrganization) {
        Response response = new Response();
        User user = (User) UserContextUtil.getAttribute("currentUser");
        String roleId = UserContextUtil.getCurrentRoleId();
        //判断用户是否为超级管理员
        if(roleId.equals("1")){
            List list = sysOrganizationService.validateOrganOnly(sysOrganization);
            if(list != null && list.size() > 0) {
                response.setMsg("名称或编号已存在！");
            } else if(sysOrganizationService.createSysOrganization(sysOrganization,user) > 0) {
                //this.createKnowledgeTree(sysOrganization);
                response.setMsg("创建成功！");
            } else {
                response.setMsg("创建失败！");
            }
        }else{
            response.setMsg("没有权限！");
        }
        return  response;
    }

    @ResponseBody
    @PostMapping("detail")
    @WebLogger("查询组织详细")
    public Response detail(@RequestBody long organId) {
        SysOrganization sysOrganization = sysOrganizationService.findById(organId);
        return new Response(sysOrganization);
    }

    @ResponseBody
    @PostMapping("edit")
    @WebLogger("编辑组织")
    public Response updateSysOrganization(@RequestBody SysOrganization sysOrganization) {
        Response response = new Response();
        User user = (User) UserContextUtil.getAttribute("currentUser");
        String roleId = UserContextUtil.getCurrentRoleId();
        //判断用户是否为超级管理员
        if(roleId.equals("1")){
            if(sysOrganizationService.updateSysOrganization(sysOrganization) > 0) {
                response.setMsg("修改成功！");
            } else {
                response.setMsg("修改失败！");
            }
        }else{
            response.setMsg("没有权限！");
        }
        return  response;
    }

    /**
     * 删除组织，其下的角色表，用户表，用户-角色表，角色-菜单也相应删除
     * 实际为假删，status改为-1
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("delete")
    @WebLogger("删除组织")
    public Response delete(@RequestBody List<String> ids) {
        sysOrganizationService.deleteSysOrganization(ids);
        return new Response("删除成功");
    }

    /**
     * 暂未用
     * @param status
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("updateStates")
    @WebLogger("启、停用组织")
    public Response updateStates(int status, @RequestBody List<String> ids) {
        int num = 0;
        if(ids != null && ids.size() > 0) {
            num = sysOrganizationService.updateStatus(status, ids);
        }
        return num > 0?new Response("修改成功！"):new Response("修改失败！");
    }
}
